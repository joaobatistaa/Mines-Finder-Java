import java.io.Serializable;
import java.util.ArrayList;

public class TabelaRecordes implements Serializable {
    private String nome;
    private long tempo;
    private transient ArrayList<TabelaRecordesListener> listeners;

    public TabelaRecordes() {
        this.nome = "Anónimo";
        this.tempo = 9999999;
        listeners=new ArrayList<>();
    }

    public void addTabelaRecordesListener(TabelaRecordesListener list) {
        if (listeners == null) listeners = new ArrayList<>();
        listeners.add(list);
    }
    public void removeTabelaRecordesListener(TabelaRecordesListener list) {
        if (listeners != null) {
            listeners.remove(list);
        }
    }

    private void notifyRecordesActualizados() {
        if (listeners != null) {
            for (TabelaRecordesListener list : listeners)
                list.recordesActualizados(this);
        }
    }

    public String getNome() {
        return nome;
    }

    public long getTempo() {
        return tempo;
    }

    public void setRecorde(String nome, long novoTempo) {
        if (novoTempo < this.tempo) {
            this.nome = nome;
            this.tempo = novoTempo;
            notifyRecordesActualizados();
        }
    }
}
