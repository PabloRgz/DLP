/**
 * @generated VGen (for ANTLR) 1.4.0
 */

package ast;

import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

import visitor.Visitor;

//	parametro -> nombre:String  tipo:tipo

public class Parametro extends AbstractAST {

	public Parametro(String nombre, Tipo tipo) {
		this.nombre = nombre;
		this.tipo = tipo;

		// Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
		// Obtiene la linea/columna a partir de las de los hijos.
		setPositions(tipo);
	}

	public Parametro(Object nombre, Object tipo) {
		this.nombre = (nombre instanceof Token) ? ((Token) nombre).getText() : (String) nombre;
		this.tipo = (Tipo) ((tipo instanceof ParserRuleContext) ? getAST(tipo) : tipo);

		// Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
		// Obtiene la linea/columna a partir de las de los hijos.
		setPositions(nombre, tipo);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	private String nombre;
	private Tipo tipo;
	private Variable definicion;

	public Variable getParametro() {
		return definicion;
	}

	public void setParametro(Variable parametro) {
		this.definicion = parametro;
	}

	public String toString() {
		return "{nombre:" + getNombre() + ", tipo:" + getTipo() + "}";
	}
}
