package semantic;

import java.util.HashMap;
import java.util.Map;

import ast.*;
import main.ErrorManager;
import visitor.DefaultVisitor;

public class Identification extends DefaultVisitor {

	private ContextMap<String, Variable> variables = new ContextMap<String, Variable>();

	private Map<String, Funcion> funciones = new HashMap<String, Funcion>();
	private Map<String, Struct> structs = new HashMap<String, Struct>();
	private Map<String, Campo> campos = new HashMap<String, Campo>();

	public Identification(ErrorManager errorManager) {
		this.errorManager = errorManager;
	}

	/*
	 * Poner aquí los visit necesarios. Si se ha usado VGen, solo hay que copiarlos
	 * de la clase 'visitor/_PlantillaParaVisitors.txt'.
	 */
	
	//class Programa { List<Definicion> definicion; }
	public Object visit(Program node, Object param) {
		visitChildren(node.getDefinicion(), param);
		return null;
	}
	
	
	// class Funcion { String nombre; List<Parametro> parametro; Tipo tipo;
	// List<Variable> variable; List<Sentencia> sentencia; }
	public Object visit(Funcion node, Object param) {

		// super.visit(node, param);
		predicado(funciones.get(node.getNombre()) == null, "Funcion ya definida: " + node.getNombre(), node);
		funciones.put(node.getNombre(), node);

		variables.set();
		if (node.getParametro() != null)
			for (Parametro child : node.getParametro())
				child.accept(this, param);

		if (node.getTipo() != null)
			node.getTipo().accept(this, param);

		if (node.getVariable() != null)
			for (Variable child : node.getVariable())
				child.accept(this, param);

		if (node.getSentencia() != null)
			for (Sentencia child : node.getSentencia())
				child.accept(this, param);
		variables.reset();

		return null;
	}

	// class LlamadaFuncion { String nombreFuncion; List<Expresion>
	// parametrosOpcionales; }
	public Object visit(LlamadaFuncion node, Object param) {

		// super.visit(node, param);
		Funcion definicion = funciones.get(node.getNombreFuncion());
		predicado(definicion != null, "Procedimiento no definido: " + node.getNombreFuncion(), node);
		node.setDefinicion(definicion);
		if (node.getParametrosOpcionales() != null)
			for (Expresion child : node.getParametrosOpcionales())
				child.accept(this, param);

		return null;
	}

	// class LlamadaFuncionExp { String nombreFuncion; List<Expresion>
	// parametrosOpcionales; }
	public Object visit(LlamadaFuncionExp node, Object param) {

		// super.visit(node, param);
		Funcion definicion = funciones.get(node.getNombreFuncion());
		predicado(definicion != null, "Funcion no definida: " + node.getNombreFuncion(), node);
		node.setDefinicion(definicion);
		if (node.getParametrosOpcionales() != null)
			for (Expresion child : node.getParametrosOpcionales())
				child.accept(this, param);

		return null;
	}

	// class Variable { String nombre; Tipo tipo; }
	public Object visit(Variable node, Object param) {

		// super.visit(node, param);
		predicado(variables.getFromTop(node.getNombre()) == null, "Variable ya definida: " + node.getNombre(), node);
		variables.put(node.getNombre(), node);

		if (node.getTipo() != null)
			node.getTipo().accept(this, param);

		return null;
	}

	// class IdentConstant { String valor; }
	public Object visit(IdentConstant node, Object param) {
		predicado(variables.getFromAny(node.getValor()) != null, "Variable sin definir: " + node.getValor(), node);
		node.setDefinicion(variables.getFromAny(node.getValor()));
		return null;
	}

	// class Parametro { String nombre; Tipo tipo; }
	public Object visit(Parametro node, Object param) {

		// super.visit(node, param);
		Variable variable = new Variable(node.getNombre(), node.getTipo(), "parametro");
		variable.setParametro(node);
		predicado(variables.getFromTop(node.getNombre()) == null, "Parametro ya definido: " + node.getNombre(), node);
		variables.put(variable.getParametro().getNombre(), variable);
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);

		return null;
	}

	// class Struct { String nombre; List<Campo> campo; }
	public Object visit(Struct node, Object param) {

		// super.visit(node, param);
		predicado(structs.get(node.getNombre()) == null, "Struct ya definido: " + node.getNombre(), node);
		structs.put(node.getNombre(), node);
		campos.clear();
		if (node.getCampo() != null)
			for (Campo child : node.getCampo())
				child.accept(this, param);

		return null;
	}

	// class IdentType { }
	public Object visit(IdentType node, Object param) {
		Struct definicion = structs.get(node.getNombre());
		predicado(definicion != null, "Struct no definido: " + node.getNombre(), node);
		node.setDefinicion(definicion);
		return null;
	}

	// class Campo { String nombre; Tipo tipo; }
	public Object visit(Campo node, Object param) {

		// super.visit(node, param);
		predicado(campos.get(node.getNombre()) == null, "Campo ya definido: " + node.getNombre(), node);
		campos.put(node.getNombre(), node);
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);

		return null;
	}

	/**
	 * predicado. Método auxiliar para implementar los predicados. Borrar si no se
	 * quiere usar.
	 *
	 * Ejemplos de uso (suponiendo que existe un método "esPrimitivo"):
	 *
	 * 1. predicado(esPrimitivo(expr.tipo), "La expresión debe ser de un tipo
	 * pimitivo", expr.getStart()); 2. predicado(esPrimitivo(expr.tipo), "La
	 * expresión debe ser de un tipo pimitivo", expr); 3.
	 * predicado(esPrimitivo(expr.tipo), "La expresión debe ser de un tipo
	 * primitivo");
	 *
	 * NOTA: El método getStart() (ejemplo 1) indica la linea/columna del fichero
	 * fuente donde estaba el nodo (y así poder dar información más detallada de
	 * la posición del error). Si se usa VGen, dicho método habrá sido generado
	 * en todos los nodos del AST. No es obligatorio llamar a getStart() (ejemplo
	 * 2), ya que si se pasa el nodo, se usará por defecto dicha posición. Si no
	 * se quiere imprimir la posición del fichero, se puede omitir el tercer
	 * argumento (ejemplo 3).
	 *
	 * @param condicion
	 *            Debe cumplirse para que no se produzca un error
	 * @param mensajeError
	 *            Se imprime si no se cumple la condición
	 * @param posicionError
	 *            Fila y columna del fichero donde se ha producido el error.
	 */
	private void predicado(boolean condicion, String mensajeError, Position posicionError) {
		if (!condicion)
			errorManager.notify("Comprobacion de tipos", mensajeError, posicionError);
	}

	private void predicado(boolean condicion, String mensajeError, AST node) {
		predicado(condicion, mensajeError, node.getStart());
	}

	private void predicado(boolean condicion, String mensajeError) {
		predicado(condicion, mensajeError, (Position) null);
	}

	private ErrorManager errorManager;
}
