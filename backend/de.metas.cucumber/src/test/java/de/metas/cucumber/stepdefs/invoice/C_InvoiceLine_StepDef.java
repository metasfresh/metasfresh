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

package de.metas.cucumber.stepdefs.invoice;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class C_InvoiceLine_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	final C_Invoice_StepDefData invoiceTable;

	public C_InvoiceLine_StepDef(@NonNull final C_Invoice_StepDefData invoiceTable)
	{
		this.invoiceTable = invoiceTable;
	}

	@And("validate created invoice lines")
	public void validate_created_invoice_lines(@NonNull final DataTable table)
	{
		final List<Map<String, String>> dataTable = table.asMaps();
		for (final Map<String, String> row : dataTable)
		{
			final String invoiceIdentifier = DataTableUtil.extractStringForColumnName(row, "Invoice.Identifier");

			final I_C_Invoice invoiceRecord = invoiceTable.get(invoiceIdentifier);

			final int productId = DataTableUtil.extractIntForColumnName(row, "productIdentifier.m_product_id");
			final BigDecimal qtyinvoiced = DataTableUtil.extractBigDecimalForColumnName(row, "qtyinvoiced");

			//dev-note: we assume the tests are not using the same product and qty on different lines
			final I_C_InvoiceLine invoiceLineRecord = queryBL.createQueryBuilder(I_C_InvoiceLine.class)
					.addEqualsFilter(I_C_InvoiceLine.COLUMNNAME_C_Invoice_ID, invoiceRecord.getC_Invoice_ID())
					.addEqualsFilter(I_C_InvoiceLine.COLUMNNAME_M_Product_ID, productId)
					.addEqualsFilter(I_C_InvoiceLine.COLUMNNAME_QtyInvoiced, qtyinvoiced)
					.create()
					.firstOnlyNotNull(I_C_InvoiceLine.class);

			validateInvoiceLine(invoiceLineRecord, row);
		}
	}

	private void validateInvoiceLine(@NonNull final I_C_InvoiceLine invoiceLine, @NonNull final Map<String, String> row)
	{
		final int productId = DataTableUtil.extractIntForColumnName(row, "productIdentifier.m_product_id");
		final BigDecimal qtyinvoiced = DataTableUtil.extractBigDecimalForColumnName(row, "qtyinvoiced");
		final boolean processed = DataTableUtil.extractBooleanForColumnName(row, "processed");

		assertThat(invoiceLine.getM_Product_ID()).isEqualTo(productId);
		assertThat(invoiceLine.getQtyInvoiced()).isEqualTo(qtyinvoiced);
		assertThat(invoiceLine.isProcessed()).isEqualTo(processed);
	}
}
