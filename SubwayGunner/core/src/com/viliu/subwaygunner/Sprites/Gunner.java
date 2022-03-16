package com.viliu.subwaygunner.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.viliu.subwaygunner.Screens.PlayScreen;
import com.viliu.subwaygunner.SubwayGunner;

public class Gunner extends Sprite {

    public static BodyDef bdef;

    public enum State {FALLING, JUMPING, STANDING, RUNNING};
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    private TextureRegion gunnerStand;
    private Animation gunnerRun;
    private Animation gunnerJump;
    private float stateTimer;
    private boolean runningRight;


    public Gunner(World world, PlayScreen screen){
        super(screen.getAtlas().findRegion("SpriteSheet_player"));
        this.world = world;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;
        bdef = new BodyDef();


        Array<TextureRegion> frames = new Array<TextureRegion>();

        for(int i = 1; i < 5; i++) {
            frames.add(new TextureRegion(getTexture(), 326+(i*46), 106, 32,32));
        }
        gunnerRun = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 1; i <= 2; i++) {
            frames.add(new TextureRegion(getTexture(), 599 + i*45, 106, 32,32));
        }
        gunnerJump = new Animation(0.1f, frames);


        defineGunner();
        gunnerStand = new TextureRegion(getTexture(), 374 + 6*45,106,32,32);
        setBounds(0,0,32/SubwayGunner.PPM,32/SubwayGunner.PPM);
        setRegion(gunnerStand);

    }
    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth()/2 + 0.04f, b2body.getPosition().y - getHeight()/2);
        setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(float dt){
        currentState = getState();

        TextureRegion region;
        switch (currentState){
            case JUMPING:
                region = (TextureRegion) gunnerJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = (TextureRegion) gunnerRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
            case STANDING:
            default:
                region = gunnerStand;
                break;
        }

        if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight = false;
        }
        else if((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }

    public State getState(){
        if(b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
            return State.JUMPING;
        else if(b2body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if(b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else
            return State.STANDING;
    }
    public void defineGunner(){

        bdef.position.set(640/ SubwayGunner.PPM, 720/ SubwayGunner.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(12/ SubwayGunner.PPM);

        fdef.filter.categoryBits = SubwayGunner.GUNNER_BIT;
        fdef.filter.maskBits = SubwayGunner.DEFAULT_BIT | SubwayGunner.COIN_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef);

        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData("head");
    }
    public static void startPos(){
        bdef.position.set(640/ SubwayGunner.PPM, 720/ SubwayGunner.PPM);
    }


}
