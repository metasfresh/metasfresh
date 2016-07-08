package de.metas.currency.exceptions;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import org.adempiere.exceptions.AdempiereException;

import de.metas.currency.ICurrencyConversionContext;

/**
 * Exception thrown when there was no currency rate found.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class NoCurrencyRateFoundException extends AdempiereException
{
	/**
	 *
	 */
	private static final long serialVersionUID = -5532453717919181505L;

	public NoCurrencyRateFoundException(final ICurrencyConversionContext conversionCtx, final int currencyFromId, final int currencyToId)
	{
		super(buildMsg(conversionCtx, currencyFromId, currencyToId));
	}

	private static String buildMsg(final ICurrencyConversionContext conversionCtx, final int currencyFromId, final int currencyToId)
	{
		return "@NotFound@ @C_Conversion_Rate_ID@"
				+ "\n @Context@: " + conversionCtx
				+ "\n @C_Currency_ID@: " + currencyFromId
				+ "\n @C_Currency_ID_To@: " + currencyToId;
	}
}
