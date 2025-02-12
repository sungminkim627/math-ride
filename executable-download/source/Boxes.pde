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
  void inputAnswer(int ans)
  {
    // When the answer is given, assign the answer to the realAnswer variable.
    realAnswer = ans;
    // input the answer in the array, and input 2 random values (+-10).
    answers[0] = int(ans);
    answers[1] = answers[0] + int((random(-10, 11)));
    answers[2] = answers[0] + int((random(-10, 11)));
    // While statements to change the values of answers[1] and answers[2] if any of them are the same as the real answer, or if they are the same as each other.
    while(answers[1] == answers[0])
    {
      answers[1] = answers[0] + int((random(-10, 11)));
    }
    while(answers[2] == answers[1] || answers[2] == answers[0])
    {
      answers[2] = answers[0] + int((random(-10, 11)));
    }
    // Shuffling the answers array, using a forloop.
    for(int i = 0; i < answers.length; i++)
    {
      int index = int(random(0, 3));
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
  void display()
  {
    // drawing 3 boxes, with the x values of 200, 300, 400, and the y value of y.
    rectMode(CENTER);
    fill(#E3373D);
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
  void increaseSpeed()
  {
    // add 0.1 to speed.
    speed += 0.1;
  }
  
  // method to reset the speed of the boxes.
  void speedReset()
  {
    // make speed = 4.
    speed = 4;
  }
  
  // method to move the boxes.
  void move()
  {
    // y pos
    y += speed;
  }
  
}
