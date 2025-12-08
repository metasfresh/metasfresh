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

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.edi.model.I_EDI_Document;
import de.metas.edi.process.export.IExport;
import de.metas.edi.process.export.impl.C_InvoiceExport;
import de.metas.esb.edi.model.I_EDI_Desadv;
import de.metas.esb.edi.model.I_EDI_cctop_invoic_v;
import de.metas.util.Check;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Invoice;
import org.compiere.util.Env;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.*;

public class EDI_cctop_invoic_v_StepDef
{
	private static final String TAGNAME_GTIN = "GTIN";
	private static final String TAGNAME_CONTACT = "Contact";
	private static final String TAGNAME_EANCOM_LOCATIONTYPE = "eancom_locationtype";
	private static final String TAGNAME_BUYER_GTIN_CU = "Buyer_GTIN_CU";
	private static final String TAGNAME_BUYER_EAN_CU = "Buyer_EAN_CU";
	private static final String TAGNAME_SUPPLIER_GTIN_CU = "Supplier_GTIN_CU";
	private static final String TAGNAME_BUYER_GTIN_TU = "Buyer_GTIN_TU";
	private static final String TAGNAME_EDI_CCTOP_INVOIC_500_V = "EDI_cctop_invoic_500_v";
	private static final String TAGNAME_EDI_CCTOP_119_V = "EDI_cctop_119_v";

	private final EDI_cctop_invoic_v_StepDefData cctopInvoiceTable;
	private final C_Invoice_StepDefData invoiceTable;
	private final EDI_Desadv_StepDefData desadvTable;

	public EDI_cctop_invoic_v_StepDef(
			@NonNull final EDI_cctop_invoic_v_StepDefData cctopInvoiceTable,
			@NonNull final C_Invoice_StepDefData invoiceTable,
			@NonNull final EDI_Desadv_StepDefData desadvTable)
	{
		this.cctopInvoiceTable = cctopInvoiceTable;
		this.invoiceTable = invoiceTable;
		this.desadvTable = desadvTable;
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
