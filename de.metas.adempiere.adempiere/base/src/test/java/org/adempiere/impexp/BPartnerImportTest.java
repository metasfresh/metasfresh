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
	public void testMultiplePartnerAndAddresses()
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

		assertMultipleBpartnerImported(ibpartners);

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

		return builder().setValue(value)
				.setFirstName(firstName)
				.setLastName(lastName)
				.setIsShipToContact(IsShipToContact)
				.setIsBillToContact(IsBillToContact)
				.setAddress1(address1)
				.setAddress2(address2)
				.setCity(city)
				.setRegion(region)
				.setCountryCode(countryCode)
				.setGroupValue(groupValue)
				.setIsShipToDefault(IsShipToDefault)
				.setIsBillToDefault(IsBillToDefault)
				.setLanguage(language)
				.build(ctx);

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
		records.add(setupFirstImportRecord());

		// create second line
		records.add(setupSecondImportRecord());

		// create third line
		records.add(setupThirdImportRecord());

		// create forth line
		records.add(setupForthImportrecord());

		return records;

	}
	
	/**Build first line
	 * <br>
	 * <code>value	 FirstName	LastName  IsShipToContact	IsBillToContact	address1	city	countryCode	groupValue	IsBillToDefault	IsShipToDefault	language</code><br>
	 * <code>G0022	 FNTest1	LNTest1	  Y					N				street 997	Berlin	DE			Standard	Y				N				de_CH</code><br>
	 * 
	 * @return
	 */
	private I_I_BPartner setupFirstImportRecord()
	{
		final String value = "G0022";
		final 		String firstName = "FNTest1";
		final String lastName = "LNTest1";
		final boolean IsShipToContact = true;
		final boolean IsBillToContact = false;
		final String address1 = "street 997";
		final String address2 = "";
		final String city = "Berlin";
		final String region = "";
		final String countryCode = "DE";
		final String groupValue = "Standard";
		final boolean IsBillToDefault = true;
		final boolean IsShipToDefault = false;
		final String language = "de_CH";
		return builder().setValue(value)
				.setFirstName(firstName)
				.setLastName(lastName)
				.setIsShipToContact(IsShipToContact)
				.setIsBillToContact(IsBillToContact)
				.setAddress1(address1)
				.setAddress2(address2)
				.setCity(city)
				.setRegion(region)
				.setCountryCode(countryCode)
				.setGroupValue(groupValue)
				.setIsShipToDefault(IsShipToDefault)
				.setIsBillToDefault(IsBillToDefault)
				.setLanguage(language)
				.build(ctx);
	}
	
	/**
	 * Build second line <br>
	 * <code>value	 FirstName	LastName  IsShipToContact	IsBillToContact	address1	city	countryCode	groupValue	IsBillToDefault	IsShipToDefault	language</code><br>
	 * <code>G0022	 FNTest2	LNTest2	  Y					N				street 997	Berlin	DE			Standard	Y				N				de_CH</code><br>
	 * @return
	 */
	private I_I_BPartner setupSecondImportRecord()
	{
		String value = "G0022";
		String firstName = "FNTest2";
		String lastName = "LNTest2";
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
		return builder().setValue(value)
				.setFirstName(firstName)
				.setLastName(lastName)
				.setIsShipToContact(IsShipToContact)
				.setIsBillToContact(IsBillToContact)
				.setAddress1(address1)
				.setAddress2(address2)
				.setCity(city)
				.setRegion(region)
				.setCountryCode(countryCode)
				.setGroupValue(groupValue)
				.setIsShipToDefault(IsShipToDefault)
				.setIsBillToDefault(IsBillToDefault)
				.setLanguage(language)
				.build(ctx);
	}
	
	/**
	 * build third line
	 * <code>value	 FirstName	LastName  IsShipToContact	IsBillToContact	address1	city	countryCode	groupValue	IsBillToDefault	IsShipToDefault	language</code><br>
	 * <code>G0023	 FNTest3	LNTest3	  Y					Y				street 997	Berlin	DE			Standard	Y				Y				de_CH</code><br>
	 * @return
	 */
	private I_I_BPartner setupThirdImportRecord()
	{
		final String value = "G0023";
		final String firstName = "FNTest3";
		final String lastName = "LNTest3";
		final boolean IsShipToContact = true;
		final boolean IsBillToContact = true;
		final String address1 = "street 997";
		final String address2 = "";
		final String city = "Berlin";
		final String region = "";
		final String countryCode = "DE";
		final String groupValue = "Standard";
		final boolean IsBillToDefault = true;
		final boolean IsShipToDefault = true;
		final String language = "de_CH";
		return builder().setValue(value)
				.setFirstName(firstName)
				.setLastName(lastName)
				.setIsShipToContact(IsShipToContact)
				.setIsBillToContact(IsBillToContact)
				.setAddress1(address1)
				.setAddress2(address2)
				.setCity(city)
				.setRegion(region)
				.setCountryCode(countryCode)
				.setGroupValue(groupValue)
				.setIsShipToDefault(IsShipToDefault)
				.setIsBillToDefault(IsBillToDefault)
				.setLanguage(language)
				.build(ctx);
	}
	
	/**
	 * Build forth line
	 * <code>value	 FirstName	LastName  IsShipToContact	IsBillToContact	address1	city	countryCode	groupValue	IsBillToDefault	IsShipToDefault	language</code><br>
	 * <code>G0024	 FNTest4	LNTest4	  Y					Y				street 998	Bonn	DE			Standard	Y				Y				de_CH</code><br>
	 * @return
	 */
	private I_I_BPartner setupForthImportrecord()
	{
		final String value = "G0024";
		final String firstName = "FNTest4";
		final String lastName = "LNTest4";
		final boolean IsShipToContact = true;
		final boolean IsBillToContact = true;
		final String address1 = "street 998";
		final String address2 = "";
		final String city = "Bonn";
		final String region = "";
		final String countryCode = "DE";
		final String groupValue = "Standard";
		final boolean IsBillToDefault = true;
		final boolean IsShipToDefault = true;
		final String language = "de_CH";
		return builder().setValue(value)
				.setFirstName(firstName)
				.setLastName(lastName)
				.setIsShipToContact(IsShipToContact)
				.setIsBillToContact(IsBillToContact)
				.setAddress1(address1)
				.setAddress2(address2)
				.setCity(city)
				.setRegion(region)
				.setCountryCode(countryCode)
				.setGroupValue(groupValue)
				.setIsShipToDefault(IsShipToDefault)
				.setIsBillToDefault(IsBillToDefault)
				.setLanguage(language)
				.build(ctx);
	}

	private void assertMultipleBpartnerImported(@NonNull final List<I_I_BPartner> ibpartners)
	{
		ibpartners.forEach(imporRecord -> asserImported(imporRecord));

		// check first partner imported
		// should have 2 contacts and one address
		assertFirstImportedBpartner(ibpartners);

		// check second partner imported
		// should have 1 contact and one address (the address is exactly the one from the first bpartner)
		{
			assertSecondImportedBpartner(ibpartners.get(2).getC_BPartner());

			// check location - is similar with the one from first partner, but should have different id
			final I_C_BPartner firstBpartner = ibpartners.get(0).getC_BPartner();
			final I_C_BPartner secondBPartner = ibpartners.get(2).getC_BPartner();
			final List<de.metas.adempiere.model.I_C_BPartner_Location> fbplocations = Services.get(IBPartnerDAO.class).retrieveBPartnerLocations(firstBpartner);
			final List<de.metas.adempiere.model.I_C_BPartner_Location> sbplocations = Services.get(IBPartnerDAO.class).retrieveBPartnerLocations(secondBPartner);
			Assert.assertTrue(fbplocations.get(0).getC_Location_ID() != sbplocations.get(0).getC_Location_ID());
		}

		// check third partner imported
		// should have 1 contact and one address
		{
			assertThirdImportedBpartner(ibpartners.get(3).getC_BPartner());
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

	private void assertFirstImportedBpartner(@NonNull final List<I_I_BPartner> ibpartners)
	{
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
	}

	private void assertSecondImportedBpartner(@NonNull final I_C_BPartner secondBPartner)
	{
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

	private void assertThirdImportedBpartner(@NonNull final I_C_BPartner thirdBPartner)
	{
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

	private static final IBPartnerBuilder builder()
	{
		return new IBPartnerBuilder();
	}

	private static class IBPartnerBuilder
	{
		private String value;
		private String firstName;
		private String lastName;
		private boolean IsShipToContact;
		private boolean IsBillToContact;
		private String address1;
		private String address2;
		private String city;
		private String region;
		private String countryCode;
		private String groupValue;
		private boolean IsBillToDefault;
		private boolean IsShipToDefault;
		private String language;

		private IBPartnerBuilder()
		{
			super();
		}

		public I_I_BPartner build(final Properties ctx)
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

		public IBPartnerBuilder setValue(String value)
		{
			this.value = value;
			return this;
		}

		public IBPartnerBuilder setFirstName(String firstName)
		{
			this.firstName = firstName;
			return this;
		}

		public IBPartnerBuilder setLastName(String lastName)
		{
			this.lastName = lastName;
			return this;
		}

		public IBPartnerBuilder setIsShipToContact(boolean isShipToContact)
		{
			IsShipToContact = isShipToContact;
			return this;
		}

		public IBPartnerBuilder setIsBillToContact(boolean isBillToContact)
		{
			IsBillToContact = isBillToContact;
			return this;
		}

		public IBPartnerBuilder setAddress1(String address1)
		{
			this.address1 = address1;
			return this;
		}

		public IBPartnerBuilder setAddress2(String address2)
		{
			this.address2 = address2;
			return this;
		}

		public IBPartnerBuilder setCity(String city)
		{
			this.city = city;
			return this;
		}

		public IBPartnerBuilder setRegion(String region)
		{
			this.region = region;
			return this;
		}

		public IBPartnerBuilder setCountryCode(String countryCode)
		{
			this.countryCode = countryCode;
			return this;
		}

		public IBPartnerBuilder setGroupValue(String groupValue)
		{
			this.groupValue = groupValue;
			return this;
		}

		public IBPartnerBuilder setIsBillToDefault(boolean isBillToDefault)
		{
			IsBillToDefault = isBillToDefault;
			return this;
		}

		public IBPartnerBuilder setIsShipToDefault(boolean isShipToDefault)
		{
			IsShipToDefault = isShipToDefault;
			return this;
		}

		public IBPartnerBuilder setLanguage(String language)
		{
			this.language = language;
			return this;
		}

	}

}
