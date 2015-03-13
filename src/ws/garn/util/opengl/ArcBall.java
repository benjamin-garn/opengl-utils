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

public class ArcBall {

	final float EPSILON = (float)1.0e-5;
	
	float adjustWidth;
	float adjustHeight;
	
	Vector3D stVec; // Click Vector
	Vector3D enVec; // Drag Vector

	public ArcBall(float newWidth, float newHeight) {
		stVec = new Vector3D(0, 0, 0);
		enVec = new Vector3D(0, 0, 0);
		setBounds(newWidth, newHeight);
	}
	
	public void setBounds(float newWidth, float newHeight) {
		assert((newWidth > 1.0f) && (newHeight > 1.0f));
		adjustWidth = 1.0f / ((newWidth - 1.0f) * 0.5f);
		adjustHeight = 1.0f / ((newHeight - 1.0f) * 0.5f);
	}
	
	public Vector3D mapToSphere(Vector2D p) {
		Vector2D tempPoint = new Vector2D((p.x * adjustWidth)  - 1.0f, 1.0f - (p.y * adjustHeight));
		float length = tempPoint.lengthSquared();
		if (length > 1.0f) {
			float norm =  1.0f / (float)Math.sqrt(length);
			return new Vector3D(tempPoint.x * norm, tempPoint.y * norm, 0);
		} else {
			return new Vector3D(tempPoint.x, tempPoint.y, (float)Math.sqrt(1.0f - length));
		}
	}
	
	public void click(Vector2D p) {
		stVec = mapToSphere(p);
	}
	
	public Quaternion drag(Vector2D p) {
		enVec = mapToSphere(p);
		
		Vector3D perp = stVec.crossProduct3D(enVec);
		if (perp.lengthSquared() > EPSILON) {
			return new Quaternion(stVec.dotProduct(enVec), perp.x, perp.y, perp.z);
		} else {
			return new Quaternion(0, 0, 0, 0);
		}
		
	}
	

}
