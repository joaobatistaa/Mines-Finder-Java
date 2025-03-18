import javax.swing.*;
import java.awt.*;

public class BotaoCampoMinado extends JButton {

    private int estado;
    private int linha;
    private int coluna;

    public BotaoCampoMinado(int linha, int coluna) {
        this.estado = CampoMinado.TAPADO;
        this.linha = linha;
        this.coluna = coluna;
    }

    // tapada - sem texto - sem fundo
    // assinalada como duvidosa - "?" - fundo amarelo
    // assinalada como com mina - "!" - fundo vermelho
    // destapada sem mina e sem minas a volta - sem texto - fundo cinzento escuro
    // destapada sem mina e com minas a volta - numero de minas a volta (1-8) - fundo cinzento escuro
    // destapada com mina - "*" - fundo cor laranja

    public void setEstado(int estado) {
        this.estado = estado;
        switch (estado) {
            case CampoMinado.VAZIO:
                setText("");
                setBackground(Color.LIGHT_GRAY);
                break;
            case CampoMinado.TAPADO:
                setText("");
                setBackground(null);
                break;
            case CampoMinado.DUVIDA:
                setText("?");
                setBackground(Color.YELLOW);
                break;
            case CampoMinado.REBENTADO:
                setText("!");
                setBackground(Color.RED);
                break;
            case CampoMinado.MARCADO:
                setText("*");
                setBackground(Color.ORANGE);
                break;
            default:
                setText(String.valueOf(estado));
                setBackground(Color.LIGHT_GRAY);
        }
    }

    public int getLinha() {
        return linha;
    }


    public int getColuna() {
        return coluna;
    }
}
