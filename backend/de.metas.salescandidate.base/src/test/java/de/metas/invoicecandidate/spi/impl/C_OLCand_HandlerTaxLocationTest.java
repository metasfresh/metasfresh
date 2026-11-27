/*
 * #%L
 * de.metas.salescandidate.base
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

package de.metas.invoicecandidate.spi.impl;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.invoice.vatid.TaxLocation;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.location.CountryId;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.organization.OrgId;
import de.metas.organization.StoreCreditCardNumberMode;
import de.metas.util.Services;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Where, by whom and when an order candidate's invoice candidate is taxed, and with which ship-from country (see {@link TaxLocation}).
 */
public class C_OLCand_HandlerTaxLocationTest
{
	private OrgId orgId;
	private CountryId orgCountryId;

	private I_C_BPartner buyer;
	private BPartnerLocationId buyerLocationId;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();

		orgCountryId = createCountry();
		final I_AD_Org org = newInstance(I_AD_Org.class);
		save(org);
		orgId = OrgId.ofRepoId(org.getAD_Org_ID());

		final I_C_BPartner orgPartner = createPartner();
		final BPartnerLocationId orgLocationId = createLocation(orgPartner, orgCountryId);
		final I_AD_OrgInfo orgInfo = newInstance(I_AD_OrgInfo.class);
		orgInfo.setAD_Org_ID(orgId.getRepoId());
		orgInfo.setOrg_BPartner_ID(orgPartner.getC_BPartner_ID());
		orgInfo.setOrgBP_Location_ID(orgLocationId.getRepoId());
		orgInfo.setStoreCreditCardData(StoreCreditCardNumberMode.DONT_STORE.getCode());
		save(orgInfo);

		buyer = createPartner();
		buyerLocationId = createLocation(buyer, createCountry());
	}

	@Test
	void dropShipPartner_isTaxLocation()
	{
		final I_C_BPartner dropShipPartner = createPartner();
		final BPartnerLocationId dropShipLocationId = createLocation(dropShipPartner, createCountry());

		final I_C_OLCand olCand = createOLCand();
		olCand.setDropShip_BPartner_Override_ID(dropShipLocationId.getBpartnerId().getRepoId());
		olCand.setDropShip_Location_Override_ID(dropShipLocationId.getRepoId());
		olCand.setDatePromised_Override(Timestamp.valueOf("2026-03-04 00:00:00"));
		olCand.setDatePromised(Timestamp.valueOf("2026-03-05 00:00:00"));
		olCand.setPresetDateInvoiced(Timestamp.valueOf("2026-03-06 00:00:00"));
		save(olCand);

		final TaxLocation result = new C_OLCand_Handler().getTaxLocation(createIC(olCand)).orElseThrow(AssertionError::new);

		assertThat(result.getBpartnerLocationId()).isEqualTo(dropShipLocationId);
		assertThat(result.getOrgId()).isEqualTo(orgId);
		assertThat(result.getTaxDate()).isEqualTo(Timestamp.valueOf("2026-03-04 00:00:00").toInstant());
		assertThat(result.getFromCountryId()).isEqualTo(orgCountryId);
	}

	@Test
	void noDropShip_buyerIsTaxLocation()
	{
		final I_C_OLCand olCand = createOLCand();
		olCand.setPresetDateInvoiced(Timestamp.valueOf("2026-03-06 00:00:00"));
		save(olCand);

		final TaxLocation result = new C_OLCand_Handler().getTaxLocation(createIC(olCand)).orElseThrow(AssertionError::new);

		assertThat(result.getBpartnerLocationId()).isEqualTo(buyerLocationId);
		assertThat(result.getOrgId()).isEqualTo(orgId);
		assertThat(result.getTaxDate()).isEqualTo(Timestamp.valueOf("2026-03-06 00:00:00").toInstant());
		assertThat(result.getFromCountryId()).isEqualTo(orgCountryId);
	}

	@Test
	void datePromisedOnly_isTaxDate()
	{
		final I_C_OLCand olCand = createOLCand();
		olCand.setDatePromised(Timestamp.valueOf("2026-03-05 00:00:00"));
		olCand.setPresetDateInvoiced(Timestamp.valueOf("2026-03-06 00:00:00"));
		save(olCand);

		final TaxLocation result = new C_OLCand_Handler().getTaxLocation(createIC(olCand)).orElseThrow(AssertionError::new);

		assertThat(result.getTaxDate()).isEqualTo(Timestamp.valueOf("2026-03-05 00:00:00").toInstant());
	}

	private I_C_OLCand createOLCand()
	{
		final I_C_OLCand olCand = newInstance(I_C_OLCand.class);
		olCand.setAD_Org_ID(orgId.getRepoId());
		olCand.setC_BPartner_ID(buyer.getC_BPartner_ID());
		olCand.setC_BPartner_Location_ID(buyerLocationId.getRepoId());
		save(olCand);
		return olCand;
	}

	private I_C_Invoice_Candidate createIC(final I_C_OLCand olCand)
	{
		final I_C_Invoice_Candidate ic = newInstance(I_C_Invoice_Candidate.class);
		ic.setAD_Org_ID(orgId.getRepoId());
		ic.setAD_Table_ID(Services.get(IADTableDAO.class).retrieveTableId(I_C_OLCand.Table_Name));
		ic.setRecord_ID(olCand.getC_OLCand_ID());
		save(ic);
		return ic;
	}

	private I_C_BPartner createPartner()
	{
		final I_C_BPartner partner = newInstance(I_C_BPartner.class);
		save(partner);
		return partner;
	}

	private BPartnerLocationId createLocation(final I_C_BPartner partner, final CountryId countryId)
	{
		final I_C_Location location = newInstance(I_C_Location.class);
		location.setC_Country_ID(countryId.getRepoId());
		save(location);
		final I_C_BPartner_Location bpl = newInstance(I_C_BPartner_Location.class);
		bpl.setC_BPartner_ID(partner.getC_BPartner_ID());
		bpl.setC_Location_ID(location.getC_Location_ID());
		save(bpl);
		return BPartnerLocationId.ofRepoId(partner.getC_BPartner_ID(), bpl.getC_BPartner_Location_ID());
	}

	private CountryId createCountry()
	{
		final I_C_Country country = newInstance(I_C_Country.class);
		save(country);
		return CountryId.ofRepoId(country.getC_Country_ID());
	}
}
