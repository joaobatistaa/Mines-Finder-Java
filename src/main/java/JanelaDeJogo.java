import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class JanelaDeJogo extends JFrame {
    private JPanel painelJogo; // painel do jogo. O nome é definido no modo Design, em "field name"
    private BotaoCampoMinado[][] botoes;
    private CampoMinado campoMinado;
    private TabelaRecordes recordes;

    public JanelaDeJogo(CampoMinado campo,  TabelaRecordes tabela) {
        this.campoMinado = campo;
        this.recordes = tabela;

        var nrLinhas = campoMinado.getNrLinhas();
        var nrColunas = campoMinado.getNrColunas();

        this.botoes = new BotaoCampoMinado[nrLinhas][nrColunas];

        painelJogo.setLayout(new GridLayout(nrLinhas, nrColunas));

        // Criar e adicionar os botões à janela
        for (int linha = 0; linha < nrLinhas; ++linha) {
            for (int coluna = 0; coluna < nrColunas; ++coluna) {
                botoes[linha][coluna] = new BotaoCampoMinado(linha, coluna);
                botoes[linha][coluna].addActionListener(this::btnCampoMinadoActionPerformed);
                botoes[linha][coluna].addMouseListener(mouseListener);
                painelJogo.add(botoes[linha][coluna]);
            }
        }

        setContentPane(painelJogo);

        // Destrói esta janela, removendo-a completamente da memória.
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Causes this Window to be sized to fit the preferred size and layouts of its subcomponents.
        pack();

        setLocationRelativeTo(null);

        setVisible(true);
    }

    public void btnCampoMinadoActionPerformed(ActionEvent e) {
        var botao = (BotaoCampoMinado) e.getSource();

        int x =  botao.getLinha();
        int y = botao.getColuna();

        campoMinado.revelarQuadricula(x, y);

        actualizarEstadoBotoes();

        if (campoMinado.isJogoTerminado()) {
            if (campoMinado.isJogadorDerrotado()) {
                JOptionPane.showMessageDialog(null, "Oh, rebentou uma mina",
                        "Perdeu!", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Parabéns. Conseguiu descobrir todas as minas em " +
                                (campoMinado.getDuracaoJogo() / 1000) + " segundos",
                        "Vitória", JOptionPane.INFORMATION_MESSAGE);

                boolean novoRecorde = campoMinado.getDuracaoJogo() < recordes.getTempo();

                if (novoRecorde) {
                    String nome = JOptionPane.showInputDialog("Introduza o seu nome");
                    recordes.setRecorde(nome, campoMinado.getDuracaoJogo());
                }
            }
            setVisible(false);
        }
    }

    private void actualizarEstadoBotoes() {
        for (int x = 0; x < campoMinado.getNrLinhas(); x++) {
            for (int y = 0; y < campoMinado.getNrColunas(); y++) {
                botoes[x][y].setEstado(campoMinado.getEstadoQuadricula(x, y));
            }
        }
    }

    MouseListener mouseListener=new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getButton() != MouseEvent.BUTTON3) {
                return;
            }

            var botao = (BotaoCampoMinado) e.getSource();

            var x = botao.getColuna();
            var y = botao.getLinha();

            var estadoQuadricula = campoMinado.getEstadoQuadricula(x, y);

            if (estadoQuadricula == CampoMinado.TAPADO) {
                campoMinado.marcarComoTendoMina(x, y);
            } else if (estadoQuadricula == CampoMinado.MARCADO) {
                campoMinado.marcarComoSuspeita(x, y);
            } else if (estadoQuadricula == CampoMinado.DUVIDA) {
                campoMinado.desmarcarQuadricula(x, y);
            }
            actualizarEstadoBotoes();

        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    };
} 