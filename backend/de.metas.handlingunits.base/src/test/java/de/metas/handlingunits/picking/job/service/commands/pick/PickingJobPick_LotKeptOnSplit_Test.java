package de.metas.handlingunits.picking.job.service.commands.pick;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
import de.metas.handlingunits.picking.job.model.HUInfo;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.model.PickingJobStep;
import de.metas.handlingunits.picking.job.model.PickingJobStepEvent;
import de.metas.handlingunits.picking.job.model.PickingJobStepEventType;
import de.metas.handlingunits.picking.job.model.PickingJobStepId;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickFromKey;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickedToHU;
import de.metas.handlingunits.picking.job.model.TUPickingTarget;
import de.metas.handlingunits.picking.job.service.commands.PickingJobCreateRequest;
import de.metas.handlingunits.picking.job.service.commands.PickingJobTestHelper;
import de.metas.order.OrderAndLineId;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.product.ProductId;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Pick from a virtual stock CU that carries a lot number and a
 * best-before date, once into a new TU (box) and once without a TU.
 * <ul>
 * <li>Lot / best-before field hidden by the picking profile: the mobile UI sends no lot / best-before flag
 * ({@code isSetLotNo=false}); the picked CU keeps the values of the CU it was split from.</li>
 * <li>Field shown and cleared by the picker: the UI sends {@code isSetLotNo=true, lotNo=null}; the picked CU is cleared.</li>
 * <li>A second pick into the same box: the box lot follows its CUs (one lot: that lot; mixed lots: empty),
 * also when no lot flag is sent.</li>
 * </ul>
 */
@ExtendWith(AdempiereTestWatcher.class)
class PickingJobPick_LotKeptOnSplit_Test
{
	private static final String LOT = "LOT-A";
	private static final LocalDate BEST_BEFORE = LocalDate.of(2027, 1, 31);

	private PickingJobTestHelper helper;
	private HUTestHelper huTestHelper;
	private IHandlingUnitsDAO handlingUnitsDAO;
	private IHandlingUnitsBL handlingUnitsBL;

	@BeforeEach
	void beforeEach()
	{
		helper = new PickingJobTestHelper();
		huTestHelper = helper.getHuTestHelper();
		handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		handlingUnitsBL = huTestHelper.handlingUnitsBL();
		Env.setClientId(Env.getCtx(), ClientId.METASFRESH);
	}

	private HuPackingInstructionsId createKartonPI(@NonNull final I_M_Product product)
	{
		final I_M_HU_PI tuPI = huTestHelper.createHUDefinition("Karton", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		final I_M_HU_PI_Item miItem = huTestHelper.createHU_PI_Item_Material(tuPI);
		final I_M_HU_PI_Item_Product piip = huTestHelper.assignProduct(miItem, product, new BigDecimal("100"), helper.uomEach);
		piip.setIsDefaultForProduct(true);
		InterfaceWrapperHelper.save(piip);
		return HuPackingInstructionsId.ofRepoId(tuPI.getM_HU_PI_ID());
	}

	private IAttributeStorage attributes(@NonNull final HuId huId)
	{
		final I_M_HU hu = handlingUnitsBL.getById(huId);
		return huTestHelper.createMutableHUContext().getHUAttributeStorageFactory().getAttributeStorage(hu);
	}

	private void setLot(@NonNull final HuId huId, @NonNull final String lot)
	{
		final IAttributeStorage storage = attributes(huId);
		storage.setSaveOnChange(true);
		storage.setValue(AttributeConstants.ATTR_LotNumber, lot);
	}

	private void setBestBefore(@NonNull final HuId huId, @NonNull final LocalDate date)
	{
		final IAttributeStorage storage = attributes(huId);
		storage.setSaveOnChange(true);
		storage.setValue(AttributeConstants.ATTR_BestBeforeDate, date);
	}

	private LocalDate getBestBefore(@NonNull final HuId huId)
	{
		return attributes(huId).getValueAsLocalDate(AttributeConstants.ATTR_BestBeforeDate);
	}

	private String getLot(@NonNull final HuId huId)
	{
		return attributes(huId).getValueAsString(AttributeConstants.ATTR_LotNumber);
	}

	@ParameterizedTest(name = "box={0}, pickerClearedFields={1}")
	@CsvSource({
			"true,  true",
			"true,  false",
			"false, true",
			"false, false",
	})
	void pick3FromStockCU(final boolean intoKarton, final boolean pickerClearedFields)
	{
		final ProductId productId = BusinessTestHelper.createProductId("P-LOT", helper.uomEach);
		final I_M_Product product = InterfaceWrapperHelper.load(productId, I_M_Product.class);
		product.setM_Product_Category_ID(BusinessTestHelper.createProductCategory("Cat", null).getRepoId());
		InterfaceWrapperHelper.save(product);

		final HuPackingInstructionsId kartonPI = createKartonPI(product);
		final HUInfo stockCU = helper.createVHUInfo(productId, "114", "QR-STOCK-CU");
		setLot(stockCU.getId(), LOT);
		setBestBefore(stockCU.getId(), BEST_BEFORE);
		assertThat(getLot(stockCU.getId())).as("precondition: stock CU lot").isEqualTo(LOT);
		assertThat(getBestBefore(stockCU.getId())).as("precondition: stock CU best-before").isEqualTo(BEST_BEFORE);

		final OrderAndLineId orderAndLineId = helper.createOrderAndLineId("SO-LOT");
		helper.packageable().orderAndLineId(orderAndLineId).productId(productId).qtyToDeliver("3").build();

		PickingJob pickingJob = helper.pickingJobService.createPickingJob(
						PickingJobCreateRequest.builder()
								.aggregationType(PickingJobAggregationType.SALES_ORDER)
								.pickerId(UserId.ofRepoId(1234))
								.salesOrderId(orderAndLineId.getOrderId())
								.deliveryBPLocationId(helper.shipToBPLocationId)
								.isAllowPickingAnyHU(false)
								.build())
				.withPickingSlot(PickingSlotIdAndCaption.of(helper.pickingSlotId, "TEST"));
		final PickingJobLine line = CollectionUtils.singleElement(pickingJob.getLines());
		if (intoKarton)
		{
			pickingJob = helper.pickingJobService.setTUPickingTarget(pickingJob, null, TUPickingTarget.ofPackingInstructions(kartonPI, "Karton"));
		}
		final PickingJobStepId stepId = CollectionUtils.singleElement(pickingJob.streamSteps().map(PickingJobStep::getId).collect(ImmutableSet.toImmutableSet()));

		final PickingJobStepEvent.PickingJobStepEventBuilder event = PickingJobStepEvent.builder()
				.pickingLineId(line.getId())
				.pickingStepId(stepId)
				.pickFromKey(PickingJobStepPickFromKey.MAIN)
				.eventType(PickingJobStepEventType.PICK)
				.qrCode(stockCU.getQrCode().toScannedCode())
				.qtyPicked(new BigDecimal("3"))
				.checkIfAlreadyPacked(true);
		if (pickerClearedFields)
		{
			// the lot / best-before fields are shown in the quantity dialog and the picker emptied them
			event.isSetLotNo(true).lotNo(null).isSetBestBeforeDate(true).bestBeforeDate(null);
		}
		pickingJob = helper.pickingJobService.processStepEvent(pickingJob, event.build());

		final List<PickingJobStepPickedToHU> picked = pickingJob.getStepById(stepId).getPickFrom(PickingJobStepPickFromKey.MAIN).getPickedTo().getActualPickedHUs();
		final HuId pickedHUId = CollectionUtils.singleElement(picked).getActualPickedHU().getId();
		final HuId pickedCUId;
		if (intoKarton)
		{
			// the picked-to HU is the CU (a TU part); its parent is the new Karton TU
			final I_M_HU pickedHU = handlingUnitsBL.getById(pickedHUId);
			final List<I_M_HU> cus = handlingUnitsDAO.retrieveIncludedHUs(pickedHU);
			final HuId tuId;
			if (cus.isEmpty())
			{
				pickedCUId = pickedHUId;
				final I_M_HU parent = handlingUnitsDAO.retrieveParent(pickedHU);
				assertThat(parent).as("picked CU sits in a TU").isNotNull();
				tuId = HuId.ofRepoId(parent.getM_HU_ID());
			}
			else
			{
				pickedCUId = HuId.ofRepoId(CollectionUtils.singleElement(cus).getM_HU_ID());
				tuId = pickedHUId;
			}
			assertThat(handlingUnitsBL.isTransportUnit(handlingUnitsBL.getById(tuId))).as("new Karton TU (not virtual)").isTrue();
			System.out.println("LOTREPRO box=true cleared=" + pickerClearedFields + ": TU " + tuId + " lot=" + getLot(tuId) + " / CU " + pickedCUId + " lot=" + getLot(pickedCUId));
		}
		else
		{
			pickedCUId = pickedHUId;
			System.out.println("LOTREPRO box=false cleared=" + pickerClearedFields + ": CU " + pickedCUId + " lot=" + getLot(pickedCUId));
		}

		assertThat(pickedCUId).as("a new CU was split off").isNotEqualTo(stockCU.getId());
		assertThat(getLot(stockCU.getId())).as("stock CU keeps its lot").isEqualTo(LOT);
		assertThat(getBestBefore(stockCU.getId())).as("stock CU keeps its best-before date").isEqualTo(BEST_BEFORE);
		if (pickerClearedFields)
		{
			assertThat(getLot(pickedCUId)).as("picked CU lot, cleared on purpose").isNullOrEmpty();
			assertThat(getBestBefore(pickedCUId)).as("picked CU best-before date, cleared on purpose").isNull();
		}
		else
		{
			assertThat(getLot(pickedCUId)).as("picked CU lot (box=" + intoKarton + ")").isEqualTo(LOT);
			assertThat(getBestBefore(pickedCUId)).as("picked CU best-before date (box=" + intoKarton + ")").isEqualTo(BEST_BEFORE);
		}
	}

	/**
	 * Two picks of 3 into the same Karton, from two stock CUs (lots LOT-A and the second lot), no lot flag sent (field hidden).
	 * The second pick lets the box lot follow its CUs: same lot twice, the lot; two lots, empty (never the first lot).
	 */
	@ParameterizedTest(name = "secondLot={0}, expectedBoxLot={1}")
	@CsvSource(value = {
			"LOT-A, LOT-A",
			"999999, ''",
	})
	void secondPickIntoSameBox_boxLotFollowsItsCUs(final String secondLot, final String expectedBoxLot)
	{
		final ProductId productId = BusinessTestHelper.createProductId("P-LOT", helper.uomEach);
		final I_M_Product product = InterfaceWrapperHelper.load(productId, I_M_Product.class);
		product.setM_Product_Category_ID(BusinessTestHelper.createProductCategory("Cat", null).getRepoId());
		InterfaceWrapperHelper.save(product);

		final HuPackingInstructionsId kartonPI = createKartonPI(product);
		final HUInfo stockCU1 = helper.createVHUInfo(productId, "114", "QR-STOCK-CU-1");
		final HUInfo stockCU2 = helper.createVHUInfo(productId, "114", "QR-STOCK-CU-2");
		setLot(stockCU1.getId(), LOT);
		setLot(stockCU2.getId(), secondLot);

		final OrderAndLineId orderAndLineId = helper.createOrderAndLineId("SO-LOT-2");
		helper.packageable().orderAndLineId(orderAndLineId).productId(productId).qtyToDeliver("6").build();

		PickingJob pickingJob = helper.pickingJobService.createPickingJob(
						PickingJobCreateRequest.builder()
								.aggregationType(PickingJobAggregationType.SALES_ORDER)
								.pickerId(UserId.ofRepoId(1234))
								.salesOrderId(orderAndLineId.getOrderId())
								.deliveryBPLocationId(helper.shipToBPLocationId)
								.isAllowPickingAnyHU(true) // no plan: every scanned stock CU is picked on the fly
								.build())
				.withPickingSlot(PickingSlotIdAndCaption.of(helper.pickingSlotId, "TEST"));
		final List<PickingJobLine> lines = pickingJob.getLines();
		assertThat(lines).hasSize(1);
		pickingJob = helper.pickingJobService.setTUPickingTarget(pickingJob, null, TUPickingTarget.ofPackingInstructions(kartonPI, "Karton"));

		pickingJob = pickFromStockCU(pickingJob, lines.get(0), stockCU1);
		pickingJob = pickFromStockCU(pickingJob, lines.get(0), stockCU2);

		final HuId boxId = pickingJob.getTuPickingTargetEffective(lines.get(0).getId())
				.map(TUPickingTarget::getTuId)
				.orElseThrow(() -> new AssertionError("no existing-TU target after the first pick"));
		assertThat(handlingUnitsBL.isTransportUnit(handlingUnitsBL.getById(boxId))).as("box is a TU").isTrue();
		assertThat(handlingUnitsDAO.retrieveIncludedHUs(handlingUnitsBL.getById(boxId))).as("box holds one CU per pick").hasSize(2);
		assertThat(getLot(boxId)).as("box lot (CU lots " + LOT + " and " + secondLot + ")").isEqualTo(expectedBoxLot.isEmpty() ? null : expectedBoxLot);
	}

	private PickingJob pickFromStockCU(@NonNull final PickingJob pickingJob, @NonNull final PickingJobLine line, @NonNull final HUInfo stockCU)
	{
		return helper.pickingJobService.processStepEvent(pickingJob, PickingJobStepEvent.builder()
				.pickingLineId(line.getId())
				.pickingStepId(null) // picked on the fly, no planned step
				.eventType(PickingJobStepEventType.PICK)
				.qrCode(stockCU.getQrCode().toScannedCode())
				.qtyPicked(new BigDecimal("3"))
				.checkIfAlreadyPacked(true)
				.build());
	}
}
