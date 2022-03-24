package org.adempiere.util.api;

/*
 * #%L
 * de.metas.util
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@SuppressWarnings("unused")
public interface IRangeAwareParams extends IParams
{
	@Nullable
	Object getParameter_ToAsObject(String parameterName);

	/**
	 * @return string value or <code>null</code> if parameter is missing
	 */
	@Nullable
	String getParameter_ToAsString(String parameterName);

	/**
	 * @return int value or <code>0</code> if parameter is missing or cannot be converted to integer
	 */
	int getParameter_ToAsInt(String parameterName, int defaultValue);

	/**
	 * @return boolean value or <code>false</code> if parameter is missing
	 */
	boolean getParameter_ToAsBool(String parameterName);

	@Nullable
	Timestamp getParameter_ToAsTimestamp(String parameterName);

	@Nullable
	LocalDate getParameter_ToAsLocalDate(String parameterName);

	@Nullable
	ZonedDateTime getParameter_ToAsZonedDateTime(String parameterName);

	/**
	 * @return {@link BigDecimal} value or <code>null</code> if parameter is missing or cannot be converted to {@link BigDecimal}
	 */
	@Nullable
	BigDecimal getParameter_ToAsBigDecimal(String parameterName);
}
