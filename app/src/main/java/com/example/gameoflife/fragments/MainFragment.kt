package com.example.gameoflife.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.gameoflife.R
import com.example.gameoflife.activities.CommunityActivity
import com.example.gameoflife.activities.GameActivity
import com.example.gameoflife.activities.UserActivity
import com.example.gameoflife.classes.Firebase
import com.example.gameoflife.classes.Pattern
import com.example.gameoflife.classes.User
import com.example.gameoflife.interfaces.FireCallback
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.*


class MainFragment : Fragment(), FireCallback {
    private lateinit var btn_main_play: Button
    private lateinit var btn_main_sign_in: Button
    private lateinit var btn_main_register: Button
    private lateinit var btn_main_articles: Button
    private lateinit var db : Firebase
    private lateinit var pattern : Pattern
    private lateinit var patterns : ArrayList<Pattern>
    private lateinit var btn_profile : Button
    private lateinit var userNow : User
    public lateinit var callback: FireCallback
    var mSettings: SharedPreferences? = null
    lateinit var getEmail : String
    val APP_PREFERENCES = "mysettings"
    val APP_PREFERENCES_EMAIL = "Email"
    val APP_PREFERENCES_PASSWORD = "Password"
    private val isSuccsesful = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_main, container, false)

        init(view)

        setListeners()


        return view
        }

        private fun init(view: View){
            mSettings = Objects.requireNonNull(activity)
            ?.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
            val bundle = arguments
            btn_main_play = view.findViewById(R.id.btn_main_play)
            btn_main_register = view.findViewById(R.id.btn_main_register)
            btn_main_sign_in = view.findViewById(R.id.btn_main_sign_in)
            btn_main_articles = view.findViewById(R.id.btn_main_arcticles_and_rules)
            btn_profile = view.findViewById(R.id.btn_main_profile_name)
            if (bundle != null) {
                getEmail = bundle.getString("emailUser").toString();
                btn_profile.visibility = View.VISIBLE;
                btn_main_sign_in.visibility = View.INVISIBLE
                btn_main_register.visibility = View.INVISIBLE

            }else if (isPreferenses()) {
                getPreferences()
                btn_profile.visibility = View.VISIBLE;
                btn_main_sign_in.visibility = View.INVISIBLE
                btn_main_register.visibility = View.INVISIBLE
            }else {
                btn_profile.visibility = View.INVISIBLE;
                getEmail = null.toString()
            }


            db = Firebase(FirebaseDatabase.getInstance(), FirebaseAuth.getInstance(), this)

            if (isPreferenses()){
                db.enterUser(mSettings?.getString(APP_PREFERENCES_EMAIL," "), mSettings?.getString(APP_PREFERENCES_PASSWORD," "),activity)
            }


        }

        private fun setListeners(){

            btn_profile.setOnClickListener(View.OnClickListener {
                if(getEmail!=null){
                    var i = Intent(activity, UserActivity::class.java)
                    i.putExtra("userEmail",getEmail)
                    startActivity(i)
                }

            })

            btn_main_play.setOnClickListener(View.OnClickListener {
                startActivity(Intent(activity, GameActivity::class.java))
            })


            btn_main_sign_in.setOnClickListener(View.OnClickListener {
                startFragment(SignInFragment())
            })


            btn_main_register.setOnClickListener(View.OnClickListener {
                startFragment(RegisterFragment())
            })


            btn_main_articles.setOnClickListener(View.OnClickListener {
                startActivity(Intent(activity, CommunityActivity::class.java))
            })
        }



        private fun startFragment(fragment: Fragment){
            val FragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
            FragmentTransaction!!.replace(R.id.containerMain,fragment)
            FragmentTransaction.addToBackStack(null)
            FragmentTransaction.commit()

        }

        override fun callString(string: String?) {
            TODO("Not yet implemented")
        }

        override fun callPattern(pattern: Pattern?) {
            this.pattern = pattern!!

            ///Log.d("haaaa",this.pattern.description)
        }

        override fun callAllPatterns(patterns: ArrayList<Pattern>?) {
            this.patterns = patterns!!
           /// Log.d("haaaa",this.patterns.get(0).description)
        }

        override fun callInt(number: Int) {
            TODO("Not yet implemented")
        }

        override fun callUser(user: User?) {
            TODO("Not yet implemented")
        }

        override fun callResponseBool(flag: Boolean) {
            if (flag){
                btn_profile.visibility = View.VISIBLE;

            }
        }

        private fun isPreferenses(): Boolean {
            return mSettings?.contains(APP_PREFERENCES_EMAIL) ?: false &&  mSettings?.contains(APP_PREFERENCES_PASSWORD) ?: false
        }

    private fun getPreferences() {
        getEmail = mSettings?.getString(APP_PREFERENCES_EMAIL, null).toString();


        }
}


