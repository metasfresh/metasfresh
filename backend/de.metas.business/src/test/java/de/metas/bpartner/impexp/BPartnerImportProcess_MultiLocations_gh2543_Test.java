package de.metas.bpartner.impexp;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.impexp.ImportRecordsAsyncExecutor;
import de.metas.impexp.MockedImportRecordsAsyncExecutor;
import de.metas.impexp.format.ImportTableDescriptorRepository;
import de.metas.impexp.processing.DBFunctionsRepository;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.IMutable;
import org.adempiere.util.lang.Mutable;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_I_BPartner;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * Test case described in <a href="https://github.com/metasfresh/metasfresh/issues/2543">this issue</a>
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public class BPartnerImportProcess_MultiLocations_gh2543_Test
{
	private Properties ctx;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		ctx = Env.getCtx();

		SpringContextHolder.registerJUnitBean(new DBFunctionsRepository());
		SpringContextHolder.registerJUnitBean(new ImportTableDescriptorRepository());
		SpringContextHolder.registerJUnitBean(ImportRecordsAsyncExecutor.class, new MockedImportRecordsAsyncExecutor());

		Services.registerService(IBPartnerBL.class, new BPartnerBL(new UserRepository()));
	}

	@Test
	public void testMultiplePartnerAndAddresses()
	{
		final List<I_I_BPartner> ibpartners = prepareImportMultipleBPartners();
		assertThat(ibpartners).as("list null").isNotNull();

		final BPartnerImportProcess importProcess = new BPartnerImportProcess();
		importProcess.setCtx(ctx);

		final IMutable<Object> state = new Mutable<>();

		ibpartners.forEach(importRecord -> importProcess.importRecord(state, importRecord, false /* isInsertOnly */));

		assertMultipleBpartnerImported(ibpartners);
	}

	private void assertMultipleBpartnerImported(final List<I_I_BPartner> ibpartners)
	{
		final IBPartnerDAO partnerDAO = Services.get(IBPartnerDAO.class);

		ibpartners.forEach(BPartnerImportTestHelper::assertImported);

		// check first partner imported
		// should have 2 contacts and one address
		assertFirstImportedBpartner(ibpartners);

		// check second partner imported
		// should have 1 contact and one address (the address is exactly the one from the first bpartner)
		{
			assertSecondImportedBpartner(ibpartners);

			// check location - is similar with the one from first partner, but should have different id
			final I_C_BPartner firstBpartner = partnerDAO.getById(BPartnerId.ofRepoId(ibpartners.get(0).getC_BPartner_ID()));
			final I_C_BPartner secondBPartner = partnerDAO.getById(BPartnerId.ofRepoId(ibpartners.get(2).getC_BPartner_ID()));

			final List<org.compiere.model.I_C_BPartner_Location> fbplocations = Services.get(IBPartnerDAO.class).retrieveBPartnerLocations(firstBpartner);
			final List<org.compiere.model.I_C_BPartner_Location> sbplocations = Services.get(IBPartnerDAO.class).retrieveBPartnerLocations(secondBPartner);
			assertThat(fbplocations.get(0).getC_Location_ID()).isNotEqualTo(sbplocations.get(0).getC_Location_ID());
		}

		// check third partner imported
		// should have 1 contact and one address
		{
			assertThirdImportedBpartner(ibpartners);
		}
	}

	private void assertFirstImportedBpartner(final List<I_I_BPartner> ibpartners)
	{
		final IBPartnerDAO partnerDAO = Services.get(IBPartnerDAO.class);

		assertThat(ibpartners.get(0).getC_BPartner_ID()).isEqualTo(ibpartners.get(1).getC_BPartner_ID());
		final I_C_BPartner firstBPartner = partnerDAO.getById(BPartnerId.ofRepoIdOrNull(ibpartners.get(0).getC_BPartner_ID()));
		//
		// check user
		final List<I_AD_User> fusers = Services.get(IBPartnerDAO.class).retrieveContacts(firstBPartner);
		assertThat(fusers).hasSize(2);
		fusers.forEach(user -> {
			assertThat(user.isShipToContact_Default()).isTrue();
			assertThat(user.isBillToContact_Default()).isFalse();
		});
		//
		// check bplocation
		final List<I_C_BPartner_Location> fbplocations = Services.get(IBPartnerDAO.class).retrieveBPartnerLocations(firstBPartner);
		assertThat(fbplocations).hasSize(1);
		fbplocations.forEach(bplocation -> {
			assertThat(bplocation.getC_Location_ID()).isGreaterThan(0);
			assertThat(bplocation.isBillToDefault()).isTrue();
			assertThat(bplocation.isBillTo()).isTrue();
			assertThat(bplocation.isShipToDefault()).isFalse();
			assertThat(bplocation.isShipTo()).isFalse();
		});
	}

	private void assertSecondImportedBpartner(final List<I_I_BPartner> ibpartners)
	{
		final IBPartnerDAO partnerDAO = Services.get(IBPartnerDAO.class);

		final I_C_BPartner secondBPartner = partnerDAO.getById(BPartnerId.ofRepoIdOrNull(ibpartners.get(2).getC_BPartner_ID()));
		//
		// check user
		final List<I_AD_User> users = Services.get(IBPartnerDAO.class).retrieveContacts(secondBPartner);
		assertThat(users).hasSize(1);
		users.forEach(user -> {
			assertThat(user.isShipToContact_Default()).isTrue();
			assertThat(user.isBillToContact_Default()).isTrue();
		});
		//
		// check bplocation
		final List<I_C_BPartner_Location> bplocations = Services.get(IBPartnerDAO.class).retrieveBPartnerLocations(secondBPartner);
		assertThat(bplocations).hasSize(1);
		bplocations.forEach(bplocation -> {
			assertThat(bplocation.getC_Location_ID()).isGreaterThan(0);
			assertThat(bplocation.isBillToDefault()).isTrue();
			assertThat(bplocation.isBillTo()).isTrue();
			assertThat(bplocation.isShipToDefault()).isTrue();
			assertThat(bplocation.isShipTo()).isTrue();
		});
	}

	private void assertThirdImportedBpartner(final List<I_I_BPartner> ibpartners)
	{
		final IBPartnerDAO partnerDAO = Services.get(IBPartnerDAO.class);

		final I_C_BPartner thirdBPartner = partnerDAO.getById(BPartnerId.ofRepoIdOrNull(ibpartners.get(3).getC_BPartner_ID()));
		//
		// check user
		final List<I_AD_User> users = Services.get(IBPartnerDAO.class).retrieveContacts(thirdBPartner);
		assertThat(users).hasSize(1);
		users.forEach(user -> {
			assertThat(user.isShipToContact_Default()).isTrue();
			assertThat(user.isBillToContact_Default()).isTrue();
		});
		//
		// check bplocation
		final List<I_C_BPartner_Location> bplocations = Services.get(IBPartnerDAO.class).retrieveBPartnerLocations(thirdBPartner);
		assertThat(bplocations).hasSize(1);
		bplocations.forEach(bplocation -> {
			assertThat(bplocation.getC_Location_ID()).isGreaterThan(0);
			assertThat(bplocation.isBillToDefault()).isTrue();
			assertThat(bplocation.isBillTo()).isTrue();
			assertThat(bplocation.isShipToDefault()).isTrue();
			assertThat(bplocation.isShipTo()).isTrue();
		});
	}

	/**
	 * Build a test case for import<br>
	 * <br>
	 * <code>value	 FirstName	LastName  IsShipToContact	IsBillToContact	address1	city	countryCode	groupValue	IsBillToDefault	IsShipToDefault	language</code><br>
	 * <code>G0022	 FNTest1	LNTest1	  Y					N				street 997	Berlin	DE			Standard	Y				N				de_CH</code><br>
	 * <code>G0022	 FNTest2	LNTest2	  Y					N				street 997	Berlin	DE			Standard	Y				N				de_CH</code><br>
	 * <code>G0023	 FNTest3	LNTest3	  Y					Y				street 997	Berlin	DE			Standard	Y				Y				de_CH</code><br>
	 * <code>G0024	 FNTest4	LNTest4	  Y					Y				street 998	Bonn	DE			Standard	Y				Y				de_CH</code><br>
	 *
	 * @implSpec <a href="https://github.com/metasfresh/metasfresh/issues/2543">...</a>
	 */
	private List<I_I_BPartner> prepareImportMultipleBPartners()
	{
		final List<I_I_BPartner> records = new ArrayList<>();

		records.add(IBPartnerFactory.builder()
				.ctx(ctx)
				.value("G0022")
				.firstName("FNTest1").lastName("LNTest1")
				.shipToContact(true).billToContact(false)
				.address1("street 997").address2("").city("Berlin").region("").countryCode("DE")
				.shipToDefaultAddress(false).billToDefaultAddress(true)
				.groupValue("Standard")
				.language("de_CH")
				.build());

		records.add(IBPartnerFactory.builder()
				.ctx(ctx)
				.value("G0022")
				.firstName("FNTest2").lastName("LNTest2")
				.shipToContact(true).billToContact(false)
				.address1("street 997").address2("").city("Berlin").region("").countryCode("DE")
				.shipToDefaultAddress(false).billToDefaultAddress(true)
				.groupValue("Standard")
				.language("de_CH")
				.build());

		records.add(IBPartnerFactory.builder()
				.ctx(ctx)
				.value("G0023")
				.firstName("FNTest3").lastName("LNTest3")
				.shipToContact(true).billToContact(true)
				.address1("street 997").address2("").city("Berlin").region("").countryCode("DE")
				.shipToDefaultAddress(true).billToDefaultAddress(true)
				.groupValue("Standard")
				.language("de_CH")
				.build());

		records.add(IBPartnerFactory.builder()
				.ctx(ctx)
				.value("G0024")
				.firstName("FNTest4").lastName("LNTest4")
				.shipToContact(true).billToContact(true)
				.address1("street 998").address2("").city("Bonn").region("").countryCode("DE")
				.shipToDefaultAddress(true).billToDefaultAddress(true)
				.groupValue("Standard")
				.language("de_CH")
				.build());

		return records;
	}
}
