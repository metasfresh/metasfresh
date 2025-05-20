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
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
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
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.SoftAssertions;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Activity;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Project;

import java.math.BigDecimal;
import java.util.List;

import static de.metas.adempiere.model.I_C_InvoiceLine.COLUMNNAME_IsManualPrice;
import static de.metas.adempiere.model.I_C_InvoiceLine.COLUMNNAME_Price_UOM_ID;
import static de.metas.adempiere.model.I_C_InvoiceLine.COLUMNNAME_QtyInvoicedInPriceUOM;
import static de.metas.adempiere.model.I_C_InvoiceLine.COLUMNNAME_TaxAmtInfo;
import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_Discount;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_LineNetAmt;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_PriceActual;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_PriceEntered;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_QtyInvoiced;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.compiere.model.I_C_Invoice.COLUMNNAME_C_Invoice_ID;
import static org.compiere.model.I_C_Invoice.COLUMNNAME_Processed;
import static org.compiere.model.I_C_InvoiceLine.COLUMNNAME_C_InvoiceLine_ID;
import static org.compiere.model.I_C_InvoiceLine.COLUMNNAME_C_UOM_ID;
import static org.compiere.model.I_C_InvoiceLine.COLUMNNAME_Line;
import static org.compiere.model.I_M_Product.COLUMNNAME_M_Product_ID;

@RequiredArgsConstructor
public class C_InvoiceLine_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IInvoiceLineBL invoiceLineBL = Services.get(IInvoiceLineBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final ITaxDAO taxDAO = Services.get(ITaxDAO.class);
	private final MoneyService moneyService = SpringContextHolder.instance.getBean(MoneyService.class);

	private final C_Invoice_StepDefData invoiceTable;
	private final C_InvoiceLine_StepDefData invoiceLineTable;
	private final M_InOutLine_StepDefData inOutLineTable;
	private final M_Product_StepDefData productTable;
	private final C_Project_StepDefData projectTable;
	private final C_Tax_StepDefData taxTable;
	private final C_TaxCategory_StepDefData taxCategoryTable;
	private final C_Activity_StepDefData activityTable;

	@And("metasfresh contains C_InvoiceLines")
	public void addC_InvoiceLines(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_C_InvoiceLine.COLUMNNAME_C_InvoiceLine_ID)
				.forEach(this::create_C_InvoiceLine);
	}

	@And("validate created invoice lines")
	public void validate_created_invoice_lines(@NonNull final DataTable table)
	{
		DataTableRows.of(table)
				.setAdditionalRowIdentifierColumnName(I_C_InvoiceLine.COLUMNNAME_C_InvoiceLine_ID)
				.forEach(row -> {
					final I_C_Invoice invoice = row.getAsIdentifier(COLUMNNAME_C_Invoice_ID).lookupNotNullIn(invoiceTable);
					final InvoiceId invoiceId = InvoiceId.ofRepoId(invoice.getC_Invoice_ID());
					final ProductId expectedProductId = getProductId(row);
					final BigDecimal qtyInvoiced = row.getAsBigDecimal(I_C_InvoiceLine.COLUMNNAME_QtyInvoiced);

					//dev-note: we assume the tests are generally not using the same product and qty on different lines...
					final IQueryBuilder<I_C_InvoiceLine> queryBuilder = queryBL.createQueryBuilder(I_C_InvoiceLine.class)
							.addEqualsFilter(I_C_InvoiceLine.COLUMNNAME_C_Invoice_ID, invoiceId)
							.addEqualsFilter(I_C_InvoiceLine.COLUMNNAME_M_Product_ID, expectedProductId)
							.addEqualsFilter(I_C_InvoiceLine.COLUMNNAME_QtyInvoiced, qtyInvoiced);

					// ...or if they do, they have different inoutlines
					row.getAsOptionalIdentifier(I_C_InvoiceLine.COLUMNNAME_M_InOutLine_ID)
							.map(inOutLineTable::getId)
							.ifPresent(inOutLineId -> queryBuilder.addEqualsFilter(I_C_InvoiceLine.COLUMNNAME_M_InOutLine_ID, inOutLineId.getRepoId()));

					final I_C_InvoiceLine invoiceLineRecord = queryBuilder.create().firstOnlyNotNull(I_C_InvoiceLine.class);

					validateInvoiceLine(invoiceLineRecord, invoice, row);
				});
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

		assertThat(invoiceLines.size()).as(invoiceLinesErrorMessage.toString()).isEqualTo(rows.size());

		rows.forEach(row -> {
			final ProductId productId = getProductId(row);

			final BigDecimal qtyInvoiced = row.getAsBigDecimal(I_C_InvoiceLine.COLUMNNAME_QtyInvoiced);

			final I_C_InvoiceLine currentInvoiceLine = Check.singleElement(invoiceLines.stream()
					.filter(line -> line.getM_Product_ID() == productId.getRepoId())
					.filter(line -> line.getQtyInvoiced().equals(qtyInvoiced))
					.collect(ImmutableList.toImmutableList()));

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
		final SoftAssertions softly = new SoftAssertions();

		final BigDecimal qtyEntered = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_InvoiceLine.COLUMNNAME_QtyEntered);
		if (qtyEntered != null)
		{
			softly.assertThat(invoiceLine.getQtyEntered()).isEqualByComparingTo(qtyEntered);
		}

		final BigDecimal qtyEnteredInBPartnerUOM = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_InvoiceLine.COLUMNNAME_QtyEnteredInBPartnerUOM);
		if (qtyEnteredInBPartnerUOM != null)
		{
			softly.assertThat(invoiceLine.getQtyEnteredInBPartnerUOM()).isEqualTo(qtyEnteredInBPartnerUOM);
		}

		final String bPartnerUOMx12de355Code = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_InvoiceLine.COLUMNNAME_C_UOM_BPartner_ID + "." + X12DE355.class.getSimpleName());
		if (Check.isNotBlank(bPartnerUOMx12de355Code))
		{
			final UomId bPartnerUOMId = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(bPartnerUOMx12de355Code));
			softly.assertThat(invoiceLine.getC_UOM_BPartner_ID()).isEqualTo(bPartnerUOMId.getRepoId());
		}

		final String uomX12de355Code = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_InvoiceLine.COLUMNNAME_C_UOM_ID + "." + X12DE355.class.getSimpleName());
		if (Check.isNotBlank(uomX12de355Code))
		{
			final UomId uomId = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(uomX12de355Code));
			softly.assertThat(invoiceLine.getC_UOM_ID()).isEqualTo(uomId.getRepoId());
		}

		final ProductId expectedProductId = getProductId(row);
		softly.assertThat(invoiceLine.getM_Product_ID()).as(COLUMNNAME_M_Product_ID).isEqualTo(expectedProductId.getRepoId());

		row.getAsOptionalBigDecimal(I_C_InvoiceLine.COLUMNNAME_QtyInvoiced)
				.ifPresent(qtyInvoiced -> {
					softly.assertThat(invoiceLine.getQtyInvoiced()).as(COLUMNNAME_QtyInvoiced).isEqualTo(qtyInvoiced);
				});

		row.getAsOptionalBoolean(I_C_InvoiceLine.COLUMNNAME_Processed)
				.ifPresent(processed -> {
					softly.assertThat(invoiceLine.isProcessed()).as(COLUMNNAME_Processed).isEqualTo(processed);
				});

		row.getAsOptionalMoney(I_C_InvoiceLine.COLUMNNAME_PriceEntered, moneyService::getCurrencyIdByCurrencyCode)
				.ifPresent(priceEntered -> {
					softly.assertThat(invoice.getC_Currency_ID()).as(I_C_Invoice.COLUMNNAME_C_Currency_ID).isEqualTo(priceEntered.getCurrencyId().getRepoId());
					softly.assertThat(invoiceLine.getPriceEntered()).as(COLUMNNAME_PriceEntered).isEqualByComparingTo(priceEntered.toBigDecimal());
				});

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

		final BigDecimal discount = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_InvoiceLine.COLUMNNAME_Discount);
		if (discount != null)
		{
			softly.assertThat(invoiceLine.getDiscount()).as(COLUMNNAME_Discount).isEqualByComparingTo(discount);
		}

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
		}

		final String priceUOMCode = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_Price_UOM_ID + "." + X12DE355.class.getSimpleName());
		if (Check.isNotBlank(priceUOMCode))
		{
			final UomId priceUOMId = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(priceUOMCode));

			softly.assertThat(invoiceLine.getPrice_UOM_ID()).as(COLUMNNAME_Price_UOM_ID).isEqualTo(priceUOMId.getRepoId());
		}

		final Boolean isManualPrice = row.getAsOptionalBoolean(COLUMNNAME_IsManualPrice).toBooleanOrNull();
		if (isManualPrice != null)
		{
			softly.assertThat(invoiceLine.isManualPrice()).as(COLUMNNAME_IsManualPrice).isEqualTo(isManualPrice);
		}

		final BigDecimal qtyInvoicedInPriceUOM = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_QtyInvoicedInPriceUOM);
		if (qtyInvoicedInPriceUOM != null)
		{
			softly.assertThat(invoiceLine.getQtyInvoicedInPriceUOM()).as(COLUMNNAME_QtyInvoicedInPriceUOM).isEqualByComparingTo(qtyInvoicedInPriceUOM);
		}

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
	}

	private void create_C_InvoiceLine(@NonNull final DataTableRow row)
	{
		final I_C_Invoice invoice = row.getAsIdentifier(COLUMNNAME_C_Invoice_ID).lookupNotNullIn(invoiceTable);

		final StepDefDataIdentifier productIdentifier = row.getAsIdentifier(I_C_InvoiceLine.COLUMNNAME_M_Product_ID);
		final ProductId productId = productTable.getIdOptional(productIdentifier)
				.orElseGet(() -> productIdentifier.getAsId(ProductId.class));

		final Quantity qtyEntered = row.getAsQuantity(COLUMNNAME_QtyInvoiced, COLUMNNAME_C_UOM_ID, uomDAO::getByX12DE355);

		final I_C_InvoiceLine invoiceLine = InterfaceWrapperHelper.newInstance(I_C_InvoiceLine.class);
		invoiceLine.setAD_Org_ID(invoice.getAD_Org_ID());
		invoiceLine.setC_Invoice_ID(invoice.getC_Invoice_ID());
		invoiceLine.setM_Product_ID(productId.getRepoId());
		invoiceLine.setQtyEntered(qtyEntered.toBigDecimal());
		invoiceLine.setQtyInvoiced(qtyEntered.toBigDecimal());
		invoiceLine.setPrice_UOM_ID(qtyEntered.getUomId().getRepoId());

		row.getAsOptionalBigDecimal("Price")
				.ifPresent(price -> {
					invoiceLine.setIsManualPrice(true);
					invoiceLine.setPriceEntered(price);
					invoiceLine.setPriceActual(price);
				});

		invoiceLineBL.updatePrices(invoiceLine);
		invoiceLineBL.updateLineNetAmt(invoiceLine, qtyEntered.toBigDecimal());

		InterfaceWrapperHelper.save(invoiceLine);

		row.getAsOptionalIdentifier("C_Tax_ID")
				.ifPresent(taxIdentifier -> {
					final TaxId taxId = TaxId.ofRepoId(invoiceLine.getC_Tax_ID());
					final Tax tax = taxDAO.getTaxById(taxId);
					taxTable.putOrReplaceIfSameId(taxIdentifier, tax);
				});

		row.getAsOptionalIdentifier()
				.ifPresent(identifier -> invoiceLineTable.putOrReplace(identifier, invoiceLine));
	}
}
