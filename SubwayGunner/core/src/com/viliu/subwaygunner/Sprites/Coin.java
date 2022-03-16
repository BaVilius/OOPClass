package com.viliu.subwaygunner.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.viliu.subwaygunner.Scenes.Hud;
import com.viliu.subwaygunner.SubwayGunner;

public class Coin extends InteractiveTileObject{
    public Coin(World world, TiledMap map, Rectangle bounds){
        super(world, map, bounds);
        fixture.setUserData(this);
        setCategoryFilter(SubwayGunner.COIN_BIT);

    }

    @Override
    public void onHit() {
        setCategoryFilter(SubwayGunner.DESTROYED_BIT);
        getCell().setTile(null);
        Hud.addScore(1);
        SubwayGunner.manager.get("audio/sfx/mixkit-retro-game-notification-212.wav", Sound.class).play();
    }


}
