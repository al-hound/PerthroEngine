package perthroEngine;

import components.SpriteRenderer;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;
import renderer.Shader;
import renderer.Texture;
import util.Time;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class LevelEditorScene extends Scene{

    private int vertexID, fragmentID, shaderProgram;

    private float[] vertexArray ={
            //position               //color                       //uv coordinates
            50.5f, -50.5f, 0.0f,       1.0f, 0.0f, 0.0f, 1.0f,     1, 1, //bottom right 0
            -50.5f, 50.5f, 0.0f,       0.0f, 1.0f, 0.0f, 1.0f,     0, 0, //top left     1
            50.5f, 50.5f, 0.0f,        0.0f, 0.0f, 1.0f, 1.0f,     1, 0, //top right    2
            -50.5f, -50.5f, 0.0f,      1.0f, 1.0f, 0.0f, 1.0f,     0, 1 //bottom left  3
    };

    //Important : Must be in counter-clockwise order
    private int[] elementArray={
            /*
                x      x

                x      x
             */
            2, 1, 0, //Top right triangle
            0, 1, 3  //bottom left triangle
    };

    private int vaoID, vboID, eboID;

    private Shader m_defaultShader;

    private Texture m_testTexture;

    public LevelEditorScene(){

    }

    @Override
    public void init() {
        this.m_camera = new Camera(new Vector2f(-200f, -300f));

        GameObject testobj = new GameObject("Test");
        testobj.addComponent(new SpriteRenderer());
        addGameObjectToScene(testobj);

        m_defaultShader = new Shader("assets/shaders/default.glsl");
        m_defaultShader.compile();
        m_testTexture = new Texture("assets/images/testImage.png");

        //Generate VAO, VBO and EBO buffer objects, and sent to GPU
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        //create a float buffer of vertices
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
        vertexBuffer.put(vertexArray).flip();

        //create VBO upload
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        // Create the indices and upload
        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
        elementBuffer.put(elementArray).flip();

        eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

        // Add the vertex attribute pointers
        int positionsSize = 3;
        int colorSize = 4;
        int uvSize = 2;
        int vertexSizeBytes = (positionsSize + colorSize + uvSize) * Float.BYTES;
        glVertexAttribPointer(0, positionsSize, GL_FLOAT, false, vertexSizeBytes, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, positionsSize * Float.BYTES);
        glEnableVertexAttribArray(1);

        glVertexAttribPointer(2, uvSize, GL_FLOAT, false, vertexSizeBytes, (positionsSize + colorSize) * Float.BYTES);
        glEnableVertexAttribArray(2);
    }

    @Override
    public void update(float dt) {
        // Bind shader program
        m_defaultShader.use();

        //upload texture to shader
        m_defaultShader.uploadTexture("TEX_SAMPLER", 0);
        glActiveTexture(GL_TEXTURE);
        m_testTexture.bind();

        m_defaultShader.uploadMat4f("uProjection", m_camera.getProjectionMatrix());
        m_defaultShader.uploadMat4f("uView", m_camera.getViewMatrix());
        m_defaultShader.uploadFloat("uTime", Time.getTime());

        // Bind the VAO that we're using
        glBindVertexArray(vaoID);

        // Enable the vertex attribute pointers
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 0);

        // Unbind everything
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        glBindVertexArray(0);

        m_defaultShader.detach();
        for (GameObject go: m_gameObjects) {
            go.update(dt);
        }
    }


}
