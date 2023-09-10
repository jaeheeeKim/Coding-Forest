package miniproject_final;

import java.io.File;
import java.util.*;
import javax.sound.sampled.*;

public class Music extends Thread{
   Clip clip;
   boolean isLoop;
   public Music(boolean isLoop) {
      try {
         this.isLoop = isLoop;
         AudioInputStream ais = AudioSystem.getAudioInputStream(new File("login.wav"));
         clip = AudioSystem.getClip();
         clip.open(ais);
         //소리 설정
         FloatControl gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
         //볼륨 조절
         gainControl.setValue(-10.0f);
      }catch(Exception e) {
         e.printStackTrace();
      }
      
   }
   
   @Override
   public void run() {
      try {
         do {
            clip.start();
         }while(isLoop);
      }catch(Exception e) {
         e.printStackTrace();
      }
   }
   
//   public static void main(String[]args) {
//	   new Music(true);
//   }
}