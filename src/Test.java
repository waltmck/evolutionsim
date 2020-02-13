import java.util.*;
import java.io.*;

public class Test{
    public static void main(String[] args) throws FileNotFoundException {
        Simulation s = new Simulation();
        int num = 0;
        Scanner console = new Scanner(System.in);
        System.out.print("output file name? ");
        PrintStream output = new PrintStream(new File(console.nextLine()));
        while(true){
            s.update();
            if(num%1000000==0){
                System.out.println();
                drawMap(s.getMap());
                System.out.println("\n\n\n"+s.numCreatures);
            }
            else if(num%1000==0){
                output.println(s.numCreatures + " ");
                if (num%20000 == 0) {
                    System.out.println(num);
                }
            }
            num++;
        }
    }

    private static void drawMap(GameObject[][] map){
        for(int y=(int) Math.log10(map[0].length); y>=0; y--){
            for(int i=0; i<Math.log10(map.length)+1; i++){
                System.out.print(" ");
            }
            for(int x=0; x<map[0].length; x++){
                if(y>Math.log10(x) && (x!=0 || y!=0)){
                    System.out.print(" ");
                } else{
                    System.out.print(x/((int)Math.pow(10, y)) % 10);
                }

            }
            System.out.println();
        }
        for(int y=map.length-1; y>=0; y--){
            int numSpaces = (int) Math.log10(map.length) - (y!=0 ? (int) Math.log10(y) : 0);
            for(int i=0; i<numSpaces; i++){
                System.out.print(" ");
            }
            System.out.print("" + y + " ");

            for(int x=0; x<map[y].length; x++){
                GameObject g = map[x][y];
                if(g==null){
                    System.out.print(" ");
                } else {
                    System.out.print(g.getAppearance().textAppearance);
                }
            }
            System.out.println(" " + y);
        }
    }


}