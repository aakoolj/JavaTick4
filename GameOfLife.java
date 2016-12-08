package uk.ac.cam.ajc318.prejava.tick4;

import java.io.*;
import java.util.*;

public class GameOfLife {

	private World mWorld;
	private PatternStore mStore;
	
	private ArrayList<World> mCachedWorlds = new ArrayList<>();
	   
    public GameOfLife(World w) {
        mWorld = w; 
    }

   public GameOfLife(PatternStore ps) {
		mStore = ps;
	}
   
   // just add new instance here for new type of world, and add copy constructor in that class
   private World copyWorld(boolean useCloning) throws CloneNotSupportedException {
	   
	   if (useCloning == false) {
	   		if ( mWorld instanceof ArrayWorld) { // if mWorld (current) is an ArrayWorld
	   			World copy;
	   			copy = new ArrayWorld((ArrayWorld)mWorld);
	   			return (World)copy;
	   		}
	   		else if ( mWorld instanceof PackedWorld) {
	   			World copy = new PackedWorld((PackedWorld)mWorld);
	   			return (World)copy;
	   		}
   	 	}
	   else {
		    World copy = (World) mWorld.clone();
		   	return copy;
	   }
	   
	   return null;
	}

   
   public void play() throws IOException, PatternFormatException, CloneNotSupportedException {
        
        String response="";
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                
        System.out.println("Please select a pattern to play (l to list:)");
        while (!response.equals("q")) {
                response = in.readLine();
                System.out.println(response);
                if (response.equals("f")) {
                        if (mWorld==null) System.out.println("Please select a pattern to play (l to list):");
                        else {	
                        	if (mWorld.getGenerationCount() + 1 >= mCachedWorlds.size()) {
                        		mWorld = copyWorld(true);
                        		mWorld.nextGeneration();
                        		
                        		//mWorld.nextGeneration(); //advance world to next gen
                        		mCachedWorlds.add(copyWorld(true)); //store a copy of current world
                        		mWorld = mCachedWorlds.get(mWorld.getGenerationCount()); 
                        	}
                        	else {
                        		mWorld = mCachedWorlds.get(mWorld.getGenerationCount() + 1);
                        	}
                            print();
                        	
                        }
                }
                else if (response.equals("b")) {
                	if (mWorld==null) System.out.println("Please select a pattern to play (l to list):");
                    else {
                    	if (mWorld.getGenerationCount() != 0) {
                    		World hold = mCachedWorlds.get(mWorld.getGenerationCount() - 1);
                    		mWorld = hold;
                    	}
                	print();
                    }
                }
                else if (response.equals("l")) {
                        List<Pattern> names = mStore.getPatternsNameSorted();
                        int i=0;
                        for (Pattern p : names) {
                                System.out.println(i+" "+p.getName()+"  ("+p.getAuthor()+")");
                                i++;
                        }
                }
                else if (response.startsWith("p")) {
                   List<Pattern> names = mStore.getPatternsNameSorted();
                   // TODO: Extract the integer after the p in response
                   String patNo = response.substring(2);
                   int patternNo = Integer.parseInt(patNo);
                   
                   
                   // TODO: Get the associated pattern
                   Pattern patternToPlay = names.get(patternNo);
                   
                   /*//THIS IS A TEST FIXED NOW
                   mWorld = new PackedWorld(patternToPlay);
                   World copyclone = copyWorld(true);
                   World copycons = copyWorld(false);
                   
                   for (int row = 0; row < mWorld.getHeight(); row++) {
                	   for (int col = 0; col < mWorld.getWidth(); col++) {
                		   boolean checkBit = mWorld.getCell(col, row);
                		   boolean checkclone = copyclone.getCell(col, row);
                		   boolean checkcons = copycons.getCell(col,  row);
                		   if (checkclone != checkBit) {
                			   System.out.println("Error copy at "+col+row);
                		   }
                		   if (checkcons != checkBit) {
                			   System.out.println("Error cons at "+col+row);
                		   }
           			   }
                   }
                   System.out.println("-" + mWorld.getGenerationCount());
               		*/
           
           			
                   
                   
                   
                   // TODO: Initialise mWorld using PackedWorld or ArrayWorld based
                   //       on pattern world size
                   if ( (patternToPlay.getHeight() * patternToPlay.getWidth()) < 65) {
                	   mWorld = new PackedWorld(patternToPlay);
                   }
                   else {
                	   mWorld = new ArrayWorld(patternToPlay);
                	   
                   }
                   
                   //ADDED THIS WHEN INITIALIZING IT STORES FIRST GENERATION
                   mCachedWorlds.add(mWorld);
                   mWorld = mCachedWorlds.get(mWorld.getGenerationCount());
                   
                   
                   print();
                }
                
        }
    }
    
    public void print() {
    	System.out.println("-" + mWorld.getGenerationCount());
    	
		for (int row = 0; row < mWorld.getHeight(); row++) {
			for (int col = 0; col < mWorld.getWidth(); col++) {
				boolean checkBit = mWorld.getCell(col, row);
				if (checkBit) {
					System.out.print("# ");
				} 
				else {
					System.out.print("_ ");
				}
			}
			System.out.println("");
		}
    }

 public static void main(String args[]) throws IOException, PatternFormatException, CloneNotSupportedException {
	 
	 	//PackedWorld w = new PackedWorld(args[0]); // this has created a new array world, same reference pattern
	 	//PackedWorld wcopy = new PackedWorld(w);	// which is ok as immutable, however same ref to mWorld X
	 	
	 	//wcopy.setCell(7, 7, true);
	 	

        if (args.length!=1) {
                System.out.println("Usage: java GameOfLife <path/url to store>");
                return;
        }
        
        try {
                PatternStore ps = new PatternStore(args[0]);
                GameOfLife gol = new GameOfLife(ps);    
                gol.play();
        }
        catch (IOException ioe) {
                System.out.println("Failed to load pattern store");
        }
        
        
    }


}
