/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.rest_api.shipping;

import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.common.rest_api.JsonMetasfreshId;
import de.metas.common.shipment.JsonCreateShipmentRequest;
import de.metas.common.shipment.JsonCreateShipmentResponse;
import de.metas.common.shipment.mpackage.JsonCreateShippingPackagesRequest;
import de.metas.inout.model.I_M_InOut;
import de.metas.inoutcandidate.api.InOutGenerateResult;
import de.metas.logging.LogManager;
import de.metas.rest_api.shipping.mpackage.ShippingPackageService;
import de.metas.rest_api.utils.JsonErrors;
import de.metas.util.Services;
import de.metas.util.web.MetasfreshRestAPIConstants;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(ShipmentRestController.ENDPOINT)
@RestController
@Profile(Profiles.PROFILE_App)
public class ShipmentRestController
{
	public static final String ENDPOINT = MetasfreshRestAPIConstants.ENDPOINT_API + "/shipment";

	private static final Logger log = LogManager.getLogger(ShipmentRestController.class);

	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	private final de.metas.rest_api.shipping.ShipmentService shipmentService;
	private final de.metas.rest_api.shipping.mpackage.ShippingPackageService shippingPackageService;

	public ShipmentRestController(@NonNull final ShipmentService shipmentService, final ShippingPackageService shippingPackageService)
	{
		this.shipmentService = shipmentService;
		this.shippingPackageService = shippingPackageService;
	}

	@PostMapping
	public ResponseEntity<?> createShipments(@RequestBody final JsonCreateShipmentRequest request)
	{
		log.debug("*** createShipments: Started with request: {}", request);

		try
		{
			final InOutGenerateResult result = shipmentService.updateShipmentSchedulesAndGenerateShipments(request);

			final ImmutableList<JsonMetasfreshId> shipmentIds = extractShipmentIds(result);
			final JsonCreateShipmentResponse jsonCreateShipmentResponse = JsonCreateShipmentResponse
					.builder()
					.createdShipmentIdList(shipmentIds)
					.build();

			log.debug("*** createShipments: Execution done! Created shipment ids: {}", shipmentIds);

			return ResponseEntity.ok(jsonCreateShipmentResponse);
		}
		catch (final Exception ex)
		{
			log.error(ex.getMessage(), ex);

			final String adLanguage = Env.getADLanguageOrBaseLanguage();
			return ResponseEntity.badRequest()
					.body(JsonErrors.ofThrowable(ex, adLanguage));
		}
	}

	@PostMapping("/packages")
	public ResponseEntity<?> createShippingPackages(@RequestBody final JsonCreateShippingPackagesRequest request)
	{
		log.debug("*** createShippingPackages: Started with request: {}", request);

		try
		{
			trxManager.runInNewTrx(() -> shippingPackageService.generateShippingPackages(request));

			log.debug("*** createShippingPackages: Execution done!");

			return ResponseEntity.status(HttpStatus.OK).build();
		}
		catch (final Exception ex)
		{
			log.error(ex.getMessage(), ex);

			final String adLanguage = Env.getADLanguageOrBaseLanguage();
			return ResponseEntity.badRequest()
					.body(JsonErrors.ofThrowable(ex, adLanguage));
		}
	}

	private static ImmutableList<JsonMetasfreshId> extractShipmentIds(final InOutGenerateResult result)
	{
		return result.getInOuts()
				.stream()
				.map(I_M_InOut::getM_InOut_ID)
				.map(JsonMetasfreshId::of)
				.collect(ImmutableList.toImmutableList());
	}
}
