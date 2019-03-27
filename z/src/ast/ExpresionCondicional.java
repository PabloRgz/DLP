/**
 * @generated VGen (for ANTLR) 1.4.0
 */

package ast;

import visitor.*;

import org.antlr.v4.runtime.*;

//	expresionCondicional:expresion -> izq:expresion  operador:String  dcha:expresion

public class ExpresionCondicional extends AbstractExpresion {

	public ExpresionCondicional(Expresion izq, String operador, Expresion dcha) {
		this.izq = izq;
		this.operador = operador;
		this.dcha = dcha;

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(izq, dcha);
	}

	public ExpresionCondicional(Object izq, Object operador, Object dcha) {
		this.izq = (Expresion) ((izq instanceof ParserRuleContext) ? getAST(izq) : izq);
		this.operador = (operador instanceof Token) ? ((Token)operador).getText() : (String) operador;
		this.dcha = (Expresion) ((dcha instanceof ParserRuleContext) ? getAST(dcha) : dcha);

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(izq, operador, dcha);
	}

	public Expresion getIzq() {
		return izq;
	}
	public void setIzq(Expresion izq) {
		this.izq = izq;
	}

	public String getOperador() {
		return operador;
	}
	public void setOperador(String operador) {
		this.operador = operador;
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
	private String operador;
	private Expresion dcha;

	public String toString() {
       return "{izq:" + getIzq() + ", operador:" + getOperador() + ", dcha:" + getDcha() + "}";
   }
}
