package com.saurabh.multichat.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ListenerRegistration
import com.saurabh.multichat.AppConstants
import com.saurabh.multichat.ChatActivity

import com.saurabh.multichat.R
import com.saurabh.multichat.recyclerview.PersonItem
import com.saurabh.multichat.util.FirestoreUtil
import com.xwray.groupie.Section
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.OnItemClickListener
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.fragment_people.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.jetbrains.anko.support.v4.startActivity

/**
 * A simple [Fragment] subclass.
 */
class PeopleFragment : Fragment() {

    private lateinit var userListenerRegistration: ListenerRegistration

    private var shouldInitRecyclerView = true

    private lateinit var peopleSection: Section

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        userListenerRegistration =
            FirestoreUtil.addUserListener(this.activity!!, this::updateRecyclerView)

        return inflater.inflate(R.layout.fragment_people, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        FirestoreUtil.removeListener(userListenerRegistration)
        shouldInitRecyclerView = true
    }

    private fun updateRecyclerView(items: List<Item>) {

        fun init() {
            recycler_view_people.apply {
                layoutManager = LinearLayoutManager(this@PeopleFragment.context)
                adapter = GroupAdapter<ViewHolder>().apply {
                    peopleSection = Section(items)
                    add(peopleSection)
                    setOnItemClickListener(onItemClick)
                }
            }
            shouldInitRecyclerView = false
        }

        fun updateItems() = peopleSection.update(items)

        if (shouldInitRecyclerView)
            init()
        else
            updateItems()

    }

    private val onItemClick = OnItemClickListener { item, view ->
        if (item is PersonItem) {
//            startActivity<ChatActivity>(
//                AppConstants.USER_NAME to item.person.name,
//                AppConstants.USER_ID to item.userId
//            )

            startActivity<ChatActivity>(
                AppConstants.USER_NAME to item.person.name,
                AppConstants.USER_ID to item.userId
            )
        }
    }

}
