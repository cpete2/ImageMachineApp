//Main Frame Class
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.util.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.*;
import javax.swing.filechooser.FileNameExtensionFilter;



/**
 *
 * @author coryp_000
 */
public class MainFrame extends JFrame implements ActionListener
{
    private final JButton openButton;
    private final JButton saveButton;
    private final JButton openAlbumButton;
    private final JButton zoominButton;
    private final JButton zoomoutButton;
    private final JButton zoomFullButton;
    private final JButton quitButton;
    private final JButton nextButton;
    private final JButton previousButton;
    private final JButton thumbnailButton;
    private final JButton captionButton;
    private final JPanel mainPanel;
    private final JPanel bottomPanel;
    private final JPanel navPannel;
    private final JPanel captionPanel;
    private JTextArea captionsArea;
    private final JLabel jLabelfirst;
    private final JLabel jLabelsecond;
    private final JLabel jLabelthird;
    private final JLabel jLabelfourth;
    private final MainFrame.ImageDraw imagePanel;
    private BufferedImage bufferedImage; 
    private BufferedImage bufferedImage1; 
    private BufferedImage bufferedImage2;
    private BufferedImage bufferedImage3;
    private BufferedImage bufferedImage4; 
    private  Image img;
    private int imageFactor;
    private final int DEFAULT_ZOOM=250;
    private final int frameWidth;
    private File[] files;
    private Album curAlbum;
    private boolean thumbNailView;
    int thumImgW=80;
    int thumImgH=60;
    int thumTopRY=50;
    int thumBotRY=180;
    int x1 = 75;
    int x2 = 215;

   
    private JPanel test = new JPanel();
    
    public MainFrame(){
        //Build Screen Size according to user's screen
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        setSize(screenWidth/2, screenHeight/2);
       
        imageFactor=DEFAULT_ZOOM;
        thumbNailView = false;
        //Initialize all objects
        openButton = new JButton("Open");
        saveButton = new JButton("Save Album");
        openAlbumButton = new JButton("Open Album");
        zoominButton = new JButton("Zoom In");
        zoomoutButton = new JButton("Zoom Out");
        zoomFullButton = new JButton("Zoom 100%");
        quitButton = new JButton("Quit");
        nextButton = new JButton("Next");
        previousButton = new JButton("Previous");
        thumbnailButton = new JButton("Thumbnail");
        captionButton = new JButton("Add Caption");
        imagePanel= this.new ImageDraw();
        mainPanel = new JPanel(); 
        bottomPanel = new JPanel();
        navPannel = new JPanel();
        captionPanel = new JPanel();
        captionsArea = new JTextArea(15,15);
        captionsArea.setLineWrap(true);
        captionsArea.setWrapStyleWord(true);
        navPannel.setLayout( new BoxLayout( navPannel,BoxLayout.Y_AXIS));
        captionPanel.setLayout( new BoxLayout(captionPanel, BoxLayout.Y_AXIS));
        
        //Add buttons to main pannel
        mainPanel.add(openButton);
        mainPanel.add(saveButton);
        mainPanel.add(openAlbumButton);
        mainPanel.add(zoominButton);
        mainPanel.add(zoomoutButton);
        mainPanel.add(zoomFullButton);
        mainPanel.add(quitButton);
        navPannel.add(previousButton);
        bottomPanel.add(thumbnailButton);
        bottomPanel.add(captionButton);
        captionPanel.add(nextButton);
        captionPanel.add(captionsArea);
        frameWidth=this.getWidth();
        
        //Labels
        jLabelfirst = new JLabel();
        jLabelsecond = new JLabel();
        jLabelthird = new JLabel();
        jLabelfourth = new JLabel();
        jLabelfirst.setBounds(x1, thumTopRY, thumImgW, thumImgH);
        jLabelsecond.setBounds(x2, thumTopRY, thumImgW, thumImgH);
        jLabelthird.setBounds(x1, thumBotRY,thumImgW, thumImgH);
        jLabelfourth.setBounds(x2, thumBotRY, thumImgW, thumImgH);
        
        jLabelfirst.addMouseListener(new MouseAdapter(){
           public void mouseClicked(MouseEvent e){
               if(e.getClickCount()==1){
                   captionsArea.setText(curAlbum.getImageCaption(curAlbum.getFile(curAlbum.getCurrentIndex())));
                   
               }
               else if(e.getClickCount() ==2){
                   captionsArea.setText("");
                   thumbNailView=false;
                   displayImage();
                   //System.out.println("first");
               }
           }
        });
        jLabelsecond.addMouseListener(new MouseAdapter(){
           public void mouseClicked(MouseEvent e){
               if(e.getClickCount()==1){
                  captionsArea.setText(curAlbum.getImageCaption(curAlbum.getFile(curAlbum.getCurrentIndex()+1)));
                  
               }
               else if(e.getClickCount() ==2){
                   captionsArea.setText("");
                   curAlbum.setCurrentIndex(curAlbum.getCurrentIndex()+1);
                   thumbNailView= false;
                   displayImage();
                   //System.out.println("second");
               }
           }
        });
         jLabelthird.addMouseListener(new MouseAdapter(){
           public void mouseClicked(MouseEvent e){
               if(e.getClickCount()==1){
                   captionsArea.setText(curAlbum.getImageCaption(curAlbum.getFile(curAlbum.getCurrentIndex()+2)));
               }
               else if(e.getClickCount() ==2){
                   captionsArea.setText("");
                   curAlbum.setCurrentIndex(curAlbum.getCurrentIndex()+2);
                   thumbNailView= false;
                   displayImage();
                   //System.out.println("third");
               }
           }
        });
          jLabelfourth.addMouseListener(new MouseAdapter(){
           public void mouseClicked(MouseEvent e){
               if(e.getClickCount()==1){
                   captionsArea.setText(curAlbum.getImageCaption(curAlbum.getFile(curAlbum.getCurrentIndex()+3)));
               }
               else if(e.getClickCount() ==2){
                   captionsArea.setText("");
                   curAlbum.setCurrentIndex(curAlbum.getCurrentIndex()+3);
                   thumbNailView= false;
                   displayImage();
                  // System.out.println("fourth");
               }
           }
        });
         
                 
        
        //Add panels to main frame
        add(mainPanel,BorderLayout.NORTH);
        add(imagePanel, BorderLayout.CENTER);
        add(navPannel, BorderLayout.WEST);
        add(bottomPanel,BorderLayout.SOUTH);
        add(captionPanel, BorderLayout.EAST);
        
        
        
        //Add listeners to buttons
        openButton.addActionListener(this);
        saveButton.addActionListener(this);
        openAlbumButton.addActionListener(this);
        zoominButton.addActionListener(this);
        zoomoutButton.addActionListener(this);
        quitButton.addActionListener(this);
        zoomFullButton.addActionListener(this);
        previousButton.addActionListener(this);
        nextButton.addActionListener(this);
        thumbnailButton.addActionListener(this);
        captionButton.addActionListener(this);
        initialLoad();
    }
    
    public void drawingImage(String path){
        try {
                    
                    bufferedImage=ImageIO.read(new File(path));
                    
                    
                }
                catch (IOException x){
                    x.printStackTrace();
                    
                }
                imagePanel.repaint();
    }
    
    public void drawThumbNailImages(String p1, String p2, String p3, String p4){
         
            try {
                    if(p1==null){
                        bufferedImage1=null;
                    }
                    else{
                        bufferedImage1=ImageIO.read(new File(p1));
                    }
                    if(p2==null){
                        bufferedImage2=null;
                    }
                    else{
                        bufferedImage2=ImageIO.read(new File(p2));
                    }
                    if(p3==null){
                        bufferedImage3=null;
                    }
                    else{
                        bufferedImage3=ImageIO.read(new File(p3));
                    }
                    if(p4==null){
                        bufferedImage4=null;
                    }
                    else{
                        bufferedImage4=ImageIO.read(new File(p4));
                    }                   
                }
                catch (IOException x){
                    x.printStackTrace();
                    
                }
                imagePanel.repaint();
    }
    public void initialLoad(){
        String currentDir;
        currentDir = System.getProperty("user.dir");
        File direcotry = new File(currentDir);
        files = direcotry.listFiles(new Filter());
        if(files.length!=0){
            curAlbum = new Album(files);
            displayImage(); 
        }
        else{
            curAlbum = new Album(files);
        }

    }
    
    public void displayImage(){
       
        drawingImage(curAlbum.getStringFilePath(curAlbum.getCurrentIndex()));  
        //Caption
        String caption = curAlbum.getImageCaption(curAlbum.getFile(curAlbum.getCurrentIndex()));
        captionsArea.append(caption);
        
    }
  
    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getActionCommand().equals("Open")){
           
           files = null; 
          String directoryPath;
            JFileChooser fileChooser = new JFileChooser();
           fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int returnVal = fileChooser.showOpenDialog(this);
            if(returnVal == JFileChooser.APPROVE_OPTION){
                //Save Caption 
                String caption = captionsArea.getText();
                curAlbum.setImageCaption(caption, curAlbum.getFile(curAlbum.getCurrentIndex()));
                //Erase and replace
                captionsArea.setText(null);
                //Continue loading
                directoryPath=fileChooser.getCurrentDirectory().getAbsolutePath();
                File direcotry = new File(directoryPath);
                files = direcotry.listFiles(new Filter());
                Album newAlbum = new Album(files);
                curAlbum = newAlbum;
                int index = curAlbum.findIndexOfImage(fileChooser.getSelectedFile().getPath());
                if (index == -1){
                    curAlbum.setCurrentIndex(0);
                }
                else{
                    curAlbum.setCurrentIndex(index);
                }
                thumbNailView=false;
                imageFactor=DEFAULT_ZOOM;   
                displayImage();
            
                }
        }
        else if (e.getActionCommand().equals("Zoom In")){
            if(!thumbNailView){
                if(imageFactor<1250){
                    imageFactor=imageFactor+25;
                }
                 imagePanel.repaint();
           }
        }
        else if (e.getActionCommand().equals("Zoom Out")){
            if(!thumbNailView){
                if(imageFactor>25){
                    imageFactor=imageFactor-25;		
               }
            imagePanel.repaint();  
            }
         }

        else if (e.getActionCommand().equals("Zoom 100%")){
            imageFactor=DEFAULT_ZOOM;     
            imagePanel.repaint();
          
        }
        else if (e.getActionCommand().equals("Next")){
            if(!thumbNailView){
                if(curAlbum.getCurrentIndex()<curAlbum.getNumOfFiles()-1){
                    //Save Caption
                    String caption = captionsArea.getText();
                    curAlbum.setImageCaption(caption, curAlbum.getFile(curAlbum.getCurrentIndex()));
                    //Erase and replace
                    captionsArea.setText(null);
                    curAlbum.incCurrentIndex();
                    displayImage();    
                }
           }
            else{
                 int range=curAlbum.getCurrentIndex()+4;
                 if(range<curAlbum.getNumOfFiles()){
                     curAlbum.setCurrentIndex(curAlbum.getCurrentIndex()+4);
                    int tempCur = curAlbum.getCurrentIndex();
                    int largest = curAlbum.getNumOfFiles();
                    String [] paths = new String [4];
                    for(int i=0; i<4;i++){
                    if(tempCur<largest){
                        paths[i]=curAlbum.getStringFilePath(tempCur);
                    }
                    else{
                        paths[i]=null;
                    }
                    tempCur++;
                    }
             
                drawThumbNailImages(paths[0], paths[1], paths[2], paths[3]);
                } 
            }
        }
        else if (e.getActionCommand().equals("Previous")){
           if(!thumbNailView){
                if(curAlbum.getCurrentIndex()>0){
                     //Save Caption 
                    String caption = captionsArea.getText();
                    curAlbum.setImageCaption(caption, curAlbum.getFile(curAlbum.getCurrentIndex()));
                    //Erase and replace
                    captionsArea.setText(null);
                    curAlbum.decCurrentIndex();
                    displayImage();
                }  
            }
           else{
                     curAlbum.setCurrentIndex(curAlbum.getCurrentIndex()-4);
                    if(curAlbum.getCurrentIndex()<0){
                     curAlbum.setCurrentIndex(0);
                     }
                    int tempCur = curAlbum.getCurrentIndex();
                    int largest = curAlbum.getNumOfFiles();
                    String [] paths = new String [4];
                    for(int i=0; i<4;i++){
                    if(tempCur<largest){
                        paths[i]=curAlbum.getStringFilePath(tempCur);
                    }
                    else{
                        paths[i]=null;
                    }
                    tempCur++;
                }
             
                drawThumbNailImages(paths[0], paths[1], paths[2], paths[3]);
          }
        }//End of previous button
        
        else if (e.getActionCommand().equals("Thumbnail")){
            
            if(!thumbNailView){
                //Save Caption
                String caption = captionsArea.getText();
                curAlbum.setImageCaption(caption, curAlbum.getFile(curAlbum.getCurrentIndex()));
                //Erase and replace
                captionsArea.setText(null);
                //captionsArea.setEditable(false);
                thumbNailView = true;
                 int tempCur = curAlbum.getCurrentIndex();
                 int largest = curAlbum.getNumOfFiles();
                 String [] paths = new String [4];
                 for(int i=0; i<4;i++){
                 if(tempCur<largest){
                    
                    paths[i]=curAlbum.getStringFilePath(tempCur);
                }
                else{
                    paths[i]=null;
                }
                tempCur++;
            }
             
            drawThumbNailImages(paths[0], paths[1], paths[2], paths[3]);
          }
        }//End of Thumnail button
        
        else if (e.getActionCommand().equals("Add Caption")){
            if(!thumbNailView){
                String S = (String) JOptionPane.showInputDialog(mainPanel, "Add Caption", "Photo Caption", JOptionPane.PLAIN_MESSAGE);
                if (!(S == null)){
                    captionsArea.append(S+"\n");
                }
           }
        }
        else if (e.getActionCommand().equals("Save Album")){
           System.out.println(curAlbum.getAlreadySaved());
            if(!curAlbum.getAlreadySaved()){
                System.out.println("in saved false");
                String path = null;
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Save Album");
                int userSelection = fileChooser.showSaveDialog(this);
            
                if(userSelection == JFileChooser.APPROVE_OPTION){
                     File fileToSave = fileChooser.getSelectedFile();
                    path=fileToSave.getAbsolutePath();   
                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(path);
                        ObjectOutputStream out = new ObjectOutputStream(fos);
                        int tempCur = curAlbum.getCurrentIndex();
                        curAlbum.setThumbNailView(thumbNailView);
                        curAlbum.setSavedPath(path);
                        curAlbum.setAlreadySaved(true);
                        curAlbum.setCurrentIndex(0);
                        out.writeObject(curAlbum);
                        curAlbum.setCurrentIndex(tempCur);
                    } catch (IOException ex) {
                        Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            else{
                System.out.println("in saved true");
                FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(curAlbum.getSavedPath());
                        ObjectOutputStream out = new ObjectOutputStream(fos);
                        int tempCur = curAlbum.getCurrentIndex();
                        curAlbum.setThumbNailView(thumbNailView);
                        curAlbum.setCurrentIndex(0);
                        out.writeObject(curAlbum);
                        curAlbum.setCurrentIndex(tempCur);
                    } catch (IOException ex) {
                        Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
        }
        else if (e.getActionCommand().equals("Open Album")){
			
            String path = null;
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Open Album");
            int userSelection = fileChooser.showOpenDialog(this);;
            if(userSelection == JFileChooser.APPROVE_OPTION){
               captionsArea.setText("");
               File fileToOpen = fileChooser.getSelectedFile();
               path = fileToOpen.getAbsolutePath();
			   System.out.println(path);
               FileInputStream fis = null;
            
                try {
                fis = new FileInputStream(path);
                ObjectInputStream in = new ObjectInputStream(fis);
                Object obj = in.readObject();
                
                if(obj instanceof Album){
                    curAlbum = (Album) obj;
                    }
                } catch (FileNotFoundException ex) {
					System.out.println("HERE!!!!! 1");
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
					System.out.println("HERE!!!!! 2");
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
					System.out.println("HERE!!!!! 3");
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
				
                thumbNailView=curAlbum.getThumbNailView();
                if (!thumbNailView){
                    displayImage();
                }
                else{
                    int tempCur = curAlbum.getCurrentIndex();
                    int largest = curAlbum.getNumOfFiles();
                    String [] paths = new String [4];
                    for(int i=0; i<4;i++){
                    if(tempCur<largest){
                    
                        paths[i]=curAlbum.getStringFilePath(tempCur);
                    }
                    else{
                        paths[i]=null;
                    }
                    tempCur++;
                }
             
             drawThumbNailImages(paths[0], paths[1], paths[2], paths[3]);
                }
            }   
        }
        else if (e.getActionCommand().equals("Quit")){
          System.exit(0);
        }
        
    }
    
    //Inner Class
    public class ImageDraw extends JPanel{
        
        @Override
        public void paintComponent(Graphics g)
        {
            //If not in thumbnail view
            if(!thumbNailView){
                super.paintComponent(g);
                 remove(jLabelfirst);
                remove(jLabelsecond);
                remove(jLabelthird);
                remove(jLabelfourth);
                if(bufferedImage != null){
                    g.drawImage(bufferedImage, 0, 0, imageFactor, imageFactor, this);
                }
            }
            //If in thumnail view;
            else{
                super.paintComponent(g);
                remove(jLabelfirst);
                remove(jLabelsecond);
                remove(jLabelthird);
                remove(jLabelfourth);
                if(bufferedImage1 != null){
                    g.drawImage(bufferedImage1, x1, thumTopRY, thumImgW, thumImgH, this);
                    add(jLabelfirst);
                }
                if(bufferedImage2 != null){
                    g.drawImage(bufferedImage2, x2, thumTopRY, thumImgW, thumImgH, this);
                    add(jLabelsecond);
                }
                if(bufferedImage3 != null){
                   g.drawImage(bufferedImage3, x1, thumBotRY, thumImgW, thumImgH, this);
                   add(jLabelthird);
                }
                if(bufferedImage4 != null){
                    g.drawImage(bufferedImage4, x2, thumBotRY,thumImgW, thumImgH, this);
                    add(jLabelfourth);
                    
                }
            }
            
        }
      
    }//End of Image Draw class
    
}//End of main fram class

