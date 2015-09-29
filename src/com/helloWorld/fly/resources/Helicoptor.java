package com.helloWorld.fly.resources;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import com.helloWorld.fly.Game;
import com.helloWorld.fly.GameManager;
import com.helloWorld.fly.SceneManager;

public class Helicoptor implements IUpdateHandler {

	private static BuildableBitmapTextureAtlas mBuildableBitmapTextureAtlas;
	private static TiledTextureRegion mHeliTiledTextureRegion;
	private static AnimatedSprite mHeliSprite;
	private static int mXPosition, mYPosition;
	private static int mScore;

	public Helicoptor() {
		mScore = 0;
		mBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(Game
				.getContext().getTextureManager(), 512, 256);
		mHeliTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(mBuildableBitmapTextureAtlas,
						Game.getContext(), "heli.png", 2, 2);
		try {
			mBuildableBitmapTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 0, 1));
			mBuildableBitmapTextureAtlas.load();
		} catch (TextureAtlasBuilderException e) {
			e.printStackTrace();
		}

		mXPosition = Game.CAMERA_WIDTH / 4;
		mYPosition = Game.CAMERA_HEIGHT / 2;
		mHeliSprite = new AnimatedSprite(mXPosition, mYPosition,
				mHeliTiledTextureRegion, Game.getContext()
						.getVertexBufferObjectManager());
		mHeliSprite.animate(new long[] { 100, 100 }, 1, 2, true);
		mHeliSprite.registerUpdateHandler(this);
	}

	public AnimatedSprite getHelicoptorSprite() {
		return mHeliSprite;
	}

	@Override
	public void onUpdate(float pSecondsElapsed) {
		
		if(GameManager.getInstance().isGameOver() == false){
			mScore += 5;
			GameManager.getInstance().setCurrentScore(mScore);
		}
		
		boolean touch = SceneManager.getInstance().getTouchStatus();
		if (touch == true && GameManager.getInstance().isGameOver() == false) {
			mYPosition -= 5;
			if(mYPosition < 0){
				SceneManager.getInstance().loadExplosion(mXPosition,mYPosition);	
			}
			mHeliSprite.setPosition(mXPosition, mYPosition);
		} else if (mYPosition < Game.CAMERA_HEIGHT){
			mYPosition += 5;
			if(mYPosition > Game.CAMERA_HEIGHT - mHeliSprite.getHeight() && mYPosition < Game.CAMERA_HEIGHT){
				SceneManager.getInstance().loadExplosion(mXPosition,mYPosition);	
			}
			mHeliSprite.setPosition(mXPosition, mYPosition);
			if(mYPosition > Game.CAMERA_HEIGHT){
				Game.getContext().runOnUpdateThread(new Runnable(){
					@Override
					public void run() {
						mHeliSprite.detachSelf();
					}
				});
			}	
		}
	}

	@Override
	public void reset() {
		mYPosition = Game.CAMERA_HEIGHT/2;
	}
}
