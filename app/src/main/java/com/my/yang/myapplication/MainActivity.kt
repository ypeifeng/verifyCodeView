package com.my.yang.myapplication

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import com.my.yang.verifycodeview.VerifyCodeView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        verifyCodeView.setInputCompleteListener(object : VerifyCodeView.InputCompleteListener {
            override fun inputComplete() {
                Toast.makeText(this@MainActivity, "inputComplete: " + verifyCodeView.editContent, Toast.LENGTH_SHORT).show()
            }

            override fun invalidContent() {
            }

        })
    }
}
