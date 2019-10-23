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
 * Model getter handler.
 * 
 * e.g. org.compiere.model.I_C_Invoice.getC_BPartner()
 * 
 * @author tsa
 *
 */
public class ModelGetterMethodInfo extends AbstractModelMethodInfo
{

	private final String valueColumnName;

	/**
	 * 
	 * @param interfaceMethod
	 * @param valueColumnName value column name (e.g. AD_Client_ID, M_Warehouse_ID etc)
	 */
	public ModelGetterMethodInfo(final Method interfaceMethod, final String valueColumnName)
	{
		super(interfaceMethod);

		this.valueColumnName = valueColumnName;
	}

	@Override
	public Object invoke(final IModelInternalAccessor model, final Object[] methodArgs) throws Exception
	{
		return model.getReferencedObject(valueColumnName, getInterfaceMethod());
	}

}
