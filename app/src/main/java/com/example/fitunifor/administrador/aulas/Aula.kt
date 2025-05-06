import android.os.Parcelable
import com.example.fitunifor.R
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Aula(
    val id: String = UUID.randomUUID().toString(),
    val nome: String,
    val professor: String,
    val diaSemana: String,
    val horario: String,
    val maxAlunos: Int,
    val imagem: Int = R.drawable.image_aula_coletiva, // Adicione um drawable padrão
    var alunosMatriculados: Int = 0 // Tornando a variável mutável
) : Parcelable {
    // Adicionando a propriedade calculada para facilitar
    val temVagas: Boolean get() = alunosMatriculados < maxAlunos
}
