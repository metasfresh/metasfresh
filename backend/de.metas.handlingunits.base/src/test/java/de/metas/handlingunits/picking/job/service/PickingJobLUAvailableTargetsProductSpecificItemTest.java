package de.metas.handlingunits.picking.job.service;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuPackingInstructionsIdAndCaption;
import de.metas.handlingunits.HuPackingInstructionsItemId;
import org.compiere.model.I_C_OrderLine;
import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
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
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

/**
 * Mobile picking "new LU" targets vs. pallet types (LU PIs) whose link to the TU PI is a product-specific LU->TU item ({@code M_HU_PI_Item.M_Product_ID}).
 * <ul>
 * <li>A pallet type is offered only if it has an item without product or an item for the picked product.</li>
 * <li>A line picked in CUs (loose quantity) builds its new LU without product, so it is offered only pallet types with an item without product.</li>
 * </ul>
 */
@ExtendWith(AdempiereTestWatcher.class)
class PickingJobLUAvailableTargetsProductSpecificItemTest
{
	private PickingJobTestHelper helper;
	private HUTestHelper huTestHelper;
	private IHandlingUnitsDAO handlingUnitsDAO;
	private IHandlingUnitsBL handlingUnitsBL;

	private ProductId productA; // picked
	private ProductId productB; // another product
	private I_M_HU_PI tuPI;
	private I_M_HU_PI_Item tuMaterialItem;
	private I_M_HU_PI_Item_Product tuPIIP_A;
	private I_M_HU_PI genericLUPI;
	private I_M_HU_PI_Item genericLUItem;

	@BeforeEach
	void beforeEach()
	{
		helper = new PickingJobTestHelper();
		huTestHelper = helper.getHuTestHelper();
		handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		Env.setClientId(Env.getCtx(), ClientId.METASFRESH);

		productA = BusinessTestHelper.createProductId("P-A", helper.uomEach);
		productB = BusinessTestHelper.createProductId("P-B", helper.uomEach);

		tuPI = huTestHelper.createHUDefinition("IFCO", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		tuMaterialItem = huTestHelper.createHU_PI_Item_Material(tuPI);
		tuPIIP_A = huTestHelper.assignProduct(tuMaterialItem, productA, new BigDecimal("10"), helper.uomEach);
		tuPIIP_A.setIsDefaultForProduct(true);
		InterfaceWrapperHelper.save(tuPIIP_A);
		huTestHelper.assignProduct(tuMaterialItem, productB, new BigDecimal("10"), helper.uomEach);

		// pallet type linked to the TU PI by an item without product; also the source LUs are built on it
		genericLUPI = huTestHelper.createHUDefinition("GENERIC-PALLET", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		genericLUItem = huTestHelper.createHU_PI_Item_IncludedHU(genericLUPI, tuPI, new BigDecimal("100"));
	}

	/**
	 * @return a pallet type whose only LU->TU item is for {@code itemProductId}
	 */
	private I_M_HU_PI createLUPIWithOnlyProductItem(final String name, final ProductId itemProductId)
	{
		final I_M_HU_PI luPI = huTestHelper.createHUDefinition(name, X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		final I_M_HU_PI_Item item = huTestHelper.createHU_PI_Item_IncludedHU(luPI, tuPI, new BigDecimal("40"));
		item.setM_Product_ID(itemProductId.getRepoId());
		InterfaceWrapperHelper.save(item);
		return luPI;
	}

	/**
	 * @param tuPIItemProduct the line's packing instruction; {@code null} means no TU packing, so the line is picked in CUs
	 */
	private PickingJob createPickingJobForProductA(@Nullable final I_M_HU_PI_Item_Product tuPIItemProduct, final PickingUnit expectedPickingUnit)
	{
		final OrderAndLineId orderAndLineId = helper.createOrderAndLineId("salesOrder1");
		helper.packageable()
				.orderAndLineId(orderAndLineId)
				.productId(productA)
				.huPIItemProductId(tuPIItemProduct != null ? HUPIItemProductId.ofRepoId(tuPIItemProduct.getM_HU_PI_Item_Product_ID()) : null)
				.qtyToDeliver("30")
				.build();
		final PickingJob pickingJob = helper.pickingJobService.createPickingJob(
						PickingJobCreateRequest.builder()
								.aggregationType(PickingJobAggregationType.SALES_ORDER)
								.pickerId(UserId.ofRepoId(1234))
								.salesOrderId(orderAndLineId.getOrderId())
								.deliveryBPLocationId(helper.shipToBPLocationId)
								.isAllowPickingAnyHU(true)
								.build())
				.withPickingSlot(PickingSlotIdAndCaption.of(helper.pickingSlotId, "TEST"));
		assertThat(CollectionUtils.singleElement(pickingJob.getLines()).getPickingUnit()).isEqualTo(expectedPickingUnit);
		return pickingJob;
	}

	private static HuPackingInstructionsId idOf(final I_M_HU_PI pi) {return HuPackingInstructionsId.ofRepoId(pi.getM_HU_PI_ID());}

	private List<HuPackingInstructionsId> lineLUTargets(final PickingJob pickingJob)
	{
		final PickingJobLine line = CollectionUtils.singleElement(pickingJob.getLines());
		return toLUPIIds(helper.pickingJobService.getLUAvailableTargets(pickingJob, line.getId()));
	}

	private List<HuPackingInstructionsId> jobLUTargets(final PickingJob pickingJob)
	{
		return toLUPIIds(helper.pickingJobService.getLUAvailableTargets(pickingJob, null));
	}

	private static List<HuPackingInstructionsId> toLUPIIds(final List<LUPickingTarget> targets)
	{
		return targets.stream().map(LUPickingTarget::getLuPIIdNotNull).collect(Collectors.toList());
	}

	private HUQRCode createSourceLUOfProductA_3TUs()
	{
		final I_M_HU sourceLU = huTestHelper.newLUs()
				.huContext(huTestHelper.createMutableHUContextOutOfTransaction())
				.loadingUnitPIItem(genericLUItem)
				.tuPIItemProduct(tuPIIP_A)
				.totalQtyCU(new BigDecimal("30"))
				.locatorId(helper.shipFromLocatorId)
				.huStatus(X_M_HU.HUSTATUS_Active)
				.buildSingleLU();
		return helper.createQRCode(HuId.ofRepoId(sourceLU.getM_HU_ID()), "QR-SOURCE-LU");
	}

	private PickingJob pickOntoNewLU(PickingJob pickingJob, final I_M_HU_PI luPI, final HUQRCode sourceQRCode, final String qtyTUs)
	{
		final PickingJobLine line = CollectionUtils.singleElement(pickingJob.getLines());
		pickingJob = helper.pickingJobService.setLUPickingTarget(pickingJob, line.getId(), LUPickingTarget.ofPackingInstructions(idOf(luPI), luPI.getName()));
		return helper.pickingJobService.processStepEvent(pickingJob, PickingJobStepEvent.builder()
				.pickingLineId(line.getId())
				.pickingStepId(null)
				.pickFromKey(PickingJobStepPickFromKey.MAIN)
				.eventType(PickingJobStepEventType.PICK)
				.qrCode(sourceQRCode.toScannedCode())
				.qtyPicked(new BigDecimal(qtyTUs))
				.qtyRejectedReasonCode(null)
				.build());
	}

	private I_M_HU_PI_Item getLUPIItemHoldingTheTUs(final HuId pickedHUId)
	{
		final I_M_HU hu = handlingUnitsDAO.getById(pickedHUId);
		final I_M_HU lu = handlingUnitsBL.isLoadingUnit(hu) ? hu : handlingUnitsBL.getLoadingUnitHU(hu);
		assertThat(lu).as("LU of the picked HU").isNotNull();
		final List<I_M_HU_PI_Item> luItemsHoldingTUs = handlingUnitsDAO.retrieveItems(lu)
				.stream()
				.map(I_M_HU_Item::getM_HU_PI_Item_ID)
				.filter(piItemId -> piItemId > 0)
				.map(piItemId -> InterfaceWrapperHelper.load(piItemId, I_M_HU_PI_Item.class))
				.filter(piItem -> piItem.getIncluded_HU_PI_ID() == tuPI.getM_HU_PI_ID())
				.collect(Collectors.toList());
		return CollectionUtils.singleElement(luItemsHoldingTUs);
	}

	/**
	 * The job's line is picked in TUs (whole TUs of product A).
	 */
	@Nested
	class TuLine
	{
		@Test
		void palletTypeWithOnlyAnotherProductsItem_isNotOffered()
		{
			final I_M_HU_PI luPIForB = createLUPIWithOnlyProductItem("PALLET-B", productB);
			final PickingJob pickingJob = createPickingJobForProductA(tuPIIP_A, PickingUnit.TU);

			assertThat(lineLUTargets(pickingJob)).containsExactly(idOf(genericLUPI)).doesNotContain(idOf(luPIForB));
			assertThat(jobLUTargets(pickingJob)).containsExactly(idOf(genericLUPI));
		}

		@Test
		void palletTypeWithOnlyThePickedProductsItem_isOffered_andThePickUsesThatItem()
		{
			final I_M_HU_PI luPIForA = createLUPIWithOnlyProductItem("PALLET-A", productA);
			final HUQRCode sourceQRCode = createSourceLUOfProductA_3TUs();
			final PickingJob pickingJob = createPickingJobForProductA(tuPIIP_A, PickingUnit.TU);

			assertThat(lineLUTargets(pickingJob)).containsExactlyInAnyOrder(idOf(genericLUPI), idOf(luPIForA));
			assertThat(jobLUTargets(pickingJob)).containsExactlyInAnyOrder(idOf(genericLUPI), idOf(luPIForA));

			final PickingJob pickingJobAfterPick = pickOntoNewLU(pickingJob, luPIForA, sourceQRCode, "3");

			final Set<HuId> pickedHUIds = CollectionUtils.singleElement(pickingJobAfterPick.getLines()).getPickedHUIds();
			assertThat(pickedHUIds).isNotEmpty();
			final I_M_HU_PI_Item luItem = getLUPIItemHoldingTheTUs(pickedHUIds.iterator().next());
			assertThat(luItem.getM_HU_PI_Version().getM_HU_PI_ID()).isEqualTo(luPIForA.getM_HU_PI_ID());
			assertThat(luItem.getM_Product_ID()).isEqualTo(productA.getRepoId());
			assertThat(luItem.getQty()).isEqualByComparingTo("40");
		}

		@Test
		void bothPalletTypes_onlyThePickedProductsIsOffered()
		{
			final I_M_HU_PI luPIForA = createLUPIWithOnlyProductItem("PALLET-A", productA);
			final I_M_HU_PI luPIForB = createLUPIWithOnlyProductItem("PALLET-B", productB);
			final PickingJob pickingJob = createPickingJobForProductA(tuPIIP_A, PickingUnit.TU);

			assertThat(lineLUTargets(pickingJob)).containsExactlyInAnyOrder(idOf(genericLUPI), idOf(luPIForA)).doesNotContain(idOf(luPIForB));
		}
	}

	@Test
	void cuLine_looseQty_palletTypeWithOnlyThePickedProductsItem_isNotOffered_genericIs()
	{
		final I_M_HU_PI luPIForA = createLUPIWithOnlyProductItem("PALLET-A", productA);
		final I_M_HU_PI luPIForB = createLUPIWithOnlyProductItem("PALLET-B", productB);
		final PickingJob pickingJob = createPickingJobForProductA(null, PickingUnit.CU);

		assertThat(lineLUTargets(pickingJob)).containsExactly(idOf(genericLUPI)).doesNotContain(idOf(luPIForA), idOf(luPIForB));
		assertThat(jobLUTargets(pickingJob)).containsExactly(idOf(genericLUPI));
	}

	@Test
	void huConsolidationLookup_stillIgnoresTheItemsProduct()
	{
		final I_M_HU_PI luPIForB = createLUPIWithOnlyProductItem("PALLET-B", productB);
		final ImmutableSet<HuPackingInstructionsItemId> tuPIItemIds = ImmutableSet.of(HuPackingInstructionsItemId.ofRepoId(tuMaterialItem.getM_HU_PI_Item_ID()));

		assertThat(toLUPIIdsOf(handlingUnitsDAO.retrieveParentLUPIs(tuPIItemIds, null))).containsExactlyInAnyOrder(idOf(genericLUPI), idOf(luPIForB));
	}

	/**
	 * TU -> intermediate unit (generic item) -> pallet (item for A only): the product rule applies on every level of the chain.
	 */
	@Test
	void productRule_appliesAlongAMultiLevelChain()
	{
		final I_M_HU_PI intermediatePI = huTestHelper.createHUDefinition("CRATE-STACK", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		huTestHelper.createHU_PI_Item_IncludedHU(intermediatePI, tuPI, new BigDecimal("5"));
		final I_M_HU_PI chainLUPI = huTestHelper.createHUDefinition("CHAIN-PALLET-A", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		final I_M_HU_PI_Item chainLUItem = huTestHelper.createHU_PI_Item_IncludedHU(chainLUPI, intermediatePI, new BigDecimal("8"));
		chainLUItem.setM_Product_ID(productA.getRepoId());
		InterfaceWrapperHelper.save(chainLUItem);

		final ImmutableSet<HuPackingInstructionsItemId> tuPIItemIds = ImmutableSet.of(HuPackingInstructionsItemId.ofRepoId(tuMaterialItem.getM_HU_PI_Item_ID()));
		assertThat(toLUPIIdsOf(handlingUnitsDAO.retrieveParentLUPIsForProduct(tuPIItemIds, null, productA))).containsExactlyInAnyOrder(idOf(genericLUPI), idOf(chainLUPI));
		assertThat(toLUPIIdsOf(handlingUnitsDAO.retrieveParentLUPIsForProduct(tuPIItemIds, null, productB))).containsExactly(idOf(genericLUPI));
		assertThat(toLUPIIdsOf(handlingUnitsDAO.retrieveParentLUPIsForProduct(tuPIItemIds, null, null))).containsExactly(idOf(genericLUPI));
		assertThat(toLUPIIdsOf(handlingUnitsDAO.retrieveParentLUPIs(tuPIItemIds, null))).containsExactlyInAnyOrder(idOf(genericLUPI), idOf(chainLUPI));
	}

	/**
	 * Job with a line of A picked in TUs and a line of B picked in CUs: the job-level list is the union of the line lists.
	 */
	@Test
	void jobLevel_isTheUnionOfTheLines()
	{
		final I_M_HU_PI luPIForA = createLUPIWithOnlyProductItem("PALLET-A", productA);
		final I_M_HU_PI luPIForB = createLUPIWithOnlyProductItem("PALLET-B", productB);

		final OrderAndLineId lineA = helper.createOrderAndLineId("salesOrder1");
		final I_C_OrderLine orderLineB = InterfaceWrapperHelper.newInstance(I_C_OrderLine.class);
		orderLineB.setC_Order_ID(lineA.getOrderRepoId());
		InterfaceWrapperHelper.save(orderLineB);
		final OrderAndLineId lineB = OrderAndLineId.ofRepoIds(lineA.getOrderRepoId(), orderLineB.getC_OrderLine_ID());

		helper.packageable().orderAndLineId(lineA).productId(productA).huPIItemProductId(HUPIItemProductId.ofRepoId(tuPIIP_A.getM_HU_PI_Item_Product_ID())).qtyToDeliver("30").build();
		helper.packageable().orderAndLineId(lineB).productId(productB).huPIItemProductId(null).qtyToDeliver("30").build();
		final PickingJob pickingJob = helper.pickingJobService.createPickingJob(
						PickingJobCreateRequest.builder()
								.aggregationType(PickingJobAggregationType.SALES_ORDER)
								.pickerId(UserId.ofRepoId(1234))
								.salesOrderId(lineA.getOrderId())
								.deliveryBPLocationId(helper.shipToBPLocationId)
								.isAllowPickingAnyHU(true)
								.build())
				.withPickingSlot(PickingSlotIdAndCaption.of(helper.pickingSlotId, "TEST"));
		assertThat(pickingJob.getLines()).extracting(PickingJobLine::getProductId, PickingJobLine::getPickingUnit)
				.containsExactlyInAnyOrder(tuple(productA, PickingUnit.TU), tuple(productB, PickingUnit.CU));

		// A in TUs: generic + A's pallet; B in CUs: generic only
		assertThat(jobLUTargets(pickingJob)).containsExactlyInAnyOrder(idOf(genericLUPI), idOf(luPIForA)).doesNotContain(idOf(luPIForB));
	}

	private static List<HuPackingInstructionsId> toLUPIIdsOf(final Set<HuPackingInstructionsIdAndCaption> luPIs)
	{
		return luPIs.stream().map(HuPackingInstructionsIdAndCaption::getId).collect(Collectors.toList());
	}
}
