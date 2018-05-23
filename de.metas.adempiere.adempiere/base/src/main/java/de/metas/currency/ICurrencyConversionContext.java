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

import java.util.Date;

/**
 * To get an instance, use {@link ICurrencyBL#createCurrencyConversionContext(Date, int, int, int)}.
 *
 * Implementations of this interface are usually immutable.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface ICurrencyConversionContext
{
	int getC_ConversionType_ID();

	Date getConversionDate();

	int getAD_Client_ID();

	int getAD_Org_ID();

	/** @return a summary, user friendly, string representation */
	String getSummary();
}
