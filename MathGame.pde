// Math Game
// Program Description: This program is a game that requires mental math skills. This program will have a home screen, how to play page, and high score page, in which the user can 
//                      freely explore, and when the user clicks a difficulty, the game will start. The user will be using key buttons to move a car left and right in order to hit
//                      the correct answer box on the road. The game will go on until user hits an incorrect box; the user will see a end message and will be able to go back to home screen.
// Raphael Kim
// June 14, 2019

// Importing minim for audio.
import ddf.minim.*;
import ddf.minim.analysis.*;
import ddf.minim.effects.*;
import ddf.minim.signals.*;
import ddf.minim.spi.*;
import ddf.minim.ugens.*;
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
void setup()
{
  // Setting the size of the canvas to 600 by 900 pixels.
  size(600, 900);
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
  crash.shiftVolume(1,0.5, 0);
  point = minim.loadFile("341695__projectsu012__coins-1.wav");
  point.shiftVolume(1,0.3, 0);
  music1 = minim.loadFile("inGame.mp3");
}

// Draw function.
void draw()
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
    background(#EDBE73);
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
void homeScreen()
{
  // Setting background colour.
  background(#EDBE73);
  // Drawing all buttons and texts for the home screen.
  rectMode(CENTER);
  noStroke();
  fill(#AAAAAA);
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
void endGameMessage()
{
  // Setting background colour.
  background(#EDBE73);
  // Button and texts for end-Game message.
  fill(#AAAAAA);
  rect(500, 800, 150, 100, 200);
  fill(255);
  textSize(30);
  // Getting the value of the answer from the MathProblem class and displaying.
  text("Crashed!\n\n\nThe Correct Answer Was: " + int(math.getAnswer()), width/2, height/2);
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
void howToPlay()
{
  // Setting background colour.
  background(#EDBE73);
  // Drawing buttons and texts for how to play page.
  fill(#AAAAAA);
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
void highScore()
{
  // Setting background colour.
  background(#EDBE73);
  // Calling the displayHighScore method, displays the high scores on the screen.
  score.displayHighScores();
  // Drawing the home screen button
  fill(#AAAAAA);
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
