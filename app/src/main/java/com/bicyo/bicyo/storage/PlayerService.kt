package alvarez.juan.cazarpatos.storage

import alvarez.juan.cazarpatos.data.Jugador
import android.app.Activity
import android.media.MediaPlayer
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase

object PlayerService {
    private val db = FirebaseFirestore.getInstance()

    fun getPlayers(players: SnapshotStateList<Jugador>) {
        db.collection("ranking").orderBy(
            "patosCazados",
            Query.Direction.DESCENDING
        ).get().addOnSuccessListener { result ->
            Log.d(EXTRA_LOGIN, "Success getting documents")
            players.updateList(result.toObjects(Jugador::class.java))
        }.addOnFailureListener { exception ->
            Log.w(EXTRA_LOGIN, "Error getting documents.", exception)
            Toast.makeText(
                null,
                "Error al obtener datos de jugadores",
                Toast.LENGTH_LONG
            ).show()

        }
    }

    fun savePlayerScore(
        player: Jugador,
        onSuccess: (message:String) -> Unit,
        onFail: (message:String) -> Unit
    ) {
        db.collection("ranking")
            .whereEqualTo("usuario", player.usuario)
            .get()
            .addOnSuccessListener { documents ->
                if (documents != null &&
                    documents.documents != null &&
                    documents.documents.count() > 0
                ) {
                    val idDocumento = documents.documents.get(0).id
                    actualizarPuntajeJugador(idDocumento, player, onSuccess, onFail)
                } else {
                    ingresarPuntajeJugador(player,onSuccess,onFail)
                }
            }
    }

    fun ingresarPuntajeJugador(
        jugador: Jugador,
        onSuccess: (message:String) -> Unit,
        onFail: (message:String) -> Unit
    ) {
        db.collection("ranking").add(jugador)
            .addOnSuccessListener { onSuccess("ingresado") }
            .addOnFailureListener { onFail("ingresado") }
    }

    fun actualizarPuntajeJugador(
        idDocumento: String,
        jugador: Jugador,
        onSuccess: (message:String) -> Unit,
        onFail: (message:String) -> Unit
    ) {
        db.collection("ranking")
            .document(idDocumento).set(jugador)
            .addOnSuccessListener { onSuccess("actualizado") }
            .addOnFailureListener { onFail("actualizado") }
    }

}

fun <T> SnapshotStateList<T>.updateList(newList: List<T>) {
    clear()
    addAll(newList)
}