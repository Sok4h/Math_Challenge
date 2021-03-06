package com.sokah.mathchallenge;

import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<Pregunta> preguntas = new ArrayList<Pregunta>();
    Pregunta preguntaEscogida;
    TextView textPregunta;
    EditText inputRespuesta;
    Button enviar;
    TextView puntajeText;
    Button intentar;
    Boolean jugando = true;
    int contador = 30;
    TextView contadorText;
    int puntaje ;
    int resultado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textPregunta= findViewById(R.id.viewpregunta);
        inputRespuesta= findViewById(R.id.respuesta);
        enviar=findViewById(R.id.enviar);
        puntajeText= findViewById(R.id.puntaje);
        intentar= findViewById(R.id.intentar);
        contadorText= findViewById(R.id.timmer);
        intentar.setVisibility(View.GONE);
        GenerarPreguntas();
        SeleccionarPregunta();
        enviar.setOnClickListener(this);
        intentar.setOnClickListener(this);
        Contador();


    }


   void SeleccionarPregunta(){

       Random random = new Random();
       resultado = random.nextInt(preguntas.size());
       preguntaEscogida=preguntas.get(resultado);
       textPregunta.setText(preguntas.get(resultado).pregunta);
    }

    void ReiniciarJuego(){

        preguntas.clear();
        jugando=true;
        GenerarPreguntas();
        puntaje=0;
        puntajeText.setText(""+puntaje);
        intentar.setVisibility(View.GONE);
        contador=30;
        contadorText.setText(""+contador);
        textPregunta.setText("");
        inputRespuesta.setText("");
        Contador();
        SeleccionarPregunta();


    }

    void GenerarPreguntas(){

        for(int i= 0;i<30;i++){
         Random random = new Random();
        int numeroUno= random.nextInt((10-1)+1)+1;
        int numeroDos= random.nextInt((10-1)+1)+1;
        int operador= random.nextInt(2);


        if(operador==1){

        String pregunta = numeroUno+"+"+ numeroDos;
        int respuesta = numeroUno+numeroDos;
        preguntas.add( new Pregunta(numeroUno+" + "+ numeroDos,""+respuesta));

        }

        else{

            String pregunta= numeroUno+"x"+numeroDos;
            int respuesta = numeroUno*numeroDos;
            preguntas.add( new Pregunta(numeroUno+" X "+ numeroDos,""+respuesta));

        }

        }

    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.enviar:

                if(jugando) {
                    if (inputRespuesta.getText().toString().isEmpty()) {


                        Toast.makeText(this, "Debes poner respuesta", Toast.LENGTH_LONG).show();
                        Log.e("funciono", "onClick: ");
                    }
                    else if (inputRespuesta.getText().toString().equals(preguntaEscogida.respuesta)) {


                        preguntas.remove(resultado);
                        SeleccionarPregunta();
                        inputRespuesta.setText("");
                        puntaje += 15;
                        puntajeText.setText("Puntaje :" + puntaje);


                    } else {

                        Toast.makeText(this, "Respuesta Incorrecta", Toast.LENGTH_LONG).show();
                        puntaje -= 15;
                        puntajeText.setText("Puntaje :" + puntaje);
                    }
                }

                break;

            case R.id.intentar:

                ReiniciarJuego();

                break;

        }
    }

    public void Contador(){

        new Thread  (

                ()->{

                    while (jugando){

                        contador--;
                        runOnUiThread(() -> {


                            contadorText.setText(""+contador);

                            if(contador<=0){

                                intentar.setVisibility(View.VISIBLE);
                                jugando=false;

                                //ReiniciarJuego();
                            }

                        });
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }



                    }

                }

        ).start();


    }
}
