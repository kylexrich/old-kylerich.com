import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;
import java.util.*;

public class filedeleter {
    public ArrayList<String> safeFileNames = new ArrayList<String>();
    
    public void run(){
         FileResource safeFiles = new FileResource("files/safefiles.txt");
         DirectoryResource dir = new DirectoryResource();
         loadSafeFiles(safeFiles);
         
         deleteFiles(dir);
    }
    
    public void loadSafeFiles(FileResource fr){
        for(String line: fr.lines()){
            safeFileNames.add(line);
        }
    }
    
    public void deleteFiles(DirectoryResource dir){
        for(File f : dir.selectedFiles()){
            if(!safeFileNames.contains(f.getName())){
                f.delete();
            }
        }
    }
}
