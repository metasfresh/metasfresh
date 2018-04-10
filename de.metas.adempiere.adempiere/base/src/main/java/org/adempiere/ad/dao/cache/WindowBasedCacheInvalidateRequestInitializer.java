package org.adempiere.ad.dao.cache;

import java.util.Set;

import org.adempiere.util.Services;
import org.compiere.util.CacheMgt;
import org.compiere.util.DB;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.logging.LogManager;

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

public class WindowBasedCacheInvalidateRequestInitializer
{
	public static final transient WindowBasedCacheInvalidateRequestInitializer instance = new WindowBasedCacheInvalidateRequestInitializer();

	private static final Logger logger = LogManager.getLogger(WindowBasedCacheInvalidateRequestInitializer.class);

	private WindowBasedCacheInvalidateRequestInitializer()
	{
	}

	public void initialize()
	{
		final IModelCacheInvalidationService registry = Services.get(IModelCacheInvalidationService.class);
		final CacheMgt cacheMgt = CacheMgt.get();

		final Set<GenericModelCacheInvalidateRequestFactory> factories = retrieveFactories();
		logger.info("Found {} factories to be registered", factories.size());

		factories.forEach(factory -> {
			registry.register(factory.getRootTableName(), DirectModelCacheInvalidateRequestFactory.instance);
			cacheMgt.enableRemoteCacheInvalidationForTableName(factory.getRootTableName());

			registry.register(factory.getChildTableName(), factory);
			cacheMgt.enableRemoteCacheInvalidationForTableName(factory.getChildTableName());
		});
	}

	private final Set<GenericModelCacheInvalidateRequestFactory> retrieveFactories()
	{
		final ImmutableSet.Builder<GenericModelCacheInvalidateRequestFactory> factories = ImmutableSet.builder();
		DB.forEachRow(
				"select * from AD_Window_ParentChildTableNames_v1 where ChildLinkColumnName is not null",
				ImmutableList.of(),
				rs -> factories.add(GenericModelCacheInvalidateRequestFactory.builder()
						.rootTableName(rs.getString("ParentTableName"))
						.childTableName(rs.getString("ChildTableName"))
						.childKeyColumnName(rs.getString("ChildKeyColumnName"))
						.childLinkColumnName(rs.getString("ChildLinkColumnName"))
						.build()));

		return factories.build();
	}

}
