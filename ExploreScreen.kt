package com.bnkk.feature.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.AssistChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.unit.dp

private data class ExploreTile(val title: String, val category: String, val colors: List<Color>)
private val tiles = listOf(
    ExploreTile("إبداع بلا حدود", "تصميم", listOf(Color(0xFF6C4DD9), Color(0xFF2D2459))),
    ExploreTile("يوميات وصور", "مجتمع", listOf(Color(0xFF167F91), Color(0xFF17394F))),
    ExploreTile("عالم الألعاب", "ألعاب", listOf(Color(0xFFD45A78), Color(0xFF5A264F))),
    ExploreTile("صوت وموسيقى", "موسيقى", listOf(Color(0xFFB17B35), Color(0xFF543B2B))),
    ExploreTile("تقنية المستقبل", "تقنية", listOf(Color(0xFF3E78C5), Color(0xFF26345B))),
    ExploreTile("لقطات قصيرة", "فيديو", listOf(Color(0xFF2C9A71), Color(0xFF164A43)))
)

@Composable
fun ExploreScreen() {
    var query by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("الكل") }
    val categories = listOf("الكل", "مجتمع", "فيديو", "موسيقى", "تقنية", "ألعاب")
    val filtered = tiles.filter {
        (selectedCategory == "الكل" || it.category == selectedCategory) &&
            (query.isBlank() || it.title.contains(query, ignoreCase = true) || it.category.contains(query, ignoreCase = true))
    }
    Column(Modifier.fillMaxSize().padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("اكتشف ما يهمك", style = MaterialTheme.typography.headlineSmall)
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            placeholder = { Text("ابحث عن أشخاص ومواضيع ومجتمعات") },
            leadingIcon = { androidx.compose.material3.Icon(Icons.Outlined.Search, contentDescription = null) },
            shape = RoundedCornerShape(18.dp)
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            categories.forEach { category ->
                AssistChip(onClick = { selectedCategory = category }, label = {
                    Text(if (selectedCategory == category) "• $category" else category)
                })
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(filtered) { tile ->
                Box(
                    modifier = Modifier.fillMaxWidth().height(148.dp).background(
                        Brush.linearGradient(tile.colors), RoundedCornerShape(20.dp)
                    ).padding(14.dp),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(tile.category, color = Color.White.copy(alpha = .76f), style = MaterialTheme.typography.labelMedium)
                        Text(tile.title, color = Color.White, style = MaterialTheme.typography.titleMedium)
                    }
                }
            }
        }
    }
}
