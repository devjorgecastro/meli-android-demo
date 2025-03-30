package com.example.meli.core.ui.container

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun BoxContainer(
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    content: @Composable BoxScope.() -> Unit
) {
    Scaffold { innerPadding ->
        Box(
            contentAlignment = alignment,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .semantics { isTraversalGroup = true }
                .then(modifier),
            content = content
        )
    }
}

@Preview
@Composable
fun BoxContainerPreview() {
    BoxContainer{
        Text(text = "Hello World!")
    }
}