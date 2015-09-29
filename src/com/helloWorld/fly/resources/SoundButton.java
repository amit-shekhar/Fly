package com.helloWorld.fly.resources;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import com.helloWorld.fly.Game;
import com.helloWorld.fly.GameManager;
import com.helloWorld.fly.ResourceManager;

public class SoundButton {

	private static BuildableBitmapTextureAtlas mBuildableBitmapTextureAtlas;
	private static TiledTextureRegion mSoundTiledTextureRegion;
	private static AnimatedSprite mSoundSprite;

	public SoundButton() {
		mBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(Game
				.getContext().getTextureManager(), 512, 256);
		mSoundTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(mBuildableBitmapTextureAtlas,
						Game.getContext(), "sound.png", 2, 1);
		try {
			mBuildableBitmapTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 0, 1));
			mBuildableBitmapTextureAtlas.load();
		} catch (TextureAtlasBuilderException e) {
			e.printStackTrace();
		}

		mSoundSprite = new AnimatedSprite(Game.CAMERA_WIDTH - 160, 10,
				mSoundTiledTextureRegion, Game.getContext()
						.getVertexBufferObjectManager()) {

			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
					if (GameManager.getInstance().isSoundEnabled() == true) {
						mSoundSprite.setCurrentTileIndex(1);
						GameManager.getInstance().setIsSoundEnabled(false);
						if (GameManager.getInstance().isGameOver() == false)
							ResourceManager.getInstance().stopHelicoptorSound();
					} else {
						mSoundSprite.setCurrentTileIndex(0);
						GameManager.getInstance().setIsSoundEnabled(true);
						if (GameManager.getInstance().isGameOver() == false)
							ResourceManager.getInstance().startHelicoptorSound();
					}
				}
				return true;
			}
		};
		if (GameManager.getInstance().isSoundEnabled()) {
			mSoundSprite.setCurrentTileIndex(0);
		} else {
			mSoundSprite.setCurrentTileIndex(1);
		}
	}
	
	public AnimatedSprite getSoundButton(){
		return mSoundSprite;
	}
	
	

}
