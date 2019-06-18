import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class MusicGUI {

    public static void main(String[] args) {

        JFrame window = new JFrame("Music Player");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        IconPanel panel = new IconPanel();

        window.setContentPane(panel);
        //window.setSize(470,600);
        window.pack();
        window.setVisible(true);

    }
}



class IconPanel extends JPanel {
    public IconPanel() {
        //String[] iconArray = {new File("tatami-galaxy-art.jpg").getAbsolutePath(), new File("Don't-Let-Me-Down-Illenium.jpg").getAbsolutePath(), new File("Symphony.jpg").getAbsolutePath()};
        String[] iconArray = {"tatami-galaxy-art.jpg", "Don't-Let-Me-Down-Illenium.jpg", "Symphony.jpg"};
        String[] songNameArray = {"Maigo Inu to Ame no Beat", "Don't Let Me Down", "Symphony"};
        String[] songArray = {"Asian Kungfu Generation - Maigo Inu to Ame no Beat - Full OP.wav", "The Chainsmokers - Don't Let Me Down (Illenium Remix).wav", "Clean Bandit - Symphony feat. Zara Larsson Official Video.wav"};

        Integer songSeeker = 0; //refers to index of song, should inc by 1 for each next action

        Icon label = new ImageIcon(getClass().getResource(iconArray[0]));
        Icon play = new ImageIcon(getClass().getResource("play-button.png"));
        Icon pause = new ImageIcon(getClass().getResource("pause.png"));
        Icon next = new ImageIcon(getClass().getResource("next.png"));
        Icon previous = new ImageIcon(getClass().getResource("previous.png"));
        Icon repeat = new ImageIcon(getClass().getResource("repeat.png"));
        Icon shuffle = new ImageIcon(getClass().getResource("shuffle.png"));

        JLabel musicL = new JLabel();
        musicL.setIcon(label);

        //JLabel songName = new JLabel("Now Playing: Maigo Inu to Ame no Beat");
        JLabel songName = new JLabel("Now Playing: " + songNameArray[songSeeker]);
        Font newFont = new Font("Tahoma", Font.BOLD, 16);
        songName.setFont(newFont);

        JToggleButton b1 = new JToggleButton(pause);
        b1.setIcon(play);
        //Integer playVal = 0;
        b1.setSelectedIcon(pause);
//	    if(b1.getIcon() == pause) {
//	    		playVal = 1;
//	    } else {
//	    		playVal = 0;
//	    }
//	    System.out.println(playVal);
        b1.addActionListener(new PlayListener(b1, songArray[songSeeker]));

        JButton b2 = new JButton();
        b2.setIcon(previous);
        b2.addActionListener(new PreviousListener(b2, iconArray, songNameArray, songArray, musicL, songName));

        JButton b3 = new JButton();
        b3.setIcon(next);
        b3.addActionListener(new NextListener(b3, iconArray, songNameArray, songArray, musicL, songName));

        JToggleButton b4 = new JToggleButton();
        b4.setIcon(repeat);

        JToggleButton b5 = new JToggleButton();
        b5.setIcon(shuffle);


        this.setLayout(new FlowLayout());
        this.add(musicL);
        this.add(songName);
        this.add(b2);
        this.add(b1);
        this.add(b3);
        this.add(b4);
        this.add(b5);
    }
}

class PlayListener implements ActionListener {

    JToggleButton button;
    Icon symPlay = new ImageIcon(this.getClass().getResource("play-button.png"));
    Integer playVal = 0;
    String song;

    public PlayListener(JToggleButton button, String songFile) {
        this.button = button;
        song = songFile;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        playVal++;
        try {
            //AudioInputStream ais = AudioSystem.getAudioInputStream(new File("Asian Kungfu Generation - Maigo Inu to Ame no Beat - Full OP.wav").getAbsoluteFile());
            AudioInputStream ais = AudioSystem.getAudioInputStream(getClass().getResource(song));
            Clip clip = AudioSystem.getClip();
            //clip.open(ais);
            System.out.println(button.getIcon());
            System.out.println(playVal);
            //if (button.getIcon().equals(symPlay)) {
            long clipTime = clip.getMicrosecondLength();
            if (/*button.getIcon().toString().equals(symPlay.toString())*/ playVal%2 != 0) {
                clip.open(ais);
                clip.setMicrosecondPosition(clipTime);
                clip.start();
                System.out.println(song + " is playing");
            /*} else {
                //TODO: instead of calling the togglebutton to the listener, get its string value i guess?
                //AudioSystem.getAudioInputStream(getClass().getResource(song)).close();
                *//*AudioSystem.getClip().stop(); AudioSystem.getClip().close();
                clip.stop();
                clip.close();
                System.out.println("Act ------" + button.getIcon());
                System.out.println("Exp ------" + symPlay);*//*
            }*/
            } else if (clip.isActive() && playVal%2 == 0) {
                clip.stop(); clip.close();
                System.out.println("failure?");
            }
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }
}

class NextListener implements ActionListener {

    JButton button;
    String[] iArray;  //icon
    String[] snArray;  //song name
    String[] sArray;  //song
    JLabel songIcon;
    JLabel songName;

    Integer timesCalled = 0; //no. of times next button has been pressed

    public NextListener(JButton button, String[] icons, String[] songNames, String[] songArray, JLabel musicLayer, JLabel songName) {
        this.button = button;
        this.iArray = icons;
        this.snArray = songNames;
        this.sArray = songArray;
        this.songIcon = musicLayer;
        this.songName = songName;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        timesCalled++;
        if(timesCalled > 2) {
            timesCalled = 0;
        }
        songIcon.setIcon(new ImageIcon(getClass().getResource(iArray[timesCalled])));
        songName.setText("Now Playing: " + snArray[timesCalled]);
        try {
            //
//			AudioInputStream ais = AudioSystem.getAudioInputStream(new File("src/" + sArray[timesCalled-1]));
//			System.out.println(sArray[timesCalled-1]);
//			Clip clip = AudioSystem.getClip();
//			clip.stop();
//			clip.close();
            //
            AudioInputStream ais1 = AudioSystem.getAudioInputStream(new File("src/" + sArray[timesCalled]));
            System.out.println(sArray[timesCalled]);
            Clip clip1 = AudioSystem.getClip();
            clip1.open(ais1);
            clip1.stop();
            clip1.close();
            AudioInputStream ais2 = AudioSystem.getAudioInputStream(new File("src/" + sArray[timesCalled]));
            Clip clip2 = AudioSystem.getClip();
            clip2.open(ais2);
            clip2.start();

        } catch (UnsupportedAudioFileException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("NEXT" + timesCalled);
    }
}

class PreviousListener implements ActionListener {

    JButton button;
    String[] iArray;  //icon
    String[] snArray;  //song name
    String[] sArray;  //song
    JLabel songIcon;
    JLabel songName;

    Integer timesCalled = 0; //no. of times next button has been pressed

    public PreviousListener(JButton button, String[] icons, String[] songNames, String[] songArray, JLabel musicLayer, JLabel songName) {
        this.button = button;
        this.iArray = icons;
        this.snArray = songNames;
        this.sArray = songArray;
        this.songIcon = musicLayer;
        this.songName = songName;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        timesCalled++;
        if(timesCalled > 2) {
            timesCalled = 0;
        }
        songIcon.setIcon(new ImageIcon(getClass().getResource(iArray[(iArray.length - 1) - timesCalled])));
        songName.setText("Now Playing: " + snArray[(snArray.length - 1) - timesCalled]);
        try {
            AudioInputStream ais1 = AudioSystem.getAudioInputStream(new File("src/" + sArray[(sArray.length - 1) - timesCalled - 1]));
            System.out.println(sArray[(sArray.length - 1) - timesCalled - 1]);
            Clip clip1 = AudioSystem.getClip();
            clip1.open(ais1);
            clip1.stop();
            AudioInputStream ais2 = AudioSystem.getAudioInputStream(new File("src/" + sArray[(sArray.length - 1) - timesCalled]));
            Clip clip2 = AudioSystem.getClip();
            clip2.open(ais2);
            clip2.start();
        } catch (UnsupportedAudioFileException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("PREVIOUS" + timesCalled);

    }

}