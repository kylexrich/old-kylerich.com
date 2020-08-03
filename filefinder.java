import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;
import java.util.*;

public class filefinder {
    public ArrayList<String> extensions = new ArrayList<String>();
    public ArrayList<String> fileNames = new ArrayList<String>();
    public ArrayList<String> fileLocations = new ArrayList<String>();
    
    public void run(){
         FileResource extensions_file = new FileResource("files/extensions.txt");
         loadExtensions(extensions_file);
         FileResource file = new FileResource();
         String fileStr = file.asString();
         
         buildFileNames(fileStr);
         buildFileLocations(fileStr);
        
        
         while(fileLocations.size() != 0){
             for (String fileLocation : fileLocations) {
                FileResource newfile = new FileResource(fileLocation);
                String newfileStr = newfile.asString();
                buildFileNames(newfileStr);
                buildFileLocations(newfileStr);
                fileLocations.remove(fileLocation);
             }
        }
        
          
        for (String fileName : fileNames) {
            System.out.println(fileName);
        }
    }
    
    public void loadExtensions(FileResource fr){
        for(String line: fr.lines()){
            extensions.add(line);
        }
    }
    
    public void buildFileNames(String fileStr){
        int startIndex = 0;
        
        while(true){
            int endIndex = findEndIndex(fileStr, startIndex);
            startIndex = fileStr.lastIndexOf('/', endIndex - 1);
            
            if(!((startIndex < fileStr.length()) && (endIndex < fileStr.length()) && (startIndex != -1) && (endIndex != -1))){
                break;
            }
            
            String currName = fileStr.substring(startIndex + 1, endIndex);
            fileNames.add(currName);
            
            startIndex = endIndex + 1;
            
            if(!((startIndex < fileStr.length()) && (endIndex < fileStr.length()) && (startIndex != -1) && (endIndex != -1))){
                break;
            }
        }
    }
    
    public int findEndIndex(String fileStr, int startIndex){
        ArrayList<Integer> indices = new ArrayList<Integer>();
        
        for(String ext : extensions){
            int currIndex = fileStr.indexOf(ext, startIndex);
            if(currIndex != -1){
                currIndex ++;
                while(true){
                    char c = fileStr.charAt(currIndex);
                    if(!Character.isLetter(c)){
                        indices.add(currIndex);
                        break;
                    }
                    currIndex ++;
                }
            }
        }
        
        int minIndex = fileStr.length();
        for(int ind : indices){
            minIndex = Math.min(minIndex, ind);
        }
        
        return minIndex;
    }
    
    public void buildFileLocations(String fileStr){
        for(String fileName : fileNames){
            
            int fileNameIndex = fileStr.indexOf(fileName);
            int startIndex = fileStr.lastIndexOf("\"",fileNameIndex);
            int endIndex = fileStr.indexOf("\"", fileNameIndex);
            if(startIndex != -1 && endIndex != -1){
                String location = fileStr.substring(startIndex, endIndex + 1);
                fileLocations.add(location);
                try
                {
                  FileResource testfile = new FileResource(location);
                  String testfileStr = testfile.asString();  
                }
                catch(ResourceException FileResource)
                {
                  fileLocations.remove(location);  
                }
            } 
            
        }
    }
    
}
