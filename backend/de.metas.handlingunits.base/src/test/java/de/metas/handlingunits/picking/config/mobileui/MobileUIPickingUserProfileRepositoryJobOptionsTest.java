package de.metas.handlingunits.picking.config.mobileui;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
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

/** Writing of the picking job options rows (the options of a customer of the profile) by the repository. */
class MobileUIPickingUserProfileRepositoryJobOptionsTest
{
	private static final ScannableCodeFormatId FORMAT_1 = ScannableCodeFormatId.ofRepoId(301);
	private static final ScannableCodeFormatId FORMAT_2 = ScannableCodeFormatId.ofRepoId(302);

	private MobileUIPickingUserProfileRepository repository;
	private I_MobileUI_UserProfile_Picking profile;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		repository = new MobileUIPickingUserProfileRepository();

		profile = InterfaceWrapperHelper.newInstance(I_MobileUI_UserProfile_Picking.class);
		profile.setName("test profile");
		profile.setPickingJobAggregationType("sales_order");
		profile.setCreateShipmentPolicy("CO");
		profile.setIsAllowSkippingRejectedReason(true);
		profile.setIsShowConfirmationPromptWhenOverPick(true);
		profile.setAllowPickToStructure_TU(true);
		profile.setCUQtyEntryMode(X_MobileUI_UserProfile_Picking.CUQTYENTRYMODE_SCAN_ONCE_ENTER_QTY);
		profile.setC_ScannableCode_Format_ID(FORMAT_1.getRepoId());
		InterfaceWrapperHelper.saveRecord(profile);
	}

	private I_MobileUI_UserProfile_Picking_Job load(final PickingJobOptionsId id)
	{
		return InterfaceWrapperHelper.load(id, I_MobileUI_UserProfile_Picking_Job.class);
	}

	private int countJobRecords()
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_MobileUI_UserProfile_Picking_Job.class).create().count();
	}

	@Test
	void createPickingJobOptions_storesAllGivenOptions()
	{
		final PickingJobOptions profileOptions = repository.getProfile().getDefaultPickingJobOptions();

		final PickingJobOptionsId id = repository.createPickingJobOptions(
				"E2E customer 1",
				profileOptions.toBuilder().cuQtyEntryMode(CUQtyEntryMode.SCAN_EACH_CU).cuLabelFormatId(FORMAT_2).build());

		final I_MobileUI_UserProfile_Picking_Job record = load(id);
		assertThat(record.getName()).isEqualTo("E2E customer 1");
		assertThat(record.getCUQtyEntryMode()).isEqualTo(X_MobileUI_UserProfile_Picking.CUQTYENTRYMODE_SCAN_EACH_CU);
		assertThat(record.getC_ScannableCode_Format_ID()).isEqualTo(FORMAT_2.getRepoId());
		// the other options start as the profile's
		assertThat(record.isAllowSkippingRejectedReason()).isTrue();
		assertThat(record.isShowConfirmationPromptWhenOverPick()).isTrue();
		assertThat(record.getPickingJobAggregationType()).isEqualTo("sales_order");
		assertThat(record.getCreateShipmentPolicy()).isEqualTo("CO");
		assertThat(record.getAllowPickToStructure_TU()).isEqualTo("Y");
	}

	@Test
	void createPickingJobOptions_modeNotSet_noFormat_storesEmptySoTheProfilesApplies()
	{
		final PickingJobOptions profileOptions = repository.getProfile().getDefaultPickingJobOptions();

		final PickingJobOptionsId id = repository.createPickingJobOptions("E2E customer 2", profileOptions.toBuilder().cuQtyEntryMode(null).cuLabelFormatId(null).build());

		assertThat(load(id).getCUQtyEntryMode()).isNull();
		assertThat(load(id).getC_ScannableCode_Format_ID()).isZero();
	}

	@Test
	void updatePickingJobOptionsCUQtyEntry_changesOnlyModeAndFormat_andNullResetsThem()
	{
		final I_MobileUI_UserProfile_Picking_Job existing = InterfaceWrapperHelper.newInstance(I_MobileUI_UserProfile_Picking_Job.class);
		existing.setName("existing");
		existing.setIsAllowSkippingRejectedReason(true);
		InterfaceWrapperHelper.saveRecord(existing);
		final PickingJobOptionsId id = PickingJobOptionsId.ofRepoId(existing.getMobileUI_UserProfile_Picking_Job_ID());

		repository.updatePickingJobOptionsCUQtyEntry(id, CUQtyEntryMode.SCAN_EACH_CU, FORMAT_2);
		assertThat(load(id).getCUQtyEntryMode()).isEqualTo(X_MobileUI_UserProfile_Picking.CUQTYENTRYMODE_SCAN_EACH_CU);
		assertThat(load(id).getC_ScannableCode_Format_ID()).isEqualTo(FORMAT_2.getRepoId());
		assertThat(load(id).isAllowSkippingRejectedReason()).isTrue();

		repository.updatePickingJobOptionsCUQtyEntry(id, null, null);
		assertThat(load(id).getCUQtyEntryMode()).isNull();
		assertThat(load(id).getC_ScannableCode_Format_ID()).isZero();
		assertThat(load(id).isAllowSkippingRejectedReason()).isTrue();
	}

	@Test
	void deleteUnusedPickingJobOptions_deletesOnlyUnreferencedRowsWithThePrefix()
	{
		final PickingJobOptions options = repository.getProfile().getDefaultPickingJobOptions();
		final PickingJobOptionsId referenced = repository.createPickingJobOptions("E2E customer used", options);
		repository.createPickingJobOptions("E2E customer unused", options);
		final PickingJobOptionsId otherName = repository.createPickingJobOptions("Customer config unused", options);

		final I_MobileUI_UserProfile_Picking_BPartner customer = InterfaceWrapperHelper.newInstance(I_MobileUI_UserProfile_Picking_BPartner.class);
		customer.setMobileUI_UserProfile_Picking_ID(profile.getMobileUI_UserProfile_Picking_ID());
		customer.setC_BPartner_ID(BPartnerId.ofRepoId(3000).getRepoId());
		customer.setMobileUI_UserProfile_Picking_Job_ID(referenced.getRepoId());
		InterfaceWrapperHelper.saveRecord(customer);

		repository.deleteUnusedPickingJobOptions("E2E customer ");

		assertThat(countJobRecords()).isEqualTo(2);
		assertThat(load(referenced)).isNotNull();
		assertThat(load(otherName)).isNotNull();
	}

	@Test
	void unlinkCULabelFormats_clearsProfileAndJobRowsOfTheGivenFormatsOnly()
	{
		final PickingJobOptions options = repository.getProfile().getDefaultPickingJobOptions();
		final PickingJobOptionsId job1 = repository.createPickingJobOptions("j1", options.toBuilder().cuLabelFormatId(FORMAT_1).build());
		final PickingJobOptionsId job2 = repository.createPickingJobOptions("j2", options.toBuilder().cuLabelFormatId(FORMAT_2).build());

		repository.unlinkCULabelFormats(ImmutableList.of(FORMAT_1));

		assertThat(InterfaceWrapperHelper.load(profile.getMobileUI_UserProfile_Picking_ID(), I_MobileUI_UserProfile_Picking.class).getC_ScannableCode_Format_ID()).isZero();
		assertThat(load(job1).getC_ScannableCode_Format_ID()).isZero();
		assertThat(load(job2).getC_ScannableCode_Format_ID()).isEqualTo(FORMAT_2.getRepoId());
	}
}
