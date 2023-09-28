package com.snowtouch.hiddenharbor.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.snowtouch.hiddenharbor.R

@Composable
fun AdPreviewCard(
    ) {
    Card(
        onClick = {},
        modifier = Modifier
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Box(modifier = Modifier) {
                Image(
                    painterResource(R.drawable.sample_ad_image),
                    contentDescription = null,
                    contentScale = ContentScale.Fit)
            }
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Advertisement description ",
                    modifier = Modifier.padding(12.dp),
                    fontSize = 20.sp,
                    overflow = TextOverflow.Clip,
                    softWrap = true,
                    maxLines = 2
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "800$",
                    modifier = Modifier.padding(8.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                Column(
                    modifier = Modifier,
                    horizontalAlignment = Alignment.End
                    ) {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Outlined.FavoriteBorder,
                            contentDescription = null
                        )
                    }
                    Text(
                        text = "12-08-2023",
                    )
                }
            }

        }
    }
}
@Preview
@Composable
fun CardPreview(){
    AdPreviewCard()
}