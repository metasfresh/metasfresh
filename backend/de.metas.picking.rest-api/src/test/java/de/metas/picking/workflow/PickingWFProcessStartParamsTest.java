package de.metas.picking.workflow;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.order.OrderId;
import org.adempiere.warehouse.WarehouseTypeId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PickingWFProcessStartParamsTest
{
	@Test
	void toParams_ofParams()
	{
		final PickingWFProcessStartParams pickingParams = PickingWFProcessStartParams.builder()
				.salesOrderId(OrderId.ofRepoId(1))
				.deliveryBPLocationId(BPartnerLocationId.ofRepoId(2, 3))
				.warehouseTypeId(WarehouseTypeId.ofRepoId(4))
				.build();

		Assertions.assertThat(PickingWFProcessStartParams.ofParams(pickingParams.toParams()))
				.usingRecursiveComparison()
				.isEqualTo(pickingParams);
	}
}