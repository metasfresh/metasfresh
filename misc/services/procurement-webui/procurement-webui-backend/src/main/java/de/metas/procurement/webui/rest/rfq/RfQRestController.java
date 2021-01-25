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

package de.metas.procurement.webui.rest.rfq;

import com.google.common.collect.ImmutableList;
import de.metas.procurement.webui.Application;
import de.metas.procurement.webui.model.Product;
import de.metas.procurement.webui.model.Rfq;
import de.metas.procurement.webui.model.RfqQty;
import de.metas.procurement.webui.model.User;
import de.metas.procurement.webui.service.ILoginService;
import de.metas.procurement.webui.service.IProductSuppliesService;
import de.metas.procurement.webui.service.IRfQService;
import de.metas.procurement.webui.service.UserConfirmationService;
import de.metas.procurement.webui.util.DateUtils;
import lombok.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping(RfQRestController.ENDPOINT)
public class RfQRestController
{
	static final String ENDPOINT = Application.ENDPOINT_ROOT + "/rfq";

	private final ILoginService loginService;
	private final IRfQService rfqService;
	private final IProductSuppliesService productSuppliesService;
	private final UserConfirmationService userConfirmationService;

	public RfQRestController(
			@NonNull final ILoginService loginService,
			@NonNull final IRfQService rfqService,
			@NonNull final IProductSuppliesService productSuppliesService,
			@NonNull final UserConfirmationService userConfirmationService)
	{
		this.loginService = loginService;
		this.rfqService = rfqService;
		this.productSuppliesService = productSuppliesService;
		this.userConfirmationService = userConfirmationService;
	}

	@GetMapping
	public JsonRfqsList getRfqs()
	{
		final User user = loginService.getLoggedInUser();
		final Locale locale = loginService.getLocale();
		final List<Rfq> rfqsList = rfqService.getActiveRfqs(user);
		return toJsonRfqsList(rfqsList, locale);
	}

	@GetMapping("/{rfqId}")
	public JsonRfq getRfq(@PathVariable("rfqId") final long rfqId)
	{
		final User user = loginService.getLoggedInUser();
		final Locale locale = loginService.getLocale();
		final Rfq rfq = rfqService.getUserActiveRfq(user, rfqId);
		return toJsonRfq(rfq, locale);
	}

	@PostMapping
	public JsonRfq changeRfq(@RequestBody @NonNull final JsonChangeRfqRequest request)
	{
		final User loggedUser = loginService.getLoggedInUser();
		final Rfq rfq = rfqService.changeActiveRfq(request, loggedUser);
		return toJsonRfq(rfq, loginService.getLocale())
				.withCountUnconfirmed(userConfirmationService.getCountUnconfirmed(loggedUser));
	}

	private JsonRfqsList toJsonRfqsList(final List<Rfq> rfqsList, @NonNull final Locale locale)
	{
		return JsonRfqsList.builder()
				.rfqs(rfqsList.stream()
						.map(rfq -> toJsonRfq(rfq, locale))
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private JsonRfq toJsonRfq(@NonNull final Rfq rfq, @NonNull final Locale locale)
	{
		final Product product = productSuppliesService.getProductById(rfq.getProduct_id());
		final BigDecimal pricePromisedUserEntered = rfq.getPricePromisedUserEntered();

		return JsonRfq.builder()
				.rfqId(rfq.getIdAsString())
				.productName(product.getName(locale))
				.packingInfo(product.getPackingInfo(locale))
				.dateStart(rfq.getDateStart())
				.dateEnd(rfq.getDateEnd())
				.dateClose(rfq.getDateClose())
				.qtyRequested(renderQty(rfq.getQtyRequested(), rfq.getQtyCUInfo(), locale))
				.qtyPromised(renderQty(rfq.getQtyPromisedUserEntered(), rfq.getQtyCUInfo(), locale))
				.price(pricePromisedUserEntered)
				.priceRendered(renderPrice(pricePromisedUserEntered, rfq.getCurrencyCode(), locale))
				.quantities(toJsonRfqQtysList(rfq, locale))
				.confirmedByUser(rfq.isConfirmedByUser())
				.build();
	}

	private static ArrayList<JsonRfqQty> toJsonRfqQtysList(
			@NonNull final Rfq rfq,
			@NonNull final Locale locale)
	{
		final ArrayList<JsonRfqQty> jsonRfqQtys = new ArrayList<>();

		for (final LocalDate date : rfq.generateAllDaysSet())
		{
			final RfqQty rfqQty = rfq.getRfqQtyByDate(date);

			final JsonRfqQty jsonRfqQty = rfqQty != null
					? toJsonRfqQty(rfqQty, rfq.getQtyCUInfo(), locale)
					: toZeroJsonRfqQty(date, rfq.getQtyCUInfo(), locale);

			jsonRfqQtys.add(jsonRfqQty);
		}
		return jsonRfqQtys;
	}

	private static JsonRfqQty toJsonRfqQty(
			@NonNull final RfqQty rfqQty,
			@NonNull final String uom,
			@NonNull final Locale locale)
	{
		final BigDecimal qtyPromised = rfqQty.getQtyPromisedUserEntered();

		return JsonRfqQty.builder()
				.date(rfqQty.getDatePromised())
				.dayCaption(DateUtils.getDayName(rfqQty.getDatePromised(), locale))
				.qtyPromised(qtyPromised)
				.qtyPromisedRendered(renderQty(qtyPromised, uom, locale))
				.confirmedByUser(rfqQty.isConfirmedByUser())
				.build();
	}

	private static JsonRfqQty toZeroJsonRfqQty(
			@NonNull final LocalDate date,
			@NonNull final String uom,
			@NonNull final Locale locale)
	{
		return JsonRfqQty.builder()
				.date(date)
				.dayCaption(DateUtils.getDayName(date, locale))
				.qtyPromised(BigDecimal.ZERO)
				.qtyPromisedRendered(renderQty(BigDecimal.ZERO, uom, locale))
				.confirmedByUser(true)
				.build();
	}

	private static String renderQty(
			@NonNull final BigDecimal qty,
			@NonNull final String uom,
			@NonNull final Locale locale)
	{
		final NumberFormat formatter = NumberFormat.getNumberInstance(locale);
		return formatter.format(qty) + " " + uom;
	}

	private static String renderPrice(
			@NonNull final BigDecimal price,
			@NonNull final String currencyCode,
			@NonNull final Locale locale)
	{
		final NumberFormat formatter = NumberFormat.getNumberInstance(locale);
		return formatter.format(price) + " " + currencyCode;
	}
}
