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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.cache.CCache;
import de.metas.handlingunits.picking.job.model.PickingJobFacetGroup;
import de.metas.handlingunits.picking.job.service.CreateShipmentPolicy;
import de.metas.picking.model.I_PickingProfile_Filter;
import de.metas.picking.model.I_PickingProfile_PickingJobConfig;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_MobileUI_UserProfile_Picking;
import org.compiere.model.I_MobileUI_UserProfile_Picking_BPartner;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.stream.Stream;

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
	private MobileUIPickingUserProfile retrieveProfile()
	{
		final I_MobileUI_UserProfile_Picking profileRecord = retrieveProfileRecord();
		if (profileRecord == null)
		{
			return MobileUIPickingUserProfile.DEFAULT;
		}

		final ImmutableSet<BPartnerId> onlyBPartnerIds = streamProfileBPartnerRecords(profileRecord)
				.map(MobileUIPickingUserProfileRepository::extractBPartnerId)
				.collect(ImmutableSet.toImmutableSet());

		return MobileUIPickingUserProfile.builder()
				.name(profileRecord.getName())
				.onlyBPartnerIds(onlyBPartnerIds)
				.isAlwaysSplitHUsEnabled(profileRecord.isAlwaysSplitHUsEnabled())
				.isAllowPickingAnyHU(profileRecord.isAllowPickingAnyHU())
				.createShipmentPolicy(CreateShipmentPolicy.ofCode(profileRecord.getCreateShipmentPolicy()))
				.filters(retrieveFilters(profileRecord))
				.fields(retrieveFields(profileRecord))
				.build();
	}

	@Nullable
	private I_MobileUI_UserProfile_Picking retrieveProfileRecord()
	{
		return queryBL.createQueryBuilder(I_MobileUI_UserProfile_Picking.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnly(I_MobileUI_UserProfile_Picking.class);
	}

	private Stream<I_MobileUI_UserProfile_Picking_BPartner> streamProfileBPartnerRecords(final I_MobileUI_UserProfile_Picking profileRecord)
	{
		return queryBL.createQueryBuilder(I_MobileUI_UserProfile_Picking_BPartner.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MobileUI_UserProfile_Picking_BPartner.COLUMNNAME_MobileUI_UserProfile_Picking_ID, profileRecord.getMobileUI_UserProfile_Picking_ID())
				.create()
				.stream();
	}

	private static BPartnerId extractBPartnerId(final I_MobileUI_UserProfile_Picking_BPartner record)
	{
		return BPartnerId.ofRepoId(record.getC_BPartner_ID());
	}

	public void save(@NonNull final MobileUIPickingUserProfile profile)
	{
		I_MobileUI_UserProfile_Picking profileRecord = retrieveProfileRecord();
		if (profileRecord == null)
		{
			profileRecord = InterfaceWrapperHelper.newInstance(I_MobileUI_UserProfile_Picking.class);
		}
		profileRecord.setIsAlwaysSplitHUsEnabled(profile.isAlwaysSplitHUsEnabled());
		profileRecord.setName(profile.getName());
		profileRecord.setIsAllowPickingAnyHU(profile.isAllowPickingAnyHU());
		profileRecord.setCreateShipmentPolicy(profile.getCreateShipmentPolicy().getCode());
		profileRecord.setIsActive(true);
		InterfaceWrapperHelper.saveRecord(profileRecord);

		final HashMap<BPartnerId, I_MobileUI_UserProfile_Picking_BPartner> profileBPartnerRecords = queryBL.createQueryBuilder(I_MobileUI_UserProfile_Picking_BPartner.class)
				.addEqualsFilter(I_MobileUI_UserProfile_Picking_BPartner.COLUMNNAME_MobileUI_UserProfile_Picking_ID, profileRecord.getMobileUI_UserProfile_Picking_ID())
				.create()
				.stream()
				.collect(GuavaCollectors.toHashMapByKey(MobileUIPickingUserProfileRepository::extractBPartnerId));

		for (final BPartnerId bpartnerId : profile.getOnlyBPartnerIds())
		{
			I_MobileUI_UserProfile_Picking_BPartner profileBPartnerRecord = profileBPartnerRecords.remove(bpartnerId);
			if (profileBPartnerRecord == null)
			{
				profileBPartnerRecord = InterfaceWrapperHelper.newInstance(I_MobileUI_UserProfile_Picking_BPartner.class);
				profileBPartnerRecord.setMobileUI_UserProfile_Picking_ID(profileRecord.getMobileUI_UserProfile_Picking_ID());
			}
			profileBPartnerRecord.setC_BPartner_ID(bpartnerId.getRepoId());
			profileBPartnerRecord.setIsActive(true);
			InterfaceWrapperHelper.saveRecord(profileBPartnerRecord);
		}

		InterfaceWrapperHelper.deleteAll(profileBPartnerRecords.values());
	}

	@NonNull
	private PickingFiltersList retrieveFilters(@NonNull final I_MobileUI_UserProfile_Picking profileRecord)
	{
		return queryBL.createQueryBuilder(I_PickingProfile_Filter.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_PickingProfile_Filter.COLUMNNAME_MobileUI_UserProfile_Picking_ID, profileRecord.getMobileUI_UserProfile_Picking_ID())
				.create()
				.stream()
				.map(record -> PickingFilter.of(PickingJobFacetGroup.ofCode(record.getFilterType()), record.getSeqNo()))
				.collect(PickingFiltersList.collect());
	}

	@NonNull
	private ImmutableList<PickingJobField> retrieveFields(@NonNull final I_MobileUI_UserProfile_Picking profileRecord)
	{
		final ImmutableList<PickingJobField> fields = queryBL.createQueryBuilder(I_PickingProfile_PickingJobConfig.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_PickingProfile_PickingJobConfig.COLUMNNAME_MobileUI_UserProfile_Picking_ID, profileRecord.getMobileUI_UserProfile_Picking_ID())
				.create()
				.stream()
				.map(MobileUIPickingUserProfileRepository::toPickingJobField)
				.collect(ImmutableList.toImmutableList());

		return !fields.isEmpty() ? fields : MobileUIPickingUserProfile.DEFAULT.getFields();
	}

	private static PickingJobField toPickingJobField(final I_PickingProfile_PickingJobConfig config)
	{
		return PickingJobField.builder()
				.field(PickingJobFieldType.ofCode(config.getPickingJobField()))
				.seqNo(config.getSeqNo())
				.isShowInDetailed(config.isDisplayInDetailed())
				.isShowInSummary(config.isDisplayInSummary())
				.pattern(config.getFormatPattern())
				.build();
	}
}
