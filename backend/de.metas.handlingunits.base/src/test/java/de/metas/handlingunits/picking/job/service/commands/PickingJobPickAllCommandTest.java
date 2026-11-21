package de.metas.handlingunits.picking.job.service.commands;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.config.mobileui.CUQtyEntryMode;
import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.i18n.AdMessageKey;
import de.metas.order.OrderAndLineId;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.product.ProductId;
import de.metas.user.UserId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_MobileUI_UserProfile_Picking;
import org.compiere.model.I_MobileUI_UserProfile_Picking_BPartner;
import org.compiere.model.I_MobileUI_UserProfile_Picking_Job;
import org.compiere.model.X_MobileUI_UserProfile_Picking;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.annotation.Nullable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Quick Pack All ({@link PickingJobService#pickAll}, the entry behind {@code POST /picking/job/{id}/pickAll}) is refused
 * when ANY line of the job has an effective new CU quantity entry mode (customer value, else profile value; resolved per line).
 */
@ExtendWith(AdempiereTestWatcher.class)
class PickingJobPickAllCommandTest
{
	private static final AdMessageKey CU_QUICK_PACK_ALL_NOT_ALLOWED = AdMessageKey.of("de.metas.handlingunits.picking.job.CU_QUICK_PACK_ALL_NOT_ALLOWED");
	private static final UserId PICKER_ID = UserId.ofRepoId(1234);

	private PickingJobTestHelper helper;
	private ProductId productId;
	private HuId vhuId;

	@BeforeEach
	void beforeEach()
	{
		helper = new PickingJobTestHelper();
		productId = BusinessTestHelper.createProductId("P1", helper.uomEach);
		vhuId = helper.createVHU(productId, "300");
		helper.createQRCode(vhuId, "QR-VHU1");
	}

	/** one line, effective mode SCAN_ONCE_ENTER_QTY from the profile */
	@ParameterizedTest
	@ValueSource(booleans = { true, false })
	void profileNewMode_refused(final boolean allowQuickPackAll)
	{
		createProfile(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY, allowQuickPackAll);
		final PickingJob job = createSalesOrderJob("salesOrder-i", helper.shipToBPLocationId);
		assertRefusedAndNothingPicked(job);
	}

	/** one line, profile MANUAL + customer record SCAN_EACH_CU: the customer wins */
	@ParameterizedTest
	@ValueSource(booleans = { true, false })
	void customerNewMode_overProfileManual_refused(final boolean allowQuickPackAll)
	{
		final I_MobileUI_UserProfile_Picking profile = createProfile(CUQtyEntryMode.MANUAL, allowQuickPackAll);
		createCustomerRecord(profile, helper.shipToBPLocationId.getBpartnerId(), CUQtyEntryMode.SCAN_EACH_CU);
		final PickingJob job = createSalesOrderJob("salesOrder-ii", helper.shipToBPLocationId);
		assertRefusedAndNothingPicked(job);
	}

	/** product job, two lines of two customers: customer A MANUAL, customer B SCAN_EACH_CU (mixed = whole job refused) */
	@ParameterizedTest
	@ValueSource(booleans = { true, false })
	void mixedProductJob_refused(final boolean allowQuickPackAll)
	{
		final I_MobileUI_UserProfile_Picking profile = createProfile(CUQtyEntryMode.MANUAL, allowQuickPackAll);
		final BPartnerLocationId customerA = helper.createBPartnerAndLocationId("CustomerA");
		final BPartnerLocationId customerB = helper.createBPartnerAndLocationId("CustomerB");
		createCustomerRecord(profile, customerB.getBpartnerId(), CUQtyEntryMode.SCAN_EACH_CU);

		createPackageable("salesOrder-iii-A", customerA);
		createPackageable("salesOrder-iii-B", customerB);
		final PickingJob job = withSavedPickingSlot(helper.pickingJobService.createPickingJob(
				PickingJobCreateRequest.builder()
						.aggregationType(PickingJobAggregationType.PRODUCT)
						.pickerId(PICKER_ID)
						.isAllowPickingAnyHU(false)
						.build()));
		assertThat(job.getLines()).as("one line per customer").hasSize(2);

		assertRefusedAndNothingPicked(job);
	}

	/** one line, MANUAL in profile and customer record, IsAllowQuickPackAll = Y: as today, every line picked and the job completed */
	@Test
	void manualOnly_pickedAndCompleted()
	{
		final I_MobileUI_UserProfile_Picking profile = createProfile(CUQtyEntryMode.MANUAL, true);
		createCustomerRecord(profile, helper.shipToBPLocationId.getBpartnerId(), CUQtyEntryMode.MANUAL);
		final PickingJob job = createSalesOrderJob("salesOrder-iv", helper.shipToBPLocationId);

		final PickingJob result = helper.pickingJobService.pickAll(job.getId(), PICKER_ID);

		assertThat(result.getDocStatus().isCompleted()).as("job completed").isTrue();
		assertThat(result.getLines()).allSatisfy(line -> assertThat(line.getQtyPicked().toBigDecimal()).as("line picked at its open quantity").isEqualByComparingTo("100"));
	}

	private void assertRefusedAndNothingPicked(final PickingJob job)
	{
		final PickingJobId jobId = job.getId();
		assertThatThrownBy(() -> helper.pickingJobService.pickAll(jobId, PICKER_ID))
				.isInstanceOf(AdempiereException.class)
				.satisfies(ex -> assertThat(((AdempiereException)ex).getErrorCode()).isEqualTo(CU_QUICK_PACK_ALL_NOT_ALLOWED.toAD_Message()));

		final PickingJob jobAfter = helper.pickingJobService.getById(jobId);
		assertThat(jobAfter.getDocStatus().isCompleted()).as("job not completed").isFalse();
		assertThat(jobAfter.isProcessed()).as("job still open").isFalse();
		assertThat(jobAfter.isNothingPicked()).as("nothing picked").isTrue();
		assertThat(jobAfter.getLines()).allSatisfy(line -> assertThat(line.getPickedHUIds()).as("no CU created").isEmpty());
	}

	private I_MobileUI_UserProfile_Picking createProfile(final CUQtyEntryMode mode, final boolean allowQuickPackAll)
	{
		final I_MobileUI_UserProfile_Picking profile = InterfaceWrapperHelper.newInstance(I_MobileUI_UserProfile_Picking.class);
		profile.setName("test profile");
		profile.setPickingJobAggregationType("sales_order");
		profile.setCreateShipmentPolicy(X_MobileUI_UserProfile_Picking.CREATESHIPMENTPOLICY_DO_NOT_CREATE);
		profile.setCUQtyEntryMode(mode.getCode());
		profile.setIsAllowQuickPackAll(allowQuickPackAll);
		InterfaceWrapperHelper.saveRecord(profile);
		return profile;
	}

	private void createCustomerRecord(final I_MobileUI_UserProfile_Picking profile, final BPartnerId customerId, @Nullable final CUQtyEntryMode mode)
	{
		final I_MobileUI_UserProfile_Picking_Job jobOptions = InterfaceWrapperHelper.newInstance(I_MobileUI_UserProfile_Picking_Job.class);
		jobOptions.setName("customer job options " + customerId.getRepoId());
		jobOptions.setPickingJobAggregationType("sales_order");
		jobOptions.setCreateShipmentPolicy(X_MobileUI_UserProfile_Picking.CREATESHIPMENTPOLICY_DO_NOT_CREATE);
		jobOptions.setCUQtyEntryMode(mode != null ? mode.getCode() : null);
		InterfaceWrapperHelper.saveRecord(jobOptions);

		final I_MobileUI_UserProfile_Picking_BPartner customer = InterfaceWrapperHelper.newInstance(I_MobileUI_UserProfile_Picking_BPartner.class);
		customer.setMobileUI_UserProfile_Picking_ID(profile.getMobileUI_UserProfile_Picking_ID());
		customer.setC_BPartner_ID(customerId.getRepoId());
		customer.setMobileUI_UserProfile_Picking_Job_ID(jobOptions.getMobileUI_UserProfile_Picking_Job_ID());
		InterfaceWrapperHelper.saveRecord(customer);
	}

	private OrderAndLineId createPackageable(final String orderDocNo, final BPartnerLocationId shipToBPLocationId)
	{
		final OrderAndLineId orderAndLineId = helper.createOrderAndLineId(orderDocNo);
		helper.packageable()
				.orderAndLineId(orderAndLineId)
				.productId(productId)
				.shipToBPLocationId(shipToBPLocationId)
				.qtyToDeliver("100")
				.build();
		return orderAndLineId;
	}

	private PickingJob createSalesOrderJob(final String orderDocNo, final BPartnerLocationId shipToBPLocationId)
	{
		final OrderAndLineId orderAndLineId = createPackageable(orderDocNo, shipToBPLocationId);
		return withSavedPickingSlot(helper.pickingJobService.createPickingJob(
				PickingJobCreateRequest.builder()
						.aggregationType(PickingJobAggregationType.SALES_ORDER)
						.pickerId(PICKER_ID)
						.salesOrderId(orderAndLineId.getOrderId())
						.deliveryBPLocationId(shipToBPLocationId)
						.isAllowPickingAnyHU(false)
						.build()));
	}

	/** pickAll reloads the job, so the picking slot (normally scanned in the app) must be persisted */
	private PickingJob withSavedPickingSlot(final PickingJob job)
	{
		final PickingJob jobWithSlot = job.withPickingSlot(PickingSlotIdAndCaption.of(helper.pickingSlotId, "TEST"));
		new PickingJobRepository().save(jobWithSlot);
		return jobWithSlot;
	}
}
