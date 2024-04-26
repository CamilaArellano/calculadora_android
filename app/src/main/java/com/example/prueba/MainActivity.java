package com.example.prueba;
/*
4SA
Equipo 1
Arellano Peraza Eira Camila
Balam Ávila Isaac Alexander
Salinas Parra Sylvana
 */
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import com.google.android.material.button.MaterialButton;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity implements View.OnCapturedPointerListener, View.OnClickListener {

    TextView result,solution;
    MaterialButton buttonC,buttonOpen,buttonClose;
    MaterialButton buttonDiv,buttonMultiply,buttonPlus,buttonMinus;
    MaterialButton button7,button8,button9,button4,button5,button6;
    MaterialButton button1,button2,button3;
    MaterialButton buttonAC,button0,buttonDot,buttonEq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result=findViewById(R.id.result);
        solution=findViewById(R.id.solution);

        asignarId(buttonC,R.id.button_c);
        asignarId(buttonOpen,R.id.button_open);
        asignarId(buttonClose,R.id.button_close);
        asignarId(buttonDiv,R.id.button_divide);
        asignarId(buttonMultiply,R.id.button_multiply);
        asignarId(buttonPlus,R.id.button_add);
        asignarId(buttonMinus,R.id.button_subtract);
        asignarId(button7,R.id.button_7);
        asignarId(button8,R.id.button_8);
        asignarId(button9,R.id.button_9);
        asignarId(button4,R.id.button_4);
        asignarId(button5,R.id.button_5);
        asignarId(button6,R.id.button_6);
        asignarId(button1,R.id.button_1);
        asignarId(button2,R.id.button_2);
        asignarId(button3,R.id.button_3);
        asignarId(buttonAC,R.id.button_ac);
        asignarId(button0,R.id.button_0);
        asignarId(buttonDot,R.id.button_dot);
        asignarId(buttonEq,R.id.button_equal);

    }
//Se inicializan los elementos de la interfaz
    void asignarId(MaterialButton btn, int id){
       btn=findViewById(id);
       btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;//Se obtiene el botón presionado
        String buttonText = button.getText().toString();//Se asigna a un string
        String data = solution.getText().toString();//Se asigna un string a la aperación

        if (buttonText.equals("AC"))
        {//Se limpia nuestra pantalla
            solution.setText("");
            result.setText("0");
            return;
        } else if (buttonText.equals("="))
        {//Se imprime el resultado
            solution.setText(result.getText());
            return;
        } else if (buttonText.equals("C"))
        {//Borra el ultimo dato
            if (data.length()>0)
            {//Primero comprueba si hay datos a borrar
                data = data.substring(0,data.length()-1);
            }
        }else
        { //Se concatena el texto de los botones
            data = data + buttonText;
        }
        //Se muestra la cadena más actualizada
        solution.setText(data);
        if(!data.isEmpty())
        {//Antes de evaluar la expresión, checa que haya datos a evaluar
            String finalResult=evaluarExpression(data);
            if (!finalResult.equals("Error"))
            {//Si no hay un error se muestra en la pantalla
                result.setText(finalResult);
            }
        }

    }

    private String evaluarExpression(String expression) {
        try {//Se utiliza la biblioteca Rhino para las operaciones
            Context context = Context.enter(); //Se crea el contexto en que se evalua la expresión
            context.setOptimizationLevel(-1);//Se selecciona un nivel de optimización
            Scriptable scriptable = context.initStandardObjects(); //La ejecución es en scripts
            //Se evalua la expresión y se convierte a string
            String finalResult = context.evaluateString(scriptable, expression, "Javascript", 1, null).toString();
            if (finalResult.endsWith(".0")) {
                finalResult = finalResult.replace(".0", "");
            }
            return finalResult;
        } catch (Exception e) {
            return "Error";
        }
    }

    @Override
    public boolean onCapturedPointer(View view, MotionEvent motionEvent) {
        return false;
    }
}