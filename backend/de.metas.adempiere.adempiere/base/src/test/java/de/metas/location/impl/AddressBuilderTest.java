package de.metas.location.impl;

import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.greeting.Greeting;
import de.metas.greeting.GreetingId;
import de.metas.greeting.GreetingRepository;
import de.metas.greeting.UpsertGreetingRequest;
import de.metas.interfaces.I_C_BPartner;
import de.metas.location.LocationId;
import de.metas.organization.OrgId;
import de.metas.user.UserRepository;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Country_Sequence;
import org.compiere.model.I_C_Greeting;
import org.compiere.model.I_C_Location;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddressBuilderTest
{
	private IBPartnerBL bpartnerBL;
	OrgId orgId;
	private GreetingRepository greetingRepository;

	@BeforeAll
	public static void beforeAll()
	{
		AdempiereTestHelper.get().staticInit();
	}

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		this.greetingRepository = new GreetingRepository();
		SpringContextHolder.registerJUnitBean(greetingRepository);
		bpartnerBL = new BPartnerBL(new UserRepository());

		orgId = prepareOrgId();
	}

	// prepraring methods
	private I_C_Country prepareCountry(final String countryName, final String displaySequence)
	{
		final I_C_Country country = newInstance(I_C_Country.class);
		country.setName(countryName);
		country.setDisplaySequence(displaySequence);
		country.setDisplaySequenceLocal("LOCAL: " + displaySequence);
		saveRecord(country);

		return country;
	}

	@SuppressWarnings("SameParameterValue")
	private void prepareCountrySequence(final I_C_Country country, final String displaySequence, final String language)
	{
		final I_C_Country_Sequence countrySeq = InterfaceWrapperHelper.newInstanceOutOfTrx(I_C_Country_Sequence.class);

		countrySeq.setDisplaySequence(displaySequence);
		countrySeq.setDisplaySequenceLocal("LOCAL: " + displaySequence);
		countrySeq.setAD_Language(language);
		countrySeq.setAD_Org_ID(orgId.getRepoId());
		countrySeq.setC_Country(country);
		saveRecord(countrySeq);
	}

	private OrgId prepareOrgId()
	{
		final I_AD_Org org = newInstance(I_AD_Org.class);
		org.setName("Org1");
		org.setValue("1");
		saveRecord(org);

		return OrgId.ofRepoId(org.getAD_Org_ID());
	}

	@SuppressWarnings("SameParameterValue")
	private I_C_Location prepareLocation(
			final String a1,
			final String a2,
			final String a3,
			final String a4,
			final String city,
			final String regionName,
			final String postal,
			final boolean isPOBoxNum,
			final String POBox,
			final I_C_Country country)
	{
		final I_C_Location location = newInstance(I_C_Location.class);
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
		saveRecord(location);

		return location;
	}

	private I_C_BPartner_Location prepareBPLocation(final I_C_Location location)
	{
		final I_C_BPartner_Location bpLoc = newInstance(I_C_BPartner_Location.class);
		bpLoc.setC_Location_ID(location.getC_Location_ID());
		bpLoc.setAD_Org_ID(orgId.getRepoId());
		saveRecord(bpLoc);

		return bpLoc;
	}

	@Builder(builderMethodName = "BPartnerBuilder")
	private I_C_BPartner prepareBPartner(final String name, final String name2, final boolean isCompany, final String AD_Language, final GreetingId greetingId)
	{
		final I_C_BPartner bpartner = InterfaceWrapperHelper.create(Env.getCtx(), I_C_BPartner.class, ITrx.TRXNAME_None);
		bpartner.setName(name);
		bpartner.setName2(name2);
		bpartner.setAD_Org_ID(orgId.getRepoId());
		bpartner.setIsCompany(isCompany);
		bpartner.setAD_Language(AD_Language);
		bpartner.setC_Greeting_ID(greetingId != null ? greetingId.getRepoId() : -1);
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

	@SuppressWarnings("SameParameterValue")
	private org.compiere.model.I_AD_User prepareUser(final String firstName, final String lastName, final String title, final I_C_Greeting greeting)
	{
		final org.compiere.model.I_AD_User user = InterfaceWrapperHelper.create(Env.getCtx(), I_AD_User.class, ITrx.TRXNAME_None);
		user.setFirstname(firstName);
		user.setLastname(lastName);
		user.setTitle(title);
		user.setAD_Org_ID(orgId.getRepoId());
		user.setC_Greeting_ID(greeting.getC_Greeting_ID());
		InterfaceWrapperHelper.save(user);

		return user;
	}

	@Nested
	public class AddressBuilder_buildAddressString
	{
		private AddressBuilder builder(final String adLanguage)
		{
			return AddressBuilder.builder()
					.orgId(orgId)
					.adLanguage(adLanguage)
					.build();
		}

		@Test
		public void case_0010()
		{
			final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", false, "", prepareCountry("Germany", "@A1@ @A2@ @C@ @CO@"));
			final boolean isLocalAddress = true;
			final String bPartnerBlock = null;
			final String userBlock = null;
			assertEquals(
					"LOCAL: addr1\naddr2\nCity1\nGermany",
					builder("de_CH")
							.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));
		}

		@Test
		public void case_0020()
		{
			final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", false, "", prepareCountry("Country1", "@A1@ @A2@ @C@ @CO@"));
			final boolean isLocalAddress = false;
			final String bPartnerBlock = null;
			final String userBlock = null;

			assertEquals(
					"addr1\naddr2\nCity1\nCountry1",
					builder("de_CH")
							.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock)

			);
		}

		@Test
		public void case_0030()
		{
			final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", false, "", prepareCountry("Country1", "@A1@ @A2@ @C@ @R@ @CO@"));
			boolean isLocalAddress = false;
			final String bPartnerBlock = null;
			final String userBlock = null;
			assertEquals(
					"addr1\naddr2\nCity1\nRegion1 Country1",
					builder("de_CH")
							.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));

			isLocalAddress = true;
			assertEquals(
					"LOCAL: addr1\naddr2\nCity1\nRegion1 Country1",
					builder("de_CH")
							.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));
		}

		@Test
		public void case_0040_UK_Address()
		{
			final I_C_Location location = prepareLocation("street", "12", null, null, "London", null, "121212", false, "", prepareCountry("UK", "@A1@ @A2@@CR@@P@@CR@@C@ @CO@"));
			boolean isLocalAddress = false;
			final String bPartnerBlock = null;
			final String userBlock = null;
			assertEquals(
					"street 12\n121212\nLondon UK",
					builder("de_CH")
							.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));

			isLocalAddress = true;
			assertEquals(
					"LOCAL: " + "street 12\n121212\nLondon UK",
					builder("de_CH")
							.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));
		}

		@Test
		public void case_0050_DE_Address()
		{
			final I_C_Location location = prepareLocation("street", "12", null, null, "Berlin", null, "121212", false, "", prepareCountry("Deutschland", "@BP@ @A1@ @A2@ @A3@ D-@P@ @C@ @CO@"));
			final String bPartnerBlock = "BPartner1";
			final String userBlock = "Contact1";

			assertEquals(
					"BPartner1\nstreet\n12\nD-121212 Berlin\nDeutschland",
					builder("de_CH")
							.buildAddressString(location, false, bPartnerBlock, userBlock));

			assertEquals(
					"LOCAL: " + "\nBPartner1\nstreet\n12\nD-121212 Berlin\nDeutschland",
					builder("de_CH")
							.buildAddressString(location, true, bPartnerBlock, userBlock));
		}

		@Test
		public void case_0060()
		{
			final I_C_Location location = prepareLocation("street", "12", null, null, "Berlin", null, "121212", false, "", prepareCountry("Deutschland", "@BP@ @CON@ @A1@ @A2@ @A3@ D-@P@ @C@ @CO@"));
			final String bPartnerBlock = "BPartner1";
			final String userBlock = "Contact1";
			assertEquals(
					"BPartner1\nContact1\nstreet\n12\nD-121212 Berlin\nDeutschland",
					builder("de_CH")
							.buildAddressString(location, false, bPartnerBlock, userBlock));

			assertEquals(
					"LOCAL: " + "\nBPartner1\nContact1\nstreet\n12\nD-121212 Berlin\nDeutschland",
					builder("de_CH")
							.buildAddressString(location, true, bPartnerBlock, userBlock));
		}

		/**
		 * task 04121 <br>
		 * check if the word for brackets is printed if the variables are not empty
		 */
		@Test
		public void case_0070()
		{
			final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", false, "", prepareCountry("Country1", "@A1@ @A2@ @P@ @C@ (Region @R@) @CO@"));

			assertEquals(
					"addr1\naddr2\n121212 City1\nRegion Region1 Country1",
					builder("de_CH")
							.buildAddressString(location, false, null, null));

			assertEquals(
					"LOCAL: addr1\naddr2\n121212 City1\nRegion Region1 Country1",
					builder("de_CH")
							.buildAddressString(location, true, null, null));
		}

		/**
		 * task 04121 <br>
		 * check if the brackets are escaped if we used escape char \
		 */
		@Test
		public void case_0080_EscapeBrackets()
		{
			final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", false, "", prepareCountry("Country1", "@A1@ @A2@ @P@ @C@ \\(Region @R@\\) @CO@"));
			boolean isLocalAddress = false;
			final String bPartnerBlock = null;
			final String userBlock = null;

			assertEquals(
					"addr1\naddr2\n121212 City1\n(Region Region1) Country1",
					builder("de_CH")
							.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));

			isLocalAddress = true;

			assertEquals(
					"LOCAL: addr1\naddr2\n121212 City1\n(Region Region1) Country1",
					builder("de_CH")
							.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));
		}

		/**
		 * task 04121 <br>
		 * check if the word inside brackets is not printed if the variables empty
		 */
		@Test
		public void case_0090_EmptyVariable()
		{
			final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "", "121212", false, "", prepareCountry("Country1", "@A1@ @A2@ @P@ @C@ (Region @R@) @CO@"));
			boolean isLocalAddress = false;
			final String bPartnerBlock = null;
			final String userBlock = null;

			assertEquals(
					"addr1\naddr2\n121212 City1\nCountry1",
					builder("de_CH")
							.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));

			isLocalAddress = true;

			assertEquals(
					"LOCAL: addr1\naddr2\n121212 City1\nCountry1",
					builder("de_CH")
							.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));
		}

		/**
		 * task 04121 <br>
		 * check if the brackets are escaped
		 */
		@Test
		public void case_0100_EscapeBrackets_EmptyVariable()
		{
			final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "", "121212", false, "", prepareCountry("Country1", "@A1@ @A2@ @P@ @C@ \\(Region @R@\\) @CO@"));
			boolean isLocalAddress = false;
			final String bPartnerBlock = null;
			final String userBlock = null;

			assertEquals(
					"addr1\naddr2\n121212 City1\n(Region ) Country1",
					builder("de_CH")
							.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));

			isLocalAddress = true;

			assertEquals(
					"LOCAL: addr1\naddr2\n121212 City1\n(Region ) Country1",
					builder("de_CH")
							.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));
		}

		@Test
		public void case_0110()
		{
			final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", false, "", prepareCountry("Country1", "@A1@ @A2@ @P@ @C@ (Postfach @PB@) @CO@"));
			boolean isLocalAddress = false;
			final String bPartnerBlock = null;
			final String userBlock = null;

			assertEquals(
					"addr1\naddr2\n121212 City1\nCountry1",
					builder("de_CH")
							.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));

			isLocalAddress = true;

			assertEquals(
					"LOCAL: addr1\naddr2\n121212 City1\nCountry1",
					builder("de_CH")
							.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));
		}

		@Test
		public void case_0120()
		{
			final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", true, "1234", prepareCountry("Country1", "@A1@ @A2@ @P@ @C@ (Postfach @PB@) @CO@"));
			boolean isLocalAddress = false;
			final String bPartnerBlock = null;
			final String userBlock = null;

			assertEquals(
					"addr1\naddr2\n121212 City1\nPostfach MSG_POBox 1234\nCountry1",
					builder("de_CH")
							.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));

			isLocalAddress = true;

			assertEquals(
					"LOCAL: addr1\naddr2\n121212 City1\nPostfach MSG_POBox 1234\nCountry1",
					builder("de_CH")
							.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));
		}

		@Test
		public void case_0130()
		{
			final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", true, "1234", prepareCountry("Country1", "@A1@ @A2@ @P@ @C@ TEST (Postfach @PB@) @CO@"));
			boolean isLocalAddress = false;
			final String bPartnerBlock = null;
			final String userBlock = null;

			assertEquals(
					"addr1\naddr2\n121212 City1\nTEST Postfach MSG_POBox 1234\nCountry1",
					builder("de_CH")
							.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));

			isLocalAddress = true;

			assertEquals(
					"LOCAL: addr1\naddr2\n121212 City1\nTEST Postfach MSG_POBox 1234\nCountry1",
					builder("de_CH")
							.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));
		}

		@Test
		public void case_0140()
		{
			final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", false, "", prepareCountry("Country1", "@A2@ @A1@ (Postfach @PB@) @P@ @C@ @CO@"));
			boolean isLocalAddress = false;
			final String bPartnerBlock = null;
			final String userBlock = null;

			assertEquals(
					"addr2\naddr1\n121212 City1\nCountry1",
					builder("de_CH")
							.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));

			isLocalAddress = true;

			assertEquals(
					"LOCAL: addr2\naddr1\n121212 City1\nCountry1",
					builder("de_CH")
							.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));
		}

		@Test
		public void case_0150()
		{
			final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", true, "1234", prepareCountry("Country1", "@A2@ @A1@ (Postfach @PB@) @P@ @C@ @CO@"));
			boolean isLocalAddress = false;
			final String bPartnerBlock = null;
			final String userBlock = null;

			assertEquals(
					"addr2\naddr1\nPostfach MSG_POBox 1234\n121212 City1\nCountry1",
					builder("de_CH")
							.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));

			isLocalAddress = true;

			assertEquals(
					"LOCAL: addr2\naddr1\nPostfach MSG_POBox 1234\n121212 City1\nCountry1",
					builder("de_CH")
							.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));
		}

		@Test
		public void case_0160()
		{
			final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", false, "", prepareCountry("Country1", "@A2@ @A1@ @PB@ @P@ @C@ @CO@"));
			boolean isLocalAddress = false;
			final String bPartnerBlock = null;
			final String userBlock = null;

			assertEquals(
					"addr2\naddr1\n121212 City1\nCountry1",
					builder("de_CH")
							.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));

			isLocalAddress = true;

			assertEquals(
					"LOCAL: addr2\naddr1\n121212 City1\nCountry1",
					builder("de_CH")
							.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));
		}

		@Test
		public void case_0170()
		{
			final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", true, "1234", prepareCountry("Country1", "@A2@ @A1@ @PB@ @P@ @C@ @CO@"));
			boolean isLocalAddress = false;
			final String bPartnerBlock = null;
			final String userBlock = null;

			assertEquals(
					"addr2\naddr1\nMSG_POBox 1234\n121212 City1\nCountry1",
					builder("de_CH")
							.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));

			isLocalAddress = true;

			assertEquals(
					"LOCAL: addr2\naddr1\nMSG_POBox 1234\n121212 City1\nCountry1",
					builder("de_CH")
							.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));
		}

		@Test
		public void case_0180()
		{
			final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", true, "1234", prepareCountry("Country1", "@A2@ @A1@ (TEST) @P@ @C@ @CO@"));
			final boolean isLocalAddress = false;
			final String bPartnerBlock = null;
			final String userBlock = null;

			assertEquals(
					"addr2\naddr1\n121212 City1\nCountry1",
					builder("de_CH")
							.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));
		}

		/**
		 * test case when we have sequences in C_COuntry_Sequence
		 */
		@Test
		public void case_0190()
		{
			final I_C_Country country = prepareCountry("Country1", "@A2@ @A1@ @P@ @C@ @CO@");
			final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", true, "1234", country);
			prepareCountrySequence(country, "@A2@ @A1@ Italien @P@ @C@ @CO@", "it_IT");

			final boolean isLocalAddress = false;
			final String bPartnerBlock = null;
			final String userBlock = null;

			assertEquals(
					"addr2\naddr1\nItalien 121212 City1\nCountry1",
					builder("it_IT")
							.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));
		}

		/**
		 * test case when the address is local and the country should be shown
		 */
		@Test
		public void case_0200()
		{
			final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", true, "1234", prepareCountry("Country1", "@A2@ @A1@ (TEST) @P@ @C@ @CO@"));
			final boolean isLocalAddress = true;
			final String bPartnerBlock = null;
			final String userBlock = null;

			assertEquals(
					"LOCAL: addr2\naddr1\n121212 City1\nCountry1",
					builder("de_CH")
							.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock));
		}
	}

	@Nested
	public class BPartnerBL_mkFullAddress
	{
		@Test
		public void case_0010()
		{

			final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", false, "", prepareCountry("Germany", "@BP@ @CON@ @A2@ @A1@ @P@ @C@ @CO@"));
			final I_C_BPartner_Location bpLocation = prepareBPLocation(location);
			final I_C_BPartner bPartner = BPartnerBuilder()
					.name("Name1")
					.name2("Name2")
					.isCompany(false)
					.AD_Language("de_DE")
					.build();
			final GreetingId greeting = prepareGreeting("Herr");
			final org.compiere.model.I_AD_User user = prepareUser("UserFN", "UserLN", "", greeting);

			assertEquals(
					"LOCAL:  \nHerr\nUserFN UserLN\naddr2\naddr1\n121212 City1\nGermany",
					bpartnerBL.mkFullAddress(bPartner, bpLocation, null, user));
		}

		@Test
		public void case_0020()
		{

			final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", false, "", prepareCountry("Germany", "@BP@ @CON@ @A2@ @A1@ @P@ @C@ @CO@"));
			final I_C_BPartner_Location bpLocation = prepareBPLocation(location);
			final I_C_BPartner bPartner = BPartnerBuilder()
					.name("Name1")
					.name2("Name2")
					.isCompany(false)
					.AD_Language("de_DE")
					.build();
			final GreetingId greeting = prepareGreeting("Herr");
			final org.compiere.model.I_AD_User user = prepareUser("UserFN", "", "", greeting);

			assertEquals(
					"LOCAL: \nName1\nName2\naddr2\naddr1\n121212 City1\nGermany",
					bpartnerBL.mkFullAddress(bPartner, bpLocation, null, user));
		}

		@Test
		public void case_0030()
		{

			final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", false, "", prepareCountry("Germany", "@BP@ (z.L. @GR@) @FN@ @LN@ @CON@ @A2@ @A1@ @P@ @C@ @CO@"));
			final I_C_BPartner_Location bpLocation = prepareBPLocation(location);
			final I_C_BPartner bPartner = BPartnerBuilder()
					.name("Name1")
					.name2("Name2")
					.isCompany(false)
					.AD_Language("de_DE")
					.build();
			final GreetingId greeting = prepareGreeting("Herr");
			final org.compiere.model.I_AD_User user = prepareUser("UserFN", "UserLN", "", greeting);

			assertEquals(
					"LOCAL:    \nz.L. Herr\nUserFN UserLN\naddr2\naddr1\n121212 City1\nGermany",
					bpartnerBL.mkFullAddress(bPartner, bpLocation, null, user));
		}

		/**
		 * task 04121 <br>
		 * check if the text between brackets is not printed if there is no variable in brackets
		 */
		@Test
		public void case_0040()
		{

			final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", false, "", prepareCountry("Germany", "@BP@ (GR) @FN@ @LN@ @CON@ @A2@ @A1@ @P@ @C@ @CO@"));
			final I_C_BPartner_Location bpLocation = prepareBPLocation(location);
			final I_C_BPartner bPartner = BPartnerBuilder()
					.name("Name1")
					.name2("Name2")
					.isCompany(false)
					.AD_Language("de_DE")
					.build();
			final GreetingId greeting = prepareGreeting("");
			final org.compiere.model.I_AD_User user = prepareUser("UserFN", "UserLN", "", greeting);

			assertEquals(
					"LOCAL:    \nUserFN UserLN\naddr2\naddr1\n121212 City1\nGermany",
					bpartnerBL.mkFullAddress(bPartner, bpLocation, null, user));
		}

		/**
		 * task 04121 <br>
		 * check if the text between brackets is printed if the greeting exists
		 */
		@Test
		public void case_0050()
		{

			final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", false, "",
					prepareCountry("Germany", "@BP@ (GR @GR@) @FN@ @LN@ @CON@ @A2@ @A1@ @P@ @C@ @CO@"));
			final I_C_BPartner_Location bpLocation = prepareBPLocation(location);
			final I_C_BPartner bPartner = BPartnerBuilder()
					.name("Name1")
					.name2("Name2")
					.isCompany(false)
					.AD_Language("de_DE")
					.build();
			final GreetingId greeting = prepareGreeting("Frau");
			final org.compiere.model.I_AD_User user = prepareUser("UserFN", "UserLN", "", greeting);

			assertEquals(
					"LOCAL:    \nGR Frau\nUserFN UserLN\naddr2\naddr1\n121212 City1\nGermany",
					bpartnerBL.mkFullAddress(bPartner, bpLocation, null, user));
		}

		/**
		 * task 04121 <br>
		 * check if the brackets are escaped also in user sequence
		 */
		@Test
		public void case_0060()
		{

			final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", false, "",
					prepareCountry("Germany", "@BP@ \\(test\\) @FN@ @LN@ @CON@ @A2@ @A1@ @P@ @C@ @CO@"));
			final I_C_BPartner_Location bpLocation = prepareBPLocation(location);
			final I_C_BPartner bPartner = BPartnerBuilder()
					.name("Name1")
					.name2("Name2")
					.isCompany(false)
					.AD_Language("de_DE")
					.build();
			final GreetingId greeting = prepareGreeting("Frau");
			final org.compiere.model.I_AD_User user = prepareUser("UserFN", "UserLN", "", greeting);

			assertEquals(
					"LOCAL:  (test)   \nUserFN UserLN\naddr2\naddr1\n121212 City1\nGermany",
					bpartnerBL.mkFullAddress(bPartner, bpLocation, null, user));
		}

		/**
		 * task 04266 <br>
		 * check if the greeting is not printed, in case of company
		 */
		@Test
		public void case_0070()
		{

			final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", false, "",
					prepareCountry("Germany", "@BP@ (GR @GR@) @FN@ @LN@ @CON@ @A2@ @A1@ @P@ @C@ @CO@"));
			final I_C_BPartner_Location bpLocation = prepareBPLocation(location);
			final I_C_BPartner bPartner = BPartnerBuilder()
					.name("Name1")
					.name2("Name2")
					.isCompany(true)
					.AD_Language("de_DE")
					.build();
			final GreetingId greeting = prepareGreeting("Frau");
			final org.compiere.model.I_AD_User user = prepareUser("UserFN", "UserLN", "", greeting);

			assertEquals(
					"LOCAL: \nName1\nName2\nUserFN UserLN\naddr2\naddr1\n121212 City1\nGermany",
					bpartnerBL.mkFullAddress(bPartner, bpLocation, null, user));
		}

		/**
		 * task 04266 <br>
		 * check if the greeting is not printed, in case of company
		 */
		@Test
		public void case_0080()
		{

			final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", false, "",
					prepareCountry("Germany", "@BP@ (GR @GR@) @FN@ @LN@ @CON@ @A2@ @A1@ @P@ @C@ @CO@"));
			final I_C_BPartner_Location bpLocation = prepareBPLocation(location);
			final I_C_BPartner bPartner = BPartnerBuilder()
					.name("CompanyAG")
					.name2("")
					.isCompany(true)
					.build();
			final GreetingId greeting = prepareGreeting("Frau");
			final org.compiere.model.I_AD_User user = prepareUser("UserFN", "UserLN", "", greeting);

			assertEquals(
					"LOCAL: \nCompanyAG\nUserFN UserLN\naddr2\naddr1\n121212 City1\nGermany",
					bpartnerBL.mkFullAddress(bPartner, bpLocation, null, user));
		}

		/**
		 * task 04266 <br>
		 * check if after greeting we have BR, in case of not company
		 */
		@Test
		public void case_0090()
		{

			final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", false, "",
					prepareCountry("Germany", "@BP@ @GR@ @FN@ @LN@ @CON@ @A2@ @A1@ @P@ @C@ @CO@"));
			final I_C_BPartner_Location bpLocation = prepareBPLocation(location);
			final I_C_BPartner bPartner = BPartnerBuilder()
					.name("Name1")
					.name2("Name2")
					.isCompany(false)
					.build();
			final GreetingId greeting = prepareGreeting("Frau");
			final org.compiere.model.I_AD_User user = prepareUser("UserFN", "UserLN", "", greeting);

			assertEquals(
					"LOCAL:     \nFrau\nUserFN UserLN\naddr2\naddr1\n121212 City1\nGermany",
					bpartnerBL.mkFullAddress(bPartner, bpLocation, null, user));
		}

		/**
		 * task 04266 <br>
		 * check if after greeting we have BR, in case of not company
		 */
		@Test
		public void case_0100()
		{

			final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", false, "",
					prepareCountry("Germany", "@BP@ (z.L. @GR@) @CON@ @A2@ @A1@ (PF @PB@) @P@ @C@ @CO@"));
			final I_C_BPartner_Location bpLocation = prepareBPLocation(location);
			final I_C_BPartner bPartner = BPartnerBuilder()
					.name("Name1")
					.name2("Name2")
					.isCompany(false)
					.build();
			final GreetingId greeting = prepareGreeting("Frau");
			final org.compiere.model.I_AD_User user = prepareUser("UserFN", "UserLN", "", greeting);

			assertEquals(
					"LOCAL:  \nz.L. Frau\naddr2\naddr1\n121212 City1\nGermany",
					bpartnerBL.mkFullAddress(bPartner, bpLocation, null, user));
		}

		/**
		 * task 04266 <br>
		 * check if after greeting we have BR, in case of not company
		 */
		@Test
		public void case_0110()
		{

			final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", false, "",
					prepareCountry("Germany", "@BP@ @CON@ @A2@ @A1@ @A3@ (Postfach @PB@) @P@ @C@ @CO@"));
			final I_C_BPartner_Location bpLocation = prepareBPLocation(location);
			final I_C_BPartner bPartner = BPartnerBuilder()
					.name("Name1")
					.name2("Name2")
					.isCompany(false)
					.build();
			final GreetingId greeting = prepareGreeting("Frau");
			final org.compiere.model.I_AD_User user = prepareUser("UserFN", "UserLN", "", greeting);

			assertEquals(
					"LOCAL:  \nFrau\nUserFN UserLN\naddr2\naddr1\n121212 City1\nGermany",
					bpartnerBL.mkFullAddress(bPartner, bpLocation, null, user));
		}

		@Test
		public void case_0120_OverrideLocationId()
		{
			final I_C_Country country = prepareCountry("Germany", "@BP@ @CON@ @A2@ @A1@ @P@ @C@ @CO@");
			final I_C_Location location = prepareLocation("a1", "a2", null, null, "City1", "Region1", "121212", false, "", country);
			final I_C_Location locationOverride = prepareLocation("a1_override", "a2_override", null, null, "City1", "Region1", "121212", false, "", country);
			final LocationId locationOverrideId = LocationId.ofRepoId(locationOverride.getC_Location_ID());

			final I_C_BPartner_Location bpLocation = prepareBPLocation(location);
			final I_C_BPartner bPartner = BPartnerBuilder()
					.name("Name1")
					.name2("Name2")
					.isCompany(false)
					.AD_Language("de_DE")
					.build();
			final GreetingId greetingId = prepareGreeting("Herr");
			final org.compiere.model.I_AD_User user = prepareUser("UserFN", "UserLN", "", greetingId);

			assertEquals(
					"LOCAL:  \nHerr\nUserFN UserLN\na2_override\na1_override\n121212 City1\nGermany",
					bpartnerBL.mkFullAddress(bPartner, bpLocation, locationOverrideId, user));
		}

		/**
		 * Test if isPOBoxNum is set to true, but no PO Box number is set.
		 */
		@Test
		public void case_0130_isPOBox()
		{
			final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", true, "",
					prepareCountry("Germany", "@BP@ @CON@ @A2@ @A1@ @A3@ (Postfach @PB@) @P@ @C@ @CO@"));
			final I_C_BPartner_Location bpLocation = prepareBPLocation(location);
			final I_C_BPartner bPartner = BPartnerBuilder()
					.name("Name1")
					.name2("Name2")
					.isCompany(false)
					.AD_Language("de_DE")
					.build();
			final GreetingId greeting = prepareGreeting("Herr");
			final org.compiere.model.I_AD_User user = prepareUser("UserFN", "UserLN", "", greeting);

			assertEquals(
					"LOCAL:  \nHerr\nUserFN UserLN\naddr2\naddr1\nPostfach MSG_POBox\n121212 City1\nGermany",
					bpartnerBL.mkFullAddress(bPartner, bpLocation, null, user));
		}

		/**
		 * check if the tokens BP_Name and BP_GR are taken in consideration
		 */
		@Test
		public void test_buildBPartnerAddressStringBPartnerBlock_0120()
		{

			final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", false, "",
					prepareCountry("Germany", "@BP_GR@ @BP_Name@ @A2@ @A1@ @A3@ (Postfach @PB@) @P@ @C@ @CO@"));

			final I_C_BPartner_Location bpLocation = prepareBPLocation(location);
			final GreetingId greetingId = prepareGreeting("Frau");

			final I_C_BPartner bPartner = BPartnerBuilder()
					.name("Name1")
					.name2("Name2")
					.greetingId(greetingId)
					.isCompany(false)
					.build();

			final org.compiere.model.I_AD_User user = prepareUser("UserFN", "UserLN", "", greetingId);

			assertEquals(
					"LOCAL:  \nFrau\nName1\naddr2\naddr1\n121212 City1\nGermany",
					bpartnerBL.mkFullAddress(bPartner, bpLocation, null, user));
		}

		/**
		 * check if the tokens BP_Name and BP can not be used together
		 */
		@Test
		public void test_buildBPartnerAddressStringBPartnerBlock_0130()
		{

			final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", false, "",
					prepareCountry("Germany", "@BP@ @BP_Name@ @A2@ @A1@ @A3@ (Postfach @PB@) @P@ @C@ @CO@"));

			final I_C_BPartner_Location bpLocation = prepareBPLocation(location);
			final GreetingId greetingId = prepareGreeting("Frau");

			final I_C_BPartner bPartner = BPartnerBuilder()
					.name("Name1")
					.name2("Name2")
					.greetingId(greetingId)
					.isCompany(false)
					.build();

			final org.compiere.model.I_AD_User user = prepareUser("UserFN", "UserLN", "", greetingId);

			assertThrows(AdempiereException.class, () -> bpartnerBL.mkFullAddress(bPartner, bpLocation, null, user));
		}

		/**
		 * check if the tokens BP_Name and BP can BE used together
		 */
		@Test
		public void test_buildBPartnerAddressStringBPartnerBlock_0140()
		{

			final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", false, "",
														  prepareCountry("Germany", "@BP_GR@ @BP_Name@ @CON@ @A2@ @A1@ @A3@ (Postfach @PB@) @P@ @C@ @CO@"));

			final I_C_BPartner_Location bpLocation = prepareBPLocation(location);
			final GreetingId greetingId = prepareGreeting("Frau");

			final I_C_BPartner bPartner = BPartnerBuilder()
					.name("Name1")
					.name2("Name2")
					.isCompany(true)
					.build();

			final org.compiere.model.I_AD_User user = prepareUser("UserFN", "UserLN", "someTitle", greetingId);

			final String actual = bpartnerBL.mkFullAddress(bPartner, bpLocation, null, user);

			assertEquals(
					"LOCAL:  \nName1\nName2\nFrau someTitle UserFN UserLN\naddr2\naddr1\n121212 City1\nGermany",
					actual);
		}


		/**
		 * check if the token CON can be used in case on non company users
		 */
		@Test
		public void test_buildBPartnerAddressStringBPartnerBlock_0150()
		{

			final I_C_Location location = prepareLocation("addr1", "addr2", null, null, "City1", "Region1", "121212", false, "",
														  prepareCountry("Germany", "@BP_GR@ @BP_Name@ @CON@ @A2@ @A1@ @A3@ (Postfach @PB@) @P@ @C@ @CO@"));

			final I_C_BPartner_Location bpLocation = prepareBPLocation(location);
			final GreetingId greetingId = prepareGreeting("Frau");

			final I_C_BPartner bPartner = BPartnerBuilder()
					.name("Name1")
					.name2("Name2")
					.isCompany(false)
					.build();

			final org.compiere.model.I_AD_User user = prepareUser("UserFN", "UserLN", "someTitle", greetingId);

			final String actual = bpartnerBL.mkFullAddress(bPartner, bpLocation, null, user);

			assertEquals(
					"LOCAL:   \nFrau\nsomeTitle UserFN UserLN\naddr2\naddr1\n121212 City1\nGermany",
					actual);
		}



		// prepraring methods
		private I_C_Country prepareCountry(final String countryName, final String displaySequence)
		{
			final I_C_Country country = newInstance(I_C_Country.class);
			country.setName(countryName);
			country.setDisplaySequence(displaySequence);
			country.setDisplaySequenceLocal("LOCAL: " + displaySequence);
			saveRecord(country);

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
			saveRecord(countrySeq);
		}

		private OrgId prepareOrgId()
		{
			final I_AD_Org org = newInstance(I_AD_Org.class);
			org.setName("Org1");
			org.setValue("1");
			saveRecord(org);

			return OrgId.ofRepoId(org.getAD_Org_ID());
		}

		@SuppressWarnings("SameParameterValue")
		private I_C_Location prepareLocation(
				final String a1,
				final String a2,
				final String a3,
				final String a4,
				final String city,
				final String regionName,
				final String postal,
				final boolean isPOBoxNum,
				final String POBox,
				final I_C_Country country)
		{
			final I_C_Location location = newInstance(I_C_Location.class);
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
			saveRecord(location);

			return location;
		}

		private GreetingId prepareGreeting(@NonNull final String name)
		{
			final Greeting greeting = greetingRepository.upsertGreeting(
					UpsertGreetingRequest.builder()
							.name(name)
							.greeting(name)
							.orgId(orgId)
							.build());

			return greeting.getId();
		}

		@SuppressWarnings("SameParameterValue")
		private org.compiere.model.I_AD_User prepareUser(final String firstName, final String lastName, final String title, final GreetingId greetingId)
		{
			final org.compiere.model.I_AD_User user = InterfaceWrapperHelper.create(Env.getCtx(), I_AD_User.class, ITrx.TRXNAME_None);
			user.setFirstname(firstName);
			user.setLastname(lastName);
			user.setTitle(title);
			user.setAD_Org_ID(orgId.getRepoId());
			user.setC_Greeting_ID(GreetingId.toRepoId(greetingId));
			InterfaceWrapperHelper.save(user);

			return user;
		}
	}
}