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

import de.metas.util.StringUtils;
import de.metas.util.lang.ReferenceListAwareEnums;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Optional;

/**
 * Generic readonly parameters.
 */
public interface IParams
{
	/** No params */
	IParams NULL = NullParams.instance;

	Collection<String> getParameterNames();

	boolean hasParameter(String parameterName);

	Object getParameterAsObject(String parameterName);

	/** @return string value or <code>null</code> if parameter is missing */
	@Nullable
	String getParameterAsString(String parameterName);

	/** @return int value or <code>defaultValue</code> if parameter is missing or cannot be converted to integer */
	int getParameterAsInt(String parameterName, int defaultValue);
	
	@Nullable
	<T extends RepoIdAware> T getParameterAsId(String parameterName, Class<T> type);

	/** @return boolean value or <code>false</code> if parameter is missing */
	boolean getParameterAsBool(String parameterName);

	@Nullable
	Boolean getParameterAsBoolean(String parameterName, @Nullable Boolean defaultValue);

	/** @return timestamp value or <code>null</code> if parameter is missing */
	@Nullable
	Timestamp getParameterAsTimestamp(String parameterName);

	/** @return local date value or <code>null</code> if parameter is missing */
	@Nullable
	LocalDate getParameterAsLocalDate(String parameterName);

	@Nullable
	ZonedDateTime getParameterAsZonedDateTime(String parameterName);

	@Nullable
	Instant getParameterAsInstant(String parameterName);

	/** @return {@link BigDecimal} value or <code>null</code> if parameter is missing or cannot be converted to {@link BigDecimal} */
	BigDecimal getParameterAsBigDecimal(String parameterName);

	default <T extends Enum<T>> Optional<T> getParameterAsEnum(final String parameterName, final Class<T> enumType)
	{
		final String value = getParameterAsString(parameterName);
		return value != null
				? Optional.of(ReferenceListAwareEnums.ofEnumCode(value, enumType))
				: Optional.empty();
	}

	@Nullable
	default Boolean getParameterAsBoolean(@NonNull final String parameterName)
	{
		final Object value = getParameterAsObject(parameterName);

		if (value == null)
		{
			return null;
		}

		return StringUtils.toBoolean(value);
	}
}
