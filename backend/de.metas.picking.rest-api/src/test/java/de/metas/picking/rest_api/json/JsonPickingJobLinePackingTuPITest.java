package de.metas.picking.rest_api.json;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.HUPIItemProduct;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.HuPackingInstructionsItemId;
import de.metas.handlingunits.IHandlingUnitsBL;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

/** {@code JsonPickingJobLine.packingTuPIId}: the TU packing instructions the line's packing names (null = no TU PI, e.g. virtual packing). */
class JsonPickingJobLinePackingTuPITest
{
	private static final HuPackingInstructionsItemId PI_ITEM_ID = HuPackingInstructionsItemId.ofRepoId(540001);
	private static final HuPackingInstructionsId TU_PI_ID = HuPackingInstructionsId.ofRepoId(540002);

	private IHandlingUnitsBL handlingUnitsBL;
	private I_C_UOM uom;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		handlingUnitsBL = mock(IHandlingUnitsBL.class);
		when(handlingUnitsBL.getPackingInstructionsId(any(HuPackingInstructionsItemId.class))).thenReturn(TU_PI_ID);

		uom = newInstance(I_C_UOM.class);
		uom.setUOMSymbol("Stk");
		saveRecord(uom);
	}

	private JsonPickingJobLine toJson(final HUPIItemProduct packingInfo)
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
				.pickingUnit(PickingUnit.CU)
				.build();

		return JsonPickingJobLine.builderFrom(line, handlingUnitsBL, uomId -> TranslatableStrings.constant("Stk"), JsonOpts.ofAdLanguage("en_US"))
				.displayGroupKey("")
				.allowPickingAnyHU(false)
				.cuQtyEntryMode("MANUAL")
				.build();
	}

	private HUPIItemProduct.HUPIItemProductBuilder packing(final int huPiItemProductRepoId)
	{
		return HUPIItemProduct.builder()
				.id(HUPIItemProductId.ofRepoId(huPiItemProductRepoId))
				.name(TranslatableStrings.constant("packing"))
				.piItemId(PI_ITEM_ID);
	}

	@Test
	void tuLine_fourCUsPerTU_hasItsTuPI()
	{
		final HUPIItemProduct packingInfo = packing(540010)
				.productId(ProductId.ofRepoId(10))
				.qtyCUsPerTU(Quantity.of(BigDecimal.valueOf(4), uom))
				.build();

		assertThat(toJson(packingInfo).getPackingTuPIId()).isEqualTo(TU_PI_ID);
		verify(handlingUnitsBL).getPackingInstructionsId(PI_ITEM_ID);
	}

	@Test
	void unlimitedCapacityItemProductOfATuPI_hasThatPI()
	{
		final HUPIItemProduct packingInfo = packing(540011).build(); // qtyCUsPerTU == null: infinite capacity

		assertThat(toJson(packingInfo).getPackingTuPIId()).isEqualTo(TU_PI_ID);
	}

	@Test
	void virtualPacking_hasNoTuPI()
	{
		final HUPIItemProduct packingInfo = packing(HUPIItemProductId.VIRTUAL_HU.getRepoId()).build();

		assertThat(toJson(packingInfo).getPackingTuPIId()).isNull();
		verifyNoInteractions(handlingUnitsBL);
	}
}
