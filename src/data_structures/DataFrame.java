package data_structures;


import java.util.ArrayList;
import java.util.List;

public class DataFrame {
	String[] index;
	String[] label;
	ArrayList<Frame> frames;
	
	
	public DataFrame(String... tabs) {
		frames = new ArrayList<Frame>();
		for (String arg : tabs) {
			frames.add(new Frame(arg));
			
		}
	}
}
