package svn.rpodmp.bsuir_android;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    TextView inputMonth;
    TextView resultViewText;
    TextView inputSalary;
    TextView inputVacInDays;
    Button resultBtn;
    double paidForVacation = 0;

    @Override
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultBtn = (Button) findViewById(R.id.resultButton);
        resultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputMonth = (TextView) findViewById(R.id.workedMonthNumber);
                inputSalary = (TextView) findViewById(R.id.middleSalaryDecNumber);
                inputVacInDays = (TextView) findViewById(R.id.vacInDays);
                resultViewText = (TextView) findViewById(R.id.resultTextView);

                int monthCount = Integer.parseInt(inputMonth.getText().toString());
                double middleSalaryCount = Double.parseDouble(inputSalary.getText().toString());
                int vacInDaysCount = Integer.parseInt(inputVacInDays.getText().toString());

                if (monthCount > 12 || monthCount < 6) {
                    Toast.makeText(getApplicationContext(),
                            String.format("Вы ввели: %s отработанных месяцев." +
                                    "\nКол-во месяцев в текущем году не должно быть больше 12 или меньше 6",
                                    inputMonth.getText()),
                            Toast.LENGTH_LONG).show();
                } else {
                    if (vacInDaysCount >= 0 && vacInDaysCount <= 28) {
                        if (middleSalaryCount >= 0) {
                            paidForVacation = getPaidForVacation(middleSalaryCount, vacInDaysCount);
                            resultViewText.setText(
                                    String.format("За %s дней отпуска\nвы получите %s р.",
                                            vacInDaysCount,
                                            new DecimalFormat("#0.00").format(paidForVacation)));
                        } else {
                            Toast.makeText(getApplicationContext(),"Введённая зарплата меньше 0р",
                                    Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(),
                                String.format("Вы запросили %s дней отпуска." +
                                                "\nЭто значение больше 28 или меньше 0 дней",
                                        vacInDaysCount),
                                Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
    }

    public double getPaidForVacation(double middleSalary, int vacInDays) {
        double onePaidVacDay = middleSalary / 29.7;
        return onePaidVacDay * vacInDays;
    }
}