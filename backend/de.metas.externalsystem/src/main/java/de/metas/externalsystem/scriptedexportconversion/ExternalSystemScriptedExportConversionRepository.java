/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.externalsystem.scriptedexportconversion;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.cache.CCache;
import de.metas.cache.CacheMgt;
import de.metas.cache.ICacheResetListener;
import de.metas.document.DocBaseType;
import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.model.I_ExternalSystem_Config_ScriptedExportConversion;
import de.metas.externalsystem.endpoint.ExternalSystemEndpointId;
import de.metas.process.AdProcessId;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.AdTableAndClientId;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.compiere.Adempiere;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ExternalSystemScriptedExportConversionRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull private final CCache<Integer, ExternalSystemScriptedExportConversionRepository.ExternalSystemScriptedExportConversionMap> cache = CCache.<Integer, ExternalSystemScriptedExportConversionRepository.ExternalSystemScriptedExportConversionMap>builder()
			.tableName(I_ExternalSystem_Config_ScriptedExportConversion.Table_Name)
			.maximumSize(1)
			.build();

	@VisibleForTesting
	public static ExternalSystemScriptedExportConversionRepository newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return new ExternalSystemScriptedExportConversionRepository();
	}

	public void addCacheResetListener(@NonNull final ExternalSystemScriptedExportConversionConfigChangedListener listener)
	{
		final ICacheResetListener cacheResetListener = (request) -> {
			listener.onConfigChanged();
			return 1L;
		};

		final CacheMgt cacheMgt = CacheMgt.get();
		cacheMgt.addCacheResetListener(I_ExternalSystem_Config_ScriptedExportConversion.Table_Name, cacheResetListener);
	}

	@NonNull
	public ExternalSystemScriptedExportConversionConfig getById(@NonNull final ExternalSystemScriptedExportConversionConfigId id)
	{
		return getMap().getById(id);
	}

	@NonNull
	public ImmutableSet<AdTableAndClientId> getTriggerOnCompleteDistinctTableAndClientIds()
	{
		return getMap().getTriggerOnCompleteDistinctTableAndClientIds();
	}

	@NonNull
	public ImmutableList<ExternalSystemScriptedExportConversionConfig> getTriggerOnCompleteConfigsByTableAndClientIds(@NonNull final AdTableAndClientId tableAndClientId)
	{
		return getMap().getTriggerOnCompleteConfigsByTableAndClientId(tableAndClientId);
	}

	@NonNull
	public ImmutableList<ExternalSystemScriptedExportConversionConfig> getByParentConfigId(@NonNull final ExternalSystemParentConfigId parentConfigId)
	{
		return getMap().getByParentConfigId(parentConfigId);
	}

	@NonNull
	private ExternalSystemScriptedExportConversionRepository.ExternalSystemScriptedExportConversionMap getMap()
	{
		return cache.getOrLoadNonNull(0, this::retrieveMap);
	}

	@NonNull
	private ExternalSystemScriptedExportConversionRepository.ExternalSystemScriptedExportConversionMap retrieveMap()
	{
		return new ExternalSystemScriptedExportConversionMap(queryBL.createQueryBuilder(I_ExternalSystem_Config_ScriptedExportConversion.class)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_ExternalSystem_Config_ScriptedExportConversion.COLUMNNAME_ExternalSystem_Config_ScriptedExportConversion_ID)
				.create()
				.stream()
				.map(this::fromRecord)
				.collect(ImmutableList.toImmutableList())
		);

	}

	@NonNull
	private ExternalSystemScriptedExportConversionConfig fromRecord(@NonNull final I_ExternalSystem_Config_ScriptedExportConversion config)
	{
		return ExternalSystemScriptedExportConversionConfig.builder()
				.id(ExternalSystemScriptedExportConversionConfigId.ofRepoId(config.getExternalSystem_Config_ScriptedExportConversion_ID()))
				.parentId(ExternalSystemParentConfigId.ofRepoId(config.getExternalSystem_Config_ID()))
				.externalSystemEndpointId(ExternalSystemEndpointId.ofRepoId(config.getExternalSystem_Endpoint_ID()))
				.value(config.getExternalSystemValue())
				.description(config.getDescription())
				.outboundDataProcessId(AdProcessId.ofRepoIdOrNull(config.getAD_Process_OutboundData_ID()))
				.scriptIdentifier(config.getScriptIdentifier())
				.tableAndClientId(AdTableAndClientId.of(
						AdTableId.ofRepoId(config.getAD_Table_ID()),
						ClientId.ofRepoId(config.getAD_Client_ID())))
				.docBaseType(DocBaseType.ofNullableCode(config.getDocBaseType()))
				.whereClause(config.getWhereClause())
				.active(config.isActive())
				.isTriggerOnComplete(config.isTriggerOnComplete())
				.build();
	}

	//
	//
	//
	//
	//

	private static class ExternalSystemScriptedExportConversionMap
	{
		@NonNull private final ImmutableMap<ExternalSystemScriptedExportConversionConfigId, ExternalSystemScriptedExportConversionConfig> byId;
		@NonNull private final ImmutableListMultimap<ExternalSystemParentConfigId, ExternalSystemScriptedExportConversionConfig> byParentConfigId;
		@NonNull private final ImmutableListMultimap<AdTableAndClientId, ExternalSystemScriptedExportConversionConfig> allTriggerOnCompleteByTableAndClientId;
		@Getter @NonNull private final ImmutableList<ExternalSystemScriptedExportConversionConfig> allActive;

		ExternalSystemScriptedExportConversionMap(final List<ExternalSystemScriptedExportConversionConfig> list)
		{
			this.byId = Maps.uniqueIndex(list, ExternalSystemScriptedExportConversionConfig::getId);
			this.byParentConfigId = list.stream()
					.collect(ImmutableListMultimap.toImmutableListMultimap(
							ExternalSystemScriptedExportConversionConfig::getParentId,
							config -> config));
			this.allTriggerOnCompleteByTableAndClientId = list.stream()
					.filter(ExternalSystemScriptedExportConversionConfig::isTriggerOnComplete)
					.collect(ImmutableListMultimap.toImmutableListMultimap(
							ExternalSystemScriptedExportConversionConfig::getTableAndClientId,
							config -> config));
			this.allActive = ImmutableList.copyOf(list);
		}

		@NonNull
		public ImmutableList<ExternalSystemScriptedExportConversionConfig> getByParentConfigId(@NonNull final ExternalSystemParentConfigId parentConfigId)
		{
			return byParentConfigId.get(parentConfigId);
		}

		@NonNull
		public ImmutableList<ExternalSystemScriptedExportConversionConfig> getTriggerOnCompleteConfigsByTableAndClientId(@NonNull final AdTableAndClientId tableAndClientId)
		{
			return allTriggerOnCompleteByTableAndClientId.get(tableAndClientId);
		}

		@NonNull
		public ImmutableSet<AdTableAndClientId> getTriggerOnCompleteDistinctTableAndClientIds()
		{
			return allTriggerOnCompleteByTableAndClientId.keySet();
		}

		@NonNull
		public ExternalSystemScriptedExportConversionConfig getById(@NonNull final ExternalSystemScriptedExportConversionConfigId id)
		{
			return Optional.ofNullable(byId.get(id)).orElseThrow(() -> new AdempiereException("No active ExternalSystemScriptedExportConversionConfig found for ID!")
					.appendParametersToMessage()
					.setParameter("ExternalSystemScriptedExportConversionConfigId", id));
		}
	}
}
