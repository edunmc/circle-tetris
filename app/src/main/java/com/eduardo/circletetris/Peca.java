package com.eduardo.circletetris;

import android.graphics.Point;
import android.os.Handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by eduardo on 2/28/15.
 */
// Gerencia a peça. Tudo relativo à peça atualmente caindo, desde a geração até o movimento
// (tudo exceto o desenho dela, que está na AreaPeca).
public class Peca {

    private Peca() {}

    public static Point[] pos = new Point[4];
    public static Point[] posNova = new Point[4];
    public static Point[] sombra = new Point[4];
    public static int n;
    public static int cor;
    public static int orientacao;
    public static int giro;
    private static MainActivity mainActivity;

    static Handler handler = new Handler();

    public static void setMainActivity(MainActivity activity) {
        mainActivity = activity;
    }

    // gera um número aleatório de 0 a max
    public static int rand(int max) {
        Random rand = new Random();
        return rand.nextInt(max+1);
    }
    public static void setPos(int a1, int a2, int b1, int b2, int c1, int c2, int d1, int d2) {
        pos[0] = new Point(a1, a2);
        pos[1] = new Point(b1, b2);
        pos[2] = new Point(c1, c2);
        pos[3] = new Point(d1, d2);
    }
    public static void setPosNova(int a1, int a2, int b1, int b2, int c1, int c2, int d1, int d2) {
        posNova[0] = new Point(a1, a2);
        posNova[1] = new Point(b1, b2);
        posNova[2] = new Point(c1, c2);
        posNova[3] = new Point(d1, d2);
    }

    public static void step() {

        boolean pode = true;

        for(int i = 0; i < pos.length; i++) {
            int x = pos[i].x;
            int y = pos[i].y;

            if(y+1 >= Jogo.jogo_h || Jogo.jogo[y+1][x] != '.') {
                pode = false;
                break;
            }
        }

        // Se puder aumenta o Y dela em 1 unidade ( fazendo ela ficar mais próxima do centro ).
        if(pode) {
            for(int i = 0; i < pos.length; i++) {
                pos[i].y++;
            }
        }
        // Se nao puder, peca tocou em algo (para de cair)
        else {
            for(int i = 0; i < pos.length; i++) {
                int x = pos[i].x;
                int y = pos[i].y;
                if(y < 4) {
                    Jogo.jogando = false;
                    pode = true;
                }
                Jogo.jogo[y][x] = (char)cor;
            }


            // checar linhas para ver se foram completadas
            //var linhas = [];
            ArrayList<Integer> linhas = new ArrayList<Integer>();

            for(int i = 0; i < pos.length; i++) {

                int linha = pos[i].y;

                if(linhas.indexOf(linha) == -1)
                    linhas.add(linha);
            }

            Collections.sort(linhas);

            ArrayList<Integer> linhas_completas = new ArrayList<Integer>();

            for(int i = 0; i < linhas.size(); i++) {
                boolean completa = true;

                for(int k = 0; k < Jogo.jogo_w; k++) {
                    if(Jogo.jogo[linhas.get(i)][k] == '.') {
                        completa = false;
                        break;
                    }
                }

                if(completa)
                    linhas_completas.add(linhas.get(i));
            }



            for(int i = 0; i < linhas_completas.size(); i++) {
                int linha = linhas_completas.get(i);

                for(int j = linha-1; j >= 0; j--) {
                    for(int k = 0; k < Jogo.jogo_w; k++) {
                        Jogo.jogo[j+1][k] = Jogo.jogo[j][k];
                    }
                }
            }

            AreaFundo areaFundo = (AreaFundo) mainActivity.findViewById(R.id.area);
            areaFundo.invalidate(); //desenha_canvas();

        }

        redesenhaPeca();

        if(!pode) novaPeca();
    }

    public static void redesenhaPeca() {
        sombra();
        AreaPeca areaPeca = (AreaPeca) mainActivity.findViewById(R.id.areaPeca);
        areaPeca.invalidate(); //desenha_canvas_peca();
    }

    public static void esquerda() {
        boolean pode = true;
        int novo_x;

        for(int i = 0; i < pos.length; i++) {
            int x = pos[i].x;
            int y = pos[i].y;

            novo_x = ((x-1) + Jogo.jogo_w) % Jogo.jogo_w;

            if(Jogo.jogo[y][novo_x] != '.') {
                pode = false;
                break;
            }
        }

        if(pode) {
            for(int i = 0; i < pos.length; i++) {
                int x = pos[i].x;

                novo_x = ((x-1) + Jogo.jogo_w) % Jogo.jogo_w;
                pos[i].x = novo_x;
            }
            redesenhaPeca();
        }
    }

    public static void direita() {
        boolean pode = true;
        int novo_x;

        for(int i = 0; i < pos.length; i++) {
            int x = pos[i].x;
            int y = pos[i].y;

            novo_x = (x+1) % Jogo.jogo_w;

            if(Jogo.jogo[y][novo_x] != '.') {
                pode = false;
                break;
            }
        }

        if(pode) {
            for(int i = 0; i < pos.length; i++) {
                int x = pos[i].x;

                novo_x = (x+1) % Jogo.jogo_w;
                pos[i].x = novo_x;
            }
            redesenhaPeca();
        }
    }

    public static void iniciaContador() {
        handler.postDelayed(runnable, Jogo.timer);
    }

    private static Runnable runnable = new Runnable() {
        @Override
        public void run() {
            step();
            handler.postDelayed(this, Jogo.timer);
        }
    };

    public static void novaPeca() {

        n = rand(6);
        cor = rand(2);
        orientacao = rand(1);
        giro = 0;

        int w = Jogo.w;

        switch(n) {
            //  *
            // ***
            case 0:
                setPos(w-3, 1,
                       w-2, 1,
                       w-1, 1,
                       w-2, 0);
                break;

            // **
            // **
            case 1:
                setPos(w-2, 1,
                       w-1, 1,
                       w-2, 0,
                       w-1, 0);
                break;

            //   *
            // ***
            case 2:
                setPos(w-3, 1,
                       w-2, 1,
                       w-1, 1,
                       w-1, 0);
                break;

            // *
            // *
            // *
            // *
            case 3:
                setPos(w-1, 3,
                       w-1, 2,
                       w-1, 1,
                       w-1, 0);
            break;

            // *
            // ***
            case 4:
                setPos(w-3, 1,
                       w-2, 1,
                       w-1, 1,
                       w-3, 0);
                break;

            //  **
            // **
            case 5:
                setPos(w-3, 1,
                       w-2, 1,
                       w-2, 0,
                       w-1, 0);
                break;

            // **
            //  **
            case 6:
                setPos(w-2, 1,
                       w-1, 1,
                       w-3, 0,
                       w-2, 0);
                break;
        }

        if(orientacao == 0) {
            pos[0].x -= Jogo.quantas/2;
            pos[1].x -= Jogo.quantas/2;
            pos[2].x -= Jogo.quantas/2;
            pos[3].x -= Jogo.quantas/2;
        }

        int giro_aleatorio = rand(3);
        while(giro_aleatorio-- > 0) gira();

        sombra();
    }

    public static void sombra() {

        int[] menor_y = new int[Jogo.jogo_h+20]; // menor_y[i] é o y de menor altura (= maior número, já que cresce de cima pra baixo) dentre as peças que tem x igual a i.
        for(int i = 0; i < menor_y.length; i++)
            menor_y[i] = -1;

        ArrayList<Integer> diferentes_x = new ArrayList<Integer>();
        for(int i = 0; i < pos.length; i++) {
            int x = pos[i].x;
            int y = pos[i].y;

            if(menor_y[x] == -1) menor_y[x] = y;
            else menor_y[x] = Math.max(menor_y[x], y);

            if(diferentes_x.indexOf(x) == -1) diferentes_x.add(x);
        }


        int menor_variacao = Jogo.jogo_h;

        for(int i = 0; i < diferentes_x.size(); i++) {
            int x = diferentes_x.get(i);
            int y = menor_y[x];


            int v = 0; // variação

            while(y+v+1 < Jogo.jogo_h && Jogo.jogo[y+v+1][x] == '.')
                v++;

            menor_variacao = Math.min(menor_variacao, v);
        }

        for(int i = 0; i < pos.length; i++) {
            int x = pos[i].x;
            int y = pos[i].y;
            sombra[i] = new Point(x, y+menor_variacao);
        }
    }

    public static void gira() {
        Point[] p = pos;
        Point[] pn = posNova;

        for(int i = 0; i < pos.length; i++) {
            posNova[i] = new Point(p[i].x, p[i].y);
        }

        boolean pode = false;

        switch(n) {
            //  *
            // ***
            case 0:
                switch(giro) {
                    case 0:
                        setPosNova(p[0].x + 2, p[0].y + 1,
                                p[1].x + 1, p[1].y,
                                p[2].x,     p[2].y - 1,
                                p[3].x,     p[3].y + 1);
                        break;
                    case 1:
                        setPosNova(p[0].x,     p[0].y - 2,
                                p[1].x - 1, p[1].y - 1,
                                p[2].x - 2, p[2].y,
                                p[3].x,     p[3].y);
                        break;
                    case 2:
                        setPosNova(p[0].x - 2, p[0].y,
                                p[1].x - 1, p[1].y + 1,
                                p[2].x,     p[2].y + 2,
                                p[3].x,     p[3].y);
                        break;
                    case 3:
                        setPosNova(p[0].x,     p[0].y + 1,
                                p[1].x + 1, p[1].y,
                                p[2].x + 2, p[2].y - 1,
                                p[3].x,     p[3].y - 1);
                        break;
                }

                pode = true;
                break;

            // **
            // **
            case 1:
                break;

            //   *
            // ***
            case 2:
                switch(giro) {
                    case 0:
                        setPosNova(p[0].x + 2, p[0].y + 1,
                                p[1].x + 1, p[1].y,
                                p[2].x,     p[2].y - 1,
                                p[3].x - 1, p[3].y);
                        break;
                    case 1:
                        setPosNova(p[0].x,     p[0].y - 2,
                                p[1].x - 1, p[1].y - 1,
                                p[2].x - 2, p[2].y,
                                p[3].x - 1, p[3].y + 1);
                        break;
                    case 2:
                        setPosNova(p[0].x - 2, p[0].y,
                                p[1].x - 1, p[1].y + 1,
                                p[2].x,     p[2].y + 2,
                                p[3].x + 1, p[3].y + 1);
                        break;
                    case 3:
                        setPosNova(p[0].x,     p[0].y + 1,
                                p[1].x + 1, p[1].y,
                                p[2].x + 2, p[2].y - 1,
                                p[3].x + 1, p[3].y - 2);
                        break;
                }

                pode = true;
                break;

            // *
            // *
            // *
            // *
            case 3:
                switch(giro) {
                    case 0:
                    case 2:
                        setPosNova(p[0].x + 2, p[0].y - 3,
                                p[1].x + 1, p[1].y - 2,
                                p[2].x,     p[2].y - 1,
                                p[3].x - 1, p[3].y);
                        break;
                    case 1:
                    case 3:
                        setPosNova(p[0].x - 2, p[0].y + 3,
                                p[1].x - 1, p[1].y + 2,
                                p[2].x,     p[2].y + 1,
                                p[3].x + 1, p[3].y);
                        break;
                }

                pode = true;
                break;

            // *
            // ***
            case 4:
                switch(giro) {
                    case 0:
                        setPosNova(p[0].x + 2, p[0].y + 1,
                                p[1].x + 1, p[1].y,
                                p[2].x,     p[2].y - 1,
                                p[3].x + 1, p[3].y + 2);
                        break;
                    case 1:
                        setPosNova(p[0].x,     p[0].y - 2,
                                p[1].x - 1, p[1].y - 1,
                                p[2].x - 2, p[2].y,
                                p[3].x + 1, p[3].y - 1);
                        break;
                    case 2:
                        setPosNova(p[0].x - 2, p[0].y,
                                p[1].x - 1, p[1].y + 1,
                                p[2].x,     p[2].y + 2,
                                p[3].x - 1, p[3].y - 1);
                        break;
                    case 3:
                        setPosNova(p[0].x,     p[0].y + 1,
                                p[1].x + 1, p[1].y,
                                p[2].x + 2, p[2].y - 1,
                                p[3].x - 1, p[3].y);
                        break;
                }

                pode = true;
                break;

            //  **
            // **
            case 5:
                switch(giro) {
                    case 0:
                    case 2:
                        setPosNova(p[0].x + 2, p[0].y + 1,
                                p[1].x + 1, p[1].y,
                                p[2].x,     p[2].y + 1,
                                p[3].x - 1, p[3].y);
                        break;
                    case 1:
                    case 3:
                        setPosNova(p[0].x - 2, p[0].y - 1,
                                p[1].x - 1, p[1].y,
                                p[2].x,     p[2].y - 1,
                                p[3].x + 1, p[3].y);
                        break;
                }

                pode = true;
                break;

            // **
            //  **
            case 6:
                switch(giro) {
                    case 0:
                    case 2:
                        setPosNova(p[0].x,     p[0].y,
                                p[1].x - 1, p[1].y - 1,
                                p[2].x,     p[2].y + 2,
                                p[3].x - 1, p[3].y + 1);
                        break;
                    case 1:
                    case 3:
                        setPosNova(p[0].x,     p[0].y,
                                p[1].x + 1, p[1].y + 1,
                                p[2].x,     p[2].y - 2,
                                p[3].x + 1, p[3].y - 1);
                        break;
                }

                pode = true;
                break;
        }

        pn[0].x = (pn[0].x + Jogo.jogo_w) % Jogo.jogo_w;
        pn[1].x = (pn[1].x + Jogo.jogo_w) % Jogo.jogo_w;
        pn[2].x = (pn[2].x + Jogo.jogo_w) % Jogo.jogo_w;
        pn[3].x = (pn[3].x + Jogo.jogo_w) % Jogo.jogo_w;

        if(pode) {
            for(int i = 0; i < pn.length; i++) {
                int x = pn[i].x;
                int y = pn[i].y;

                if(y >= Jogo.jogo_h || Jogo.jogo[y][x] != '.') {
                    pode = false;
                    break;
                }
            }
        }

        if(pode) {
            for(int i = 0; i < pn.length; i++) {
                p[i].x = pn[i].x;
                p[i].y = pn[i].y;
            }
            giro++;
            giro %= 4;

            redesenhaPeca();
        }
    }
}


