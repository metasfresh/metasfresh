package de.metas.frontend_testing.masterdata.mobile_configuration;

import de.metas.bpartner.BPartnerId;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_MobileUI_UserProfile_Picking;
import org.compiere.model.I_MobileUI_UserProfile_Picking_BPartner;
import org.compiere.model.I_MobileUI_UserProfile_Picking_Job;

import javax.annotation.Nullable;

/**
 * Writes the CU quantity entry mode and CU label format of one customer of the picking profile.
 * They are stored on the customer's picking job options row ({@link I_MobileUI_UserProfile_Picking_Job}); a customer of a fresh masterdata run has none,
 * so it is created here and linked on the customer's row of the profile.
 * <p>
 * A customer's job options row replaces the profile's options as a whole (booleans have no "empty = profile" state), so a created row
 * starts as a copy of the profile's options: giving a customer a CU setting must not change its other options.
 */
final class CustomerCUQtyEntryWriter
{
	private CustomerCUQtyEntryWriter() {}

	static void write(
			@NonNull final I_MobileUI_UserProfile_Picking profileRecord,
			@NonNull final BPartnerId customerId,
			@Nullable final String cuQtyEntryMode,
			final int cuLabelFormatId)
	{
		final I_MobileUI_UserProfile_Picking_BPartner customerRecord = Services.get(IQueryBL.class)
				.createQueryBuilder(I_MobileUI_UserProfile_Picking_BPartner.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MobileUI_UserProfile_Picking_BPartner.COLUMNNAME_MobileUI_UserProfile_Picking_ID, profileRecord.getMobileUI_UserProfile_Picking_ID())
				.addEqualsFilter(I_MobileUI_UserProfile_Picking_BPartner.COLUMNNAME_C_BPartner_ID, customerId.getRepoId())
				.create()
				.firstOnly(I_MobileUI_UserProfile_Picking_BPartner.class);
		if (customerRecord == null)
		{
			throw new AdempiereException("Customer " + customerId + " is not a customer of the picking profile; it must be listed in mobileConfig.picking.customers");
		}

		final boolean hasJobOptions = customerRecord.getMobileUI_UserProfile_Picking_Job_ID() > 0;
		if (!hasJobOptions && cuQtyEntryMode == null && cuLabelFormatId <= 0)
		{
			return; // nothing to reset: the customer has no own job options row
		}

		final I_MobileUI_UserProfile_Picking_Job jobRecord;
		if (hasJobOptions)
		{
			jobRecord = InterfaceWrapperHelper.load(customerRecord.getMobileUI_UserProfile_Picking_Job_ID(), I_MobileUI_UserProfile_Picking_Job.class);
		}
		else
		{
			jobRecord = newJobOptionsCopyOfProfile(profileRecord, customerId);
		}

		jobRecord.setCUQtyEntryMode(cuQtyEntryMode);
		jobRecord.setC_ScannableCode_Format_ID(Math.max(cuLabelFormatId, 0));
		InterfaceWrapperHelper.saveRecord(jobRecord);

		if (!hasJobOptions)
		{
			customerRecord.setMobileUI_UserProfile_Picking_Job_ID(jobRecord.getMobileUI_UserProfile_Picking_Job_ID());
			InterfaceWrapperHelper.saveRecord(customerRecord);
		}
	}

	private static I_MobileUI_UserProfile_Picking_Job newJobOptionsCopyOfProfile(
			@NonNull final I_MobileUI_UserProfile_Picking profile,
			@NonNull final BPartnerId customerId)
	{
		final I_MobileUI_UserProfile_Picking_Job job = InterfaceWrapperHelper.newInstance(I_MobileUI_UserProfile_Picking_Job.class);
		job.setName("E2E customer " + customerId.getRepoId() + " " + System.currentTimeMillis());

		// the options that PickingJobOptions takes from the job row alone: see MobileUIPickingUserProfileRepository#fromRecord(I_MobileUI_UserProfile_Picking_Job)
		job.setPickingJobAggregationType(profile.getPickingJobAggregationType());
		job.setIsAllowPickingAnyHU(profile.isAllowPickingAnyHU());
		job.setIsAlwaysSplitHUsEnabled(profile.isAlwaysSplitHUsEnabled());
		job.setIsShipOnCloseLU(profile.isShipOnCloseLU());
		job.setAllowPickToStructure_LU_TU(StringUtils.ofBoolean(profile.isAllowPickToStructure_LU_TU()));
		job.setAllowPickToStructure_TU(StringUtils.ofBoolean(profile.isAllowPickToStructure_TU()));
		job.setAllowPickToStructure_LU_CU(StringUtils.ofBoolean(profile.isAllowPickToStructure_LU_CU()));
		job.setAllowPickToStructure_CU(StringUtils.ofBoolean(profile.isAllowPickToStructure_CU()));
		job.setIsCatchWeightTUPickingEnabled(profile.isCatchWeightTUPickingEnabled());
		job.setIsConsiderSalesOrderCapacity(profile.isConsiderSalesOrderCapacity());
		job.setIsAllowSkippingRejectedReason(profile.isAllowSkippingRejectedReason());
		job.setIsShowConfirmationPromptWhenOverPick(profile.isShowConfirmationPromptWhenOverPick());
		job.setIsAllowCompletingPartialPickingJob(profile.isAllowCompletingPartialPickingJob());
		job.setIsShowLastPickedBestBeforeDateForLines(profile.isShowLastPickedBestBeforeDateForLines());
		job.setIsAnonymousHuPickedOnTheFly(profile.isAnonymousHuPickedOnTheFly());
		job.setIsDisplayPickingSlotSuggestions(StringUtils.ofBoolean(profile.isDisplayPickingSlotSuggestions()));
		job.setCreateShipmentPolicy(profile.getCreateShipmentPolicy());
		job.setPickingLineGroupBy(profile.getPickingLineGroupBy());
		job.setPickingLineSortBy(profile.getPickingLineSortBy());
		return job;
	}
}
