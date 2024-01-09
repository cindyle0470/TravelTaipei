package com.cindyle.traveltaipei.attractions

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cindyle.traveltaipei.AttDetailActivity
import com.cindyle.traveltaipei.R
import com.cindyle.traveltaipei.bean.AttBean
import com.google.gson.Gson

class AttFragment : Fragment() {
    private lateinit var viewOfLayout: View
    private var adapter: AttAdapter = AttAdapter()
    private lateinit var mVModel: AttViewModel
    private lateinit var pgBar: ProgressBar
    private lateinit var rv: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewOfLayout = inflater.inflate(R.layout.fragment_att, container, false)
        mVModel = ViewModelProvider(this).get(AttViewModel::class.java)
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
            adapter.setList(response, requireActivity())
            hideProgress()
        }
    }

    private fun initAdapter() {
        rv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rv.adapter = adapter

        adapter.setListener(object: AttAdapter.AttListener{
            override fun onClick(pos: Int, data: AttBean.AttData?) {
                val intent = Intent(activity, AttDetailActivity::class.java)
                val dataJson = Gson().toJson(data)
                intent.putExtra("attData", dataJson)
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