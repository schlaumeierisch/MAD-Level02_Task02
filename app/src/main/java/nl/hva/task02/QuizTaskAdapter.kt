package nl.hva.task02

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import nl.hva.task02.databinding.ItemQuizTaskBinding

class QuizTaskAdapter(private val quizTasks: List<QuizTask>, private val clickListener: (QuizTask) -> Unit): RecyclerView.Adapter<QuizTaskAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemQuizTaskBinding.bind(itemView)

        fun databind(quizTask: QuizTask, clickListener: (QuizTask) -> Unit) {
            binding.tvStatement.text = quizTask.statement
            itemView.setOnClickListener {
                clickListener(quizTask)
            }
        }
    }

    /**
     * Creates and returns a ViewHolder object, inflating a standard layout called simple_list_item_1.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_quiz_task, parent, false)
        )
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.databind(quizTasks[position], clickListener)
    }

    /**
     * Returns the size of the list
     */
    override fun getItemCount(): Int {
        return quizTasks.size
    }
}