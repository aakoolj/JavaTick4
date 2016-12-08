package uk.ac.cam.ajc318.prejava.tick4;

public class PackedWorld extends World implements Cloneable {
	
	private long mWorld;
	
	public PackedWorld(String serial) throws PatternFormatException {
		super(serial); // creates mPattern and mGeneration = 0
		
		if ( (getWidth() * getHeight()) > 64 ) {
			throw new PatternFormatException("This does not fit in a long.");
		}
		
		mWorld = 0L;
		getPattern().initialise(this);
	}
	
	
	public PackedWorld(PackedWorld w) {
		super(w);
		// doesnt't work just copies reference - mWorld = w.mWorld;
		// HAVE TO GO THROUGH ALL OF w
		
		long newWorld = 0L;
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				boolean nextCell = w.getCell(col, row); // added w.
				int position = 8*row + col;
				
				if (nextCell) {
			      newWorld |= 1L<<position;
			    }
			    else {
			    	newWorld &= (~(1L<<position));
			    }
			}
		}
		mWorld = newWorld;
	}
	
	
	public PackedWorld(Pattern pattern) throws PatternFormatException {
		
		super(pattern);
		
		mWorld = 0L;
		pattern.initialise(this);
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		PackedWorld cloned = (PackedWorld)super.clone();
		cloned.mWorld = mWorld;
		return cloned;
	}

	@Override
	public void nextGenerationImpl() {
		long newWorld = 0L;
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				boolean nextCell = computeCell(col, row);
				int position = 8*row + col;
				//now setting the cell in newWorld
				if (nextCell)
			    {
			      // update the value "packed" with the bit at "position" set to 1
			      newWorld |= 1L<<position;
			    }
			    else
			    {
			      // update the value "packed" with the bit at "position" set to 0
			      newWorld &= (~(1L<<position));
			    }
			}
		}
		mWorld = newWorld;
	}

	@Override
	public void setCell(int col, int row, boolean val) {
		if ( (col > 7) || (col < 0) || (row > 7) || (row < 0) ) {
			System.out.println("Check values of col or row...");
		}	
		int position = 8*row + col;
		
		if (val)
	    {
	      // update the value "packed" with the bit at "position" set to 1
	      mWorld |= 1L<<position;
	    }
	    else
	    {
	      // update the value "packed" with the bit at "position" set to 0
	      mWorld &= (~(1L<<position));
	    }
	}


	@Override
	public boolean getCell(int col, int row) {
		if ( (col > 7) || (col < 0) || (row > 7) || (row < 0) ) {
			return false;
		}		
		
		int bitPosition = 8*row + col;
		long check;
		long cutBits = (mWorld >> bitPosition);
		
		if ( (cutBits & 1) > 0 ) {
			check = 1;
		}
		else {
			check = 0;
		}
		return (check == 1);
	}

}

