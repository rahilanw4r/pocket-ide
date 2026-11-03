#!/usr/bin/env python3
import os
import sys
import json
import glob
import urllib.request
import urllib.error

def main():
    token = os.environ.get("GH_TOKEN") or os.environ.get("GITHUB_TOKEN")
    if not token:
        print("Error: GH_TOKEN or GITHUB_TOKEN environment variable required.")
        sys.exit(1)

    repo = os.environ.get("GITHUB_REPOSITORY", "rahilanw4r/pocket-ide")
    tag = os.environ.get("TAG", "v1.0.40")
    title = f"Pocket IDE {tag}: Development environment for Android"

    notes_file = "release_notes.md"
    body = ""
    if os.path.exists(notes_file):
        with open(notes_file, "r", encoding="utf-8") as f:
            body = f.read()

    headers = {
        "Authorization": f"Bearer {token}",
        "Accept": "application/vnd.github+json",
        "User-Agent": "Pocket-IDE-Release-Bot",
        "X-GitHub-Api-Version": "2022-11-28"
    }

    print(f"Creating or fetching release for {repo} tag {tag}...")

    # Check if release exists
    release_url = f"https://api.github.com/repos/{repo}/releases/tags/{tag}"
    req = urllib.request.Request(release_url, headers=headers)
    release = None

    try:
        with urllib.request.urlopen(req) as resp:
            release = json.loads(resp.read().decode("utf-8"))
            print(f"Release already exists with ID: {release.get('id')}")
    except urllib.error.HTTPError as e:
        if e.code == 404:
            print("Release does not exist yet. Creating new release...")
        else:
            print(f"Unexpected status checking release: {e.code} {e.read().decode('utf-8', errors='ignore')}")

    if not release:
        create_url = f"https://api.github.com/repos/{repo}/releases"
        payload = {
            "tag_name": tag,
            "target_commitish": "main",
            "name": title,
            "body": body,
            "draft": False,
            "prerelease": False
        }
        create_req = urllib.request.Request(
            create_url,
            data=json.dumps(payload).encode("utf-8"),
            headers=headers,
            method="POST"
        )
        try:
            with urllib.request.urlopen(create_req) as resp:
                release = json.loads(resp.read().decode("utf-8"))
                print(f"Successfully created release ID: {release.get('id')}")
        except urllib.error.HTTPError as e:
            print(f"Error creating release ({e.code}): {e.read().decode('utf-8', errors='ignore')}")
            sys.exit(1)

    release_id = release["id"]

    # Locate APK
    apk_pattern = "Pocket-IDE-*.apk"
    apk_files = glob.glob(apk_pattern)
    if not apk_files:
        apk_files = glob.glob("app/build/outputs/apk/online/debug/*.apk")
    if not apk_files:
        print("Error: No APK file found to upload.")
        sys.exit(1)

    apk_file = apk_files[0]
    apk_name = f"Pocket-IDE-{tag}.apk"
    file_size = os.path.getsize(apk_file)
    print(f"Found APK: {apk_file} ({file_size} bytes). Target name: {apk_name}")

    # Check existing assets to delete if replacing
    existing_assets = release.get("assets", [])
    for asset in existing_assets:
        if asset.get("name") == apk_name:
            asset_id = asset.get("id")
            print(f"Deleting existing asset ID: {asset_id}...")
            del_url = f"https://api.github.com/repos/{repo}/releases/assets/{asset_id}"
            del_req = urllib.request.Request(del_url, headers=headers, method="DELETE")
            try:
                with urllib.request.urlopen(del_req) as resp:
                    print("Existing asset deleted.")
            except Exception as e_del:
                print(f"Warning deleting old asset: {e_del}")

    # Upload APK asset
    upload_url = f"https://uploads.github.com/repos/{repo}/releases/{release_id}/assets?name={apk_name}"
    upload_headers = {
        "Authorization": f"Bearer {token}",
        "Accept": "application/vnd.github+json",
        "Content-Type": "application/vnd.android.package-archive",
        "Content-Length": str(file_size),
        "User-Agent": "Pocket-IDE-Release-Bot",
        "X-GitHub-Api-Version": "2022-11-28"
    }

    print(f"Uploading {apk_name} to {upload_url}...")
    with open(apk_file, "rb") as f_data:
        upload_req = urllib.request.Request(
            upload_url,
            data=f_data,
            headers=upload_headers,
            method="POST"
        )
        try:
            with urllib.request.urlopen(upload_req) as resp:
                asset_resp = json.loads(resp.read().decode("utf-8"))
                print(f"Upload complete! Asset ID: {asset_resp.get('id')}, URL: {asset_resp.get('browser_download_url')}")
        except urllib.error.HTTPError as e:
            print(f"Error uploading asset ({e.code}): {e.read().decode('utf-8', errors='ignore')}")
            sys.exit(1)

    print(f"Release {tag} published successfully!")

if __name__ == "__main__":
    main()
