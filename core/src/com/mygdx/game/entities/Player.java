package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.model.Box2DWorld;
import com.mygdx.game.model.GameWorld;
import com.mygdx.game.model.PhysicsObject;

/**
 * @author Lukasz Zmudziak, @lukz_dev on 2016-01-29.
 */
public class Player extends Entity implements PhysicsObject {

    // Config
    private final float SPEED = 8;
    private final Color color;

    // Controls
    private Vector2 direction = new Vector2();

    // Physics
    private Body body;
    private boolean flagForDelete = false;
    private Vector2 velocity = new Vector2();

    // Temp
    private Vector2 tempVec2 = new Vector2();

    public Player(float x, float y, float radius, GameWorld gameWorld, Color color) {
        super(x, y, radius * 2, radius * 2);
        this.color = color;

        this.body = gameWorld.getBox2DWorld().getBodyBuilder()
                .fixture(gameWorld.getBox2DWorld().getFixtureDefBuilder()
                        .circleShape(getBounds().getWidth() / 2)
                        .density(1f)
                        .friction(0.2f)
                        .restitution(0.5f)
//                                .maskBits(Box2DWorld.WALKER_MASK)
//                        .categoryBits(Box2DWorld.CATEGORY.ENEMY)
                        .build())
//                .fixedRotation()
                .angularDamping(3f)
                .linearDamping(10f)
                .position(x, y)
                .type(BodyDef.BodyType.DynamicBody)
                .userData(this)
                .build();
    }

    @Override
    public void draw(SpriteBatch batch) {

    }

    @Override public void drawDebug (ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(color);
        shapeRenderer.circle(position.x, position.y, bounds.width/2, 16);
    }

    @Override
    public void update(float delta) {
        position.set(body.getPosition());
        rotation = body.getAngle() * MathUtils.radDeg;

        // Transform direction into velocity
        if(direction.x != 0 || direction.y != 0) {
            velocity.set(direction).limit(1).scl(SPEED);

            tempVec2.set(body.getLinearVelocity()).lerp(velocity, 0.08f * 60 * delta);

            body.setTransform(position.x, position.y, tempVec2.angle() * MathUtils.degRad);
            body.setLinearVelocity(tempVec2.x, tempVec2.y);
        }

        // Direction not provided but velocity is
//        if(direction.isZero() && !velocity.isZero()) {
//            velocity.clamp(-1, 1).scl(SPEED);
//
//            tempVec2.set(body.getLinearVelocity()).lerp(velocity, 0.08f * 60 * delta);
//
//            body.setTransform(position.x, position.y, tempVec2.angle() * MathUtils.degRad);
//            body.setLinearVelocity(tempVec2.x, tempVec2.y);
//        }

//        if(!velocity.isZero()) {
//
//        }
    }

    @Override
    public void dispose() {

    }

    @Override
    public void handleBeginContact(PhysicsObject psycho2, GameWorld world) {

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

    public Vector2 getDirection() {
        return direction;
    }
}
