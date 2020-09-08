package de.metas.location.impl;

import de.metas.greeting.Greeting;
import de.metas.greeting.GreetingId;
import de.metas.greeting.GreetingRepository;
import de.metas.i18n.Language;
import de.metas.interfaces.I_C_BPartner;
import de.metas.location.CountryCustomInfo;
import de.metas.location.CountryId;
import de.metas.location.CountrySequences;
import de.metas.location.ICountryDAO;
import de.metas.location.ILocationBL;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Greeting;
import org.compiere.model.I_C_Location;
import org.compiere.util.Env;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.adempiere.model.InterfaceWrapperHelper.create;

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
	private static final transient Logger log = LogManager.getLogger(AddressBuilder.class);
	private final ICountryDAO countriesRepo = Services.get(ICountryDAO.class);
	private final GreetingRepository greetingRepository = SpringContextHolder.instance.getBean(GreetingRepository.class);

	/**
	 * org is mandatory; we need it when we retrieve country sequences; needs to be a perfect match
	 */
	private final OrgId orgId;
	private final String adLanguage;

	@Builder
	private AddressBuilder(
			final OrgId orgId,
			final String adLanguage)
	{
		this.orgId = orgId != null ? orgId : OrgId.ANY;
		this.adLanguage = adLanguage;
	}

	private OrgId getOrgId()
	{
		return orgId;
	}

	private String getAD_Language()
	{
		return adLanguage;
	}

	private static enum Uservars
	{
		Title("TI"),

		LastName("LN"),

		FirstName("FN"),

		Greeting("GR");

		private final String name;

		Uservars(String name)
		{
			this.name = name;
		}

		public String getName()
		{
			return name;
		}
	}

	/**
	 * Build address string
	 *
	 * @param location
	 * @param isLocalAddress true if this is a local address (i.e. location's country is same as our tenant)
	 * @param bPartnerBlock
	 * @param userBlock
	 * @return
	 */
	public String buildAddressString(
			final I_C_Location location,
			final boolean isLocalAddress,
			final String bPartnerBlock,
			final String userBlock)
	{
		final CountryId countryId = CountryId.ofRepoId(location.getC_Country_ID());
		final I_C_Country country = countriesRepo.getById(countryId);
		final String displaySequence = getDisplaySequence(country, isLocalAddress);

		String inStr = displaySequence;
		final StringBuilder outStr = new StringBuilder();

		final List<String> bracketsTxt = extractBracketsString(inStr);

		// treat brackets cases first if exists
		for (final String s : bracketsTxt)
		{
			Check.assume(s.startsWith("(") || s.startsWith("\\("), "Expected brackets or escaped brackets!");
			Check.assume(s.endsWith(")") || s.endsWith("\\)"), "Expected brackets or escaped brackets!");

			String in = new String(s);
			final StringBuilder out = new StringBuilder();
			if (s.startsWith("("))
			{
				in = in.substring(1, s.length() - 1); // take out brackets
				replaceAddrToken(location, isLocalAddress, in, out, bPartnerBlock, userBlock, true);
			}
			else if (s.startsWith("\\("))
			{
				in = in.substring(1, in.length() - 2).concat(")"); // take out escaped chars
				replaceAddrToken(location, isLocalAddress, in, out, bPartnerBlock, userBlock, false);
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
		replaceAddrToken(location, isLocalAddress, inStr, outStr, bPartnerBlock, userBlock, false);

		final String retValue = StringUtils.replace(outStr.toString().trim(), "\\n", "\n");
		return retValue;
	}

	/**
	 * replace variables
	 *
	 * @param location
	 * @param isLocalAddress {@code true} the given {@code inStr} is the *local* address sequence
	 * @param inStr
	 * @param outStr
	 * @param bPartnerBlock
	 * @param userBlock
	 * @param withBrackets
	 */
	private void replaceAddrToken(
			final I_C_Location location,
			boolean isLocalAddress,
			String inStr,
			StringBuilder outStr,
			String bPartnerBlock,
			String userBlock,
			final boolean withBrackets)
	{
		final CountryId countryId = CountryId.ofRepoId(location.getC_Country_ID());

		final boolean explicitBreaks = inStr.indexOf("@CR@") >= 0;

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
					if ((outStr.lastIndexOf("\n") == -1 || outStr.lastIndexOf("\n") != outStr.length() - 1) && text.length() != 0)
					{
						outStr.append(text);
					}
					// add text without begining empty space if we have new line
					else if (outStr.lastIndexOf("\n") == outStr.length() - 1 && text.trim().length() != 0)
					{
						outStr.append(StringUtils.cleanBeginWhitespace(text));
					}
				}
			}

			inStr = inStr.substring(i + 1, inStr.length()); // from first @

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
			if (token.equals("C"))
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
			else if (token.equals("R"))
			{
				if (location.getC_Region() != null)
				{
					outStr.append(location.getC_Region().getName());
				}
				else if (location.getRegionName() != null
						&& location.getRegionName().length() > 0)
				{
					outStr.append(location.getRegionName()); // local region name
				}
			}
			else if (token.equals("P"))
			{
				if (location.getPostal() != null)
				{
					outStr.append(location.getPostal());
				}
			}
			else if (token.equals("A"))
			{
				final String add = location.getPostal_Add();
				if (add != null && add.length() > 0)
				{
					outStr.append("-").append(add);
				}
			}
			else if (token.equals("CO"))
			{
				String language = adLanguage == null ? Language.getBaseAD_Language() : adLanguage;
				final String countryName = countriesRepo.getCountryNameById(countryId).translate(language);
				if (!Check.isEmpty(countryName, true))
				{
					outStr.append(countryName);
				}
			}
			else if (token.equals("A1"))
			{
				final String address1 = location.getAddress1();
				if (address1 != null && address1.length() > 0)
				{
					outStr.append(address1);
					if (!explicitBreaks)
					{
						outStr.append('\n');
					}
				}
			}
			else if (token.equals("A2"))
			{
				final String address2 = location.getAddress2();
				if (address2 != null && address2.length() > 0)
				{
					outStr.append(address2);
					if (!explicitBreaks)
					{
						outStr.append('\n');
					}
				}
			}
			else if (token.equals("A3"))
			{
				final String address3 = location.getAddress3();
				if (address3 != null && address3.length() > 0)
				{
					outStr.append(address3);
					if (!explicitBreaks)
					{
						outStr.append('\n');
					}
				}
			}
			else if (token.equals("A4"))
			{
				final String address4 = location.getAddress4();
				if (address4 != null && address4.length() > 0)
				{
					outStr.append(address4);
					if (!explicitBreaks)
					{
						outStr.append('\n');
					}
				}
			}
			else if (token.equals("BP"))
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
			else if (token.equals("CON"))
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
			else if ("CR".equals(token))
			{
				outStr.append('\n');
			}
			else if ("PB".equals(token)) // postal box
			{
				// if we have box number, added it as it is
				if (location != null && !Check.isEmpty(location.getPOBox(), true))
				{
					outStr.append(location.getPOBox());
					// add an automatic new line
					if (!explicitBreaks)
					{
						outStr.append('\n');
					}
				}
			}
			else if (Uservars.FirstName.getName().equals(token) || Uservars.LastName.getName().equals(token) || Uservars.Title.getName().equals(token) || Uservars.Greeting.getName().equals(token))
			{
				; // nothing to do
			}
			else
			{
				log.warn("Token {} is not recognized in display sequence of country {}", token, countryId);
			}

			inStr = inStr.substring(j + 1, inStr.length()); // from second @
			i = inStr.indexOf('@');
		}

		if (withBrackets)
		{
			// if variables are empty, don't put prefix
			if (StringUtils.cleanWhitespace(outStr.toString()).length() == 0)
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
			@Nullable final org.compiere.model.I_C_BPartner bPartner,
			@Nullable final org.compiere.model.I_C_BPartner_Location location,
			final I_AD_User user,
			final String trxName)
	{
		if (bPartner == null || location == null)
		{
			log.debug("One of bPartner=" + bPartner + ", location=" + location + " is null. Returning");
			return "";
		}

		final Integer bPartnerLocationId = location.getC_BPartner_Location_ID();

		if (bPartnerLocationId == null || bPartnerLocationId <= 0)
		{
			return "";
		}

		final String bPartnerBlock = buildBPartnerBlock(bPartner, user, location);

		final Properties ctx = Env.getCtx();

		final I_C_Country countryLocal = countriesRepo.getDefault(ctx);
		final boolean isLocal = location.getC_Location() == null ? false : location.getC_Location().getC_Country_ID() == countryLocal.getC_Country_ID();

		// User Anschriftenblock
		final String userBlock = buildUserBlock(bPartner, isLocal, user, bPartnerBlock, trxName);

		// Addressblock
		final String fullAddressBlock = Services.get(ILocationBL.class)
				.mkAddress(
						location.getC_Location(),
						create(bPartner, I_C_BPartner.class),
						bPartnerBlock,
						userBlock);

		return fullAddressBlock;
	}

	/**
	 * build BPartner block
	 *
	 * @param bPartner
	 * @param user
	 * @return
	 */
	private String buildBPartnerBlock(@NonNull final org.compiere.model.I_C_BPartner bPartner, @Nullable final I_AD_User user, @NonNull final I_C_BPartner_Location bplocation)
	{
		// Name, Name2
		String bpName = "";
		String bpName2 = "";

		if (bPartner.isCompany()
				|| user == null
				|| user.getAD_User_ID() == 0
				|| Check.isEmpty(user.getLastname(), true))
		{
			// task https://github.com/metasfresh/metasfresh/issues/5804
			// prefer BPartner name from location if is set
			if (!Check.isEmpty(bplocation.getBPartnerName(), true))
			{
				bpName = bplocation.getBPartnerName();
				bpName2 = null;
			}
			else
			{
				bpName = bPartner.getName();
				bpName2 = bPartner.getName2();
			}
		}

		final StringBuilder sbBPartner = new StringBuilder();
		//
		// Geschaeftspartner Anschriftenblock
		if (!Check.isEmpty(bpName))
		{
			sbBPartner.append(bpName);
		}
		if (!Check.isEmpty(bpName2))
		{
			sbBPartner.append('\n');
			sbBPartner.append(bpName2);
		}

		return sbBPartner.toString();

	}

	private void replaceUserToken(String inStr, final I_AD_User user, final boolean withBrackets, StringBuilder outStr, final boolean isPartnerCompany)
	{
		String userGreeting = "";
		final I_C_Greeting greetingOfUser = user.getC_Greeting();
		if (greetingOfUser != null && greetingOfUser.getC_Greeting_ID() > 0)
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
			final String preText = inStr.substring(i + 1, inStr.length()); // from first @

			final int j = preText.indexOf('@'); // next @
			if (j < 0)
			{
				token = ""; // no second tag
			}
			else
			{
				token = preText.substring(0, j);
			}

			if (!Uservars.FirstName.toString().equals(token) || !Uservars.LastName.toString().equals(token) || !Uservars.Title.toString().equals(token) || !Uservars.Greeting.toString().equals(token))
			{
				inStr = inStr.substring(i + 1 + j + 1, inStr.length());
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
					if ((outStr.lastIndexOf("\n") == -1 || outStr.lastIndexOf("\n") != outStr.length() - 1) && text.length() != 0)
					{
						outStr.append(text);
					}
					// add text without beginning empty space if we have new line
					else if (outStr.lastIndexOf("\n") == outStr.length() - 1 && text.trim().length() != 0)
					{
						outStr.append(StringUtils.cleanBeginWhitespace(text));
					}
				}
			}

			inStr = inStr.substring(i + 1, inStr.length()); // from first @

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
			else if ("CR".equals(token))
			{
				outStr.append('\n');
			}
			inStr = inStr.substring(j + 1, inStr.length()); // from second @
			i = inStr.indexOf('@');
		}

		if (withBrackets)
		{
			// if variables are empty, don't put prefix
			if (StringUtils.cleanWhitespace(outStr.toString()).length() == 0)
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
	 *
	 * @param ctx
	 * @param isLocal       true if local country
	 * @param user
	 * @param bPartnerBlock
	 * @param trxName
	 * @return
	 */
	private String buildUserBlock(@NonNull final org.compiere.model.I_C_BPartner bPartner, final boolean isLocal, final I_AD_User user, final String bPartnerBlock, final String trxName)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(bPartner);
		final boolean isPartnerCompany = bPartner.isCompany();
		final Language language = Language.asLanguage(bPartner.getAD_Language());

		String userGreeting = "";
		if (user != null)
		{
			// Greeting
			final GreetingId greetingId = GreetingId.ofRepoIdOrNull(user.getC_Greeting_ID());
			if (greetingId != null)
			{
				final Greeting greeting = greetingRepository.getByIdAndLang(greetingId, language);
				userGreeting = greeting.getGreeting();
			}

			final String userName = user.getLastname();
			final String userVorname = user.getFirstname();
			final String userTitle = user.getTitle();

			//
			// construct string

			final CountryCustomInfo userInfo = countriesRepo.retriveCountryCustomInfo(ctx, trxName);
			String ds = userInfo == null ? "" : userInfo.getCaptureSequence();
			if (ds == null || ds.length() == 0)
			{
				final I_C_Country country = countriesRepo.getDefault(ctx);
				ds = getDisplaySequence(country, isLocal);
			}

			final List<String> bracketsTxt = extractBracketsString(ds);

			// treat brackets cases first if exists
			for (final String s : bracketsTxt)
			{
				Check.assume(s.startsWith("(") || s.startsWith("\\("), "Expected brackets or escaped brackets!");
				Check.assume(s.endsWith(")") || s.endsWith("\\)"), "Expected brackets or escaped brackets!");

				String in = new String(s);
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
	 *
	 * @param newToken
	 * @param sb
	 */
	private void addToken(final String newToken, final StringBuilder sb)
	{
		if (!Check.isEmpty(newToken))
		{
			final String s = sb.toString();

			if (!s.endsWith("\n") && !s.endsWith(" ") && s.length() > 0)
			{
				sb.append(" ");
			}
			sb.append(newToken);
		}
	}

	private List<String> extractBracketsString(String block)
	{
		final String regex = "\\\\?(\\()(.+?)(\\))";
		final Pattern p = Pattern.compile(regex);
		final Matcher m = p.matcher(block);
		final List<String> bracketsTxt = new ArrayList<>();

		while (m.find())
		{
			bracketsTxt.add(m.group());
		}

		return bracketsTxt;
	}

	private String getDisplaySequence(final I_C_Country country, final boolean isLocalAddress)
	{
		final CountryId countryId = CountryId.ofRepoId(country.getC_Country_ID());
		final CountrySequences countrySequence = countriesRepo
				.getCountrySequences(countryId, getOrgId(), getAD_Language())
				.orElse(null);
		if (countrySequence == null)
		{
			final String displaySequence = isLocalAddress ? country.getDisplaySequenceLocal() : country.getDisplaySequence();
			return displaySequence != null ? displaySequence : "";
		}
		else
		{
			final String displaySequence = isLocalAddress ? countrySequence.getLocalAddressDisplaySequence() : countrySequence.getAddressDisplaySequence();
			return displaySequence != null ? displaySequence : "";
		}
	}

}
