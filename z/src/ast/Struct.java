/**
 * @generated VGen (for ANTLR) 1.4.0
 */

package ast;

import java.util.*;
import visitor.*;

import org.antlr.v4.runtime.*;

//	struct:definicion -> nombre:String  campo:campo*

public class Struct extends AbstractDefinicion {

	public Struct(String nombre, List<Campo> campo) {
		this.nombre = nombre;
		this.campo = campo;

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(campo);
	}

	@SuppressWarnings("unchecked")
	public Struct(Object nombre, Object campo) {
		this.nombre = (nombre instanceof Token) ? ((Token)nombre).getText() : (String) nombre;
		this.campo = (List<Campo>) campo;

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(nombre, campo);
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Campo> getCampo() {
		return campo;
	}
	public void setCampo(List<Campo> campo) {
		this.campo = campo;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private String nombre;
	private List<Campo> campo;

	public String toString() {
       return "{nombre:" + getNombre() + ", campo:" + getCampo() + "}";
   }
}
