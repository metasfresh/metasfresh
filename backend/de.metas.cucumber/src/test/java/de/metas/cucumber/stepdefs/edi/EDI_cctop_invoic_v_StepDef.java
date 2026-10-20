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

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableList;
import de.metas.JsonObjectMapperHolder;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.edi.async.spi.impl.EDIWorkpackageProcessor;
import de.metas.edi.model.I_EDI_Document;
import de.metas.edi.process.export.IExport;
import de.metas.edi.process.export.impl.C_InvoiceExport;
import de.metas.esb.edi.model.I_EDI_Desadv;
import de.metas.esb.edi.model.I_EDI_cctop_invoic_v;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.annotation.Nullable;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.*;

public class EDI_cctop_invoic_v_StepDef
{
	private static final String COLUMNNAME_EDIErrorMsg_AD_Message = "EDIErrorMsg.AD_Message";
	private static final List<String> SYSTEM_LANGUAGES = ImmutableList.of("de_DE", "de_CH", "en_US");
	private static final String TAGNAME_GTIN = "GTIN";
	private static final String TAGNAME_CONTACT = "Contact";
	private static final String TAGNAME_EANCOM_LOCATIONTYPE = "eancom_locationtype";
	private static final String TAGNAME_BUYER_GTIN_CU = "Buyer_GTIN_CU";
	private static final String TAGNAME_BUYER_EAN_CU = "Buyer_EAN_CU";
	private static final String TAGNAME_SUPPLIER_GTIN_CU = "Supplier_GTIN_CU";
	private static final String TAGNAME_BUYER_GTIN_TU = "Buyer_GTIN_TU";
	private static final String TAGNAME_EDI_CCTOP_INVOIC_500_V = "EDI_cctop_invoic_500_v";
	private static final String TAGNAME_EDI_CCTOP_119_V = "EDI_cctop_119_v";
	private static final String TAGNAME_LINE = "Line";
	private static final String TAGNAME_VALUE = "Value";
	private static final String TAGNAME_QTY_INVOICED = "QtyInvoiced";

	@NonNull private final IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);
	@NonNull private final IMsgBL msgBL = Services.get(IMsgBL.class);

	private final EDI_cctop_invoic_v_StepDefData cctopInvoiceTable;
	private final C_Invoice_StepDefData invoiceTable;
	private final EDI_Desadv_StepDefData desadvTable;
	private final M_Product_StepDefData productTable;
	private final TestContext testContext;

	public EDI_cctop_invoic_v_StepDef(
			@NonNull final EDI_cctop_invoic_v_StepDefData cctopInvoiceTable,
			@NonNull final C_Invoice_StepDefData invoiceTable,
			@NonNull final EDI_Desadv_StepDefData desadvTable,
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final TestContext testContext)
	{
		this.cctopInvoiceTable = cctopInvoiceTable;
		this.invoiceTable = invoiceTable;
		this.desadvTable = desadvTable;
		this.productTable = productTable;
		this.testContext = testContext;
	}

	/**
	 * Asserts the order of the {@code Lines} of the single invoice in the last INVOIC JSON export response:
	 * line by line, in array order, the {@code Invoice_Line} and whether the line is a packing line ({@code Packaging} set).
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Invoice_Line</b> — (required) the expected {@code Invoice_Line}, one row per line, in the expected order<br>
	 *   <b>IsPackagingMaterial</b> — (required) {@code Y} if the line carries the {@code Packaging} sub-object, {@code N} if {@code Packaging} is null<br>
	 * @cucumber.depends TestContext (the response of the INVOIC JSON export request)
	 * @cucumber.example
	 * <pre>
	 * And the INVOIC JSON response lists its Lines in this order:
	 *   | Invoice_Line | IsPackagingMaterial |
	 *   | 10           | N                   |
	 *   | 20           | Y                   |
	 * </pre>
	 */
	@And("the INVOIC JSON response lists its Lines in this order:")
	public void validate_INVOIC_JSON_lines_order(@NonNull final DataTable dataTable) throws IOException
	{
		final JsonNode invoices = JsonObjectMapperHolder.sharedJsonObjectMapper()
				.readTree(testContext.getApiResponseBodyAsString())
				.get("metasfresh_INVOIC");
		assertThat(invoices).as("metasfresh_INVOIC").isNotNull();
		assertThat(invoices.size()).as("number of invoices in metasfresh_INVOIC").isEqualTo(1);

		final List<String> actualLines = new ArrayList<>();
		invoices.get(0).get("Lines").forEach(line -> actualLines.add(
				lineOrderKey(line.get("Invoice_Line").asInt(), isPackagingLine(line.get("Packaging")))));

		final List<String> expectedLines = DataTableRows.of(dataTable).stream()
				.map(row -> lineOrderKey(row.getAsInt("Invoice_Line"), row.getAsBoolean("IsPackagingMaterial")))
				.collect(ImmutableList.toImmutableList());

		assertThat(actualLines).as("Invoice_Line|IsPackagingMaterial of the INVOIC JSON Lines, in array order").containsExactlyElementsOf(expectedLines);
	}

	private static boolean isPackagingLine(@Nullable final JsonNode packaging)
	{
		return packaging != null && !packaging.isNull() && packaging.path("IsPackagingMaterial").asBoolean(false);
	}

	private static String lineOrderKey(final int invoiceLine, final boolean isPackagingMaterial)
	{
		return invoiceLine + "|" + (isPackagingMaterial ? "Y" : "N");
	}

	/**
	 * Asserts the complete list of line items ({@code EDI_cctop_invoic_500_v} elements) of an {@code EDI_cctop_invoic_v} XML
	 * received from RabbitMQ: no line item more, none less.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Line</b> — (required) the line item's {@code Line}<br>
	 *   <b>M_Product_ID</b> — (required, identifier-ref) product; compared with the line item's {@code Value}<br>
	 *   <b>QtyInvoiced</b> — (required) the line item's {@code QtyInvoiced}<br>
	 * @cucumber.depends StepDefData: EDI_cctop_invoic_v_StepDefData, M_Product_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And the EDI_cctop_invoic_v ic_1 XML has exactly these EDI_cctop_invoic_500_v lines:
	 *   | Line | M_Product_ID | QtyInvoiced |
	 *   | 10   | goods        | 20          |
	 * </pre>
	 */
	@And("^the EDI_cctop_invoic_v (.*) XML has exactly these EDI_cctop_invoic_500_v lines:$")
	public void validate_exact_EDI_cctop_invoic_500_v_lines(@NonNull final String invoicIdentifier, @NonNull final DataTable dataTable)
	{
		final Document invoic = cctopInvoiceTable.get(invoicIdentifier);
		final NodeList lineElements = invoic.getElementsByTagName(TAGNAME_EDI_CCTOP_INVOIC_500_V);

		final List<String> actualLines = new ArrayList<>();
		for (int index = 0; index < lineElements.getLength(); index++)
		{
			final Element lineElement = (Element)lineElements.item(index);
			actualLines.add(lineKey(
					getElementText(lineElement, TAGNAME_LINE),
					getElementText(lineElement, TAGNAME_VALUE),
					new BigDecimal(getElementText(lineElement, TAGNAME_QTY_INVOICED))));
		}

		final List<String> expectedLines = DataTableRows.of(dataTable).stream()
				.map(row -> lineKey(
						String.valueOf(row.getAsInt(TAGNAME_LINE)),
						row.getAsIdentifier(I_M_Product.COLUMNNAME_M_Product_ID).lookupNotNullIn(productTable).getValue(),
						row.getAsBigDecimal(TAGNAME_QTY_INVOICED)))
				.collect(ImmutableList.toImmutableList());

		assertThat(actualLines).as("EDI_cctop_invoic_500_v line items (Line|Value|QtyInvoiced) of %s", invoicIdentifier)
				.containsExactlyInAnyOrderElementsOf(expectedLines);
	}

	private static String lineKey(@NonNull final String line, @NonNull final String productValue, @NonNull final BigDecimal qtyInvoiced)
	{
		return line + "|" + productValue + "|" + qtyInvoiced.stripTrailingZeros().toPlainString();
	}

	@NonNull
	private static String getElementText(@NonNull final Element parent, @NonNull final String tagName)
	{
		final Element element = getElement(parent, tagName);
		assertThat(element).as("element %s in %s", tagName, parent.getTagName()).isNotNull();
		return element.getTextContent().trim();
	}

	@Then("invoice is EDI exported")
	public void edi_export_invoice(@NonNull final DataTable table)
	{
		final List<Map<String, String>> dataTable = table.asMaps();
		for (final Map<String, String> row : dataTable)
		{
			ediExportInvoice(row);
		}
	}

	@Then("invoice is enqueued for EDI export")
	public void enqueue_edi_export_invoice(@NonNull final DataTable table)
	{
		DataTableRows.of(table).forEach(this::enqueue_edi_export_invoice);
	}

	private void enqueue_edi_export_invoice(@NonNull final DataTableRow row)
	{
		final de.metas.edi.model.I_C_Invoice ediInvoiceRecord =
				InterfaceWrapperHelper.create(
						row.getAsIdentifier(I_C_Invoice.COLUMNNAME_C_Invoice_ID).lookupNotNullIn(invoiceTable),
						de.metas.edi.model.I_C_Invoice.class);

		final IWorkPackageQueue queue = workPackageQueueFactory.getQueueForEnqueuing(Env.getCtx(), EDIWorkpackageProcessor.class);

		queue.newWorkPackage()
				.setPriority(IWorkPackageQueue.PRIORITY_AUTO)
				.addElement(ediInvoiceRecord)
				.bindToThreadInheritedTrx()
				.buildAndEnqueue();
	}

	/**
	 * Waits until each C_Invoice has the given export status, then optionally checks its error message.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>C_Invoice_ID</b> — (required, identifier-ref) the invoice<br>
	 *   <b>EDI_ExportStatus</b> — (required) expected export status code<br>
	 *   <b>EDIErrorMsg.AD_Message</b> — (optional) AD_Message value; the stored error message must contain the text of that message in at least one system language (de_DE, de_CH, en_US), so the check does not depend on the base language<br>
	 * @cucumber.depends StepDefData: C_Invoice_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then after not more than 30s, C_Invoice records have the following export status
	 *   | C_Invoice_ID | EDI_ExportStatus | EDIErrorMsg.AD_Message |
	 *   | invoice_1    | I                | SomeMessageKey         |
	 * </pre>
	 */
	@Then("^after not more than (.*)s, C_Invoice records have the following export status$")
	public void validate_export_status(final int timeoutSec, @NonNull final DataTable table) throws InterruptedException
	{
		DataTableRows.of(table).forEach(row -> validateExportStatus(timeoutSec, row));
	}

	private void validateExportStatus(
			final int timeoutSec,
			@NonNull final DataTableRow tableRow) throws InterruptedException
	{
		final de.metas.edi.model.I_C_Invoice ediInvoiceRecord =
				InterfaceWrapperHelper.create(
						tableRow.getAsIdentifier(I_C_Invoice.COLUMNNAME_C_Invoice_ID).lookupNotNullIn(invoiceTable),
						de.metas.edi.model.I_C_Invoice.class);

		final String exportStatus = tableRow.getAsString(I_EDI_Desadv.COLUMNNAME_EDI_ExportStatus);

		StepDefUtil.tryAndWait(timeoutSec, 1000, () -> {
			InterfaceWrapperHelper.refresh(ediInvoiceRecord);
			return exportStatus.equals(ediInvoiceRecord.getEDI_ExportStatus());
		});

		tableRow.getAsOptionalString(COLUMNNAME_EDIErrorMsg_AD_Message)
				.ifPresent(messageKey -> assertThat(SYSTEM_LANGUAGES.stream()
						.map(adLanguage -> msgBL.getMsg(adLanguage, AdMessageKey.of(messageKey)))
						.anyMatch(text -> ediInvoiceRecord.getEDIErrorMsg() != null && ediInvoiceRecord.getEDIErrorMsg().contains(text)))
						.as("EDIErrorMsg of C_Invoice %s is expected to contain the text of the message %s in one of %s, but is: %s",
							tableRow.getAsIdentifier(I_C_Invoice.COLUMNNAME_C_Invoice_ID).getAsString(), messageKey, SYSTEM_LANGUAGES, ediInvoiceRecord.getEDIErrorMsg())
						.isTrue());
	}

	@Then("EDI_cctop_invoic_500_v of the following EDI_cctop_invoic_v is validated")
	public void validate_EDI_cctop_invoic_500_v(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			validateEdiInvoic500(tableRow);
		}
	}

	@Then("^validate EDI_cctop_119_v within EDI_cctop_invoic_v (.*) by location type$")
	public void validate_EDI_cctop_119_v(
			@NonNull final String invoicIdentifier,
			@NonNull final DataTable dataTable)
	{
		final Document doc = cctopInvoiceTable.get(invoicIdentifier);

		final NodeList invoic500List = doc.getElementsByTagName(TAGNAME_EDI_CCTOP_119_V);

		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);

		assertThat(invoic500List.getLength()).isEqualTo(tableRows.size());

		final Map<String, Element> locationType2Cctop119Element = getLocationType2Cctop119Element(invoic500List);

		for (final Map<String, String> tableRow : tableRows)
		{
			validateEdiCctop119(tableRow, locationType2Cctop119Element);
		}
	}

	private void validateEdiCctop119(
			@NonNull final Map<String, String> tableRow,
			@NonNull final Map<String, Element> locationType2Cctop119Element)
	{
		final String eanComLocationType = DataTableUtil.extractStringForColumnName(tableRow, TAGNAME_EANCOM_LOCATIONTYPE);

		final Element cctop119Element = locationType2Cctop119Element.get(eanComLocationType);
		assertThat(cctop119Element).isNotNull();

		final String contact = DataTableUtil.extractNullableStringForColumnName(tableRow, "OPT." + TAGNAME_CONTACT);

		if (Check.isNotBlank(contact))
		{
			final Element contactElement = getElement(cctop119Element, TAGNAME_CONTACT);

			final String actualContactValue = DataTableUtil.nullToken2Null(contact);

			if (actualContactValue == null)
			{
				assertThat(contactElement).isNull();
			}
			else
			{
				assertThat(contactElement.getTextContent()).isEqualTo(actualContactValue);
			}
		}
	}

	private void validateEdiInvoic500(@NonNull final Map<String, String> tableRow)
	{
		final String invoicIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_EDI_cctop_invoic_v.COLUMNNAME_EDI_cctop_invoic_v_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final Document invoic = cctopInvoiceTable.get(invoicIdentifier);

		assertThat(invoic).isNotNull();

		final Element invoic500Element = getElement(invoic, TAGNAME_EDI_CCTOP_INVOIC_500_V);

		assertThat(invoic500Element).isNotNull();

		final String buyerGTINCU = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + TAGNAME_BUYER_GTIN_CU);

		if (Check.isNotBlank(buyerGTINCU))
		{
			final Element buyerGtinCuElement = getElement(invoic500Element, TAGNAME_BUYER_GTIN_CU);

			assertThat(buyerGtinCuElement).isNotNull();
			assertThat(buyerGtinCuElement.getTextContent()).isEqualTo(buyerGTINCU);
		}

		final String buyerEanCu = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + TAGNAME_BUYER_EAN_CU);

		if (Check.isNotBlank(buyerEanCu))
		{
			final Element buyerEanCuElement = getElement(invoic500Element, TAGNAME_BUYER_EAN_CU);

			assertThat(buyerEanCuElement).isNotNull();
			assertThat(buyerEanCuElement.getTextContent()).isEqualTo(buyerEanCu);
		}

		final String supplierGtinCu = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + TAGNAME_SUPPLIER_GTIN_CU);

		if (Check.isNotBlank(supplierGtinCu))
		{
			final Element supplierGtinCuElement = getElement(invoic500Element, TAGNAME_SUPPLIER_GTIN_CU);

			assertThat(supplierGtinCuElement).isNotNull();
			assertThat(supplierGtinCuElement.getTextContent()).isEqualTo(supplierGtinCu);
		}

		final String buyerGtinTu = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + TAGNAME_BUYER_GTIN_TU);

		if (Check.isNotBlank(buyerGtinTu))
		{
			final Element buyerGtinTuElement = getElement(invoic500Element, TAGNAME_BUYER_GTIN_TU);

			assertThat(buyerGtinTuElement).isNotNull();
			assertThat(buyerGtinTuElement.getTextContent()).isEqualTo(buyerGtinTu);
		}

		final String gtin = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + TAGNAME_GTIN);

		if (Check.isNotBlank(gtin))
		{
			final Element gtinElement = getElement(invoic500Element, TAGNAME_GTIN);

			assertThat(gtinElement).isNotNull();
			assertThat(gtinElement.getTextContent()).isEqualTo(gtin);
		}
	}

	@Nullable
	private static Element getElement(@NonNull final Node node, @NonNull final String tagName)
	{
		final Element element = node instanceof Document
				? ((Document)node).getDocumentElement()
				: (Element)node;

		final NodeList nodeList = element.getElementsByTagName(tagName);

		if (nodeList.getLength() == 0)
		{
			return null;
		}

		final Node childNode = nodeList.item(0);

		assertThat(childNode.getNodeType()).isEqualTo(Node.ELEMENT_NODE);

		return (Element)childNode;
	}

	private void ediExportInvoice(@NonNull final Map<String, String> row)
	{
		final String invoiceIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Invoice.COLUMNNAME_C_Invoice_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final Integer invoiceId = invoiceTable.getOptional(invoiceIdentifier)
				.map(I_C_Invoice::getC_Invoice_ID)
				.orElseGet(() -> Integer.parseInt(invoiceIdentifier));

		final de.metas.edi.model.I_C_Invoice invoiceRecord = InterfaceWrapperHelper.load(invoiceId, de.metas.edi.model.I_C_Invoice.class);

		final String tableIdentifierColumnName = org.compiere.model.I_C_Invoice.COLUMNNAME_C_Invoice_ID;

		final IExport<? extends I_EDI_Document> export = new C_InvoiceExport(invoiceRecord, tableIdentifierColumnName, Env.getClientId());
		final List<Exception> feedback = export.doExport();

		assertThat(feedback).isNullOrEmpty();
	}

	@NonNull
	private static Map<String, Element> getLocationType2Cctop119Element(@NonNull final NodeList invoic500List)
	{
		final Map<String, Element> locationType2Cctop119Element = new HashMap<>();

		for (int index = 0; index < invoic500List.getLength(); index++)
		{
			final Node cctop119 = invoic500List.item(index);

			assertThat(cctop119.getNodeType()).isEqualTo(Node.ELEMENT_NODE);

			final Element locationTypeElement = getElement(cctop119, TAGNAME_EANCOM_LOCATIONTYPE);

			assertThat(locationTypeElement).isNotNull();

			locationType2Cctop119Element.put(locationTypeElement.getTextContent(), (Element)cctop119);
		}

		return locationType2Cctop119Element;
	}

	@And("validate EDI_cctop_invoic_v:")
	public void validateEDI_cctop_invoic_v(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String invoicIdentifier = DataTableUtil.extractStringForColumnName(row, I_EDI_cctop_invoic_v.COLUMNNAME_EDI_cctop_invoic_v_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final Document invoic = cctopInvoiceTable.get(invoicIdentifier);

			final String desadvIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_EDI_cctop_invoic_v.COLUMNNAME_EDIDesadvDocumentNo + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(desadvIdentifier))
			{
				final I_EDI_Desadv desadvRecord = desadvTable.get(desadvIdentifier);

				final Element ediDocNoElement = getElement(invoic, I_EDI_cctop_invoic_v.COLUMNNAME_EDIDesadvDocumentNo);
				assertThat(ediDocNoElement).isNotNull();
				assertThat(ediDocNoElement.getTextContent()).isEqualTo(desadvRecord.getDocumentNo());
			}
		}
	}
}
