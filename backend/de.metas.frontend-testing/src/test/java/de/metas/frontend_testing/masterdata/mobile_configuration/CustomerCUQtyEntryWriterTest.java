package de.metas.frontend_testing.masterdata.mobile_configuration;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.picking.config.mobileui.CUQtyEntryMode;
import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfileService;
import de.metas.scannable_code.format.ScannableCodeFormatId;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_MobileUI_UserProfile_Picking;
import org.compiere.model.I_MobileUI_UserProfile_Picking_BPartner;
import org.compiere.model.I_MobileUI_UserProfile_Picking_Job;
import org.compiere.model.X_MobileUI_UserProfile_Picking;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerCUQtyEntryWriterTest
{
	private static final BPartnerId CUSTOMER_ID = BPartnerId.ofRepoId(1000);
	private static final ScannableCodeFormatId PROFILE_FORMAT_ID = ScannableCodeFormatId.ofRepoId(77);

	private I_MobileUI_UserProfile_Picking profile;
	private MobileUIPickingUserProfileService service;
	private CustomerCUQtyEntryWriter writer;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		service = MobileUIPickingUserProfileService.newInstanceForUnitTesting();
		writer = new CustomerCUQtyEntryWriter(service);

		profile = InterfaceWrapperHelper.newInstance(I_MobileUI_UserProfile_Picking.class);
		profile.setName("test profile");
		profile.setCUQtyEntryMode(X_MobileUI_UserProfile_Picking.CUQTYENTRYMODE_MANUAL);
		profile.setIsAllowSkippingRejectedReason(true);
		profile.setIsShowConfirmationPromptWhenOverPick(true);
		profile.setPickingJobAggregationType("sales_order");
		profile.setAllowPickToStructure_TU(true);
		profile.setC_ScannableCode_Format_ID(PROFILE_FORMAT_ID.getRepoId());
		profile.setCreateShipmentPolicy("CO");
		InterfaceWrapperHelper.saveRecord(profile);
	}

	private I_MobileUI_UserProfile_Picking_BPartner createCustomerRecord(final Integer jobOptionsId)
	{
		final I_MobileUI_UserProfile_Picking_BPartner customerRecord = InterfaceWrapperHelper.newInstance(I_MobileUI_UserProfile_Picking_BPartner.class);
		customerRecord.setMobileUI_UserProfile_Picking_ID(profile.getMobileUI_UserProfile_Picking_ID());
		customerRecord.setC_BPartner_ID(CUSTOMER_ID.getRepoId());
		if (jobOptionsId != null)
		{
			customerRecord.setMobileUI_UserProfile_Picking_Job_ID(jobOptionsId);
		}
		InterfaceWrapperHelper.saveRecord(customerRecord);
		return customerRecord;
	}

	private I_MobileUI_UserProfile_Picking_BPartner reload(final I_MobileUI_UserProfile_Picking_BPartner customerRecord)
	{
		return InterfaceWrapperHelper.load(customerRecord.getMobileUI_UserProfile_Picking_BPartner_ID(), I_MobileUI_UserProfile_Picking_BPartner.class);
	}

	private int countJobRecords()
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_MobileUI_UserProfile_Picking_Job.class).create().count();
	}

	@Test
	void customerWithJobOptions_updatesTheExistingRow_andResetsAbsentValues()
	{
		final I_MobileUI_UserProfile_Picking_Job existing = InterfaceWrapperHelper.newInstance(I_MobileUI_UserProfile_Picking_Job.class);
		existing.setName("existing");
		existing.setCUQtyEntryMode(X_MobileUI_UserProfile_Picking.CUQTYENTRYMODE_SCAN_EACH_CU);
		InterfaceWrapperHelper.saveRecord(existing);
		final I_MobileUI_UserProfile_Picking_BPartner customerRecord = createCustomerRecord(existing.getMobileUI_UserProfile_Picking_Job_ID());

		writer.write(CUSTOMER_ID, null, null);

		assertThat(countJobRecords()).isEqualTo(1);
		assertThat(reload(customerRecord).getMobileUI_UserProfile_Picking_Job_ID()).isEqualTo(existing.getMobileUI_UserProfile_Picking_Job_ID());
		final I_MobileUI_UserProfile_Picking_Job job = InterfaceWrapperHelper.load(existing.getMobileUI_UserProfile_Picking_Job_ID(), I_MobileUI_UserProfile_Picking_Job.class);
		assertThat(job.getCUQtyEntryMode()).isNull();
		assertThat(ScannableCodeFormatId.ofRepoIdOrNull(job.getC_ScannableCode_Format_ID())).isNull();
	}

	@Nested
	class CustomerWithoutJobOptions
	{
		@Test
		void cuSettingsGiven_createsAndLinksJobOptionsThatKeepTheProfileOptions()
		{
			final I_MobileUI_UserProfile_Picking_BPartner customerRecord = createCustomerRecord(null);

			writer.write(CUSTOMER_ID, CUQtyEntryMode.SCAN_ONCE_ENTER_QTY, null);

			assertThat(countJobRecords()).isEqualTo(1);
			final int jobId = reload(customerRecord).getMobileUI_UserProfile_Picking_Job_ID();
			assertThat(jobId).isGreaterThan(0);
			final I_MobileUI_UserProfile_Picking_Job job = InterfaceWrapperHelper.load(jobId, I_MobileUI_UserProfile_Picking_Job.class);
			assertThat(job.getCUQtyEntryMode()).isEqualTo(X_MobileUI_UserProfile_Picking.CUQTYENTRYMODE_SCAN_ONCE_ENTER_QTY);
			assertThat(ScannableCodeFormatId.ofRepoIdOrNull(job.getC_ScannableCode_Format_ID())).isNull();
			assertThat(job.isAllowSkippingRejectedReason()).isTrue();
			assertThat(job.isShowConfirmationPromptWhenOverPick()).isTrue();
			assertThat(job.getPickingJobAggregationType()).isEqualTo("sales_order");
			assertThat(job.getCreateShipmentPolicy()).isEqualTo("CO");
			assertThat(job.getName()).startsWith(CustomerCUQtyEntryWriter.JOB_OPTIONS_NAME_PREFIX);
			assertThat(job.getAllowPickToStructure_TU()).isEqualTo("Y");
			assertThat(service.getPickingJobOptions(CUSTOMER_ID).getCUQtyEntryModeOrDefault()).isEqualTo(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY);
			assertThat(service.getPickingJobOptions(CUSTOMER_ID).getCULabelFormatId()).contains(PROFILE_FORMAT_ID);
		}

		@Test
		void noCuSettings_createsNothing()
		{
			final I_MobileUI_UserProfile_Picking_BPartner customerRecord = createCustomerRecord(null);

			writer.write(CUSTOMER_ID, null, null);

			assertThat(countJobRecords()).isZero();
			assertThat(reload(customerRecord).getMobileUI_UserProfile_Picking_Job_ID()).isZero();
		}
	}
}
