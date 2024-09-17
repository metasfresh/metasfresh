package de.metas.location.impl;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.greeting.Greeting;
import de.metas.greeting.GreetingId;
import de.metas.greeting.GreetingRepository;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.Language;
import de.metas.location.AddressDisplaySequence;
import de.metas.location.Addressvars;
import de.metas.location.CountryId;
import de.metas.location.CountrySequences;
import de.metas.location.ICountryDAO;
import de.metas.location.ILocationBL;
import de.metas.location.LocationId;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Location;
import org.compiere.util.Env;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public class AddressBuilder
{
	private static final Logger log = LogManager.getLogger(AddressBuilder.class);
	private final ICountryDAO countriesRepo = Services.get(ICountryDAO.class);
	private final GreetingRepository greetingRepository = SpringContextHolder.instance.getBean(GreetingRepository.class);
	private final ILocationBL locationBL = Services.get(ILocationBL.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	public static final AdMessageKey MSG_POBox = AdMessageKey.of("MSG_POBox");
	private static final String TOKEN_NewLine = "CR";

	/**
	 * org is mandatory; we need it when we retrieve country sequences; needs to be a perfect match
	 */
	@NonNull
	private final OrgId orgId;
	private final String adLanguage;

	@Builder
	private AddressBuilder(
			@Nullable final OrgId orgId,
			@Nullable final String adLanguage)
	{
		this.orgId = orgId != null ? orgId : OrgId.ANY;
		this.adLanguage = adLanguage;
	}

	@NonNull
	private OrgId getOrgId()
	{
		return orgId;
	}

	private String getAD_Language()
	{
		return adLanguage;
	}

	@RequiredArgsConstructor
	@Getter
	private enum UserVars
	{
		Title("TI"),

		LastName("LN"),

		FirstName("FN"),

		Greeting("GR");

		@NonNull private final String name;

		private static final ImmutableMap<String, UserVars> byName = Maps.uniqueIndex(Arrays.asList(values()), UserVars::getName);

		public static boolean isValidName(final String name)
		{
			return byName.get(name) != null;
		}
	}

	/**
	 * Build address string
	 *
	 * @param isLocalAddress true if this is a local address (i.e. location's country is same as our tenant)
	 */
	public String buildAddressString(
			final I_C_Location location,
			final boolean isLocalAddress,
			@Nullable final String bPartnerBlock,
			@Nullable final String userBlock)
	{
		final I_C_Country country = countriesRepo.getById(CountryId.ofRepoId(location.getC_Country_ID()));
		final AddressDisplaySequence displaySequence = getDisplaySequence(country, isLocalAddress);
		return buildAddressString(location, displaySequence, bPartnerBlock, userBlock);
	}

	private String buildAddressString(
			@NonNull final I_C_Location location,
			@NonNull final AddressDisplaySequence displaySequence,
			@Nullable final String bPartnerBlock,
			@Nullable final String userBlock)
	{
		final List<String> bracketsTxt = extractBracketsString(displaySequence);

		String inStr = displaySequence.getPattern();
		final StringBuilder outStr = new StringBuilder();

		// treat brackets cases first if exists
		for (final String s : bracketsTxt)
		{
			Check.assume(s.startsWith("(") || s.startsWith("\\("), "Expected brackets or escaped brackets!");
			Check.assume(s.endsWith(")") || s.endsWith("\\)"), "Expected brackets or escaped brackets!");

			String in = s;
			final StringBuilder out = new StringBuilder();
			if (s.startsWith("("))
			{
				in = in.substring(1, s.length() - 1); // take out brackets
				replaceAddrToken(location, in, out, bPartnerBlock, userBlock, true);
			}
			else if (s.startsWith("\\("))
			{
				in = in.substring(1, in.length() - 2).concat(")"); // take out escaped chars
				replaceAddrToken(location, in, out, bPartnerBlock, userBlock, false);
			}

			// take the plus space
			if (out.length() == 0)
			{
				inStr = inStr.replace(s.concat(" "), out.toString());
			}
			else
			{
				if (out.lastIndexOf("\n") == out.length() - 1)
				{
					inStr = inStr.replace(s + " ", out.toString());
				}
				else
				{
					inStr = inStr.replace(s, out.toString());
				}
			}
		}

		// old behavior
		// variables in brackets already parsed
		replaceAddrToken(location, inStr, outStr, bPartnerBlock, userBlock, false);

		return StringUtils.replace(outStr.toString().trim(), "\\n", "\n");
	}

	/**
	 * replace variables
	 */
	private void replaceAddrToken(
			@NonNull final I_C_Location location,
			String inStr,
			StringBuilder outStr,
			final String bPartnerBlock,
			final String userBlock,
			final boolean withBrackets)
	{
		final CountryId countryId = CountryId.ofRepoId(location.getC_Country_ID());

		final boolean explicitBreaks = inStr.contains("@CR@");

		String token;
		int i = inStr.indexOf('@');

		String prefixTxt = "";
		if (withBrackets && i != -1)
		{
			prefixTxt = inStr.substring(0, i); // up to @
		}

		while (i != -1)
		{
			if (!withBrackets)
			{
				final String text = inStr.substring(0, i); // until first @
				if (!Check.isEmpty(text, false))
				{
					// add text, even if is empty, if is not new line or no new line
					if ((outStr.lastIndexOf("\n") == -1 || outStr.lastIndexOf("\n") != outStr.length() - 1) && !text.isEmpty())
					{
						outStr.append(text);
					}
					// add text without begining empty space if we have new line
					else if (outStr.lastIndexOf("\n") == outStr.length() - 1 && !text.trim().isEmpty())
					{
						outStr.append(StringUtils.cleanBeginWhitespace(text));
					}
				}
			}

			inStr = inStr.substring(i + 1); // from first @

			int j = inStr.indexOf('@'); // next @
			if (j < 0)
			{
				token = ""; // no second tag
				j = i + 1;
			}
			else
			{
				token = inStr.substring(0, j);
			}

			final String language = adLanguage == null ? Language.getBaseAD_Language() : adLanguage;
			// Tokens
			if (token.equals(Addressvars.City.getName()))
			{
				if (location.getCity() != null)
				{
					outStr.append(location.getCity());
					if (!explicitBreaks)
					{
						outStr.append('\n');
					}
				}
			}
			else if (token.equals(Addressvars.Region.getName()))
			{
				if (location.getC_Region() != null)
				{
					outStr.append(location.getC_Region().getName());
				}
				else if (location.getRegionName() != null
						&& !location.getRegionName().isEmpty())
				{
					outStr.append(location.getRegionName()); // local region name
				}
			}
			else if (token.equals(Addressvars.Postal.getName()))
			{
				if (location.getPostal() != null)
				{
					outStr.append(location.getPostal());
				}
			}
			else if (token.equals(Addressvars.Postal_Add.getName()))
			{
				final String add = location.getPostal_Add();
				if (add != null && !add.isEmpty())
				{
					outStr.append("-").append(add);
				}
			}
			else if (token.equals(Addressvars.Country.getName()))
			{
				final String countryName = countriesRepo.getCountryNameById(countryId).translate(language);
				if (!Check.isEmpty(countryName, true))
				{
					outStr.append(countryName);
				}
			}
			else if (token.equals(Addressvars.Address1.getName()))
			{
				final String address1 = location.getAddress1();
				if (address1 != null && !address1.isEmpty())
				{
					outStr.append(address1);
					if (!explicitBreaks)
					{
						outStr.append('\n');
					}
				}
			}
			else if (token.equals(Addressvars.Address2.getName()))
			{
				final String address2 = location.getAddress2();
				if (address2 != null && !address2.isEmpty())
				{
					outStr.append(address2);
					if (!explicitBreaks)
					{
						outStr.append('\n');
					}
				}
			}
			else if (token.equals(Addressvars.Address3.getName()))
			{
				final String address3 = location.getAddress3();
				if (address3 != null && !address3.isEmpty())
				{
					outStr.append(address3);
					if (!explicitBreaks)
					{
						outStr.append('\n');
					}
				}
			}
			else if (token.equals(Addressvars.Address4.getName()))
			{
				final String address4 = location.getAddress4();
				if (address4 != null && !address4.isEmpty())
				{
					outStr.append(address4);
					if (!explicitBreaks)
					{
						outStr.append('\n');
					}
				}
			}
			else if (token.equals(Addressvars.BPartner.getName()) || token.equals(Addressvars.BPartnerName.getName()))
			{
				if (!Check.isEmpty(bPartnerBlock, true))
				{
					if (!explicitBreaks && !Check.isEmpty(outStr.toString(), true) && !outStr.toString().endsWith("\n"))
					{
						outStr.append('\n');
					}
					outStr.append(bPartnerBlock.trim());
					if (!explicitBreaks)
					{
						outStr.append('\n');
					}
				}
			}
			else if (token.equals(Addressvars.Contact.getName()))
			{
				if (!Check.isEmpty(userBlock, true))
				{
					if (!explicitBreaks && !Check.isEmpty(outStr.toString(), true) && !outStr.toString().endsWith("\n"))
					{
						outStr.append('\n');
					}
					outStr.append(userBlock.trim());
					if (!explicitBreaks)
					{
						outStr.append('\n');
					}
				}
			}
			else if (TOKEN_NewLine.equals(token))
			{
				outStr.append('\n');
			}
			else if (Addressvars.POBox.getName().equals(token)) // postal box
			{
				if (location.isPOBoxNum())
				{
					outStr.append(msgBL.getTranslatableMsgText(MSG_POBox).translate(language));
					// if we have box number, added it as it is
					if (!Check.isBlank(location.getPOBox()))
					{
						outStr.append(' ').append(location.getPOBox());
					}

					// add an automatic new line
					if (!explicitBreaks)
					{
						outStr.append('\n');
					}
				}
			}
			else if (UserVars.isValidName(token))
			{
				// nothing to do
			}
			else
			{
				log.warn("Token {} is not recognized in display sequence of country {}", token, countryId);
			}

			inStr = inStr.substring(j + 1); // from second @
			i = inStr.indexOf('@');
		}

		if (withBrackets)
		{
			// if variables are empty, don't put prefix
			if (StringUtils.cleanWhitespace(outStr.toString()).isEmpty())
			{
				outStr = new StringBuilder();
				return;
			}
			else
			{
				outStr.insert(0, prefixTxt);
			}
		}

		outStr.append(inStr); // add the rest of the string
	}

	public String buildBPartnerFullAddressString(
			@Nullable final org.compiere.model.I_C_BPartner bpartner,
			@Nullable final org.compiere.model.I_C_BPartner_Location bpLocation,
			@Nullable final LocationId locationId,
			@Nullable final I_AD_User bpContact)
	{
		return buildBPartnerFullAddressString(bpartner, bpLocation, locationId, bpContact, null);
	}

	public String buildBPartnerFullAddressString(
			@Nullable final org.compiere.model.I_C_BPartner bpartner,
			@Nullable final org.compiere.model.I_C_BPartner_Location bpLocation,
			@Nullable final LocationId locationId,
			@Nullable final I_AD_User bpContact,
			@Nullable final AddressDisplaySequence displaySequenceParam)
	{
		if (bpartner == null || bpLocation == null)
		{
			log.debug("One of bpartner={}, location={} is null. Returning empty string.", bpartner, bpLocation);
			return "";
		}

		final LocationId effectiveLocationId = locationId != null ? locationId : LocationId.ofRepoIdOrNull(bpLocation.getC_Location_ID());
		if (effectiveLocationId == null)
		{
			return "";
		}
		final I_C_Location location = locationBL.getRecordById(effectiveLocationId);
		if (location == null)
		{
			log.warn("No location found for {}. Returning empty address string.", effectiveLocationId);
			return "";
		}

		final AddressDisplaySequence displaySequenceToUse = displaySequenceParam != null ? displaySequenceParam : extractDisplaySequence(location);
		final String bPartnerBlock = buildBPartnerBlock(bpartner, bpContact, bpLocation, displaySequenceToUse);
		final String userBlock = buildUserBlock(bpartner, displaySequenceToUse, bpContact);
		return buildAddressString(location, displaySequenceToUse, bPartnerBlock, userBlock);
	}

	@NonNull
	private AddressDisplaySequence extractDisplaySequence(@NonNull final I_C_Location location)
	{
		final boolean isLocal = isLocalCountry(location);
		final CountryId countryId = CountryId.ofRepoId(location.getC_Country_ID());
		final I_C_Country countryRecord = countriesRepo.getById(countryId);
		final AddressDisplaySequence displaySequence = getDisplaySequence(countryRecord, isLocal);
		try
		{
			displaySequence.assertValid();
		}
		catch (final AdempiereException e)
		{
			throw e.setParameter("C_Country ", countryRecord)
					.setParameter("IsLocalCountry", isLocal)
					.setParameter("C_Location", location);
		}
		return displaySequence;
	}

	private boolean isLocalCountry(@NonNull final I_C_Location location)
	{
		final I_C_Country countryLocal = getLocalCountry();
		return location.getC_Country_ID() == countryLocal.getC_Country_ID();
	}

	private I_C_Country getLocalCountry()
	{
		final Properties ctx = Env.getCtx();
		return countriesRepo.getDefault(ctx);
	}

	private String buildBPartnerBlock(
			@NonNull final org.compiere.model.I_C_BPartner bpartner,
			@Nullable final I_AD_User user,
			@NonNull final I_C_BPartner_Location bplocation,
			@NonNull final AddressDisplaySequence displaySequence)
	{
		final BPartnerInfo bpInfos = extractBPartnerInfos(bpartner, user, bplocation, displaySequence);
		final StringBuilder sbBPartner = new StringBuilder();

		final String bpGreeting = bpInfos.getBpGreeting();
		final String bpName = bpInfos.getBpName();
		final String bpName2 = bpInfos.getBpName2();

		if (Check.isNotBlank(bpGreeting))
		{
			sbBPartner.append(bpGreeting);
		}

		if (Check.isNotBlank(bpName))
		{
			if (sbBPartner.length() > 0)
			{
				sbBPartner.append('\n');
			}
			sbBPartner.append(bpName);
		}

		if (Check.isNotBlank(bpName2))
		{
			sbBPartner.append('\n');
			sbBPartner.append(bpName2);
		}

		return sbBPartner.toString();
	}

	private BPartnerInfo extractBPartnerInfos(
			@NonNull final org.compiere.model.I_C_BPartner bPartner,
			@Nullable final I_AD_User bpContact,
			@NonNull final I_C_BPartner_Location bplocation,
			@NonNull final AddressDisplaySequence displaySequence)
	{
		// Name, Name2, bp greeting
		String bpName = "";
		String bpName2 = "";
		String bpGreeting = "";

		final boolean existsBPName = displaySequence.hasToken(Addressvars.BPartnerName);
		final boolean existsBPGreeting = displaySequence.hasToken(Addressvars.BPartnerGreeting);
		final boolean bpartnerHasGreeting = bPartner.getC_Greeting_ID() > 0;

		//Use Partner name and greeting when rendering address only if there is greeting set
		if ((existsBPName || existsBPGreeting) && !bPartner.isCompany() && bpartnerHasGreeting)
		{
			if (existsBPName)
			{
				bpName = bPartner.getName();
			}

			if (existsBPGreeting)
			{
				final GreetingId greetingIdOfBPartner = GreetingId.ofRepoIdOrNull(bPartner.getC_Greeting_ID());
				final Greeting greetingOfBPartner = greetingIdOfBPartner != null
						? greetingRepository.getById(greetingIdOfBPartner)
						: null;
				if (greetingOfBPartner != null)
				{
					bpGreeting = greetingOfBPartner.getName();
				}
			}
		}
		else
		{
			if (bPartner.isCompany()
					|| bpContact == null
					|| bpContact.getAD_User_ID() == 0
					|| Check.isBlank(bpContact.getLastname()))
			{
				// task https://github.com/metasfresh/metasfresh/issues/5804
				// prefer BPartner name from location if is set
				final String bpartnerNameFromBPLocation = bplocation.getBPartnerName();
				if (Check.isNotBlank(bpartnerNameFromBPLocation))
				{
					bpName = bpartnerNameFromBPLocation;
					bpName2 = null;
				}
				else
				{
					bpName = bPartner.getName();
					bpName2 = StringUtils.trimBlankToNull(bPartner.getName2());
				}
			}
		}

		return BPartnerInfo.builder()
				.bpName(bpName)
				.bpName2(bpName2)
				.bpGreeting(bpGreeting)
				.build();
	}

	private void replaceUserToken(String inStr, final I_AD_User user, final boolean withBrackets, StringBuilder outStr, final boolean isPartnerCompany)
	{
		String userGreeting = "";
		final GreetingId greetingIdOfUser = GreetingId.ofRepoIdOrNull(user.getC_Greeting_ID());
		final Greeting greetingOfUser = greetingIdOfUser != null
				? greetingRepository.getById(greetingIdOfUser)
				: null;
		if (greetingOfUser != null)
		{
			userGreeting = greetingOfUser.getName();
		}

		final String userName = user.getLastname();
		final String userVorname = user.getFirstname();
		final String userTitle = user.getTitle();

		String token;
		int i = inStr.indexOf('@');

		String prefixTxt = "";
		if (withBrackets && i != -1)
		{
			prefixTxt = inStr.substring(0, i); // up to @
		}

		if (!withBrackets)
		{
			// remove the text before other variables
			final String preText = inStr.substring(i + 1); // from first @

			final int j = preText.indexOf('@'); // next @
			if (j < 0)
			{
				token = ""; // no second tag
			}
			else
			{
				token = preText.substring(0, j);
			}

			if (!UserVars.FirstName.toString().equals(token) || !UserVars.LastName.toString().equals(token) || !UserVars.Title.toString().equals(token) || !UserVars.Greeting.toString().equals(token))
			{
				inStr = inStr.substring(i + 1 + j + 1);
				i = inStr.indexOf('@');
			}
		}

		while (i != -1)
		{
			if (!withBrackets)
			{
				final String text = inStr.substring(0, i); // until first @
				if (!Check.isEmpty(text, false))
				{
					// add text, even if is empty, if is not new line or no new line
					if ((outStr.lastIndexOf("\n") == -1 || outStr.lastIndexOf("\n") != outStr.length() - 1) && !text.isEmpty())
					{
						outStr.append(text);
					}
					// add text without beginning empty space if we have new line
					else if (outStr.lastIndexOf("\n") == outStr.length() - 1 && !text.trim().isEmpty())
					{
						outStr.append(StringUtils.cleanBeginWhitespace(text));
					}
				}
			}

			inStr = inStr.substring(i + 1); // from first @

			int j = inStr.indexOf('@'); // next @
			if (j < 0)
			{
				token = ""; // no second tag
				j = i + 1;
			}
			else
			{
				token = inStr.substring(0, j);
			}

			// Tokens
			if (token.equals("TI"))
			{
				if (!Check.isEmpty(userTitle, true))
				{
					outStr.append(userTitle);
				}
			}
			else if (token.equals("GR"))
			{
				if (!Check.isEmpty(userGreeting, true) && !isPartnerCompany)
				{
					outStr.append(userGreeting);
					outStr.append('\n');
				}
			}
			else if (token.equals("FN"))
			{
				if (!Check.isEmpty(userVorname, true))
				{
					outStr.append(userVorname);
				}
			}
			else if (token.equals("LN"))
			{
				if (!Check.isEmpty(userName, true))
				{
					outStr.append(userName);
				}
			}
			else if (TOKEN_NewLine.equals(token))
			{
				outStr.append('\n');
			}
			inStr = inStr.substring(j + 1); // from second @
			i = inStr.indexOf('@');
		}

		if (withBrackets)
		{
			// if variables are empty, don't put prefix
			if (StringUtils.cleanWhitespace(outStr.toString()).isEmpty())
			{
				outStr = new StringBuilder();
				return;
			}
			else
			{
				outStr.insert(0, prefixTxt);
			}
		}

		outStr.append(inStr); // add the rest of the string
	}

	/**
	 * build User block
	 */
	private String buildUserBlock(
			@NonNull final org.compiere.model.I_C_BPartner bPartner,
			final AddressDisplaySequence displaySequence,
			final I_AD_User user)
	{

		final boolean existsBPName = displaySequence.hasToken(Addressvars.BPartnerName);
		final boolean existsBPGreeting = displaySequence.hasToken(Addressvars.BPartnerGreeting);
		final boolean bpartnerHasGreeting = bPartner.getC_Greeting_ID() > 0;

		if ((existsBPName || existsBPGreeting) && !bPartner.isCompany() && bpartnerHasGreeting)
		{
			return "";
		}

		final boolean isPartnerCompany = bPartner.isCompany();
		final Language language = Language.optionalOfNullable(bPartner.getAD_Language())
				.orElseGet(Language::getBaseLanguage);

		String userGreeting = "";
		if (user != null)
		{
			// Greeting
			final GreetingId greetingId = GreetingId.ofRepoIdOrNull(user.getC_Greeting_ID());
			if (greetingId != null)
			{
				final Greeting greeting = greetingRepository.getById(greetingId);
				userGreeting = greeting.getGreeting(language.getAD_Language());
			}

			final String userName = user.getLastname();
			final String userVorname = user.getFirstname();
			final String userTitle = user.getTitle();

			//
			// construct string

			String ds = displaySequence.getPattern();

			final List<String> bracketsTxt = extractBracketsString(displaySequence);

			// treat brackets cases first if exists
			for (final String s : bracketsTxt)
			{
				Check.assume(s.startsWith("(") || s.startsWith("\\("), "Expected brackets or escaped brackets!");
				Check.assume(s.endsWith(")") || s.endsWith("\\)"), "Expected brackets or escaped brackets!");

				String in = s;
				final StringBuilder out = new StringBuilder();
				if (s.startsWith("("))
				{
					in = in.substring(1, s.length() - 1); // take out brackets
					replaceUserToken(in, user, true, out, isPartnerCompany);
				}
				else if (s.startsWith("\\("))
				{
					in = in.substring(1, in.length() - 2).concat(")"); // take out escaped chars
					replaceUserToken(in, user, true, out, isPartnerCompany);
				}

				// take the plus space
				if (out.length() == 0)
				{
					ds = ds.replace(s.concat(" "), out.toString());
				}
				else
				{
					if (out.lastIndexOf("\n") == out.length() - 1)
					{
						ds = ds.replace(s.concat(" "), out.toString());
					}
					else
					{
						ds = ds.replace(s, out.toString());
					}
				}
			}

			final StringBuilder sbUser = new StringBuilder();
			replaceUserToken(ds, user, false, sbUser, isPartnerCompany);

			if (Check.isEmpty(sbUser.toString(), true))
			{
				if (!Check.isEmpty(userName))
				{
					addToken(userGreeting, sbUser);
					if (!Check.isEmpty(userGreeting, true) && !isPartnerCompany)
					{
						sbUser.append("\n");
					}
					addToken(userTitle, sbUser);
					addToken(userVorname, sbUser);
					addToken(userName, sbUser);
				}
			}

			return sbUser.toString();

		}

		return "";

	}

	/**
	 * Checks if the new token is Empty, if not it will be added. This method also makes sure, that between each newly
	 * added String there's exactly one " ". If sb is empty no " " will be added.
	 */
	private static void addToken(final String newToken, final StringBuilder sb)
	{
		if (!Check.isEmpty(newToken))
		{
			final String s = sb.toString();

			if (!s.endsWith("\n") && !s.endsWith(" ") && !s.isEmpty())
			{
				sb.append(" ");
			}
			sb.append(newToken);
		}
	}

	private static final Pattern PATTERN_BRACKETS = Pattern.compile("\\\\?(\\()(.+?)(\\))");

	private static List<String> extractBracketsString(final AddressDisplaySequence displaySequence)
	{
		final Matcher m = PATTERN_BRACKETS.matcher(displaySequence.getPattern());
		final List<String> bracketsTxt = new ArrayList<>();

		while (m.find())
		{
			bracketsTxt.add(m.group());
		}

		return bracketsTxt;
	}

	private AddressDisplaySequence getDisplaySequence(@NonNull final I_C_Country country, final boolean isLocalAddress)
	{
		final CountryId countryId = CountryId.ofRepoId(country.getC_Country_ID());
		final CountrySequences countrySequence = countriesRepo
				.getCountrySequences(countryId, getOrgId(), getAD_Language())
				.orElse(null);
		if (countrySequence == null)
		{
			return AddressDisplaySequence.ofNullable(isLocalAddress ? country.getDisplaySequenceLocal() : country.getDisplaySequence());
		}
		else
		{
			return isLocalAddress ? countrySequence.getLocalAddressDisplaySequence() : countrySequence.getAddressDisplaySequence();
		}
	}
}
