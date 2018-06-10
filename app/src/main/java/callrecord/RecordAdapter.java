package callrecord;

import android.content.Intent;
import android.sax.StartElementListener;
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
import com.example.ljw14.tencentadvance.RecordDetail;

import org.w3c.dom.Text;

import java.util.List;


/**
 * 该文件的代码，参考《第一行代码Android》书
 */
public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder>{
    private List<Record> mRecordList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        View recordView;
        TextView recordName;
        TextView recordTelephone;
        TextView recordStartTime;
        TextView recordType;
        TextView recordDurationTime;
        TextView recordLocation;
        public ViewHolder(View view){
            super(view);
            recordView = view;
            recordName = (TextView) view.findViewById(R.id.recordName);
            recordTelephone = (TextView) view.findViewById(R.id.recordTelephone);
            recordStartTime = (TextView) view.findViewById(R.id.recordStartTime);
            recordType = (TextView) view.findViewById(R.id.recordType);
            recordDurationTime = (TextView) view.findViewById(R.id.recordDurationTime);
            recordLocation = (TextView) view.findViewById(R.id.recordLocation);
        }
    }

    public RecordAdapter(List<Record> recordList){
        this.mRecordList = recordList;
    }

    public void setmRecordList(List<Record> mRecordList) {
        this.mRecordList = mRecordList;
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
                inflate(R.layout.record_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.recordView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Record record = mRecordList.get(position);
                Toast.makeText(v.getContext(), "you clicked" + record.getDisplayName()
                        , Toast.LENGTH_SHORT).show();
                String name = record.getDisplayName();
                String telephone = record.getPhone();
                String startTime = record.getDate();
                String endTime = record.getType();
                Intent intent = new Intent(v.getContext(), RecordDetail.class);
                intent.putExtra("peopleName", name);
                intent.putExtra("peopleTelephone", telephone);
                intent.putExtra("StartTime", startTime);
                intent.putExtra("EndTime",endTime);
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
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Record record = mRecordList.get(position);
        holder.recordName.setText(record.getDisplayName());
        holder.recordStartTime.setText(record.getDate());
        holder.recordTelephone.setText(record.getPhone());
        holder.recordType.setText(record.getType());
        holder.recordDurationTime.setText(record.getDurationTime());
        holder.recordLocation.setText(record.getLocation());
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return mRecordList.size();
    }
}
