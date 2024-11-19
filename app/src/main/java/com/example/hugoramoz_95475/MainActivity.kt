package com.example.hugoramoz_95475

import DBHelper
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hugoramoz_95475.lista.DicasAdapter
import com.example.hugoramoz_95475.models.Dica

class MainActivity : AppCompatActivity() {

    private lateinit var dicas: MutableList<Dica>
    private lateinit var dicasAdapter: DicasAdapter
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DBHelper(this)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        dicas = dbHelper.getAllDicas()

        dicasAdapter = DicasAdapter(dicas) { dica ->
            onDicaClicked(dica)
        }

        recyclerView.adapter = dicasAdapter

        val edtTitulo: EditText = findViewById(R.id.edtTitulo)
        val edtDescricao: EditText = findViewById(R.id.edtDescricao)
        val btnCadastrar: Button = findViewById(R.id.btnCadastrar)

        btnCadastrar.setOnClickListener {
            val titulo = edtTitulo.text.toString().trim()
            val descricao = edtDescricao.text.toString().trim()

            if (titulo.isNotEmpty() && descricao.isNotEmpty()) {
                val id = dbHelper.insertDica(titulo, descricao)

                if (id != -1L) {
                    dicas.add(Dica(titulo, descricao))
                    dicasAdapter.notifyItemInserted(dicas.size - 1)
                    edtTitulo.text.clear()
                    edtDescricao.text.clear()
                } else {
                    Toast.makeText(this, "Erro ao cadastrar a dica.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun onDicaClicked(dica: Dica) {
        if (dica.descricao.contains("http", ignoreCase = true)) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(dica.descricao))
            startActivity(intent)
        } else {
            Toast.makeText(this, "Detalhes: ${dica.descricao}", Toast.LENGTH_SHORT).show()
        }
    }
}
