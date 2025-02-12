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
  void reset()
  {
    score = 0;
  }
  
  // addScore method to add 1 to score each time it's called.
  void addScore()
  {
    score += 1;
  }
  
  // method to display the score.
  void displayScore()
  {
    // displaying the score on the bottom right of the screen.
    fill(0);
    text("Score: " + score, 520, 850);
  }
  
  // method to check the high score, accepts the difficulty.
  void checkHighScore(int d)
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
  void displayHighScores()
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
