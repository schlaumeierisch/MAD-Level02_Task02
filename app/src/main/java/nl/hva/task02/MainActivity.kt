package nl.hva.task02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import nl.hva.task02.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val quizTasks = arrayListOf<QuizTask>()
    private val quizTaskAdapter = QuizTaskAdapter(quizTasks) {
        quizTask: QuizTask -> quizTaskItemClicked(quizTask)
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        binding.rvQuizTasks.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        binding.rvQuizTasks.adapter = quizTaskAdapter
        binding.rvQuizTasks.addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))

        quizTasks.addAll(generateQuizTasks())

        createItemTouchHelper().attachToRecyclerView(binding.rvQuizTasks)
    }

    /**
     * Give back an arraylist with quiz tasks.
     */
    private fun generateQuizTasks(): ArrayList<QuizTask> {
        return arrayListOf(
            QuizTask(getString(R.string.statement_1), false),
            QuizTask(getString(R.string.statement_2), true),
            QuizTask(getString(R.string.statement_3), true),
            QuizTask(getString(R.string.statement_4), true)
        )
    }

    private fun quizTaskItemClicked(quizTask: QuizTask) {
        Snackbar.make(binding.root, "This statement seems to be ${quizTask.isCorrect}", Snackbar.LENGTH_LONG).show()
    }

    /**
     * Create a touch helper to recognize when a user swipes an item from a recycler view.
     * An ItemTouchHelper enables touch behavior (like swipe and move) on each ViewHolder,
     * and uses callbacks to signal when a user is performing these actions.
     */
    private fun createItemTouchHelper(): ItemTouchHelper {
        // Callback which is used to create the ItemTouch helper. Only enables left swipe.
        // Use ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) to also enable right swipe.
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            // Enables or Disables the ability to move items up and down.
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            // Callback triggered when a user swiped an item.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition

                if ((direction == ItemTouchHelper.LEFT && !quizTasks.elementAt(position).isCorrect) ||
                    (direction == ItemTouchHelper.RIGHT && quizTasks.elementAt(position).isCorrect)) {
                    quizTasks.removeAt(position)
                    quizTaskAdapter.notifyItemRemoved(position)
                } else {
                    Snackbar.make(binding.root, getString(R.string.snackbar_message), Snackbar.LENGTH_LONG).show()

                    // needed to "reset" view - otherwise the item just disappears
                    quizTaskAdapter.notifyItemChanged(position)
                }
            }
        }
        return ItemTouchHelper(callback)
    }
}