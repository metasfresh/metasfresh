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
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class M_InOut_Line_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final StepDefData<I_M_InOut> shipmentTable;

	public M_InOut_Line_StepDef(@NonNull final StepDefData<I_M_InOut> shipmentTable)
	{
		this.shipmentTable = shipmentTable;
	}

	@And("validate the created shipment lines")
	public void validate_created_shipment_lines(@NonNull final DataTable table)
	{
		final List<Map<String, String>> dataTable = table.asMaps();
		for (final Map<String, String> row : dataTable)
		{
			final String shipmentIdentifier = DataTableUtil.extractStringForColumnName(row, "M_InOut_ID.Identifier");

			final I_M_InOut shipmentRecord = shipmentTable.get(shipmentIdentifier);

			final int productId = DataTableUtil.extractIntForColumnName(row, "M_Product_ID.Identifier");

			//dev-note: we assume the tests are not using the same product on different lines
			final I_M_InOutLine shipmentLineRecord = queryBL.createQueryBuilder(I_M_InOutLine.class)
					.addEqualsFilter(I_M_InOutLine.COLUMNNAME_M_InOut_ID, shipmentRecord.getM_InOut_ID())
					.addEqualsFilter(I_M_InOutLine.COLUMNNAME_M_Product_ID, productId)
					.create()
					.firstOnlyNotNull(I_M_InOutLine.class);

			validateShipmentLine(shipmentLineRecord, row);
		}
	}

	private void validateShipmentLine(@NonNull final I_M_InOutLine shipmentLine, @NonNull final Map<String, String> row)
	{
		final int productId = DataTableUtil.extractIntForColumnName(row, "M_Product_ID.Identifier");
		final BigDecimal movementqty = DataTableUtil.extractBigDecimalForColumnName(row, "movementqty");
		final boolean processed = DataTableUtil.extractBooleanForColumnName(row, "processed");

		assertThat(shipmentLine.getM_Product_ID()).isEqualTo(productId);
		assertThat(shipmentLine.getMovementQty()).isEqualTo(movementqty);
		assertThat(shipmentLine.isProcessed()).isEqualTo(processed);
	}
}
