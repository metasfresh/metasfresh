/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.boilerplate;

import de.metas.util.Check;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.translate;

/**
 * @author metas-dev <dev@metasfresh.com>
 */
@Service
public class BoilerPlateService
{
	public static final String FunctionSeparator = "/";
	public static final String NamePattern = "[a-zA-Z0-9-_+]+";
	public static final String NamePatternWithFunctions = "[a-zA-Z0-9-_+" + FunctionSeparator + "]+";
	public static final String TagBegin = "@";
	public static final String TagEnd = "@";
	public static final Pattern NameTagPattern = Pattern.compile(TagBegin + "([ ]*" + NamePatternWithFunctions + "[ ]*)" + TagEnd, Pattern.CASE_INSENSITIVE);

	public static final String FUNCTION_stripWhitespaces = "stripWhitespaces";
	public static final String FUNCTION_trim = "trim";
	public static final String FUNCTION_urlEncode = "urlEncode";
	public static final String FUNCTION_upperCase = "upperCase";
	public static final String FUNCTION_lowerCase = "lowerCase";

	public static String parseText(final Properties ctx, final String text,
								   final boolean isEmbeded,
								   final BoilerPlateContext context,
								   final String trxName)
	{
		if (text == null)
		{
			return null;
		}
		//
		String textFixed = text;
		if (isEmbeded)
		{
			textFixed = textFixed.replace("<html>", "")
					.replace("</html>", "")
					.replace("<head>", "")
					.replace("</head>", "")
					.replace("<body>", "")
					.replace("</body>", "")
					.trim();
		}
		final Matcher m = NameTagPattern.matcher(textFixed);
		final StringBuffer sb = new StringBuffer();
		while (m.find())
		{
			final String[] functions = getTagNameAndFunctions(m);
			final String refName = functions[0];
			functions[0] = null;

			final MADBoilerPlateVar var = MADBoilerPlateVar.get(ctx, refName);

			String replacement;
			if (context != null && context.containsKey(refName))
			{
				final Object attrValue = context.get(refName);
				replacement = attrValue == null ? "" : attrValue.toString();
			}
			else if (var != null)
			{
				try
				{
					replacement = var.evaluate(context);
				}
				catch (final Exception e)
				{
					log.warn(e.getLocalizedMessage(), e);
					replacement = m.group();
				}
			}
			else
			{
				final MADBoilerPlate ref = getByName(ctx, refName, trxName);
				if (ref == null)
				{
					throw new AdempiereException("@NotFound@ @AD_BoilerPlate_ID@ (@Name@:" + refName + ")");
				}
				replacement = ref.getTextSnippetParsed(true, context);
			}
			if (replacement == null)
			{
				replacement = "";
			}
			replacement = applyTagFunctions(replacement, functions);
			m.appendReplacement(sb, replacement);
		}
		m.appendTail(sb);
		return sb.toString();

	}

	private static String applyTagFunctions(String text, final String[] functions)
	{
		if (text == null)
		{
			text = "";
		}
		for (final String function : functions)
		{
			if (Check.isEmpty(function, true))
			{
				continue;
			}
			text = applyTagFunction(text, function.trim());
		}
		return text;
	}

	private static String applyTagFunction(final String text, final String function)
	{
		if (FUNCTION_stripWhitespaces.equalsIgnoreCase(function))
		{
			return text.replaceAll("\\s", "");
		}
		else if (FUNCTION_trim.equalsIgnoreCase(function))
		{
			return text.trim();
		}
		else if (FUNCTION_urlEncode.equalsIgnoreCase(function))
		{
			try
			{
				return URLEncoder.encode(text, "UTF-8");
			}
			catch (final UnsupportedEncodingException e)
			{
				throw new AdempiereException(e);
			}
		}
		else if (FUNCTION_upperCase.equalsIgnoreCase(function))
		{
			return text.toUpperCase();
		}
		else if (FUNCTION_lowerCase.equalsIgnoreCase(function))
		{
			return text.toLowerCase();
		}
		else
		{
			throw new AdempiereException("@NotSupported@ @Function@ " + function);
		}
	}

	protected static String getTagString(final Matcher m)
	{
		String refName;
		if (m.groupCount() >= 1)
		{
			refName = m.group(1);
		}
		else
		{
			refName = m.group();
		}
		refName = refName.trim();
		return refName;
	}

	protected static String getTagName(final Matcher m)
	{
		final String[] parts = getTagString(m).split(FunctionSeparator);
		return parts != null && parts.length > 0 ? parts[0] : "";
	}

	protected static String[] getTagNameAndFunctions(final Matcher m)
	{
		final String[] parts = getTagString(m).split(FunctionSeparator);
		return parts;
	}
}
