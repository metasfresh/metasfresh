/******************************************************************************
 * Copyright (C) 2009 Low Heng Sin                                            *
 * Copyright (C) 2009 Idalica Corporation                                     *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package de.metas.ui.web.base.session;

import java.io.Serializable;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Preference;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Language;

import com.google.common.base.MoreObjects;

/**
 *
 * @author hengsin
 * @author Teo Sarca
 */
public final class UserPreference implements Serializable
{
	private static final long serialVersionUID = -3434473097915381994L;

	/** Language */
	public static final String P_LANGUAGE = "Language";
	private static final String DEFAULT_LANGUAGE = Language.getName(System.getProperty("user.language") + "_" + System.getProperty("user.country"));

	/** Role */
	public static final String P_ROLE = "Role";
	private static final String DEFAULT_ROLE = "";
	/** Client Name */
	public static final String P_CLIENT = "Client";
	private static final String DEFAULT_CLIENT = "";
	/** Org Name */
	public static final String P_ORG = "Organization";
	private static final String DEFAULT_ORG = "";
	/** Warehouse Name */
	public static final String P_WAREHOUSE = "Warehouse";
	private static final String DEFAULT_WAREHOUSE = "";

	/** Auto Commit */
	public static final String P_AUTO_COMMIT = "AutoCommit";
	private static final String DEFAULT_AUTO_COMMIT = "Y";

	/** Language Name Context **/
	public static final String LANGUAGE_NAME = "#LanguageName";

	/** window tab placement **/
	public static final String P_WINDOW_TAB_PLACEMENT = "WindowTabPlacement";
	public static final String DEFAULT_WINDOW_TAB_PLACEMENT = "Left";

	/** window tab collapsible **/
	public static final String P_WINDOW_TAB_COLLAPSIBLE = "WindowTabCollapsible";
	public static final String DEFAULT_WINDOW_TAB_COLLAPSIBLE = "N";

	/** Auto New **/
	public static final String P_AUTO_NEW = "AutoNew";
	public static final String DEFAULT_AUTO_NEW = "Y";

	/** Menu Collapsed **/
	public static final String P_MENU_COLLAPSED = "MenuCollapsed";
	public static final String DEFAULT_MENU_COLLAPSED = "N";

	/** Ini Properties */
	private static final String[] PROPERTIES = new String[] {
			P_LANGUAGE,
			P_ROLE,
			P_CLIENT,
			P_ORG,
			P_WAREHOUSE,
			P_AUTO_COMMIT,
			P_AUTO_NEW,
			P_WINDOW_TAB_PLACEMENT,
			P_WINDOW_TAB_COLLAPSIBLE,
			P_MENU_COLLAPSED };
	/** Ini Property Values */
	private static final String[] DEFAULT_VALUES = new String[] {
			DEFAULT_LANGUAGE,
			DEFAULT_ROLE,
			DEFAULT_CLIENT,
			DEFAULT_ORG,
			DEFAULT_WAREHOUSE,
			DEFAULT_AUTO_COMMIT,
			DEFAULT_AUTO_NEW,
			DEFAULT_WINDOW_TAB_PLACEMENT,
			DEFAULT_WINDOW_TAB_COLLAPSIBLE,
			DEFAULT_MENU_COLLAPSED };

	/** Container for Properties */
	private Properties props;
	private int m_AD_User_ID = -1;

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("AD_User_ID", m_AD_User_ID)
				.add("properties", props)
				.toString();
	}

	/**
	 * save user preference
	 */
	public void savePreference()
	{
		if (m_AD_User_ID < 0)
		{
			return;
		}

		final Properties ctx = createDAOCtx();
		final Map<String, I_AD_Preference> preferencesMap = retrievePreferencesMap(ctx, m_AD_User_ID);

		for (int i = 0; i < PROPERTIES.length; i++)
		{
			final String attribute = PROPERTIES[i];
			I_AD_Preference preference = preferencesMap.get(attribute);
			if (preference == null)
			{
				preference = InterfaceWrapperHelper.create(ctx, I_AD_Preference.class, ITrx.TRXNAME_ThreadInherited);
				preference.setAD_User_ID(m_AD_User_ID);
				preference.setAttribute(attribute);
			}

			final String value = getProperty(attribute);
			preference.setValue(value);
			InterfaceWrapperHelper.save(preference);
		}
	}

	private static final Properties createDAOCtx()
	{
		final Properties ctx = Env.deriveCtx(Env.getCtx());
		Env.setContext(ctx, Env.CTXNAME_AD_Client_ID, Env.CTXVALUE_AD_Client_ID_System);
		Env.setContext(ctx, Env.CTXNAME_AD_Org_ID, Env.CTXVALUE_AD_Org_ID_System);
		return ctx;
	}

	private static final Map<String, I_AD_Preference> retrievePreferencesMap(final Properties ctx, final int adUserId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Preference.class, ctx, ITrx.TRXNAME_ThreadInherited)
				.addEqualsFilter(I_AD_Preference.COLUMNNAME_AD_Client_ID, Env.getAD_Client_ID(ctx))
				.addEqualsFilter(I_AD_Preference.COLUMNNAME_AD_Org_ID, Env.getAD_Org_ID(ctx))
				.addEqualsFilter(I_AD_Preference.COLUMNNAME_AD_User_ID, adUserId)
				.addEqualsFilter(I_AD_Preference.COLUMNNAME_AD_Window_ID, null)
				.addInArrayOrAllFilter(I_AD_Preference.COLUMNNAME_Attribute, PROPERTIES)
				//
				.orderBy()
				.addColumn(I_AD_Preference.COLUMNNAME_Attribute)
				.addColumn(I_AD_Preference.COLUMNNAME_AD_Client_ID)
				.addColumn(I_AD_Preference.COLUMNNAME_AD_Org_ID)
				.endOrderBy()
				//
				.create()
				.list(I_AD_Preference.class)
				.stream()
				.collect(GuavaCollectors.toImmutableMapByKey(I_AD_Preference::getAttribute));
	}

	/**
	 * load user preference
	 *
	 * @param adUserId
	 */
	private void loadPreference(final int adUserId)
	{
		if (adUserId < 0)
		{
			return;
		}

		final Properties ctx = createDAOCtx();
		final Map<String, I_AD_Preference> preferencesMap = retrievePreferencesMap(ctx, adUserId);

		final Properties props = new Properties();
		for (int i = 0; i < PROPERTIES.length; i++)
		{
			final String attribute = PROPERTIES[i];
			String value = DEFAULT_VALUES[i];

			final I_AD_Preference preference = preferencesMap.get(attribute);
			if (preference != null)
			{
				value = preference.getValue();
			}

			props.setProperty(attribute, value);
		}

		m_AD_User_ID = adUserId;
		this.props = props;
	}

	public void loadPreference(final Properties ctx)
	{
		final int adUserId = Env.getAD_User_ID(ctx);
		loadPreference(adUserId);
	}

	/**
	 * Set Property
	 *
	 * @param key Key
	 * @param value Value
	 */
	public void setProperty(final String key, final String value)
	{
		if (props == null)
		{
			props = new Properties();
		}
		if (value == null)
		{
			props.setProperty(key, "");
		}
		else
		{
			props.setProperty(key, value);
		}
	}

	/**
	 * Set Property
	 *
	 * @param key Key
	 * @param value Value
	 */
	public void setProperty(final String key, final Boolean value)
	{
		setProperty(key, DisplayType.toBooleanString(value));
	}

	/**
	 * Set Property
	 *
	 * @param key Key
	 * @param value Value
	 */
	public void setProperty(final String key, final int value)
	{
		setProperty(key, String.valueOf(value));
	}

	/**
	 * Get Property
	 *
	 * @param key Key
	 * @return Value
	 */
	public String getProperty(final String key)
	{
		if (key == null)
		{
			return "";
		}

		if(props == null)
		{
			return "";
		}
		
		final String value = props.getProperty(key, "");
		if (Check.isEmpty(value))
		{
			return "";
		}
		
		return value;
	}

	/**
	 * Get Property as Boolean
	 *
	 * @param key Key
	 * @return Value
	 */
	public boolean isPropertyBool(final String key)
	{
		final String value = getProperty(key);
		return DisplayType.toBoolean(value, false);
	}

	public void updateContext(final Properties ctx)
	{
		Env.setContext(ctx, "#ShowTrl", true);
		Env.setContext(ctx, "#ShowAdvanced", true);
		Env.setAutoCommit(ctx, isPropertyBool(P_AUTO_COMMIT));
		Env.setAutoNew(ctx, isPropertyBool(P_AUTO_NEW));
	}
}
