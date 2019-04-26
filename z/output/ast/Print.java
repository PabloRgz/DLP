/**
 * @generated VGen (for ANTLR) 1.4.0
 */

package ast;

import visitor.*;

import org.antlr.v4.runtime.*;

//	print:sentencia -> exp:expresion  tipoPrint:String

public class Print extends AbstractSentencia {

	public Print(Expresion exp, String tipoPrint) {
		this.exp = exp;
		this.tipoPrint = tipoPrint;

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(exp);
	}

	public Print(Object exp, Object tipoPrint) {
		this.exp = (Expresion) ((exp instanceof ParserRuleContext) ? getAST(exp) : exp);
		this.tipoPrint = (tipoPrint instanceof Token) ? ((Token)tipoPrint).getText() : (String) tipoPrint;

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(exp, tipoPrint);
	}

	public Expresion getExp() {
		return exp;
	}
	public void setExp(Expresion exp) {
		this.exp = exp;
	}

	public String getTipoPrint() {
		return tipoPrint;
	}
	public void setTipoPrint(String tipoPrint) {
		this.tipoPrint = tipoPrint;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Expresion exp;
	private String tipoPrint;

	public String toString() {
       return "{exp:" + getExp() + ", tipoPrint:" + getTipoPrint() + "}";
   }
}
