package de.metas.picking.service;

import java.io.Serial;
import java.math.BigDecimal;

import org.adempiere.exceptions.AdempiereException;

import de.metas.quantity.Quantity;

/**
 * Exception thrown when {@link IPackingItem#subtract(BigDecimal)} method is asked to subtract a qty bigger than available.
 *
 * @author tsa
 *
 */
public class PackingItemSubtractException extends AdempiereException
{
	/**
	 *
	 */
	@Serial
	private static final long serialVersionUID = -8116893046623677802L;

	public static final String MSG = "de.metas.adempiere.form.PackingItemSubtractException";

	PackingItemSubtractException(
			final IPackingItem packingItem,
			final Quantity qtyToSubtract,
			final Quantity qtyToSubtractRemaining)
	{
		super("@" + MSG + "@"
				+ "\n PackingItem: " + packingItem
				+ "\n QtyToSubtract: " + qtyToSubtract
				+ "\n QtyToSubtractRemaining: " + qtyToSubtractRemaining);
	}
}
