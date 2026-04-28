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
import com.google.common.collect.ImmutableSet;
import de.metas.cache.CCache;
import de.metas.cache.CacheMgt;
import de.metas.cache.ICacheResetListener;
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

@Repository
public class DocOutboundConfigRepository
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull private final CCache<Integer, DocOutboundConfigMap> cache = CCache.<Integer, DocOutboundConfigMap>builder()
			.tableName(I_C_Doc_Outbound_Config.Table_Name)
			.maximumSize(1)
			.build();

	public static DocOutboundConfigRepository newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return new DocOutboundConfigRepository();
	}

	public void addCacheResetListener(@NonNull final DocOutboundConfigChangedListener listener)
	{
		final ICacheResetListener cacheResetListener = (request) -> {
			listener.onConfigChanged();
			return 1L;
		};

		final CacheMgt cacheMgt = CacheMgt.get();
		cacheMgt.addCacheResetListener(I_C_Doc_Outbound_Config.Table_Name, cacheResetListener);
	}

	@Nullable
	public DocOutboundConfig getByQuery(@NonNull final DocOutboundConfigQuery query)
	{
		return getDocOutboundConfigMap().getByQuery(query);
	}

	@NonNull
	private DocOutboundConfigMap getDocOutboundConfigMap()
	{
		return cache.getOrLoadNonNull(0, this::retrieveDocOutboundConfigMap);
	}

	@NotNull
	private DocOutboundConfigMap retrieveDocOutboundConfigMap()
	{
		final ImmutableList<DocOutboundConfig> docOutboundConfig = queryBL.createQueryBuilder(I_C_Doc_Outbound_Config.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(DocOutboundConfigRepository::ofRecord)
				.collect(ImmutableList.toImmutableList());

		return new DocOutboundConfigMap(docOutboundConfig);
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
	public ImmutableSet<AdTableId> getDistinctConfigTableIds()
	{
		return getDocOutboundConfigMap().getTableIds();
	}
}
