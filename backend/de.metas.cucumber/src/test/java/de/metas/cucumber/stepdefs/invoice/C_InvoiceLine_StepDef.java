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

import com.google.common.collect.ImmutableList;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.common.util.Check;
import de.metas.cucumber.stepdefs.C_Tax_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_M_Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_C_Tax_ID;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_M_Product.COLUMNNAME_M_Product_ID;

public class C_InvoiceLine_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	final C_Invoice_StepDefData invoiceTable;
	final M_Product_StepDefData productTable;
	final C_Tax_StepDefData taxTable;

	public C_InvoiceLine_StepDef(
			@NonNull final C_Invoice_StepDefData invoiceTable,
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final C_Tax_StepDefData taxTable)
	{
		this.invoiceTable = invoiceTable;
		this.productTable = productTable;
		this.taxTable = taxTable;
	}

	@And("validate created invoice lines")
	public void validate_created_invoice_lines(@NonNull final DataTable table)
	{
		final List<Map<String, String>> dataTable = table.asMaps();
		for (final Map<String, String> row : dataTable)
		{
			final String invoiceIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Invoice.COLUMNNAME_C_Invoice_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

			final I_C_Invoice invoiceRecord = invoiceTable.get(invoiceIdentifier);

			final String productIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_Product product = productTable.get(productIdentifier);

			final BigDecimal qtyinvoiced = DataTableUtil.extractBigDecimalForColumnName(row, "qtyinvoiced");

			//dev-note: we assume the tests are not using the same product and qty on different lines
			final I_C_InvoiceLine invoiceLineRecord = queryBL.createQueryBuilder(I_C_InvoiceLine.class)
					.addEqualsFilter(I_C_InvoiceLine.COLUMNNAME_C_Invoice_ID, invoiceRecord.getC_Invoice_ID())
					.addEqualsFilter(I_C_InvoiceLine.COLUMNNAME_M_Product_ID, product.getM_Product_ID())
					.addEqualsFilter(I_C_InvoiceLine.COLUMNNAME_QtyInvoiced, qtyinvoiced)
					.create()
					.firstOnlyNotNull(I_C_InvoiceLine.class);

			validateInvoiceLine(invoiceLineRecord, row);
		}
	}

	@And("^validate invoice lines for (.*):$")
	public void validate_invoice_lines(@NonNull final String invoiceIdentifier, @NonNull final DataTable table)
	{
		final I_C_Invoice invoiceRecord = invoiceTable.get(invoiceIdentifier);

		final List<I_C_InvoiceLine> invoiceLines = queryBL.createQueryBuilder(I_C_InvoiceLine.class)
				.addEqualsFilter(I_C_InvoiceLine.COLUMNNAME_C_Invoice_ID, invoiceRecord.getC_Invoice_ID())
				.create()
				.list(I_C_InvoiceLine.class);

		final List<Map<String, String>> dataTable = table.asMaps();

		assertThat(invoiceLines.size()).isEqualTo(dataTable.size());

		for (final Map<String, String> row : dataTable)
		{

			final String productIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_Product product = productTable.get(productIdentifier);

			final BigDecimal qtyinvoiced = DataTableUtil.extractBigDecimalForColumnName(row, "qtyinvoiced");

			final I_C_InvoiceLine currentInvoiceLine = Check.singleElement(invoiceLines.stream()
																				   .filter(line -> line.getM_Product_ID() == product.getM_Product_ID())
																				   .filter(line -> line.getQtyInvoiced().equals(qtyinvoiced))
																				   .collect(ImmutableList.toImmutableList()));

			validateInvoiceLine(currentInvoiceLine, row);
		}
	}

	private void validateInvoiceLine(@NonNull final I_C_InvoiceLine invoiceLine, @NonNull final Map<String, String> row)
	{
		final String productIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_Product.COLUMNNAME_M_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final int expectedProductId = productTable.getOptional(productIdentifier)
				.map(I_M_Product::getM_Product_ID)
				.orElseGet(() -> Integer.parseInt(productIdentifier));

		final BigDecimal qtyinvoiced = DataTableUtil.extractBigDecimalForColumnName(row, "qtyinvoiced");
		final boolean processed = DataTableUtil.extractBooleanForColumnName(row, "processed");

		assertThat(invoiceLine.getM_Product_ID()).isEqualTo(expectedProductId);
		assertThat(invoiceLine.getQtyInvoiced()).isEqualTo(qtyinvoiced);
		assertThat(invoiceLine.isProcessed()).isEqualTo(processed);

		final BigDecimal priceEntered = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_InvoiceLine.COLUMNNAME_PriceEntered);

		if (priceEntered != null)
		{
			assertThat(invoiceLine.getPriceEntered()).isEqualTo(priceEntered);
		}

		final BigDecimal priceActual = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_InvoiceLine.COLUMNNAME_PriceActual);

		if (priceActual != null)
		{
			assertThat(invoiceLine.getPriceActual()).isEqualTo(priceActual);
		}

		final BigDecimal lineNetAmt = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_InvoiceLine.COLUMNNAME_LineNetAmt);

		if (lineNetAmt != null)
		{
			assertThat(invoiceLine.getLineNetAmt()).isEqualTo(lineNetAmt);
		}

		final BigDecimal discount = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_InvoiceLine.COLUMNNAME_Discount);

		if (discount != null)
		{
			assertThat(invoiceLine.getDiscount()).isEqualTo(discount);
		}

		final String taxIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_C_Tax_ID + "." + TABLECOLUMN_IDENTIFIER);

		if (taxIdentifier != null)
		{
			final I_C_Tax taxRecord = taxTable.get(taxIdentifier);
			assertThat(invoiceLine.getC_Tax_ID()).isEqualTo(taxRecord.getC_Tax_ID());
		}
	}
}
