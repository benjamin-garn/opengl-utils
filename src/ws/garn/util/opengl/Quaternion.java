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

public class Quaternion {
	
	/**
	 * The Math Inside
	 * 
	 * q = ( x0 * 1 + x1 * i + x2 * j + x3 * k )
	 * 
	 * Hamilton Rules
	 *		i² = j² = k² = -1
	 * 		ij = +k, jk = +i, ki = +j
	 * 		ji = -k, kj = -i, ik = -j
	 * 		ijk = -1
	 * 
	 */

    /** Identity quaternion. */
    public static final Quaternion IDENTITY = new Quaternion(1, 0, 0, 0);
    /** Zero quaternion. */
    public static final Quaternion ZERO = new Quaternion(0, 0, 0, 0);
    /** i */
    public static final Quaternion I = new Quaternion(0, 1, 0, 0);
    /** j */
    public static final Quaternion J = new Quaternion(0, 0, 1, 0);
    /** k */
    public static final Quaternion K = new Quaternion(0, 0, 0, 1);	
	
	// Components
	final float x0, x1, x2, x3;
	
	public Quaternion(float x0, float x1, float x2, float x3) {
		super();
		this.x0 = x0;
		this.x1 = x1;
		this.x2 = x2;
		this.x3 = x3;
	}
	
	public Quaternion(float alpha, Vector3D n) {
		float l = n.length();
		if (l == 0) {
			x0 = 1;
			x1 = x2 = x3 = 0;
			return;
		}
		l = 1f / l;
		float sin = (float) Math.sin(alpha / 2);
		float cos = (float) Math.cos(alpha / 2);
		
		x0 = cos;
		x1 = n.x * l * sin;
		x2 = n.y * l * sin;
		x3 = n.z * l * sin;
	}
	
	public Quaternion(Vector3D n) {
		x0 = 0;
		x1 = n.x;
		x2 = n.y;
		x3 = n.z;
	}

	
	/**
	 * Addition
	 * @param q
	 * @return
	 */
	public Quaternion add(Quaternion q) {
		return new Quaternion(x0 + q.x0, x1 + q.x1, x2 + q.x2, x3 + q.x3);
	}
	
	/**
	 * Matrix Mutliplikation
	 * @param q
	 * @return
	 */
	public Quaternion mul(Quaternion q) {
		return new Quaternion(
				x0*q.x0 - x1*q.x1 - x2*q.x2 - x3*q.x3, 
				x0*q.x1 + x1*q.x0 + x2*q.x3 - x3*q.x2, 
				x0*q.x2 - x1*q.x3 + x2*q.x0 + x3*q.x1, 
				x0*q.x3 + x1*q.x2 - x2*q.x1 + x3*q.x0  
		);
	}

	/**
	 * Matrix Division
	 * @param q
	 * @return
	 */
	public Quaternion div(Quaternion q) {
		return mul(q.inverse());
	}
	
	/**
	 * Skalar Multiplikation
	 * @param s
	 * @return
	 */
	public Quaternion mul (float s) {
		return new Quaternion(s * x0, s * x1, s * x2, s * x3);
	}
	
	/**
	 * Return inverse
	 * @return
	 */
	public Quaternion inverse() {
		return mul(-1);
	}
	
	/**
	 * Subtraktion
	 * @param q
	 * @return
	 */
	public Quaternion substract(Quaternion q) {
		return add(q.inverse());
	}
	
	/**+
	 * Konjugation
	 * @return
	 */
	public Quaternion conjugate() {
		return new Quaternion(x0, -x1, -x2, -x3);
	}
	
	/**
	 * Skalarprodukt
	 * @param q
	 * @return
	 */
	public float dotProduct(Quaternion q) {
		return   x0 * q.x0 
				+ x1 * q.x1
				+ x2 + q.x2
				+ x3 * q.x3;
	}
	
	/**
	 * Kreuzprodukt
	 * @param q
	 * @return
	 */
	public Quaternion crossProduct(Quaternion q) {
		return new Quaternion(
				0,							// TODO: Ist das korrekt??? Das Kreuzprodukt ist 
											//        scheinbar nur über das Vektorprodukt des imaginärteils definiert
				(x2*q.x3 - x3*q.x2), 
				(x3*q.x1 - x1*q.x3), 
				(x1*q.x2 - x2*q.x1)
		);
	}
	
	/**
	 * Norm 
	 * @return
	 */
	public float lengthSquared() {
		return x0 * x0 + x1 * x1 + x2 * x2 + x3 * x3;
	}
	
	/**
	 * Betrag
	 * @return
	 */
	public float length() {
		return (float) Math.sqrt(lengthSquared());
	}
	
	public Quaternion unitLength() {
		float l = length();
		if (l != .0) {
			return new Quaternion(x0 / l, x1 / l, x2 / l, x3 / l);
		}
		return this;
	}
	
	public Vector3D vector() {
		return new Vector3D(x1, x2, x3);
	}

	public float angle() {
		return x0;
	}
	
	public Vector3D rotate(Vector3D v) {
		Quaternion p = new Quaternion(v);
		Quaternion h = mul(p).mul(conjugate());
		return h.vector();
	}
		
	public String toString() {
		return getClass().getSimpleName() + " [x0 = "+x0+", x1 = "+x1+", x2 = "+x2+", x3 = "+x3+"]";
	}
	
	public float getW() {
		return x0;
	}

	public float getX() {
		return x1;
	}
	
	public float getY() {
		return x2;
	}
	
	public float getZ() {
		return x3;
	}
	
}
