package de.metas.ui.web.window.datatypes.json;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import javax.annotation.Nullable;

import org.compiere.util.Env;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Value
@Builder(toBuilder = true)
public class JSONDateConfig
{
	public static final JSONDateConfig DEFAULT = JSONDateConfig.builder()
			.zonedDateTimeFormatter(Env.DATE_FORMAT)
			.timestampFormatter(Env.DATE_FORMAT)
			.localDateFormatter(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
			.localTimeFormatter(DateTimeFormatter.ofPattern("HH:mm"))
			.timeZoneFormatter(DateTimeFormatter.ofPattern("XXX"))
			.build();

	public static final JSONDateConfig LEGACY = JSONDateConfig.builder()
			.zonedDateTimeFormatter(Env.DATE_FORMAT)
			.timestampFormatter(Env.DATE_FORMAT)
			.convertToZonedDateTimeBeforeFormatting(true)
			.localDateFormatter(Env.DATE_FORMAT)
			.localTimeFormatter(Env.DATE_FORMAT)
			.timeZoneFormatter(DateTimeFormatter.ofPattern("XXX"))
			.build();

	@NonNull
	DateTimeFormatter zonedDateTimeFormatter;

	@NonNull
	DateTimeFormatter timestampFormatter;

	// legacy
	@Deprecated
	boolean convertToZonedDateTimeBeforeFormatting;

	@NonNull
	DateTimeFormatter localDateFormatter;

	@NonNull
	DateTimeFormatter localTimeFormatter;

	@NonNull
	DateTimeFormatter timeZoneFormatter;

	/** Fixed time zone, used for testing. */
	@Nullable
	ZoneId fixedTimeZone;

	public boolean isLegacy()
	{
		return this == LEGACY;
	}
}
