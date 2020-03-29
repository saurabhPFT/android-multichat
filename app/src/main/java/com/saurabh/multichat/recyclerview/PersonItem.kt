package com.saurabh.multichat.recyclerview

import android.content.Context
import com.bumptech.glide.Glide
import com.saurabh.multichat.R
import com.saurabh.multichat.model.User
import com.saurabh.multichat.util.StorageUtil
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_person.*

class PersonItem(val person: User,
                 val userId:String,
                 private val context: Context
): Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.textView_name.text=person.name
        viewHolder.textView_bio.text=person.bio
        if(person.profilePicturePath!=null){
            Glide.with(context)
                .load(StorageUtil.pathToReference(person.profilePicturePath))
                .placeholder(R.drawable.ic_account_circle_black_24dp)
                .into(viewHolder.imageView_profile_picture)
        }
         }

    override fun getLayout()= R.layout.item_person
}