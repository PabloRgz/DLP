package codegeneration;

import ast.*;
import visitor.*;

/**
 * Clase encargada de asignar direcciones a las variables.
 */
public class MemoryAllocation extends DefaultVisitor {

	
	private int bp = 0;
	
	/*
	 * Poner aquí los visit necesarios. Si se ha usado VGen, solo hay que copiarlos
	 * de la clase 'visitor/_PlantillaParaVisitors.txt'.
	 */

	// class Program { List<Definicion> definicion; }
	public Object visit(Program node, Object param) {

		// super.visit(node, param);

		int direccionActual = 0;

		if (node.getDefinicion() != null)
			for (Definicion child : node.getDefinicion()) {
				child.accept(this, param);
				if (child instanceof Variable) {
					((Variable) child).setDireccion(direccionActual);
					direccionActual += ((Variable) child).getTipo().getSize();
				}
			}

		return null;
	}

	// class Funcion { String nombre; List<Parametro> parametro; Tipo tipo;
	// List<Variable> variable; List<Sentencia> sentencia; }
	public Object visit(Funcion node, Object param) {

		// super.visit(node, param);
		int direccionParametros = 4;
		int direccionLocales = 0;

		if (node.getParametro() != null)
			for (int i = node.getParametro().size() - 1; i >= 0; i--) {
				node.getParametro().get(i).accept(this, param);
				node.getParametro().get(i).setDireccion(bp + direccionParametros);
				direccionParametros += node.getParametro().get(i).getTipo().getSize();
			}

		if (node.getTipo() != null)
			node.getTipo().accept(this, param);

		if (node.getVariable() != null)
			for (Variable child : node.getVariable()) {
				child.accept(this, param);
				direccionLocales += child.getTipo().getSize();
				child.setDireccion(bp - direccionLocales);
			}

		if (node.getSentencia() != null)
			for (Sentencia child : node.getSentencia())
				child.accept(this, param);

		return null;
	}

	// class Variable { String nombre; Tipo tipo; }
	public Object visit(Variable node, Object param) {

		// super.visit(node, param);

		if (node.getTipo() != null)
			node.getTipo().accept(this, param);

		return null;
	}

	// class Struct { String nombre; List<Campo> campo; }
	public Object visit(Struct node, Object param) {

		// super.visit(node, param);
		int direccion = 0;

		if (node.getCampo() != null)
			for (Campo child : node.getCampo()) {
				child.accept(this, param);
				child.setDireccion(direccion);
				direccion += child.getTipo().getSize();
			}

		return null;
	}

	// class Campo { String nombre; Tipo tipo; }
	public Object visit(Campo node, Object param) {

		// super.visit(node, param);

		if (node.getTipo() != null)
			node.getTipo().accept(this, param);

		return null;
	}

}
