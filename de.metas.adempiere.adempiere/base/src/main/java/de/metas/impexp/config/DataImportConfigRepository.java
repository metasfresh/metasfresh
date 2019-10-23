package de.metas.impexp.config;

import java.util.Collection;
import java.util.Optional;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_DataImport;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.cache.CCache;
import de.metas.impexp.format.ImpFormatId;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;

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

@Repository
public class DataImportConfigRepository
{
	private final CCache<Integer, DataImportConfigsCollection> cache = CCache.<Integer, DataImportConfigRepository.DataImportConfigsCollection> builder()
			.tableName(I_C_DataImport.Table_Name)
			.build();

	public DataImportConfig getById(@NonNull final DataImportConfigId id)
	{
		return getCollection().getById(id);
	}

	public Optional<DataImportConfig> getByInternalName(@NonNull final String internalName)
	{
		return getCollection().getByInternalName(internalName);
	}

	private DataImportConfigsCollection getCollection()
	{
		return cache.getOrLoad(0, this::retrieveCollection);
	}

	private DataImportConfigsCollection retrieveCollection()
	{
		final ImmutableList<DataImportConfig> configs = Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_C_DataImport.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(record -> toDataImportConfig(record))
				.collect(ImmutableList.toImmutableList());

		return new DataImportConfigsCollection(configs);
	}

	private static DataImportConfig toDataImportConfig(@NonNull final I_C_DataImport record)
	{
		return DataImportConfig.builder()
				.id(DataImportConfigId.ofRepoId(record.getC_DataImport_ID()))
				.internalName(StringUtils.trimBlankToNull(record.getInternalName()))
				.impFormatId(ImpFormatId.ofRepoId(record.getAD_ImpFormat_ID()))
				.build();
	}

	private static class DataImportConfigsCollection
	{
		private final ImmutableMap<DataImportConfigId, DataImportConfig> configsById;
		private final ImmutableMap<String, DataImportConfig> configsByInternalName;

		public DataImportConfigsCollection(final Collection<DataImportConfig> configs)
		{
			configsById = Maps.uniqueIndex(configs, DataImportConfig::getId);
			configsByInternalName = configs.stream()
					.filter(config -> config.getInternalName() != null)
					.collect(GuavaCollectors.toImmutableMapByKey(DataImportConfig::getInternalName));
		}

		public DataImportConfig getById(@NonNull final DataImportConfigId id)
		{
			final DataImportConfig config = configsById.get(id);
			if (config == null)
			{
				throw new AdempiereException("@NotFound@ @C_DataConfig_ID@: " + id);
			}
			return config;
		}

		public Optional<DataImportConfig> getByInternalName(final String internalName)
		{
			Check.assumeNotEmpty(internalName, "internalName is not empty");
			return Optional.ofNullable(configsByInternalName.get(internalName));
		}
	}
}
