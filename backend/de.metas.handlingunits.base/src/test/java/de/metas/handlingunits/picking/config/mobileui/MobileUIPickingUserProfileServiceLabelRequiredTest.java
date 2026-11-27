package de.metas.handlingunits.picking.config.mobileui;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.HUPIItemProduct;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuPackingInstructionsItemId;
import de.metas.handlingunits.picking.config.PickingConfigRepositoryV2;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.model.PickingJobLineId;
import de.metas.handlingunits.picking.job.model.PickingUnit;
import de.metas.handlingunits.picking.job.service.CreateShipmentPolicy;
import de.metas.i18n.TranslatableStrings;
import de.metas.order.OrderAndLineId;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleId;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.product.ProductValueAndName;
import de.metas.quantity.Quantity;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/** The one rule "a job with a label-required line cannot use pick-all", shared by the pick-all command and the job JSON. */
class MobileUIPickingUserProfileServiceLabelRequiredTest
{
	private static final BPartnerId CUSTOMER_LABEL = BPartnerId.ofRepoId(1);
	private static final BPartnerId CUSTOMER_MANUAL = BPartnerId.ofRepoId(2);

	private MobileUIPickingUserProfileService service;
	private I_C_UOM uom;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		uom = newInstance(I_C_UOM.class);
		uom.setUOMSymbol("Stk");
		saveRecord(uom);

		final MobileUIPickingUserProfileRepository repository = mock(MobileUIPickingUserProfileRepository.class);
		when(repository.getPickingJobOptions(CUSTOMER_LABEL)).thenReturn(options(CUQtyEntryMode.SCAN_EACH_CU));
		when(repository.getPickingJobOptions(CUSTOMER_MANUAL)).thenReturn(options(CUQtyEntryMode.MANUAL));
		service = new MobileUIPickingUserProfileService(repository, mock(PickingConfigRepositoryV2.class));
	}

	private static PickingJobOptions options(final CUQtyEntryMode mode)
	{
		return PickingJobOptions.builder()
				.allowedPickToStructures(AllowedPickToStructures.EMPTY)
				.createShipmentPolicy(CreateShipmentPolicy.DO_NOT_CREATE)
				.cuQtyEntryMode(mode)
				.build();
	}

	private PickingJobLine line(final BPartnerId customerId)
	{
		return PickingJobLine.builder()
				.id(PickingJobLineId.ofRepoId(1))
				.caption(TranslatableStrings.constant("line"))
				.productId(ProductId.ofRepoId(10))
				.productNo("P-1")
				.productCategoryId(ProductCategoryId.ofRepoId(11))
				.productValueAndName(ProductValueAndName.of("P-1", TranslatableStrings.constant("product")))
				.packingInfo(HUPIItemProduct.builder()
						.id(HUPIItemProductId.VIRTUAL_HU)
						.name(TranslatableStrings.constant("virtual"))
						.piItemId(HuPackingInstructionsItemId.ofRepoId(540001))
						.build())
				.qtyToPick(Quantity.of(BigDecimal.ONE, uom))
				.salesOrderAndLineId(OrderAndLineId.ofRepoIds(20, 21))
				.salesOrderDocumentNo("SO-1")
				.orderLineSeqNo(1)
				.deliveryBPLocationId(BPartnerLocationId.ofRepoId(customerId, 100))
				.scheduleId(ShipmentScheduleAndJobScheduleId.ofRepoIds(41, 0))
				.steps(ImmutableList.of())
				.pickingUnit(PickingUnit.CU)
				.build();
	}

	@Test
	void noLine_isNotRequired()
	{
		assertThat(service.isAnyLabelRequiredLine(ImmutableList.<PickingJobLine>of())).isFalse();
	}

	@Test
	void onlyManualLines_isNotRequired()
	{
		assertThat(service.isAnyLabelRequiredLine(ImmutableList.of(line(CUSTOMER_MANUAL), line(CUSTOMER_MANUAL)))).isFalse();
	}

	@Test
	void oneLabelRequiredLineAmongManualOnes_isRequired()
	{
		assertThat(service.isAnyLabelRequiredLine(ImmutableList.of(line(CUSTOMER_MANUAL), line(CUSTOMER_LABEL)))).isTrue();
	}
}
