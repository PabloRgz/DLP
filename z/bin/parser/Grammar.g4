grammar Grammar
	;
import Lexicon
	;

@parser::header{ import ast.*;}

start returns[Program ast]
	: defs EOF {$ast = new Program($defs.list);}
	;

defs returns [List<Definicion> list = new ArrayList<Definicion>()]
	: (def {$list.add($def.ast);})*
	;

def returns[Definicion ast]
	: variable {$variable.ast.setAmbito("global"); $ast = $variable.ast;}
	| struct {$ast = $struct.ast;}
	| funcion {$ast = $funcion.ast;}
	;
	
variables returns [List<Variable> list = new ArrayList<Variable>()]
	: (variable {$variable.ast.setAmbito("local"); $list.add($variable.ast);})*
	;	

variable returns[Variable ast]
	: 'var' IDENT ':' tipo ';' {$ast = new Variable($IDENT, $tipo.ast, "");}
	;  
    

struct returns [Struct ast]
	: 'struct' IDENT '{' campos '}' ';' { $ast = new Struct($IDENT, $campos.list);}
	;

campos returns[List<Campo> list = new ArrayList<Campo>()]
	: (campo{$list.add($campo.ast);})*
	;	
	
campo returns[Campo ast]
	: IDENT ':' tipo ';' {$ast = new Campo($IDENT, $tipo.ast);} 
	;
	
tipo returns[Tipo ast]
	: 'int' { $ast = new IntType(); }
	| 'float' { $ast = new FloatType(); }
	| 'char' { $ast = new CharType(); }
	| IDENT { $ast = new IdentType($IDENT); }
	| '[' INT_CONSTANT ']' tipo  {$ast = new ArrayType($INT_CONSTANT, $tipo.ast);}
	;
	
	
funcion returns[Funcion ast]
	:  IDENT '(' parametros ')' '{' variables sentencias '}' { $ast = new Funcion($IDENT, $parametros.list, null, $variables.list, $sentencias.list); }
	|  IDENT '(' parametros ')' ':' tipo '{' variables sentencias '}' { $ast = new Funcion($IDENT, $parametros.list, $tipo.ast, $variables.list, $sentencias.list); }
	;

parametros returns[List<Parametro> list = new ArrayList<Parametro>()]
	: (parametro{$list.add($parametro.ast);}(','parametro{$list.add($parametro.ast);})*)? 
	;
	
parametro returns[Parametro ast]
	: IDENT ':' tipo { $ast = new Parametro($IDENT, $tipo.ast);}
	;
	
sentencias returns [List<Sentencia> list = new ArrayList<Sentencia>()]
	: (sentencia{$list.add($sentencia.ast);})* 
	;	
	
sentencia returns[Sentencia ast]
	: 'print' expresion ';' { $ast = new Print($expresion.ast, ""); }
	| 'printsp' expresion ';'  { $ast = new Print($expresion.ast, "sp"); }
	| 'println' expresion? ';'  { $ast = new Print($expresion.ast, "ln"); }
	| 'read' expresion ';'  { $ast = new Read($expresion.ast); }
	|  expresion '=' expresion ';'  {$ast = new Asignacion($ctx.expresion(0), $ctx.expresion(1)); }
 	| 'if' '(' expresion ')' '{' sentencias '}'  { $ast = new If($expresion.ast, $sentencias.list, null); }
	| 'if' '(' expresion ')' '{' sentencias '}' 'else' '{' sentencias '}'  { $ast = new If($expresion.ast, $ctx.sentencias(0).list, $ctx.sentencias(1).list); }
	| 'while' '(' expresion ')' '{' sentencias '}'  { $ast = new While( $expresion.ast, $sentencias.list); }
	| IDENT '(' parametros_llamada ')' ';'  { $ast = new LlamadaFuncion($IDENT, $parametros_llamada.list); }
	| 'return' ';'  { $ast = new Return(null); }
	| 'return' expresion ';'  { $ast = new Return($expresion.ast); }  
	;
	
expresion returns[Expresion ast]
	: INT_CONSTANT  {  $ast = new IntConstant($INT_CONSTANT); }
	| REAL_CONSTANT { $ast = new RealConstant($REAL_CONSTANT); }
	| CHAR_CONSTANT { $ast = new CharConstant($CHAR_CONSTANT); }
	| IDENT { $ast = new IdentConstant($IDENT); }
	| '(' expresion ')' { $ast = $expresion.ast; }
	| 'cast' '<' tipo '>' '(' expresion ')' { $ast = new Cast($tipo.ast, $expresion.ast); }
	| '!' expresion { $ast = new Not($expresion.ast); }
	| expresion '[' expresion ']' { $ast = new Array($ctx.expresion(0), $ctx.expresion(1)); }
	| expresion op=('*'|'/') expresion { $ast = new ExpresionMatematica($ctx.expresion(0), $op, $ctx.expresion(1)); }
	| expresion op=('+'|'-') expresion { $ast = new ExpresionMatematica($ctx.expresion(0), $op, $ctx.expresion(1)); }
	| expresion op=('<' | '>' | '<=' | '>=') expresion { $ast = new ExpresionCondicional($ctx.expresion(0), $op, $ctx.expresion(1)); }
	| expresion op=('==' | '!=') expresion { $ast = new ExpresionCondicional($ctx.expresion(0), $op, $ctx.expresion(1)); }
	| expresion '&&' expresion { $ast = new ExpresionLogica($ctx.expresion(0), "&&", $ctx.expresion(1)); }
	| expresion '||' expresion { $ast = new ExpresionLogica($ctx.expresion(0), "||", $ctx.expresion(1)); }
	| expresion '.' IDENT { $ast = new Acceso($ctx.expresion(0), $IDENT); }
	| IDENT '(' parametros_llamada ')' { $ast = new LlamadaFuncionExp($IDENT, $parametros_llamada.list); }
	;
	
parametros_llamada returns[List<Expresion> list = new ArrayList<Expresion>()]
	: (parametro_llamada{$list.add($parametro_llamada.ast);}(','parametro_llamada{$list.add($ctx.parametro_llamada.ast);})*)? 
	;	
	
parametro_llamada returns[Expresion ast]
	: expresion { $ast = $expresion.ast;}
	;	
	
	