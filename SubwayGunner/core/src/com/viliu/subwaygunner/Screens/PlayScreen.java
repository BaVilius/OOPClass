/// Vilius Bakutis 5 grupe


package com.viliu.subwaygunner.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.viliu.subwaygunner.Scenes.Hud;
import com.viliu.subwaygunner.Sprites.Gunner;
import com.viliu.subwaygunner.Sprites.Coin;
import com.viliu.subwaygunner.SubwayGunner;
import com.viliu.subwaygunner.Tools.B2WorldCreator;
import com.viliu.subwaygunner.Tools.WorldContactListener;
import com.viliu.subwaygunner.Sprites.InteractiveTileObject;


public class PlayScreen implements Screen {

    private SubwayGunner game;
    private TextureAtlas atlas;

    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Hud hud;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
    private Gunner player;

    private Music music;
    private B2WorldCreator collBox;

    private String[] maps = {"testing.tmx","secondtest.tmx", "last.tmx"};
    public int currentLvl = 0;
    public boolean isEdit = false;




    public PlayScreen(SubwayGunner game){
        atlas = new TextureAtlas("Gunner.pack");

        this.game = game;
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(SubwayGunner.V_WIDTH / SubwayGunner.PPM , SubwayGunner.V_HEIGHT/SubwayGunner.PPM, gameCam);
        hud = new Hud(game.batch);
        mapLoader = new TmxMapLoader();
        map = mapLoader.load(maps[0]);
        renderer = new OrthogonalTiledMapRenderer(map, 1/ SubwayGunner.PPM);
        gameCam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2, 0);

        world = new World(new Vector2(0, -10), true);

        collBox = new B2WorldCreator(world, map);
        player = new Gunner(world, this);

        world.setContactListener(new WorldContactListener());

        music = SubwayGunner.manager.get("audio/music/bensound-extremeaction.mp3", Music.class);
        music.setLooping(true);
        music.play();
    }
    @Override
    public void show() {

    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

    public void handleInput(float dt){
        if(!isEdit){
            if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
                player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
            }
            if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2){
                player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
            }
            if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2){
                player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.E)){
                isEdit = true;
            }
        }
        else if(isEdit){
            if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
                gameCam.position.x+=0.1f;
            }
            if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
                gameCam.position.x-=0.1f;
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.E)){
                isEdit = false;
            }
            if(Gdx.input.isTouched()){
                getEditCell().setTile(null);
            }
        }


    }
    public void update(float dt){
        handleInput(dt);

        world.step(1/60f, 6,2);

        player.update(dt);
        if(!isEdit){
            gameCam.position.x = player.b2body.getPosition().x;
        }


        gameCam.update();

        renderer.setView(gameCam);
    }

    @Override
    public void render(float delta) {
        update(delta);

        if(Hud.coneCount == 5){
            currentLvl++;
            nextLevel(currentLvl);

        }

        if(isEdit){
            ///getEditCell().setTile(null);
        }

        Gdx.gl.glClearColor(1, 0, 0, 0.5f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
        if(!isEdit){
            player.draw(game.batch);
        }

        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        if(!isEdit){
            hud.stage.draw();
        }


    }

    public TiledMapTileLayer.Cell getEditCell(){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(2);
        return layer.getCell((int)(Gdx.input.getX() * SubwayGunner.PPM / 16), (int)(Gdx.input.getY() * SubwayGunner.PPM / 16));
    }


    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    public void nextLevel(int currentLvl){
        map = new TmxMapLoader().load(maps[currentLvl]);
        world.dispose();
        world = new World(new Vector2(0, -10), true);
        renderer.setMap(map);
        Hud.nullScore(Hud.coneCount);
        player = new Gunner(world, this);
        world.setContactListener(new WorldContactListener());
        new B2WorldCreator(world, map);

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        hud.dispose();
    }
}
