package de.metas.adempiere.service;

import de.metas.util.ISingletonService;

public interface IVariableParserBL extends ISingletonService
{
	public static final Object NOT_RESOLVED = new Object();

	public Object resolveVariable(String variable, Object model, Object defaultValue);
}
