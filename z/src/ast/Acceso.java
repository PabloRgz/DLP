/**
 * @generated VGen (for ANTLR) 1.4.0
 */

package ast;

import visitor.*;

import org.antlr.v4.runtime.*;

//	acceso:expresion -> nombre:expresion  string:String

public class Acceso extends AbstractExpresion {

	public Acceso(Expresion nombre, String string) {
		this.nombre = nombre;
		this.string = string;

		// Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
		// Obtiene la linea/columna a partir de las de los hijos.
		setPositions(nombre);
	}

	public Acceso(Object nombre, Object string) {
		this.nombre = (Expresion) ((nombre instanceof ParserRuleContext) ? getAST(nombre) : nombre);
		this.string = (string instanceof Token) ? ((Token) string).getText() : (String) string;

		// Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
		// Obtiene la linea/columna a partir de las de los hijos.
		setPositions(nombre, string);
	}

	public Expresion getNombre() {
		return nombre;
	}

	public void setNombre(Expresion nombre) {
		this.nombre = nombre;
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	}

	private Expresion nombre;
	private String string;
	private Struct campo;

	public Struct getCampo() {
		return campo;
	}

	public void setCampo(Struct campo) {
		this.campo = campo;
	}

	public String toString() {
		return "{nombre:" + getNombre() + ", string:" + getString() + "}";
	}
}
