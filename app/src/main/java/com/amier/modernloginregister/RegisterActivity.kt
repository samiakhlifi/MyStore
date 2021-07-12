package com.amier.modernloginregister

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

 var databaseReference : DatabaseReference? =null
    var database : FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth= FirebaseAuth.getInstance()
        database= FirebaseDatabase.getInstance()
        databaseReference= database?.reference!!.child("profile")

        register()
        btnLogRegister.setOnClickListener {
            onBackPressed()
        }
    }

    private fun register(){
        registerButton.setOnClickListener(){
     if(TextUtils.isEmpty(name.text.toString())){
    name.setError("please enter name")
         return@setOnClickListener
          }
            else if(TextUtils.isEmpty(email.text.toString())){
               email.setError("please enter email")
         return@setOnClickListener
            }else if(TextUtils.isEmpty(password.text.toString())){
    password.setError("please enter password")
         return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword( email.text.toString(),
            password.text.toString()).addOnCompleteListener {
                if(it.isSuccessful){


                    val currentUser=auth.currentUser
                    val currentUserDb=databaseReference?.child((currentUser?.uid!!))
                    currentUserDb?.child("name")?.setValue(name.text.toString())
                    currentUserDb?.child("email")?.setValue(email.text.toString())
                    currentUserDb?.child("password")?.setValue(password.text.toString())

                    Toast.makeText(this@RegisterActivity,"regitration sucess",Toast.LENGTH_LONG).show()
finish()
                }else
                    Toast.makeText(this@RegisterActivity,"regitration failed",Toast.LENGTH_LONG).show()


            }

        }


    }
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right)
    }

}
