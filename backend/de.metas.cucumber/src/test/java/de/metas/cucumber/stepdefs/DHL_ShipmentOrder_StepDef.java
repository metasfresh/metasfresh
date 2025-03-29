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
		DataTableRows.of(dataTable)
				.forEach(row -> {
					final SoftAssertions softly = new SoftAssertions();

					final I_DHL_ShipmentOrder dhlShipmentOrder = row.getAsIdentifier(I_DHL_ShipmentOrder.COLUMNNAME_DHL_ShipmentOrder_ID).lookupNotNullIn( dhlShipmentOrderTable);

					final BPartnerId bpartnerId = row.getAsIdentifier(I_DHL_ShipmentOrder.COLUMNNAME_C_BPartner_ID).lookupNotNullIdIn(bpartnerTable);
					softly.assertThat(BPartnerId.ofRepoIdOrNull(dhlShipmentOrder.getC_BPartner_ID())).isEqualTo(bpartnerId);

					row.getAsOptionalInt(I_DHL_ShipmentOrder.COLUMNNAME_DHL_LengthInCm)
							.ifPresent(lengthInCm -> softly.assertThat(dhlShipmentOrder.getDHL_LengthInCm()).as("LengthInCm").isEqualTo(lengthInCm));
					row.getAsOptionalInt(I_DHL_ShipmentOrder.COLUMNNAME_DHL_HeightInCm)
							.ifPresent(heightInCm -> softly.assertThat(dhlShipmentOrder.getDHL_HeightInCm()).as("HeightInCm").isEqualTo(heightInCm));
					row.getAsOptionalInt(I_DHL_ShipmentOrder.COLUMNNAME_DHL_WidthInCm)
							.ifPresent(widthInCm -> softly.assertThat(dhlShipmentOrder.getDHL_WidthInCm()).as("WidthInCm").isEqualTo(widthInCm));
					row.getAsOptionalBigDecimal(I_DHL_ShipmentOrder.COLUMNNAME_DHL_WeightInKg)
							.ifPresent(weightInKg -> softly.assertThat(dhlShipmentOrder.getDHL_WeightInKg()).as("WeightInKg").isEqualByComparingTo(weightInKg));

					softly.assertAll();
				});
	}
}
