/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.tax.api;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.location.ICountryAreaBL;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_CountryArea;
import org.compiere.model.I_C_CountryArea_Assign;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Tax;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static de.metas.tax.api.TypeOfDestCountry.DOMESTIC;
import static de.metas.tax.api.TypeOfDestCountry.OUTSIDE_COUNTRY_AREA;
import static de.metas.tax.api.TypeOfDestCountry.WITHIN_COUNTRY_AREA;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class TaxDAOTest
{
	private static final int C_TAX_CATEGORY_ID = 42;
	private static final Instant DATE_OF_INTEREST = new Date().toInstant();
	private static final Timestamp VALID_FROM = TimeUtil.addDays(new Date(), -1);
	private static final Timestamp VALID_TO = TimeUtil.addDays(new Date(), 1);
	private static final OrgId ORG_ID = OrgId.ofRepoId(10);
	private static final int ORG_COUNTRY_ID = 1000;
	private static final String ORG_COUNTRY_CODE = "XX";
	private static final int EU_COUNTRY_ID = 1001;
	private static final String EU_COUNTRY_CODE = "YY";
	private static final int NON_EU_COUNTRY_ID = 2000;
	private static final String NON_EU_COUNTRY_CODE = "ZZ";

	private ITaxDAO taxDAO;
	private final Map<TypeOfDestCountry, BPartnerLocationId> typeOfDestCountryBPartnerLocationIdMap = new HashMap<>();
	private final Map<TypeOfDestCountry, TaxId> typeOfDestCountryTaxIdMap = new HashMap<>();

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		taxDAO = Services.get(ITaxDAO.class);
		createTestData();
	}

	@Test
	public void noOrgIdNoWarehouseId()
	{
		assertThatExceptionOfType(AdempiereException.class).isThrownBy(() ->
				taxDAO.getBy(TaxQuery.builder()
						.bPartnerLocationId(typeOfDestCountryBPartnerLocationIdMap.get(DOMESTIC))
						.dateOfInterest(DATE_OF_INTEREST)
						.build())
		);
	}

	@Test
	public void orgIdNoWarehouseIdDomestic()
	{
		final Collection<Tax> taxes = taxDAO.getBy(TaxQuery.builder()
				.orgId(ORG_ID)
				.bPartnerLocationId(typeOfDestCountryBPartnerLocationIdMap.get(DOMESTIC))
				.dateOfInterest(DATE_OF_INTEREST)
				.build());
		assertThat(taxes.size()).isEqualTo(1);
		final Tax tax = taxes.stream().findFirst().get();
		assertThat(tax.getTaxId()).isEqualTo(typeOfDestCountryTaxIdMap.get(DOMESTIC));
	}

	@Test
	public void orgIdNoWarehouseIdEU()
	{
		final Collection<Tax> taxes = taxDAO.getBy(TaxQuery.builder()
				.orgId(ORG_ID)
				.bPartnerLocationId(typeOfDestCountryBPartnerLocationIdMap.get(WITHIN_COUNTRY_AREA))
				.dateOfInterest(DATE_OF_INTEREST)
				.build());
		assertThat(taxes.size()).isEqualTo(1);
		final Tax tax = taxes.stream().findFirst().get();
		assertThat(tax.getTaxId()).isEqualTo(typeOfDestCountryTaxIdMap.get(WITHIN_COUNTRY_AREA));
	}

	@Test
	public void orgIdNoWarehouseIdNonEU()
	{
		final Collection<Tax> taxes = taxDAO.getBy(TaxQuery.builder()
				.orgId(ORG_ID)
				.bPartnerLocationId(typeOfDestCountryBPartnerLocationIdMap.get(OUTSIDE_COUNTRY_AREA))
				.dateOfInterest(DATE_OF_INTEREST)
				.build());
		assertThat(taxes.size()).isEqualTo(1);
		final Tax tax = taxes.stream().findFirst().get();
		assertThat(tax.getTaxId()).isEqualTo(typeOfDestCountryTaxIdMap.get(OUTSIDE_COUNTRY_AREA));
	}

	private void createTestData()
	{
		createOrgData();
		typeOfDestCountryBPartnerLocationIdMap.put(DOMESTIC, createBPartnerData(ORG_COUNTRY_ID, ORG_COUNTRY_CODE, false));
		typeOfDestCountryBPartnerLocationIdMap.put(WITHIN_COUNTRY_AREA, createBPartnerData(EU_COUNTRY_ID, EU_COUNTRY_CODE, true));
		typeOfDestCountryBPartnerLocationIdMap.put(OUTSIDE_COUNTRY_AREA, createBPartnerData(NON_EU_COUNTRY_ID, NON_EU_COUNTRY_CODE, false));

		typeOfDestCountryTaxIdMap.put(DOMESTIC, createTaxData(DOMESTIC, ORG_COUNTRY_ID));
		typeOfDestCountryTaxIdMap.put(WITHIN_COUNTRY_AREA, createTaxData(WITHIN_COUNTRY_AREA, EU_COUNTRY_ID));
		typeOfDestCountryTaxIdMap.put(OUTSIDE_COUNTRY_AREA, createTaxData(OUTSIDE_COUNTRY_AREA, NON_EU_COUNTRY_ID));
	}

	private BPartnerLocationId createBPartnerData(final int countryId, final String countryCode, boolean includeInEU)
	{
		final I_C_Country country = newInstance(I_C_Country.class);
		country.setAD_Org_ID(ORG_ID.getRepoId());
		country.setC_Country_ID(countryId);
		country.setCountryCode(countryCode);
		save(country);

		if (includeInEU)
		{
			final I_C_CountryArea countryArea = newInstance(I_C_CountryArea.class);
			countryArea.setAD_Org_ID(ORG_ID.getRepoId());
			countryArea.setValue(ICountryAreaBL.COUNTRYAREAKEY_EU);
			save(countryArea);

			final I_C_CountryArea_Assign countryAreaAssign = newInstance(I_C_CountryArea_Assign.class);
			countryAreaAssign.setAD_Org_ID(ORG_ID.getRepoId());
			countryAreaAssign.setC_CountryArea_ID(countryArea.getC_CountryArea_ID());
			countryAreaAssign.setC_Country_ID(countryId);
			countryAreaAssign.setValidFrom(VALID_FROM);
			countryAreaAssign.setValidTo(VALID_TO);
			save(countryAreaAssign);

		}

		final I_C_BPartner bpartner = newInstance(I_C_BPartner.class);
		bpartner.setAD_Org_ID(ORG_ID.getRepoId());
		save(bpartner);

		final I_C_Location location = newInstance(I_C_Location.class);
		location.setAD_Org_ID(ORG_ID.getRepoId());
		location.setC_Country_ID(countryId);
		save(location);

		final I_C_BPartner_Location bPartnerLocation = newInstance(I_C_BPartner_Location.class);
		bPartnerLocation.setAD_Org_ID(ORG_ID.getRepoId());
		bPartnerLocation.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		bPartnerLocation.setC_Location_ID(location.getC_Location_ID());
		bPartnerLocation.setIsBillTo(true);
		save(bPartnerLocation);

		return BPartnerLocationId.ofRepoId(bPartnerLocation.getC_BPartner_ID(), bPartnerLocation.getC_BPartner_Location_ID());
	}

	private void createOrgData()
	{
		final I_AD_Org org = newInstance(I_AD_Org.class);
		org.setAD_Org_ID(ORG_ID.getRepoId());
		save(org);

		final I_C_BPartner bpartner = newInstance(I_C_BPartner.class);
		bpartner.setAD_Org_ID(ORG_ID.getRepoId());
		bpartner.setAD_OrgBP_ID(ORG_ID.getRepoId());
		save(bpartner);

		final I_C_Location location = newInstance(I_C_Location.class);
		location.setAD_Org_ID(ORG_ID.getRepoId());
		location.setC_Country_ID(ORG_COUNTRY_ID);
		save(location);

		final I_C_BPartner_Location bPartnerLocation = newInstance(I_C_BPartner_Location.class);
		bPartnerLocation.setAD_Org_ID(ORG_ID.getRepoId());
		bPartnerLocation.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		bPartnerLocation.setC_Location_ID(location.getC_Location_ID());
		bPartnerLocation.setIsBillTo(true);
		save(bPartnerLocation);
	}

	TaxId createTaxData(final TypeOfDestCountry typeOfDestCountry, final int toCountryId)
	{
		final I_C_Tax tax = newInstance(I_C_Tax.class);
		tax.setAD_Org_ID(ORG_ID.getRepoId());
		tax.setC_Country_ID(ORG_COUNTRY_ID);
		tax.setTo_Country_ID(toCountryId);
		tax.setTypeOfDestCountry(typeOfDestCountry.getCode());
		tax.setRequiresTaxCertificate(false);
		tax.setIsSmallbusiness(false);
		tax.setValidFrom(VALID_FROM);
		tax.setSOPOType(SOPOType.BOTH.getCode());
		tax.setC_TaxCategory_ID(C_TAX_CATEGORY_ID);
		save(tax);
		return TaxId.ofRepoId(tax.getC_Tax_ID());
	}

}
