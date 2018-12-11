package de.metas.ordercandidate.rest;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.service.OrgId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Location;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

import de.metas.bpartner.service.BPartnerIdNotFoundException;
import de.metas.ordercandidate.api.OLCandBPartnerInfo;
import de.metas.ordercandidate.rest.SyncAdvise.IfExists;
import de.metas.ordercandidate.rest.SyncAdvise.IfNotExists;
import mockit.Mocked;

/*
 * #%L
 * de.metas.ordercandidate.rest-api-impl
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class BPartnerMasterDataProviderTest
{
	@Rule
	public final TestWatcher testWatcher = new AdempiereTestWatcher();

	@Mocked
	private PermissionService permissionService;

	private JsonBPartner jsonBPartner;

	private JsonBPartnerInfo jsonBPartnerInfo;

	private JsonBPartnerLocation jsonBPartnerLocation;

	private BPartnerMasterDataProvider bpartnerMasterDataProvider;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		final I_C_Country countryRecord = newInstance(I_C_Country.class);
		countryRecord.setCountryCode("DE");
		saveRecord(countryRecord);

		bpartnerMasterDataProvider = new BPartnerMasterDataProvider(permissionService);

		jsonBPartner = JsonBPartner.builder()
				.code("jsonBPartner.Code")
				.name("jsonBPartner.Name")
				.build();

		jsonBPartnerLocation = JsonBPartnerLocation.builder()
				.countryCode("DE")
				.gln("jsonBPartnerLocation.GLN")
				.externalId("jsonBPartnerLocation.ExternalId")
				.build();

		JsonBPartnerContact jsonBPartnerContact = JsonBPartnerContact.builder()
				.externalId("jsonBPartnerContact.ExternalId")
				.email("jsonBPartnerContact.Email")
				.build();

		jsonBPartnerInfo = JsonBPartnerInfo.builder()
				.bpartner(jsonBPartner)
				.location(jsonBPartnerLocation)
				.contact(jsonBPartnerContact)
				.build();
	}

	@Test
	public void getCreateBPartnerInfo_NotExists_Fail()
	{
		final SyncAdvise syncAdvise = SyncAdvise.builder().ifNotExists(IfNotExists.FAIL).build();
		final JsonBPartnerInfo jsonBPartnerInfoToUse = jsonBPartnerInfo.toBuilder().syncAdvise(syncAdvise).build();

		assertThatThrownBy(() -> bpartnerMasterDataProvider.getCreateBPartnerInfo(jsonBPartnerInfoToUse, OrgId.ofRepoId(10)))
				.isInstanceOf(BPartnerIdNotFoundException.class)
				.hasMessageContaining("Code=jsonBPartner.Code")
				.hasMessageContaining("GLN=jsonBPartnerLocation.GLN");
	}

	@Test
	public void getCreateBPartnerInfo_NotExists_Create()
	{
		final SyncAdvise syncAdvise = SyncAdvise.builder().ifNotExists(IfNotExists.CREATE).build();
		final JsonBPartnerInfo jsonBPartnerInfoToUse = jsonBPartnerInfo.toBuilder().syncAdvise(syncAdvise).build();

		// invoke the method under test
		bpartnerMasterDataProvider.getCreateBPartnerInfo(jsonBPartnerInfoToUse, OrgId.ofRepoId(10));

		final List<I_C_BPartner> bpartnerRecords = POJOLookupMap
				.get()
				.getRecords(I_C_BPartner.class);
		assertThat(bpartnerRecords).hasSize(1);
		final I_C_BPartner bpartnerRecord = bpartnerRecords.get(0);

		assertThat(bpartnerRecord.getAD_Org_ID()).isEqualTo(10);
		assertThat(bpartnerRecord.getValue()).isEqualTo("jsonBPartner.Code");
		assertThat(bpartnerRecord.getName()).isEqualTo("jsonBPartner.Name");

		final List<I_C_BPartner_Location> bpartnerLocationRecords = POJOLookupMap
				.get()
				.getRecords(I_C_BPartner_Location.class);
		assertThat(bpartnerLocationRecords).hasSize(1);
		final I_C_BPartner_Location bpartnerLocationRecord = bpartnerLocationRecords.get(0);

		assertThat(bpartnerLocationRecord.getAD_Org_ID()).isEqualTo(10);
		assertThat(bpartnerLocationRecord.getExternalId()).isEqualTo("jsonBPartnerLocation.ExternalId");
		assertThat(bpartnerLocationRecord.getGLN()).isEqualTo("jsonBPartnerLocation.GLN");
		assertThat(bpartnerLocationRecord.getC_Location().getC_Country().getCountryCode()).isEqualTo("DE");
		assertThat(bpartnerLocationRecord.getC_BPartner_ID()).isEqualTo(bpartnerRecord.getC_BPartner_ID());

		final List<I_AD_User> bpartnerContactRecords = POJOLookupMap
				.get()
				.getRecords(I_AD_User.class, u -> u.getAD_User_ID() > 0/* exclude the system user */);
		assertThat(bpartnerContactRecords).hasSize(1);
		final I_AD_User bpartnerContactRecord = bpartnerContactRecords.get(0);

		assertThat(bpartnerContactRecord.getAD_Org_ID()).isEqualTo(10);
		assertThat(bpartnerContactRecord.getExternalId()).isEqualTo("jsonBPartnerContact.ExternalId");
		assertThat(bpartnerContactRecord.getEMail()).isEqualTo("jsonBPartnerContact.Email");
		assertThat(bpartnerContactRecord.getC_BPartner_ID()).isEqualTo(bpartnerRecord.getC_BPartner_ID());
	}

	@Test
	public void getCreateBPartnerInfo_Exists_DontUpdate()
	{
		final I_C_BPartner bpartnerRecord = newInstance(I_C_BPartner.class);
		saveRecord(bpartnerRecord);

		final I_C_BPartner_Location bpLocationRecord = newInstance(I_C_BPartner_Location.class);
		bpLocationRecord.setC_BPartner(bpartnerRecord);
		bpLocationRecord.setGLN("jsonBPartnerLocation.GLN");
		saveRecord(bpLocationRecord);

		final SyncAdvise syncAdvise = SyncAdvise.builder().ifExists(IfExists.DONT_UPDATE).build();
		final JsonBPartnerInfo jsonBPartnerInfoToUse = jsonBPartnerInfo.toBuilder().syncAdvise(syncAdvise).build();

		// invoke the method under test
		final OLCandBPartnerInfo result = bpartnerMasterDataProvider.getCreateBPartnerInfo(jsonBPartnerInfoToUse, OrgId.ofRepoId(10));

		assertThat(result.getBpartnerId().getRepoId()).isEqualTo(bpartnerRecord.getC_BPartner_ID());
		assertThat(result.getBpartnerLocationId().getRepoId()).isEqualTo(bpLocationRecord.getC_BPartner_Location_ID());
		assertThat(result.getContactId()).isNull();

		final I_C_BPartner bpartnerRecordAfter = load(bpartnerRecord.getC_BPartner_ID(), I_C_BPartner.class);
		assertThat(bpartnerRecordAfter.getValue()).isNull();
		assertThat(bpartnerRecordAfter.getName()).isNull();

		final I_C_BPartner_Location bpLocationRecordAfter = load(bpLocationRecord.getC_BPartner_Location_ID(), I_C_BPartner_Location.class);
		assertThat(bpLocationRecordAfter.getGLN()).isEqualTo("jsonBPartnerLocation.GLN");
		assertThat(bpLocationRecordAfter.getExternalId()).isNull();

		final List<I_AD_User> bpartnerContactRecordsAfter = POJOLookupMap
				.get()
				.getRecords(I_AD_User.class, u -> u.getAD_User_ID() > 0/* exclude the system user */);
		assertThat(bpartnerContactRecordsAfter).isEmpty();
	}

	@Test
	public void getCreateBPartnerInfo_Exists_Update()
	{
		final I_C_BPartner bpartnerRecord = newInstance(I_C_BPartner.class);
		saveRecord(bpartnerRecord);

		final I_C_Country countryRecord = newInstance(I_C_Country.class);
		countryRecord.setCountryCode("DE");
		saveRecord(countryRecord);

		final I_C_Location locationRecord = newInstance(I_C_Location.class);
		locationRecord.setC_Country(countryRecord);
		saveRecord(locationRecord);

		final I_C_BPartner_Location bpLocationRecord = newInstance(I_C_BPartner_Location.class);
		bpLocationRecord.setC_BPartner(bpartnerRecord);
		bpLocationRecord.setC_Location(locationRecord);
		bpLocationRecord.setGLN("jsonBPartnerLocation.GLN");
		saveRecord(bpLocationRecord);

		final SyncAdvise syncAdvise = SyncAdvise.builder().ifExists(IfExists.UPDATE).build();
		final JsonBPartnerInfo jsonBPartnerInfoToUse = jsonBPartnerInfo.toBuilder().syncAdvise(syncAdvise).build();

		// invoke the method under test
		final OLCandBPartnerInfo result = bpartnerMasterDataProvider.getCreateBPartnerInfo(jsonBPartnerInfoToUse, OrgId.ofRepoId(10));

		assertThat(result.getBpartnerId().getRepoId()).isEqualTo(bpartnerRecord.getC_BPartner_ID());
		assertThat(result.getBpartnerLocationId().getRepoId()).isEqualTo(bpLocationRecord.getC_BPartner_Location_ID());
		assertThat(result.getContactId()).isNotNull(); // contact was created on-the-fly

		final I_C_BPartner bpartnerRecordAfter = load(bpartnerRecord.getC_BPartner_ID(), I_C_BPartner.class);
		assertThat(bpartnerRecordAfter.getValue()).isEqualTo("jsonBPartner.Code"); // was updated
		assertThat(bpartnerRecordAfter.getName()).isEqualTo("jsonBPartner.Name"); // was updated

		final I_C_BPartner_Location bpLocationRecordAfter = load(bpLocationRecord.getC_BPartner_Location_ID(), I_C_BPartner_Location.class);
		assertThat(bpLocationRecordAfter.getGLN()).isEqualTo("jsonBPartnerLocation.GLN");
		assertThat(bpLocationRecordAfter.getExternalId()).isEqualTo("jsonBPartnerLocation.ExternalId"); // was updated

		// the bpartner's AD_User was created on the fly
		final List<I_AD_User> bpartnerContactRecordsAfter = POJOLookupMap
				.get()
				.getRecords(I_AD_User.class, u -> u.getAD_User_ID() > 0/* exclude the system user */);
		assertThat(bpartnerContactRecordsAfter).hasSize(1);

		final I_AD_User bpartnerContactRecordAfter = bpartnerContactRecordsAfter.get(0);

		assertThat(bpartnerContactRecordAfter.getAD_User_ID()).isEqualTo(result.getContactId().getRepoId());
		assertThat(bpartnerContactRecordAfter.getAD_Org_ID()).isEqualTo(10);
		assertThat(bpartnerContactRecordAfter.getExternalId()).isEqualTo("jsonBPartnerContact.ExternalId");
		assertThat(bpartnerContactRecordAfter.getEMail()).isEqualTo("jsonBPartnerContact.Email");
		assertThat(bpartnerContactRecordAfter.getC_BPartner_ID()).isEqualTo(bpartnerRecord.getC_BPartner_ID());
	}
}
