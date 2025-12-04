/*
 * #%L
 * de.metas.document.archive.base
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

package de.metas.document.archive.config;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.cache.CCache;
import de.metas.common.util.CoalesceUtil;
import de.metas.document.DocBaseType;
import de.metas.document.archive.model.I_C_Doc_Outbound_Config;
import de.metas.organization.OrgId;
import de.metas.report.PrintFormatId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.AdTableId;
import org.compiere.Adempiere;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DocOutboundConfigRepository
{
	private final CCache<Integer, DocOutboundConfigRepository.DocOutboundConfigMap> cache = CCache.<Integer, DocOutboundConfigRepository.DocOutboundConfigMap>builder()
			.tableName(I_C_Doc_Outbound_Config.Table_Name)
			.maximumSize(1)
			.build();

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public static DocOutboundConfigRepository newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return new DocOutboundConfigRepository();
	}

	@Nullable
	public DocOutboundConfig getByQuery(@NonNull final DocOutboundConfigQuery query)
	{
		return getDocOutboundConfigMap().getByQuery(query);
	}

	@NonNull
	private DocOutboundConfigRepository.DocOutboundConfigMap getDocOutboundConfigMap()
	{
		return cache.getOrLoadNonNull(0, this::retrieveDocOutboundConfigMap);
	}

	@NotNull
	private DocOutboundConfigRepository.DocOutboundConfigMap retrieveDocOutboundConfigMap()
	{
		final ImmutableList<DocOutboundConfig> docOutboundConfig = queryBL.createQueryBuilder(I_C_Doc_Outbound_Config.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(DocOutboundConfigRepository::ofRecord)
				.collect(ImmutableList.toImmutableList());

		return new DocOutboundConfigRepository.DocOutboundConfigMap(docOutboundConfig);
	}

	private static DocOutboundConfig ofRecord(@NotNull final I_C_Doc_Outbound_Config record)
	{
		return DocOutboundConfig.builder()
				.id(DocOutboundConfigId.ofRepoId(record.getC_Doc_Outbound_Config_ID()))
				.tableId(AdTableId.ofRepoId(record.getAD_Table_ID()))
				.docBaseType(DocBaseType.ofNullableCode(record.getDocBaseType()))
				.printFormatId(PrintFormatId.ofRepoIdOrNull(record.getAD_PrintFormat_ID()))
				.ccPath(record.getCCPath())
				.isDirectProcessQueueItem(record.isDirectProcessQueueItem())
				.isDirectEnqueue(record.isDirectEnqueue())
				.isAutoSendDocument(record.isAutoSendDocument())
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.build();
	}

	@NonNull
	public ImmutableList<DocOutboundConfig> getByTableId(@NonNull final AdTableId tableId)
	{
		return getDocOutboundConfigMap().getByTableId(tableId);
	}

	@NonNull
	public ImmutableSet<AdTableId> getDistinctConfigTableIds()
	{
		return getDocOutboundConfigMap().byTableId.keySet();
	}

	private static final class DocOutboundConfigMap
	{
		private final ImmutableMap<DocOutboundConfigQuery, DocOutboundConfig> byQuery;
		private final ImmutableMap<AdTableId, ImmutableList<DocOutboundConfig>> byTableId;

		DocOutboundConfigMap(final List<DocOutboundConfig> list)
		{
			this.byQuery = Maps.uniqueIndex(list, config -> DocOutboundConfigQuery.builder()
					.tableId(config.getTableId())
					.docBaseType(config.getDocBaseType())
					.orgId(config.getOrgId())
					.build()
			);
			this.byTableId = list.stream().collect(ImmutableMap.toImmutableMap(DocOutboundConfig::getTableId, ImmutableList::of));
		}

		@Nullable
		public DocOutboundConfig getByQuery(@NonNull final DocOutboundConfigQuery query)
		{
            return CoalesceUtil.coalesceSuppliers(
					() -> byQuery.get(query),
					() -> byQuery.get(query.withDocBaseType(null)),
					() -> byQuery.get(query.withOrgId(OrgId.ANY)),
					() -> byQuery.get(query.withDocBaseType(null).withOrgId(OrgId.ANY))
			);
		}

		@NonNull
		public ImmutableList<DocOutboundConfig> getByTableId(@NonNull final AdTableId tableId)
		{
			return byTableId.getOrDefault(tableId, ImmutableList.of());
		}
	}
}
