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
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_Project;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_TaxCategory;
import org.compiere.model.I_M_Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_C_InvoiceLine.COLUMNNAME_C_TaxCategory_ID;
import static org.compiere.model.I_C_InvoiceLine.COLUMNNAME_C_Tax_ID;
import static org.compiere.model.I_C_InvoiceLine.COLUMNNAME_M_Product_ID;

public class C_InvoiceLine_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	private final StepDefData<I_C_Invoice> invoiceTable;
	private final StepDefData<I_C_InvoiceLine> invoiceLineTable;
	private final StepDefData<I_M_Product> productTable;
	private final StepDefData<I_C_Project> projectTable;
	private final StepDefData<I_C_Tax> taxTable;
	private final StepDefData<I_C_TaxCategory> taxCategoryTable;

	public C_InvoiceLine_StepDef(
			@NonNull final StepDefData<I_C_Invoice> invoiceTable,
			@NonNull final StepDefData<I_C_InvoiceLine> invoiceLineTable,
			@NonNull final StepDefData<I_M_Product> productTable,
			@NonNull final StepDefData<I_C_Project> projectTable,
			@NonNull final StepDefData<I_C_Tax> taxTable,
			@NonNull final StepDefData<I_C_TaxCategory> taxCategoryTable)
	{
		this.invoiceTable = invoiceTable;
		this.invoiceLineTable = invoiceLineTable;
		this.productTable = productTable;
		this.projectTable = projectTable;
		this.taxTable = taxTable;
		this.taxCategoryTable = taxCategoryTable;
	}

	@And("validate created invoice lines")
	public void validate_created_invoice_lines(@NonNull final DataTable table)
	{
		final List<Map<String, String>> dataTable = table.asMaps();
		for (final Map<String, String> row : dataTable)
		{
			final String invoiceIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Invoice.COLUMNNAME_C_Invoice_ID + "." + TABLECOLUMN_IDENTIFIER);

			final I_C_Invoice invoiceRecord = invoiceTable.get(invoiceIdentifier);

			final String productIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
			final int expectedProductId = productTable.getOptional(productIdentifier)
					.map(I_M_Product::getM_Product_ID)
					.orElseGet(() -> Integer.parseInt(productIdentifier));

			final BigDecimal qtyinvoiced = DataTableUtil.extractBigDecimalForColumnName(row, "qtyinvoiced");

			//dev-note: we assume the tests are not using the same product and qty on different lines
			final I_C_InvoiceLine invoiceLineRecord = queryBL.createQueryBuilder(I_C_InvoiceLine.class)
					.addEqualsFilter(I_C_InvoiceLine.COLUMNNAME_C_Invoice_ID, invoiceRecord.getC_Invoice_ID())
					.addEqualsFilter(COLUMNNAME_M_Product_ID, expectedProductId)
					.addEqualsFilter(I_C_InvoiceLine.COLUMNNAME_QtyInvoiced, qtyinvoiced)
					.create()
					.firstOnlyNotNull(I_C_InvoiceLine.class);

			validateInvoiceLine(invoiceLineRecord, row);
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

		final BigDecimal qtyEntered = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_InvoiceLine.COLUMNNAME_QtyEntered);

		if (qtyEntered != null)
		{
			assertThat(invoiceLine.getQtyEntered()).isEqualByComparingTo(qtyEntered);
		}

		// final BigDecimal qtyEnteredInBPartnerUOM = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_InvoiceLine.COLUMNNAME_QtyEnteredInBPartnerUOM);
		//
		// if (qtyEnteredInBPartnerUOM != null)
		// {
		// 	assertThat(invoiceLine.getQtyEnteredInBPartnerUOM()).isEqualTo(qtyEnteredInBPartnerUOM);
		// }

		// final String bPartnerUOMx12de355Code = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_InvoiceLine.COLUMNNAME_C_UOM_BPartner_ID + "." + X12DE355.class.getSimpleName());
		//
		// if (Check.isNotBlank(bPartnerUOMx12de355Code))
		// {
		// 	final UomId bPartnerUOMId = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(bPartnerUOMx12de355Code));
		// 	assertThat(invoiceLine.getC_UOM_BPartner_ID()).isEqualTo(bPartnerUOMId.getRepoId());
		// }

		final String uomX12de355Code = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_InvoiceLine.COLUMNNAME_C_UOM_ID + "." + X12DE355.class.getSimpleName());

		if (Check.isNotBlank(uomX12de355Code))
		{
			final UomId uomId = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(uomX12de355Code));
			assertThat(invoiceLine.getC_UOM_ID()).isEqualTo(uomId.getRepoId());
		}

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

		// final BigDecimal discount = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_InvoiceLine.COLUMNNAME_Discount);
		//
		// if (discount != null)
		// {
		// 	assertThat(invoiceLine.getDiscount()).isEqualTo(discount);
		// }

		final String taxIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_C_Tax_ID + "." + TABLECOLUMN_IDENTIFIER);

		if (taxIdentifier != null)
		{
			final I_C_Tax taxRecord = taxTable.get(taxIdentifier);
			assertThat(invoiceLine.getC_Tax_ID()).isEqualTo(taxRecord.getC_Tax_ID());
		}

		final String taxCategoryIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_C_TaxCategory_ID + "." + TABLECOLUMN_IDENTIFIER);

		if (Check.isNotBlank(taxCategoryIdentifier))
		{
			final Integer taxCategoryId = taxCategoryTable.getOptional(taxCategoryIdentifier)
					.map(I_C_TaxCategory::getC_TaxCategory_ID)
					.orElseGet(() -> Integer.parseInt(taxCategoryIdentifier));

			assertThat(invoiceLine.getC_TaxCategory_ID()).as("C_TaxCategory_ID").isEqualTo(taxCategoryId);
		}

		final String invoiceLineIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_InvoiceLine.COLUMNNAME_C_InvoiceLine_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		invoiceLineTable.putOrReplace(invoiceLineIdentifier, invoiceLine);

		final String projectIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Invoice.COLUMNNAME_C_Project_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		if (Check.isNotBlank(projectIdentifier))
		{
			final Integer projectId = projectTable.getOptional(projectIdentifier)
					.map(I_C_Project::getC_Project_ID)
					.orElseGet(() -> Integer.parseInt(projectIdentifier));

			assertThat(invoiceLine.getC_Project_ID()).isEqualTo(projectId);
		}
	}
}
