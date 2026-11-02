package com.bnkk.feature.create

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.VideoLibrary
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CreateScreen(
    selectedMediaName: String?,
    onPickMedia: () -> Unit,
    onPublish: (String) -> Unit
) {
    var caption by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("أنشئ منشوراً", style = MaterialTheme.typography.headlineSmall)
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(22.dp)
        ) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = caption,
                    onValueChange = { caption = it },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 5,
                    placeholder = { Text("ماذا تريد أن تشارك اليوم؟") },
                    shape = RoundedCornerShape(16.dp)
                )
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    TextButton(onClick = onPickMedia) {
                        Icon(Icons.Outlined.Image, contentDescription = null)
                        Text(" صورة أو فيديو")
                    }
                    TextButton(onClick = onPickMedia) {
                        Icon(Icons.Outlined.VideoLibrary, contentDescription = null)
                        Text(" المعرض")
                    }
                }
                if (!selectedMediaName.isNullOrBlank()) {
                    Row(
                        modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(12.dp)).padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Outlined.Image, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Text("  تم اختيار: $selectedMediaName", style = MaterialTheme.typography.bodySmall)
                    }
                }
                Button(
                    onClick = { onPublish(caption) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text("إضافة إلى المعاينة")
                }
                Text(
                    "المعاينة محلية حالياً. لا يتم رفع الصور أو نشر المحتوى على الإنترنت قبل ربط خادم BNKK.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
