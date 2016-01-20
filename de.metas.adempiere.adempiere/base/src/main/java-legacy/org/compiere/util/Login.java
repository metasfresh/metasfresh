/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.util;

import java.security.Principal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;

import org.adempiere.acct.api.IAcctSchemaDAO;
import org.adempiere.acct.api.IPostingService;
import org.adempiere.acct.api.exception.AccountingException;
import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.security.IUserRolePermissionsDAO;
import org.adempiere.ad.security.permissions.OrgResource;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBNoConnectionException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.IClientDAO;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.user.api.IUserDAO;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.Adempiere;
import org.compiere.db.CConnection;
import org.compiere.model.I_AD_Role;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MCountry;
import org.compiere.model.MSession;
import org.compiere.model.MSystem;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.X_AD_Role;

import de.metas.adempiere.model.I_AD_Session;
import de.metas.adempiere.model.I_AD_User;
import de.metas.adempiere.service.IPrinterRoutingBL;

/**
 *	Login Manager
 *	
 *  @author Jorg Janke
 *  @author victor.perez@e-evolution.com, e-Evolution http://www.e-evolution.com
 *		<li>Incorrect global Variable when you use multi Account Schema
 *			http://sourceforge.net/tracker/?func=detail&atid=879335&aid=2531597&group_id=176962
 *  @author teo.sarca@gmail.com
 *  	<li>BF [ 2867246 ] Do not show InTrazit WHs on login
 *  		https://sourceforge.net/tracker/?func=detail&aid=2867246&group_id=176962&atid=879332
 *  @version $Id: Login.java,v 1.6 2006/10/02 05:19:06 jjanke Exp $
 */
public class Login
{
	/**
	 * see http://dewiki908/mediawiki/index.php/05730_Use_different_Theme_colour_on_UAT_system
	 */
	private static final String SYSCONFIG_UI_WindowHeader_Notice = "UI_WindowHeader_Notice";
	/**
	 * see http://dewiki908/mediawiki/index.php/06009_Login_Lager_weg_%28106922640136%29
	 */
	private static final String SYSCONFIG_ShowWarehouseOnLogin = "ShowWarehouseOnLogin";

	// services
	private final transient ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final transient IClientDAO clientDAO = Services.get(IClientDAO.class);
	private final transient IUserRolePermissionsDAO userRolePermissionsDAO = Services.get(IUserRolePermissionsDAO.class);
	private final transient IUserDAO userDAO = Services.get(IUserDAO.class);
	private final transient IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	private final transient IAcctSchemaDAO acctSchemaDAO = Services.get(IAcctSchemaDAO.class);
	private final transient IPrinterRoutingBL printerRoutingBL = Services.get(IPrinterRoutingBL.class);

	/**
	 * Test Init - Set Environment for tests
	 * 
	 * @param isClient client session
	 * @return Context
	 */
	public static Properties initTest(boolean isClient)
	{
		// logger.entering("Env", "initTest");
		if (!Adempiere.startupEnvironment(true))
			System.exit(1);
		// Test Context
		Properties ctx = Env.getCtx();
		Login login = new Login(ctx);
		KeyNamePair[] roles = login.getRoles(CConnection.get(), "System", "System", true);
		// load role
		if (roles != null && roles.length > 0)
		{
			KeyNamePair[] clients = login.getClients(roles[0]);
			// load client
			if (clients != null && clients.length > 0)
			{
				KeyNamePair[] orgs = login.getOrgs(clients[0]);
				// load org
				if (orgs != null && orgs.length > 0)
				{
					KeyNamePair[] whs = login.getWarehouses(orgs[0]);
					//
					login.loadPreferences(orgs[0], null, null, null);
				}
			}
		}
		//
		Env.setContext(ctx, Env.CTXNAME_Date, "2000-01-01");
		// logger.exiting("Env", "initTest");
		return ctx;
	}   // testInit

	/**
	 * Java Version Test
	 * 
	 * @param isClient client connection
	 * @return true if Java Version is OK
	 */
	public static boolean isJavaOK(boolean isClient)
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
		log.info(msg.toString());
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
		// msg.append("  <>  1.5.0");
		// //
		// if (isClient)
		// JOptionPane.showMessageDialog(null, msg.toString(),
		// org.compiere.Adempiere.getName() + " - Java Version Check",
		// ok ? JOptionPane.WARNING_MESSAGE : JOptionPane.ERROR_MESSAGE);
		// else
		// log.severe(msg.toString());
		// return ok;
	}   // isJavaOK

	/**************************************************************************
	 * Login
	 * 
	 * @param ctx context
	 */
	public Login(Properties ctx)
	{
		super();
		if (ctx == null)
			throw new IllegalArgumentException("Context missing");
		m_ctx = ctx;
	}	// Login

	public Properties getCtx()
	{
		return m_ctx;
	}

	/** Logger */
	private static CLogger log = CLogger.getCLogger(Login.class);
	/** Context */
	private Properties m_ctx = null;
	/** Connection Profile */
	private String m_connectionProfile = null;

	/**
	 * (Test) Client Login.
	 * <p>
	 * - Get Connection<br>
	 * - Compare User info
	 * <p>
	 * Sets Context with login info
	 * 
	 * @param cc connection
	 * @param app_user user
	 * @param app_pwd pwd
	 * @param force ignore pwd
	 * @return Array of Role KeyNamePair or null if error The error (NoDatabase, UserPwdError, DBLogin) is saved in the log
	 */
	private KeyNamePair[] getRoles(CConnection cc, String app_user, String app_pwd, boolean force)
	{
		// Establish connection
		DB.setDBTarget(cc);
		Env.setContext(m_ctx, "#Host", cc.getAppsHost());
		Env.setContext(m_ctx, "#Database", cc.getDbName());

		// Check the connection
		Connection conn = DB.getConnectionRO();
		if (conn == null)
		{
			// shall not happen
			throw new DBNoConnectionException();
		}
		DB.close(conn);

		if (app_pwd == null)
			return null;
		//
		return getRoles(app_user, app_pwd, force);
	}   // getRoles

	/**
	 * (Web) Client Login.
	 * <p>
	 * Compare User Info
	 * <p>
	 * Sets Context with login info
	 * 
	 * @param app_user Principal
	 * @return role array or null if in error. The error (NoDatabase, UserPwdError, DBLogin) is saved in the log
	 */
	public KeyNamePair[] getRoles(Principal app_user)
	{
		if (app_user == null)
			return null;
		// login w/o password as previously authorized
		return getRoles(app_user.getName(), null, false);
	}   // getRoles

	/**
	 * Client Login.
	 * <p>
	 * Compare User Info
	 * <p>
	 * Sets Context with login info
	 * 
	 * @param app_user user id
	 * @param app_pwd password
	 * @return role array or null if in error. The error (NoDatabase, UserPwdError, DBLogin) is saved in the log
	 */
	public KeyNamePair[] getRoles(String app_user, String app_pwd)
	{
		final boolean force = false;
		return getRoles(app_user, app_pwd, force);
	}   // login

	/**
	 * Actual DB login procedure.
	 * 
	 * @param app_user user
	 * @param app_pwd pwd
	 * @param force ignore pwd
	 * @return role array or null if in error. The error (NoDatabase, UserPwdError, DBLogin) is saved in the log
	 */
	private KeyNamePair[] getRoles(final String app_user, String app_pwd, final boolean force)
	{
		log.info("User=" + app_user);
		long start = System.currentTimeMillis();
		if (app_user == null)
		{
			log.warning("No Apps User");
			return null;
		}

		// Authentification
		if (Ini.isClient())
			CConnection.get().setAppServerCredential(app_user, app_pwd);

		if (app_pwd == null || app_pwd.length() == 0)
		{
			log.warning("No Apps Password");
			return null;
		}

		//
		// Try auth via LDAP
		boolean authenticated = false;
		if (!authenticated)
		{
			authenticated = authLDAP(app_user, app_pwd);
		}

		//
		// If we were authenticated, reset the password becuse we no longer need it
		if (authenticated)
		{
			app_pwd = null;
		}

		//
		// If not authenticated so far, use AD_User as backup
		//

		final int maxLoginFailure = sysConfigBL.getIntValue("ZK_LOGIN_FAILURES_MAX", 3);
		final int accountLockExpire = sysConfigBL.getIntValue("USERACCOUNT_LOCK_EXPIRE", 30);
		MSession sessionPO;
		if (m_remoteAddr != null)
			sessionPO = MSession.get(m_ctx, m_remoteAddr, m_remoteHost, m_webSession);
		else
			sessionPO = MSession.get(m_ctx, true);
		final I_AD_Session session = InterfaceWrapperHelper.create(sessionPO, I_AD_Session.class);
		if (!app_user.equals(session.getLoginUsername()))
		{
			session.setLoginUsername(app_user);
			sessionPO.saveEx();
		}

		KeyNamePair[] retValue = null;
		ArrayList<KeyNamePair> list = new ArrayList<KeyNamePair>();
		//
		final StringBuilder sql = new StringBuilder("SELECT u.AD_User_ID, r.AD_Role_ID,r.Name, ")
				.append(" u.ConnectionProfile ")
				.append(", u.LoginFailureCount, u.IsAccountLocked , u.Password , u.LoginFailureDate, ") // metas: ab
				.append("(CASE WHEN ((u.Password=? AND (SELECT IsEncrypted FROM AD_Column WHERE AD_Column_ID=417)='N') " // #1
						+ "OR (u.Password=? AND (SELECT IsEncrypted FROM AD_Column WHERE AD_Column_ID=417)='Y'))"	// #2
						+ " THEN 'Y' ELSE 'N' END) AS PasswordValid")
				.append(" FROM AD_User u")
				.append(" INNER JOIN AD_User_Roles ur ON (u.AD_User_ID=ur.AD_User_ID AND ur.IsActive='Y')")
				.append(" INNER JOIN AD_Role r ON (ur.AD_Role_ID=r.AD_Role_ID AND r.IsActive='Y') ")
				// 04212: only use the new field "Login" and "Email"
				.append(" WHERE (u.Login=? OR u.EMail=?)")		// #3,4
				// US362: Login using email address (2010070610000359) - added EMail=?
				.append(" AND u.IsActive='Y' AND u.issystemuser='Y'")
				.append(" AND EXISTS (SELECT 1 FROM AD_Client c WHERE u.AD_Client_ID=c.AD_Client_ID AND c.IsActive='Y')")

				.append(" ORDER BY ")
				.append(I_AD_Role.COLUMNNAME_SeqNo + " NULLS LAST,")
				.append(I_AD_Role.COLUMNNAME_AD_Role_ID);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), null);
			pstmt.setString(1, app_pwd);
			pstmt.setString(2, SecureEngine.encrypt(app_pwd));
			pstmt.setString(3, app_user);
			pstmt.setString(4, app_user); // US362: Login using email address (2010070610000359)

			// execute a query
			rs = pstmt.executeQuery();

			if (!rs.next())		// no record found
			{
				if (force)
				{
					Env.setContext(m_ctx, Env.CTXNAME_AD_User_Name, "System");
					Env.setContext(m_ctx, Env.CTXNAME_AD_User_ID, 0);
					Env.setContext(m_ctx, "#AD_User_Description", "System Forced Login");
					Env.setContext(m_ctx, Env.CTXNAME_AD_Role_UserLevel, X_AD_Role.USERLEVEL_System);  	// Format 'SCO'
					Env.setContext(m_ctx, "#User_Client", "0");		// Format c1, c2, ...
					Env.setContext(m_ctx, Env.CTXNAME_User_Org, "0"); 		// Format o1, o2, ...
					DB.close(rs, pstmt); // not really needed
					retValue = new KeyNamePair[] { new KeyNamePair(0, "System Administrator") };
					return retValue;
				}
				else
				{
					DB.close(rs, pstmt); // not really needed
					log.saveError("UserPwdError", app_user, false);
					return null;
				}
			}
			final int adUserId = rs.getInt(I_AD_User.COLUMNNAME_AD_User_ID);
			final I_AD_User user = userDAO.retrieveUser(m_ctx, adUserId);
			int loginFailureCount = rs.getInt(I_AD_User.COLUMNNAME_LoginFailureCount);

			// metas: us902: Update logged in user if not equal.
			// Because we are creating the session before we know which user will be logged, initially
			// the AD_Session.CreatedBy is zero.
			if (sessionPO.getAD_User_ID() != user.getAD_User_ID())
			{
				sessionPO.setAD_User_ID(user.getAD_User_ID());
				sessionPO.saveEx();
			}
			// metas: us902: end

			boolean isAccountLocked = DisplayType.toBoolean(rs.getString("IsAccountLocked"));
			if (isAccountLocked)
			{
				Timestamp curentLogin = (new Timestamp(System.currentTimeMillis()));
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
					log.saveError("UserAccountLockedError", app_user, false);
					return null;
				}
			}
			boolean isPasswordValid = authenticated;
			if (!isPasswordValid)
				isPasswordValid = DisplayType.toBoolean(rs.getString("PasswordValid"));
			if (!isPasswordValid)
			{
				loginFailureCount = loginFailureCount + 1;
				user.setLoginFailureCount(loginFailureCount);
				user.setLoginFailureDate(new Timestamp(System.currentTimeMillis()));
				if (user.getLoginFailureCount() >= maxLoginFailure)
				{
					user.setIsAccountLocked(true);
					user.setLockedFromIP(session.getRemote_Addr());
					InterfaceWrapperHelper.save(user);
					session.setIsLoginIncorrect(true);
					sessionPO.logout();
					Env.setContext(getCtx(), Env.CTXNAME_AD_Session_ID, "");
					log.saveError("UserAccountLockedError", app_user, false);
					return null;
				}
				InterfaceWrapperHelper.save(user);
				session.setIsLoginIncorrect(true);
				sessionPO.logout();
				Env.setContext(getCtx(), Env.CTXNAME_AD_Session_ID, "");
				log.saveError("UserPwdError", app_user, false);
				return null;
			}
			else
			{
				user.setLoginFailureCount(0);
				user.setIsAccountLocked(false);
				InterfaceWrapperHelper.save(user);
			}

			Env.setContext(m_ctx, Env.CTXNAME_AD_User_Name, app_user);
			Env.setContext(m_ctx, Env.CTXNAME_AD_User_ID, rs.getInt(1));
			Env.setContext(m_ctx, Env.CTXNAME_SalesRep_ID, rs.getInt(1));
			//
			if (Ini.isClient())
			{
				if (MSystem.isSwingRememberUserAllowed())
					Ini.setProperty(Ini.P_UID, app_user);
				else
					Ini.setProperty(Ini.P_UID, "");
				if (Ini.isPropertyBool(Ini.P_STORE_PWD) && MSystem.isSwingRememberPasswordAllowed())
					Ini.setProperty(Ini.P_PWD, app_pwd);

				m_connectionProfile = rs.getString(4);		// User Based
				if (m_connectionProfile != null)
				{
					CConnection cc = CConnection.get();
					if (!cc.getConnectionProfile().equals(m_connectionProfile))
					{
						cc.setConnectionProfile(m_connectionProfile);
						Ini.setProperty(Ini.P_CONNECTION, cc.toStringLong());
						Ini.saveProperties(false);
					}
				}
			}

			do	// read all roles
			{
				int AD_Role_ID = rs.getInt(2);
				if (AD_Role_ID == IUserRolePermissions.SYSTEM_ROLE_ID)
				{
					Env.setContext(m_ctx, "#SysAdmin", true);
				}
				String Name = rs.getString(3);
				KeyNamePair p = new KeyNamePair(AD_Role_ID, Name);
				list.add(p);
			}
			while (rs.next());
			//
			retValue = new KeyNamePair[list.size()];
			list.toArray(retValue);
			log.fine("User=" + app_user + " - roles #" + retValue.length);
		}
		catch (Exception ex)
		{
			log.log(Level.SEVERE, sql.toString(), ex);
			log.saveError("DBLogin", ex);
			retValue = null;
		}
		//
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		// long ms = System.currentTimeMillis() - start;
		return retValue;
	}	// getRoles

	private final boolean authLDAP(final String app_user, final String app_pwd)
	{
		// LDAP auth is not currently supported in JUnit testing mode
		if (Adempiere.isUnitTestMode())
		{
			return false;
		}

		final MSystem system = MSystem.get(m_ctx);
		if (system == null)
		{
			throw new IllegalStateException("No System Info");
		}

		// LDAP auth not configured
		if (!system.isLDAP())
		{
			return false;
		}

		final boolean authenticated = system.isLDAP(app_user, app_pwd);
		return authenticated;
	}

	/**************************************************************************
	 * Load Clients.
	 * <p>
	 * Sets Role info in context and loads its clients
	 * 
	 * @param role role information
	 * @return list of valid client {@link KeyNamePair}s or null if in error
	 */
	public KeyNamePair[] getClients(final KeyNamePair role)
	{
		if (role == null)
			throw new IllegalArgumentException("Role missing");

		final int AD_Client_ID = -1; // N/A
		final int AD_Role_ID = role.getKey();

		if (Env.getContext(m_ctx, Env.CTXNAME_AD_User_ID).length() == 0)	// could be number 0
			throw new UnsupportedOperationException("Missing Context: " + Env.CTXNAME_AD_User_ID);
		final int AD_User_ID = Env.getContextAsInt(m_ctx, Env.CTXNAME_AD_User_ID);
		final Date loginDate = SystemTime.asDayTimestamp(); // NOTE: to avoid hysteresis of Role->Date->Role, we always use system time
		final IUserRolePermissions rolePermissions = userRolePermissionsDAO.retrieveUserRolePermissions(AD_Role_ID, AD_User_ID, AD_Client_ID, loginDate);

		// Get login AD_Clients
		final Set<KeyNamePair> clientsList = new TreeSet<>();
		for (final OrgResource orgResource : rolePermissions.getLoginOrgs())
		{
			clientsList.add(orgResource.asClientKeyNamePair());
		}

		if (clientsList.isEmpty())
		{
			log.log(Level.SEVERE, "No Clients for Role: " + role.toStringX());
			return null;
		}

		// Role Info
		Env.setContext(m_ctx, Env.CTXNAME_AD_Role_ID, rolePermissions.getAD_Role_ID());
		Env.setContext(m_ctx, Env.CTXNAME_AD_Role_Name, rolePermissions.getName());
		Ini.setProperty(Ini.P_ROLE, rolePermissions.getName());
		// User Level
		Env.setContext(m_ctx, Env.CTXNAME_AD_Role_UserLevel, rolePermissions.getUserLevel().getUserLevelString()); // Format 'SCO'

		//
		// ConnectionProfile
		// NOTE: commented out because it's deprecated anyways
		// CConnection cc = CConnection.get();
		// if (m_connectionProfile == null) // No User Based
		// {
		// m_connectionProfile = rs.getString(2); // Role Based
		// if (m_connectionProfile != null
		// && !cc.getConnectionProfile().equals(m_connectionProfile))
		// {
		// cc.setConnectionProfile(m_connectionProfile);
		// Ini.setProperty(Ini.P_CONNECTION, cc.toStringLong());
		// Ini.saveProperties(false);
		// }
		// }

		//
		final boolean allowLoginDateOverride = rolePermissions.hasPermission(IUserRolePermissions.PERMISSION_AllowLoginDateOverride);
		Env.setContext(m_ctx, Env.CTXNAME_IsAllowLoginDateOverride, allowLoginDateOverride);

		return clientsList.toArray(new KeyNamePair[clientsList.size()]);
	}   // getClients

	/**
	 * Load Organizations.
	 * <p>
	 * Sets Client info in context and loads its organization, the role has access to
	 * 
	 * @param client client information
	 * @return list of valid Org KeyNodePairs or null if in error
	 */
	public KeyNamePair[] getOrgs(final KeyNamePair client)
	{
		if (client == null)
			throw new IllegalArgumentException("Client missing");

		final Properties ctx = getCtx();
		if (Env.getContext(ctx, Env.CTXNAME_AD_Role_ID).length() == 0)	// could be number 0
			throw new UnsupportedOperationException("Missing Context: " + Env.CTXNAME_AD_Role_ID);

		final int AD_Client_ID = client.getKey();
		final int AD_Role_ID = Env.getContextAsInt(ctx, Env.CTXNAME_AD_Role_ID);
		final int AD_User_ID = Env.getContextAsInt(ctx, Env.CTXNAME_AD_User_ID);
		final Date loginDate = SystemTime.asDayTimestamp(); // NOTE: to avoid hysteresis of Role->Date->Role, we always use system time
		final IUserRolePermissions role = userRolePermissionsDAO.retrieveUserRolePermissions(AD_Role_ID, AD_User_ID, AD_Client_ID, loginDate);

		// Get login organizations
		final Set<KeyNamePair> orgsList = new TreeSet<>();
		for (final OrgResource orgResource : role.getLoginOrgs())
		{
			orgsList.add(orgResource.asOrgKeyNamePair());
		}

		// No Orgs
		if (orgsList.isEmpty())
		{
			log.log(Level.WARNING, "No Org for Client: " + client.toStringX()
					+ ", AD_Role_ID=" + AD_Role_ID
					+ ", AD_User_ID=" + AD_User_ID
					+ "\n Permissions: " + role);
			return null;
		}

		// Client Info
		Env.setContext(ctx, Env.CTXNAME_AD_Client_ID, client.getKey());
		Env.setContext(ctx, "#AD_Client_Name", client.getName());
		Ini.setProperty(Ini.P_CLIENT, client.getName());

		return orgsList.toArray(new KeyNamePair[orgsList.size()]);
	}   // getOrgs

	/**
	 * Load Warehouses
	 * 
	 * @param org organization
	 * @return Array of Warehouse Info
	 */
	public KeyNamePair[] getWarehouses(final KeyNamePair org)
	{
		if (org == null)
			throw new IllegalArgumentException("Org missing");

		if (!isShowWarehouseOnLogin())
		{
			return new KeyNamePair[] {};
		}

		final int adOrgId = org.getKey();
		final Set<KeyNamePair> warehouses = new TreeSet<>();
		for (final I_M_Warehouse warehouse : Services.get(IWarehouseDAO.class).retrieveForOrg(getCtx(), adOrgId))
		{
			// do not show in tranzit warehouses - teo_sarca [ 2867246 ]
			if (warehouse.isInTransit())
			{
				continue;
			}

			final KeyNamePair warehouseKNP = new KeyNamePair(warehouse.getM_Warehouse_ID(), warehouse.getName());
			warehouses.add(warehouseKNP);
		}

		return warehouses.toArray(new KeyNamePair[warehouses.size()]);
	}   // getWarehouses

	/**
	 * Validate Login
	 * 
	 * @param org log-in org
	 * @return error message
	 */
	public String validateLogin(KeyNamePair org)
	{
		final boolean fireLoginComplete = true;
		return validateLogin(org, fireLoginComplete);
	}

	public String validateLogin(KeyNamePair org, final boolean fireLoginComplete)
	{
		int AD_Client_ID = Env.getAD_Client_ID(m_ctx);
		int AD_Org_ID = org.getKey();
		int AD_Role_ID = Env.getAD_Role_ID(m_ctx);
		int AD_User_ID = Env.getAD_User_ID(m_ctx);

		//
		// metas: Update session
		final MSession session = MSession.get(m_ctx, false);
		if (session != null)
		{
			session.set_ValueNoCheck(MSession.COLUMNNAME_AD_Client_ID, AD_Client_ID); // Force AD_Client_ID update
			session.setAD_Org_ID(AD_Org_ID);
			session.setAD_Role_ID(AD_Role_ID);
			session.setAD_User_ID(AD_User_ID);
			session.setLoginDate(Env.getContextAsDate(m_ctx, Env.CTXNAME_Date));
			if (session.save())
			{
				session.updateContext(true);
			}
		}
		else
		{
			// At this point, we definitely need to have a session created
			log.severe("No session found");
		}
		// metas: end

		//
		// Fire LoginComplete
		if (fireLoginComplete)
		{
			final String error = ModelValidationEngine.get().loginComplete(AD_Client_ID, AD_Org_ID, AD_Role_ID, AD_User_ID);
			if (error != null && error.length() > 0)
			{
				log.severe("Refused: " + error);
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
			final java.sql.Timestamp timestamp,
			final String printerName)
	{
		if (log.isLoggable(Level.INFO))
			log.info("Org: " + org.toStringX());

		if (m_ctx == null || org == null)
			throw new IllegalArgumentException("Required parameter missing");
		if (Env.getContext(m_ctx, Env.CTXNAME_AD_Client_ID).length() == 0)
			throw new UnsupportedOperationException("Missing Context #AD_Client_ID");
		if (Env.getContext(m_ctx, Env.CTXNAME_AD_User_ID).length() == 0)
			throw new UnsupportedOperationException("Missing Context #AD_User_ID");
		if (Env.getContext(m_ctx, Env.CTXNAME_AD_Role_ID).length() == 0)
			throw new UnsupportedOperationException("Missing Context #AD_Role_ID");

		//
		// Org Info - assumes that it is valid
		Env.setContext(m_ctx, Env.CTXNAME_AD_Org_ID, org.getKey());
		Env.setContext(m_ctx, Env.CTXNAME_AD_Org_Name, org.getName());
		Ini.setProperty(Ini.P_ORG, org.getName());

		//
		// Role additional info
		final IUserRolePermissions userRolePermissions = Env.getUserRolePermissions(m_ctx);
		Env.setContext(m_ctx, Env.CTXNAME_User_Org, userRolePermissions.getAD_Org_IDs_AsString());

		// task 06009 if ShowWarehouseOnLogin is N pick the warehouse of the orgInfo (if it has one)
		if (isShowWarehouseOnLogin())
		{
			// Warehouse Info
			if (warehouse != null)
			{
				Env.setContext(m_ctx, Env.CTXNAME_M_Warehouse_ID, warehouse.getKey());
				Ini.setProperty(Ini.P_WAREHOUSE, warehouse.getName());
			}
		}
		else
		{
			final I_M_Warehouse orgWarehouse = warehouseDAO.retrieveOrgWarehouse(m_ctx, Integer.parseInt(org.getID()));
			if (orgWarehouse != null)
			{
				Env.setContext(m_ctx, Env.CTXNAME_M_Warehouse_ID, orgWarehouse.getM_Warehouse_ID());
				Ini.setProperty(Ini.P_WAREHOUSE, orgWarehouse.getName());
			}

		}

		//
		// Date (default today)
		final Timestamp today;
		if (timestamp == null)
		{
			today = SystemTime.asDayTimestamp();
		}
		else
		{
			today = TimeUtil.trunc(timestamp, TimeUtil.TRUNC_DAY);
		}
		Env.setContext(m_ctx, Env.CTXNAME_Date, today);

		// Optional Printer
		Env.setContext(m_ctx, Env.CTXNAME_Printer, printerName == null ? "" : printerName);
		Ini.setProperty(Ini.P_PRINTER, printerName);

		// Load Role Info
		// Env.resetUserRolePermissions(); // FIXME: not sure if this is neede, i guess not

		// Other
		Env.setAutoCommit(m_ctx, Ini.isPropertyBool(Ini.P_A_COMMIT));
		Env.setAutoNew(m_ctx, Ini.isPropertyBool(Ini.P_A_NEW));
		if (Services.get(IPostingService.class).isEnabled()
				&& userRolePermissions.hasPermission(IUserRolePermissions.PERMISSION_ShowAcct))
		{
			Env.setContext(m_ctx, Env.CTXNAME_ShowAcct, Ini.getProperty(Ini.P_SHOW_ACCT));
		}
		else
		{
			Env.setContext(m_ctx, Env.CTXNAME_ShowAcct, false);
		}
		Env.setContext(m_ctx, "#ShowTrl", Ini.getProperty(Ini.P_SHOW_TRL));
		Env.setContext(m_ctx, "#ShowAdvanced", Ini.getProperty(Ini.P_SHOW_ADVANCED));
		// Cash As Payment
		Env.setContext(m_ctx, "#CashAsPayment", sysConfigBL.getBooleanValue("CASH_AS_PAYMENT", true, Env.getAD_Client_ID(m_ctx)));

		String retValue = "";
		final int AD_Client_ID = Env.getContextAsInt(m_ctx, Env.CTXNAME_AD_Client_ID);
		final int AD_Org_ID = org.getKey();
		final int AD_Role_ID = Env.getContextAsInt(m_ctx, Env.CTXNAME_AD_Role_ID);

		// Other Settings
		Env.setContext(m_ctx, "#YYYY", "Y");
		Env.setContext(m_ctx, "#StdPrecision", 2);

		//
		// AccountSchema Info (first)
		int C_AcctSchema_ID = 0;
		try
		{
			final I_C_AcctSchema acctSchema = acctSchemaDAO.retrieveAcctSchema(m_ctx);
			C_AcctSchema_ID = acctSchema.getC_AcctSchema_ID();

			// Accounting Info
			Env.setContext(m_ctx, "$C_AcctSchema_ID", acctSchema.getC_AcctSchema_ID());
			Env.setContext(m_ctx, "$C_Currency_ID", acctSchema.getC_Currency_ID());
			Env.setContext(m_ctx, "$HasAlias", acctSchema.isHasAlias());
		}
		catch (AccountingException e)
		{
			// No Warning for System
			if (AD_Role_ID != Env.CTXVALUE_AD_Role_ID_System)
			{
				retValue = "NoValidAcctInfo";
			}
		}
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			// Define AcctSchema , Currency, HasAlias for Multi AcctSchema **/
			// Note: this might override the context values we set above. Leving that code untouched for now..
			final MAcctSchema[] ass = MAcctSchema.getClientAcctSchema(getCtx(), AD_Client_ID);
			if (ass != null && ass.length > 1)
			{
				for (final MAcctSchema as : ass)
				{
					C_AcctSchema_ID = clientDAO.retrieveClientInfo(getCtx(), AD_Client_ID).getC_AcctSchema1_ID();
					if (as.getAD_OrgOnly_ID() != 0)
					{
						if (as.isSkipOrg(AD_Org_ID))
						{
							continue;
						}
						else
						{
							C_AcctSchema_ID = as.getC_AcctSchema_ID();
							Env.setContext(m_ctx, "$C_AcctSchema_ID", C_AcctSchema_ID);
							Env.setContext(m_ctx, "$C_Currency_ID", as.getC_Currency_ID());
							Env.setContext(m_ctx, "$HasAlias", as.isHasAlias());
							break;
						}
					}
				}
			}

			// Accounting Elements
			final String sqlElementType = "SELECT ElementType "
					+ "FROM C_AcctSchema_Element "
					+ "WHERE C_AcctSchema_ID=?"
					+ " AND IsActive='Y'";
			pstmt = DB.prepareStatement(sqlElementType, null);
			pstmt.setInt(1, C_AcctSchema_ID);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				Env.setContext(m_ctx, "$Element_" + rs.getString("ElementType"), true);
			}
			DB.close(rs, pstmt);

			// This reads all relevant window neutral defaults
			// overwriting superseeded ones. Window specific is read in Mainain
			String sqlWindowDefaults = "SELECT Attribute, Value, AD_Window_ID "
					+ "FROM AD_Preference "
					+ "WHERE AD_Client_ID IN (0, @#AD_Client_ID@)"
					+ " AND AD_Org_ID IN (0, @#AD_Org_ID@)"
					+ " AND (AD_User_ID IS NULL OR AD_User_ID=0 OR AD_User_ID=@#AD_User_ID@)"
					+ " AND IsActive='Y' "
					+ "ORDER BY Attribute, AD_Client_ID, AD_User_ID DESC, AD_Org_ID";
			// the last one overwrites - System - Client - User - Org - Window
			sqlWindowDefaults = Env.parseContext(m_ctx, 0, sqlWindowDefaults, false);
			if (sqlWindowDefaults.length() == 0)
			{
				log.log(Level.SEVERE, "loadPreferences - Missing Environment");
			}
			else
			{
				pstmt = DB.prepareStatement(sqlWindowDefaults, ITrx.TRXNAME_None);
				rs = pstmt.executeQuery();
				while (rs.next())
				{
					final String preferenceName = rs.getString(1);
					final String preferenceValue = rs.getString(2);
					final int AD_Window_ID = rs.getInt(3);
					Env.setPreference(m_ctx, AD_Window_ID, preferenceName, preferenceValue);
				}
				DB.close(rs, pstmt);
			}

			// Default Values
			log.info("Default Values ...");
			final String sqlDefaulValues = "SELECT t.TableName, c.ColumnName "
					+ "FROM AD_Column c "
					+ " INNER JOIN AD_Table t ON (c.AD_Table_ID=t.AD_Table_ID) "
					+ "WHERE c.IsKey='Y' AND t.IsActive='Y'"
					+ " AND EXISTS (SELECT * FROM AD_Column cc "
					+ " WHERE ColumnName = 'IsDefault' AND t.AD_Table_ID=cc.AD_Table_ID AND cc.IsActive='Y')";
			pstmt = DB.prepareStatement(sqlDefaulValues, ITrx.TRXNAME_None);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				loadDefault(rs.getString(1), rs.getString(2));
			}
			DB.close(rs, pstmt);
			pstmt = null;
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, "loadPreferences", e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		//
		Ini.saveProperties();
		// Country
		Env.setContext(m_ctx, "#C_Country_ID", MCountry.getDefault(m_ctx).getC_Country_ID());
		//
		// metas: c.ghita@metas.ro : start : 01552
		if (sysConfigBL.getBooleanValue("CACHE_WINDOWS_DISABLED", true))
		{
			Ini.setProperty(Ini.P_CACHE_WINDOW, false);
		}
		// metas: c.ghita@metas.ro : end : 01552

		//
		// Set System Status Message
		// see http://dewiki908/mediawiki/index.php/05730_Use_different_Theme_colour_on_UAT_system
		// NOTE: we are retrieving from database and we are storing in context
		// because this String is used in low level UI components and in some cases there is no database connection at all
		{
			final String windowHeaderNotice = sysConfigBL.getValue(SYSCONFIG_UI_WindowHeader_Notice, AD_Client_ID);
			Env.setContext(m_ctx, Env.CTXNAME_UI_WindowHeader_Notice, windowHeaderNotice);
		}

		//
		// Call ModelValidators afterLoadPreferences - teo_sarca FR [ 1670025 ]
		ModelValidationEngine.get().afterLoadPreferences(m_ctx);
		return retValue;
	}	// loadPreferences

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

		String value = null;
		//
		String sql = "SELECT " + ColumnName + " FROM " + TableName	// most specific first
				+ " WHERE IsDefault='Y' AND IsActive='Y' ORDER BY AD_Client_ID DESC, AD_Org_ID DESC";
		sql = Env.getUserRolePermissions(m_ctx).addAccessSQL(sql, TableName, IUserRolePermissions.SQL_NOTQUALIFIED, IUserRolePermissions.SQL_RO);
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
			log.log(Level.SEVERE, TableName + " (" + sql + ")", e);
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
			if (TableName.equals("C_DocType"))
				Env.setContext(m_ctx, "#C_DocTypeTarget_ID", value);
			else
				Env.setContext(m_ctx, "#" + ColumnName, value);
		}
	}	// loadDefault

	private String m_remoteAddr = null;
	private String m_remoteHost = null;
	private String m_webSession = null;

	public void setRemoteAddr(String remoteAddr)
	{
		m_remoteAddr = remoteAddr;
	}

	public String getRemoteAddr()
	{
		return m_remoteAddr;
	}	// RemoteAddr

	public void setRemoteHost(String remoteHost)
	{
		m_remoteHost = remoteHost;
	}

	public String getRemoteHost()
	{
		return m_remoteHost;
	}	// RemoteHost

	public void setWebSession(String webSession)
	{
		m_webSession = webSession;
	}

	public String getWebSession()
	{
		return m_webSession;
	}	// WebSession

	public boolean isShowWarehouseOnLogin()
	{
		return sysConfigBL.getBooleanValue(SYSCONFIG_ShowWarehouseOnLogin, true, 0, 0);
	}
}	// Login
