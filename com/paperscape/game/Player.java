//============================================================================================================================//
package com.paperscape.game;
//============================================================================================================================//
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

//============================================================================================================================//
public class Player {

    public Vector2 position, size; //two seperate Vector2 objects, they  hold X and Y values

    public TextureRegion image;

    public Rectangle bounds;

    public float speed;

    public float boundsXEnd;
    public float boundsYEnd;

    public boolean isDead;

    public int starsCollected;

    //============================================================================================================================//
    public Player(Vector2 position, Vector2 size) {

        this.position = position;
        this.size = size;

        //invisible rectangle surrounding sprite (X-position, Y-position, X-walls_image-size, Y-walls_image-size)
        bounds = new Rectangle(position.x, position.y, size.x, size.y);
        image = GameAssets.playerSprite;
        speed = 8;
        isDead = false;
        starsCollected = 0;

    }

    //============================================================================================================================//
    public void update() {

        bounds.set(position.x, position.y, size.x, size.y);

        boundsXEnd = bounds.x + size.x;
        boundsYEnd = bounds.y + size.y;



    }

    //============================================================================================================================//
    public void draw(SpriteBatch batch) {

        if (!isDead) {

            batch.draw(image, bounds.x, bounds.y, size.x, size.y);

        }

    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Vector2 getSize() {
        return size;
    }

//============================================================================================================================//
}
//============================================================================================================================//