package com.helloWorld.fly.resources;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import com.helloWorld.fly.Game;

public class Explosion {
	
	private static BuildableBitmapTextureAtlas mBuildableBitmapTextureAtlas;
	private static TiledTextureRegion mExplosionTiledTextureRegion;
	private static AnimatedSprite mExplosionSprite;
	
	public Explosion(final int pXPosition,final int pYPosition){
		mBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(Game
				.getContext().getTextureManager(), 512, 256);
		mExplosionTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(mBuildableBitmapTextureAtlas,
						Game.getContext(), "explode.png", 8, 1);
		try {
			mBuildableBitmapTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 0, 1));
			mBuildableBitmapTextureAtlas.load();
		} catch (TextureAtlasBuilderException e) {
			e.printStackTrace();
		}
		mExplosionSprite = new AnimatedSprite(pXPosition, pYPosition,
				mExplosionTiledTextureRegion, Game.getContext()
						.getVertexBufferObjectManager());
		mExplosionSprite.animate(100,false);
	}
	
	public AnimatedSprite getExplosionSprite(){
		return mExplosionSprite;
	}
}
