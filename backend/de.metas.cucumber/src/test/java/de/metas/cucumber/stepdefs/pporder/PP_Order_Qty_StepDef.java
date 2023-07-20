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

package de.metas.cucumber.stepdefs.pporder;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.hu.M_HU_StepDefData;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.handlingunits.pporder.api.impl.hu_pporder_issue_producer.CreateDraftIssuesCommand;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.compiere.model.I_M_Product;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.testcontainers.shaded.com.google.common.collect.ImmutableList;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PP_Order_Qty_StepDef
{
	private final PP_Order_Qty_StepDefData ppOrderQtyTable;
	private final M_HU_StepDefData huTable;
	private final M_Product_StepDefData productTable;
	private final PP_Order_BOMLine_StepDefData ppOrderBOMLineTable;

	public PP_Order_Qty_StepDef(
			@NonNull final PP_Order_Qty_StepDefData ppOrderQtyTable,
			@NonNull final M_HU_StepDefData huTable,
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final PP_Order_BOMLine_StepDefData ppOrderBOMLineTable)
	{
		this.ppOrderQtyTable = ppOrderQtyTable;
		this.huTable = huTable;
		this.productTable = productTable;
		this.ppOrderBOMLineTable = ppOrderBOMLineTable;
	}

	@And("select M_HU to be issued for productionOrder")
	public void preselect_hu_to_be_issued(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			final String huIdentifier = DataTableUtil.extractStringForColumnName(row, I_PP_Order_Qty.COLUMNNAME_M_HU_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_HU hu = huTable.get(huIdentifier);

			final String bomLineIdentifier = DataTableUtil.extractStringForColumnName(row, I_PP_Order_Qty.COLUMNNAME_PP_Order_BOMLine_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_PP_Order_BOMLine bomLine = ppOrderBOMLineTable.get(bomLineIdentifier);

			final ZonedDateTime movementDate = DataTableUtil.extractZonedDateTimeOrNullForColumnName(row, "OPT." + I_PP_Order_Qty.COLUMNNAME_MovementDate);

			final List<I_PP_Order_Qty> ppOrderQties = CreateDraftIssuesCommand.builder()
					.targetOrderBOMLines(ImmutableList.of(bomLine))
					.movementDate(movementDate)
					.issueFromHUs(ImmutableList.of(hu))
					.changeHUStatusToIssued(true)
					.build()
					.execute();

			final String ppOrderQtyIdentifier = DataTableUtil.extractStringForColumnName(row, I_PP_Order_Qty.COLUMNNAME_PP_Order_Qty_ID + "." + TABLECOLUMN_IDENTIFIER);
			ppOrderQtyTable.putOrReplace(ppOrderQtyIdentifier, ppOrderQties.get(0));
		}
	}

	@And("validate PP_Order_Qty records")
	public void validate_pp_order_qty_records(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			final String ppOrderQtyIdentifier = DataTableUtil.extractStringForColumnName(row, I_PP_Order_Qty.COLUMNNAME_PP_Order_Qty_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_PP_Order_Qty ppOrderQty = ppOrderQtyTable.get(ppOrderQtyIdentifier);

			final String huIdentifier = DataTableUtil.extractStringForColumnName(row, I_PP_Order_Qty.COLUMNNAME_M_HU_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_HU hu = huTable.get(huIdentifier);

			final String productIdentifier = DataTableUtil.extractStringForColumnName(row, I_PP_Order_Qty.COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_Product product = productTable.get(productIdentifier);

			final String bomLineIdentifier = DataTableUtil.extractStringForColumnName(row, I_PP_Order_Qty.COLUMNNAME_PP_Order_BOMLine_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_PP_Order_BOMLine bomLine = ppOrderBOMLineTable.get(bomLineIdentifier);

			final boolean processed = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + I_PP_Order_Qty.COLUMNNAME_Processed, false);

			assertThat(ppOrderQty).isNotNull();
			assertThat(ppOrderQty.getM_HU_ID()).isEqualTo(hu.getM_HU_ID());
			assertThat(ppOrderQty.getM_Product_ID()).isEqualTo(product.getM_Product_ID());
			assertThat(ppOrderQty.getPP_Order_BOMLine_ID()).isEqualTo(bomLine.getPP_Order_BOMLine_ID());
			assertThat(ppOrderQty.isProcessed()).isEqualTo(processed);
		}
	}
}