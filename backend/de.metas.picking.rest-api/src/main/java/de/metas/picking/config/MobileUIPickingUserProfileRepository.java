/*
 * #%L
 * de.metas.picking.rest-api
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

package de.metas.picking.config;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.cache.CCache;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_MobileUI_UserProfile_Picking;
import org.compiere.model.I_MobileUI_UserProfile_Picking_BPartner;
import org.springframework.stereotype.Repository;

@Repository
public class MobileUIPickingUserProfileRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, MobileUIPickingUserProfile> cache = CCache.<Integer, MobileUIPickingUserProfile>builder()
			.tableName(I_MobileUI_UserProfile_Picking.Table_Name)
			.build();

	public MobileUIPickingUserProfile getProfile()
	{
		return cache.getOrLoad(0, this::retrieveProfile);
	}

	@NonNull
	public MobileUIPickingUserProfile retrieveProfile()
	{
		final I_MobileUI_UserProfile_Picking profile = queryBL.createQueryBuilder(I_MobileUI_UserProfile_Picking.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnly(I_MobileUI_UserProfile_Picking.class);

		if (profile == null)
		{
			return MobileUIPickingUserProfile.DEFAULT;
		}

		final ImmutableSet<BPartnerId> onlyBPartnerIds = queryBL.createQueryBuilder(I_MobileUI_UserProfile_Picking_BPartner.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MobileUI_UserProfile_Picking_BPartner.COLUMNNAME_MobileUI_UserProfile_Picking_ID, profile.getMobileUI_UserProfile_Picking_ID())
				.create()
				.stream()
				.map(I_MobileUI_UserProfile_Picking_BPartner::getC_BPartner_ID)
				.map(BPartnerId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());

		return MobileUIPickingUserProfile.builder()
				.onlyBPartnerIds(onlyBPartnerIds)
				.build();
	}
}
