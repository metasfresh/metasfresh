package de.metas.order.impl;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_SysConfig;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class OrderLineBLGetPriceDateTest
{
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	void purchaseOrder_usesDatePromised_whenSysConfigEnabled()
	{
		final LocalDate dateOrdered = LocalDate.of(2024, 1, 1);
		final LocalDate datePromised = LocalDate.of(2024, 3, 1);

		setSysConfig(OrderLineBL.SYSCONFIG_PO_PRICE_DATE_USE_DATE_PROMISED, "Y");

		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setIsSOTrx(false); // purchase order
		order.setDateOrdered(TimeUtil.asTimestamp(dateOrdered));
		order.setDatePromised(TimeUtil.asTimestamp(datePromised));
		InterfaceWrapperHelper.save(order);

		final I_C_OrderLine orderLine = InterfaceWrapperHelper.newInstance(I_C_OrderLine.class);
		orderLine.setDateOrdered(TimeUtil.asTimestamp(dateOrdered));
		orderLine.setDatePromised(TimeUtil.asTimestamp(datePromised));
		InterfaceWrapperHelper.save(orderLine);

		final ZonedDateTime priceDate = OrderLineBL.getPriceDate(orderLine, order);

		assertThat(priceDate.toLocalDate())
				.as("purchase order with SysConfig=Y must use DatePromised for price list version selection")
				.isEqualTo(datePromised);
	}

	@Test
	void purchaseOrder_usesDateOrdered_whenSysConfigDisabled()
	{
		final LocalDate dateOrdered = LocalDate.of(2024, 1, 1);
		final LocalDate datePromised = LocalDate.of(2024, 3, 1);

		// SysConfig not set → default false → DateOrdered

		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setIsSOTrx(false); // purchase order
		order.setDateOrdered(TimeUtil.asTimestamp(dateOrdered));
		order.setDatePromised(TimeUtil.asTimestamp(datePromised));
		InterfaceWrapperHelper.save(order);

		final I_C_OrderLine orderLine = InterfaceWrapperHelper.newInstance(I_C_OrderLine.class);
		orderLine.setDateOrdered(TimeUtil.asTimestamp(dateOrdered));
		orderLine.setDatePromised(TimeUtil.asTimestamp(datePromised));
		InterfaceWrapperHelper.save(orderLine);

		final ZonedDateTime priceDate = OrderLineBL.getPriceDate(orderLine, order);

		assertThat(priceDate.toLocalDate())
				.as("purchase order without SysConfig must use DateOrdered (default behavior)")
				.isEqualTo(dateOrdered);
	}

	@Test
	void salesOrder_alwaysUsesDatePromised_regardlessOfSysConfig()
	{
		final LocalDate dateOrdered = LocalDate.of(2024, 1, 1);
		final LocalDate datePromised = LocalDate.of(2024, 3, 1);

		// SysConfig deliberately NOT set — SO must use DatePromised unconditionally

		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setIsSOTrx(true); // sales order
		order.setDateOrdered(TimeUtil.asTimestamp(dateOrdered));
		order.setDatePromised(TimeUtil.asTimestamp(datePromised));
		InterfaceWrapperHelper.save(order);

		final I_C_OrderLine orderLine = InterfaceWrapperHelper.newInstance(I_C_OrderLine.class);
		orderLine.setDateOrdered(TimeUtil.asTimestamp(dateOrdered));
		orderLine.setDatePromised(TimeUtil.asTimestamp(datePromised));
		InterfaceWrapperHelper.save(orderLine);

		final ZonedDateTime priceDate = OrderLineBL.getPriceDate(orderLine, order);

		assertThat(priceDate.toLocalDate())
				.as("sales order must always use DatePromised regardless of SysConfig")
				.isEqualTo(datePromised);
	}

	private static void setSysConfig(final String name, final String value)
	{
		final I_AD_SysConfig sysConfig = InterfaceWrapperHelper.newInstance(I_AD_SysConfig.class);
		sysConfig.setName(name);
		sysConfig.setValue(value);
		InterfaceWrapperHelper.save(sysConfig);
	}
}
