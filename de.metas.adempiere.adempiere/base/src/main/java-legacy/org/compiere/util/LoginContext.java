package org.compiere.util;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.Properties;

import org.adempiere.ad.security.TableAccessLevel;
import org.adempiere.service.IValuePreferenceBL.IUserValuePreference;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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
 * Login context for {@link Login}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class LoginContext
{
	private final Properties _ctx;

	private String _remoteAddr = null;
	private String _remoteHost = null;
	private String _webSession = null;

	public LoginContext(final Properties ctx)
	{
		super();
		if (ctx == null)
		{
			throw new IllegalArgumentException("Context missing");
		}
		this._ctx = ctx;
	}

	private final Properties getCtx()
	{
		return _ctx;
	}

	public final Properties getSessionContext()
	{
		return getCtx();
	}

	public void setProperty(final String name, final String value)
	{
		Env.setContext(getCtx(), name, value);
	}

	public void setProperty(final String name, final boolean valueBoolean)
	{
		Env.setContext(getCtx(), name, valueBoolean);
	}

	public void setProperty(final String name, final int valueInt)
	{
		Env.setContext(getCtx(), name, valueInt);
	}

	private final int getMandatoryPropertyAsInt(final String name)
	{
		return getOptionalPropertyAsInt(name)
				.orElseThrow(() -> new UnsupportedOperationException("Missing Context: " + name));
	}
	
	private final Optional<Integer> getOptionalPropertyAsInt(final String name)
	{
		final Properties ctx = getCtx();
		if (Env.getContext(ctx, name).length() == 0)   	// could be number 0
		{
			return Optional.empty();
		}
		final int valueInt = Env.getContextAsInt(ctx, name);
		return Optional.of(valueInt);
	}


	private int getPropertyAsInt(final String name)
	{
		return Env.getContextAsInt(getCtx(), name);
	}

	public void setProperty(final String name, final Timestamp valueDate)
	{
		Env.setContext(getCtx(), name, valueDate);
	}

	private Timestamp getPropertyAsDate(final String name)
	{
		return Env.getContextAsDate(getCtx(), name);
	}

	private boolean getPropertyAsBoolean(final String name)
	{
		return DisplayType.toBoolean(Env.getContext(getCtx(), name));
	}

	public void setAutoCommit(final boolean autoCommit)
	{
		Env.setAutoCommit(getCtx(), autoCommit);
	}

	public void setAutoNew(final boolean autoNew)
	{
		Env.setAutoNew(getCtx(), autoNew);
	}

	public void setPreference(final IUserValuePreference userValuePreference)
	{
		Env.setPreference(getCtx(), userValuePreference);
	}

	public void resetAD_Session_ID()
	{
		setProperty(Env.CTXNAME_AD_Session_ID, "");
	}

	public void setAD_Language(final String AD_Language)
	{
		setProperty(Env.CTXNAME_AD_Language, AD_Language);
	}

	public void setUser(final int AD_User_ID, final String username)
	{
		setProperty(Env.CTXNAME_AD_User_ID, AD_User_ID);
		setProperty(Env.CTXNAME_AD_User_Name, username);
		setProperty(Env.CTXNAME_SalesRep_ID, AD_User_ID);
	}

	public int getAD_User_ID()
	{
		return getMandatoryPropertyAsInt(Env.CTXNAME_AD_User_ID);
	}
	
	public Optional<Integer> getAD_User_ID_IfExists()
	{
		return getOptionalPropertyAsInt(Env.CTXNAME_AD_User_ID);
	}

	public void setSysAdmin(final boolean sysAdmin)
	{
		setProperty("#SysAdmin", sysAdmin);
	}

	public void setRole(final int AD_Role_ID, final String roleName)
	{
		setProperty(Env.CTXNAME_AD_Role_ID, AD_Role_ID);
		setProperty(Env.CTXNAME_AD_Role_Name, roleName);

		Ini.setProperty(Ini.P_ROLE, roleName);
	}

	public void setRoleUserLevel(final TableAccessLevel roleUserLevel)
	{
		setProperty(Env.CTXNAME_AD_Role_UserLevel, roleUserLevel.getUserLevelString()); // Format 'SCO'
	}

	public void setAllowLoginDateOverride(final boolean allowLoginDateOverride)
	{
		setProperty(Env.CTXNAME_IsAllowLoginDateOverride, allowLoginDateOverride);
	}

	public boolean isAllowLoginDateOverride()
	{
		return getPropertyAsBoolean(Env.CTXNAME_IsAllowLoginDateOverride);
	}

	public int getAD_Role_ID()
	{
		return getMandatoryPropertyAsInt(Env.CTXNAME_AD_Role_ID);
	}

	public void setClient(final int AD_Client_ID, final String clientName)
	{
		setProperty(Env.CTXNAME_AD_Client_ID, AD_Client_ID);
		setProperty(Env.CTXNAME_AD_Client_Name, clientName);

		Ini.setProperty(Ini.P_CLIENT, clientName);
	}

	public int getAD_Client_ID()
	{
		return getMandatoryPropertyAsInt(Env.CTXNAME_AD_Client_ID);
	}

	public Timestamp getLoginDate()
	{
		return getPropertyAsDate(Env.CTXNAME_Date);
	}

	public void setLoginDate(final Timestamp date)
	{
		setProperty(Env.CTXNAME_Date, date);
	}

	public void setOrg(final int AD_Org_ID, final String orgName)
	{
		setProperty(Env.CTXNAME_AD_Org_ID, AD_Org_ID);
		setProperty(Env.CTXNAME_AD_Org_Name, orgName);

		Ini.setProperty(Ini.P_ORG, orgName);
	}

	public void setUserOrgs(final String userOrgs)
	{
		setProperty(Env.CTXNAME_User_Org, userOrgs);
	}

	public int getAD_Org_ID()
	{
		return getMandatoryPropertyAsInt(Env.CTXNAME_AD_Org_ID);
	}

	public void setWarehouse(final int M_Warehouse_ID, final String warehouseName)
	{
		setProperty(Env.CTXNAME_M_Warehouse_ID, M_Warehouse_ID);

		Ini.setProperty(Ini.P_WAREHOUSE, warehouseName);
	}

	public int getM_Warehouse_ID()
	{
		return getPropertyAsInt(Env.CTXNAME_M_Warehouse_ID);
	}

	public void setPrinterName(final String printerName)
	{
		setProperty(Env.CTXNAME_Printer, printerName == null ? "" : printerName);

		Ini.setProperty(Ini.P_PRINTER, printerName);
	}

	public void setAcctSchema(final int C_AcctSchema_ID, final int C_Currency_ID, final boolean hasAlias)
	{
		setProperty("$C_AcctSchema_ID", C_AcctSchema_ID);
		setProperty("$C_Currency_ID", C_Currency_ID);
		setProperty("$HasAlias", hasAlias);
	}

	public int getC_AcctSchema_ID()
	{
		return getPropertyAsInt("$C_AcctSchema_ID");
	}

	public void setRemoteAddr(String remoteAddr)
	{
		_remoteAddr = remoteAddr;
	}

	public String getRemoteAddr()
	{
		return _remoteAddr;
	}

	public void setRemoteHost(String remoteHost)
	{
		_remoteHost = remoteHost;
	}

	public String getRemoteHost()
	{
		return _remoteHost;
	}

	public void setWebSession(String webSession)
	{
		_webSession = webSession;
	}

	public String getWebSession()
	{
		return _webSession;
	}
}
