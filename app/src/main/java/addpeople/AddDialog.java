package addpeople;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import com.example.ljw14.tencentadvance.R;


public class AddDialog extends Dialog {

    private Activity context;
    private Button addButtonOk;
    public EditText addTextName;
    public EditText addTextTelephone;
    public EditText addAdditionInfo;
    private View.OnClickListener mClickListener;

    public AddDialog(Activity context){
        super(context);
        this.context = context;
    }

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

        this.setContentView(R.layout.add_dialog);

        addTextName = (EditText) findViewById(R.id.addNameAAA);
        addTextTelephone = (EditText) findViewById(R.id.addTelephoneAAA);
        addAdditionInfo = (EditText) findViewById(R.id.addAdditionInfoAAA);

        Window dialogWindow = this.getWindow();

        WindowManager windowManager = context.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
        layoutParams.height = (int)(display.getHeight() * 0.6);
        layoutParams.width = (int)(display.getWidth() * 0.8);
        dialogWindow.setAttributes(layoutParams);

        addButtonOk = (Button) findViewById(R.id.addButtonOkAAA);
        addButtonOk.setOnClickListener(mClickListener);

        this.setCancelable(true);
    }
}
