/**
 * @generated VGen (for ANTLR) 1.4.0
 */

package ast;

import java.util.*;
import visitor.*;

import org.antlr.v4.runtime.*;

//	llamadaFuncion:sentencia -> nombreFuncion:String  parametrosOpcionales:expresion*

public class LlamadaFuncion extends AbstractSentencia {

	public LlamadaFuncion(String nombreFuncion, List<Expresion> parametrosOpcionales) {
		this.nombreFuncion = nombreFuncion;
		this.parametrosOpcionales = parametrosOpcionales;

		// Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
		// Obtiene la linea/columna a partir de las de los hijos.
		setPositions(parametrosOpcionales);
	}

	@SuppressWarnings("unchecked")
	public LlamadaFuncion(Object nombreFuncion, Object parametrosOpcionales) {
		this.nombreFuncion = (nombreFuncion instanceof Token) ? ((Token) nombreFuncion).getText()
				: (String) nombreFuncion;
		this.parametrosOpcionales = (List<Expresion>) parametrosOpcionales;

		// Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
		// Obtiene la linea/columna a partir de las de los hijos.
		setPositions(nombreFuncion, parametrosOpcionales);
	}

	public String getNombreFuncion() {
		return nombreFuncion;
	}

	public void setNombreFuncion(String nombreFuncion) {
		this.nombreFuncion = nombreFuncion;
	}

	public List<Expresion> getParametrosOpcionales() {
		return parametrosOpcionales;
	}

	public void setParametrosOpcionales(List<Expresion> parametrosOpcionales) {
		this.parametrosOpcionales = parametrosOpcionales;
	}

	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	}

	private String nombreFuncion;
	private List<Expresion> parametrosOpcionales;

	public String toString() {
		return "{nombreFuncion:" + getNombreFuncion() + ", parametrosOpcionales:" + getParametrosOpcionales() + "}";
	}
}
