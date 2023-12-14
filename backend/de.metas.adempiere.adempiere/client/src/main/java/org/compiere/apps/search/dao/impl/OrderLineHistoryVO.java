package org.compiere.apps.search.dao.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.util.Services;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_AttributeSetInstance;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Contains aggregated information of ordered product quantities and their dates
 *
 * @author al
 */
public class OrderLineHistoryVO
{
	private final Timestamp datePromised;
	private final BigDecimal qtyReserved;
	private final String asiDescription;
	private final int asiId;
	private final String partnerName;
	private final String docBaseType;
	private final String documentNo;
	private final String warehouseName;

	public OrderLineHistoryVO(final I_C_OrderLine orderLine)
	{
		super();

		final I_C_Order order = orderLine.getC_Order();
		datePromised = order.getDatePromised();

		qtyReserved = orderLine.getQtyReserved();

		final I_M_AttributeSetInstance asi = orderLine.getM_AttributeSetInstance();
		if (asi != null)
		{
			asiDescription = asi.getDescription();
			asiId = asi.getM_AttributeSetInstance_ID();
		}
		else
		{
			asiDescription = "";
			asiId = 0;
		}

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(order.getC_BPartner_ID());
		partnerName = Services.get(IBPartnerDAO.class).getBPartnerNameById(bpartnerId);

		final DocTypeId docTypeId = DocTypeId.ofRepoId(order.getC_DocType_ID());
		final I_C_DocType docType = Services.get(IDocTypeDAO.class).getRecordById(docTypeId);
		docBaseType = docType.getDocBaseType();

		documentNo = new StringBuilder(docType.getPrintName())
				.append(" ").append(order.getDocumentNo())
				.toString();

		final WarehouseId warehouseId = WarehouseId.ofRepoIdOrNull(orderLine.getM_Warehouse_ID());
		if (warehouseId != null)
		{
			warehouseName = Services.get(IWarehouseDAO.class).getWarehouseName(warehouseId);
		}
		else
		{
			warehouseName = "";
		}
	}

	public Timestamp getDatePromised()
	{
		return datePromised;
	}

	public BigDecimal getQtyReserved()
	{
		return qtyReserved;
	}

	public String getASIDescription()
	{
		return asiDescription;
	}

	public int getASIId()
	{
		return asiId;
	}

	public String getPartnerName()
	{
		return partnerName;
	}

	public String getDocBaseType()
	{
		return docBaseType;
	}

	public String getDocumentNo()
	{
		return documentNo;
	}

	public String getWarehouseName()
	{
		return warehouseName;
	}
}
