
public class SensoryInput {

    public double[][] vision = new double[3][3];//need to change variable type becasue of comparison


    public SensoryInput(int x, int y, GameObject[][] map)
    {
        int[] direction = map[x][y].getDirection();//gets directional vector
        Creature myCreature = (Creature) map[x][y];

        int rotations = 0;//counter for rotations
        int[] vect = {0, 1};//declares initial vector as north and then rotates counter clockwise


        while(vect != direction)//rotate the vector until it is in the right direction and count how mnay rotaions needed
        {
            vect = new int[]{-vect[1], vect[0]};
            rotations++;
        }

        for (int i=-1; i<2; i++){
            for(int j=0; j<3; j++){
                int[] delta={i, j};//declares a new sample vector based on the current position in the array
                for(int k=0; k<rotations; k++){
                    delta=new int[]{-vect[1], vect[0]};
                }
                if(x+delta[0] < map.length && x+delta[0] >=0 && y+delta[1]<map[0].length && y+delta[1]>=0){
                    GameObject obj = map[x+delta[0]][y+delta[1]];
                    if(obj==null){
                        vision[i+1][j]=0;
                    } else if((i|j)==0){
                        vision[i+1][j]=obj.getHealth();//get health of creature
                    } else {
                        double similarity = obj.getSimilarity(myCreature) + 1;
                        vision[i+1][j] = similarity;
                    }
                }
                else{
                    vision[i+1][j]=3;
                }
            }
        }
    }
}

//3x3 matrix to see whats in front
// -1 for food, 0 for empty, 1-2 for creaturs based on simularity
//take in corrdinates xy and board
//check the board for positions 2 infront and one on the side to fill the matrix
//int based on direction

//recive the health of the creature
//0,1 north, 0,-1 south, 1,0 east, -1,0 west
// get direction from gameObject
//3 is edge 
//check if its out of bounds
