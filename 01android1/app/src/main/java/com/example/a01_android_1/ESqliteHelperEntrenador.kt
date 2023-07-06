package com.example.a01_android_1

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class ESqliteHelperEntrenador(
    contexto: Context?, //this
): SQLiteOpenHelper(
    contexto,
    "moviles", //nombre BDD
    null,
    1
) {

    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaEntrenador =
            """
                CREATE TABLE ENTRENADOR(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombre VARCHAR(50),
                    descripcion VARCHAR(50)
                )
            """.trimIndent()
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun crearEntrenador(
        nombre: String,
        descripcion: String
    ): Boolean{
        val baseDatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("descripcion", descripcion)
        val resultadoGuardar = baseDatosEscritura.insert(
            "ENTRENADOR",//Nombre tabla
            null,
            valoresAGuardar //Valores
        )
        baseDatosEscritura.close()
        return if (resultadoGuardar.toInt() === -1) false else true
    }

    fun eliminarEntrenadorFormulario(id: Int): Boolean{
        val conexionEscritura = writableDatabase
        // where id = ?
        val parametrosConsultaDelete = arrayOf( id.toString() )
        val resultadoEliminacion = conexionEscritura
            .delete(
                "ENTRENADOR",//Nombre tabla
                "id=?",//Consulta where
                parametrosConsultaDelete
            )
        conexionEscritura.close()
        return if (resultadoEliminacion.toInt() === -1) false else true
    }

    fun actualizarEntrenadorFormulario(
        nombre: String,
        descripcion: String,
        id: Int
    ):Boolean{
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("descripcion", descripcion)
        //where Id = ?
        val parametrosConsultaActualizar = arrayOf( id.toString() )
        val resultadoActualizacion = conexionEscritura
            .update(
                "ENTRENADOR",//Nombre tabla
                valoresAActualizar,//Valores
                "id=?",//Consulta where
                parametrosConsultaActualizar
            )
        conexionEscritura.close()
        return if (resultadoActualizacion.toInt() === -1) false else true
    }

    fun consultaEntrenadorPorId(id: Int): BEntrenador{
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM ENTRENADOR WHERE ID = ?
        """.trimIndent()
        val parametrosConsultaLectura = arrayOf(id.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura, //Consulta
            parametrosConsultaLectura, //Parámetros
        )
        //Lógica de Búsqueda
        val existeUsuario = resultadoConsultaLectura.moveToFirst()
        val usuarioEncontrado = BEntrenador(0, "", "")
        val arreglo = arrayListOf<BEntrenador>()
        if(existeUsuario){
            do {
                val id = resultadoConsultaLectura.getString(0)//Índice 0
                val nombre = resultadoConsultaLectura.getString(1)
                val descripcion = resultadoConsultaLectura.getString(2)

            }while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return usuarioEncontrado
    }
}