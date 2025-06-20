package de.metas.adempiere.exception;

import java.io.Serial;
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
	@Serial
	private static final long serialVersionUID = -2828581995978841992L;

	public static final String AD_Message = "OrderInvoicePricesNotMatch";

	public OrderInvoicePricesNotMatchException(String priceName, BigDecimal orderPrice, BigDecimal invoicePrice)
	{
		super("@" + AD_Message + "@ @" + priceName + "@ (@C_Order_ID@: " + orderPrice + ", @C_Invoice_ID@: " + invoicePrice + ")");
	}
}
