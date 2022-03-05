import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class particle extends PApplet {

ArrayList<Particle>  particle = new ArrayList<Particle>(); //<>//
int count = 3000;

public void setup(){
  colorMode(HSB, 255);
  
  strokeWeight(1.5f);
  for(int i = 0; i < count; i ++){
    Particle p = new Particle();
    p.initC();
    particle.add(p);
  }
  blendMode(ADD);
}

public void draw(){
  blendMode(BLEND);
  if(mouseButton == RIGHT){
    clear();
  }else{   
    fill(0, 20);
    rect(0,0,width, height);
  }
  blendMode(ADD);
  for(Particle p : particle){
    p.update();
  }
}

class Particle{
  float x, y;
  float px, py;
  float type;
  
  boolean isClicking = false;
  boolean isExplode = false;
  public void initC(){
    x = width/2;
    y = height / 2;
    px = x;
    py = y;
    type = random(0, 255);
  }
  public void init(){
    int r = (int)random(2);
    if(r == 0){
       x = random((int)random(2) * width);
       y = random(0, height);
    }else if(r == 1){
      y = random((int)random(2) * height);
       x = random(0, width);
    }
    px = x;
    py = y;
    type = random(0, 255);
  }
  public void update(){
    
    if(x <  0 || x > width){
      isExplode = false;
      initC();
    }
    if(y < 0 || y > height){
      isExplode = false;
      initC();
    }
    
    if(isExplode){
       x += cos(radians(map(type, 0, 225, 0, 360))) * 10;
       y += sin(radians(map(type, 0, 225, 0, 360))) * 10;
        stroke(map(sin(radians(frameCount + type * 0.4f)) * 122 + 122, 0, 255, 0, 255), 180, 210, 150);
    }else if(mousePressed){
     // if(mouseButton == LEFT){
        //Action 1
        float a = noise(type, x / 300, y / 200) * 1000;
        
        float mAngle = atan2(mouseY - py + map(type, 0, 255, -20, 20), mouseX - px + map(type, 0, 255, -20, 20));
        x += cos(mAngle) * map(type, 0, 255, -10, 10) + cos(radians(a));
        y += sin(mAngle) * map(type, 0, 255, -10, 10) + sin(radians(a));
        stroke(map(sin(radians(frameCount + type * 0.3f)) * 122 + 122, 0, 255, 0, 255), 180, 210, 150);
        isClicking = true;
        if(abs((int)mouseX - (int)x) < 5 && abs((int)mouseY - (int)y) < 5){
          init();
        }
      //}else if(mouseButton == RIGHT){
        //Action 2
        
      //}
        
    }else{
      if(isClicking){
        isExplode = true;
        isClicking = false;
      }
      float a = noise(type, x / 300 , y / 200) * map(type, 0, 225, 0, 500);
      x += cos(radians(a + map(type, 0, 225, 0, 360))) * map(type + a, 0f, 255f, 1f, 3f);
      y += sin(radians(a + map(type, 0, 225, 0, 360))) * map(type + a, 0f, 255f, 1f, 3f);
      stroke(map(sin(radians(frameCount + type * 0.18f)) * 122 + 122, 0, 255, 0, 255), 180, 210, 150);
    }
    
    line(px, py, x, y);
    
    px = x;
    py = y;
  }
}
  public void settings() {  fullScreen(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#666666", "--stop-color=#cccccc", "particle" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
