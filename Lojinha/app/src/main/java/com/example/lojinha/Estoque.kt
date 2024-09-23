package com.example.lojinha

class Estoque {

    companion object {
        var listaEstoque = listOf<Produto>()

        fun adicionarProdutos(nome: String, categoria: String, preco: Float, quant: Int) {
            listaEstoque += listOf(Produto(nome, categoria, preco, quant))
        }

        fun calcularValorTotalEstoque(): Float {
            var precoTotal = 0.0f

            listaEstoque.forEach { produto ->
                precoTotal += produto.preco * produto.quantEstoque
            }

            return precoTotal
        }

        fun quantidadeTotalProdutos(): Int{
            var quant = 0

            listaEstoque.forEach { quant += it.quantEstoque}
            return quant
        }
    }

}