package de.metas.document.dimension;

import de.metas.lang.SOTrx;
import de.metas.order.OrderId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;

import static org.assertj.core.api.Assertions.assertThat;

class OrderLineDimensionFactoryTest
{
	OrderLineDimensionFactory dimensionFactory;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		dimensionFactory = new OrderLineDimensionFactory();
	}

	@Test
	void get_and_set()
	{
		final I_C_OrderLine record = InterfaceWrapperHelper.newInstance(I_C_OrderLine.class);
		final Dimension dimension = DimensionTest.newFullyPopulatedDimension();
		dimensionFactory.updateRecord(record, dimension);
		dimensionFactory.updateRecordUserElements(record, dimension);

		final Dimension dimensionFromRecord = dimensionFactory.getFromRecord(record);
		final Dimension dimensionFromRecordExpected = dimension.toBuilder()
				.userElement1Id(0)
				.userElement2Id(0)
				.build();
		assertThat(dimensionFromRecord).usingRecursiveComparison().isEqualTo(dimensionFromRecordExpected);
		assertThat(dimensionFromRecord).isEqualTo(dimensionFromRecordExpected);
	}

	@Test
	void fieldsThatShallNotBeOverridden()
	{
		final I_C_OrderLine record = InterfaceWrapperHelper.newInstance(I_C_OrderLine.class);
		record.setC_Order_ID(12345);

		dimensionFactory.updateRecord(record, DimensionTest.newFullyPopulatedDimension());

		assertThat(record.getC_Order_ID()).isEqualTo(12345);
	}

	@Nested
	class getFromRecord_check_salesOrderId
	{
		@Builder(builderMethodName = "orderLine", builderClassName = "OrderLineBuilder")
		private I_C_OrderLine createOrderLine(
				@NonNull final SOTrx soTrx,
				@Nullable final OrderId lineOrderSOId)
		{
			final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
			order.setIsSOTrx(soTrx.toBoolean());
			InterfaceWrapperHelper.save(order);

			final I_C_OrderLine orderLine = InterfaceWrapperHelper.newInstance(I_C_OrderLine.class);
			orderLine.setC_Order_ID(order.getC_Order_ID());
			orderLine.setC_OrderSO_ID(OrderId.toRepoId(lineOrderSOId));
			InterfaceWrapperHelper.save(orderLine);

			return orderLine;
		}

		@Test
		void purchaseOrderLine_orderSOId_not_set()
		{
			final I_C_OrderLine orderLine = orderLine().soTrx(SOTrx.PURCHASE).lineOrderSOId(null).build();
			assertThat(dimensionFactory.getFromRecord(orderLine).getSalesOrderId()).isNull();
		}

		@Test
		void purchaseOrderLine_orderSOId_set()
		{
			final I_C_OrderLine orderLine = orderLine().soTrx(SOTrx.PURCHASE).lineOrderSOId(OrderId.ofRepoId(1234)).build();
			assertThat(dimensionFactory.getFromRecord(orderLine).getSalesOrderId()).isEqualTo(OrderId.ofRepoId(1234));
		}

		@Test
		void salesOrderLine_orderSOId_not_set()
		{
			final I_C_OrderLine orderLine = orderLine().soTrx(SOTrx.SALES).lineOrderSOId(null).build();
			assertThat(dimensionFactory.getFromRecord(orderLine).getSalesOrderId()).isEqualTo(OrderId.ofRepoId(orderLine.getC_Order_ID()));
		}

		@Test
		void salesOrderLine_orderSOId_set()
		{
			final I_C_OrderLine orderLine = orderLine().soTrx(SOTrx.SALES).lineOrderSOId(OrderId.ofRepoId(1234)).build();
			assertThat(dimensionFactory.getFromRecord(orderLine).getSalesOrderId()).isEqualTo(OrderId.ofRepoId(1234));
		}

	}
}