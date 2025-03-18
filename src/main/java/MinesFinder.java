import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MinesFinder extends JFrame {
    private JPanel painelPrincipal;
    private JLabel title_label;
    private JPanel jp_recordes;
    private JPanel jp_grid;
    private JLabel lbl_title;
    private JLabel lbl_level_type_easy;
    private JLabel lbl_player_name_easy;
    private JLabel lbl_level_type_hard;
    private JLabel lbl_level_type_medium;
    private JLabel lbl_player_name_medium;
    private JLabel lbl_player_name_hard;
    private JButton btn_play_hard_game;
    private JButton btn_play_medium_game;
    private JButton btn_exit;
    private JButton btn_play_easy_game;
    private JLabel lbl_player_time_medium;
    private JLabel lbl_player_time_hard;
    private JLabel lbl_player_time_easy;

    private TabelaRecordes recordesFacil;
    private TabelaRecordes recordesMedio;
    private TabelaRecordes recordesDificil;

    public MinesFinder(String title) {
        super(title);

        this.recordesFacil = new TabelaRecordes();
        this.recordesMedio = new TabelaRecordes();
        this.recordesDificil = new TabelaRecordes();
        lerRecordesDoDisco();

        lbl_player_name_easy.setText(recordesFacil.getNome());
        lbl_player_time_easy.setText(Long.toString(recordesFacil.getTempo()/1000));
        lbl_player_name_medium.setText(recordesMedio.getNome());
        lbl_player_time_medium.setText(Long.toString(recordesMedio.getTempo()/1000));
        lbl_player_name_hard.setText(recordesDificil.getNome());
        lbl_player_time_hard.setText(Long.toString(recordesDificil.getTempo()/1000));

        recordesFacil.addTabelaRecordesListener(new TabelaRecordesListener() {
            @Override
            public void recordesActualizados(TabelaRecordes recordes) {
                recordesFacilActualizado(recordes);
            }
        });

        recordesMedio.addTabelaRecordesListener(new TabelaRecordesListener() {
            @Override
            public void recordesActualizados(TabelaRecordes recordes) {
                recordesMedioActualizado(recordes);
            }
        });

        recordesDificil.addTabelaRecordesListener(new TabelaRecordesListener() {
            @Override
            public void recordesActualizados(TabelaRecordes recordes) {
                recordesDificilActualizado(recordes);
            }
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(painelPrincipal);

        // Causes this Window to be sized to fit the preferred size and layouts of its subcomponents.
        pack();

        setLocationRelativeTo(null);

        btn_exit.addActionListener(this::btnExitActionPerformed);

        btn_play_easy_game.addActionListener(this::btnPlayEasyActionPerformed);
        btn_play_medium_game.addActionListener(this::btnPlayMediumActionPerformed);
        btn_play_hard_game.addActionListener(this::btnPlayHardActionPerformed);
    }

    public static void main(String[] args) {
        new MinesFinder("Mines Finder").setVisible(true);
    }

    private void btnExitActionPerformed(ActionEvent e) {
        System.exit(0);
    }

    private void btnPlayEasyActionPerformed(ActionEvent e) {
        var janela = new JanelaDeJogo(new CampoMinado(9,9, 5), recordesFacil);
        janela.setVisible(true);
    }

    private void btnPlayMediumActionPerformed(ActionEvent e) {
        var janela = new JanelaDeJogo(new CampoMinado(9,9, 10), recordesMedio);
        janela.setVisible(true);
    }

    private void btnPlayHardActionPerformed(ActionEvent e) {
        var janela = new JanelaDeJogo(new CampoMinado(16,30, 31), recordesDificil);
        janela.setVisible(true);
    }

    private void recordesFacilActualizado(TabelaRecordes recordes) {
        lbl_player_name_easy.setText(recordes.getNome());
        lbl_player_time_easy.setText(Long.toString(recordes.getTempo()/1000));
        guardarRecordesDisco();
    }
    private void recordesMedioActualizado(TabelaRecordes recordes) {
        lbl_player_name_medium.setText(recordes.getNome());
        lbl_player_time_medium.setText(Long.toString(recordes.getTempo()/1000));
        guardarRecordesDisco();
    }
    private void recordesDificilActualizado(TabelaRecordes recordes) {
        lbl_player_name_hard.setText(recordes.getNome());
        lbl_player_time_hard.setText(Long.toString(recordes.getTempo()/1000));
        guardarRecordesDisco();
    }

    private void guardarRecordesDisco() {
        ObjectOutputStream oos = null;
        try {
            File f  =new
                    File(System.getProperty("user.home")+File.separator+"minesfinder.recordes");
            oos = new ObjectOutputStream(new FileOutputStream(f));
            oos.writeObject(recordesFacil);
            oos.writeObject(recordesMedio);
            oos.writeObject(recordesDificil);
            oos.close();
        } catch (IOException ex) {
            Logger.getLogger(MinesFinder.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
    }

    private void lerRecordesDoDisco() {
        ObjectInputStream ois = null;
        File f = new
                File(System.getProperty("user.home")+File.separator+"minesfinder.recordes");
        if (f.canRead()) {
            try {
                ois = new ObjectInputStream(new FileInputStream(f));
                recordesFacil=(TabelaRecordes) ois.readObject();
                recordesMedio=(TabelaRecordes) ois.readObject();
                recordesDificil=(TabelaRecordes) ois.readObject();
                ois.close();
            } catch (IOException ex) {
                Logger.getLogger(MinesFinder.class.getName()).log(Level.SEVERE,
                        null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MinesFinder.class.getName()).log(Level.SEVERE,
                        null, ex);
            }
        }
    }
} 