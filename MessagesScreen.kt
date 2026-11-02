package com.bnkk.feature.messages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bnkk.model.ChatPreview

private val chats = listOf(
    ChatPreview("c1", "مريم خالد", "م", "وصلتني الصور، شكراً لك!", "10:42", 2, true),
    ChatPreview("c2", "أحمد علي", "أ", "هل نبدأ المشروع اليوم؟", "09:18", 0, true),
    ChatPreview("c3", "فريق BNKK", "B", "هناك تحديث جديد للتطبيق", "أمس", 4),
    ChatPreview("c4", "سارة", "س", "رائع جداً ✨", "الثلاثاء", 0)
)

@Composable
fun MessagesScreen() {
    var selectedChat by remember { mutableStateOf<ChatPreview?>(null) }
    var search by remember { mutableStateOf("") }
    if (selectedChat == null) {
        Column(Modifier.fillMaxSize().padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text("رسائلك", style = MaterialTheme.typography.headlineSmall)
            OutlinedTextField(
                value = search,
                onValueChange = { search = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                placeholder = { Text("ابحث في المحادثات") },
                leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = null) },
                shape = RoundedCornerShape(16.dp)
            )
            LazyColumn(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                items(chats.filter { it.name.contains(search, ignoreCase = true) || it.message.contains(search, ignoreCase = true) }) { chat ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 9.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Avatar(chat.initials, chat.online)
                        Column(Modifier.weight(1f).padding(start = 12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(chat.name, style = MaterialTheme.typography.titleSmall)
                            Text(chat.message, color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodySmall)
                        }
                        Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.spacedBy(5.dp)) {
                            Text(chat.time, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            if (chat.unreadCount > 0) {
                                Text("${chat.unreadCount}", modifier = Modifier.background(MaterialTheme.colorScheme.primary, CircleShape).padding(horizontal = 7.dp, vertical = 3.dp), color = MaterialTheme.colorScheme.onPrimary, style = MaterialTheme.typography.labelSmall)
                            }
                        }
                        TextButton(onClick = { selectedChat = chat }) { Text("فتح") }
                    }
                }
            }
            Text("المحادثات المعروضة تجريبية على الجهاز. المراسلة الفورية تحتاج إلى خادم واتصال حقيقي.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    } else {
        ChatThread(chat = selectedChat!!, onBack = { selectedChat = null })
    }
}

@Composable
private fun Avatar(initials: String, online: Boolean) {
    androidx.compose.foundation.layout.Box(
        modifier = Modifier.size(48.dp).background(MaterialTheme.colorScheme.surfaceVariant, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(initials, style = MaterialTheme.typography.titleMedium)
    }
}

@Composable
private fun ChatThread(chat: ChatPreview, onBack: () -> Unit) {
    val messages = remember(chat.id) {
        mutableStateListOf("أهلاً! كيف حالك؟", chat.message)
    }
    var draft by remember { mutableStateOf("") }
    Column(Modifier.fillMaxSize()) {
        Row(Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "رجوع") }
            Text(chat.name, style = MaterialTheme.typography.titleMedium)
        }
        LazyColumn(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            reverseLayout = true,
            contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(messages.reversed()) { message ->
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Text(message, modifier = Modifier.background(MaterialTheme.colorScheme.primary.copy(alpha = .18f), RoundedCornerShape(16.dp)).padding(12.dp))
                }
            }
        }
        Row(Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = draft,
                onValueChange = { draft = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("اكتب رسالة") },
                shape = RoundedCornerShape(18.dp),
                singleLine = true
            )
            IconButton(onClick = {
                if (draft.isNotBlank()) {
                    messages.add(0, draft.trim())
                    draft = ""
                }
            }) { Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "إرسال", tint = MaterialTheme.colorScheme.primary) }
        }
    }
}
