/**
 * @generated VGen (for ANTLR) 1.4.0
 */

package visitor;

import ast.*;
import java.util.*;

/*
DefaultVisitor. Implementación base del visitor para ser derivada por nuevos visitor.
	No modificar esta clase. Para crear nuevos visitor usar el fichero "_PlantillaParaVisitors.txt".
	DefaultVisitor ofrece una implementación por defecto de cada nodo que se limita a visitar los nodos hijos.
*/
public class DefaultVisitor implements Visitor {

	//	class Program { List<Definicion> definicion; }
	public Object visit(Program node, Object param) {
		visitChildren(node.getDefinicion(), param);
		return null;
	}

	//	class Variable { String nombre;  Tipo tipo;  String ambito; }
	public Object visit(Variable node, Object param) {
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		return null;
	}

	//	class Struct { String nombre;  List<Campo> campo; }
	public Object visit(Struct node, Object param) {
		visitChildren(node.getCampo(), param);
		return null;
	}

	//	class Funcion { String nombre;  List<Parametro> parametro;  Tipo tipo;  List<Variable> variable;  List<Sentencia> sentencia; }
	public Object visit(Funcion node, Object param) {
		visitChildren(node.getParametro(), param);
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		visitChildren(node.getVariable(), param);
		visitChildren(node.getSentencia(), param);
		return null;
	}

	//	class Campo { String nombre;  Tipo tipo; }
	public Object visit(Campo node, Object param) {
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		return null;
	}

	//	class Parametro { String nombre;  Tipo tipo; }
	public Object visit(Parametro node, Object param) {
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		return null;
	}

	//	class IntType {  }
	public Object visit(IntType node, Object param) {
		return null;
	}

	//	class FloatType {  }
	public Object visit(FloatType node, Object param) {
		return null;
	}

	//	class CharType {  }
	public Object visit(CharType node, Object param) {
		return null;
	}

	//	class IdentType { String nombre; }
	public Object visit(IdentType node, Object param) {
		return null;
	}

	//	class ArrayType { String posicion;  Tipo tipo; }
	public Object visit(ArrayType node, Object param) {
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		return null;
	}

	//	class Print { Expresion exp; }
	public Object visit(Print node, Object param) {
		if (node.getExp() != null)
			node.getExp().accept(this, param);
		return null;
	}

	//	class Read { Expresion expr; }
	public Object visit(Read node, Object param) {
		if (node.getExpr() != null)
			node.getExpr().accept(this, param);
		return null;
	}

	//	class Asignacion { Expresion izq;  Expresion dcha; }
	public Object visit(Asignacion node, Object param) {
		if (node.getIzq() != null)
			node.getIzq().accept(this, param);
		if (node.getDcha() != null)
			node.getDcha().accept(this, param);
		return null;
	}

	//	class If { Expresion condicion;  List<Sentencia> verdadero;  List<Sentencia> falso; }
	public Object visit(If node, Object param) {
		if (node.getCondicion() != null)
			node.getCondicion().accept(this, param);
		visitChildren(node.getVerdadero(), param);
		visitChildren(node.getFalso(), param);
		return null;
	}

	//	class While { Expresion condicion;  List<Sentencia> sentencia; }
	public Object visit(While node, Object param) {
		if (node.getCondicion() != null)
			node.getCondicion().accept(this, param);
		visitChildren(node.getSentencia(), param);
		return null;
	}

	//	class LlamadaFuncion { String nombreFuncion;  List<Expresion> parametrosOpcionales; }
	public Object visit(LlamadaFuncion node, Object param) {
		visitChildren(node.getParametrosOpcionales(), param);
		return null;
	}

	//	class Return { Expresion retorno; }
	public Object visit(Return node, Object param) {
		if (node.getRetorno() != null)
			node.getRetorno().accept(this, param);
		return null;
	}

	//	class IntConstant { String valor; }
	public Object visit(IntConstant node, Object param) {
		return null;
	}

	//	class RealConstant { String valor; }
	public Object visit(RealConstant node, Object param) {
		return null;
	}

	//	class CharConstant { String valor; }
	public Object visit(CharConstant node, Object param) {
		return null;
	}

	//	class IdentConstant { String valor; }
	public Object visit(IdentConstant node, Object param) {
		return null;
	}

	//	class Array { Expresion nombreArray;  Expresion valor; }
	public Object visit(Array node, Object param) {
		if (node.getNombreArray() != null)
			node.getNombreArray().accept(this, param);
		if (node.getValor() != null)
			node.getValor().accept(this, param);
		return null;
	}

	//	class Acceso { Expresion nombre;  String string; }
	public Object visit(Acceso node, Object param) {
		if (node.getNombre() != null)
			node.getNombre().accept(this, param);
		return null;
	}

	//	class Cast { Tipo tipo;  Expresion valor; }
	public Object visit(Cast node, Object param) {
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		if (node.getValor() != null)
			node.getValor().accept(this, param);
		return null;
	}

	//	class ExpresionMatematica { Expresion izq;  String operador;  Expresion dcha; }
	public Object visit(ExpresionMatematica node, Object param) {
		if (node.getIzq() != null)
			node.getIzq().accept(this, param);
		if (node.getDcha() != null)
			node.getDcha().accept(this, param);
		return null;
	}

	//	class ExpresionLogica { Expresion izq;  String operador;  Expresion dcha; }
	public Object visit(ExpresionLogica node, Object param) {
		if (node.getIzq() != null)
			node.getIzq().accept(this, param);
		if (node.getDcha() != null)
			node.getDcha().accept(this, param);
		return null;
	}

	//	class ExpresionCondicional { Expresion izq;  String operador;  Expresion dcha; }
	public Object visit(ExpresionCondicional node, Object param) {
		if (node.getIzq() != null)
			node.getIzq().accept(this, param);
		if (node.getDcha() != null)
			node.getDcha().accept(this, param);
		return null;
	}

	//	class Not { Expresion expresion; }
	public Object visit(Not node, Object param) {
		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		return null;
	}

	//	class LlamadaFuncionExp { String nombreFuncion;  List<Expresion> parametrosOpcionales; }
	public Object visit(LlamadaFuncionExp node, Object param) {
		visitChildren(node.getParametrosOpcionales(), param);
		return null;
	}

    // Método auxiliar -----------------------------
    protected void visitChildren(List<? extends AST> children, Object param) {
        if (children != null)
            for (AST child : children)
                child.accept(this, param);
    }
}
