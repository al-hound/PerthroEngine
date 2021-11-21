package perthroEngine;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseListener {
	
	//singleton
	private static MouseListener instance;

	//mouse scroll
	private double m_scrollX, m_scrollY;
	//mouse position
	private double m_posX, m_posY, m_lastX, m_lastY;
	//press
	private boolean m_mouseButtonPressed[] = new boolean[3];
	private boolean m_isDragging;
		
	private MouseListener() {
		m_scrollX = 0.0;
		m_scrollY = 0.0;
		m_posX = 0.0;
		m_posY = 0.0;
		m_lastX = 0.0;
		m_lastY = 0.0;
		m_isDragging = false;
	}
	
	public static MouseListener get() {
		if(MouseListener.instance == null) {
			instance = new MouseListener();
		}
		return instance;
	}
	
	public static void mousePosCallback(long window, double xPos, double yPos) {
		get().m_lastX = get().m_posX;
		get().m_lastY = get().m_posY;
		get().m_posX = xPos;
		get().m_posY = yPos;
		get().m_isDragging = get().m_mouseButtonPressed[0] || get().m_mouseButtonPressed[1] || get().m_mouseButtonPressed[2];
	}
	
	public static void mouseButtonCallback(long window, int button, int action, int mods) {
		if(action == GLFW_PRESS){
			if(button < get().m_mouseButtonPressed.length){
				get().m_mouseButtonPressed[button]= true;
			}
		}else if(action == GLFW_RELEASE){
			if(button < get().m_mouseButtonPressed.length){
				get().m_mouseButtonPressed[button] = false;
				get().m_isDragging = false;
			}
		}
	}

	public static void mouseScrollCallback(long window, double xOffset, double yOffset){
		get().m_scrollX = xOffset;
		get().m_scrollY = yOffset;
	}

	public static void endFrame(){
		get().m_scrollX = 0;
		get().m_scrollY = 0;
		get().m_lastX = get().m_posX;
		get().m_lastY = get().m_posY;
	}

	public static float getScrollX() {
		return (float)get().m_scrollX;
	}

	public static float getScrollY() {
		return (float)get().m_scrollY;
	}

	public static float getPosX() {
		return (float)get().m_posX;
	}

	public static float getPosY() {
		return (float)get().m_posY;
	}

	public static float getLastX() {
		return (float)get().m_lastX;
	}

	public static float getLastY() {
		return (float)get().m_lastY;
	}

	public static float getDistanceX() {
		return (float)(get().m_lastX - get().m_posX);
	}

	public static float getDistanceY() {
		return (float)(get().m_lastY - get().m_posY);
	}

	public static boolean isDragging(){
		return get().m_isDragging;
	}

	public static boolean mouseButtonDown(int button){
		if(button < get().m_mouseButtonPressed.length){
			return get().m_mouseButtonPressed[button];
		}else{
			return false;
		}
	}
}
