package com.example.newquiz

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.children
import com.example.newquiz.app.D
import com.example.newquiz.app.invisble
import com.example.newquiz.databinding.ActivityMainBinding
import com.example.newquiz.model.FlagData
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var flagList: MutableList<FlagData>
    private var index = 0
    private lateinit var flagName_main: String
    private var answer = ""
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
            dialog.setCancelable(true)
            dialog.setMessage("Tog`ri Javob = ${right}\n" +
                    "Xato Javob = ${wrong}")
            dialog.setPositiveButton("Ok") { dialogInterface: DialogInterface, i: Int ->
                this.index = 0
                this.answer = ""
                this.wrong = 0
                this.right = 0
                loadQuiz(this.index)
            }
            dialog.show()
            return
        }

        binding.apply {
            txtQuiz.text = "${index + 1}-Savol"
            layout1.removeAllViews()
            layout2.removeAllViews()
            layout3.removeAllViews()
        }
        flagName_main = flagList[index].name
        binding.image.setImageResource(flagList[index].image)
        loadBtn(flagName_main)
    }

    private fun loadBtn(flagName: String) {

        for (i in 1..flagName.length) {
            val btn = Button(this)
            btn.id = i
            btn.layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT, 1F
            )
            binding.layout1.addView(btn)
        }

        var randomName = randomFlagName(flagName)

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
        }

    }

    private fun btnAnsswer(text: String, view: View) {
        binding.layout1.children.forEach {
            var btn = it as Button
            if (btn.text.isEmpty()) {
                answer += text
                btn.text = text
                view.invisble()
                if (answer.length == flagName_main.length) {
                    if (answer == flagName_main) {
                        right++
                        binding.txtRight.text = "Tog`ri Javob\n${right}"
                    } else {
                        wrong++
                        binding.txtWrong.text = "Xato Javob\n${wrong}"
                    }
                    answer = ""
                    loadQuiz(++index)
                }
                return
            }
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
        flagList.add(FlagData("USA", R.drawable.usa))
        flagList.add(FlagData("UZBEKISTAN", R.drawable.uzb))
        flagList.add(FlagData("KAZAKHSTAN", R.drawable.qozoq))
        flagList.add(FlagData("BRAZILIYA", R.drawable.braz))
        flagList.add(FlagData("CANADA", R.drawable.canada))
        flagList.add(FlagData("AFGHANISTAN", R.drawable.afganistan))
        flagList.add(FlagData("INDIA", R.drawable.india))
        flagList.add(FlagData("RUSSIA", R.drawable.rus))
        flagList.add(FlagData("FRANSIYA", R.drawable.fansiya))
    }
}