package com.paperscape.game;
//============================================================================================================================//
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

//============================================================================================================================//
public class LevelManager {

    //used to create on screen player controls
    OnScreenControls guiControls;
    //track animation time
    float stateTime, buttonTextWidth, buttonTextHeight;
    //---------------------------
    Player player;
    Vector2 playerStartPosition, playerSize, flagSize, enemySize, starSize, activateDoorButtonSize,
            pauseButtonSize, toggleSoundButtonSize, levelSelectMenuButtonSize, restartButtonSize;
    //---------------------------
    //images to on screen controls
    public TextureRegion leftImage, rightImage, upImage, downImage,
                         //images for buttons
                         pauseButtonImage = GameAssets.pauseButtonSprite,
                         toggleSoundButtonImage = GameAssets.soundOnSprite,
                         levelSelectMenuButtonImage = GameAssets.levelSelectMenuSprite,
                         restartButtonImage = GameAssets.restartButtonSprite;
    //---------------------------
    int currentLevel, standardWallSize,
                            //initialising default number of objects in a level
                            boundsCount = 4, enemyCount = 0, waypointCount = 0, wallCount = 0, starCount = 0, flagCount = 0,
                                    underlineFontCount = 0, doorCount = 0, activateDoorButtonCount = 0,
                            //default number of buttons in a level
                            pauseButtonCount = 1, toggleSoundButtonCount = 1, levelSelectMenuButtonCount = 1, restartButtonCount = 1;

    //--------------/Arrays\--------------------
    Enemy[] enemies;
    Walls[] bounds, walls, underlineFonts;
    Door[] doors;
    float[] doorCloseSpeed;
    int[] doorOpenDirection;
    ActivateDoorButton[] activateDoorButtons;
    Star[] stars;
    Flag[] flags;
    Button[] pauseButtons, toggleSoundButtons, levelSelectMenuButtons, restartButtons;
    Vector2[] enemyStartPosition, boundsPosition, boundsSize, wallPosition, wallSize, starsPosition, flagPosition,
              pauseButtonPosition, toggleSoundButtonPosition, levelSelectMenuButtonPosition, underlineFontPosition, underlineFontSize,
            doorPosition, doorSize, activateDoorButtonPosition, restartButtonPosition;
    //2-dimensional array for holding which enemy should follow which waypoints
    Vector2[][] waypoints;

//============================================================================================================================//
    public LevelManager(int currentLevel, OrthographicCamera camera) {

        //determining the level to be loaded
        this.currentLevel = currentLevel;
        //receiving a camera from the Level Class and initialising it
        GameAssets.camera = camera;
        //stateTime is the time since an animation has started - Initialised at 0
        stateTime = 0F;
        //load the animations for objects
        GameAssets.starAnimation();
        GameAssets.flagAnimation();
        //begin playing the levels background music
        GameAssets.playMusic(GameAssets.LEVEL_MUSIC_INT);
        //floats for text displayed on a button
        buttonTextWidth = 70;
        buttonTextHeight = 25;

        //setting the size of the on-screen objects
        playerSize = new Vector2(50, 50);
        enemySize = new Vector2(50, 50);
        starSize = new Vector2(80, 80);
        //defualt width or height of a wall - used for consitancy between walls
        standardWallSize = 20;
        //size of the buttons that open doors in levels
        activateDoorButtonSize = new Vector2(60, 60);

        //initialising an exit flag for a level, it is an array incase more than one exit is present
        //amount of exit flags in a level
        flagCount = 1;
        //size of exit flags
        flagSize = new Vector2(100, 100);
        //Vector 2 array to hold the position of any flags
        flagPosition = new Vector2[flagCount];

        //size of buttons used for pause menu in level
        pauseButtonSize = new Vector2(120, 120);
        toggleSoundButtonSize = new Vector2(250, 250);
        levelSelectMenuButtonSize = new Vector2(250, 250);
        restartButtonSize = new Vector2(250, 250);

        //the wall object to act as an underline for "PAUSED" text in pause menu
        underlineFontCount = 1;
            underlineFontSize = new Vector2[underlineFontCount];
            underlineFontPosition = new Vector2[underlineFontCount];
                underlineFontSize[0] = new Vector2(1000, standardWallSize);
                underlineFontPosition[0] = new Vector2(GameAssets.screenBounds_left.getX() + 440, 830);

//====================================================================================================================\\
/**********************************************************************************************
 **** LEVELS - each case represents the level number and what to include in a certain level ***
 *********************************************************************************************/
//====================================================================================================================\\

        //each case is a seperate level that can be loaded
        switch (currentLevel) {

//--------------------------------------------------------------------------------------------------

            //** THIS IS A BLANK LEVEL TEMPLATE; USED TO CREATE NEW LEVELS MORE CONVENIENTLY **\\

            case 0:

/*                //the co-ordinates of where the player will spawn
                playerStartPosition = new Vector2();
                //the co-ordinates of where the exit will spawn once activated
                flagPosition[0] = new Vector2();*/

/*                //the number of walls in the level
                wallCount = 5;
                    //arrays for size and position of each wall
                    wallSize = new Vector2[wallCount]; wallPosition = new Vector2[wallCount];
                        //wall #01
                        wallSize[0] = new Vector2();
                        wallPosition[0] = new Vector2();
                        //wall #02
                        wallSize[1] = new Vector2();
                        wallPosition[1] = new Vector2();
                        //wall #03
                        wallSize[2] = new Vector2();
                        wallPosition[2] = new Vector2();
                        //wall #04
                        wallSize[3] = new Vector2();
                        wallPosition[3] = new Vector2();
                        //wall #05
                        wallSize[4] = new Vector2();
                        wallPosition[4] = new Vector2();*/

/*                //the number of doors in a level; the number of buttons used to open doors in a level
                doorCount = 2; activateDoorButtonCount = doorCount;
                    //arrays to hold the position and size of each door
                    doorSize = new Vector2[doorCount]; doorPosition = new Vector2[doorCount];
                    //arrays to hold the speed and direction which each door will open
                    doorCloseSpeed = new float[doorCount]; doorOpenDirection = new int[doorCount];
                    //array to hold the poistion of each of the buttons used to open a door
                    activateDoorButtonPosition = new Vector2[activateDoorButtonCount];
                        //door #01
                        doorSize[0] = new Vector2();
                        doorPosition[0] = new Vector2();
                            activateDoorButtonPosition[0] = new Vector2();
                            doorCloseSpeed[0] = 0f;
                            doorOpenDirection[0] = ActivateDoorButton.HORISONTAL;
                        //door #02
                        doorSize[1] = new Vector2();
                        doorPosition[1] = new Vector2();
                            activateDoorButtonPosition[1] = new Vector2();
                            doorCloseSpeed[1] = 0f;
                            doorOpenDirection[1] = ActivateDoorButton.VERTICAL;*/

/*                //the number of stars in a level
                starCount = 5;
                    //array that holds the position of each star
                    starsPosition = new Vector2[starCount];
                        starsPosition[0] = new Vector2();
                        starsPosition[1] = new Vector2();
                        starsPosition[2] = new Vector2();
                        starsPosition[3] = new Vector2();
                        starsPosition[4] = new Vector2();*/

/*                //number of enemies present in level; number of waypoints for each enemy
                enemyCount = 5; waypointCount = 6;
                    //Vector2 array to hold the start position of each enemy
                    enemyStartPosition = new Vector2[enemyCount];
                    //2-D Vector2 array to hold the position of waypoints for each enemy, [enemyNumber][nextWaypoint]
                    waypoints = new Vector2[enemyCount][waypointCount];
                        //enemy #01
                        enemyStartPosition[0] = new Vector2();
                            waypoints[0][0] = new Vector2();
                            waypoints[0][1] = new Vector2();
                            waypoints[0][2] = new Vector2();
                            waypoints[0][3] = new Vector2();
                            waypoints[0][4] = new Vector2();
                            waypoints[0][5] = new Vector2();
                            waypoints[0][6] = new Vector2();
                        //enemy #02
                        enemyStartPosition[1] = new Vector2();
                            waypoints[1][0] = new Vector2();
                            waypoints[1][1] = new Vector2();
                            waypoints[1][2] = new Vector2();
                            waypoints[1][3] = new Vector2();
                            waypoints[1][4] = new Vector2();
                            waypoints[1][5] = new Vector2();
                            waypoints[1][6] = new Vector2();
                        //enemy #03
                        enemyStartPosition[2] = new Vector2();
                            waypoints[2][0] = new Vector2();
                            waypoints[2][1] = new Vector2();
                            waypoints[2][2] = new Vector2();
                            waypoints[2][3] = new Vector2();
                            waypoints[2][4] = new Vector2();
                            waypoints[2][5] = new Vector2();
                            waypoints[2][6] = new Vector2();
                        //enemy #04
                        enemyStartPosition[3] = new Vector2();
                            waypoints[3][0] = new Vector2();
                            waypoints[3][1] = new Vector2();
                            waypoints[3][2] = new Vector2();
                            waypoints[3][3] = new Vector2();
                            waypoints[3][4] = new Vector2();
                            waypoints[3][5] = new Vector2();
                            waypoints[3][6] = new Vector2();
                        //enemy #05
                        enemyStartPosition[4] = new Vector2();
                            waypoints[4][0] = new Vector2();
                            waypoints[4][1] = new Vector2();
                            waypoints[4][2] = new Vector2();
                            waypoints[4][3] = new Vector2();
                            waypoints[4][4] = new Vector2();
                            waypoints[4][5] = new Vector2();
                            waypoints[4][6] = new Vector2();*/

                break;

//--------------------------------------------------------------------------------------------------
            case 1:

                //co-ordinates where player will spawn
                playerStartPosition = new Vector2(90, 660);
                //co-ordinates where exit will spawn
                flagPosition[0] = new Vector2((GameAssets.screenBounds_right.getX() - 200), 630);

                //the number of wall included in the level
                wallCount = 5;
                    //arrays for size and position of each wall
                    wallSize = new Vector2[wallCount]; wallPosition = new Vector2[wallCount];
                        //wall #01
                        wallSize[0] = new Vector2(2030, standardWallSize);
                        wallPosition[0] = new Vector2(-20, 840);
                        //wall #02
                        wallSize[1] = new Vector2(840, standardWallSize);
                        wallPosition[1] = new Vector2(-20, 540);
                        //wall #03
                        wallSize[2] = new Vector2(standardWallSize, 270);
                        wallPosition[2] = new Vector2(800, GameAssets.screenBounds_bottom.y);
                        //wall #04
                        wallSize[3] = new Vector2(standardWallSize, 260);
                        wallPosition[3] = new Vector2(1100, GameAssets.screenBounds_bottom.y);
                        //wall #05
                        wallSize[4] = new Vector2(900, standardWallSize);
                        wallPosition[4] = new Vector2(1100, 540);

                //the number of stars included in the level
                starCount = 1;
                    //array to store the position of all the stars
                    starsPosition = new Vector2[starCount];
                        starsPosition[0] = new Vector2(920, 350);

                //number of enemies present in level; number of waypoints for each enemy
                enemyCount = 1; waypointCount = 4;
                    //Vector2 array to hold the start position of each enemy
                    enemyStartPosition = new Vector2[enemyCount];
                    //2-D Vector2 array to hold the position of waypoints for each enemy, [enemyNumber][nextWaypoint]
                    waypoints = new Vector2[enemyCount][waypointCount];
                            //enemy #01
                            enemyStartPosition[0] = new Vector2((GameAssets.screenBounds_right.getX() - 240), 675);
                                waypoints[0][0] = new Vector2(890, 675);
                                waypoints[0][1] = new Vector2(890, 440);
                                waypoints[0][2] = new Vector2(890, 675);
                                waypoints[0][3] = new Vector2(1650, 675);

                break;

//--------------------------------------------------------------------------------------------------
            case 2:

                //co-ordinates where player will spawn
                playerStartPosition = new Vector2(80, (GameAssets.screenBounds_top.getY() - playerSize.y) - 40);
                //co-ordinates where exit will spawn
                flagPosition[0] = new Vector2(40, 940);

                //the number of wall included in the level
                wallCount = 5;
                    //arrays for size and position of each wall
                    wallSize = new Vector2[wallCount]; wallPosition = new Vector2[wallCount];
                         //wall #01
                         wallSize[0] = new Vector2(standardWallSize, 550);
                         wallPosition[0] = new Vector2(220, (GameAssets.screenBounds_top.getY() - wallSize[0].y) + 20);
                         //wall #02
                         wallSize[1] = new Vector2(350, standardWallSize);
                         wallPosition[1] = new Vector2(530, 750);
                         //wall #03
                         wallSize[2] = new Vector2(standardWallSize, 350);
                         wallPosition[2] = new Vector2(860, (GameAssets.screenBounds_top.getY() - wallSize[2].y) + 20);
                         //wall #04
                         wallSize[3] = new Vector2(standardWallSize, 350);
                         wallPosition[3] = new Vector2(1320, GameAssets.screenBounds_bottom.getY());
                         //wall #05
                         wallSize[4] = new Vector2(350, standardWallSize);
                         wallPosition[4] = new Vector2(1320, 620);

                //the number of stars included in the level
                starCount = 2;
                    //array to store the position of all the stars
                    starsPosition = new Vector2[starCount];
                        starsPosition[0] = new Vector2(650, 890);
                        starsPosition[1] = new Vector2(1480, 420);

                //number of enemies present in level; number of waypoints for each enemy
                enemyCount = 3; waypointCount = 6;
                    //Vector2 array to hold the start position of each enemy
                    enemyStartPosition = new Vector2[enemyCount];
                    //2-D Vector2 array to hold the position of waypoints for each enemy, [enemyNumber][nextWaypoint]
                    waypoints = new Vector2[enemyCount][waypointCount];
                        //enemy #01
                        enemyStartPosition[0] = new Vector2(75, 500);
                            waypoints[0][0] = new Vector2(75, 750);
                            waypoints[0][1] = new Vector2(420, 420);
                            waypoints[0][2] = new Vector2(670, 900);
                            waypoints[0][3] = new Vector2(370, 900);
                            waypoints[0][4] = new Vector2(370, 400);
                            waypoints[0][5] = new Vector2(55, 400);
                        //enemy #02
                        enemyStartPosition[1] = new Vector2(990, 440);
                            waypoints[1][0] = new Vector2(530, 440);
                            waypoints[1][1] = new Vector2(1130, 440);
                            waypoints[1][2] = new Vector2(1130, 880);
                            waypoints[1][3] = new Vector2(1700, 880);
                            waypoints[1][4] = new Vector2(1130, 880);
                            waypoints[1][5] = new Vector2(990, 440);
                        //enemy #03
                        enemyStartPosition[2] = new Vector2(1760, 370);
                            waypoints[2][0] = new Vector2(1760, 720);
                            waypoints[2][1] = new Vector2(1300, 730);
                            waypoints[2][2] = new Vector2(1760, 730);
                            waypoints[2][3] = new Vector2(1760, 420);
                            waypoints[2][4] = new Vector2(1500, 430);
                            waypoints[2][5] = new Vector2(1800, 430);

                break;

//--------------------------------------------------------------------------------------------------
            case 3:

                //co-ordinates where player will spawn
                playerStartPosition = new Vector2(1720, (GameAssets.screenBounds_top.getY() - playerSize.y) - 80);
                //co-ordinates where exit will spawn
                flagPosition[0] = new Vector2(810, 710);

                //the number of wall included in the level
                wallCount = 5;
                    //arrays for size and position of each wall
                    wallSize = new Vector2[wallCount]; wallPosition = new Vector2[wallCount];
                        //wall #01
                        wallSize[0] = new Vector2(standardWallSize, 300);
                        wallPosition[0] = new Vector2(280, (GameAssets.screenBounds_top.getY() - wallSize[0].y) + 20);
                        //wall #02
                        wallSize[1] = new Vector2(standardWallSize, 300);
                        wallPosition[1] = new Vector2(280, GameAssets.screenBounds_bottom.getY());
                        //wall #03
                        wallSize[2] = new Vector2(350, standardWallSize);
                        wallPosition[2] = new Vector2(700, 650);
                        //wall #04
                        wallSize[3] = new Vector2(standardWallSize, 200);
                        wallPosition[3] = new Vector2(1630, 490);
                        //wall #05
                        wallSize[4] = new Vector2(600, standardWallSize);
                        wallPosition[4] = new Vector2((GameAssets.screenBounds_right.getX() - wallSize[4].x) + 20, 855);

                //the number of stars included in the level
                starCount = 3;
                    //array to store the position of all the stars
                    starsPosition = new Vector2[starCount];
                        starsPosition[0] = new Vector2(95, 930);
                        starsPosition[1] = new Vector2(860, 470);
                        starsPosition[2] = new Vector2(1760, 370);

                //number of enemies present in level; number of waypoints for each enemy
                enemyCount = 3; waypointCount = 6;
                    //Vector2 array to hold the start position of each enemy
                    enemyStartPosition = new Vector2[enemyCount];
                    //2-D Vector2 array to hold the position of waypoints for each enemy, [enemyNumber][nextWaypoint]
                    waypoints = new Vector2[enemyCount][waypointCount];
                        //enemy #01
                        enemyStartPosition[0] = new Vector2(110, 650);
                            waypoints[0][0] = new Vector2(500, 650);
                            waypoints[0][1] = new Vector2(80, 650);
                            waypoints[0][2] = new Vector2(80, 450);
                            waypoints[0][3] = new Vector2(80, 860);
                            waypoints[0][4] = new Vector2(80, 430);
                            waypoints[0][5] = new Vector2(150, 430);
                        //enemy #02
                        enemyStartPosition[1] = new Vector2(1120, 400);
                            waypoints[1][0] = new Vector2(1120, 750);
                            waypoints[1][1] = new Vector2(540, 760);
                            waypoints[1][2] = new Vector2(540, 480);
                            waypoints[1][3] = new Vector2(600, 480);
                            waypoints[1][4] = new Vector2(700, 480);
                            waypoints[1][5] = new Vector2(1140, 480);
                        //enemy #03
                        enemyStartPosition[2] = new Vector2(1550, 400);
                            waypoints[2][0] = new Vector2(1150, 400);
                            waypoints[2][1] = new Vector2(1160, 920);
                            waypoints[2][2] = new Vector2(1160, 720);
                            waypoints[2][3] = new Vector2(1800, 720);
                            waypoints[2][4] = new Vector2(1800, 400);
                            waypoints[2][5] = new Vector2(1550, 400);

                break;

//--------------------------------------------------------------------------------------------------
            case 4:

                //co-ordinates where player will spawn
                playerStartPosition = new Vector2(GameAssets.screenBounds_left.getX() + 30, 1000);
                //co-ordinates where exit will spawn
                flagPosition[0] = new Vector2(70, 340);

                wallCount = 4;
                    //arrays for size and position of each wall
                    wallSize = new Vector2[wallCount]; wallPosition = new Vector2[wallCount];
                        //wall #01
                        wallSize[0] = new Vector2(600, standardWallSize);
                        wallPosition[0] = new Vector2(GameAssets.screenBounds_left.getX(), 900);
                        //wall #02
                        wallSize[1] = new Vector2(standardWallSize, 200);
                        wallPosition[1] = new Vector2(300, GameAssets.screenBounds_bottom.getY());
                        //wall #03
                        wallSize[2] = new Vector2(standardWallSize, 240);
                        wallPosition[2] = new Vector2(1200, GameAssets.screenBounds_top.getY() - wallSize[2].y);
                        //wall #04
                        wallSize[3] = new Vector2(700, standardWallSize);
                        wallPosition[3] = new Vector2(GameAssets.screenBounds_right.getX() - (wallSize[3].x - 10), 520);

                //the number of stars included in the level
                starCount = 4;
                    //array to store the position of all the stars
                    starsPosition = new Vector2[starCount];
                        starsPosition[0] = new Vector2(120, 760);
                        starsPosition[1] = new Vector2(400, 350);
                        starsPosition[2] = new Vector2(1500, 930);
                        starsPosition[3] = new Vector2(1760, 370);

                //number of enemies present in level; number of waypoints for each enemy
                enemyCount = 3; waypointCount = 6;
                    //Vector2 array to hold the start position of each enemy
                    enemyStartPosition = new Vector2[enemyCount];
                    //2-D Vector2 array to hold the position of waypoints for each enemy, [enemyNumber][nextWaypoint]
                    waypoints = new Vector2[enemyCount][waypointCount];
                        //enemy #01
                        enemyStartPosition[0] = new Vector2(85, 700);
                            waypoints[0][0] = new Vector2(85, 740);
                            waypoints[0][1] = new Vector2(460, 740);
                            waypoints[0][2] = new Vector2(460, 400);
                            waypoints[0][3] = new Vector2(460, 740);
                            waypoints[0][4] = new Vector2(85, 740);
                            waypoints[0][5] = new Vector2(85, 400);
                        //enemy #02
                        enemyStartPosition[1] = new Vector2(1070, 980);
                            waypoints[1][0] = new Vector2(1070, 400);
                            waypoints[1][1] = new Vector2(1710, 400);
                            waypoints[1][2] = new Vector2(800, 400);
                            waypoints[1][3] = new Vector2(800, 980);
                            waypoints[1][4] = new Vector2(160, 980);
                            waypoints[1][5] = new Vector2(1070, 980);
                        //enemy #03
                        enemyStartPosition[2] = new Vector2(1340, 640);
                            waypoints[2][0] = new Vector2(1340, 940);
                            waypoints[2][1] = new Vector2(1740, 940);
                            waypoints[2][2] = new Vector2(1740, 640);
                            waypoints[2][3] = new Vector2(1340, 640);
                            waypoints[2][4] = new Vector2(1000, 640);
                            waypoints[2][5] = new Vector2(1340, 640);

                break;

//--------------------------------------------------------------------------------------------------
            case 5:

                //co-ordinates where player will spawn
                playerStartPosition = new Vector2((GameAssets.screenResolutionX / 2) - (playerSize.x / 2), (GameAssets.screenBounds_top.getY() - playerSize.y) - 20);
                //co-ordinates where exit will spawn
                flagPosition[0] = new Vector2(((GameAssets.screenResolutionX / 2) - flagSize.x) + 40, 920);

                //the number of walls in the level
                wallCount = 5;
                    //arrays for size and position of each wall
                    wallSize = new Vector2[wallCount]; wallPosition = new Vector2[wallCount];
                        //wall #01
                        wallSize[0] = new Vector2(550, standardWallSize);
                        wallPosition[0] = new Vector2(GameAssets.screenBounds_left.getX(), 600);
                        //wall #02
                        wallSize[1] = new Vector2(standardWallSize, 300);
                        wallPosition[1] = new Vector2((GameAssets.screenResolutionX / 2) - (wallSize[1].x / 2) - 115, GameAssets.screenBounds_top.getY() - wallSize[1].y);
                        //wall #03
                        wallSize[2] = new Vector2(standardWallSize, 250);
                        wallPosition[2] = new Vector2((GameAssets.screenResolutionX / 2) - (wallSize[2].x / 2), GameAssets.screenBounds_bottom.getY());
                        //wall #04
                        wallSize[3] = new Vector2(standardWallSize, 300);
                        wallPosition[3] = new Vector2((GameAssets.screenResolutionX / 2) - (wallSize[3].x / 2) + 115, GameAssets.screenBounds_top.getY() - wallSize[3].y);
                        //wall #05
                        wallSize[4] = new Vector2(550, standardWallSize);
                        wallPosition[4] = new Vector2((GameAssets.screenBounds_right.getX() - wallSize[4].x) + 20, 600);

                //the number of stars in a level
                starCount = 5;
                    starsPosition = new Vector2[starCount];
                        starsPosition[0] = new Vector2((GameAssets.screenBounds_left.getX() + 140), (GameAssets.screenBounds_top.getY() - (starSize.x) - 40));
                        starsPosition[1] = new Vector2((GameAssets.screenBounds_left.getX() + 40), (GameAssets.screenBounds_bottom.getY() + 100));
                        starsPosition[2] = new Vector2((GameAssets.screenResolutionX / 2 - (starSize.x / 2)), 620);
                        starsPosition[3] = new Vector2(((GameAssets.screenBounds_right.getX() - (starSize.x)) - 140), (GameAssets.screenBounds_top.getY() - (starSize.x) - 40));
                        starsPosition[4] = new Vector2(((GameAssets.screenBounds_right.getX() - (starSize.x)) - 40), (GameAssets.screenBounds_bottom.getY() + 100));

                //number of enemies present in level; number of waypoints for each enemy
                enemyCount = 3; waypointCount = 7;
                    //Vector2 array to hold the start position of each enemy
                    enemyStartPosition = new Vector2[enemyCount];
                    //2-D Vector2 array to hold the position of waypoints for each enemy, [enemyNumber][nextWaypoint]
                    waypoints = new Vector2[enemyCount][waypointCount];
                        //enemy #01
                        enemyStartPosition[0] = new Vector2(700, 370);
                            waypoints[0][0] = new Vector2(700, 730);
                            waypoints[0][1] = new Vector2(200, 730);
                            waypoints[0][2] = new Vector2(200, 950);
                            waypoints[0][3] = new Vector2(700, 730);
                            waypoints[0][4] = new Vector2(700, 400);
                            waypoints[0][5] = new Vector2(200, 410);
                            waypoints[0][6] = new Vector2(700, 410);
                        //enemy #02
                        enemyStartPosition[1] = new Vector2(1200, 730);
                            waypoints[1][0] = new Vector2(1200, 400);
                            waypoints[1][1] = new Vector2(1720, 410);
                            waypoints[1][2] = new Vector2(1200, 410);
                            waypoints[1][3] = new Vector2(1200, 730);
                            waypoints[1][4] = new Vector2(1720, 730);
                            waypoints[1][5] = new Vector2(1720, 950);
                            waypoints[1][6] = new Vector2(1200, 730);
                        //enemy #03
                        enemyStartPosition[2] = new Vector2(1760, 680);
                            waypoints[2][0] = new Vector2(980, 680);
                            waypoints[2][1] = new Vector2(600, 680);
                            waypoints[2][2] = new Vector2(980, 680);
                            waypoints[2][3] = new Vector2(980, 920);
                            waypoints[2][4] = new Vector2(1280 ,680);
                            waypoints[2][5] = new Vector2(980, 680);
                            waypoints[2][6] = new Vector2(980, 680);

                break;

//--------------------------------------------------------------------------------------------------
            case 6:

                //the co-ordinates of where the player will spawn
                playerStartPosition = new Vector2(150, 660);
                //the co-ordinates of where the exit will spawn once activated
                flagPosition[0] = new Vector2(1660, 920);

                //the number of walls in the level
                wallCount = 11;
                    //arrays for size and position of each wall
                    wallSize = new Vector2[wallCount]; wallPosition = new Vector2[wallCount];
                        //wall #01
                        wallSize[0] = new Vector2(830, standardWallSize);
                        wallPosition[0] = new Vector2(-20, 820);
                        //wall #02
                        wallSize[1] = new Vector2(825, standardWallSize);
                        wallPosition[1] = new Vector2(-20, 540);
                        //wall #03
                        wallSize[2] = new Vector2(standardWallSize, 250);
                        wallPosition[2] = new Vector2(780, 840);
                        //wall #04
                        wallSize[3] = new Vector2(standardWallSize, 250);
                        wallPosition[3] = new Vector2(780, GameAssets.screenBounds_bottom.getY());
                        //wall #05
                        wallSize[4] = new Vector2(standardWallSize, 250);
                        wallPosition[4] = new Vector2(1050, 840);
                        //wall #06
                        wallSize[5] = new Vector2(standardWallSize, 250);
                        wallPosition[5] = new Vector2(1050, GameAssets.screenBounds_bottom.getY());
                        //wall #07
                        wallSize[6] = new Vector2(570, standardWallSize);
                        wallPosition[6] = new Vector2(1050, 820);
                        //wall #08
                        wallSize[7] = new Vector2(570, standardWallSize);
                        wallPosition[7] = new Vector2(1050, 540);
                        //wall #09
                        wallSize[8] = new Vector2(standardWallSize, 250);
                        wallPosition[8] = new Vector2(1590, 820);
                        //wall #10
                        wallSize[9] = new Vector2(standardWallSize, 250);
                        wallPosition[9] = new Vector2(1590, GameAssets.screenBounds_bottom.getY());
                        //wall #11
                        wallSize[10] = new Vector2(standardWallSize, 800);
                        wallPosition[10] = new Vector2(1850, GameAssets.screenBounds_bottom.getY());

                //the number of doors in a level; the number of buttons used to open doors in a level
                doorCount = 2; activateDoorButtonCount = doorCount;
                    //arrays to hold the position and size of each door
                    doorSize = new Vector2[doorCount]; doorPosition = new Vector2[doorCount];
                    //arrays to hold the speed and direction which each door will open
                    doorCloseSpeed = new float[doorCount]; doorOpenDirection = new int[doorCount];
                    //array to hold the poistion of each of the buttons used to open a door
                    activateDoorButtonPosition = new Vector2[activateDoorButtonCount];
                        //door #01
                        doorSize[0] = new Vector2(standardWallSize, 280);
                        doorPosition[0] = new Vector2(1060, 550);
                            activateDoorButtonPosition[0] = new Vector2(900, 950);
                            doorCloseSpeed[0] = 2f;
                            doorOpenDirection[0] = ActivateDoorButton.VERTICAL;
                        //door #02
                        doorSize[1] = new Vector2(260, standardWallSize);
                        doorPosition[1] = new Vector2(1600, 820);
                            activateDoorButtonPosition[1] = new Vector2(1700, 350);
                            doorCloseSpeed[1] = 2f;
                            doorOpenDirection[1] = ActivateDoorButton.HORISONTAL;

                //the number of stars in a level
                starCount = 1;
                    //array that holds the position of each star
                    starsPosition = new Vector2[starCount];
                        starsPosition[0] = new Vector2(890, 360);

                //number of enemies present in level; number of waypoints for each enemy
                enemyCount = 1; waypointCount = 4;
                    //Vector2 array to hold the start position of each enemy
                    enemyStartPosition = new Vector2[enemyCount];
                    //2-D Vector2 array to hold the position of waypoints for each enemy, [enemyNumber][nextWaypoint]
                    waypoints = new Vector2[enemyCount][waypointCount];
                        //enemy #01
                        enemyStartPosition[0] = new Vector2(1740, 660);
                            waypoints[0][0] = new Vector2(1280, 660);
                            waypoints[0][1] = new Vector2(1740, 660);
                            waypoints[0][2] = new Vector2(1740, 440);
                            waypoints[0][3] = new Vector2(1740, 660);

                break;

//--------------------------------------------------------------------------------------------------
            case 7:

                //the co-ordinates of where the player will spawn
                playerStartPosition = new Vector2(1670, 700);
                //the co-ordinates of where the exit will spawn once activated
                flagPosition[0] = new Vector2(200, 930);

                //the number of walls in the level
                wallCount = 10;
                    //arrays for size and position of each wall
                    wallSize = new Vector2[wallCount]; wallPosition = new Vector2[wallCount];
                        //wall #01
                        wallSize[0] = new Vector2(standardWallSize, 800);
                        wallPosition[0] = new Vector2(120, GameAssets.screenBounds_bottom.getY());
                        //wall #02
                        wallSize[1] = new Vector2(standardWallSize, 250);
                        wallPosition[1] = new Vector2(400, 850);
                        //wall #03
                        wallSize[2] = new Vector2(standardWallSize, 300);
                        wallPosition[2] = new Vector2(400, GameAssets.screenBounds_bottom.getY());
                        //wall #04
                        wallSize[3] = new Vector2(1400, standardWallSize);
                        wallPosition[3] = new Vector2(400, 840);
                        //wall #05
                        wallSize[4] = new Vector2(470, standardWallSize);
                        wallPosition[4] = new Vector2(400, 580);
                        //wall #06
                        wallSize[5] = new Vector2(standardWallSize, 300);
                        wallPosition[5] = new Vector2(850, GameAssets.screenBounds_bottom.getY());
                        //wall #07
                        wallSize[6] = new Vector2(standardWallSize, 280);
                        wallPosition[6] = new Vector2(1110, GameAssets.screenBounds_bottom.getY());
                        //wall #08
                        wallSize[7] = new Vector2(450, standardWallSize);
                        wallPosition[7] = new Vector2(1110, 580);
                        //wall #09
                        wallSize[8] = new Vector2(standardWallSize, 300);
                        wallPosition[8] = new Vector2(1550, GameAssets.screenBounds_bottom.getY());
                        //wall #10
                        wallSize[9] = new Vector2(standardWallSize, 560);
                        wallPosition[9] = new Vector2(1780, GameAssets.screenBounds_bottom.getY());

                //the number of doors in a level; the number of buttons used to open doors in a level
                doorCount = 3; activateDoorButtonCount = doorCount;
                    //arrays to hold the position and size of each door
                    doorSize = new Vector2[doorCount]; doorPosition = new Vector2[doorCount];
                    //arrays to hold the speed and direction which each door will open
                    doorCloseSpeed = new float[doorCount]; doorOpenDirection = new int[doorCount];
                    //array to hold the poistion of each of the buttons used to open a door
                    activateDoorButtonPosition = new Vector2[activateDoorButtonCount];
                        //door #01
                        doorSize[0] = new Vector2(290, standardWallSize);
                        doorPosition[0] = new Vector2(120, 850);
                            activateDoorButtonPosition[0] = new Vector2(970, 340);
                            doorCloseSpeed[0] = .8f;
                            doorOpenDirection[0] = ActivateDoorButton.HORISONTAL;
                        //door #02
                        doorSize[1] = new Vector2(270, standardWallSize);
                        doorPosition[1] = new Vector2(860, 580);
                            activateDoorButtonPosition[1] = new Vector2(1360, 680);
                            doorCloseSpeed[1] = .8f;
                            doorOpenDirection[1] = ActivateDoorButton.HORISONTAL;
                        //door #03
                        doorSize[2] = new Vector2(standardWallSize, 260);
                        doorPosition[2] = new Vector2(1540, 600);
                            activateDoorButtonPosition[2] = new Vector2(1650, 380);
                            doorCloseSpeed[2] = 1f;
                            doorOpenDirection[2] = ActivateDoorButton.VERTICAL;

                //the number of stars in a level
                starCount = 2;
                    //array that holds the position of each star
                    starsPosition = new Vector2[starCount];
                        starsPosition[0] = new Vector2(230, 380);
                        starsPosition[1] = new Vector2(960, 440);

                //number of enemies present in level; number of waypoints for each enemy
                enemyCount = 2; waypointCount = 4;
                    //Vector2 array to hold the start position of each enemy
                    enemyStartPosition = new Vector2[enemyCount];
                    //2-D Vector2 array to hold the position of waypoints for each enemy, [enemyNumber][nextWaypoint]
                    waypoints = new Vector2[enemyCount][waypointCount];
                        //enemy #01
                        enemyStartPosition[0] = new Vector2(240, 460);
                            waypoints[0][0] = new Vector2(240, 660);
                            waypoints[0][1] = new Vector2(580, 680);
                            waypoints[0][2] = new Vector2(200, 680);
                            waypoints[0][3] = new Vector2(200, 460);
                        //enemy #02
                        enemyStartPosition[1] = new Vector2(1300, 690);
                            waypoints[1][0] = new Vector2(980, 690);
                            waypoints[1][1] = new Vector2(770, 690);
                            waypoints[1][2] = new Vector2(980, 690);
                            waypoints[1][3] = new Vector2(1300, 690);


                break;

//--------------------------------------------------------------------------------------------------
            case 8:

                playerStartPosition = new Vector2(210, 350);
                flagPosition[0] = new Vector2(1750, 580);

                wallCount = 15;
                    //arrays for size and position of each wall
                    wallSize = new Vector2[wallCount]; wallPosition = new Vector2[wallCount];
                        //wall #01
                        wallSize[0] = new Vector2(standardWallSize, 660);
                        wallPosition[0] = new Vector2(120, GameAssets.screenBounds_bottom.getY());
                        //wall #02
                        wallSize[1] = new Vector2(1570, standardWallSize);
                        wallPosition[1] = new Vector2(120, 940);
                        //wall #03
                        wallSize[2] = new Vector2(standardWallSize, 220);
                        wallPosition[2] = new Vector2(350, GameAssets.screenBounds_bottom.getY());
                        //wall #04
                        wallSize[3] = new Vector2(420, standardWallSize);
                        wallPosition[3] = new Vector2(350, 500);
                        //wall #05
                        wallSize[4] = new Vector2(standardWallSize, 220);
                        wallPosition[4] = new Vector2(550, 520);
                        //wall #06
                        wallSize[5] = new Vector2(standardWallSize, 220);
                        wallPosition[5] = new Vector2(750, GameAssets.screenBounds_bottom.getY());
                        //wall #07
                        wallSize[6] = new Vector2(standardWallSize, 220);
                        wallPosition[6] = new Vector2(860, 740);
                        //wall #08
                        wallSize[7] = new Vector2(standardWallSize, 220);
                        wallPosition[7] = new Vector2(960, GameAssets.screenBounds_bottom.getY());
                        //wall #09
                        wallSize[8] = new Vector2(420, standardWallSize);
                        wallPosition[8] = new Vector2(960, 500);
                        //wall #10
                        wallSize[9] = new Vector2(standardWallSize, 220);
                        wallPosition[9] = new Vector2(1170, 520);
                        //wall #11
                        wallSize[10] = new Vector2(standardWallSize, 220);
                        wallPosition[10] = new Vector2(1360, GameAssets.screenBounds_bottom.getY());
                        //wall #12
                        wallSize[11] = new Vector2(standardWallSize, 220);
                        wallPosition[11] = new Vector2(1660, 730);
                        //wall #13
                        wallSize[12] = new Vector2(280, standardWallSize);
                        wallPosition[12] = new Vector2(1660, 730);
                        //wall #14
                        wallSize[13] = new Vector2(280, standardWallSize);
                        wallPosition[13] = new Vector2(1660, 500);
                        //wall #15
                        wallSize[14] = new Vector2(standardWallSize, 220);
                        wallPosition[14] = new Vector2(1660, GameAssets.screenBounds_bottom.getY());

                //the number of doors in a level; the number of buttons used to open doors in a level
                doorCount = 3; activateDoorButtonCount = doorCount;
                    //arrays to hold the position and size of each door
                    doorSize = new Vector2[doorCount]; doorPosition = new Vector2[doorCount];
                    //arrays to hold the speed and direction which each door will open
                    doorCloseSpeed = new float[doorCount]; doorOpenDirection = new int[doorCount];
                    //array to hold the poistion of each of the buttons used to open a door
                    activateDoorButtonPosition = new Vector2[activateDoorButtonCount];
                        //door #01
                        doorSize[0] = new Vector2(220, standardWallSize);
                        doorPosition[0] = new Vector2(750, 490);
                            activateDoorButtonPosition[0] = new Vector2(1500, 370);
                            doorCloseSpeed[0] = .45f;
                            doorOpenDirection[0] = ActivateDoorButton.HORISONTAL;
                        //door #02
                        doorSize[1] = new Vector2(300, standardWallSize);
                        doorPosition[1] = new Vector2(1370, 500);
                            activateDoorButtonPosition[1] = new Vector2(950, 840);
                            doorCloseSpeed[1] = 1f;
                            doorOpenDirection[1] = ActivateDoorButton.HORISONTAL;
                        //door #03
                        doorSize[2] = new Vector2(standardWallSize, 240);
                        doorPosition[2] = new Vector2(1660, 500);
                            activateDoorButtonPosition[2] = new Vector2(610, 550);
                            doorCloseSpeed[2] = .6f;
                            doorOpenDirection[2] = ActivateDoorButton.VERTICAL;

                //the number of stars in a level
                starCount = 3;
                    //array that holds the position of each star
                    starsPosition = new Vector2[starCount];
                        starsPosition[0] = new Vector2(180, 830);
                        starsPosition[1] = new Vector2(830, 360);
                        starsPosition[2] = new Vector2(1770, 580);

                //number of enemies present in level; number of waypoints for each enemy
                enemyCount = 2; waypointCount = 7;
                    //Vector2 array to hold the start position of each enemy
                    enemyStartPosition = new Vector2[enemyCount];
                    //2-D Vector2 array to hold the position of waypoints for each enemy, [enemyNumber][nextWaypoint]
                    waypoints = new Vector2[enemyCount][waypointCount];
                        //enemy #01
                        enemyStartPosition[0] = new Vector2(380, 540);
                            waypoints[0][0] = new Vector2(380, 780);
                            waypoints[0][1] = new Vector2(700, 780);
                            waypoints[0][2] = new Vector2(700, 640);
                            waypoints[0][3] = new Vector2(820, 640);
                            waypoints[0][4] = new Vector2(700, 640);
                            waypoints[0][5] = new Vector2(700, 780);
                            waypoints[0][6] = new Vector2(380, 780);
                        //enemy #02
                        enemyStartPosition[1] = new Vector2(1440, 800);
                            waypoints[1][0] = new Vector2(980, 800);
                            waypoints[1][1] = new Vector2(980, 640);
                            waypoints[1][2] = new Vector2(980, 800);
                            waypoints[1][3] = new Vector2(1500, 800);
                            waypoints[1][4] = new Vector2(1500, 660);
                            waypoints[1][5] = new Vector2(1500, 800);
                            waypoints[1][6] = new Vector2(1200, 800);

                break;

//--------------------------------------------------------------------------------------------------

            case 9:

                //the co-ordinates of where the player will spawn
                playerStartPosition = new Vector2(920, 650);
                //the co-ordinates of where the exit will spawn once activated
                flagPosition[0] = new Vector2(870, 630);

                //the number of walls in the level
                wallCount = 6;
                    //arrays for size and position of each wall
                    wallSize = new Vector2[wallCount]; wallPosition = new Vector2[wallCount];
                        //wall #01
                        wallSize[0] = new Vector2(350, standardWallSize);
                        wallPosition[0] = new Vector2(GameAssets.screenBounds_left.x, 660);
                        //wall #02
                        wallSize[1] = new Vector2(700, standardWallSize);
                        wallPosition[1] = new Vector2(600, 780);
                        //wall #03
                        wallSize[2] = new Vector2(700, standardWallSize);
                        wallPosition[2] = new Vector2(600, 570);
                        //wall #04
                        wallSize[3] = new Vector2(standardWallSize, 150);
                        wallPosition[3] = new Vector2(940, 940);
                        //wall #05
                        wallSize[4] = new Vector2(standardWallSize, 150);
                        wallPosition[4] = new Vector2(940, GameAssets.screenBounds_bottom.y);
                        //wall #06
                        wallSize[5] = new Vector2(350, standardWallSize);
                        wallPosition[5] = new Vector2(1580, 660);

                //the number of doors in a level; the number of buttons used to open doors in a level
                doorCount = 4; activateDoorButtonCount = doorCount;
                    //arrays to hold the position and size of each door
                    doorSize = new Vector2[doorCount]; doorPosition = new Vector2[doorCount];
                    //arrays to hold the speed and direction which each door will open
                    doorCloseSpeed = new float[doorCount]; doorOpenDirection = new int[doorCount];
                    //array to hold the poistion of each of the buttons used to open a door
                    activateDoorButtonPosition = new Vector2[activateDoorButtonCount];
                        //door #01
                        doorSize[0] = new Vector2(standardWallSize, 420);
                        doorPosition[0] = new Vector2(340, 670);
                            activateDoorButtonPosition[0] = new Vector2(1020, 320);
                            doorCloseSpeed[0] = 1f;
                            doorOpenDirection[0] = ActivateDoorButton.VERTICAL;
                        //door #02
                        doorSize[1] = new Vector2(standardWallSize, 180);
                        doorPosition[1] = new Vector2(590, 590);
                            activateDoorButtonPosition[1] = new Vector2(1790, 740);
                            doorCloseSpeed[1] = .36f;
                            doorOpenDirection[1] = ActivateDoorButton.VERTICAL;
                        //door #03
                        doorSize[2] = new Vector2(standardWallSize, 180);
                        doorPosition[2] = new Vector2(1270, 590);
                            activateDoorButtonPosition[2] = new Vector2(740, 640);
                            doorCloseSpeed[2] = 1f;
                            doorOpenDirection[2] = ActivateDoorButton.VERTICAL;
                        //door #04
                        doorSize[3] = new Vector2(standardWallSize, 390);
                        doorPosition[3] = new Vector2(1570, GameAssets.screenBounds_bottom.getY());
                            activateDoorButtonPosition[3] = new Vector2(820, 980);
                            doorCloseSpeed[3] = 1f;
                            doorOpenDirection[3] = ActivateDoorButton.VERTICAL;

                //the number of stars in a level
                starCount = 4;
                    //array that holds the position of each star
                    starsPosition = new Vector2[starCount];
                        starsPosition[0] = new Vector2(40, 980);
                        starsPosition[1] = new Vector2(40, 340);
                        starsPosition[2] = new Vector2(980, 980);
                        starsPosition[3] = new Vector2(1780, 340);

                //number of enemies present in level; number of waypoints for each enemy
                enemyCount = 2; waypointCount = 4;
                    //Vector2 array to hold the start position of each enemy
                    enemyStartPosition = new Vector2[enemyCount];
                    //2-D Vector2 array to hold the position of waypoints for each enemy, [enemyNumber][nextWaypoint]
                    waypoints = new Vector2[enemyCount][waypointCount];
                        //enemy #01
                        enemyStartPosition[0] = new Vector2(460, 720);
                            waypoints[0][0] = new Vector2(460, 480);
                            waypoints[0][1] = new Vector2(1420, 480);
                            waypoints[0][2] = new Vector2(1420, 840);
                            waypoints[0][3] = new Vector2(440, 840);
                        //enemy #02
                        enemyStartPosition[1] = new Vector2(1420, 640);
                            waypoints[1][0] = new Vector2(1420, 840);
                            waypoints[1][1] = new Vector2(440, 840);
                            waypoints[1][2] = new Vector2(460, 480);
                            waypoints[1][3] = new Vector2(1420, 480);

                break;

//--------------------------------------------------------------------------------------------------

            case 10:

                //the co-ordinates of where the player will spawn
                playerStartPosition = new Vector2(220, 970);
                //the co-ordinates of where the exit will spawn once activated
                flagPosition[0] = new Vector2(200, 360);

                //the number of walls in the level
                wallCount = 12;
                    //arrays for size and position of each wall
                    wallSize = new Vector2[wallCount]; wallPosition = new Vector2[wallCount];
                        //wall #01
                        wallSize[0] = new Vector2(standardWallSize, 800);
                        wallPosition[0] = new Vector2(100, GameAssets.screenBounds_bottom.getY());
                        //wall #02
                        wallSize[1] = new Vector2(standardWallSize, 300);
                        wallPosition[1] = new Vector2(370, 800);
                        //wall #03
                        wallSize[2] = new Vector2(450, standardWallSize);
                        wallPosition[2] = new Vector2(100, 530);
                        //wall #04
                        wallSize[3] = new Vector2(500, standardWallSize);
                        wallPosition[3] = new Vector2(370, 800);
                        //wall #05
                        wallSize[4] = new Vector2(standardWallSize, 300);
                        wallPosition[4] = new Vector2(850, 800);
                        //wall #06
                        wallSize[5] = new Vector2(standardWallSize, 260);
                        wallPosition[5] = new Vector2(860, GameAssets.screenBounds_bottom.getY());
                        //wall #07
                        wallSize[6] = new Vector2(standardWallSize, 300);
                        wallPosition[6] = new Vector2(1100, 800);
                        //wall #08
                        wallSize[7] = new Vector2(standardWallSize, 260);
                        wallPosition[7] = new Vector2(1100, GameAssets.screenBounds_bottom.getY());
                        //wall #09
                        wallSize[8] = new Vector2(460, standardWallSize);
                        wallPosition[8] = new Vector2(1100, 800);
                        //wall #10
                        wallSize[9] = new Vector2(standardWallSize, 300);
                        wallPosition[9] = new Vector2(1540, 800);
                        //wall #11
                        wallSize[10] = new Vector2(standardWallSize, 260);
                        wallPosition[10] = new Vector2(1540, GameAssets.screenBounds_bottom.getY());
                        //wall #12
                        wallSize[11] = new Vector2(standardWallSize, 800);
                        wallPosition[11] = new Vector2(1800, GameAssets.screenBounds_bottom.getY());

                //the number of doors in a level; the number of buttons used to open doors in a level
                doorCount = 3; activateDoorButtonCount = doorCount;
                    //arrays to hold the position and size of each door
                    doorSize = new Vector2[doorCount]; doorPosition = new Vector2[doorCount];
                    //arrays to hold the speed and direction which each door will open
                    doorCloseSpeed = new float[doorCount]; doorOpenDirection = new int[doorCount];
                    //array to hold the poistion of each of the buttons used to open a door
                    activateDoorButtonPosition = new Vector2[activateDoorButtonCount];
                        //door #01
                        doorSize[0] = new Vector2(350, standardWallSize);
                        doorPosition[0] = new Vector2(530, 530);
                            activateDoorButtonPosition[0] = new Vector2(960, 980);
                            doorCloseSpeed[0] = 1f;
                            doorOpenDirection[0] = ActivateDoorButton.HORISONTAL;
                        //door #02
                        doorSize[1] = new Vector2(250, standardWallSize);
                        doorPosition[1] = new Vector2(860, 800);
                            activateDoorButtonPosition[1] = new Vector2(1650, 340);
                            doorCloseSpeed[1] = .5f;
                            doorOpenDirection[1] = ActivateDoorButton.HORISONTAL;
                        //door #03
                        doorSize[2] = new Vector2(standardWallSize, 260);
                        doorPosition[2] = new Vector2(1540, 540);
                            activateDoorButtonPosition[2] = new Vector2(1160, 340);
                            doorCloseSpeed[2] = .5f;
                            doorOpenDirection[2] = ActivateDoorButton.VERTICAL;

                //the number of stars in a level
                starCount = 5;
                    //array that holds the position of each star
                    starsPosition = new Vector2[starCount];
                        starsPosition[0] = new Vector2(200, 600);
                        starsPosition[1] = new Vector2(700, 360);
                        starsPosition[2] = new Vector2(950, 860);
                        starsPosition[3] = new Vector2(950, 360);
                        starsPosition[4] = new Vector2(1640, 910);

                //number of enemies present in level; number of waypoints for each enemy
                enemyCount = 4; waypointCount = 4;
                    //Vector2 array to hold the start position of each enemy
                    enemyStartPosition = new Vector2[enemyCount];
                    //2-D Vector2 array to hold the position of waypoints for each enemy, [enemyNumber][nextWaypoint]
                    waypoints = new Vector2[enemyCount][waypointCount];
                        //enemy #01
                        enemyStartPosition[0] = new Vector2(240, 820);
                            waypoints[0][0] = new Vector2(240, 680);
                            waypoints[0][1] = new Vector2(640, 660);
                            waypoints[0][2] = new Vector2(180, 660);
                            waypoints[0][3] = new Vector2(180, 820);
                        //enemy #02
                        enemyStartPosition[1] = new Vector2(720, 380);
                            waypoints[1][0] = new Vector2(200, 380);
                            waypoints[1][1] = new Vector2(720, 380);
                            waypoints[1][2] = new Vector2(200, 380);
                            waypoints[1][3] = new Vector2(720, 380);
                        //enemy #03
                        enemyStartPosition[2] = new Vector2(1160, 660);
                            waypoints[2][0] = new Vector2(1160, 420);
                            waypoints[2][1] = new Vector2(1420, 420);
                            waypoints[2][2] = new Vector2(1420, 620);
                            waypoints[2][3] = new Vector2(1160, 620);
                        //enemy #04
                        enemyStartPosition[3] = new Vector2(1660, 520);
                            waypoints[3][0] = new Vector2(1660, 780);
                            waypoints[3][1] = new Vector2(1660, 940);
                            waypoints[3][2] = new Vector2(1660, 780);
                            waypoints[3][3] = new Vector2(1660, 400);

                break;

//--------------------------------------------------------------------------------------------------
        }
//--------------------------------------------------------------------------------------------------

//====================================================================================================================\\
/**********************************************************************************************************************
****END OF LEVELS - all levels are initialised above, the code below creates a level based on the switch data above****
***********************************************************************************************************************/
//====================================================================================================================\\

        //setting the position of boundaries boundaries for a level
        boundsPosition = new Vector2[boundsCount];
        boundsSize = new Vector2[boundsCount];
        //left bounds
        boundsPosition[0] = new Vector2(GameAssets.screenBounds_left.getX(), GameAssets.screenBounds_left.getY());
        boundsSize[0] = new Vector2(GameAssets.screenBounds_left.getWidth(), GameAssets.screenBounds_left.getHeight());
        //right bounds
        boundsPosition[1] = new Vector2(GameAssets.screenBounds_right.getX(), GameAssets.screenBounds_right.getY());
        boundsSize[1] = new Vector2(GameAssets.screenBounds_right.getWidth(), GameAssets.screenBounds_right.getHeight());
        //top bounds
        boundsPosition[2] = new Vector2(GameAssets.screenBounds_top.getX(), GameAssets.screenBounds_top.getY());
        boundsSize[2] = new Vector2(GameAssets.screenBounds_top.getWidth(), GameAssets.screenBounds_top.getHeight());
        //bottom bounds
        boundsPosition[3] = new Vector2(GameAssets.screenBounds_bottom.getX(), GameAssets.screenBounds_bottom.getY());
        boundsSize[3] = new Vector2(GameAssets.screenBounds_bottom.getWidth(), GameAssets.screenBounds_bottom.getHeight());

        //setting where to start the player depending on the current level
        player = new Player(playerStartPosition, playerSize);

        //pause menu button
        pauseButtonPosition = new Vector2[pauseButtonCount];
            pauseButtonPosition[0] = new Vector2((GameAssets.screenBounds_right.getX() - pauseButtonSize.x) - 20, (GameAssets.screenBounds_top.getY() - pauseButtonSize.y) - 20);

        //restart level button
        restartButtonPosition = new Vector2[restartButtonCount];
            restartButtonPosition[0] = new Vector2(((GameAssets.screenResolutionX / 2) - (restartButtonSize.x / 2) - restartButtonSize.x), (GameAssets.screenResolutionY / 2) + (restartButtonSize.y / 2) - (restartButtonSize.y / 3 * 2));

        //level select menu button
        levelSelectMenuButtonPosition = new Vector2[levelSelectMenuButtonCount];
            levelSelectMenuButtonPosition[0] = new Vector2((GameAssets.screenResolutionX / 2) + (levelSelectMenuButtonSize.x / 2), (GameAssets.screenResolutionY / 2) + (levelSelectMenuButtonSize.y / 2) - (levelSelectMenuButtonSize.y / 3 * 2));

        //toggle sound button
        toggleSoundButtonPosition = new Vector2[toggleSoundButtonCount];
            toggleSoundButtonPosition[0] = new Vector2(((GameAssets.screenResolutionX / 2) - (toggleSoundButtonSize.x / 2)), (GameAssets.screenResolutionY / 2) - (toggleSoundButtonSize.y / 2) - (toggleSoundButtonSize.y + (toggleSoundButtonSize.y / 6)));

        //initialise arrays and their respecive values
        enemies = new Enemy[enemyCount];
        bounds = new Walls[boundsCount];
        walls = new Walls[wallCount];
        doors = new Door[doorCount];
        activateDoorButtons = new ActivateDoorButton[activateDoorButtonCount];
        underlineFonts = new Walls[underlineFontCount];
        stars = new Star[starCount];
        flags = new Flag[flagCount];
        pauseButtons = new Button[pauseButtonCount];
        toggleSoundButtons = new Button[toggleSoundButtonCount];
        levelSelectMenuButtons = new Button[levelSelectMenuButtonCount];
        restartButtons = new Button[restartButtonCount];

        //create all bounds to keep the player in the screen
        for (int i = 0; i < boundsCount; i++) {
            bounds[i] = createWall(boundsPosition[i], boundsSize[i]);
        }

        //create all the walls used in a level
        for (int i = 0; i < wallCount; i++) {
            walls[i] = createWall(wallPosition[i], wallSize[i]);
        }

        //create any doors if they are included in the level
        for (int i = 0; i < doorCount; i++) {
            doors[i] = createDoor(doorPosition[i], doorSize[i], doorCloseSpeed[i], doorOpenDirection[i]);
        }

        //create any buttons that open doors
        for (int i = 0; i < activateDoorButtonCount; i++) {
            activateDoorButtons[i] = createActivateDoorButton(activateDoorButtonPosition[i], activateDoorButtonSize, doors[i]);
        }

        //create all the stars used in a level
        for (int i = 0; i < starCount; i++) {
            stars[i] = createStar(starsPosition[i], starSize, starCount);
        }

        //create all enemies and sights for a level
        for (int i = 0; i < enemyCount; i++) {
            enemies[i] = createNewEnemy(enemyStartPosition[i], enemySize, waypointCount, waypoints, i);
        }

        //create any flags used in a menu
        for (int i = 0; i < flagCount; i++) {
            flags[i] = createFlag(flagPosition[i], flagSize);
        }

        //create a pause button
        for (int i = 0; i < pauseButtonCount; i++) {
            //create all buttons of a certain type (Vector2 position, Vector2 size, TextureRegion buttonImage, int levelButtonValue)
            pauseButtons[i] = createButton(pauseButtonPosition[i], pauseButtonSize, pauseButtonImage, 0);
        }

        //create the toggle music button for pause menu
        for (int i = 0; i < toggleSoundButtonCount; i++) {
            //create all buttons of a certain type (Vector2 position, Vector2 size, TextureRegion buttonImage, int levelButtonValue)
            toggleSoundButtons[i] = createButton(toggleSoundButtonPosition[i], toggleSoundButtonSize, toggleSoundButtonImage, 0);
        }

        //create the level select menu button for pause menu
        for (int i = 0; i < levelSelectMenuButtonCount; i++) {
            //create all buttons of a certain type (Vector2 position, Vector2 size, TextureRegion buttonImage, int levelButtonValue)
            levelSelectMenuButtons[i] = createButton(levelSelectMenuButtonPosition[i], levelSelectMenuButtonSize, levelSelectMenuButtonImage, 0);
        }

        //create all of the restart level buttons for pause menu
        for (int i = 0; i < restartButtonCount; i++) {
            //create all buttons of a certain type (Vector2 position, Vector2 size, TextureRegion buttonImage, int levelButtonValue)
            restartButtons[i] = createButton(restartButtonPosition[i], restartButtonSize, restartButtonImage, 0);
        }

        //create all the underlineFonts for pause menu
        for (int i = 0; i < underlineFontCount; i++) {
            underlineFonts[i] = createWall(underlineFontPosition[i], underlineFontSize[i]);
        }

    }

/*============================================================================================================================
                                                **Drawing**
=============================================================================================================================*/
    public void draw(SpriteBatch batch) {

        //DeltaTime is the time in-between frames - the state time is being added to the delta time - true means isLooping
        stateTime += Gdx.graphics.getDeltaTime();
        GameAssets.starCurrentFrame = GameAssets.starAnim.getKeyFrame(stateTime, true);
        GameAssets.flagCurrentFrame = GameAssets.flagAnim.getKeyFrame(stateTime, true);

        //draw the background sprite from GameAssets Class onto the SpriteBatch. This makes it visible on screen
        batch.draw(GameAssets.paperBackgroundSprite, 0, 0, GameAssets.screenResolutionX, GameAssets.screenResolutionY);

        //draw any of the buttons that open doors
        for (int i = 0; i < activateDoorButtonCount; i++) {
            activateDoorButtons[i].draw(batch);
        }

        //draw player to the screen
        player.draw(batch);

        //draw all of the walls for a level to the screen
        for (int i = 0; i < wallCount; i++) {
            walls[i].draw(batch);
        }

        //draw any doors if they are included in a level
        for (int i = 0; i < doorCount; i++) {
            doors[i].draw(batch);
        }

        //draw on screen controls
        batch.draw(leftImage, guiControls.leftBounds.x, guiControls.leftBounds.y, guiControls.left_right_width, guiControls.left_right_height);
        batch.draw(rightImage, guiControls.rightBounds.x, guiControls.rightBounds.y, guiControls.left_right_width, guiControls.left_right_height);
        batch.draw(upImage, guiControls.upBounds.x, guiControls.upBounds.y, guiControls.up_down_width, guiControls.up_down_height);
        batch.draw(downImage, guiControls.downBounds.x, guiControls.downBounds.y, guiControls.up_down_width, guiControls.up_down_height);

        //draw all stars for a level
        for (int i = 0; i < starCount; i++) {
            stars[i].draw(batch);
        }

        //draw any exit flags present in a menu
        for (int i = 0; i < flagCount; i++) {
            flags[i].draw(batch);
        }

        //draw all enemies to the screen
        for (int i = 0; i < enemyCount; i++) {
            enemies[i].draw(batch);
        }

        //draw the pause button to the screen
        for (int i = 0; i < pauseButtonCount; i++) {
            pauseButtons[i].draw(batch);
        }

    }

//============================================================================================================================//
    //what to draw to sreen when the game is paused
    public void drawPause(SpriteBatch batch) {

        batch.draw(GameAssets.pauseBackgroundSprite, 0, 0, GameAssets.screenResolutionX, GameAssets.screenResolutionY);

        //size of the chosen font
        GameAssets.fontRobotoRegular.getData().setScale(1f);
        GameAssets.fontRobotoRegular.draw(batch, "PAUSED", (GameAssets.screenResolutionX / 2) - 350, 1030);

        //draw any underlineFonts (includes underlines for titles etc.)
        for (int i = 0; i < underlineFontCount; i++) {
            underlineFonts[i].draw(batch);
        }

        //draw the toggle sound button to the screen
        for (int i = 0; i < toggleSoundButtonCount; i++) {

            //if the sound is currently turned on
            if (GameAssets.getToggleSoundState()) {
                toggleSoundButtons[i].buttonImage = GameAssets.soundOnSprite;
            }
                //if the sound is not currently toggled on
                else if (!GameAssets.getToggleSoundState()) {
                    toggleSoundButtons[i].buttonImage = GameAssets.soundOffSprite;
                }

            toggleSoundButtons[i].draw(batch);
        }

        //draw the level select menu button in the pausse screen
        for (int i = 0; i < levelSelectMenuButtonCount; i++) {
            levelSelectMenuButtons[i].draw(batch);
        }

        //draw the restart level button in the pause menu
        for (int i = 0; i < restartButtonCount; i++) {
            restartButtons[i].draw(batch);
        }

    }

/*============================================================================================================================
                                                **ShapeRenderer**
=============================================================================================================================*/
    public void renderEnemies(ShapeRenderer shapeRenderer) {
        for (int i = 0; i < enemyCount; i++) {
            //passing (ShapeRenderer shapeRenderer, int enemyCount
            enemies[i].renderShapes(shapeRenderer, i);
        }
    }
//============================================================================================================================//
    public void renderStarPositions(ShapeRenderer shapeRenderer) {
        for (int i = 0; i < starCount; i++) {
            stars[i].renderShapes(shapeRenderer);
        }
    }

/*============================================================================================================================
                                                **Updating**
=============================================================================================================================*/

    public void updateLevel(PaperscapeGame game) {

        //set game variable to the one passed from level
        GameAssets.game = game;

//------------------------------------------------------------------------------------------------\\
        player.update();

        //update the bounds of the level in regards to the player touching them
        for (int i = 0; i < boundsCount; i++) {
            bounds[i].update(player);
        }

        //update all of the walls included in a level, i.e. check if the player hits any walls
        for (int i = 0; i < wallCount; i++) {
            walls[i].update(player);
        }

        //update any doors if they are in a level
        for (int i = 0; i < doorCount; i++) {
            doors[i].update(player);
        }

        //check for contact betwen player any any buttons that open doors
        for (int i = 0; i < activateDoorButtonCount; i++) {
            activateDoorButtons[i].update(player);
        }

        //update all of the stars in a level
        for (int i = 0; i < starCount; i++) {
            stars[i].update(player);
        }

//--------------------------------------------------------------------------------------------------
        for (int i = 0; i < flagCount; i ++) {
            //update passing (player, starCount)
            flags[i].update(player, starCount);

            //the the player makes contact with an exit flag
            if (flags[i].levelComplete) {
                //run the stop music method from GameAssets passing the int for the level music
                GameAssets.stopMusic(GameAssets.LEVEL_MUSIC_INT);
                //set screen to level complete screen (game, currentScreen - next screen, previousScreen - this screen, currentLevel, starCount)
                game.setScreen(new Menu(game, MenuManager.LEVEL_COMPLETE_SCREEN, 0, currentLevel, starCount));
            }
        }

//-------------------------------Stars move to bottom of screen-------------------------------------
                for (int i = 0; i < starCount; i++) {
                    if (stars[i].isCollected) {
                        if (stars[i].position.x < stars[i].starDisplayPosition[i].x) {
                            stars[i].position.x += stars[i].speed;
                        }
                        if (stars[i].position.x > stars[i].starDisplayPosition[i].x) {
                            stars[i].position.x -= stars[i].speed;
                        }
                        if (stars[i].position.y > stars[i].starDisplayPosition[i].y) {
                            stars[i].position.y -= stars[i].speed;
                        }
                    }
                }

//------------------------------Update all enemies in a level---------------------------------------
                for (int i = 0; i < enemyCount; i++) {
                    //run the update method from all enemies and sights
                    enemies[i].update(player);
                    //------------------------------------------
                    //deciding which direction to move an enemy
                    if (enemies[i].directionMoving == "LEFT") {
                        enemies[i].position.x -= enemies[i].speed;
                    } else if (enemies[i].directionMoving == "RIGHT") {
                        enemies[i].position.x += enemies[i].speed;
                    } else if (enemies[i].directionMoving == "UP") {
                        enemies[i].position.y += enemies[i].speed;
                    } else if (enemies[i].directionMoving == "DOWN") {
                        enemies[i].position.y -= enemies[i].speed;
                    }
                }

        //---------------------------------------------------
        //if the player makes contact with an enemy or an enemy sight
        if (player.isDead) {
            //run the stopMusic method from GameAssets class passing the int for the level music
            GameAssets.stopMusic(GameAssets.LEVEL_MUSIC_INT);
            //change screen to death screen (game, currentScreen - next screen, previousScreen - this screen, currentLevel, starCount)
            game.setScreen(new Menu(game, MenuManager.DEATH_SCREEN, 0, currentLevel, 0));
        }

    }

//======================================What to do when paused=====================================================//
    public void updatePaused(Vector3 touch, OrthographicCamera camera) {

        //setting the games camera and Vector3 touch to the ones passed when method is called
        GameAssets.camera = camera;
        GameAssets.touch = touch;

        //check all of the toggleSoundButtons if there are any
        for (int i = 0; i < toggleSoundButtonCount; i++) {
            //look for touches on any of the buttons
            toggleSoundButtons[i].update(touch, camera);

                if (toggleSoundButtons[i].isClicked) {
                    //what to do when any toggleSoundButtons are pressed
                    //also used in Level class
                }
        }

        //check all of the level select menu buttons
        for (int i = 0; i < levelSelectMenuButtonCount; i++) {
            //check for touches on any of the buttons
            levelSelectMenuButtons[i].update(touch, camera);
                //if the Boolean value for a button being clicked is true
                if (levelSelectMenuButtons[i].isClicked) {
                    //run the stopMusic method from the GameAssets class passing the int for the level music
                    GameAssets.stopMusic(GameAssets.LEVEL_MUSIC_INT);
                }
        }

        //check if any restart level buttons are pressed
        for (int i = 0; i < restartButtonCount; i++) {
            //check for touches on any of the buttons
            restartButtons[i].update(touch, camera);
            //if the Boolean value for a button being clicked is true
            if (restartButtons[i].isClicked) {
                //run the stopMusic method from the GameAssets class passing the int for the level music
                GameAssets.stopMusic(GameAssets.LEVEL_MUSIC_INT);
            }
        }

        //check for any touches to the screen
        for (int i = 0; i < GameAssets.numberOfMultiTouches; i++) {
           if (Gdx.input.isTouched(i) &&
                   !toggleSoundButtons[0].isClicked &&
                   !levelSelectMenuButtons[0].isClicked) {
               //Setting a value for the position of a the 5 touches
               touch.set(Gdx.input.getX(i), Gdx.input.getY(i), 0);
               //allows re-sizing while still correct touch locations
               GameAssets.camera.unproject(touch);
               //set the game to be in a running state
                Level.gameState = Level.RUN_STATE;
               //write the current state of the game the console
               Gdx.app.log("Game State", "Running");
            }
        }

    }


//============================================================================================================================//
    public void update(Vector3 touch, OrthographicCamera camera, OnScreenControls guiControls) {

        //setting the games camera and Vector3 touch to the ones passed when method is called
        GameAssets.touch = touch;
        GameAssets.camera = camera;
        //setting the guiControls object to the one passed to the method
        this.guiControls = guiControls;

        leftImage = guiControls.leftImage;
        rightImage = guiControls.rightImage;
        upImage = guiControls.upImage;
        downImage = guiControls.downImage;

//--------------------------------------------------------------------------------------------------
        //check for touches on the pause button
        for (int i = 0; i < pauseButtonCount; i++) {
            pauseButtons[i].update(touch, camera);
            //what to do when the pause button is pressed
            if (pauseButtons[i].isClicked) {
                Level.gameState = Level.PAUSE_STATE;
                //write the current state of the game the console
                Gdx.app.log("Game State", "Paused");

                //make the game wait so the same touch wont restart the game
                try {
                    //how long to wait in milliseconds
                    Thread.sleep(GameAssets.waitTimeForPause);
                } catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }

            }

        }

//-------------------------------------Keyboard Controls--------------------------------------------
        //keyboard controls; used for testing purposes
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {

            player.position.x -= player.speed;
            leftImage = guiControls.leftImagePressed;

        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {

            player.position.x += player.speed;
            rightImage = guiControls.rightImagePressed;

        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {

            player.position.y += player.speed;
            upImage = guiControls.upImagePressed;


        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {

            player.position.y -= player.speed;
            downImage = guiControls.downImagePressed;

        }

//---------------------------------On Screen Controls-----------------------------------------------
        //an integer "i" equals 0; while "i" is less than 5; increase "i" by 1; Allows multi-touch of up to 5 fingers
        for (int i = 0; i < GameAssets.numberOfMultiTouches; i++) {

            if (Gdx.input.isTouched(i)) {
                //Setting a value for the position of a the 5 touches
                touch.set(Gdx.input.getX(i), Gdx.input.getY(i), 0);
                //allows re-sizing while still correct touch locations
                GameAssets.camera.unproject(touch);
                //write the position of the touch to the console
                Gdx.app.log("Touch Position " + Integer.toString(i), "X" + Float.toString(touch.x) + ", " + "Y" + Float.toString(touch.y));

                //if x-position of touch is more than where button starts and is less than where button ends
                if (touch.x >= guiControls.leftBounds.x && touch.x <= guiControls.leftBoundsXEnd &&
                        touch.y >= guiControls.leftBounds.y && touch.y <= guiControls.leftBoundsYEnd) {
                    //move the player on the x-axis the amount of players closeSpeed
                    player.position.x -= player.speed;
                    leftImage = guiControls.leftImagePressed;
                }

                if (touch.x >= guiControls.rightBounds.x && touch.x <= guiControls.rightBoundsXEnd &&
                        touch.y >= guiControls.rightBounds.y && touch.y <= guiControls.rightBoundsYEnd) {
                    player.position.x += player.speed;
                    rightImage = guiControls.rightImagePressed;
                }

                if (touch.x >= guiControls.upBounds.x && touch.x <= guiControls.upBoundsXEnd &&
                        touch.y >= guiControls.upBounds.y && touch.y <= guiControls.upBoundsYEnd) {
                    //move the player on the x-axis the amount of players closeSpeed
                    player.position.y += player.speed;
                    upImage = guiControls.upImagePressed;
                }

                if (touch.x >= guiControls.downBounds.x && touch.x <= guiControls.downBoundsXEnd &&
                        touch.y >= guiControls.downBounds.y && touch.y <= guiControls.downBoundsYEnd) {
                    //move the player on the x-axis the amount of players closeSpeed
                    player.position.y -= player.speed;
                    downImage = guiControls.downImagePressed;
                }

            }

        }
    }

/*============================================================================================================================
                                               **Create Methods**
=============================================================================================================================*/
    public Enemy createNewEnemy(Vector2 position, Vector2 size, int waypointCount, Vector2[][] waypoints, int waypointsToFollow) {
        Enemy enemy;
            enemy = new Enemy(new Vector2(position.x, position.y), new Vector2(size.x, size.y), waypointCount, waypoints, waypointsToFollow);
        return enemy;
    }

//============================================================================================================================//
    public Walls createWall(Vector2 wallPosition, Vector2 wallSize) {
        Walls wall;
            wall = new Walls(wallPosition, wallSize);
        return wall;
    }

//============================================================================================================================//
    public Door createDoor(Vector2 doorPosition, Vector2 doorSize, float doorSpeed, int openDirection) {
        Door door;
            door =  new Door(doorPosition, doorSize, doorSpeed, openDirection);
        return door;
    }

//============================================================================================================================
    public ActivateDoorButton createActivateDoorButton(Vector2 position, Vector2 size, Door door) {
        ActivateDoorButton activateDoorButton;
            activateDoorButton = new ActivateDoorButton(position, size, door);
        return activateDoorButton;
    }

    //============================================================================================================================//
    public Star createStar(Vector2 starPosition, Vector2 starSize, int starCount) {
        Star star;
            star = new Star(starPosition, starSize, starCount);
        return star;
    }

//============================================================================================================================//
    public Flag createFlag(Vector2 position, Vector2 size) {
        Flag flag;
            flag = new Flag(position, size);
        return flag;
    }
//============================================================================================================================//
    public Button createButton(Vector2 position, Vector2 size, TextureRegion image, int levelButtonValue) {
        Button button;
            button = new Button(position, size, image, levelButtonValue);
        return button;
    }

/*============================================================================================================================
                                               **Dispose Method**
=============================================================================================================================*/
    public void dispose() {

        GameAssets.dispose();

    }

//============================================================================================================================//
}
//============================================================================================================================//

