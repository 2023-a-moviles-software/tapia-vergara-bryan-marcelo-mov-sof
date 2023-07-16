package com.example.a01_examen.database

import com.example.a01_examen.models.Client
import com.example.a01_examen.models.Payment
import java.time.LocalDate

class DataBaseMemory {
    companion object{
        val clients = arrayListOf<Client>()
        init {
            clients.add(
                Client(
                    0,
                    "1727628362",
                    "Bryan Tapia",
                    "0958881256",
                    "Quito",
                    false,
                    arrayListOf(
                        Payment(0, "Enero", LocalDate.parse("2021-01-01"), 50.0, true, false),
                        Payment(1, "Febrero", LocalDate.parse("2021-02-01"), 50.0, true, false)
                    )
                )
            )
            clients.add(
                Client(
                    1,
                    "0501591267",
                    "Marcelo Tapia",
                    "0999219312",
                    "Quito",
                    true,
                    arrayListOf(
                        Payment(0, "Enero", LocalDate.parse("2021-01-01"), 50.0, true, true),
                        Payment(1, "Febrero", LocalDate.parse("2021-02-01"), 50.0, false, false)
                    )
                )
            )
        }
    }
}