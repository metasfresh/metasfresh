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

package de.metas.cucumber.stepdefs;

import de.metas.bpartner.BPartnerId;
import de.metas.cucumber.stepdefs.context.SharedTestContext;
import de.metas.mpackage.PackageId;
import de.metas.shipper.gateway.dhl.DhlDeliveryOrderRepository;
import de.metas.shipper.gateway.dhl.model.I_DHL_ShipmentOrder;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.assertj.core.api.SoftAssertions;
import org.compiere.SpringContextHolder;

@RequiredArgsConstructor
public class DHL_ShipmentOrder_StepDef
{
	@NonNull private final DhlDeliveryOrderRepository dhlDeliveryOrderRepository = SpringContextHolder.instance.getBean(DhlDeliveryOrderRepository.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final M_Package_StepDefData packageTable;
	@NonNull private final DHL_ShipmentOrder_StepDefData dhlShipmentOrderTable;
	@NonNull private final C_BPartner_StepDefData bpartnerTable;
	@NonNull private final C_BPartner_Location_StepDefData bpartnerLocationTable;

	@And("validate DHL_ShipmentOrder:")
	public void validateDHLShipmentOrders(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.forEach(this::validateDHLShipmentOrder);
	}

	private void validateDHLShipmentOrder(final DataTableRow row)
	{
		final PackageId packageId = row.getAsIdentifier("M_Package_ID").lookupNotNullIdIn(packageTable);
		SharedTestContext.put("packageId", packageId);

		final I_DHL_ShipmentOrder dhlShipmentOrder = dhlDeliveryOrderRepository.getShipmentOrderByPackageId(packageId)
				.orElseThrow(() -> new AdempiereException("No DHL_ShipmentOrder found for " + packageId));
		SharedTestContext.put("dhlShipmentOrder", dhlShipmentOrder);

		final SoftAssertions softly = new SoftAssertions();

		row.getAsOptionalIdentifier(I_DHL_ShipmentOrder.COLUMNNAME_C_BPartner_ID)
				.map(bpartnerTable::getId)
				.ifPresent(bpartnerId -> softly.assertThat(BPartnerId.ofRepoIdOrNull(dhlShipmentOrder.getC_BPartner_ID())).isEqualTo(bpartnerId));
		row.getAsOptionalInt(I_DHL_ShipmentOrder.COLUMNNAME_DHL_LengthInCm)
				.ifPresent(lengthInCm -> softly.assertThat(dhlShipmentOrder.getDHL_LengthInCm()).as("LengthInCm").isEqualTo(lengthInCm));
		row.getAsOptionalInt(I_DHL_ShipmentOrder.COLUMNNAME_DHL_HeightInCm)
				.ifPresent(heightInCm -> softly.assertThat(dhlShipmentOrder.getDHL_HeightInCm()).as("HeightInCm").isEqualTo(heightInCm));
		row.getAsOptionalInt(I_DHL_ShipmentOrder.COLUMNNAME_DHL_WidthInCm)
				.ifPresent(widthInCm -> softly.assertThat(dhlShipmentOrder.getDHL_WidthInCm()).as("WidthInCm").isEqualTo(widthInCm));
		row.getAsOptionalBigDecimal(I_DHL_ShipmentOrder.COLUMNNAME_DHL_WeightInKg)
				.ifPresent(weightInKg -> softly.assertThat(dhlShipmentOrder.getDHL_WeightInKg()).as("WeightInKg").isEqualByComparingTo(weightInKg));

		softly.assertAll();
	}

}
