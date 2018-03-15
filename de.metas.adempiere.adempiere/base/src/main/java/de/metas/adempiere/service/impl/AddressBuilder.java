package de.metas.adempiere.service.impl;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Country_Sequence;
import org.compiere.model.I_C_Greeting;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.slf4j.Logger;

import de.metas.adempiere.model.I_AD_User;
import de.metas.adempiere.model.I_C_Location;
import de.metas.adempiere.service.ICountryCustomInfo;
import de.metas.adempiere.service.ICountryDAO;
import de.metas.adempiere.service.ILocationBL;
import de.metas.interfaces.I_C_BPartner;
import de.metas.logging.LogManager;

public class AddressBuilder
{
	private static final transient Logger log = LogManager.getLogger(AddressBuilder.class);

	/**
	 * org is mandatory; we need it when we retrieve country sequences; needs to be a perfect match
	 */
	private final I_AD_Org org;
	private String adLanguage;

	private int getAD_Org_ID()
	{
		return org == null ? Env.CTXVALUE_AD_Org_ID_Any : org.getAD_Org_ID();
	}

	private String getAD_Language()
	{
		return adLanguage;
	}

	public AddressBuilder setLanguage(String language)
	{
		this.adLanguage = language;
		return this;
	}

	public enum Uservars
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

	public AddressBuilder(final I_AD_Org org)
	{
		super();
		this.org = org;
	}

	/**
	 * Build address string
	 *
	 * @param location
	 * @param isLocalAddress
	 *            true if this is a local address (i.e. location's country is same as our tenant)
	 * @param bPartnerBlock
	 * @param userBlock
	 * @return
	 */
	public String buildAddressString(final I_C_Location location, boolean isLocalAddress, String bPartnerBlock, String userBlock)
	{
		final String displaySequence = getDisplaySequence(location.getC_Country(), isLocalAddress);

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

		final String retValue = Util.replace(outStr.toString().trim(), "\\n", "\n");
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
		final I_C_Country country = location.getC_Country();

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
						outStr.append(Util.cleanBeginWhitespace(text));
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
				final I_C_Country countryTrl = InterfaceWrapperHelper.translate(country, I_C_Country.class);
				final String countryName = countryTrl.getName();

				if (countryName != null && countryName.length() > 0)
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
				if (location != null)
				{
					if (location.isPOBoxNum())
					{
						if (Check.isEmpty(location.getPOBox(), true))
						{
							// if we have new line, don't add empty field
							if (outStr.lastIndexOf("\n") != -1 && outStr.lastIndexOf("\n") == outStr.length() - 1)
							{
								outStr.append("");
							}
							// add an empty field, so that if we have a text between brackets, to be printed
							else
							{
								// add an automatic new line
								if (!explicitBreaks)
								{
									outStr.append('\n');
								}
								// outStr.append(" ");
							}

						}
						// if we have box number, added it as it is
						else
						{
							outStr.append(location.getPOBox());
							// add an automatic new line
							if (!explicitBreaks)
							{
								outStr.append('\n');
							}
						}

					}
				}
			}
			else if (Uservars.FirstName.getName().equals(token) || Uservars.LastName.getName().equals(token) || Uservars.Title.getName().equals(token) || Uservars.Greeting.getName().equals(token))
			{
				; // nothing to do
			}
			else
			{
				log.warn("Token " + token + " is not recognized in display sequence of country " + country);
			}

			inStr = inStr.substring(j + 1, inStr.length()); // from second @
			i = inStr.indexOf('@');
		}

		if (withBrackets)
		{
			// if variables are empty, don't put prefix
			if (Util.cleanWhitespace(outStr.toString()).length() == 0)
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

		final String bPartnerBlock = buildBPartnerBlock(bPartner, user);

		final Properties ctx = Env.getCtx();

		final I_C_Country countryLocal = Services.get(ICountryDAO.class).getDefault(ctx);
		final boolean isLocal = location.getC_Location() == null ? false : location.getC_Location().getC_Country_ID() == countryLocal.getC_Country_ID();

		// User Anschriftenblock
		final String userBlock = buildUserBlock(InterfaceWrapperHelper.getCtx(bPartner), isLocal, user, bPartnerBlock, bPartner.isCompany(), trxName);

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
	private String buildBPartnerBlock(final org.compiere.model.I_C_BPartner bPartner, final I_AD_User user)
	{
		// Name, Name2
		String bpName = "";
		String bpName2 = "";

		if (bPartner.isCompany()
				|| user == null
				|| user.getAD_User_ID() == 0
				|| Check.isEmpty(user.getLastname(), true))
		{
			bpName = bPartner.getName();
			bpName2 = bPartner.getName2();
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
						outStr.append(Util.cleanBeginWhitespace(text));
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
			if (Util.cleanWhitespace(outStr.toString()).length() == 0)
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
	 * @param isLocal true if local country
	 * @param user
	 * @param bPartnerBlock
	 * @param trxName
	 * @return
	 */
	private String buildUserBlock(final Properties ctx, final boolean isLocal, final I_AD_User user, final String bPartnerBlock, final boolean isPartnerCompany, final String trxName)
	{
		String userGreeting = "";
		if (user != null)
		{
			final I_C_Greeting greetingOfUser = user.getC_Greeting();
			if (greetingOfUser != null && greetingOfUser.getC_Greeting_ID() > 0)
			{
				userGreeting = greetingOfUser.getName();
			}
			final String userName = user.getLastname();
			final String userVorname = user.getFirstname();
			final String userTitle = user.getTitle();

			// Greeting

			//
			// construct string

			final ICountryCustomInfo userInfo = Services.get(ICountryDAO.class).retriveCountryCustomInfo(ctx, trxName);
			String ds = userInfo == null ? "" : userInfo.getCaptureSequence();
			if (ds == null || ds.length() == 0)
			{
				final I_C_Country country = Services.get(ICountryDAO.class).getDefault(ctx);

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
		final I_C_Country_Sequence countrySequence = Services.get(ICountryDAO.class).retrieveCountrySequence(country, getAD_Org_ID(), getAD_Language());
		if (countrySequence == null)
		{
			final String displaySequence = isLocalAddress ? country.getDisplaySequenceLocal() : country.getDisplaySequence();
			return displaySequence;
		}
		else
		{
			final String displaySequence = isLocalAddress ? countrySequence.getDisplaySequenceLocal() : countrySequence.getDisplaySequence();
			return displaySequence;
		}
	}

}
