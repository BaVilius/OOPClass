package com.viliu.subwaygunner.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.viliu.subwaygunner.SubwayGunner;

public class Hud implements Disposable {

    public Stage stage;
    private Viewport viewport;


    public static Integer coneCount = 0;

    static Label coneLabel;
    Label levelLabel;
    Label gunnerLabel;

    public Hud(SpriteBatch sb){
        viewport = new FitViewport(SubwayGunner.V_WIDTH, SubwayGunner.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);
        BitmapFont font = new BitmapFont();
        font.getData().setScale(1.7f, 1.7f);

        levelLabel = new Label("1-1", new Label.LabelStyle(font, Color.WHITE));
        gunnerLabel = new Label("Subway Gunner", new Label.LabelStyle(font, Color.WHITE));
        coneLabel = new Label(String.format("Cones: %03d", coneCount), new Label.LabelStyle(font, Color.WHITE));

        table.add(gunnerLabel).expandX().padTop(10);
        table.add(levelLabel).expandX().padTop(10);
        table.add(coneLabel).expandX().padTop(10);

        stage.addActor(table);

    }
    public void update (float dt) {

    }

    public static void addScore(int value){
        coneCount += value;
        coneLabel.setText(String.format("Cones: %03d", coneCount));
    }

    public static void nullScore(int value){
        coneCount = 0;
        coneLabel.setText(String.format("Cones: %03d", coneCount));
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
