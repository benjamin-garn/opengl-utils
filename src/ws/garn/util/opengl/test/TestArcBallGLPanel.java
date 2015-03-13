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
package ws.garn.util.opengl.test;

import java.awt.Dimension;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.swing.JFrame;

import com.jogamp.opengl.util.FPSAnimator;

import ws.garn.util.opengl.ArcBallGLJPanel;

public class TestArcBallGLPanel {
	
	public static void main(String[] args) {
		
		JFrame frame = new JFrame("ArcBall Test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ArcBallGLJPanel panel = new ArcBallGLJPanel(){

			private static final long serialVersionUID = 1L;

			@Override
			protected void startup(GLAutoDrawable drawable) {
			}

			@Override
			protected void displayScene(GL2 gl) {
				gl.glTranslatef(0, 0, -5);
				gl.glScalef(scale, scale, scale);
				startArcBallRotation(gl);
				
				// Your Objects
				glut.glutSolidTeapot(1);
				
				endArcBallRotation(gl);
			}
			
		};
		panel.setPreferredSize(new Dimension(500,500));
		frame.add(panel);;
		frame.pack();
		frame.setVisible(true);
		new FPSAnimator(panel, 60).start();
	}
}
