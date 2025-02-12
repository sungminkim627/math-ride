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
  void reset()
  {
  // make a and b any int values between 1 and range.
  a = int(random(1, range));
  b = int(random(1, range));
  // make r a random number between 1 and 3.
  r = int(random(1, 4));
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
  void setDifficulty(int d)
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
  void displayQuestion()
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
