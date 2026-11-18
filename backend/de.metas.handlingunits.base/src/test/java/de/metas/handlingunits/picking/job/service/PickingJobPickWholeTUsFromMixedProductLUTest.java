package de.metas.handlingunits.picking.job.service;

import com.google.common.collect.ImmutableSet;
import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
import de.metas.handlingunits.picking.job.model.LUPickingTarget;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.model.PickingJobStepEvent;
import de.metas.handlingunits.picking.job.model.PickingJobStepEventType;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickFromKey;
import de.metas.handlingunits.picking.job.model.PickingUnit;
import de.metas.handlingunits.picking.job.service.commands.PickingJobCreateRequest;
import de.metas.handlingunits.picking.job.service.commands.PickingJobTestHelper;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.order.OrderAndLineId;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.product.ProductId;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Mobile picking of whole TUs out of an LU that also holds TUs of another product (the pick-from LU only has to <i>contain</i> the picked product):
 * only TUs of the picked product may be moved onto the new LU.
 */
@ExtendWith(AdempiereTestWatcher.class)
class PickingJobPickWholeTUsFromMixedProductLUTest
{
	private PickingJobTestHelper helper;
	private HUTestHelper huTestHelper;
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	@BeforeEach
	void beforeEach()
	{
		helper = new PickingJobTestHelper();
		huTestHelper = helper.getHuTestHelper();
		Env.setClientId(Env.getCtx(), ClientId.METASFRESH);
	}

	@Test
	void pickWholeTUsFromLU_intoNewLU_onlyPickedProductsTUsAreMoved()
	{
		final ProductId pickedProductId = BusinessTestHelper.createProductId("P-PICKED", helper.uomEach);
		final ProductId otherProductId = BusinessTestHelper.createProductId("P-OTHER", helper.uomEach);

		final I_M_HU_PI tuPI = huTestHelper.createHUDefinition("TU", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		final I_M_HU_PI_Item tuMaterialItem = huTestHelper.createHU_PI_Item_Material(tuPI);
		final I_M_HU_PI_Item_Product pickedProductTUPIIP = huTestHelper.assignProduct(tuMaterialItem, pickedProductId, new BigDecimal("10"), helper.uomEach);
		pickedProductTUPIIP.setIsDefaultForProduct(true);
		InterfaceWrapperHelper.save(pickedProductTUPIIP);
		huTestHelper.assignProduct(tuMaterialItem, otherProductId, new BigDecimal("10"), helper.uomEach);

		final I_M_HU_PI luPI = huTestHelper.createHUDefinition("LU", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		final I_M_HU_PI_Item luItem = huTestHelper.createHU_PI_Item_IncludedHU(luPI, tuPI, new BigDecimal("10"));

		//
		// Source LU: 3 TUs of the picked product (aggregated) + 2 real TUs of the other product.
		// Real TUs are iterated before aggregated ones (IHandlingUnitsDAO.HU_ITEMS_COMPARATOR), so the other product's TUs come first.
		final I_M_HU sourceLU = huTestHelper.newLUs()
				.huContext(huTestHelper.createMutableHUContextOutOfTransaction())
				.loadingUnitPIItem(luItem)
				.tuPIItemProduct(pickedProductTUPIIP)
				.totalQtyCU(new BigDecimal("30"))
				.locatorId(helper.shipFromLocatorId)
				.huStatus(X_M_HU.HUSTATUS_Active)
				.buildSingleLU();
		final HUQRCode sourceLUQRCode = helper.createQRCode(HuId.ofRepoId(sourceLU.getM_HU_ID()), "QR-SOURCE-LU"); // the test helper supports only single-product HUs
		for (int i = 0; i < 2; i++)
		{
			final HuId otherProductTUId = helper.createHU(HuPackingInstructionsId.ofRepoId(tuPI.getM_HU_PI_ID()), otherProductId, helper.qty("10", otherProductId));
			HUTransformService.newInstance().tuToExistingLU(handlingUnitsDAO.getById(otherProductTUId), QtyTU.ONE, sourceLU);
		}

		//
		// Picking job: pick 3 TUs of the picked product onto a new LU
		final OrderAndLineId orderAndLineId = helper.createOrderAndLineId("salesOrder1");
		helper.packageable()
				.orderAndLineId(orderAndLineId)
				.productId(pickedProductId)
				.huPIItemProductId(HUPIItemProductId.ofRepoId(pickedProductTUPIIP.getM_HU_PI_Item_Product_ID()))
				.qtyToDeliver("30")
				.build();
		PickingJob pickingJob = helper.pickingJobService.createPickingJob(
						PickingJobCreateRequest.builder()
								.aggregationType(PickingJobAggregationType.SALES_ORDER)
								.pickerId(UserId.ofRepoId(1234))
								.salesOrderId(orderAndLineId.getOrderId())
								.deliveryBPLocationId(helper.shipToBPLocationId)
								.isAllowPickingAnyHU(true) // no picking plan: the picker scans the source LU, as on the mobile UI
								.build())
				.withPickingSlot(PickingSlotIdAndCaption.of(helper.pickingSlotId, "TEST"));
		final PickingJobLine line = CollectionUtils.singleElement(pickingJob.getLines());
		assertThat(line.getPickingUnit()).isEqualTo(PickingUnit.TU);

		pickingJob = helper.pickingJobService.setLUPickingTarget(pickingJob, line.getId(),
				LUPickingTarget.ofPackingInstructions(HuPackingInstructionsId.ofRepoId(luPI.getM_HU_PI_ID()), "LU"));

		pickingJob = helper.pickingJobService.processStepEvent(pickingJob, PickingJobStepEvent.builder()
				.pickingLineId(line.getId())
				.pickingStepId(null) // step is created when picking
				.pickFromKey(PickingJobStepPickFromKey.MAIN)
				.eventType(PickingJobStepEventType.PICK)
				.qrCode(sourceLUQRCode.toScannedCode())
				.qtyPicked(new BigDecimal("3")) // 3 TUs
				.qtyRejectedReasonCode(null)
				.build());

		//
		// Only the picked product was moved; the other product's TUs stay on the source LU
		assertThat(productsOf(CollectionUtils.singleElement(pickingJob.getLines()).getPickedHUIds()))
				.as("products on the picked HUs")
				.containsOnly(pickedProductId);
		assertThat(productsOf(ImmutableSet.of(HuId.ofRepoId(sourceLU.getM_HU_ID()))))
				.as("products left on the source LU")
				.containsOnly(otherProductId);
	}

	/** @return the products with a positive quantity on the given HUs */
	private List<ProductId> productsOf(final java.util.Collection<HuId> huIds)
	{
		return handlingUnitsDAO.getByIds(huIds)
				.stream()
				.flatMap(hu -> handlingUnitsBL.getStorageFactory().getStorage(hu).getProductStorages().stream())
				.filter(storage -> storage.getQty().isPositive()) // emptied storages remain as zero-qty records
				.map(IHUProductStorage::getProductId)
				.distinct()
				.collect(com.google.common.collect.ImmutableList.toImmutableList());
	}
}
