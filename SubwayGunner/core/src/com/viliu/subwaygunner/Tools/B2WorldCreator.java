package com.viliu.subwaygunner.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.viliu.subwaygunner.Sprites.Coin;
import com.viliu.subwaygunner.SubwayGunner;

public class B2WorldCreator {
    public B2WorldCreator (World world, TiledMap map){
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;
        Fixture fixture;

        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth()/2)/ SubwayGunner.PPM, (rect.getY() + rect.getHeight()/2)/ SubwayGunner.PPM);

            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2/ SubwayGunner.PPM,rect.getHeight()/2/ SubwayGunner.PPM);
            fdef.shape = shape;

            body.createFixture(fdef);

        }

        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Coin(world, map, rect);

        }
    }

}
