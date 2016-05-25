package com.paperscape.game;
//============================================================================================================================//
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

//============================================================================================================================//
public class Menu implements Screen {

    MenuManager menuManager;
    int currentScreen, currentLevel, starCount;
    public static int previousScreen;

//============================================================================================================================//
    public Menu(PaperscapeGame game, int currentScreen, int previousScreen, int currentLevel, int starCount) {

        //initialise the screen as a game so it can be seen by the main class
        GameAssets.game = game;
        this.currentScreen = currentScreen;
        this.previousScreen = previousScreen;
        this.currentLevel = currentLevel;

        //setting the left_right_width and left_right_height of camera object, false means position of 0 on axis
        GameAssets.camera.setToOrtho(false, GameAssets.screenResolutionX, GameAssets.screenResolutionY);
        menuManager = new MenuManager(currentScreen, currentLevel, starCount);

        //--------------Make the app wait before drawing/updating------------------------------------
        if (MenuManager.currentScreen == MenuManager.DEATH_SCREEN) {
            GameAssets.waitTimeForScreens = 300;
        } else {
            GameAssets.waitTimeForScreens = 200;
        }

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
        generalUpdate(GameAssets.game, GameAssets.touch, GameAssets.camera);

        checkButtons();

        //------------------------------------
        /*Sprite Batch*/
        //resize the sprite batch depending on screen size
        GameAssets.batch.setProjectionMatrix(GameAssets.camera.combined);
        //resize the shape renderer depending on screen size
        GameAssets.shapeRenderer.setProjectionMatrix(GameAssets.camera.combined);

        //code that draws sprites to the screen goes after this
        GameAssets.batch.begin();
            menuManager.draw(GameAssets.batch);
        GameAssets.batch.end();

        /*
        //used to check the position of objects
        GameAssets.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            menuManager.renderEnemies(GameAssets.shapeRenderer);
        GameAssets.shapeRenderer.end();
        */

    }

//============================================================================================================================//
    public void generalUpdate(PaperscapeGame game, Vector3 touch, OrthographicCamera camera) {

        menuManager.update(game, touch, camera);

    }

//============================================================================================================================//
    public void checkButtons() {

        switch (MenuManager.currentScreen) {

//--------------------------------------------------------------------------------------------------
            case MenuManager.MAIN_MENU_SCREEN:

                if (menuManager.mainButtons[0].isClicked) {
                    //changes the screen to LevelSelect Screen. (game, currentScreen - next screen, previousScreen - this screen, currentLevel, starCount)
                    GameAssets.game.setScreen(new Menu(GameAssets.game, MenuManager.LEVEL_SELECT_SCREEN, MenuManager.MAIN_MENU_SCREEN, 0, 0));
                }

                if (menuManager.creditsButtons[0].isClicked) {
                    //changes the screen to Credits Screen. (game, currentScreen - next screen, previousScreen - this screen, currentLevel, starCount)
                    GameAssets.game.setScreen(new Menu(GameAssets.game, MenuManager.CREDITS_SCREEN, MenuManager.MAIN_MENU_SCREEN, 0, 0));
                }

                if (menuManager.toggleSoundButtons[0].isClicked) {

                    //if the toggle sound button is pressed and the sound is already playing
                    if (GameAssets.getToggleSoundState()) {
                        //run the stop music method in game assets passing the int value for the level music
                        GameAssets.stopMusic(GameAssets.MENU_MUSIC_INT);
                        //save the state of the volume to the preferences file
                        GameAssets.setToggleSoundState(false);
                        //write the state of the volume to the console
                        Gdx.app.log("Volume Toggled", Boolean.toString(GameAssets.getToggleSoundState()));
                    }
                    //otherwise if the button is pressed and the sound is not currently playing
                    else if (!GameAssets.getToggleSoundState()) {
                        GameAssets.setToggleSoundState(true);
                        GameAssets.playMusic(GameAssets.MENU_MUSIC_INT);
                        Gdx.app.log("Volume Toggled", Boolean.toString(GameAssets.getToggleSoundState()));
                    }

                }

                break;

//--------------------------------------------------------------------------------------------------
            case MenuManager.LEVEL_SELECT_SCREEN:

                //check if the button to go back to main menu is being pressed
                if (menuManager.backButtons[0].isClicked) {
                    //changes the screen to Main Menu Screen. (game, currentScreen - next screen, previousScreen - this screen, currentLevel, starCount)
                    GameAssets.game.setScreen(new Menu(GameAssets.game, MenuManager.MAIN_MENU_SCREEN, MenuManager.LEVEL_SELECT_SCREEN, 0, 0));
                }

                //find all of the level buttons in the level select screen
                for (int i = 0; i < menuManager.levelButtonCount; i++) {

                    //if any of the buttons are clicked and the level number is less than or equal to max level completed by a user
                    if (menuManager.levelButtons[i].isClicked &&
                            menuManager.levelButtons[i].levelButtonValue <= GameAssets.getMaxLevelCompleted()) {
                        //create a temporary int called j, it equals 1 as arrays begin at zero
                        // i.e. levelButtons[0] will take the user to level 01 etc.
                        int j = 1;
                        //j is equal to itself plus the value of a level button
                        j += i;
                        //set the screen to a new level, the level number is the value of j
                        GameAssets.game.setScreen(new Level(GameAssets.game, j));
                    }

                }

                break;

//--------------------------------------------------------------------------------------------------
            case MenuManager.LEVEL_COMPLETE_SCREEN:

                if (menuManager.levelSelectMenuButtons[0].isClicked) {
                    //changes the screen to LevelSelect Screen. (game, currentScreen - next screen, previousScreen - this screen, currentLevel, starCount)
                    GameAssets.game.setScreen(new Menu(GameAssets.game, MenuManager.LEVEL_SELECT_SCREEN, MenuManager.LEVEL_COMPLETE_SCREEN, 0, 0));
                }

                break;

//--------------------------------------------------------------------------------------------------
            case MenuManager.DEATH_SCREEN:

                if (menuManager.restartButtons[0].isClicked) {
                    GameAssets.game.setScreen(new Level(GameAssets.game, currentLevel));
                }

                if (menuManager.levelSelectMenuButtons[0].isClicked) {
                    //changes the screen to LevelSelect Screen. (game, currentScreen - next screen, previousScreen - this screen, currentLevel, starCount)
                    GameAssets.game.setScreen(new Menu(GameAssets.game, MenuManager.LEVEL_SELECT_SCREEN, MenuManager.DEATH_SCREEN, 0, 0));
                }

                break;

//--------------------------------------------------------------------------------------------------
            case MenuManager.CREDITS_SCREEN:

                if (menuManager.mainButtons[0].isClicked) {
                    //changes the screen to Main Menu Screen. (ggame, currentScreen - next screen, previousScreen - this screen, currentLevel, starCount)
                    GameAssets.game.setScreen(new Menu(GameAssets.game, MenuManager.MAIN_MENU_SCREEN, MenuManager.CREDITS_SCREEN, 0, 0));
                }

                if (menuManager.subButtons[0].isClicked) {
                    GameAssets.trackTitle = "DJ";
                    GameAssets.trackArtist = "Jahzzar";
                    GameAssets.trackAlbum = "DJ [single]";
                    GameAssets.license = "Creative Commons Attribution-ShareAlike 4.0 International";
                    GameAssets.licenseLink = "http://creativecommons.org/licenses/by-sa/4.0/";
                    GameAssets.changesMade = "No Changes Made";
                }

                if (menuManager.subButtons[1].isClicked) {
                    GameAssets.trackTitle = "AcidJazz";
                    GameAssets.trackArtist = "Kevin MacLeod";
                    GameAssets.trackAlbum = "Jazz Sampler";
                    GameAssets.license = "Creative Commons Attribution 3.0 Unported";
                    GameAssets.licenseLink = "http://creativecommons.org/licenses/by/3.0/";
                    GameAssets.changesMade = "No Changes Made";
                }

                if (menuManager.subButtons[2].isClicked) {
                    GameAssets.trackTitle = "New Hamra";
                    GameAssets.trackArtist = "Cosmic Analog Ensemble";
                    GameAssets.trackAlbum = "Deltas of Matacumbo";
                    GameAssets.license = "Creative Commons Attribution 4.0 International";
                    GameAssets.licenseLink = "http://creativecommons.org/licenses/by/4.0/";
                    GameAssets.changesMade = "No Changes Made";
                }

                if (menuManager.subButtons[3].isClicked) {
                    GameAssets.trackTitle = "Autoguano";
                    GameAssets.trackArtist = "Cosmic Analog Ensemble";
                    GameAssets.trackAlbum = "Deltas of Matacumbo";
                    GameAssets.license = "Creative Commons Attribution 4.0 International";
                    GameAssets.licenseLink = "http://creativecommons.org/licenses/by/4.0/";
                    GameAssets.changesMade = "No Changes Made";
                }

                break;

//--------------------------------------------------------------------------------------------------
        }
//--------------------------------------------------------------------------------------------------
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
        menuManager.dispose();

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