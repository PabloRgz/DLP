package codegeneration;

import java.io.*;
import java.io.ObjectOutputStream.PutField;
import java.util.*;

import ast.*;
import visitor.*;

enum CodeFunction {
	ADDRESS, VALUE
}

public class CodeSelection extends DefaultVisitor {

	private Map<String, String> instrucciones = new HashMap<String, String>();
	private int contadorIf = 0;
	private int contadorWhile = 0;

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
		out("call main");
		out("halt");
		if (node.getDefinicion() != null)
			for (Definicion child : node.getDefinicion())
				child.accept(this, param);
		return null;
	}

	// class Campo { String nombre; Tipo tipo; }
	public Object visit(Campo node, Object param) {

		// super.visit(node, param);

		out("push " + node.getDireccion());
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
		out("}");
		return null;
	}

	// class Funcion { String nombre; List<Parametro> parametro; Tipo tipo;
	// List<Variable> variable; List<Sentencia> sentencia; }
	public Object visit(Funcion node, Object param) {

		// super.visit(node, param);

		int vLocal = 0;
		int tparametros = 0;

		out("#line " + node.getStart().getLine());
		//out("#func " + node.getNombre());
		out(node.getNombre() + ":");
		for (Variable v : node.getVariable()) {
			vLocal += v.getTipo().getSize();
		}

		for (Parametro p : node.getParametro()) {
			tparametros += p.getTipo().getSize();
			//out("#param " + p.getNombre() + p.getTipo().getMAPLName());
		}
		out("enter " + vLocal);

		if (node.getSentencia() != null)
			for (Sentencia child : node.getSentencia())
				child.accept(this, param);

		if (node.getTipo() == null)
			out("ret 0, " + vLocal + ", " + tparametros);

		return null;
	}

	// class Parametro { String nombre; Tipo tipo; }
	public Object visit(Parametro node, Object param) {

		// super.visit(node, param);

		if (node.getTipo() != null)
			node.getTipo().accept(this, param);

		return null;
	}

	// class Print { Expresion exp; }
	public Object visit(Print node, Object param) {

		// super.visit(node, param);

		out("#line " + node.getEnd().getLine());
		if (node.getTipoPrint().equals("")) {
			node.getExp().accept(this, CodeFunction.VALUE);
			out("out" + node.getExp().getTipo().getSuffix());
		} else if (node.getTipoPrint().equals("sp")) {
			node.getExp().accept(this, CodeFunction.VALUE);
			out("out" + node.getExp().getTipo().getSuffix());
			out("pushb 32");
			out("outb");
		} else if (node.getTipoPrint().equals("ln") && node.getExp() != null) {
			node.getExp().accept(this, CodeFunction.VALUE);
			out("out" + node.getExp().getTipo().getSuffix());
			out("pushb 10");
			out("outb");
		} else if (node.getTipoPrint().equals("ln") && node.getExp() == null) {
			out("pushb 10");
			out("outb");
		}

		return null;
	}

	// class Read { Expresion expr; }
	public Object visit(Read node, Object param) {

		// super.visit(node, param);
		out("#line " + node.getEnd().getLine());
		node.getExpr().accept(this, CodeFunction.ADDRESS);
		out("in" + node.getExpr().getTipo().getSuffix());
		out("store" + node.getExpr().getTipo().getSuffix());

		return null;
	}

	// class Asignacion { Expresion izq; Expresion dcha; }
	public Object visit(Asignacion node, Object param) {

		// super.visit(node, param);

		out("#line " + node.getEnd().getLine());
		node.getIzq().accept(this, CodeFunction.ADDRESS);
		node.getDcha().accept(this, CodeFunction.VALUE);
		out("store" + node.getIzq().getTipo().getSuffix());

		return null;
	}

	// class If { Expresion condicion; List<Sentencia> verdadero; List<Sentencia>
	// falso; }
	public Object visit(If node, Object param) {

		// super.visit(node, param);

		int contador = this.contadorIf;

		out("#line " + node.getStart().getLine());
		contadorIf++;
		node.getCondicion().accept(this, CodeFunction.VALUE);
		if (node.getFalso() != null) {
			out("jz else" + contador);
		} else {
			out("jz finIf" + contador);
		}
		for (int i = 0; i < node.getVerdadero().size(); i++) {
			node.getVerdadero().get(i).accept(this, param);
			if (i == node.getVerdadero().size() - 1 && !(node.getVerdadero().get(i) instanceof Return)) {
				out("jmp finIf" + contador);
			}
		}
		if (node.getFalso() != null) {
			out("else" + contador + ":");
			for (Sentencia s : node.getFalso()) {
				s.accept(this, param);
			}
		}
		out("finIf" + contador + ":");

		return null;
	}

	// class While { Expresion condicion; List<Sentencia> sentencia; }
	public Object visit(While node, Object param) {

		// super.visit(node, param);

		int contador = this.contadorWhile;

		out("#line " + node.getStart().getLine());
		contadorWhile++;
		out("while" + contador + ":");
		node.getCondicion().accept(this, CodeFunction.VALUE);
		out("jz finWhile" + contador);
		visitChildren(node.getSentencia(), param);
		out("jmp while" + contador);
		out("finWhile" + contador + ":");
		return null;
	}

	// class LlamadaFuncion { String nombreFuncion; List<Expresion>
	// parametrosOpcionales; }
	public Object visit(LlamadaFuncion node, Object param) {

		// super.visit(node, param);

		out("#line " + node.getEnd().getLine());
		if (node.getParametrosOpcionales() != null)
			for (Expresion child : node.getParametrosOpcionales())
				child.accept(this, CodeFunction.VALUE);
		out("call " + node.getNombreFuncion());
		if (node.getFuncion().getTipo() != null) {
			out("pop");
		}
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

		// Ret valorRetorno, locales, parametros
		if (node.getRetorno() != null) {
			node.getRetorno().accept(this, CodeFunction.VALUE);
			out("ret " + node.getRetorno().getTipo().getSize() + ", " + vlocales + ", " + parametros);
		}

		else {
			out("ret 0" + ", " + vlocales + ", " + parametros);
		}

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
		if ("'\\n'".equals(node.getValor()))
			out("pushb 10");
		else {
			out("pushb " + (int) node.getValor().charAt(1));
		}
		return null;
	}

	// class IdentConstant { String valor; }
	public Object visit(IdentConstant node, Object param) {

		if (CodeFunction.ADDRESS.equals(param)) {
			if (node.getDefinicion().getAmbito().equals("global")) {
				out("pusha " + node.getDefinicion().getDireccion());
			} else if (node.getDefinicion().getAmbito().equals("local")) {
				out("pusha BP");
				out("push " + node.getDefinicion().getDireccion());
				out("add");
			} else if (node.getDefinicion().getAmbito().equals("parametro")) {
				out("pusha BP");
				out("push " + node.getDefinicion().getDireccion());
				out("add");
			}
		}
		if (CodeFunction.VALUE.equals(param)) {
			visit(node, CodeFunction.ADDRESS);
			out("load" + node.getDefinicion().getTipo().getSuffix());
		}
		return null;
	}

	// class Array { Expresion nombreArray; Expresion valor; }
	public Object visit(Array node, Object param) {

		// super.visit(node, param);

		node.getNombreArray().accept(this, CodeFunction.ADDRESS);
		out("push " + ((ArrayType) (node.getNombreArray().getTipo())).getTipo().getSize());

		node.getValor().accept(this, CodeFunction.VALUE);
		out("mul");
		out("add");

		if (CodeFunction.VALUE.equals(param)) {
			out("load " + ((ArrayType) (node.getNombreArray().getTipo())).getTipo().getSuffix());
		}
		return null;
	}

	// class Cast { Tipo tipo; Expresion valor; }
	public Object visit(Cast node, Object param) {

		// super.visit(node, param);

		if (node.getTipo() != null)
			node.getTipo().accept(this, param);

		if (node.getValor() != null)
			node.getValor().accept(this, CodeFunction.VALUE);

		out(node.getValor().getTipo().getSuffix() + "2" + node.getTipo().getSuffix());
		return null;
	}

	// class ExpresionMatematica { Expresion izq; String operador; Expresion dcha; }
	public Object visit(ExpresionMatematica node, Object param) {

		// super.visit(node, param);

		if (node.getIzq() != null)
			node.getIzq().accept(this, CodeFunction.VALUE);

		if (node.getDcha() != null)
			node.getDcha().accept(this, CodeFunction.VALUE);

		out(instrucciones.get(node.getOperador()) + node.getIzq().getTipo().getSuffix());
		return null;
	}

	// class ExpresionLogica { Expresion izq; String operador; Expresion dcha; }
	public Object visit(ExpresionLogica node, Object param) {

		// super.visit(node, param);

		if (node.getIzq() != null)
			node.getIzq().accept(this, CodeFunction.VALUE);

		if (node.getDcha() != null)
			node.getDcha().accept(this, CodeFunction.VALUE);

		out(instrucciones.get(node.getOperador()));
		return null;
	}

	// class ExpresionCondicional { Expresion izq; String operador; Expresion dcha;
	// }
	public Object visit(ExpresionCondicional node, Object param) {

		// super.visit(node, param);

		if (node.getIzq() != null)
			node.getIzq().accept(this, CodeFunction.VALUE);

		if (node.getDcha() != null)
			node.getDcha().accept(this, CodeFunction.VALUE);

		out(instrucciones.get(node.getOperador()) + node.getIzq().getTipo().getSuffix());
		return null;
	}

	// class Not { Expresion expresion; }
	public Object visit(Not node, Object param) {

		// super.visit(node, param);

		if (node.getExpresion() != null)
			node.getExpresion().accept(this, CodeFunction.VALUE);

		out("not");
		return null;
	}

	// class Acceso { Expresion nombre; String string; }
	public Object visit(Acceso node, Object param) {

		// super.visit(node, param);

		if (CodeFunction.VALUE.equals(param)) {
			node.accept(this, CodeFunction.ADDRESS);
			out("load" + node.getTipo().getSuffix());
		} else {
			node.getNombre().accept(this, CodeFunction.ADDRESS);
			List<Campo> listaCampos = ((IdentType) (node.getNombre()).getTipo()).getDefinicion().getCampo();
			for (Campo c : listaCampos) {
				if (c.getNombre().equals(node.getString())) {
					out("push " + c.getDireccion());
				}
			}
			out("add");
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
