package com.pocketide.app.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pocketide.app.R
import com.pocketide.app.model.AgentKind
import com.pocketide.app.model.Project
import com.pocketide.app.model.ProjectKind
import com.pocketide.app.ui.AppUiState
import com.pocketide.app.ui.components.PocketPrimaryButton
import com.pocketide.app.ui.components.PocketSecondaryButton
import com.pocketide.app.ui.theme.PocketBorder
import com.pocketide.app.ui.theme.PocketCyan
import com.pocketide.app.ui.theme.PocketEmerald
import com.pocketide.app.ui.theme.PocketObsidian
import com.pocketide.app.ui.theme.PocketPrimaryBlue
import com.pocketide.app.ui.theme.PocketSurface
import com.pocketide.app.ui.theme.PocketSurfaceElevated
import com.pocketide.app.ui.theme.PocketTextMuted
import com.pocketide.app.ui.theme.PocketTextPrimary
import com.pocketide.app.ui.theme.PocketTextSecondary

@Composable
fun PocketHomeScreen(
    state: AppUiState,
    listState: LazyListState,
    onOpenProject: (Project) -> Unit,
    onCreateProject: (String) -> Unit,
    onCreateQuickProject: () -> Unit,
    onViewAllProjects: () -> Unit,
    onOpenCommandCenter: () -> Unit,
    onOpenDeveloper: () -> Unit,
    onAskCopilot: (String) -> Unit,
    modifier: Modifier = Modifier,
    onOpenTerminal: () -> Unit = {},
    onOpenMore: () -> Unit = onOpenDeveloper,
) {
    var showCreateDialog by remember { mutableStateOf(false) }
    var newProjectName by remember { mutableStateOf("") }
    var promptInput by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    var showCommunityCard by rememberSaveable { mutableStateOf(true) }

    val recentProjects = remember(state.projects) {
        state.projects.take(4)
    }

    val quickTemplates = listOf(
        "React + Vite" to "Create a modern React web app with Vite and Tailwind CSS",
        "Python Script" to "Build a Python utility script with CLI arguments and clean structure",
        "Android UI" to "Create a Jetpack Compose Android UI screen with Material 3",
        "REST API" to "Build a lightweight Express.js REST API with health and data routes",
        "Scratchpad" to "Start a blank project with an index.html and script.js"
    )

    LazyColumn(
        state = listState,
        modifier = modifier
            .fillMaxSize()
            .background(PocketObsidian),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Top Bar: App Identity, Linux Status & Quick Header Actions
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.app_logo),
                        contentDescription = "Pocket IDE",
                        modifier = Modifier
                            .size(30.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "Pocket IDE",
                                color = PocketTextPrimary,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = (-0.2).sp
                            )
                            // Linux status badge
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(PocketEmerald.copy(alpha = 0.12f))
                                    .border(1.dp, PocketEmerald.copy(alpha = 0.3f), RoundedCornerShape(4.dp))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "PRoot ARM64",
                                    color = PocketEmerald,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                        }
                        Text(
                            text = "Mobile Linux Development",
                            color = PocketTextMuted,
                            fontSize = 11.sp
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Quick Terminal Action
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(PocketSurfaceElevated)
                            .border(1.dp, PocketBorder, RoundedCornerShape(10.dp))
                            .clickable(onClick = onOpenTerminal),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Terminal,
                            contentDescription = "Terminal",
                            tint = PocketTextSecondary,
                            modifier = Modifier.size(17.dp)
                        )
                    }

                    // Command Center Search
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(PocketSurfaceElevated)
                            .border(1.dp, PocketBorder, RoundedCornerShape(10.dp))
                            .clickable(onClick = onOpenCommandCenter),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = PocketTextSecondary,
                            modifier = Modifier.size(17.dp)
                        )
                    }

                    // More / Settings
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(PocketSurfaceElevated)
                            .border(1.dp, PocketBorder, RoundedCornerShape(10.dp))
                            .clickable(onClick = onOpenMore),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Settings",
                            tint = PocketTextSecondary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }

        // 2. Active AI Agent & Model Status Pill
        item {
            val agentTitle = state.agentKind.title
            val modelName = if (state.agentKind == AgentKind.ANTIGRAVITY) {
                if (state.antigravityModel.isNotBlank()) state.antigravityModel else "Gemini 3.8 Flash"
            } else {
                if (state.provider.model.isNotBlank()) state.provider.model else state.provider.kind.title
            }

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .clickable(onClick = onOpenMore),
                shape = RoundedCornerShape(12.dp),
                color = PocketSurface,
                border = androidx.compose.foundation.BorderStroke(1.dp, PocketBorder)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(PocketPrimaryBlue.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = PocketPrimaryBlue,
                                modifier = Modifier.size(13.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "Active Coding Engine",
                                color = PocketTextMuted,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "$agentTitle · $modelName",
                                color = PocketTextPrimary,
                                fontSize = 12.5.sp,
                                fontWeight = FontWeight.SemiBold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Configure",
                        tint = PocketTextMuted,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }

        // 3. Fast Launcher: "What do you want to build?"
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = PocketSurface,
                border = androidx.compose.foundation.BorderStroke(1.dp, PocketBorder)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "What do you want to build?",
                        color = PocketTextPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = (-0.3).sp
                    )

                    // Prompt Input Field with inline Send button
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        color = PocketSurfaceElevated,
                        border = androidx.compose.foundation.BorderStroke(1.dp, PocketBorder)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            BasicTextField(
                                value = promptInput,
                                onValueChange = { promptInput = it },
                                modifier = Modifier.weight(1f),
                                textStyle = TextStyle(
                                    color = PocketTextPrimary,
                                    fontSize = 13.5.sp,
                                    lineHeight = 18.sp
                                ),
                                cursorBrush = SolidColor(PocketPrimaryBlue),
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                                keyboardActions = KeyboardActions(
                                    onSend = {
                                        val text = promptInput.trim()
                                        if (text.isNotBlank()) {
                                            promptInput = ""
                                            onAskCopilot(text)
                                        }
                                    }
                                ),
                                decorationBox = { inner ->
                                    if (promptInput.isEmpty()) {
                                        Text(
                                            text = "Describe an app, script, or question…",
                                            color = PocketTextMuted,
                                            fontSize = 13.sp
                                        )
                                    }
                                    inner()
                                }
                            )

                            // Submit Button
                            val canSubmit = promptInput.isNotBlank()
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(if (canSubmit) PocketPrimaryBlue else PocketBorder)
                                    .clickable(enabled = canSubmit) {
                                        val text = promptInput.trim()
                                        if (text.isNotBlank()) {
                                            promptInput = ""
                                            onAskCopilot(text)
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.Send,
                                    contentDescription = "Send",
                                    tint = if (canSubmit) Color.White else PocketTextMuted,
                                    modifier = Modifier.size(15.dp)
                                )
                            }
                        }
                    }

                    // Quick starter templates chips
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        quickTemplates.forEach { (label, prompt) ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(PocketSurfaceElevated)
                                    .border(1.dp, PocketBorder, RoundedCornerShape(8.dp))
                                    .clickable {
                                        promptInput = prompt
                                    }
                                    .padding(horizontal = 9.dp, vertical = 5.dp)
                            ) {
                                Text(
                                    text = label,
                                    color = PocketTextSecondary,
                                    fontSize = 11.5.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }
        }

        // 4. Quick Action Tiles (2x2 Grid: New Project, Instant Sandbox, Open/Import, Terminal)
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    HomeActionCard(
                        title = "New Project",
                        subtitle = "Create workspace",
                        icon = Icons.Default.Add,
                        accentColor = PocketPrimaryBlue,
                        onClick = {
                            newProjectName = ""
                            showCreateDialog = true
                        },
                        modifier = Modifier.weight(1f)
                    )

                    HomeActionCard(
                        title = "Quick Sandbox",
                        subtitle = "Instant container",
                        icon = Icons.Default.Bolt,
                        accentColor = PocketPrimaryBlue,
                        onClick = onCreateQuickProject,
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    HomeActionCard(
                        title = "Import Project",
                        subtitle = "ZIP or Git clone",
                        icon = Icons.Default.FolderOpen,
                        accentColor = PocketTextSecondary,
                        onClick = onViewAllProjects,
                        modifier = Modifier.weight(1f)
                    )

                    HomeActionCard(
                        title = "Linux Terminal",
                        subtitle = "Full ARM64 shell",
                        icon = Icons.Default.Terminal,
                        accentColor = PocketTextSecondary,
                        onClick = onOpenTerminal,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // 5. Recent Workspaces Header & List
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Recent Workspaces",
                        color = PocketTextPrimary,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    if (state.projects.isNotEmpty()) {
                        Text(
                            text = "View all (${state.projects.size})",
                            color = PocketPrimaryBlue,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.clickable(onClick = onViewAllProjects)
                        )
                    }
                }

                if (recentProjects.isEmpty()) {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        color = PocketSurface,
                        border = androidx.compose.foundation.BorderStroke(1.dp, PocketBorder)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 28.dp, horizontal = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(PocketSurfaceElevated),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.FolderOpen,
                                    contentDescription = null,
                                    tint = PocketTextMuted,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                            Text(
                                text = "No workspaces yet",
                                color = PocketTextPrimary,
                                fontSize = 14.5.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "Start with a new project or instant sandbox above.",
                                color = PocketTextSecondary,
                                fontSize = 12.sp
                            )
                        }
                    }
                } else {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        recentProjects.forEach { project ->
                            key(project.id) {
                                Surface(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(12.dp))
                                        .clickable { onOpenProject(project) },
                                    shape = RoundedCornerShape(12.dp),
                                    color = PocketSurface,
                                    border = androidx.compose.foundation.BorderStroke(1.dp, PocketBorder)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 14.dp, vertical = 12.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .size(38.dp)
                                                    .clip(RoundedCornerShape(8.dp))
                                                    .background(
                                                        if (project.kind == ProjectKind.QUICK_PROJECT)
                                                            PocketCyan.copy(alpha = 0.12f)
                                                        else PocketPrimaryBlue.copy(alpha = 0.12f)
                                                    ),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Icon(
                                                    imageVector = if (project.kind == ProjectKind.QUICK_PROJECT)
                                                        Icons.Default.Bolt
                                                    else Icons.Default.Folder,
                                                    contentDescription = null,
                                                    tint = if (project.kind == ProjectKind.QUICK_PROJECT)
                                                        PocketCyan
                                                    else PocketPrimaryBlue,
                                                    modifier = Modifier.size(20.dp)
                                                )
                                            }
                                            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                                ) {
                                                    Text(
                                                        text = project.name,
                                                        color = PocketTextPrimary,
                                                        fontSize = 14.sp,
                                                        fontWeight = FontWeight.SemiBold,
                                                        maxLines = 1,
                                                        overflow = TextOverflow.Ellipsis
                                                    )
                                                    if (project.kind == ProjectKind.QUICK_PROJECT) {
                                                        Box(
                                                            modifier = Modifier
                                                                .clip(RoundedCornerShape(4.dp))
                                                                .background(PocketPrimaryBlue.copy(alpha = 0.12f))
                                                                .padding(horizontal = 5.dp, vertical = 2.dp)
                                                        ) {
                                                            Text(
                                                                text = "Sandbox",
                                                                color = PocketPrimaryBlue,
                                                                fontSize = 11.sp,
                                                                fontWeight = FontWeight.Bold
                                                            )
                                                        }
                                                    }
                                                }
                                                Text(
                                                    text = if (project.rootPath.isNotBlank()) project.rootPath else "/workspace/${project.slug}",
                                                    color = PocketTextMuted,
                                                    fontSize = 11.sp,
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis,
                                                    fontFamily = FontFamily.Monospace
                                                )
                                                Text(
                                                    text = "${project.language.ifBlank { "General" }} · ${project.formattedUpdatedAt}",
                                                    color = PocketTextSecondary,
                                                    fontSize = 11.sp,
                                                    maxLines = 1
                                                )
                                            }
                                        }

                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                            contentDescription = "Open",
                                            tint = PocketTextMuted,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // 6. Community & Telegram Support Card
        if (showCommunityCard) {
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    color = PocketSurface,
                    border = androidx.compose.foundation.BorderStroke(1.dp, PocketBorder)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(28.dp)
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(PocketSurfaceElevated),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.ic_telegram),
                                        contentDescription = "Telegram",
                                        tint = Color.Unspecified,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Column {
                                    Text(
                                        text = "Pocket IDE Community",
                                        color = PocketTextPrimary,
                                        fontSize = 13.5.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(
                                        text = "Official updates & discussion",
                                        color = PocketTextSecondary,
                                        fontSize = 11.sp
                                    )
                                }
                            }

                            Text(
                                text = "Dismiss",
                                color = PocketTextMuted,
                                fontSize = 11.5.sp,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .clickable { showCommunityCard = false }
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(PocketSurfaceElevated)
                                    .border(1.dp, PocketBorder, RoundedCornerShape(8.dp))
                                    .clickable {
                                        try {
                                            val intent = android.content.Intent(
                                                android.content.Intent.ACTION_VIEW,
                                                android.net.Uri.parse("https://t.me/PocketIDE")
                                            )
                                            context.startActivity(intent)
                                        } catch (_: Exception) {}
                                    }
                                    .padding(vertical = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Telegram Channel",
                                    color = PocketTextPrimary,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(PocketSurfaceElevated)
                                    .border(1.dp, PocketBorder, RoundedCornerShape(8.dp))
                                    .clickable {
                                        try {
                                            val intent = android.content.Intent(
                                                android.content.Intent.ACTION_VIEW,
                                                android.net.Uri.parse("https://t.me/PocketIDECommunity")
                                            )
                                            context.startActivity(intent)
                                        } catch (_: Exception) {}
                                    }
                                    .padding(vertical = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Community Discussion",
                                    color = PocketTextPrimary,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }
        }

        // Bottom clearance for floating nav bar
        item {
            Spacer(Modifier.height(72.dp))
        }
    }

    // Create New Project Dialog
    if (showCreateDialog) {
        AlertDialog(
            onDismissRequest = { showCreateDialog = false },
            title = { Text("New Project", color = PocketTextPrimary, fontWeight = FontWeight.Bold) },
            text = {
                OutlinedTextField(
                    value = newProjectName,
                    onValueChange = { newProjectName = it },
                    label = { Text("Project Name") },
                    placeholder = { Text("my-new-app") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = PocketTextPrimary,
                        unfocusedTextColor = PocketTextPrimary,
                        focusedBorderColor = PocketPrimaryBlue,
                        unfocusedBorderColor = PocketBorder,
                        focusedLabelColor = PocketPrimaryBlue,
                        unfocusedLabelColor = PocketTextSecondary
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                PocketPrimaryButton(
                    text = "Create",
                    onClick = {
                        val name = newProjectName.trim()
                        if (name.isNotBlank()) {
                            onCreateProject(name)
                            showCreateDialog = false
                        }
                    },
                    height = 38.dp
                )
            },
            dismissButton = {
                TextButton(onClick = { showCreateDialog = false }) {
                    Text("Cancel", color = PocketTextSecondary)
                }
            },
            containerColor = PocketSurface,
            shape = RoundedCornerShape(12.dp)
        )
    }
}

@Composable
private fun HomeActionCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    accentColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        color = PocketSurface,
        border = androidx.compose.foundation.BorderStroke(1.dp, PocketBorder)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(accentColor.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(18.dp)
                )
            }
            Column {
                Text(
                    text = title,
                    color = PocketTextPrimary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = subtitle,
                    color = PocketTextSecondary,
                    fontSize = 11.sp
                )
            }
        }
    }
}
