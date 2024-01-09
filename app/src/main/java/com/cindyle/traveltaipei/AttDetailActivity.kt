package com.cindyle.traveltaipei

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.cindyle.traveltaipei.bean.AttBean
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.youth.banner.Banner
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.indicator.RectangleIndicator
import com.youth.banner.listener.OnBannerListener

class AttDetailActivity : AppCompatActivity() {
    private lateinit var pgBar: ProgressBar
    private var attData: AttBean.AttData? = null
    private lateinit var url: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_att_detail)

        pgBar = findViewById(R.id.pgBar)
        url = findViewById(R.id.url)

        getData()
        initToolbar()
        initBanner()
        initInfo()

        url.setOnClickListener(View.OnClickListener { view: View? ->
            val it = Intent(this, AttWebViewActivity::class.java)
            it.putExtra("att_url", attData!!.url)
            it.putExtra("title", attData!!.name)
            startActivity(it)
            if (Build.VERSION.SDK_INT >= 34) {
                overrideActivityTransition(AppCompatActivity.OVERRIDE_TRANSITION_OPEN, R.anim.in_anim, R.anim.in_anim)
            } else{
                overridePendingTransition(R.anim.in_anim, R.anim.in_anim)
            }
        })

    }

    private fun initInfo() {
        val time = findViewById<TextView>(R.id.time)
        val address = findViewById<TextView>(R.id.address)
        val info = findViewById<TextView>(R.id.info)
        val tel = findViewById<TextView>(R.id.tel)
        time.text = attData!!.open_time
        address.text = attData!!.address
        info.text = attData!!.introduction
        tel.text = attData!!.tel
        url.text = Html.fromHtml("<u>" + attData!!.url + "</u>")
    }

    private fun initBanner() {
        val  banner:Banner<AttBean.AttData.Img,BannerImageAdapter<AttBean.AttData.Img>> = findViewById(R.id.banner)
        val imgs: List<AttBean.AttData.Img> = attData!!.images
        if (attData!!.images.isEmpty()) {
            banner.visibility = View.GONE
        } else {
            banner.visibility = View.VISIBLE
            banner.setAdapter(object : BannerImageAdapter<AttBean.AttData.Img>(imgs) {
                override fun onBindView(holder: BannerImageHolder, data:AttBean.AttData.Img, position: Int, size: Int) {
                    Glide.with(holder.itemView)
                        .load(data.src)
                        .apply(RequestOptions.bitmapTransform(RoundedCorners(30)))
                        .into(holder.imageView) }
            }).addBannerLifecycleObserver(this).setIndicator(CircleIndicator(this))

            banner
                .setIndicator(RectangleIndicator(this))
                .addBannerLifecycleObserver(this)
                .setOnBannerListener(OnBannerListener { data:AttBean.AttData.Img?, position: Int ->
                    Toast.makeText(this, position.toString(), Toast.LENGTH_SHORT)
                })
        }
    }

    private fun getData() {
        val json = intent.getStringExtra("attData")
        attData = Gson().fromJson(json, object : TypeToken<AttBean.AttData?>() {}.type)
    }

    private fun initToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val toolbarTitle = findViewById<TextView>(R.id.toolbar_title)
        val title = attData!!.name
        toolbarTitle.text = title
        toolbar.setNavigationOnClickListener { view: View? ->
            finish()
            if (Build.VERSION.SDK_INT >= 34) {
                overrideActivityTransition(AppCompatActivity.OVERRIDE_TRANSITION_OPEN,R.anim.fin_anim, R.anim.fout_anim)
            } else{
                overridePendingTransition(R.anim.fin_anim, R.anim.fout_anim)
            }
        }
    }

}