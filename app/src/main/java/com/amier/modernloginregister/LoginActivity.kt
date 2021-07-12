package com.amier.modernloginregister

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth= FirebaseAuth.getInstance()
        login()



        btnRegLogin.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left)
        }




    }

    private fun login(){
        loginButton.setOnClickListener {

            if(TextUtils.isEmpty(email.text.toString())){
                email.setError("please enter email")
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(password.text.toString()))
            {
                password.setError("please enter password")
                return@setOnClickListener
            }
            auth.signInWithEmailAndPassword(email.text.toString(),password.text.toString())
                .addOnCompleteListener {
                    if(it.isSuccessful){
                  startActivity(Intent(this@LoginActivity,ProfileActivity::class.java))
                   finish()
                    }else {
                        Toast.makeText(this@LoginActivity,"login failed", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

}
