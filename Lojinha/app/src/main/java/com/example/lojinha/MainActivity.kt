package com.example.lojinha

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson



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
            label = { Text(text = "Preço") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )
        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = quantEstoque,
            onValueChange = { quantEstoque = it },
            label = { Text(text = "Quantidade em Estoque") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            // Verificação de parametros
            if (nome.isBlank() || categoria.isBlank() || preco.isBlank() || quantEstoque.isBlank()) {
                Toast.makeText(context, "Preencher todos os campos!", Toast.LENGTH_SHORT).show()
            } else if (quantEstoque.toInt() < 1) {
                Toast.makeText(context, "Quantidade não pode ser menor que 1!", Toast.LENGTH_SHORT)
                    .show()
            } else if (preco.toFloat() < 0) {
                Toast.makeText(context, "Preço não pode ser menor que 0!", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Estoque.adicionarProdutos(nome, categoria, preco.toFloat(), quantEstoque.toInt())

                Toast.makeText(context, "Produto Cadastrado com sucesso!", Toast.LENGTH_SHORT)
                    .show()

                nome = ""
                preco = ""
                quantEstoque = ""
                categoria = ""
            }
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

        composable("informacaoEstoque") { InformacaoEstoque(navController) }

        composable("detalhes/{produtoJson}") { backStackEntry ->
            val produtoJson = backStackEntry.arguments?.getString("produtoJson")
            val produto = Gson().fromJson(produtoJson, Produto::class.java)

            Detalhes(navController, produto)
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

        LazyColumn() {
            items(Estoque.listaEstoque) { produto ->
                Text(
                    text = "${produto.nome} - (${produto.quantEstoque} unidades)",
                    fontSize = 15.sp
                )
                Button(onClick = {
                    val produtoJson = Gson().toJson(produto)
                    navController.navigate("detalhes/$produtoJson")
                }) {
                    Text(text = "Detalhes")
                }
            }
        }

        Button(onClick = {
            // Volta para a tela anterior
            navController.popBackStack()
        }) {
            Text(text = "Voltar")
        }
    }
}

@Composable
fun ListaProduto(produto: Produto, navController: NavController) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Produto ${produto.nome} (${produto.quantEstoque})", fontSize = 20.sp)

        Button(onClick = {
            // Navega para a tela de detalhes com os parâmetros
            navController.navigate(
                "detalhes/${produto.nome}/${produto.categoria}/${produto.preco}/${produto.quantEstoque}"
            )
        }) {
            Text(text = "Detalhes")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            navController.navigate("informacaoEstoque")
        }) {
            Text(text = "Estatísticas")
        }
    }
}


@Composable
fun Detalhes(navController: NavController, produto: Produto) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Nome: ${produto.nome}", fontSize = 24.sp)
        Text(text = "Categoria: ${produto.categoria}", fontSize = 24.sp)
        Text(text = "Preço: R$ ${produto.preco}", fontSize = 24.sp)
        Text(text = "Quantidade em Estoque: ${produto.quantEstoque}", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            navController.popBackStack()
        }) {
            Text(text = "Voltar")
        }
    }
}

@Composable
fun InformacaoEstoque(navController: NavController) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "${Estoque.calcularValorTotalEstoque()} é o valor total do estoques", fontSize = 20.sp)

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Atualmente possuimos ${Estoque.quantidadeTotalProdutos()} produtos", fontSize = 20.sp)

        Button(onClick = { navController.popBackStack() }) {
            Text(text = "Voltar")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LayoutPreview() {
    LayoutMain()
}
