package com.helloWorld.fly.resources;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;

import android.opengl.GLES20;

import com.helloWorld.fly.Game;

public class BackgroundSet {

	private BuildableBitmapTextureAtlas mBuildableBitmapTextureAtlas;
	private ITextureRegion mMenuBackgroundRegion;
	private Sprite mMenuBackgroundSprite;

	public BackgroundSet() {

		mBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(Game
				.getContext().getTextureManager(), 2048, 1024);
		
	}

	public Sprite getMenuBackground() {
		mMenuBackgroundRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mBuildableBitmapTextureAtlas,
						Game.getContext(), "menuback.png");
		try {
			mBuildableBitmapTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 0, 1));
			mBuildableBitmapTextureAtlas.load();
		} catch (TextureAtlasBuilderException e) {
			e.printStackTrace();
		}
		mMenuBackgroundSprite = new Sprite(0, 0, mMenuBackgroundRegion, Game
				.getContext().getVertexBufferObjectManager());
		mMenuBackgroundSprite.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		mMenuBackgroundSprite.setAlpha(0.2f);

		return mMenuBackgroundSprite;
	}

}
