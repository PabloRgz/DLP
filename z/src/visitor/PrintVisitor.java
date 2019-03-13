package visitor;

import ast.*;

/*
Plantilla para Visitors.
Para crear un nuevo Visitor cortar y pegar este código y ya se tendría un visitor que compila y
que al ejecutarlo recorrería todo el árbol (sin hacer nada aún en él).
Solo quedaría añadir a cada método visit aquello adicional que se quiera realizar sobre su nodo del AST.
*/

public class PrintVisitor extends DefaultVisitor {

	// ---------------------------------------------------------
	// Tareas a realizar en cada método visit:
	//
	// Si en algún método visit NO SE QUIERE HACER NADA más que recorrer los
	// hijos entonces se puede
	// borrar (dicho método se heredaría de DefaultVisitor con el código de
	// recorrido).
	//
	// Lo siguiente es para cuando se quiera AÑADIR alguna funcionalidad adicional
	// a un visit:
	//
	// - El código que aparece en cada método visit es aquel que recorre los
	// hijos. Es el mismo código
	// que está implementado en el padre (DefaultVisitor). Por tanto la llamada a
	// 'super.visit' y el
	// resto del código del método hacen lo mismo (por ello 'super.visit' está
	// comentado).
	//
	// - Lo HABITUAL será borrar todo el código de recorrido dejando solo la
	// llamada a 'super.visit'. De esta
	// manera, cada método visit se puede centrar en la tarea que tiene que
	// realizar sobre su nodo del AST
	// (dejando que el padre se encargue del recorrido de los hijos).
	//
	// - La razón de que aparezca el código de recorrido de los hijos es por si se
	// necesita realizar alguna
	// tarea DURANTE el mismo (por ejemplo ir comprobando su tipo). En este caso, ya
	// se tiene implementado
	// dicho recorrido y solo habría que incrustar las acciones adicionales en el
	// mismo. En este caso,
	// es la llamada a 'super.visit' la que debería ser borrada.
	// ---------------------------------------------------------

	// class Program { List<Definicion> definicion; }
	public Object visit(Program node, Object param) {

		// super.visit(node, param);

		if (node.getDefinicion() != null)
			for (Definicion child : node.getDefinicion())
				child.accept(this, param);

		return null;
	}

	// class Variable { String nombre; Tipo tipo; }
	public Object visit(Variable node, Object param) {

		// super.visit(node, param);

		System.out.print("var " + node.getNombre() + ":");
		if (node.getTipo() != null) {
			node.getTipo().accept(this, param);
		}
		System.out.println(";");
		return null;
	}

	// class Struct { String nombre; List<Campo> campo; }
	public Object visit(Struct node, Object param) {

		// super.visit(node, param);
		System.out.print("struct " + node.getNombre() + " {");
		if (node.getCampo() != null)
			for (Campo child : node.getCampo())
				child.accept(this, param);
		System.out.println("}" + ";");
		return null;
	}

	// class Funcion { String nombre; List<Parametro> parametro; Tipo tipo;
	// List<Variable> variable; List<Sentencia> sentencia; }
	public Object visit(Funcion node, Object param) {

		// super.visit(node, param);

		System.out.print(node.getNombre() + "(");
		if (node.getParametro() != null) {
			for (Parametro child : node.getParametro()) {
				child.accept(this, param);
			}
			System.out.print(")");
		}
		System.out.print(" tipo retorno: ");
		if (node.getTipo() != null) {
			node.getTipo().accept(this, param);
		}
		System.out.println("{ ");
		if (node.getVariable() != null) {
			for (Variable child : node.getVariable()) {
				child.accept(this, param);
			}

		}

		if (node.getSentencia() != null) {
			for (Sentencia child : node.getSentencia()) {
				child.accept(this, param);
				System.out.println(";");
			}

		}
		System.out.println("}");
		return null;
	}

	// class Campo { String nombre; Tipo tipo; }
	public Object visit(Campo node, Object param) {

		// super.visit(node, param);

		System.out.print(node.getNombre() + ":");
		if (node.getTipo() != null) {
			node.getTipo().accept(this, param);
		}
		System.out.print("; ");
		return null;
	}

	// class Parametro { String nombre; Tipo tipo; }
	public Object visit(Parametro node, Object param) {

		// super.visit(node, param);
		System.out.print(node.getNombre() + ":");
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		System.out.print(", ");
		return null;
	}

	// class IntType { }
	public Object visit(IntType node, Object param) {
		System.out.print("int");
		return null;
	}

	// class FloatType { }
	public Object visit(FloatType node, Object param) {
		System.out.print("float");
		return null;
	}

	// class CharType { }
	public Object visit(CharType node, Object param) {
		System.out.print("char");
		return null;
	}

	// class IdentType { }
	public Object visit(IdentType node, Object param) {
		System.out.print("ident");
		return null;
	}

	// class ArrayType { String posicion; Tipo tipo; }
	public Object visit(ArrayType node, Object param) {

		// super.visit(node, param);
		System.out.print("[" + node.getPosicion() + "]");
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);

		return null;
	}

	// class Print { Expresion exp; }
	public Object visit(Print node, Object param) {

		// super.visit(node, param);
		System.out.println("print ");
		if (node.getExp() != null)
			node.getExp().accept(this, param);

		return null;
	}

	// class Read { Expresion expr; }
	public Object visit(Read node, Object param) {

		// super.visit(node, param);
		System.out.print("read ");
		if (node.getExpr() != null)
			node.getExpr().accept(this, param);

		return null;
	}

	// class Asignacion { Expresion izq; Expresion dcha; }
	public Object visit(Asignacion node, Object param) {

		// super.visit(node, param);

		if (node.getIzq() != null)
			node.getIzq().accept(this, param);
		System.out.print("=");
		if (node.getDcha() != null)
			node.getDcha().accept(this, param);
		return null;
	}

	// class If { Expresion condicion; List<Sentencia> verdadero; List<Sentencia>
	// falso; }
	public Object visit(If node, Object param) {

		// super.visit(node, param);

		System.out.print("if (");
		if (node.getCondicion() != null)
			node.getCondicion().accept(this, param);
		System.out.print(")");
		if (node.getVerdadero() != null) {
			System.out.print("{");
			for (Sentencia child : node.getVerdadero()) {
				child.accept(this, param);
				System.out.print( ";" );
			}
			System.out.print("}");
		}
		if (node.getFalso() != null) {
			System.out.print("else {");
			for (Sentencia child : node.getFalso()) {
				child.accept(this, param);
				System.out.print( ";" );
			}
			System.out.print("}");
		}
		return null;
	}

	// class While { Expresion condicion; List<Sentencia> sentencia; }
	public Object visit(While node, Object param) {

		// super.visit(node, param);
		System.out.print("while (");
		if (node.getCondicion() != null)
			node.getCondicion().accept(this, param);
		System.out.print(")");
		if (node.getSentencia() != null)
			for (Sentencia child : node.getSentencia()) {
				child.accept(this, param);
				System.out.print( " ");
			}
		return null;
	}

	// class LlamadaFuncion { String nombreFuncion; List<Expresion>
	// parametrosOpcionales; }
	public Object visit(LlamadaFuncion node, Object param) {

		// super.visit(node, param);
		System.out.print(node.getNombreFuncion());
		if (node.getParametrosOpcionales() != null)
			for (Expresion child : node.getParametrosOpcionales())
				child.accept(this, param);

		return null;
	}

	// class Return { Expresion retorno; }
	public Object visit(Return node, Object param) {

		// super.visit(node, param);
		System.out.print("return ");
		if (node.getRetorno() != null)
			node.getRetorno().accept(this, param);
		return null;
	}

	// class IntConstant { String valor; }
	public Object visit(IntConstant node, Object param) {
		System.out.print(node.getValor());
		return null;
	}

	// class RealConstant { String valor; }
	public Object visit(RealConstant node, Object param) {
		System.out.print(node.getValor());
		return null;
	}

	// class CharConstant { String valor; }
	public Object visit(CharConstant node, Object param) {
		System.out.print(node.getValor());
		return null;
	}

	// class IdentConstant { String valor; }
	public Object visit(IdentConstant node, Object param) {
		System.out.print(node.getValor());
		return null;
	}

	// class Array { Expresion nombreArray; Expresion valor; }
	public Object visit(Array node, Object param) {

		// super.visit(node, param);
		System.out.print("[");
		if (node.getNombreArray() != null)
			node.getNombreArray().accept(this, param);

		if (node.getValor() != null)
			node.getValor().accept(this, param);
		System.out.print("]");
		return null;
	}

	// class Cast { Tipo tipo; Expresion valor; }
	public Object visit(Cast node, Object param) {

		// super.visit(node, param);
		System.out.print("(cast < ");
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		System.out.print(">)");
		if (node.getValor() != null)
			node.getValor().accept(this, param);
		
		return null;
	}

	// class ExpresionMatematica { Expresion izq; String operador; Expresion dcha; }
	public Object visit(ExpresionMatematica node, Object param) {

		// super.visit(node, param);
		System.out.print("(");
		if (node.getIzq() != null)
			node.getIzq().accept(this, param);
		System.out.print(node.getOperador());
		if (node.getDcha() != null)
			node.getDcha().accept(this, param);
		System.out.print(")");
		return null;
	}

	// class ExpresionLogica { Expresion izq; String operador; Expresion dcha; }
	public Object visit(ExpresionLogica node, Object param) {

		// super.visit(node, param);
		System.out.print("( ");
		if (node.getIzq() != null)
			node.getIzq().accept(this, param);
		System.out.print(node.getOperador());
		if (node.getDcha() != null)
			node.getDcha().accept(this, param);
		System.out.print(")");
		return null;
	}

	// class Not { Expresion expresion; }
	public Object visit(Not node, Object param) {

		// super.visit(node, param);
		System.out.print("!");
		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		return null;
	}

	// class Acceso { Expresion nombre; String string; }
	public Object visit(Acceso node, Object param) {

		// super.visit(node, param);
		System.out.print(node.getString() + ".");
		if (node.getNombre() != null)
			node.getNombre().accept(this, param);
		return null;
	}

	// class LlamadaFuncionExp { String nombreFuncion; List<Expresion>
	// parametrosOpcionales; }
	public Object visit(LlamadaFuncionExp node, Object param) {

		// super.visit(node, param);
		System.out.print(node.getNombreFuncion());
		if (node.getParametrosOpcionales() != null)
			for (Expresion child : node.getParametrosOpcionales())
				child.accept(this, param);
		return null;
	}
}