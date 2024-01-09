package com.cindyle.traveltaipei.news

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cindyle.traveltaipei.NewsWebViewActivity
import com.cindyle.traveltaipei.R
import com.cindyle.traveltaipei.bean.NewsBean

class NewsFragment : Fragment() {
    private lateinit var viewOfLayout: View
    private var adapter: NewsAdapter = NewsAdapter()
    private lateinit var mVModel: NewsViewModel
    private lateinit var pgBar: ProgressBar
    private lateinit var rv: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewOfLayout = inflater.inflate(R.layout.fragment_news, container, false)
        mVModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        findViews(viewOfLayout)
        return viewOfLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        showProgress()

        val lang = activity?.getSharedPreferences("currentLang", Context.MODE_PRIVATE)!!.getString("LANG", "zh-tw")
        mVModel.getData(lang)
        mVModel.data.observe(this as LifecycleOwner) { response ->
            val noData = viewOfLayout.findViewById<TextView>(R.id.no_data)
            if (response.isEmpty()){
                noData.visibility = View.VISIBLE
            } else {
                noData.visibility = View.GONE
                adapter.setList(response)
            }
            hideProgress()
        }
    }

    private fun initAdapter() {
        rv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rv.adapter = adapter
        adapter.setListener(object: NewsAdapter.NewsListener{
            override fun onClick(pos: Int, data: NewsBean.NewsData?) {
                val intent = Intent(activity, NewsWebViewActivity::class.java)
                intent.putExtra("news_url", data!!.url)
                startActivity(intent)
                if (Build.VERSION.SDK_INT >= 34) {
                    activity!!.overrideActivityTransition(AppCompatActivity.OVERRIDE_TRANSITION_OPEN, R.anim.in_anim, R.anim.in_anim)
                } else{
                    activity!!.overridePendingTransition(R.anim.in_anim, R.anim.in_anim)
                }
            }
        })
    }

    fun findViews(v : View){
        pgBar = v.findViewById<ProgressBar>(R.id.pgBar)
        rv = v.findViewById<RecyclerView>(R.id.rv)
    }

    fun showProgress() {
        pgBar.visibility = View.VISIBLE
    }

    fun hideProgress() {
        pgBar.visibility = View.GONE
    }
}