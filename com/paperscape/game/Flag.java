//============================================================================================================================//
package com.paperscape.game;
//============================================================================================================================//
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

//============================================================================================================================//
public class Flag {

    public Vector2 position, size;

    public TextureRegion image;
    public Rectangle bounds;
    public Player player;
    //static as is used to in Level class to draw certain fonts
    public static Boolean drawExit;
    public Boolean levelComplete;
    public float boundsXEnd, boundsYEnd;

    //============================================================================================================================//
    public Flag(Vector2 position, Vector2 size) {

        this.position = position;
        this.size = size;

        //invisible rectangle surrounding sprite (X-position, Y-position, X-walls_image-size, Y-walls_image-size)
        bounds = new Rectangle(position.x, position.y, size.x, size.y);
        image = GameAssets.creditsButtonSprite;
        drawExit = false;
        levelComplete = false;

    }

    //============================================================================================================================//
    public void update(Player player, int totalStars) {

        this.player = player;
        bounds.set(position.x, position.y, size.x, size.y);
        boundsXEnd = bounds.x + size.x;
        boundsYEnd = bounds.y + size.y;

        if (player.starsCollected == totalStars) {
            drawExit = true;
        }

        if (drawExit && (player.bounds.overlaps(bounds))) {
            levelComplete = true;
        }
    }

    //============================================================================================================================//
    public void draw(SpriteBatch batch) {

        if (drawExit) {
            batch.draw(GameAssets.flagCurrentFrame, bounds.x, bounds.y, size.x, size.y);
        }


    }

//============================================================================================================================//
}
//============================================================================================================================//