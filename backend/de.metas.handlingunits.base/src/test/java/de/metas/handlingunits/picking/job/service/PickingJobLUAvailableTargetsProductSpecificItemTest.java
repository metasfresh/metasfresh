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
import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.IHandlingUnitsBL;
import lombok.NonNull;
import lombok.Value;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
	private IHUPIItemProductDAO huPIItemProductDAO;

	private ProductId productA; // picked
	private ProductId productB; // another product
	private I_M_HU_PI tuPI;
	private I_M_HU_PI_Item tuMaterialItem;
	private I_M_HU_PI_Item_Product tuPIIP_A;
	private I_M_HU_PI_Item_Product tuPIIP_B;
	private I_M_HU_PI genericLUPI;
	private I_M_HU_PI_Item genericLUItem;

	@BeforeEach
	void beforeEach()
	{
		helper = new PickingJobTestHelper();
		huTestHelper = helper.getHuTestHelper();
		handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		huPIItemProductDAO = Services.get(IHUPIItemProductDAO.class);
		Env.setClientId(Env.getCtx(), ClientId.METASFRESH);

		productA = BusinessTestHelper.createProductId("P-A", helper.uomEach);
		productB = BusinessTestHelper.createProductId("P-B", helper.uomEach);

		tuPI = huTestHelper.createHUDefinition("IFCO", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		tuMaterialItem = huTestHelper.createHU_PI_Item_Material(tuPI);
		tuPIIP_A = huTestHelper.assignProduct(tuMaterialItem, productA, new BigDecimal("10"), helper.uomEach);
		tuPIIP_A.setIsDefaultForProduct(true);
		InterfaceWrapperHelper.save(tuPIIP_A);
		tuPIIP_B = huTestHelper.assignProduct(tuMaterialItem, productB, new BigDecimal("10"), helper.uomEach);

		// pallet type linked to the TU PI by an item without product; also the source LUs are built on it
		genericLUPI = huTestHelper.createHUDefinition("GENERIC-PALLET", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		genericLUItem = huTestHelper.createHU_PI_Item_IncludedHU(genericLUPI, tuPI, new BigDecimal("100"));
	}

	/**
	 * @return a pallet type whose only LU->TU item is for {@code itemProductId}
	 */
	/**
	 * @return a pallet type linked to the TU only via an intermediate unit (generic item, level 1) and an item for {@code itemProductId} (level 2)
	 */
	private I_M_HU_PI createChainLUPIWithOnlyProductItemOnLevel2(final ProductId itemProductId)
	{
		final I_M_HU_PI intermediatePI = huTestHelper.createHUDefinition("CRATE-STACK", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		huTestHelper.createHU_PI_Item_IncludedHU(intermediatePI, tuPI, new BigDecimal("5"));
		final I_M_HU_PI chainLUPI = huTestHelper.createHUDefinition("CHAIN-PALLET", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		final I_M_HU_PI_Item chainLUItem = huTestHelper.createHU_PI_Item_IncludedHU(chainLUPI, intermediatePI, new BigDecimal("8"));
		chainLUItem.setM_Product_ID(itemProductId.getRepoId());
		InterfaceWrapperHelper.save(chainLUItem);
		return chainLUPI;
	}

	private I_M_HU_PI createLUPIWithOnlyProductItem(final String name, final ProductId itemProductId)
	{
		return createLUPIWithOnlyProductItem(name, itemProductId, null);
	}

	private I_M_HU_PI createLUPIWithOnlyProductItem(final String name, final ProductId itemProductId, @Nullable final BPartnerId itemPartnerId)
	{
		final I_M_HU_PI luPI = huTestHelper.createHUDefinition(name, X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		final I_M_HU_PI_Item item = huTestHelper.createHU_PI_Item_IncludedHU(luPI, tuPI, new BigDecimal("40"));
		item.setM_Product_ID(itemProductId.getRepoId());
		item.setC_BPartner_ID(BPartnerId.toRepoId(itemPartnerId));
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

		/**
		 * Picks loose pieces of product A, i.e. a VHU (not packed in a TU), as {@code qtyTUs} new TUs onto the line's current LU target.
		 */
		private PickingJob pickLoosePieces(final PickingJob pickingJob, final HUQRCode vhuQRCode, final String qtyTUs)
		{
			final PickingJobLine line = CollectionUtils.singleElement(pickingJob.getLines());
			return helper.pickingJobService.processStepEvent(pickingJob, PickingJobStepEvent.builder()
					.pickingLineId(line.getId())
					.pickingStepId(null)
					.pickFromKey(PickingJobStepPickFromKey.MAIN)
					.eventType(PickingJobStepEventType.PICK)
					.qrCode(vhuQRCode.toScannedCode())
					.qtyPicked(new BigDecimal(qtyTUs))
					.qtyRejectedReasonCode(null)
					.build());
		}

		private PickingJob setNewLUTarget(final PickingJob pickingJob, final I_M_HU_PI luPI)
		{
			final PickingJobLine line = CollectionUtils.singleElement(pickingJob.getLines());
			return helper.pickingJobService.setLUPickingTarget(pickingJob, line.getId(), LUPickingTarget.ofPackingInstructions(idOf(luPI), luPI.getName()));
		}

		private I_M_HU getLUOfFirstPickedHU(final PickingJob pickingJob)
		{
			final HuId pickedHUId = CollectionUtils.singleElement(pickingJob.getLines()).getPickedHUIds().iterator().next();
			final I_M_HU pickedHU = handlingUnitsDAO.getById(pickedHUId);
			return handlingUnitsBL.isLoadingUnit(pickedHU) ? pickedHU : handlingUnitsBL.getLoadingUnitHU(pickedHU);
		}

		/**
		 * @return the LU items (PI items) via which the LU's TUs are attached, one entry per TU
		 */
		private List<Integer> getPIItemIdsOfIncludedTUs(final I_M_HU lu)
		{
			return handlingUnitsDAO.retrieveIncludedHUs(lu)
					.stream()
					.map(tu -> handlingUnitsDAO.retrieveParentItem(tu).getM_HU_PI_Item_ID())
					.collect(Collectors.toList());
		}

		/**
		 * Pallet type with the generic item (30) and an item for A (40): loose pieces of A still go on the generic item (a generic item comes first).
		 */
		@Test
		void loosePieces_ontoNewLUOfPalletWithGenericAndThePickedProductsItem_useTheGenericItem()
		{
			final I_M_HU_PI_Item luItemForA = huTestHelper.createHU_PI_Item_IncludedHU(genericLUPI, tuPI, new BigDecimal("40"));
			luItemForA.setM_Product_ID(productA.getRepoId());
			InterfaceWrapperHelper.save(luItemForA);
			final HUQRCode vhuQRCode = helper.createVHUInfo(productA, "30", "QR-VHU-A").getQrCode();
			final PickingJob pickingJob = setNewLUTarget(createPickingJobForProductA(tuPIIP_A, PickingUnit.TU), genericLUPI);

			final PickingJob after = pickLoosePieces(pickingJob, vhuQRCode, "1");

			final I_M_HU lu = getLUOfFirstPickedHU(after);
			assertThat(getPIItemIdsOfIncludedTUs(lu)).containsExactly(genericLUItem.getM_HU_PI_Item_ID());
		}

		/**
		 * Pallet type whose only item for the TU PI is A's (the target list offers it for a TU line of A): loose pieces of A go on that item.
		 */
		@Test
		void loosePieces_ontoNewLUOfPalletWithOnlyThePickedProductsItem_useThatItem()
		{
			final I_M_HU_PI luPIForA = createLUPIWithOnlyProductItem("PALLET-A", productA);
			final HUQRCode vhuQRCode = helper.createVHUInfo(productA, "30", "QR-VHU-A").getQrCode();
			final PickingJob pickingJob = createPickingJobForProductA(tuPIIP_A, PickingUnit.TU);
			assertThat(lineLUTargets(pickingJob)).contains(idOf(luPIForA));

			final PickingJob after = pickLoosePieces(setNewLUTarget(pickingJob, luPIForA), vhuQRCode, "1");

			final I_M_HU lu = getLUOfFirstPickedHU(after);
			assertThat(lu.getM_HU_PI_Version().getM_HU_PI_ID()).isEqualTo(luPIForA.getM_HU_PI_ID());
			final I_M_HU_PI_Item luItem = getLUPIItemHoldingTheTUs(HuId.ofRepoId(lu.getM_HU_ID()));
			assertThat(luItem.getM_Product_ID()).isEqualTo(productA.getRepoId());
			assertThat(getPIItemIdsOfIncludedTUs(lu)).containsExactly(luItem.getM_HU_PI_Item_ID());
		}

		/**
		 * A whole-TU pick builds an LU on A's only item of PALLET-A; then loose pieces of A are picked onto that existing LU: they go on the same item.
		 */
		@Test
		void loosePieces_ontoExistingLUOfPalletWithOnlyThePickedProductsItem_useThatItem()
		{
			final I_M_HU_PI luPIForA = createLUPIWithOnlyProductItem("PALLET-A", productA);
			final HUQRCode sourceQRCode = createSourceLUOfProductA_3TUs();
			final HUQRCode vhuQRCode = helper.createVHUInfo(productA, "30", "QR-VHU-A").getQrCode();
			final PickingJob afterFirstPick = pickOntoNewLU(createPickingJobForProductA(tuPIIP_A, PickingUnit.TU), luPIForA, sourceQRCode, "1");
			final I_M_HU existingLU = getLUOfFirstPickedHU(afterFirstPick);
			final I_M_HU_PI_Item luItemForA = getLUPIItemHoldingTheTUs(HuId.ofRepoId(existingLU.getM_HU_ID()));
			assertThat(luItemForA.getM_Product_ID()).as("precondition: LU built on product A's item").isEqualTo(productA.getRepoId());
			final HuId existingLUId = HuId.ofRepoId(existingLU.getM_HU_ID());
			final PickingJobLine line = CollectionUtils.singleElement(afterFirstPick.getLines());
			final PickingJob pickingJobOnExistingLU = helper.pickingJobService.setLUPickingTarget(afterFirstPick, line.getId(),
					LUPickingTarget.ofExistingHU(existingLUId, helper.createQRCode(existingLUId, "QR-EXISTING-LU")));

			pickLoosePieces(pickingJobOnExistingLU, vhuQRCode, "1");

			assertThat(getPIItemIdsOfIncludedTUs(handlingUnitsDAO.getById(existingLUId)))
					.containsExactly(luItemForA.getM_HU_PI_Item_ID(), luItemForA.getM_HU_PI_Item_ID());
		}

		private I_M_HU_PI_Item addItemForProductA(final I_M_HU_PI luPI)
		{
			final I_M_HU_PI_Item luItemForA = huTestHelper.createHU_PI_Item_IncludedHU(luPI, tuPI, new BigDecimal("40"));
			luItemForA.setM_Product_ID(productA.getRepoId());
			InterfaceWrapperHelper.save(luItemForA);
			return luItemForA;
		}

		private PickingJob setExistingLUTarget(final PickingJob pickingJob, final I_M_HU lu, final String qrCode)
		{
			final PickingJobLine line = CollectionUtils.singleElement(pickingJob.getLines());
			final HuId luId = HuId.ofRepoId(lu.getM_HU_ID());
			return helper.pickingJobService.setLUPickingTarget(pickingJob, line.getId(), LUPickingTarget.ofExistingHU(luId, helper.createQRCode(luId, qrCode)));
		}

		/**
		 * Pallet type with the generic item (30) and an item for A (40). A whole-TU pick builds the LU on A's item;
		 * loose pieces of A picked onto that existing LU keep the item the LU already uses (not the generic one).
		 */
		@Test
		void loosePieces_ontoExistingLUBuiltOnThePickedProductsItem_keepThatItem()
		{
			final I_M_HU_PI_Item luItemForA = addItemForProductA(genericLUPI);
			final HUQRCode sourceQRCode = createSourceLUOfProductA_3TUs();
			final HUQRCode vhuQRCode = helper.createVHUInfo(productA, "30", "QR-VHU-A").getQrCode();
			final PickingJob afterFirstPick = pickOntoNewLU(createPickingJobForProductA(tuPIIP_A, PickingUnit.TU), genericLUPI, sourceQRCode, "1");
			final I_M_HU existingLU = getLUOfFirstPickedHU(afterFirstPick);
			assertThat(getPIItemIdsOfIncludedTUs(existingLU)).as("precondition: LU built on A's item").containsExactly(luItemForA.getM_HU_PI_Item_ID());

			pickLoosePieces(setExistingLUTarget(afterFirstPick, existingLU, "QR-EXISTING-LU"), vhuQRCode, "1");

			assertThat(getPIItemIdsOfIncludedTUs(handlingUnitsDAO.getById(HuId.ofRepoId(existingLU.getM_HU_ID()))))
					.containsExactly(luItemForA.getM_HU_PI_Item_ID(), luItemForA.getM_HU_PI_Item_ID());
		}

		/**
		 * Control: same pallet type (generic item + A's item), but the existing LU is built on the generic item: loose pieces of A stay on the generic item.
		 */
		@Test
		void loosePieces_ontoExistingLUBuiltOnTheGenericItem_keepTheGenericItem()
		{
			addItemForProductA(genericLUPI);
			final I_M_HU existingLU = huTestHelper.newLUs()
					.huContext(huTestHelper.createMutableHUContextOutOfTransaction())
					.loadingUnitPIItem(genericLUItem)
					.tuPIItemProduct(tuPIIP_A)
					.totalQtyCU(new BigDecimal("10"))
					.locatorId(helper.shipFromLocatorId)
					.huStatus(X_M_HU.HUSTATUS_Active)
					.buildSingleLU();
			assertThat(getPIItemIdsOfIncludedTUs(existingLU)).as("precondition: LU built on the generic item").containsExactly(genericLUItem.getM_HU_PI_Item_ID());
			final HUQRCode vhuQRCode = helper.createVHUInfo(productA, "30", "QR-VHU-A").getQrCode();
			final PickingJob pickingJob = setExistingLUTarget(createPickingJobForProductA(tuPIIP_A, PickingUnit.TU), existingLU, "QR-EXISTING-LU");

			pickLoosePieces(pickingJob, vhuQRCode, "1");

			assertThat(getPIItemIdsOfIncludedTUs(handlingUnitsDAO.getById(HuId.ofRepoId(existingLU.getM_HU_ID()))))
					.containsExactly(genericLUItem.getM_HU_PI_Item_ID(), genericLUItem.getM_HU_PI_Item_ID());
		}

		/**
		 * Pallet type whose only item for the TU PI is another product's: loose pieces of A are refused as before.
		 */
		@Test
		void loosePieces_ontoNewLUOfPalletWithOnlyAnotherProductsItem_areRefused()
		{
			final I_M_HU_PI luPIForB = createLUPIWithOnlyProductItem("PALLET-B", productB);
			final HUQRCode vhuQRCode = helper.createVHUInfo(productA, "30", "QR-VHU-A").getQrCode();
			final PickingJob pickingJob = setNewLUTarget(createPickingJobForProductA(tuPIIP_A, PickingUnit.TU), luPIForB);

			assertThatThrownBy(() -> pickLoosePieces(pickingJob, vhuQRCode, "1"))
					.hasMessageContaining("ERR_LU_HAS_NO_TU_SUB_PACK_INSTR");
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
	 * A line of the job to create: its product and its TU packing instruction ({@code null}: no TU packing, so the line is picked in CUs).
	 */
	@Value(staticConstructor = "of")
	private static class JobLineSpec
	{
		@NonNull ProductId productId;
		@Nullable I_M_HU_PI_Item_Product tuPIItemProduct;
	}

	private PickingJob createPickingJob(final JobLineSpec... lineSpecs)
	{
		final OrderAndLineId firstLine = helper.createOrderAndLineId("salesOrder1");
		for (int i = 0; i < lineSpecs.length; i++)
		{
			final OrderAndLineId orderAndLineId;
			if (i == 0)
			{
				orderAndLineId = firstLine;
			}
			else
			{
				final I_C_OrderLine orderLine = InterfaceWrapperHelper.newInstance(I_C_OrderLine.class);
				orderLine.setC_Order_ID(firstLine.getOrderRepoId());
				InterfaceWrapperHelper.save(orderLine);
				orderAndLineId = OrderAndLineId.ofRepoIds(firstLine.getOrderRepoId(), orderLine.getC_OrderLine_ID());
			}

			final I_M_HU_PI_Item_Product tuPIItemProduct = lineSpecs[i].getTuPIItemProduct();
			helper.packageable()
					.orderAndLineId(orderAndLineId)
					.productId(lineSpecs[i].getProductId())
					.huPIItemProductId(tuPIItemProduct != null ? HUPIItemProductId.ofRepoId(tuPIItemProduct.getM_HU_PI_Item_Product_ID()) : null)
					.qtyToDeliver("30")
					.build();
		}

		final PickingJob pickingJob = helper.pickingJobService.createPickingJob(
						PickingJobCreateRequest.builder()
								.aggregationType(PickingJobAggregationType.SALES_ORDER)
								.pickerId(UserId.ofRepoId(1234))
								.salesOrderId(firstLine.getOrderId())
								.deliveryBPLocationId(helper.shipToBPLocationId)
								.isAllowPickingAnyHU(true)
								.build())
				.withPickingSlot(PickingSlotIdAndCaption.of(helper.pickingSlotId, "TEST"));
		assertThat(pickingJob.getLines()).hasSize(lineSpecs.length);
		return pickingJob;
	}

	/**
	 * The job-level list (no line given) is the union of the line lists.
	 */
	@Nested
	class JobLevel
	{
		@Test
		void tuLineAndCuLine_unionOfTheLineLists()
		{
			final I_M_HU_PI luPIForA = createLUPIWithOnlyProductItem("PALLET-A", productA);
			final I_M_HU_PI luPIForB = createLUPIWithOnlyProductItem("PALLET-B", productB);
			final PickingJob pickingJob = createPickingJob(JobLineSpec.of(productA, tuPIIP_A), JobLineSpec.of(productB, null));
			assertThat(pickingJob.getLines()).extracting(PickingJobLine::getProductId, PickingJobLine::getPickingUnit)
					.containsExactlyInAnyOrder(tuple(productA, PickingUnit.TU), tuple(productB, PickingUnit.CU));

			// A in TUs: generic + A's pallet; B in CUs: generic only
			assertThat(jobLUTargets(pickingJob)).containsExactlyInAnyOrder(idOf(genericLUPI), idOf(luPIForA)).doesNotContain(idOf(luPIForB));
		}

		@Test
		void severalLinesOfTheSameProduct_sameListAsOneLine()
		{
			final I_M_HU_PI luPIForA = createLUPIWithOnlyProductItem("PALLET-A", productA);
			final I_M_HU_PI luPIForB = createLUPIWithOnlyProductItem("PALLET-B", productB);
			final PickingJob pickingJob = createPickingJob(JobLineSpec.of(productA, tuPIIP_A), JobLineSpec.of(productA, tuPIIP_A), JobLineSpec.of(productA, tuPIIP_A));

			assertThat(jobLUTargets(pickingJob)).containsExactlyInAnyOrder(idOf(genericLUPI), idOf(luPIForA)).doesNotContain(idOf(luPIForB));
		}

		/**
		 * TU -> intermediate unit (generic item) -> pallet (item for A only): the product-specific item is on level 2.
		 * Neither a B line in TUs nor an A line in CUs can use that pallet, so it must not be offered.
		 */
		@Test
		void productSpecificItemOnLevel2_notUsableByAnyLine_isNotOffered()
		{
			final I_M_HU_PI chainLUPI = createChainLUPIWithOnlyProductItemOnLevel2(productA);
			final PickingJob pickingJob = createPickingJob(JobLineSpec.of(productB, tuPIIP_B), JobLineSpec.of(productA, null));
			assertThat(pickingJob.getLines()).extracting(PickingJobLine::getProductId, PickingJobLine::getPickingUnit)
					.containsExactlyInAnyOrder(tuple(productB, PickingUnit.TU), tuple(productA, PickingUnit.CU));

			assertThat(jobLUTargets(pickingJob)).containsExactly(idOf(genericLUPI)).doesNotContain(idOf(chainLUPI));
		}

		@Test
		void productSpecificItemOnLevel2_usableByALineInTUs_isOffered()
		{
			final I_M_HU_PI chainLUPI = createChainLUPIWithOnlyProductItemOnLevel2(productA);
			final PickingJob pickingJob = createPickingJob(JobLineSpec.of(productB, tuPIIP_B), JobLineSpec.of(productA, tuPIIP_A));

			assertThat(jobLUTargets(pickingJob)).containsExactlyInAnyOrder(idOf(genericLUPI), idOf(chainLUPI));
		}

		/**
		 * Without product-specific items, the list is what the product-agnostic lookup over all the job's TU items returned before.
		 */
		@Test
		void genericSetup_severalProducts_sameAsTheProductAgnosticLookup()
		{
			final I_M_HU_PI boxPI = huTestHelper.createHUDefinition("BOX", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
			final I_M_HU_PI_Item_Product boxPIIP_B = huTestHelper.assignProduct(huTestHelper.createHU_PI_Item_Material(boxPI), productB, new BigDecimal("6"), helper.uomEach);
			final I_M_HU_PI boxLUPI = huTestHelper.createHUDefinition("BOX-PALLET", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
			huTestHelper.createHU_PI_Item_IncludedHU(boxLUPI, boxPI, new BigDecimal("50"));
			final PickingJob pickingJob = createPickingJob(JobLineSpec.of(productA, tuPIIP_A), JobLineSpec.of(productB, boxPIIP_B), JobLineSpec.of(productA, null));

			final ImmutableSet<HuPackingInstructionsItemId> allTUPIItemIds = huPIItemProductDAO.retrieveForProducts(ImmutableSet.of(productA, productB), pickingJob.getCustomerId())
					.stream()
					.map(itemProduct -> HuPackingInstructionsItemId.ofRepoId(itemProduct.getM_HU_PI_Item_ID()))
					.collect(ImmutableSet.toImmutableSet());
			final List<HuPackingInstructionsId> productAgnosticResult = toLUPIIdsOf(handlingUnitsDAO.retrieveParentLUPIs(allTUPIItemIds, pickingJob.getCustomerId()));

			assertThat(productAgnosticResult).containsExactlyInAnyOrder(idOf(genericLUPI), idOf(boxLUPI));
			assertThat(jobLUTargets(pickingJob)).containsExactlyInAnyOrderElementsOf(productAgnosticResult);
		}
	}

	/**
	 * An item for a partner and a product is used only for that partner's job and that product.
	 */
	@Test
	void partnerAndProductItem_offeredOnlyForThatPartner()
	{
		final BPartnerId jobCustomerId = helper.shipToBPLocationId.getBpartnerId();
		final BPartnerId otherPartnerId = helper.createBPartnerAndLocationId("other-partner").getBpartnerId();
		final I_M_HU_PI luPIForCustomerAndA = createLUPIWithOnlyProductItem("PALLET-CUSTOMER-A", productA, jobCustomerId);
		final I_M_HU_PI luPIForOtherPartnerAndA = createLUPIWithOnlyProductItem("PALLET-OTHER-A", productA, otherPartnerId);
		final PickingJob pickingJob = createPickingJob(JobLineSpec.of(productA, tuPIIP_A));

		assertThat(lineLUTargets(pickingJob)).containsExactlyInAnyOrder(idOf(genericLUPI), idOf(luPIForCustomerAndA)).doesNotContain(idOf(luPIForOtherPartnerAndA));
		assertThat(jobLUTargets(pickingJob)).containsExactlyInAnyOrder(idOf(genericLUPI), idOf(luPIForCustomerAndA));
	}

	private static List<HuPackingInstructionsId> toLUPIIdsOf(final Set<HuPackingInstructionsIdAndCaption> luPIs)
	{
		return luPIs.stream().map(HuPackingInstructionsIdAndCaption::getId).collect(Collectors.toList());
	}
}
