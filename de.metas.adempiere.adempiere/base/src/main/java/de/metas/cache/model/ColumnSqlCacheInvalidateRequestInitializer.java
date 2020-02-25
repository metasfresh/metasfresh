package de.metas.cache.model;

import java.util.Set;

import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableSet;

import de.metas.cache.CacheMgt;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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

	@Value
	@Builder
	private static class ColumnSqlSourceDescriptor
	{
		@NonNull
		String targetTableName;

		@NonNull
		String sourceTableName;

		@NonNull
		String sqlToGetTargetRecordIdBySourceRecordId;
	}

	private static final Logger logger = LogManager.getLogger(ColumnSqlCacheInvalidateRequestInitializer.class);
	private final IModelCacheInvalidationService registry = Services.get(IModelCacheInvalidationService.class);
	private final CacheMgt cacheMgt = CacheMgt.get();

	private ColumnSqlCacheInvalidateRequestInitializer()
	{
	}

	private void initialize()
	{
		final Set<ColumnSqlSourceDescriptor> descriptors = retrieveColumnSqlSourceDescriptors();
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

	private final Set<ColumnSqlSourceDescriptor> retrieveColumnSqlSourceDescriptors()
	{
		// FIXME: hardcoded prototype
		return ImmutableSet.of(
				ColumnSqlSourceDescriptor.builder()
						.targetTableName(I_C_BPartner.Table_Name)
						.sourceTableName(I_AD_User.Table_Name)
						.sqlToGetTargetRecordIdBySourceRecordId("select C_BPartner_ID from AD_User u where u.AD_User_ID=@Record_ID@ AND u.IsActive='Y' AND u.IsDefaultContact='Y'")
						.build());
	}
}
