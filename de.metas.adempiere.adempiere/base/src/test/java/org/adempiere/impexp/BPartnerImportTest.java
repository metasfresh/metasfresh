/**
 * 
 */
package org.adempiere.impexp;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IMutable;
import org.adempiere.util.lang.Mutable;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_I_BPartner;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class BPartnerImportTest
{
	private static final transient Logger logger = LogManager.getLogger(BPartnerImportTest.class);

	private static final int C_Country_ID = 101;

	private Properties ctx;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		this.ctx = Env.getCtx();
	}

	@Test
	public void testSingleBpartner() throws Exception
	{
		I_I_BPartner ibpartner = prepareImportSingleBPartner();
		Assert.assertNotNull("bpartner null", ibpartner);

		final BPartnerImportProcess importProcess = new BPartnerImportProcess();
		importProcess.setCtx(ctx);
		final IMutable<Object> state = new Mutable<>();
		//
		importProcess.importRecord(state, ibpartner);
		//
		asserImported(ibpartner);
		//
		// check user
		final I_AD_User user = ibpartner.getAD_User();
		Assert.assertTrue(user.getLastname().equals(ibpartner.getLastname()));
		Assert.assertTrue(user.getFirstname().equals(ibpartner.getFirstname()));
		Assert.assertTrue(user.isShipToContact_Default());
		Assert.assertFalse(user.isBillToContact_Default());
		//
		// check bplocation
		final I_C_BPartner_Location bplocation = ibpartner.getC_BPartner_Location();
		Assert.assertTrue(bplocation.getC_Location_ID() > 0);
		Assert.assertTrue(bplocation.isBillToDefault());
		Assert.assertTrue(bplocation.isBillTo());
		Assert.assertFalse(bplocation.isShipToDefault());
		Assert.assertFalse(bplocation.isShipTo());

	}

	@Test
	public void tesMultiplePartnerAndAddresses()
	{
		final List<I_I_BPartner> ibpartners = prepareImportMultipleBPartners();
		Assert.assertNotNull("list null", ibpartners);

		final BPartnerImportProcess importProcess = new BPartnerImportProcess();
		importProcess.setCtx(ctx);

		final IMutable<Object> state = new Mutable<>();

		ibpartners.forEach(importRecord -> {
			try
			{
				importProcess.importRecord(state, importRecord);
			}
			catch (Exception e)
			{
				logger.warn(e.getMessage());
			}
		});

		ibpartners.forEach(imporRecord -> asserImported(imporRecord));

		// check first partner imported
		// should have 2 contacts and one address
		Assert.assertTrue(ibpartners.get(0).getC_BPartner_ID() == ibpartners.get(1).getC_BPartner_ID());
		final I_C_BPartner firstBPartner = ibpartners.get(0).getC_BPartner();
		//
		// check user
		final List<de.metas.adempiere.model.I_AD_User> fusers = Services.get(IBPartnerDAO.class).retrieveContacts(firstBPartner);
		Assert.assertTrue(!fusers.isEmpty());
		Assert.assertTrue(fusers.size() == 2);
		fusers.forEach(user -> {
			Assert.assertTrue(user.isShipToContact_Default());
			Assert.assertFalse(user.isBillToContact_Default());
		});
		//
		// check bplocation
		final List<de.metas.adempiere.model.I_C_BPartner_Location> fbplocations = Services.get(IBPartnerDAO.class).retrieveBPartnerLocations(firstBPartner);
		Assert.assertTrue(!fbplocations.isEmpty());
		Assert.assertTrue(fbplocations.size() == 1);
		fbplocations.forEach(bplocation -> {
			Assert.assertTrue(bplocation.getC_Location_ID() > 0);
			Assert.assertTrue(bplocation.isBillToDefault());
			Assert.assertTrue(bplocation.isBillTo());
			Assert.assertFalse(bplocation.isShipToDefault());
			Assert.assertFalse(bplocation.isShipTo());
		});

		// check second partner imported
		// should have 1 contact and one address (the address is exactly the one from the first bpartner)
		{
			final I_C_BPartner secondBPartner = ibpartners.get(2).getC_BPartner();
			//
			// check user
			final List<de.metas.adempiere.model.I_AD_User> users = Services.get(IBPartnerDAO.class).retrieveContacts(secondBPartner);
			Assert.assertTrue(!users.isEmpty());
			Assert.assertTrue(users.size() == 1);
			users.forEach(user -> {
				Assert.assertTrue(user.isShipToContact_Default());
				Assert.assertTrue(user.isBillToContact_Default());
			});
			//
			// check bplocation
			final List<de.metas.adempiere.model.I_C_BPartner_Location> bplocations = Services.get(IBPartnerDAO.class).retrieveBPartnerLocations(secondBPartner);
			Assert.assertTrue(!bplocations.isEmpty());
			Assert.assertTrue(bplocations.size() == 1);
			bplocations.forEach(bplocation -> {
				Assert.assertTrue(bplocation.getC_Location_ID() > 0);
				Assert.assertTrue(bplocation.isBillToDefault());
				Assert.assertTrue(bplocation.isBillTo());
				Assert.assertTrue(bplocation.isShipToDefault());
				Assert.assertTrue(bplocation.isShipTo());
			});
		}
		
		// check third partner imported
		// should have 1 contact and one address
		{
			final I_C_BPartner thirdBPartner = ibpartners.get(3).getC_BPartner();
			//
			// check user
			final List<de.metas.adempiere.model.I_AD_User> users = Services.get(IBPartnerDAO.class).retrieveContacts(thirdBPartner);
			Assert.assertTrue(!users.isEmpty());
			Assert.assertTrue(users.size() == 1);
			users.forEach(user -> {
				Assert.assertTrue(user.isShipToContact_Default());
				Assert.assertTrue(user.isBillToContact_Default());
			});
			//
			// check bplocation
			final List<de.metas.adempiere.model.I_C_BPartner_Location> bplocations = Services.get(IBPartnerDAO.class).retrieveBPartnerLocations(thirdBPartner);
			Assert.assertTrue(!bplocations.isEmpty());
			Assert.assertTrue(bplocations.size() == 1);
			bplocations.forEach(bplocation -> {
				Assert.assertTrue(bplocation.getC_Location_ID() > 0);
				Assert.assertTrue(bplocation.isBillToDefault());
				Assert.assertTrue(bplocation.isBillTo());
				Assert.assertTrue(bplocation.isShipToDefault());
				Assert.assertTrue(bplocation.isShipTo());
			});
		}

	}

	private void asserImported(@NonNull final I_I_BPartner ibpartner)
	{
		Assert.assertTrue(ibpartner.getC_BPartner_ID() > 0);
		Assert.assertTrue(ibpartner.getAD_User_ID() > 0);
		Assert.assertTrue(ibpartner.getC_BPartner_Location_ID() > 0);

		final I_C_BPartner bpartner = ibpartner.getC_BPartner();
		Assert.assertNotNull(bpartner.getAD_Language());
	}

	/**
	 * Build a test case for import<br>
	 * <br>
	 * <code>value	 FirstName	LastName  IsShipToContact	IsBillToContact	address1	city	countryCode	groupValue	IsBillToDefault	IsShipToDefault	language</code><br>
	 * <code>G0022	 FNTest1	LNTest1	  Y					N				street 997	Berlin	DE			Standard	Y				N				de_CH</code><br>
	 * 
	 * @param lines
	 * @return
	 */
	private I_I_BPartner prepareImportSingleBPartner()
	{
		// create first line
		String value = "G0022";
		String firstName = "FNTest1";
		String lastName = "LNTest1";
		boolean IsShipToContact = true;
		boolean IsBillToContact = false;
		String address1 = "street 997";
		String address2 = "";
		String city = "Berlin";
		String region = "";
		String countryCode = "DE";
		String groupValue = "Standard";
		boolean IsShipToDefault = false;
		boolean IsBillToDefault = true;
		String language = "de_CH";
		return createIBPartner(value, firstName, lastName, address1, address2, city, region, countryCode, groupValue, language, IsShipToContact, IsBillToContact, IsBillToDefault, IsShipToDefault);
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
	 * @return
	 */
	private List<I_I_BPartner> prepareImportMultipleBPartners()
	{
		final List<I_I_BPartner> records = new ArrayList<>();

		// create first line
		String value = "G0022";
		String firstName = "FNTest1";
		String lastName = "LNTest1";
		boolean IsShipToContact = true;
		boolean IsBillToContact = false;
		String address1 = "street 997";
		String address2 = "";
		String city = "Berlin";
		String region = "";
		String countryCode = "DE";
		String groupValue = "Standard";
		boolean IsBillToDefault = true;
		boolean IsShipToDefault = false;
		String language = "de_CH";
		I_I_BPartner ibpartner = createIBPartner(value, firstName, lastName, address1, address2, city, region, countryCode, groupValue, language, IsShipToContact, IsBillToContact, IsBillToDefault, IsShipToDefault);
		records.add(ibpartner);

		// create second line
		value = "G0022";
		firstName = "FNTest2";
		lastName = "LNTest2";
		IsShipToContact = true;
		IsBillToContact = false;
		address1 = "street 997";
		address2 = "";
		city = "Berlin";
		region = "";
		countryCode = "DE";
		groupValue = "Standard";
		IsBillToDefault = true;
		IsShipToDefault = false;
		language = "de_CH";
		ibpartner = createIBPartner(value, firstName, lastName, address1, address2, city, region, countryCode, groupValue, language, IsShipToContact, IsBillToContact, IsBillToDefault, IsShipToDefault);
		records.add(ibpartner);

		// create third line
		value = "G0023";
		firstName = "FNTest3";
		lastName = "LNTest3";
		IsShipToContact = true;
		IsBillToContact = true;
		address1 = "street 997";
		address2 = "";
		city = "Berlin";
		region = "";
		countryCode = "DE";
		groupValue = "Standard";
		IsBillToDefault = true;
		IsShipToDefault = true;
		language = "de_CH";
		ibpartner = createIBPartner(value, firstName, lastName, address1, address2, city, region, countryCode, groupValue, language, IsShipToContact, IsBillToContact, IsBillToDefault, IsShipToDefault);
		records.add(ibpartner);

		// create forth line
		value = "G0024";
		firstName = "FNTest4";
		lastName = "LNTest4";
		IsShipToContact = true;
		IsBillToContact = true;
		address1 = "street 998";
		address2 = "";
		city = "Bonn";
		region = "";
		countryCode = "DE";
		groupValue = "Standard";
		IsBillToDefault = true;
		IsShipToDefault = true;
		language = "de_CH";
		ibpartner = createIBPartner(value, firstName, lastName, address1, address2, city, region, countryCode, groupValue, language, IsShipToContact, IsBillToContact, IsBillToDefault, IsShipToDefault);
		records.add(ibpartner);

		return records;

	}

	private I_I_BPartner createIBPartner(final String value, final String firstName, final String lastName, final String address1, final String address2,
			final String city, final String region, final String countryCode, final String groupValue, final String language, final boolean IsShipToContact,
			final boolean IsBillToContact, final boolean IsBillToDefault, final boolean IsShipToDefault)
	{
		final I_I_BPartner ibpartner = InterfaceWrapperHelper.create(ctx, I_I_BPartner.class, ITrx.TRXNAME_None);
		ibpartner.setValue(value);
		ibpartner.setFirstname(firstName);
		ibpartner.setLastname(lastName);
		ibpartner.setAddress1(address1);
		ibpartner.setAddress2(address2);
		ibpartner.setCity(city);
		ibpartner.setRegionName(region);
		ibpartner.setGroupValue(groupValue);
		ibpartner.setCountryCode(countryCode);
		ibpartner.setAD_Language(language);
		ibpartner.setIsBillToContact_Default(IsBillToContact);
		ibpartner.setIsShipToContact_Default(IsShipToContact);
		ibpartner.setIsBillToDefault(IsBillToDefault);
		ibpartner.setIsShipToDefault(IsShipToDefault);
		ibpartner.setC_Country_ID(C_Country_ID);
		InterfaceWrapperHelper.save(ibpartner);

		return ibpartner;
	}

}
