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
  void display()
  {
    // displaying the road, and the road lines.
    // total of 10 road lines, 5 on each side.
    rectMode(CENTER);
    noStroke();
    fill(#AAAAAA);
    rect(300, 450, 300, 900);
    fill(255);
    for(int i = 0; i < 5; i += 1)
    {
      rect(x1, y[i], 10, 100);
      rect(x2, y[i], 10, 100);
    }
  }
  
  // method to increase speed.
  void increaseSpeed()
  {
    // add 0.1 to speed.
    speed += 0.1;
  }
  
  // method to reset the speed of the road.
  void reset()
  {
    speed = 4;
  }
  
  // method to move the road.
  void move()
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
