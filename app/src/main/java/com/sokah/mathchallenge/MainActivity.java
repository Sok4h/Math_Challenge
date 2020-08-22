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

        preguntas.add(new Pregunta("5x4","20"));
        preguntas.add(new Pregunta("10/2","5"));
        preguntas.add(new Pregunta("10x5","50"));
        preguntas.add(new Pregunta("40/2","20"));
        preguntas.add(new Pregunta("40/5","8"));
        SeleccionarPregunta();
        enviar.setOnClickListener(this);
        intentar.setOnClickListener(this);
        contadorText.setText("30");



        new Thread(

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


    void SeleccionarPregunta(){

        Random random = new Random();
       resultado = random.nextInt(preguntas.size());
       preguntaEscogida=preguntas.get(resultado);
       textPregunta.setText(preguntas.get(resultado).pregunta);
    }

    void ReiniciarJuego(){

        preguntas.clear();
        preguntas.add(new Pregunta("5x4","20"));
        preguntas.add(new Pregunta("10/2","5"));
        preguntas.add(new Pregunta("10x5","50"));
        preguntas.add(new Pregunta("40/2","20"));
        preguntas.add(new Pregunta("40/5","8"));

        puntaje=0;
        puntajeText.setText(""+puntaje);
        intentar.setVisibility(View.GONE);
        jugando=true;
        contador=30;
        contadorText.setText(""+contador);



    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.enviar:

                if(inputRespuesta.getText().toString().isEmpty()) {


                    Toast.makeText(this,"Debes poner respuesta",Toast.LENGTH_LONG).show();
                    Log.e("funciono", "onClick: " );
                }

               else if(inputRespuesta.getText().toString().equals(preguntaEscogida.respuesta)){


                    preguntas.remove(resultado);
                    SeleccionarPregunta();
                    inputRespuesta.setText("");
                    puntaje+=15;
                    puntajeText.setText("Puntaje :"+ puntaje);


                }

                else{

                    Toast.makeText(this,"Respuesta Incorrecta",Toast.LENGTH_LONG).show();
                    puntaje-=15;
                    puntajeText.setText("Puntaje :"+ puntaje);
                }

                break;

            case R.id.intentar:

                ReiniciarJuego();

                break;

        }
    }
}
