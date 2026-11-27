/*
 * #%L
 * de.metas.business
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
package de.metas.invoice.vatid;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.location.CountryId;
import de.metas.organization.FiscalRepresentationId;
import de.metas.organization.OrgId;
import de.metas.tax.api.TaxId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Fiscal_Representation;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Tax;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.sql.Timestamp;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

public class InvoiceVATaxIdServiceTest
{
	private static final OrgId ORG = OrgId.ofRepoId(1000000);
	private static final Timestamp TAX_DATE = Timestamp.valueOf("2026-06-22 00:00:00");

	private InvoiceVATaxIdService service;
	private CountryId nl;
	private CountryId gr;
	private CountryId de;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		service = InvoiceVATaxIdService.newInstanceForUnitTesting();
		nl = createCountry("NL");
		gr = createCountry("GR");
		de = createCountry("DE");
	}

	@Test
	public void nlLocation_noRegistration()
	{
		final BPartnerLocationId location = createLocation(nl, "NL333333330B01");

		final InvoiceVATaxIds result = service.resolve(taxLocation(location), createTax(true));

		assertThat(result).isEqualTo(new InvoiceVATaxIds("NL333333330B01", null, null));
	}

	@Test
	public void grLocation_ownRegistration()
	{
		final BPartnerLocationId location = createLocation(gr, null);
		final I_C_Fiscal_Representation row = createFiscalRepresentation(gr, "EL094259216", null);

		final InvoiceVATaxIds result = service.resolve(taxLocation(location), createTax(true));

		assertThat(result).isEqualTo(new InvoiceVATaxIds(
				null,
				"EL094259216",
				FiscalRepresentationId.ofRepoId(row.getC_Fiscal_Representation_ID())));
	}

	@Test
	public void grLocation_viaRepresentative_rowWithoutVAT_usesRepresentativeVAT()
	{
		final BPartnerLocationId location = createLocation(gr, null);
		final I_C_BPartner representative = createBPartner("EL998877666");
		final I_C_Fiscal_Representation row = createFiscalRepresentation(gr, null, representative);

		final InvoiceVATaxIds result = service.resolve(taxLocation(location), createTax(true));

		assertThat(result).isEqualTo(new InvoiceVATaxIds(
				null,
				"EL998877666",
				FiscalRepresentationId.ofRepoId(row.getC_Fiscal_Representation_ID())));
	}

	@Test
	public void grLocation_ownRegistration_keepsBuyerVAT()
	{
		final BPartnerLocationId location = createLocation(gr, "EL111111111");
		final I_C_Fiscal_Representation row = createFiscalRepresentation(gr, "EL094259216", null);

		final InvoiceVATaxIds result = service.resolve(taxLocation(location), createTax(true));

		assertThat(result).isEqualTo(new InvoiceVATaxIds(
				"EL111111111",
				"EL094259216",
				FiscalRepresentationId.ofRepoId(row.getC_Fiscal_Representation_ID())));
	}

	/**
	 * A registration without any VAT ID (own or representative's): the registration is still recorded,
	 * the seller VAT ID stays NULL (readers fall back to the organization's main VAT ID).
	 */
	@Test
	public void grLocation_registrationWithoutAnyVAT_sellerNullButRegistrationRecorded()
	{
		final BPartnerLocationId location = createLocation(gr, null);
		final I_C_BPartner representative = createBPartner(null);
		final I_C_Fiscal_Representation row = createFiscalRepresentation(gr, null, representative);

		final InvoiceVATaxIds result = service.resolve(taxLocation(location), createTax(true));

		assertThat(result).isEqualTo(new InvoiceVATaxIds(
				null,
				null,
				FiscalRepresentationId.ofRepoId(row.getC_Fiscal_Representation_ID())));
	}

	/**
	 * D16: the registration applies only if the line's tax has IsFiscalRepresentation='Y'.
	 */
	@Test
	public void grLocation_registration_lineTaxNotFiscalRep_mainVat()
	{
		final BPartnerLocationId location = createLocation(gr, null);
		createFiscalRepresentation(gr, "EL094259216", null);

		final InvoiceVATaxIds result = service.resolve(taxLocation(location), createTax(false));

		assertThat(result).isEqualTo(new InvoiceVATaxIds(null, null, null));
	}

	private TaxLocation taxLocation(final BPartnerLocationId location)
	{
		return new TaxLocation(location, ORG, TAX_DATE, de);
	}

	private static I_C_BPartner createBPartner(@Nullable final String vatTaxId)
	{
		final I_C_BP_Group group = InterfaceWrapperHelper.newInstance(I_C_BP_Group.class);
		group.setIsDeviatingBillBPartner(false);
		saveRecord(group);

		final I_C_BPartner bpartner = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
		bpartner.setC_BP_Group_ID(group.getC_BP_Group_ID());
		bpartner.setVATaxID(vatTaxId);
		saveRecord(bpartner);
		return bpartner;
	}

	private static CountryId createCountry(final String code)
	{
		final I_C_Country country = InterfaceWrapperHelper.newInstance(I_C_Country.class);
		country.setCountryCode(code);
		saveRecord(country);
		return CountryId.ofRepoId(country.getC_Country_ID());
	}

	private static BPartnerLocationId createLocation(final CountryId countryId, @Nullable final String locationVATaxId)
	{
		final I_C_BPartner bpartner = createBPartner(null);

		final I_C_Location location = InterfaceWrapperHelper.newInstance(I_C_Location.class);
		location.setC_Country_ID(countryId.getRepoId());
		saveRecord(location);

		final I_C_BPartner_Location bpLocation = InterfaceWrapperHelper.newInstance(I_C_BPartner_Location.class);
		bpLocation.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		bpLocation.setC_Location_ID(location.getC_Location_ID());
		bpLocation.setVATaxID(locationVATaxId);
		saveRecord(bpLocation);

		return BPartnerLocationId.ofRepoId(bpartner.getC_BPartner_ID(), bpLocation.getC_BPartner_Location_ID());
	}

	private static I_C_Fiscal_Representation createFiscalRepresentation(
			final CountryId toCountryId,
			@Nullable final String vatTaxId,
			@Nullable final I_C_BPartner representative)
	{
		final I_C_Fiscal_Representation row = InterfaceWrapperHelper.newInstance(I_C_Fiscal_Representation.class);
		row.setAD_Org_ID(ORG.getRepoId());
		row.setTo_Country_ID(toCountryId.getRepoId());
		row.setValidFrom(Timestamp.valueOf("2026-06-01 00:00:00"));
		row.setVATaxID(vatTaxId);
		row.setTaxID("TAX");
		if (representative != null)
		{
			row.setC_BPartner_Representative_ID(representative.getC_BPartner_ID());
		}
		saveRecord(row);
		return row;
	}

	private static TaxId createTax(final boolean isFiscalRepresentation)
	{
		final I_C_Tax tax = InterfaceWrapperHelper.newInstance(I_C_Tax.class);
		tax.setName("test-tax");
		tax.setAD_Org_ID(ORG.getRepoId());
		tax.setValidFrom(Timestamp.valueOf("2026-01-01 00:00:00"));
		tax.setC_TaxCategory_ID(1);
		tax.setSOPOType("B");
		tax.setIsFiscalRepresentation(isFiscalRepresentation ? "Y" : "N");
		saveRecord(tax);
		return TaxId.ofRepoId(tax.getC_Tax_ID());
	}
}
