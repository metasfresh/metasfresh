package de.metas.handlingunits.picking.candidate.commands;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HUPIItemProduct;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.allocation.transfer.LUTUResult;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.PackToSpec;
import de.metas.handlingunits.picking.candidate.commands.PackToHUsProducer.PackToHURequest;
import de.metas.handlingunits.picking.candidate.commands.PackToHUsProducer.PackToInfo;
import de.metas.handlingunits.picking.job.model.TUPickingTarget;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCodePackingInfo;
import de.metas.handlingunits.qrcodes.model.HUQRCodeUniqueId;
import de.metas.handlingunits.qrcodes.model.HUQRCodeUnitType;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.product.ProductId;
import de.metas.quantity.Capacity;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * CU quantity entry: {@link PackToHURequest#isCuQtyEntry()}.
 * Every case builds the request like the pick command does: checkIfAlreadyPacked(true), createInventoryForMissingQty(true).
 */
@ExtendWith(AdempiereTestWatcher.class)
public class PackToHUsProducerCUQtyEntryTest
{
	private IHandlingUnitsBL handlingUnitsBL;
	private IHUPIItemProductBL huPIItemProductBL;

	private ProcessPickingCandidatesCommandTestHelper helper;
	private I_C_UOM uomEach;
	private ProductId productId;
	private HuPackingInstructionsId tuPIId;
	private PackToHUsProducer producer;

	@BeforeEach
	public void beforeEach()
	{
		helper = new ProcessPickingCandidatesCommandTestHelper();
		handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		huPIItemProductBL = Services.get(IHUPIItemProductBL.class);
		uomEach = helper.uomEach;
		productId = helper.createProduct("P-0001", uomEach);

		final PackToSpec tuSpec = helper.createTUPackingInstructions(productId, Quantity.of("4", uomEach));
		final HUPIItemProduct piip = huPIItemProductBL.getById(tuSpec.getTuPackingInstructionsId());
		tuPIId = handlingUnitsBL.getPackingInstructionsId(piip.getPiItemId());

		producer = PackToHUsProducer.builder()
				.handlingUnitsBL(handlingUnitsBL)
				.huPIItemProductBL(huPIItemProductBL)
				.uomConversionBL(Services.get(IUOMConversionBL.class))
				.inventoryService(helper.inventoryService)
				.build();
	}

	private Quantity qty(final String value) {return Quantity.of(value, uomEach);}

	private I_M_HU vhu(final String qty) {return handlingUnitsBL.getById(helper.createVHU(productId, qty(qty)));}

	private PackToInfo newBox()
	{
		return PackToInfo.builder()
				.shipToBPLocationId(helper.shipToBPLocationId)
				.shipFromLocatorId(helper.shipFromLocatorId)
				.tu(TUPickingTarget.ofPackingInstructions(tuPIId, "TU"))
				.tuCapacity(Capacity.createInfiniteCapacity(productId, uomEach))
				.build();
	}

	private PackToInfo openBox(final I_M_HU box)
	{
		final HUQRCode qrCode = HUQRCode.builder()
				.id(HUQRCodeUniqueId.random())
				.packingInfo(HUQRCodePackingInfo.builder()
						.huUnitType(HUQRCodeUnitType.TU)
						.packingInstructionsId(tuPIId)
						.caption("TU")
						.build())
				.attributes(ImmutableList.of())
				.build();
		return PackToInfo.builder()
				.shipToBPLocationId(helper.shipToBPLocationId)
				.shipFromLocatorId(helper.shipFromLocatorId)
				.tu(TUPickingTarget.ofExistingHU(HuId.ofRepoId(box.getM_HU_ID()), qrCode))
				.build();
	}

	private LUTUResult pack(final I_M_HU source, final String qty, final PackToInfo packToInfo, final boolean newModes)
	{
		final IHUContext huContext = helper.huTestHelper.createMutableHUContextOutOfTransaction();
		return producer.packToHU(PackToHURequest.builder()
				.huContext(huContext)
				.pickFromHUId(HuId.ofRepoId(source.getM_HU_ID()))
				.packToInfo(packToInfo)
				.productId(productId)
				.qtyPicked(qty(qty))
				.documentRef(TableRecordReference.of(source))
				.checkIfAlreadyPacked(true)
				.createInventoryForMissingQty(true)
				.cuQtyEntry(newModes)
				.build());
	}

	private List<I_M_HU> cusOf(final I_M_HU tu) {return handlingUnitsBL.retrieveIncludedHUs(tu);}

	private BigDecimal qtyOf(final I_M_HU hu)
	{
		final IHUStorage storage = helper.huTestHelper.createMutableHUContextOutOfTransaction().getHUStorageFactory().getStorage(hu);
		return storage.getQuantity(productId, uomEach).toBigDecimal();
	}

	/** (i) new box, N=5 from the pieces 4 + 4 -> one CU of 5, result = partial TU whose single CU is that CU. */
	@Test
	public void newBox_scanOnceN5_fromTwoPieces_partialTUWithOneCUOf5()
	{
		// source = a box (TU) holding two pieces 4 + 4: 5 are taken across both pieces (no inventory needed)
		final I_M_HU sourceTU = sourceTUWithTwoPieces();
		assertThat(cusOf(sourceTU)).hasSize(2);
		final I_M_HU piece1 = sourceTU;

		final LUTUResult result = pack(piece1, "5", newBox(), true);

		final LUTUResult.TU tu = result.getSingleTU();
		assertThat(tu.isFullTU()).as("partial TU, the CU is the picked HU").isFalse();
		final List<I_M_HU> cus = cusOf(tu.toHU());
		assertThat(cus).hasSize(1);
		assertThat(qtyOf(cus.get(0))).isEqualByComparingTo("5");
		assertThat(tu.getCUsNotEmpty()).extracting(LUTUResult.TUPart::getId).containsExactly(HuId.ofRepoId(cus.get(0).getM_HU_ID()));
	}

	/** (ii) open box holding 3, N=5 -> a second CU of 5 in the box; the result names that CU. */
	@Test
	public void openBox_with3_n5_oneMoreCUOf5()
	{
		final I_M_HU box = handlingUnitsBL.getById(helper.createHU(tuPIId, productId, qty("3")));
		final I_M_HU source = vhu("8");

		final LUTUResult result = pack(source, "5", openBox(box), true);

		final LUTUResult.TU tu = result.getSingleTU();
		assertThat(tu.isFullTU()).isFalse();
		assertThat(tu.getCUsNotEmpty()).hasSize(1);
		final List<I_M_HU> cus = cusOf(box);
		assertThat(cus).hasSize(2);
		assertThat(cus).extracting(this::qtyOf).usingComparatorForType(BigDecimal::compareTo, BigDecimal.class).containsExactlyInAnyOrder(new BigDecimal("3"), new BigDecimal("5"));
		final I_M_HU cu5 = cus.stream().filter(cu -> qtyOf(cu).compareTo(new BigDecimal("5")) == 0).findFirst().get();
		assertThat(tu.getCUsNotEmpty().get(0).getId()).isEqualTo(HuId.ofRepoId(cu5.getM_HU_ID()));
	}

	/**
	 * (ii) lots A then B into one box (new box, then the open box): each CU keeps its lot and each result names its CU as the picked HU.
	 * Emptying the TU lot (mixed lots) is done afterwards by {@code PickedHUAttributesUpdater} recomputing it from the partial TU's CUs;
	 * without it the HU framework leaves the first lot on the TU, which is not asserted here.
	 */
	@Test
	public void lotsAThenB_intoOneBox_eachCUKeepsItsLot_bothResultsArePartialTUs()
	{
		final I_M_HU sourceA = vhu("10");
		final I_M_HU sourceB = vhu("10");
		setLot(sourceA, "LOT-A");
		setLot(sourceB, "LOT-B");

		final LUTUResult first = pack(sourceA, "2", newBox(), true);
		final I_M_HU box = first.getSingleTU().toHU();
		final LUTUResult second = pack(sourceB, "3", openBox(box), true);
		assertThat(first.getSingleTU().isFullTU()).isFalse();
		assertThat(second.getSingleTU().isFullTU()).isFalse();
		assertThat(lotOf(cusOf(box).stream().filter(cu -> cu.getM_HU_ID() == second.getSingleTU().getCUsNotEmpty().get(0).toHU().getM_HU_ID()).findFirst().get())).isEqualTo("LOT-B");

		final List<I_M_HU> cus = cusOf(box);
		assertThat(cus).hasSize(2);
		assertThat(cus).extracting(cu -> lotOf(cu)).containsExactlyInAnyOrder("LOT-A", "LOT-B");
	}

	private void setLot(final I_M_HU hu, final String lot)
	{
		final IHUContext huContext = helper.huTestHelper.createMutableHUContextOutOfTransaction();
		final IAttributeStorage storage = huContext.getHUAttributeStorageFactory().getAttributeStorage(hu);
		storage.setSaveOnChange(true);
		storage.setValue(helper.huTestHelper.attr_LotNumber, lot);
	}

	private String lotOf(final I_M_HU hu)
	{
		final IHUContext huContext = helper.huTestHelper.createMutableHUContextOutOfTransaction();
		final IAttributeStorage storage = huContext.getHUAttributeStorageFactory().getAttributeStorage(hu);
		return storage.hasAttribute(helper.huTestHelper.attr_LotNumber) ? storage.getValueAsString(helper.huTestHelper.attr_LotNumber) : null;
	}

	/** (iii) new box, Scan each CU, qty 1 -> partial TU, its CU returned. */
	@Test
	public void newBox_scanEachCU_qty1_partialTUWithItsCU()
	{
		final I_M_HU source = vhu("10");

		final LUTUResult result = pack(source, "1", newBox(), true);

		final LUTUResult.TU tu = result.getSingleTU();
		assertThat(tu.isFullTU()).isFalse();
		final List<I_M_HU> cus = cusOf(tu.toHU());
		assertThat(cus).hasSize(1);
		assertThat(qtyOf(cus.get(0))).isEqualByComparingTo("1");
		assertThat(tu.getCUsNotEmpty().get(0).getId()).isEqualTo(HuId.ofRepoId(cus.get(0).getM_HU_ID()));
	}

	/** (iii) flags off: full TU as today. */
	@Test
	public void newBox_flagsOff_fullTU()
	{
		final LUTUResult result = pack(vhu("10"), "1", newBox(), false);

		assertThat(result.getSingleTU().isFullTU()).isTrue();
	}

	/** (iv) new box, qty 1, source = a TU of the line's TU packing holding exactly 1 pack -> a NEW box, the source TU is not the picked HU. */
	@Test
	public void newBox_sourceIsTUWithExactlyOnePack_newBoxIsMade()
	{
		final I_M_HU sourceTU = handlingUnitsBL.getById(helper.createHU(tuPIId, productId, qty("1")));

		final LUTUResult result = pack(sourceTU, "1", newBox(), true);

		final LUTUResult.TU tu = result.getSingleTU();
		assertThat(tu.isFullTU()).isFalse();
		assertThat(tu.getId()).as("the source TU is not the picked box").isNotEqualTo(HuId.ofRepoId(sourceTU.getM_HU_ID()));
		final List<I_M_HU> cus = cusOf(tu.toHU());
		assertThat(cus).hasSize(1);
		assertThat(tu.getCUsNotEmpty().get(0).getId()).isEqualTo(HuId.ofRepoId(cus.get(0).getM_HU_ID()));
	}

	/** (iv) flags off: the source TU is taken as the box, as today. */
	@Test
	public void newBox_sourceIsTUWithExactlyOnePack_flagsOff_sourceTUIsTheBox()
	{
		final I_M_HU sourceTU = handlingUnitsBL.getById(helper.createHU(tuPIId, productId, qty("1")));

		final LUTUResult result = pack(sourceTU, "1", newBox(), false);

		assertThat(result.getSingleTU().getId()).isEqualTo(HuId.ofRepoId(sourceTU.getM_HU_ID()));
	}

	/** Flags off, open box: full TU as today; the 5 pieces are added as CUs (one source CU of 8 -> 5 taken). */
	@Test
	public void openBox_flagsOff_fullTUAsToday()
	{
		final I_M_HU box = handlingUnitsBL.getById(helper.createHU(tuPIId, productId, qty("3")));

		final LUTUResult result = pack(vhu("8"), "5", openBox(box), false);

		assertThat(result.getSingleTU().isFullTU()).isTrue();
		assertThat(cusOf(box)).extracting(this::qtyOf).usingComparatorForType(BigDecimal::compareTo, BigDecimal.class)
				.containsExactlyInAnyOrder(new BigDecimal("3"), new BigDecimal("5"));
	}

	/** Flags off, open box, the source holds several pieces (4 + 4): today's error. */
	@Test
	public void openBox_fromTwoPieces_flagsOff_failsAsToday()
	{
		final I_M_HU box = handlingUnitsBL.getById(helper.createHU(tuPIId, productId, qty("3")));
		final I_M_HU sourceTU = sourceTUWithTwoPieces();

		assertThatThrownBy(() -> pack(sourceTU, "5", openBox(box), false))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("exactly one");
	}

	/** open box holding 3, the source holds two pieces 4 + 4, N=5 -> one new CU of 5 in the box (needs the consolidation to one CU). */
	@Test
	public void openBox_fromTwoPieces_n5_oneCUOf5()
	{
		final I_M_HU box = handlingUnitsBL.getById(helper.createHU(tuPIId, productId, qty("3")));
		final I_M_HU sourceTU = sourceTUWithTwoPieces();

		final LUTUResult result = pack(sourceTU, "5", openBox(box), true);

		assertThat(result.getSingleTU().isFullTU()).isFalse();
		assertThat(cusOf(box)).extracting(this::qtyOf).usingComparatorForType(BigDecimal::compareTo, BigDecimal.class)
				.containsExactlyInAnyOrder(new BigDecimal("3"), new BigDecimal("5"));
		assertThat(result.getSingleTU().getCUsNotEmpty()).hasSize(1);
	}

	private I_M_HU sourceTUWithTwoPieces()
	{
		final I_M_HU sourceTU = handlingUnitsBL.getById(helper.createHU(tuPIId, productId, qty("4")));
		final IHUContext sourceContext = helper.huTestHelper.createMutableHUContextOutOfTransaction();
		HUTransformService.newInstance(sourceContext).addCUsToTU(ImmutableList.of(vhu("4")), sourceTU);
		return sourceTU;
	}
}
