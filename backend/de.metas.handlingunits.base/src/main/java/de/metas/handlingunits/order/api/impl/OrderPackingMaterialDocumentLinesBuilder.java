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
import org.compiere.model.I_C_Order;

import de.metas.handlingunits.IPackingMaterialDocumentLine;
import de.metas.handlingunits.IPackingMaterialDocumentLineSource;
import de.metas.handlingunits.impl.AbstractPackingMaterialDocumentLinesBuilder;
import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.order.IOrderDAO;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderLinePriceUpdateRequest;
import de.metas.order.OrderLinePriceUpdateRequest.ResultUOM;
import de.metas.product.IProductBL;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * Iterates an order's lines and creates additional lines for the HU packing material.
 */
public final class OrderPackingMaterialDocumentLinesBuilder extends AbstractPackingMaterialDocumentLinesBuilder
{
	//
	// Services; note that this lineBuilder is shortLived, so it's OK to have those services as members
	private final transient IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final transient IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
	private final transient IProductBL productBL = Services.get(IProductBL.class);

	private final I_C_Order order;

	/**
	 * Creates a new instance of the given order.
	 *
	 * @param order
	 */
	public OrderPackingMaterialDocumentLinesBuilder(@NonNull final I_C_Order order)
	{
		this.order = order;
	}

	public void addAllOrderLinesFromOrder()
	{
		// note that we want all because there might be existing inactive packaging lines to reuse
		final List<I_C_OrderLine> orderLinesAll = retrieveAllOrderLinesFromOrder();

		//
		// Add Packing Material Order Lines first
		// and gather regular order lines (sources for packing materials)
		final List<IPackingMaterialDocumentLineSource> lines = new ArrayList<>();
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
	public void deactivateAndUnlinkAllPackingMaterialOrderLinesFromOrder()
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
	protected void assertValid(final IPackingMaterialDocumentLineSource source)
	{
		Check.assumeInstanceOf(source, OrderLinePackingMaterialDocumentLineSource.class, "source");

		final OrderLinePackingMaterialDocumentLineSource orderLineSource = toImpl(source);
		final I_C_OrderLine orderLine = orderLineSource.getC_OrderLine();
		Check.assume(order.getC_Order_ID() == orderLine.getC_Order_ID(),
				"Order line {} shall be part of {}",
				orderLine, order);

	}

	@Override
	protected IPackingMaterialDocumentLine createPackingMaterialDocumentLine(final I_M_HU_PackingMaterial packingMaterial)
	{
		final I_C_OrderLine orderLine = orderLineBL.createOrderLine(order, I_C_OrderLine.class);
		final UomId uomId = productBL.getStockUOMId(packingMaterial.getM_Product_ID());

		orderLine.setM_Product_ID(packingMaterial.getM_Product_ID());
		orderLine.setC_UOM_ID(uomId.getRepoId()); // prevent the system from picking its default-UOM; there might be no UOM-conversion to/from the product's UOM
		orderLine.setIsPackagingMaterial(true);

		return new OrderLinePackingMaterialDocumentLine(orderLine);
	}

	private OrderLinePackingMaterialDocumentLine toImpl(final IPackingMaterialDocumentLine pmLine)
	{
		return (OrderLinePackingMaterialDocumentLine)pmLine;
	}

	private OrderLinePackingMaterialDocumentLineSource toImpl(final IPackingMaterialDocumentLineSource source)
	{
		return (OrderLinePackingMaterialDocumentLineSource)source;
	}

	@Override
	protected void removeDocumentLine(final IPackingMaterialDocumentLine pmLine)
	{
		final OrderLinePackingMaterialDocumentLine orderLinePMLine = toImpl(pmLine);
		final I_C_OrderLine pmOrderLine = orderLinePMLine.getC_OrderLine();

		if (!InterfaceWrapperHelper.isNew(pmOrderLine))
		{
			InterfaceWrapperHelper.delete(pmOrderLine);
		}
	}

	@Override
	protected void createDocumentLine(final IPackingMaterialDocumentLine pmLine)
	{
		final OrderLinePackingMaterialDocumentLine orderLinePMLine = toImpl(pmLine);
		final I_C_OrderLine pmOrderLine = orderLinePMLine.getC_OrderLine();

		// qtyOrdered is in the product's UOM whereas QtyEntered is in the order line's UOM. They don't have to be the same.
		// pmOrderLine.setQtyEntered(pmOrderLine.getQtyOrdered());

		final boolean ordereWasInactive = !pmOrderLine.isActive();
		pmOrderLine.setIsActive(true);

		if (ordereWasInactive)
		{
			// while the order line was inactive e.g. the order's datePromised might have been changed
			orderLineBL.setOrder(pmOrderLine, order);
		}

		orderLineBL.updatePrices(OrderLinePriceUpdateRequest.builder()
				.orderLine(pmOrderLine)
				.resultUOM(ResultUOM.CONTEXT_UOM)
				.updatePriceEnteredAndDiscountOnlyIfNotAlreadySet(true)
				.updateLineNetAmt(true)
				.build());

		InterfaceWrapperHelper.save(pmOrderLine);
	}

	@Override
	protected void linkSourceToDocumentLine(final IPackingMaterialDocumentLineSource source,
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
