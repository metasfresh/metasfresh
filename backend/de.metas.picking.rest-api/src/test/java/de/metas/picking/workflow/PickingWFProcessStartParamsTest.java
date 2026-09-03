package de.metas.picking.workflow;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
import de.metas.order.OrderId;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleId;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleIdSet;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseTypeId;
import org.assertj.core.api.Assertions;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PickingWFProcessStartParamsTest
{
	private I_C_UOM each;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		each = BusinessTestHelper.createUomEach();
	}

	@Test
	void toParams_ofParams()
	{
		final PickingWFProcessStartParams pickingParams = PickingWFProcessStartParams.builder()
				.aggregationType(PickingJobAggregationType.SALES_ORDER)
				.salesOrderId(OrderId.ofRepoId(1))
				.deliveryBPLocationId(BPartnerLocationId.ofRepoId(2, 3))
				.warehouseTypeId(WarehouseTypeId.ofRepoId(4))
				.productId(ProductId.ofRepoId(5))
				.qtyToDeliver(Quantity.of("321", each))
				.qtyAvailableToPick(Quantity.of("123", each))
				.scheduleIds(ShipmentScheduleAndJobScheduleIdSet.ofCollection(ImmutableSet.of(
						ShipmentScheduleAndJobScheduleId.ofRepoIds(1, 2),
						ShipmentScheduleAndJobScheduleId.ofRepoIds(3, -1)
				)))
				.build();

		Assertions.assertThat(PickingWFProcessStartParams.ofParams(pickingParams.toParams()))
				.usingRecursiveComparison()
				.isEqualTo(pickingParams);
	}
}