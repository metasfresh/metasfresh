/*
 * #%L
 * de-metas-camel-alberta-camelroutes
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.camel.externalsystems.alberta.common;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import org.threeten.bp.OffsetDateTime;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import static org.threeten.bp.temporal.ChronoField.EPOCH_DAY;
import static org.threeten.bp.temporal.ChronoField.MILLI_OF_SECOND;

public class AlbertaUtil
{
	@NonNull
	public static Instant asInstantNotNull(@NonNull final String offsetDateTime)
	{
		return Instant.parse(offsetDateTime);
	}

	@Nullable
	public static Instant asInstant(@Nullable final OffsetDateTime offsetDateTime)
	{
		if (offsetDateTime == null)
		{
			return null;
		}

		// we can't loose the millis.
		final long millis = offsetDateTime.toEpochSecond() * 1000 + offsetDateTime.getLong(MILLI_OF_SECOND);

		return Instant.ofEpochMilli(millis);
	}

	@Nullable
	public static LocalDate asJavaLocalDate(@Nullable final org.threeten.bp.LocalDate localDate)
	{
		if (localDate == null)
		{
			return null;
		}

		return LocalDate.ofEpochDay(localDate.getLong(EPOCH_DAY));
	}

	@Nullable
	public static org.threeten.bp.LocalDate fromJavaLocalDate(@Nullable final LocalDate localDate)
	{
		if (localDate == null)
		{
			return null;
		}

		return org.threeten.bp.LocalDate.ofEpochDay(localDate.toEpochDay());
	}

	@Nullable
	public static List<BigDecimal> asBigDecimalIds(@Nullable final List<String> ids)
	{
		if (ids == null || ids.isEmpty())
		{
			return null;
		}

		return ids.stream()
				.map(BigDecimal::new)
				.collect(ImmutableList.toImmutableList());
	}
}
