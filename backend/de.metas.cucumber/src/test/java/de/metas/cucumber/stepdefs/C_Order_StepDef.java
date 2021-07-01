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

import de.metas.common.util.time.SystemTime;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.Data;
import lombok.NonNull;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_Product;

import java.util.List;
import java.util.Map;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.compiere.model.I_C_Order.COLUMNNAME_C_BPartner_ID;

public class C_Order_StepDef
{
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final StepDefData<I_C_BPartner> bpartnerTable;
	private final StepDefData<I_C_Order> orderTable;

	public C_Order_StepDef(
			@NonNull final StepDefData<I_C_BPartner> bpartnerTable,
			@NonNull final StepDefData<I_C_Order> orderTable)
	{
		this.bpartnerTable = bpartnerTable;
		this.orderTable = orderTable;
	}

	@Given("metasfresh contains C_Orders:")
	public void metasfresh_contains_c_invoice_candidates(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String bpartnerIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_C_BPartner_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_C_BPartner bpartner = bpartnerTable.get(bpartnerIdentifier);

			final I_C_Order order = newInstance(I_C_Order.class);
			order.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
			order.setC_BPartner_ID(bpartner.getC_BPartner_ID());

			order.setIsSOTrx(DataTableUtil.extractBooleanForColumnName(tableRow, I_C_Order.COLUMNNAME_IsSOTrx));
			order.setDateOrdered(DataTableUtil.extractDateTimestampForColumnName(tableRow, I_C_Order.COLUMNNAME_DateOrdered));

			saveRecord(order);

			orderTable.put(DataTableUtil.extractRecordIdentifier(tableRow, I_C_Order.COLUMNNAME_C_Order_ID), order);
		}
	}

	@Given("^the order identified by (.*) is completed$")
	public void order_is_completed(@NonNull final String orderIdentifier)
	{
		final I_C_Order order = orderTable.get(orderIdentifier);
		order.setDocAction(IDocument.ACTION_Complete); // we need this because otherwise MOrder.completeIt() won't complete it
		documentBL.processEx(order, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
	}
}
