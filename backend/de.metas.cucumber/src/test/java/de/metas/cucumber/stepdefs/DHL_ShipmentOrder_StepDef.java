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

import de.metas.mpackage.PackageId;
import de.metas.shipper.gateway.dhl.model.I_DHL_ShipmentOrder;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Package;

import java.math.BigDecimal;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.assertThat;

@AllArgsConstructor
public class DHL_ShipmentOrder_StepDef
{
	private final M_Package_StepDefData packageTable;
	private final DHL_ShipmentOrder_StepDefData dhlShipmentOrderTable;
	private final C_BPartner_StepDefData bpartnerTable;
	private final C_BPartner_Location_StepDefData bpartnerLocationTable;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@And("^load DHL_ShipmentOrder:$")
	public void load_DHL_ShipmentOrder(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String packageIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_Package.COLUMNNAME_M_Package_ID + "." + TABLECOLUMN_IDENTIFIER);
			final PackageId packageId = PackageId.ofRepoId(packageTable.get(packageIdentifier).getM_Package_ID());

			final I_DHL_ShipmentOrder dhlShipmentOrder = queryBL.createQueryBuilder(I_DHL_ShipmentOrder.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_DHL_ShipmentOrder.COLUMNNAME_PackageId, packageId)
					.create()
					.firstOnly();
			assertThat(dhlShipmentOrder).as("DHL_ShipmentOrder").isNotNull();

			final String dhlShipmentOrderIdentifier = DataTableUtil.extractStringForColumnName(row, I_DHL_ShipmentOrder.COLUMNNAME_DHL_ShipmentOrder_ID + "." + TABLECOLUMN_IDENTIFIER);
			dhlShipmentOrderTable.putOrReplace(dhlShipmentOrderIdentifier, dhlShipmentOrder);
		}
	}

	@And("validate DHL_ShipmentOrder:")
	public void validate_DHL_ShipmentOrder(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final SoftAssertions softly = new SoftAssertions();

			final String dhlShipmentOrderIdentifier = DataTableUtil.extractStringForColumnName(row, I_DHL_ShipmentOrder.COLUMNNAME_DHL_ShipmentOrder_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_DHL_ShipmentOrder dhlShipmentOrder = dhlShipmentOrderTable.get(dhlShipmentOrderIdentifier);

			final String bpartnerIdentifier = DataTableUtil.extractStringForColumnName(row, I_DHL_ShipmentOrder.COLUMNNAME_C_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_BPartner bPartner = bpartnerTable.get(bpartnerIdentifier);

			softly.assertThat(bPartner.getC_BPartner_ID()).isEqualTo(dhlShipmentOrder.getC_BPartner_ID());

			final Integer lengthInCm = DataTableUtil.extractIntForColumnName(row, I_DHL_ShipmentOrder.COLUMNNAME_DHL_LengthInCm);
			softly.assertThat(lengthInCm).as("lengthInCm").isEqualTo(dhlShipmentOrder.getDHL_LengthInCm());
			final Integer heightInCm = DataTableUtil.extractIntForColumnName(row, I_DHL_ShipmentOrder.COLUMNNAME_DHL_HeightInCm);
			softly.assertThat(heightInCm).as("heightInCm").isEqualTo(dhlShipmentOrder.getDHL_HeightInCm());
			final Integer widthInCm = DataTableUtil.extractIntForColumnName(row, I_DHL_ShipmentOrder.COLUMNNAME_DHL_WidthInCm);
			softly.assertThat(widthInCm).as("widthInCm").isEqualTo(dhlShipmentOrder.getDHL_WidthInCm());
			final BigDecimal weightInKg = DataTableUtil.extractBigDecimalForColumnName(row, I_DHL_ShipmentOrder.COLUMNNAME_DHL_WeightInKg);
			softly.assertThat(weightInKg).as("weightInKg").isEqualTo(dhlShipmentOrder.getDHL_WeightInKg());

			softly.assertAll();
		}
	}
}
