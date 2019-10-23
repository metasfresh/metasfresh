package org.adempiere.ad.persistence;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.lang.reflect.Method;

/**
 * Model setter handler
 * 
 * e.g. org.compiere.model.I_C_Invoice.setC_BPartner(I_C_BPartner bpartner)
 * 
 * @author tsa
 *
 */
/*package*/class ModelSetterMethodInfo extends AbstractModelMethodInfo
{
	private final Class<?> parameterType;

	private final String idPropertyName;

	public ModelSetterMethodInfo(final Method interfaceMethod, final Class<?> parameterType, final String idPropertyName)
	{
		super(interfaceMethod);

		this.parameterType = parameterType;
		this.idPropertyName = idPropertyName;
	}

	@Override
	public Object invoke(final IModelInternalAccessor model, final Object[] methodArgs)
	{
		final Object value = methodArgs[0];
		model.setValueFromPO(idPropertyName, parameterType, value);
		return null;
	}
}
