# Pocket IDE — Design System

## 1. Principles
- **Clarity over Decoration**: No floating glowing clouds, artificial glassmorphic blurs, or nested rounded cards.
- **Developer Restraint**: Charcoal/obsidian canvas (`#141414`), grounded card/bar surface (`#1C1C1C`), and a singular high-contrast action blue (`#3B82F6` dark / `#0969DA` light).
- **Semantics Reserved**: Emerald/Green is strictly for "Running" / "Success" / "Git Added". Amber is strictly for "Warning" / "Pending Approval". Rose/Red is strictly for "Error" / "Destructive" / "Git Removed".
- **Ergonomic Typography**: Distinct font scale ratio (> 1.25x between heading steps), minimum 11sp for any functional UI text, and Monospace for all file paths, commands, code, and system metadata.
- **Subtle Motion**: Fast, linear-out-slow deceleration curves (200-250ms). Zero elastic or spring bouncing.

## 2. Color System

### Dark Theme (Default)
| Token | Hex | Role |
| --- | --- | --- |
| `PocketObsidian` | `#141414` | Window canvas |
| `PocketSurface` | `#1C1C1C` | Primary cards, bars, dialogs |
| `PocketSurfaceElevated` | `#282828` | Raised interactive controls, search inputs |
| `PocketBorder` | `#2C2C2C` | Subtle structural dividers and container borders |
| `PocketTextPrimary` | `#F0F0F0` | High-contrast body and headings |
| `PocketTextSecondary` | `#A0A0A0` | Subtitles, secondary descriptions |
| `PocketTextMuted` | `#707070` | Inactive icons, timestamps, placeholders |
| `PocketPrimaryBlue` | `#3B82F6` | Primary buttons, active tabs, selected states |
| `PocketBlueSurface` | `#1E293B` | Subtle blue badge and active container tint |
| `PocketEmerald` | `#10B981` | Success, live process, Git additions |
| `PocketAmber` | `#F59E0B` | Warning, confirmation requested |
| `PocketRose` | `#EF4444` | Errors, destructive actions, Git deletions |

### Light Theme
| Token | Hex | Role |
| --- | --- | --- |
| `PocketObsidian` | `#F6F8FA` | Window canvas |
| `PocketSurface` | `#FFFFFF` | Primary cards, bars, dialogs |
| `PocketSurfaceElevated` | `#F0F2F5` | Raised controls, inputs |
| `PocketBorder` | `#D0D7DE` | Subtle dividers and borders |
| `PocketTextPrimary` | `#1F2328` | Primary text |
| `PocketTextSecondary` | `#59636E` | Secondary metadata |
| `PocketTextMuted` | `#8C959F` | Muted tertiary text |
| `PocketPrimaryBlue` | `#0969DA` | Primary blue accent |

## 3. Typography Scale
| Role | Size | Weight | Font Family | Tracking |
| --- | --- | --- | --- | --- |
| Title Large | 20sp | Bold | Default | -0.3sp |
| Title Medium | 16sp | SemiBold | Default | -0.2sp |
| Subhead | 14sp | SemiBold | Default | 0sp |
| Body | 13.5sp | Regular / Medium | Default | 0sp |
| Caption / Meta | 11.5sp | Medium | Default | 0.1sp |
| Code / Terminal | 12sp | Medium | Monospace | 0sp |
| Code Small / Badges | 11sp | SemiBold | Monospace | 0.2sp |

Floor rule: No functional text under 11sp.

## 4. Spacing & Radius Scale
- **Spacing Steps**: 4dp, 8dp, 12dp, 16dp, 24dp, 32dp.
- **Component Radii**:
  - Micro-tags / Badges: 4dp
  - Buttons / Inputs / List Items: 8dp
  - Primary Panels / Modals / Bottom Sheets: 12dp
  - Floating Nav Dock: 12dp
- **Anti-Pattern Guard**: No `16dp+` corner radius on small buttons. No nesting rounded cards inside rounded cards without visual hierarchy.

## 5. Touch & Accessibility
- Minimum interactive touch target: 44dp x 44dp (or at least 36-40dp height with full horizontal measure).
- Active vs inactive states must be clearly discernible without relying exclusively on color hue.
- Text contrast must exceed WCAG AA (4.5:1 for normal text, 3:1 for large text).
