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

import de.metas.common.rest_api.JsonAttributeInstance;
import de.metas.common.rest_api.JsonAttributeSetInstance;
import de.metas.common.rest_api.JsonMetasfreshId;
import de.metas.common.rest_api.JsonQuantity;
import de.metas.common.shipmentschedule.JsonCustomer;
import de.metas.common.shipmentschedule.JsonProduct;
import de.metas.common.shipmentschedule.JsonRequestShipmentCandidateResults;
import de.metas.common.shipmentschedule.JsonResponseShipmentCandidate;
import de.metas.common.shipmentschedule.JsonResponseShipmentCandidates;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;

@Service
public class ShipmentCandidateExporter
{
	/**
	 * Exports them; Flags them as "exported - don't touch"; creates an export audit table with one line per shipment schedule.
	 */
	public JsonResponseShipmentCandidates exportShipmentCandidates()
	{
		final JsonResponseShipmentCandidates result = JsonResponseShipmentCandidates.builder()
				.transactionKey("transactionKey")
				.item(JsonResponseShipmentCandidate.builder()
						.id(JsonMetasfreshId.of(10))
						.orgCode("orgCode")
						.dateOrdered(LocalDateTime.of(2020, Month.JULY, 14, 05, 48))
						.poReference("poReference")
						.orderDocumentNo("orderDocumentNo")
						.product(JsonProduct.builder().name("name").productNo("productNo").description("description").weight(BigDecimal.TEN).packageSize(BigDecimal.ONE).build())
						.customer(JsonCustomer.builder().street("street").streetNo("streetNo").postal("postal").city("city").build())
						.quantity(JsonQuantity.builder().qty(BigDecimal.ONE).uomCode("PCE").build())
						.quantity(JsonQuantity.builder().qty(BigDecimal.TEN).uomCode("KG").build())
						.attributeSetInstance(JsonAttributeSetInstance.builder().id(JsonMetasfreshId.of(20))
								.attributeInstance(JsonAttributeInstance.builder().attributeName("attributeName_1").attributeCode("attributeCode_1").valueInt(10).build())
								.attributeInstance(JsonAttributeInstance.builder().attributeName("attributeName_2").attributeCode("attributeCode_2").valueStr("valueStr").build())
								.build())
						.build())
				.build();
		return result;
	}

	/**
	 * Use the given pojo's transactionKey to load the respective export audit table and update its lines
	 */
	public void updateStatus(final JsonRequestShipmentCandidateResults status)
	{
	}
}
