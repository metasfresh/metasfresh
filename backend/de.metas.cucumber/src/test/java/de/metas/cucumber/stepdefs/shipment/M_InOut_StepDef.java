/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2021 metas GmbH
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

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefData;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.compiere.model.I_M_InOut;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class M_InOut_StepDef
{
	private final StepDefData<I_M_InOut> shipmentTable;

	public M_InOut_StepDef(@NonNull final StepDefData<I_M_InOut> shipmentTable)
	{
		this.shipmentTable = shipmentTable;
	}

	@And("validate created shipments")
	public void validate_created_shipments(@NonNull final DataTable table)
	{
		final List<Map<String, String>> dataTable = table.asMaps();
		for (final Map<String, String> row : dataTable)
		{
			final String identifier = DataTableUtil.extractStringForColumnName(row, "Shipment.Identifier");
			final int bpartnerId = DataTableUtil.extractIntForColumnName(row, "c_bpartner_id");
			final int bpartnerLocationId = DataTableUtil.extractIntForColumnName(row, "c_bpartner_location_id");
			final Timestamp dateOrdered = DataTableUtil.extractDateTimestampForColumnName(row, "dateordered");
			final String poReference = DataTableUtil.extractStringForColumnName(row, "poreference");
			final boolean processed = DataTableUtil.extractBooleanForColumnName(row, "processed");
			final String docStatus = DataTableUtil.extractStringForColumnName(row, "docStatus");

			final I_M_InOut shipment = shipmentTable.get(identifier);

			assertThat(shipment.getC_BPartner_ID()).isEqualTo(bpartnerId);
			assertThat(shipment.getC_BPartner_Location_ID()).isEqualTo(bpartnerLocationId);
			assertThat(shipment.getDateOrdered()).isEqualTo(dateOrdered);
			assertThat(shipment.getPOReference()).isEqualTo(poReference);
			assertThat(shipment.isProcessed()).isEqualTo(processed);
			assertThat(shipment.getDocStatus()).isEqualTo(docStatus);
		}
	}
}
