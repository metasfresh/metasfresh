/*
 * #%L
 * de.metas.contracts
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

package de.metas.contracts.invoicecandidate;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.location.CountryId;
import de.metas.organization.OrgId;
import de.metas.organization.StoreCreditCardNumberMode;
import lombok.Getter;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Location;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

/**
 * Test data shared by the tax location tests of the contract invoice candidate handlers: an org whose address is in a known country,
 * plus partner locations in other countries.
 */
@Getter
public class TaxLocationTestFixture
{
	private final OrgId orgId;
	private final CountryId orgCountryId;

	public TaxLocationTestFixture()
	{
		orgCountryId = createCountry();
		final I_AD_Org org = newInstance(I_AD_Org.class);
		save(org);
		orgId = OrgId.ofRepoId(org.getAD_Org_ID());

		final BPartnerLocationId orgLocationId = createLocation(orgCountryId);
		final I_AD_OrgInfo orgInfo = newInstance(I_AD_OrgInfo.class);
		orgInfo.setAD_Org_ID(orgId.getRepoId());
		orgInfo.setOrg_BPartner_ID(orgLocationId.getBpartnerId().getRepoId());
		orgInfo.setOrgBP_Location_ID(orgLocationId.getRepoId());
		orgInfo.setStoreCreditCardData(StoreCreditCardNumberMode.DONT_STORE.getCode());
		save(orgInfo);
	}

	/** A new partner with one location in a new country. */
	public BPartnerLocationId createLocation()
	{
		return createLocation(createCountry());
	}

	public BPartnerLocationId createLocation(final CountryId countryId)
	{
		final I_C_BPartner partner = newInstance(I_C_BPartner.class);
		save(partner);

		final I_C_Location location = newInstance(I_C_Location.class);
		location.setC_Country_ID(countryId.getRepoId());
		save(location);

		final I_C_BPartner_Location bpl = newInstance(I_C_BPartner_Location.class);
		bpl.setC_BPartner_ID(partner.getC_BPartner_ID());
		bpl.setC_Location_ID(location.getC_Location_ID());
		save(bpl);
		return BPartnerLocationId.ofRepoId(partner.getC_BPartner_ID(), bpl.getC_BPartner_Location_ID());
	}

	private static CountryId createCountry()
	{
		final I_C_Country country = newInstance(I_C_Country.class);
		save(country);
		return CountryId.ofRepoId(country.getC_Country_ID());
	}
}
