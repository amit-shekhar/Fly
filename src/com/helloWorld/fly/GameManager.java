package com.helloWorld.fly;

import android.content.Context;
import android.content.SharedPreferences;

public class GameManager extends Object{
	
	private static final String PREFS_NAME = "fly";
	private static final String HIGH_SCORE = "highScore";
	private static final String SOUND_ENABLED = "soundEnabled";
	
	private static GameManager INSTANCE;
	
	private SharedPreferences mSettings;
	private SharedPreferences.Editor mEditor;
	
	private int mHighScore;
	private int mCurrentScore;
	private boolean mIsGameOver;
	private boolean mIsSoundEnabled;
	
	private GameManager(){
		if(mSettings == null){
			mSettings = Game.getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		}
		mEditor = mSettings.edit();
		mHighScore = mSettings.getInt(HIGH_SCORE, 0);
		mIsSoundEnabled = mSettings.getBoolean(SOUND_ENABLED, true);
		mCurrentScore = 0;
		

	}
	
	public void init(){
		
	}
	
	public static GameManager getInstance(){
		if(INSTANCE == null){
			INSTANCE = new GameManager();
		}
		return INSTANCE;
	}
	
	public int getHighScore(){
		return mHighScore;
	}
	
	public int getCurrentScore(){
		return mCurrentScore;
	}
	
	public boolean isGameOver(){
		return mIsGameOver;
	}
	
	public boolean isSoundEnabled(){
		return mIsSoundEnabled;
	}
	
	public void setCurrentScore(int score){
		mCurrentScore = score;
	}
	
	public void setIsSoundEnabled(boolean value){
		mIsSoundEnabled = value;
		mEditor.putBoolean(SOUND_ENABLED, mIsSoundEnabled);
		mEditor.commit();
	}
	
	public void setIsGameOver(boolean value){
		mIsGameOver = value;
		if(value == true){
			if(mCurrentScore > mHighScore){
				mHighScore = mCurrentScore;
				mEditor.putInt(HIGH_SCORE, mHighScore);
				mEditor.commit();
			}
		}
	}
	
	public void reset(){
		mIsGameOver = false;
		mCurrentScore = 0;
		mHighScore = mSettings.getInt(HIGH_SCORE, 0);
		mIsSoundEnabled = mSettings.getBoolean(SOUND_ENABLED, true);
	}
}
