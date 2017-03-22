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

import de.metas.impex.model.I_AD_InputDataSource;

public interface I_C_Order extends de.metas.adempiere.model.I_C_Order
{
	// @formatter:off
	public static String COLUMNNAME_AD_InputDataSource_ID = "AD_InputDataSource_ID";
	public int getAD_InputDataSource_ID();
	public I_AD_InputDataSource getAD_InputDataSource();
	public void setAD_InputDataSource_ID(int AD_InputDataSource_ID);
	public void setAD_InputDataSource(I_AD_InputDataSource AD_InputDataSource);

	public static final String COLUMNNAME_InvoiceStatus = "InvoiceStatus";
	public String getInvoiceStatus();
	public void setInvoiceStatus(String InvoiceStatus);
	
	/** Completely Invoiced = CI */
	public static final String INVOICESTATUS_CompletelyInvoiced = "CI";
	/** Partly Invoiced = PI */
	public static final String INVOICESTATUS_PartlyInvoiced = "PI";
	/** Open = O */
	public static final String INVOICESTATUS_Open = "O";

	public static final String COLUMNNAME_DeliveryStatus = "DeliveryStatus";
	public String getDeliveryStatus();
	public void setDeliveryStatus(String DeliveryStatus);
	
	/** Completely Delivered = CD */
	public static final String DELIVERYSTATUS_CompletelyDelivered = "CD";
	/** Partly Delivered = PD */
	public static final String DELIVERYSTATUS_PartlyDelivered = "PD";
	/** Open = O */
	public static final String DELIVERYSTATUS_Open = "O";
		
	public static final String COLUMNNAME_QtyInvoiced = "QtyInvoiced";
	public BigDecimal getQtyInvoiced();
	public void setQtyInvoiced(BigDecimal QtyInvoiced);
	
	public static final String COLUMNNAME_QtyOrdered = "QtyOrdered";
	public BigDecimal getQtyOrdered();
	public void setQtyOrdered(BigDecimal QtyOrdered);
	
	public static final String COLUMNNAME_QtyMoved = "QtyMoved";
	public BigDecimal getQtyMoved();
	public void setQtyMoved(BigDecimal QtyMoved);
	
	// #653
	public static final String COLUMNNAME_LotNumberDate = "LotNumberDate";
	public void setLotNumberDate (java.sql.Timestamp LotNumberDate);
	public java.sql.Timestamp getLotNumberDate();

	// @formatter:on
}
