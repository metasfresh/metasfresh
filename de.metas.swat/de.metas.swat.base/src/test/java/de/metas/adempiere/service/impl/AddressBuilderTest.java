package de.metas.adempiere.service.impl;

import org.adempiere.ad.trx.api.ITrx;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import org.adempiere.bpartner.service.IBPartnerBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Country_Sequence;
import org.compiere.model.I_C_Greeting;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.metas.adempiere.model.I_AD_User;
import de.metas.adempiere.model.I_C_BPartner_Location;
import de.metas.adempiere.model.I_C_Location;
import de.metas.interfaces.I_C_BPartner;

public class AddressBuilderTest
{
	private AddressBuilder builder;
	I_AD_Org org;

	@BeforeClass
	public static void staticInit()
	{
		AdempiereTestHelper.get().staticInit();
	}

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		org = prepareAD_Org();

		builder = new AddressBuilder(org);
	}

	@Test
	public void test_buildAddressString_0010()
	{
		final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", false, "",
				prepareCountry("Germany", "@A1@ @A2@ @C@ @CO@"));
		final boolean isLocalAddress = true;
		final String bPartnerBlock = null;
		final String userBlock = null;
		Assert.assertEquals(
				"LOCAL: addr1\naddr2\nCity1\nGermany",
				builder
						.setLanguage("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));
	}

	@Test
	public void test_buildAddressString_0020()
	{
		final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", false, "",
				prepareCountry("Country1", "@A1@ @A2@ @C@ @CO@"));
		final boolean isLocalAddress = false;
		final String bPartnerBlock = null;
		final String userBlock = null;

		Assert.assertEquals(
				"addr1\naddr2\nCity1\nCountry1",
				builder
						.setLanguage("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock)

		);
	}

	@Test
	public void test_buildAddressString_0030()
	{
		final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", false, "",
				prepareCountry("Country1", "@A1@ @A2@ @C@ @R@ @CO@"));
		boolean isLocalAddress = false;
		final String bPartnerBlock = null;
		final String userBlock = null;
		Assert.assertEquals(
				"addr1\naddr2\nCity1\nRegion1 Country1",
				builder
						.setLanguage("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));

		isLocalAddress = true;
		Assert.assertEquals(
				"LOCAL: addr1\naddr2\nCity1\nRegion1 Country1",
				builder
						.setLanguage("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));
	}

	@Test
	public void test_buildAddressString_0040_UK_Address()
	{
		final I_C_Location location = prepareLocation("street", "12", null, null, "London", null, "121212", false, "",
				prepareCountry("UK", "@A1@ @A2@@CR@@P@@CR@@C@ @CO@"));
		boolean isLocalAddress = false;
		final String bPartnerBlock = null;
		final String userBlock = null;
		Assert.assertEquals(
				"street 12\n121212\nLondon UK",
				builder
						.setLanguage("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));

		isLocalAddress = true;
		Assert.assertEquals(
				"LOCAL: " + "street 12\n121212\nLondon UK",
				builder
						.setLanguage("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));
	}

	@Test
	public void test_buildAddressString_0050_DE_Address()
	{
		final I_C_Location location = prepareLocation("street", "12", null, null, "Berlin", null, "121212", false, "",
				prepareCountry("Deutschland", "@BP@ @A1@ @A2@ @A3@ D-@P@ @C@ @CO@"));
		boolean isLocalAddress = false;
		final String bPartnerBlock = "BPartner1";
		final String userBlock = "Contact1";

		Assert.assertEquals(
				"BPartner1\nstreet\n12\nD-121212 Berlin\nDeutschland",
				builder
						.setLanguage("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));

		isLocalAddress = true;
		Assert.assertEquals(
				"LOCAL: " + "\nBPartner1\nstreet\n12\nD-121212 Berlin\nDeutschland",
				builder
						.setLanguage("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));
	}

	@Test
	public void test_buildAddressString_0060()
	{
		final I_C_Location location = prepareLocation("street", "12", null, null, "Berlin", null, "121212", false, "",
				prepareCountry("Deutschland", "@BP@ @CON@ @A1@ @A2@ @A3@ D-@P@ @C@ @CO@"));
		boolean isLocalAddress = false;
		final String bPartnerBlock = "BPartner1";
		final String userBlock = "Contact1";
		Assert.assertEquals(
				"BPartner1\nContact1\nstreet\n12\nD-121212 Berlin\nDeutschland",
				builder
						.setLanguage("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));

		isLocalAddress = true;
		Assert.assertEquals(
				"LOCAL: " + "\nBPartner1\nContact1\nstreet\n12\nD-121212 Berlin\nDeutschland",
				builder
						.setLanguage("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));
	}

	/**
	 * task 04121 <br>
	 * check if the word for brackets is printed if the variables are not empty
	 */
	@Test
	public void test_buildAddressString_0070()
	{
		final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", false, "",
				prepareCountry("Country1", "@A1@ @A2@ @P@ @C@ (Region @R@) @CO@"));
		boolean isLocalAddress = false;
		final String bPartnerBlock = null;
		final String userBlock = null;

		Assert.assertEquals(
				"addr1\naddr2\n121212 City1\nRegion Region1 Country1",
				builder
						.setLanguage("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));

		isLocalAddress = true;

		Assert.assertEquals(
				"LOCAL: addr1\naddr2\n121212 City1\nRegion Region1 Country1",
				builder
						.setLanguage("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));
	}

	/**
	 * task 04121 <br>
	 * check if the brackets are escaped if we used escape char \
	 */
	@Test
	public void test_buildAddressString_0080_EscapeBrackets()
	{
		final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", false, "",
				prepareCountry("Country1", "@A1@ @A2@ @P@ @C@ \\(Region @R@\\) @CO@"));
		boolean isLocalAddress = false;
		final String bPartnerBlock = null;
		final String userBlock = null;

		Assert.assertEquals(
				"addr1\naddr2\n121212 City1\n(Region Region1) Country1",
				builder
						.setLanguage("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));

		isLocalAddress = true;

		Assert.assertEquals(
				"LOCAL: addr1\naddr2\n121212 City1\n(Region Region1) Country1",
				builder
						.setLanguage("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));
	}

	/**
	 * task 04121 <br>
	 * check if the word inside brackets is not printed if the variables empty
	 */
	@Test
	public void test_buildAddressString_0090_EmptyVariable()
	{
		final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "", "121212", false, "",
				prepareCountry("Country1", "@A1@ @A2@ @P@ @C@ (Region @R@) @CO@"));
		boolean isLocalAddress = false;
		final String bPartnerBlock = null;
		final String userBlock = null;

		Assert.assertEquals(
				"addr1\naddr2\n121212 City1\nCountry1",
				builder
						.setLanguage("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));

		isLocalAddress = true;

		Assert.assertEquals(
				"LOCAL: addr1\naddr2\n121212 City1\nCountry1",
				builder
						.setLanguage("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));
	}

	/**
	 * task 04121 <br>
	 * check if the brackets are escaped
	 */
	@Test
	public void test_buildAddressString_0100_EscapeBrackets_EmptyVariable()
	{
		final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "", "121212", false, "",
				prepareCountry("Country1", "@A1@ @A2@ @P@ @C@ \\(Region @R@\\) @CO@"));
		boolean isLocalAddress = false;
		final String bPartnerBlock = null;
		final String userBlock = null;

		Assert.assertEquals(
				"addr1\naddr2\n121212 City1\n(Region ) Country1",
				builder
						.setLanguage("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));

		isLocalAddress = true;

		Assert.assertEquals(
				"LOCAL: addr1\naddr2\n121212 City1\n(Region ) Country1",
				builder
						.setLanguage("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));
	}

	@Test
	public void test_buildAddressString_0110()
	{
		final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", true, "",
				prepareCountry("Country1", "@A1@ @A2@ @P@ @C@ (Postfach @PB@) @CO@"));
		boolean isLocalAddress = false;
		final String bPartnerBlock = null;
		final String userBlock = null;

		Assert.assertEquals(
				"addr1\naddr2\n121212 City1\nPostfach \nCountry1",
				builder
						.setLanguage("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));

		isLocalAddress = true;

		Assert.assertEquals(
				"LOCAL: addr1\naddr2\n121212 City1\nPostfach \nCountry1",
				builder
						.setLanguage("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));
	}

	@Test
	public void test_buildAddressString_0120()
	{
		final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", true, "1234",
				prepareCountry("Country1", "@A1@ @A2@ @P@ @C@ (Postfach @PB@) @CO@"));
		boolean isLocalAddress = false;
		final String bPartnerBlock = null;
		final String userBlock = null;

		Assert.assertEquals(
				"addr1\naddr2\n121212 City1\nPostfach 1234\nCountry1",
				builder
						.setLanguage("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));

		isLocalAddress = true;

		Assert.assertEquals(
				"LOCAL: addr1\naddr2\n121212 City1\nPostfach 1234\nCountry1",
				builder
						.setLanguage("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));
	}

	@Test
	public void test_buildAddressString_0130()
	{
		final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", true, "1234",
				prepareCountry("Country1", "@A1@ @A2@ @P@ @C@ TEST (Postfach @PB@) @CO@"));
		boolean isLocalAddress = false;
		final String bPartnerBlock = null;
		final String userBlock = null;

		Assert.assertEquals(
				"addr1\naddr2\n121212 City1\nTEST Postfach 1234\nCountry1",
				builder
						.setLanguage("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));

		isLocalAddress = true;

		Assert.assertEquals(
				"LOCAL: addr1\naddr2\n121212 City1\nTEST Postfach 1234\nCountry1",
				builder
						.setLanguage("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));
	}

	@Test
	public void test_buildAddressString_0140()
	{
		final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", true, "",
				prepareCountry("Country1", "@A2@ @A1@ (Postfach @PB@) @P@ @C@ @CO@"));
		boolean isLocalAddress = false;
		final String bPartnerBlock = null;
		final String userBlock = null;

		Assert.assertEquals(
				"addr2\naddr1\nPostfach \n121212 City1\nCountry1",
				builder
						.setLanguage("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));

		isLocalAddress = true;

		Assert.assertEquals(
				"LOCAL: addr2\naddr1\nPostfach \n121212 City1\nCountry1",
				builder
						.setLanguage("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));
	}

	@Test
	public void test_buildAddressString_0150()
	{
		final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", true, "1234",
				prepareCountry("Country1", "@A2@ @A1@ (Postfach @PB@) @P@ @C@ @CO@"));
		boolean isLocalAddress = false;
		final String bPartnerBlock = null;
		final String userBlock = null;

		Assert.assertEquals(
				"addr2\naddr1\nPostfach 1234\n121212 City1\nCountry1",
				builder
						.setLanguage("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));

		isLocalAddress = true;

		Assert.assertEquals(
				"LOCAL: addr2\naddr1\nPostfach 1234\n121212 City1\nCountry1",
				builder
						.setLanguage("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));
	}

	@Test
	public void test_buildAddressString_0160()
	{
		final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", true, "",
				prepareCountry("Country1", "@A2@ @A1@ @PB@ @P@ @C@ @CO@"));
		boolean isLocalAddress = false;
		final String bPartnerBlock = null;
		final String userBlock = null;

		Assert.assertEquals(
				"addr2\naddr1\n121212 City1\nCountry1",
				builder
						.setLanguage("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));

		isLocalAddress = true;

		Assert.assertEquals(
				"LOCAL: addr2\naddr1\n121212 City1\nCountry1",
				builder
						.setLanguage("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));
	}

	@Test
	public void test_buildAddressString_0170()
	{
		final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", true, "1234",
				prepareCountry("Country1", "@A2@ @A1@ @PB@ @P@ @C@ @CO@"));
		boolean isLocalAddress = false;
		final String bPartnerBlock = null;
		final String userBlock = null;

		Assert.assertEquals(
				"addr2\naddr1\n1234\n121212 City1\nCountry1",
				builder
						.setLanguage("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));

		isLocalAddress = true;

		Assert.assertEquals(
				"LOCAL: addr2\naddr1\n1234\n121212 City1\nCountry1",
				builder
						.setLanguage("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));
	}

	@Test
	public void test_buildAddressString_0180()
	{
		final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", true, "1234",
				prepareCountry("Country1", "@A2@ @A1@ (TEST) @P@ @C@ @CO@"));
		final boolean isLocalAddress = false;
		final String bPartnerBlock = null;
		final String userBlock = null;

		Assert.assertEquals(
				"addr2\naddr1\n121212 City1\nCountry1",
				builder
						.setLanguage("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));
	}

	/**
	 * test case when we have sequences in C_COuntry_Sequence
	 */
	@Test
	public void test_buildAddressString_0190()
	{
		final I_C_Country country = prepareCountry("Country1", "@A2@ @A1@ @P@ @C@ @CO@");
		final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", true, "1234", country);
		prepareCountrySequence(country, "@A2@ @A1@ Italien @P@ @C@ @CO@", "it_IT");

		final boolean isLocalAddress = false;
		final String bPartnerBlock = null;
		final String userBlock = null;

		Assert.assertEquals(
				"addr2\naddr1\nItalien 121212 City1\nCountry1",
				builder
						.setLanguage("it_IT")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));
	}

	/**
	 * test case when the address is local and the country should be shown
	 */
	@Test
	public void test_buildAddressString_0200()
	{
		final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", true, "1234",
				prepareCountry("Country1", "@A2@ @A1@ (TEST) @P@ @C@ @CO@"));
		final boolean isLocalAddress = true;
		final String bPartnerBlock = null;
		final String userBlock = null;

		Assert.assertEquals(
				"LOCAL: addr2\naddr1\n121212 City1\nCountry1",
				builder
						.setLanguage("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));
	}

	@Test
	public void test_buildBPartnerAddressStringContactBlock_0010()
	{

		final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", false, "",
				prepareCountry("Germany", "@BP@ @CON@ @A2@ @A1@ @P@ @C@ @CO@"));
		final I_C_BPartner_Location bpLocation = prepareBPLocation(location);
		final I_C_BPartner bPartner = prepareBPartner("Name1", "Name2", false);
		final I_C_Greeting greeting = prepareGreeting("Herr");
		final I_AD_User user = prepareUser("UserFN", "UserLN", "", greeting);

		Assert.assertEquals(
				"LOCAL:  \nHerr\nUserFN UserLN\naddr2\naddr1\n121212 City1\nGermany",
				Services.get(IBPartnerBL.class).mkFullAddress(bPartner, bpLocation, user, null));
	}

	@Test
	public void test_buildBPartnerAddressStringBPartnerBlock_0020()
	{

		final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", false, "",
				prepareCountry("Germany", "@BP@ @CON@ @A2@ @A1@ @P@ @C@ @CO@"));
		final I_C_BPartner_Location bpLocation = prepareBPLocation(location);
		final I_C_BPartner bPartner = prepareBPartner("Name1", "Name2", false);
		final I_C_Greeting greeting = prepareGreeting("Herr");
		final I_AD_User user = prepareUser("UserFN", "", "", greeting);

		Assert.assertEquals(
				"LOCAL: \nName1\nName2\naddr2\naddr1\n121212 City1\nGermany",
				Services.get(IBPartnerBL.class).mkFullAddress(bPartner, bpLocation, user, null));
	}

	@Test
	public void test_buildBPartnerAddressStringBPartnerBlock_0030()
	{

		final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", false, "",
				prepareCountry("Germany", "@BP@ (z.L. @GR@) @FN@ @LN@ @CON@ @A2@ @A1@ @P@ @C@ @CO@"));
		final I_C_BPartner_Location bpLocation = prepareBPLocation(location);
		final I_C_BPartner bPartner = prepareBPartner("Name1", "Name2", false);
		final I_C_Greeting greeting = prepareGreeting("Herr");
		final I_AD_User user = prepareUser("UserFN", "UserLN", "", greeting);

		Assert.assertEquals(
				"LOCAL:    \nz.L. Herr\nUserFN UserLN\naddr2\naddr1\n121212 City1\nGermany",
				Services.get(IBPartnerBL.class).mkFullAddress(bPartner, bpLocation, user, null));
	}

	/**
	 * task 04121 <br>
	 * check if the text between brackets is not printed if there is no variable in brackets
	 */
	@Test
	public void test_buildBPartnerAddressStringBPartnerBlock_0040()
	{

		final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", false, "",
				prepareCountry("Germany", "@BP@ (GR) @FN@ @LN@ @CON@ @A2@ @A1@ @P@ @C@ @CO@"));
		final I_C_BPartner_Location bpLocation = prepareBPLocation(location);
		final I_C_BPartner bPartner = prepareBPartner("Name1", "Name2", false);
		final I_C_Greeting greeting = prepareGreeting("");
		final I_AD_User user = prepareUser("UserFN", "UserLN", "", greeting);

		Assert.assertEquals(
				"LOCAL:    \nUserFN UserLN\naddr2\naddr1\n121212 City1\nGermany",
				Services.get(IBPartnerBL.class).mkFullAddress(bPartner, bpLocation, user, null));
	}

	/**
	 * task 04121 <br>
	 * check if the text between brackets is printed if the greeting exists
	 */
	@Test
	public void test_buildBPartnerAddressStringBPartnerBlock_0050()
	{

		final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", false,
				"",
				prepareCountry("Germany", "@BP@ (GR @GR@) @FN@ @LN@ @CON@ @A2@ @A1@ @P@ @C@ @CO@"));
		final I_C_BPartner_Location bpLocation = prepareBPLocation(location);
		final I_C_BPartner bPartner = prepareBPartner("Name1", "Name2", false);
		final I_C_Greeting greeting = prepareGreeting("Frau");
		final I_AD_User user = prepareUser("UserFN", "UserLN", "", greeting);

		Assert.assertEquals(
				"LOCAL:    \nGR Frau\nUserFN UserLN\naddr2\naddr1\n121212 City1\nGermany",
				Services.get(IBPartnerBL.class).mkFullAddress(bPartner, bpLocation, user, null));
	}

	/**
	 * task 04121 <br>
	 * check if the brackets are escaped also in user sequence
	 */
	@Test
	public void test_buildBPartnerAddressStringBPartnerBlock_0060()
	{

		final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", false,
				"",
				prepareCountry("Germany", "@BP@ \\(test\\) @FN@ @LN@ @CON@ @A2@ @A1@ @P@ @C@ @CO@"));
		final I_C_BPartner_Location bpLocation = prepareBPLocation(location);
		final I_C_BPartner bPartner = prepareBPartner("Name1", "Name2", false);
		final I_C_Greeting greeting = prepareGreeting("Frau");
		final I_AD_User user = prepareUser("UserFN", "UserLN", "", greeting);

		Assert.assertEquals(
				"LOCAL:  (test)   \nUserFN UserLN\naddr2\naddr1\n121212 City1\nGermany",
				Services.get(IBPartnerBL.class).mkFullAddress(bPartner, bpLocation, user, null));
	}

	/**
	 * task 04266 <br>
	 * check if the greeting is not printed, in case of company
	 */
	@Test
	public void test_buildBPartnerAddressStringBPartnerBlock_0070()
	{

		final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", false,
				"",
				prepareCountry("Germany", "@BP@ (GR @GR@) @FN@ @LN@ @CON@ @A2@ @A1@ @P@ @C@ @CO@"));
		final I_C_BPartner_Location bpLocation = prepareBPLocation(location);
		final I_C_BPartner bPartner = prepareBPartner("Name1", "Name2", true);
		final I_C_Greeting greeting = prepareGreeting("Frau");
		final I_AD_User user = prepareUser("UserFN", "UserLN", "", greeting);

		Assert.assertEquals(
				"LOCAL: \nName1\nName2\nUserFN UserLN\naddr2\naddr1\n121212 City1\nGermany",
				Services.get(IBPartnerBL.class).mkFullAddress(bPartner, bpLocation, user, null));
	}

	/**
	 * task 04266 <br>
	 * check if the greeting is not printed, in case of company
	 */
	@Test
	public void test_buildBPartnerAddressStringBPartnerBlock_0080()
	{

		final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", false,
				"",
				prepareCountry("Germany", "@BP@ (GR @GR@) @FN@ @LN@ @CON@ @A2@ @A1@ @P@ @C@ @CO@"));
		final I_C_BPartner_Location bpLocation = prepareBPLocation(location);
		final I_C_BPartner bPartner = prepareBPartner("CompanyAG", "", true);
		final I_C_Greeting greeting = prepareGreeting("Frau");
		final I_AD_User user = prepareUser("UserFN", "UserLN", "", greeting);

		Assert.assertEquals(
				"LOCAL: \nCompanyAG\nUserFN UserLN\naddr2\naddr1\n121212 City1\nGermany",
				Services.get(IBPartnerBL.class).mkFullAddress(bPartner, bpLocation, user, null));
	}

	/**
	 * task 04266 <br>
	 * check if after greeting we have BR, in case of not company
	 */
	@Test
	public void test_buildBPartnerAddressStringBPartnerBlock_0090()
	{

		final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", false,
				"",
				prepareCountry("Germany", "@BP@ @GR@ @FN@ @LN@ @CON@ @A2@ @A1@ @P@ @C@ @CO@"));
		final I_C_BPartner_Location bpLocation = prepareBPLocation(location);
		final I_C_BPartner bPartner = prepareBPartner("Name1", "Name2", false);
		final I_C_Greeting greeting = prepareGreeting("Frau");
		final I_AD_User user = prepareUser("UserFN", "UserLN", "", greeting);

		Assert.assertEquals(
				"LOCAL:     \nFrau\nUserFN UserLN\naddr2\naddr1\n121212 City1\nGermany",
				Services.get(IBPartnerBL.class).mkFullAddress(bPartner, bpLocation, user, null));
	}

	/**
	 * task 04266 <br>
	 * check if after greeting we have BR, in case of not company
	 */
	@Test
	public void test_buildBPartnerAddressStringBPartnerBlock_0100()
	{

		final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", false,
				"",
				prepareCountry("Germany", "@BP@ (z.L. @GR@) @CON@ @A2@ @A1@ (PF @PB@) @P@ @C@ @CO@"));
		final I_C_BPartner_Location bpLocation = prepareBPLocation(location);
		final I_C_BPartner bPartner = prepareBPartner("Name1", "Name2", false);
		final I_C_Greeting greeting = prepareGreeting("Frau");
		final I_AD_User user = prepareUser("UserFN", "UserLN", "", greeting);

		Assert.assertEquals(
				"LOCAL:  \nz.L. Frau\naddr2\naddr1\n121212 City1\nGermany",
				Services.get(IBPartnerBL.class).mkFullAddress(bPartner, bpLocation, user, null));
	}

	/**
	 * task 04266 <br>
	 * check if after greeting we have BR, in case of not company
	 */
	@Test
	public void test_buildBPartnerAddressStringBPartnerBlock_0110()
	{

		final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", false,
				"",
				prepareCountry("Germany", "@BP@ @CON@ @A2@ @A1@ @A3@ (Postfach @PB@) @P@ @C@ @CO@"));
		final I_C_BPartner_Location bpLocation = prepareBPLocation(location);
		final I_C_BPartner bPartner = prepareBPartner("Name1", "Name2", false);
		final I_C_Greeting greeting = prepareGreeting("Frau");
		final I_AD_User user = prepareUser("UserFN", "UserLN", "", greeting);

		Assert.assertEquals(
				"LOCAL:  \nFrau\nUserFN UserLN\naddr2\naddr1\n121212 City1\nGermany",
				Services.get(IBPartnerBL.class).mkFullAddress(bPartner, bpLocation, user, null));
	}

	// prepraring methods

	private I_C_Country prepareCountry(final String countryName, final String displaySequence)
	{
		final I_C_Country country = InterfaceWrapperHelper.create(Env.getCtx(), I_C_Country.class, ITrx.TRXNAME_None);

		country.setName(countryName);
		country.setDisplaySequence(displaySequence);
		country.setDisplaySequenceLocal("LOCAL: " + displaySequence);
		InterfaceWrapperHelper.save(country);

		return country;
	}

	private I_C_Country_Sequence prepareCountrySequence(final I_C_Country country, final String displaySequence, final String language)
	{
		final I_C_Country_Sequence countrySeq = InterfaceWrapperHelper.create(Env.getCtx(), I_C_Country_Sequence.class, ITrx.TRXNAME_None);

		countrySeq.setDisplaySequence(displaySequence);
		countrySeq.setDisplaySequenceLocal("LOCAL: " + displaySequence);
		countrySeq.setAD_Language(language);
		countrySeq.setAD_Org(org);
		countrySeq.setC_Country(country);
		InterfaceWrapperHelper.save(countrySeq);

		return countrySeq;
	}

	private I_AD_Org prepareAD_Org()
	{
		final I_AD_Org org = InterfaceWrapperHelper.create(Env.getCtx(), I_AD_Org.class, ITrx.TRXNAME_None);
		org.setName("Org1");
		org.setValue("1");
		InterfaceWrapperHelper.save(org);

		return org;
	}

	private I_C_Location prepareLocation(final String a1, final String a2, final String a3, final String a4,
			final String city, final String regionName, final String postal, final boolean isPOBoxNum, final String POBox, final I_C_Country country)
	{
		final I_C_Location location = InterfaceWrapperHelper.create(Env.getCtx(), I_C_Location.class, ITrx.TRXNAME_None);
		location.setAddress1(a1);
		location.setAddress2(a2);
		location.setAddress3(a3);
		location.setAddress4(a4);
		location.setCity(city);
		location.setRegionName(regionName);
		location.setPostal(postal);
		location.setIsPOBoxNum(isPOBoxNum);
		location.setPOBox(POBox);
		location.setC_Country_ID(country.getC_Country_ID());
		InterfaceWrapperHelper.save(location);

		return location;
	}

	private I_C_BPartner_Location prepareBPLocation(final I_C_Location location)
	{
		final I_C_BPartner_Location bpLoc = InterfaceWrapperHelper.create(Env.getCtx(), I_C_BPartner_Location.class, ITrx.TRXNAME_None);
		bpLoc.setC_Location_ID(location.getC_Location_ID());
		bpLoc.setAD_Org(org);
		InterfaceWrapperHelper.save(bpLoc);

		return bpLoc;
	}

	private I_C_BPartner prepareBPartner(final String name, final String name2, final boolean isCompany)
	{
		final I_C_BPartner bpartner = InterfaceWrapperHelper.create(Env.getCtx(), I_C_BPartner.class, ITrx.TRXNAME_None);
		bpartner.setName(name);
		bpartner.setName2(name2);
		bpartner.setAD_Org(org);
		bpartner.setIsCompany(isCompany);
		InterfaceWrapperHelper.save(bpartner);

		return bpartner;
	}

	private I_C_Greeting prepareGreeting(final String name)
	{
		final I_C_Greeting greeting = InterfaceWrapperHelper.create(Env.getCtx(), I_C_Greeting.class, ITrx.TRXNAME_None);
		greeting.setName(name);
		greeting.setAD_Org_ID(org.getAD_Org_ID());
		InterfaceWrapperHelper.save(greeting);

		return greeting;
	}

	private I_AD_User prepareUser(final String firstName, final String lastName, final String title, final I_C_Greeting greeting)
	{
		final I_AD_User user = InterfaceWrapperHelper.create(Env.getCtx(), I_AD_User.class, ITrx.TRXNAME_None);
		user.setFirstname(firstName);
		user.setLastname(lastName);
		user.setTitle(title);
		user.setAD_Org(org);
		user.setC_Greeting_ID(greeting.getC_Greeting_ID());
		InterfaceWrapperHelper.save(user);

		return user;
	}
}
