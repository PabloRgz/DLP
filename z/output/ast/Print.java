/**
 * @generated VGen (for ANTLR) 1.4.0
 */

package ast;

import visitor.*;

import org.antlr.v4.runtime.*;

//	print:sentencia -> exp:expresion

public class Print extends AbstractSentencia {

	public Print(Expresion exp) {
		this.exp = exp;

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(exp);
	}

	public Print(Object exp) {
		this.exp = (Expresion) ((exp instanceof ParserRuleContext) ? getAST(exp) : exp);

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(exp);
	}

	public Expresion getExp() {
		return exp;
	}
	public void setExp(Expresion exp) {
		this.exp = exp;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Expresion exp;

	public String toString() {
       return "{exp:" + getExp() + "}";
   }
}
