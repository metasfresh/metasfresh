package org.compiere.model.copy;

public enum ValueToCopyType
{
	DIRECT_COPY,
	SET_EXPLICIT_VALUE,
	COMPUTE_DEFAULT,
	COMPUTE_WITH_FUNCTION,
	APPEND_SUFFIX,
	SKIP,
	NOT_SPECIFIED,
	;

	public boolean isDirectCopy() {return DIRECT_COPY.equals(this);}
}
