package com.example.lojinha

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LayoutMain()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Cadastro(navController: NavController, listaProdutos: MutableList<Produto>) {
    var nome by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var preco by remember { mutableStateOf("") }
    var quantEstoque by remember { mutableStateOf("") }

    var context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(value = nome, onValueChange = { nome = it }, label = { Text(text = "Nome") })
        Spacer(modifier = Modifier.height(20.dp))

        TextField(value = categoria, onValueChange = { categoria = it }, label = { Text(text = "Categoria") })
        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = preco,
            onValueChange = {
                preco = it
            },
            label = { Text(text = "Preço") }
        )
        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = quantEstoque,
            onValueChange = { quantEstoque = it },
            label = { Text(text = "Quantidade em Estoque") }
        )
        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            if(nome.isNotBlank() || categoria.isNotBlank() || preco.isNotBlank() || quantEstoque.isNotBlank()){
                // Adiciona o produto à lista
                listaProdutos.add(Produto(nome, categoria, preco.toFloatOrNull() ?: 0.0f, quantEstoque.toIntOrNull() ?: 0))
                nome = ""
                categoria = ""
                preco = ""
                quantEstoque = ""
            } else {
                Toast.makeText(
                    context,
                    "Preencha todos os campos",
                    Toast.LENGTH_SHORT
                ).show()
            }
            Toast.makeText(
                context,
                "produto cadastrado com sucesso",
                Toast.LENGTH_SHORT
            ).show()

        }) {
            Text(text = "Cadastrar")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            navController.navigate("produtoActivity")
        }) {
            Text(text = "Ver Produtos")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LayoutMain() {
    val navController = rememberNavController()
    val listaProdutos = remember { mutableStateListOf<Produto>() }

    NavHost(
        navController = navController,
        startDestination = "cadastro"
    ) {
        composable("cadastro") {
            Cadastro(navController = navController, listaProdutos = listaProdutos)
        }
        composable("produtoActivity") {
            ProdutoActivity(navController = navController, produtos = listaProdutos)
        }
    }
}

@Composable
fun ProdutoActivity(navController: NavController, produtos: List<Produto>) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        if (produtos.isEmpty()) {
            Text(
                text = "Nenhuma produto cadastrado",
                modifier = Modifier.padding(16.dp)
            )
        } else {
            LazyColumn(Modifier.fillMaxWidth()) {
                items(produtos) { produto ->
                    ListaProduto(produto = produto)
                }
            }
        }
    }
}

        @Composable
        fun ListaProduto(produto: Produto) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "${produto.nome}, (${produto.quantEstoque})", fontSize = 20.sp)
            }
        }

@Preview(showBackground = true)
@Composable
fun LayoutPreview() {
    LayoutMain()
}
