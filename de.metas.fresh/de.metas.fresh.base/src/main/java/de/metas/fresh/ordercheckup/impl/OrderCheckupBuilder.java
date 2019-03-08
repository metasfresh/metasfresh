package de.metas.fresh.ordercheckup.impl;

/*
 * #%L
 * de.metas.fresh.base
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

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;

import de.metas.fresh.model.I_C_Order_MFGWarehouse_Report;
import de.metas.fresh.model.I_C_Order_MFGWarehouse_ReportLine;
import de.metas.fresh.ordercheckup.OrderCheckupBarcode;
import de.metas.product.ResourceId;
import de.metas.user.UserId;
import de.metas.util.Check;

/**
 * {@link I_C_Order_MFGWarehouse_Report} builder.
 *
 * @author tsa
 *
 */
public class OrderCheckupBuilder
{
	public static final OrderCheckupBuilder newBuilder()
	{
		return new OrderCheckupBuilder();
	}

	private boolean _built = false;
	private String _documentType = null;
	private I_C_Order _order;
	private WarehouseId _warehouseId;
	private ResourceId _plantId;
	private UserId _reponsibleUserId;
	private final List<I_C_OrderLine> _orderLines = new ArrayList<>();

	private OrderCheckupBuilder()
	{
	}

	/**
	 * Builds the {@link I_C_Order_MFGWarehouse_Report}.
	 *
	 * If there were no {@link I_C_OrderLine}s added, no report will be created.
	 */
	public void build()
	{
		markAsBuild();

		// Do nothing if there are no order lines
		final List<I_C_OrderLine> orderLines = getOrderLines();
		if (orderLines.isEmpty())
		{
			return;
		}

		final I_C_Order order = getC_Order();

		//
		// Create report header
		final I_C_Order_MFGWarehouse_Report report = InterfaceWrapperHelper.newInstance(I_C_Order_MFGWarehouse_Report.class, order);
		report.setAD_Org_ID(order.getAD_Org_ID());
		report.setDocumentType(getDocumentType());
		report.setC_Order(order);
		report.setC_BPartner_ID(order.getC_BPartner_ID());
		report.setM_Warehouse_ID(WarehouseId.toRepoId(getWarehouseId()));
		report.setPP_Plant_ID(ResourceId.toRepoId(getPlantId()));
		report.setAD_User_Responsible_ID(UserId.toRepoId(getReponsibleUserId()));
		report.setProcessed(false); // we will set it to true when we are done with the lines
		InterfaceWrapperHelper.save(report);

		//
		// Create report lines
		for (final I_C_OrderLine orderLine : orderLines)
		{
			final I_C_Order_MFGWarehouse_ReportLine reportLine = InterfaceWrapperHelper.newInstance(I_C_Order_MFGWarehouse_ReportLine.class, order);
			reportLine.setC_Order_MFGWarehouse_Report(report);
			reportLine.setAD_Org_ID(orderLine.getAD_Org_ID());
			reportLine.setC_OrderLine(orderLine);
			reportLine.setM_Product_ID(orderLine.getM_Product_ID());
			reportLine.setBarcode(OrderCheckupBarcode.ofC_OrderLine_ID(reportLine.getC_OrderLine_ID()).toBarcodeString());
			InterfaceWrapperHelper.save(reportLine);
		}

		//
		// Mark the report as processed
		// NOTE we do this only at the end because this is the moment where doc outbound shall react and create/print the PDF report.
		report.setProcessed(true);
		InterfaceWrapperHelper.save(report);
	}

	private final void assertNotBuilt()
	{
		Check.assume(!_built, "not already built");
	}

	private final void markAsBuild()
	{
		assertNotBuilt();
		_built = true;
	}

	public OrderCheckupBuilder setC_Order(final I_C_Order order)
	{
		assertNotBuilt();

		Check.assumeNotNull(order, "order not null");
		this._order = order;
		return this;
	}

	private final I_C_Order getC_Order()
	{
		Check.assumeNotNull(_order, "_order not null");
		return _order;
	}

	public OrderCheckupBuilder addOrderLine(final I_C_OrderLine orderLine)
	{
		assertNotBuilt();

		Check.assumeNotNull(orderLine, "orderLine not null");
		Check.assume(getC_Order().getC_Order_ID() == orderLine.getC_Order_ID(), "order line shall be part of provided order");
		_orderLines.add(orderLine);
		return this;
	}

	private final List<I_C_OrderLine> getOrderLines()
	{
		return _orderLines;
	}

	public OrderCheckupBuilder setWarehouseId(@Nullable final WarehouseId warehouseId)
	{
		this._warehouseId = warehouseId;
		return this;
	}

	private WarehouseId getWarehouseId()
	{
		return _warehouseId;
	}

	public OrderCheckupBuilder setPlantId(ResourceId plantId)
	{
		this._plantId = plantId;
		return this;
	}

	private ResourceId getPlantId()
	{
		return _plantId;
	}

	public OrderCheckupBuilder setReponsibleUserId(UserId reponsibleUserId)
	{
		this._reponsibleUserId = reponsibleUserId;
		return this;
	}

	private UserId getReponsibleUserId()
	{
		return _reponsibleUserId;
	}

	public OrderCheckupBuilder setDocumentType(String documentType)
	{
		this._documentType = documentType;
		return this;
	}

	private String getDocumentType()
	{
		Check.assumeNotEmpty(_documentType, "documentType not empty");
		return _documentType;
	}
}
