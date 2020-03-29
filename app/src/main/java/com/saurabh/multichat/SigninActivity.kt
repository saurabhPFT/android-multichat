package com.saurabh.multichat

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.saurabh.multichat.util.FirestoreUtil
import kotlinx.android.synthetic.main.activity_signin.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.design.longSnackbar
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask

class SigninActivity : AppCompatActivity() {

    private val RC_SIGN_IN=1
    private val signInProviders= listOf(AuthUI.IdpConfig.EmailBuilder()
        .setAllowNewAccounts(true)
        .setRequireName(true).build())



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        account_sign_in.setOnClickListener{
            val intent =AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(signInProviders)
                .setLogo(R.drawable.ic_fire_emoji)
                .build()
            startActivityForResult(intent,RC_SIGN_IN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode==RC_SIGN_IN){
            val response=IdpResponse.fromResultIntent(data)
            if(resultCode== Activity.RESULT_OK){
                FirestoreUtil.initCurrentUserIfFirstTime {
                    startActivity(intentFor<MainActivity>().newTask().clearTask())
                }

            }
            else if(resultCode==Activity.RESULT_CANCELED){
                if(response==null){
                    when(response?.error?.errorCode){
                        ErrorCodes.NO_NETWORK->
                            constraint_layout.longSnackbar("No network")
                    ErrorCodes.UNKNOWN_ERROR->
                    constraint_layout.longSnackbar("Unknown Error")
                    }
                    }
                }
            }
        }
    }

