package de.metas.cache.model;

import java.util.List;

import org.adempiere.ad.table.api.ColumnSqlSourceDescriptor;
import org.adempiere.ad.table.api.IADTableDAO;
import org.slf4j.Logger;

import de.metas.cache.CacheMgt;
import de.metas.logging.LogManager;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

public class ColumnSqlCacheInvalidateRequestInitializer
{
	public static void setup()
	{
		new ColumnSqlCacheInvalidateRequestInitializer().initialize();
	}

	private static final Logger logger = LogManager.getLogger(ColumnSqlCacheInvalidateRequestInitializer.class);
	private final IModelCacheInvalidationService registry = Services.get(IModelCacheInvalidationService.class);
	private final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
	private final CacheMgt cacheMgt = CacheMgt.get();

	private ColumnSqlCacheInvalidateRequestInitializer()
	{
	}

	private void initialize()
	{
		final List<ColumnSqlSourceDescriptor> descriptors = adTableDAO.retrieveColumnSqlSourceDescriptors();
		logger.info("Found {} descriptors to be registered", descriptors.size());

		for (final ColumnSqlSourceDescriptor descriptor : descriptors)
		{
			register(descriptor);
		}
	}

	private void register(final ColumnSqlSourceDescriptor descriptor)
	{
		cacheMgt.enableRemoteCacheInvalidationForTableName(descriptor.getSourceTableName());
		cacheMgt.enableRemoteCacheInvalidationForTableName(descriptor.getTargetTableName());

		registry.register(descriptor.getSourceTableName(), ColumnSqlCacheInvalidateRequestFactory.builder()
				.targetTableName(descriptor.getTargetTableName())
				.sqlToGetTargetRecordIdBySourceRecordId(descriptor.getSqlToGetTargetRecordIdBySourceRecordId())
				.build());
	}
}
