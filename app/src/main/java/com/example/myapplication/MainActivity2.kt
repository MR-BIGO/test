package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log.d
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.spotify.protocol.types.Uri
import com.spotify.sdk.android.auth.AccountsQueryParameters.CLIENT_ID
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse

private val REQUEST_CODE = 1337
private val REDIRECT_URI = "yourcustomprotocol://callback"

class MainActivity2 : AppCompatActivity() {
    private lateinit var button: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        button = findViewById(R.id.btnLoginClickable)

        button.setOnClickListener {
            val builder: AuthorizationRequest.Builder =
                AuthorizationRequest.Builder(
                    CLIENT_ID,
                    AuthorizationResponse.Type.TOKEN,
                    REDIRECT_URI
                )

            builder.setScopes(arrayOf("streaming"))
            val request = builder.build()

            // AuthorizationClient.openLoginActivity(this, REQUEST_CODE, request)
            AuthorizationClient.openLoginInBrowser(this, request)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE) {
            val response = AuthorizationClient.getResponse(resultCode, intent)
            when (response.type) {
                AuthorizationResponse.Type.TOKEN -> {
                    d("Check the result", response.accessToken)
                }

                AuthorizationResponse.Type.ERROR -> {
                    d("Check the result", response.error)
                }

                else -> {
                    d("Check the result", "else")
                }
            }
        }
    }

    //    override fun onNewIntent(intent: Intent?) {
//        super.onNewIntent(intent)
//        val uri: android.net.Uri? = intent?.getData();
//        if (uri != null) {
//            val response: AuthorizationResponse = AuthorizationResponse.fromUri(uri);
//
//            when (response.type) {
//                // Response was successful and contains auth token
//                response.Type.TOKEN -> {}
//                // Handle successful response
//
//
//                // Auth flow returned an error
//                case ERROR:
//                // Handle error response
//                break;
//
//                // Most likely auth flow was cancelled
//                default:
//                // Handle other cases
//            }
//    }
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        val uri: android.net.Uri? = intent.data
        if (uri != null) {
            val response: AuthorizationResponse = AuthorizationResponse.fromUri(uri)

            when (response.type) {
                AuthorizationResponse.Type.TOKEN -> {
                    d("Check the result", AuthorizationResponse.Type.TOKEN.toString())
                }

                AuthorizationResponse.Type.ERROR -> {
                    d("Check the result", AuthorizationResponse.Type.ERROR.toString())
                }

                else -> {
                    d("Check the result", "else")
                }
            }
        }
    }


}