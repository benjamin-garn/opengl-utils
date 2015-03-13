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

import java.util.Locale;

public class Matrix4f {
	
	public final static Matrix4f ZERO = new Matrix4f (
			0, 0, 0, 0, 
			0, 0, 0, 0, 
			0, 0, 0, 0, 
			0, 0, 0, 0 
	);

	public final static Matrix4f IDENTITY = new Matrix4f (
			1, 0, 0, 0, 
			0, 1, 0, 0, 
			0, 0, 1, 0, 
			0, 0, 0, 1 
	);

	final float m00, m01, m02, m03; 
	final float m10, m11, m12, m13; 
	final float m20, m21, m22, m23; 
	final float m30, m31, m32, m33; 
	
	public float[] toArray() {
		return new float[] {
				m00, m01, m02, m03, 
				m10, m11, m12, m13, 
				m20, m21, m22, m23, 
				m30, m31, m32, m33, 
		};
	}
		
//	public Matrix4f(float[] matrix) {
//		assert(matrix.length == 16);
//		m00 = matrix[0];  m01 = matrix[1];  m02 = matrix[2];  m03 = matrix[3];
//		m10 = matrix[4];  m11 = matrix[5];  m12 = matrix[6];  m13 = matrix[7];
//		m20 = matrix[8];  m21 = matrix[9];  m22 = matrix[10]; m23 = matrix[11];
//		m30 = matrix[12]; m31 = matrix[13]; m32 = matrix[14]; m33 = matrix[15];
//	}

	public Matrix4f(float m00, float m01, float m02, float m03, float m10,
			float m11, float m12, float m13, float m20, float m21, float m22,
			float m23, float m30, float m31, float m32, float m33) {
		this.m00 = m00; this.m01 = m01; this.m02 = m02; this.m03 = m03;
		this.m10 = m10; this.m11 = m11; this.m12 = m12; this.m13 = m13;
		this.m20 = m20; this.m21 = m21; this.m22 = m22; this.m23 = m23;
		this.m30 = m30; this.m31 = m31; this.m32 = m32; this.m33 = m33;
	}

	public static Matrix4f createTransposed(float m00, float m01, float m02, float m03, float m10,
			float m11, float m12, float m13, float m20, float m21, float m22,
			float m23, float m30, float m31, float m32, float m33) {
		return new Matrix4f(
				m00, m10, m20, m30, 
				m01, m11, m21, m31, 
				m02, m12, m22, m32, 
				m03, m13, m23, m33
		);
	}

	/**
	 * Multiply by Skalar
	 * @param s
	 * @return
	 */
	public Matrix4f mul(float s) {
		return new Matrix4f( 
				m00*s, m01*s, m02*s, m03*s, 
				m10*s, m11*s, m12*s, m13*s, 
				m20*s, m21*s, m22*s, m23*s, 
				m30*s, m31*s, m32*s, m33*s 		
		);
	}
	
	/**
	 * Multiply by another Matrix4f
	 * @param m
	 * @return
	 */
	public Matrix4f mul(Matrix4f m) {
		float[] m1 = this.toArray();
		float[] m2 = m.toArray();
		return new Matrix4f(
				dotProduct(m1, 0, m2, 0), dotProduct(m1, 0, m2, 1), dotProduct(m1, 0, m2, 2), dotProduct(m1, 0, m2, 3), 
				dotProduct(m1, 1, m2, 0), dotProduct(m1, 1, m2, 1), dotProduct(m1, 1, m2, 2), dotProduct(m1, 1, m2, 3), 
				dotProduct(m1, 2, m2, 0), dotProduct(m1, 2, m2, 1), dotProduct(m1, 2, m2, 2), dotProduct(m1, 2, m2, 3), 
				dotProduct(m1, 3, m2, 0), dotProduct(m1, 3, m2, 1), dotProduct(m1, 3, m2, 2), dotProduct(m1, 3, m2, 3)
		);
	}
	
	private static float dotProduct(float[] m1, int row, float[] m2, int col) {
		return m1[(row*4)+0] * m2[(0*4)+col] + 
				m1[(row*4)+1] * m2[(1*4)+col] + 
				m1[(row*4)+2] * m2[(2*4)+col] + 
				m1[(row*4)+3] * m2[(3*4)+col]; 
	}
	
	
	/**
	 * Constructs Matrix from Quaternion
	 * http://stackoverflow.com/questions/1556260/convert-quaternion-rotation-to-rotation-matrix
	 * @param q
	 * @return
	 */
	public static Matrix4f fromQuaternion(Quaternion q) {
		float qw = q.getW();
		float qx = q.getX();
		float qy = q.getY();
		float qz = q.getZ();
		float n = (float) (1.0f/Math.sqrt(qx*qx+qy*qy+qz*qz+qw*qw));
		qx *= n;
		qy *= n;
		qz *= n;
		qw *= n;
		// too lazy to transpose manually :-)
		return createTransposed(
						1.0f - 2.0f*qy*qy - 2.0f*qz*qz, 2.0f*qx*qy - 2.0f*qz*qw, 2.0f*qx*qz + 2.0f*qy*qw, 0.0f,
						2.0f*qx*qy + 2.0f*qz*qw, 1.0f - 2.0f*qx*qx - 2.0f*qz*qz, 2.0f*qy*qz - 2.0f*qx*qw, 0.0f,
						2.0f*qx*qz - 2.0f*qy*qw, 2.0f*qy*qz + 2.0f*qx*qw, 1.0f - 2.0f*qx*qx - 2.0f*qy*qy, 0.0f,
						0.0f, 0.0f, 0.0f, 1.0f
		);
	}
	
	public String toString() {
		return "["+f(m00)+", "+f(m01)+", "+f(m02)+", "+f(m03)+"]\n"+
				"["+f(m10)+", "+f(m11)+", "+f(m21)+", "+f(m13)+"]\n"+
				"["+f(m20)+", "+f(m21)+", "+f(m22)+", "+f(m23)+"]\n"+
				"["+f(m30)+", "+f(m31)+", "+f(m32)+", "+f(m33)+"]";
	}
	
	public String toString2() {
		StringBuilder b = new StringBuilder();
		float[] m = toArray();
		for (int i = 0; i < 16; i++) {
			b.append(m[i] + ((i+1)%4==0&&i<15?"\n":", "));
		}
		return b.toString();
	}
		
	private static String f(float f) {
		return String.format(Locale.US, "%.2f", f);
	}
		
}
