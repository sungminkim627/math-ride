import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import ddf.minim.*; 
import ddf.minim.analysis.*; 
import ddf.minim.effects.*; 
import ddf.minim.signals.*; 
import ddf.minim.spi.*; 
import ddf.minim.ugens.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class MathGame extends PApplet {

// Math Game
// Program Description: This program is a game that requires mental math skills. This program will have a home screen, how to play page, and high score page, in which the user can 
//                      freely explore, and when the user clicks a difficulty, the game will start. The user will be using key buttons to move a car left and right in order to hit
//                      the correct answer box on the road. The game will go on until user hits an incorrect box; the user will see a end message and will be able to go back to home screen.
// Raphael Kim
// June 14, 2019

// Importing minim for audio.






// Creating sampleFont to display string on the screen.
PFont sampleFont;
// Creating PImage to display pictures on the screen.
PImage carArt;
// int variable for state of Game: 1 = homescreen, 2 = in game, 3 = lose msg page, 4 = how to play page, 5 = high score page.
int game = 1;
// Instantiating the Boxes class.
Boxes box = new Boxes();
// Instantiating the Road class.
Road road = new Road();
// Instantiating the Car class.
Car car = new Car();
// Instantiating the MathProblem class.
MathProblem math = new MathProblem();
// Instantiating the Score class.
Score score = new Score();
// Setting up minim.
Minim minim;
// Creating variable for all the sounds: crash sound, point counter sound, background music 1, and background music 2.
AudioPlayer crash;
AudioPlayer point;
AudioPlayer music1;
AudioPlayer music2;


// Setup function.
public void setup()
{
  // Setting the size of the canvas to 600 by 900 pixels.
  
  // Setup for font.
  // Loading the font in the data folder.
  sampleFont = loadFont("MyanmarText-Bold-20.vlw");
  // Setting the text font to the loaded font.
  textFont(sampleFont);
  // Setting the distance between each letters.
  textLeading(30);
  // Setting text allignment to center, center.
  textAlign(CENTER, CENTER);
  // Setup for displaying image
  // Loading the image from the data folder.
  carArt = loadImage("Car.png");
  // Setting image mode to center.
  imageMode(CENTER);
  // Setup for minim
  minim = new Minim(this);
  // Loading each sound files into variables.
  crash = minim.loadFile("156031__iwiploppenisse__explosion.mp3");
  crash.shiftVolume(1,0.5f, 0);
  point = minim.loadFile("341695__projectsu012__coins-1.wav");
  point.shiftVolume(1,0.3f, 0);
  music1 = minim.loadFile("inGame.mp3");
}

// Draw function.
public void draw()
{
  // If game state is at 1 (Homescreen).
  if(game == 1)
  {
    // Call HomeScreen function.
    homeScreen();
  }
  
  // If game state is at 2 (in-game).
  else if(game == 2)
  {
    // Play the background music.
    music1.play();
    if(music1.position() > 264000)
    {
      music1.rewind();
    }
    music1.unmute();
    // Setting background colour.
    background(0xffEDBE73);
    // Displaying road object.
    road.display();
    // Move the road.
    road.move();
    // Diaplaying box object.
    box.display();
    // Move the box.
    box.move();
    // Displaying the car object.
    car.display();
    // If the user gets the correct answer (the x value of the real box == the car's x value, and the y of the box is greater than equal to car's y(frontside)).
    if(box.correctX() == car.getX() && box.getY() >= car.getY()-50)
    {
      // Play the point sound.
      point.play();
      // If it's already playing, rewind.
      if(point.isPlaying())
      {
        point.rewind();
      }
      // Reset the math question.
      math.reset();
      // Input a new answer to the box object.
      box.inputAnswer(math.getAnswer());
      // Add a score.
      score.addScore();
      // Increase the speed of the road.
      road.increaseSpeed();
      // Increase the speed of the box.
      box.increaseSpeed();
    }
    // If the user does not get the correct answer (if y of the box passes the car, but x of the car does not equal x of the box).
    else if(box.getY() >= car.getY()-50 && box.correctX() != car.getX())
    {
      // Check for high score, if yes, put the score into the correct high score.
      score.checkHighScore(math.getDifficulty());
      // Play the crash sound.
      crash.play();
      // If crash sound is already playing, rewind.
      if(crash.isPlaying())
      {
        crash.rewind();
      }
      // Change game state to 3.
      game = 3;
    }
    // Displaying the math question.
    math.displayQuestion();
    // Displaying the score.
    score.displayScore();
  }
  
  // If game state is at 3 (after-game page).
  else if(game == 3)
  {
    // Call endGameMessage function.
    endGameMessage();
    // mute the background music.
    music1.mute();
  }
  
  // If game state is at 4 (how to play page).
  else if(game == 4)
  {
    // Call howToPlay function.
    howToPlay();
  }
  
  // If game state is at 5 (high score page).
  else if(game == 5)
  {
    // Call highScore function.
    highScore();
  }
}

// homeScreen Function.
public void homeScreen()
{
  // Setting background colour.
  background(0xffEDBE73);
  // Drawing all buttons and texts for the home screen.
  rectMode(CENTER);
  noStroke();
  fill(0xffAAAAAA);
  rect(100, 500, 150, 70, 200);
  rect(300, 500, 150, 70, 200);
  rect(500, 500, 150, 70, 200);
  rect(width/2, 650, 200, 70, 200);
  rect(width/2, 800, 200, 70, 200);
  fill(255);
  textSize(30);
  text("Math Game", width/2, 300);
  textSize(20);
  text("Difficulty:" , width/2, 430);
  text("Easy", 100, 500);
  text("Medium", 300, 500);
  text("Hard", 500, 500);
  text("How To Play", width/2, 650);
  text("High Scores", width/2, 800);
  // If the user clicks the mouse.
  if(mousePressed)
  {
    if(mouseY >= 465 && mouseY <= 535)
    {
      // If the mouse is on Easy button.
      if(mouseX >= 25 && mouseX <= 175)
      {
        // set difficulty to easy.
        math.setDifficulty(1);
        // Change game state to 2.
        game = 2;
        // reset the math question.
        math.reset();
        // input the new answer from the math class into the box class, in order to display them from the box class.
        box.inputAnswer(math.getAnswer());
      }
      // If the mouse is on Medium button.
      else if(mouseX >= 225 && mouseX <= 375)
      {
        // set difficulty to medium.
        math.setDifficulty(2);
        // Change game state to 2.
        game = 2;
        // reset the math question.
        math.reset();
        // input the new answer from the math class into the box class, in order to display them from the box class.
        box.inputAnswer(math.getAnswer());
      }
      // If the mouse is on Hard button.
      else if(mouseX >= 425 && mouseX <= 575)
      {
        // set difficulty to hard.
        math.setDifficulty(3);
        // Change game state to 2.
        game = 2;
        // reset the math question.
        math.reset();
        // input the new answer from the math class into the box class, in order to display them from the box class.
        box.inputAnswer(math.getAnswer());
      }
    }
    // If mouse is on the how to play button.
    if(mouseY >= 615 && mouseY <= 685)
    {
      if(mouseX >= 200 && mouseX <= 400)
      {
        // Change game state to 4.
        game = 4;
      }
    }
    // If mouse is on the high score button.
    if(mouseY >= 765 && mouseY <= 835)
    {
      if(mouseX >= 200 && mouseX <= 400)
      {
        // Change game state to 5.
        game = 5;
      }
    }
    
  }
}

// endGameMessage function.
public void endGameMessage()
{
  // Setting background colour.
  background(0xffEDBE73);
  // Button and texts for end-Game message.
  fill(0xffAAAAAA);
  rect(500, 800, 150, 100, 200);
  fill(255);
  textSize(30);
  // Getting the value of the answer from the MathProblem class and displaying.
  text("Crashed!\n\n\nThe Correct Answer Was: " + PApplet.parseInt(math.getAnswer()), width/2, height/2);
  textSize(20);
  text("Home screen", 500, 800);
  // If mouse is pressed.
  if(mousePressed)
  {
    if(mouseY >= 750 && mouseY <= 850)
    {
      if(mouseX >= 425 && mouseX <= 575)
      {
        // If the mouse is over the home screen button.
        // Change game state to 1. (go back to homescreen)
        game = 1;
        // Reset the car class (bring to middle), score class (make score = 0), and reset the speed of road and the boxes. 
        car.reset();
        score.reset();
        road.reset();
        box.speedReset();
      }
    }
  }
}

// howToPlay function.
public void howToPlay()
{
  // Setting background colour.
  background(0xffEDBE73);
  // Drawing buttons and texts for how to play page.
  fill(0xffAAAAAA);
  rect(500, 800, 150, 100, 200);
  fill(255);
  textSize(20);
  text("You are driving in a magical math desert.\nWhen you encounter a  math problem,\nthere will be 3 boxes, "
  + "one on each lane of the road.\n If you hit the box with the wrong answer written on it, \nyour car will crash into it.\n"
  + "And if you hit the box with the right answer on it,\n the boxes will disappear and the next question will appear.\n\n\nUse 'a' and 'd' to move left and right.", width/2, 400);
  text("Home screen", 500, 800);
  // If mouse is pressed
  if(mousePressed)
  {
    if(mouseY >= 750 && mouseY <= 850)
    {
      if(mouseX >= 425 && mouseX <= 575)
      {
        // If the mouse is on the home screen button, change game state to 1. (go back to homescreen)
        game = 1;
      }
    }
  }
}

// highScore function.
public void highScore()
{
  // Setting background colour.
  background(0xffEDBE73);
  // Calling the displayHighScore method, displays the high scores on the screen.
  score.displayHighScores();
  // Drawing the home screen button
  fill(0xffAAAAAA);
  rect(500, 800, 150, 100, 200);
  fill(255);
  textSize(30);
  text("High Scores", width/2, 200);
  textSize(20);
  text("Home screen", 500, 800);
  // If mouse is pressed
  if(mousePressed)
  {
    if(mouseY >= 750 && mouseY <= 850)
    {
      if(mouseX >= 425 && mouseX <= 575)
      {
        // If the mouse is on the home screen button, change game state to 1. (go back to homescreen)
        game = 1;
      }
    }
  }
}
// Boxes class.
class Boxes
{
  // variables needed for Boxes class.
  // array with the length of 3, to put in a real answer, and 2 incorrect numbers.
  private int[] answers = new int[3];
  // variable that will be used to shuffle the array.
  private int tValue;
  // variable to hold the value of the real answer.
  private int realAnswer;
  // variable to hold the x value of the answer box.
  private int answerX;
  // variable for y position of the boxes.
  private float y;
  // variable for speed.
  private float speed = 4;
  
  // inputAnswer method, accepts a float value (answer).
  public void inputAnswer(int ans)
  {
    // When the answer is given, assign the answer to the realAnswer variable.
    realAnswer = ans;
    // input the answer in the array, and input 2 random values (+-10).
    answers[0] = PApplet.parseInt(ans);
    answers[1] = answers[0] + PApplet.parseInt((random(-10, 11)));
    answers[2] = answers[0] + PApplet.parseInt((random(-10, 11)));
    // While statements to change the values of answers[1] and answers[2] if any of them are the same as the real answer, or if they are the same as each other.
    while(answers[1] == answers[0])
    {
      answers[1] = answers[0] + PApplet.parseInt((random(-10, 11)));
    }
    while(answers[2] == answers[1] || answers[2] == answers[0])
    {
      answers[2] = answers[0] + PApplet.parseInt((random(-10, 11)));
    }
    // Shuffling the answers array, using a forloop.
    for(int i = 0; i < answers.length; i++)
    {
      int index = PApplet.parseInt(random(0, 3));
      tValue = answers[i];
      answers[i] = answers[index];
      answers[index]  = tValue;
    }
    // Resetting y value of the blocks to -200 (out of the screen on top).
    y = -200;
  }
  
  // method to return the value of x for the answer box.
  public int correctX()
  {
    // Checking which answer is real in the answers array using the realAnswer variable, which took the real answer before.
    // If one of them is equal, assign the corresponding x value to the answerX.
    if(answers[0] == realAnswer)
    {
      answerX = 200;
    }
    else if(answers[1] == realAnswer)
    {
      answerX = 300;
    }
    else if(answers[2] == realAnswer)
    {
      answerX = 400;
    }
    // return answerX.
    return answerX;
  }
  
  // method to return the y value of the boxes.
  public float getY()
  {
    // return y;
    return y;
  }
  
  // Display method, to display the boxes.
  public void display()
  {
    // drawing 3 boxes, with the x values of 200, 300, 400, and the y value of y.
    rectMode(CENTER);
    fill(0xffE3373D);
    rect(200, y, 60, 60);
    rect(300, y, 60, 60);
    rect(400, y, 60, 60);
    fill(255);
    textSize(25);
    textLeading(20);
    // drawing numbers on the boxes.
    textAlign(CENTER, CENTER);
    text(answers[0], 200, y);
    text(answers[1], 300, y);
    text(answers[2], 400, y);
  }
  
  // method to increase the speed of the boxes.
  public void increaseSpeed()
  {
    // add 0.1 to speed.
    speed += 0.1f;
  }
  
  // method to reset the speed of the boxes.
  public void speedReset()
  {
    // make speed = 4.
    speed = 4;
  }
  
  // method to move the boxes.
  public void move()
  {
    // y pos
    y += speed;
  }
  
}
// Car class
class Car
{
  // variables needed for car class.
  // x, and y variables to hold the position of the car.
  private int x = 300;
  private int y = 800;
  
  // method to display the car.
  public void display()
  {
    // displaying the image with the value of x and y.
    image(carArt, x, y);
  }
  
  // method to make the car move right.
  public void moveRight()
  {
    // when it's called, move car 100 pixels to the right.
    x += 100;
    // constrain so it doesn't go off the road.
    x = constrain(x, 200, 400);
  }
  
  // method to make the car move left, same thing as the one above, but the opposite way.
  public void moveLeft()
  {
    x -= 100;
    x = constrain(x, 200, 400);
  }
  
  // method to return the x value of the car.
  public int getX()
  {
    // return x.
    return x;
  }
  
  // method to return the y value of the car.
  public int getY()
  {
    // return y.
    return y;
  }
  
  // method to reset the car position to the middle of the road.
  public void reset()
  {
    x = 300;
  }
}

// keyPressed function.
public void keyPressed()
{
  // if the user is in-game.
  if(game == 2)
  {
    // and user presses 'a' or 'A', call the moveLeft method.
    if(key == 'a' || key == 'A')
    {
      car.moveLeft();
    }
    // and user presses 'd' or 'D', call the moveRight method.
    if(key == 'd' || key == 'D')
    {
      car.moveRight();
    }
  }
}
// MathPrblem class.
class MathProblem
{
  // variables needed for MathProblem class.
  // int variables a, b, and r. a and b will be the two numbers that will be used to make the equation, and r will be used to randomize between +, -, or x.
  private int a, b, r;
  // int variable range to hold the range of numbers a and b can be.
  private int range;
  // variable for answer.
  private int answer;
  // String variable to hold the math equation.
  private String question;
  // int variable for difficulty (1 is easy, 2 is medium, 3 is hard).
  private int difficulty;
  
  // reset method
  public void reset()
  {
  // make a and b any int values between 1 and range.
  a = PApplet.parseInt(random(1, range));
  b = PApplet.parseInt(random(1, range));
  // make r a random number between 1 and 3.
  r = PApplet.parseInt(random(1, 4));
    // switch statement to check r.
    switch(r)
    {
      // if r is 1, the question will be addition
      case 1:
        answer = a + b;
        question = a + " + " + b + " = ?";
       break;
      // if r is 2, the question will be subtraction
      case 2:
        answer = a - b;
        question = a + " - " + b + " = ?";
        break;
      // if r is 3, the question will be multiplication.
      case 3:
        answer = a * b;
        question = a + " x " + b + " = ?";
        break;
      // in every case, the answer variable will take the value of the answer, and the string variable question will take on the string version of the math equation.
    }
  }
  
  // method to set difficulty, takes an int value.
  public void setDifficulty(int d)
  {
    // set variable difficulty to d.
    difficulty = d;
    // switch to check difficulty, depending on the difficulty, range differs.
    switch(difficulty)
    {
      case 1:
        range = 11;
        break;
       case 2:
        range = 51;
        break;
       case 3:
        range = 101;
        break;
    }
  }
  
  // method to return the difficulty.
  public int getDifficulty()
  {
    // return difficulty.
    return difficulty;
  }
  
  // method to return the answer.
  public int getAnswer()
  {
    // return answer.
    return answer;
  }
  
  // method to display question
  public void displayQuestion()
  {
    // displaying the box and the text on the top of the screen.
    fill(0, 0, 0, 100);
    rectMode(CENTER);
    rect(300, 50, 200, 100, 200);
    fill(255);
    textSize(25);
    textLeading(20);
    textAlign(CENTER, CENTER);
    text(question, 300, 50);
  }
  
  
  
}
// Road class.
class Road
{
  // variables needed for this class.
  // x1 and x2 are the x values of road lines.
  private float x1 = 250;
  private float x2 = 350;
  // array to hold the y values of 5 roads lines on each side.
  private float[] y = new float[5];
  // variable to hold the speed of the road.
  private float speed = 4;
  
  // constructor, when road is called, assign y array with multiples of 200, first one is 0.
  Road()
  {
    for(int i = 0; i < 5; i += 1)
    {
      y[i] = i*200;
    }
  }
  
  // display method.
  public void display()
  {
    // displaying the road, and the road lines.
    // total of 10 road lines, 5 on each side.
    rectMode(CENTER);
    noStroke();
    fill(0xffAAAAAA);
    rect(300, 450, 300, 900);
    fill(255);
    for(int i = 0; i < 5; i += 1)
    {
      rect(x1, y[i], 10, 100);
      rect(x2, y[i], 10, 100);
    }
  }
  
  // method to increase speed.
  public void increaseSpeed()
  {
    // add 0.1 to speed.
    speed += 0.1f;
  }
  
  // method to reset the speed of the road.
  public void reset()
  {
    speed = 4;
  }
  
  // method to move the road.
  public void move()
  {
    // using the forloop, add "speed" to each y values in the array.
    for(int i = 0; i < 5; i += 1)
    {
      y[i]+=speed;
      if(y[i] > 950)
      {
        y[i] = -50;
      }
    }
  }
  
}
// Score class
class Score
{
  // variable to hold the current score.
  private int score = 0;
  // arrays to hold the top 3 scores for each difficulties.
  private int[] easyHighScore = {0, 0, 0};
  private int[] mediumHighScore = {0, 0, 0};
  private int[] hardHighScore = {0, 0, 0};
  
  // reset method to reset the score to 0.
  public void reset()
  {
    score = 0;
  }
  
  // addScore method to add 1 to score each time it's called.
  public void addScore()
  {
    score += 1;
  }
  
  // method to display the score.
  public void displayScore()
  {
    // displaying the score on the bottom right of the screen.
    fill(0);
    text("Score: " + score, 520, 850);
  }
  
  // method to check the high score, accepts the difficulty.
  public void checkHighScore(int d)
  {
    // in each difficulty, if the current score is higher than the 3rd highest score, replace it and sort.
    switch(d)
    {
      case 1:
        if(score > easyHighScore[0])
        {
          easyHighScore[0] = score;
          easyHighScore = sort(easyHighScore);
        }
      break;
      case 2:
      if(score > mediumHighScore[0])
        {
          mediumHighScore[0] = score;
          mediumHighScore = sort(mediumHighScore);
        }
      break;
      case 3:
        if(score > hardHighScore[0])
        {
          hardHighScore[0] = score;
          hardHighScore = sort(hardHighScore);
        }
      break;
    }
  }
  
  // method to display the high scores.
  public void displayHighScores()
  {
    // texts for high score page.
    text("Easy", 100, 300);
    text("Medium", 300, 300);
    text("Hard", 500, 300);
    
    text("1. " + easyHighScore[2], 60, 400); 
    text("2. " + easyHighScore[1], 60, 500); 
    text("3. " + easyHighScore[0], 60, 600); 
    
    text("1. " + mediumHighScore[2], 260, 400); 
    text("2. " + mediumHighScore[1], 260, 500); 
    text("3. " + mediumHighScore[0], 260, 600); 
    
    text("1. " + hardHighScore[2], 460, 400); 
    text("2. " + hardHighScore[1], 460, 500); 
    text("3. " + hardHighScore[0], 460, 600); 

  }
}
  public void settings() {  size(600, 900); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "MathGame" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
