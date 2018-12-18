package com.my.yang.verifycodeview

import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.blankj.utilcode.util.ScreenUtils
import kotlinx.android.synthetic.main.view_verify_code.view.*



class VerifyCodeView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RelativeLayout(context, attrs, defStyleAttr) {
    private var textViews: Array<TextView?>
    var editContent: String? = null

    private var count = 4 //验证码输入的个数

    private var inputCompleteListener: InputCompleteListener? = null

    init {

        var screenDensity = ScreenUtils.getScreenDensity()

        View.inflate(context, R.layout.view_verify_code, this)
        edtVerifyCodeView.isCursorVisible = false

        var ta = context.obtainStyledAttributes(attrs, R.styleable.VerifyCodeView)

        count = ta.getInteger(R.styleable.VerifyCodeView_count, 4)
        edtVerifyCodeView.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(count))

        var textSize = ta.getInteger(R.styleable.VerifyCodeView_textSize, 20)
        var itemHeight = ta.getInteger(R.styleable.VerifyCodeView_itemHeight, 48)
        var itemWidth = ta.getInteger(R.styleable.VerifyCodeView_itemWidth,41)
        var lineColor = ta.getColor(R.styleable.VerifyCodeView_lineColor,Color.parseColor("#000000"))
        var textColor = ta.getColor(R.styleable.VerifyCodeView_textColor,Color.parseColor("#000000"))

        textViews = arrayOfNulls<TextView?>(count)


        for (i in 0 until count){

            var layoutParams = LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT)
            layoutParams.weight = 1f

            var rlItemRoot = RelativeLayout(context)
            rlItemRoot.layoutParams = layoutParams

            var tvLayoutParams = RelativeLayout.LayoutParams((itemWidth*screenDensity).toInt(),(itemHeight*screenDensity).toInt())
            tvLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT)
            var textView = TextView(context)
            textView.layoutParams = tvLayoutParams
            textView.setTextColor(textColor)
            textView.textSize =textSize.toFloat()
            textView.gravity = Gravity.CENTER

            var lineLayoutParams = RelativeLayout.LayoutParams((itemWidth*screenDensity).toInt(),(1*screenDensity).toInt())
            var line = View(context)
            lineLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
            lineLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL)
            line.layoutParams = lineLayoutParams
            line.setBackgroundColor(lineColor)

            rlItemRoot.addView(textView)
            textViews[i] = textView
            rlItemRoot.addView(line)

            llVerifyCodeViewRoot.addView(rlItemRoot)
        }

        setEditTextListener()
    }

    private fun setEditTextListener() {
        edtVerifyCodeView.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun afterTextChanged(editable: Editable) {
                editContent = edtVerifyCodeView.text.toString()

                if (inputCompleteListener != null) {
                    if (editContent!!.length >= count) {
                        inputCompleteListener!!.inputComplete()
                    } else {
                        inputCompleteListener!!.invalidContent()
                    }
                }

                for (i in 0 until count) {
                    if (i < editContent!!.length) {
                        textViews[i]?.text = editContent!![i].toString()
                    } else {
                        textViews[i]?.text = ""
                    }
                }
            }
        })
    }

    fun setInputCompleteListener(inputCompleteListener: InputCompleteListener) {
        this.inputCompleteListener = inputCompleteListener
    }

    interface InputCompleteListener {

        fun inputComplete()

        fun invalidContent()
    }


}