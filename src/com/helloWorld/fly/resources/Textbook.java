package com.helloWorld.fly.resources;

import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;

import android.graphics.Typeface;

import com.helloWorld.fly.Game;
import com.helloWorld.fly.GameManager;

public class Textbook {

	private Font mFont;
	private Text mHighScoreText, mCurrentScoreText, mContinueText;

	public Textbook() {
		mFont = FontFactory.create(Game.getContext().getFontManager(), Game
				.getContext().getTextureManager(), 512, 256, Typeface.create(
				Typeface.DEFAULT, Typeface.BOLD), 64f, true);
		mFont.prepareLetters("ABCDEFGHIJKLMNOPQRSTUVWXYZ: 1234567890"
				.toCharArray());
		mFont.load();
	}

	public Text getHighScoreText() {
		mHighScoreText = new Text(Game.CAMERA_WIDTH*0.3f,
				Game.CAMERA_HEIGHT / 2 - 100, mFont, "HIGH SCORE :"
						+ GameManager.getInstance().getHighScore(), Game
						.getContext().getVertexBufferObjectManager());
		
		return mHighScoreText;
	}

	public Text getCurrentScoreText() {
		mCurrentScoreText = new Text(Game.CAMERA_WIDTH * 0.3f,
				Game.CAMERA_HEIGHT / 2, mFont, "YOUR SCORE :"
						+ GameManager.getInstance().getCurrentScore(), Game
						.getContext().getVertexBufferObjectManager());
		
		return mCurrentScoreText;
	}

	public Text getContinueText() {
		mContinueText = new Text(Game.CAMERA_WIDTH * 0.2f ,
				(Game.CAMERA_HEIGHT * 3) / 4, mFont,
				"TOUCH HERE TO CONTINUE", Game.getContext()
						.getVertexBufferObjectManager());
		
		return mContinueText;
	}
}


















