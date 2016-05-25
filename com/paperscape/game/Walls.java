//============================================================================================================================//
package com.paperscape.game;
//============================================================================================================================//
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

//============================================================================================================================//
public class Walls {

    //two seperate Vector2 objects, they  hold X and Y values
    Vector2 position, size;
    //texture region finds an image in a sprite sheet
    public TextureRegion image;
    //used for detecting collisions and setting the on screen position of sprite
    public Rectangle bounds;
    //used for random number generation
    public Random random;
    public int wallImageInt;
    public float boundsXEnd, boundsYEnd;
    public Player player;

//============================================================================================================================//
    //constructor is called, it requires two Vector2 objects
    public Walls(Vector2 position, Vector2 size) {

        //setting position and size to whatever was passed through the constructor method
        this.position = position;
        this.size = size;

        //invisible rectangle surrounding object (X-position, Y-position, X-walls_image-size, Y-walls_image-size)
        bounds = new Rectangle(position.x, position.y, size.x, size.y);
        //add the position and size of the object together to find the the opposites sides of the bounds
        boundsXEnd = bounds.x + size.x;
        boundsYEnd = bounds.y + size.y;


//-------------------------------using RNG to choose an image for each wall----------------------------------------------------

        //a new random object to generate the numbers
        random = new Random();
        //the nextInt method creates 0 automatically and the amount specified, 1 is added to give the true value of how many numbers are generated
        wallImageInt = random.nextInt(3) + 1;

        switch(wallImageInt) {
//--------------------------------------------------------------------------------------------------
            case 1:

                image = GameAssets.wallSprite1;

                break;

//--------------------------------------------------------------------------------------------------
            case 2:

                image = GameAssets.wallSprite2;

                break;

//--------------------------------------------------------------------------------------------------
            case 3:

                image = GameAssets.wallSprite3;

                break;

//--------------------------------------------------------------------------------------------------
            case 4:

                image = GameAssets.wallSprite4;

                break;

//--------------------------------------------------------------------------------------------------
            case 5:

                image = GameAssets.wallSprite5;

                break;

//--------------------------------------------------------------------------------------------------

        }

    }

//============================================================================================================================//
    public void update(Player player) {

        this.player = player;
        bounds.set(position.x, position.y, size.x, size.y);


        //collision with player from the left
        if (player.bounds.overlaps(bounds) && (player.position.x < bounds.x)) {

            player.position.x = bounds.x - player.size.x;

        }

        //collision with player from the right
        else if (player.bounds.overlaps(bounds) && (player.boundsXEnd > boundsXEnd)) {

            player.position.x = boundsXEnd;

        }

        //collision with player from the bottom
        else if (player.bounds.overlaps(bounds) && (player.position.y < bounds.y)) {

            player.position.y = bounds.y - player.size.y;

        }

        //collision with player from the top
        else if (player.bounds.overlaps(bounds) && (player.boundsYEnd > boundsYEnd)) {

            player.position.y = boundsYEnd;

        }

    }

//============================================================================================================================//
    public void draw(SpriteBatch batch) {

        batch.draw(image, position.x, position.y, size.x, size.y);

    }

//============================================================================================================================//
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

//============================================================================================================================//
}
//============================================================================================================================//