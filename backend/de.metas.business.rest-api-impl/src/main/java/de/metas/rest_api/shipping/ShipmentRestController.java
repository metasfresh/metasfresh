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
import de.metas.common.shipment.JsonCreateShipmentRequest;
import de.metas.inout.model.I_M_InOut;
import de.metas.inoutcandidate.api.InOutGenerateResult;
import de.metas.logging.LogManager;
import de.metas.rest_api.shipping.info.GenerateShipmentsRequest;
import de.metas.util.web.MetasfreshRestAPIConstants;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
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

	private final de.metas.rest_api.shipping.ShipmentService shipmentService;

	public ShipmentRestController(final de.metas.rest_api.shipping.ShipmentService shipmentService)
	{
		this.shipmentService = shipmentService;
	}

	@PostMapping
	public ResponseEntity<?> createShipments(@RequestBody final JsonCreateShipmentRequest request)
	{
		log.debug("*** createShipments: Started with request: {}", request);

		try
		{
			shipmentService.validateRequest(request);
		}
		catch (final Exception e)
		{
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		request.getCreateShipmentInfoList()
				.stream()
				.map(shipmentService::buildUpdateShipmentScheduleRequest)
				.forEach(shipmentService::updateShipmentSchedule);

		final GenerateShipmentsRequest generateShipmentsRequest = shipmentService.buildGenerateShipmentsRequest(request);

		final InOutGenerateResult result = shipmentService.generateShipments(generateShipmentsRequest);

		final ImmutableList<Integer> shipmentIds = result
				.getInOuts()
				.stream()
				.map(I_M_InOut::getM_InOut_ID)
				.collect(ImmutableList.toImmutableList());

		log.debug("*** createShipments: Execution done! Created shipment ids: {}", shipmentIds);

		return ResponseEntity.ok(shipmentIds);
	}
}
