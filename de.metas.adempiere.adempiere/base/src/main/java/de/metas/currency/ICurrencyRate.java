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
 * Currency rate, together with all informations (currency from/to, conversion type etc).
 * 
 * This class can also convert a given amount, by using {@link #convertAmount(BigDecimal)}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface ICurrencyRate
{
	BigDecimal getConversionRate();

	int getFrom_Currency_ID();

	int getTo_Currency_ID();

	int getC_ConversionType_ID();

	Date getConversionDate();

	int getCurrencyPrecision();

	BigDecimal convertAmount(BigDecimal amount);
}
