package perthroEngine;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class KeyListener {

    //singleton
    private static KeyListener instance;

    private boolean m_keyPressed[] = new boolean[350];

    private KeyListener(){

    }

    public static KeyListener get() {
        if(KeyListener.instance == null){
            instance = new KeyListener();
        }
        return instance;
    }

    public static void keyCallback(long window, int key, int scanCode, int action, int mods){
        if(action == GLFW_PRESS){
            get().m_keyPressed[key] = true;
        }else if(action == GLFW_RELEASE){
            get().m_keyPressed[key] = false;
        }
    }

    public static boolean isKeyPressed(int keyCode){
        return get().m_keyPressed[keyCode];
    }
}
