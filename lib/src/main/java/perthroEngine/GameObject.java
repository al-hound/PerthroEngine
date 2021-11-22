package perthroEngine;

import components.Component;

import java.util.ArrayList;

public class GameObject {

    private String m_name;

    public GameObject(String name){
        m_name = name;
    }

    private ArrayList<Component> m_Components = new ArrayList<>();

    /*
     * method that gets called every frame
     */
    public void start() {
        for (Component component : m_Components) {
            component.start();
        }
    }

    /*
     * method that gets called every frame
     */
    public void update(double dt) {
        for (Component component : m_Components) {
            component.update(dt);
        }
    }

    /*
     * adds a component to the entity
     */
    public void addComponent(Component component) {
        m_Components.add(component);
        component.setEntity(this);
    }

    /*
     * remove a component from the entity
     */
    public void removeComponent(Component component) {
        m_Components.remove(component);
    }

    /*
     * check if the entity contains a component from the specified class
     */
    public boolean hasComponent(Class<? extends Component> componentClass) {
        for (Component Ecomponent : m_Components) {
            if(Ecomponent.getClass().equals(componentClass)) {
                return true;
            }
        }
        return false;
    }

    /*
     * returns a component from the specified class
     */
    public Component getComponent(Class<? extends Component> componentClass) {
        for (Component Ecomponent : m_Components) {
            if(Ecomponent.getClass().equals(componentClass)) {
                return Ecomponent;
            }
        }
        return null;
    }
}
