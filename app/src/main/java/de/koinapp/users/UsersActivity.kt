package de.koinapp.users

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import de.koinapp.R
import de.koinapp.login.LoginActivity
import de.koinapp.model.GithubUser
import de.koinapp.utils.LoadingState
import org.koin.androidx.viewmodel.ext.android.viewModel


class UsersActivity : AppCompatActivity() {

    @BindView(R.id.textSumOfUsers) lateinit var textSumOfUsers: TextView

    @BindView(R.id.buttonLogin) lateinit var buttonLogin: Button

    @BindView(R.id.listUsers) lateinit var listUsers: RecyclerView

    private val userViewModel by viewModel<UsersViewModel>()
    private lateinit var adapter: UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        setupUI()
        setupViewModel()
    }

    @OnClick(R.id.buttonLogin)
    fun actionLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun setupUI(){
        adapter= UsersAdapter(userViewModel.data.value?: emptyList()) { user -> userItemClicked(user)}
        listUsers.adapter = adapter
        this.listUsers.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        this.listUsers.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    private fun userItemClicked(user: GithubUser) {
        Toast.makeText(this, "Clicked: ${user.login}", Toast.LENGTH_SHORT).show()
    }

    private fun setupViewModel(){
        userViewModel.data.observe(this, Observer {
            if(it.isEmpty()){
                textSumOfUsers.text = getString(R.string.text_no_users_available)
            }else{
                textSumOfUsers.text = getString(R.string.text_users_found, it.size)
            }
            adapter.update(it)
        })
        userViewModel.loadingState.observe(this, Observer {
            if (it.status == LoadingState.Status.FAILED) textSumOfUsers.text = getString(R.string.text_fail_to_fetch_data)
            else if (it.status == LoadingState.Status.RUNNING) textSumOfUsers.text = getString(R.string.text_loading_data)
        })
    }
}
