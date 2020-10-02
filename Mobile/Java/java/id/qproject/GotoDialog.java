package id.qproject;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;

class GotoDialog extends Dialog {

    private MainActivity activity;
    private Context context;
    private Button search, cancel;
    private EditText text;
    private GotoDialog thisDialog;

    public static String page;

    public GotoDialog(MainActivity context) {
        super(context);
    
        this.activity = context;
        this.thisDialog = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goto_layout);
        getWindow().setLayout(android.view.ViewGroup.LayoutParams.FILL_PARENT,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        initalize();
    }

    private void initalize() {
    
        text = (EditText) findViewById(R.id.goto_text);
        search = (Button) findViewById(R.id.goto_search);
        cancel = (Button) findViewById(R.id.goto_cancel);

        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                thisDialog.cancel();
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textString = text.getText().toString();
                activity.gotoPage(textString);
                thisDialog.cancel();
            }
        });
    }

}