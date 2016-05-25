//============================================================================================================================//
package com.paperscape.game;
//============================================================================================================================//
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

//============================================================================================================================//
public class OnScreenControls {

    public TextureRegion leftImage;
    public TextureRegion rightImage;
    public TextureRegion upImage;
    public TextureRegion downImage;

    public TextureRegion leftImagePressed;
    public TextureRegion rightImagePressed;
    public TextureRegion upImagePressed;
    public TextureRegion downImagePressed;

    public Rectangle leftBounds;
    public Rectangle rightBounds;
    public Rectangle upBounds;
    public Rectangle downBounds;

    public int left_right_width;
    public int left_right_height;
    public int up_down_width;
    public int up_down_height;

    public float leftBoundsX;
    public float leftBoundsY;
    public float rightBoundsX;
    public float rightBoundsY;
    public float upBoundsX;
    public float upBoundsY;
    public float downBoundsX;
    public float downBoundsY;
    public float leftBoundsXEnd;
    public float leftBoundsYEnd;
    public float rightBoundsXEnd;
    public float rightBoundsYEnd;
    public float upBoundsXEnd;
    public float upBoundsYEnd;
    public float downBoundsXEnd;
    public float downBoundsYEnd;

    //--------------------------------------------------------------------------------------------------------------------------//
    //constructor method
    public OnScreenControls() {

        leftBoundsX = 50;
        leftBoundsY = 40;
        rightBoundsX = 450;
        rightBoundsY = 40;
        upBoundsX = 1200;
        upBoundsY = 40;
        downBoundsX = 1575;
        downBoundsY = 40;

        //width & height for left and right levelSelectMenuButtons
        left_right_width = 300;
        left_right_height = 200;
        //width & height for up and down levelSelectMenuButtons
        up_down_width = 300;
        up_down_height = 200;

        leftImage = GameAssets.leftArrowSprite;
        leftImagePressed = GameAssets.leftArrowSpritePressed;
        //invisible rectangle surrounding sprite (X-position, Y-position, X-walls_image-size, Y-walls_image-size)
        leftBounds = new Rectangle(leftBoundsX, leftBoundsY, left_right_width, left_right_height);

        rightImage = GameAssets.rightArrowSprite;
        rightImagePressed = GameAssets.rightArrowSpritePressed;
        //invisible rectangle surrounding sprite (X-position, Y-position, X-walls_image-size, Y-walls_image-size)
        rightBounds = new Rectangle(rightBoundsX, rightBoundsY, left_right_width, left_right_height);

        upImage = GameAssets.upArrowSprite;
        upImagePressed = GameAssets.upArrowSpritePressed;
        //invisible rectangle surrounding sprite (X-position, Y-position, X-walls_image-size, Y-walls_image-size)
        upBounds = new Rectangle(upBoundsX, upBoundsY, up_down_width, up_down_height);

        downImage = GameAssets.downArrowSprite;
        downImagePressed = GameAssets.downArrowSpritePressed;
        //invisible rectangle surrounding sprite (X-position, Y-position, X-walls_image-size, Y-walls_image-size)
        downBounds = new Rectangle(downBoundsX, downBoundsY, up_down_width, up_down_height);


        //find the end of the sprite, used for tracking touches that are inside bounds
        leftBoundsXEnd = leftBounds.x + left_right_width;
        leftBoundsYEnd = leftBounds.y + left_right_height;

        rightBoundsXEnd = rightBounds.x + left_right_width;
        rightBoundsYEnd = rightBounds.y + left_right_height;

        upBoundsXEnd = upBounds.x + up_down_width;
        upBoundsYEnd = upBounds.y + up_down_height;

        downBoundsXEnd = downBounds.x + up_down_width;
        downBoundsYEnd = downBounds.y + up_down_height;

    }

//============================================================================================================================//
}
//============================================================================================================================//