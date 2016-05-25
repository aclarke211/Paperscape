package com.paperscape.game;
//============================================================================================================================//
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

//============================================================================================================================//
public class MenuManager {

    //track animation time
    float stateTime,
          //floats used by 'credits screen'
          infoTextX, infoTextY, buttonTextWidth, buttonTextHeight;
    //player to update exits etc.
    Player player;
    //TextureRegions for button images
    TextureRegion levelButtonImage = GameAssets.lockedLevelSprite,
                  levelSelectMenuButtonImage = GameAssets.levelSelectMenuSprite,
                  backButtonImage = GameAssets.backButtonSprite,
                  mainButtonImage = GameAssets.yellowButtonSprite,
                  creditsButtonImage = GameAssets.creditsButtonSprite,
                  restartButtonImage = GameAssets.restartButtonSprite,
                  toggleSoundButtonImage = GameAssets.soundOnSprite,
                  exitButtonImage = GameAssets.exitButtonSprite;
    //int varibles used by MenuManager
    int currentLevel, standardWallSize,
            //initialising the amount of objects at 0
            levelButtonCount = 0, levelSelectMenuButtonCount = 0, backButtonCount = 0, mainButtonCount = 0,
            subButtonCount = 0, creditsButtonCount = 0, restartButtonCount = 0, toggleSoundButtonCount = 0,
            exitButtonCount,
            starCount = 0, underlineFontCount = 0, flagCount = 0, newEnemyCount = 0, newWaypointCount = 0;
    //int for the screen to be displayed, it is static as is called in a 'switch' in the Menu class
    static int currentScreen;
    //vector2's for X and Y sizes of objects
    Vector2 levelButtonSize, levelSelectMenuButtonSize, backButtonSize, mainButtonSize, subButtonSize,
            creditsButtonSize, restartButtonSize, toggleSoundButtonSize, exitButtonSize,
            playerStartPosition, playerSize, starSize, flagSize, enemySize,
            openLevelsTextPosition, lockedLevelsTextPosition;

    //--------------/Arrays\--------------------
    Button[] levelButtons, levelSelectMenuButtons, backButtons, mainButtons, subButtons,
            creditsButtons, restartButtons, toggleSoundButtons, exitButtons;
    Star[] stars;
    Walls[] underlineFonts;
    Flag[] flags;
    Enemy[] newEnemies;
    Vector2[] levelButtonPosition, levelSelectMenuButtonPosition, backButtonPosition, mainButtonPosition,
              subButtonPosition, restartButtonPosition, creditsButtonPosition, toggleSoundButtonPosition,
              exitButtonPosition,
              starPosition, underlineFontPosition, underlineFontSize, flagPosition, newEnemyStartPosition;
    //2-dimensional array for holding which enemy should follow which waypoints
    Vector2[][] newWaypoints;

    //---------/Integers for Switch Cases - final so can be used in switch; static to be referenced
    //                                          in other classes when setting the screen\----------
    static final int MAIN_MENU_SCREEN = 1,
                     LEVEL_SELECT_SCREEN = 2,
                     LEVEL_COMPLETE_SCREEN = 3,
                     DEATH_SCREEN = 4,
                     CREDITS_SCREEN = 5;

//============================================================================================================================//
    public MenuManager(int currentScreen, int currentLevel, int starCount) {

        //determining which screen to be displayed
        this.currentScreen = currentScreen;
        //determining which level a user came from
        this.currentLevel = currentLevel;
        //setting the amount of stars for a screen by the int received, used for level complete screen.
        this.starCount = starCount;
        //stateTime is the time since an animation has started - Initialised at 0
        stateTime = 0F;
        //run the animation methods from GameAssets class
        GameAssets.starAnimation();
        GameAssets.flagAnimation();

        //floats used for displaying fonts in credits screen
        infoTextX = GameAssets.screenBounds_left.getX() + 560;
        infoTextY = GameAssets.screenBounds_top.getY() - 220;
        //floats for text displayed on a button
        buttonTextWidth = 70;
        buttonTextHeight = 25;

        //defualt width or height of a wall - used for consitancy between underlineFonts
        standardWallSize = 20;
        //initialising the size of objects
        playerSize = new Vector2(50, 50);
        enemySize = new Vector2(50, 50);
        levelButtonSize = new Vector2(220, 220);
        levelSelectMenuButtonSize = new Vector2(250, 250);
        backButtonSize = new Vector2(150, 150);
        mainButtonSize = new Vector2(400, 128);
        subButtonSize = new Vector2(400, 90);
        creditsButtonSize = new Vector2(130, 130);
        restartButtonSize = new Vector2(250, 250);
        toggleSoundButtonSize = new Vector2(130, 130);
        exitButtonSize = new Vector2(260, 90);
        starSize = new Vector2(220, 220);
        flagSize = new Vector2(100, 100);

//====================================================================================================================\\
/**********************************************************************************************
******** MENUS - each case is a int variable, used to load multiple version of a menu ********
*********************************************************************************************/
//====================================================================================================================\\

        switch (currentScreen) {

//--------------------------------------------------------------------------------------------------
            case MAIN_MENU_SCREEN:

                //a player is created to be passed to flag, need to allow animation to show
                playerStartPosition = new Vector2(GameAssets.screenBounds_left.getX() + 30, 1000);
                player = new Player(playerStartPosition, playerSize);

                mainButtonCount = 1;
                    mainButtonPosition = new Vector2[mainButtonCount];
                        mainButtonPosition[0] = new Vector2((GameAssets.screenResolutionX / 2 - 200), 140);

                exitButtonCount = 1;
                    exitButtonPosition = new Vector2[exitButtonCount];
                        exitButtonPosition[0] = new Vector2((GameAssets.screenResolutionX / 2) - (exitButtonSize.x / 2), 30);

                creditsButtonCount = 1;
                    creditsButtonPosition = new Vector2[creditsButtonCount];
                        creditsButtonPosition[0] = new Vector2(1700, 900);

                //the number of toggle sound buttons in the menu
                toggleSoundButtonCount = 1;
                    //an array to hold the positions of all of the buttons
                    toggleSoundButtonPosition = new Vector2[toggleSoundButtonCount];
                        //the position of the first button
                        toggleSoundButtonPosition[0] = new Vector2(100, 900);


                underlineFontCount = 1;
                    underlineFontSize = new Vector2[underlineFontCount];
                    underlineFontPosition = new Vector2[underlineFontCount];
                        underlineFontSize[0] = new Vector2(1300, standardWallSize);
                        underlineFontPosition[0] = new Vector2(GameAssets.screenBounds_left.getX() + 280, 820);

                flagCount = 1;
                    flagSize = new Vector2(250, 250);
                    flagPosition = new Vector2[flagCount];
                        flagPosition[0] = new Vector2(GameAssets.screenResolutionX / 2 - 200, 400);

                //if the main menu is loaded and the user did not come from level select or credits screen
                //allows for music to keep playing throughout screens
                if (Menu.previousScreen != LEVEL_SELECT_SCREEN &&
                        Menu.previousScreen != CREDITS_SCREEN) {
                    GameAssets.playMusic(GameAssets.MENU_MUSIC_INT);
                }

                break;

//--------------------------------------------------------------------------------------------------
            case LEVEL_SELECT_SCREEN:

                levelButtonCount = 10;
                    levelButtonPosition = new Vector2[levelButtonCount];
                        levelButtonPosition[0] = new Vector2((GameAssets.screenBounds_left.getX() + (levelButtonSize.x / 2)), 750);
                        levelButtonPosition[1] = new Vector2((GameAssets.screenBounds_left.getX() + (levelButtonSize.x / 2)) + 330, 750);
                        levelButtonPosition[2] = new Vector2((GameAssets.screenBounds_left.getX() + (levelButtonSize.x / 2)) + 660, 750);
                        levelButtonPosition[3] = new Vector2((GameAssets.screenBounds_left.getX() + (levelButtonSize.x / 2)) + 990, 750);
                        levelButtonPosition[4] = new Vector2((GameAssets.screenBounds_left.x + (levelButtonSize.x / 2)) + 1320, 750);
                        levelButtonPosition[5] = new Vector2((GameAssets.screenBounds_left.getX() + (levelButtonSize.x / 2)), 400);
                        levelButtonPosition[6] = new Vector2((GameAssets.screenBounds_left.getX() + (levelButtonSize.x / 2)) + 330, 400);
                        levelButtonPosition[7] = new Vector2((GameAssets.screenBounds_left.getX() + (levelButtonSize.x / 2)) + 660, 400);
                        levelButtonPosition[8] = new Vector2((GameAssets.screenBounds_left.getX() + (levelButtonSize.x / 2)) + 990, 400);
                        levelButtonPosition[9] = new Vector2((GameAssets.screenBounds_left.x + (levelButtonSize.x / 2)) + 1320, 400);

                backButtonCount = 1;
                    backButtonPosition = new Vector2[backButtonCount];
                        backButtonPosition[0] = new Vector2(1700, 900);

                //if the user did not come from the main menu, begin playing menu music
                //helps keep the music playing continuously throughout menus
                if (Menu.previousScreen != MAIN_MENU_SCREEN) {
                    GameAssets.playMusic(GameAssets.MENU_MUSIC_INT);
                }

                break;

//--------------------------------------------------------------------------------------------------
            case LEVEL_COMPLETE_SCREEN:

                levelSelectMenuButtonCount = 1;
                    levelSelectMenuButtonPosition = new Vector2[levelSelectMenuButtonCount];
                        levelSelectMenuButtonPosition[0] = new Vector2((GameAssets.screenResolutionX / 2 - (levelSelectMenuButtonSize.x / 2)), 20);

                underlineFontCount = 1;
                    underlineFontSize = new Vector2[underlineFontCount];
                    underlineFontPosition = new Vector2[underlineFontCount];
                        underlineFontSize[0] = new Vector2(1300, standardWallSize);
                        underlineFontPosition[0] = new Vector2(GameAssets.screenBounds_left.getX() + 320, 860);

                //position of stars when different amounts are displayed, starCount received in constructor
                starPosition = new Vector2[starCount];
                    if (starCount == 1) {
                        starPosition[0] = new Vector2(((GameAssets.screenResolutionX / 2) - (starSize.x / 2)), 480);
                    }
                    else if (starCount == 2) {
                        starPosition[0] = new Vector2((GameAssets.screenResolutionX / 2) - (starSize.x + (starSize.x / 4)), 480);
                        starPosition[1] = new Vector2((GameAssets.screenResolutionX / 2) + (starSize.x / 4), 480);
                    }
                    else if (starCount == 3) {
                        starPosition[0] = new Vector2(400, 480);
                        starPosition[1] = new Vector2(GameAssets.screenResolutionX / 2 - 110, 480);
                        starPosition[2] = new Vector2(1285, 480);
                    }
                    else if (starCount == 4) {
                        starPosition[0] = new Vector2(200, 480);
                        starPosition[1] = new Vector2(630, 480);
                        starPosition[2] = new Vector2(1060, 480);
                        starPosition[3] = new Vector2(1500, 480);
                    }
                    else if (starCount == 5) {
                        starPosition[0] = new Vector2((GameAssets.screenResolutionX / 2) - (starSize.x + (starSize.x / 2)), 370);
                        starPosition[1] = new Vector2((GameAssets.screenResolutionX / 2) + (starSize.x / 2), 370);
                        starPosition[2] = new Vector2(400, 590);
                        starPosition[3] = new Vector2(GameAssets.screenResolutionX / 2 - 110, 590);
                        starPosition[4] = new Vector2(1285, 590);
                    }

                //begin playing the level complete music
                GameAssets.playMusic(GameAssets.LEVEL_COMPLETE_MUSIC_INT);

                //storing the number of levels the user has completed
                int maxLevelCompleted = 1;
                maxLevelCompleted += currentLevel;
                if (maxLevelCompleted > GameAssets.getMaxLevelCompleted()) {
                    GameAssets.setMaxLevelCompleted(maxLevelCompleted);
                }

                //storing the new total amount of stars that the user has collected
                GameAssets.setTotalStarsCollected(GameAssets.getTotalStarsCollected() + starCount);
                Gdx.app.log("Total Stars", Integer.toString(GameAssets.getTotalStarsCollected()));

                //updating the current amount of stars in prefs file
                GameAssets.setCurrentAmountOfStars(GameAssets.getCurrentAmountOfStars() + starCount);

                break;

//--------------------------------------------------------------------------------------------------
            case DEATH_SCREEN:

                //a player is created to be passed the enemy, need to allow enemy to move etc.
                playerStartPosition = new Vector2(GameAssets.screenBounds_left.getX() + 30, 1000);
                player = new Player(playerStartPosition, playerSize);

                levelSelectMenuButtonCount = 1;
                    levelSelectMenuButtonPosition = new Vector2[levelSelectMenuButtonCount];
                        levelSelectMenuButtonPosition[0] = new Vector2(((GameAssets.screenResolutionX / 2) - (levelSelectMenuButtonSize.x / 2)) + levelSelectMenuButtonSize.x, 20);

                restartButtonCount = 1;
                    restartButtonPosition = new Vector2[restartButtonCount];
                        restartButtonPosition[0] = new Vector2(((GameAssets.screenResolutionX / 2) - (levelSelectMenuButtonSize.x / 2)) - levelSelectMenuButtonSize.x, 20);

                newEnemyCount = 1;
                //making the enemy bigger for this menu
                enemySize = new Vector2(100, 100);
                    newEnemyStartPosition = new Vector2[newEnemyCount];
                        newEnemyStartPosition[0] = new Vector2(600, 500);

                //number of waypoints for each enemy object
                newWaypointCount = 2;
                    //2-D Vector2 array to hold the position of waypoints for each enemy, [enemyNumber][nextWaypoint]
                    newWaypoints = new Vector2[newWaypointCount][newWaypointCount];
                        //waypoints for enemy[0]
                        newWaypoints[0][0] = new Vector2((GameAssets.screenBounds_left.getX() + 400) - enemySize.x, newEnemyStartPosition[0].y);
                        newWaypoints[0][1] = new Vector2(GameAssets.screenBounds_right.getX() - 400, newEnemyStartPosition[0].y);

                underlineFontCount = 1;
                    underlineFontSize = new Vector2[underlineFontCount];
                    underlineFontPosition = new Vector2[underlineFontCount];
                        underlineFontSize[0] = new Vector2(1300, standardWallSize);
                        underlineFontPosition[0] = new Vector2(GameAssets.screenBounds_left.getX() + 320, 820);

                //begin playing the captured music
                GameAssets.playMusic(GameAssets.CAPTURED_MUSIC_INT);

                //update the prefs file for the total time the user has been captured
                GameAssets.setTotalDeaths(GameAssets.getTotalDeaths() + 1);
                Gdx.app.log("Total Deaths", Integer.toString(GameAssets.getTotalDeaths()));

                break;

//--------------------------------------------------------------------------------------------------
            case CREDITS_SCREEN:

                mainButtonCount = 1;
                    mainButtonPosition = new Vector2[mainButtonCount];
                        mainButtonPosition[0] = new Vector2((GameAssets.screenResolutionX / 2 - 200), 70);

                subButtonCount = 4;
                    subButtonPosition = new Vector2[subButtonCount];
                        //menu music button position
                        subButtonPosition[0] = new Vector2(GameAssets.screenBounds_left.getX() + 40, GameAssets.screenBounds_top.getY() - 260);
                        //level music button position
                        subButtonPosition[1] = new Vector2(GameAssets.screenBounds_left.getX() + 40, GameAssets.screenBounds_top.getY() - 380);
                        //level complete music button position
                        subButtonPosition[2] = new Vector2(GameAssets.screenBounds_left.getX() + 40, GameAssets.screenBounds_top.getY() - 500);
                        //captured music button position
                        subButtonPosition[3] = new Vector2(GameAssets.screenBounds_left.getX() + 40, GameAssets.screenBounds_top.getY() - 610);

                underlineFontCount = 1;
                    underlineFontSize = new Vector2[underlineFontCount];
                    underlineFontPosition = new Vector2[underlineFontCount];
                        underlineFontSize[0] = new Vector2(550, standardWallSize);
                        underlineFontPosition[0] = new Vector2(GameAssets.screenBounds_right.getX() - 570, GameAssets.screenBounds_top.getY() - 190);

                //if the user did not come the main menu, begin playing the menu music
                if (Menu.previousScreen != MAIN_MENU_SCREEN) {
                    GameAssets.playMusic(GameAssets.MENU_MUSIC_INT);
                }

                break;
        }

//====================================================================================================================\\
/**********************************************************************************************************************
 ****END OF MENUS - all menus are initialised above, the code below creates a screen based on the switch data above****
 ***********************************************************************************************************************/
//====================================================================================================================\\

        levelButtons = new Button[levelButtonCount];
        levelSelectMenuButtons = new Button[levelSelectMenuButtonCount];
        backButtons = new Button[backButtonCount];
        mainButtons = new Button[mainButtonCount];
        subButtons = new Button[subButtonCount];
        creditsButtons = new Button[creditsButtonCount];
        restartButtons = new Button[restartButtonCount];
        toggleSoundButtons = new Button[toggleSoundButtonCount];
        exitButtons = new Button[exitButtonCount];
        newEnemies = new Enemy[newEnemyCount];
        underlineFonts = new Walls[underlineFontCount];
        stars = new Star[starCount];
        flags = new Flag[flagCount];

            //create temp int variables to act as each instance of an object
            for (int i = 0; i < levelButtonCount; i++) {
                //create all buttons of a certain type (Vector2 position, Vector2 size, TextureRegion buttonImage, int levelButtonValue)
                levelButtons[i] = createButton(levelButtonPosition[i], levelButtonSize, levelButtonImage, i);

                if (levelButtons[i].levelButtonValue <= GameAssets.getMaxLevelCompleted()) {
                    levelButtons[i].buttonImage = GameAssets.levelButtonSprite;
                }
            }

            //create any buttons to go to the level select screen
            for (int i = 0; i < levelSelectMenuButtonCount; i++) {
                //create all buttons of a certain type (Vector2 position, Vector2 size, TextureRegion buttonImage, int levelButtonValue)
                levelSelectMenuButtons[i] = createButton(levelSelectMenuButtonPosition[i], levelSelectMenuButtonSize, levelSelectMenuButtonImage, 0);
            }

            //create any buttons to return to a previous screen
            for (int i = 0; i < backButtonCount; i++) {
                //create all buttons of a certain type (Vector2 position, Vector2 size, TextureRegion buttonImage, int levelButtonValue)
                backButtons[i] = createButton(backButtonPosition[i], backButtonSize, backButtonImage, 0);
            }

            //create any main buttons
            for (int i = 0; i < mainButtonCount; i++) {
                //create all buttons of a certain type (Vector2 position, Vector2 size, TextureRegion buttonImage, int levelButtonValue)
                mainButtons[i] = createButton(mainButtonPosition[i], mainButtonSize, mainButtonImage, 0);
            }

            //create any sub buttons for a menu
            for (int i = 0; i < subButtonCount; i++) {
                //create all buttons of a certain type (Vector2 position, Vector2 size, TextureRegion buttonImage, int levelButtonValue)
                subButtons[i] = createButton(subButtonPosition[i], subButtonSize, mainButtonImage, 0);
            }

            //create any buttons to go to the credits screen
            for (int i = 0; i < creditsButtonCount; i++) {
                //create all buttons of a certain type (Vector2 position, Vector2 size, TextureRegion buttonImage, int levelButtonValue)
                creditsButtons[i] = createButton(creditsButtonPosition[i], creditsButtonSize, creditsButtonImage, 0);
            }

            //create any restart buttons
            for (int i = 0; i < restartButtonCount; i++) {
                //create all buttons of a certain type (Vector2 position, Vector2 size, TextureRegion buttonImage, int levelButtonValue)
                restartButtons[i] = createButton(restartButtonPosition[i], restartButtonSize, restartButtonImage, 0);
            }

            //create any of the toggle sound buttons if they exist
            for (int i = 0; i < toggleSoundButtonCount; i++) {
                //create all buttons of a certain type (Vector2 position, Vector2 size, TextureRegion buttonImage, int levelButtonValue)
                toggleSoundButtons[i] = createButton(toggleSoundButtonPosition[i], toggleSoundButtonSize, toggleSoundButtonImage, 0);
            }

            //create any exit buttons include din a menu
            for (int i = 0; i < exitButtonCount; i++) {
                //create all buttons of a certain type (Vector2 position, Vector2 size, TextureRegion buttonImage, int levelButtonValue)
                exitButtons[i] = createButton(exitButtonPosition[i], exitButtonSize, exitButtonImage, 0);
            }

            //create all enemies and sights for a menu
            for (int i = 0; i < newEnemyCount; i++) {
                newEnemies[i] = createNewEnemy(newEnemyStartPosition[i], enemySize, newWaypointCount, newWaypoints, i);
            }

            //create all the underlineFonts for a menu
            for (int i = 0; i < underlineFontCount; i++) {
                underlineFonts[i] = createWall(underlineFontPosition[i], underlineFontSize[i]);
            }

            //create any stars used in a menu
            for (int i = 0; i < starCount; i++) {
                    stars[i] = createStar(starPosition[i], starSize, starCount);
                }

            //create any flags used in a menu
            for (int i = 0; i < flagCount; i++) {
                flags[i] = createFlag(flagPosition[i], flagSize);
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

        //if there are any level levelSelectMenuButtons in a screen, draw them
        for (int i = 0; i < levelButtonCount; i++) {
            levelButtons[i].draw(batch);
        }

        //draw any levelSelectMenuButtons to the screen
        for (int i = 0; i < levelSelectMenuButtonCount; i++) {
            levelSelectMenuButtons[i].draw(batch);
        }

        //draw a back button to the screen
        for (int i = 0; i < backButtonCount; i++) {
            backButtons[i].draw(batch);
        }

        //draw any main buttons to the screen
        for (int i = 0; i < mainButtonCount; i++) {
            mainButtons[i].draw(batch);
        }

        //draw any sub buttons to the screen
        for (int i = 0; i < subButtonCount; i++) {
            subButtons[i].draw(batch);
        }

        //draw buttons to take user to credits screen
        for (int i = 0; i < creditsButtonCount; i++) {
            creditsButtons[i].draw(batch);
        }

        //draw any restart buttons to the screen
        for (int i = 0; i < restartButtonCount; i++) {
            restartButtons[i].draw(batch);
        }

        //find any toggle sound buttons if they exist
        for (int i = 0; i < toggleSoundButtonCount; i++) {

            //if the sound is currently turned on
            if (GameAssets.getToggleSoundState()) {
                //set the image of the toggle sound button to its 'on' image
                toggleSoundButtons[i].buttonImage = GameAssets.soundOnSprite;
            }
            //if the sound is not currently toggled on
            else if (!GameAssets.getToggleSoundState()) {
                //set the image of the toggle sound button to its 'off' image
                toggleSoundButtons[i].buttonImage = GameAssets.soundOffSprite;
            }

            //draw all of the toggle sound buttons to the batch
            toggleSoundButtons[i].draw(batch);
        }

        //draw any exit buttons to the screen
        for (int i = 0; i < exitButtonCount; i++) {
            exitButtons[i].draw(batch);
        }

        //draw all enemies to the screen
        for (int i = 0; i < newEnemyCount; i++) {
            newEnemies[i].draw(batch);
        }

        //draw any underlineFonts (includes underlines for titles etc.)
        for (int i = 0; i < underlineFontCount; i++) {
            underlineFonts[i].draw(batch);
        }

        //draw any stars to the screen
        for (int i = 0; i < starCount; i++) {
            stars[i].draw(batch);
        }

        //draw any exit flags present in a menu
        for (int i = 0; i < flagCount; i++) {
            flags[i].draw(batch);
        }

//---------------------------------Extra Assets for Different Screens-------------------------------
        //check what screen to display
        switch (currentScreen) {

//--------------------------------------------------------------------------------------------------
            case MAIN_MENU_SCREEN:

                //size of the chosen font
                GameAssets.fontRobotoRegular.getData().setScale(1f);
                GameAssets.fontRobotoRegular.draw(batch, "Paperscape", 420, 1050);

                for (int i = 0; i < mainButtonCount; i++) {
                    GameAssets.fontRobotoThinBold.getData().setScale(0.9f);
                        GameAssets.fontRobotoThinBold.draw(batch, "Start", mainButtons[0].buttonBounds.getX() + 128, mainButtons[0].buttonBounds.getY() + 95);
                }

                for (int i = 0; i < exitButtonCount; i++) {
                    GameAssets.fontRobotoThinBold.getData().setScale(.6f);
                        GameAssets.fontRobotoThinBold.draw(batch, "Exit", exitButtons[i].buttonBounds.getX() + 100, exitButtons[i].buttonBounds.getY() + 60);
                }

                for (int i = 0; i < creditsButtonCount; i++) {
                    GameAssets.fontRobotoThinBold.getData().setScale(.45f);
                        GameAssets.fontRobotoThinBold.draw(batch, "Credits", creditsButtons[i].buttonBounds.getX() + 13, creditsButtons[i].buttonBounds.getY() + 75);
                    GameAssets.fontRobotoThinBold.getData().setScale(.65f);
                }

                break;

//--------------------------------------------------------------------------------------------------
            case LEVEL_SELECT_SCREEN:

                //size of the chosen font
                GameAssets.fontRobotoRegular.getData().setScale(1f);
                //drawing text to the screen
                GameAssets.fontRobotoRegular.draw(batch, "Level Select", 380, 225);
                //change font colour to white for levelSelectMenuButtons
                GameAssets.fontRobotoThinBold.setColor(Color.WHITE);
                //reduce the size of the font to fit on top of button
                GameAssets.fontRobotoThinBold.getData().setScale(.55f);
                //drawing the font for level numbers on level levelSelectMenuButtons
                //for loop to find each button
                for (int i = 0; i < levelButtonCount; i++) {
                    //holds the text to display a level number
                    String levelNumber;
                    //level number is equal to 1 as arrays start at 0
                    int j = 1;
                    //level number is equal to 1 + array value
                    j += i;
                    //convert level number to a string
                    levelNumber = Integer.toString(j);

                    openLevelsTextPosition = new Vector2(levelButtons[i].buttonBounds.x + 35, levelButtons[i].buttonBounds.y + 125);
                    lockedLevelsTextPosition = new Vector2(levelButtons[i].buttonBounds.x + 35, levelButtons[i].buttonBounds.y + 85);

                    if (levelButtons[i].levelButtonValue <= GameAssets.getMaxLevelCompleted()) {

                        //if the level button leads to a level that is less than level 10
                        if (j < 10) {
                            //draw text on top of each button
                            GameAssets.fontRobotoThinBold.draw(batch, "Level 0" + levelNumber, openLevelsTextPosition.x, openLevelsTextPosition.y);
                        }

                        //if the level button leads to a level between level 10 and level 100
                        if (j >= 10 && j < 100) {
                            //draw text on top of each button
                            GameAssets.fontRobotoThinBold.draw(batch, "Level " + levelNumber, openLevelsTextPosition.x, openLevelsTextPosition.y);
                        }
                    }
                    //otherwise if the level that the button leads to is locked, draw the text in a different position
                    else if (!(levelButtons[i].levelButtonValue <= GameAssets.getMaxLevelCompleted())) {
                        //if the level button leads to a level that is less than level 10
                        if (j < 10) {
                            //draw text on top of each button
                            GameAssets.fontRobotoThinBold.draw(batch, "Level 0" + levelNumber, lockedLevelsTextPosition.x, lockedLevelsTextPosition.y);
                        }
                        //if the level button leads to a level between level 10 and level 100
                        if (j >= 10 && j < 100) {
                            //draw text on top of each button
                            GameAssets.fontRobotoThinBold.draw(batch, "Level " + levelNumber, lockedLevelsTextPosition.x, lockedLevelsTextPosition.y);
                        }
                    }

                }

                //change the font colour back to default black
                GameAssets.fontRobotoThinBold.setColor(Color.BLACK);
                //change the size of the font back to its original value
                GameAssets.fontRobotoThinBold.getData().setScale(.6f);

                break;

//--------------------------------------------------------------------------------------------------
            case LEVEL_COMPLETE_SCREEN:

                //setting the size of the chosen font
                GameAssets.fontRobotoRegular.getData().setScale(.8f);
                    GameAssets.fontRobotoRegular.draw(batch, "Level Complete", 430, 1042);
                GameAssets.fontRobotoRegular.getData().setScale(1f);

                //setting the size of the chosen font
                GameAssets.fontRobotoThinBold.getData().setScale(.6f);
                //displaying info about the total stars collected by interrogating the prefs file
                //draw to screen (SpriteBatch batch, String textToDisplay, int x, int y, int width, int textAlignment, boolean wrapText)
                GameAssets.fontRobotoThinBold.draw(batch, "Total Stars Collected: " + GameAssets.getTotalStarsCollected(),
                        (GameAssets.screenBounds_right.getX() - 650), 350, 600, 1, true);

                break;

//--------------------------------------------------------------------------------------------------
            case DEATH_SCREEN:

                GameAssets.fontRobotoRegular.setColor(Color.RED);
                    //size of the chosen font
                    GameAssets.fontRobotoRegular.getData().setScale(1f);
                    GameAssets.fontRobotoRegular.draw(batch, "Captured!", 530, 1050);
                GameAssets.fontRobotoRegular.setColor(Color.BLACK);

/*                GameAssets.fontRobotoThinBold.getData().setScale(.6f);
                GameAssets.fontRobotoThinBold.draw(batch, "Times Caught: " + GameAssets.getTotalDeaths(), 1220, 790);*/

                break;

//--------------------------------------------------------------------------------------------------
            case CREDITS_SCREEN:
                GameAssets.fontRobotoRegular.getData().setScale(.75f);
                GameAssets.fontRobotoRegular.draw(batch, "Credits", GameAssets.screenBounds_right.getX() - 500, GameAssets.screenBounds_top.getY() - 40);
                GameAssets.fontRobotoRegular.getData().setScale(1);

                for (int i = 0; i < mainButtonCount; i++) {
                    GameAssets.fontRobotoThinBold.draw(batch, "Back", mainButtons[0].buttonBounds.x + 135, mainButtons[0].buttonBoundsYEnd - 40);
                }

                for (int i = 0; i < subButtonCount; i++) {
                    GameAssets.fontRobotoThinBold.draw(batch, "Menu Music", subButtons[0].buttonBounds.x + buttonTextWidth, subButtons[0].buttonBoundsYEnd - buttonTextHeight);
                    GameAssets.fontRobotoThinBold.draw(batch, "Level Music", subButtons[1].buttonBounds.x + buttonTextWidth, subButtons[1].buttonBoundsYEnd - buttonTextHeight);
                    GameAssets.fontRobotoThinBold.draw(batch, "Goal Music", subButtons[2].buttonBounds.x + (buttonTextWidth + 10), subButtons[2].buttonBoundsYEnd - buttonTextHeight);
                    GameAssets.fontRobotoThinBold.draw(batch, "Caught Music", subButtons[3].buttonBounds.x + (buttonTextWidth - 20), subButtons[3].buttonBoundsYEnd - buttonTextHeight);
                }

                GameAssets.fontRobotoThinBold.draw(batch, "Music:", GameAssets.screenBounds_left.getX() + 40, GameAssets.screenBounds_top.getY() - 60);
                GameAssets.fontRobotoThinBold.draw(batch, "Programming, Graphics & Sound Effects By:", GameAssets.screenBounds_left.getX() + 40, GameAssets.screenBounds_bottom.getY() + 100);

                GameAssets.fontRobotoThinBold.draw(batch, "Alex Clarke", GameAssets.screenBounds_right.getX() - 350, GameAssets.screenBounds_bottom.getY() + 85);

                GameAssets.fontRobotoThinBold.draw(batch, GameAssets.trackTitle, infoTextX, infoTextY);
                GameAssets.fontRobotoThinBold.draw(batch, GameAssets.trackArtist, infoTextX, infoTextY - 60);
                GameAssets.fontRobotoThinBold.draw(batch, GameAssets.trackAlbum, infoTextX, infoTextY - 120);
                GameAssets.fontRobotoThinBold.draw(batch, GameAssets.license, infoTextX, infoTextY - 180);
                GameAssets.fontRobotoThinBold.draw(batch, GameAssets.licenseLink, infoTextX, infoTextY - 240);
                GameAssets.fontRobotoThinBold.draw(batch, GameAssets.changesMade, infoTextX, infoTextY - 300);

                break;
        }
    }

/*============================================================================================================================
                                                **ShapeRenderer**
=============================================================================================================================*/
    public void renderEnemies(ShapeRenderer shapeRenderer) {
        for (int i = 0; i < newEnemyCount; i++) {
            //passing (ShapeRenderer shapeRenderer, int enemyCount
            newEnemies[i].renderShapes(shapeRenderer, i);
        }
    }

/*============================================================================================================================
                                                **Updating**
=============================================================================================================================*/
    public void update(PaperscapeGame game, Vector3 touch, OrthographicCamera camera) {

        GameAssets.game = game;
        //receiving a camera from the Menu Class and initialising it
        GameAssets.camera = camera;
        GameAssets.touch = touch;

        //check for any touches on the screen
        for (int i = 0; i < GameAssets.numberOfMultiTouches; i++) {
            if (Gdx.input.isTouched(i)) {
                //Setting a value for the position of a the 5 touches
                touch.set(Gdx.input.getX(i), Gdx.input.getY(i), 0);
                //allows re-sizing while still correct touch locations
                GameAssets.camera.unproject(touch);
                //write the position of the touch to the console
                Gdx.app.log("Touch Position " + Integer.toString(i), "X" + Float.toString(touch.x) + ", " + "Y" + Float.toString(touch.y));
            }
        }

//--------------------------------------------------------------------------------------------------
        //run code for all of the level buttons
        for (int i = 0; i < levelButtonCount; i++) {
            //check for touches on any of the buttons
            levelButtons[i].update(touch, camera);
                //if a level button is clicked and the level number is less than...
                //...the value of the maximum level completed by the player
                if (levelButtons[i].isClicked &&
                        levelButtons[i].levelButtonValue <= GameAssets.getMaxLevelCompleted()) {
                    //run the stopMusic method from GameAssets passing the int for the menu music
                    GameAssets.stopMusic(GameAssets.MENU_MUSIC_INT);
                }
        }

//--------------------------------------------------------------------------------------------------
        //run code for all of the level select buttons
        for (int i = 0; i < levelSelectMenuButtonCount; i++) {
            //check for touches on any of the level select menu buttons
            levelSelectMenuButtons[i].update(touch, camera);
                //if the Boolean for any of the buttons being clicked is true
                if (levelSelectMenuButtons[i].isClicked) {
//--------------------------------------------------------------------------------------------------
                    //check which screen the level select menu button is being displayed on
                    switch (MenuManager.currentScreen) {

                        //if the button is on the level complete screen
                        case MenuManager.LEVEL_COMPLETE_SCREEN:
                            //run the stopMusic method from GameAssets passing the int for the level complete music
                            GameAssets.stopMusic(GameAssets.LEVEL_COMPLETE_MUSIC_INT);

                            break;
//--------------------------------------------------------------------------------------------------
                        //if the buttons is on the death/captured screen
                        case MenuManager.DEATH_SCREEN:
                            //run the stopMusic method from GameAssets passing the int for the captured music
                            GameAssets.stopMusic(GameAssets.CAPTURED_MUSIC_INT);

                            break;
//--------------------------------------------------------------------------------------------------
                    }
                }
        }

//--------------------------------------------------------------------------------------------------
        for (int i = 0; i < backButtonCount; i++) {
            backButtons[i].update(touch, camera);

                if (backButtons[i].isClicked) {
                    //run code here when any back buttons are pressed
                }
        }

//--------------------------------------------------------------------------------------------------
        for (int i = 0; i < mainButtonCount; i++) {
            mainButtons[i].update(touch, camera);

                if (mainButtons[i].isClicked) {
                    //run code here when any of the large recatangle buttons are pressed
                }
        }

//--------------------------------------------------------------------------------------------------
        for (int i = 0; i < subButtonCount; i++) {
            subButtons[i].update(touch, camera);

                if (subButtons[i].isClicked) {
                    //run code here when any sub button is pressed
                }
        }

//--------------------------------------------------------------------------------------------------
        for (int i = 0; i < creditsButtonCount; i++) {
            creditsButtons[i].update(touch, camera);

                if (creditsButtons[i].isClicked) {
                    //run code here when any button to go to the credits screen is pressed
                }
        }

//--------------------------------------------------------------------------------------------------
        //run code for all of the restart level buttons
        for (int i = 0; i < restartButtonCount; i++) {
            //check for touches on any of the buttons
            restartButtons[i].update(touch, camera);
            //if the Boolean for determining if a button has been pressed is true
            if (restartButtons[i].isClicked) {
                //run the stopMusic method from GameAssets passing the int for the captured music
                GameAssets.stopMusic(GameAssets.CAPTURED_MUSIC_INT);
            }
        }

//--------------------------------------------------------------------------------------------------
        //check all of the toggleSoundButtons if there are any
        for (int i = 0; i < toggleSoundButtonCount; i++) {
            //look for touches on any of the buttons
            toggleSoundButtons[i].update(touch, camera);

            if (toggleSoundButtons[i].isClicked) {
                //what to do when any toggleSoundButtons are pressed
                //also used in Level/Menu classes
            }
        }

//--------------------------------------------------------------------------------------------------
        for (int i = 0; i < exitButtonCount; i++) {
            exitButtons[i].update(touch, camera);

            if (exitButtons[i].isClicked) {
                //run if any of the exit buttons are pressed, closes the application
                Gdx.app.exit();
            }
        }

//------------------------------Update all enemies in a menu---------------------------------------
        for (int i = 0; i < newEnemyCount; i++) {
            //run the update method from all enemies and sights
            newEnemies[i].update(player);
            //------------------------------------------
            //deciding which direction to move an enemy
            if (newEnemies[i].directionMoving == "LEFT") {
                newEnemies[i].position.x -= newEnemies[i].speed;
            } else if (newEnemies[i].directionMoving == "RIGHT") {
                newEnemies[i].position.x += newEnemies[i].speed;
            } else if (newEnemies[i].directionMoving == "UP") {
                newEnemies[i].position.y += newEnemies[i].speed;
            } else if (newEnemies[i].directionMoving == "DOWN") {
                newEnemies[i].position.y -= newEnemies[i].speed;
            }
        }

//--------------------------------------------------------------------------------------------------
        for (int i = 0; i < flagCount; i ++) {
            //update passing (player, starCount)
            flags[i].update(player, 0);
        }

    }

/*============================================================================================================================
                                               **Create Methods**
=============================================================================================================================*/
    public Button createButton(Vector2 position, Vector2 size, TextureRegion image, int levelButtonValue) {
        Button button;
        button = new Button(position, size, image, levelButtonValue);
        return button;
    }

//============================================================================================================================//
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

/*============================================================================================================================
                                               **Dispose Method**
=============================================================================================================================*/
    public void dispose() {

        GameAssets.dispose();

    }


//============================================================================================================================//
}
//============================================================================================================================//