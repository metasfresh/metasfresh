/*
 * #%L
 * de.metas.swat.base
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

package de.metas.invoicecandidate.vatid;

import de.metas.invoicecandidate.model.I_C_Invoice_Line_Alloc;
import de.metas.location.ICountryAreaBL;
import de.metas.pricing.tax.ProductTaxCategoryRepository;
import de.metas.pricing.tax.ProductTaxCategoryService;
import de.metas.tax.api.SOPOType;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_CountryArea;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_C_DocType;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * The VAT IDs of a sales invoice at completion: the check of a prepared candidate invoice (lines with invoice candidate allocations) to which lines were added by hand,
 * including lines that reference an order line and the re-tax a doc type that overwrites the invoice date causes at completion,
 * and which documents are not resolved as manual invoices.
 */
class C_InvoiceVATaxIdCheckTest
{
	private static final Timestamp DATE = TimeUtil.parseTimestamp("2026-09-02");
	private static final String DE_VAT = "DE123456788";
	private static final String NL_VAT = "NL123456782B01";
	private static final String OTHER_DE_VAT = "DE136695976";
	private static final String AT_VAT = "ATU13585627";
	private static final String SELLER_VAT = "EL094259216";
	private static final int FISCAL_REPRESENTATION_ID = 540001;
	private static final int TAX_CATEGORY_ID = 44;

	private InvoiceVATaxIdStamper stamper;

	private int orgId;

	private I_C_BP_Group group;
	private I_C_BPartner_Location billToDE;
	private I_C_BPartner_Location deliveryNL;
	private int deId;
	private int atId;
	private int warehouseDEId;
	private int productId;
	private I_C_Tax taxDENL0Exempt;
	private I_C_Tax taxDEDE19;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
		// needed by the invoice line BL, which the stamper uses to predict a re-tax
		SpringContextHolder.registerJUnitBean(new ProductTaxCategoryService(new ProductTaxCategoryRepository()));
		stamper = InvoiceVATaxIdStamper.newInstanceForUnitTesting();

		// the re-tax inputs and the tax query look up the org and its info
		orgId = AdempiereTestHelper.createOrgWithTimeZone().getRepoId();

		deId = createCountry("DE");
		final int nlId = createCountry("NL");
		atId = createCountry("AT");
		// the tax query asks whether the destination is in the EU; the taxes here do not depend on it (no TypeOfDestCountry)
		final I_C_CountryArea countryAreaEU = newInstance(I_C_CountryArea.class);
		countryAreaEU.setValue(ICountryAreaBL.COUNTRYAREAKEY_EU);
		save(countryAreaEU);

		group = newInstance(I_C_BP_Group.class);
		group.setIsDeviatingBillBPartner(false);
		save(group);

		final I_C_BPartner customer = createBPartner(DE_VAT);

		billToDE = createLocation(customer, deId, DE_VAT);
		deliveryNL = createLocation(customer, nlId, NL_VAT);

		warehouseDEId = createWarehouse(createLocation(createBPartner(null), deId, null));

		final I_M_Product product = newInstance(I_M_Product.class);
		product.setAD_Org_ID(orgId);
		save(product);
		productId = product.getM_Product_ID();

		taxDENL0Exempt = createTax(true, deId, nlId);
		taxDEDE19 = createTax(false, deId, deId);
	}

	/**
	 * A hand-added line with an order line and the order line's tax (as "create from order" copies it) was taxed from the order line's (Dutch) location.
	 */
	@Test
	void handAddedLineWithOrderLine_taxFromOrderLine_usesOrderLineLocation()
	{
		final I_C_Invoice invoice = createInvoice(NL_VAT);
		createCandidateLine(invoice, taxDENL0Exempt);

		final I_C_InvoiceLine handAddedLine = createLine(invoice, taxDENL0Exempt);
		handAddedLine.setC_OrderLine_ID(createOrderLine(deliveryNL, taxDENL0Exempt).getC_OrderLine_ID());
		save(handAddedLine);

		assertThatCode(() -> stamper.stampOrCheck(invoice)).doesNotThrowAnyException();
	}

	/**
	 * A hand-added line with an order line whose tax is the one the re-tax gives from the order line's location (i.e. it was re-taxed after a date change)
	 * was taxed from the order line's (Dutch) location, even though the order line itself has another tax.
	 */
	@Test
	void handAddedLineWithOrderLine_reTaxedTax_usesOrderLineLocation()
	{
		final I_C_Invoice invoice = createInvoice(NL_VAT);
		createCandidateLine(invoice, taxDENL0Exempt);

		final I_C_InvoiceLine handAddedLine = createProductLine(invoice, taxDENL0Exempt);
		handAddedLine.setC_OrderLine_ID(createOrderLine(deliveryNL, taxDEDE19).getC_OrderLine_ID());
		save(handAddedLine);

		assertThatCode(() -> stamper.stampOrCheck(invoice)).doesNotThrowAnyException();
	}

	/**
	 * A hand-added line with an order line but another tax than the order line's and the re-tax's was taxed by {@code MInvoiceLine.setTax}, i.e. from the German bill-to.
	 */
	@Test
	void handAddedLineWithOrderLine_otherTax_usesBillTo()
	{
		final I_C_Tax taxDEDE0Exempt = createTax(true, deId, deId);
		final I_C_Invoice invoice = createInvoice(NL_VAT);
		createCandidateLine(invoice, taxDENL0Exempt);

		final I_C_InvoiceLine handAddedLine = createProductLine(invoice, taxDEDE0Exempt);
		handAddedLine.setC_OrderLine_ID(createOrderLine(deliveryNL, taxDENL0Exempt).getC_OrderLine_ID());
		save(handAddedLine);

		assertThatThrownBy(() -> stamper.stampOrCheck(invoice))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("C_Invoice_ExemptLineVATaxIdMismatch");
	}

	/**
	 * A doc type that overwrites the invoice date on completion re-taxes the lines after the check: a hand-added line with the Austrian standard rate
	 * becomes exempt on the Austrian VAT ID of the bill-to, which is not the stored Dutch one. Without that doc type flag the line is not exempt and not checked.
	 */
	@Test
	void overwriteDateDocType_predictsReTax()
	{
		final I_C_BPartner_Location billToAT = createLocation(createBPartner(AT_VAT), atId, AT_VAT);
		createTax(true, deId, atId);
		final I_C_Tax taxDEAT19 = createTax(false, deId, atId);

		final I_C_DocType docType = newInstance(I_C_DocType.class);
		docType.setDocBaseType(X_C_DocType.DOCBASETYPE_ARInvoice);
		docType.setIsOverwriteDateOnComplete(false);
		save(docType);

		final I_C_Invoice invoice = createInvoice(billToAT, NL_VAT);
		invoice.setC_DocType_ID(docType.getC_DocType_ID());
		invoice.setC_DocTypeTarget_ID(docType.getC_DocType_ID());
		invoice.setM_Warehouse_ID(warehouseDEId);
		save(invoice);
		createCandidateLine(invoice, taxDENL0Exempt);
		createProductLine(invoice, taxDEAT19);

		assertThatCode(() -> stamper.stampOrCheck(invoice)).doesNotThrowAnyException();

		docType.setIsOverwriteDateOnComplete(true);
		save(docType);

		assertThatThrownBy(() -> stamper.stampOrCheck(invoice))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("C_Invoice_ExemptLineVATaxIdMismatch");
	}

	/**
	 * A hand-added line with a shipment line was taxed from the shipment's location (here the Dutch one), not from the bill-to.
	 */
	@Test
	void handAddedLineWithShipment_usesShipmentLocation()
	{
		final I_C_Invoice invoice = createInvoice(NL_VAT);
		createCandidateLine(invoice, taxDENL0Exempt);

		final I_C_InvoiceLine handAddedLine = createLine(invoice, taxDENL0Exempt);
		handAddedLine.setM_InOutLine_ID(createShipmentLine(deliveryNL).getM_InOutLine_ID());
		save(handAddedLine);

		assertThatCode(() -> stamper.stampOrCheck(invoice)).doesNotThrowAnyException();
	}

	/**
	 * Counterpart of {@link #handAddedLineWithShipment_usesShipmentLocation()}: without a shipment line, the same line is resolved from the German bill-to.
	 */
	@Test
	void handAddedExemptLineWithoutShipment_otherVATaxId_fails()
	{
		final I_C_Invoice invoice = createInvoice(NL_VAT);
		createCandidateLine(invoice, taxDENL0Exempt);
		createLine(invoice, taxDENL0Exempt);

		assertThatThrownBy(() -> stamper.stampOrCheck(invoice))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("C_Invoice_ExemptLineVATaxIdMismatch");
	}

	/**
	 * Candidate lines agree with the stored VAT IDs by construction; this one would resolve to the German bill-to, yet it is not checked.
	 */
	@Test
	void candidateLine_isNotChecked()
	{
		final I_C_Invoice invoice = createInvoice(NL_VAT);
		createCandidateLine(invoice, taxDENL0Exempt);

		assertThatCode(() -> stamper.stampOrCheck(invoice)).doesNotThrowAnyException();
	}

	@Test
	void lineWithoutCertificateTax_isNotChecked()
	{
		final I_C_Invoice invoice = createInvoice(NL_VAT);
		createCandidateLine(invoice, taxDENL0Exempt);
		createLine(invoice, taxDEDE19);

		assertThatCode(() -> stamper.stampOrCheck(invoice)).doesNotThrowAnyException();
	}

	/**
	 * A candidate invoice prepared before the VAT IDs were stored completes as before.
	 */
	@Test
	void notStampedInvoice_isNotChecked()
	{
		final I_C_Invoice invoice = createInvoice(null);
		createCandidateLine(invoice, taxDENL0Exempt);
		createLine(invoice, taxDENL0Exempt);

		assertThatCode(() -> stamper.stampOrCheck(invoice)).doesNotThrowAnyException();
	}

	/**
	 * A reversal is completed right after its lines were copied; it is not resolved again (which could block it, its lines resolve to different VAT IDs),
	 * it gets the stored values of the reversed invoice.
	 */
	@Test
	void reversal_getsOriginalVATaxIds()
	{
		final I_C_Invoice original = createStampedInvoice();
		final I_C_Invoice reversal = createInvoice(OTHER_DE_VAT);
		reversal.setReversal_ID(original.getC_Invoice_ID());
		save(reversal);
		createLineForShipment(reversal, billToDE);
		createLineForShipment(reversal, deliveryNL);

		assertThatCode(() -> stamper.stampOrCheck(reversal)).doesNotThrowAnyException();
		assertOriginalVATaxIds(reversal);
	}

	/**
	 * A credit memo copied from an invoice gets the invoice's stored values; it is not resolved again either.
	 */
	@Test
	void creditMemo_getsOriginalVATaxIds()
	{
		final I_C_Invoice original = createStampedInvoice();
		final I_C_Invoice creditMemo = createCreditMemo(original);
		createLineForShipment(creditMemo, billToDE);
		createLineForShipment(creditMemo, deliveryNL);

		assertThatCode(() -> stamper.stampOrCheck(creditMemo)).doesNotThrowAnyException();
		assertOriginalVATaxIds(creditMemo);
	}

	/**
	 * The stored values of the credited invoice are copied as they are, also when they are empty (e.g. the invoice was completed before they were stored),
	 * although the credit memo's line would resolve the Dutch VAT ID of its delivery address.
	 */
	@Test
	void creditMemoOfNotStampedInvoice_staysEmpty()
	{
		final I_C_Invoice original = createInvoice(null);
		final I_C_Invoice creditMemo = createCreditMemo(original);
		createLineForShipment(creditMemo, deliveryNL);

		stamper.stampOrCheck(creditMemo);

		assertThat(creditMemo.getBuyerVATaxID()).isNull();
		assertThat(creditMemo.getSellerVATaxID()).isNull();
		assertThat(creditMemo.getC_Fiscal_Representation_ID()).isLessThanOrEqualTo(0);
	}

	private I_C_Invoice createStampedInvoice()
	{
		final I_C_Invoice original = createInvoice(NL_VAT);
		original.setSellerVATaxID(SELLER_VAT);
		original.setC_Fiscal_Representation_ID(FISCAL_REPRESENTATION_ID);
		save(original);
		return original;
	}

	private I_C_Invoice createCreditMemo(final I_C_Invoice original)
	{
		final I_C_Invoice creditMemo = createInvoice(null);
		creditMemo.setC_DocTypeTarget_ID(createDocType(X_C_DocType.DOCBASETYPE_ARCreditMemo));
		creditMemo.setRef_Invoice_ID(original.getC_Invoice_ID());
		save(creditMemo);
		return creditMemo;
	}

	private static void assertOriginalVATaxIds(final I_C_Invoice invoice)
	{
		assertThat(invoice.getBuyerVATaxID()).isEqualTo(NL_VAT);
		assertThat(invoice.getSellerVATaxID()).isEqualTo(SELLER_VAT);
		assertThat(invoice.getC_Fiscal_Representation_ID()).isEqualTo(FISCAL_REPRESENTATION_ID);
	}

	/**
	 * A manual invoice whose lines resolve no VAT ID of an address and no fiscal representation completes as before:
	 * here the two shipments went to partners with different VAT IDs on the partner only.
	 */
	@Test
	void manualInvoiceWithoutAddressVATaxIdOrFiscalRepresentation_isNotStamped()
	{
		final I_C_BPartner_Location deliveryPartner1 = createLocation(createBPartner(DE_VAT), deId, null);
		final I_C_BPartner_Location deliveryPartner2 = createLocation(createBPartner(OTHER_DE_VAT), deId, null);
		final I_C_Invoice invoice = createInvoice(null);
		createLineForShipment(invoice, deliveryPartner1);
		createLineForShipment(invoice, deliveryPartner2);

		assertThatCode(() -> stamper.stampOrCheck(invoice)).doesNotThrowAnyException();
		assertThat(invoice.getBuyerVATaxID()).isNull();
	}

	private I_C_Invoice createInvoice(@Nullable final String buyerVATaxId)
	{
		return createInvoice(billToDE, buyerVATaxId);
	}

	private I_C_Invoice createInvoice(final I_C_BPartner_Location billTo, @Nullable final String buyerVATaxId)
	{
		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setAD_Org_ID(orgId);
		invoice.setIsSOTrx(true);
		invoice.setDateInvoiced(DATE);
		invoice.setC_BPartner_ID(billTo.getC_BPartner_ID());
		invoice.setC_BPartner_Location_ID(billTo.getC_BPartner_Location_ID());
		invoice.setC_BPartner_Location_Value_ID(billTo.getC_Location_ID());
		invoice.setBuyerVATaxID(buyerVATaxId);
		save(invoice);
		return invoice;
	}

	private void createCandidateLine(final I_C_Invoice invoice, final I_C_Tax tax)
	{
		final I_C_InvoiceLine line = createLine(invoice, tax);

		final I_C_Invoice_Line_Alloc alloc = newInstance(I_C_Invoice_Line_Alloc.class);
		alloc.setAD_Org_ID(orgId);
		alloc.setC_InvoiceLine_ID(line.getC_InvoiceLine_ID());
		save(alloc);
	}

	private I_C_InvoiceLine createLine(final I_C_Invoice invoice, final I_C_Tax tax)
	{
		final I_C_InvoiceLine line = newInstance(I_C_InvoiceLine.class);
		line.setAD_Org_ID(orgId);
		line.setC_Invoice_ID(invoice.getC_Invoice_ID());
		line.setC_Tax_ID(tax.getC_Tax_ID());
		save(line);
		return line;
	}

	/**
	 * A line with product and tax category, i.e. one the re-tax recomputes.
	 */
	private I_C_InvoiceLine createProductLine(final I_C_Invoice invoice, final I_C_Tax tax)
	{
		final I_C_InvoiceLine line = createLine(invoice, tax);
		line.setM_Product_ID(productId);
		line.setC_TaxCategory_ID(TAX_CATEGORY_ID);
		save(line);
		return line;
	}

	private I_C_OrderLine createOrderLine(final I_C_BPartner_Location delivery, final I_C_Tax tax)
	{
		final I_C_Order order = newInstance(I_C_Order.class);
		order.setAD_Org_ID(orgId);
		order.setIsSOTrx(true);
		save(order);

		final I_C_OrderLine orderLine = newInstance(I_C_OrderLine.class);
		orderLine.setAD_Org_ID(orgId);
		orderLine.setC_Order_ID(order.getC_Order_ID());
		orderLine.setC_BPartner_ID(delivery.getC_BPartner_ID());
		orderLine.setC_BPartner_Location_ID(delivery.getC_BPartner_Location_ID());
		orderLine.setC_BPartner_Location_Value_ID(delivery.getC_Location_ID());
		orderLine.setM_Warehouse_ID(warehouseDEId);
		orderLine.setDatePromised(DATE);
		orderLine.setC_Tax_ID(tax.getC_Tax_ID());
		save(orderLine);
		return orderLine;
	}

	private int createWarehouse(final I_C_BPartner_Location warehouseLocation)
	{
		final I_M_Warehouse warehouse = newInstance(I_M_Warehouse.class);
		warehouse.setAD_Org_ID(orgId);
		warehouse.setC_BPartner_ID(warehouseLocation.getC_BPartner_ID());
		warehouse.setC_BPartner_Location_ID(warehouseLocation.getC_BPartner_Location_ID());
		save(warehouse);
		return warehouse.getM_Warehouse_ID();
	}

	private I_M_InOutLine createShipmentLine(final I_C_BPartner_Location delivery)
	{
		final I_M_InOut shipment = newInstance(I_M_InOut.class);
		shipment.setAD_Org_ID(orgId);
		shipment.setIsSOTrx(true);
		shipment.setMovementDate(DATE);
		shipment.setC_BPartner_ID(delivery.getC_BPartner_ID());
		shipment.setC_BPartner_Location_ID(delivery.getC_BPartner_Location_ID());
		shipment.setC_BPartner_Location_Value_ID(delivery.getC_Location_ID());
		save(shipment);

		final I_M_InOutLine shipmentLine = newInstance(I_M_InOutLine.class);
		shipmentLine.setAD_Org_ID(orgId);
		shipmentLine.setM_InOut_ID(shipment.getM_InOut_ID());
		save(shipmentLine);
		return shipmentLine;
	}

	private void createLineForShipment(final I_C_Invoice invoice, final I_C_BPartner_Location delivery)
	{
		final I_C_InvoiceLine line = createLine(invoice, taxDENL0Exempt);
		line.setM_InOutLine_ID(createShipmentLine(delivery).getM_InOutLine_ID());
		save(line);
	}

	private I_C_BPartner createBPartner(@Nullable final String vatTaxId)
	{
		final I_C_BPartner bpartner = newInstance(I_C_BPartner.class);
		bpartner.setC_BP_Group_ID(group.getC_BP_Group_ID());
		bpartner.setVATaxID(vatTaxId);
		save(bpartner);
		return bpartner;
	}

	private static int createDocType(final String docBaseType)
	{
		final I_C_DocType docType = newInstance(I_C_DocType.class);
		docType.setDocBaseType(docBaseType);
		save(docType);
		return docType.getC_DocType_ID();
	}

	private static int createCountry(final String countryCode)
	{
		final I_C_Country country = newInstance(I_C_Country.class);
		country.setCountryCode(countryCode);
		save(country);
		return country.getC_Country_ID();
	}

	private I_C_BPartner_Location createLocation(final I_C_BPartner bpartner, final int countryId, @Nullable final String vatTaxId)
	{
		final I_C_Location location = newInstance(I_C_Location.class);
		location.setAD_Org_ID(orgId);
		location.setC_Country_ID(countryId);
		save(location);

		final I_C_BPartner_Location bpartnerLocation = newInstance(I_C_BPartner_Location.class);
		bpartnerLocation.setAD_Org_ID(orgId);
		bpartnerLocation.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		bpartnerLocation.setC_Location_ID(location.getC_Location_ID());
		bpartnerLocation.setVATaxID(vatTaxId);
		save(bpartnerLocation);
		return bpartnerLocation;
	}

	private I_C_Tax createTax(final boolean requiresTaxCertificate, final int fromCountryId, final int toCountryId)
	{
		final I_C_Tax tax = newInstance(I_C_Tax.class);
		tax.setAD_Org_ID(orgId);
		tax.setName(requiresTaxCertificate ? "exempt" : "standard");
		tax.setC_Country_ID(fromCountryId);
		tax.setTo_Country_ID(toCountryId);
		tax.setRequiresTaxCertificate(requiresTaxCertificate ? "Y" : "N");
		tax.setIsSmallbusiness(null);
		tax.setIsFiscalRepresentation(null);
		tax.setValidFrom(TimeUtil.parseTimestamp("2020-01-01"));
		tax.setValidTo(TimeUtil.parseTimestamp("9999-12-31"));
		tax.setSOPOType(SOPOType.SALES_TAX.getCode());
		tax.setC_TaxCategory_ID(TAX_CATEGORY_ID);
		tax.setRate(requiresTaxCertificate ? BigDecimal.ZERO : new BigDecimal("19"));
		save(tax);
		return tax;
	}
}
