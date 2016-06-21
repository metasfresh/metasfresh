package de.metas.adempiere.form;

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
	private static final long serialVersionUID = -8116893046623677802L;

	public static final String MSG = "de.metas.adempiere.form.PackingItemSubtractException";

	public PackingItemSubtractException(final IPackingItem packingItem, final BigDecimal qtyToSubtract, final BigDecimal qtyToSubtractRemaining)
	{
		super("@" + MSG + "@"
				+ "\n PackingItem: " + packingItem
				+ "\n QtyToSubtract: " + qtyToSubtract
				+ "\n QtyToSubtractRemaining: " + qtyToSubtractRemaining);
	}
}
