/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.adempiere.acct.api.IAcctSchemaDAO;
import org.adempiere.acct.api.IPostingService;
import org.adempiere.acct.api.exception.AccountingException;
import org.adempiere.ad.security.IRoleDAO;
import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.security.IUserRolePermissionsDAO;
import org.adempiere.ad.security.permissions.OrgResource;
import org.adempiere.ad.service.ISystemBL;
import org.adempiere.ad.session.ISessionBL;
import org.adempiere.ad.session.MFSession;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.IClientDAO;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.service.IValuePreferenceBL;
import org.adempiere.user.api.IUserDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_AD_Role;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.MAcctSchema;
import org.compiere.model.ModelValidationEngine;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableSet;

import de.metas.adempiere.model.I_AD_User;
import de.metas.adempiere.service.ICountryDAO;
import de.metas.adempiere.service.IPrinterRoutingBL;
import de.metas.i18n.Language;
import de.metas.logging.LogManager;
import lombok.NonNull;

/**
 * Login Manager
 *
 * @author Jorg Janke
 * @author victor.perez@e-evolution.com, e-Evolution http://www.e-evolution.com
 *         <li>Incorrect global Variable when you use multi Account Schema
 *         http://sourceforge.net/tracker/?func=detail&atid=879335&aid=2531597&group_id=176962
 * @author teo.sarca@gmail.com
 *         <li>BF [ 2867246 ] Do not show InTrazit WHs on login
 *         https://sourceforge.net/tracker/?func=detail&aid=2867246&group_id=176962&atid=879332
 * @version $Id: Login.java,v 1.6 2006/10/02 05:19:06 jjanke Exp $
 */
public class Login
{
	/**
	 * @task http://dewiki908/mediawiki/index.php/05730_Use_different_Theme_colour_on_UAT_system
	 */
	private static final String SYSCONFIG_UI_WindowHeader_Notice_Text = "UI_WindowHeader_Notice_Text";

	/**
	 * @task https://metasfresh.atlassian.net/browse/FRESH-352
	 */
	private static final String SYSCONFIG_UI_WindowHeader_Notice_BG_Color = "UI_WindowHeader_Notice_BG_Color";

	/**
	 * @task https://metasfresh.atlassian.net/browse/FRESH-352
	 */
	private static final String SYSCONFIG_UI_WindowHeader_Notice_FG_Color = "UI_WindowHeader_Notice_FG_Color";

	/**
	 * see http://dewiki908/mediawiki/index.php/06009_Login_Lager_weg_%28106922640136%29
	 */
	private static final String SYSCONFIG_ShowWarehouseOnLogin = "ShowWarehouseOnLogin";

	// services
	private static final transient Logger log = LogManager.getLogger(Login.class);

	private final transient IClientDAO clientDAO = Services.get(IClientDAO.class);
	private final transient IUserRolePermissionsDAO userRolePermissionsDAO = Services.get(IUserRolePermissionsDAO.class);
	private final transient IUserDAO userDAO = Services.get(IUserDAO.class);
	private final transient IRoleDAO roleDAO = Services.get(IRoleDAO.class);
	private final transient IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	private final transient IAcctSchemaDAO acctSchemaDAO = Services.get(IAcctSchemaDAO.class);

	//
	// State
	private final LoginContext _loginContext;
	// NOTE: please avoid having other state variables. If needed, pls add them to LoginContext.

	/**
	 * Java Version Test
	 *
	 * @param isClient client connection
	 * @return true if Java Version is OK
	 */
	public static boolean isJavaOK(final boolean isClient)
	{
		// Java System version check
		final String jVersion = System.getProperty("java.version");

		final StringBuilder msg = new StringBuilder();
		msg.append(System.getProperty("java.vm.name")).append(" - ").append(jVersion);

		// if (isClient)
		// {
		// JOptionPane.showMessageDialog(null, msg.toString(),
		// org.compiere.Adempiere.getName() + " - Java Version Check",
		// JOptionPane.INFORMATION_MESSAGE);
		// }
		// else
		// {
		log.debug("Using java version: {}", msg);
		// }

		return true;
		// 04406 end - below is the commented-out original code

		// if (jVersion.startsWith("1.5.0"))
		// return true;
		// //vpj-cd e-evolution support to java 6
		// if (jVersion.startsWith("1.6.0"))
		// return true;
		// //end
		// // Warning
		// boolean ok = false;
		// // if (jVersion.startsWith("1.4")
		// // || jVersion.startsWith("1.5.1")) // later/earlier release
		// // ok = true;
		//
		// // Error Message
		// StringBuffer msg = new StringBuffer();
		// msg.append(System.getProperty("java.vm.name")).append(" - ").append(jVersion);
		// if (ok)
		// msg.append("(untested)");
		// msg.append(" <> 1.5.0");
		// //
		// if (isClient)
		// JOptionPane.showMessageDialog(null, msg.toString(),
		// org.compiere.Adempiere.getName() + " - Java Version Check",
		// ok ? JOptionPane.WARNING_MESSAGE : JOptionPane.ERROR_MESSAGE);
		// else
		// log.error(msg.toString());
		// return ok;
	}   // isJavaOK

	/**************************************************************************
	 * Login
	 *
	 * @param ctx context
	 */
	public Login(final Properties ctx)
	{
		this(new LoginContext(ctx));
	}

	public Login(@NonNull final LoginContext loginContext)
	{
		this._loginContext = loginContext;
	}

	public LoginContext getCtx()
	{
		return _loginContext;
	}

	/**
	 * Login and get roles.
	 *
	 * @param username user or email
	 * @param password password
	 * @return available roles; never null or empty
	 * @throws AdempiereException in case of any error (including no roles found)
	 */
	public Set<KeyNamePair> authenticate(final String username, final String password)
	{
		log.debug("User={}", username);

		if (Check.isEmpty(username, true))
		{
			throw new AdempiereException("@UserOrPasswordInvalid@");
		}

		//
		// Authentification
		if (Check.isEmpty(password, false))
		{
			throw new AdempiereException("@UserOrPasswordInvalid@");
		}

		//
		// Try auth via LDAP
		final LoginContext ctx = getCtx();

		//
		// If not authenticated so far, use AD_User as backup
		//
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		final int maxLoginFailure = sysConfigBL.getIntValue("ZK_LOGIN_FAILURES_MAX", 3);
		final int accountLockExpire = sysConfigBL.getIntValue("USERACCOUNT_LOCK_EXPIRE", 30);
		final MFSession session = startSession();
		session.setLoginUsername(username);

		final I_AD_User user = userDAO.retrieveLoginUserByUserId(username);
		int loginFailureCount = user.getLoginFailureCount();

		// metas: us902: Update logged in user if not equal.
		// Because we are creating the session before we know which user will be logged, initially
		// the AD_Session.CreatedBy is zero.
		session.setAD_User_ID(user.getAD_User_ID());

		final boolean isAccountLocked = user.isAccountLocked();
		if (isAccountLocked)
		{
			final Timestamp curentLogin = (new Timestamp(System.currentTimeMillis()));
			long loginFailureTime = user.getLoginFailureDate().getTime();
			long newloginFailureTime = loginFailureTime + (1000 * 60 * accountLockExpire);
			Timestamp acountUnlock = new Timestamp(newloginFailureTime);
			if (curentLogin.compareTo(acountUnlock) > 0)
			{
				user.setLoginFailureCount(0);
				user.setIsAccountLocked(false);
				InterfaceWrapperHelper.save(user);
			}
			else
			{
				throw new AdempiereException("@UserAccountLockedError@"); // TODO: specific exception
			}
		}

		final boolean isPasswordValid = Objects.equals(password, user.getPassword());
		if (!isPasswordValid)
		{
			loginFailureCount++;
			user.setLoginFailureCount(loginFailureCount);
			user.setLoginFailureDate(new Timestamp(System.currentTimeMillis()));
			if (user.getLoginFailureCount() >= maxLoginFailure)
			{
				user.setIsAccountLocked(true);
				user.setLockedFromIP(session.getRemote_Addr());
				InterfaceWrapperHelper.save(user);

				destroySessionOnLoginIncorrect(session);
				throw new AdempiereException("@UserAccountLockedError@"); // TODO: specific exception
			}

			InterfaceWrapperHelper.save(user);

			destroySessionOnLoginIncorrect(session);
			throw new AdempiereException("@UserOrPasswordInvalid@");
		}
		else
		{
			user.setLoginFailureCount(0);
			user.setIsAccountLocked(false);
			InterfaceWrapperHelper.save(user);
		}

		final int AD_User_ID = user.getAD_User_ID();
		ctx.setUser(AD_User_ID, username);

		//
		if (Ini.isClient())
		{
			final ISystemBL systemBL = Services.get(ISystemBL.class);

			if (systemBL.isRememberUserAllowed("SWING_LOGIN_ALLOW_REMEMBER_ME"))
				Ini.setProperty(Ini.P_UID, username);
			else
				Ini.setProperty(Ini.P_UID, "");
			if (Ini.isPropertyBool(Ini.P_STORE_PWD) && systemBL.isRememberPasswordAllowed("SWING_LOGIN_ALLOW_REMEMBER_ME"))
				Ini.setProperty(Ini.P_PWD, password);
		}

		//
		// Use user's AD_Language, if any
		if (!Check.isEmpty(user.getAD_Language()))
		{
			final Language language = Language.getLanguage(user.getAD_Language());
			Env.verifyLanguage(language);
			ctx.setAD_Language(language.getAD_Language());
		}

		//
		// Get user's roles
		final Set<KeyNamePair> roles = new LinkedHashSet<>();
		for (final I_AD_Role role : roleDAO.retrieveRolesForUser(ctx.getSessionContext(), user.getAD_User_ID()))
		{
			// if webui then skip non-webui roles
			if (ctx.isWebui() && !role.isWEBUI_Role())
			{
				continue;
			}

			if (role.getAD_Role_ID() == IUserRolePermissions.SYSTEM_ROLE_ID)
			{
				ctx.setSysAdmin(true);
			}

			final KeyNamePair roleKNP = KeyNamePair.of(role.getAD_Role_ID(), role.getName());
			roles.add(roleKNP);
		}
		//
		if (roles.isEmpty())
		{
			throw new AdempiereException("No roles"); // TODO: specific exception
		}

		log.debug("User={} - roles #{}", username, roles);
		return roles;
	}

	private MFSession startSession()
	{
		final LoginContext ctx = getCtx();

		final MFSession session = Services.get(ISessionBL.class).getCurrentOrCreateNewSession(ctx.getSessionContext());
		final String remoteAddr = getRemoteAddr();
		if (remoteAddr != null)
		{
			session.setRemote_Addr(remoteAddr, getRemoteHost());
			session.setWebSessionId(getWebSession());
		}

		return session;
	}

	private void destroySessionOnLoginIncorrect(final MFSession session)
	{
		session.setLoginIncorrect();
		Services.get(ISessionBL.class).logoutCurrentSession();

		getCtx().resetAD_Session_ID();
	}

	/**
	 * Sets current role and retrieves clients on which given role can login
	 *
	 * @param role role information
	 * @return list of valid client {@link KeyNamePair}s or null if in error
	 */
	public Set<KeyNamePair> setRoleAndGetClients(final KeyNamePair role)
	{
		if (role == null || role.getKey() < 0)
			throw new IllegalArgumentException("Role missing");

		//
		// Get user role
		final LoginContext ctx = getCtx();
		final int AD_Role_ID = role.getKey();
		final int AD_User_ID = ctx.getAD_User_ID();
		final IUserRolePermissions rolePermissions = getUserRolePermissions(AD_Role_ID, AD_User_ID);

		//
		// Get login AD_Clients
		final Set<KeyNamePair> clientsList = rolePermissions.getLoginClients();
		if (clientsList.isEmpty())
		{
			// shall not happen because in this case rolePermissions retrieving should fail
			throw new AdempiereException("No Clients for Role: " + role.toStringX());
		}

		//
		// Update login context
		ctx.setRole(rolePermissions.getAD_Role_ID(), rolePermissions.getName());
		ctx.setRoleUserLevel(rolePermissions.getUserLevel());
		ctx.setAllowLoginDateOverride(rolePermissions.hasPermission(IUserRolePermissions.PERMISSION_AllowLoginDateOverride));

		return clientsList;
	}

	private IUserRolePermissions getUserRolePermissions(final int AD_Role_ID, final int AD_User_ID)
	{
		//
		// Get user role
		final int AD_Client_ID = -1; // N/A
		final Date loginDate = SystemTime.asDayTimestamp(); // NOTE: to avoid hysteresis of Role->Date->Role, we always use system time
		final IUserRolePermissions rolePermissions = userRolePermissionsDAO.retrieveUserRolePermissions(AD_Role_ID, AD_User_ID, AD_Client_ID, loginDate);
		return rolePermissions;
	}

	public Set<KeyNamePair> getAvailableClients(final int AD_Role_ID, final int AD_User_ID)
	{
		return getUserRolePermissions(AD_Role_ID, AD_User_ID)
				.getLoginClients();
	}

	/**
	 * Sets current client in context and retrieves organizations on which we can login
	 *
	 * @param client client information
	 * @return list of valid organizations; never returns null
	 */
	public Set<KeyNamePair> setClientAndGetOrgs(final KeyNamePair client)
	{
		if (client == null || client.getKey() < 0)
			throw new IllegalArgumentException("Client missing");

		//
		// Get login organizations
		final LoginContext ctx = getCtx();
		final int AD_Client_ID = client.getKey();
		final int AD_Role_ID = ctx.getAD_Role_ID();
		final int AD_User_ID = ctx.getAD_User_ID();
		final Set<KeyNamePair> orgsList = getAvailableOrgs(AD_Role_ID, AD_User_ID, AD_Client_ID);

		//
		// Update login context
		ctx.setClient(client.getKey(), client.getName());

		return orgsList;
	}

	public Set<KeyNamePair> getAvailableOrgs(final int AD_Role_ID, final int AD_User_ID, final int AD_Client_ID)
	{
		Check.assume(AD_Role_ID >= 0, "AD_Role_ID >= 0");
		Check.assume(AD_User_ID >= 0, "AD_User_ID >= 0");
		Check.assume(AD_Client_ID >= 0, "AD_Client_ID >= 0");

		//
		// Get user role
		final Date loginDate = SystemTime.asDayTimestamp(); // NOTE: to avoid hysteresis of Role->Date->Role, we always use system time
		final IUserRolePermissions role = userRolePermissionsDAO.retrieveUserRolePermissions(AD_Role_ID, AD_User_ID, AD_Client_ID, loginDate);

		//
		// Get login organizations
		final Set<KeyNamePair> orgsList = new TreeSet<>();
		for (final OrgResource orgResource : role.getLoginOrgs())
		{
			orgsList.add(orgResource.asOrgKeyNamePair());
		}
		// No Orgs
		if (orgsList.isEmpty())
		{
			log.warn("No Org for AD_Client_ID={}, AD_Role_ID={}, AD_User_ID={} \nPermissions: {}", AD_Client_ID, AD_Role_ID, AD_User_ID, role);
			return ImmutableSet.of();
		}

		return orgsList;
	}

	/**
	 * Load Warehouses
	 *
	 * @param org organization
	 * @return Array of Warehouse Info
	 */
	public Set<KeyNamePair> getWarehouses(final KeyNamePair org)
	{
		if (org == null)
			throw new IllegalArgumentException("Org missing");

		if (!isShowWarehouseOnLogin())
		{
			return ImmutableSet.of();
		}

		final LoginContext ctx = getCtx();

		//
		// Get login warehouses
		final int adOrgId = org.getKey();
		final Set<KeyNamePair> warehouses = new TreeSet<>();
		for (final I_M_Warehouse warehouse : Services.get(IWarehouseDAO.class).retrieveForOrg(ctx.getSessionContext(), adOrgId))
		{
			// do not show in tranzit warehouses - teo_sarca [ 2867246 ]
			if (warehouse.isInTransit())
			{
				continue;
			}

			final KeyNamePair warehouseKNP = new KeyNamePair(warehouse.getM_Warehouse_ID(), warehouse.getName());
			warehouses.add(warehouseKNP);
		}

		return warehouses;
	}

	/**
	 * Validate Login
	 *
	 * @param org log-in org
	 * @return error message
	 */
	public String validateLogin(final KeyNamePair org)
	{
		final boolean fireLoginComplete = true;
		return validateLogin(org, fireLoginComplete);
	}

	public String validateLogin(final KeyNamePair org, final boolean fireLoginComplete)
	{
		final LoginContext ctx = getCtx();
		final int AD_Client_ID = ctx.getAD_Client_ID();
		final int AD_Org_ID = org.getKey();
		final int AD_Role_ID = ctx.getAD_Role_ID();
		final int AD_User_ID = ctx.getAD_User_ID();

		//
		// Update AD_Session
		final MFSession session = Services.get(ISessionBL.class).getCurrentSession(ctx.getSessionContext());
		if (session != null)
		{
			session.setLoginInfo(AD_Client_ID, AD_Org_ID, AD_Role_ID, AD_User_ID, ctx.getLoginDate());
			session.updateContext(ctx.getSessionContext());
		}
		else
		{
			// At this point, we definitely need to have a session created
			log.error("No session found");
		}
		// metas: end

		//
		// Fire LoginComplete
		if (fireLoginComplete)
		{
			final String error = ModelValidationEngine.get().loginComplete(AD_Client_ID, AD_Org_ID, AD_Role_ID, AD_User_ID);
			if (error != null && error.length() > 0)
			{
				log.error("Refused: " + error);
				return error;
			}
		}

		return null;
	}	// validateLogin

	/**
	 * Load Preferences into Context for selected client.
	 * <p>
	 * Sets Org info in context and loads relevant field from - AD_Client/Info,<br>
	 * - C_AcctSchema,<br>
	 * - C_AcctSchema_Elements<br>
	 * - AD_Preference
	 * <p>
	 * Assumes that the context is set for #AD_Client_ID, #AD_User_ID, #AD_Role_ID
	 *
	 * @param org org information
	 * @param warehouse optional warehouse information
	 * @param timestamp optional date
	 * @param printerName optional printer info
	 * @return AD_Message of error (NoValidAcctInfo) or ""
	 */
	public String loadPreferences(
			final KeyNamePair org,
			final KeyNamePair warehouse,
			final java.sql.Timestamp timestamp)
	{
		Check.assumeNotNull(org, "Parameter org is not null");

		final LoginContext ctx = getCtx();
		final int AD_Client_ID = ctx.getAD_Client_ID();
		final int AD_User_ID = ctx.getAD_User_ID();
		final int AD_Role_ID = ctx.getAD_Role_ID();

		//
		// Org Info - assumes that it is valid
		ctx.setOrg(org.getKey(), org.getName());
		final int AD_Org_ID = ctx.getAD_Org_ID();

		//
		// Date (default today)
		final Timestamp loginDate;
		if (timestamp == null)
		{
			loginDate = SystemTime.asDayTimestamp();
		}
		else
		{
			loginDate = TimeUtil.trunc(timestamp, TimeUtil.TRUNC_DAY);
		}
		ctx.setLoginDate(loginDate);

		//
		// Role additional info
		final IUserRolePermissions userRolePermissions = userRolePermissionsDAO.retrieveUserRolePermissions(AD_Role_ID, AD_User_ID, AD_Client_ID, loginDate);
		ctx.setUserOrgs(userRolePermissions.getAD_Org_IDs_AsString());

		//
		// Warehouse
		// task 06009 if ShowWarehouseOnLogin is N pick the warehouse of the orgInfo (if it has one)
		if (isShowWarehouseOnLogin())
		{
			// Warehouse Info
			if (warehouse != null)
			{
				ctx.setWarehouse(warehouse.getKey(), warehouse.getName());
			}
		}
		else
		{
			final I_M_Warehouse orgWarehouse = warehouseDAO.retrieveOrgWarehouse(ctx.getSessionContext(), AD_Org_ID);
			if (orgWarehouse != null)
			{
				ctx.setWarehouse(orgWarehouse.getM_Warehouse_ID(), orgWarehouse.getName());
			}
		}

		// Other
		ctx.setPrinterName(Services.get(IPrinterRoutingBL.class).getDefaultPrinterName()); // Optional Printer
		ctx.setAutoCommit(Ini.isPropertyBool(Ini.P_A_COMMIT));
		ctx.setAutoNew(Ini.isPropertyBool(Ini.P_A_NEW));
		ctx.setProperty(Env.CTXNAME_ShowAcct, Services.get(IPostingService.class).isEnabled()
				&& userRolePermissions.hasPermission(IUserRolePermissions.PERMISSION_ShowAcct)
				&& Ini.isPropertyBool(Ini.P_SHOW_ACCT));
		ctx.setProperty("#ShowTrl", Ini.isPropertyBool(Ini.P_SHOW_TRL));
		ctx.setProperty("#ShowAdvanced", Ini.isPropertyBool(Ini.P_SHOW_ADVANCED));

		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		ctx.setProperty("#CashAsPayment", sysConfigBL.getBooleanValue("CASH_AS_PAYMENT", true, AD_Client_ID));

		String retValue = "";

		//
		// Other Settings
		ctx.setProperty("#YYYY", "Y");
		ctx.setProperty("#StdPrecision", 2);

		//
		// AccountSchema Info (first)
		try
		{
			loadAccounting();
		}
		catch (AccountingException e)
		{
			retValue = "NoValidAcctInfo";
		}

		try
		{
			//
			// Load preferences
			loadPreferences();

			//
			// Default Values
			loadDefaults();
		}
		catch (Exception e)
		{
			log.error("loadPreferences", e);
		}

		//
		// Save Ini properties
		if (Ini.isClient())
		{
			Ini.saveProperties();
		}

		//
		// Country
		ctx.setProperty("#C_Country_ID", Services.get(ICountryDAO.class).getDefault(ctx.getSessionContext()).getC_Country_ID());

		//
		// metas: c.ghita@metas.ro : start : 01552
		if (sysConfigBL.getBooleanValue("CACHE_WINDOWS_DISABLED", true))
		{
			Ini.setProperty(Ini.P_CACHE_WINDOW, false);
		}

		//
		// Set System Status Message
		loadUIWindowHeaderNotice();

		//
		// Call ModelValidators afterLoadPreferences - teo_sarca FR [ 1670025 ]
		ModelValidationEngine.get().afterLoadPreferences(ctx.getSessionContext());

		return retValue;
	}	// loadPreferences

	/**
	 * Loads accounting info
	 *
	 * @throws AccountingException if no accounting schema was found.
	 */
	private void loadAccounting() throws AccountingException
	{
		final LoginContext ctx = getCtx();
		final int AD_Role_ID = ctx.getAD_Role_ID();

		// Don't try to load the accounting schema for System role
		if (AD_Role_ID == Env.CTXVALUE_AD_Role_ID_System)
		{
			return;
		}

		final I_C_AcctSchema acctSchema = acctSchemaDAO.retrieveAcctSchema(ctx.getSessionContext()); // could throw AccountingException
		int C_AcctSchema_ID = acctSchema.getC_AcctSchema_ID();

		//
		// Accounting Info
		ctx.setAcctSchema(acctSchema.getC_AcctSchema_ID(), acctSchema.getC_Currency_ID(), acctSchema.isHasAlias());

		//
		// Define AcctSchema , Currency, HasAlias for Multi AcctSchema **/
		// Note: this might override the context values we set above. Leving that code untouched for now..
		final int AD_Client_ID = ctx.getAD_Client_ID();
		final MAcctSchema[] ass = MAcctSchema.getClientAcctSchema(ctx.getSessionContext(), AD_Client_ID);
		if (ass != null && ass.length > 1)
		{
			final int AD_Org_ID = ctx.getAD_Org_ID();
			for (final MAcctSchema as : ass)
			{
				C_AcctSchema_ID = clientDAO.retrieveClientInfo(ctx.getSessionContext(), AD_Client_ID).getC_AcctSchema1_ID();
				if (as.getAD_OrgOnly_ID() != 0)
				{
					if (as.isSkipOrg(AD_Org_ID))
					{
						continue;
					}
					else
					{
						C_AcctSchema_ID = as.getC_AcctSchema_ID();
						ctx.setAcctSchema(C_AcctSchema_ID, as.getC_Currency_ID(), as.isHasAlias());
						break;
					}
				}
			}
		}

		loadAccountingSchemaElements();
	}

	private void loadAccountingSchemaElements()
	{
		final LoginContext ctx = getCtx();
		final int C_AcctSchema_ID = ctx.getC_AcctSchema_ID();

		if (C_AcctSchema_ID <= 0)
		{
			return;
		}

		//
		// Load accounting schema elements
		final String sql = "SELECT ElementType FROM C_AcctSchema_Element WHERE C_AcctSchema_ID=? AND IsActive=?";
		final Object[] sqlParams = new Object[] { C_AcctSchema_ID, true };
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				ctx.setProperty(Env.CTXNAME_AcctSchemaElementPrefix + rs.getString("ElementType"), true);
			}
		}
		catch (SQLException e)
		{
			throw new DBException(e, sql, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	private void loadPreferences()
	{
		final LoginContext ctx = getCtx();
		final int AD_Client_ID = ctx.getAD_Client_ID();
		final int AD_Org_ID = ctx.getAD_Org_ID();
		final int AD_User_ID = ctx.getAD_User_ID();

		Services.get(IValuePreferenceBL.class)
				.getAllWindowPreferences(AD_Client_ID, AD_Org_ID, AD_User_ID)
				.stream()
				.flatMap(userValuePreferences -> userValuePreferences.values().stream())
				.forEach(ctx::setPreference);
	}

	private void loadDefaults() throws SQLException
	{
		log.debug("Default Values ...");

		final String sqlDefaulValues = "SELECT t.TableName, c.ColumnName "
				+ "FROM AD_Column c "
				+ " INNER JOIN AD_Table t ON (c.AD_Table_ID=t.AD_Table_ID) "
				+ "WHERE c.IsKey='Y' AND t.IsActive='Y'"
				+ " AND EXISTS (SELECT * FROM AD_Column cc "
				+ " WHERE ColumnName = 'IsDefault' AND t.AD_Table_ID=cc.AD_Table_ID AND cc.IsActive='Y')";

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sqlDefaulValues, ITrx.TRXNAME_None);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				final String tableName = rs.getString(1);
				final String columnName = rs.getString(2);
				loadDefault(tableName, columnName);
			}
		}
		finally
		{
			DB.close(rs, pstmt);
			pstmt = null;
			rs = null;
		}

	}

	/**
	 * Load Default Value for Table into Context.
	 *
	 * @param TableName table name
	 * @param ColumnName column name
	 */
	private void loadDefault(final String TableName, final String ColumnName)
	{
		if (TableName.startsWith("AD_Window")
				|| TableName.startsWith("AD_PrintFormat")
				|| TableName.startsWith("AD_Workflow"))
		{
			return;
		}

		final LoginContext ctx = getCtx();

		String value = null;
		//
		String sql = "SELECT " + ColumnName + " FROM " + TableName	// most specific first
				+ " WHERE IsDefault='Y' AND IsActive='Y' ORDER BY AD_Client_ID DESC, AD_Org_ID DESC";
		sql = Env.getUserRolePermissions(ctx.getSessionContext()).addAccessSQL(sql, TableName, IUserRolePermissions.SQL_NOTQUALIFIED, IUserRolePermissions.SQL_RO);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				value = rs.getString(1);
			}

			// If there is more then one default value, we shall avoid having a default value in context at all
			if (rs.next())
			{
				value = null;
			}
		}
		catch (SQLException e)
		{
			log.error(TableName + " (" + sql + ")", e);
			return;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		// Set Context Value
		if (value != null && value.length() != 0)
		{
			if (I_C_DocType.Table_Name.equals(TableName))
				ctx.setProperty("#C_DocTypeTarget_ID", value);
			else
				ctx.setProperty("#" + ColumnName, value);
		}
	}	// loadDefault

	/**
	 *
	 * Set System Status Message.
	 *
	 * See http://dewiki908/mediawiki/index.php/05730_Use_different_Theme_colour_on_UAT_system.
	 *
	 * NOTE: we are retrieving from database and we are storing in context because this String is used in low level UI components and in some cases there is no database connection at all
	 */
	private void loadUIWindowHeaderNotice()
	{
		final LoginContext ctx = getCtx();
		final int AD_Client_ID = ctx.getAD_Client_ID();
		final int AD_Org_ID = ctx.getAD_Org_ID();

		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

		final String windowHeaderNoticeText = sysConfigBL.getValue(SYSCONFIG_UI_WindowHeader_Notice_Text, AD_Client_ID, AD_Org_ID);
		ctx.setProperty(Env.CTXNAME_UI_WindowHeader_Notice_Text, windowHeaderNoticeText);

		// FRESH-352: also allow setting the status message's foreground and background color.
		final String windowHeaderBackgroundColor = sysConfigBL.getValue(SYSCONFIG_UI_WindowHeader_Notice_BG_Color, AD_Client_ID, AD_Org_ID);
		ctx.setProperty(Env.CTXNAME_UI_WindowHeader_Notice_BG_COLOR, windowHeaderBackgroundColor);

		final String windowHeaderForegroundColor = sysConfigBL.getValue(SYSCONFIG_UI_WindowHeader_Notice_FG_Color, AD_Client_ID, AD_Org_ID);
		ctx.setProperty(Env.CTXNAME_UI_WindowHeader_Notice_FG_COLOR, windowHeaderForegroundColor);
	}

	public void setRemoteAddr(final String remoteAddr)
	{
		getCtx().setRemoteAddr(remoteAddr);
	}

	public String getRemoteAddr()
	{
		return getCtx().getRemoteAddr();
	}	// RemoteAddr

	public void setRemoteHost(final String remoteHost)
	{
		getCtx().setRemoteHost(remoteHost);
	}

	public String getRemoteHost()
	{
		return getCtx().getRemoteHost();
	}	// RemoteHost

	public void setWebSession(final String webSession)
	{
		getCtx().setWebSession(webSession);
	}

	public String getWebSession()
	{
		return getCtx().getWebSession();
	}	// WebSession

	public boolean isShowWarehouseOnLogin()
	{
		final boolean defaultValue = true;
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		return sysConfigBL.getBooleanValue(SYSCONFIG_ShowWarehouseOnLogin, defaultValue, Env.CTXVALUE_AD_Client_ID_System, Env.CTXVALUE_AD_Org_ID_System);
	}

	public boolean isAllowLoginDateOverride()
	{
		return getCtx().isAllowLoginDateOverride();
	}
}	// Login
