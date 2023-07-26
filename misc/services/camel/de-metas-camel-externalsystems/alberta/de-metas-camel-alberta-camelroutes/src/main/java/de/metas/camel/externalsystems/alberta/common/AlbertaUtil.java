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

import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.temporal.TemporalField;

import javax.annotation.Nullable;
import java.time.Instant;
import java.time.LocalDate;

import static org.threeten.bp.temporal.ChronoField.EPOCH_DAY;
import static org.threeten.bp.temporal.ChronoField.MILLI_OF_SECOND;

public class AlbertaUtil
{
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
}
