/**
Name: Cory Petersen
Project: 6 Image Machine
**/
import java.awt.*;
import javax.swing.*;
/**
 *
 * @author coryp_000
 */
public class JIMachine {
    
    
    public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {

          @Override
          public void run() {
              MainFrame frame = new MainFrame();
              frame.setTitle("Image Viewer");
              frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
              frame.setVisible(true);
          }
      });
    }
    
}