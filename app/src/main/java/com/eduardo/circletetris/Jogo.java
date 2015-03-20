package com.eduardo.circletetris;

import android.graphics.RectF;

/**
 * Created by eduardo on 2/28/15.
 */
// Variáveis "globais" acessadas por todas as classes.
public class Jogo {


    public static float largura; // largura do canvas
    public static float altura_touch; // altura da area com os botoes de toque, embaixo
    public static float altura; // altura do canvas
    public static float menor; // min(largura, altura)

    public static float centro_x; // centro do canvas
    public static float centro_y; // centro do canvas

    public static float grossura; // espessura (altura) das pecas


    public static int jogo_w = 24; // largura do grid
    public static int jogo_h = 20; // altura do grid
    public static int w = jogo_w;
    public static int h = jogo_h;
    public static int quantas = jogo_w; // quantas pecas por "linha" (largura de cada será calculada automaticamente)
    public static float tamanho = 360/quantas; // largura das pecas, em graus

    public static int timer = 500; // tempo para cada step


    public static char[][] jogo; // grid com o jogo

    public static boolean jogando = true; // jogo rolando?

    public static String[] cores = new String[] {"#5a5858", "#ec747d", "#41c0a9"};


    static {
        Jogo.jogo = new char[Jogo.jogo_h][];

        for(int i = 0; i < Jogo.jogo_h; i++) {
            Jogo.jogo[i] = new char[Jogo.jogo_w];
            for(int j = 0; j < Jogo.jogo_w; j++) {
                Jogo.jogo[i][j] = '.';
            }
        }
    }



    // retorna as coordenadas do retangulo (RectF) que contem a esfera de raio raio com centro no centro da Area
    public static RectF raio2rect(float raio) {
        //RectF(float left, float top, float right, float bottom)
        return new RectF(Jogo.centro_x - raio, Jogo.centro_y - raio, Jogo.centro_x + raio, Jogo.centro_y + raio);
    }
}
