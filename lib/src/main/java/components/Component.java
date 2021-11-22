package components;

import perthroEngine.GameObject;

public abstract class Component {

    private GameObject m_gameObject = null;

    public GameObject getEntity() {
        return m_gameObject;
    }

    public void setEntity(GameObject gameObject) {
        m_gameObject = gameObject;
    }

    public void start(){

    }

    public abstract void update(double dt);

}
