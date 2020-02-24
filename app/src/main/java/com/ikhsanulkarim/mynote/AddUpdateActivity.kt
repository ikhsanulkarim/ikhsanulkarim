import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ikhsanulkarim.mynote.NotDatabase
import com.ikhsanulkarim.mynote.NoteModel
import com.ikhsanulkarim.mynote.R
import com.ikhsanulkarim.mynote.RSA
import kotlinx.android.synthetic.main.activity_add_update.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*

class AddUpdateActivity : AppCompatActivity() {
    var noteDatabase: NotDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_update)
        noteDatabase = NotDatabase.getinstance(this)
        generateDate()

        aua_btn_save.onClick {
            insertNewNote()
        }
    }
    private fun generateDate(){
        val locale = Locale("in", "ID")
        val dateFormat: SimpleDateFormat? =
            SimpleDateFormat("EEE , dd MMM yyyy , hh:mm aaa", locale)
        val date = Calendar.getInstance().time
        val createDate = dateFormat?.format(date).toString()
        aua_tv_date.text = createDate
    }
    private fun insertNewNote() {
        val cipher = doEncrypt()
        val note = NoteModel(
            title = aua_edt_title.text.toString(),
            massage = cipher,
            createdAt = aua_tv_date.text.toString()
        )
        GlobalScope.launch {
            noteDatabase?.noteDao()?.insertNote(note)
            runOnUiThread {
                toast("Data berhasil ditambahkan")
            }
        }
    }

    private fun runOnUiThread(any: Any) {

    }

    private fun doEncrypt() : String{
        val RSA = RSA()
        val keyEncrypt = RSA.eValue(RSA.Qn)
        val plainteks = aua_edt_note.text.toString()
        var cipherTeks = ""
        for (i in 0 until plainteks.length) {
            val character = plainteks[i]
            val cipher = RSA.encrypt(character, keyEncrypt, RSA.n)
            cipherTeks += cipher
        }
        Log.d("Encryption", "$cipherTeks")
        return cipherTeks
    }
}