package de.metas.picking.rest_api.json;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.HUPIItemProduct;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuPackingInstructionsItemId;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.model.PickingJobLineId;
import de.metas.handlingunits.picking.job.model.PickingUnit;
import de.metas.i18n.TranslatableStrings;
import de.metas.order.OrderAndLineId;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleId;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.product.ProductValueAndName;
import de.metas.quantity.Quantity;
import de.metas.workflow.rest_api.controller.v2.json.JsonOpts;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/** {@code JsonPickingJobLine.builderFrom}: unit and quantities of a line on a TU packing, per picking unit. */
class JsonPickingJobLineTuPackingTest
{
	private static final HuPackingInstructionsItemId PI_ITEM_ID = HuPackingInstructionsItemId.ofRepoId(540001);

	private I_C_UOM uom;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		uom = newInstance(I_C_UOM.class);
		uom.setUOMSymbol("Stk");
		saveRecord(uom);
	}

	private JsonPickingJobLine toJson(final HUPIItemProduct packingInfo)
	{
		return toJson(packingInfo, PickingUnit.CU, "MANUAL");
	}

	private JsonPickingJobLine toJson(final HUPIItemProduct packingInfo, final PickingUnit pickingUnit, final String cuQtyEntryMode)
	{
		final PickingJobLine line = PickingJobLine.builder()
				.id(PickingJobLineId.ofRepoId(1))
				.caption(TranslatableStrings.constant("line"))
				.productId(ProductId.ofRepoId(10))
				.productNo("P-1")
				.productCategoryId(ProductCategoryId.ofRepoId(11))
				.productValueAndName(ProductValueAndName.of("P-1", TranslatableStrings.constant("product")))
				.packingInfo(packingInfo)
				.qtyToPick(Quantity.of(BigDecimal.valueOf(8), uom))
				.salesOrderAndLineId(OrderAndLineId.ofRepoIds(20, 21))
				.salesOrderDocumentNo("SO-1")
				.orderLineSeqNo(10)
				.deliveryBPLocationId(BPartnerLocationId.ofRepoId(30, 31))
				.scheduleId(ShipmentScheduleAndJobScheduleId.ofRepoIds(40, 0))
				.steps(ImmutableList.of())
				.pickingUnit(pickingUnit)
				.build();

		return JsonPickingJobLine.builderFrom(line, uomId -> TranslatableStrings.constant("Stk"), JsonOpts.ofAdLanguage("en_US"))
				.displayGroupKey("")
				.allowPickingAnyHU(false)
				.cuQtyEntryMode(cuQtyEntryMode)
				.build();
	}

	private HUPIItemProduct.HUPIItemProductBuilder packing(final int huPiItemProductRepoId)
	{
		return HUPIItemProduct.builder()
				.id(HUPIItemProductId.ofRepoId(huPiItemProductRepoId))
				.name(TranslatableStrings.constant("packing"))
				.piItemId(PI_ITEM_ID);
	}

	/** a label-based-mode line on a box (TU) packing counts CUs: the loader gives it picking unit CU, the JSON must then report uom = CU symbol and quantities in CUs (8 CU, not 2 TU) */
	@Test
	void labelBasedModeLineOnTuPacking_countsCUs()
	{
		final HUPIItemProduct packingInfo = packing(540010)
				.productId(ProductId.ofRepoId(10))
				.qtyCUsPerTU(Quantity.of(BigDecimal.valueOf(4), uom))
				.build();

		final JsonPickingJobLine json = toJson(packingInfo, PickingUnit.CU, "SCAN_ONCE_ENTER_QTY");

		assertThat(json.getPickingUnit()).isEqualTo(PickingUnit.CU);
		assertThat(json.getUom()).isEqualTo("Stk");
		assertThat(json.getQtyToPick()).isEqualByComparingTo("8");
		assertThat(json.getQtyRemainingToPick()).isEqualByComparingTo("8");
	}

	/** control: the same packing with picking unit TU (Manual) counts TUs: uom "TU", 2 TU */
	@Test
	void manualLineOnTuPacking_countsTUs()
	{
		final HUPIItemProduct packingInfo = packing(540010)
				.productId(ProductId.ofRepoId(10))
				.qtyCUsPerTU(Quantity.of(BigDecimal.valueOf(4), uom))
				.build();

		final JsonPickingJobLine json = toJson(packingInfo, PickingUnit.TU, "MANUAL");

		assertThat(json.getPickingUnit()).isEqualTo(PickingUnit.TU);
		assertThat(json.getUom()).isEqualTo("TU");
		assertThat(json.getQtyToPick()).isEqualByComparingTo("2");
	}
}
