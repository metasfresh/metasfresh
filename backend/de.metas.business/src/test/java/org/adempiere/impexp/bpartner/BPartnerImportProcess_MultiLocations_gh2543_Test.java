package org.adempiere.impexp.bpartner;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IMutable;
import org.adempiere.util.lang.Mutable;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_I_BPartner;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
 * Test case described in https://github.com/metasfresh/metasfresh/issues/2543
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class BPartnerImportProcess_MultiLocations_gh2543_Test
{
	private Properties ctx;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		ctx = Env.getCtx();
	}

	@Test
	public void testMultiplePartnerAndAddresses()
	{
		final List<I_I_BPartner> ibpartners = prepareImportMultipleBPartners();
		Assert.assertNotNull("list null", ibpartners);

		final BPartnerImportProcess importProcess = new BPartnerImportProcess();
		importProcess.setCtx(ctx);

		final IMutable<Object> state = new Mutable<>();

		ibpartners.forEach(importRecord -> importProcess.importRecord(state, importRecord));

		assertMultipleBpartnerImported(ibpartners);

	}

	private void assertMultipleBpartnerImported(final List<I_I_BPartner> ibpartners)
	{
		ibpartners.forEach(BPartnerImportTestHelper::assertImported);

		// check first partner imported
		// should have 2 contacts and one address
		assertFirstImportedBpartner(ibpartners);

		// check second partner imported
		// should have 1 contact and one address (the address is exactly the one from the first bpartner)
		{
			assertSecondImportedBpartner(ibpartners);

			// check location - is similar with the one from first partner, but should have different id
			final I_C_BPartner firstBpartner = ibpartners.get(0).getC_BPartner();
			final I_C_BPartner secondBPartner = ibpartners.get(2).getC_BPartner();
			final List<de.metas.adempiere.model.I_C_BPartner_Location> fbplocations = Services.get(IBPartnerDAO.class).retrieveBPartnerLocations(firstBpartner);
			final List<de.metas.adempiere.model.I_C_BPartner_Location> sbplocations = Services.get(IBPartnerDAO.class).retrieveBPartnerLocations(secondBPartner);
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
		Assert.assertTrue(ibpartners.get(0).getC_BPartner_ID() == ibpartners.get(1).getC_BPartner_ID());
		final I_C_BPartner firstBPartner = ibpartners.get(0).getC_BPartner();
		//
		// check user
		final List<de.metas.adempiere.model.I_AD_User> fusers = Services.get(IBPartnerDAO.class).retrieveContacts(firstBPartner);
		assertThat(fusers).isNotEmpty();
		assertThat(fusers).hasSize(2);
		fusers.forEach(user -> {
			Assert.assertTrue(user.isShipToContact_Default());
			Assert.assertFalse(user.isBillToContact_Default());
		});
		//
		// check bplocation
		final List<de.metas.adempiere.model.I_C_BPartner_Location> fbplocations = Services.get(IBPartnerDAO.class).retrieveBPartnerLocations(firstBPartner);
		Assert.assertTrue(!fbplocations.isEmpty());
		assertThat(fbplocations).hasSize(1);
		fbplocations.forEach(bplocation -> {
			Assert.assertTrue(bplocation.getC_Location_ID() > 0);
			Assert.assertTrue(bplocation.isBillToDefault());
			Assert.assertTrue(bplocation.isBillTo());
			Assert.assertFalse(bplocation.isShipToDefault());
			Assert.assertFalse(bplocation.isShipTo());
		});
	}

	private void assertSecondImportedBpartner(final List<I_I_BPartner> ibpartners)
	{
		final I_C_BPartner secondBPartner = ibpartners.get(2).getC_BPartner();
		//
		// check user
		final List<de.metas.adempiere.model.I_AD_User> users = Services.get(IBPartnerDAO.class).retrieveContacts(secondBPartner);
		assertThat(users).isNotEmpty();
		assertThat(users).hasSize(1);
		users.forEach(user -> {
			Assert.assertTrue(user.isShipToContact_Default());
			Assert.assertTrue(user.isBillToContact_Default());
		});
		//
		// check bplocation
		final List<de.metas.adempiere.model.I_C_BPartner_Location> bplocations = Services.get(IBPartnerDAO.class).retrieveBPartnerLocations(secondBPartner);
		Assert.assertTrue(!bplocations.isEmpty());
		assertThat(bplocations).hasSize(1);
		bplocations.forEach(bplocation -> {
			Assert.assertTrue(bplocation.getC_Location_ID() > 0);
			Assert.assertTrue(bplocation.isBillToDefault());
			Assert.assertTrue(bplocation.isBillTo());
			Assert.assertTrue(bplocation.isShipToDefault());
			Assert.assertTrue(bplocation.isShipTo());
		});
	}

	private void assertThirdImportedBpartner(final List<I_I_BPartner> ibpartners)
	{
		final I_C_BPartner thirdBPartner = ibpartners.get(3).getC_BPartner();
		//
		// check user
		final List<de.metas.adempiere.model.I_AD_User> users = Services.get(IBPartnerDAO.class).retrieveContacts(thirdBPartner);
		assertThat(users).isNotEmpty();
		assertThat(users).hasSize(1);
		users.forEach(user -> {
			Assert.assertTrue(user.isShipToContact_Default());
			Assert.assertTrue(user.isBillToContact_Default());
		});
		//
		// check bplocation
		final List<de.metas.adempiere.model.I_C_BPartner_Location> bplocations = Services.get(IBPartnerDAO.class).retrieveBPartnerLocations(thirdBPartner);
		assertThat(bplocations).isNotEmpty();
		assertThat(bplocations).hasSize(1);
		bplocations.forEach(bplocation -> {
			Assert.assertTrue(bplocation.getC_Location_ID() > 0);
			Assert.assertTrue(bplocation.isBillToDefault());
			Assert.assertTrue(bplocation.isBillTo());
			Assert.assertTrue(bplocation.isShipToDefault());
			Assert.assertTrue(bplocation.isShipTo());
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
	 * @param lines
	 * @task https://github.com/metasfresh/metasfresh/issues/2543
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
