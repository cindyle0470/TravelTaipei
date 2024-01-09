package com.cindyle.traveltaipei

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.os.LocaleList
import android.util.DisplayMetrics
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.cindyle.traveltaipei.attractions.AttFragment
import com.cindyle.traveltaipei.news.NewsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var currentLang: String
    private lateinit var config: Configuration
    private lateinit var dm: DisplayMetrics
    private lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLocalLang()
        setContentView(R.layout.activity_main)

        setLangOnClick()
        selectFragment()
    }

    fun setLocalLang() {
        dm = resources.displayMetrics
        config = Resources.getSystem().configuration
        val lang = getSharedPreferences("currentLang", MODE_PRIVATE).getString("LANG", "zh-tw")
        when (lang) {
            "zh-tw" -> config.setLocales(LocaleList(Locale.TRADITIONAL_CHINESE))
            "zh-cn" -> config.setLocales(LocaleList(Locale.SIMPLIFIED_CHINESE))
            "en" -> config.setLocales(LocaleList(Locale.ENGLISH))
            "ja" -> config.setLocales(LocaleList(Locale.JAPAN))
            "ko" -> config.setLocales(LocaleList(Locale.KOREA))
            "es" -> config.setLocales(LocaleList(Locale("es")))
            "id" -> config.setLocales(LocaleList(Locale("id")))
            "th" -> config.setLocales(LocaleList(Locale("th")))
            "vi" -> config.setLocales(LocaleList(Locale("vi")))
        }
        resources.updateConfiguration(config, dm)
    }

    fun setLangOnClick(){
        val langIcon = findViewById<ImageView>(R.id.languages_icon)
        langIcon.setOnClickListener {
            val popupMenu = PopupMenu(this@MainActivity, it)
            // 获取布局文件
            popupMenu.menuInflater.inflate(R.menu.language_menu, popupMenu.menu)
            popupMenu.show()

            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.zh_tw -> {
                        currentLang = "zh-tw"
                        config.setLocales(LocaleList(Locale.TRADITIONAL_CHINESE))
                    }
                    R.id.zh_cn -> {
                        currentLang = "zh-cn"
                        config.setLocales(LocaleList(Locale.SIMPLIFIED_CHINESE))
                    }
                    R.id.en -> {
                        currentLang = "en"
                        config.setLocales(LocaleList(Locale.ENGLISH))
                    }
                    R.id.ja -> {
                        currentLang = "ja"
                        config.setLocales(LocaleList(Locale.JAPAN))
                    }
                    R.id.ko -> {
                        currentLang = "ko"
                        config.setLocales(LocaleList(Locale.KOREA))
                    }
                    R.id.es -> {
                        currentLang = "es"
                        config.setLocales(LocaleList(Locale("es")))
                    }
                    R.id.th -> {
                        currentLang = "th"
                        config.setLocales(LocaleList(Locale("th")))
                    }
                    R.id.vi -> {
                        currentLang = "vi"
                        config.setLocales(LocaleList(Locale("vi")))
                    }
                }
                pref = getSharedPreferences("currentLang", MODE_PRIVATE)
                pref.edit()
                    .putString("LANG", currentLang)
                    .apply()
                resources.updateConfiguration(config, dm)
                refreshLang()
                true
            }
        }
    }

    fun refreshLang(){
        //设置好选择的语言以后，需要清除任务栈中的所有activity，打开首页，确保以后进入的页面都是当前选择的语言
        val intent = Intent(this@MainActivity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    fun selectFragment(){
        val btnNav : BottomNavigationView = findViewById(R.id.btn_nav)
        val newsFragment = NewsFragment()
        val attFragment = AttFragment()

        pref = getSharedPreferences("currentTab", MODE_PRIVATE)

        val fragmentManager : FragmentManager = supportFragmentManager

        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.add(R.id.frameLayout, newsFragment)
        transaction.commit()
        btnNav.setOnItemSelectedListener { item ->
            val trans = fragmentManager.beginTransaction()
            when (item.itemId) {
                R.id.nav1 -> {
                    trans.replace(R.id.frameLayout, newsFragment)
                    trans.commit()
                }

                R.id.nav2 -> {
                    trans.replace(R.id.frameLayout, attFragment)
                    trans.commit()
                }
            }
            true
        }
    }
}