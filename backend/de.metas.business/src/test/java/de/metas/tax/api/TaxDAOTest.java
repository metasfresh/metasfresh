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

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.lang.SOTrx;
import de.metas.location.ICountryAreaBL;
import de.metas.location.LocationId;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_CountryArea;
import org.compiere.model.I_C_CountryArea_Assign;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_AD_OrgInfo;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static de.metas.tax.api.TypeOfDestCountry.DOMESTIC;
import static de.metas.tax.api.TypeOfDestCountry.OUTSIDE_COUNTRY_AREA;
import static de.metas.tax.api.TypeOfDestCountry.WITHIN_COUNTRY_AREA;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

class TaxDAOTest
{
	private static final int C_TAX_CATEGORY_ID = 42;
	@NonNull
	private static final Timestamp DATE_OF_INTEREST = TimeUtil.asTimestamp(new Date());
	private static final Timestamp VALID_FROM = TimeUtil.addDays(new Date(), -1);
	private static final Timestamp VALID_TO = TimeUtil.addDays(new Date(), 1);

	private static final OrgId ORG_ID = OrgId.ofRepoId(10);
	private static final LocationId locationId = LocationId.ofRepoId(11);
	private static final BPartnerId bPartnerId = BPartnerId.ofRepoId(12);

	private static final int ORG_COUNTRY_ID = 1000;
	private static final String ORG_COUNTRY_CODE = "AA";

	private static final WarehouseId WAREHOUSE_ID = WarehouseId.ofRepoId(100);
	private static final int WAREHOUSE_COUNTRY_ID = 1001;
	private static final String WAREHOUSE_COUNTRY_CODE = "BB";

	private static final int EU_COUNTRY_ID = 1010;
	private static final String EU_COUNTRY_CODE = "CC";

	private static final int NON_EU_COUNTRY_ID = 1011;
	private static final String NON_EU_COUNTRY_CODE = "DD";

	private static final int COUNTRY_AREA_ID = 10000;

	private ITaxDAO taxDAO;
	private final Map<TypeOfDestCountry, BPartnerLocationAndCaptureId> typeOfDestCountryBPartnerLocationIdMap = new HashMap<>();
	private final Map<TypeOfDestCountry, TaxId> typeOfDestCountryTaxIdByOrgIdMap = new HashMap<>();
	private final Map<TypeOfDestCountry, TaxId> typeOfDestCountryTaxIdByWarehouseIdMap = new HashMap<>();

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		taxDAO = Services.get(ITaxDAO.class);
		createTestData();
	}

	@Nested
	@DisplayName("Org Id provided, Warehouse Id NOT provided")
	public class OrgIdNoWarehouseId
	{
		@Test
		@DisplayName("Domestic")
		public void domestic()
		{
			orgIdNoWarehouseIdTestByTypeOfDestCountry(DOMESTIC);
		}

		@Test
		@DisplayName("Within EU")
		public void eu()
		{
			orgIdNoWarehouseIdTestByTypeOfDestCountry(WITHIN_COUNTRY_AREA);
		}

		@Test
		@DisplayName("Outside EU")
		public void nonEu()
		{
			orgIdNoWarehouseIdTestByTypeOfDestCountry(OUTSIDE_COUNTRY_AREA);
		}
	}

	private void orgIdNoWarehouseIdTestByTypeOfDestCountry(final TypeOfDestCountry type)
	{
		final Tax tax = taxDAO.getBy(TaxQuery.builder()
				.orgId(ORG_ID)
				.bPartnerLocationId(typeOfDestCountryBPartnerLocationIdMap.get(type))
				.dateOfInterest(DATE_OF_INTEREST)
				.soTrx(SOTrx.SALES)
				.build());
		assertThat(tax).isNotNull();
		assertThat(tax.getTaxId()).isEqualTo(typeOfDestCountryTaxIdByOrgIdMap.get(type));
	}

	@Nested
	@DisplayName("Org Id provided, Warehouse Id provided")
	public class OrgIdWarehouseId
	{
		@BeforeEach
		public void warehouseSetup()
		{
			typeOfDestCountryBPartnerLocationIdMap.put(DOMESTIC, createBPartnerData(WAREHOUSE_COUNTRY_ID, WAREHOUSE_COUNTRY_CODE, true));
		}

		@Test
		@DisplayName("Domestic")
		public void domestic()
		{
			orgIdWarehouseIdTestByTypeOfDestCountry(DOMESTIC);
		}

		@Test
		@DisplayName("Within EU")
		public void eu()
		{
			orgIdWarehouseIdTestByTypeOfDestCountry(WITHIN_COUNTRY_AREA);
		}

		@Test
		@DisplayName("Outside EU")
		public void nonEu()
		{
			orgIdWarehouseIdTestByTypeOfDestCountry(OUTSIDE_COUNTRY_AREA);
		}
	}

	private void orgIdWarehouseIdTestByTypeOfDestCountry(final TypeOfDestCountry type)
	{
		final Tax tax = taxDAO.getBy(TaxQuery.builder()
				.orgId(ORG_ID)
				.warehouseId(WAREHOUSE_ID)
				.bPartnerLocationId(typeOfDestCountryBPartnerLocationIdMap.get(type))
				.dateOfInterest(DATE_OF_INTEREST)
				.soTrx(SOTrx.SALES)
				.build());
		assertThat(tax).isNotNull();
		assertThat(tax.getTaxId()).isEqualTo(typeOfDestCountryTaxIdByWarehouseIdMap.get(type));
	}

	private void createTestData()
	{
		createOrgData();
		createWarehouseData();
		typeOfDestCountryBPartnerLocationIdMap.put(DOMESTIC, createBPartnerData(ORG_COUNTRY_ID, ORG_COUNTRY_CODE, true));
		typeOfDestCountryBPartnerLocationIdMap.put(WITHIN_COUNTRY_AREA, createBPartnerData(EU_COUNTRY_ID, EU_COUNTRY_CODE, true));
		typeOfDestCountryBPartnerLocationIdMap.put(OUTSIDE_COUNTRY_AREA, createBPartnerData(NON_EU_COUNTRY_ID, NON_EU_COUNTRY_CODE, false));

		typeOfDestCountryTaxIdByOrgIdMap.put(DOMESTIC, createTaxData(DOMESTIC, ORG_COUNTRY_ID, ORG_COUNTRY_ID));
		typeOfDestCountryTaxIdByOrgIdMap.put(WITHIN_COUNTRY_AREA, createTaxData(WITHIN_COUNTRY_AREA, ORG_COUNTRY_ID, EU_COUNTRY_ID));
		typeOfDestCountryTaxIdByOrgIdMap.put(OUTSIDE_COUNTRY_AREA, createTaxData(OUTSIDE_COUNTRY_AREA, ORG_COUNTRY_ID, NON_EU_COUNTRY_ID));
	}

	private void createWarehouseData()
	{
		final BPartnerLocationAndCaptureId bPartnerLocationId = createBPartnerData(WAREHOUSE_COUNTRY_ID, WAREHOUSE_COUNTRY_CODE, true);

		final I_M_Warehouse warehouse = newInstance(I_M_Warehouse.class);
		warehouse.setAD_Org_ID(ORG_ID.getRepoId());
		warehouse.setM_Warehouse_ID(WAREHOUSE_ID.getRepoId());
		warehouse.setC_BPartner_ID(bPartnerLocationId.getBpartnerId().getRepoId());
		warehouse.setC_BPartner_Location_ID(bPartnerLocationId.getBpartnerLocationId().getRepoId());
		save(warehouse);

		typeOfDestCountryTaxIdByWarehouseIdMap.put(DOMESTIC, createTaxData(DOMESTIC, WAREHOUSE_COUNTRY_ID, WAREHOUSE_COUNTRY_ID));
		typeOfDestCountryTaxIdByWarehouseIdMap.put(WITHIN_COUNTRY_AREA, createTaxData(WITHIN_COUNTRY_AREA, WAREHOUSE_COUNTRY_ID, EU_COUNTRY_ID));
		typeOfDestCountryTaxIdByWarehouseIdMap.put(OUTSIDE_COUNTRY_AREA, createTaxData(OUTSIDE_COUNTRY_AREA, WAREHOUSE_COUNTRY_ID, NON_EU_COUNTRY_ID));
	}

	private BPartnerLocationAndCaptureId createBPartnerData(final int countryId, final String countryCode, final boolean includeInEU)
	{
		final I_C_Location location = createLocation(countryId, countryCode, includeInEU);

		final I_C_BPartner bpartner = newInstance(I_C_BPartner.class);
		bpartner.setAD_Org_ID(ORG_ID.getRepoId());
		save(bpartner);

		final I_C_BPartner_Location bPartnerLocation = newInstance(I_C_BPartner_Location.class);
		bPartnerLocation.setAD_Org_ID(ORG_ID.getRepoId());
		bPartnerLocation.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		bPartnerLocation.setC_Location_ID(location.getC_Location_ID());
		bPartnerLocation.setIsBillTo(true);
		save(bPartnerLocation);

		return BPartnerLocationAndCaptureId.ofRepoId(bPartnerLocation.getC_BPartner_ID(), bPartnerLocation.getC_BPartner_Location_ID(), bPartnerLocation.getC_Location_ID());
	}

	@NonNull
	private I_C_Location createLocation(final int countryId, final String countryCode, final boolean includeInEU)
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
			countryArea.setC_CountryArea_ID(COUNTRY_AREA_ID);
			save(countryArea);

			final I_C_CountryArea_Assign countryAreaAssign = newInstance(I_C_CountryArea_Assign.class);
			countryAreaAssign.setAD_Org_ID(ORG_ID.getRepoId());
			countryAreaAssign.setC_CountryArea_ID(countryArea.getC_CountryArea_ID());
			countryAreaAssign.setC_Country_ID(countryId);
			countryAreaAssign.setValidFrom(VALID_FROM);
			countryAreaAssign.setValidTo(VALID_TO);
			save(countryAreaAssign);

		}

		final I_C_Location location = newInstance(I_C_Location.class);
		location.setAD_Org_ID(ORG_ID.getRepoId());
		location.setC_Country_ID(countryId);
		save(location);
		return location;
	}

	private void createOrgData()
	{
		final I_AD_Org org = newInstance(I_AD_Org.class);
		org.setAD_Org_ID(ORG_ID.getRepoId());
		save(org);

		final I_C_Location location = newInstance(I_C_Location.class);
		location.setC_Location_ID(locationId.getRepoId());
		location.setAD_Org_ID(ORG_ID.getRepoId());
		location.setC_Country_ID(ORG_COUNTRY_ID);
		save(location);

		final I_C_BPartner bpartner = newInstance(I_C_BPartner.class);
		bpartner.setC_BPartner_ID(bPartnerId.getRepoId());
		bpartner.setAD_Org_ID(ORG_ID.getRepoId());
		bpartner.setAD_OrgBP_ID(ORG_ID.getRepoId());
		save(bpartner);

		final I_C_BPartner_Location bPartnerLocation = newInstance(I_C_BPartner_Location.class);
		bPartnerLocation.setAD_Org_ID(ORG_ID.getRepoId());
		bPartnerLocation.setC_BPartner_ID(bPartnerId.getRepoId());
		bPartnerLocation.setC_Location_ID(locationId.getRepoId());
		bPartnerLocation.setIsBillTo(true);
		save(bPartnerLocation);

		final I_AD_OrgInfo orgInfo = newInstance(I_AD_OrgInfo.class);
		orgInfo.setAD_Org_ID(ORG_ID.getRepoId());
		orgInfo.setOrgBP_Location_ID(bPartnerLocation.getC_BPartner_Location_ID());
		orgInfo.setOrg_BPartner_ID(bPartnerId.getRepoId());
		orgInfo.setStoreCreditCardData(X_AD_OrgInfo.STORECREDITCARDDATA_Letzte4Stellen);
		save(orgInfo);
	}

	private TaxId createTaxData(final TypeOfDestCountry typeOfDestCountry, final int countryId, final int toCountryId)
	{
		final I_C_Tax tax = newInstance(I_C_Tax.class);
		tax.setName(typeOfDestCountry + "-" + countryId + "-" + toCountryId);
		tax.setAD_Org_ID(ORG_ID.getRepoId());
		tax.setC_Country_ID(countryId);
		tax.setTo_Country_ID(toCountryId);
		tax.setTypeOfDestCountry(typeOfDestCountry.getCode());
		tax.setRequiresTaxCertificate(null);
		tax.setIsSmallbusiness(null);
		tax.setValidFrom(VALID_FROM);
		tax.setSOPOType(SOPOType.BOTH.getCode());
		tax.setC_TaxCategory_ID(C_TAX_CATEGORY_ID);
		save(tax);
		return TaxId.ofRepoId(tax.getC_Tax_ID());
	}

}
