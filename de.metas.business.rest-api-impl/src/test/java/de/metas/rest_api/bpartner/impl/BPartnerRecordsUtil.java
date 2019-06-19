package de.metas.rest_api.bpartner.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_BPartner_Recent_V;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Postal;

import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2019 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@UtilityClass
public class BPartnerRecordsUtil
{
	public static final String C_COUNTRY_RECORD_COUNTRY_CODE = "countryRecord.countryCode";

	public static final int C_BP_GROUP_ID = 15;
	public static final String BP_GROUP_RECORD_NAME = "bpGroupRecord.name";

	public static final String C_BPARTNER_LOCATION_GLN = "bpartnerLocationRecord.gln";
	public static final String C_BPARTNER_LOCATION_EXTERNAL_ID = "bpartnerLocation.externalId";
	public static final int AD_ORG_ID = 10;
	public static final String AD_USER_EXTERNAL_ID = "abcde";
	public static final String C_BPARTNER_EXTERNAL_ID = "fghij";

	public static final int C_BPARTNER_ID = 20;
	public static final int AD_USER_ID = 30;
	public static final int C_BBPARTNER_LOCATION_ID = 40;


	public void createBPartnerData(int idOffSet)
	{
		final String idOffSetStr = idOffSet == 0 ? "" : "_" + Integer.toString(idOffSet);

		final I_C_BPartner bpartnerRecord = newInstance(I_C_BPartner.class);
		bpartnerRecord.setC_BPartner_ID(C_BPARTNER_ID + idOffSet);
		bpartnerRecord.setAD_Org_ID(AD_ORG_ID);
		bpartnerRecord.setExternalId(C_BPARTNER_EXTERNAL_ID + idOffSetStr);
		bpartnerRecord.setName("bpartnerRecord.name");
		bpartnerRecord.setValue("bpartnerRecord.value");
		bpartnerRecord.setC_BP_Group_ID(C_BP_GROUP_ID);
		saveRecord(bpartnerRecord);

		final I_C_BPartner_Recent_V sinceRecord = newInstance(I_C_BPartner_Recent_V.class);
		sinceRecord.setC_BPartner_ID(bpartnerRecord.getC_BPartner_ID());
		sinceRecord.setC_BPartner_Recent_V_ID(bpartnerRecord.getC_BPartner_ID());
		saveRecord(sinceRecord);

		final I_AD_User contactRecord = newInstance(I_AD_User.class);
		contactRecord.setAD_Org_ID(AD_ORG_ID);
		contactRecord.setAD_User_ID(AD_USER_ID + idOffSet);
		contactRecord.setC_BPartner(bpartnerRecord);
		contactRecord.setExternalId(AD_USER_EXTERNAL_ID + idOffSetStr);
		contactRecord.setValue("bpartnerRecord.value");
		contactRecord.setName("bpartnerRecord.name");
		contactRecord.setLastname("bpartnerRecord.lastName");
		contactRecord.setFirstname("bpartnerRecord.firstName");
		contactRecord.setEMail("bpartnerRecord.email");
		contactRecord.setPhone("bpartnerRecord.phone");
		saveRecord(contactRecord);

		final I_C_Country countryRecord = newInstance(I_C_Country.class);
		countryRecord.setCountryCode(C_COUNTRY_RECORD_COUNTRY_CODE);
		saveRecord(countryRecord);

		final I_C_Postal postalRecord = newInstance(I_C_Postal.class);
		postalRecord.setC_Country(countryRecord);
		postalRecord.setPostal("postalRecord.postal");
		postalRecord.setDistrict("postalRecord.district");
		saveRecord(postalRecord);

		final I_C_Location locationRecord = newInstance(I_C_Location.class);
		locationRecord.setC_Postal(postalRecord);
		locationRecord.setC_Country(countryRecord);
		locationRecord.setAddress1("locationRecord.address1");
		locationRecord.setAddress2("locationRecord.address2");
		locationRecord.setPOBox("locationRecord.poBox");
		locationRecord.setPostal("locationRecord.postal");
		locationRecord.setCity("locationRecord.city");
		locationRecord.setRegionName("locationRecord.regionName");
		locationRecord.setAddress2("locationRecord.address2");
		locationRecord.setAddress2("locationRecord.address2");
		saveRecord(locationRecord);

		final I_C_BPartner_Location bpartnerLocationRecord = newInstance(I_C_BPartner_Location.class);
		bpartnerLocationRecord.setAD_Org_ID(AD_ORG_ID);
		bpartnerLocationRecord.setC_BPartner_Location_ID(C_BBPARTNER_LOCATION_ID + idOffSet);
		bpartnerLocationRecord.setC_BPartner(bpartnerRecord);
		bpartnerLocationRecord.setC_Location(locationRecord);

		bpartnerLocationRecord.setGLN(C_BPARTNER_LOCATION_GLN + idOffSetStr);
		bpartnerLocationRecord.setExternalId(C_BPARTNER_LOCATION_EXTERNAL_ID + idOffSetStr);
		saveRecord(bpartnerLocationRecord);
	}
}
