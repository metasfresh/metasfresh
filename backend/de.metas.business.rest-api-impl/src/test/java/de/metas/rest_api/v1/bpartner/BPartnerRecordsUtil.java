/*
 * #%L
 * de.metas.business.rest-api-impl
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

package de.metas.rest_api.v1.bpartner;

import de.metas.common.util.time.SystemTime;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.money.CurrencyId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.organization.OrgInfoUpdateRequest;
import de.metas.user.UserId;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_BPartner_Recent_V;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Postal;
import org.compiere.util.Env;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class BPartnerRecordsUtil
{

	public static final String C_COUNTRY_RECORD_COUNTRY_CODE = "countryRecord.countryCode";

	public static final int C_BP_GROUP_ID = 15;
	public static final String BP_GROUP_RECORD_NAME = "bpGroupRecord.name";

	public static final String C_BPARTNER_LOCATION_GLN = "bpartnerLocationRecord.gln";
	public static final String C_BPARTNER_LOCATION_EXTERNAL_ID = "bpartnerLocation.externalId";
	public static final int AD_ORG_ID = 10;
	public static final String AD_ORG_VALUE = "orgRecord.value";

	public static final String AD_USER_EXTERNAL_ID = "contactRecord.externalId";
	public static final String AD_USER_VALUE = "contactRecord.value";

	public static final String C_BPARTNER_EXTERNAL_ID = "fghij";
	public static final String C_BPARTNER_VALUE = "bpartnerRecord.value";

	public static final int C_BPARTNER_ID = 20;
	public static final int AD_USER_ID = 30;
	public static final int C_BBPARTNER_LOCATION_ID = 40;
	public static final int C_BP_BANKACCOUNT_ID = 50;

	public static void createBPartnerData(final int idOffSet)
	{

		final UserId adUserId = UserId.ofRepoId(AD_USER_ID + idOffSet);
		final String idOffSetStr = idOffSet == 0 ? "" : "_" + idOffSet;

		final I_AD_Org org = InterfaceWrapperHelper.newInstance(I_AD_Org.class);
		org.setAD_Org_ID(AD_ORG_ID);
		org.setValue(AD_ORG_VALUE);
		saveRecord(org);

		final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

		orgDAO.createOrUpdateOrgInfo(OrgInfoUpdateRequest.builder()
											 .orgId(OrgId.ofRepoId(AD_ORG_ID))
											 .build());

		setupTimeSource();
		try (final IAutoCloseable ignored = Env.temporaryChangeLoggedUserId(adUserId))
		{
			final I_C_BPartner bpartnerRecord = newInstance(I_C_BPartner.class);
			bpartnerRecord.setC_BPartner_ID(C_BPARTNER_ID + idOffSet);
			bpartnerRecord.setAD_Org_ID(AD_ORG_ID);
			bpartnerRecord.setExternalId(C_BPARTNER_EXTERNAL_ID + idOffSetStr);
			bpartnerRecord.setName("bpartnerRecord.name" + idOffSetStr);
			bpartnerRecord.setValue(C_BPARTNER_VALUE + idOffSetStr);
			bpartnerRecord.setC_BP_Group_ID(C_BP_GROUP_ID);
			bpartnerRecord.setIsVendor(true);
			bpartnerRecord.setIsCustomer(true);
			bpartnerRecord.setVATaxID("VATaxID" + idOffSetStr);
			setCreatedByAndWhen(bpartnerRecord, adUserId); // have to do it manually because we are setting the record ID too
			saveRecord(bpartnerRecord);

			final I_C_BPartner_Recent_V sinceRecord = newInstance(I_C_BPartner_Recent_V.class);
			sinceRecord.setC_BPartner_ID(bpartnerRecord.getC_BPartner_ID());
			sinceRecord.setC_BPartner_Recent_V_ID(bpartnerRecord.getC_BPartner_ID());
			saveRecord(sinceRecord);

			final I_AD_User contactRecord = newInstance(I_AD_User.class);
			contactRecord.setAD_User_ID(adUserId.getRepoId());
			contactRecord.setAD_Org_ID(AD_ORG_ID);
			contactRecord.setC_BPartner_ID(bpartnerRecord.getC_BPartner_ID());
			contactRecord.setExternalId(AD_USER_EXTERNAL_ID + idOffSetStr);
			contactRecord.setValue(AD_USER_VALUE + idOffSetStr);
			contactRecord.setName("contactRecord.name" + idOffSetStr);
			contactRecord.setLastname("contactRecord.lastName" + idOffSetStr);
			contactRecord.setFirstname("contactRecord.firstName" + idOffSetStr);
			contactRecord.setEMail("contactRecord.email" + idOffSetStr);
			contactRecord.setPhone("contactRecord.phone" + idOffSetStr);
			setCreatedByAndWhen(contactRecord, adUserId); // have to do it manually because we are setting the record ID too
			saveRecord(contactRecord);

			final I_C_Country countryRecord = newInstance(I_C_Country.class);
			countryRecord.setCountryCode(C_COUNTRY_RECORD_COUNTRY_CODE);
			saveRecord(countryRecord);

			final I_C_Postal postalRecord = newInstance(I_C_Postal.class);
			postalRecord.setC_Country(countryRecord);
			postalRecord.setPostal("locationRecord.postal" + idOffSetStr);
			postalRecord.setDistrict("postalRecord.district" + idOffSetStr);
			postalRecord.setCity("locationRecord.city" + idOffSetStr);
			postalRecord.setRegionName("locationRecord.regionName" + idOffSetStr);
			saveRecord(postalRecord);

			final I_C_Location locationRecord = newInstance(I_C_Location.class);
			locationRecord.setC_Postal(postalRecord);
			locationRecord.setC_Country(countryRecord);
			locationRecord.setAddress1("locationRecord.address1" + idOffSetStr);
			locationRecord.setAddress2("locationRecord.address2" + idOffSetStr);
			locationRecord.setPOBox("locationRecord.poBox" + idOffSetStr);
			locationRecord.setPostal("locationRecord.postal" + idOffSetStr);
			locationRecord.setCity("locationRecord.city" + idOffSetStr);
			locationRecord.setRegionName("locationRecord.regionName" + idOffSetStr);
			locationRecord.setAddress2("locationRecord.address2" + idOffSetStr);
			locationRecord.setAddress2("locationRecord.address2" + idOffSetStr);
			saveRecord(locationRecord);

			final I_C_BPartner_Location bpartnerLocationRecord = newInstance(I_C_BPartner_Location.class);
			bpartnerLocationRecord.setC_BPartner_Location_ID(C_BBPARTNER_LOCATION_ID + idOffSet);
			bpartnerLocationRecord.setAD_Org_ID(AD_ORG_ID);
			bpartnerLocationRecord.setC_BPartner_ID(bpartnerRecord.getC_BPartner_ID());
			bpartnerLocationRecord.setC_Location(locationRecord);
			bpartnerLocationRecord.setGLN(C_BPARTNER_LOCATION_GLN + idOffSetStr);
			bpartnerLocationRecord.setExternalId(C_BPARTNER_LOCATION_EXTERNAL_ID + idOffSetStr);
			setCreatedByAndWhen(bpartnerLocationRecord, adUserId); // have to do it manually because we are setting the record ID too
			saveRecord(bpartnerLocationRecord);

			{
				final CurrencyRepository currencyRepo = new CurrencyRepository();
				final CurrencyId currencyId = currencyRepo.getCurrencyIdByCurrencyCode(CurrencyCode.EUR);

				final I_C_BP_BankAccount bpBankAccountRecord = newInstance(I_C_BP_BankAccount.class);
				bpBankAccountRecord.setC_BP_BankAccount_ID(C_BP_BANKACCOUNT_ID + idOffSet);
				bpBankAccountRecord.setAD_Org_ID(AD_ORG_ID);
				bpBankAccountRecord.setC_BPartner_ID(bpartnerRecord.getC_BPartner_ID());
				bpBankAccountRecord.setIBAN("INITIAL-IBAN-1");
				bpBankAccountRecord.setSwiftCode("INITIAL-SWIFTCODE-1");
				bpBankAccountRecord.setC_Currency_ID(currencyId.getRepoId());
				setCreatedByAndWhen(bpBankAccountRecord, adUserId); // have to do it manually because we are setting the record ID too
				saveRecord(bpBankAccountRecord);
			}
		}
		finally
		{
			resetTimeSource();
		}
	}

	private static void setCreatedByAndWhen(final Object model, final UserId adUserId)
	{
		InterfaceWrapperHelper.setValue(model, InterfaceWrapperHelper.COLUMNNAME_CreatedBy, adUserId.getRepoId());
		InterfaceWrapperHelper.setValue(model, InterfaceWrapperHelper.COLUMNNAME_Created, de.metas.common.util.time.SystemTime.asTimestamp());
	}

	/** Set time source to one static value so that we know which created/updated timestamps to expect in our created records */
	public static void setupTimeSource()
	{
		SystemTime.setTimeSource(() -> 1561133544); // Fri, 21 Jun 2019 16:12:24 GMT
	}

	public static void resetTimeSource()
	{
		de.metas.common.util.time.SystemTime.resetTimeSource();
	}

}
