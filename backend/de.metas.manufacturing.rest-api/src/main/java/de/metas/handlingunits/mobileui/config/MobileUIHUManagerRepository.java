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
public class MobileUIHUManagerRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<OrgId, MobileUIHUManager> cache = CCache.<OrgId, MobileUIHUManager>builder()
			.tableName(I_MobileUI_UserProfile_Picking.Table_Name)
			.build();

	@NonNull
	public MobileUIHUManager getHUManagerConfig(@NonNull final OrgId orgId)
	{
		return cache.getOrLoad(orgId, this::retrieveHUManagerConfig);
	}

	@NonNull
	private MobileUIHUManager retrieveHUManagerConfig(@NonNull final OrgId orgId)
	{
		return retrieveHUManagerRecord(orgId)
				.map(this::buildMobileUIHUManager)
				.orElse(MobileUIHUManager.DEFAULT);
	}

	@NonNull
	private MobileUIHUManager buildMobileUIHUManager(@NonNull final I_MobileUI_HUManager profileRecord)
	{
		return MobileUIHUManager.builder()
				.attributes(retrieveAttributes(MobileUIHUManagerId.ofRepoId(profileRecord.getMobileUI_HUManager_ID())))
				.build();
	}

	@NonNull
	private Optional<I_MobileUI_HUManager> retrieveHUManagerRecord(@NonNull final OrgId orgId)
	{
		return queryBL.createQueryBuilder(I_MobileUI_HUManager.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MobileUI_HUManager.COLUMNNAME_AD_Org_ID, orgId)
				.create()
				.firstOnlyOptional(I_MobileUI_HUManager.class);
	}

	@NonNull
	private ImmutableList<MobileUIHUManagerAttribute> retrieveAttributes(@NonNull final MobileUIHUManagerId mobileUIHUManagerId)
	{
		return queryBL.createQueryBuilder(I_MobileUI_HUManager_Attribute.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MobileUI_HUManager_Attribute.COLUMNNAME_MobileUI_HUManager_ID, mobileUIHUManagerId)
				.create()
				.stream()
				.map(MobileUIHUManagerRepository::toHUManagerAttribute)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private static MobileUIHUManagerAttribute toHUManagerAttribute(@NonNull final I_MobileUI_HUManager_Attribute attribute)
	{
		return MobileUIHUManagerAttribute.builder()
				.seqNo(attribute.getSeqNo())
				.attributeId(AttributeId.ofRepoId(attribute.getM_Attribute_ID()))
				.build();
	}
}
