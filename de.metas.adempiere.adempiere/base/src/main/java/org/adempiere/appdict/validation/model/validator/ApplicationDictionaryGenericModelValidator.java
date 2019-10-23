package org.adempiere.appdict.validation.model.validator;

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


import org.adempiere.appdict.validation.spi.IADValidator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;

public class ApplicationDictionaryGenericModelValidator<T> implements ModelValidator
{
	private final Class<T> itemClass;
	private final IADValidator<T> validator;
	private final String tableName;

	private int adClientId = -1;

	public ApplicationDictionaryGenericModelValidator(final Class<T> itemClass, final IADValidator<T> validator)
	{
		this.itemClass = itemClass;
		this.validator = validator;
		this.tableName = InterfaceWrapperHelper.getTableName(itemClass);
	}

	@Override
	public void initialize(ModelValidationEngine engine, MClient client)
	{
		adClientId = client == null ? -1 : client.getAD_Client_ID();
		engine.addModelChange(tableName, this);
	}

	@Override
	public int getAD_Client_ID()
	{
		return adClientId;
	}

	@Override
	public String login(int AD_Org_ID, int AD_Role_ID, int AD_User_ID)
	{
		return null;
	}

	@Override
	public String modelChange(PO po, int type) throws Exception
	{
		if (type == TYPE_BEFORE_NEW || type == TYPE_BEFORE_CHANGE)
		{
			final T item = InterfaceWrapperHelper.create(po, itemClass);
			validator.validate(item);
		}
		return null;
	}

	@Override
	public String docValidate(PO po, int timing)
	{
		return null;
	}
}
