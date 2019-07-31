package ee.roost.rene.androidjavamasterclass.sensibleconverter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText vastus;
    private EditText uusArv;
    private TextView kuvaTehteMark;

    // Muutujad operandide ja tehtetyybi jaoks
    private Double operand1 = null;
    private String kaimasOlevTehe = "=";

    private static final String OLEK_KAIMASOLEV_TEHE = "KaimasolevTehe";
    private static final String OLEK_OPERAND1 = "Operand1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vastus = findViewById(R.id.vastus);
        uusArv = findViewById(R.id.uus_arv);
        kuvaTehteMark = findViewById(R.id.tehte_mark);

        Button nuppNr0 = findViewById(R.id.nupp_nr0);
        Button nuppNr1 = findViewById(R.id.nupp_nr1);
        Button nuppNr2 = findViewById(R.id.nupp_nr2);
        Button nuppNr3 = findViewById(R.id.nupp_nr3);
        Button nuppNr4 = findViewById(R.id.nupp_nr4);
        Button nuppNr5 = findViewById(R.id.nupp_nr5);
        Button nuppNr6 = findViewById(R.id.nupp_nr6);
        Button nuppNr7 = findViewById(R.id.nupp_nr7);
        Button nuppNr8 = findViewById(R.id.nupp_nr8);
        Button nuppNr9 = findViewById(R.id.nupp_nr9);
        Button nuppKoma = findViewById(R.id.nupp_koma);

        Button nuppVordub = findViewById(R.id.nupp_vordub);
        Button nuppJaga = findViewById(R.id.nupp_jaga);
        Button nuppKorruta = findViewById(R.id.nupp_korruta);
        Button nuppLahuta = findViewById(R.id.nupp_lahuta);
        Button nuppLiida = findViewById(R.id.nupp_liida);

        View.OnClickListener arvKuular = new View.OnClickListener() {
            @Override
            public void onClick(View vaade) {
                Button nupp = (Button) vaade;
                uusArv.append(nupp.getText().toString());
            }
        };
        nuppNr0.setOnClickListener(arvKuular);
        nuppNr1.setOnClickListener(arvKuular);
        nuppNr2.setOnClickListener(arvKuular);
        nuppNr3.setOnClickListener(arvKuular);
        nuppNr4.setOnClickListener(arvKuular);
        nuppNr5.setOnClickListener(arvKuular);
        nuppNr6.setOnClickListener(arvKuular);
        nuppNr7.setOnClickListener(arvKuular);
        nuppNr8.setOnClickListener(arvKuular);
        nuppNr9.setOnClickListener(arvKuular);
        nuppKoma.setOnClickListener(arvKuular);

        View.OnClickListener teheKuular = new View.OnClickListener() {
            @Override
            public void onClick(View vaade) {
                Button nupp = (Button) vaade;
                String tehteMark = nupp.getText().toString();
                String vaartus = uusArv.getText().toString();
                try {
                    Double doubleVaartus = Double.valueOf(vaartus);
                    sooritaTehe(doubleVaartus, tehteMark);
                } catch (NumberFormatException erind) {
                    uusArv.setText("");
                }
                kaimasOlevTehe = tehteMark;
                kuvaTehteMark.setText(kaimasOlevTehe);
            }
        };
        nuppVordub.setOnClickListener(teheKuular);
        nuppJaga.setOnClickListener(teheKuular);
        nuppKorruta.setOnClickListener(teheKuular);
        nuppLahuta.setOnClickListener(teheKuular);
        nuppLiida.setOnClickListener(teheKuular);

        Button nuppNeg = findViewById(R.id.nupp_negatiivne);
        nuppNeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String vaartus = uusArv.getText().toString();
                if(vaartus.length() == 0) {
                    uusArv.setText("-");
                } else {
                    try {
                        double doubleVaartus = Double.parseDouble(vaartus);
                        doubleVaartus *= 1;
                        uusArv.setText(Double.toString(doubleVaartus));
                    } catch (NumberFormatException erind) {
                        // uusArv oli "-" voi ".", seega tuhjenda string
                        uusArv.setText("");
                    }
                }
            }
        });

        Button nuppNulli = findViewById(R.id.nupp_nulli);
        nuppNulli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vastus.setText("");
                uusArv.setText("");
                kuvaTehteMark.setText("");
                operand1 = null;
                kaimasOlevTehe = "";
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(OLEK_KAIMASOLEV_TEHE, kaimasOlevTehe);
        if (operand1 != null) {
            outState.putDouble(OLEK_OPERAND1, operand1);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        kaimasOlevTehe = savedInstanceState.getString(OLEK_KAIMASOLEV_TEHE);
        operand1 = savedInstanceState.getDouble(OLEK_OPERAND1);
        kuvaTehteMark.setText(kaimasOlevTehe);
    }

    @SuppressLint("SetTextI18n")
    private void sooritaTehe(Double vaartus, String tehteMark) {
        if (operand1 == null) {
            operand1 = vaartus;
        } else {
            if (kaimasOlevTehe.equals("=")) {
                kaimasOlevTehe = tehteMark;
            }
            switch (kaimasOlevTehe) {
                case "=":
                    operand1 = vaartus;
                    break;
                case "/":
                    if (vaartus == 0) {
                        operand1 = 0.0;
                    } else {
                        operand1 /= vaartus;
                    }
                    break;
                case "*":
                    operand1 *= vaartus;
                    break;
                case "-":
                    operand1 -= vaartus;
                    break;
                case "+":
                    operand1 += vaartus;
                    break;
            }
        }

        vastus.setText(operand1.toString());
        uusArv.setText("");
    }
}
