<<<<<<< HEAD
package de.metas.cache.model;

import java.util.List;

import com.google.common.collect.ImmutableList;

=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
<<<<<<< HEAD
 * 
=======
 *
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
<<<<<<< HEAD
 * 
=======
 *
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

<<<<<<< HEAD
=======
package de.metas.cache.model;

import com.google.common.collect.ImmutableList;
import org.adempiere.model.InterfaceWrapperHelper;

import java.util.List;

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
public final class DirectModelCacheInvalidateRequestFactory implements ModelCacheInvalidateRequestFactory
{
	public static final transient DirectModelCacheInvalidateRequestFactory instance = new DirectModelCacheInvalidateRequestFactory();

	private DirectModelCacheInvalidateRequestFactory()
	{
	}

	@Override
	public List<CacheInvalidateRequest> createRequestsFromModel(
			final ICacheSourceModel model,
			final ModelCacheInvalidationTiming timing_NOTUSED)
	{
		final int recordId = model.getRecordId();
<<<<<<< HEAD
		if (recordId < 0)
=======
		final String keyColumnName = InterfaceWrapperHelper.getKeyColumnName(model.getTableName());
		if (recordId < InterfaceWrapperHelper.getFirstValidIdByColumnName(keyColumnName))
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		{
			return ImmutableList.of();
		}

		final String tableName = model.getTableName();
		return ImmutableList.of(CacheInvalidateRequest.rootRecord(tableName, recordId));
	}
}
