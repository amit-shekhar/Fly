package com.helloWorld.fly.resources;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;

import com.helloWorld.fly.Game;
import com.helloWorld.fly.ResourceManager;
import com.helloWorld.fly.SceneManager;

public class Hurdle {

	private static BuildableBitmapTextureAtlas mBuildableBitmapTextureAtlas;
	private static ITextureRegion mHurdleTextureRegion;

	public Hurdle() {
		mBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(Game
				.getContext().getTextureManager(), 32, 256);
		mHurdleTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mBuildableBitmapTextureAtlas,
						Game.getContext(), "hurdle.png");
		try {
			mBuildableBitmapTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 0, 1));
			mBuildableBitmapTextureAtlas.load();
		} catch (TextureAtlasBuilderException e) {
			e.printStackTrace();
		}

		

	}
	
	public Sprite getHurdleSprite(final int pXPosition, final int pYPosition,final int pSpeed){
		final Sprite mHurdleSprite = new Sprite(pXPosition, pYPosition,
				mHurdleTextureRegion, Game.getContext()
						.getVertexBufferObjectManager());
		final AnimatedSprite mHeliSprite = ResourceManager.getInstance().getHelicoptorSprite();
		mHurdleSprite.registerEntityModifier(new MoveModifier(pSpeed, pXPosition, 0,
				pYPosition, pYPosition) {
			
			
			@Override
					protected void onManagedUpdate(float pSecondsElapsed,
							IEntity pItem) {
						super.onManagedUpdate(pSecondsElapsed, pItem);
						if(mHurdleSprite.collidesWith(mHeliSprite)){
							SceneManager.getInstance().loadExplosion((int)mHeliSprite.getX(), (int)mHeliSprite.getY());
						}
			}

			@Override
			protected void onModifierFinished(IEntity pItem) {
				Game.getContext().runOnUpdateThread(new Runnable() {

					@Override
					public void run() {
						SceneManager.getInstance().getCurrentScene().detachChild(mHurdleSprite);
					}
				});
				super.onModifierFinished(pItem);
			}
		});
		return mHurdleSprite;
	}
}
