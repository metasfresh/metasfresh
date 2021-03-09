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

package de.metas.procurement.webui.rest.dailyReport;

import de.metas.procurement.webui.model.Product;
import de.metas.procurement.webui.model.ProductSupply;
import de.metas.procurement.webui.model.User;
import de.metas.procurement.webui.service.IProductSuppliesService;
import de.metas.procurement.webui.util.DateUtils;
import lombok.Builder;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

class JsonDailyReportProducer
{
	@NonNull
	private final IProductSuppliesService productSuppliesService;

	@NonNull
	private final User user;

	@NonNull
	private final Locale locale;

	@NonNull
	private final LocalDate date;

	@Builder
	private JsonDailyReportProducer(
			@NonNull final IProductSuppliesService productSuppliesService,
			@NonNull final User user,
			@NonNull final Locale locale,
			@NonNull final LocalDate date)
	{
		this.productSuppliesService = productSuppliesService;
		this.user = user;
		this.locale = locale;
		this.date = date;
	}

	public JsonDailyReport execute()
	{
		final HashSet<String> addedProductIds = new HashSet<>();
		final ArrayList<JsonDailyReportItem> resultItems = new ArrayList<>();

		final List<ProductSupply> dailySupplies = productSuppliesService.getProductSupplies(user.getBpartner(), date);
		for (final ProductSupply productSupply : dailySupplies)
		{
			final JsonDailyReportItem item = toJsonDailyReportItem(productSupply);
			resultItems.add(item);
			addedProductIds.add(item.getProductId());
		}

		final List<Product> favoriteProducts = productSuppliesService.getUserFavoriteProducts(user);
		for (final Product product : favoriteProducts)
		{
			if (!addedProductIds.add(product.getIdAsString()))
			{
				continue;
			}

			resultItems.add(toZeroJsonDailyReportItem(product));
		}

		resultItems.sort(Comparator.comparing(JsonDailyReportItem::getProductName));

		return JsonDailyReport.builder()
				.date(date)
				.dayCaption(DateUtils.getDayName(date, locale))
				.products(resultItems)
				.build();
	}

	private JsonDailyReportItem toJsonDailyReportItem(final ProductSupply productSupply)
	{
		final Product product = productSuppliesService.getProductById(productSupply.getProductId());
		return JsonDailyReportItem.builder()
				.productId(product.getIdAsString())
				.productName(product.getName(locale))
				.packingInfo(product.getPackingInfo(locale))
				.qty(productSupply.getQtyUserEntered())
				.confirmedByUser(productSupply.isQtyConfirmedByUser())
				.build();
	}

	private JsonDailyReportItem toZeroJsonDailyReportItem(final Product product)
	{
		return JsonDailyReportItem.builder()
				.productId(product.getIdAsString())
				.productName(product.getName(locale))
				.packingInfo(product.getPackingInfo(locale))
				.qty(BigDecimal.ZERO)
				.confirmedByUser(true)
				.build();
	}

}
