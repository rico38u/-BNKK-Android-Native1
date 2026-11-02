package com.bnkk.feature.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProfileScreen() {
    var following by remember { mutableStateOf(false) }
    Column(
        Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(Modifier.size(92.dp).background(MaterialTheme.colorScheme.primary.copy(alpha = .2f), CircleShape), contentAlignment = Alignment.Center) {
            Text("B", style = MaterialTheme.typography.headlineLarge, color = MaterialTheme.colorScheme.primary)
        }
        Text("مستخدم BNKK", style = MaterialTheme.typography.headlineSmall)
        Text("@bnkk_user", color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text("أشارك الأفكار واللحظات والموسيقى مع مجتمع BNKK.", style = MaterialTheme.typography.bodyMedium)
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            ProfileStat("المنشورات", "24")
            ProfileStat("المتابعون", "1.8K")
            ProfileStat("أتابع", "315")
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Button(onClick = { following = !following }, modifier = Modifier.weight(1f), shape = RoundedCornerShape(14.dp)) {
                Text(if (following) "تتابع" else "متابعة")
            }
            OutlinedButton(onClick = {}, modifier = Modifier.weight(1f), shape = RoundedCornerShape(14.dp)) {
                Text("تعديل الملف")
            }
        }
        Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface), shape = RoundedCornerShape(20.dp)) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("ملفك الشخصي", style = MaterialTheme.typography.titleMedium)
                Text("هذه بيانات توضيحية. تسجيل الحسابات والملفات الشخصية العامة ستعمل بعد تفعيل المصادقة وقاعدة البيانات.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        Spacer(Modifier.height(4.dp))
    }
}

@Composable
private fun ProfileStat(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(value, style = MaterialTheme.typography.titleLarge)
        Text(label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}
