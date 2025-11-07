/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.cucumber.stepdefs.shipmentschedule.process;

import com.google.common.collect.ImmutableSet;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.shipmentschedule.M_ShipmentSchedule_StepDefData;
import de.metas.cucumber.stepdefs.shipper.Carrier_Goods_Type_StepDefData;
import de.metas.cucumber.stepdefs.shipper.Carrier_Product_StepDefData;
import de.metas.cucumber.stepdefs.shipper.Carrier_Service_StepDefData;
import de.metas.cucumber.stepdefs.shipper.M_Shipper_StepDefData;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.CarrierGoodsTypeId;
import de.metas.inoutcandidate.CarrierProductId;
import de.metas.inoutcandidate.CarrierServiceId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.shipper.gateway.commons.process.CarrierAdviseProcessService;
import de.metas.shipper.gateway.commons.process.CarrierAdviseUpdateRequest;
import de.metas.shipping.ShipperId;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_Carrier_Goods_Type;
import org.compiere.model.I_Carrier_Product;
import org.compiere.model.I_Carrier_Service;

import java.util.ArrayList;
import java.util.List;

import static de.metas.inoutcandidate.model.I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID;

@RequiredArgsConstructor
public class M_ShipmentSchedule_Advise_StepDef
{
	@NonNull private final CarrierAdviseProcessService carrierAdviseProcessService = SpringContextHolder.instance.getBean(CarrierAdviseProcessService.class);

	@NonNull private final M_ShipmentSchedule_StepDefData shipmentScheduleTable;
	@NonNull private final M_Shipper_StepDefData shipperTable;
	@NonNull private final Carrier_Product_StepDefData carrierProductTable;
	@NonNull private final Carrier_Goods_Type_StepDefData carrierGoodsTypeTable;
	@NonNull private final Carrier_Service_StepDefData carrierServiceTable;

	@And("Process M_ShipmentSchedule_Advise_Manual is run")
	public void runM_ShipmentSchedule_Advise_Manual(@NonNull final DataTable dataTable)
	{

		final DataTableRow row = DataTableRows.of(dataTable).singleRow();
		final ShipmentScheduleId shipmentScheduleId = shipmentScheduleTable.getId(row.getAsIdentifier(COLUMNNAME_M_ShipmentSchedule_ID));
		final ShipperId shipperId = shipperTable.getId(row.getAsIdentifier(I_M_ShipmentSchedule.COLUMNNAME_M_Shipper_ID));
		final CarrierProductId carrierProductId = carrierProductTable.getId(row.getAsIdentifier(I_Carrier_Product.COLUMNNAME_Carrier_Product_ID));
		final CarrierGoodsTypeId carrierGoodsTypeId = carrierGoodsTypeTable.getId(row.getAsIdentifier(I_Carrier_Goods_Type.COLUMNNAME_Carrier_Goods_Type_ID));

		final List<CarrierServiceId> carrierServiceIds = new ArrayList<>();
		row.getAsOptionalIdentifier(I_Carrier_Service.COLUMNNAME_Carrier_Service_ID)
				.ifPresent(identifier -> carrierServiceIds.add(identifier.lookupNotNullIdIn(carrierServiceTable)));
		row.getAsOptionalIdentifier(I_Carrier_Service.COLUMNNAME_Carrier_Service_ID + "2")
				.ifPresent(identifier -> carrierServiceIds.add(identifier.lookupNotNullIdIn(carrierServiceTable)));

		carrierAdviseProcessService.updateEligibleShipmentSchedules(
				CarrierAdviseUpdateRequest.builder()
						.query(de.metas.inoutcandidate.ShipmentScheduleQuery.builder()
								.shipperId(shipperId)
								.shipmentScheduleId(shipmentScheduleId)
								.build())
						.isIncludeCarrierAdviseManual(false)
						.carrierProductId(carrierProductId)
						.carrierGoodsTypeId(carrierGoodsTypeId)
						.carrierServiceIds(carrierServiceIds)
						.build()
		);
	}

	@And("Process M_ShipmentSchedule_Advise is run")
	public void runM_ShipmentSchedule_Advise(@NonNull final DataTable dataTable)
	{
		final DataTableRow row = DataTableRows.of(dataTable).singleRow();
		final ShipmentScheduleId shipmentScheduleId = shipmentScheduleTable.getId(row.getAsIdentifier(COLUMNNAME_M_ShipmentSchedule_ID));
		final boolean isIncludeCarrierAdviseManual = row.getAsOptionalBoolean("IsIncludeCarrierAdviseManual").orElse(false);
		carrierAdviseProcessService.requestCarrierAdvises(ImmutableSet.of(shipmentScheduleId), isIncludeCarrierAdviseManual);
	}
}
