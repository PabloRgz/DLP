/**
 * @generated VGen (for ANTLR) 1.4.0
 */

package ast;

import visitor.*;

import org.antlr.v4.runtime.*;

//	asignacion:sentencia -> izq:expresion  dcha:expresion

public class Asignacion extends AbstractSentencia {

	public Asignacion(Expresion izq, Expresion dcha) {
		this.izq = izq;
		this.dcha = dcha;

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(izq, dcha);
	}

	public Asignacion(Object izq, Object dcha) {
		this.izq = (Expresion) ((izq instanceof ParserRuleContext) ? getAST(izq) : izq);
		this.dcha = (Expresion) ((dcha instanceof ParserRuleContext) ? getAST(dcha) : dcha);

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(izq, dcha);
	}

	public Expresion getIzq() {
		return izq;
	}
	public void setIzq(Expresion izq) {
		this.izq = izq;
	}

	public Expresion getDcha() {
		return dcha;
	}
	public void setDcha(Expresion dcha) {
		this.dcha = dcha;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Expresion izq;
	private Expresion dcha;

	public String toString() {
       return "{izq:" + getIzq() + ", dcha:" + getDcha() + "}";
   }
}
