/**
 * @generated VGen (for ANTLR) 1.4.0
 */

package ast;

import visitor.*;

import org.antlr.v4.runtime.*;

//	array:expresion -> nombreArray:expresion  valor:expresion

public class Array extends AbstractExpresion {

	public Array(Expresion nombreArray, Expresion valor) {
		this.nombreArray = nombreArray;
		this.valor = valor;

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(nombreArray, valor);
	}

	public Array(Object nombreArray, Object valor) {
		this.nombreArray = (Expresion) ((nombreArray instanceof ParserRuleContext) ? getAST(nombreArray) : nombreArray);
		this.valor = (Expresion) ((valor instanceof ParserRuleContext) ? getAST(valor) : valor);

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(nombreArray, valor);
	}

	public Expresion getNombreArray() {
		return nombreArray;
	}
	public void setNombreArray(Expresion nombreArray) {
		this.nombreArray = nombreArray;
	}

	public Expresion getValor() {
		return valor;
	}
	public void setValor(Expresion valor) {
		this.valor = valor;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Expresion nombreArray;
	private Expresion valor;

	public String toString() {
       return "{nombreArray:" + getNombreArray() + ", valor:" + getValor() + "}";
   }
}
