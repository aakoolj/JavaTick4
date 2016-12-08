package uk.ac.cam.ajc318.prejava.tick4;

public abstract class World implements Cloneable {
	
	private int mGeneration;
	private Pattern mPattern;
	
	public World(Pattern pattern) throws PatternFormatException {
		mPattern = pattern;
		mGeneration = 0;
	}
	
	public static void main(String[] args) {

	}
	
	public World(World w) {
		mGeneration = w.mGeneration;
		mPattern = w.mPattern;
	}
	
	public World(String format) throws PatternFormatException {
		mPattern = new Pattern(format);
		mGeneration = 0;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		World cloned = (World)super.clone();
		cloned.mGeneration = mGeneration;
		cloned.mPattern = mPattern;
		return cloned;
	}
	
	
	protected void incrementGenerationCount() {
		mGeneration++;
	}
	
	public int getWidth() {
		return (mPattern.getWidth());
	}
	
	public int getHeight() {
		return (mPattern.getHeight());
	}
	
	public int getGenerationCount() {
		return (mGeneration);
	}
	
	protected Pattern getPattern() {
		return (mPattern);
	}
	
	public void nextGeneration() {
        nextGenerationImpl();
        mGeneration++;
	}
	
	protected abstract void nextGenerationImpl();
	
	public abstract void setCell(int c, int r, boolean val);
	
	public abstract boolean getCell(int c, int r); 
	// changed from int to boolean
	 
	protected int countNeighbours(int col, int row) {
		boolean checkBit = false;
   		int noNeighbours = 0;
   		
   		for (int y = row - 1; y < row + 2; y++) {
			for (int x = col - 1; x < col + 2; x++) {
				checkBit = getCell(x, y);
				if (checkBit == true) {
					noNeighbours++;
				}
			}
		}
   		if (getCell(col, row) == true) {
   			noNeighbours--;
   		}
   		return noNeighbours;
	}
	
	protected boolean computeCell(int col, int row) {
		// liveCell is true if the cell at position (col,row) in world is live
   		boolean liveCell = getCell(col, row);
   		
   		// neighbours is the number of live neighbours to cell (col,row)
  		int neighbours = countNeighbours(col, row);
  		
  		// we will return this value at the end of the method to indicate whether 
   		// cell (col,row) should be live in the next generation
   		boolean nextCell = false;
    
   		//A live cell with less than two neighbours dies (underpopulation)
   		if (neighbours < 2) {
      		nextCell = false;
  		}
 
   		//A live cell with two or three neighbours lives (a balanced population)
   		//TODO: write a if statement to check neighbours and update nextCell
   		if (  ( (neighbours > 1) && (neighbours < 4) ) && (liveCell == true) ) {
   			nextCell = true;
   		}

   		//A live cell with with more than three neighbours dies (overcrowding)
  		//TODO: write a if statement to check neighbours and update nextCell
  		if ( (neighbours > 3) && (liveCell == true) ) {
  			nextCell = false;
  		}

   		//A dead cell with exactly three live neighbours comes alive
   		//TODO: write a if statement to check neighbours and update nextCell
   		if ( (neighbours == 3) && (liveCell == false) ) {
   			nextCell = true;
   		}
   	
    	
   		return nextCell;
	}
}
