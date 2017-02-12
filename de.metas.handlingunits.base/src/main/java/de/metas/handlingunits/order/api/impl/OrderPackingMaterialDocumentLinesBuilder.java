package de.metas.handlingunits.order.api.impl;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Order;

import de.metas.adempiere.service.IOrderDAO;
import de.metas.adempiere.service.IOrderLineBL;
import de.metas.handlingunits.IPackingMaterialDocumentLine;
import de.metas.handlingunits.IPackingMaterialDocumentLineSource;
import de.metas.handlingunits.impl.AbstractPackingMaterialDocumentLinesBuilder;
import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;

/**
 * Iterates an order's lines and creates additional lines for the HU packing material.
 *
 */
public final class OrderPackingMaterialDocumentLinesBuilder extends AbstractPackingMaterialDocumentLinesBuilder
{
	//
	// Services
	private final transient IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final transient IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);

	private final I_C_Order order;

	/**
	 * Creates a new instance of the given order.
	 *
	 * @param order
	 */
	public OrderPackingMaterialDocumentLinesBuilder(final I_C_Order order)
	{
		super();

		Check.assumeNotNull(order, "order not null");
		this.order = order;
	}

	public final void addAllOrderLinesFromOrder()
	{
		final List<I_C_OrderLine> orderLinesAll = retrieveAllOrderLinesFromOrder();

		//
		// Add Packing Material Order Lines first
		// and gather regular order lines (sources for packing materials)
		final List<IPackingMaterialDocumentLineSource> lines = new ArrayList<IPackingMaterialDocumentLineSource>();
		for (final I_C_OrderLine orderLine : orderLinesAll)
		{
			if (orderLine.isPackagingMaterial())
			{
				final IPackingMaterialDocumentLine pmLine = new OrderLinePackingMaterialDocumentLine(orderLine);
				addPackingMaterialDocumentLine(pmLine);
			}
			else
			{
				final IPackingMaterialDocumentLineSource pmLineSource = new OrderLinePackingMaterialDocumentLineSource(orderLine);
				lines.add(pmLineSource);
			}
		}

		//
		// Add regular lines
		for (final IPackingMaterialDocumentLineSource line : lines)
		{
			addSource(line);
		}
	}

	private List<I_C_OrderLine> retrieveAllOrderLinesFromOrder()
	{
		final List<I_C_OrderLine> orderLinesAll = InterfaceWrapperHelper.createList(
				orderDAO.retrieveOrderLines(order),
				I_C_OrderLine.class);
		return orderLinesAll;
	}

	/**
	 * Reset existing packing material order lines, ie.e deactivate and unlink them, and set their quantities to zero.<br>
	 * They might eventually be removed in {@link #create()}, if they don't get new quantities assigned from the builder.
	 *
	 */
	public final void deactivateAndUnlinkAllPackingMaterialOrderLinesFromOrder()
	{
		final List<I_C_OrderLine> oLines = retrieveAllOrderLinesFromOrder();
		for (final I_C_OrderLine orderLine : oLines)
		{
			if (orderLine.isPackagingMaterial())
			{
				orderLine.setIsActive(false);
				orderLine.setQtyEntered(BigDecimal.ZERO);
				orderLine.setQtyOrdered(BigDecimal.ZERO);
			}
			else
			{
				orderLine.setC_PackingMaterial_OrderLine(null);
			}
			InterfaceWrapperHelper.save(orderLine);
		}
	}

	@Override
	protected final void assertValid(final IPackingMaterialDocumentLineSource source)
	{
		Check.assumeInstanceOf(source, OrderLinePackingMaterialDocumentLineSource.class, "source");

		final OrderLinePackingMaterialDocumentLineSource orderLineSource = toImpl(source);
		final I_C_OrderLine orderLine = orderLineSource.getC_OrderLine();
		Check.assume(order.getC_Order_ID() == orderLine.getC_Order_ID(),
				"Order line {} shall be part of {}",
				orderLine, order);

	}

	@Override
	protected final IPackingMaterialDocumentLine createPackingMaterialDocumentLine(final I_M_HU_PackingMaterial packingMaterial)
	{
		final I_C_OrderLine orderLine = orderLineBL.createOrderLine(order, I_C_OrderLine.class);
		orderLine.setM_Product_ID(packingMaterial.getM_Product_ID());
		orderLine.setIsPackagingMaterial(true);

		return new OrderLinePackingMaterialDocumentLine(orderLine);
	}

	private final OrderLinePackingMaterialDocumentLine toImpl(final IPackingMaterialDocumentLine pmLine)
	{
		return (OrderLinePackingMaterialDocumentLine)pmLine;
	}

	private final OrderLinePackingMaterialDocumentLineSource toImpl(final IPackingMaterialDocumentLineSource source)
	{
		return (OrderLinePackingMaterialDocumentLineSource)source;
	}

	@Override
	protected final void removeDocumentLine(final IPackingMaterialDocumentLine pmLine)
	{
		final OrderLinePackingMaterialDocumentLine orderLinePMLine = toImpl(pmLine);
		final I_C_OrderLine pmOrderLine = orderLinePMLine.getC_OrderLine();

		if (!InterfaceWrapperHelper.isNew(pmOrderLine))
		{
			InterfaceWrapperHelper.delete(pmOrderLine);
		}
	}

	@Override
	protected final void createDocumentLine(final IPackingMaterialDocumentLine pmLine)
	{
		final OrderLinePackingMaterialDocumentLine orderLinePMLine = toImpl(pmLine);
		final I_C_OrderLine pmOrderLine = orderLinePMLine.getC_OrderLine();

		// not cool: qtyOrdered is in the product's UOM whereas QtyEntered is in the order lines UOM. They don't have to be the same.
		// pmOrderLine.setQtyEntered(pmOrderLine.getQtyOrdered());

		pmOrderLine.setIsActive(true);
		orderLineBL.setPrices(pmOrderLine);
		InterfaceWrapperHelper.save(pmOrderLine);
	}

	@Override
	protected final void linkSourceToDocumentLine(final IPackingMaterialDocumentLineSource source,
			final IPackingMaterialDocumentLine pmLine)
	{
		final OrderLinePackingMaterialDocumentLineSource orderLineSource = toImpl(source);
		final OrderLinePackingMaterialDocumentLine orderLinePMLine = toImpl(pmLine);

		final I_C_OrderLine regularOrderLine = orderLineSource.getC_OrderLine();
		Check.assume(regularOrderLine.getC_OrderLine_ID() > 0, "Regular order line shall be already saved: {}", regularOrderLine);

		final I_C_OrderLine pmOrderLine;
		if (orderLinePMLine == null)
		{
			pmOrderLine = null;
		}
		else
		{
			pmOrderLine = orderLinePMLine.getC_OrderLine();
		}

		regularOrderLine.setC_PackingMaterial_OrderLine(pmOrderLine);
		InterfaceWrapperHelper.save(regularOrderLine);
	}
}
