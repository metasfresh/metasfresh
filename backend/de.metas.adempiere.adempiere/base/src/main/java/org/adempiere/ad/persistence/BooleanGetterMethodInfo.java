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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.lang.reflect.Method;

/**
 * Boolean getter handler
 * <p>
 * e.g. org.compiere.model.I_C_Invoice.isProcessed()
 *
 * @author tsa
 */
/* package */class BooleanGetterMethodInfo extends AbstractModelMethodInfo
{

	private final String propertyName;
	private final Class<?> returnType;

	public BooleanGetterMethodInfo(final Method interfaceMethod, final String propertyName)
	{
		super(interfaceMethod);
		this.propertyName = propertyName;
		this.returnType = interfaceMethod.getReturnType();
	}

	@Override
	public Object invoke(final IModelInternalAccessor model, final Object[] methodArgs) throws Exception
	{
		// TODO: optimization: cache matched PropertyName and ColumnIndex

		String propertyNameToUse = propertyName;
		int ii = model.getColumnIndex(propertyNameToUse);
		if (ii >= 0)
		{
			return model.getValue(propertyNameToUse, ii, returnType);
		}

		propertyNameToUse = "Is" + propertyName;
		ii = model.getColumnIndex(propertyNameToUse);
		if (ii >= 0)
		{
			return model.getValue(propertyNameToUse, ii, returnType);
		}

		propertyNameToUse = "is" + propertyName;
		ii = model.getColumnIndex(propertyNameToUse);
		if (ii >= 0)
		{
			return model.getValue(propertyNameToUse, ii, returnType);
		}

		final Method interfaceMethod = getInterfaceMethod();
		if (interfaceMethod.isDefault())
		{
			return model.invokeParent(interfaceMethod, methodArgs);
		}

		//
		throw new IllegalArgumentException("Method " + interfaceMethod + " is not supported on model " + model);
	}

}
