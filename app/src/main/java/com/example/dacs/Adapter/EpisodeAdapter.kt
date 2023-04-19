import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dacs.Adapter.PhimMoiAdapter
import com.example.dacs.Data.Episode
import com.example.dacs.Data.MovieData
import com.example.dacs.R

class EpisodeAdapter(private val pmlist: List<Episode>) : RecyclerView.Adapter<EpisodeAdapter.ViewHolder>() {
    inner  class  ViewHolder (itemView: View) : RecyclerView.ViewHolder (itemView) {
        private val title: TextView = itemView.findViewById(R.id.episode_number)
        fun bind(episode: Episode) {
            title.text = episode.name

        }
    }

    // co the view cua firebase
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_episode, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.textView.setText(pmlist.get(position).Ntitle)
//        Glide.with(holder.itemView.context).load(pmlist.get(position).Nthumb).into(holder.imageView)
        val movie = pmlist[position]
        holder.bind(movie)

    }

    override fun getItemCount(): Int = pmlist.size
}