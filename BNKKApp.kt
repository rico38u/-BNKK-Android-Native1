package com.bnkk.app

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bnkk.designsystem.BNKKTheme
import com.bnkk.feature.create.CreateScreen
import com.bnkk.feature.explore.ExploreScreen
import com.bnkk.feature.home.HomeScreen
import com.bnkk.feature.messages.MessagesScreen
import com.bnkk.feature.profile.ProfileScreen
import kotlinx.coroutines.launch

private enum class AppTab(val title: String) {
    HOME("الرئيسية"), EXPLORE("اكتشف"), CREATE("نشر"), MESSAGES("الرسائل"), PROFILE("حسابي")
}

@Composable
fun BNKKApp() {
    var currentTab by rememberSaveable { mutableStateOf(AppTab.HOME) }
    var selectedMediaName by rememberSaveable { mutableStateOf<String?>(null) }
    val localPosts = remember { mutableStateListOf<String>() }
    val snackbarState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val mediaPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        selectedMediaName = uri?.let { getDisplayName(context, it) ?: it.lastPathSegment ?: "ملف وسائط" }
    }

    BNKKTheme {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            topBar = {
                BNKKTopBar(
                    title = currentTab.title,
                    onSearch = { currentTab = AppTab.EXPLORE },
                    onNotifications = {
                        scope.launch { snackbarState.showSnackbar("الإشعارات ستُربط بالخادم في مرحلة التكامل") }
                    }
                )
            },
            bottomBar = { BNKKNavigationBar(currentTab) { currentTab = it } },
            snackbarHost = { SnackbarHost(hostState = snackbarState) }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                verticalArrangement = Arrangement.Top
            ) {
                when (currentTab) {
                    AppTab.HOME -> HomeScreen(userPosts = localPosts)
                    AppTab.EXPLORE -> ExploreScreen()
                    AppTab.CREATE -> CreateScreen(
                        selectedMediaName = selectedMediaName,
                        onPickMedia = {
                            mediaPicker.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo)
                            )
                        },
                        onPublish = { caption ->
                            val entry = buildString {
                                append(caption.trim())
                                if (!selectedMediaName.isNullOrBlank()) {
                                    if (isNotEmpty()) append("\n")
                                    append("مرفق: ").append(selectedMediaName)
                                }
                            }
                            if (entry.isNotBlank()) {
                                localPosts.add(0, entry)
                                selectedMediaName = null
                                currentTab = AppTab.HOME
                                scope.launch {
                                    snackbarState.showSnackbar("حُفظت المعاينة على هذا الجهاز فقط؛ النشر السحابي غير مفعّل بعد")
                                }
                            } else {
                                scope.launch { snackbarState.showSnackbar("اكتب شيئاً أو أرفق صورة أو فيديو أولاً") }
                            }
                        }
                    )
                    AppTab.MESSAGES -> MessagesScreen()
                    AppTab.PROFILE -> ProfileScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BNKKTopBar(
    title: String,
    onSearch: () -> Unit,
    onNotifications: () -> Unit
) {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("BNKK", fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.primary)
                Text(title, style = MaterialTheme.typography.titleMedium)
            }
        },
        actions = {
            IconButton(onClick = onSearch) {
                Icon(Icons.Outlined.Search, contentDescription = "بحث")
            }
            IconButton(onClick = onNotifications) {
                Icon(Icons.Outlined.NotificationsNone, contentDescription = "الإشعارات")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onBackground
        )
    )
}

@Composable
private fun BNKKNavigationBar(current: AppTab, onSelect: (AppTab) -> Unit) {
    val items = listOf(
        Triple(AppTab.HOME, Icons.Filled.Home, "الرئيسية"),
        Triple(AppTab.EXPLORE, Icons.Filled.Explore, "اكتشف"),
        Triple(AppTab.CREATE, Icons.Filled.AddCircle, "نشر"),
        Triple(AppTab.MESSAGES, Icons.Outlined.MailOutline, "الرسائل"),
        Triple(AppTab.PROFILE, Icons.Outlined.PersonOutline, "حسابي")
    )
    NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
        items.forEach { (tab, icon, label) ->
            NavigationBarItem(
                selected = current == tab,
                onClick = { onSelect(tab) },
                icon = { Icon(icon, contentDescription = label) },
                label = { Text(label, style = MaterialTheme.typography.labelSmall) }
            )
        }
    }
}

private fun getDisplayName(context: Context, uri: Uri): String? {
    return runCatching {
        context.contentResolver.query(uri, arrayOf(OpenableColumns.DISPLAY_NAME), null, null, null)
            ?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    if (index >= 0) cursor.getString(index) else null
                } else null
            }
    }.getOrNull()
}
