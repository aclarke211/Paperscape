package com.paperscape.game;
//=========================================================================================================================
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

//=========================================================================================================================
public class ActivateDoorButton {

    //the location on screen and size of the object
    Vector2 position, size, originalSize, openSize;
    //holds an image to display representing the object on screen
    public TextureRegion image;
    //surrounds the object and can be interacted with
    public Rectangle bounds;
    //finding the opposite sides of the bounds
    public float boundsXEnd, boundsYEnd;
    //holds the passed player object
    public Player player;
    //a doors object (acts as the one to be opened in game)
    public Door door;
    //used to see if the player is touching the button or not
    public Boolean buttonTouched, doorClosed;

    //the cases used in direction switch
    public static final int HORISONTAL = 1,
                            VERTICAL = 2;

//=========================================================================================================================
    public ActivateDoorButton(Vector2 position, Vector2 size, Door door) {

        this.position = position;
        this.size = size;
        this.door = door;
        originalSize = new Vector2(door.size.x, door.size.y);

        //invisible rectangle surrounding object (X-position, Y-position, X-walls_image-size, Y-walls_image-size)
        bounds = new Rectangle(position.x, position.y, size.x, size.y);
        //add the position and size of the object together to find the the opposites sides of the bounds
        boundsXEnd = bounds.x + size.x;
        boundsYEnd = bounds.y + size.y;

        //setting what size the door should stop opening at
        openSize = new Vector2(50, 50);

        //setting the image for the object, stored in GameAssets class
        image = GameAssets.doorButtonSprite;
        //initialising the button to not be touched so the doors stay closed
        buttonTouched = false;
        //initalising the door to be seen as closed
        doorClosed = true;


    }

//==========================================================================================================================
    public void update(Player player) {

        //initialising the player object as the one passed by method
        this.player = player;
        //resetting the bounds to the current position of object
        bounds.set(position.x, position.y, size.x, size.y);

        doorClosed = true;

        if (player.bounds.overlaps(bounds)) {
            buttonTouched = true;
            image = GameAssets.doorButtonActiveSprite;
        } else if (!player.bounds.overlaps(bounds)) {
            buttonTouched = false;
            image = GameAssets.doorButtonSprite;
        }

        switch (door.openingDirection) {

            case HORISONTAL:

                if (buttonTouched && door.size.x >= openSize.x && door.size.x >= 0) {
                    door.size.x -= door.openSpeed;
                    doorClosed = false;
                }

                if (!buttonTouched) {
                    if (door.size.x <= originalSize.x) {
                        door.size.x += door.closeSpeed;
                        doorClosed = false;
                    }
                }

                break;

            case VERTICAL:

                if (buttonTouched && door.size.y >= openSize.y && door.size.y >= 0) {
                    door.size.y -= door.openSpeed;
                    doorClosed = false;
                }

                if (!buttonTouched) {
                    if (door.size.y <= originalSize.y) {
                        door.size.y += door.closeSpeed;
                        doorClosed = false;
                    }
                }

                break;
        }

    }

    //=======================================================================================================================
    public void draw(SpriteBatch batch) {

        batch.draw(image, position.x, position.y, size.x, size.y);

    }

    //===============================================Getters and Setters======================================================
    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getSize() {
        return size;
    }

    public TextureRegion getImage() {
        return image;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Player getPlayer() {
        return player;
    }

    public float getBoundsXEnd() {
        return boundsXEnd;
    }

    public float getBoundsYEnd() {
        return boundsYEnd;
    }

//=========================================================================================================================
}
//=========================================================================================================================