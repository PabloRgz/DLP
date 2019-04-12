package codegeneration;

import java.io.*;
import java.util.*;

import ast.*;
import visitor.*;

enum CodeFunction {
	ADDRESS, VALUE
}

public class CodeSelection extends DefaultVisitor {

	private Map<String, String> instrucciones = new HashMap<String, String>();

	public CodeSelection(Writer writer, String sourceFile) {
		this.writer = new PrintWriter(writer);
		this.sourceFile = sourceFile;

		instrucciones.put("+", "add");
		instrucciones.put("-", "sub");
		instrucciones.put("*", "mul");
		instrucciones.put("/", "div");
		instrucciones.put("<", "lt");
		instrucciones.put("<=", "le");
		instrucciones.put(">", "gt");
		instrucciones.put(">=", "ge");
		instrucciones.put("==", "eq");
		instrucciones.put("!=", "ne");
		instrucciones.put("&&", "and");
		instrucciones.put("||", "or");

	}

	// class Program { List<Definicion> definicion; }
	public Object visit(Program node, Object param) {

		// super.visit(node, param);
		out("#source \"" + sourceFile + "\"");
		if (node.getDefinicion() != null)
			for (Definicion child : node.getDefinicion())
				child.accept(this, param);
		out("halt");
		return null;
	}

	// class Variable { String nombre; Tipo tipo; }
	public Object visit(Variable node, Object param) {

		out("#GLOBAL " + node.getNombre() + ":" + node.getTipo().getMAPLName());
		return null;
	}

	// class Struct { String nombre; List<Campo> campo; }
	public Object visit(Struct node, Object param) {

		// super.visit(node, param);
		out("#TYPE" + node.getNombre() + ":" + "{ ");
		if (node.getCampo() != null) {
			for (Campo child : node.getCampo()) {
				child.accept(this, param);
				out(child.getNombre() + ":" + child.getTipo().getMAPLName());
			}
		}

		return null;
	}

	// class Funcion { String nombre; List<Parametro> parametro; Tipo tipo;
	// List<Variable> variable; List<Sentencia> sentencia; }
	public Object visit(Funcion node, Object param) {

		// super.visit(node, param);

		int vLocal = 0;
		
		for (Variable v : node.getVariable()) {
			vLocal += v.getTipo().getSize();
		}
		
		for(Parametro p: node.getParametro()) {
			out("#param" + p.getNombre() + p.getTipo().getMAPLName());
		}

		out(node.getNombre() + ":");
		out("#func " + node.getNombre());

		if (node.getParametro() != null) {
			for (Parametro child : node.getParametro()) {
				child.accept(this, param);
			}
		}

		if (node.getTipo() != null)
			node.getTipo().accept(this, param);

		if (node.getVariable() != null) {
			for (Variable child : node.getVariable()) {
				child.accept(this, param);
				out("#local" + child.getNombre() + ":" + child.getTipo().getMAPLName());
			}
		}

		if (node.getSentencia() != null)
			for (Sentencia child : node.getSentencia())
				child.accept(this, param);

		return null;
	}

	// class Parametro { String nombre; Tipo tipo; }
	public Object visit(Parametro node, Object param) {

		// super.visit(node, param);

		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		
		return null;
	}

	// class If { Expresion condicion; List<Sentencia> verdadero; List<Sentencia>
	// falso; }
	public Object visit(If node, Object param) {

		// super.visit(node, param);

		if (node.getCondicion() != null)
			node.getCondicion().accept(this, param);

		if (node.getVerdadero() != null)
			for (Sentencia child : node.getVerdadero())
				child.accept(this, param);

		if (node.getFalso() != null)
			for (Sentencia child : node.getFalso())
				child.accept(this, param);

		return null;
	}

	// class Return { Expresion retorno; }
	public Object visit(Return node, Object param) {

		// super.visit(node, param);

		int vlocales = 0;
		int parametros = 0;

		for (Parametro p : node.getFuncion().getParametro()) {
			parametros += p.getTipo().getSize();
		}

		for (Variable v : node.getFuncion().getVariable()) {
			vlocales += v.getTipo().getSize();
		}

		if (node.getRetorno() != null)
			node.getRetorno().accept(this, CodeFunction.VALUE);

		// Ret valorRetorno, locales, parametros
		out("ret " + node.getRetorno().getTipo().getSize() + ", " + vlocales + ", " + parametros);

		return null;
	}

	// class IntConstant { String valor; }
	public Object visit(IntConstant node, Object param) {
		assert (param == CodeFunction.VALUE);
		out("push " + node.getValor());
		return null;
	}

	// class RealConstant { String valor; }
	public Object visit(RealConstant node, Object param) {
		assert (param == CodeFunction.VALUE);
		out("pushf " + node.getValor());
		return null;
	}

	// class CharConstant { String valor; }
	public Object visit(CharConstant node, Object param) {
		assert (param == CodeFunction.VALUE);
		out("pushb " + node.getValor());
		return null;
	}

	// class IdentConstant { String valor; }
	public Object visit(IdentConstant node, Object param) {
		return null;
	}

	// class Array { Expresion nombreArray; Expresion valor; }
	public Object visit(Array node, Object param) {

		// super.visit(node, param);

		if (node.getNombreArray() != null)
			node.getNombreArray().accept(this, param);

		if (node.getValor() != null)
			node.getValor().accept(this, param);

		return null;
	}

	// class Cast { Tipo tipo; Expresion valor; }
	public Object visit(Cast node, Object param) {

		// super.visit(node, param);

		if (node.getTipo() != null)
			node.getTipo().accept(this, param);

		if (node.getValor() != null)
			node.getValor().accept(this, param);

		out(node.getValor().getTipo().getSuffix() + "2" + node.getTipo().getSuffix());
		return null;
	}

	// class ExpresionMatematica { Expresion izq; String operador; Expresion dcha; }
	public Object visit(ExpresionMatematica node, Object param) {

		// super.visit(node, param);

		if (node.getIzq() != null)
			node.getIzq().accept(this, param);

		if (node.getDcha() != null)
			node.getDcha().accept(this, param);

		out(instrucciones.get(node.getOperador()) + node.getIzq().getTipo().getSuffix());
		return null;
	}

	// class ExpresionLogica { Expresion izq; String operador; Expresion dcha; }
	public Object visit(ExpresionLogica node, Object param) {

		// super.visit(node, param);

		if (node.getIzq() != null)
			node.getIzq().accept(this, param);

		if (node.getDcha() != null)
			node.getDcha().accept(this, param);

		out(instrucciones.get(node.getOperador()));
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

		out(instrucciones.get(node.getOperador()) + node.getIzq().getTipo().getSuffix());
		return null;
	}

	// class Not { Expresion expresion; }
	public Object visit(Not node, Object param) {

		// super.visit(node, param);

		if (node.getExpresion() != null)
			node.getExpresion().accept(this, CodeFunction.VALUE);

		out("not ");
		return null;
	}

	// class Acceso { Expresion nombre; String string; }
	public Object visit(Acceso node, Object param) {

		// super.visit(node, param);

		if (node.getNombre() != null) {
			node.getNombre().accept(this, param);
			Tipo tipo = node.getNombre().getTipo();
			out("push " + node);
		}

		return null;
	}

	// class LlamadaFuncionExp { String nombreFuncion; List<Expresion>
	// parametrosOpcionales; }
	public Object visit(LlamadaFuncionExp node, Object param) {

		// super.visit(node, param);

		if (node.getParametrosOpcionales() != null)
			for (Expresion child : node.getParametrosOpcionales())
				child.accept(this, CodeFunction.VALUE);
		out("call " + node.getNombreFuncion());
		return null;
	}

	// Método auxiliar recomendado (opcional) -------------
	private void out(String instruction) {
		writer.println(instruction);
	}

	private PrintWriter writer;
	private String sourceFile;
}
