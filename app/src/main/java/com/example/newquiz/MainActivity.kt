package com.example.newquiz

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.children
import com.example.newquiz.app.D
import com.example.newquiz.app.invisble
import com.example.newquiz.app.visble
import com.example.newquiz.databinding.ActivityMainBinding
import com.example.newquiz.model.FlagData
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var flagList: MutableList<FlagData>
    private lateinit var btnList: MutableList<Button>
    private lateinit var flagName_main: String
    private var index = 0
    private var right = 0
    private var wrong = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        flagListAddData()
        loadQuiz(index)
    }

    private fun loadQuiz(i: Int) {
        if (i + 1 > flagList.size) {
            var dialog = AlertDialog.Builder(this)
            dialog.setCancelable(false)
            dialog.setMessage("Tog`ri Javob = ${right}\n" +
                    "Xato Javob = ${wrong}")
            dialog.setPositiveButton("Ok") { dialogInterface: DialogInterface, i: Int ->
                this.index = 0
                this.wrong = 0
                this.right = 0
                loadQuiz(this.index)
                binding.txtRight.text = "Tog`ri Javob\n${right}"
                binding.txtWrong.text = "Xato Javob\n${wrong}"
            }
            dialog.show()
            return
        }
        binding.txtQuiz.text = "${index + 1}-Savol"
        flagName_main = flagList[index].name
        binding.image.setImageResource(flagList[index].image)
        loadBtn()
    }

    private fun loadBtn() {
        btnList = mutableListOf()
        for (i in 1..flagName_main.length) {
            val btn = Button(this)
            btn.id = i
            btn.layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT, 1F
            )
            btn.setOnClickListener {
                if (btn.text.isNotEmpty())
                btnList[btn.hint.toString().toInt()].visble()
                btn.text = ""
                btn.hint = ""
            }
            binding.layout1.addView(btn)
        }

         var randomName = randomFlagName(flagName_main)

        for (i in 0..8) {
            val btn = Button(this)
            btn.id = i
            btn.layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT, 1F
            )
            btn.setText(randomName[i].toString())
            btn.setOnClickListener {
                btnAnsswer(btn.text.toString(), it)
            }
            binding.layout2.addView(btn)
            btnList.add(btn)
        }

        for (i in 9..17) {
            val btn = Button(this)
            btn.id = i
            btn.layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT, 1F
            )
            btn.setText(randomName[i].toString())
            btn.setOnClickListener {
                btnAnsswer(btn.text.toString(), it)
            }
            binding.layout3.addView(btn)
            btnList.add(btn)
        }

    }

    private fun btnAnsswer(text: String, view: View) {
        binding.layout1.children.forEach {
            var btn = it as Button
            if (btn.text.isEmpty()) {
                btn.text = text
                btn.setHint(view.id.toString())
                view.invisble()
                checkAnswer()
                return
            }
        }
    }

    private fun checkAnswer() {
        var answer = ""
        binding.layout1.children.forEach {
            var btn = it as Button
            if (btn.text.isNotEmpty()) {
                answer += btn.text
            }
        }
        if (answer.length == flagName_main.length) {
            if (answer == flagName_main) {
                right++
                binding.txtRight.text = "Tog`ri Javob\n${right}"
            } else {
                wrong++
                binding.txtWrong.text = "Xato Javob\n${wrong}"
            }
            object :CountDownTimer(500,500){
                override fun onTick(p0: Long) {

                }

                override fun onFinish() {
                    binding.apply {
                        layout1.removeAllViews()
                        layout2.removeAllViews()
                        layout3.removeAllViews()
                    }
                    loadQuiz(++index)
                }
            }.start()


        }

    }

    private fun randomFlagName(flagName: String): String {
        var s = "ABCDIEFGHJKLMNOPRSTYVWXYZ"
        var flag = flagName.plus(s.subSequence(0, 18 - flagName.length))
        var char = flag.toMutableList()
        char.shuffle()
        flag = ""
        char.forEach {
            flag += it
        }
        return flag

    }

    private fun flagListAddData() {
        flagList = mutableListOf()

        flagList.add(FlagData("UZBEKISTAN", R.drawable.uzb))
        flagList.add(FlagData("USA", R.drawable.usa))
        flagList.add(FlagData("KAZAKHSTAN", R.drawable.qozoq))
        flagList.add(FlagData("BRAZILIYA", R.drawable.braz))
        flagList.add(FlagData("CANADA", R.drawable.canada))
        flagList.add(FlagData("AFGHANISTAN", R.drawable.afganistan))
        flagList.add(FlagData("INDIA", R.drawable.india))
        flagList.add(FlagData("RUSSIA", R.drawable.rus))
        flagList.add(FlagData("FRANSIYA", R.drawable.fansiya))
        flagList.add(FlagData("CHINA", R.drawable.china))
        flagList.add(FlagData("KOREAN", R.drawable.korea))
        flagList.add(FlagData("JAPAN", R.drawable.japn))

    }
}