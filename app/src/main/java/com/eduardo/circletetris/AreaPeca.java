package com.eduardo.circletetris;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by eduardo on 2/26/15.
 */
// Area onde é desenhada a peça atualmente caindo e sua sombra.
// É atualizada sempre (no intervalo especificado em Jogo.timer).
public class AreaPeca extends View {

    private static boolean iniciado = false;

    public AreaPeca(Context context) {
        super(context);
    }
    public AreaPeca(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public AreaPeca(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init() {
        if(!iniciado) {
            Peca.novaPeca();
            Peca.iniciaContador();
            iniciado = true;
        }
    }

    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld){
        super.onSizeChanged(xNew, yNew, xOld, yOld);
        init();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        // atualiza canvas com a peça atualmente em movimento
        //desenha_canvas_peca()
        Paint paint = new Paint();

        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);

        // TODO: não usar fatores absolutos, colocar tudo em funcão de Jogo.menor ou similar


        // sombra
        for(int i = 0; i < Peca.pos.length; i++) {
            int x = Peca.sombra[i].x;
            int y = Peca.sombra[i].y;

            int bloco = Peca.cor;
            int lin = y;


            float raio = Jogo.menor/8 + (Jogo.grossura/2) + (Jogo.jogo_h - lin - 1)*Jogo.grossura;

            float rad_offset = (Jogo.menor/8 + (Jogo.grossura/2)) * 0.04f / raio;


            paint.setColor(Color.parseColor("#e2e2e2"));
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(Jogo.grossura - 1.75f);

            canvas.drawArc(Jogo.raio2rect(raio), x*Jogo.tamanho, Jogo.tamanho - rad_offset*10, false, paint);

        }

        // peca caindo atualmente
        for(int i = 0; i < Peca.pos.length; i++) {
            int x = Peca.pos[i].x;
            int y = Peca.pos[i].y;

            int bloco = Peca.cor;
            int lin = y;


            float raio = Jogo.menor/8 + (Jogo.grossura/2) + (Jogo.jogo_h - lin - 1)*Jogo.grossura;

            float rad_offset = (Jogo.menor/8 + (Jogo.grossura/2)) * 0.04f / raio;


            paint.setColor(Color.parseColor(Jogo.cores[bloco]));
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(Jogo.grossura - 1.75f);

            canvas.drawArc(Jogo.raio2rect(raio), x*Jogo.tamanho, Jogo.tamanho - rad_offset*10, false, paint);


        }
    }
}