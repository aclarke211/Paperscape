//============================================================================================================================//
package com.paperscape.game;
//============================================================================================================================//
import com.badlogic.gdx.Game;

                            /** WORKING DIRECTORY MAY NEED TO BE CHANGED TO ANDROID ASSETS FOLDER
                                            WHEN PROJECT IS ORIGINALLY IMPORTED **/

//============================================================================================================================//
public class PaperscapeGame extends Game {

    //menuManager to determine which screen to show
    public MenuManager menuManager;
    //Menu object holds the screen which will be shown
    public Menu mainMenu;

//============================================================================================================================//
    @Override
    public void create () {

        //load the assets used by the game, this is the only time it is called in the game
        GameAssets.loadAssets();

        //Initialising the screens that will be used by the game
        //passing (currentScreen, currentLevel, starCount) to menuManager
        menuManager = new MenuManager(1, 0, 0);
        //create a new Main Menu screen using menuManager (game, currentScreen - next screen, previousScreen - this screen, currentLevel, starCount)
        mainMenu = new Menu(this, menuManager.MAIN_MENU_SCREEN, 0, 0, 0);
        //set the screen to the Main Menu initialised above
        setScreen(mainMenu);

    }

//============================================================================================================================//
}
//============================================================================================================================//
