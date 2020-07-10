package com.gddomenico.ih.states;

import static com.gddomenico.ih.handlers.B2DVars.PPM;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.gddomenico.ih.handlers.B2DVars;
import com.gddomenico.ih.handlers.GameStateManager;
import com.gddomenico.ih.handlers.MyContactListener;
import com.gddomenico.ih.handlers.MyInput;
import com.gddomenico.ih.invasorsHunt;
import java.util.Random;

public class Play extends GameState {

    private World world;
    private Box2DDebugRenderer b2dr;

    private OrthographicCamera b2dCam;

    public boolean rightArm = false;

    private Body playerBody;
    private Body enemyBody;
    private MyContactListener cl;

    public Play(GameStateManager gsm) {
        super(gsm);

        world = new World(new Vector2(0, 0), true);
        cl = new MyContactListener();
        world.setContactListener(cl);
        b2dr = new Box2DDebugRenderer();

        createPlayer();

        createEnemies();

        //set up b2d cam
        b2dCam = new OrthographicCamera();
        b2dCam.setToOrtho(false, invasorsHunt.V_WIDTH / PPM, invasorsHunt.V_HEIGHT / PPM);
    }

    public void handleInput() {
        if(MyInput.isDown(MyInput.BUTTON_W)) {
            playerBody.applyForceToCenter(0, 2, true);
        }
        if(MyInput.isUp(MyInput.BUTTON_W)) {
            playerBody.setLinearVelocity(0,0);
        }
        if(MyInput.isDown(MyInput.BUTTON_S)){
            playerBody.applyForceToCenter(0, -2, true);
        }
        if(MyInput.isUp(MyInput.BUTTON_S)) {
            playerBody.setLinearVelocity(0,0);
        }
        if(MyInput.isDown(MyInput.BUTTON_A)){
            rightArm = true;
            playerBody.applyForceToCenter(-2, 0, true);
        }
        if(MyInput.isUp(MyInput.BUTTON_A)) {
            playerBody.setLinearVelocity(0,0);
        }
        if(MyInput.isDown(MyInput.BUTTON_D)){
            rightArm = false;
            playerBody.applyForceToCenter(2, 0, true);
        }
        if(MyInput.isUp(MyInput.BUTTON_D)) {
            playerBody.setLinearVelocity(0,0);
        }
        if(MyInput.isPressed(MyInput.BUTTON_K)){
            if(cl.isPlayerOnContact()){
                if(cl.isLeftContact() && rightArm == false) {
                    System.out.println("Punch Left!!");
                    Array<Body> bodies = new Array<Body>();
                    world.getBodies(bodies);
                    System.out.println(bodies);
                }else if(cl.isRightContact() && rightArm == true){
                    System.out.println("Punch Right!!");
                }
            }else{
                System.out.println("Missed!!");
            }

        }
        if(MyInput.isDown(MyInput.BUTTON_SPACE)){
            System.out.println("SPACE");
        }
    }

    public void update(float dt) {

        handleInput();

        world.step(dt, 6, 2);

    }
    public void render() {
        //clear screen
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        b2dr.render(world, b2dCam.combined);
    }
    public void dispose() {}

    public void createPlayer(){

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();

        //player
        bdef.position.set(160 / PPM,120 / PPM);
        bdef.type =  BodyDef.BodyType.DynamicBody;
        playerBody = world.createBody(bdef);

        shape.setAsBox(5 / PPM,5 / PPM);
        fdef.shape = shape;
        //to change the category
        fdef.isSensor = true;
        playerBody.createFixture(fdef).setUserData("Player");

        // create a foot sensor
        shape.setAsBox(5/PPM, 2/PPM, new Vector2(0, -3 / PPM), 0);
        fdef.shape = shape;
        fdef.isSensor = false;
        fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
        fdef.filter.maskBits = B2DVars.BIT_ENEMY;
        playerBody.createFixture(fdef).setUserData("Foot");

    }
    //Just a func to get an random int
    public int getRand(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    public void createEnemies(){
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();

        for (int i=0;i<=5;i++){
            String id = Integer.toString(i);
            //enemy
            bdef.position.set((100+getRand(getRand(-50,50),200)) / PPM,(120+getRand(getRand(-50,50),100)) / PPM);
            bdef.type =  BodyDef.BodyType.KinematicBody;
            enemyBody = world.createBody(bdef);
            enemyBody.setGravityScale(0f);

            shape.setAsBox(5 / PPM,5 / PPM);
            fdef.shape = shape;
            //to change the category
            fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
            fdef.filter.maskBits = B2DVars.BIT_ENEMY;
            enemyBody.createFixture(fdef).setUserData("Enemy");

            // create a foot sensor
            shape.setAsBox(5/PPM, 2/PPM, new Vector2(0, -2/ PPM), 0);
            fdef.shape = shape;
            fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
            fdef.filter.maskBits = B2DVars.BIT_PLAYER;
            enemyBody.createFixture(fdef).setUserData("Foot_Enemy");
        }
    }
}
