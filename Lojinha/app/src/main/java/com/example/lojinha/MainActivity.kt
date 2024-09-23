package com.example.lojinha

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

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

    var navController = rememberNavController()

    var nome by remember { mutableStateOf("")}
    var categoria by remember { mutableStateOf("")}
    var preco by remember { mutableStateOf("")}
    var quantEstoque by remember { mutableStateOf("")}
    var listaProdutos by remember { mutableStateOf(listOf<Produto>()) }

    var precoFloat by remember { mutableStateOf(0.0f) }
    var quantEstoqueFloat by remember { mutableStateOf(0) }


    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        TextField(value = nome, onValueChange = {nome = it}, label = { Text(text = "Nome")})

        Spacer(modifier = Modifier.height(20.dp))

        TextField(value = categoria, onValueChange = {categoria = it}, label = { Text(text = "categoria")})

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = preco,
            onValueChange = {
                preco = it
                precoFloat = it.toFloatOrNull() ?: 0.0f
            },
            label = { Text(text = "Preço") }
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = quantEstoque,
            onValueChange = {
                quantEstoque = it
                quantEstoqueFloat = it.toIntOrNull() ?: 0 },
            label = { Text(text = "Quantidade em Estoque") }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            listaProdutos += Produto(nome, categoria, precoFloat, quantEstoqueFloat)
            nome = ""
            categoria = ""
            precoFloat = 0.0f
            quantEstoqueFloat = 0
        }) {
            Text(text = "Cadastrar")
        }
    }
}

@Composable
fun ProdutoActivity(){

}

@Preview(showBackground = true)
@Composable
fun LayoutPreview() {
    LayoutMain()
}