//============================================================================================================================//
package com.paperscape.game;
//============================================================================================================================//
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

//============================================================================================================================//
public class Button {

    //holds the position of the button object
    public Vector2 position, size;
    //the image displayed as the button
    public TextureRegion buttonImage;
    //whether the button is currently being pressed or not
    public Boolean isClicked;
    //acts as a boarder around the button that can be interacted with
    public Rectangle buttonBounds;
    //finding the opposite ends of the button, used for checking if a touch occurs within the button's bounds
    public float buttonBoundsXEnd, buttonBoundsYEnd;
    //as the level buttons are held in an array beginning at 0, this int will be added to the array value get the correct level number
    public int levelButtonValue = 1;

//============================================================================================================================//
    public Button(Vector2 position, Vector2 size, TextureRegion buttonImage, int levelButtonValue) {

        //setting variables in this class to those passed by constructor
        this.position = position;
        this.size = size;
        this.buttonImage = buttonImage;
        this.levelButtonValue += levelButtonValue;

        //invisible rectangle surrounding sprite (X-position, Y-position, X-walls_image-size, Y-walls_image-size)
        buttonBounds = new Rectangle(position.x, position.y, size.x, size.y);

        //finding the edge of the sprite; used for touch/hit detection
        buttonBoundsXEnd = buttonBounds.x + size.x;
        buttonBoundsYEnd = buttonBounds.y + size.y;

        //initialising the button as not being clicked
        isClicked = false;

    }

//============================================================================================================================//
    public void update(Vector3 touch, OrthographicCamera camera) {

            //if there is a touch on the screen
            if (Gdx.input.isTouched()) {
                //set the values of the passed Vector3 to the position of the touch
                touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                //allows re-sizing while still correct touch locations
                camera.unproject(touch);

                //if x-position of touch is more than where button starts and is less than where button ends
                if (touch.x >= buttonBounds.x && touch.x <= buttonBoundsXEnd &&
                        touch.y >= buttonBounds.y && touch.y <= buttonBoundsYEnd) {

                    //if a touch occurs within the bounds of a button, set the isClicked Boolean to true
                    isClicked = true;

                    //make the game wait so the same touch wont reset the button
                    try {
                        //how long to wait in milliseconds
                        Thread.sleep(GameAssets.waitTimeForPause);
                    } catch(InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
                //what happens when a touch occurs outside the buttons bounds
                else {
                    isClicked = false;
                }
            }

        //if the screen is not currently being touched
        else if (!Gdx.input.isTouched()) {
                isClicked = false;
            }

        }

//============================================================================================================================//
    public void draw(SpriteBatch batch) {

        //render the button using the passed sprite batch
        batch.draw(buttonImage, buttonBounds.x, buttonBounds.y, size.x, size.y);

    }

//============================================================================================================================//
}
//============================================================================================================================//
