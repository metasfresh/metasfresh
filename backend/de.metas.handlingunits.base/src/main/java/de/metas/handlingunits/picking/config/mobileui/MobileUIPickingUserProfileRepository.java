/*
 * #%L
 * de.metas.handlingunits.base
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

package de.metas.handlingunits.picking.config.mobileui;

import com.google.common.collect.ImmutableList;
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
import org.compiere.model.I_MobileUI_UserProfile_Picking_Job;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

@Repository
public class MobileUIPickingUserProfileRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, MobileUIPickingUserProfile> profileCache = CCache.<Integer, MobileUIPickingUserProfile>builder()
			.tableName(I_MobileUI_UserProfile_Picking.Table_Name) // header
			.additionalTableNameToResetFor(I_MobileUI_UserProfile_Picking_BPartner.Table_Name) // customers
			.additionalTableNameToResetFor(I_PickingProfile_PickingJobConfig.Table_Name) // fields
			.additionalTableNameToResetFor(I_PickingProfile_Filter.Table_Name) // filters
			.build();

	private final CCache<Integer, PickingJobOptionsCollection> pickingJobOptionsCache = CCache.<Integer, PickingJobOptionsCollection>builder()
			.tableName(I_MobileUI_UserProfile_Picking_Job.Table_Name)
			.build();

	@NonNull
	public PickingJobOptions getPickingJobOptions(@Nullable final BPartnerId customerId)
	{
		return getProfile().getPickingJobOptions(customerId, getPickingJobOptionsCollection());
	}

	@NonNull
	public PickingJobAggregationType getAggregationType(@Nullable final BPartnerId customerId)
	{
		return getProfile().getAggregationType(customerId, getPickingJobOptionsCollection());
	}

	private PickingJobOptionsCollection getPickingJobOptionsCollection()
	{
		return pickingJobOptionsCache.getOrLoad(0, this::retrievePickingJobOptionsCollection);
	}

	public MobileUIPickingUserProfile getProfile()
	{
		return profileCache.getOrLoad(0, this::retrieveProfile);
	}

	@NonNull
	private MobileUIPickingUserProfile retrieveProfile()
	{
		final I_MobileUI_UserProfile_Picking profileRecord = retrieveProfileRecord();
		if (profileRecord == null)
		{
			return MobileUIPickingUserProfile.DEFAULT;
		}
		final MobileUIPickingUserProfileId profileId = MobileUIPickingUserProfileId.ofRepoId(profileRecord.getMobileUI_UserProfile_Picking_ID());

		return MobileUIPickingUserProfile.builder()
				.name(profileRecord.getName())
				.isAllowPickingAnyCustomer(profileRecord.isAllowAnyCustomer())
				.isFilterByBarcode(profileRecord.isFilterByBarcode())
				.customerConfigs(retrievePickingCustomerConfigsCollection(profileId))
				.defaultPickingJobOptions(extractPickingJobOptions(profileRecord))
				.filters(retrieveFilters(profileId))
				.fields(retrieveFields(profileId))
				.build();
	}

	private static PickingJobOptions extractPickingJobOptions(final I_MobileUI_UserProfile_Picking profileRecord)
	{
		return PickingJobOptions.builder()
				.aggregationType(PickingJobAggregationType.ofCode(profileRecord.getPickingJobAggregationType()))
				.isAlwaysSplitHUsEnabled(profileRecord.isAlwaysSplitHUsEnabled())
				.isAllowPickingAnyHU(profileRecord.isAllowPickingAnyHU())
				.isPickWithNewLU(profileRecord.isPickingWithNewLU())
				.isAllowNewTU(profileRecord.isAllowNewTU())
				.considerSalesOrderCapacity(profileRecord.isConsiderSalesOrderCapacity())
				.isCatchWeightTUPickingEnabled(profileRecord.isCatchWeightTUPickingEnabled())
				.isAllowSkippingRejectedReason(profileRecord.isAllowSkippingRejectedReason())
				.isShowConfirmationPromptWhenOverPick(profileRecord.isShowConfirmationPromptWhenOverPick())
				.isAllowCompletingPartialPickingJob(profileRecord.isAllowCompletingPartialPickingJob())
				.isShowLastPickedBestBeforeDateForLines(profileRecord.isShowLastPickedBestBeforeDateForLines())
				.createShipmentPolicy(CreateShipmentPolicy.ofCode(profileRecord.getCreateShipmentPolicy()))
				.pickingLineGroupBy(PickingLineGroupBy.ofNullableCode(profileRecord.getPickingLineGroupBy()))
				.pickingLineSortBy(PickingLineSortBy.ofNullableCode(profileRecord.getPickingLineSortBy()))
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

	private PickingCustomerConfigsCollection retrievePickingCustomerConfigsCollection(final MobileUIPickingUserProfileId profileId)
	{
		return streamProfileBPartnerRecords(profileId)
				.map(MobileUIPickingUserProfileRepository::fromRecord)
				.collect(PickingCustomerConfigsCollection.collect());
	}

	private Stream<I_MobileUI_UserProfile_Picking_BPartner> streamProfileBPartnerRecords(@NonNull final MobileUIPickingUserProfileId profileId)
	{
		return queryBL.createQueryBuilder(I_MobileUI_UserProfile_Picking_BPartner.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MobileUI_UserProfile_Picking_BPartner.COLUMNNAME_MobileUI_UserProfile_Picking_ID, profileId)
				.create()
				.stream();
	}

	private static PickingCustomerConfig fromRecord(final I_MobileUI_UserProfile_Picking_BPartner record)
	{
		return PickingCustomerConfig.builder()
				.customerId(BPartnerId.ofRepoId(record.getC_BPartner_ID()))
				.pickingJobOptionsId(PickingJobOptionsId.ofRepoIdOrNull(record.getMobileUI_UserProfile_Picking_Job_ID()))
				.build();
	}

	private static BPartnerId extractBPartnerId(final I_MobileUI_UserProfile_Picking_BPartner record)
	{
		return BPartnerId.ofRepoId(record.getC_BPartner_ID());
	}

	public void save(@NonNull final MobileUIPickingUserProfile profile)
	{
		//
		// Header record
		final MobileUIPickingUserProfileId profileId;
		{
			I_MobileUI_UserProfile_Picking profileRecord = retrieveProfileRecord();
			if (profileRecord == null)
			{
				profileRecord = InterfaceWrapperHelper.newInstance(I_MobileUI_UserProfile_Picking.class);
			}
			profileRecord.setIsActive(true);
			profileRecord.setName(profile.getName());
			profileRecord.setIsAllowAnyCustomer(profile.isAllowPickingAnyCustomer());
			profileRecord.setIsFilterByBarcode(profile.isFilterByBarcode());
			updateRecord(profileRecord, profile.getDefaultPickingJobOptions());
			InterfaceWrapperHelper.saveRecord(profileRecord);
			profileId = MobileUIPickingUserProfileId.ofRepoId(profileRecord.getMobileUI_UserProfile_Picking_ID());
		}

		//
		// BPartner configs
		save_BPartnerConfigs(profile, profileId);
	}

	private void save_BPartnerConfigs(final @NonNull MobileUIPickingUserProfile profile, final MobileUIPickingUserProfileId profileId)
	{
		final HashMap<BPartnerId, I_MobileUI_UserProfile_Picking_BPartner> profileBPartnerRecords = streamProfileBPartnerRecords(profileId)
				.collect(GuavaCollectors.toHashMapByKey(MobileUIPickingUserProfileRepository::extractBPartnerId));
		for (final PickingCustomerConfig bpartnerConfig : profile.getCustomerConfigs())
		{
			I_MobileUI_UserProfile_Picking_BPartner bpartnerConfigRecord = profileBPartnerRecords.remove(bpartnerConfig.getCustomerId());
			if (bpartnerConfigRecord == null)
			{
				bpartnerConfigRecord = InterfaceWrapperHelper.newInstance(I_MobileUI_UserProfile_Picking_BPartner.class);
				bpartnerConfigRecord.setMobileUI_UserProfile_Picking_ID(profileId.getRepoId());
			}
			updateRecord(bpartnerConfigRecord, bpartnerConfig);
			InterfaceWrapperHelper.saveRecord(bpartnerConfigRecord);
		}

		InterfaceWrapperHelper.deleteAll(profileBPartnerRecords.values());
	}

	private static void updateRecord(@NonNull final I_MobileUI_UserProfile_Picking_BPartner record, @NonNull final PickingCustomerConfig from)
	{
		record.setIsActive(true);
		record.setC_BPartner_ID(from.getCustomerId().getRepoId());
		record.setMobileUI_UserProfile_Picking_Job_ID(PickingJobOptionsId.toRepoId(from.getPickingJobOptionsId()));
	}

	private static void updateRecord(@NonNull final I_MobileUI_UserProfile_Picking record, @NonNull final PickingJobOptions from)
	{
		record.setPickingJobAggregationType(Objects.requireNonNull(from.getAggregationType()).getCode());
		record.setIsAllowPickingAnyHU(from.isAllowPickingAnyHU());
		record.setIsAlwaysSplitHUsEnabled(from.isAlwaysSplitHUsEnabled());
		record.setIsPickingWithNewLU(from.isPickWithNewLU());
		record.setIsAllowNewTU(from.isAllowNewTU());
		record.setIsAllowCompletingPartialPickingJob(from.isAllowCompletingPartialPickingJob());
		record.setIsCatchWeightTUPickingEnabled(from.isCatchWeightTUPickingEnabled());
		record.setIsConsiderSalesOrderCapacity(from.isConsiderSalesOrderCapacity());
		record.setIsAllowSkippingRejectedReason(from.isAllowSkippingRejectedReason());
		record.setIsShowConfirmationPromptWhenOverPick(from.isShowConfirmationPromptWhenOverPick());
		record.setIsShowLastPickedBestBeforeDateForLines(from.isShowLastPickedBestBeforeDateForLines());
		record.setCreateShipmentPolicy(from.getCreateShipmentPolicy().getCode());
		record.setPickingLineGroupBy(from.getPickingLineGroupBy().map(PickingLineGroupBy::getCode).orElse(null));
		record.setPickingLineSortBy(from.getPickingLineSortBy().map(PickingLineSortBy::getCode).orElse(null));
	}

	@NonNull
	private PickingFiltersList retrieveFilters(@NonNull final MobileUIPickingUserProfileId profileId)
	{
		return queryBL.createQueryBuilder(I_PickingProfile_Filter.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_PickingProfile_Filter.COLUMNNAME_MobileUI_UserProfile_Picking_ID, profileId)
				.create()
				.stream()
				.map(record -> PickingFilter.of(PickingJobFacetGroup.ofCode(record.getFilterType()), record.getSeqNo()))
				.collect(PickingFiltersList.collect());
	}

	@NonNull
	private ImmutableList<PickingJobField> retrieveFields(@NonNull final MobileUIPickingUserProfileId profileId)
	{
		final ImmutableList<PickingJobField> fields = queryBL.createQueryBuilder(I_PickingProfile_PickingJobConfig.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_PickingProfile_PickingJobConfig.COLUMNNAME_MobileUI_UserProfile_Picking_ID, profileId)
				.create()
				.stream()
				.map(MobileUIPickingUserProfileRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());

		return !fields.isEmpty() ? fields : MobileUIPickingUserProfile.DEFAULT.getFields();
	}

	private static PickingJobField fromRecord(final I_PickingProfile_PickingJobConfig record)
	{
		return PickingJobField.builder()
				.field(PickingJobFieldType.ofCode(record.getPickingJobField()))
				.seqNo(record.getSeqNo())
				.isShowInDetailed(record.isDisplayInDetailed())
				.isShowInSummary(record.isDisplayInSummary())
				.pattern(record.getFormatPattern())
				.build();
	}

	private PickingJobOptionsCollection retrievePickingJobOptionsCollection()
	{
		return queryBL.createQueryBuilder(I_MobileUI_UserProfile_Picking_Job.class)
				// .addOnlyActiveRecordsFilter() // all
				.create()
				.stream()
				.map(MobileUIPickingUserProfileRepository::fromRecord)
				.collect(PickingJobOptionsCollection.collect());

	}

	private static Map.Entry<PickingJobOptionsId, PickingJobOptions> fromRecord(final I_MobileUI_UserProfile_Picking_Job record)
	{
		final PickingJobOptionsId pickingJobOptionsId = PickingJobOptionsId.ofRepoId(record.getMobileUI_UserProfile_Picking_Job_ID());
		final PickingJobOptions pickingJobOptions = PickingJobOptions.builder()
				.aggregationType(PickingJobAggregationType.ofNullableCode(record.getPickingJobAggregationType()))
				.isAlwaysSplitHUsEnabled(record.isAlwaysSplitHUsEnabled())
				.isAllowPickingAnyHU(record.isAllowPickingAnyHU())
				.isPickWithNewLU(record.isPickingWithNewLU())
				.isAllowNewTU(record.isAllowNewTU())
				.considerSalesOrderCapacity(record.isConsiderSalesOrderCapacity())
				.isCatchWeightTUPickingEnabled(record.isCatchWeightTUPickingEnabled())
				.isAllowSkippingRejectedReason(record.isAllowSkippingRejectedReason())
				.isShowConfirmationPromptWhenOverPick(record.isShowConfirmationPromptWhenOverPick())
				.isShowLastPickedBestBeforeDateForLines(record.isShowLastPickedBestBeforeDateForLines())
				.createShipmentPolicy(CreateShipmentPolicy.ofCode(record.getCreateShipmentPolicy()))
				.pickingLineGroupBy(PickingLineGroupBy.ofNullableCode(record.getPickingLineGroupBy()))
				.pickingLineSortBy(PickingLineSortBy.ofNullableCode(record.getPickingLineSortBy()))
				.build();

		return GuavaCollectors.entry(pickingJobOptionsId, pickingJobOptions);
	}
}
