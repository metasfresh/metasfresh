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
import de.metas.commons.shipmentschedule.JsonResponseShipmentSchedule;
import de.metas.commons.shipmentschedule.JsonResponseShipmentScheduleList;
import de.metas.util.web.MetasfreshRestAPIConstants;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(ShipmentCandidatesRestController.ENDPOINT)
@RestController
@Profile(Profiles.PROFILE_App)
public class ShipmentCandidatesRestController
{
	public static final String ENDPOINT = MetasfreshRestAPIConstants.ENDPOINT_API + "/shipments";

	@GetMapping("shipmentSchedules")
	public ResponseEntity<JsonResponseShipmentScheduleList> getShipmentSchedules()
	{
		final JsonResponseShipmentScheduleList result = JsonResponseShipmentScheduleList.builder()
				.responseItem(JsonResponseShipmentSchedule.builder().orderDocumentNo("123").build())
				.build();
		return ResponseEntity.ok(result);
	}

	@PostMapping("shipmentSchedules")
	public ResponseEntity<String> postShipmentScheduleStatus()
	{
		return ResponseEntity.accepted().body("Shipment candidates updated");
	}
}
