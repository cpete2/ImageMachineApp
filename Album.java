import java.io.File;
import java.io.Serializable;
import java.util.*;
/**
 *
 * @author coryp_000
 */
public class Album implements Serializable{
    int currentIndex;
    File files[];
    Map<File, String> images;
    boolean isInThumbNailView;
    boolean wasAlreadySaved;
    String savedPath;
    
    public Album(File f []){
        currentIndex = 0;
        images = new HashMap<>();
        files = f;
        for (File f1 : files ){
            images.put(f1, null);
            
        }
        isInThumbNailView=false;
        wasAlreadySaved=false;
        savedPath=null;
    }
    
    public void setThumbNailView(boolean tf){
        isInThumbNailView=tf;
    }

    public Album() {
    }

    public Album(int currentIndex, File[] files, Map<File, String> images, boolean isInThumbNailView, boolean wasAlreadySaved) {
        this.currentIndex = currentIndex;
        this.files = files;
        this.images = images;
        this.isInThumbNailView = isInThumbNailView;
        this.wasAlreadySaved = wasAlreadySaved;
    }
    
    public void setSavedPath(String sp){
        savedPath=sp;
    }
    
    public String getSavedPath(){
        return savedPath;
    }
    public boolean getAlreadySaved(){
        return wasAlreadySaved;
    }
    
    public void setAlreadySaved(boolean tf){
        wasAlreadySaved=tf;
    }
    
    public boolean getThumbNailView(){
        return isInThumbNailView;
    }
    
    public String getStringFilePath(int i){
        return files[i].toString();
    }
    
    public int getNumOfFiles (){
        return files.length;
    }
    
    public void setCurrentIndex(int i){
        currentIndex=i;
    }
    
    public File getFile(int index){
        return files[index];
    }
    
    public int getCurrentIndex(){
        return currentIndex;
    }
    public void incCurrentIndex(){
        currentIndex++;
    }
    public void decCurrentIndex(){
        currentIndex--;
    }
    public void setImageCaption(String caption, File f){
        images.put(f, caption);
    }
    public String getImageCaption(File f){
        return images.get(f);
    }
    
    public int findIndexOfImage(String img){
        int index=-1;
        
        
        for (int i=0; i<files.length-1;i++){
          
            if(img.equals(files[i].toString())){
                index=i;
            }
            
        }
        
        return index;
    }
    
    public void printOutAlbum(){
        for (File file : files) {
            System.out.println(file.toString() + "\n");
        }
    }
}
