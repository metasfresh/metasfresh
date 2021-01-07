/*
 * #%L
 * procurement-webui-backend
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

package de.metas.procurement.webui.rest.weeklyReport;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.google.common.collect.ImmutableList;
import de.metas.procurement.webui.util.DateUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@Value
@Builder
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class JsonDailyProductQtyReport
{
	@NonNull
	LocalDate date;
	@NonNull
	String dayCaption;

	@NonNull
	BigDecimal qty;

	boolean confirmedByUser;

	public static JsonDailyProductQtyReport zero(@NonNull final LocalDate date, @NonNull final Locale locale)
	{
		return JsonDailyProductQtyReport.builder()
				.date(date)
				.dayCaption(DateUtils.getDayName(date, locale))
				.qty(BigDecimal.ZERO)
				.confirmedByUser(true)
				.build();
	}

	public static ImmutableList<JsonDailyProductQtyReport> zero(@NonNull final List<LocalDate> dateList, @NonNull final Locale locale)
	{
		return dateList.stream()
				.map(date -> zero(date, locale))
				.collect(ImmutableList.toImmutableList());
	}
}
