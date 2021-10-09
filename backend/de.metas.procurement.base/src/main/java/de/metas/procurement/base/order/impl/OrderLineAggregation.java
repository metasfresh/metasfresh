package de.metas.procurement.base.order.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import de.metas.order.location.adapter.OrderLineDocumentLocationAdapterFactory;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_AttributeSetInstance;

import de.metas.order.IOrderLineBL;
import de.metas.procurement.base.order.model.I_C_OrderLine;
import de.metas.util.Check;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.procurement.base
 * %%
 * Copyright (C) 2016 metas GmbH
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
 * Instances of this class are handled by {@link OrderLinesAggregator} and can generate a new {@link I_C_OrderLine}.<br>
 * It is assumed that all purchase candidates added to the same instance have an equal {@link PurchaseCandidate#getLineAggregationKey()} value.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class OrderLineAggregation
{
	// services
	private final transient IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);

	private final I_C_Order order;
	private I_C_OrderLine orderLine;

	private final List<PurchaseCandidate> purchaseCandidates = new ArrayList<>();

	public OrderLineAggregation(final I_C_Order order)
	{
		super();
		Check.assumeNotNull(order, "order not null");
		this.order = order;
	}

	public I_C_OrderLine build()
	{
		if (orderLine == null)
		{
			return null;
		}

		// Save the order line
		InterfaceWrapperHelper.save(orderLine);

		// Create allocations
		for (final PurchaseCandidate candidate : purchaseCandidates)
		{
			candidate.createAllocation(orderLine);
		}

		final I_C_OrderLine orderLine = this.orderLine;
		this.orderLine = null;
		return orderLine;
	}

	public void add(final PurchaseCandidate candidate)
	{
		final BigDecimal qty = candidate.getQtyToOrder();
		if (qty.signum() == 0)
		{
			return;
		}

		if (orderLine == null)
		{
			orderLine = createOrderLine(candidate);
		}

		orderLine.setQtyEntered(orderLine.getQtyEntered().add(qty));
		// NOTE: we are not touching QtyOrdered. We expect to be automatically maintained.

		purchaseCandidates.add(candidate);
	}

	private I_C_OrderLine createOrderLine(final PurchaseCandidate candidate)
	{
		final int flatrateDataEntryId = candidate.getC_Flatrate_DataEntry_ID();
		final int huPIItemProductId = candidate.getM_HU_PI_Item_Product_ID();
		final Timestamp datePromised = candidate.getDatePromised();
		final BigDecimal price = candidate.getPrice();

		final I_C_OrderLine orderLine = orderLineBL.createOrderLine(order, I_C_OrderLine.class);
		orderLine.setIsMFProcurement(true);

		//
		// BPartner/Location/Contact
		OrderLineDocumentLocationAdapterFactory.locationAdapter(orderLine).setFromOrderHeader(order);

		//
		// PMM Contract
		if (flatrateDataEntryId > 0)
		{
			orderLine.setC_Flatrate_DataEntry_ID(flatrateDataEntryId);
		}

		//
		// Product/UOM/Handling unit
		orderLine.setM_Product_ID(candidate.getM_Product_ID());
		orderLine.setC_UOM_ID(candidate.getC_UOM_ID());
		if (huPIItemProductId > 0)
		{
			orderLine.setM_HU_PI_Item_Product_ID(huPIItemProductId);
		}

		//
		// ASI
		final AttributeSetInstanceId attributeSetInstanceId = candidate.getAttributeSetInstanceId();
		final I_M_AttributeSetInstance contractASI = attributeSetInstanceId.isRegular()
				? Services.get(IAttributeDAO.class).getAttributeSetInstanceById(attributeSetInstanceId)
				: null;
		final I_M_AttributeSetInstance asi;
		if (contractASI != null)
		{
			asi = Services.get(IAttributeDAO.class).copy(contractASI);
		}
		else
		{
			asi = null;
		}
		orderLine.setPMM_Contract_ASI(contractASI);
		orderLine.setM_AttributeSetInstance(asi);

		//
		// Quantities
		orderLine.setQtyEntered(BigDecimal.ZERO);
		orderLine.setQtyOrdered(BigDecimal.ZERO);

		//
		// Dates
		orderLine.setDatePromised(datePromised);

		//
		// Pricing
		orderLine.setIsManualPrice(true); // go with the candidate's price. e.g. don't reset it to 0 because we have no PL
		orderLine.setPriceEntered(price);
		orderLine.setPriceActual(price);

		//
		// BPartner/Location/Contact

		return orderLine;
	}
}
