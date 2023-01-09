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

import de.metas.common.util.Check;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Locator_StepDefData;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.uom.IUOMDAO;
import de.metas.uom.X12DE355;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_M_InOut.COLUMNNAME_M_InOut_ID;
import static org.compiere.model.I_M_InOutLine.COLUMNNAME_M_InOutLine_ID;
import static org.compiere.model.I_M_InOutLine.COLUMNNAME_M_Locator_ID;
import static org.compiere.model.I_M_InOutLine.COLUMNNAME_M_Product_ID;
import static org.compiere.model.I_M_InOutLine.COLUMNNAME_MovementQty;
import static org.compiere.model.I_M_InOutLine.COLUMNNAME_QtyEntered;

public class M_InOut_Line_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IUOMDAO uomDao = Services.get(IUOMDAO.class);

	private final M_InOut_StepDefData shipmentTable;
	private final M_InOutLine_StepDefData shipmentLineTable;
	private final M_Product_StepDefData productTable;
	private final M_Locator_StepDefData locatorTable;

	public M_InOut_Line_StepDef(
			@NonNull final M_InOut_StepDefData shipmentTable,
			@NonNull final M_InOutLine_StepDefData shipmentLineTable,
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final M_Locator_StepDefData locatorTable)
	{
		this.shipmentTable = shipmentTable;
		this.shipmentLineTable = shipmentLineTable;
		this.productTable = productTable;
		this.locatorTable = locatorTable;
	}

	@And("validate the created shipment lines")
	public void validate_created_shipment_lines(@NonNull final DataTable table)
	{
		final List<Map<String, String>> dataTable = table.asMaps();
		for (final Map<String, String> row : dataTable)
		{
			final String shipmentIdentifier = DataTableUtil.extractStringForColumnName(row, "Shipment.Identifier");

			final I_M_InOut shipmentRecord = shipmentTable.get(shipmentIdentifier);

			final int productId = DataTableUtil.extractIntForColumnName(row, "productIdentifier.m_product_id");

			//dev-note: we assume the tests are not using the same product on different lines
			final I_M_InOutLine shipmentLineRecord = queryBL.createQueryBuilder(I_M_InOutLine.class)
					.addEqualsFilter(I_M_InOutLine.COLUMNNAME_M_InOut_ID, shipmentRecord.getM_InOut_ID())
					.addEqualsFilter(COLUMNNAME_M_Product_ID, productId)
					.create()
					.firstOnlyNotNull(I_M_InOutLine.class);

			validateShipmentLine(shipmentLineRecord, row);
		}
	}

	@And("metasfresh contains M_InOutLine")
	public void metasfresh_contains_M_InOutLine(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> table = dataTable.asMaps();
		for (final Map<String, String> row : table)
		{
			final de.metas.inout.model.I_M_InOutLine inOutLine = InterfaceWrapperHelper.newInstance(de.metas.inout.model.I_M_InOutLine.class);

			final String inOutIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_InOut_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_InOut inOut = shipmentTable.get(inOutIdentifier);
			assertThat(inOut).isNotNull();

			inOutLine.setM_InOut_ID(inOut.getM_InOut_ID());

			final BigDecimal qtyEntered = DataTableUtil.extractBigDecimalForColumnName(row, COLUMNNAME_QtyEntered);
			inOutLine.setQtyEntered(qtyEntered);

			final BigDecimal movementQty = DataTableUtil.extractBigDecimalForColumnName(row, COLUMNNAME_MovementQty);
			inOutLine.setMovementQty(movementQty);

			final String uomCode = DataTableUtil.extractStringForColumnName(row, "UomCode");
			final I_C_UOM uom = uomDao.getByX12DE355(X12DE355.ofCode(uomCode));
			assertThat(uom).isNotNull();

			inOutLine.setC_UOM_ID(uom.getC_UOM_ID());

			final String productIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(productIdentifier))
			{
				final I_M_Product product = productTable.get(productIdentifier);
				assertThat(product).isNotNull();

				inOutLine.setM_Product_ID(product.getM_Product_ID());
			}

			final String locatorIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_M_Locator_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(locatorIdentifier))
			{
				final I_M_Locator locator = locatorTable.get(locatorIdentifier);
				assertThat(locator).isNotNull();

				inOutLine.setM_Locator_ID(locator.getM_Locator_ID());
			}

			InterfaceWrapperHelper.saveRecord(inOutLine);

			final String inOutLineIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_InOutLine_ID + "." + TABLECOLUMN_IDENTIFIER);
			shipmentLineTable.putOrReplace(inOutLineIdentifier, inOutLine);
		}
	}

	private void validateShipmentLine(@NonNull final I_M_InOutLine shipmentLine, @NonNull final Map<String, String> row)
	{
		final int productId = DataTableUtil.extractIntForColumnName(row, "productIdentifier.m_product_id");
		final BigDecimal movementqty = DataTableUtil.extractBigDecimalForColumnName(row, "movementqty");
		final boolean processed = DataTableUtil.extractBooleanForColumnName(row, "processed");

		assertThat(shipmentLine.getM_Product_ID()).isEqualTo(productId);
		assertThat(shipmentLine.getMovementQty()).isEqualTo(movementqty);
		assertThat(shipmentLine.isProcessed()).isEqualTo(processed);
	}
}
