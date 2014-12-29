package colorSelector;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MixerApplet2 extends JPanel{
  
  static final int WIDE = 256;
  static final int HIGH = 256;
  int lastX, lastY;
  ColorSelectorPlus parent;
  
  public MixerApplet2(ColorSelectorPlus csp){
    super();
    parent = csp;
    
    addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        updateMouse(e);
      }
    });
    
    addMouseMotionListener(new MouseMotionListener() {
      
      @Override
      public void mouseMoved(MouseEvent e) {
        
      }
      
      @Override
      public void mouseDragged(MouseEvent e) {
        updateMouse(e);
      }
    });
  }
  
  public Dimension getPreferredSize() {
    return new Dimension(256, 256);
  }
  
  public void paintComponent(Graphics g){
    super.paintComponent(g); 
    for(int i = 0; i < getWidth(); i++){
      for (int j = 0; j < getHeight(); j++) {
        g.setColor(Color.getHSBColor(parent.hue/360.0f, (getWidth()-i)/256.0f, (getHeight()-j)/256.0f));
        g.drawLine(getWidth()-i, j, getWidth()-i, j);
      }
    }
    
    Graphics2D g2 = (Graphics2D) g;
    g2.setStroke(new BasicStroke(2));
    g2.drawLine(lastX - 10, lastY, lastX - 5, lastY);
    g2.drawLine(lastX + 10, lastY, lastX + 5, lastY);
    g2.drawLine(lastX, lastY - 10, lastX, lastY - 5);
    g2.drawLine(lastX, lastY + 10, lastX, lastY + 5);
    
  }
  
  public void redraw(){
    SwingUtilities.invokeLater(new Runnable()
    {
        public void run()
        {
          //System.out.println("H " + parent.hue);
            repaint();
        }
    }); 
  }
  
  public void redraw2() {
    lastX = parent.saturation;
    lastY = 255-parent.brightness;
    //redraw();
  }
  
  public void updateMouse(MouseEvent e) {
    if (e.getX() < 0 || e.getX() > getWidth() || e.getY() < 0
        || e.getY() > getHeight())
      return;
    lastX = e.getX();
    lastY = e.getY();
    parent.saturation = (int) (e.getX() * 255.0 / getWidth());
    parent.brightness = (int)((getHeight() - e.getY())*255.0/getHeight());
    redraw();
    parent.txtS.setText(String.valueOf(parent.saturation));
    parent.txtV.setText(String.valueOf(parent.brightness)); 
    //System.out.println("UM");
//    if ((mouseX >= 0) && (mouseX < 256) && (mouseY >= 0) && (mouseY < 256)) {     
//      parent.brightness = height - mouseY - 1;
//      parent.saturation = mouseX;   
//      System.out.println("SetColVal "+parent.red + "," + parent.green + "," + parent.blue + "," + parent.hue + ","
//          + parent.saturation + "," + parent.brightness);
//      lastX = mouseX;
//      lastY = mouseY;
//      parent.txtS.setText(String.valueOf(parent.saturation));
//      parent.txtV.setText(String.valueOf(parent.brightness));     
//    }
  }
}
