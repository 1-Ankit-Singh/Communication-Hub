package com.project.communicationhub

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.project.communicationhub.databinding.ActivityChatBinding
import com.project.communicationhub.models.User
import com.project.communicationhub.utils.KeyboardVisibilityUtil
import com.squareup.picasso.Picasso
import com.vanniktech.emoji.EmojiManager
import com.vanniktech.emoji.google.GoogleEmojiProvider

const val USER_ID = "userId"
const val USER_THUMB_IMAGE = "thumbImage"
const val USER_NAME = "userName"

class ChatActivity : AppCompatActivity() {

    //Initializing variables
    private lateinit var chatActivity: ActivityChatBinding
    /*private val friendId: String = intent.getStringExtra(USER_ID)!!
    private val name: String = intent.getStringExtra(USER_NAME)!!
    private val image: String = intent.getStringExtra(USER_THUMB_IMAGE)!!
    private val mCurrentUid: String = FirebaseAuth.getInstance().uid!!
    private val db: FirebaseDatabase = FirebaseDatabase.getInstance()
    lateinit var currentUser: User
    //lateinit var chatAdapter: ChatAdapter

    private lateinit var keyboardVisibilityHelper: KeyboardVisibilityUtil
    //private val mutableItems: MutableList<ChatEvent> = mutableListOf()
    private val mLinearLayout: LinearLayoutManager by lazy { LinearLayoutManager(this) }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EmojiManager.install(GoogleEmojiProvider())
        chatActivity = ActivityChatBinding.inflate(layoutInflater)
        setContentView(chatActivity.root)

        /*FirebaseFirestore.getInstance().collection("users").document(mCurrentUid).get()
            .addOnSuccessListener {
                currentUser = it.toObject(User::class.java)!!
            }

        chatActivity.nameTv.text = name
        Picasso.get().load(image).into(chatActivity.userImgView)*/

    }

    companion object {
        fun createChatActivity(context: Context, id: String, name: String, image: String): Intent {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra(USER_ID, id)
            intent.putExtra(USER_NAME, name)
            intent.putExtra(USER_THUMB_IMAGE, image)

            return intent
        }
    }

}