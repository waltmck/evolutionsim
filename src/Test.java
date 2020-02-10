import java.util.concurrent.TimeUnit;

public class Test{
    public static void main(String[] args){
        Simulation s = new Simulation();

        while(true){
            s.update();
            drawMap(s.getMap());
        }
    }

    private static void drawMap(GameObject[][] map){
        for(int y=map.length-1; y>=0; y--){
            for(int x=0; x<map[y].length; x++){
                GameObject g = map[x][y];
                if(g==null){
                    System.out.print(" ");
                } else if(g instanceof Creature){
                    System.out.print("C");
                } else if(g instanceof Food){
                    System.out.print("F");
                }
            }
            System.out.println();
        }
    }
}