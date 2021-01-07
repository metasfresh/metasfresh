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
import de.metas.procurement.webui.model.Trend;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

@Value
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class JsonWeeklyProductReport
{
	@NonNull
	String productId;

	@NonNull
	String productName;

	@Nullable
	String packingInfo;

	@NonNull
	BigDecimal qty;

	@NonNull
	List<JsonDailyProductQtyReport> dailyQuantities;

	@Nullable
	Trend nextWeekTrend;

	@Builder
	private JsonWeeklyProductReport(
			@NonNull final String productId,
			@NonNull final String productName,
			@Nullable final String packingInfo,
			@NonNull final List<JsonDailyProductQtyReport> dailyQuantities,
			@Nullable final Trend nextWeekTrend)
	{
		this.productId = productId;
		this.productName = productName;
		this.packingInfo = packingInfo;
		this.dailyQuantities = ImmutableList.copyOf(dailyQuantities);
		this.nextWeekTrend = nextWeekTrend;

		this.qty = dailyQuantities.stream()
				.map(JsonDailyProductQtyReport::getQty)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}
}
