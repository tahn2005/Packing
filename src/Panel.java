package puzzle;
import javax.swing.*;
import java.awt.*;

public class Panel{
    JPanel mainpanel;
    int scale;

    public Panel(int width, int height){
        setscale(width, height);
        int w = width * scale;
        int h = height * scale;
        JFrame frame = new JFrame();
        frame.setTitle("Puzzle");    
        frame.setSize(w, h);              
        frame.setMinimumSize(new Dimension(w, h));
        frame.setMaximumSize(new Dimension(w, h));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        mainpanel = new JPanel();
        mainpanel.setBackground(Color.WHITE);
        mainpanel.setLayout(null);
        frame.setLayout(new BorderLayout());
        frame.add(mainpanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void setscale(int a, int b){
        if(a > b){
            this.scale = 800/a;
        }
        else{
            this.scale = 800/b;
        }
    }

    //prints solution screen
    public void addpanel(int x, int y, int width, int length, int id){
        String ids = String.valueOf(id);
        JLabel label = new JLabel(ids);
        label.setForeground(Color.WHITE);
        JPanel panel = new JPanel();
        panel.setBounds(x*scale, y*scale, width*scale, length*scale);
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.BLACK);
        JPanel border1 = new JPanel();
        border1.setBackground(Color.GREEN);
        border1.setPreferredSize(new Dimension(1, 0));
        JPanel border2 = new JPanel();
        border2.setBackground(Color.GREEN);
        border2.setPreferredSize(new Dimension(1, 0));
        JPanel border3 = new JPanel();
        border3.setBackground(Color.GREEN);
        border3.setPreferredSize(new Dimension(0, 1));
        JPanel border4 = new JPanel();
        border4.setBackground(Color.GREEN);
        border4.setPreferredSize(new Dimension(0, 1));
        panel.add(border1, BorderLayout.EAST);
        panel.add(border2, BorderLayout.WEST);
        panel.add(border3, BorderLayout.NORTH);
        panel.add(border4, BorderLayout.SOUTH);
        panel.add(label, BorderLayout.CENTER);
        mainpanel.add(panel);
        mainpanel.revalidate();  
        mainpanel.repaint(); 
    }

    public void removepanel(){
        mainpanel.removeAll();
        mainpanel.revalidate();  
        mainpanel.repaint(); 
    }

    //prints "No solution exists" on screen
    public void unsolved(){
        JLabel label =  new JLabel("No solution exists");
        label.setFont(new Font("Arial", Font.BOLD, 30));

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panel.add(label);

        mainpanel.removeAll();
        mainpanel.setLayout(new GridLayout(3, 0));
        mainpanel.add(new JLabel(""));
        mainpanel.add(panel);
        mainpanel.revalidate();  
        mainpanel.repaint(); 
    }
}