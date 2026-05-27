package de.metas.inoutcandidate.qty_reservation;

import com.google.common.collect.ImmutableSet;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateBL;
import de.metas.order.OrderLineId;
import de.metas.util.Services;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_QtyReservation;
import org.compiere.model.I_M_Warehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

class QtyReservationServiceCloseAllActiveTest
{
	private QtyReservationService service;

	@BeforeEach
	void setup()
	{
		AdempiereTestHelper.get().init();

		// Register a mocked IShipmentScheduleInvalidateBL because QtyReservationService requires it
		final IShipmentScheduleInvalidateBL mockInvalidateBL = Mockito.mock(IShipmentScheduleInvalidateBL.class);

		final QtyReservationRepository repository = new QtyReservationRepository();
		service = new QtyReservationService(mockInvalidateBL, repository);
	}

	@Test
	void closesActiveReservationsSetsProcessedToY()
	{
		// Arrange — a UOM, product, warehouse, order line, and an active M_QtyReservation
		final I_C_UOM uom = newInstance(I_C_UOM.class);
		save(uom);

		final I_M_Product product = newInstance(I_M_Product.class);
		product.setC_UOM_ID(uom.getC_UOM_ID());
		save(product);

		final I_M_Warehouse warehouse = newInstance(I_M_Warehouse.class);
		save(warehouse);

		final I_C_OrderLine line = newInstance(I_C_OrderLine.class);
		line.setM_Product_ID(product.getM_Product_ID());
		line.setC_UOM_ID(uom.getC_UOM_ID());
		line.setQtyOrdered(new BigDecimal("10"));
		save(line);
		final OrderLineId orderLineId = OrderLineId.ofRepoId(line.getC_OrderLine_ID());

		final I_M_QtyReservation reservation = newInstance(I_M_QtyReservation.class);
		reservation.setC_OrderLine_ID(line.getC_OrderLine_ID());
		reservation.setM_Product_ID(product.getM_Product_ID());
		reservation.setM_Warehouse_ID(warehouse.getM_Warehouse_ID());
		reservation.setC_UOM_ID(uom.getC_UOM_ID());
		reservation.setQty(new BigDecimal("6"));
		reservation.setQtyDelivered(BigDecimal.ZERO);
		reservation.setQtyTU(new BigDecimal("1"));
		reservation.setProcessed(false);
		reservation.setAttributesKey("");                // empty key = AttributesKey.NONE
		reservation.setSupplyType("OH");                 // canonical SupplyType code
		save(reservation);

		// Act
		service.closeAllActiveForOrderLines(ImmutableSet.of(orderLineId));

		// Assert — reload the row and verify Processed=Y (and QtyDelivered now == Qty)
		final I_M_QtyReservation reloaded = Services.get(org.adempiere.ad.dao.IQueryBL.class)
				.createQueryBuilder(I_M_QtyReservation.class)
				.addEqualsFilter(I_M_QtyReservation.COLUMNNAME_C_OrderLine_ID, line.getC_OrderLine_ID())
				.create()
				.firstOnlyNotNull();

		assertThat(reloaded.isProcessed()).as("Processed should be Y after close").isTrue();
		assertThat(reloaded.getQtyDelivered()).as("QtyDelivered should equal Qty").isEqualByComparingTo(new BigDecimal("6"));
	}
}
