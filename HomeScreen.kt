package com.bnkk.feature.home

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.ModeComment
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bnkk.model.StoryPreview
import com.bnkk.model.TimelinePost

private val sampleStories = listOf(
    StoryPreview("s1", "قصي", "ق"), StoryPreview("s2", "مريم", "م"),
    StoryPreview("s3", "آدم", "أ"), StoryPreview("s4", "سارة", "س"),
    StoryPreview("s5", "نور", "ن"), StoryPreview("s6", "ياسين", "ي")
)

private val samplePosts = listOf(
    TimelinePost("p1", "BNKK Team", "@bnkk", "منذ 12 دقيقة", "مساحتك الجديدة للتواصل والإبداع تبدأ من هنا. شارك أفكارك واكتشف مجتمعات جديدة.", 1280, 86, true, "مجتمع BNKK"),
    TimelinePost("p2", "ليلى أحمد", "@layla", "منذ ساعة", "كل يوم فرصة جديدة لصناعة شيء يستحق أن تتذكره ✨", 248, 19, true, "لحظات اليوم"),
    TimelinePost("p3", "عمر", "@omar", "منذ 3 ساعات", "ما أكثر ميزة تتمنون رؤيتها في BNKK؟ نعمل على تطوير التجربة خطوة بخطوة.", 97, 34)
)

@Composable
fun HomeScreen(userPosts: List<String>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 22.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("لك", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                Text("أتابع", color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(Modifier.weight(1f))
                AssistChip(onClick = {}, label = { Text("الأحدث") })
            }
        }
        item { StoriesStrip() }
        if (userPosts.isNotEmpty()) {
            items(userPosts, key = { it.hashCode() }) { content ->
                PostCard(
                    post = TimelinePost("local-${content.hashCode()}", "أنت", "@you", "الآن · معاينة محلية", content, 0, 0),
                    initialLikes = 0
                )
            }
        }
        items(samplePosts, key = { it.id }) { post -> PostCard(post = post) }
        item {
            Text(
                "هذه منشورات تجريبية للعرض. ستظهر منشورات المستخدمين الحقيقية بعد ربط واجهة الخادم.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp)
            )
        }
    }
}

@Composable
private fun StoriesStrip() {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(14.dp), contentPadding = PaddingValues(vertical = 4.dp)) {
        item { StoryBubble("أنت", "+", false) }
        items(sampleStories) { story -> StoryBubble(story.name, story.initials, story.isNew) }
    }
}

@Composable
private fun StoryBubble(name: String, initials: String, highlight: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(5.dp)) {
        Box(
            modifier = Modifier
                .size(62.dp)
                .background(
                    if (highlight) Brush.linearGradient(listOf(Color(0xFF9B8CFF), Color(0xFF50D5E8)))
                    else Brush.linearGradient(listOf(Color(0xFF31394D), Color(0xFF1B2230))),
                    CircleShape
                )
                .padding(3.dp)
                .background(MaterialTheme.colorScheme.surface, CircleShape)
                .padding(3.dp)
                .background(Color(0xFF252D40), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(initials, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        }
        Text(name, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun PostCard(post: TimelinePost, initialLikes: Int = post.likes) {
    var liked by remember(post.id) { mutableStateOf(false) }
    val likeCount = initialLikes + if (liked) 1 else 0
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.size(42.dp).background(
                        Brush.linearGradient(listOf(Color(0xFF9B8CFF), Color(0xFF50D5E8))), CircleShape
                    ), contentAlignment = Alignment.Center
                ) {
                    Text(post.author.take(1), color = Color(0xFF0B0D13), fontWeight = FontWeight.Black)
                }
                Column(Modifier.weight(1f).padding(start = 10.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(post.author, fontWeight = FontWeight.Bold)
                        if (post.verified) Text("✓", color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Black)
                    }
                    Text("${post.handle} · ${post.timeLabel}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                IconButton(onClick = {}) { Icon(Icons.Outlined.MoreHoriz, contentDescription = "المزيد") }
            }
            Text(post.body, style = MaterialTheme.typography.bodyLarge, lineHeight = MaterialTheme.typography.bodyLarge.lineHeight)
            post.mediaLabel?.let {
                Box(
                    modifier = Modifier.fillMaxWidth().height(164.dp).background(
                        Brush.linearGradient(listOf(Color(0xFF242A49), Color(0xFF174351), Color(0xFF34264F))),
                        RoundedCornerShape(18.dp)
                    ), contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("✦", style = MaterialTheme.typography.headlineLarge, color = Color.White)
                        Text(it, color = Color.White, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { liked = !liked }) {
                        Icon(
                            if (liked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = "إعجاب",
                            tint = if (liked) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Text(likeCount.toString(), style = MaterialTheme.typography.labelMedium)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.ModeComment, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(" ${post.replies}", style = MaterialTheme.typography.labelMedium)
                }
                Spacer(Modifier.weight(1f))
                IconButton(onClick = {}) { Icon(Icons.Outlined.Share, contentDescription = "مشاركة") }
            }
        }
    }
}
