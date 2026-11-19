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
		// options that differ from the defaults of a new job options row
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
	void customerWithoutJobOptions_cuSettingsGiven_createsAndLinksJobOptionsThatKeepTheProfileOptions()
	{
		final I_MobileUI_UserProfile_Picking_BPartner customerRecord = createCustomerRecord(null);

		writer.write(CUSTOMER_ID, CUQtyEntryMode.SCAN_ONCE_ENTER_QTY, null);

		assertThat(countJobRecords()).isEqualTo(1);
		final int jobId = reload(customerRecord).getMobileUI_UserProfile_Picking_Job_ID();
		assertThat(jobId).isGreaterThan(0);
		final I_MobileUI_UserProfile_Picking_Job job = InterfaceWrapperHelper.load(jobId, I_MobileUI_UserProfile_Picking_Job.class);
		assertThat(job.getCUQtyEntryMode()).isEqualTo(X_MobileUI_UserProfile_Picking.CUQTYENTRYMODE_SCAN_ONCE_ENTER_QTY);
		assertThat(ScannableCodeFormatId.ofRepoIdOrNull(job.getC_ScannableCode_Format_ID())).isNull(); // empty on the row = the profile's
		// the new row must not change the customer's other options: they start as a copy of the profile's
		assertThat(job.isAllowSkippingRejectedReason()).isTrue();
		assertThat(job.isShowConfirmationPromptWhenOverPick()).isTrue();
		assertThat(job.getPickingJobAggregationType()).isEqualTo("sales_order");
		assertThat(job.getCreateShipmentPolicy()).isEqualTo("CO");
		assertThat(job.getName()).startsWith(CustomerCUQtyEntryWriter.JOB_OPTIONS_NAME_PREFIX);
		assertThat(job.getAllowPickToStructure_TU()).isEqualTo("Y");
		// the mode / format of the new row are what was asked; the effective values fall back to the profile
		assertThat(service.getPickingJobOptions(CUSTOMER_ID).getCuQtyEntryMode()).isEqualTo(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY);
		assertThat(service.getPickingJobOptions(CUSTOMER_ID).getCuLabelFormatId()).contains(PROFILE_FORMAT_ID);
	}

	@Test
	void customerWithoutJobOptions_noCuSettings_createsNothing()
	{
		final I_MobileUI_UserProfile_Picking_BPartner customerRecord = createCustomerRecord(null);

		writer.write(CUSTOMER_ID, null, null);

		assertThat(countJobRecords()).isZero();
		assertThat(reload(customerRecord).getMobileUI_UserProfile_Picking_Job_ID()).isZero();
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
}
