package org.adempiere.ad.persistence.modelgen;

import org.adempiere.util.Check;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Generates Java code for AD_Ref_List items.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class ADRefListGenerator
{
	public static final ADRefListGenerator newInstance()
	{
		return new ADRefListGenerator();
	}

	private String _columnName;
	private ListInfo _listInfo;

	private ADRefListGenerator()
	{
		super();
	}

	/**
	 * Generates java constants.
	 *
	 * @return java constants. Example:
	 *
	 *         <pre>
	 * public static final int NEXTACTION_AD_Reference_ID = 219;
	 * public static final String NEXTACTION_None = &quot;N&quot;;
	 * public static final String NEXTACTION_FollowUp = &quot;F&quot;;
	 * </pre>
	 */
	public String generateConstants()
	{
		final String columnName = getColumnName();
		final String columnNameUC = columnName.toUpperCase();
		final ListInfo listInfo = getListInfo();
		final int AD_Reference_ID = listInfo.getAD_Reference_ID();

		final String referenceName = listInfo.getName();

		final StringBuilder javaCode = new StringBuilder();
		javaCode.append("\n\t/** ")
				.append("\n\t * ").append(columnName).append(" AD_Reference_ID=").append(AD_Reference_ID)
				.append("\n\t * Reference name: ").append(referenceName)
				.append("\n\t */")
				.append("\n\tpublic static final int ").append(columnNameUC).append("_AD_Reference_ID=").append(AD_Reference_ID).append(";");

		//
		for (final ListItemInfo item : listInfo.getItems())
		{
			final String value = item.getValue();

			String name = item.getName();
			// metas: 02827: begin
			final String valueName = item.getValueName();
			if (!Check.isEmpty(valueName, true))
			{
				name = valueName;
			}
			// metas: 02827: end

			final String nameClean = createJavaName(name);
			javaCode.append("\n\t/** ").append(name).append(" = ").append(value).append(" */");
			javaCode.append("\n\tpublic static final String ").append(columnNameUC)
					.append("_").append(nameClean)
					.append(" = \"").append(value).append("\";");
		}

		return javaCode.toString();
	}

	private final String createJavaName(final String name)
	{
		final char[] nameArray = name.toCharArray();
		final StringBuilder nameClean = new StringBuilder();
		boolean initCap = true;
		for (int i = 0; i < nameArray.length; i++)
		{
			final char c = nameArray[i];
			// metas: teo_sarca: begin
			// replacing german umlauts with equivalent ascii
			if (c == '\u00c4')
			{
				nameClean.append("Ae");
				initCap = false;
			}
			else if (c == '\u00dc')
			{
				nameClean.append("Ue");
				initCap = false;
			}
			else if (c == '\u00d6')
			{
				nameClean.append("Oe");
				initCap = false;
			}
			else if (c == '\u00e4')
			{
				nameClean.append("ae");
				initCap = false;
			}
			else if (c == '\u00fc')
			{
				nameClean.append("ue");
				initCap = false;
			}
			else if (c == '\u00f6')
			{
				nameClean.append("oe");
				initCap = false;
			}
			else if (c == '\u00df')
			{
				nameClean.append("ss");
				initCap = false;
			}
			else
			// metas: teo_sarca: end
			if (Character.isJavaIdentifierPart(c))
			{
				if (initCap)
				{
					nameClean.append(Character.toUpperCase(c));
				}
				else
				{
					nameClean.append(c);
				}
				initCap = false;
			}
			else
			{
				if (c == '+')
				{
					nameClean.append("Plus");
				}
				else if (c == '-')
				{
					nameClean.append("_");
				}
				else if (c == '>')
				{
					if (name.indexOf('<') == -1)
					{
						nameClean.append("Gt");
					}
				}
				else if (c == '<')
				{
					if (name.indexOf('>') == -1)
					{
						nameClean.append("Le");
					}
				}
				else if (c == '!')
				{
					nameClean.append("Not");
				}
				else if (c == '=')
				{
					nameClean.append("Eq");
				}
				else if (c == '~')
				{
					nameClean.append("Like");
				}
				initCap = true;
			}
		}

		return nameClean.toString();
	}

	public ADRefListGenerator setColumnName(final String columnName)
	{
		_columnName = columnName;
		return this;
	}

	private final String getColumnName()
	{
		Check.assumeNotEmpty(_columnName, "_columnName not empty");
		return _columnName;
	}

	public ADRefListGenerator setListInfo(final ListInfo listInfo)
	{
		Check.assumeNotNull(listInfo, "listInfo not null");
		_listInfo = listInfo;
		return this;
	}

	private ListInfo getListInfo()
	{
		return _listInfo;
	}
}
