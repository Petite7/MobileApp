package remind;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ljw14.tencentadvance.R;

public class AddDialog extends Dialog {
    private Activity context;
    private Button addRemindButtonOk;
    public EditText addRemindTextEvent;
    public EditText addRemindTextTime;
    public EditText addRemindAdditionInfo;
    private View.OnClickListener mClickListener;


    public AddDialog(Activity context, int theme, View.OnClickListener clickListener){
        super(context);
        this.context = context;
        this.mClickListener = clickListener;
    }

    /**
     * Similar to {@link Activity#onCreate}, you should initialize your dialog
     * in this method, including calling {@link #setContentView}.
     *
     * @param savedInstanceState If this dialog is being reinitialized after a
     *                           the hosting activity was previously shut down, holds the result from
     *                           the most recent call to {@link #onSaveInstanceState}, or null if this
     *                           is the first time.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.remind_add_dialog);

        addRemindTextEvent = (EditText) findViewById(R.id.addRemindEvent);
        addRemindTextTime = (EditText) findViewById(R.id.addRemindTime);
        addRemindAdditionInfo = (EditText) findViewById(R.id.addRemindAdditionInfo);

        Window dialogWindow = this.getWindow();
        WindowManager windowManager = context.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
        layoutParams.height = (int)(display.getHeight() * 0.6);
        layoutParams.width = (int)(display.getWidth() * 0.5);
        dialogWindow.setAttributes(layoutParams);

        addRemindButtonOk = (Button) findViewById(R.id.addRemindButtonOk);
        addRemindButtonOk.setOnClickListener(mClickListener);
    }
}
