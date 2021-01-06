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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimaps;
import de.metas.procurement.webui.Application;
import de.metas.procurement.webui.model.BPartner;
import de.metas.procurement.webui.model.Product;
import de.metas.procurement.webui.model.ProductSupply;
import de.metas.procurement.webui.model.User;
import de.metas.procurement.webui.service.ILoginService;
import de.metas.procurement.webui.service.IProductSuppliesService;
import de.metas.procurement.webui.util.DateUtils;
import de.metas.procurement.webui.util.YearWeekFormatter;
import lombok.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.threeten.extra.YearWeek;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping(WeeklyReportRestController.ENDPOINT)
public class WeeklyReportRestController
{
	static final String ENDPOINT = Application.ENDPOINT_ROOT + "/weeklyReport";

	private final ILoginService loginService;
	private final IProductSuppliesService productSuppliesService;

	public WeeklyReportRestController(
			@NonNull final ILoginService loginService,
			@NonNull final IProductSuppliesService productSuppliesService)
	{
		this.loginService = loginService;
		this.productSuppliesService = productSuppliesService;
	}

	@GetMapping("/{weekYear}")
	public JsonWeeklyReport getWeeklyReport(@PathVariable("weekYear") @NonNull final String weekYearStr)
	{
		final User user = loginService.getLoggedInUser();
		final BPartner bpartner = user.getBpartner();
		final Locale locale = loginService.getLocale();

		final YearWeek yearWeek = YearWeekFormatter.parse(weekYearStr);
		final LocalDate startDate = yearWeek.atDay(DayOfWeek.MONDAY);
		final LocalDate endDate = yearWeek.atDay(DayOfWeek.SUNDAY);
		final List<LocalDate> days = getDays(startDate, endDate);

		final List<Product> favoriteProducts = productSuppliesService.getUserFavoriteProducts(user);
		final ImmutableMap<Long, Product> favoriteProductsById = Maps.uniqueIndex(favoriteProducts, Product::getId);

		final List<ProductSupply> dailySupplies = productSuppliesService.getProductSupplies(
				bpartner.getId(),
				-1, // all products
				startDate,
				endDate);
		final ImmutableListMultimap<Long, ProductSupply> dailySuppliesByProductId = Multimaps.index(dailySupplies, dailySupply -> dailySupply.getProduct().getId());

		final ImmutableSet<Long> productIds = ImmutableSet.<Long>builder()
				.addAll(dailySuppliesByProductId.keySet())
				.addAll(favoriteProductsById.keySet())
				.build();

		final ArrayList<JsonWeeklyProductReport> resultProducts = new ArrayList<>(productIds.size());
		for (final Long productId : productIds)
		{
			final ImmutableList<ProductSupply> productDailySupplies = dailySuppliesByProductId.get(productId);
			if (productDailySupplies.isEmpty())
			{
				final Product product = favoriteProductsById.get(productId);
				resultProducts.add(JsonWeeklyProductReport.builder()
						.productId(product.getIdAsString())
						.productName(product.getName(locale))
						.packingInfo(product.getPackingInfo(locale))
						.dailyQuantities(JsonDailyProductQtyReport.zero(days, locale))
						.build());
			}
			else
			{
				final ImmutableMap<LocalDate, ProductSupply> productDailySuppliesByDate = Maps.uniqueIndex(productDailySupplies, ProductSupply::getDay);
				final ArrayList<JsonDailyProductQtyReport> resultDailyQtys = new ArrayList<>(days.size());
				for (final LocalDate date : days)
				{
					final ProductSupply productSupply = productDailySuppliesByDate.get(date);
					if (productSupply != null)
					{
						resultDailyQtys.add(JsonDailyProductQtyReport.builder()
								.date(date)
								.dayCaption(DateUtils.getDayName(date, locale))
								.qty(productSupply.getQty())
								.build());
					}
					else
					{
						resultDailyQtys.add(JsonDailyProductQtyReport.zero(date, locale));
					}
				}

				final Product product = productDailySupplies.get(0).getProduct();
				resultProducts.add(JsonWeeklyProductReport.builder()
						.productId(product.getIdAsString())
						.productName(product.getName(locale))
						.packingInfo(product.getPackingInfo(locale))
						.dailyQuantities(resultDailyQtys)
						.build());
			}
		}

		resultProducts.sort(Comparator.comparing(JsonWeeklyProductReport::getProductName));

		return JsonWeeklyReport.builder()
				.week(YearWeekFormatter.toJsonString(yearWeek))
				.weekCaption(YearWeekFormatter.toDisplayName(yearWeek))
				.previousWeek(YearWeekFormatter.toJsonString(yearWeek.minusWeeks(1)))
				.nextWeek(YearWeekFormatter.toJsonString(yearWeek.plusWeeks(1)))
				.products(resultProducts)
				.build();
	}

	private static List<LocalDate> getDays(final LocalDate startDate, final LocalDate endDate)
	{
		final ArrayList<LocalDate> result = new ArrayList<>();
		for (LocalDate date = startDate; date.compareTo(endDate) <= 0; date = date.plusDays(1))
		{
			result.add(date);
		}

		return result;
	}
}
