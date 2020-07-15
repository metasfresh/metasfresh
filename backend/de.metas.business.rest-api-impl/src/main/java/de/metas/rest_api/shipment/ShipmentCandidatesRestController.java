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

package de.metas.rest_api.shipment;

import de.metas.Profiles;
import de.metas.common.rest_api.JsonAttributeInstance;
import de.metas.common.rest_api.JsonAttributeSetInstance;
import de.metas.common.rest_api.JsonMetasfreshId;
import de.metas.common.rest_api.JsonQuantity;
import de.metas.common.shipmentschedule.JsonCustomer;
import de.metas.common.shipmentschedule.JsonProduct;
import de.metas.common.shipmentschedule.JsonRequestShipmentCandidateResults;
import de.metas.common.shipmentschedule.JsonResponseShipmentCandidate;
import de.metas.common.shipmentschedule.JsonResponseShipmentCandidates;
import de.metas.util.web.MetasfreshRestAPIConstants;
import lombok.NonNull;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;

@RequestMapping(ShipmentCandidatesRestController.ENDPOINT)
@RestController
@Profile(Profiles.PROFILE_App)
public class ShipmentCandidatesRestController
{
	public static final String ENDPOINT = MetasfreshRestAPIConstants.ENDPOINT_API + "/shipments";

	private final ShipmentCandidateExporter shipmentCandidateExporter;

	public ShipmentCandidatesRestController(@NonNull final ShipmentCandidateExporter shipmentCandidateExporter)
	{
		this.shipmentCandidateExporter=shipmentCandidateExporter;
	}

	@GetMapping("shipmentCandidates")
	public ResponseEntity<JsonResponseShipmentCandidates> getShipmentCandidates()
	{
		final JsonResponseShipmentCandidates result = shipmentCandidateExporter.exportShipmentCandidates();
		return ResponseEntity.ok(result);
	}

	@PostMapping("shipmentCandidates")
	public ResponseEntity<String> postShipmentCandidatesStatus(@RequestBody @NonNull final JsonRequestShipmentCandidateResults status)
	{
		shipmentCandidateExporter.updateStatus(status);
		return ResponseEntity.accepted().body("Shipment candidates updated");
	}
}
