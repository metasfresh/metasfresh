/*
 * #%L
 * de.metas.elasticsearch
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.fulltextsearch.config;

import com.google.common.collect.ImmutableSet;
import de.metas.elasticsearch.IESSystem;
import de.metas.elasticsearch.model.I_ES_FTS_Config;
import de.metas.i18n.BooleanWithReason;
import de.metas.util.Services;
import lombok.NonNull;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class FTSConfigService
{
	private final IESSystem esSystem = Services.get(IESSystem.class);
	private final FTSConfigRepository ftsConfigRepository;
	private final FTSFilterDescriptorRepository ftsFilterDescriptorRepository;

	public FTSConfigService(
			@NonNull final FTSConfigRepository ftsConfigRepository,
			@NonNull final FTSFilterDescriptorRepository ftsFilterDescriptorRepository)
	{
		this.ftsConfigRepository = ftsConfigRepository;
		this.ftsFilterDescriptorRepository = ftsFilterDescriptorRepository;
	}

	public BooleanWithReason getEnabled()
	{
		return esSystem.getEnabled();
	}

	public RestHighLevelClient elasticsearchClient()
	{
		return esSystem.elasticsearchClient();
	}

	public void addListener(@NonNull final FTSConfigChangedListener listener) {ftsConfigRepository.addListener(listener);}

	public FTSConfig getConfigByESIndexName(@NonNull final String esIndexName) {return ftsConfigRepository.getByESIndexName(esIndexName);}

	public FTSConfig getConfigById(@NonNull final FTSConfigId ftsConfigId) {return ftsConfigRepository.getConfigById(ftsConfigId);}

	public FTSConfigSourceTablesMap getSourceTables()
	{
		return ftsConfigRepository.getSourceTables();
	}

	public Optional<FTSFilterDescriptor> getFilterByTargetTableName(@NonNull final String targetTableName)
	{
		return ftsFilterDescriptorRepository.getByTargetTableName(targetTableName);
	}

	public FTSFilterDescriptor getFilterById(@NonNull final FTSFilterDescriptorId id)
	{
		return ftsFilterDescriptorRepository.getById(id);
	}

	public void updateConfigFields(@NonNull final I_ES_FTS_Config record)
	{
		final FTSConfigId configId = FTSConfigId.ofRepoId(record.getES_FTS_Config_ID());
		final Set<ESFieldName> esFieldNames = ESDocumentToIndexTemplate.ofJsonString(record.getES_DocumentToIndexTemplate()).getESFieldNames();
		ftsConfigRepository.setConfigFields(configId, esFieldNames);
	}

	public void deleteDependingData(@NonNull final FTSConfigId configId)
	{
		ftsConfigRepository.setConfigFields(configId, ImmutableSet.of());
		ftsConfigRepository.deleteSourceTablesByConfigId(configId);
	}

}
