package com.helloWorld.fly;

import java.util.Random;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;

import com.helloWorld.fly.resources.BackgroundSet;
import com.helloWorld.fly.resources.Explosion;
import com.helloWorld.fly.resources.Helicoptor;
import com.helloWorld.fly.resources.Hurdle;
import com.helloWorld.fly.resources.SoundBox;
import com.helloWorld.fly.resources.SoundButton;
import com.helloWorld.fly.resources.Textbook;

public class ResourceManager extends Object{
	
	private static ResourceManager INSTANCE;
	
	private AnimatedSprite mHelicoptorSprite;
	private AnimatedSprite mSoundButtonSprite;
	private Sprite mHurdleSprite;
	
	private Sprite mMenuBackgroundSprite;
	
	private Sound mExplosionSound;
	private Music mHelicoptorMusic;
	
	private Textbook mTextbook;
	private Hurdle mHurdle;
	
	public static ResourceManager getInstance(){
		if(INSTANCE == null){
			INSTANCE = new ResourceManager();
		}
		return INSTANCE;
	}
	
	public void loadResources(){
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		SoundFactory.setAssetBasePath("sfx/");
		MusicFactory.setAssetBasePath("sfx/");
		loadHelicoptor();
		loadHUD();
		loadSounds();
		loadTextbook();
		loadHurdle();
	}

	public void loadScenes(){
		SceneManager.getInstance().loadGameScene();
		SceneManager.getInstance().loadMenuScene();
	}
	
	public void loadHelicoptor(){
		mHelicoptorSprite = new Helicoptor().getHelicoptorSprite();
	}
	
	private void loadSounds(){
		SoundBox soundBox = new SoundBox();
		mExplosionSound = soundBox.getExplosionSound();
		mHelicoptorMusic = soundBox.getHelicoptorMusic();
	}
	
	private void loadTextbook(){
		mTextbook = new Textbook();
	}

	private void loadHUD(){
		mSoundButtonSprite = new SoundButton().getSoundButton();
		Game.getHUD().attachChild(mSoundButtonSprite);
		Game.getHUD().registerTouchArea(mSoundButtonSprite);
	}
	
	private void loadHurdle(){
		mHurdle = new Hurdle();
	}
	
	
	public AnimatedSprite getHelicoptorSprite(){
		return mHelicoptorSprite;
	}
	
	public Sprite getHurdleSprite(int pX,int pY){
		Random r = new Random();
		mHurdleSprite = mHurdle.getHurdleSprite(pX,pY,r.nextInt(4)+6);
		return mHurdleSprite;
	}
	
	public AnimatedSprite getExplosionSprite(int x,int y){
		return new Explosion(x,y).getExplosionSprite();
	}
	
	public Sprite getMenuBackground(){
		mMenuBackgroundSprite = new BackgroundSet().getMenuBackground();
		return mMenuBackgroundSprite;
	}
	
	public void playExplosionSound(){
		mExplosionSound.play();;
	}
	
	public void startHelicoptorSound(){
		mHelicoptorMusic.play();;
	}
	
	public void stopHelicoptorSound(){
		mHelicoptorMusic.pause();
	}
	
	public Text getHighScoreText(){
		return mTextbook.getHighScoreText();
	}
	
	public Text getCurrentScoreText(){
		return mTextbook.getCurrentScoreText();
	}
}
