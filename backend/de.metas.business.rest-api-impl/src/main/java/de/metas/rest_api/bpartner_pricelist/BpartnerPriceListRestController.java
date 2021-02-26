package de.metas.rest_api.bpartner_pricelist;

import static de.metas.rest_api.bpartner.SwaggerDocConstants.BPARTNER_IDENTIFIER_DOC;

import java.time.LocalDate;

import javax.annotation.Nullable;

import de.metas.common.util.time.SystemTime;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.metas.Profiles;
import de.metas.lang.SOTrx;
import de.metas.rest_api.bpartner.impl.BpartnerRestController;
import de.metas.rest_api.bpartner_pricelist.command.GetPriceListCommand;
import de.metas.rest_api.bpartner_pricelist.response.JsonResponsePriceList;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.util.Check;
import io.swagger.annotations.ApiParam;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business.rest-api-impl
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

@RequestMapping(BpartnerRestController.ENDPOINT)
@RestController
@Profile(Profiles.PROFILE_App)
public class BpartnerPriceListRestController
{
	private final BpartnerPriceListServicesFacade servicesFacade;

	public BpartnerPriceListRestController(
			@NonNull final BpartnerPriceListServicesFacade servicesFacade)
	{
		this.servicesFacade = servicesFacade;
	}

	@GetMapping("/{bpartnerIdentifier}/sales/prices/{countryCode}")
	public ResponseEntity<JsonResponsePriceList> getSalesPriceList(
			@ApiParam(required = true, value = BPARTNER_IDENTIFIER_DOC) //
			@PathVariable("bpartnerIdentifier") //
			@NonNull final String bpartnerIdentifierStr,
			//
			@ApiParam(required = true, value = "Country code (2 letters)") //
			@PathVariable("countryCode") //
			@NonNull final String countryCode,
			//
			@ApiParam(required = false, value = "Date on which the prices shall be valid. The format is 'yyyy-MM-dd'.") //
			@RequestParam(name = "date", required = false) //
			@Nullable final String dateStr)
	{
		final IdentifierString bpartnerIdentifier = IdentifierString.of(bpartnerIdentifierStr);
		return getProductPrices(bpartnerIdentifier, SOTrx.SALES, countryCode, dateStr);
	}

	@GetMapping("/{bpartnerIdentifier}/purchase/prices/{countryCode}")
	public ResponseEntity<JsonResponsePriceList> getPurchasePriceList(
			@ApiParam(required = true, value = BPARTNER_IDENTIFIER_DOC) //
			@PathVariable("bpartnerIdentifier") //
			@NonNull final String bpartnerIdentifierStr,
			//
			@ApiParam(required = true, value = "Country code (2 letters)") //
			@PathVariable("countryCode") //
			@NonNull final String countryCode,
			//
			@ApiParam(required = false, value = "Date on which the prices shall be valid. The format is 'yyyy-MM-dd'.") //
			@RequestParam(name = "date", required = false) //
			@Nullable final String dateStr)
	{
		final IdentifierString bpartnerIdentifier = IdentifierString.of(bpartnerIdentifierStr);
		return getProductPrices(bpartnerIdentifier, SOTrx.PURCHASE, countryCode, dateStr);
	}

	private ResponseEntity<JsonResponsePriceList> getProductPrices(
			@NonNull final IdentifierString bpartnerIdentifier,
			@NonNull final SOTrx soTrx,
			@NonNull final String countryCode,
			@Nullable final String dateStr)
	{
		try
		{
			final LocalDate date = !Check.isEmpty(dateStr) ? TimeUtil.asLocalDate(dateStr) : SystemTime.asLocalDate();

			final JsonResponsePriceList result = GetPriceListCommand.builder()
					.servicesFacade(servicesFacade)
					//
					.bpartnerIdentifier(bpartnerIdentifier)
					.soTrx(soTrx)
					.countryCode(countryCode)
					.date(date)
					.execute();

			return ResponseEntity.ok(result);
		}
		catch (final Exception ex)
		{
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body(JsonResponsePriceList.error(ex, Env.getADLanguageOrBaseLanguage()));
		}
	}
}
