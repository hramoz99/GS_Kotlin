import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import com.example.hugoramoz_95475.models.Dica

class DBHelper(context: Context) : SQLiteOpenHelper(context, "dicasDB", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableSQL = """
            CREATE TABLE dicas (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                titulo TEXT NOT NULL,
                descricao TEXT NOT NULL
            );
        """
        db?.execSQL(createTableSQL)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableSQL = "DROP TABLE IF EXISTS dicas"
        db?.execSQL(dropTableSQL)
        onCreate(db)
    }

    fun insertDica(titulo: String, descricao: String): Long {
        val db = writableDatabase
        val values = ContentValues()
        values.put("titulo", titulo)
        values.put("descricao", descricao)
        return db.insert("dicas", null, values)
    }

    fun getAllDicas(): MutableList<Dica> {
        val dicasList = mutableListOf<Dica>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM dicas", null)

        if (cursor.moveToFirst()) {
            do {
                val titulo = cursor.getString(cursor.getColumnIndex("titulo"))
                val descricao = cursor.getString(cursor.getColumnIndex("descricao"))
                dicasList.add(Dica(titulo, descricao))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return dicasList
    }
}
