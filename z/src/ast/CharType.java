/**
 * @generated VGen (for ANTLR) 1.4.0
 */

package ast;

import visitor.*;

import org.antlr.v4.runtime.*;

//	charType:tipo -> 

public class CharType extends AbstractTipo {

	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	}

	public String toString() {
		return "{CharType}";
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public char getSuffix() {
		return 'b';
	}

	@Override
	public String getMAPLName() {
		return "char";
	}
}
