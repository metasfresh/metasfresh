/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.cucumber.stepdefs.shipment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ImmutableList;
import de.metas.JsonObjectMapperHolder;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.shipping.v2.shipment.JsonCreateShipmentInfo;
import de.metas.common.shipping.v2.shipment.JsonCreateShipmentRequest;
import de.metas.common.shipping.v2.shipment.ShipmentScheduleIdentifier;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.cucumber.stepdefs.shipmentschedule.M_ShipmentSchedule_StepDefData;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

public class ShipmentRestController_StepDef
{
	private final M_ShipmentSchedule_StepDefData shipmentScheduleTable;
	private final M_Product_StepDefData productTable;
	private final TestContext testContext;

	public ShipmentRestController_StepDef(
			@NonNull final M_ShipmentSchedule_StepDefData shipmentScheduleTable,
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final TestContext testContext)
	{
		this.shipmentScheduleTable = shipmentScheduleTable;
		this.productTable = productTable;
		this.testContext = testContext;
	}

	@And("the user creates a JsonCreateShipmentRequest and stores it in the context")
	public void set_JsonCreateShipmentRequest_to_context(@NonNull final DataTable dataTable) throws JsonProcessingException
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String shipmentScheduleIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID + ".Identifier");
			final I_M_ShipmentSchedule shipmentScheduleRecord = shipmentScheduleTable.get(shipmentScheduleIdentifier);

			final BigDecimal movementQuantity = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_M_InOutLine.COLUMNNAME_MovementQty);

			final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_Product.COLUMNNAME_M_Product_ID + ".Identifier");
			final I_M_Product productRecord = productTable.get(productIdentifier);

			final String deliveryRule = DataTableUtil.extractStringForColumnName(tableRow, I_M_ShipmentSchedule.COLUMNNAME_DeliveryRule);
			final ZonedDateTime movementDate = DataTableUtil.extractZonedDateTimeForColumnName(tableRow, I_M_InOut.COLUMNNAME_MovementDate);

			final JsonCreateShipmentRequest jsonCreateShipmentRequest = JsonCreateShipmentRequest.builder()
					.createShipmentInfoList(ImmutableList.of(JsonCreateShipmentInfo.builder()
																	 .deliveryRule(deliveryRule)
																	 .movementDate(LocalDateTime.from(movementDate))
																	 .movementQuantity(movementQuantity)
																	 .productSearchKey(productRecord.getValue())
																	 .shipmentScheduleIdentifier(
																			 ShipmentScheduleIdentifier.builder()
																					 .shipmentScheduleId(JsonMetasfreshId.of(shipmentScheduleRecord.getM_ShipmentSchedule_ID()))
																					 .build())
																	 .build()))
					.build();

			testContext.setRequestPayload(JsonObjectMapperHolder.newJsonObjectMapper().writeValueAsString(jsonCreateShipmentRequest));
		}
	}
}