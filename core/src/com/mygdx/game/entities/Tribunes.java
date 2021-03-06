package com.mygdx.game.entities;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.G;
import com.mygdx.game.model.GameWorld;
import com.mygdx.game.model.PhysicsObject;
import com.mygdx.game.utils.FixtureDefBuilder;

/**
 * @author Lukasz Zmudziak, @lukz_dev on 2016-01-29.
 */
public class Tribunes extends Entity implements PhysicsObject {

    // Physics
    private Body body;
    private boolean flagForDelete = false;
    private TextureAtlas.AtlasRegion region;

    public Tribunes (float x, float y, float width, float height, GameWorld gameWorld, TextureAtlas.AtlasRegion region) {
        super(x, y, width, height);
        FixtureDefBuilder builder = gameWorld.getBox2DWorld().getFixtureDefBuilder();
        this.body = gameWorld.getBox2DWorld().getBodyBuilder()
                .fixture(builder
                        .boxShape(bounds.width / 2, bounds.height / 2)
                        .density(0)
                        .build())
                .angularDamping(1f)
           // center of the screen
                .position(x + bounds.width / 2, y + bounds.height / 2)
                .type(BodyDef.BodyType.StaticBody)
                .userData(this)
                .build();
        this.region = region;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(region, bounds.x, bounds.y, bounds.width, bounds.height);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void handleBeginContact(PhysicsObject psycho2, GameWorld world) {
//        if(psycho2 instanceof Sacrifice) {
//            G.assets.get(G.A.SOUND_BOUNCE, Sound.class).play(0.3f, 1 + 1 + MathUtils.random(-0.05f, 0.05f), 0);
//        }
    }

    @Override
    public Body getBody() {
        return body;
    }

    @Override
    public boolean getFlagForDelete() {
        return flagForDelete;
    }

    @Override
    public void setFlagForDelete(boolean flag) {
        flagForDelete = flag;
    }
}
