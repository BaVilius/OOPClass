package com.viliu.subwaygunner.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.viliu.subwaygunner.SubwayGunner;

public abstract class   InteractiveTileObject {
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;
    protected Fixture fixture;


    public InteractiveTileObject(World world, TiledMap map, Rectangle bounds){
        this.world = world;
        this.map = map;
        this.bounds  = bounds;

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX() + bounds.getWidth()/2)/ SubwayGunner.PPM, (bounds.getY() + bounds.getHeight()/2)/ SubwayGunner.PPM);

        body = world.createBody(bdef);
        shape.setAsBox(bounds.getWidth()/2/ SubwayGunner.PPM,bounds.getHeight()/2/ SubwayGunner.PPM);
        fdef.shape = shape;

        fixture = body.createFixture(fdef);


    }

    public abstract void onHit();
    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }

    public TiledMapTileLayer.Cell getCell(){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(2);
        return layer.getCell((int)(body.getPosition().x * SubwayGunner.PPM / 16), (int)(body.getPosition().y * SubwayGunner.PPM / 16));
    }
    public TiledMapTileLayer.Cell getEditCell(){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(2);
        return layer.getCell((int)(Gdx.input.getX() * SubwayGunner.PPM / 16), (int)(Gdx.input.getY() * SubwayGunner.PPM / 16));
    }
}
