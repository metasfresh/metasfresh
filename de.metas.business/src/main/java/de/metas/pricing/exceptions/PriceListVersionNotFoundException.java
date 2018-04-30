package de.metas.pricing.exceptions;

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


import java.util.Date;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_PriceList;

public class PriceListVersionNotFoundException extends AdempiereException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4882682472737353586L;

	public PriceListVersionNotFoundException(final I_M_PriceList priceList, final Date date)
	{
		super(buildMessage(priceList, date));
	}

	private static String buildMessage(I_M_PriceList priceList, Date date)
	{
		final StringBuilder sb = new StringBuilder("@NotFound@: @M_PriceList_Version@");
		sb.append("\n@M_PriceList_ID@: ").append(priceList == null ? "" : priceList.getName());
		sb.append("\n@Date@: ").append(date == null ? "" : date);

		return sb.toString();
	}

}
