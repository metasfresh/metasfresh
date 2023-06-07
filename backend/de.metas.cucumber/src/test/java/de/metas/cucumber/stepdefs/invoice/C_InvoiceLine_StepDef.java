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
import de.metas.invoice.service.IInvoiceLineBL;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static de.metas.adempiere.model.I_C_InvoiceLine.COLUMNNAME_IsManualPrice;
import static de.metas.adempiere.model.I_C_InvoiceLine.COLUMNNAME_Price_UOM_ID;
import static de.metas.adempiere.model.I_C_InvoiceLine.COLUMNNAME_QtyInvoicedInPriceUOM;
import static de.metas.adempiere.model.I_C_InvoiceLine.COLUMNNAME_TaxAmtInfo;
import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_C_Tax_ID;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_Discount;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_LineNetAmt;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_PriceActual;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_PriceEntered;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_QtyInvoiced;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_C_Invoice.COLUMNNAME_C_Invoice_ID;
import static org.compiere.model.I_C_Invoice.COLUMNNAME_Processed;
import static org.compiere.model.I_C_InvoiceLine.COLUMNNAME_C_UOM_ID;
import static org.compiere.model.I_C_InvoiceLine.COLUMNNAME_Line;
import static org.compiere.model.I_C_Order.COLUMNNAME_UserElementString2;
import static org.compiere.model.I_M_Product.COLUMNNAME_M_Product_ID;

public class C_InvoiceLine_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IInvoiceLineBL invoiceLineBL = Services.get(IInvoiceLineBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	final C_Invoice_StepDefData invoiceTable;
	final C_InvoiceLine_StepDefData invoiceLineTable;
	final M_Product_StepDefData productTable;
	final C_Tax_StepDefData taxTable;

	public C_InvoiceLine_StepDef(
			@NonNull final C_Invoice_StepDefData invoiceTable,
			@NonNull final C_InvoiceLine_StepDefData invoiceLineTable,
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final C_Tax_StepDefData taxTable)
	{
		this.invoiceTable = invoiceTable;
		this.invoiceLineTable = invoiceLineTable;
		this.productTable = productTable;
		this.taxTable = taxTable;
	}

	@And("metasfresh contains C_InvoiceLines")
	public void addC_InvoiceLines(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> dataTableRow : rows)
		{
			create_C_InvoiceLine(dataTableRow);
		}
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
			final Integer expectedProductId = productTable.getOptional(productIdentifier)
					.map(I_M_Product::getM_Product_ID)
					.orElseGet(() -> Integer.parseInt(productIdentifier));

			final BigDecimal qtyinvoiced = DataTableUtil.extractBigDecimalForColumnName(row, "qtyinvoiced");

			//dev-note: we assume the tests are not using the same product and qty on different lines
			final I_C_InvoiceLine invoiceLineRecord = queryBL.createQueryBuilder(I_C_InvoiceLine.class)
					.addEqualsFilter(I_C_InvoiceLine.COLUMNNAME_C_Invoice_ID, invoiceRecord.getC_Invoice_ID())
					.addEqualsFilter(I_C_InvoiceLine.COLUMNNAME_M_Product_ID, expectedProductId)
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
			final Integer productID = productTable.getOptional(productIdentifier)
					.map(I_M_Product::getM_Product_ID)
					.orElseGet(() -> Integer.parseInt(productIdentifier));
			assertThat(productID).isNotNull();

			final BigDecimal qtyinvoiced = DataTableUtil.extractBigDecimalForColumnName(row, "qtyinvoiced");

			final I_C_InvoiceLine currentInvoiceLine = Check.singleElement(invoiceLines.stream()
																				   .filter(line -> line.getM_Product_ID() == productID)
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

		final SoftAssertions softly = new SoftAssertions();

		softly.assertThat(invoiceLine.getM_Product_ID()).as(COLUMNNAME_M_Product_ID).isEqualTo(expectedProductId);
		softly.assertThat(invoiceLine.getQtyInvoiced()).as(COLUMNNAME_QtyInvoiced).isEqualTo(qtyinvoiced);
		softly.assertThat(invoiceLine.isProcessed()).as(COLUMNNAME_Processed).isEqualTo(processed);

		final BigDecimal priceEntered = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_InvoiceLine.COLUMNNAME_PriceEntered);

		if (priceEntered != null)
		{
			softly.assertThat(invoiceLine.getPriceEntered()).as(COLUMNNAME_PriceEntered).isEqualByComparingTo(priceEntered);
		}

		final BigDecimal priceActual = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_InvoiceLine.COLUMNNAME_PriceActual);

		if (priceActual != null)
		{
			softly.assertThat(invoiceLine.getPriceActual()).as(COLUMNNAME_PriceActual).isEqualByComparingTo(priceActual);
		}

		final BigDecimal lineNetAmt = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_InvoiceLine.COLUMNNAME_LineNetAmt);

		if (lineNetAmt != null)
		{
			softly.assertThat(invoiceLine.getLineNetAmt()).as(COLUMNNAME_LineNetAmt).isEqualByComparingTo(lineNetAmt);
		}

		final BigDecimal discount = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_InvoiceLine.COLUMNNAME_Discount);

		if (discount != null)
		{
			softly.assertThat(invoiceLine.getDiscount()).as(COLUMNNAME_Discount).isEqualByComparingTo(discount);
		}

		final String taxIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_C_Tax_ID + "." + TABLECOLUMN_IDENTIFIER);

		if (taxIdentifier != null)
		{
			final I_C_Tax taxRecord = taxTable.get(taxIdentifier);
			softly.assertThat(invoiceLine.getC_Tax_ID()).as(COLUMNNAME_C_Tax_ID).isEqualTo(taxRecord.getC_Tax_ID());
		}

		final Integer line = DataTableUtil.extractIntegerOrNullForColumnName(row, "OPT." + COLUMNNAME_Line);
		if (line != null)
		{
			softly.assertThat(invoiceLine.getLine()).as(COLUMNNAME_Line).isEqualTo(line);
		}

		final BigDecimal taxAmtInfo = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_TaxAmtInfo);

		if (taxAmtInfo != null)
		{
			softly.assertThat(invoiceLine.getTaxAmtInfo()).as(COLUMNNAME_TaxAmtInfo).isEqualByComparingTo(taxAmtInfo);
		}

		final String uomCode = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_C_UOM_ID + "." + X12DE355.class.getSimpleName());
		if (Check.isNotBlank(uomCode))
		{
			final UomId uomId = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(uomCode));

			softly.assertThat(invoiceLine.getC_UOM_ID()).as(COLUMNNAME_C_UOM_ID).isEqualTo(uomId.getRepoId());
		}

		final String priceUOMCode = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_Price_UOM_ID + "." + X12DE355.class.getSimpleName());
		if (Check.isNotBlank(priceUOMCode))
		{
			final UomId priceUOMId = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(priceUOMCode));

			softly.assertThat(invoiceLine.getPrice_UOM_ID()).as(COLUMNNAME_Price_UOM_ID).isEqualTo(priceUOMId.getRepoId());
		}

		final Boolean isManualPrice = DataTableUtil.extractBooleanForColumnNameOrNull(row, "OPT." + COLUMNNAME_IsManualPrice);
		if (isManualPrice != null)
		{
			softly.assertThat(invoiceLine.isManualPrice()).as(COLUMNNAME_IsManualPrice).isEqualTo(isManualPrice);
		}

		final BigDecimal qtyInvoicedInPriceUOM = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_QtyInvoicedInPriceUOM);
		if (qtyInvoicedInPriceUOM != null)
		{
			softly.assertThat(invoiceLine.getQtyInvoicedInPriceUOM()).as(COLUMNNAME_QtyInvoicedInPriceUOM).isEqualByComparingTo(qtyInvoicedInPriceUOM);
		}


		final String userElementString2 = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_UserElementString2);
		if (de.metas.util.Check.isNotBlank(userElementString2))
		{
			softly.assertThat(invoiceLine.getUserElementString2()).as("UserElementString2").isEqualTo(userElementString2);
		}

		softly.assertAll();

		final String invoiceLineIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_InvoiceLine.COLUMNNAME_C_InvoiceLine_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		invoiceLineTable.putOrReplace(invoiceLineIdentifier, invoiceLine);
	}

	private void create_C_InvoiceLine(@NonNull final Map<String, String> row)
	{
		final String productIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
		final Integer productId = productTable.getOptional(productIdentifier)
				.map(I_M_Product::getM_Product_ID)
				.orElseGet(() -> Integer.parseInt(productIdentifier));

		final BigDecimal qtyInvoiced = DataTableUtil.extractBigDecimalForColumnName(row, COLUMNNAME_QtyInvoiced);

		final String invoiceIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Invoice_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_Invoice invoice = invoiceTable.get(invoiceIdentifier);

		final String x12de355Code = DataTableUtil.extractStringForColumnName(row, I_C_UOM.COLUMNNAME_C_UOM_ID + "." + X12DE355.class.getSimpleName());
		final UomId productPriceUomId = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(x12de355Code));

		final I_C_InvoiceLine invoiceLine = InterfaceWrapperHelper.newInstance(I_C_InvoiceLine.class);

		invoiceLine.setM_Product_ID(productId);
		invoiceLine.setQtyInvoiced(qtyInvoiced);
		invoiceLine.setQtyEntered(qtyInvoiced);
		invoiceLine.setC_Invoice_ID(invoice.getC_Invoice_ID());
		invoiceLine.setPrice_UOM_ID(productPriceUomId.getRepoId());

		invoiceLineBL.updatePrices(invoiceLine);
		invoiceLineBL.updateLineNetAmt(invoiceLine, qtyInvoiced);

		InterfaceWrapperHelper.save(invoiceLine);

		final String invoiceLineIdentifier = DataTableUtil.extractStringForColumnName(row, StepDefConstants.TABLECOLUMN_IDENTIFIER);
		invoiceLineTable.putOrReplace(invoiceLineIdentifier, invoiceLine);
	}
}
