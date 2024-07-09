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
import de.metas.cache.CCache;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.mm.attributes.AttributeId;
import org.compiere.model.I_MobileUI_HUManager;
import org.compiere.model.I_MobileUI_HUManager_Attribute;
import org.compiere.model.I_MobileUI_UserProfile_Picking;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class HUManagerProfileRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<OrgId, HUManagerProfile> cache = CCache.<OrgId, HUManagerProfile>builder()
			.tableName(I_MobileUI_UserProfile_Picking.Table_Name)
			.build();

	@NonNull
	public HUManagerProfile getProfile(@NonNull final OrgId orgId)
	{
		return cache.getOrLoad(orgId, this::retrieveProfile);
	}

	@NonNull
	private HUManagerProfile retrieveProfile(@NonNull final OrgId orgId)
	{
		return retrieveProfileRecord(orgId)
				.map(this::fromRecord)
				.orElse(HUManagerProfile.DEFAULT);
	}

	@NonNull
	private HUManagerProfile fromRecord(@NonNull final I_MobileUI_HUManager record)
	{
		return HUManagerProfile.builder()
				.displayedAttributeIdsInOrder(retrieveDisplayedAttributeIdsInOrder(record.getMobileUI_HUManager_ID()))
				.build();
	}

	@NonNull
	private Optional<I_MobileUI_HUManager> retrieveProfileRecord(@NonNull final OrgId orgId)
	{
		return queryBL.createQueryBuilder(I_MobileUI_HUManager.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MobileUI_HUManager.COLUMNNAME_AD_Org_ID, orgId)
				.create()
				.firstOnlyOptional(I_MobileUI_HUManager.class);
	}

	@NonNull
	private ImmutableList<AttributeId> retrieveDisplayedAttributeIdsInOrder(final int mobileUI_HUManager_ID)
	{
		return queryBL.createQueryBuilder(I_MobileUI_HUManager_Attribute.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MobileUI_HUManager_Attribute.COLUMNNAME_MobileUI_HUManager_ID, mobileUI_HUManager_ID)
				.orderBy(I_MobileUI_HUManager_Attribute.COLUMNNAME_SeqNo)
				.orderBy(I_MobileUI_HUManager_Attribute.COLUMNNAME_MobileUI_HUManager_Attribute_ID)
				.create()
				.stream()
				.map(record -> AttributeId.ofRepoId(record.getM_Attribute_ID()))
				.collect(ImmutableList.toImmutableList());
	}
}
