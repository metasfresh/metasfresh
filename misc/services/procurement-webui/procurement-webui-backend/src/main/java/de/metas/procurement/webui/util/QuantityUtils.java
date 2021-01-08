package de.metas.procurement.webui.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/*
 * #%L
 * metasfresh-procurement-webui
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public final class QuantityUtils
{
	private QuantityUtils()
	{
	}

	public static final String toString(final BigDecimal qty)
	{
		if (qty == null)
		{
			return "0";
		}

		return qty.setScale(0, RoundingMode.UP).toString();
	}
	
	public static final BigDecimal toQuantity(String qtyStr)
	{
		if (qtyStr == null)
		{
			return BigDecimal.ZERO;
		}
		
		qtyStr = qtyStr.trim();
		if(qtyStr.isEmpty())
		{
			return BigDecimal.ZERO;
		}

		return new BigDecimal(qtyStr);
	}


}
