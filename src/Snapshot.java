import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Snapshot implements Serializable {
    public static final long serialVersionUID = 1;
    public List<Genes> genes;

    public Snapshot(Simulation s){
        genes = new ArrayList<Genes>();
        GameObject[][] map = s.getMap();
        for(int i=0; i<map.length; i++){
            for(int j=0; j<map[i].length; j++){
                GameObject o = map[i][j];
                if(o instanceof Creature){
                    genes.add(((Creature) o).getGenes());
                }
            }
        }
    }

    @Override
    public String toString(){
        String s = "";
        for(Genes g : genes){
            s += ("A: "+g.getAttack() + " D: " + g.getDefense() + " H: " + g.getHealth() + " R: " + g.getRegen() + "\n");
        }
        return s;
    }
}