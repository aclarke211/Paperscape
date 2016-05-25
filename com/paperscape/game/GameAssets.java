//============================================================================================================================//
package com.paperscape.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

//============================================================================================================================//
public class GameAssets {

    //declaring an instance of the main class for the project
    public static PaperscapeGame game;
    //resolution of the screen
    public static float screenResolutionX, screenResolutionY;
    //textures used by sprites
    public static Texture paperBackgroundTexture, spriteSheet1, spriteSheet2;
    //individual sprites
    public static Sprite paperBackgroundSprite, pauseBackgroundSprite,
            playerSprite, enemySprite, textBackgroundSprite, pauseButtonSprite,
            leftArrowSprite, rightArrowSprite, upArrowSprite, downArrowSprite,
            leftArrowSpritePressed, rightArrowSpritePressed, upArrowSpritePressed, downArrowSpritePressed,
            sightVerticalDownSprite, sightVerticalUpSprite, sightHorisontalLeftSprite, sightHorisontalRightSprite,
            levelButtonSprite, lockedLevelSprite, yellowButtonSprite, creditsButtonSprite,
            restartButtonSprite, levelSelectMenuSprite, soundOnSprite, soundOffSprite,
            exitButtonSprite, doorSprite, backButtonSprite, doorButtonSprite, doorButtonActiveSprite,
            wallSprite1, wallSprite2, wallSprite3, wallSprite4, wallSprite5;

    //int to hold the size of each individual sprite (i.e. 128 = 128x128 sprite image)
    public static int frameSize,
    //variables used to locate sprites within a sprite sheet
            framePosition01X, framePosition01Y, framePosition02X, framePosition02Y,
            framePosition03X, framePosition03Y, framePosition04X, framePosition04Y,
            framePosition05X, framePosition05Y, framePosition06X, framePosition06Y,
            framePosition07X, framePosition07Y, framePosition08X, framePosition08Y,
            framePosition09X, framePosition09Y, framePosition10X, framePosition10Y,
            framePosition11X, framePosition11Y, framePosition12X, framePosition12Y,
            framePosition13X, framePosition13Y, framePosition14X, framePosition14Y,
            framePosition15X, framePosition15Y, framePosition16X, framePosition16Y,
            //these ints hold a value of milliseconds, used when telling the app to sleep for a period of time
            waitTimeForScreens, timeForMusicToLoad, waitTimeForPause,
            //how many individual touches to the screen the game will recognise
            numberOfMultiTouches = 5;

    //these act as the boarders to the screen
    public static Rectangle screenBounds_left, screenBounds_right, screenBounds_top, screenBounds_bottom;

    //fonts used in the game
    public static BitmapFont fontRobotoRegular, fontRobotoThinBold;

    //sprite sheet of an animation
    public static Texture starAnimationSheet;
    //array to hold each tile in sprite sheet
    public static TextureRegion[] starSheetFrames;
    //frame in sprite sheet that will be on screen
    public static TextureRegion starCurrentFrame;
    //an animation object to create the animation from frames
    public static Animation starAnim;

    //sprite sheet of an animation
    public static Texture flagAnimationSheet;
    //array to hold each tile in sprite sheet
    public static TextureRegion[] flagSheetFrames;
    //frame in sprite sheet that will be on screen
    public static TextureRegion flagCurrentFrame;
    //an animation object to create the animation from frames
    public static Animation flagAnim;

    //music objects
    public static Music menuMusic, levelMusic, levelCompleteMusic, capturedMusic;
    //sound effects
    public static Sound star_pickup;

    //ints used in switch to see what music to play
    public static final int MENU_MUSIC_INT = 1,
                            LEVEL_MUSIC_INT = 2,
                            LEVEL_COMPLETE_MUSIC_INT = 3,
                            CAPTURED_MUSIC_INT = 4,
                            //sound effects
                            STAR_PICK_UP_SOUND_INT = 1;

    //these Strings hold the words for information about music used in the game
    public static String trackTitle, trackArtist, trackAlbum, license, licenseLink, changesMade;
    //this file is stored on the user's device and hold information such as their max completed level
    public static Preferences prefs;
    //holds the 'key' for checking data in the prefernces file
    public static final String PAPERSCAPE_PREFERENCES = "PaperscapeUserPrefs",
                               MAX_LEVEL_COMPLETED = "maxLevelCompleted",
                               SOUND_IS_TOGGLED = "soundIsToggled",
                               TOTAL_STARS_COLLECTED = "totalStarsCollected",
                               CURRENT_AMOUNT_OF_STARS = "currentAmountOfStars",
                               TOTAL_DEATHS = "totalDeaths";

    //used to draw to screen
    public static SpriteBatch batch;
    //used to draw objects for debug versions of game
    public static ShapeRenderer shapeRenderer;
    //sets the resolution of the game and allos sprite batch to draw
    public static OrthographicCamera camera;
    //stores the position of a touch to the screen
    public static Vector3 touch;
    //used to check if assets such as sounds have been loaded
    public static AssetManager assetManager;

//============================================================================================================================//
    public static void loadAssets() {

        //how long the app will wait between screen in milliseconds. Is ran when the game changes a screen
        waitTimeForScreens = 200;
        //how long the game will wait in milliseconds. Used so the same touch doesn't retstart the game when the pause button is pressed
        waitTimeForPause = 100;
        //how long the game will wait in milliseconds. Helps Android by not playing music as soon as it is created
        timeForMusicToLoad = 100;

        //when called and initialised is set to the resolution of the screen, allows sprite batch to draw to screen
        camera = new OrthographicCamera();
            //the resolution of the game, currently 1080p HD as it will be scaled down for smaller screens
            screenResolutionX = 1920; screenResolutionY = 1080;
        //holds the position of a touch made on the screen
        touch = new Vector3();
        //the spritebatch used by Level, Menu etc. to draw objects to screen
        batch = new SpriteBatch();
        //used to check position of enemy waypoints
        shapeRenderer = new ShapeRenderer();

        //initialising paperBackgroundTexture object as walls_image file in menu folder
        paperBackgroundTexture = new Texture(Gdx.files.internal("paper-background.png"));
        //used for scaling
        paperBackgroundTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        //initialising a sprite which will hold the paperBackgroundTexture
        paperBackgroundSprite = new Sprite(paperBackgroundTexture);

        //-------------------------------------------------------------------------
        //the size of each individual sprite (i.e. 128 = 128x128 sprite image)
        frameSize = 128;
        //the position of each of the frames within a sprite sheet
        //0, 0 is the top left of the sprite sheet
            framePosition01X = 0;                   framePosition01Y = 0;
            framePosition02X = frameSize;           framePosition02Y = 0;
            framePosition03X = (frameSize * 2);     framePosition03Y = 0;
            framePosition04X = (frameSize * 3);     framePosition04Y = 0;
            framePosition05X = 0;                   framePosition05Y = frameSize;
            framePosition06X = frameSize;           framePosition06Y = frameSize;
            framePosition07X = (frameSize * 2);     framePosition07Y = frameSize;
            framePosition08X = (frameSize * 3);     framePosition08Y = frameSize;
            framePosition09X = 0;                   framePosition09Y = (frameSize * 2);
            framePosition10X = frameSize;           framePosition10Y = (frameSize * 2);
            framePosition11X = (frameSize * 2);     framePosition11Y = (frameSize * 2);
            framePosition12X = (frameSize * 3);     framePosition12Y = (frameSize * 2);
            framePosition13X = 0;                   framePosition13Y = (frameSize * 3);
            framePosition14X = frameSize;           framePosition14Y = (frameSize * 3);
            framePosition15X = (frameSize * 2);     framePosition15Y = (frameSize * 3);
            framePosition16X = (frameSize * 3);     framePosition16Y = (frameSize * 3);

        //initialising the first sprite sheet from an image in the 'assets' folder
        spriteSheet1 = new Texture(Gdx.files.internal("sprite_sheet_1.png"));
            //declaring each sprite, (what texture are they from, X-position, Y-position, X-size, Y-size);
            playerSprite = new Sprite(spriteSheet1, framePosition01X, framePosition01Y, frameSize, frameSize);
            enemySprite = new Sprite (spriteSheet1, framePosition02X, framePosition02Y, frameSize, frameSize);
            textBackgroundSprite = new Sprite (spriteSheet1, framePosition03X, framePosition03Y, frameSize, frameSize);
            pauseButtonSprite = new Sprite(spriteSheet1, framePosition04X, framePosition04Y, frameSize, frameSize);
            leftArrowSprite = new Sprite(spriteSheet1, framePosition05X, framePosition05Y, frameSize, frameSize);
            upArrowSprite = new Sprite(spriteSheet1, framePosition06X, framePosition06Y, frameSize, frameSize);
            leftArrowSpritePressed = new Sprite(spriteSheet1, framePosition07X, framePosition07Y, frameSize, frameSize);
            upArrowSpritePressed = new Sprite(spriteSheet1, framePosition08X, framePosition08Y, frameSize, frameSize);
            sightVerticalDownSprite = new Sprite(spriteSheet1, framePosition09X, framePosition09Y, frameSize, frameSize);
            sightHorisontalRightSprite = new Sprite(spriteSheet1, framePosition10X, framePosition10Y, frameSize, frameSize);
            levelButtonSprite = new Sprite(spriteSheet1, framePosition11X, framePosition11Y, frameSize, frameSize);
            lockedLevelSprite = new Sprite(spriteSheet1, framePosition12X, framePosition12Y, frameSize, frameSize);
            yellowButtonSprite = new Sprite (spriteSheet1, framePosition13X, framePosition13Y, frameSize, frameSize);
            creditsButtonSprite = new Sprite(spriteSheet1, framePosition14X, framePosition14Y, frameSize, frameSize);
            restartButtonSprite = new Sprite(spriteSheet1, framePosition15X, framePosition15Y, frameSize, frameSize);
            levelSelectMenuSprite = new Sprite (spriteSheet1, framePosition16X, framePosition16Y, frameSize, frameSize);

            //initialising a sprite for an enemy sight pointing up from the sprite for a sight facing down
            sightVerticalUpSprite = new Sprite(spriteSheet1, framePosition09X, framePosition09Y, frameSize, frameSize);
                //the sprite needs to be flipped vertically (boolean X, boolean Y)
                sightVerticalUpSprite.flip(false, true);
            //initialising a sprite for an enemy sight pointing left from the sprite for a sight facing right
            sightHorisontalLeftSprite = new Sprite(spriteSheet1, framePosition10X, framePosition10Y, frameSize, frameSize);
                //the sprite needs to be flipped horizontally (boolean X, boolean Y)
                sightHorisontalLeftSprite.flip(true, false);

            //sprite for the right arrow of GUI controls
            rightArrowSprite = new Sprite(spriteSheet1, framePosition05X, framePosition05Y, frameSize, frameSize);
                //needs to be flipped horizontally (boolean X, boolean Y)
                rightArrowSprite.flip(true, false);
            //sprite for when the right arrow of GUI controls is pressed
            rightArrowSpritePressed = new Sprite(spriteSheet1, framePosition07X, framePosition07Y, frameSize, frameSize);
                //needs to be flipped horizontally (boolean X, boolean Y)
                rightArrowSpritePressed.flip(true, false);

            //sprite for the down arrow of GUI controls
            downArrowSprite = new Sprite(spriteSheet1, framePosition06X, framePosition06Y, frameSize, frameSize);
                //needs to be flipped vertically (boolean X, boolean Y)
                downArrowSprite.flip(false, true);
            //sprite for when the down arrow of GUI controls is pressed
            downArrowSpritePressed = new Sprite(spriteSheet1, framePosition08X, framePosition08Y, frameSize, frameSize);
                //needs to be flipped vertically (boolean X, boolean Y)
                downArrowSpritePressed.flip(false, true);

        //locating the second sprite sheet
        spriteSheet2 = new Texture(Gdx.files.internal("sprite_sheet_2.png"));
            //declaring each sprite, (what texture are they from, X-position, Y-position, X-size, Y-size);
            soundOnSprite = new Sprite(spriteSheet2, framePosition01X, framePosition01Y, frameSize, frameSize);
            soundOffSprite = new Sprite(spriteSheet2, framePosition02X, framePosition02Y, frameSize, frameSize);
            pauseBackgroundSprite = new Sprite(spriteSheet2, framePosition03X, framePosition03Y, frameSize, frameSize);
            exitButtonSprite = new Sprite(spriteSheet2, framePosition04X, framePosition04Y, frameSize, frameSize);
            doorSprite = new Sprite(spriteSheet2, framePosition05X, framePosition05Y, frameSize, frameSize);
            backButtonSprite = new Sprite(spriteSheet2, framePosition06X, framePosition06Y, frameSize, frameSize);
            doorButtonSprite = new Sprite(spriteSheet2, framePosition07X, framePosition07Y, frameSize, frameSize);
            doorButtonActiveSprite = new Sprite(spriteSheet2, framePosition08X, framePosition08Y, frameSize, frameSize);
            wallSprite1 = new Sprite(spriteSheet2, framePosition09X, framePosition09Y, frameSize, frameSize);
            wallSprite2 = new Sprite(spriteSheet2, framePosition10X, framePosition10Y, frameSize, frameSize);
            wallSprite3 = new Sprite(spriteSheet2, framePosition11X, framePosition11Y, frameSize, frameSize);
            wallSprite4 = new Sprite(spriteSheet2, framePosition12X, framePosition12Y, frameSize, frameSize);
            wallSprite5 = new Sprite(spriteSheet2, framePosition13X, framePosition13Y, frameSize, frameSize);

//-----------------------------------Screen-bounds-rectangles---------------------------------------
        //rectange shape for screen boundaries (X-position, Y-position, X-width, Y-height)
        screenBounds_left = new Rectangle(-10, 0, 10, 1080);
        screenBounds_right = new Rectangle(1915, 0, 10, 1080);
        screenBounds_top = new Rectangle(0, 1080, 1925, 10);
        screenBounds_bottom = new Rectangle(0, 290, 1925, 10);

//-------------------------------------Setting-up-fonts---------------------------------------------
        fontRobotoRegular = new BitmapFont(Gdx.files.internal("fonts/roboto_regular_bitmap.fnt"), false);
        fontRobotoRegular.setColor(Color.BLACK);
        fontRobotoRegular.getData().setScale(1f);

        fontRobotoThinBold = new BitmapFont(Gdx.files.internal("fonts/roboto_thin_bold_bitmap.fnt"), false);
        fontRobotoThinBold.setColor(Color.BLACK);
        fontRobotoThinBold.getData().setScale(1f);

//----------------------------------Strings-used-by-the-game----------------------------------------
        trackTitle = "<---";
        trackArtist = "Please";
        trackAlbum = "Select";
        license = "a";
        licenseLink = "Music";
        changesMade = "Track";

//---------------------------------Preferences-for-user-data----------------------------------------
        //create of load the preferences XML file
        prefs = Gdx.app.getPreferences(PAPERSCAPE_PREFERENCES);
        //if there is no value for completed level, then set it to 1
        if (!prefs.contains(MAX_LEVEL_COMPLETED)) {
            prefs.putInteger(MAX_LEVEL_COMPLETED, 1);
            //save the preferences file
            prefs.flush();
        }
        //if there is no value for volume being toggled, then set it to true
        if (!prefs.contains(SOUND_IS_TOGGLED)) {
            prefs.putBoolean(SOUND_IS_TOGGLED, true);
            //save the preferences file
            prefs.flush();
        }
        //if there is no value for the total amount of stars, set it to 0
        if (!prefs.contains(TOTAL_STARS_COLLECTED)) {
            prefs.putInteger(TOTAL_STARS_COLLECTED, 0);
            //save the preferences file
            prefs.flush();
        }
        //if there is no value for the current amount of stars, set it to 0
        if (!prefs.contains(CURRENT_AMOUNT_OF_STARS)) {
            prefs.putInteger(CURRENT_AMOUNT_OF_STARS, 0);
            //save the preferences file
            prefs.flush();
        }
        //if there is no value for the total amount of deaths, set it to 0
        if (!prefs.contains(TOTAL_DEATHS)) {
            prefs.putInteger(TOTAL_DEATHS, 0);
            //save the preferences file
            prefs.flush();
        }

//----------------------------Loading Music and Sounds to assetManager--------------------
        assetManager = new AssetManager();
        //store a sound object in the assetManager
        assetManager.load("sounds/star_pickup.mp3", Sound.class);
        //load all of the music files used by game
        //menu music
        assetManager.load("sounds/dj.mp3", Music.class);
        //level music
        assetManager.load("sounds/acid_jazz.ogg", Music.class);
        //level complete music
        assetManager.load("sounds/new_hamra.mp3", Music.class);
        //captured music
        assetManager.load("sounds/autoguano.mp3", Music.class);
        //tell the assetManager that all loading is done
        assetManager.finishLoading();

        //output the current max level completed to the console
        Gdx.app.log(MAX_LEVEL_COMPLETED, Integer.toString(getMaxLevelCompleted()));
//--------------------------------------------------------------------------------------------------
    }

//=============================================Sound Effects===================================================================
    public static void playSoundEffect(int soundToPlay) {

        //if the sound is toggled to be on in the preferences file
        if (prefs.getBoolean(SOUND_IS_TOGGLED, true)) {

            //run a certain case depending on the int passed by method
            switch(soundToPlay) {
//--------------------------------------------------------------------------------------------------
                //if the passed int is the same as the star pickup int
                case STAR_PICK_UP_SOUND_INT:

                //check if the sound effect has been loaded to memory, this is due to needing to wait 1 frame for sounds to work on Android
                if (assetManager.isLoaded("sounds/star_pickup.mp3")) {
                    //initialise the Sound object
                    star_pickup = assetManager.get("sounds/star_pickup.mp3", Sound.class);
                    //play the sound and keep track of its ID (long is a very long int variable and is unique to this sound effect)
                    long star_pickupID = star_pickup.play();
                    //make sure the sound only plays once (long id, Boolean isLooping)
                    GameAssets.star_pickup.setLooping(star_pickupID, false);

                } else {
                    //if the sound is not loaded to asset manager, write an error message to the console
                    Gdx.app.log("Star Pickup Sound", "File not loaded.");
                }

                break;
            }
//--------------------------------------------------------------------------------------------------
        }
//--------------------------------------------------------------------------------------------------
    }

//=============================================Play Music====================================================================
    public static void playMusic(int musicToPlay) {

        //if the sound is toggled to be on in the preferences file
        if (prefs.getBoolean(SOUND_IS_TOGGLED, true)) {

            //run a certain case depending on the int passed by method
            switch (musicToPlay) {
//--------------------------------------------------------------------------------------------------
                //if the passed int matches that of the main menu music
                case MENU_MUSIC_INT:

                    //check is the music file has been loaded by the asset manager
                    if (assetManager.isLoaded("sounds/dj.mp3")) {
                        //create the menu music from the file loaded in the asset manager
                        menuMusic = assetManager.get("sounds/dj.mp3", Music.class);

                        //program waits before playing the music, helps with sound not stopping on Android
                        try {
                            //how long to wait in milliseconds
                            Thread.sleep(timeForMusicToLoad);
                        }
                        //throw an exception if the app cannot be put to sleep
                        catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }

                        //play the music object
                        menuMusic.play();
                        //allow the track to loop continuously
                        menuMusic.setLooping(true);
                    } else {
                        //if the file was not loaded by the asset manager, write an error message to the console
                        Gdx.app.log("Menu Music", "File not loaded.");
                    }

                    break;
//--------------------------------------------------------------------------------------------------
                //if the int passed matches the int for the level music
                case LEVEL_MUSIC_INT:

                    //check if the file has been loaded by the asset manager
                    if (assetManager.isLoaded("sounds/acid_jazz.ogg")) {
                        //if it has loaded, create a music object from the file
                        levelMusic = assetManager.get("sounds/acid_jazz.ogg", Music.class);

                        //program waits before playing music, helps with sound not stopping on Android
                        try {
                            //how long to wait in milliseconds
                            Thread.sleep(timeForMusicToLoad);
                        }
                        //throw an exception if the app cannot be put to sleep
                        catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }

                        //play the music object
                        levelMusic.play();
                        //set it to continuously loop
                        levelMusic.setLooping(true);
                    } else {
                        //if the file could not be loaded by the asset manager, write an error message to the console
                        Gdx.app.log("Level Music", "File not loaded.");
                    }

                    break;
//--------------------------------------------------------------------------------------------------
                //if the passed int matches the one for the level complete music
                case LEVEL_COMPLETE_MUSIC_INT:

                    //check if the file has been loaded by the asset manager
                    if (assetManager.isLoaded("sounds/new_hamra.mp3")) {
                        //create the music object from the loaded file
                        levelCompleteMusic = assetManager.get("sounds/new_hamra.mp3", Music.class);

                        //program waits before player, helps with sound not stopping on Android
                        try {
                            //how long to wait in milliseconds
                            Thread.sleep(timeForMusicToLoad);
                        }
                        //throw an exception if the app cannot be put to sleep
                        catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }

                        //begin playiing the music object
                        levelCompleteMusic.play();
                        //set it to infinitely loop
                        levelCompleteMusic.setLooping(true);
                    } else {
                        //if the file could not be loaded by asset manager, write an error message to the console
                        Gdx.app.log("Level Complete Music", "File not loaded.");
                    }

                    break;
//--------------------------------------------------------------------------------------------------
                //if the passed int matches the captured music int
                case CAPTURED_MUSIC_INT:

                    //check if the file has been loaded by the asset manager
                    if (assetManager.isLoaded("sounds/autoguano.mp3")) {
                        //create the music object from the loaded file
                        capturedMusic = assetManager.get("sounds/autoguano.mp3", Music.class);

                        //program waits before player, helps with sound not stopping on Android
                        try {
                            //how long to wait in milliseconds
                            Thread.sleep(timeForMusicToLoad);
                        }
                        //throw an exception if the app cannot be put to sleep
                        catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }

                        //begin playing the music object
                        capturedMusic.play();
                        //set it to loop infinitely
                        capturedMusic.setLooping(true);
                    } else {
                        //if the music file could not be loaded by asset manager, display an error message in the console
                        Gdx.app.log("Captured Music", "File not loaded.");
                    }

                    break;
            }
//--------------------------------------------------------------------------------------------------
        } else {
            //if the boolean for sound toggled in the prefs file is false, display an error message in the console
            Gdx.app.log("Music", "Sound is currently disabled.");
        }
//--------------------------------------------------------------------------------------------------
    }

//===============================================Stop Music=====================================================================
    public static void stopMusic(int musicToStop) {

        if (prefs.getBoolean(SOUND_IS_TOGGLED, true)) {

            switch(musicToStop) {
//--------------------------------------------------------------------------------------------------
                //if the int passed matches the menu music int
                case MENU_MUSIC_INT:

                    //check is the music file has been loaded by the asset manager
                    if (assetManager.isLoaded("sounds/dj.mp3")) {
                        //create the menu music from the file loaded in the asset manager
                        menuMusic = assetManager.get("sounds/dj.mp3", Music.class);

                        menuMusic.stop();
                    }

                    break;
//--------------------------------------------------------------------------------------------------
                //if the int passed matches the level music int
                case LEVEL_MUSIC_INT:

                    //check if the file has been loaded by the asset manager
                    if (assetManager.isLoaded("sounds/acid_jazz.ogg")) {
                        //if it has loaded, create a music object from the file
                        levelMusic = assetManager.get("sounds/acid_jazz.ogg", Music.class);

                        levelMusic.stop();
                    }

                    break;
//--------------------------------------------------------------------------------------------------
                //if the int passed matches the level complete music int
                case LEVEL_COMPLETE_MUSIC_INT:

                    //check if the file has been loaded by the asset manager
                    if (assetManager.isLoaded("sounds/new_hamra.mp3")) {
                        //create the music object from the loaded file
                        levelCompleteMusic = assetManager.get("sounds/new_hamra.mp3", Music.class);

                        levelCompleteMusic.stop();
                    }

                    break;
//--------------------------------------------------------------------------------------------------
                //if the int passed matches the captured music int
                case CAPTURED_MUSIC_INT:

                        //check if the file has been loaded by the asset manager
                        if (assetManager.isLoaded("sounds/autoguano.mp3")) {
                            //create the music object from the loaded file
                            capturedMusic = assetManager.get("sounds/autoguano.mp3", Music.class);

                        capturedMusic.stop();
                    }

                    break;
            }
        }
//--------------------------------------------------------------------------------------------------
        else {
            //if the Boolean for sound being toggled in the prefs file is false, display an error message in the console
            Gdx.app.log("Music", "Sound is currently disabled.");
        }

//--------------------------------------------------------------------------------------------------
    }

//===============================================Animations====================================================================
    public static void starAnimation() {

        //the sprite sheet used to hold each from of the animation
        starAnimationSheet = new Texture(Gdx.files.internal("star_animation.png"));
        //temporary 2-dimensional array to hold images from texture sheet, (variable name, tile width, tile height);
        TextureRegion[][] tempArray = TextureRegion.split(starAnimationSheet, 128, 128);
        //how many different images are in the texture sheet
        starSheetFrames = new TextureRegion[16];

        //the positional value of an animation frame within the TextureRegion array
        int index = 0;
        //i = how many rows of animation frames are in the sprite sheet
        for(int i=0; i<4; i++) {
            //j = how many columns of animation frames are in the sprite sheet
            for(int j=0; j<4; j++) {
                //create each frame from the values stored in 'i' and 'j'
                starSheetFrames[index++] = tempArray[i][j];
            }

            //variable is new Animation, (closeSpeed of frames 0-1F, TextureRegion holding frames);
            starAnim = new Animation(0.07F, starSheetFrames);
        }
//--------------------------------------------------------------------------------------------------
    }

//============================================================================================================================//
    public static void flagAnimation() {

        flagAnimationSheet = new Texture(Gdx.files.internal("flag_animation.png"));
        //Temporary multidimensional array to hold images from texture sheet, (variable name, tile width, tile height);
        TextureRegion[][] tempArray = TextureRegion.split(flagAnimationSheet, 128, 128);
        //How many different images are in the texture sheet
        flagSheetFrames = new TextureRegion[16];

        //the positional value of an animation frame within the TextureRegion array
        int index = 0;
        //i = how many rows of animation frames are in the sprite sheet
        for(int i=0; i<4; i++) {
            //j = how many columns of animation frames are in the sprite sheet
            for(int j=0; j<4; j++) {
                //create each frame from the values stored in 'i' and 'j'
                flagSheetFrames[index++] = tempArray[i][j];
            }

            //variable is new Animation, (closeSpeed of frame 0-1F, TextureRegion holding frames);
            flagAnim = new Animation(0.07F, flagSheetFrames);
        }
//--------------------------------------------------------------------------------------------------
    }

//========================Dispose method to be called in classes that use GameAssets================================
    public static void dispose() {
        //used for memory management, objects have to manually disposed by calling their dispose methods inside of this method
//-----------------------------------------------------------------
        game.dispose();
        assetManager.dispose();
//-----------------------------------------------------------------
        paperBackgroundTexture.dispose();
        spriteSheet1.dispose();
        spriteSheet2.dispose();
//-----------------------------------------------------------------
        fontRobotoRegular.dispose();
        fontRobotoThinBold.dispose();
//-----------------------------------------------------------------
        starAnimationSheet.dispose();
        flagAnimationSheet.dispose();
//-----------------------------------------------------------------
        shapeRenderer.dispose();
        batch.dispose();
//-----------------------------------------------------------------
        star_pickup.dispose();
//-----------------------------------------------------------------
        menuMusic.dispose();
        levelMusic.dispose();
        levelCompleteMusic.dispose();
        capturedMusic.dispose();
//-----------------------------------------------------------------
    }

//====================================Getters and Setters=================================================================//
//----------------------------------Setters-for-Preferences-----------------------------------------
    public static void setMaxLevelCompleted(int i) {
        //store the passed value for maxLevelComlete key
        prefs.putInteger(MAX_LEVEL_COMPLETED, i);
        //save the preferences file
        prefs.flush();
    }
//-----------------------------------------------------------------
    public static void setToggleSoundState(Boolean toggleVolume) {
        //store the state of the passed Boolean for volumeIsToggled key
        prefs.putBoolean(SOUND_IS_TOGGLED, toggleVolume);
        //save the preferences file
        prefs.flush();
    }
//----------------------------------------------------------------
    public static void setTotalStarsCollected(int i) {
        //place the passed int in the prefs file for the total stars key
        prefs.putInteger(TOTAL_STARS_COLLECTED, i);
        //save the preferences file
        prefs.flush();
    }
//-----------------------------------------------------------------
    //this method can be used when a feature for the user to spend collected stars is implemented
    public static void setCurrentAmountOfStars(int i) {

        //checking if the total amount of stars is over 9999 and if so do not increase the total any more
        if (i >= 9999) {
            i = 9999;
        }

        //update the prefs file with the passed int for the current amount of stars key
        prefs.putInteger(CURRENT_AMOUNT_OF_STARS, i);
        //save the preferences file
        prefs.flush();
    }
//-----------------------------------------------------------------
public static void setTotalDeaths(int i) {
        //update the prefs file with the passed int for total amount of deaths
        prefs.putInteger(TOTAL_DEATHS, i);
        //save the preferences file
        prefs.flush();
    }

//-----------------------------Getters-for-Preferences------------------------------------------
    public static int getMaxLevelCompleted() {
        //look for an integer in the preferences file that matches the max level completed key
        return prefs.getInteger(MAX_LEVEL_COMPLETED);
    }
//-----------------------------------------------------------------
    public static Boolean getToggleSoundState() {
        //find the value of a Boolean in the preferences file that matches the toggle sound key
        return prefs.getBoolean(SOUND_IS_TOGGLED);
    }
//-----------------------------------------------------------------
public static int getTotalStarsCollected() {
        //find the int in the prefs file for total stars key
        return prefs.getInteger(TOTAL_STARS_COLLECTED);
    }
//-----------------------------------------------------------------
public static int getCurrentAmountOfStars() {
        //find the int in the prefs file for the current stars key
        return prefs.getInteger(CURRENT_AMOUNT_OF_STARS);
    }
//-----------------------------------------------------------------
    public static int getTotalDeaths() {
        //find the int in the prefs file for total deaths key
        return prefs.getInteger(TOTAL_DEATHS);
    }

//============================================================================================================================//
}
//============================================================================================================================//