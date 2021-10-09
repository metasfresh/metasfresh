package de.metas.order.model;

/*
 * #%L
 * de.metas.swat.base
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


import java.math.BigDecimal;

public interface I_C_Order extends de.metas.adempiere.model.I_C_Order
{
	// @formatter:off
	String COLUMNNAME_AD_InputDataSource_ID = "AD_InputDataSource_ID";
	int getAD_InputDataSource_ID();
	void setAD_InputDataSource_ID(int AD_InputDataSource_ID);

	String COLUMNNAME_InvoiceStatus = "InvoiceStatus";
	String getInvoiceStatus();
	void setInvoiceStatus(String InvoiceStatus);

	/** Completely Invoiced = CI */
	String INVOICESTATUS_CompletelyInvoiced = "CI";
	/** Partly Invoiced = PI */
	String INVOICESTATUS_PartlyInvoiced = "PI";
	/** Open = O */
	String INVOICESTATUS_Open = "O";

	String COLUMNNAME_DeliveryStatus = "DeliveryStatus";
	String getDeliveryStatus();
	void setDeliveryStatus(String DeliveryStatus);

	/** Completely Delivered = CD */
	String DELIVERYSTATUS_CompletelyDelivered = "CD";
	/** Partly Delivered = PD */
	String DELIVERYSTATUS_PartlyDelivered = "PD";
	/** Open = O */
	String DELIVERYSTATUS_Open = "O";

	String COLUMNNAME_QtyInvoiced = "QtyInvoiced";
	BigDecimal getQtyInvoiced();
	void setQtyInvoiced(BigDecimal QtyInvoiced);

	String COLUMNNAME_QtyOrdered = "QtyOrdered";
	BigDecimal getQtyOrdered();
	void setQtyOrdered(BigDecimal QtyOrdered);

	String COLUMNNAME_QtyMoved = "QtyMoved";
	BigDecimal getQtyMoved();
	void setQtyMoved(BigDecimal QtyMoved);

	// #653
	String COLUMNNAME_LotNumberDate = "LotNumberDate";
	void setLotNumberDate (java.sql.Timestamp LotNumberDate);
	java.sql.Timestamp getLotNumberDate();

	// @formatter:on
}
