package com.birdwastemanagement.app.ui.screens.receipt

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
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
import com.birdwastemanagement.app.ui.theme.DeepForestGreen
import com.birdwastemanagement.app.ui.theme.MintBackground
import com.birdwastemanagement.app.ui.theme.PaidGreen
import com.birdwastemanagement.app.ui.theme.PaidGreenLight
import com.birdwastemanagement.app.ui.theme.SoftMint

@Composable
fun PaymentReceiptScreen(
    receiptNo: String,
    amount: String,
    paymentMethod: String,
    onDoneClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        containerColor = MintBackground,
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(24.dp))

                // Large Green Checkmark Icon in Circle
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(PaidGreenLight)
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Success Check",
                        tint = PaidGreen,
                        modifier = Modifier.size(48.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Payment Successful!",
                    style = MaterialTheme.typography.headlineMedium,
                    color = DeepForestGreen,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Thank you for contributing to a cleaner city.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Receipt Content Card
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Receipt No:", color = Color.Gray)
                            Text(text = receiptNo, fontWeight = FontWeight.Bold)
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Date & Time:", color = Color.Gray)
                            Text(text = "24 Oct 2025, 10:30 AM", fontWeight = FontWeight.Medium)
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Payment Method:", color = Color.Gray)
                            Text(text = paymentMethod, fontWeight = FontWeight.Medium)
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        Divider()
                        Spacer(modifier = Modifier.height(16.dp))

                        // Highlighted Container Showing "Paid Amount: ৳ 200"
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(SoftMint)
                                .padding(16.dp)
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "Paid Amount",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = DeepForestGreen
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "৳ $amount",
                                    style = MaterialTheme.typography.headlineLarge,
                                    color = DeepForestGreen,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            // Action Buttons
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Share Receipt Button
                    Button(
                        onClick = { },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = DeepForestGreen),
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                    ) {
                        Icon(Icons.Default.Share, contentDescription = "Share Icon")
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "Share", fontWeight = FontWeight.Bold)
                    }

                    // Download PDF Button
                    OutlinedButton(
                        onClick = { },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                    ) {
                        Icon(Icons.Default.Download, contentDescription = "Download Icon", tint = DeepForestGreen)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "PDF", color = DeepForestGreen, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = onDoneClick,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray.copy(alpha = 0.4f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text(text = "Back to Home", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
