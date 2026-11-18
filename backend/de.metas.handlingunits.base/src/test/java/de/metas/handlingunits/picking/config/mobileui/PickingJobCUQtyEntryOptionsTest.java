package de.metas.handlingunits.picking.config.mobileui;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.picking.job.service.CreateShipmentPolicy;
import de.metas.scannable_code.format.ScannableCodeFormatId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_MobileUI_UserProfile_Picking;
import org.compiere.model.I_MobileUI_UserProfile_Picking_BPartner;
import org.compiere.model.I_MobileUI_UserProfile_Picking_Job;
import org.compiere.model.X_MobileUI_UserProfile_Picking;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;

import static org.assertj.core.api.Assertions.assertThat;

class PickingJobCUQtyEntryOptionsTest
{
	private static final ScannableCodeFormatId FORMAT_PROFILE = ScannableCodeFormatId.ofRepoId(101);
	private static final ScannableCodeFormatId FORMAT_CUSTOMER = ScannableCodeFormatId.ofRepoId(102);

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	private static PickingJobOptions options(@Nullable final CUQtyEntryMode mode, @Nullable final ScannableCodeFormatId formatId)
	{
		return PickingJobOptions.builder()
				.allowedPickToStructures(AllowedPickToStructures.EMPTY)
				.createShipmentPolicy(CreateShipmentPolicy.DO_NOT_CREATE)
				.cuQtyEntryMode(mode)
				.cuLabelFormatId(formatId)
				.build();
	}

	@Nested
	class Resolution
	{
		@Test
		void modeCustomerEmpty_fallsBackToProfile()
		{
			final PickingJobOptions resolved = options(null, null).fallbackTo(options(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY, null));
			assertThat(resolved.getCuQtyEntryMode()).isEqualTo(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY);
		}

		@Test
		void modeCustomerSet_wins()
		{
			final PickingJobOptions resolved = options(CUQtyEntryMode.SCAN_EACH_CU, null).fallbackTo(options(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY, null));
			assertThat(resolved.getCuQtyEntryMode()).isEqualTo(CUQtyEntryMode.SCAN_EACH_CU);
		}

		@Test
		void customerMode_manual_winsOverProfileNewMode()
		{
			final PickingJobOptions resolved = options(CUQtyEntryMode.MANUAL, null).fallbackTo(options(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY, null));
			assertThat(resolved.getCuQtyEntryMode()).isEqualTo(CUQtyEntryMode.MANUAL);
		}

		@Test
		void noProfileMode_isManual()
		{
			final PickingJobOptions resolved = options(null, null).fallbackTo(options(null, null));
			assertThat(resolved.getCuQtyEntryMode()).isEqualTo(CUQtyEntryMode.MANUAL);
			assertThat(options(null, null).getCuQtyEntryMode()).isEqualTo(CUQtyEntryMode.MANUAL);
		}

		@Test
		void formatCustomerEmpty_fallsBackToProfile()
		{
			final PickingJobOptions resolved = options(null, null).fallbackTo(options(null, FORMAT_PROFILE));
			assertThat(resolved.getCuLabelFormatId()).contains(FORMAT_PROFILE);
		}

		@Test
		void formatCustomerSet_wins()
		{
			final PickingJobOptions resolved = options(null, FORMAT_CUSTOMER).fallbackTo(options(null, FORMAT_PROFILE));
			assertThat(resolved.getCuLabelFormatId()).contains(FORMAT_CUSTOMER);
		}

		@Test
		void modeAndFormatResolveIndependently()
		{
			final PickingJobOptions resolved = options(CUQtyEntryMode.SCAN_EACH_CU, null)
					.fallbackTo(options(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY, FORMAT_PROFILE));
			assertThat(resolved.getCuQtyEntryMode()).isEqualTo(CUQtyEntryMode.SCAN_EACH_CU);
			assertThat(resolved.getCuLabelFormatId()).contains(FORMAT_PROFILE);
		}

		@Test
		void noFormatAnywhere_isEmpty()
		{
			final PickingJobOptions resolved = options(null, null).fallbackTo(options(null, null));
			assertThat(resolved.getCuLabelFormatId()).isEmpty();
		}

		@Test
		void enumFlags()
		{
			assertThat(CUQtyEntryMode.MANUAL.isNewMode()).isFalse();
			assertThat(CUQtyEntryMode.SCAN_EACH_CU.isNewMode()).isTrue();
			assertThat(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY.isNewMode()).isTrue();
			assertThat(CUQtyEntryMode.SCAN_EACH_CU.isScanEachCU()).isTrue();
			assertThat(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY.isScanEachCU()).isFalse();
			assertThat(CUQtyEntryMode.ofCode(X_MobileUI_UserProfile_Picking.CUQTYENTRYMODE_SCAN_EACH_CU)).isEqualTo(CUQtyEntryMode.SCAN_EACH_CU);
		}
	}

	/** The repository loads the profile's and the customer job row's mode / format into the options. */
	@Nested
	class RepositoryLoad
	{
		private final BPartnerId customerId = BPartnerId.ofRepoId(2000);

		private MobileUIPickingUserProfileRepository repository;

		@BeforeEach
		void beforeEach()
		{
			repository = new MobileUIPickingUserProfileRepository();

			final I_MobileUI_UserProfile_Picking profile = InterfaceWrapperHelper.newInstance(I_MobileUI_UserProfile_Picking.class);
			profile.setName("test profile");
			profile.setPickingJobAggregationType("sales_order");
			profile.setCreateShipmentPolicy("CO");
			profile.setCUQtyEntryMode(X_MobileUI_UserProfile_Picking.CUQTYENTRYMODE_SCAN_ONCE_ENTER_QTY);
			profile.setC_ScannableCode_Format_ID(FORMAT_PROFILE.getRepoId());
			InterfaceWrapperHelper.saveRecord(profile);

			final I_MobileUI_UserProfile_Picking_Job job = InterfaceWrapperHelper.newInstance(I_MobileUI_UserProfile_Picking_Job.class);
			job.setName("customer job options");
			job.setPickingJobAggregationType("sales_order");
			job.setCreateShipmentPolicy("CO");
			InterfaceWrapperHelper.saveRecord(job);

			final I_MobileUI_UserProfile_Picking_BPartner customer = InterfaceWrapperHelper.newInstance(I_MobileUI_UserProfile_Picking_BPartner.class);
			customer.setMobileUI_UserProfile_Picking_ID(profile.getMobileUI_UserProfile_Picking_ID());
			customer.setC_BPartner_ID(customerId.getRepoId());
			customer.setMobileUI_UserProfile_Picking_Job_ID(job.getMobileUI_UserProfile_Picking_Job_ID());
			InterfaceWrapperHelper.saveRecord(customer);
		}

		private I_MobileUI_UserProfile_Picking_Job customerJob()
		{
			return InterfaceWrapperHelper.load(
					repository.getProfile().getCustomerConfigs().getPickingJobOptionsId(customerId).orElseThrow(() -> new AssertionError("no job options")).getRepoId(),
					I_MobileUI_UserProfile_Picking_Job.class);
		}

		@Test
		void customerJobEmpty_usesProfileModeAndFormat()
		{
			final PickingJobOptions options = repository.getPickingJobOptions(customerId);
			assertThat(options.getCuQtyEntryMode()).isEqualTo(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY);
			assertThat(options.getCuLabelFormatId()).contains(FORMAT_PROFILE);
		}

		@Test
		void customerJobSet_wins_profileForOtherCustomersAndNull()
		{
			final I_MobileUI_UserProfile_Picking_Job job = customerJob();
			job.setCUQtyEntryMode(X_MobileUI_UserProfile_Picking.CUQTYENTRYMODE_SCAN_EACH_CU);
			job.setC_ScannableCode_Format_ID(FORMAT_CUSTOMER.getRepoId());
			InterfaceWrapperHelper.saveRecord(job);

			final PickingJobOptions customerOptions = repository.getPickingJobOptions(customerId);
			assertThat(customerOptions.getCuQtyEntryMode()).isEqualTo(CUQtyEntryMode.SCAN_EACH_CU);
			assertThat(customerOptions.getCuLabelFormatId()).contains(FORMAT_CUSTOMER);

			final PickingJobOptions otherCustomerOptions = repository.getPickingJobOptions(BPartnerId.ofRepoId(2001));
			assertThat(otherCustomerOptions.getCuQtyEntryMode()).isEqualTo(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY);
			assertThat(otherCustomerOptions.getCuLabelFormatId()).contains(FORMAT_PROFILE);
		}
	}
}
