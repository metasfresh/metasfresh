package de.metas.currency;

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

import java.math.BigDecimal;
import java.util.Date;

/**
 * The result of a currency conversion.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface ICurrencyConversionResult
{
	int getAD_Client_ID();

	int getAD_Org_ID();

	int getC_ConversionType_ID();

	Date getConversionDate();

	/**
	 * @return source amount (in {@link #getSource_Currency_ID()})
	 */
	BigDecimal getSourceAmount();

	/**
	 * @return converted amount (in {@link #getC_Currency_ID()})
	 */
	BigDecimal getAmount();

	int getC_Currency_ID();

	int getSource_Currency_ID();

	BigDecimal getConversionRate();
}
