package pl.km.kalkulator

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.buttonSimple).setOnClickListener {
            startActivity(Intent(this, BasicCalculator::class.java))
        }
        findViewById<Button>(R.id.buttonAdvance).setOnClickListener {
            startActivity(Intent(this, AdvanceCalculator::class.java))
        }
        findViewById<Button>(R.id.buttonAbout).setOnClickListener {
            startActivity(Intent(this, AboutPage::class.java))
        }
        findViewById<Button>(R.id.buttonExit).setOnClickListener {
            finishAffinity()
        }
    }
}