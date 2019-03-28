package semantic;

import ast.AST;
import ast.Acceso;
import ast.Array;
import ast.ArrayType;
import ast.Asignacion;
import ast.Campo;
import ast.Cast;
import ast.CharConstant;
import ast.CharType;
import ast.Expresion;
import ast.ExpresionCondicional;
import ast.ExpresionLogica;
import ast.ExpresionMatematica;
import ast.FloatType;
import ast.Funcion;
import ast.IdentConstant;
import ast.IdentType;
import ast.If;
import ast.IntConstant;
import ast.IntType;
import ast.LlamadaFuncion;
import ast.LlamadaFuncionExp;
import ast.Not;
import ast.Parametro;
import ast.Position;
import ast.Print;
import ast.Read;
import ast.RealConstant;
import ast.Return;
import ast.Sentencia;
import ast.Struct;
import ast.Tipo;
import ast.Variable;
import ast.While;
import main.ErrorManager;
import visitor.DefaultVisitor;

public class TypeChecking extends DefaultVisitor {

	public TypeChecking(ErrorManager errorManager) {
		this.errorManager = errorManager;
	}

	/*
	 * Poner aqu铆 los visit necesarios. Si se ha usado VGen, solo hay que copiarlos
	 * de la clase 'visitor/_PlantillaParaVisitors.txt'.
	 */

	// class Funcion { String nombre; List<Parametro> parametro; Tipo tipo;
	// List<Variable> variable; List<Sentencia> sentencia; }
	public Object visit(Funcion node, Object param) {

		// super.visit(node, param);

		for (Sentencia s : node.getSentencia())
			s.setFuncion(node);

		if (node.getParametro() != null)
			for (Parametro child : node.getParametro()) {
				child.accept(this, param);
				predicado(esTipoSimple(child.getTipo()), "Los par醡etros deben de ser tipos primitivos", node);
			}

		if (node.getTipo() != null) {
			node.getTipo().accept(this, param);
			predicado(esTipoSimple(node.getTipo()), "Error: Retorno de tipo no simple");
		}

		if (node.getVariable() != null)
			for (Variable child : node.getVariable())
				child.accept(this, param);

		if (node.getSentencia() != null)
			for (Sentencia child : node.getSentencia())
				child.accept(this, param);

		return null;
	}

	// class Return { Expresion retorno; }
	public Object visit(Return node, Object param) {

		// super.visit(node, param);
		if (node.getRetorno() != null)
			node.getRetorno().accept(this, param);

		if (node.getFuncion().getTipo() == null)
			predicado(node.getRetorno() == null, "El return no debe tener expresi髇 en funciones void", node);
		else {
			predicado(node.getRetorno() != null, "Debe haber un valor de retorno", node);
			if (node.getRetorno() != null) {
				predicado(mismoTipo(node.getRetorno().getTipo(), node.getFuncion().getTipo()),
						"Tipo de retorno no coincide", node);
			}
		}

		return null;
	}

	// class LlamadaFuncion { String nombreFuncion; List<Expresion>
	// parametrosOpcionales; }
	public Object visit(LlamadaFuncion node, Object param) {

		// super.visit(node, param);

		if (node.getParametrosOpcionales() != null)
			for (Expresion child : node.getParametrosOpcionales())
				child.accept(this, param);

		predicado(node.getDefinicion().getParametro().size() == node.getParametrosOpcionales().size(),
				"Numero de argumentos incorrecto", node);

		if ((node.getDefinicion().getParametro().size() == node.getParametrosOpcionales().size())) {
			for (int i = 0; i < node.getParametrosOpcionales().size(); i++) {
				predicado(
						mismoTipo(node.getParametrosOpcionales().get(i).getTipo(),
								node.getDefinicion().getParametro().get(i).getTipo()),
						"Tipo de los par醡etros no coincide", node);
			}

		}
		return null;
	}

	// class If { Expresion condicion; List<Sentencia> verdadero; List<Sentencia>
	// falso; }
	public Object visit(If node, Object param) {

		// super.visit(node, param);

		if (node.getCondicion() != null)
			node.getCondicion().accept(this, param);

		predicado(node.getCondicion().getTipo() instanceof IntType, "La condicion debe ser de tipo entero", node);

		if (node.getVerdadero() != null) {
			for (Sentencia child : node.getVerdadero()) {
				child.setFuncion(node.getFuncion());
				child.accept(this, param);
			}
		}

		if (node.getFalso() != null) {
			for (Sentencia child : node.getFalso()) {
				child.setFuncion(node.getFuncion());
				child.accept(this, param);
			}
		}
		return null;
	}

	// class While { Expresion condicion; List<Sentencia> sentencia; }
	public Object visit(While node, Object param) {

		// super.visit(node, param);

		if (node.getCondicion() != null)
			node.getCondicion().accept(this, param);

		predicado(node.getCondicion().getTipo() instanceof IntType, "La condicion debe ser de tipo entero", node);

		if (node.getSentencia() != null) {
			for (Sentencia child : node.getSentencia()) {
				child.setFuncion(node.getFuncion());
				child.accept(this, param);
			}
		}

		return null;
	}

	// class Asignacion { Expresion izq; Expresion dcha; }
	public Object visit(Asignacion node, Object param) {

		// super.visit(node, param);

		if (node.getIzq() != null) {
			node.getIzq().accept(this, param);
		}

		if (node.getDcha() != null) {
			node.getDcha().accept(this, param);
		}

		predicado(node.getIzq().isModificable(), " Valor de la izquierda no modificable", node);
		predicado(esTipoSimple(node.getIzq().getTipo()), " Valor de la izquierda debe ser simple", node);
		predicado(mismoTipo(node.getIzq().getTipo(), node.getDcha().getTipo()), "Valores de distinto tipo", node);

		return null;
	}

	// class IntConstant { String valor; }
	public Object visit(IntConstant node, Object param) {
		node.setTipo(new IntType());
		node.setModificable(false);
		return null;
	}

	// class RealConstant { String valor; }
	public Object visit(RealConstant node, Object param) {
		node.setTipo(new FloatType());
		node.setModificable(false);
		return null;
	}

	// class CharConstant { String valor; }
	public Object visit(CharConstant node, Object param) {
		node.setTipo(new CharType());
		node.setModificable(false);
		return null;
	}

	// class IdentConstant { String valor; }
	public Object visit(IdentConstant node, Object param) {
		node.setTipo(node.getDefinicion().getTipo());
		node.setModificable(true);
		return null;
	}

	// class Print { Expresion exp; }
	public Object visit(Print node, Object param) {

		// super.visit(node, param);

		if (node.getExp() != null)
			node.getExp().accept(this, param);

		predicado(esTipoSimple(node.getExp().getTipo()), "Debe ser un tipo simple", node);

		return null;
	}

	// class Read { Expresion expr; }
	public Object visit(Read node, Object param) {

		// super.visit(node, param);

		if (node.getExpr() != null)
			node.getExpr().accept(this, param);

		predicado(esTipoSimple(node.getExpr().getTipo()), "Debe ser un tipo simple", node);
		predicado(node.getExpr().isModificable(), "Debe de ser modificable", node);
		return null;
	}

	// class Array { Expresion nombreArray; Expresion valor; }
	public Object visit(Array node, Object param) {

		// super.visit(node, param);

		if (node.getNombreArray() != null)
			node.getNombreArray().accept(this, param);

		if (node.getValor() != null)
			node.getValor().accept(this, param);

		predicado(node.getNombreArray().getTipo() instanceof ArrayType, "Debe ser tipo array", node);
		if (node.getNombreArray().getTipo() instanceof ArrayType) {
			predicado(node.getValor().getTipo() instanceof IntType, "Debe ser indice entero", node);
			node.setTipo(((ArrayType) node.getNombreArray().getTipo()).getTipo());
		}

		node.setModificable(true);
		return null;
	}

	// class Cast { Tipo tipo; Expresion valor; }
	public Object visit(Cast node, Object param) {

		// super.visit(node, param);

		if (node.getTipo() != null)
			node.getTipo().accept(this, param);

		if (node.getValor() != null)
			node.getValor().accept(this, param);

		predicado(esTipoSimple(node.getTipo()), "Tipos simples", node);
		predicado(esTipoSimple(node.getValor().getTipo()), "Expresiones de tipos simples", node);
		predicado(!mismoTipo(node.getTipo(), node.getValor().getTipo()), "Deben ser distintos tipos", node);

		node.setModificable(false);

		return null;
	}

	// class ExpresionMatematica { Expresion izq; String operador; Expresion dcha; }
	public Object visit(ExpresionMatematica node, Object param) {

		// super.visit(node, param);

		if (node.getIzq() != null)
			node.getIzq().accept(this, param);

		if (node.getDcha() != null)
			node.getDcha().accept(this, param);

		predicado(mismoTipo(node.getIzq().getTipo(), node.getDcha().getTipo()), "Deben ser tipos simples", node);
		predicado(esTipoSimple(node.getIzq().getTipo()), "Deben ser del mismo tipo", node);
		node.setTipo(node.getIzq().getTipo());
		node.setModificable(false);

		return null;
	}

	// class ExpresionLogica { Expresion izq; String operador; Expresion dcha; }
	public Object visit(ExpresionLogica node, Object param) {

		// super.visit(node, param);

		if (node.getIzq() != null)
			node.getIzq().accept(this, param);

		if (node.getDcha() != null)
			node.getDcha().accept(this, param);

		predicado(
				node.getIzq().getTipo() instanceof IntType
						&& mismoTipo(node.getDcha().getTipo(), node.getIzq().getTipo()),
				"Deben ser del mismo tipo", node);
		node.setTipo(node.getIzq().getTipo());
		node.setModificable(false);

		return null;
	}

	// class ExpresionCondicional { Expresion izq; String operador; Expresion dcha;
	// }
	public Object visit(ExpresionCondicional node, Object param) {

		// super.visit(node, param);

		if (node.getIzq() != null)
			node.getIzq().accept(this, param);

		if (node.getDcha() != null)
			node.getDcha().accept(this, param);

		predicado(mismoTipo(node.getIzq().getTipo(), node.getDcha().getTipo()), "Deben ser del mismo tipo", node);
		predicado(node.getIzq().getTipo() instanceof IntType || node.getIzq().getTipo() instanceof FloatType,
				"Deben ser de tipo entero o real", node);

		node.setTipo(new IntType());
		node.setModificable(false);

		return null;
	}

	// class Not { Expresion expresion; }
	public Object visit(Not node, Object param) {

		// super.visit(node, param);

		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);

		predicado(node.getExpresion().getTipo() instanceof IntType, "Deben ser de tipo entero", node);
		node.setTipo(new IntType());
		node.setModificable(false);

		return null;
	}

	// class Acceso { Expresion nombre; String string; }
	public Object visit(Acceso node, Object param) {

		// super.visit(node, param);

		if (node.getNombre() != null)
			node.getNombre().accept(this, param);

		predicado(node.getNombre().getTipo() instanceof IdentType, "Se requiere tipo struct", node);

		if (node.getNombre().getTipo() instanceof IdentType) {
			IdentType st = (IdentType) node.getNombre().getTipo();
			Struct s = (Struct) st.getDefinicion();
			for (Campo c : s.getCampo()) {
				if (c.getNombre().equals(node.getString())) {
					node.setTipo(c.getTipo());
				}
			}
			predicado(node.getTipo() != null, "Campo no definido", node.getStart());
		}

		node.setModificable(true);

		return null;
	}

	// class LlamadaFuncionExp { String nombreFuncion; List<Expresion>
	// parametrosOpcionales; }
	public Object visit(LlamadaFuncionExp node, Object param) {

		// super.visit(node, param);

		if (node.getParametrosOpcionales() != null)
			for (Expresion child : node.getParametrosOpcionales())
				child.accept(this, param);

		predicado(node.getDefinicion().getTipo() != null, "No tiene tipo de retorno", node);
		predicado(node.getParametrosOpcionales().size() == node.getDefinicion().getParametro().size(),
				"Numero de argumentos incorrecto", node);

		if (node.getParametrosOpcionales().size() == node.getDefinicion().getParametro().size()) {
			for (int i = 0; i < node.getParametrosOpcionales().size(); i++) {
				predicado(
						mismoTipo(node.getParametrosOpcionales().get(i).getTipo(),
								node.getDefinicion().getParametro().get(i).getTipo()),
						"El tipo de los parametros no coincide", node);
			}
		}

		node.setTipo(node.getDefinicion().getTipo());
		node.setModificable(false);

		return null;
	}

	/**
	 * predicado. M茅todo auxiliar para implementar los predicados. Borrar si no se
	 * quiere usar.
	 *
	 * Ejemplos de uso (suponiendo que existe un m茅todo "esPrimitivo"):
	 *
	 * 1. predicado(esPrimitivo(expr.tipo), "La expresi贸n debe ser de un tipo
	 * pimitivo", expr.getStart()); 2. predicado(esPrimitivo(expr.tipo), "La
	 * expresi贸n debe ser de un tipo pimitivo", expr); 3.
	 * predicado(esPrimitivo(expr.tipo), "La expresi贸n debe ser de un tipo
	 * primitivo");
	 *
	 * NOTA: El m茅todo getStart() (ejemplo 1) indica la linea/columna del fichero
	 * fuente donde estaba el nodo (y as铆 poder dar informaci贸n m谩s detallada de
	 * la posici贸n del error). Si se usa VGen, dicho m茅todo habr谩 sido generado
	 * en todos los nodos del AST. No es obligatorio llamar a getStart() (ejemplo
	 * 2), ya que si se pasa el nodo, se usar谩 por defecto dicha posici贸n. Si no
	 * se quiere imprimir la posici贸n del fichero, se puede omitir el tercer
	 * argumento (ejemplo 3).
	 *
	 * @param condicion
	 *            Debe cumplirse para que no se produzca un error
	 * @param mensajeError
	 *            Se imprime si no se cumple la condici贸n
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

	private boolean esTipoSimple(Tipo tipo) {
		return tipo instanceof IntType || tipo instanceof FloatType || tipo instanceof CharType;
	}

	private boolean mismoTipo(Tipo tipo1, Tipo tipo2) {
		return tipo1 != null && tipo2 != null && tipo1.getClass().equals(tipo2.getClass());
	}

	private ErrorManager errorManager;
}
