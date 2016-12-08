package uk.ac.cam.ajc318.prejava.tick4;

public class Pattern implements Comparable<Pattern>{
	private String mName;
    private String mAuthor;
    private int mWidth;
    private int mHeight;
    private int mStartCol;
    private int mStartRow;
    private String mCells;
    private String[] mLiveCells;	//***had to make this to an array []***

    public String getName() {
       return mName;
    }
    public String getAuthor() {
    	return mAuthor;
    }
    public int getWidth() {
    	return mWidth;
    }
    public int getHeight() {
    	return mHeight;
    }
    public int getStartCol(){
    	return mStartCol;
    }
    public int getStartRow(){
    	return mStartRow;
    }
    public String getCells(){  
    	return mCells;
    }
    

    public Pattern(String format) throws PatternFormatException {
    	if (format == null) {
    		throw new PatternFormatException("Please specify a pattern.");
    	}
    	String[] data = format.split(":");
    
    	if (data.length < 7) {
    		throw new PatternFormatException
    			("Invalid pattern format: Incorrect number of fields in pattern (found "+data.length+").");
    	}
    	
    	mName = data[0];
    	mAuthor = data[1];
		
    	try {
        mWidth = Integer.parseInt(data[2]);
    	} catch (NumberFormatException e3) {
    		throw new PatternFormatException("Invalid pattern format: Could not interpret the width field as a number ('"+data[2]+"' given).");
    	}
        mHeight = Integer.parseInt(data[3]);
       
        try {
        mStartCol = Integer.parseInt(data[4]);
        } catch (NumberFormatException e4) {
        	throw new PatternFormatException("Invalid pattern format: Could not interpret the startX field as a number ('"+data[4]+"' found).");
        }
    	mStartRow = Integer.parseInt(data[5]);
    	
    	mCells = data[6];
    	mLiveCells = data[6].split(" ");
    	
    	
    	
    }

    public void initialise(World world) throws PatternFormatException {
    	for (int y = 0; y < mHeight; ++y) {      // starting world as all dead
    		for (int x = 0; x < mWidth; ++x) {
    			world.setCell(x,y,false);
    		}
    	}

    	for (int y = 0; y < mLiveCells.length; y++) {  //only works for a square array of cells
    		char[] rowCoords = mLiveCells[y].toCharArray();
    		
    		for (int x = 0; x < rowCoords.length; x++) {
    			
    			if (Character.getNumericValue(rowCoords[x]) == (int)1 ) {
    				world.setCell(x + getStartCol(),y + getStartRow(),true);
    			}
    		}
    	}
    }
	@Override
	public int compareTo(Pattern p) {
		// TODO Auto-generated method stub
		return mName.compareTo(p.getName());
		
	}
}
 	
