package uk.ac.cam.ajc318.prejava.tick4;

public class ArrayWorld extends World implements Cloneable {

    private boolean[][] mWorld;
    private boolean[] mDeadRow;
    
    public ArrayWorld(String serial) throws PatternFormatException {
    	super(serial); // creates mPattern and mGeneration = 0
    	
    	// TODO: initialise mWorld
    	mWorld = new boolean[getHeight()][getWidth()];
    	getPattern().initialise(this); //can't access mPattern have to use getPattern()    	
    	
    	
    }
    

    public ArrayWorld(ArrayWorld w) {
    	super(w);
    	 // mWorld = w.mWorld - reference doesn't work so gonna go through and copy each element
    	
    	boolean[][] temp = new boolean[w.getHeight()][w.getWidth()];
    	for (int y = 0; y < w.mWorld.length; y++) {
    		for (int x = 0; x < w.mWorld[y].length; x++) {
    			temp[y][x] = w.mWorld[y][x];
    		}
    	}
    	
    	mWorld = temp;
    	mDeadRow = w.mDeadRow; // copies reference
    }
    
    
    public ArrayWorld(Pattern pattern) throws PatternFormatException {
    	super(pattern);
    	mDeadRow = new boolean[getWidth()];
    	
    	mWorld = new boolean[getHeight()][getWidth()];
    	getPattern().initialise(this);
    	
    	for (int y = 0; y < getHeight(); y++) {
    		boolean liveRow = false;
    		for (int x = 0; x < getWidth(); x++) {
    			if (getCell(x, y) == true) {
    				liveRow = true;
    			}
    		}
    		if (liveRow == false) {
    			mWorld[y] = mDeadRow;
    		}
    		System.out.println("blah");
    	}
    	
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
    	ArrayWorld cloned = (ArrayWorld) super.clone();
  
    	boolean[][] temp = new boolean[mWorld.length][mWorld[1].length];
    	
    	/*
    	for (int i = 0; i < mWorld.length; i++) {
    		if (mWorld[i] == mDeadRow) {
    			temp[i] = mDeadRow;
    		}
    		else {
    			temp[i] = mWorld[i].clone();
    		}
    	}
    	cloned.mWorld = temp;
    	*/
    	
    	for (int y = 0; y < mWorld.length; y++) {		//deep clones mWorld
    		for (int x = 0; x < mWorld[y].length; x++) {
    			temp[y][x] = mWorld[y][x];
    		}
    	}
    	cloned.mWorld = temp;
    	for (int y = 0; y < temp.length; y++) {
    		boolean liveRow = false;
    		for (int x = 0; x < temp[y].length; x++) {
    			boolean test = getCell(x,y);
    			if (getCell(x, y) == true) {
    				liveRow = true;
    			}
    		}
    		if (liveRow == false) {
    			cloned.mWorld[y] = mDeadRow;
    		}
    	} 
    	
    	
    	return cloned;
    }

	@Override
	public void nextGenerationImpl() {
		boolean[][] nextGeneration = new boolean[mWorld.length][];
		
        for (int y = 0; y < mWorld.length; ++y) {
            nextGeneration[y] = new boolean[mWorld[y].length];
            
            for (int x = 0; x < mWorld[y].length; ++x) {
                boolean nextCell = computeCell(x, y);
                nextGeneration[y][x]=nextCell;
            }
        }
        mWorld = nextGeneration;
	}

	@Override
	public void setCell(int col, int row, boolean val) {
		mWorld[row][col] = val;
	}

	@Override
	public boolean getCell(int col, int row) {
		if (row < 0 || row >= getHeight()) return false;
		if (col < 0 || col >= getWidth()) return false;
		
		return mWorld[row][col];
	}
	
}