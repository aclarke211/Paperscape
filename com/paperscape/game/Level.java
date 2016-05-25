package com.paperscape.game;
//============================================================================================================================//
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;

//============================================================================================================================//
public class Level implements Screen {

    LevelManager levelManager;
    int currentLevel;
    static int gameState;
    OnScreenControls guiControls;

    //--------ints game state switch------------------
    static final int RUN_STATE = 1,
                     PAUSE_STATE = 2;

    //============================================================================================================================//
    public Level(PaperscapeGame game, int currentLevel) {

        //initialise the screen as a game so it can be seen by the main class
        GameAssets.game = game;
        this.currentLevel = currentLevel;

        //setting the left_right_width and left_right_height of camera object, false means position of 0 on axis (top or bottom)
        GameAssets.camera.setToOrtho(false, GameAssets.screenResolutionX, GameAssets.screenResolutionY);

        //acts as the on screen controls
        guiControls = new OnScreenControls();
        //used to create an individual level
        levelManager = new LevelManager(currentLevel, GameAssets.camera);
        //initialising the state of the game to be in a running state
        gameState = RUN_STATE;

        //--------------Make the app wait before drawing/updating------------------------------------
        try {
            //how long to wait in milliseconds
            Thread.sleep(GameAssets.waitTimeForScreens);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

    }

    //============================================================================================================================//
    @Override
    public void render(float delta) {

        //makes the whole screen a colour, (red, green, blue, alpha) how much of each?, 0-1F, 1F = white, alpha = transparency
        Gdx.gl.glClearColor(1F, 1F, 1F, 1F);
        //clears the canvas ready for drawing
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //update the camera on the screen
        GameAssets.camera.update();

        //continuously checking for touches or inputs
        generalUpdate(GameAssets.touch, GameAssets.camera);

        //------------------------------------
        /*Sprite Batch*/
        //resize the sprite batch depending on screen size
        GameAssets.batch.setProjectionMatrix(GameAssets.camera.combined);
        //resize the shape renderer depending on screen size
        GameAssets.shapeRenderer.setProjectionMatrix(GameAssets.camera.combined);

//--------------------------------------------------------------------------------------------------
        //code that draws sprites to the screen goes after this
        GameAssets.batch.begin();

            levelManager.draw(GameAssets.batch);

//--------------------------------------------------------------------------------------------------
                                    /*Drawing fonts for individual levels*/
            switch (currentLevel) {
//--------------------------------------------------------------------------------------
                case 1:
                    //while the game is not paused
                    if (gameState == RUN_STATE) {

                        //set the size of the chosen font
                        GameAssets.fontRobotoThinBold.getData().setScale(.55f);

                        //draw the following text when the exit flag is being displayed on screen
                        if (Flag.drawExit) {
                            //draw to screen (SpriteBatch batch, String textToDisplay, int x, int y, int width, int textAlignment, boolean wrapText)
                            GameAssets.fontRobotoThinBold.draw(GameAssets.batch, "Reach the exit to complete the level.",
                                    1240, 455, 600, 1, true);
                        }

                        //only draw this text before the exit flag appears on screen
                        else if (!Flag.drawExit) {
                            //draw to screen (SpriteBatch batch, String textToDisplay, int x, int y, int width, int textAlignment, boolean wrapText)
                            GameAssets.fontRobotoThinBold.draw(GameAssets.batch, "Use the buttons below the margin to " +
                                    "move the player.", 100, 465, 600, 1, true);

                            //set the size of the chosen font
                            GameAssets.fontRobotoThinBold.getData().setScale(.9f);
                                GameAssets.fontRobotoThinBold.draw(GameAssets.batch, "Avoid enemies and collect stars!", 490, 1000);
                        }

                    }

                    break;

//--------------------------------------------------------------------------------------
                case 6:

                    //while the game is not paused
                    if (gameState == RUN_STATE) {

                        //if none of the door open buttons are being pressed...
                        if (!levelManager.activateDoorButtons[0].buttonTouched &&
                                !levelManager.activateDoorButtons[1].buttonTouched) {
                            //set the size of the chosen font
                            GameAssets.fontRobotoThinBold.getData().setScale(.8f);
                            //draw to screen (SpriteBatch batch, String textToDisplay, int x, int y, int width, int textAlignment, boolean wrapText)
                            GameAssets.fontRobotoThinBold.draw(GameAssets.batch, "Activating buttons...", 120, 990);
                        }

                        if (levelManager.activateDoorButtons[0].buttonTouched) {
                            //set the size of the chosen font
                            GameAssets.fontRobotoThinBold.getData().setScale(.6f);
                            //draw to screen (SpriteBatch batch, String textToDisplay, int x, int y, int width, int textAlignment, boolean wrapText)
                            GameAssets.fontRobotoThinBold.draw(GameAssets.batch, "...can open doors to new paths.",
                                    1090, 1000, 500, 1, true);
                        }

                            else if (levelManager.activateDoorButtons[1].buttonTouched) {
                                //set the size of the chosen font
                                GameAssets.fontRobotoThinBold.getData().setScale(.6f);
                                //draw to screen (SpriteBatch batch, String textToDisplay, int x, int y, int width, int textAlignment, boolean wrapText)
                                GameAssets.fontRobotoThinBold.draw(GameAssets.batch, "...can reveal the exit.",
                                        1080, 440, 500, 1, true);
                            }

                    }

                    break;

//--------------------------------------------------------------------------------------
                case 7:

                    //while the game is not paused
                    if (gameState == RUN_STATE) {

                        //if none of the door open buttons are being pressed...
                        if (!levelManager.activateDoorButtons[0].buttonTouched &&
                                !levelManager.activateDoorButtons[1].buttonTouched &&
                                !levelManager.activateDoorButtons[2].buttonTouched) {
                            //set the size of the chosen font
                            GameAssets.fontRobotoRegular.getData().setScale(.5f);
                            //draw to screen (SpriteBatch batch, String textToDisplay, int x, int y, int width, int textAlignment, boolean wrapText)
                            GameAssets.fontRobotoRegular.draw(GameAssets.batch, "Beware of doors...", 700, 1020);
                        }

                        if (levelManager.activateDoorButtons[0].buttonTouched &&
                                !levelManager.activateDoorButtons[1].doorClosed) {
                            //set the size of the chosen font
                            GameAssets.fontRobotoRegular.getData().setScale(.5f);

                            if (levelManager.player.starsCollected == levelManager.starCount) {
                                //draw to screen (SpriteBatch batch, String textToDisplay, int x, int y, int width, int textAlignment, boolean wrapText)
                                GameAssets.fontRobotoRegular.draw(GameAssets.batch, "Quick, for the exit!", 700, 1020);
                            }
                                else if (levelManager.player.starsCollected != levelManager.starCount) {
                                //draw to screen (SpriteBatch batch, String textToDisplay, int x, int y, int width, int textAlignment, boolean wrapText)
                                GameAssets.fontRobotoRegular.draw(GameAssets.batch, "Beware of doors...", 700, 1020);
                            }

                        }
                            else if (levelManager.activateDoorButtons[0].buttonTouched &&
                                levelManager.activateDoorButtons[1].doorClosed) {
                            //set the size of the chosen font
                            GameAssets.fontRobotoRegular.getData().setScale(.5f);
                            //draw to screen (SpriteBatch batch, String textToDisplay, int x, int y, int width, int textAlignment, boolean wrapText)
                            GameAssets.fontRobotoRegular.draw(GameAssets.batch, "Beware of doors...", 700, 1020);
                            }

                        if (levelManager.activateDoorButtons[1].buttonTouched) {
                            //set the size of the chosen font
                            GameAssets.fontRobotoThinBold.getData().setScale(.6f);
                            //draw to screen (SpriteBatch batch, String textToDisplay, int x, int y, int width, int textAlignment, boolean wrapText)
                            GameAssets.fontRobotoThinBold.draw(GameAssets.batch, "...from some you can't escape.",
                                    440, 490, 400, 1, true);
                        }

                        if (levelManager.activateDoorButtons[2].buttonTouched) {
                            //set the size of the chosen font
                            GameAssets.fontRobotoThinBold.getData().setScale(.6f);
                            //draw to screen (SpriteBatch batch, String textToDisplay, int x, int y, int width, int textAlignment, boolean wrapText)
                            GameAssets.fontRobotoThinBold.draw(GameAssets.batch, "...trapping you inside.",
                                    1140, 490, 400, 1, true);
                        }

                        if (levelManager.player.bounds.getX() >= levelManager.doors[1].bounds.getX() &&
                                levelManager.player.bounds.getX() <= levelManager.doors[1].getBoundsXEnd() &&
                                levelManager.player.bounds.getY() <= levelManager.doors[1].bounds.getY()) {

                            if (levelManager.activateDoorButtons[1].doorClosed) {
                                //set the size of the chosen font
                                GameAssets.fontRobotoThinBold.getData().setScale(.6f);
                                //draw to screen (SpriteBatch batch, String textToDisplay, int x, int y, int textAlignment, boolean wrapText)
                                GameAssets.fontRobotoThinBold.draw(GameAssets.batch, "Uh oh! (hint: pause to restart)", 780, 920);
                            }
                        }

                    }

                    break;

//--------------------------------------------------------------------------------------
            }

    //--------------------------------------------------------------------------------------------------
                //if the game is in a paused state, display the pause menu on top of the current level
                if (gameState == PAUSE_STATE) {
                    levelManager.drawPause(GameAssets.batch);
                }

        GameAssets.batch.end();

//------------------------------------------------------------
        //used to check the position of objects
        GameAssets.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            //levelManager.renderEnemies(GameAssets.shapeRenderer);
            //levelManager.renderStarPositions(GameAssets.shapeRenderer);
        GameAssets.shapeRenderer.end();

    }

    //============================================================================================================================//
    public void generalUpdate(Vector3 touch, OrthographicCamera camera) {

        //detect the state of the game, run the appropriate update methods
        switch (gameState) {

            case RUN_STATE:

                    //run the normal update methods for moving enemies etc.
                    levelManager.update(touch, camera, guiControls);
                    levelManager.updateLevel(GameAssets.game);

                break;

            case PAUSE_STATE:

                    //run the update method used specifically for when the game is paused
                    levelManager.updatePaused(touch, camera);

                    if (levelManager.toggleSoundButtons[0].isClicked) {

                        //if the toggle sound button is pressed and the sound is already playing
                        if (GameAssets.getToggleSoundState()) {
                             //run the stop music method in game assets passing the int value for the level music
                             GameAssets.stopMusic(GameAssets.LEVEL_MUSIC_INT);
                             //save the state of the volume to the preferences file
                             GameAssets.setToggleSoundState(false);
                             //write the state of the volume to the console
                             Gdx.app.log("Volume Toggled", Boolean.toString(GameAssets.getToggleSoundState()));
                        }
                        //otherwise if the button is pressed and the sound is not currently playing
                        else if (!GameAssets.getToggleSoundState()) {
                            GameAssets.setToggleSoundState(true);
                            GameAssets.playMusic(GameAssets.LEVEL_MUSIC_INT);
                            Gdx.app.log("Volume Toggled", Boolean.toString(GameAssets.getToggleSoundState()));
                        }

                    }

                    //runs if the button to go to the level select screen is pressed
                    if (levelManager.levelSelectMenuButtons[0].isClicked) {
                        //changes the screen to LevelSelect Screen. (game, currentScreen - next screen, previousScreen - this screen, currentLevel, starCount)
                        GameAssets.game.setScreen(new Menu(GameAssets.game, MenuManager.LEVEL_SELECT_SCREEN, 0, 0, 0));
                    }

                    //runs when the restart level button is clicked
                    if (levelManager.restartButtons[0].isClicked) {
                        //sets the screen to the level the player is currently on (PaperscapeGame game, int levelToLoad)
                        GameAssets.game.setScreen(new Level(GameAssets.game, levelManager.currentLevel));
                    }

                break;
        }

        }


    //============================================================================================================================//
    @Override
    //runs once when game is initialised, can be used for opening credits
    public void show() {


    }

    //============================================================================================================================//
    @Override
    public void pause() {

    }

    //============================================================================================================================//
    @Override
    public void resume() {

    }

    //============================================================================================================================//
    @Override
    public void dispose() {

        GameAssets.dispose();
        levelManager.dispose();

    }

    //============================================================================================================================//
    @Override
    public void hide() {


    }

    //============================================================================================================================//
    @Override
    public void resize(int width, int height) {

    }

//============================================================================================================================//
}
//============================================================================================================================//