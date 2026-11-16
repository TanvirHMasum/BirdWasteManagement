package com.birdwastemanagement.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.birdwastemanagement.app.utils.localizeDigits
import com.birdwastemanagement.app.domain.model.BillStatus
import com.birdwastemanagement.app.domain.model.MonthlyData
import com.birdwastemanagement.app.ui.theme.DeepForestGreen
import com.birdwastemanagement.app.ui.theme.DueRed
import com.birdwastemanagement.app.ui.theme.DueRedLight
import com.birdwastemanagement.app.ui.theme.PaidGreen
import com.birdwastemanagement.app.ui.theme.PaidGreenLight
import com.birdwastemanagement.app.ui.theme.VibrantLightGreen

@Composable
fun StatusBadge(status: BillStatus, modifier: Modifier = Modifier) {
    val isPaid = status == BillStatus.PAID
    val backgroundColor = if (isPaid) PaidGreenLight else DueRedLight
    val textColor = if (isPaid) PaidGreen else DueRed
    val label = if (isPaid) "Paid" else "Due"

    Surface(
        color = backgroundColor,
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
    ) {
        Text(
            text = label,
            color = textColor,
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun MockBarChart(
    data: List<MonthlyData>,
    modifier: Modifier = Modifier
) {
    val maxVal = data.maxOfOrNull { it.amount } ?: 15f

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Monthly Collection Trend (Lakhs ৳)".localizeDigits(),
                style = MaterialTheme.typography.titleMedium,
                color = DeepForestGreen,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                data.forEach { item ->
                    val barHeightRatio = (item.amount / maxVal).coerceIn(0.1f, 1.0f)
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "৳${item.amount}".localizeDigits(),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Box(
                            modifier = Modifier
                                .width(20.dp)
                                .fillMaxHeight(barHeightRatio)
                                .clip(RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
                                .background(
                                    if (item.month == "Jun") VibrantLightGreen else DeepForestGreen
                                )
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = item.month,
                            style = MaterialTheme.typography.bodyMedium,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LogoIcon(modifier: Modifier = Modifier, sizeDp: Int = 80) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(sizeDp.dp)
            .clip(CircleShape)
            .background(DeepForestGreen)
    ) {
        Text(
            text = "🐦‍⬛",
            fontSize = (sizeDp * 0.45).sp,
            textAlign = TextAlign.Center
        )
    }
}
