package com.example.gameoflife.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gameoflife.R
import com.example.gameoflife.classes.DbPatterns
import com.example.gameoflife.classes.PatternAdapter
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds


class PatternFragment : Fragment() {
    var paths: HashMap<String,Int> = HashMap()
    var db: DbPatterns? = null
    var card_current : CardView? = null
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        paths["glider"] = R.mipmap.ic_glider
        paths["fight"] = R.mipmap.ic_fight
        paths["wash"] = R.mipmap.ic_wash
        paths["weekender"] = R.mipmap.ic_weekender
        paths["me"] = R.drawable.ic_baseline_grid_on_24
        val view = inflater.inflate(R.layout.fragment_pattern, container, false)
        val btn_del = view.findViewById<Button>(R.id.btn_del_current)

        db = DbPatterns(activity)

        val rv = view.findViewById<RecyclerView>(R.id.rv_patterns)
        val tvcur = view.findViewById<TextView>(R.id.tv_current_title)
        val iv = view.findViewById<ImageView>(R.id.pattern_iv_curent)
        view.setOnClickListener(View.OnClickListener {})
        val arr = db!!.selectAll()
        val titl = db!!.getqq()
        for (a in arr){
            if (a.title.equals(titl)){
                paths.get(a.path)?.let { iv.setImageResource(it) }
                tvcur.setText(titl)
                continue
            }
        }


        btn_del.setOnClickListener(View.OnClickListener {
            db!!.deleteqq()
            tvcur.text = "Empty Field"
            iv.setImageResource(R.drawable.ic_baseline_grid_on_24)
        })

        val adapter = PatternAdapter(arr, view, activity)
        card_current = view.findViewById(R.id.pattern_card_patterns)

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)

        rv.layoutManager = layoutManager
        rv.adapter = adapter

        return view
    }

}