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
import de.metas.tax.api.SOPOType;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * The completion check of a prepared candidate invoice (lines with invoice candidate allocations) to which lines were added by hand.
 */
class C_InvoiceVATaxIdCheckTest
{
	private static final int ORG_ID = 10;
	private static final Timestamp DATE = TimeUtil.parseTimestamp("2026-09-02");
	private static final String DE_VAT = "DE123456788";
	private static final String NL_VAT = "NL123456782B01";

	private InvoiceVATaxIdStamper stamper;

	private I_C_BPartner_Location billToDE;
	private I_C_BPartner_Location deliveryNL;
	private I_C_Tax taxDENL0Exempt;
	private I_C_Tax taxDEDE19;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
		stamper = InvoiceVATaxIdStamper.newInstanceForUnitTesting();

		final int deId = createCountry("DE");
		final int nlId = createCountry("NL");

		final I_C_BP_Group group = newInstance(I_C_BP_Group.class);
		group.setIsDeviatingBillBPartner(false);
		save(group);

		final I_C_BPartner customer = newInstance(I_C_BPartner.class);
		customer.setC_BP_Group_ID(group.getC_BP_Group_ID());
		customer.setVATaxID(DE_VAT);
		save(customer);

		billToDE = createLocation(customer, deId, DE_VAT);
		deliveryNL = createLocation(customer, nlId, NL_VAT);

		taxDENL0Exempt = createTax(true);
		taxDEDE19 = createTax(false);
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

	private I_C_Invoice createInvoice(@Nullable final String buyerVATaxId)
	{
		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setAD_Org_ID(ORG_ID);
		invoice.setIsSOTrx(true);
		invoice.setDateInvoiced(DATE);
		invoice.setC_BPartner_ID(billToDE.getC_BPartner_ID());
		invoice.setC_BPartner_Location_ID(billToDE.getC_BPartner_Location_ID());
		invoice.setC_BPartner_Location_Value_ID(billToDE.getC_Location_ID());
		invoice.setBuyerVATaxID(buyerVATaxId);
		save(invoice);
		return invoice;
	}

	private void createCandidateLine(final I_C_Invoice invoice, final I_C_Tax tax)
	{
		final I_C_InvoiceLine line = createLine(invoice, tax);

		final I_C_Invoice_Line_Alloc alloc = newInstance(I_C_Invoice_Line_Alloc.class);
		alloc.setAD_Org_ID(ORG_ID);
		alloc.setC_InvoiceLine_ID(line.getC_InvoiceLine_ID());
		save(alloc);
	}

	private I_C_InvoiceLine createLine(final I_C_Invoice invoice, final I_C_Tax tax)
	{
		final I_C_InvoiceLine line = newInstance(I_C_InvoiceLine.class);
		line.setAD_Org_ID(ORG_ID);
		line.setC_Invoice_ID(invoice.getC_Invoice_ID());
		line.setC_Tax_ID(tax.getC_Tax_ID());
		save(line);
		return line;
	}

	private I_M_InOutLine createShipmentLine(final I_C_BPartner_Location delivery)
	{
		final I_M_InOut shipment = newInstance(I_M_InOut.class);
		shipment.setAD_Org_ID(ORG_ID);
		shipment.setIsSOTrx(true);
		shipment.setMovementDate(DATE);
		shipment.setC_BPartner_ID(delivery.getC_BPartner_ID());
		shipment.setC_BPartner_Location_ID(delivery.getC_BPartner_Location_ID());
		shipment.setC_BPartner_Location_Value_ID(delivery.getC_Location_ID());
		save(shipment);

		final I_M_InOutLine shipmentLine = newInstance(I_M_InOutLine.class);
		shipmentLine.setAD_Org_ID(ORG_ID);
		shipmentLine.setM_InOut_ID(shipment.getM_InOut_ID());
		save(shipmentLine);
		return shipmentLine;
	}

	private static int createCountry(final String countryCode)
	{
		final I_C_Country country = newInstance(I_C_Country.class);
		country.setCountryCode(countryCode);
		save(country);
		return country.getC_Country_ID();
	}

	private static I_C_BPartner_Location createLocation(final I_C_BPartner bpartner, final int countryId, final String vatTaxId)
	{
		final I_C_Location location = newInstance(I_C_Location.class);
		location.setAD_Org_ID(ORG_ID);
		location.setC_Country_ID(countryId);
		save(location);

		final I_C_BPartner_Location bpartnerLocation = newInstance(I_C_BPartner_Location.class);
		bpartnerLocation.setAD_Org_ID(ORG_ID);
		bpartnerLocation.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		bpartnerLocation.setC_Location_ID(location.getC_Location_ID());
		bpartnerLocation.setVATaxID(vatTaxId);
		save(bpartnerLocation);
		return bpartnerLocation;
	}

	private static I_C_Tax createTax(final boolean requiresTaxCertificate)
	{
		final I_C_Tax tax = newInstance(I_C_Tax.class);
		tax.setAD_Org_ID(ORG_ID);
		tax.setName(requiresTaxCertificate ? "exempt" : "standard");
		tax.setRequiresTaxCertificate(requiresTaxCertificate ? "Y" : "N");
		tax.setIsSmallbusiness(null);
		tax.setIsFiscalRepresentation(null);
		tax.setValidFrom(TimeUtil.parseTimestamp("2020-01-01"));
		tax.setSOPOType(SOPOType.SALES_TAX.getCode());
		tax.setC_TaxCategory_ID(44);
		tax.setRate(requiresTaxCertificate ? BigDecimal.ZERO : new BigDecimal("19"));
		save(tax);
		return tax;
	}
}
