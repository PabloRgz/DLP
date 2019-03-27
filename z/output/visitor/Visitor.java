/**
 * @generated VGen (for ANTLR) 1.4.0
 */

package visitor;

import ast.*;

public interface Visitor {
	public Object visit(Program node, Object param);
	public Object visit(Variable node, Object param);
	public Object visit(Struct node, Object param);
	public Object visit(Funcion node, Object param);
	public Object visit(Campo node, Object param);
	public Object visit(Parametro node, Object param);
	public Object visit(IntType node, Object param);
	public Object visit(FloatType node, Object param);
	public Object visit(CharType node, Object param);
	public Object visit(IdentType node, Object param);
	public Object visit(ArrayType node, Object param);
	public Object visit(Print node, Object param);
	public Object visit(Read node, Object param);
	public Object visit(Asignacion node, Object param);
	public Object visit(If node, Object param);
	public Object visit(While node, Object param);
	public Object visit(LlamadaFuncion node, Object param);
	public Object visit(Return node, Object param);
	public Object visit(IntConstant node, Object param);
	public Object visit(RealConstant node, Object param);
	public Object visit(CharConstant node, Object param);
	public Object visit(IdentConstant node, Object param);
	public Object visit(Array node, Object param);
	public Object visit(Cast node, Object param);
	public Object visit(ExpresionMatematica node, Object param);
	public Object visit(ExpresionLogica node, Object param);
	public Object visit(ExpresionCondicional node, Object param);
	public Object visit(Not node, Object param);
	public Object visit(Acceso node, Object param);
	public Object visit(LlamadaFuncionExp node, Object param);
}
