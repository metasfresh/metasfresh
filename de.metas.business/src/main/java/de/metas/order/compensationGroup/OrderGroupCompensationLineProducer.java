package de.metas.order.compensationGroup;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_C_OrderLine;
import org.compiere.util.Util;

import com.google.common.collect.ImmutableList;

import de.metas.order.IOrderLineBL;
import de.metas.product.IProductBL;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2017 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Creates the sales order compensation line for a given order line compensation group
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public class OrderGroupCompensationLineProducer
{
	public static final OrderGroupCompensationLineProducer newInstance()
	{
		return new OrderGroupCompensationLineProducer();
	}

	private final transient IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
	private final transient IProductBL productBL = Services.get(IProductBL.class);

	private List<I_C_OrderLine> _groupOrderLines;
	private int _compensationProductId = -1;

	private OrderGroupCompensationLineProducer()
	{
	}

	public OrderGroupCompensationLineProducer groupOrderLines(final List<I_C_OrderLine> groupOrderLines)
	{
		_groupOrderLines = groupOrderLines.stream()
				.peek(OrderGroupCompensationUtils::assertNotCompensationLine)
				.collect(ImmutableList.toImmutableList());
		return this;
	}

	private List<I_C_OrderLine> getGroupOrderLines()
	{
		Check.assumeNotEmpty(_groupOrderLines, "groupOrderLines is not empty");
		return _groupOrderLines;
	}

	public OrderGroupCompensationLineProducer compensationProductId(final int compensationProductId)
	{
		_compensationProductId = compensationProductId;
		return this;
	}

	private int getCompensationProductId()
	{
		Check.assume(_compensationProductId > 0, "compensationProductId > 0");
		return _compensationProductId;
	}

	public I_C_OrderLine createCompensationLine()
	{
		final List<I_C_OrderLine> groupOrderLines = getGroupOrderLines();
		final I_C_Order order = groupOrderLines.get(0).getC_Order();

		final int groupNo = groupOrderLines.stream()
				.map(I_C_OrderLine::getGroupNo)
				.distinct()
				.collect(GuavaCollectors.singleElementOrThrow(() -> new AdempiereException("Order lines shall be part of the same group")));

		final I_C_OrderLine compensationLine = orderLineBL.createOrderLine(order);
		compensationLine.setGroupNo(groupNo);
		compensationLine.setIsGroupCompensationLine(true);

		final I_M_Product product = load(getCompensationProductId(), I_M_Product.class);
		compensationLine.setM_Product(product);
		compensationLine.setC_UOM(productBL.getStockingUOM(product));
		compensationLine.setGroupCompensationType(extractGroupCompensationType(product));
		compensationLine.setGroupCompensationAmtType(extractGroupCompensationAmtType(product));

		setDefaultAmounts(compensationLine);

		save(compensationLine);

		return compensationLine;
	}

	private static final String extractGroupCompensationType(final I_M_Product product)
	{
		return Util.coalesce(product.getGroupCompensationType(), X_C_OrderLine.GROUPCOMPENSATIONTYPE_Discount);
	}

	private static final String extractGroupCompensationAmtType(final I_M_Product product)
	{
		return Util.coalesce(product.getGroupCompensationAmtType(), X_C_OrderLine.GROUPCOMPENSATIONAMTTYPE_Percent);
	}

	private void setDefaultAmounts(final I_C_OrderLine compensationLine)
	{
		compensationLine.setIsManualPrice(true);
		compensationLine.setCompensationGroupDiscount(BigDecimal.ZERO);
		compensationLine.setPriceEntered(BigDecimal.ZERO);
		compensationLine.setPriceActual(BigDecimal.ZERO);

		final String amtType = compensationLine.getGroupCompensationAmtType();
		if (X_C_OrderLine.GROUPCOMPENSATIONAMTTYPE_Percent.equals(amtType))
		{
			compensationLine.setQtyEntered(BigDecimal.ONE);
			compensationLine.setQtyOrdered(BigDecimal.ONE);
		}
		else if (X_C_OrderLine.GROUPCOMPENSATIONAMTTYPE_PriceAndQty.equals(amtType))
		{
			compensationLine.setQtyEntered(BigDecimal.ZERO);
			compensationLine.setQtyOrdered(BigDecimal.ZERO);
		}
		else
		{
			throw new AdempiereException("Unknown amount type: " + amtType); // shall not happen
		}
	}

}
