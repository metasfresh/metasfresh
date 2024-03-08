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

package de.metas.cucumber.stepdefs;

import de.metas.document.engine.IDocumentBL;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Order;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.compiere.model.I_C_Order.COLUMNNAME_C_Order_ID;

public class OrderDocAction_StepDef
{
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);

	private final C_Order_StepDefData orderTable;

	public OrderDocAction_StepDef(@NonNull final C_Order_StepDefData orderTable)
	{
		this.orderTable = orderTable;
	}

	@And("perform document action")
	public void perform_doc_action(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String docAction = DataTableUtil.extractStringForColumnName(tableRow, "DocAction");

			final String orderIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow,  COLUMNNAME_C_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(orderIdentifier))
			{
				final I_C_Order order = orderTable.get(orderIdentifier);

				documentBL.processEx(InterfaceWrapperHelper.load(order.getC_Order_ID(), I_C_Order.class), docAction);
			}
		}
	}
}
