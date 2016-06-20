package svin.beerbowl.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;

import svin.beerbowl.R;
import svin.beerbowl.models.Player;

public class SubmitMatchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sumbit_match);

        NumberPicker numPickLeft = (NumberPicker)findViewById(R.id.LeftTeamScore);
        numPickLeft.setMinValue(0);
        numPickLeft.setMaxValue(20);
        numPickLeft.setWrapSelectorWheel(false);
        numPickLeft.setValue(0);

        NumberPicker numPickRight = (NumberPicker)findViewById(R.id.RightTeamScore);
        numPickRight.setMinValue(0);
        numPickRight.setMaxValue(20);
        numPickRight.setWrapSelectorWheel(false);
        numPickRight.setValue(0);

        Spinner HomeFirst = (Spinner) findViewById(R.id.HomeFirstName);
        Spinner HomeSecond = (Spinner) findViewById(R.id.HomeSecondName);
        Spinner AwayFirst = (Spinner) findViewById(R.id.AwayFirstName);
        Spinner AwaySecond = (Spinner) findViewById(R.id.AwaySecondName);

        Button SubmitButton = (Button) findViewById(R.id.submitButton);
        // TODO check for best practice
        assert SubmitButton != null;
        SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckMatch())
                    SendSubmitMatchRequest();
                    MessageBox("Det fint!",MsgType.OK);
            }
        });
    }

    private boolean CheckMatch() {
        if (((NumberPicker) findViewById(R.id.LeftTeamScore)).getValue() == ((NumberPicker) findViewById(R.id.RightTeamScore)).getValue()) {
            MessageBox("No Equal Scores allow", MsgType.ERROR);
            return false;
        }

        String name1 = ((Player) ((Spinner) findViewById(R.id.HomeFirstName)).getSelectedItem()).getPlayerName();
        String name2 = ((Player) ((Spinner) findViewById(R.id.HomeSecondName)).getSelectedItem()).getPlayerName();
        String name3 = ((Player) ((Spinner) findViewById(R.id.AwayFirstName)).getSelectedItem()).getPlayerName();
        String name4 = ((Player) ((Spinner) findViewById(R.id.AwaySecondName)).getSelectedItem()).getPlayerName();

        if (name1.equals(name2) ||
                name1.equals(name3) ||
                name1.equals(name4) ||
                name2.equals(name3) ||
                name2.equals(name4) ||
                name3.equals(name4)) {
            MessageBox("No Split Personalities", MsgType.ERROR);
            return false;
        }

        return true;
    }

    private void SendSubmitMatchRequest() {

    }


    private enum MsgType {
        OK, ERROR
    }

    private void MessageBox(String Msg, final MsgType type) {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(SubmitMatchActivity.this);
        dlgAlert.setMessage(Msg);
        if(type == MsgType.ERROR){
            dlgAlert.setTitle("Error Message...");
            dlgAlert.setPositiveButton("OK", null);
        }else{
            dlgAlert.setTitle("Success!");
            dlgAlert.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }
            );
        }
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }
}
