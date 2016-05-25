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
public class Enemy {

    //---------------------------------Initialise-variables---------------------------------------\\
    public Vector2 position, size, sightPosition, sightSize;
    public TextureRegion image, sightImage;
    public Rectangle bounds, sightBounds;
    public Rectangle[] waypointBounds;
    public Player player;
    public float speed, boundsXEnd, boundsYEnd, sightBoundsXEnd, sightBoundsYEnd;
    public String directionMoving;

    //values for total amount of waypoints, which one to go to next, and which set to follow
    int waypointCount, nextWaypoint, waypointsToFollow;
    //2-Dimensional Vector2 array to set position of waypoints for each enemy
    Vector2[][] waypoints;
    //used to check status of waypoints
    public Boolean waypointReached, resetWaypoints,
    //position of sight relative to enemy
    sightLeftEnemy, sightRightEnemy, sightAboveEnemy, sightBelowEnemy;

//============================================================================================================================//
    public Enemy(Vector2 position, Vector2 size, int waypointCount, Vector2[][] waypoints, int waypointsToFollow) {

        this.position = position;
        this.size = size;
        this.directionMoving = "NOT MOVING";
        this.waypointCount = waypointCount;
        this.waypointsToFollow = waypointsToFollow;
        this.waypoints = waypoints;
        waypointBounds = new Rectangle[waypointCount];
        //to start with a sight is positioned at the enemy position
        sightPosition = new Vector2(position.x, position.y);
        //size of sight is double the size of an enemy
        sightSize = new Vector2(size.x * 2, size.y * 2);

        //invisible rectangle surrounding sprite (X-position, Y-position, X-walls_image-size, Y-walls_image-size)
        bounds = new Rectangle(position.x, position.y, size.x, size.y);
        sightBounds = new Rectangle(sightPosition.x, sightPosition.y, sightSize.x, sightSize.y);

        //image used by enemy object is taken from GameAssets class
        image = GameAssets.enemySprite;
        //sight image is originally facing upwards
        sightImage = GameAssets.sightVerticalUpSprite;
        //how fast the enemy can move
        speed = 4;

        //the sight is initialised to have no position relative to the enemy
        sightLeftEnemy = false;
        sightRightEnemy = false;
        sightBelowEnemy = false;
        sightAboveEnemy = false;

        //----------------------------Initialising Waypoints----------------------------------------
        //create a rectangle for each waypoint
        for (int i = 0; i < waypointCount; i++) {
            waypointBounds[i] = new Rectangle(waypoints[waypointsToFollow][i].x, waypoints[waypointsToFollow][i].y, size.x, size.y);
        }

        //arrays start at 0, so the first waypoint has a value of 0
        nextWaypoint = 0;
        //the first waypoint has not yet been reached
        waypointReached = false;
        //no need to reset the waypoints until they have all been reached
        resetWaypoints = false;

    }

//============================================================================================================================//
    public void draw(SpriteBatch batch) {

        //draw the enemy to the screen via passed spritebatch
        batch.draw(image, bounds.x, bounds.y, size.x, size.y);
        batch.draw(sightImage, sightBounds.x, sightBounds.y, sightSize.x, sightSize.y);

    }

//============================================================================================================================//
    //used for checking the position of the waypoints for each enemy
    public void renderShapes(ShapeRenderer shapeRenderer, int enemyNumber) {

        //set the waypoints to a different colour, depending on the enemy array number
        switch (enemyNumber) {
            case 0:
                shapeRenderer.setColor(Color.RED);
                break;
            case 1:
                shapeRenderer.setColor(Color.BLUE);
                break;
            case 2:
                shapeRenderer.setColor(Color.ORANGE);
                break;
            case 3:
                shapeRenderer.setColor(Color.CYAN);
                break;
            case 4:
                shapeRenderer.setColor(Color.PURPLE);
                break;
        }

        //render all waypoints to the screen
        for (int i = 0; i < waypointCount; i++) {
            //render a rectangle object at the bounds of each waypoint at the size of an enemy
            shapeRenderer.rect(waypointBounds[i].x, waypointBounds[i].y, size.x, size.y);
        }

        //draw a rectangle for each enemy, it is the same colour at that enemy's waypoints
        for (int i = 0; i <= enemyNumber; i++) {
            shapeRenderer.rect(bounds.x, bounds.y, size.x, size.y);
        }

    }

//============================================================================================================================//
    public void update(Player player) {

        //Gdx.app.log("Update Method", "****starting****");
        this.player = player;
        //set bounds of enemy object
        bounds.set(position.x, position.y, size.x, size.y);
        //calculate position of opposite edge of object
        boundsXEnd = bounds.x + size.x;
        boundsYEnd = bounds.y + size.y;
        //find the end of the sight by adding the current value of the shapes position to its size
        sightBoundsXEnd = sightBounds.x + sightSize.x;
        sightBoundsYEnd = sightBounds.y + sightSize.y;

        //write the position of enemy to console
        //Gdx.app.log("Enemy Bounds X", Float.toString(position.x));
        //Gdx.app.log("Enemy Bounds Y", Float.toString(position.y));

        //-------------------------------Enemy Sight------------------------------------------------
        if (sightBelowEnemy) {
            sightBounds.set(getPosition().x - (getSize().x / 2), (getBounds().y - getSightSize().x), getSightSize().x, getSightSize().y);
            sightImage = GameAssets.sightVerticalDownSprite;
        }

        if (sightAboveEnemy) {
            sightBounds.set(getPosition().x - (getSize().x / 2), boundsYEnd, getSightSize().x, getSightSize().y);
            sightImage = GameAssets.sightVerticalUpSprite;
        }

        if (sightLeftEnemy) {
            sightBounds.set(bounds.x - getSightSize().x, getPosition().y - (getSize().y / 2), getSightSize().x, getSightSize().y);
            sightImage = GameAssets.sightHorisontalLeftSprite;
        }

        if (sightRightEnemy) {
            sightBounds.set(boundsXEnd, getPosition().y - (getSize().y / 2), getSightSize().x, getSightSize().y);
            sightImage = GameAssets.sightHorisontalRightSprite;
        }

        //--------------------------------Waypoints-------------------------------------------------
        //setting bounds rectangle for each waypoint
        for (int i = 0; i < waypointCount; i++) {
            waypointBounds[i].set(waypoints[waypointsToFollow][i].x, waypoints[waypointsToFollow][i].y, size.x, size.y);
        }

        //what to do when resetWaypoints is true
        if (resetWaypoints) {
            //set the nextWaypoint value to 0, the enemy will go towards the first waypoint in their set
            nextWaypoint = 0;
            //sets itself to false
            resetWaypoints = false;
            //write data to the console
            //Gdx.app.log("Waypoints", "RESET!");
            //Gdx.app.log("Next Waypoint", Integer.toString(nextWaypoint));
        }

        //if a waypoint is not yet reached, run the following code
        if (!waypointReached) {

            //Gdx.app.log("Waypoints", "Not Reached, moving " + directionMoving + " to waypoint " + Integer.toString(nextWaypoint));
            //checking which direction to move enemy
            if (position.x > waypoints[waypointsToFollow][nextWaypoint].x) {
                directionMoving = "LEFT";
                    sightLeftEnemy = true;
                    sightRightEnemy = false;
                    sightAboveEnemy = false;
                    sightBelowEnemy = false;
            } else if (position.x < waypoints[waypointsToFollow][nextWaypoint].x) {
                directionMoving = "RIGHT";
                    sightLeftEnemy = false;
                    sightRightEnemy = true;
                    sightAboveEnemy = false;
                    sightBelowEnemy = false;
            }

            if (position.y < waypoints[waypointsToFollow][nextWaypoint].y) {
                directionMoving = "UP";
                sightLeftEnemy = false;
                sightRightEnemy = false;
                sightAboveEnemy = true;
                sightBelowEnemy = false;
            } else if (position.y > waypoints[waypointsToFollow][nextWaypoint].y) {
                directionMoving = "DOWN";
                sightLeftEnemy = false;
                sightRightEnemy = false;
                sightAboveEnemy = false;
                sightBelowEnemy = true;
            }

            if (bounds.overlaps(waypointBounds[nextWaypoint])) {
                waypointReached = true;
            }

            //what to do when a waypoint is reached by enemy
            if (waypointReached) {
                //Gdx.app.log("Waypoint", "Reached!");
                //nextWaypoint = nextWaypoint + 1
                nextWaypoint++;
                //set itself back to false, so next waypoint becomes active
                waypointReached = false;
                //Gdx.app.log("Next Waypoint", Integer.toString(nextWaypoint));

                //the the value of the next waypoint does not exist, reset the waypoints
                if (nextWaypoint >= waypointCount) {
                    resetWaypoints = true;
                }
            }

            //if the player sprite touches the enemy or enemy sight sprites then execute the code
            if (player.bounds.overlaps(bounds) || player.bounds.overlaps(sightBounds)) {
                player.isDead = true;
            }

        }
    }

//===================================Getters and Setters======================================================================//
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
    public Vector2 getSightSize() {
        return sightSize;
    }

//============================================================================================================================//
}
//============================================================================================================================//