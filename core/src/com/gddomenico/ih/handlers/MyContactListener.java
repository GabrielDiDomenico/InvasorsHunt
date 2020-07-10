package com.gddomenico.ih.handlers;


import com.badlogic.gdx.physics.box2d.*;

public class MyContactListener implements ContactListener {

    private boolean playerOnContact;
    private boolean rightContact;
    private boolean leftContact;
    public Fixture fa;
    public Fixture fb;

    public void beginContact(Contact c) {
        fa = c.getFixtureA();
        fb = c.getFixtureB();

        if(fa.getUserData() != null && fa.getUserData().equals("Foot")){
            playerOnContact = true;
        }
        if(fb.getUserData() != null && fb.getUserData().equals("Foot")){
            playerOnContact = true;
        }
        if(fa.getUserData() != null && fa.getUserData().equals("Enemy")){
            playerOnContact = true;
        }
        if(fb.getUserData() != null && fb.getUserData().equals("Enemy")){
            playerOnContact = true;
        }
    }

    public void endContact(Contact c) {
        fa = c.getFixtureA();
        fb = c.getFixtureB();

        if(fa.getUserData() != null && fa.getUserData().equals("Foot")){
            playerOnContact = false;
        }
        if(fb.getUserData() != null && fb.getUserData().equals("Foot")){
            playerOnContact = false;
        }
    }

    public boolean isPlayerOnContact() { return playerOnContact; }

    public void preSolve(Contact c, Manifold m) {
        if(m.getLocalNormal().epsilonEquals(1,-0)){
            leftContact = true;
            rightContact = false;
        }
        if(m.getLocalNormal().epsilonEquals(-1,-0)){
            rightContact = true;
            leftContact = false;
        }

    }
    public boolean isRightContact() { return rightContact; }
    public boolean isLeftContact() { return leftContact; }


    public void postSolve(Contact c, ContactImpulse ci) {}

}
