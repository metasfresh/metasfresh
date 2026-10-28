/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2026 metas GmbH
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
package de.metas.cucumber.stepdefs.customs;

import de.metas.cucumber.stepdefs.C_BPartner_Location_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.shipment.M_InOutLine_StepDefData;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.document.engine.IDocument;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Customs_Invoice;
import org.compiere.model.I_C_Customs_Invoice_Line;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_InOutLine_To_C_Customs_Invoice_Line;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_C_DocType;
import org.compiere.util.TimeUtil;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static de.metas.cucumber.stepdefs.util.SqlQueryTestUtil.queryRows;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Creates customs invoices (header, lines, shipment-line links) and asserts the output of the customs invoice report functions.
 */
@RequiredArgsConstructor
public class C_Customs_Invoice_StepDef
{
	private static final String AD_LANGUAGE = "en_US";
	private static final String DEFAULT_CURRENCY_ISO_CODE = "EUR";
	/** The report functions hardcode this C_UOM_ID as kg, see e.g. docs_sales_customs_invoice_footer. */
	private static final int HARDCODED_KG_UOM_ID = 540017;
	private static final String UOM_CODE_KGM = "KGM";

	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	@NonNull private final C_Customs_Invoice_StepDefData customsInvoiceTable;
	@NonNull private final C_Customs_Invoice_Line_StepDefData customsInvoiceLineTable;
	@NonNull private final C_BPartner_StepDefData bpartnerTable;
	@NonNull private final C_BPartner_Location_StepDefData bpartnerLocationTable;
	@NonNull private final M_Product_StepDefData productTable;
	@NonNull private final M_InOutLine_StepDefData inoutLineTable;
	@NonNull private final CurrencyRepository currencyRepository;

	/**
	 * Creates draft customs invoices; the document type is the active one with base type {@code CustomsInvoice}.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Identifier</b> — (required) alias of the customs invoice<br>
	 *   <b>C_BPartner_ID</b> — (required, identifier-ref) the customer<br>
	 *   <b>C_BPartner_Location_ID</b> — (optional, identifier-ref) the customer's location<br>
	 *   <b>DateInvoiced</b> — (optional) date, e.g. 2022-05-17<br>
	 *   <b>C_Currency_ID</b> — (optional, ISO code, default EUR) the currency<br>
	 *   <b>DocumentNo</b> — (optional, default: "CI-" + identifier)<br>
	 * @cucumber.depends StepDefData: C_BPartner_StepDefData, C_BPartner_Location_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And metasfresh contains C_Customs_Invoice:
	 *   | Identifier | C_BPartner_ID | C_BPartner_Location_ID | DateInvoiced |
	 *   | ci_1       | bpartner_1    | bpl_1                  | 2022-05-17   |
	 * </pre>
	 */
	@And("metasfresh contains C_Customs_Invoice:")
	public void metasfresh_contains_C_Customs_Invoice(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_C_Customs_Invoice record = newInstance(I_C_Customs_Invoice.class);
			record.setC_DocType_ID(retrieveCustomsInvoiceDocTypeId());
			record.setDocStatus(IDocument.STATUS_Drafted);
			record.setDocAction(IDocument.ACTION_Complete);
			record.setDateInvoiced(row.getAsOptionalLocalDateTimestamp(I_C_Customs_Invoice.COLUMNNAME_DateInvoiced)
					.orElseGet(() -> TimeUtil.asTimestamp(Instant.now())));

			record.setC_BPartner_ID(bpartnerTable.getId(row.getAsIdentifier(I_C_Customs_Invoice.COLUMNNAME_C_BPartner_ID)).getRepoId());
			record.setC_Currency_ID(currencyRepository.getCurrencyIdByCurrencyCode(row.getAsOptionalCurrencyCode().orElseGet(() -> CurrencyCode.ofThreeLetterCode(DEFAULT_CURRENCY_ISO_CODE))).getRepoId());
			record.setDocumentNo(row.getAsOptionalString(I_C_Customs_Invoice.COLUMNNAME_DocumentNo)
					.orElseGet(() -> "CI-" + row.getAsIdentifier().getAsString()));
			row.getAsOptionalIdentifier(I_C_Customs_Invoice.COLUMNNAME_C_BPartner_Location_ID)
					.map(identifier -> identifier.lookupNotNullIn(bpartnerLocationTable))
					.map(I_C_BPartner_Location::getC_BPartner_Location_ID)
					.ifPresent(record::setC_BPartner_Location_ID);

			saveRecord(record);
			customsInvoiceTable.putOrReplace(row.getAsIdentifier(), record);
		});
	}

	/**
	 * Creates customs invoice lines; {@code LineNo} counts up per invoice.
	 * Kg must be the UOM that the report functions hardcode as kg (C_UOM_ID 540017); the step fails otherwise.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Identifier</b> — (required) alias of the line<br>
	 *   <b>C_Customs_Invoice_ID</b> — (required, identifier-ref) the customs invoice<br>
	 *   <b>M_Product_ID</b> — (required, identifier-ref) the product<br>
	 *   <b>InvoicedQty</b> — (required) invoiced quantity<br>
	 *   <b>C_UOM_ID.X12DE355</b> — (required) UOM code, e.g. KGM<br>
	 *   <b>LineNetAmt</b> — (optional, default 0) line amount<br>
	 * @cucumber.depends StepDefData: C_Customs_Invoice_StepDefData, M_Product_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And metasfresh contains C_Customs_Invoice_Line:
	 *   | Identifier | C_Customs_Invoice_ID | M_Product_ID | InvoicedQty | C_UOM_ID.X12DE355 | LineNetAmt |
	 *   | cil_1      | ci_1                 | p_1          | 27          | KGM               | 100        |
	 * </pre>
	 */
	@And("metasfresh contains C_Customs_Invoice_Line:")
	public void metasfresh_contains_C_Customs_Invoice_Line(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_C_Customs_Invoice customsInvoice = row.getAsIdentifier(I_C_Customs_Invoice_Line.COLUMNNAME_C_Customs_Invoice_ID).lookupNotNullIn(customsInvoiceTable);
			final I_M_Product product = row.getAsIdentifier(I_C_Customs_Invoice_Line.COLUMNNAME_M_Product_ID).lookupNotNullIn(productTable);
			final I_C_UOM uom = uomDAO.getByX12DE355(row.getAsUOMCode(I_C_Customs_Invoice_Line.COLUMNNAME_C_UOM_ID));
			if (UOM_CODE_KGM.equals(uom.getX12DE355()))
			{
				// the report functions only take the "is kg" branch for this id; otherwise netto/brutto weights would silently be computed via the conversion branch
				assertThat(uom.getC_UOM_ID()).as("C_UOM_ID of KGM; the customs invoice report functions hardcode the kg UOM id").isEqualTo(HARDCODED_KG_UOM_ID);
			}

			final int existingLinesCount = queryBL.createQueryBuilder(I_C_Customs_Invoice_Line.class)
					.addEqualsFilter(I_C_Customs_Invoice_Line.COLUMNNAME_C_Customs_Invoice_ID, customsInvoice.getC_Customs_Invoice_ID())
					.create()
					.count();

			final I_C_Customs_Invoice_Line record = newInstance(I_C_Customs_Invoice_Line.class);
			record.setC_Customs_Invoice_ID(customsInvoice.getC_Customs_Invoice_ID());
			record.setLineNo((existingLinesCount + 1) * 10);
			record.setM_Product_ID(product.getM_Product_ID());
			record.setInvoicedQty(row.getAsBigDecimal(I_C_Customs_Invoice_Line.COLUMNNAME_InvoicedQty));
			record.setC_UOM_ID(uom.getC_UOM_ID());
			record.setLineNetAmt(row.getAsOptionalBigDecimal(I_C_Customs_Invoice_Line.COLUMNNAME_LineNetAmt).orElse(BigDecimal.ZERO));
			saveRecord(record);

			customsInvoiceLineTable.putOrReplace(row.getAsIdentifier(), record);
		});
	}

	/**
	 * Links customs invoice lines to shipment lines. The link carries the customs line's product,
	 * because the gross weight calculation joins on line and product.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>C_Customs_Invoice_Line_ID</b> — (required, identifier-ref) the customs invoice line<br>
	 *   <b>M_InOutLine_ID</b> — (required, identifier-ref) the shipment line<br>
	 *   <b>MovementQty</b> — (optional, default: the shipment line's MovementQty)<br>
	 * @cucumber.depends StepDefData: C_Customs_Invoice_Line_StepDefData, M_InOutLine_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And metasfresh contains M_InOutLine_To_C_Customs_Invoice_Line:
	 *   | C_Customs_Invoice_Line_ID | M_InOutLine_ID |
	 *   | cil_1                     | shipmentLine_1 |
	 * </pre>
	 */
	@And("metasfresh contains M_InOutLine_To_C_Customs_Invoice_Line:")
	public void metasfresh_contains_M_InOutLine_To_C_Customs_Invoice_Line(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_C_Customs_Invoice_Line customsLine = row.getAsIdentifier(I_M_InOutLine_To_C_Customs_Invoice_Line.COLUMNNAME_C_Customs_Invoice_Line_ID).lookupNotNullIn(customsInvoiceLineTable);
			final I_M_InOutLine shipmentLine = row.getAsIdentifier(I_M_InOutLine_To_C_Customs_Invoice_Line.COLUMNNAME_M_InOutLine_ID).lookupNotNullIn(inoutLineTable);

			final I_M_InOutLine_To_C_Customs_Invoice_Line record = newInstance(I_M_InOutLine_To_C_Customs_Invoice_Line.class);
			record.setC_Customs_Invoice_ID(customsLine.getC_Customs_Invoice_ID());
			record.setC_Customs_Invoice_Line_ID(customsLine.getC_Customs_Invoice_Line_ID());
			record.setM_InOutLine_ID(shipmentLine.getM_InOutLine_ID());
			record.setM_InOut_ID(shipmentLine.getM_InOut_ID());
			record.setM_Product_ID(customsLine.getM_Product_ID());
			record.setC_UOM_ID(customsLine.getC_UOM_ID());
			record.setMovementQty(row.getAsOptionalBigDecimal(I_M_InOutLine_To_C_Customs_Invoice_Line.COLUMNNAME_MovementQty).orElseGet(shipmentLine::getMovementQty));
			saveRecord(record);
		});
	}

	/**
	 * Calls {@code de_metas_endcustomer_fresh_reports.docs_sales_customs_invoice_footer} and compares its rows with the table. Rows are ordered by nettoTotalWeight, then bruttoTotalWeight; list multi-row expectations in that order.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>nettoTotalWeight</b> — (required) expected net total weight<br>
	 *   <b>bruttoTotalWeight</b> — (required) expected gross total weight<br>
	 * @cucumber.depends StepDefData: C_Customs_Invoice_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then the customs invoice footer of ci_1 is:
	 *   | nettoTotalWeight | bruttoTotalWeight |
	 *   | 3115.030         | 3236.340          |
	 * </pre>
	 */
	@Then("the customs invoice footer of {word} is:")
	public void the_customs_invoice_footer_is(@NonNull final String customsInvoiceIdentifier, @NonNull final DataTable dataTable)
	{
		final int customsInvoiceId = customsInvoiceTable.getId(customsInvoiceIdentifier).getRepoId();
		final List<BigDecimal[]> actualRows = new ArrayList<>();
		queryRows(
				"SELECT nettoTotalWeight, bruttoTotalWeight FROM de_metas_endcustomer_fresh_reports.docs_sales_customs_invoice_footer("
						+ customsInvoiceId + "::numeric, '" + AD_LANGUAGE + "'::varchar) ORDER BY nettoTotalWeight, bruttoTotalWeight",
				rs -> actualRows.add(new BigDecimal[] { rs.getBigDecimal(1), rs.getBigDecimal(2) }));

		final List<BigDecimal[]> expectedRows = new ArrayList<>();
		DataTableRows.of(dataTable).forEach(row -> expectedRows.add(new BigDecimal[] {
				row.getAsBigDecimal("nettoTotalWeight"),
				row.getAsBigDecimal("bruttoTotalWeight") }));

		assertThat(actualRows).as("footer rows of customs invoice " + customsInvoiceIdentifier).hasSameSizeAs(expectedRows);
		for (int i = 0; i < expectedRows.size(); i++)
		{
			assertThat(actualRows.get(i)[0]).as("nettoTotalWeight of footer row " + i).isEqualByComparingTo(expectedRows.get(i)[0]);
			assertThat(actualRows.get(i)[1]).as("bruttoTotalWeight of footer row " + i).isEqualByComparingTo(expectedRows.get(i)[1]);
		}
	}

	/**
	 * Calls {@code de_metas_endcustomer_fresh_reports.docs_sales_customs_invoice_details} and compares the sums over all its rows.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>invoicedqty</b> — (optional) expected sum of the invoiced quantities<br>
	 *   <b>linenetamt</b> — (optional) expected sum of the line amounts<br>
	 *   <b>bruttoweight</b> — (optional) expected sum of the gross weights<br>
	 * @cucumber.depends StepDefData: C_Customs_Invoice_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then the customs invoice details of ci_1 sum to:
	 *   | bruttoweight |
	 *   | 3236.340     |
	 * </pre>
	 */
	@Then("the customs invoice details of {word} sum to:")
	public void the_customs_invoice_details_sum_to(@NonNull final String customsInvoiceIdentifier, @NonNull final DataTable dataTable)
	{
		final int customsInvoiceId = customsInvoiceTable.getId(customsInvoiceIdentifier).getRepoId();
		final List<BigDecimal[]> actualRows = new ArrayList<>();
		queryRows(
				"SELECT COALESCE(SUM(invoicedqty), 0), COALESCE(SUM(linenetamt), 0), COALESCE(SUM(bruttoweight), 0)"
						+ " FROM de_metas_endcustomer_fresh_reports.docs_sales_customs_invoice_details("
						+ customsInvoiceId + "::numeric, '" + AD_LANGUAGE + "'::varchar)",
				rs -> actualRows.add(new BigDecimal[] { rs.getBigDecimal(1), rs.getBigDecimal(2), rs.getBigDecimal(3) }));
		final BigDecimal[] actual = actualRows.get(0);

		DataTableRows.of(dataTable).forEach(row -> {
			row.getAsOptionalBigDecimal("invoicedqty").ifPresent(expected -> assertThat(actual[0]).as("sum of invoicedqty").isEqualByComparingTo(expected));
			row.getAsOptionalBigDecimal("linenetamt").ifPresent(expected -> assertThat(actual[1]).as("sum of linenetamt").isEqualByComparingTo(expected));
			row.getAsOptionalBigDecimal("bruttoweight").ifPresent(expected -> assertThat(actual[2]).as("sum of bruttoweight").isEqualByComparingTo(expected));
		});
	}

	private int retrieveCustomsInvoiceDocTypeId()
	{
		final int docTypeId = queryBL.createQueryBuilder(I_C_DocType.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_DocType.COLUMNNAME_DocBaseType, X_C_DocType.DOCBASETYPE_CustomsInvoice)
				.orderBy(I_C_DocType.COLUMNNAME_C_DocType_ID)
				.create()
				.firstId();
		if (docTypeId <= 0)
		{
			throw new AdempiereException("No active C_DocType with DocBaseType=" + X_C_DocType.DOCBASETYPE_CustomsInvoice + " found");
		}
		return docTypeId;
	}
}
