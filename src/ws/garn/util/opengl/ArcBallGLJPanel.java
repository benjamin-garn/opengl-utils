/**
   Ben's OpenGL Utilities
   Copyright (C) 2015 Benjamin Garn

   This program is free software; you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation; either version 3 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program; if not, write to the Free Software Foundation,
   Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA

 */
package ws.garn.util.opengl;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLJPanel;
import javax.media.opengl.glu.GLU;

import ws.garn.util.opengl.ArcBall;
import ws.garn.util.opengl.Matrix4f;
import ws.garn.util.opengl.Quaternion;
import ws.garn.util.opengl.Vector2D;

import com.jogamp.opengl.util.gl2.GLUT;

public abstract class ArcBallGLJPanel extends GLJPanel implements GLEventListener, MouseListener, MouseMotionListener, MouseWheelListener {

	private static final long serialVersionUID = 1L;
	
	protected GLU glu;  
	protected GLUT glut;

	private ArcBall arcBall;
    private Matrix4f lastRot;
    private Matrix4f thisRot;
    private final Object matrixLock = new Object();
    private float[] matrix = new float[16];
    protected Quaternion q = Quaternion.ZERO;
    
    protected float scale = 1;
 	
	public ArcBallGLJPanel() {

		arcBall = new ArcBall(getWidth(), getHeight());
		
		addGLEventListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);

	}
	
	@Override
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		glu = new GLU();
		glut = new GLUT();
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // set background (clear) color
		gl.glClearDepth(1.0f); // set clear depth value to farthest
		gl.glEnable(GL2.GL_DEPTH_TEST); // enables depth testing
		gl.glDepthFunc(GL2.GL_LEQUAL); // the type of depth test to do
		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST); // best perspective correction
		gl.glShadeModel(GL2.GL_SMOOTH); // blends colors nicely, and smoothes out lighting
		
		gl.glEnable(GL2.GL_LIGHTING);
		gl.glEnable(GL2.GL_LIGHT0);
		gl.glEnable(GL2.GL_COLOR_MATERIAL); 
		
		lastRot = Matrix4f.IDENTITY;
		thisRot = Matrix4f.IDENTITY;
		matrix = thisRot.toArray();
		
		startup(drawable);
		
	}
	
	/**
	 * OpenGL Init Code
	 */
	protected abstract void startup(GLAutoDrawable drawable);

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glViewport(0, 0, width, height);
		
		
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(45f, (float)width / (float)height, 0.1f, 100.0f);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
		arcBall.setBounds((float) width, (float) height);
	}
	
	@Override
	public void dispose(GLAutoDrawable drawable) {
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();

		synchronized(matrixLock) {
            matrix = thisRot.toArray();
        }
		
        displayScene(gl);
		
		gl.glFlush();
	}
	
	/**
	 * Rotate following Objects according to ArcBall
	 * @param gl
	 */
	protected void startArcBallRotation(GL2 gl) {
		gl.glPushMatrix();
        gl.glMultMatrixf(matrix, 0);
	}

	/**
	 * End Rotation of Arcball
	 * @param gl
	 */
	protected void endArcBallRotation(GL2 gl) {
		gl.glPopMatrix();
	}
	
	/**
	 * Main OpenGL Display Method
	 * @param gl
	 */
	protected abstract void displayScene(GL2 gl);

	@Override
	public void mouseClicked(MouseEvent arg0) {
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.isShiftDown()) {
			
		} else {
	        synchronized(matrixLock) {
	            lastRot = thisRot;
	        }
	        arcBall.click(new Vector2D(e.getX(), e.getY()));   
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (e.isShiftDown()) {
			
		} else {
	        synchronized(matrixLock) {
	        	q =  arcBall.drag( new Vector2D(e.getX(), e.getY()));
	        	thisRot = Matrix4f.fromQuaternion(q);
	        	thisRot = lastRot.mul(thisRot);
	        	
	        }
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		scale = scale - scale * (e.getWheelRotation() * 0.05f);
		if (scale < 0.1) scale = 0.1f;
		if (scale > 2) scale = 2;
		
	}

}
