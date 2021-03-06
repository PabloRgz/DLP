/**
 * @generated VGen (for ANTLR) 1.4.0
 */

package ast;

import visitor.*;

import org.antlr.v4.runtime.*;

//	identType:tipo -> nombre:String

public class IdentType extends AbstractTipo {

	public IdentType(String nombre) {
		this.nombre = nombre;
	}

	public IdentType(Object nombre) {
		this.nombre = (nombre instanceof Token) ? ((Token) nombre).getText() : (String) nombre;

		// Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
		// Obtiene la linea/columna a partir de las de los hijos.
		setPositions(nombre);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	}

	private String nombre;
	private Struct definicion;

	public Struct getDefinicion() {
		return definicion;
	}

	public void setDefinicion(Struct definicion) {
		this.definicion = definicion;
	}

	public String toString() {
		return "{nombre:" + getNombre() + "}";
	}

	@Override
	public int getSize() {
		int size = 0;
		for (Campo c : this.getDefinicion().getCampo()){
			size += c.getTipo().getSize();
		}
		return size;
	}

	@Override
	public char getSuffix() {
		return ' ';
	}

	@Override
	public String getMAPLName() {
		return nombre;
	}
}
