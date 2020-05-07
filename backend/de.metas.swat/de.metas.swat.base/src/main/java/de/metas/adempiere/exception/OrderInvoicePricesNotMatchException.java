package de.metas.adempiere.exception;

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

import org.adempiere.exceptions.AdempiereException;

/**
 * Thrown when sales order price not match sales invoice price
 * 
 * @author tsa
 * 
 */
public class OrderInvoicePricesNotMatchException extends AdempiereException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2828581995978841992L;

	public static final String AD_Message = "OrderInvoicePricesNotMatch";

	public OrderInvoicePricesNotMatchException(String priceName, BigDecimal orderPrice, BigDecimal invoicePrice)
	{
		super("@" + AD_Message + "@ @" + priceName + "@ (@C_Order_ID@: " + orderPrice + ", @C_Invoice_ID@: " + invoicePrice + ")");
	}
}
