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

public class Vector2D {

	final float x, y;

	public Vector2D(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2D add(Vector2D v) {
		return new Vector2D(x + v.x , y + v.y);
	}
	
	public Vector2D subctract(Vector2D v) {
		return new Vector2D(x - v.x, y - v.y);
	}
	
	public Vector2D mul(float f) {
		return new Vector2D(x * f, y * f);
	}
	
	public Vector2D div(float d) {
		return new Vector2D(x / d, y / d);
	}

	public float length() {
		return (float) Math.sqrt(lengthSquared());
	}
	
	public float lengthSquared() {
		return x*x + y*y;
	}
	
	public Vector2D unitLength() {
		float l = length();
		if (l != .0) {
			return new Vector2D(x / l, y / l);	
		}
		return this;
	}

	public float[] toArray() {
		return new float[] {x, y};
	}

	public String toString() {
		return getClass().getSimpleName() + " [x = "+x+", y = "+y+"]";
	}

}
