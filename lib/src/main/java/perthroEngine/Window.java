package perthroEngine;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;
import util.Time;

import java.nio.*;
import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {

    //colors
    private float m_r, m_g, m_b, m_a;
    //window size
    private int m_width;
    private int m_height;
    //game title
    private String m_title;

    //singleton
    private static Window window = null;
    //windows id
    private long glfwWindow;

    private static Scene currentScene = null;

    private Window(){
        this.setWidth(1920);
        this.setHeight(1080);
        this.setTitle("Game");
        m_r = 1f;
        m_g = 1f;
        m_b = 1f;
        m_a = 1f;
    }

    public static Window get() {
        if (Window.window == null){
            Window.window = new Window();
        }
        return window;
    }

    private void init() {
    	 // Setup an error callback
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW.");
        }

        // Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        // Create the window
        glfwWindow = glfwCreateWindow(m_width, m_height, m_title, NULL, NULL);
        if (glfwWindow == NULL) {
            throw new IllegalStateException("Failed to create the GLFW window.");
        }

        //setup callbacks
        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);

        // Make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(glfwWindow);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        Window.changeScene(0);
    }

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
    }

    private void loop() {
    	float beginTime = Time.getTime();
        float endTime;
        float dt = 0.0f;
        //gameloop
    	 while (!glfwWindowShouldClose(glfwWindow)) {
             // Poll events
             glfwPollEvents();

             glClearColor(m_r, m_g, m_b, m_a);
             glClear(GL_COLOR_BUFFER_BIT);

             if(KeyListener.isKeyPressed(GLFW_KEY_SPACE)){
                 System.out.println("Space pressed");
             }

             if (dt >= 0){
                 currentScene.update(dt);
             }

             glfwSwapBuffers(glfwWindow);

             endTime = Time.getTime();
             dt = endTime - beginTime;
             beginTime = endTime;
         }
    }

    public static void changeScene(int newScene){
        switch (newScene){
            case 0:
                currentScene = new LevelEditorScene();
                currentScene.init();
                currentScene.start();
                break;
            case 1:
                currentScene = new LevelScene();
                currentScene.init();
                currentScene.start();
                break;
            default:
                assert false : "Unknown scene '"+newScene+"'";
                break;
        }
    }

    public int getWidth() {
        return m_width;
    }

    public void setWidth(int width) {
        this.m_width = width;
    }

    public int getHeight() {
        return m_height;
    }

    public void setHeight(int height) {
        this.m_height = height;
    }

    public String getTitle() {
        return m_title;
    }

    public void setTitle(String title) {
        this.m_title = title;
    }

    public void changeBackgroundColor(float red, float green, float blue, float alpha){
        m_r = red;
        m_g = green;
        m_b = blue;
        m_a = alpha;
    }

    public void changeBackgroundColor(float red, float green, float blue){
        m_r = red;
        m_g = green;
        m_b = blue;
    }
}
