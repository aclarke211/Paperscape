//============================================================================================================================//
package com.paperscape.game;
//============================================================================================================================//
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

//============================================================================================================================//
public class Star {

    Vector2 position, size;
    public TextureRegion image;
    public Rectangle bounds;
    public Player player;
    public Boolean isCollected;
    public float boundsXEnd, boundsYEnd;
    int starCount;
    Vector2[] starDisplayPosition;
    public Rectangle[] waypointBounds;
    public float speed;


//============================================================================================================================//
    public Star(Vector2 position, Vector2 size, int starCount) {

        this.position = position;
        this.size = size;
        this.starCount = starCount;
        waypointBounds = new Rectangle[starCount];

        //invisible rectangle surrounding sprite (X-position, Y-position, X-walls_image-size, Y-walls_image-size)
        bounds = new Rectangle(position.x, position.y, size.x, size.y);
        image = GameAssets.lockedLevelSprite;
        isCollected = false;
        speed = 5;

        //array holding positions for each star after it has been collected
        starDisplayPosition = new Vector2[starCount];
        //find where to move each star when collected, the position is different depending on the star count
        switch (starCount) {
            case 1:
                starDisplayPosition[0] = new Vector2((GameAssets.screenResolutionX / 2) - (size.x / 2), 100);
                break;
            case 2:
                starDisplayPosition[0] = new Vector2((GameAssets.screenResolutionX / 2) - size.x, 100);
                starDisplayPosition[1] = new Vector2((GameAssets.screenResolutionX / 2) + (size.x / 2), 100);
                break;
            case 3:
                starDisplayPosition[0] = new Vector2((GameAssets.screenResolutionX / 2) - (size.x * 2) + 20, 100);
                starDisplayPosition[1] = new Vector2((GameAssets.screenResolutionX / 2) - (size.x / 2) + 20, 100);
                starDisplayPosition[2] = new Vector2((GameAssets.screenResolutionX / 2) + (size.x + 20), 100);
                break;
            case 4:
                starDisplayPosition[0] = new Vector2((GameAssets.screenResolutionX / 2) - size.x, 150);
                starDisplayPosition[1] = new Vector2((GameAssets.screenResolutionX / 2) + (size.x / 2), 150);
                starDisplayPosition[2] = new Vector2((GameAssets.screenResolutionX / 2) - size.x, 50);
                starDisplayPosition[3] = new Vector2((GameAssets.screenResolutionX / 2) + (size.x / 2), 50);
                break;
            case 5:
                starDisplayPosition[0] = new Vector2((GameAssets.screenResolutionX / 2) - (size.x * 2) + 20, 150);
                starDisplayPosition[1] = new Vector2((GameAssets.screenResolutionX / 2) - (size.x / 2) + 20, 150);
                starDisplayPosition[2] = new Vector2((GameAssets.screenResolutionX / 2) + (size.x + 20), 150);
                starDisplayPosition[3] = new Vector2((GameAssets.screenResolutionX / 2) - size.x, 50);
                starDisplayPosition[4] = new Vector2((GameAssets.screenResolutionX / 2) + (size.x / 2), 50);
                break;
        }
    }

//============================================================================================================================//
    public void update(Player player) {

        this.player = player;

        bounds.set(position.x, position.y, size.x, size.y);

        boundsXEnd = bounds.x + size.x;
        boundsYEnd = bounds.y + size.y;


        //if a star has not yet been collected and it is touched by the player
        if (!isCollected && (player.bounds.overlaps(bounds))) {

            GameAssets.playSoundEffect(GameAssets.STAR_PICK_UP_SOUND_INT);

            isCollected = true;
            player.starsCollected++;

        }
    }

//============================================================================================================================//
    public void draw(SpriteBatch batch) {

        //if a star has not been touched by player, draw it to the screen
        //if (!isCollected) {
            batch.draw(GameAssets.starCurrentFrame, bounds.x, bounds.y, size.x, size.y);
        //}
    }

//============================================================================================================================//
    public void renderShapes(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.LIGHT_GRAY);

        for (int i = 0; i < starCount; i++) {
            shapeRenderer.rect(starDisplayPosition[i].x, starDisplayPosition[i].y, size.x, size.y);
        }
    }

//==========================================Getters and Setters===============================================================//
    public Rectangle getBounds() {
        return bounds;
    }

//============================================================================================================================//
    public Vector2 getSize() {
        return size;
    }

//============================================================================================================================//
    public Vector2 getPosition() {
        return position;
    }

//============================================================================================================================//
}
//============================================================================================================================//