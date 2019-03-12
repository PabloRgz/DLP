/**
 * @generated VGen (for ANTLR) 1.4.0
 */

package ast;

import java.util.*;
import visitor.*;

import org.antlr.v4.runtime.*;

//	funcion:definicion -> nombre:String  parametro:parametro*  tipo:tipo  variable:variable*  sentencia:sentencia*

public class Funcion extends AbstractDefinicion {

	public Funcion(String nombre, List<Parametro> parametro, Tipo tipo, List<Variable> variable, List<Sentencia> sentencia) {
		this.nombre = nombre;
		this.parametro = parametro;
		this.tipo = tipo;
		this.variable = variable;
		this.sentencia = sentencia;

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(parametro, tipo, variable, sentencia);
	}

	@SuppressWarnings("unchecked")
	public Funcion(Object nombre, Object parametro, Object tipo, Object variable, Object sentencia) {
		this.nombre = (nombre instanceof Token) ? ((Token)nombre).getText() : (String) nombre;
		this.parametro = (List<Parametro>) parametro;
		this.tipo = (Tipo) ((tipo instanceof ParserRuleContext) ? getAST(tipo) : tipo);
		this.variable = (List<Variable>) variable;
		this.sentencia = (List<Sentencia>) sentencia;

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(nombre, parametro, tipo, variable, sentencia);
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Parametro> getParametro() {
		return parametro;
	}
	public void setParametro(List<Parametro> parametro) {
		this.parametro = parametro;
	}

	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public List<Variable> getVariable() {
		return variable;
	}
	public void setVariable(List<Variable> variable) {
		this.variable = variable;
	}

	public List<Sentencia> getSentencia() {
		return sentencia;
	}
	public void setSentencia(List<Sentencia> sentencia) {
		this.sentencia = sentencia;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private String nombre;
	private List<Parametro> parametro;
	private Tipo tipo;
	private List<Variable> variable;
	private List<Sentencia> sentencia;

	public String toString() {
       return "{nombre:" + getNombre() + ", parametro:" + getParametro() + ", tipo:" + getTipo() + ", variable:" + getVariable() + ", sentencia:" + getSentencia() + "}";
   }
}
