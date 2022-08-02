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

import de.metas.cucumber.stepdefs.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.project.C_Project_StepDefData;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_Project;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.compiere.model.I_M_InOutLine.COLUMNNAME_M_Product_ID;

public class M_InOut_Line_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	private final M_InOut_StepDefData shipmentTable;
	private final M_InOutLine_StepDefData shipmentLineTable;
	private final C_OrderLine_StepDefData orderLineTable;
	private final M_Product_StepDefData productTable;
	private final C_Project_StepDefData projectTable;

	public M_InOut_Line_StepDef(
			@NonNull final M_InOut_StepDefData shipmentTable,
			@NonNull final M_InOutLine_StepDefData shipmentLineTable,
			@NonNull final C_OrderLine_StepDefData orderLineTable,
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final C_Project_StepDefData projectTable)
	{
		this.shipmentTable = shipmentTable;
		this.shipmentLineTable = shipmentLineTable;
		this.orderLineTable = orderLineTable;
		this.productTable = productTable;
		this.projectTable = projectTable;
	}

	@And("^validate the created (shipment|material receipt) lines$")
	public void validate_created_M_InOutLine(@NonNull final String model_UNUSED, @NonNull final DataTable table)
	{
		final List<Map<String, String>> dataTable = table.asMaps();
		for (final Map<String, String> row : dataTable)
		{
			final String shipmentIdentifier = DataTableUtil.extractStringForColumnName(row, "M_InOut_ID.Identifier");

			final I_M_InOut shipmentRecord = shipmentTable.get(shipmentIdentifier);

			final String productIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
			final Integer expectedProductId = productTable.getOptional(productIdentifier)
					.map(I_M_Product::getM_Product_ID)
					.orElseGet(() -> Integer.parseInt(productIdentifier));

			//dev-note: we assume the tests are not using the same product on different lines
			final IQueryBuilder<I_M_InOutLine> lineQueryBuilder = queryBL.createQueryBuilder(I_M_InOutLine.class)
					.addEqualsFilter(I_M_InOutLine.COLUMNNAME_M_InOut_ID, shipmentRecord.getM_InOut_ID())
					.addEqualsFilter(COLUMNNAME_M_Product_ID, expectedProductId);

			final String orderLineIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_M_InOutLine.COLUMNNAME_C_OrderLine_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(orderLineIdentifier))
			{
				final I_C_OrderLine orderLine = orderLineTable.get(orderLineIdentifier);
				lineQueryBuilder.addEqualsFilter(I_M_InOutLine.COLUMNNAME_C_OrderLine_ID, orderLine.getC_OrderLine_ID());
			}

			final I_M_InOutLine shipmentLineRecord = lineQueryBuilder
					.create()
					.firstOnlyNotNull(I_M_InOutLine.class);

			validateShipmentLine(shipmentLineRecord, row);

			final String shipmentLineIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_InOutLine.COLUMNNAME_M_InOutLine_ID + "." + TABLECOLUMN_IDENTIFIER);
			shipmentLineTable.putOrReplace(shipmentLineIdentifier, shipmentLineRecord);
		}
	}

	private void validateShipmentLine(@NonNull final I_M_InOutLine shipmentLine, @NonNull final Map<String, String> row)
	{
		final String productIdentifier = DataTableUtil.extractStringForColumnName(row, "M_Product_ID.Identifier");
		final Integer expectedProductId = productTable.getOptional(productIdentifier)
				.map(I_M_Product::getM_Product_ID)
				.orElseGet(() -> Integer.parseInt(productIdentifier));

		final BigDecimal movementqty = DataTableUtil.extractBigDecimalForColumnName(row, "movementqty");
		final boolean processed = DataTableUtil.extractBooleanForColumnName(row, "processed");

		final String x12de355Code = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_M_InOutLine.COLUMNNAME_C_UOM_ID + "." + X12DE355.class.getSimpleName());

		if (Check.isNotBlank(x12de355Code))
		{
			final UomId uomId = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(x12de355Code));
			assertThat(shipmentLine.getC_UOM_ID()).isEqualTo(uomId.getRepoId());
		}

		assertThat(shipmentLine.getM_Product_ID()).isEqualTo(expectedProductId);
		assertThat(shipmentLine.getMovementQty()).isEqualByComparingTo(movementqty);
		assertThat(shipmentLine.isProcessed()).isEqualTo(processed);

		final String projectIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_M_InOutLine.COLUMNNAME_C_Project_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		if (Check.isNotBlank(projectIdentifier))
		{
			final Integer projectId = projectTable.getOptional(projectIdentifier)
					.map(I_C_Project::getC_Project_ID)
					.orElseGet(() -> Integer.parseInt(projectIdentifier));

			assertThat(shipmentLine.getC_Project_ID()).isEqualTo(projectId);
		}
	}
}
