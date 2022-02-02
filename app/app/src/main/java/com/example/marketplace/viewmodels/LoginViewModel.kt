import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marketplace.MyApplication
import com.example.marketplace.model.LoginRequest
import com.example.marketplace.model.User
import com.example.marketplace.repository.Repository

class LoginViewModel(val context: Context, val repository: Repository) : ViewModel() {
    var token: MutableLiveData<String> = MutableLiveData()
    var user = MutableLiveData<User>()

    init {
        user.value = User()
    }

    suspend fun login() {
        val request =
            LoginRequest(username = user.value!!.username, password = user.value!!.password)
        try {
            val result = repository.login(request)
            MyApplication.token = result.token
            token.value = result.token
            MyApplication.username = user.value!!.username
            Log.d("xxx", "MyApplication - token:  ${MyApplication.token}")
        } catch (e: Exception) {
            Log.d("xxx", "LoginViewModel - exception: ${e.toString()}")
        }
    }


}
