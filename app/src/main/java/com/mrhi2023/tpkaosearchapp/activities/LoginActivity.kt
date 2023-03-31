package com.mrhi2023.tpkaosearchapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.mrhi2023.tpkaosearchapp.G
import com.mrhi2023.tpkaosearchapp.R
import com.mrhi2023.tpkaosearchapp.databinding.ActivityLoginBinding
import com.mrhi2023.tpkaosearchapp.model.UserAccount

class LoginActivity : AppCompatActivity() {

    val binding:ActivityLoginBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //둘러보기 버튼 클릭으로 로그인없이 Main 화면으로 이동
        binding.tvGo.setOnClickListener {
            startActivity( Intent(this, MainActivity::class.java) )
            finish()
        }

        //회원가입 버튼 클릭
        binding.tvSignup.setOnClickListener {
            //회원가입화면으로 전환
            startActivity(Intent(this, SignupActivity::class.java))
        }

        //이메일 로그인 버튼 클릭
        binding.layoutEmail.setOnClickListener {
            //이메일 로그인 화면으로 전환
            startActivity(Intent(this, EmailSigninActivity::class.java))
        }

        //간편로그인 버튼들 클릭
        binding.ivLoginKakao.setOnClickListener { clickedLoginKakao() }
        binding.ivLoginGoogle.setOnClickListener { clickedLoginGoogle() }
        binding.ivLoginNaver.setOnClickListener { clickedLoginNaver() }

        //카카오 키해시 값 얻어오기
        val keyHash:String= Utility.getKeyHash(this)
        Log.i("keyHash", keyHash)

    }//onCreate method..

    private fun clickedLoginKakao(){

        // 카카오 로그인 공통 callback 함수
        val callback:(OAuthToken?, Throwable?)->Unit= { token, error ->
            if(token!=null){
                Toast.makeText(this, "카카오 로그인 성공", Toast.LENGTH_SHORT).show()

                //사용자 정보 요청 [ 1.회원번호, 2.이메일주소 ]
                UserApiClient.instance.me { user, error ->
                    if(user!=null){
                        var id:String= user.id.toString()
                        var email:String= user.kakaoAccount?.email ?: ""  //혹시 null이면 이메일의 기본값은 ""

                        Toast.makeText(this, "$email", Toast.LENGTH_SHORT).show()
                        G.userAccount= UserAccount(id, email)

                        // main 화면으로 이동
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                }

            }else{
                Toast.makeText(this, "카카오 로그인 실패", Toast.LENGTH_SHORT).show()
            }
        }

        //카카오톡이 설치되어 있으면 카톡으로 로그인, 아니면 카카오계정 로그인
        if(UserApiClient.instance.isKakaoTalkLoginAvailable(this)){
            UserApiClient.instance.loginWithKakaoTalk(this,callback = callback)
        }else{
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
        }

    }

    private fun clickedLoginGoogle(){

        // Google에서 검색 [ 안드로이드 구글 로그인 ]

        // 구글 로그인 옵션객체 생성 - Builder 이용
        val signInOptions: GoogleSignInOptions= GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()


        // 구글 로그인 화면(액티비티)을 실행하는 Intent를 통해 로그인 구현
        val intent:Intent= GoogleSignIn.getClient(this, signInOptions).signInIntent
        resultLauncher.launch(intent)
    }

    //구글 로그인 화면(액티비티)의 실행결과를 받아오는 계약체결 대행사
    val resultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), object : ActivityResultCallback<ActivityResult>{
        override fun onActivityResult(result: ActivityResult?) {
            //로그인 결과를 가져온 인텐트(택배기사) 객체 소환
            val intent:Intent?= result?.data

            //돌아온 Intent로부터 구글 계정 정보를 가져오는 작업 수행
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(intent)

            val accout:GoogleSignInAccount= task.result
            var id:String= accout.id.toString()
            var email:String= accout.email ?: ""

            Toast.makeText(this@LoginActivity, "$email", Toast.LENGTH_SHORT).show()
            G.userAccount= UserAccount(id, email)

            //main 화면으로 이동
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }
    })

    private fun clickedLoginNaver(){

    }

}