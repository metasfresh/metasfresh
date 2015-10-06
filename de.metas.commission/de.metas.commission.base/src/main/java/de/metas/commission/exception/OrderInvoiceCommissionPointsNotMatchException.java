package de.metas.commission.exception;

/*
 * #%L
 * de.metas.commission.base
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
 * Thrown when sales order commission points not match sales invoice commission points
 * 
 * @author tsa
 * 
 */
public class OrderInvoiceCommissionPointsNotMatchException extends AdempiereException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2828581995978841992L;

	public static final String AD_Message = "OrderInvoiceCommissionPointsNotMatch";

	public OrderInvoiceCommissionPointsNotMatchException(final String commissionPointsName, final BigDecimal orderPoints, final BigDecimal invoicePoints)
	{
		super("@" + OrderInvoiceCommissionPointsNotMatchException.AD_Message + "@ @" + commissionPointsName + "@ (@C_Order_ID@: " + orderPoints + ", @C_Invoice_ID@: " + invoicePoints + ")");
	}
}
