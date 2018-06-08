package remind;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import com.example.ljw14.tencentadvance.R;

import java.util.List;

public class RemindAdapter extends RecyclerView.Adapter<RemindAdapter.ViewHolder> {

    private List<Remind> mRemindList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View remindView;
        TextView remindTime;
        TextView remindEvent;
        TextView remindFinished;

        public ViewHolder(View view){
            super(view);
            remindView = view;
            remindTime = (TextView) view.findViewById(R.id.remindTime);
            remindEvent = (TextView) view.findViewById(R.id.remindEvent);
            remindFinished = (TextView) view.findViewById(R.id.remindFinished);
        }
    }

    public RemindAdapter(List<Remind> remindList){
        this.mRemindList = remindList;
    }

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.remind_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.remindView.setOnClickListener(new View.OnClickListener(){
            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Remind remind = mRemindList.get(position);

            }
        });
        return holder;
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p>
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Remind remind = mRemindList.get(position);
        holder.remindTime.setText(remind.getRemindTime());
        holder.remindEvent.setText(remind.getRemindEvent());
        holder.remindFinished.setText(remind.getFinished());
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return mRemindList.size();
    }
}
