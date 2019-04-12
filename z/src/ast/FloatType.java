/**
 * @generated VGen (for ANTLR) 1.4.0
 */

package ast;

import visitor.*;

import org.antlr.v4.runtime.*;

//	floatType:tipo -> 

public class FloatType extends AbstractTipo {

	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	}

	public String toString() {
		return "{FloatType}";
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	public char getSuffix() {
		return 'f';
	}

	@Override
	public String getMAPLName() {
		return "float";
	}
}
