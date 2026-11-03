# Pocket IDE — Product Truth

## Purpose
Pocket IDE is a mobile-first Android development environment powered by an isolated PRoot Linux userspace, integrated Git workflows, code editor, terminal, in-app web preview, and integrated AI coding agents (Google Antigravity agy, Claude Code, and custom API providers). It turns ARM64 Android devices into serious, self-contained development machines.

## Target Audience
- Software engineers, mobile developers, and students who need a portable development workstation on Android phones and tablets.
- Developers looking to run local developer tools (Node.js, Python, C++, Android SDK, Git) and use AI coding assistants directly from mobile.

## Core Capabilities & Features
1. **Workspace Management**: Instant ephemeral sandboxes, persistent project folders, Git cloning, and ZIP imports.
2. **Editor & Code Studio**: Code browsing, mobile-adapted file editing, syntax handling, and file-by-file diff reviews.
3. **ARM64 Linux Terminal**: Native pty shell execution in PRoot, virtual developer accessory keys (Esc, Tab, Ctrl-C, arrows, pipe), and quick dev scripts.
4. **AI Coding Agent**: Streaming chat with tool approval checkpoints, expandable reasoning chains, diff visualization, and model selection.
5. **In-App Web Preview**: Live responsive browser view for local web dev servers (Vite, Next, Express, static HTML) with device presets.
6. **System & Toolchain Health**: Diagnostic controls, automated dependency setup, theme switching, and offline resilience.

## Primary User Journey
1. **Launch**: Instant overview of active workspace or quick sandbox launcher.
2. **Edit & Collaborate**: Edit code directly or prompt the AI agent to make changes across the project.
3. **Review**: Inspect line-by-line diffs with one-tap accept/revert before applying to files.
4. **Execute**: Run build commands, tests, or interactive REPLs in the Linux terminal.
5. **Preview**: Verify frontend changes in the live web preview.

## Design Philosophy & Tone
- **Minimal, Utilitarian, Purposeful**: Every element exists to assist developer flow. No decorative fluff, fake glow effects, or decorative bounce animations.
- **Calm, High-Contrast Palette**: Charcoal/obsidian base in dark mode, crisp clean slate in light mode, single primary action blue, with semantic colors (green, amber, red) reserved exclusively for system status and errors.
- **Effortless Mobile Ergonomics**: Generous tap targets (minimum 44dp), comfortable spacing, single-handed reachability, and clean visual hierarchy.

