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

package de.metas.cucumber.stepdefs.edi;

import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.StringUtils;
import de.metas.cucumber.stepdefs.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.esb.edi.model.I_EDI_Desadv;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.*;

public class EDI_Desadv_StepDef
{
	private final C_Order_StepDefData orderTable;
	private final Edi_Desadv_StepDefData ediDesadvTable;

	public EDI_Desadv_StepDef(
			@NonNull final C_Order_StepDefData orderTable,
			@NonNull final Edi_Desadv_StepDefData ediDesadvTable)
	{
		this.orderTable = orderTable;
		this.ediDesadvTable = ediDesadvTable;
	}

	@Then("validate created edi desadv")
	public void validate_edi_desadv(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			validateEdiDesadv(tableRow);
		}
	}

	private void validateEdiDesadv(@NonNull final Map<String, String> tableRow)
	{
		final String orderIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_Order.COLUMNNAME_C_Order_ID + "." + TABLECOLUMN_IDENTIFIER);

		final de.metas.edi.model.I_C_Order orderRecord = InterfaceWrapperHelper.create(
				CoalesceUtil.coalesceSuppliers(
						() -> orderTable.get(orderIdentifier),
						() -> InterfaceWrapperHelper.load(StringUtils.toIntegerOrZero(orderIdentifier), I_C_Order.class)), de.metas.edi.model.I_C_Order.class);

		final I_EDI_Desadv ediDesadvRecord = InterfaceWrapperHelper.load(orderRecord.getEDI_Desadv_ID(), I_EDI_Desadv.class);

		final BigDecimal sumDeliveredInStockingUOM = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_EDI_Desadv.COLUMNNAME_SumDeliveredInStockingUOM);
		final BigDecimal sumOrderedInStockingUOM = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_EDI_Desadv.COLUMNNAME_SumOrderedInStockingUOM);

		assertThat(ediDesadvRecord.getSumDeliveredInStockingUOM()).isEqualByComparingTo(sumDeliveredInStockingUOM);
		assertThat(ediDesadvRecord.getSumOrderedInStockingUOM()).isEqualByComparingTo(sumOrderedInStockingUOM);

		final String recordIdentifier = DataTableUtil.extractRecordIdentifier(tableRow, "EDI_Desadv");
		ediDesadvTable.put(recordIdentifier, ediDesadvRecord);
	}
}
