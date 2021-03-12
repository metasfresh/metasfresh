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

import de.metas.procurement.webui.Application;
import de.metas.procurement.webui.model.Product;
import de.metas.procurement.webui.model.Trend;
import de.metas.procurement.webui.model.User;
import de.metas.procurement.webui.model.WeekSupply;
import de.metas.procurement.webui.service.ILoginService;
import de.metas.procurement.webui.service.IProductSuppliesService;
import de.metas.procurement.webui.service.UserConfirmationService;
import de.metas.procurement.webui.util.YearWeekUtil;
import lombok.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.threeten.extra.YearWeek;

@RestController
@RequestMapping(WeeklyReportRestController.ENDPOINT)
public class WeeklyReportRestController
{
	static final String ENDPOINT = Application.ENDPOINT_ROOT + "/weeklyReport";

	private final ILoginService loginService;
	private final IProductSuppliesService productSuppliesService;
	private final UserConfirmationService userConfirmationService;

	public WeeklyReportRestController(
			@NonNull final ILoginService loginService,
			@NonNull final IProductSuppliesService productSuppliesService,
			@NonNull final UserConfirmationService userConfirmationService)
	{
		this.loginService = loginService;
		this.productSuppliesService = productSuppliesService;
		this.userConfirmationService = userConfirmationService;
	}

	@GetMapping("/{weekYear}")
	public JsonWeeklyReport getWeeklyReport(@PathVariable("weekYear") @NonNull final String weekYearStr)
	{
		final YearWeek yearWeek = YearWeekUtil.parse(weekYearStr);
		return getWeeklyReport(yearWeek, -1);
	}

	@GetMapping("/{weekYear}/{productId}")
	public JsonWeeklyReport getWeeklyReport(
			@PathVariable("weekYear") @NonNull final String weekYearStr,
			@PathVariable("productId") final long productId)
	{
		final YearWeek yearWeek = YearWeekUtil.parse(weekYearStr);
		return getWeeklyReport(yearWeek, productId);
	}

	private JsonWeeklyReport getWeeklyReport(
			@NonNull final YearWeek yearWeek,
			final long singleProductId)
	{
		final User user = loginService.getLoggedInUser();

		return JsonWeeklyReportProducer.builder()
				.productSuppliesService(productSuppliesService)
				.userConfirmationService(userConfirmationService)
				.user(user)
				.locale(loginService.getLocale())
				.week(yearWeek)
				.singleProductId(singleProductId)
				.build()
				.execute();
	}

	@PostMapping("/nextWeekTrend")
	public JsonSetNextWeekTrendResponse setNextWeekTrend(@RequestBody @NonNull final JsonSetNextWeekTrendRequest request)
	{
		final User user = loginService.getLoggedInUser();
		final Product product = productSuppliesService.getProductById(Long.parseLong(request.getProductId()));
		final YearWeek week = YearWeekUtil.parse(request.getWeek());
		final @NonNull Trend trend = request.getTrend();
		final WeekSupply weeklyForecast = productSuppliesService.setNextWeekTrend(
				user.getBpartner(),
				product,
				week,
				trend);

		return JsonSetNextWeekTrendResponse.builder()
				.productId(weeklyForecast.getProductIdAsString())
				.week(YearWeekUtil.toJsonString(weeklyForecast.getWeek()))
				.trend(weeklyForecast.getTrend())
				.build();
	}
}
