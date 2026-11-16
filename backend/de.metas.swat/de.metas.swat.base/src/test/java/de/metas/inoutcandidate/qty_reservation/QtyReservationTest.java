package de.metas.inoutcandidate.qty_reservation;

import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.QtyTU;
import de.metas.material.event.commons.AttributesKey;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QtyReservationTest
{
	private I_C_UOM uom;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		uom = BusinessTestHelper.createUOM("PCE");
	}

	@Test
	void withQty_replacesQtyAndQtyTU()
	{
		final QtyReservation original = QtyReservation.builder()
				.id(QtyReservationId.ofRepoId(1))
				.orderLineId(OrderLineId.ofRepoId(100))
				.productId(ProductId.ofRepoId(1))
				.warehouseId(WarehouseId.ofRepoId(1))
				.attributesKey(AttributesKey.NONE)
				.qtyTU(QtyTU.ofInt(100))
				.qty(Quantity.of(100, uom))
				.qtyDelivered(Quantity.of(0, uom))
				.build();

		final QtyReservation result = original.withQty(Quantity.of(75, uom), QtyTU.ofInt(75));

		assertThat(result.getQty()).isEqualTo(Quantity.of(75, uom));
		assertThat(result.getQtyTU()).isEqualTo(QtyTU.ofInt(75));

		// original instance must be unchanged
		assertThat(original.getQty()).isEqualTo(Quantity.of(100, uom));
		assertThat(original.getQtyTU()).isEqualTo(QtyTU.ofInt(100));
	}
}
