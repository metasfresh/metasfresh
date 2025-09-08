package org.compiere.util;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.organization.OrgId;
import de.metas.security.RoleId;
import de.metas.security.TableAccessLevel;
import de.metas.user.UserId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.adempiere.service.ClientId;
import org.adempiere.service.IValuePreferenceBL.IUserValuePreference;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Properties;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Login context for {@link Login}.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@SuppressWarnings("SameParameterValue")
@Getter
@Setter
public class LoginContext
{
	@Getter(AccessLevel.PRIVATE)
	private final Properties ctx;

	private String remoteAddr = null;
	private String remoteHost = null;

	/**
	 * true if logging from webui
	 */
	private boolean webui = false;
	private String webSessionId = null;

	private static final String CTXNAME_IsPasswordAuth = "#IsPasswordAuth";
	private static final String CTXNAME_Is2FAAuth = "#Is2FAAuth";

	public LoginContext(@NonNull final Properties ctx)
	{
		this.ctx = ctx;
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

	private int getMandatoryPropertyAsInt(final String name)
	{
		return getOptionalPropertyAsInt(name)
				.orElseThrow(() -> new UnsupportedOperationException("Missing Context: " + name));
	}

	private Optional<Integer> getOptionalPropertyAsInt(final String name)
	{
		final Properties ctx = getCtx();
		if (Env.getContext(ctx, name).isEmpty())    // could be number 0
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

	private void setProperty(final String name, final LocalDate valueDate)
	{
		Env.setContext(getCtx(), name, TimeUtil.asDate(valueDate));
	}

	private LocalDate getPropertyAsLocalDate(final String name)
	{
		return TimeUtil.asLocalDate(Env.getContextAsDate(getCtx(), name));
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

	public void setIsPasswordAuthenticated() {setProperty(CTXNAME_IsPasswordAuth, true);}

	public boolean isPasswordAuthenticated() {return getPropertyAsBoolean(CTXNAME_IsPasswordAuth);}

	public void setIs2FAAuthenticated(boolean authenticated) {setProperty(CTXNAME_Is2FAAuth, authenticated);}

	public boolean is2FAAuthenticated() {return getPropertyAsBoolean(CTXNAME_Is2FAAuth);}

	public void setUser(final UserId userId, final String username)
	{
		setProperty(Env.CTXNAME_AD_User_ID, UserId.toRepoId(userId));
		setProperty(Env.CTXNAME_AD_User_Name, username);
		setProperty(Env.CTXNAME_SalesRep_ID, UserId.toRepoId(userId));
	}

	public UserId getUserId()
	{
		return UserId.ofRepoId(getMandatoryPropertyAsInt(Env.CTXNAME_AD_User_ID));
	}

	public Optional<UserId> getUserIdIfExists()
	{
		return getOptionalPropertyAsInt(Env.CTXNAME_AD_User_ID)
				.map(UserId::ofRepoId);
	}

	public void setSysAdmin(final boolean sysAdmin)
	{
		setProperty("#SysAdmin", sysAdmin);
	}

	public void setRole(
			final RoleId roleId, 
			final String roleName)
	{
		setProperty(Env.CTXNAME_AD_Role_ID, RoleId.toRepoId(roleId));
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

	public RoleId getRoleId()
	{
		return RoleId.ofRepoId(getMandatoryPropertyAsInt(Env.CTXNAME_AD_Role_ID));
	}

	public void setClient(final ClientId clientId, final String clientName)
	{
		setProperty(Env.CTXNAME_AD_Client_ID, ClientId.toRepoId(clientId));
		setProperty(Env.CTXNAME_AD_Client_Name, clientName);

		Ini.setProperty(Ini.P_CLIENT, clientName);
	}

	public ClientId getClientId()
	{
		return ClientId.ofRepoId(getMandatoryPropertyAsInt(Env.CTXNAME_AD_Client_ID));
	}

	public LocalDate getLoginDate()
	{
		return getPropertyAsLocalDate(Env.CTXNAME_Date);
	}

	public void setLoginDate(final LocalDate date)
	{
		setProperty(Env.CTXNAME_Date, date);
	}

	public void setOrg(final OrgId orgId, final String orgName)
	{
		setProperty(Env.CTXNAME_AD_Org_ID, OrgId.toRepoId(orgId));
		setProperty(Env.CTXNAME_AD_Org_Name, orgName);

		Ini.setProperty(Ini.P_ORG, orgName);
	}

	public void setUserOrgs(final String userOrgs)
	{
		setProperty(Env.CTXNAME_User_Org, userOrgs);
	}

	public OrgId getOrgId()
	{
		return OrgId.ofRepoId(getMandatoryPropertyAsInt(Env.CTXNAME_AD_Org_ID));
	}

	public void setWarehouse(final WarehouseId warehouseId, final String warehouseName)
	{
		setProperty(Env.CTXNAME_M_Warehouse_ID, WarehouseId.toRepoId(warehouseId));
		Ini.setProperty(Ini.P_WAREHOUSE, warehouseName);
	}

	@Nullable
	public WarehouseId getWarehouseId()
	{
		return WarehouseId.ofRepoIdOrNull(getPropertyAsInt(Env.CTXNAME_M_Warehouse_ID));
	}

	public void setPrinterName(final String printerName)
	{
		setProperty(Env.CTXNAME_Printer, printerName == null ? "" : printerName);

		Ini.setProperty(Ini.P_PRINTER, printerName);
	}

	public void setAcctSchema(final AcctSchema acctSchema)
	{
		setProperty("$C_AcctSchema_ID", acctSchema.getId().getRepoId());
		setProperty("$C_Currency_ID", acctSchema.getCurrencyId().getRepoId());
		setProperty("$HasAlias", acctSchema.getValidCombinationOptions().isUseAccountAlias());
	}

	@Nullable
	public AcctSchemaId getAcctSchemaId()
	{
		return AcctSchemaId.ofRepoIdOrNull(getPropertyAsInt("$C_AcctSchema_ID"));
	}
}
