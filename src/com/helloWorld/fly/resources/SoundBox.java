package com.helloWorld.fly.resources;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;

import com.helloWorld.fly.Game;

public class SoundBox {

	private Sound mExplosionSound;
	private Music mHelicoptorMusic;

	public SoundBox() {
		try {
			mExplosionSound = SoundFactory.createSoundFromAsset(Game
					.getContext().getSoundManager(), Game.getContext(),
					"explosion.mp3");
			mHelicoptorMusic = MusicFactory.createMusicFromAsset(Game
					.getContext().getMusicManager(), Game.getContext(),
					"helicoptor.mp3");
			mHelicoptorMusic.setLooping(true);
		} catch (IOException e) {

			e.printStackTrace();
		}

	}
	
	public Sound getExplosionSound(){
		return mExplosionSound;
	}
	
	public Music getHelicoptorMusic(){
		return mHelicoptorMusic;
	}

}
