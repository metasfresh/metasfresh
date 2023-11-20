/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.externalsystem;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.cache.CCache;
import de.metas.externalsystem.leichmehl.ExternalSystemLeichMehlConfigProductMapping;
import de.metas.externalsystem.leichmehl.ExternalSystemLeichMehlConfigProductMappingId;
import de.metas.externalsystem.leichmehl.ExternalSystemLeichMehlPluFileConfig;
import de.metas.externalsystem.leichmehl.ExternalSystemLeichMehlPluFileConfigId;
import de.metas.externalsystem.leichmehl.LeichMehlPluFileConfigGroup;
import de.metas.externalsystem.leichmehl.LeichMehlPluFileConfigGroupId;
import de.metas.externalsystem.leichmehl.ReplacementSource;
import de.metas.externalsystem.leichmehl.TargetFieldType;
import de.metas.externalsystem.model.I_ExternalSystem_Config_LeichMehl_ProductMapping;
import de.metas.externalsystem.model.I_LeichMehl_PluFile_Config;
import de.metas.externalsystem.model.I_LeichMehl_PluFile_ConfigGroup;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

@Repository
public class ExternalSystemLeichMehlConfigProductMappingRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<LeichMehlPluFileConfigGroupId, LeichMehlPluFileConfigGroup> groupsCache = CCache.<LeichMehlPluFileConfigGroupId, LeichMehlPluFileConfigGroup>builder()
			.cacheMapType(CCache.CacheMapType.LRU)
			.initialCapacity(1000) // max size
			.tableName(I_LeichMehl_PluFile_ConfigGroup.Table_Name) // header
			.additionalTableNameToResetFor(I_LeichMehl_PluFile_Config.Table_Name) // lines
			.build();

	@NonNull
	public Optional<ExternalSystemLeichMehlConfigProductMapping> getByProductIdAndPartnerId(@NonNull final ProductId productId, @Nullable final BPartnerId bPartnerId)
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_Config_LeichMehl_ProductMapping.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ExternalSystem_Config_LeichMehl_ProductMapping.COLUMNNAME_M_Product_ID, productId)
				.addInArrayFilter(I_ExternalSystem_Config_LeichMehl_ProductMapping.COLUMNNAME_C_BPartner_ID, bPartnerId, null)
				.orderBy(I_ExternalSystem_Config_LeichMehl_ProductMapping.COLUMNNAME_C_BPartner_ID)
				.create()
				.firstOptional(I_ExternalSystem_Config_LeichMehl_ProductMapping.class)
				.map(this::toExternalSystemLeichMehlConfigProductMapping);
	}

	@NonNull
	private ExternalSystemLeichMehlConfigProductMapping toExternalSystemLeichMehlConfigProductMapping(@NonNull final I_ExternalSystem_Config_LeichMehl_ProductMapping record)
	{
		return ExternalSystemLeichMehlConfigProductMapping.builder()
				.id(ExternalSystemLeichMehlConfigProductMappingId.ofRepoId(record.getExternalSystem_Config_LeichMehl_ProductMapping_ID()))
				.pluFile(record.getPLU_File())
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.bPartnerId(BPartnerId.ofRepoIdOrNull(record.getC_BPartner_ID()))
				.leichMehlPluFileConfigGroup(getConfigGroupById(LeichMehlPluFileConfigGroupId.ofRepoId(record.getLeichMehl_PluFile_ConfigGroup_ID())))
				.build();
	}

	private LeichMehlPluFileConfigGroup getConfigGroupById(LeichMehlPluFileConfigGroupId id)
	{
		return groupsCache.getOrLoad(id, this::retrieveConfigGroupById);
	}

	private LeichMehlPluFileConfigGroup retrieveConfigGroupById(LeichMehlPluFileConfigGroupId id)
	{
		return toLeichMehlPluFileConfigGroup(queryBL.createQueryBuilder(I_LeichMehl_PluFile_ConfigGroup.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_LeichMehl_PluFile_ConfigGroup.COLUMNNAME_LeichMehl_PluFile_ConfigGroup_ID, id)
				.create()
				.firstNotNull(I_LeichMehl_PluFile_ConfigGroup.class));
	}

	private LeichMehlPluFileConfigGroup toLeichMehlPluFileConfigGroup(@NonNull final I_LeichMehl_PluFile_ConfigGroup leichMehlPluFileConfigGroup)
	{
		final LeichMehlPluFileConfigGroupId leichMehlPluFileConfigGroupId = LeichMehlPluFileConfigGroupId.ofRepoId(leichMehlPluFileConfigGroup.getLeichMehl_PluFile_ConfigGroup_ID());
		return LeichMehlPluFileConfigGroup.builder()
				.id(leichMehlPluFileConfigGroupId)
				.name(leichMehlPluFileConfigGroup.getName())
				.externalSystemLeichMehlPluFileConfigs(getExternalSystemLeichMehlPluFileConfigs(leichMehlPluFileConfigGroupId))
				.build();
	}

	@NonNull
	private List<ExternalSystemLeichMehlPluFileConfig> getExternalSystemLeichMehlPluFileConfigs(@NonNull final LeichMehlPluFileConfigGroupId leichMehlPluFileConfigGroupId)
	{
		return queryBL.createQueryBuilder(I_LeichMehl_PluFile_Config.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_LeichMehl_PluFile_Config.COLUMNNAME_LeichMehl_PluFile_ConfigGroup_ID, leichMehlPluFileConfigGroupId)
				.create()
				.stream()
				.map(ExternalSystemLeichMehlConfigProductMappingRepository::toExternalSystemLeichMehlPluFileConfig)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private static ExternalSystemLeichMehlPluFileConfig toExternalSystemLeichMehlPluFileConfig(@NonNull final I_LeichMehl_PluFile_Config record)
	{
		return ExternalSystemLeichMehlPluFileConfig.builder()
				.id(ExternalSystemLeichMehlPluFileConfigId.ofRepoId(record.getLeichMehl_PluFile_Config_ID()))
				.leichMehlPluFileConfigGroupId(LeichMehlPluFileConfigGroupId.ofRepoId(record.getLeichMehl_PluFile_ConfigGroup_ID()))
				.targetFieldName(record.getTargetFieldName())
				.targetFieldType(TargetFieldType.ofCode(record.getTargetFieldType()))
				.replacement(record.getReplacement())
				.replaceRegExp(record.getReplaceRegExp())
				.replacementSource(ReplacementSource.ofCode(record.getReplacementSource()))
				.build();
	}
}
