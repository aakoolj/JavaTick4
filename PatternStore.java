package uk.ac.cam.ajc318.prejava.tick4;

import java.io.*;
import java.net.*;
import java.util.*;

public class PatternStore {
	
	private List<Pattern> mPatterns = new LinkedList<>();
	private Map<String,List<Pattern>> mMapAuths = new HashMap<>();
	private Map<String,Pattern> mMapName = new HashMap<>();

   public PatternStore(String source) throws IOException { //decides whether it is a URL or not
       if (source.startsWith("http://")) {
          loadFromURL(source); // then loads appropriate method
       }
       else {
          loadFromDisk(source);
       }
   }
   
    
   public PatternStore(Reader source) throws IOException {
      load(source);
   }
    
   private void load(Reader r) throws IOException {
      // TODO: read each line from the reader and print it to the screen    
	   BufferedReader br = new BufferedReader(r);
	   String currentLine;
	   Pattern currentPattern;
	   
	   while ((currentLine = br.readLine()) != null) { 
		 	System.out.println(currentLine); 	
		 	
		 	try {
				currentPattern = new Pattern(currentLine);
			} catch (PatternFormatException e) {
				System.out.println("Malformed pattern line: " + currentLine);
				continue;
			}
		 	
		 	mPatterns.add(currentPattern);
		 	
		 	for (int i = 0; i < mPatterns.size(); i++) {
		 		if (currentPattern.getAuthor().equals(mPatterns.get(i).getAuthor())) {
		 			mMapAuths.put(currentPattern.getAuthor(), mPatterns); // will always add the one just added
		 		}
		 	}
		 	
		 	mMapName.put(currentPattern.getName(), currentPattern);
		 	
	   }
   }
    
    
   private void loadFromURL(String url) throws IOException {
    // TODO: Create a Reader for the URL and then call load on it
	   URL destination = new URL(url);
	   URLConnection conn = destination.openConnection();
	   
	   Reader r = new java.io.InputStreamReader(conn.getInputStream());
	   load(r);
	   
   }

   private void loadFromDisk(String filename) throws IOException {
    // TODO: Create a Reader for the file and then call load on it
	   Reader r = new FileReader(filename);
	   load(r);
   }

   public static void main(String args[]) throws IOException  {
      PatternStore p =
       new PatternStore(args[0]);
      
      try {
    	  List<Pattern> test1 = p.getPatternsAuthorSorted();
    	  List<Pattern> test2 = p.getPatternsNameSorted();
    	  List<Pattern> test3 = p.getPatternsByAuthor("life lexicon");
    	  Pattern test4 = p.getPatternByName("alfie");
    	  List<String> test5 = p.getPatternAuthors();
    	  List<String> test6 = p.getPatternNames();
    	  System.out.println();
      	
      } catch (PatternNotFound e) {
    	  System.out.println(e.getMessage());
      }
      
      
    
   }
   
   public List<Pattern> getPatternsNameSorted() { //CORRECT
	   // TODO: Get a list of all patterns sorted by name
	   
	   Collections.sort(mPatterns);
	   
	   List<Pattern> copy = new LinkedList<>(mPatterns);
	   return copy;  
	}

	public List<Pattern> getPatternsAuthorSorted() { //CORRECT
	   // TODO: Get a list of all patterns sorted by author then name
		Collections.sort(mPatterns, new Comparator<Pattern>() {
			   public int compare(Pattern p1, Pattern p2) {
			      return (p1.getAuthor()).compareTo(p2.getAuthor());
			   }
			 }
		);
		
		List<Pattern> copy = new LinkedList<>(mPatterns);
		return copy;
	}

	public List<Pattern> getPatternsByAuthor(String author) throws PatternNotFound { 
	   // TODO:  return a list of patterns from a particular author sorted by name
		List<Pattern> patternsForAuthor = new LinkedList<>();
		
		for (int i = 0; i < mPatterns.size(); i++) {
			if (mPatterns.get(i).getAuthor().equals(author)) {
				patternsForAuthor.add(mPatterns.get(i));
			}
		}
		
		Collections.sort(patternsForAuthor);
		return patternsForAuthor;
			
	}

	public Pattern getPatternByName(String name) throws PatternNotFound { //CORRECT
	   // TODO: Get a particular pattern by name
		Pattern returnPattern;
		
		returnPattern = mMapName.get(name);
		if (returnPattern == null) {
			throw new PatternNotFound("Pattern not found with name: "+name);
		}
			
		return returnPattern;
	}

	public List<String> getPatternAuthors() { //CORRECT
	   // TODO: Get a sorted list of all pattern authors in the store
		List<String> authors = new LinkedList<>();
		Set<String> noDupes = new TreeSet<>();
		
		for (int i = 0; i < mPatterns.size(); i++) {
			noDupes.add((mPatterns.get(i)).getAuthor());
		}
		authors.addAll(noDupes);
		Collections.sort(authors);
		return authors;
	}

	public List<String> getPatternNames() { //CORRECT
	   // TODO: Get a list of all pattern names in the store,
	   // sorted by name
		List<String> names = new LinkedList<>();
		
		Collections.sort(mPatterns, new Comparator<Pattern>() {
			   public int compare(Pattern p1, Pattern p2) {
			      return (p1.getName()).compareTo(p2.getName());
			   }
			 });
		
		for (int i = 0; i < mPatterns.size(); i++) {
			names.add(i, mPatterns.get(i).getName());
		}
		
		return names;
	}
}
