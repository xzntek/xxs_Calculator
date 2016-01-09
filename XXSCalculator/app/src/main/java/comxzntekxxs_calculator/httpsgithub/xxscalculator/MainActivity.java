package comxzntekxxs_calculator.httpsgithub.xxscalculator;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.matheclipse.parser.client.eval.DoubleEvaluator;


public class MainActivity extends AppCompatActivity {

        String formular = "";
        String resolt = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Typeface resFont = Typeface.createFromAsset(getAssets(),
                "square_sans_serif_7.ttf");
        TextView tv = (TextView) findViewById(R.id.resView);
        tv.setTypeface(resFont);
        if (savedInstanceState != null){
            formular = savedInstanceState.getString("formular");
            resolt = savedInstanceState.getString("res");
        }
        TextView resView = (TextView) findViewById(R.id.resView);
        if (resolt.length() == 0){
            resView.setText(formular);
        }else {
            resView.setText(resolt);
        }
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("formular", formular);
        savedInstanceState.putString("res",resolt);
        super.onSaveInstanceState(savedInstanceState);
    }

    public void btnEqual (View view){
        TextView resView = (TextView) findViewById(R.id.resView);
        DoubleEvaluator engine = new DoubleEvaluator();
        formular = formular.replace('x','*');
        double res;
        try {
             res = engine.evaluate(formular);
        }catch (Exception ex) {
            resView.setText("ERROR");
            resolt = "";
            return;
        }
        String resStr = Double.toString(res);
        if (resStr.charAt(resStr.length()-1) == '0' && resStr.charAt(resStr.length() - 2) == '.' ){
          resStr = resStr.substring(0,resStr.length() - 2);
        }
        resolt = resStr;
        resView.setText(resStr);
    }

    public void btnClearClk (View view){
        TextView resView = (TextView) findViewById(R.id.resView);
        formular = "";
        resolt = "";
        resView.setText(formular);
    }

    public  void btnDeleteClk(View view){
        TextView resView = (TextView) findViewById(R.id.resView);

        if (formular.length() == 0){
            resolt = "";
            return;
        }
        formular = formular.substring(0, formular.length() - 1);
        resView.setText(formular);
    }

    public void nrClk(View view){
        TextView resView = (TextView) findViewById(R.id.resView);
        Button button = (Button) view;
        String buttonName = button.getText().toString();
        String lastNr =  getLastNr();

        if (lastNr.equals("0")){
            formular = formular.substring(0,formular.length() - 1) + buttonName;
        }else
        {
            formular += buttonName;
        }
        resView.setText(formular);
    }

    public void signClk(View view){
        Button button = (Button) view;
        String buttonName = button.getText().toString();
        TextView resView = (TextView) findViewById(R.id.resView);
        if (formular.equals("")){
            if (buttonName.equals("-")){
                formular = "-";
            }
            else return;
        }
        String lastNr =  getLastNr();
        char lastChar = lastNr.charAt(lastNr.length() - 1);
        if (Character.isDigit(lastChar)){
            formular += buttonName;
        }else if (lastChar == '-' && formular.length() == 1 && buttonName.equals("+") ){
            formular = "";
        }else if (formular.length() > 1){
            if (((lastChar == '/' ||  lastChar == 'x') && buttonName.equals("-")) || lastChar == ')'){
                formular += buttonName;
            }else {
                formular = formular.substring(0,formular.length() - 1) + buttonName;
            }
        }
        resView.setText(formular);
    }


    public void comaClk(View view){
        TextView resView = (TextView) findViewById(R.id.resView);
            if (formular.equals("")){
            formular = "0.";
            resView.setText(formular);
            return;
        }
        String lastNr =  getLastNr();
        char lastChar = lastNr.charAt(lastNr.length() - 1);
        if (!Character.isDigit(lastChar) && lastChar != '.' && !lastNr.contains(".")){
            formular = formular.substring(0,formular.length() - 1) + ".";
        }else if (!lastNr.contains(".")){
            formular += ".";
        }
        resView.setText(formular);
    }

    public  void ParenthesesClk(View view){
        Button button = (Button) view;
        String buttonName = button.getText().toString();
        TextView resView = (TextView) findViewById(R.id.resView);
        if (formular.length() == 0 && buttonName.equals("(")){
            formular = "(";
            resView.setText(formular);
            return;
        }
        char lastChar = formular.charAt(formular.length() - 1);

        if (lastChar == '.'  ||
                ((lastChar == '(' || lastChar == ')') && buttonName.equals(String.valueOf(lastChar)))){
            formular = formular.substring(0,formular.length() - 1) + buttonName;
        }else{
            formular += buttonName;
        }
        resView.setText(formular);
    }
    private String getLastNr(){
        if (formular.length() <= 1) return formular;
        int lastPos = -1;
        char[] charArray = formular.toCharArray();
        for (int i = formular.length() -2 ; i > -1 ; i--){
          if (!Character.isDigit(charArray[i]) && charArray[i] != '.' ){
              lastPos = i;
              break;
            }
        }
        if (lastPos == -1){
            return  formular;
        }
        return  formular.substring(lastPos +1,formular.length());
    }
}
