/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2023 metas GmbH
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
<<<<<<< HEAD
import de.metas.invoice.service.IInvoiceLineBL;
=======
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.activity.C_Activity_StepDefData;
import de.metas.cucumber.stepdefs.pricing.C_TaxCategory_StepDefData;
import de.metas.cucumber.stepdefs.project.C_Project_StepDefData;
import de.metas.cucumber.stepdefs.shipment.M_InOutLine_StepDefData;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceLineBL;
import de.metas.money.MoneyService;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.tax.api.ITaxDAO;
import de.metas.tax.api.Tax;
import de.metas.tax.api.TaxId;
>>>>>>> ce978dd873 (improve readability of cucumber tests)
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
<<<<<<< HEAD
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
=======
import org.assertj.core.api.SoftAssertions;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Activity;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Project;
>>>>>>> ce978dd873 (improve readability of cucumber tests)

import java.math.BigDecimal;
import java.util.List;

import static de.metas.adempiere.model.I_C_InvoiceLine.COLUMNNAME_Price_UOM_ID;
import static de.metas.adempiere.model.I_C_InvoiceLine.COLUMNNAME_QtyInvoicedInPriceUOM;
import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_C_Tax_ID;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_Processed;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_QtyInvoiced;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_C_Invoice.COLUMNNAME_C_Invoice_ID;
<<<<<<< HEAD
=======
import static org.compiere.model.I_C_Invoice.COLUMNNAME_Processed;
import static org.compiere.model.I_C_InvoiceLine.COLUMNNAME_C_InvoiceLine_ID;
import static org.compiere.model.I_C_InvoiceLine.COLUMNNAME_C_UOM_ID;
import static org.compiere.model.I_C_InvoiceLine.COLUMNNAME_Line;
>>>>>>> ce978dd873 (improve readability of cucumber tests)
import static org.compiere.model.I_M_Product.COLUMNNAME_M_Product_ID;

public class C_InvoiceLine_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IInvoiceLineBL invoiceLineBL = Services.get(IInvoiceLineBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
<<<<<<< HEAD
=======
	private final ITaxDAO taxDAO = Services.get(ITaxDAO.class);
	private final MoneyService moneyService = SpringContextHolder.instance.getBean(MoneyService.class);
>>>>>>> ce978dd873 (improve readability of cucumber tests)

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
<<<<<<< HEAD
		final List<Map<String, String>> dataTable = table.asMaps();
		for (final Map<String, String> row : dataTable)
		{
			final String invoiceIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Invoice.COLUMNNAME_C_Invoice_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
=======
		DataTableRows.of(table)
				.setAdditionalRowIdentifierColumnName(I_C_InvoiceLine.COLUMNNAME_C_InvoiceLine_ID)
				.forEach(row -> {
					final I_C_Invoice invoice = row.getAsIdentifier(COLUMNNAME_C_Invoice_ID).lookupNotNullIn(invoiceTable);
					final InvoiceId invoiceId = InvoiceId.ofRepoId(invoice.getC_Invoice_ID());
					final ProductId expectedProductId = getProductId(row);
					final BigDecimal qtyInvoiced = row.getAsBigDecimal(I_C_InvoiceLine.COLUMNNAME_QtyInvoiced);
>>>>>>> ce978dd873 (improve readability of cucumber tests)

					//dev-note: we assume the tests are generally not using the same product and qty on different lines...
					final IQueryBuilder<I_C_InvoiceLine> queryBuilder = queryBL.createQueryBuilder(I_C_InvoiceLine.class)
							.addEqualsFilter(I_C_InvoiceLine.COLUMNNAME_C_Invoice_ID, invoiceId)
							.addEqualsFilter(I_C_InvoiceLine.COLUMNNAME_M_Product_ID, expectedProductId)
							.addEqualsFilter(I_C_InvoiceLine.COLUMNNAME_QtyInvoiced, qtyInvoiced);

<<<<<<< HEAD
			final String productIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
			final int expectedProductId = productTable.getOptional(productIdentifier)
					.map(I_M_Product::getM_Product_ID)
					.orElseGet(() -> Integer.parseInt(productIdentifier));

			final BigDecimal qtyinvoiced = DataTableUtil.extractBigDecimalForColumnName(row, COLUMNNAME_QtyInvoiced);

			//dev-note: we assume the tests are not using the same product and qty on different lines
			final I_C_InvoiceLine invoiceLineRecord = queryBL.createQueryBuilder(I_C_InvoiceLine.class)
					.addEqualsFilter(I_C_InvoiceLine.COLUMNNAME_C_Invoice_ID, invoiceRecord.getC_Invoice_ID())
					.addEqualsFilter(I_C_InvoiceLine.COLUMNNAME_M_Product_ID, expectedProductId)
					.addEqualsFilter(I_C_InvoiceLine.COLUMNNAME_QtyInvoiced, qtyinvoiced)
					.create()
					.firstOnlyNotNull(I_C_InvoiceLine.class);

			validateInvoiceLine(invoiceLineRecord, row);
		}
=======
					// ...or if they do, they have different inoutlines
					row.getAsOptionalIdentifier(I_C_InvoiceLine.COLUMNNAME_M_InOutLine_ID)
							.map(inOutLineTable::getId)
							.ifPresent(inOutLineId -> queryBuilder.addEqualsFilter(I_C_InvoiceLine.COLUMNNAME_M_InOutLine_ID, inOutLineId.getRepoId()));

					final I_C_InvoiceLine invoiceLineRecord = queryBuilder.create().firstOnlyNotNull(I_C_InvoiceLine.class);

					validateInvoiceLine(invoiceLineRecord, invoice, row);
				});
>>>>>>> ce978dd873 (improve readability of cucumber tests)
	}

	@And("^validate invoice lines for (.*):$")
	public void validate_invoice_lines(@NonNull final String invoiceIdentifier, @NonNull final DataTable table)
	{
		final I_C_Invoice invoiceRecord = invoiceTable.get(invoiceIdentifier);

		final List<I_C_InvoiceLine> invoiceLines = queryBL.createQueryBuilder(I_C_InvoiceLine.class)
				.addEqualsFilter(I_C_InvoiceLine.COLUMNNAME_C_Invoice_ID, invoiceRecord.getC_Invoice_ID())
				.orderBy(COLUMNNAME_Line)
				.orderBy(COLUMNNAME_C_InvoiceLine_ID)
				.create()
				.list(I_C_InvoiceLine.class);

<<<<<<< HEAD
		final List<Map<String, String>> dataTable = table.asMaps();

		assertThat(invoiceLines.size()).isEqualTo(dataTable.size());
=======
		final StringBuilder invoiceLinesErrorMessage = new StringBuilder("Found the following invoice lines: \n");
		invoiceLines.forEach(invoiceLine -> {
			invoiceLinesErrorMessage.append("C_InvoiceLine.QtyInvoiced: ").append(invoiceLine.getQtyInvoiced()).append("; ");
			invoiceLinesErrorMessage.append("C_InvoiceLine.Processed: ").append(invoiceLine.isProcessed()).append("; ");
			invoiceLinesErrorMessage.append("C_InvoiceLine.PriceEntered: ").append(invoiceLine.getPriceEntered()).append("; ");
			invoiceLinesErrorMessage.append("C_InvoiceLine.PriceActual: ").append(invoiceLine.getPriceActual()).append("; ");
			invoiceLinesErrorMessage.append("C_InvoiceLine.LineNetAmt: ").append(invoiceLine.getLineNetAmt()).append("; ");
			invoiceLinesErrorMessage.append("C_InvoiceLine.Discount: ").append(invoiceLine.getDiscount()).append("; ");
			invoiceLinesErrorMessage.append("\n");
		});

		final DataTableRows rows = DataTableRows.of(table)
				.setAdditionalRowIdentifierColumnName(I_C_InvoiceLine.COLUMNNAME_C_InvoiceLine_ID);
>>>>>>> ce978dd873 (improve readability of cucumber tests)

		assertThat(invoiceLines.size()).as(invoiceLinesErrorMessage.toString()).isEqualTo(rows.size());

<<<<<<< HEAD
			final String productIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
			final Integer productID = productTable.getOptional(productIdentifier)
					.map(I_M_Product::getM_Product_ID)
					.orElseGet(() -> Integer.parseInt(productIdentifier));
			assertThat(productID).isNotNull();

			final BigDecimal qtyinvoiced = DataTableUtil.extractBigDecimalForColumnName(row, COLUMNNAME_QtyInvoiced);

			final I_C_InvoiceLine currentInvoiceLine = Check.singleElement(invoiceLines.stream()
																				   .filter(line -> line.getM_Product_ID() == productID)
																				   .filter(line -> line.getQtyInvoiced().equals(qtyinvoiced))
																				   .collect(ImmutableList.toImmutableList()));
=======
		rows.forEach(row -> {
			final ProductId productId = getProductId(row);

			final BigDecimal qtyInvoiced = row.getAsBigDecimal(I_C_InvoiceLine.COLUMNNAME_QtyInvoiced);

			final I_C_InvoiceLine currentInvoiceLine = Check.singleElement(invoiceLines.stream()
					.filter(line -> line.getM_Product_ID() == productId.getRepoId())
					.filter(line -> line.getQtyInvoiced().equals(qtyInvoiced))
					.collect(ImmutableList.toImmutableList()));
>>>>>>> ce978dd873 (improve readability of cucumber tests)

			validateInvoiceLine(currentInvoiceLine, invoiceRecord, row);
		});
	}

	private ProductId getProductId(final DataTableRow row)
	{
		final StepDefDataIdentifier productIdentifier = row.getAsIdentifier(I_C_InvoiceLine.COLUMNNAME_M_Product_ID);
		return productTable.getIdOptional(productIdentifier)
				.orElseGet(() -> productIdentifier.getAsId(ProductId.class));
	}

	private void validateInvoiceLine(
			@NonNull final I_C_InvoiceLine invoiceLine,
			@NonNull final I_C_Invoice invoice,
			@NonNull final DataTableRow row)
	{
<<<<<<< HEAD
		final String productIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_Product.COLUMNNAME_M_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final int expectedProductId = productTable.getOptional(productIdentifier)
				.map(I_M_Product::getM_Product_ID)
				.orElseGet(() -> Integer.parseInt(productIdentifier));

		final BigDecimal qtyinvoiced = DataTableUtil.extractBigDecimalForColumnName(row, COLUMNNAME_QtyInvoiced);
		final boolean processed = DataTableUtil.extractBooleanForColumnName(row, COLUMNNAME_Processed);
=======
		final SoftAssertions softly = new SoftAssertions();
>>>>>>> ce978dd873 (improve readability of cucumber tests)

		final BigDecimal qtyEntered = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_InvoiceLine.COLUMNNAME_QtyEntered);
		if (qtyEntered != null)
		{
			assertThat(invoiceLine.getQtyEntered()).isEqualByComparingTo(qtyEntered);
		}

		final BigDecimal qtyEnteredInBPartnerUOM = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_InvoiceLine.COLUMNNAME_QtyEnteredInBPartnerUOM);
		if (qtyEnteredInBPartnerUOM != null)
		{
			assertThat(invoiceLine.getQtyEnteredInBPartnerUOM()).isEqualTo(qtyEnteredInBPartnerUOM);
		}

		final String bPartnerUOMx12de355Code = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_InvoiceLine.COLUMNNAME_C_UOM_BPartner_ID + "." + X12DE355.class.getSimpleName());
		if (Check.isNotBlank(bPartnerUOMx12de355Code))
		{
			final UomId bPartnerUOMId = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(bPartnerUOMx12de355Code));
			assertThat(invoiceLine.getC_UOM_BPartner_ID()).isEqualTo(bPartnerUOMId.getRepoId());
		}

		final String uomX12de355Code = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_InvoiceLine.COLUMNNAME_C_UOM_ID + "." + X12DE355.class.getSimpleName());
		if (Check.isNotBlank(uomX12de355Code))
		{
			final UomId uomId = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(uomX12de355Code));
			assertThat(invoiceLine.getC_UOM_ID()).isEqualTo(uomId.getRepoId());
		}

<<<<<<< HEAD
		assertThat(invoiceLine.getM_Product_ID()).isEqualTo(expectedProductId);
		assertThat(invoiceLine.getQtyInvoiced()).isEqualTo(qtyinvoiced);
		assertThat(invoiceLine.isProcessed()).isEqualTo(processed);
=======
		final ProductId expectedProductId = getProductId(row);
		softly.assertThat(invoiceLine.getM_Product_ID()).as(COLUMNNAME_M_Product_ID).isEqualTo(expectedProductId.getRepoId());
>>>>>>> ce978dd873 (improve readability of cucumber tests)

		row.getAsOptionalBigDecimal(I_C_InvoiceLine.COLUMNNAME_QtyInvoiced)
				.ifPresent(qtyInvoiced -> {
					softly.assertThat(invoiceLine.getQtyInvoiced()).as(COLUMNNAME_QtyInvoiced).isEqualTo(qtyInvoiced);
				});

<<<<<<< HEAD
		if (priceEntered != null)
		{
			assertThat(invoiceLine.getPriceEntered()).isEqualTo(priceEntered);
		}
=======
		row.getAsOptionalBoolean(I_C_InvoiceLine.COLUMNNAME_Processed)
				.ifPresent(processed -> {
					softly.assertThat(invoiceLine.isProcessed()).as(COLUMNNAME_Processed).isEqualTo(processed);
				});
>>>>>>> ce978dd873 (improve readability of cucumber tests)

		row.getAsOptionalMoney(I_C_InvoiceLine.COLUMNNAME_PriceEntered, moneyService::getCurrencyIdByCurrencyCode)
				.ifPresent(priceEntered -> {
					softly.assertThat(invoice.getC_Currency_ID()).as(I_C_Invoice.COLUMNNAME_C_Currency_ID).isEqualTo(priceEntered.getCurrencyId().getRepoId());
					softly.assertThat(invoiceLine.getPriceEntered()).as(COLUMNNAME_PriceEntered).isEqualByComparingTo(priceEntered.toBigDecimal());
				});

<<<<<<< HEAD
		if (priceActual != null)
		{
			assertThat(invoiceLine.getPriceActual()).isEqualTo(priceActual);
		}

		final BigDecimal lineNetAmt = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_InvoiceLine.COLUMNNAME_LineNetAmt);

		if (lineNetAmt != null)
		{
			assertThat(invoiceLine.getLineNetAmt()).isEqualTo(lineNetAmt);
		}
=======
		row.getAsOptionalMoney(I_C_InvoiceLine.COLUMNNAME_PriceActual, moneyService::getCurrencyIdByCurrencyCode)
				.ifPresent(priceActual -> {
					softly.assertThat(invoice.getC_Currency_ID()).as(I_C_Invoice.COLUMNNAME_C_Currency_ID).isEqualTo(priceActual.getCurrencyId().getRepoId());
					softly.assertThat(invoiceLine.getPriceActual()).as(COLUMNNAME_PriceActual).isEqualByComparingTo(priceActual.toBigDecimal());
				});

		row.getAsOptionalMoney(I_C_InvoiceLine.COLUMNNAME_LineNetAmt, moneyService::getCurrencyIdByCurrencyCode)
				.ifPresent(lineNetAmt -> {
					softly.assertThat(invoice.getC_Currency_ID()).as(I_C_Invoice.COLUMNNAME_C_Currency_ID).isEqualTo(lineNetAmt.getCurrencyId().getRepoId());
					softly.assertThat(invoiceLine.getLineNetAmt()).as(COLUMNNAME_LineNetAmt).isEqualByComparingTo(lineNetAmt.toBigDecimal());
				});
>>>>>>> ce978dd873 (improve readability of cucumber tests)

		final BigDecimal discount = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_InvoiceLine.COLUMNNAME_Discount);
		if (discount != null)
		{
			assertThat(invoiceLine.getDiscount()).isEqualTo(discount);
		}

<<<<<<< HEAD
		final String taxIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_C_Tax_ID + "." + TABLECOLUMN_IDENTIFIER);

		if (taxIdentifier != null)
		{
			final I_C_Tax taxRecord = taxTable.get(taxIdentifier);
			assertThat(invoiceLine.getC_Tax_ID()).isEqualTo(taxRecord.getC_Tax_ID());
=======
		row.getAsOptionalIdentifier(I_C_InvoiceLine.COLUMNNAME_C_Tax_ID)
				.map(taxTable::getId)
				.ifPresent(taxId -> softly.assertThat(invoiceLine.getC_Tax_ID()).isEqualTo(taxId.getRepoId()));

		row.getAsOptionalIdentifier(I_C_InvoiceLine.COLUMNNAME_C_TaxCategory_ID)
				.map(taxCategoryTable::getId)
				.ifPresent(taxCategoryId -> {
					softly.assertThat(invoiceLine.getC_TaxCategory_ID()).as("C_TaxCategory_ID").isEqualTo(taxCategoryId.getRepoId());
				});

		row.getAsOptionalInt(COLUMNNAME_Line)
				.ifPresent(line -> softly.assertThat(invoiceLine.getLine()).as(COLUMNNAME_Line).isEqualTo(line));

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
>>>>>>> ce978dd873 (improve readability of cucumber tests)
		}

		final String priceUOMCode = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_Price_UOM_ID + "." + X12DE355.class.getSimpleName());
		if (Check.isNotBlank(priceUOMCode))
		{
			final UomId priceUOMId = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(priceUOMCode));

<<<<<<< HEAD
			assertThat(invoiceLine.getPrice_UOM_ID()).as(COLUMNNAME_Price_UOM_ID).isEqualTo(priceUOMId.getRepoId());
=======
			softly.assertThat(invoiceLine.getPrice_UOM_ID()).as(COLUMNNAME_Price_UOM_ID).isEqualTo(priceUOMId.getRepoId());
		}

		final Boolean isManualPrice = row.getAsOptionalBoolean(COLUMNNAME_IsManualPrice).toBooleanOrNull();
		if (isManualPrice != null)
		{
			softly.assertThat(invoiceLine.isManualPrice()).as(COLUMNNAME_IsManualPrice).isEqualTo(isManualPrice);
>>>>>>> ce978dd873 (improve readability of cucumber tests)
		}

		final BigDecimal qtyInvoicedInPriceUOM = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_QtyInvoicedInPriceUOM);
		if (qtyInvoicedInPriceUOM != null)
		{
			assertThat(invoiceLine.getQtyInvoicedInPriceUOM()).as(COLUMNNAME_QtyInvoicedInPriceUOM).isEqualByComparingTo(qtyInvoicedInPriceUOM);
		}
<<<<<<< HEAD
		
		final String invoiceLineIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_InvoiceLine.COLUMNNAME_C_InvoiceLine_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		invoiceLineTable.putOrReplace(invoiceLineIdentifier, invoiceLine);
=======

		final String projectIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Invoice.COLUMNNAME_C_Project_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		if (Check.isNotBlank(projectIdentifier))
		{
			final Integer projectId = projectTable.getOptional(projectIdentifier)
					.map(I_C_Project::getC_Project_ID)
					.orElseGet(() -> Integer.parseInt(projectIdentifier));

			softly.assertThat(invoiceLine.getC_Project_ID()).isEqualTo(projectId);
		}

		final String costCenterIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_InvoiceLine.COLUMNNAME_C_Activity_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(costCenterIdentifier))
		{
			final I_C_Activity activity = activityTable.get(costCenterIdentifier);
			softly.assertThat(invoiceLine.getC_Activity_ID()).isEqualTo(activity.getC_Activity_ID());
		}

		final String description = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_InvoiceLine.COLUMNNAME_Description);
		if (Check.isNotBlank(description))
		{
			softly.assertThat(invoiceLine.getDescription()).isEqualTo(description);
		}

		// final Boolean isHidePriceAndAmountOnPrint = DataTableUtil.extractBooleanForColumnNameOrNull(row, "OPT." + COLUMNNAME_IsHidePriceAndAmountOnPrint);
		// if (isHidePriceAndAmountOnPrint != null)
		// {
		// 	softly.assertThat(invoiceLine.isHidePriceAndAmountOnPrint()).as(COLUMNNAME_IsHidePriceAndAmountOnPrint).isEqualTo(isHidePriceAndAmountOnPrint);
		// }

		row.getAsOptionalIdentifier().ifPresent(identifier -> invoiceLineTable.putOrReplace(identifier, invoiceLine));

		softly.assertAll();
>>>>>>> ce978dd873 (improve readability of cucumber tests)
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
