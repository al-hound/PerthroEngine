package perthroEngine;

public abstract class Scene {

    protected Camera m_camera;

    public Scene(){

    }

    public void init(){

    }

    public abstract void update(float dt);
}
