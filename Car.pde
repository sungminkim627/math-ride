// Car class
class Car
{
  // variables needed for car class.
  // x, and y variables to hold the position of the car.
  private int x = 300;
  private int y = 800;
  
  // method to display the car.
  void display()
  {
    // displaying the image with the value of x and y.
    image(carArt, x, y);
  }
  
  // method to make the car move right.
  void moveRight()
  {
    // when it's called, move car 100 pixels to the right.
    x += 100;
    // constrain so it doesn't go off the road.
    x = constrain(x, 200, 400);
  }
  
  // method to make the car move left, same thing as the one above, but the opposite way.
  void moveLeft()
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
  void reset()
  {
    x = 300;
  }
}

// keyPressed function.
void keyPressed()
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
