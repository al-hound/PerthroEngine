package perthroEngine;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Camera {

    //matrix
    private Matrix4f m_projectionMatrix, m_viewMatrix;
    //coordinates
    private Vector2f m_position;

    public Camera(Vector2f position){
        m_position = position;
        m_projectionMatrix = new Matrix4f();
        m_viewMatrix = new Matrix4f();
        adjustProjection();
    }

    public void adjustProjection(){
        m_projectionMatrix.identity();
        m_projectionMatrix.ortho(0.0f, 32.0f * 40.0f, 0.0f, 32.0f * 21.0f, 0.0f, 100.0f);
    }

    public  Matrix4f getViewMatrix(){
        Vector3f cameraFront = new Vector3f(0.0f, 0.0f, -1.0f);
        Vector3f cameraUp = new Vector3f(0.0f, 1.0f, 0.0f);
        m_viewMatrix.identity();
        m_viewMatrix = m_viewMatrix.lookAt(new Vector3f(m_position.x, m_position.y, 20.0f), cameraFront.add(m_position.x, m_position.y, 0.0f), cameraUp);
        return m_viewMatrix;
    }

    public Matrix4f getProjectionMatrix() {
        return m_projectionMatrix;
    }
}
