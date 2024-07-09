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
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.cache.CCache;
import de.metas.common.util.CoalesceUtil;
import de.metas.organization.OrgId;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.mm.attributes.AttributeId;
import org.compiere.model.I_MobileUI_HUManager;
import org.compiere.model.I_MobileUI_HUManager_Attribute;
import org.compiere.model.I_MobileUI_UserProfile_Picking;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collector;

@Repository
public class HUManagerProfileRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, HUManagerProfilesMap> cache = CCache.<Integer, HUManagerProfilesMap>builder()
			.tableName(I_MobileUI_UserProfile_Picking.Table_Name)
			.build();

	@NonNull
	public HUManagerProfile getProfile(@NonNull final OrgId orgId)
	{
		return getMap().getByOrgId(orgId);
	}

	@NonNull
	private HUManagerProfilesMap getMap()
	{
		return cache.getOrLoad(0, this::retrieveMap);
	}

	@NonNull
	private HUManagerProfilesMap retrieveMap()
	{
		@NonNull final ImmutableListMultimap<Integer, AttributeId> displayedAttributeIdsInOrderByProfileId = retrieveDisplayedAttributeIdsInOrder();

		return queryBL.createQueryBuilder(I_MobileUI_HUManager.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(record -> fromRecord(record, displayedAttributeIdsInOrderByProfileId))
				.collect(HUManagerProfilesMap.collect());
	}

	@NonNull
	private ImmutableListMultimap<Integer, AttributeId> retrieveDisplayedAttributeIdsInOrder()
	{
		return queryBL.createQueryBuilder(I_MobileUI_HUManager_Attribute.class)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_MobileUI_HUManager_Attribute.COLUMNNAME_MobileUI_HUManager_ID)
				.orderBy(I_MobileUI_HUManager_Attribute.COLUMNNAME_SeqNo)
				.orderBy(I_MobileUI_HUManager_Attribute.COLUMNNAME_MobileUI_HUManager_Attribute_ID)
				.create()
				.stream()
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						I_MobileUI_HUManager_Attribute::getMobileUI_HUManager_ID,
						record -> AttributeId.ofRepoId(record.getM_Attribute_ID())
				));
	}

	@NonNull
	private static HUManagerProfile fromRecord(
			@NonNull final I_MobileUI_HUManager record,
			@NonNull final ImmutableListMultimap<Integer, AttributeId> displayedAttributeIdsInOrderByProfileId)
	{
		return HUManagerProfile.builder()
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.displayedAttributeIdsInOrder(displayedAttributeIdsInOrderByProfileId.get(record.getMobileUI_HUManager_ID()))
				.build();
	}

	private static class HUManagerProfilesMap
	{
		public static final HUManagerProfilesMap EMPTY = new HUManagerProfilesMap(ImmutableList.of());

		private final ImmutableMap<OrgId, HUManagerProfile> byOrgId;

		private HUManagerProfilesMap(final List<HUManagerProfile> list)
		{
			this.byOrgId = Maps.uniqueIndex(list, HUManagerProfile::getOrgId);
		}

		public static Collector<HUManagerProfile, ?, HUManagerProfilesMap> collect()
		{
			return GuavaCollectors.collectUsingListAccumulator(HUManagerProfilesMap::ofList);
		}

		private static HUManagerProfilesMap ofList(List<HUManagerProfile> list)
		{
			return list.isEmpty() ? EMPTY : new HUManagerProfilesMap(list);
		}

		@NonNull
		public HUManagerProfile getByOrgId(@NonNull final OrgId orgId)
		{
			return CoalesceUtil.coalesceSuppliersNotNull(
					() -> byOrgId.get(orgId),
					() -> byOrgId.get(OrgId.ANY),
					() -> HUManagerProfile.DEFAULT
			);
		}
	}
}
