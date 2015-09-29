package com.helloWorld.fly;

import java.util.Random;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.color.Color;

import com.helloWorld.fly.resources.Textbook;

public class SceneManager extends Object implements IOnSceneTouchListener{
	private static SceneManager INSTANCE;
	private Scene mGameScene, mMenuScene;
	private Scene mCurrentScene = null;
	private Random r = new Random();
	private boolean mTouch = false;
	private Textbook textbook = new Textbook();

	private SceneManager() {
		r = new Random();
	}
	
	public void init(){
	
	}

	public static SceneManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new SceneManager();
		}
		return INSTANCE;
	}

	public void loadGameScene() {
		mGameScene = new Scene();
		mGameScene.setBackground(new Background(Color.WHITE));
		mGameScene.attachChild(ResourceManager.getInstance()
				.getHelicoptorSprite());
		if(GameManager.getInstance().isSoundEnabled())
			ResourceManager.getInstance().startHelicoptorSound();
		mGameScene.registerUpdateHandler(new TimerHandler(1.0f,
				new ITimerCallback() {
					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						mGameScene.attachChild(ResourceManager.getInstance()
								.getHurdleSprite(1280, r.nextInt(700) - 50));
						pTimerHandler.reset();
					}
				}));
		mGameScene.setOnSceneTouchListener(this);
	}

	public void loadMenuScene() {
		mMenuScene = new Scene();
		mMenuScene.setBackgroundEnabled(false);
		mMenuScene.attachChild(ResourceManager.getInstance()
				.getMenuBackground());
		mMenuScene.attachChild(textbook.getHighScoreText());
		mMenuScene.attachChild(textbook.getContinueText());
		mMenuScene.registerTouchArea(textbook.getContinueText());
		mMenuScene.setOnAreaTouchListener(new IOnAreaTouchListener(){

			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					ITouchArea pTouchArea, float pTouchAreaLocalX,
					float pTouchAreaLocalY) {
				INSTANCE.resetGameScene();
				return false;
			}
			
		});
	}

	public boolean getTouchStatus() {
		return mTouch;
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {

		if (pScene == mGameScene) {
			switch (pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_UP:
				mTouch = false;
				break;
			case TouchEvent.ACTION_DOWN:
				mTouch = true;
				break;
			}
		}else if (pScene == mMenuScene){
			
		}
		return false;
	}
	
	
	public Scene getGameScene(){
		mCurrentScene = mGameScene;
		return mGameScene;
	}
	
	public Scene getMenuScene(){
		mMenuScene.attachChild(textbook.getCurrentScoreText());
		return mMenuScene;
	}
	
	public Scene getCurrentScene(){
		return mCurrentScene;
	}
	
	public void loadExplosion(final int x,final int y){
		ResourceManager.getInstance().getHelicoptorSprite().stopAnimation(3);
		ResourceManager.getInstance().stopHelicoptorSound();
		mCurrentScene.attachChild(ResourceManager.getInstance().getExplosionSprite(x, y));
		if(GameManager.getInstance().isSoundEnabled())
			ResourceManager.getInstance().playExplosionSound();
		if(GameManager.getInstance().isGameOver() == false)
			mCurrentScene.setChildScene(SceneManager.getInstance().getMenuScene());
		GameManager.getInstance().setIsGameOver(true);
	}
	
	public void resetGameScene(){
		Game.getContext().runOnUpdateThread(new Runnable(){
			@Override
			public void run() {
				mCurrentScene.clearChildScene();
				mCurrentScene.detachChildren();
				GameManager.getInstance().reset();
				ResourceManager.getInstance().loadHelicoptor();
				if(GameManager.getInstance().isSoundEnabled())
					ResourceManager.getInstance().startHelicoptorSound();
				mCurrentScene.attachChild(ResourceManager.getInstance().getHelicoptorSprite());		
				INSTANCE.loadMenuScene();
			}
		});
		
	}
}
