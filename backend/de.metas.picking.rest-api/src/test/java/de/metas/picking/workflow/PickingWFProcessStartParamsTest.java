package de.metas.picking.workflow;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
import de.metas.order.OrderId;
import de.metas.product.ProductId;
import org.adempiere.warehouse.WarehouseTypeId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PickingWFProcessStartParamsTest
{
	@Test
	void toParams_ofParams()
	{
		final PickingWFProcessStartParams pickingParams = PickingWFProcessStartParams.builder()
				.aggregationType(PickingJobAggregationType.SALES_ORDER)
				.salesOrderId(OrderId.ofRepoId(1))
				.deliveryBPLocationId(BPartnerLocationId.ofRepoId(2, 3))
				.warehouseTypeId(WarehouseTypeId.ofRepoId(4))
				.productId(ProductId.ofRepoId(5))
				.build();

		Assertions.assertThat(PickingWFProcessStartParams.ofParams(pickingParams.toParams()))
				.usingRecursiveComparison()
				.isEqualTo(pickingParams);
	}
}