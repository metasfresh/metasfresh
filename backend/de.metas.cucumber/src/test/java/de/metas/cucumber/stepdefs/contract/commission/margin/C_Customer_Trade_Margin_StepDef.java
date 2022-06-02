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

package de.metas.cucumber.stepdefs.contract.commission.margin;

import de.metas.contracts.commission.model.I_C_Customer_Trade_Margin;
import de.metas.contracts.commission.model.I_C_Customer_Trade_Margin_Line;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Product;

import java.util.List;
import java.util.Map;

import static de.metas.contracts.commission.model.I_C_Customer_Trade_Margin.COLUMNNAME_Commission_Product_ID;
import static de.metas.contracts.commission.model.I_C_Customer_Trade_Margin.COLUMNNAME_Name;
import static de.metas.contracts.commission.model.I_C_Customer_Trade_Margin.COLUMNNAME_PointsPrecision;
import static de.metas.contracts.commission.model.I_C_Customer_Trade_Margin_Line.COLUMNNAME_Margin;
import static de.metas.contracts.commission.model.I_C_Customer_Trade_Margin_Line.COLUMNNAME_SeqNo;
import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.*;

public class C_Customer_Trade_Margin_StepDef
{
	private final M_Product_StepDefData productTable;
	private final C_Customer_Trade_Margin_StepDefData tradeMarginTable;
	private final C_Customer_Trade_Margin_Line_StepDefData tradeMarginLineTable;

	public C_Customer_Trade_Margin_StepDef(
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final C_Customer_Trade_Margin_StepDefData tradeMarginTable,
			@NonNull final C_Customer_Trade_Margin_Line_StepDefData tradeMarginLineTable)
	{
		this.productTable = productTable;
		this.tradeMarginTable = tradeMarginTable;
		this.tradeMarginLineTable = tradeMarginLineTable;
	}

	@And("metasfresh contains C_Customer_Trade_Margin:")
	public void metasfresh_contains_I_C_Customer_Trade_Margin(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final I_C_Customer_Trade_Margin tradeMargin = InterfaceWrapperHelper.newInstance(I_C_Customer_Trade_Margin.class);

			final String name = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_Name);
			assertThat(name).as(COLUMNNAME_Name + " is mandatory").isNotBlank();
			tradeMargin.setName(name);

			final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_Commission_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
			assertThat(productIdentifier).as(COLUMNNAME_Commission_Product_ID + "." + TABLECOLUMN_IDENTIFIER + " is mandatory").isNotBlank();
			final I_M_Product product = productTable.get(productIdentifier);
			assertThat(product).as("Missing M_Product record for identifier " + productIdentifier).isNotNull();
			tradeMargin.setCommission_Product_ID(product.getM_Product_ID());

			final int pointsPrecision = DataTableUtil.extractIntForColumnName(tableRow, COLUMNNAME_PointsPrecision);
			tradeMargin.setPointsPrecision(pointsPrecision);

			tradeMargin.setIsActive(true);

			InterfaceWrapperHelper.saveRecord(tradeMargin);

			final String tradeMarginIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_Customer_Trade_Margin.COLUMNNAME_C_Customer_Trade_Margin_ID + "." + TABLECOLUMN_IDENTIFIER);

			tradeMarginTable.put(tradeMarginIdentifier, tradeMargin);
		}
	}

	@And("metasfresh contains C_Customer_Trade_Margin_Line:")
	public void metasfresh_contains_I_C_Customer_Trade_Margin_Line(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final I_C_Customer_Trade_Margin_Line customerTradeMarginLine = InterfaceWrapperHelper.newInstance(I_C_Customer_Trade_Margin_Line.class);

			final String tradeMarginIdentifier = tableRow.get(I_C_Customer_Trade_Margin_Line.COLUMNNAME_C_Customer_Trade_Margin_ID + "." + TABLECOLUMN_IDENTIFIER);
			assertThat(tradeMarginIdentifier).as(I_C_Customer_Trade_Margin_Line.COLUMNNAME_C_Customer_Trade_Margin_ID + "." + TABLECOLUMN_IDENTIFIER + " is mandatory").isNotBlank();
			final I_C_Customer_Trade_Margin tradeMargin = tradeMarginTable.get(tradeMarginIdentifier);
			assertThat(tradeMargin).as("Missing I_C_Customer_Trade_Margin record for identifier " + tradeMarginIdentifier).isNotNull();

			customerTradeMarginLine.setC_Customer_Trade_Margin_ID(tradeMargin.getC_Customer_Trade_Margin_ID());

			final int seqNo = DataTableUtil.extractIntForColumnName(tableRow, COLUMNNAME_SeqNo);
			customerTradeMarginLine.setSeqNo(seqNo);

			final int margin = DataTableUtil.extractIntForColumnName(tableRow, COLUMNNAME_Margin);
			customerTradeMarginLine.setMargin(margin);

			customerTradeMarginLine.setIsActive(true);

			InterfaceWrapperHelper.saveRecord(customerTradeMarginLine);

			final String tradeMarginLineIdentifier = tableRow.get(I_C_Customer_Trade_Margin_Line.COLUMNNAME_C_Customer_Trade_Margin_Line_ID + "." + TABLECOLUMN_IDENTIFIER);
			tradeMarginLineTable.put(tradeMarginLineIdentifier, customerTradeMarginLine);
		}
	}
}
