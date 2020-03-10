package de.metas.location.impl;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Country_Sequence;
import org.compiere.model.I_C_Greeting;
import org.compiere.model.I_C_Location;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.metas.ShutdownListener;
import de.metas.StartupListener;
import de.metas.adempiere.model.I_AD_User;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.greeting.GreetingRepository;
import de.metas.interfaces.I_C_BPartner;
import de.metas.organization.OrgId;
import de.metas.user.UserRepository;
import lombok.Builder;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { StartupListener.class, ShutdownListener.class, GreetingRepository.class })
public class AddressBuilderTest
{
	OrgId orgId;
	private IBPartnerBL bpartnerBL;

	@BeforeClass
	public static void staticInit()
	{
		AdempiereTestHelper.get().staticInit();
	}

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		orgId = prepareOrgId();

		bpartnerBL = new BPartnerBL(new UserRepository());
	}

	private AddressBuilder builder(final String adLanguage)
	{
		return AddressBuilder.builder()
				.orgId(orgId)
				.adLanguage(adLanguage)
				.build();
	}

	@Test
	public void test_buildAddressString_0010()
	{
		final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", "", prepareCountry("Germany", "@A1@ @A2@ @C@ @CO@"));
		final boolean isLocalAddress = true;
		final String bPartnerBlock = null;
		final String userBlock = null;
		Assert.assertEquals(
				"LOCAL: addr1\naddr2\nCity1\nGermany",
				builder("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));
	}

	@Test
	public void test_buildAddressString_0020()
	{
		final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", "", prepareCountry("Country1", "@A1@ @A2@ @C@ @CO@"));
		final boolean isLocalAddress = false;
		final String bPartnerBlock = null;
		final String userBlock = null;

		Assert.assertEquals(
				"addr1\naddr2\nCity1\nCountry1",
				builder("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock)

		);
	}

	@Test
	public void test_buildAddressString_0030()
	{
		final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", "", prepareCountry("Country1", "@A1@ @A2@ @C@ @R@ @CO@"));
		boolean isLocalAddress = false;
		final String bPartnerBlock = null;
		final String userBlock = null;
		Assert.assertEquals(
				"addr1\naddr2\nCity1\nRegion1 Country1",
				builder("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));

		isLocalAddress = true;
		Assert.assertEquals(
				"LOCAL: addr1\naddr2\nCity1\nRegion1 Country1",
				builder("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));
	}

	@Test
	public void test_buildAddressString_0040_UK_Address()
	{
		final I_C_Location location = prepareLocation("street", "12", null, null, "London", null, "121212", "", prepareCountry("UK", "@A1@ @A2@@CR@@P@@CR@@C@ @CO@"));
		boolean isLocalAddress = false;
		final String bPartnerBlock = null;
		final String userBlock = null;
		Assert.assertEquals(
				"street 12\n121212\nLondon UK",
				builder("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));

		isLocalAddress = true;
		Assert.assertEquals(
				"LOCAL: " + "street 12\n121212\nLondon UK",
				builder("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));
	}

	@Test
	public void test_buildAddressString_0050_DE_Address()
	{
		final I_C_Location location = prepareLocation("street", "12", null, null, "Berlin", null, "121212", "", prepareCountry("Deutschland", "@BP@ @A1@ @A2@ @A3@ D-@P@ @C@ @CO@"));
		boolean isLocalAddress = false;
		final String bPartnerBlock = "BPartner1";
		final String userBlock = "Contact1";

		Assert.assertEquals(
				"BPartner1\nstreet\n12\nD-121212 Berlin\nDeutschland",
				builder("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));

		isLocalAddress = true;
		Assert.assertEquals(
				"LOCAL: " + "\nBPartner1\nstreet\n12\nD-121212 Berlin\nDeutschland",
				builder("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));
	}

	@Test
	public void test_buildAddressString_0060()
	{
		final I_C_Location location = prepareLocation("street", "12", null, null, "Berlin", null, "121212", "", prepareCountry("Deutschland", "@BP@ @CON@ @A1@ @A2@ @A3@ D-@P@ @C@ @CO@"));
		boolean isLocalAddress = false;
		final String bPartnerBlock = "BPartner1";
		final String userBlock = "Contact1";
		Assert.assertEquals(
				"BPartner1\nContact1\nstreet\n12\nD-121212 Berlin\nDeutschland",
				builder("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));

		isLocalAddress = true;
		Assert.assertEquals(
				"LOCAL: " + "\nBPartner1\nContact1\nstreet\n12\nD-121212 Berlin\nDeutschland",
				builder("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));
	}

	/**
	 * task 04121 <br>
	 * check if the word for brackets is printed if the variables are not empty
	 */
	@Test
	public void test_buildAddressString_0070()
	{
		final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", "", prepareCountry("Country1", "@A1@ @A2@ @P@ @C@ (Region @R@) @CO@"));
		boolean isLocalAddress = false;
		final String bPartnerBlock = null;
		final String userBlock = null;

		Assert.assertEquals(
				"addr1\naddr2\n121212 City1\nRegion Region1 Country1",
				builder("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));

		isLocalAddress = true;

		Assert.assertEquals(
				"LOCAL: addr1\naddr2\n121212 City1\nRegion Region1 Country1",
				builder("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));
	}

	/**
	 * task 04121 <br>
	 * check if the brackets are escaped if we used escape char \
	 */
	@Test
	public void test_buildAddressString_0080_EscapeBrackets()
	{
		final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", "", prepareCountry("Country1", "@A1@ @A2@ @P@ @C@ \\(Region @R@\\) @CO@"));
		boolean isLocalAddress = false;
		final String bPartnerBlock = null;
		final String userBlock = null;

		Assert.assertEquals(
				"addr1\naddr2\n121212 City1\n(Region Region1) Country1",
				builder("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));

		isLocalAddress = true;

		Assert.assertEquals(
				"LOCAL: addr1\naddr2\n121212 City1\n(Region Region1) Country1",
				builder("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));
	}

	/**
	 * task 04121 <br>
	 * check if the word inside brackets is not printed if the variables empty
	 */
	@Test
	public void test_buildAddressString_0090_EmptyVariable()
	{
		final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "", "121212", "", prepareCountry("Country1", "@A1@ @A2@ @P@ @C@ (Region @R@) @CO@"));
		boolean isLocalAddress = false;
		final String bPartnerBlock = null;
		final String userBlock = null;

		Assert.assertEquals(
				"addr1\naddr2\n121212 City1\nCountry1",
				builder("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));

		isLocalAddress = true;

		Assert.assertEquals(
				"LOCAL: addr1\naddr2\n121212 City1\nCountry1",
				builder("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));
	}

	/**
	 * task 04121 <br>
	 * check if the brackets are escaped
	 */
	@Test
	public void test_buildAddressString_0100_EscapeBrackets_EmptyVariable()
	{
		final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "", "121212", "", prepareCountry("Country1", "@A1@ @A2@ @P@ @C@ \\(Region @R@\\) @CO@"));
		boolean isLocalAddress = false;
		final String bPartnerBlock = null;
		final String userBlock = null;

		Assert.assertEquals(
				"addr1\naddr2\n121212 City1\n(Region ) Country1",
				builder("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));

		isLocalAddress = true;

		Assert.assertEquals(
				"LOCAL: addr1\naddr2\n121212 City1\n(Region ) Country1",
				builder("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));
	}

	@Test
	public void test_buildAddressString_0110()
	{
		final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", "", prepareCountry("Country1", "@A1@ @A2@ @P@ @C@ (Postfach @PB@) @CO@"));
		boolean isLocalAddress = false;
		final String bPartnerBlock = null;
		final String userBlock = null;

		Assert.assertEquals(
				"addr1\naddr2\n121212 City1\nCountry1",
				builder("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));

		isLocalAddress = true;

		Assert.assertEquals(
				"LOCAL: addr1\naddr2\n121212 City1\nCountry1",
				builder("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));
	}

	@Test
	public void test_buildAddressString_0120()
	{
		final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", "1234", prepareCountry("Country1", "@A1@ @A2@ @P@ @C@ (Postfach @PB@) @CO@"));
		boolean isLocalAddress = false;
		final String bPartnerBlock = null;
		final String userBlock = null;

		Assert.assertEquals(
				"addr1\naddr2\n121212 City1\nPostfach 1234\nCountry1",
				builder("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));

		isLocalAddress = true;

		Assert.assertEquals(
				"LOCAL: addr1\naddr2\n121212 City1\nPostfach 1234\nCountry1",
				builder("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));
	}

	@Test
	public void test_buildAddressString_0130()
	{
		final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", "1234", prepareCountry("Country1", "@A1@ @A2@ @P@ @C@ TEST (Postfach @PB@) @CO@"));
		boolean isLocalAddress = false;
		final String bPartnerBlock = null;
		final String userBlock = null;

		Assert.assertEquals(
				"addr1\naddr2\n121212 City1\nTEST Postfach 1234\nCountry1",
				builder("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));

		isLocalAddress = true;

		Assert.assertEquals(
				"LOCAL: addr1\naddr2\n121212 City1\nTEST Postfach 1234\nCountry1",
				builder("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));
	}

	@Test
	public void test_buildAddressString_0140()
	{
		final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", "", prepareCountry("Country1", "@A2@ @A1@ (Postfach @PB@) @P@ @C@ @CO@"));
		boolean isLocalAddress = false;
		final String bPartnerBlock = null;
		final String userBlock = null;

		Assert.assertEquals(
				"addr2\naddr1\n121212 City1\nCountry1",
				builder("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));

		isLocalAddress = true;

		Assert.assertEquals(
				"LOCAL: addr2\naddr1\n121212 City1\nCountry1",
				builder("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));
	}

	@Test
	public void test_buildAddressString_0150()
	{
		final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", "1234", prepareCountry("Country1", "@A2@ @A1@ (Postfach @PB@) @P@ @C@ @CO@"));
		boolean isLocalAddress = false;
		final String bPartnerBlock = null;
		final String userBlock = null;

		Assert.assertEquals(
				"addr2\naddr1\nPostfach 1234\n121212 City1\nCountry1",
				builder("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));

		isLocalAddress = true;

		Assert.assertEquals(
				"LOCAL: addr2\naddr1\nPostfach 1234\n121212 City1\nCountry1",
				builder("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));
	}

	@Test
	public void test_buildAddressString_0160()
	{
		final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", "", prepareCountry("Country1", "@A2@ @A1@ @PB@ @P@ @C@ @CO@"));
		boolean isLocalAddress = false;
		final String bPartnerBlock = null;
		final String userBlock = null;

		Assert.assertEquals(
				"addr2\naddr1\n121212 City1\nCountry1",
				builder("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));

		isLocalAddress = true;

		Assert.assertEquals(
				"LOCAL: addr2\naddr1\n121212 City1\nCountry1",
				builder("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));
	}

	@Test
	public void test_buildAddressString_0170()
	{
		final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", "1234", prepareCountry("Country1", "@A2@ @A1@ @PB@ @P@ @C@ @CO@"));
		boolean isLocalAddress = false;
		final String bPartnerBlock = null;
		final String userBlock = null;

		Assert.assertEquals(
				"addr2\naddr1\n1234\n121212 City1\nCountry1",
				builder("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));

		isLocalAddress = true;

		Assert.assertEquals(
				"LOCAL: addr2\naddr1\n1234\n121212 City1\nCountry1",
				builder("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));
	}

	@Test
	public void test_buildAddressString_0180()
	{
		final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", "1234", prepareCountry("Country1", "@A2@ @A1@ (TEST) @P@ @C@ @CO@"));
		final boolean isLocalAddress = false;
		final String bPartnerBlock = null;
		final String userBlock = null;

		Assert.assertEquals(
				"addr2\naddr1\n121212 City1\nCountry1",
				builder("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));
	}

	/**
	 * test case when we have sequences in C_COuntry_Sequence
	 */
	@Test
	public void test_buildAddressString_0190()
	{
		final I_C_Country country = prepareCountry("Country1", "@A2@ @A1@ @P@ @C@ @CO@");
		final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", "1234", country);
		prepareCountrySequence(country, "@A2@ @A1@ Italien @P@ @C@ @CO@", "it_IT");

		final boolean isLocalAddress = false;
		final String bPartnerBlock = null;
		final String userBlock = null;

		Assert.assertEquals(
				"addr2\naddr1\nItalien 121212 City1\nCountry1",
				builder("it_IT")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));
	}

	/**
	 * test case when the address is local and the country should be shown
	 */
	@Test
	public void test_buildAddressString_0200()
	{
		final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", "1234", prepareCountry("Country1", "@A2@ @A1@ (TEST) @P@ @C@ @CO@"));
		final boolean isLocalAddress = true;
		final String bPartnerBlock = null;
		final String userBlock = null;

		Assert.assertEquals(
				"LOCAL: addr2\naddr1\n121212 City1\nCountry1",
				builder("de_CH")
						.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));
	}

	@Test
	public void test_buildBPartnerAddressStringContactBlock_0010()
	{

		final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", "", prepareCountry("Germany", "@BP@ @CON@ @A2@ @A1@ @P@ @C@ @CO@"));
		final I_C_BPartner_Location bpLocation = prepareBPLocation(location);
		final I_C_BPartner bPartner = builderBPartner()
				.name("Name1")
				.name2("Name2")
				.isCompany(false)
				.AD_Language("de_DE")
				.build();
		final I_C_Greeting greeting = prepareGreeting("Herr");
		final I_AD_User user = prepareUser("UserFN", "UserLN", "", greeting);

		Assert.assertEquals(
				"LOCAL:  \nHerr\nUserFN UserLN\naddr2\naddr1\n121212 City1\nGermany",
				bpartnerBL.mkFullAddress(bPartner, bpLocation, user, null));
	}

	@Test
	public void test_buildBPartnerAddressStringBPartnerBlock_0020()
	{

		final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", "", prepareCountry("Germany", "@BP@ @CON@ @A2@ @A1@ @P@ @C@ @CO@"));
		final I_C_BPartner_Location bpLocation = prepareBPLocation(location);
		final I_C_BPartner bPartner = builderBPartner()
				.name("Name1")
				.name2("Name2")
				.isCompany(false)
				.AD_Language("de_DE")
				.build();
		final I_C_Greeting greeting = prepareGreeting("Herr");
		final I_AD_User user = prepareUser("UserFN", "", "", greeting);

		Assert.assertEquals(
				"LOCAL: \nName1\nName2\naddr2\naddr1\n121212 City1\nGermany",
				bpartnerBL.mkFullAddress(bPartner, bpLocation, user, null));
	}

	@Test
	public void test_buildBPartnerAddressStringBPartnerBlock_0030()
	{

		final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", "", prepareCountry("Germany", "@BP@ (z.L. @GR@) @FN@ @LN@ @CON@ @A2@ @A1@ @P@ @C@ @CO@"));
		final I_C_BPartner_Location bpLocation = prepareBPLocation(location);
		final I_C_BPartner bPartner = builderBPartner()
				.name("Name1")
				.name2("Name2")
				.isCompany(false)
				.AD_Language("de_DE")
				.build();
		final I_C_Greeting greeting = prepareGreeting("Herr");
		final I_AD_User user = prepareUser("UserFN", "UserLN", "", greeting);

		Assert.assertEquals(
				"LOCAL:    \nz.L. Herr\nUserFN UserLN\naddr2\naddr1\n121212 City1\nGermany",
				bpartnerBL.mkFullAddress(bPartner, bpLocation, user, null));
	}

	/**
	 * task 04121 <br>
	 * check if the text between brackets is not printed if there is no variable in brackets
	 */
	@Test
	public void test_buildBPartnerAddressStringBPartnerBlock_0040()
	{

		final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", "", prepareCountry("Germany", "@BP@ (GR) @FN@ @LN@ @CON@ @A2@ @A1@ @P@ @C@ @CO@"));
		final I_C_BPartner_Location bpLocation = prepareBPLocation(location);
		final I_C_BPartner bPartner = builderBPartner()
				.name("Name1")
				.name2("Name2")
				.isCompany(false)
				.AD_Language("de_DE")
				.build();
		final I_C_Greeting greeting = prepareGreeting("");
		final I_AD_User user = prepareUser("UserFN", "UserLN", "", greeting);

		Assert.assertEquals(
				"LOCAL:    \nUserFN UserLN\naddr2\naddr1\n121212 City1\nGermany",
				bpartnerBL.mkFullAddress(bPartner, bpLocation, user, null));
	}

	/**
	 * task 04121 <br>
	 * check if the text between brackets is printed if the greeting exists
	 */
	@Test
	public void test_buildBPartnerAddressStringBPartnerBlock_0050()
	{

		final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", "",
				prepareCountry("Germany", "@BP@ (GR @GR@) @FN@ @LN@ @CON@ @A2@ @A1@ @P@ @C@ @CO@"));
		final I_C_BPartner_Location bpLocation = prepareBPLocation(location);
		final I_C_BPartner bPartner = builderBPartner()
				.name("Name1")
				.name2("Name2")
				.isCompany(false)
				.AD_Language("de_DE")
				.build();
		final I_C_Greeting greeting = prepareGreeting("Frau");
		final I_AD_User user = prepareUser("UserFN", "UserLN", "", greeting);

		Assert.assertEquals(
				"LOCAL:    \nGR Frau\nUserFN UserLN\naddr2\naddr1\n121212 City1\nGermany",
				bpartnerBL.mkFullAddress(bPartner, bpLocation, user, null));
	}

	/**
	 * task 04121 <br>
	 * check if the brackets are escaped also in user sequence
	 */
	@Test
	public void test_buildBPartnerAddressStringBPartnerBlock_0060()
	{

		final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", "",
				prepareCountry("Germany", "@BP@ \\(test\\) @FN@ @LN@ @CON@ @A2@ @A1@ @P@ @C@ @CO@"));
		final I_C_BPartner_Location bpLocation = prepareBPLocation(location);
		final I_C_BPartner bPartner = builderBPartner()
				.name("Name1")
				.name2("Name2")
				.isCompany(false)
				.AD_Language("de_DE")
				.build();
		final I_C_Greeting greeting = prepareGreeting("Frau");
		final I_AD_User user = prepareUser("UserFN", "UserLN", "", greeting);

		Assert.assertEquals(
				"LOCAL:  (test)   \nUserFN UserLN\naddr2\naddr1\n121212 City1\nGermany",
				bpartnerBL.mkFullAddress(bPartner, bpLocation, user, null));
	}

	/**
	 * task 04266 <br>
	 * check if the greeting is not printed, in case of company
	 */
	@Test
	public void test_buildBPartnerAddressStringBPartnerBlock_0070()
	{

		final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", "",
				prepareCountry("Germany", "@BP@ (GR @GR@) @FN@ @LN@ @CON@ @A2@ @A1@ @P@ @C@ @CO@"));
		final I_C_BPartner_Location bpLocation = prepareBPLocation(location);
		final I_C_BPartner bPartner = builderBPartner()
				.name("Name1")
				.name2("Name2")
				.isCompany(true)
				.AD_Language("de_DE")
				.build();
		final I_C_Greeting greeting = prepareGreeting("Frau");
		final I_AD_User user = prepareUser("UserFN", "UserLN", "", greeting);

		Assert.assertEquals(
				"LOCAL: \nName1\nName2\nUserFN UserLN\naddr2\naddr1\n121212 City1\nGermany",
				bpartnerBL.mkFullAddress(bPartner, bpLocation, user, null));
	}

	/**
	 * task 04266 <br>
	 * check if the greeting is not printed, in case of company
	 */
	@Test
	public void test_buildBPartnerAddressStringBPartnerBlock_0080()
	{

		final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", "",
				prepareCountry("Germany", "@BP@ (GR @GR@) @FN@ @LN@ @CON@ @A2@ @A1@ @P@ @C@ @CO@"));
		final I_C_BPartner_Location bpLocation = prepareBPLocation(location);
		final I_C_BPartner bPartner = builderBPartner()
				.name("CompanyAG")
				.name2("")
				.isCompany(true)
				.AD_Language("de_DE")
				.build();
		final I_C_Greeting greeting = prepareGreeting("Frau");
		final I_AD_User user = prepareUser("UserFN", "UserLN", "", greeting);

		Assert.assertEquals(
				"LOCAL: \nCompanyAG\nUserFN UserLN\naddr2\naddr1\n121212 City1\nGermany",
				bpartnerBL.mkFullAddress(bPartner, bpLocation, user, null));
	}

	/**
	 * task 04266 <br>
	 * check if after greeting we have BR, in case of not company
	 */
	@Test
	public void test_buildBPartnerAddressStringBPartnerBlock_0090()
	{

		final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", "",
				prepareCountry("Germany", "@BP@ @GR@ @FN@ @LN@ @CON@ @A2@ @A1@ @P@ @C@ @CO@"));
		final I_C_BPartner_Location bpLocation = prepareBPLocation(location);
		final I_C_BPartner bPartner = builderBPartner()
				.name("Name1")
				.name2("Name2")
				.isCompany(false)
				.AD_Language("de_DE")
				.build();
		final I_C_Greeting greeting = prepareGreeting("Frau");
		final I_AD_User user = prepareUser("UserFN", "UserLN", "", greeting);

		Assert.assertEquals(
				"LOCAL:     \nFrau\nUserFN UserLN\naddr2\naddr1\n121212 City1\nGermany",
				bpartnerBL.mkFullAddress(bPartner, bpLocation, user, null));
	}

	/**
	 * task 04266 <br>
	 * check if after greeting we have BR, in case of not company
	 */
	@Test
	public void test_buildBPartnerAddressStringBPartnerBlock_0100()
	{

		final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", "",
				prepareCountry("Germany", "@BP@ (z.L. @GR@) @CON@ @A2@ @A1@ (PF @PB@) @P@ @C@ @CO@"));
		final I_C_BPartner_Location bpLocation = prepareBPLocation(location);
		final I_C_BPartner bPartner = builderBPartner()
				.name("Name1")
				.name2("Name2")
				.isCompany(false)
				.AD_Language("de_DE")
				.build();
		final I_C_Greeting greeting = prepareGreeting("Frau");
		final I_AD_User user = prepareUser("UserFN", "UserLN", "", greeting);

		Assert.assertEquals(
				"LOCAL:  \nz.L. Frau\naddr2\naddr1\n121212 City1\nGermany",
				bpartnerBL.mkFullAddress(bPartner, bpLocation, user, null));
	}

	/**
	 * task 04266 <br>
	 * check if after greeting we have BR, in case of not company
	 */
	@Test
	public void test_buildBPartnerAddressStringBPartnerBlock_0110()
	{

		final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", "",
				prepareCountry("Germany", "@BP@ @CON@ @A2@ @A1@ @A3@ (Postfach @PB@) @P@ @C@ @CO@"));
		final I_C_BPartner_Location bpLocation = prepareBPLocation(location);
		final I_C_BPartner bPartner = builderBPartner()
				.name("Name1")
				.name2("Name2")
				.isCompany(false)
				.AD_Language("de_DE")
				.build();
		final I_C_Greeting greeting = prepareGreeting("Frau");
		final I_AD_User user = prepareUser("UserFN", "UserLN", "", greeting);

		Assert.assertEquals(
				"LOCAL:  \nFrau\nUserFN UserLN\naddr2\naddr1\n121212 City1\nGermany",
				bpartnerBL.mkFullAddress(bPartner, bpLocation, user, null));
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

	private void prepareCountrySequence(final I_C_Country country, final String displaySequence, final String language)
	{
		final I_C_Country_Sequence countrySeq = InterfaceWrapperHelper.newInstanceOutOfTrx(I_C_Country_Sequence.class);

		countrySeq.setDisplaySequence(displaySequence);
		countrySeq.setDisplaySequenceLocal("LOCAL: " + displaySequence);
		countrySeq.setAD_Language(language);
		countrySeq.setAD_Org_ID(orgId.getRepoId());
		countrySeq.setC_Country(country);
		InterfaceWrapperHelper.save(countrySeq);
	}

	private OrgId prepareOrgId()
	{
		final I_AD_Org org = InterfaceWrapperHelper.create(Env.getCtx(), I_AD_Org.class, ITrx.TRXNAME_None);
		org.setName("Org1");
		org.setValue("1");
		InterfaceWrapperHelper.save(org);

		return OrgId.ofRepoId(org.getAD_Org_ID());
	}

	private I_C_Location prepareLocation(
			final String a1,
			final String a2,
			final String a3,
			final String a4,
			final String city,
			final String regionName,
			final String postal,
			final String POBox,
			final I_C_Country country)
	{
		final I_C_Location location = InterfaceWrapperHelper.create(Env.getCtx(), I_C_Location.class, ITrx.TRXNAME_None);
		location.setAddress1(a1);
		location.setAddress2(a2);
		location.setAddress3(a3);
		location.setAddress4(a4);
		location.setCity(city);
		location.setRegionName(regionName);
		location.setPostal(postal);
		// location.setIsPOBoxNum(isPOBoxNum);
		location.setPOBox(POBox);
		location.setC_Country_ID(country.getC_Country_ID());
		InterfaceWrapperHelper.save(location);

		return location;
	}

	private I_C_BPartner_Location prepareBPLocation(final I_C_Location location)
	{
		final I_C_BPartner_Location bpLoc = InterfaceWrapperHelper.create(Env.getCtx(), I_C_BPartner_Location.class, ITrx.TRXNAME_None);
		bpLoc.setC_Location_ID(location.getC_Location_ID());
		bpLoc.setAD_Org_ID(orgId.getRepoId());
		InterfaceWrapperHelper.save(bpLoc);

		return bpLoc;
	}

	@Builder(builderMethodName = "builderBPartner")
	private I_C_BPartner prepareBPartner(final String name, final String name2, final boolean isCompany, final String AD_Language)
	{
		final I_C_BPartner bpartner = InterfaceWrapperHelper.create(Env.getCtx(), I_C_BPartner.class, ITrx.TRXNAME_None);
		bpartner.setName(name);
		bpartner.setName2(name2);
		bpartner.setAD_Org_ID(orgId.getRepoId());
		bpartner.setIsCompany(isCompany);
		bpartner.setAD_Language(AD_Language);
		InterfaceWrapperHelper.save(bpartner);

		return bpartner;
	}

	private I_C_Greeting prepareGreeting(final String name)
	{
		final I_C_Greeting greeting = InterfaceWrapperHelper.create(Env.getCtx(), I_C_Greeting.class, ITrx.TRXNAME_None);
		greeting.setName(name);
		greeting.setGreeting(name);
		greeting.setAD_Org_ID(orgId.getRepoId());
		InterfaceWrapperHelper.save(greeting);

		return greeting;
	}

	private I_AD_User prepareUser(final String firstName, final String lastName, final String title, final I_C_Greeting greeting)
	{
		final I_AD_User user = InterfaceWrapperHelper.create(Env.getCtx(), I_AD_User.class, ITrx.TRXNAME_None);
		user.setFirstname(firstName);
		user.setLastname(lastName);
		user.setTitle(title);
		user.setAD_Org_ID(orgId.getRepoId());
		user.setC_Greeting_ID(greeting.getC_Greeting_ID());
		InterfaceWrapperHelper.save(user);

		return user;
	}
}
