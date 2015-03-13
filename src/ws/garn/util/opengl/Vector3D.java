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

public class Vector3D {

	public final float x, y, z;

	public Vector3D(float x, float y, float z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3D add(Vector3D v) {
		return new Vector3D(x + v.x , y + v.y, z + v.z);
	}
	
	public Vector3D subctract(Vector3D v) {
		return new Vector3D(x - v.x, y - v.y, z - v.z);
	}
	
	public Vector3D mul(float f) {
		return new Vector3D(x * f, y * f, z * f);
	}
	
	public Vector3D div(float d) {
		return new Vector3D(x / d, y / d, z / d);
	}

	public float length() {
		return (float) Math.sqrt(lengthSquared());
	}
	
	public float lengthSquared() {
		return x*x + y*y + z*z;
	}
	
	public Vector3D unitLength() {
		float l = length();
		if (l != .0) {
			return new Vector3D(x / l, y / l, z / l);	
		}
		return this;
	}

	public float[] toArray() {
		return new float[] {x, y, z};
	}

	public String toString() {
		return getClass().getSimpleName() + " [x = "+x+", y = "+y+", z = "+z+"]";
	}
	
	public Vector3D crossProduct3D(Vector3D v) {
		return new Vector3D(
				y * v.z - z * v.y, 
				z * v.x - x * v.z,
				x * v.y - y * v.x
		);
	}
	
	public float dotProduct(Vector3D v) {
		return x * v.x + y * v.y + z * v.z;
	}

}
