package perthroEngine;

import java.util.LinkedList;

public abstract class Scene {

    protected Camera m_camera;
    private boolean m_isRunning = false;

    protected LinkedList<GameObject> m_gameObjects;
    protected LinkedList<GameObject> m_erasableGameObjects;
    protected LinkedList<GameObject> m_addableGameObjects;
    private boolean addQueue = false;
    private boolean removeQueue = false;

    public Scene(){
        m_gameObjects = new LinkedList<>();
        m_erasableGameObjects = new LinkedList<>();
        m_addableGameObjects = new LinkedList<>();
    }

    public void init(){

    }

    public void start(){
        for (GameObject go: m_gameObjects) {
            go.start();
        }
    }

    public void addGameObjectToScene(GameObject go){
        m_gameObjects.add(go);
        if (!m_isRunning){
            go.start();
        }
    }

    public abstract void update(float dt);
}
