package com.example.meli.core.ui.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.meli.core.ui.R

@Composable
fun SuggestedQueryItem(
    title: String,
    onClick: () -> Unit = {},
    modifier: Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.clickable { onClick() }
    ) {

        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(.1f)
                .fillMaxHeight()
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxHeight()
        ) {
            Text(
                text = title,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth(.8f)
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .fillMaxWidth(1f)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.sharp_arrow_insert_24),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxHeight()
                    .clickable { onClick() }
            )
        }
    }
}


@Preview(
    showBackground = true,
    widthDp = 300,
    heightDp = 50
)
@Composable
fun SuggestedQueryItemPreview() {
    SuggestedQueryItem(
        title = "iPhone 13 Usado",
        modifier = Modifier.fillMaxWidth()
    )
}