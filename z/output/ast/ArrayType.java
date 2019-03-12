/**
 * @generated VGen (for ANTLR) 1.4.0
 */

package ast;

import visitor.*;

import org.antlr.v4.runtime.*;

//	arrayType:tipo -> posicion:String  tipo:tipo

public class ArrayType extends AbstractTipo {

	public ArrayType(String posicion, Tipo tipo) {
		this.posicion = posicion;
		this.tipo = tipo;

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(tipo);
	}

	public ArrayType(Object posicion, Object tipo) {
		this.posicion = (posicion instanceof Token) ? ((Token)posicion).getText() : (String) posicion;
		this.tipo = (Tipo) ((tipo instanceof ParserRuleContext) ? getAST(tipo) : tipo);

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(posicion, tipo);
	}

	public String getPosicion() {
		return posicion;
	}
	public void setPosicion(String posicion) {
		this.posicion = posicion;
	}

	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private String posicion;
	private Tipo tipo;

	public String toString() {
       return "{posicion:" + getPosicion() + ", tipo:" + getTipo() + "}";
   }
}
