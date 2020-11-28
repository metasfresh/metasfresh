package org.adempiere.ad.column.autoapplyvalrule;

import java.util.concurrent.ConcurrentHashMap;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Service;

import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Service
@ToString
public class ValRuleAutoApplierService
{
	private final ConcurrentHashMap<String, ValRuleAutoApplier> tableName2Applier = new ConcurrentHashMap<>();

	public void registerApplier(@NonNull final ValRuleAutoApplier valRuleAutoApplier)
	{
		tableName2Applier.put(valRuleAutoApplier.getTableName(), valRuleAutoApplier);
	}

	public void invokeApplierFor(@NonNull final Object recordModel)
	{
		final String tableName = InterfaceWrapperHelper.getModelTableName(recordModel);
		final ValRuleAutoApplier applier = tableName2Applier.get(tableName);
		if (applier == null)
		{
			return; // no applier registered; nothing to do
		}

		try
		{
			applier.handleRecord(recordModel);
		}
		catch (final RuntimeException e)
		{
			throw AdempiereException.wrapIfNeeded(e)
					.appendParametersToMessage()
					.setParameter("valRuleAutoApplier", applier)
					.setParameter("recordModel", recordModel);
		}
	}

	public void unregisterForTableName(@NonNull final String tableName)
	{
		tableName2Applier.remove(tableName);

	}
}
