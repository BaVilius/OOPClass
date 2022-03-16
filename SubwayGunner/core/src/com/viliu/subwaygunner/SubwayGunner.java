package com.viliu.subwaygunner;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.viliu.subwaygunner.Screens.PlayScreen;

public class SubwayGunner extends Game {
	public static final int V_WIDTH = 720;
	public static final int V_HEIGHT = 480;
	public static final float PPM = 100;
	public SpriteBatch batch;

	public static final short DEFAULT_BIT = 1;
	public static final short GUNNER_BIT = 2;
	public static final short COIN_BIT = 4;
	public static final short DESTROYED_BIT = 8;

	public static AssetManager manager;
	
	@Override
	public void create () {
		batch = new SpriteBatch();

		manager = new AssetManager();
		manager.load("audio/music/bensound-extremeaction.mp3", Music.class);
		manager.load("audio/sfx/mixkit-retro-game-notification-212.wav", Sound.class);
		manager.finishLoading();

		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	public void dispose(){
		super.dispose();
		manager.dispose();
		batch.dispose();
	}
}
