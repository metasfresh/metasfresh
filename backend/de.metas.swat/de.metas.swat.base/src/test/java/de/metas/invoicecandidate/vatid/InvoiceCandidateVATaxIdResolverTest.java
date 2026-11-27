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

import de.metas.bpartner.BPartnerLocationId;
import de.metas.invoice.vatid.InvoiceVATaxIdService;
import de.metas.invoice.vatid.InvoiceVATaxIds;
import de.metas.invoice.vatid.TaxLocation;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.pricing.tax.ProductTaxCategoryRepository;
import de.metas.pricing.tax.ProductTaxCategoryService;
import de.metas.organization.OrgId;
import de.metas.tax.api.TaxId;
import de.metas.util.Services;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Tax;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.annotation.Nullable;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

class InvoiceCandidateVATaxIdResolverTest
{
	private InvoiceCandidateVATaxIdResolver resolver;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
		SpringContextHolder.registerJUnitBean(new ProductTaxCategoryService(new ProductTaxCategoryRepository())); // needed by InvoiceLineBL
		resolver = InvoiceCandidateVATaxIdResolver.newInstanceForUnitTesting();
	}

	@Test
	void purchaseCandidate_notResolved()
	{
		// purchase invoices keep their VAT ID columns empty, and their aggregation key unchanged;
		// the candidate has no tax, location or handler: a resolution would fail on it
		final I_C_Invoice_Candidate ic = newInstance(I_C_Invoice_Candidate.class);
		ic.setIsSOTrx(false);
		save(ic);

		assertThat(resolver.resolve(ic, null, resolver.newCache())).isNull();
	}

	/**
	 * A customer who cannot use VAT IDs by delivery (no fiscal representation of any org, no partner location with a VAT ID of its own)
	 * pays nothing per candidate whose tax is not exempt on a VAT ID: neither the candidate's tax location nor the tax of its line is looked up.
	 */
	@Test
	void noFiscalRepresentationAndNoLocationVATaxId_noLocationOrTaxLookup()
	{
		final InvoiceCandidateVATaxIdResolver resolverWithFailingLookups = newResolverWithFailingLookups(null);

		// a sales candidate without location or handler: any location lookup would fail on it; its (cached) tax is not exempt
		final I_C_Invoice_Candidate ic = newInstance(I_C_Invoice_Candidate.class);
		ic.setIsSOTrx(true);
		ic.setC_Tax_ID(createTax(false).getRepoId());
		save(ic);

		assertThat(resolverWithFailingLookups.resolve(ic, null, resolverWithFailingLookups.newCache())).isEqualTo(InvoiceVATaxIds.NONE);
	}

	/**
	 * The short-circuit only skips work, it never changes a result: a location that carries its partner's own VAT ID is still resolved,
	 * because it is billed to another partner (e.g. a drop-ship third party or central billing) whose VAT ID differs.
	 */
	@Test
	void noFiscalRepresentation_locationWithItsPartnersVATaxId_billedToAnotherPartner_isResolved()
	{
		final I_C_BPartner thirdParty = newInstance(I_C_BPartner.class);
		thirdParty.setVATaxID("NL112233442B01");
		save(thirdParty);
		final I_C_BPartner_Location thirdPartyLocation = newInstance(I_C_BPartner_Location.class);
		thirdPartyLocation.setC_BPartner_ID(thirdParty.getC_BPartner_ID());
		thirdPartyLocation.setVATaxID("NL112233442B01");
		save(thirdPartyLocation);
		final BPartnerLocationId thirdPartyLocationId = BPartnerLocationId.ofRepoId(thirdParty.getC_BPartner_ID(), thirdPartyLocation.getC_BPartner_Location_ID());

		final I_C_BPartner billPartner = newInstance(I_C_BPartner.class);
		billPartner.setVATaxID("DE123456788");
		save(billPartner);

		final I_C_Invoice_Candidate ic = newInstance(I_C_Invoice_Candidate.class);
		ic.setIsSOTrx(true);
		ic.setBill_BPartner_ID(billPartner.getC_BPartner_ID());
		save(ic);

		final TaxLocation taxLocation = new TaxLocation(thirdPartyLocationId, OrgId.ofRepoId(ic.getAD_Org_ID()), TimeUtil.parseTimestamp("2026-09-02"), null);
		final InvoiceCandidateVATaxIdResolver resolverWithFailingTaxLookup = newResolverWithFailingLookups(taxLocation);

		assertThat(resolverWithFailingTaxLookup.resolve(ic, null, resolverWithFailingTaxLookup.newCache()))
				.isEqualTo(new InvoiceVATaxIds("NL112233442B01", null, null));
	}

	/**
	 * Without a fiscal representation of the org no line tax can give a seller VAT ID: the buyer VAT ID of the delivery address is resolved without looking up the line's tax.
	 */
	@Test
	void noFiscalRepresentation_locationVATaxId_buyerOnlyWithoutTaxLookup()
	{
		final I_C_BPartner customer = newInstance(I_C_BPartner.class);
		customer.setVATaxID("DE123456788");
		save(customer);
		final I_C_BPartner_Location delivery = newInstance(I_C_BPartner_Location.class);
		delivery.setC_BPartner_ID(customer.getC_BPartner_ID());
		delivery.setVATaxID("NL123456782B01");
		save(delivery);
		final BPartnerLocationId deliveryId = BPartnerLocationId.ofRepoId(customer.getC_BPartner_ID(), delivery.getC_BPartner_Location_ID());

		final I_C_Invoice_Candidate ic = newInstance(I_C_Invoice_Candidate.class);
		ic.setIsSOTrx(true);
		ic.setBill_BPartner_ID(customer.getC_BPartner_ID());
		save(ic);

		final TaxLocation taxLocation = new TaxLocation(deliveryId, OrgId.ofRepoId(ic.getAD_Org_ID()), TimeUtil.parseTimestamp("2026-09-02"), null);
		final InvoiceCandidateVATaxIdResolver resolverWithFailingTaxLookup = newResolverWithFailingLookups(taxLocation);

		final InvoiceVATaxIds vatTaxIds = resolverWithFailingTaxLookup.resolve(ic, null, resolverWithFailingTaxLookup.newCache());
		assertThat(vatTaxIds).isEqualTo(new InvoiceVATaxIds("NL123456782B01", null, null));
	}

	/**
	 * The short-circuit never changes a result: without fiscal representations and location VAT IDs, a candidate exempt on the VAT ID of a drop-ship third party
	 * (on its partner only) is resolved to that VAT ID, which justified the exemption.
	 */
	@Test
	void noFiscalRepresentationAndNoLocationVATaxId_exemptOnThirdPartyHeaderVATaxId_isResolved()
	{
		final I_C_BPartner thirdParty = newInstance(I_C_BPartner.class);
		thirdParty.setVATaxID("NL112233442B01");
		save(thirdParty);
		final I_C_BPartner_Location thirdPartyLocation = newInstance(I_C_BPartner_Location.class);
		thirdPartyLocation.setC_BPartner_ID(thirdParty.getC_BPartner_ID());
		save(thirdPartyLocation);
		final BPartnerLocationId thirdPartyLocationId = BPartnerLocationId.ofRepoId(thirdParty.getC_BPartner_ID(), thirdPartyLocation.getC_BPartner_Location_ID());

		final I_C_BPartner billPartner = newInstance(I_C_BPartner.class);
		billPartner.setVATaxID("DE123456788");
		save(billPartner);

		final I_C_Invoice_Candidate ic = newInstance(I_C_Invoice_Candidate.class);
		ic.setIsSOTrx(true);
		ic.setBill_BPartner_ID(billPartner.getC_BPartner_ID());
		ic.setC_Tax_ID(createTax(true).getRepoId());
		save(ic);

		final TaxLocation taxLocation = new TaxLocation(thirdPartyLocationId, OrgId.ofRepoId(ic.getAD_Org_ID()), TimeUtil.parseTimestamp("2026-09-02"), null);
		registerHandlerBL(taxLocation);
		final InvoiceCandidateVATaxIdResolver resolverWithLineTax = new InvoiceCandidateVATaxIdResolver(InvoiceVATaxIdService.newInstanceForUnitTesting(), new InvoiceCandidateLineTaxResolver());

		assertThat(resolverWithLineTax.resolve(ic, null, resolverWithLineTax.newCache()))
				.isEqualTo(new InvoiceVATaxIds("NL112233442B01", null, null));
	}

	/**
	 * Central billing: the same candidate with a tax that is not exempt keeps the bill partner's VAT ID (nothing stored), as before.
	 */
	@Test
	void noFiscalRepresentationAndNoLocationVATaxId_notExemptForThirdParty_none()
	{
		final I_C_BPartner thirdParty = newInstance(I_C_BPartner.class);
		thirdParty.setVATaxID("DE111111125");
		save(thirdParty);
		final I_C_BPartner_Location thirdPartyLocation = newInstance(I_C_BPartner_Location.class);
		thirdPartyLocation.setC_BPartner_ID(thirdParty.getC_BPartner_ID());
		save(thirdPartyLocation);
		final BPartnerLocationId thirdPartyLocationId = BPartnerLocationId.ofRepoId(thirdParty.getC_BPartner_ID(), thirdPartyLocation.getC_BPartner_Location_ID());

		final I_C_BPartner billPartner = newInstance(I_C_BPartner.class);
		billPartner.setVATaxID("DE123456788");
		save(billPartner);

		final I_C_Invoice_Candidate ic = newInstance(I_C_Invoice_Candidate.class);
		ic.setIsSOTrx(true);
		ic.setBill_BPartner_ID(billPartner.getC_BPartner_ID());
		ic.setC_Tax_ID(createTax(false).getRepoId());
		save(ic);

		final TaxLocation taxLocation = new TaxLocation(thirdPartyLocationId, OrgId.ofRepoId(ic.getAD_Org_ID()), TimeUtil.parseTimestamp("2026-09-02"), null);
		registerHandlerBL(taxLocation);
		final InvoiceCandidateVATaxIdResolver resolverWithLineTax = new InvoiceCandidateVATaxIdResolver(InvoiceVATaxIdService.newInstanceForUnitTesting(), new InvoiceCandidateLineTaxResolver());

		assertThat(resolverWithLineTax.resolve(ic, null, resolverWithLineTax.newCache())).isEqualTo(InvoiceVATaxIds.NONE);
	}

	private static TaxId createTax(final boolean requiresTaxCertificate)
	{
		final I_C_Tax tax = newInstance(I_C_Tax.class);
		tax.setName("test-tax");
		tax.setValidFrom(TimeUtil.parseTimestamp("2026-01-01"));
		tax.setC_TaxCategory_ID(1);
		tax.setSOPOType("B");
		tax.setRequiresTaxCertificate(requiresTaxCertificate ? "Y" : "N");
		save(tax);
		return TaxId.ofRepoId(tax.getC_Tax_ID());
	}

	private static void registerHandlerBL(final TaxLocation taxLocation)
	{
		final IInvoiceCandidateHandlerBL handlerBL = Mockito.mock(
				IInvoiceCandidateHandlerBL.class,
				invocation -> {
					if ("getTaxLocation".equals(invocation.getMethod().getName()))
					{
						return taxLocation;
					}
					throw new AssertionError("unexpected handler call: " + invocation);
				});
		Services.registerService(IInvoiceCandidateHandlerBL.class, handlerBL);
	}

	/**
	 * @param taxLocation the tax location the candidate handlers report; {@code null}: looking it up fails the test. Looking up the line's tax always fails the test.
	 */
	private static InvoiceCandidateVATaxIdResolver newResolverWithFailingLookups(@Nullable final TaxLocation taxLocation)
	{
		final IInvoiceCandidateHandlerBL handlerBL = Mockito.mock(
				IInvoiceCandidateHandlerBL.class,
				invocation -> {
					if (taxLocation != null && "getTaxLocation".equals(invocation.getMethod().getName()))
					{
						return taxLocation;
					}
					throw new AssertionError("the candidate's tax location must not be looked up: " + invocation);
				});
		Services.registerService(IInvoiceCandidateHandlerBL.class, handlerBL);

		final InvoiceCandidateLineTaxResolver lineTaxResolver = Mockito.mock(
				InvoiceCandidateLineTaxResolver.class,
				invocation -> {
					throw new AssertionError("the line's tax must not be looked up: " + invocation);
				});
		return new InvoiceCandidateVATaxIdResolver(InvoiceVATaxIdService.newInstanceForUnitTesting(), lineTaxResolver);
	}
}
