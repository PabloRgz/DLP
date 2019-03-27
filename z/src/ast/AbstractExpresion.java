/**
 * @generated VGen (for ANTLR) 1.4.0
 */

package ast;

public abstract class AbstractExpresion extends AbstractAST implements Expresion {

	Tipo tipo;
	boolean modificable;

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public boolean isModificable() {
		return modificable;
	}

	public void setModificable(boolean modificable) {
		this.modificable = modificable;
	}

}
