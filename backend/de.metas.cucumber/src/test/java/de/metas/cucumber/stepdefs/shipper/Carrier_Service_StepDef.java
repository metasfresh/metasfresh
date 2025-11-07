/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.cucumber.stepdefs.shipper;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.ValueAndName;
import de.metas.cucumber.stepdefs.shipmentschedule.M_ShipmentSchedule_StepDefData;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.CarrierService;
import de.metas.inoutcandidate.CarrierShipmentScheduleServiceRepository;
import de.metas.shipper.gateway.commons.model.CarrierShipmentOrderServiceRepository;
import de.metas.shipping.ShipperId;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_Carrier_Service;
import org.compiere.model.I_M_ShipmentSchedule_Carrier_Service;

import static de.metas.frontend_testing.expectations.assertions.Assertions.assertThat;

@RequiredArgsConstructor
public class Carrier_Service_StepDef
{
	@NonNull private final CarrierShipmentOrderServiceRepository carrierServiceRepository = SpringContextHolder.instance.getBean(CarrierShipmentOrderServiceRepository.class);
	@NonNull private final CarrierShipmentScheduleServiceRepository carrierShipmentScheduleServiceRepository = SpringContextHolder.instance.getBean(CarrierShipmentScheduleServiceRepository.class);

	@NonNull private final Carrier_Service_StepDefData carrierServiceTable;
	@NonNull private final M_Shipper_StepDefData shipperTable;
	@NonNull private final M_ShipmentSchedule_StepDefData shipmentScheduleTable;

	@And("metasfresh contains Carrier_Services:")
	public void add_Carrier_Services(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createCarrier_Service);
	}

	private void createCarrier_Service(@NonNull final DataTableRow row)
	{
		final ValueAndName valueAndName = row.suggestValueAndName();
		final ShipperId shipperId = shipperTable.getId(row.getAsIdentifier(I_Carrier_Service.COLUMNNAME_M_Shipper_ID));

		final CarrierService carrierService = carrierServiceRepository.getOrCreateService(shipperId, valueAndName.getValue(), valueAndName.getName());

		carrierServiceTable.put(row.getAsIdentifier(), carrierService);
	}

	@And("M_ShipmentSchedule has no carrier services assigned")
	public void m_shipmentSchedule_has_no_carrier_services(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.forEach(row -> {
			final ShipmentScheduleId shipmentScheduleId = row.getAsIdentifier(I_M_ShipmentSchedule_Carrier_Service.COLUMNNAME_M_ShipmentSchedule_ID).lookupNotNullIdIn(shipmentScheduleTable);
			assertThat(carrierShipmentScheduleServiceRepository.getAssignedServiceIdsByShipmentScheduleId(shipmentScheduleId)).isEmpty();
		});
	}
}
