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
import java.util.Optional;
import java.util.Properties;

import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.InterfaceWrapperHelper;

public class PlainParametersDAO extends AbstractParametersDAO
{
	@Override
	public IParameterPO newParameterPO(final Object parent, final String parameterTable)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(parent);
		final String trxName = InterfaceWrapperHelper.getTrxName(parent);

		final IParameterPO paramPO = POJOWrapper.create(ctx, parameterTable, IParameterPO.class, trxName);

		final int recordId = InterfaceWrapperHelper.getId(parent);
		final String tableName = InterfaceWrapperHelper.getModelTableName(parent);
		final String parentLinkColumnName = tableName + "_ID";

		// We need to set it directly in map, because else it won't be recognized as an available attribute (hasColumnName method)
		POJOWrapper.getWrapper(paramPO).setValue(parentLinkColumnName, recordId);

		return paramPO;
	}

	@Override
	public List<IParameterPO> retrieveParamPOs(final Properties ctx, final String parentTable, final int parentId, final String parameterTable, final String trxName)
	{
		final String parentLinkColumnName = parentTable + "_ID";

		final List<IParameterPO> paramPOs = new ArrayList<>();

		final List<Object> rawRecords = POJOLookupMap.get().getRawRecords(parameterTable);
		for (final Object rawRecord : rawRecords)
		{
			final IParameterPO paramPO = InterfaceWrapperHelper.create(rawRecord, IParameterPO.class);

			if (!POJOWrapper.getWrapper(paramPO).hasColumnName(parentLinkColumnName))
			{
				continue;
			}

			final Optional<Integer> currentParentId = InterfaceWrapperHelper.getValue(paramPO, parentLinkColumnName);
			if (!currentParentId.isPresent())
			{
				continue;
			}

			if (currentParentId.get() != parentId)
			{
				continue;
			}

			paramPOs.add(paramPO);
		}

		return paramPOs;
	}

}
