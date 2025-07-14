package com.esrabildik.feature.homescreen.component


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.esrabildik.feature.ui.theme.backgroundCard


@Composable
fun CategoryCard(
    category: String,
    isSelected: Boolean = false,
    onClick: (String) -> Unit
) {
    val displayName = category.replace("-", " ").replaceFirstChar { it.uppercase() }

    val backgroundColor = if (isSelected) backgroundCard else Color.White
    val textColor = if (isSelected) Color.White else Color.Black

    Card(
        modifier = Modifier
            .padding(5.dp)
            .size(180.dp, 50.dp),
        onClick = {
            onClick(category.lowercase())
        },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(2.dp))
    {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = displayName,
                color = textColor,
                modifier = Modifier.padding(4.dp),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }

    }
}