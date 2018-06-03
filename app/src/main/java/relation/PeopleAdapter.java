package relation;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ljw14.tencentadvance.PersonActivity;
import com.example.ljw14.tencentadvance.R;

import java.util.List;


/**
 * 该文件的代码，参考《第一行代码Android》书
 */
public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.ViewHolder> {
    private List<People> mPeopleList;
    public PeopleAdapter(List<People> peopleList){
        mPeopleList = peopleList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        View peopleView;
        TextView peopleName;
        TextView peopleTelephone;
        public ViewHolder(View view){
            super(view);
            peopleView = view;
            peopleName = (TextView) view.findViewById(R.id.peopleName);
            peopleTelephone = (TextView) view.findViewById(R.id.peopleTelephone);
        }
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
     * . Since it will be re-used to display
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
                inflate(R.layout.people_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.peopleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {////这里用于设置条目点击事件
                int position = holder.getAdapterPosition();
                People people = mPeopleList.get(position);
                Toast.makeText(v.getContext(), "you clicked view" + people.getName(),
                        Toast.LENGTH_SHORT).show();

                String name = people.getName();
                String telephone = people.getTelephone();
                Intent intent = new Intent(v.getContext(), PersonActivity.class);
                intent.putExtra("peopleName", name);
                intent.putExtra("peopleTelephone", telephone);
                v.getContext().startActivity(intent);
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
     * Override  instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        People people = mPeopleList.get(position);
        holder.peopleName.setText(people.getName());
        holder.peopleTelephone.setText(people.getTelephone());
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return mPeopleList.size();
    }

    public void setmPeopleList(List<People> mPeopleList) {
        this.mPeopleList = mPeopleList;
    }
}
