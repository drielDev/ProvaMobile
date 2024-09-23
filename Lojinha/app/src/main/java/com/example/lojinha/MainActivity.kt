package com.example.lojinha

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.lojinha.ui.theme.LojinhaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LayoutMain() {

    var nome by remember { mutableStateOf("")}
    var categoria by remember { mutableStateOf("")}
    var preco by remember { mutableStateOf("")}
    var quantEstoque by remember { mutableStateOf("")}
    var listaProdutos by remember { mutableStateOf(listOf<Produto>()) }

    var precoFloat by remember { mutableStateOf(0.0f) }
    var quantProdutoFloat by remember { mutableStateOf(0) }


    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        TextField(value = nome, onValueChange = {nome = it}, label = { Text(text = "Nome")})

        TextField(value = categoria, onValueChange = {categoria = it}, label = { Text(text = "categoria")})

        TextField(
            value = preco,
            onValueChange = {
                preco = it
                precoFloat = it.toFloatOrNull() ?: 0.0f
            },
            label = { Text(text = "Preço") }
        )

        TextField(
            value = quantEstoque,
            onValueChange = {
                quantEstoque = it
                quantProdutoFloat = it.toIntOrNull() ?: 0 },
            label = { Text(text = "Quantidade em Estoque") }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LayoutPreview() {
    LayoutMain()
}