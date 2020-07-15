package de.metas.adempiere.service.impl;

/*
 * #%L
 * de.metas.swat.base
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


import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.model.GenericPO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.PO;
import org.compiere.model.Query;

import de.metas.cache.annotation.CacheCtx;
import de.metas.cache.annotation.CacheIgnore;

public class ParametersDAO extends AbstractParametersDAO 
{
	@Override
	public IParameterPO newParameterPO(final Object parent, String parameterTable)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(parent);
		final String trxName = InterfaceWrapperHelper.getTrxName(parent);
		
		final PO po = new GenericPO(parameterTable, ctx, 0, trxName);
		final IParameterPO paramPO = InterfaceWrapperHelper.create(po, IParameterPO.class);
		
		final int recordId = InterfaceWrapperHelper.getId(parent);
		final String tableName = InterfaceWrapperHelper.getModelTableName(parent);
		final String parentLinkColumnName = tableName + "_ID";

		InterfaceWrapperHelper.setValue(paramPO, parentLinkColumnName, recordId);

		return paramPO;
	}

	@Override
	@Cached
	public List<IParameterPO> retrieveParamPOs(
			final @CacheCtx Properties ctx,
			final String parentTable, 
			final int parentId, 
			final String parameterTable,
			final @CacheIgnore String trxName)
	{
		final String wc = parentTable + "_ID=?";

		final int[] ids = new Query(ctx, parameterTable, wc, trxName)
						.setParameters(parentId)
						.getIDs();

		final List<IParameterPO> result = new ArrayList<IParameterPO>();

		for (final int id : ids)
		{
			final GenericPO po = new GenericPO(parameterTable, ctx, id, trxName);
			final IParameterPO paramPO = InterfaceWrapperHelper.create(po, IParameterPO.class);
			result.add(paramPO);
		}

		return result;
	}

}
