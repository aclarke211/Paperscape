package com.paperscape.game;
//=========================================================================================================================
import com.badlogic.gdx.math.Vector2;

//=========================================================================================================================
public class Door extends Walls {

    float openSpeed, closeSpeed;
    //used in a switch (int ActivateDoorButton class) to check which direction to open a door
    int openingDirection;


//=========================================================================================================================
    //constructor where attributes are passed to the door object
    public Door(Vector2 position, Vector2 size, float speed, int direction) {
            //running the constructor from the parent class 'Walls', passing its required variables
            super(position, size);
            //setting the speed that a door will open at
            openSpeed = 5f;
            //setting the closeSpeed of the door to the value passed to method
            this.closeSpeed = speed;
            //assigning the open direction int to the value passed by method
            this.openingDirection = direction;
            //re-initialising the image from a wall to a door
            image = GameAssets.doorSprite;

    }

//=========================================================================================================================
}
//=========================================================================================================================