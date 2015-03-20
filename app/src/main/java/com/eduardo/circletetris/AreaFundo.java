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
// Area de "fundo" onde são desenhadas as peças paradas, que já caíram.
// Só é atualizada quando a peça atual termina de cair e o "fundo" muda.
public class AreaFundo extends View {

    public AreaFundo(Context context) {
        super(context);
    }
    public AreaFundo(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public AreaFundo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init() {
        MainActivity.atualizaTexto("");

        Jogo.largura = this.getWidth();
        Jogo.altura = this.getHeight();
        Jogo.menor = Math.min(Jogo.altura, Jogo.largura);

        Jogo.grossura = Jogo.menor/32;

        Jogo.centro_x = Jogo.largura/2;
        Jogo.centro_y = Jogo.altura/2;

        // pecas de exemplo antes do jogo ter sido totalmente implementado
        //Jogo.jogo[18] = ".........a...a.......a..".toCharArray();
        //Jogo.jogo[19] = "...a.....a..aaa....aaa..".toCharArray();
    }

    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld){
        super.onSizeChanged(xNew, yNew, xOld, yOld);
        init();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //desenha_canvas()
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);

        // menor/8 - 1.75f
        canvas.drawCircle(Jogo.largura/2, Jogo.altura/2, Jogo.menor/8, paint);


        for(int lin = Jogo.jogo_h - 1; lin >= 0; lin--) {
            for(int i = 0; i < Jogo.quantas; i++) {
                char bloco = Jogo.jogo[lin][i];
                if(bloco == '.') continue;

                float raio = Jogo.menor/8 + (Jogo.grossura/2) + (Jogo.jogo_h - lin - 1)*Jogo.grossura;


                paint.setColor(Color.parseColor(Jogo.cores[bloco]));
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(Jogo.grossura - 1.25f);

                canvas.drawArc(Jogo.raio2rect(raio), i*Jogo.tamanho, Jogo.tamanho, false, paint);
            }
        }
    }
}