/*
 * #%L
 * de.metas.manufacturing.rest-api
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.handlingunits.mobileui.config;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import de.metas.cache.CCache;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.mm.attributes.AttributeId;
import org.compiere.model.I_MobileUI_HUManager;
import org.compiere.model.I_MobileUI_HUManager_Attribute;
import org.compiere.model.I_MobileUI_HUManager_LayoutSection;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class HUManagerProfileRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, HUManagerProfilesMap> cache = CCache.<Integer, HUManagerProfilesMap>builder()
			.tableName(I_MobileUI_HUManager.Table_Name)
			.additionalTableNamesToResetFor(ImmutableList.of(I_MobileUI_HUManager_Attribute.Table_Name))
			.build();

	@NonNull
	public HUManagerProfile getProfile(@NonNull final OrgId orgId)
	{
		return getMap().getByOrgId(orgId);
	}

	@NonNull
	private HUManagerProfilesMap getMap()
	{
		//noinspection DataFlowIssue
		return cache.getOrLoad(0, this::retrieveMap);
	}

	@NonNull
	private HUManagerProfilesMap retrieveMap()
	{
		final ImmutableListMultimap<HUManagerProfileId, AttributeId> displayedAttributeIdsInOrderByProfileId = retrieveDisplayedAttributeIdsInOrder();
		final Map<HUManagerProfileId, HUManagerProfileLayoutSectionList> layoutSectionsByProfileId = retrieveLayoutSectionsInOrder();

		return queryBL.createQueryBuilder(I_MobileUI_HUManager.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(record -> fromRecord(record, displayedAttributeIdsInOrderByProfileId, layoutSectionsByProfileId))
				.collect(HUManagerProfilesMap.collect());
	}

	@NonNull
	private ImmutableListMultimap<HUManagerProfileId, AttributeId> retrieveDisplayedAttributeIdsInOrder()
	{
		return queryBL.createQueryBuilder(I_MobileUI_HUManager_Attribute.class)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_MobileUI_HUManager_Attribute.COLUMNNAME_MobileUI_HUManager_ID)
				.orderBy(I_MobileUI_HUManager_Attribute.COLUMNNAME_SeqNo)
				.orderBy(I_MobileUI_HUManager_Attribute.COLUMNNAME_MobileUI_HUManager_Attribute_ID)
				.create()
				.stream()
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						record -> HUManagerProfileId.ofRepoId(record.getMobileUI_HUManager_ID()),
						record -> AttributeId.ofRepoId(record.getM_Attribute_ID())
				));
	}

	@NonNull
	private static HUManagerProfile fromRecord(
			@NonNull final I_MobileUI_HUManager record,
			@NonNull final ImmutableListMultimap<HUManagerProfileId, AttributeId> displayedAttributeIdsInOrderByProfileId,
			@NonNull final Map<HUManagerProfileId, HUManagerProfileLayoutSectionList> layoutSectionsByProfileId)
	{
		final HUManagerProfileId profileId = HUManagerProfileId.ofRepoId(record.getMobileUI_HUManager_ID());

		return HUManagerProfile.builder()
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.displayedAttributeIdsInOrder(displayedAttributeIdsInOrderByProfileId.get(profileId))
				.layoutSections(layoutSectionsByProfileId.getOrDefault(profileId, HUManagerProfileLayoutSectionList.DEFAULT))
				.build();
	}

	@NonNull
	private Map<HUManagerProfileId, HUManagerProfileLayoutSectionList> retrieveLayoutSectionsInOrder()
	{
		return queryBL.createQueryBuilder(I_MobileUI_HUManager_LayoutSection.class)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_MobileUI_HUManager_LayoutSection.COLUMNNAME_MobileUI_HUManager_ID)
				.orderBy(I_MobileUI_HUManager_LayoutSection.COLUMNNAME_SeqNo)
				.orderBy(I_MobileUI_HUManager_LayoutSection.COLUMNNAME_MobileUI_HUManager_LayoutSection_ID)
				.create()
				.stream()
				.collect(Collectors.groupingBy(
						record -> HUManagerProfileId.ofRepoId(record.getMobileUI_HUManager_ID()),
						Collectors.mapping(
								HUManagerProfileRepository::fromRecord,
								HUManagerProfileLayoutSectionList.collectOrDefault()
						)
				));
	}

	private static @NotNull HUManagerProfileLayoutSection fromRecord(final I_MobileUI_HUManager_LayoutSection record)
	{
		return HUManagerProfileLayoutSection.ofCode(record.getLayoutSection());
	}

}
