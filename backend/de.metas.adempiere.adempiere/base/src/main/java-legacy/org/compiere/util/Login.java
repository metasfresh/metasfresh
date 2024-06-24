package org.compiere.util;

import com.google.common.collect.ImmutableSet;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.acct.api.IPostingService;
import de.metas.adempiere.service.IPrinterRoutingBL;
import de.metas.common.util.time.SystemTime;
import de.metas.location.ICountryDAO;
import de.metas.logging.LogManager;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.organization.OrgInfo;
import de.metas.security.IRoleDAO;
import de.metas.security.IUserRolePermissions;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.security.Role;
import de.metas.security.RoleId;
import de.metas.security.permissions.OrgResource;
import de.metas.user.UserId;
import de.metas.user.api.IUserBL;
import de.metas.user.api.IUserDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import de.metas.util.hash.HashableString;
import lombok.NonNull;
import org.adempiere.ad.service.ISystemBL;
import org.adempiere.ad.session.ISessionBL;
import org.adempiere.ad.session.MFSession;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.IClientDAO;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.service.IValuePreferenceBL;
import org.compiere.model.I_AD_User;
import org.compiere.model.ModelValidationEngine;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Set;

/**
 * Login Manager
 *
 * @author Jorg Janke
 * @author victor.perez@e-evolution.com, e-Evolution http://www.e-evolution.com
 * <li>Incorrect global Variable when you use multi Account Schema
 * http://sourceforge.net/tracker/?func=detail&atid=879335&aid=2531597&group_id=176962
 * @author teo.sarca@gmail.com
 * <li>BF [ 2867246 ] Do not show InTrazit WHs on login
 * https://sourceforge.net/tracker/?func=detail&aid=2867246&group_id=176962&atid=879332
 * @version $Id: Login.java,v 1.6 2006/10/02 05:19:06 jjanke Exp $
 */
public class Login
{
	/**
	 * Task http://dewiki908/mediawiki/index.php/05730_Use_different_Theme_colour_on_UAT_system
	 */
	private static final String SYSCONFIG_UI_WindowHeader_Notice_Text = "UI_WindowHeader_Notice_Text";

	/**
	 * Task https://metasfresh.atlassian.net/browse/FRESH-352
	 */
	private static final String SYSCONFIG_UI_WindowHeader_Notice_BG_Color = "UI_WindowHeader_Notice_BG_Color";

	/**
	 * Task https://metasfresh.atlassian.net/browse/FRESH-352
	 */
	private static final String SYSCONFIG_UI_WindowHeader_Notice_FG_Color = "UI_WindowHeader_Notice_FG_Color";

	// services
	private static final transient Logger log = LogManager.getLogger(Login.class);

	private final transient IUserRolePermissionsDAO userRolePermissionsDAO = Services.get(IUserRolePermissionsDAO.class);
	private final transient IUserDAO userDAO = Services.get(IUserDAO.class);
	private final transient IUserBL userBL = Services.get(IUserBL.class);
	private final transient IRoleDAO roleDAO = Services.get(IRoleDAO.class);
	private final transient IAcctSchemaDAO acctSchemaDAO = Services.get(IAcctSchemaDAO.class);
	private final transient IClientDAO clientDAO = Services.get(IClientDAO.class);
	private final transient IOrgDAO orgsRepo = Services.get(IOrgDAO.class);
	private final transient ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final transient ISessionBL sessionBL = Services.get(ISessionBL.class);
	private final transient ISystemBL systemBL = Services.get(ISystemBL.class);
	private final transient IPrinterRoutingBL printerRoutingBL = Services.get(IPrinterRoutingBL.class);
	private final transient ICountryDAO countriesRepo = Services.get(ICountryDAO.class);
	private final transient IPostingService postingService = Services.get(IPostingService.class);
	private final transient IValuePreferenceBL valuePreferenceBL = Services.get(IValuePreferenceBL.class);

	//
	// State
	private final LoginContext _loginContext;
	// NOTE: please avoid having other state variables. If needed, pls add them to LoginContext.

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
	public LoginAuthenticateResponse authenticate(@Nullable final String username, @Nullable final HashableString password)
	{
		if (username == null || Check.isBlank(username))
		{
			throw new AdempiereException("@UserOrPasswordInvalid@").markAsUserValidationError();
		}
		if (HashableString.isEmpty(password))
		{
			throw new AdempiereException("@UserOrPasswordInvalid@").markAsUserValidationError();
		}

		final LoginContext ctx = getCtx();

		//
		// If not authenticated so far, use AD_User as backup
		//
		final int maxLoginFailure = sysConfigBL.getIntValue("ZK_LOGIN_FAILURES_MAX", 3);
		final int accountLockExpire = sysConfigBL.getIntValue("USERACCOUNT_LOCK_EXPIRE", 30);
		final MFSession session = startSession();
		session.setLoginUsername(username);

		final I_AD_User user = userDAO.retrieveLoginUserByUserId(username);
		int loginFailureCount = user.getLoginFailureCount();

		// metas: us902: Update logged in user if not equal.
		// Because we are creating the session before we know which user will be logged, initially
		// the AD_Session.CreatedBy is zero.
		final UserId userId = UserId.ofRepoId(user.getAD_User_ID());
		session.setAD_User_ID(userId.getRepoId());

		final boolean isAccountLocked = user.isAccountLocked();
		if (isAccountLocked)
		{
			final Timestamp curentLogin = (new Timestamp(System.currentTimeMillis()));
			final long loginFailureTime = user.getLoginFailureDate().getTime();
			final long newloginFailureTime = loginFailureTime + (1000L * 60 * accountLockExpire);
			final Timestamp acountUnlock = new Timestamp(newloginFailureTime);
			if (curentLogin.compareTo(acountUnlock) > 0)
			{
				user.setLoginFailureCount(0);
				user.setIsAccountLocked(false);
				InterfaceWrapperHelper.save(user);
			}
			else
			{
				throw new AdempiereException("@UserAccountLockedError@").markAsUserValidationError(); // TODO: specific exception
			}
		}

		if (!userBL.isPasswordMatching(user, password))
		{
			loginFailureCount++;
			user.setLoginFailureCount(loginFailureCount);
			user.setLoginFailureDate(de.metas.common.util.time.SystemTime.asTimestamp());
			if (user.getLoginFailureCount() >= maxLoginFailure)
			{
				user.setIsAccountLocked(true);
				user.setLockedFromIP(session.getRemote_Addr());
				InterfaceWrapperHelper.save(user);

				destroySessionOnLoginIncorrect(session);
				throw new AdempiereException("@UserAccountLockedError@").markAsUserValidationError(); // TODO: specific exception
			}

			InterfaceWrapperHelper.save(user);

			destroySessionOnLoginIncorrect(session);
			throw new AdempiereException("@UserOrPasswordInvalid@").markAsUserValidationError();
		}
		else
		{
			user.setLoginFailureCount(0);
			user.setIsAccountLocked(false);
			InterfaceWrapperHelper.save(user);
		}

		ctx.setUser(userId, username);

		//
		if (Ini.isSwingClient())
		{
			if (systemBL.isRememberUserAllowed("SWING_LOGIN_ALLOW_REMEMBER_ME"))
			{
				Ini.setProperty(Ini.P_UID, username);
			}
			else
			{
				Ini.setProperty(Ini.P_UID, "");
			}

			if (Ini.isPropertyBool(Ini.P_STORE_PWD)
					&& systemBL.isRememberPasswordAllowed("SWING_LOGIN_ALLOW_REMEMBER_ME")
					&& password.isPlain())
			{
				Ini.setProperty(Ini.P_PWD, password.getValue());
			}
		}

		//
		// Use user's AD_Language, if any
		StringUtils.trimBlankToOptional(user.getAD_Language())
				.map(Env::verifyLanguageFallbackToBase)
				.ifPresent(ctx::setAD_Language);

		//
		// Get user's roles
		final ArrayList<Role> roles = new ArrayList<>();
		for (final Role role : roleDAO.getUserRoles(userId))
		{
			// if webui then skip non-webui roles
			if (ctx.isWebui() && !role.isWebuiRole())
			{
				continue;
			}

			if (role.getId().isSystem())
			{
				ctx.setSysAdmin(true);
			}

			roles.add(role);
		}
		//
		if (roles.isEmpty())
		{
			throw new AdempiereException("@NoRoles@").markAsUserValidationError(); // TODO: specific exception
		}

		log.debug("User={}, roles={}", username, roles);
		return LoginAuthenticateResponse.builder()
				.userId(userId)
				.availableRoles(roles)
				.build();
	}

	private MFSession startSession()
	{
		final LoginContext ctx = getCtx();

		final MFSession session = sessionBL.getCurrentOrCreateNewSession(ctx.getSessionContext());
		final String remoteAddr = getRemoteAddr();
		if (remoteAddr != null)
		{
			session.setRemote_Addr(remoteAddr, getRemoteHost());
			session.setWebSessionId(getWebSessionId());
		}

		return session;
	}

	private void destroySessionOnLoginIncorrect(final MFSession session)
	{
		session.setLoginIncorrect();
		sessionBL.logoutCurrentSession();

		getCtx().resetAD_Session_ID();
	}

	/**
	 * Sets current role and retrieves clients on which given role can login
	 *
	 * @return list of valid client {@link KeyNamePair}s or null if in error
	 */
	public Set<ClientId> setRoleAndGetClients(@NonNull final RoleId roleId)
	{
		//
		// Get user role
		final LoginContext ctx = getCtx();
		final UserId userId = ctx.getUserId();
		final IUserRolePermissions rolePermissions = getUserRolePermissions(roleId, userId);

		//
		// Get login AD_Clients
		final Set<ClientId> clientIds = rolePermissions.getLoginClientIds();
		if (clientIds.isEmpty())
		{
			// shall not happen because in this case rolePermissions retrieving should fail
			throw new AdempiereException("No Clients for Role: " + roleId).markAsUserValidationError();
		}

		//
		// Update login context
		ctx.setRole(rolePermissions.getRoleId(), rolePermissions.getName());
		ctx.setRoleUserLevel(rolePermissions.getUserLevel());
		ctx.setAllowLoginDateOverride(rolePermissions.hasPermission(IUserRolePermissions.PERMISSION_AllowLoginDateOverride));

		return clientIds;
	}

	private IUserRolePermissions getUserRolePermissions(final RoleId roleId, final UserId userId)
	{
		//
		// Get user role
		final ClientId clientId = null; // N/A
		final LocalDate loginDate = SystemTime.asLocalDate(); // NOTE: to avoid hysteresis of Role->Date->Role, we always use system time
		return userRolePermissionsDAO.getUserRolePermissions(roleId, userId, clientId, loginDate);
	}

	public Set<ClientId> getAvailableClients(final RoleId roleId, final UserId userId)
	{
		return getUserRolePermissions(roleId, userId).getLoginClientIds();
	}

	/**
	 * Sets current client in context and retrieves organizations on which we can login
	 *
	 * @return list of valid organizations; never returns null
	 */
	public ImmutableSet<OrgId> setClientAndGetOrgs(@NonNull final ClientId clientId)
	{
		final String clientName = clientDAO.getClientNameById(clientId);
		return setClientAndGetOrgs(clientId, clientName);
	}

	public ImmutableSet<OrgId> setClientAndGetOrgs(final ClientId clientId, final String clientName)
	{
		if (clientId == null)
		{
			throw new IllegalArgumentException("Client missing");
		}

		//
		// Get login organizations
		final LoginContext ctx = getCtx();
		final RoleId roleId = ctx.getRoleId();
		final UserId userId = ctx.getUserId();
		final ImmutableSet<OrgId> orgIds = getAvailableOrgs(roleId, userId, clientId);

		//
		// Update login context
		ctx.setClient(clientId, clientName);

		return orgIds;
	}

	public ImmutableSet<OrgId> getAvailableOrgs(
			@NonNull final RoleId roleId,
			@NonNull final UserId userId,
			@NonNull final ClientId clientId)
	{
		//
		// Get user role
		final LocalDate loginDate = de.metas.common.util.time.SystemTime.asLocalDate(); // NOTE: to avoid hysteresis of Role->Date->Role, we always use system time
		final IUserRolePermissions role = userRolePermissionsDAO.getUserRolePermissions(roleId, userId, clientId, loginDate);

		//
		// Get login organizations
		final ImmutableSet<OrgId> orgIds = role.getLoginOrgs()
				.stream()
				.map(OrgResource::getOrgIdOrAny)
				.collect(ImmutableSet.toImmutableSet());
		// No Orgs
		if (orgIds.isEmpty())
		{
			log.warn("No Org for AD_Client_ID={}, AD_Role_ID={}, AD_User_ID={} \nPermissions: {}", clientId, roleId, userId, role);
			return ImmutableSet.of();
		}

		return orgIds;
	}

	/**
	 * Validate Login
	 *
	 * @return error message
	 */
	@Nullable
	public String validateLogin(@NonNull final OrgId orgId)
	{
		final boolean fireLoginComplete = true;
		return validateLogin(orgId, fireLoginComplete);
	}

	@Nullable
	public String validateLogin(@NonNull final OrgId orgId, final boolean fireLoginComplete)
	{
		final LoginContext ctx = getCtx();
		final ClientId clientId = ctx.getClientId();
		final RoleId roleId = ctx.getRoleId();
		final UserId userId = ctx.getUserId();

		//
		// Update AD_Session
		final MFSession session = sessionBL.getCurrentSession(ctx.getSessionContext());
		if (session != null)
		{
			session.setLoginInfo(clientId, orgId, roleId, userId, ctx.getLoginDate());
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
			final String error = ModelValidationEngine.get().loginComplete(clientId.getRepoId(), orgId.getRepoId(), roleId.getRepoId(), userId.getRepoId());
			if (error != null && error.length() > 0)
			{
				log.error("Refused: " + error);
				return error;
			}
		}

		return null;
	}    // validateLogin

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
	 * @return AD_Message of error (NoValidAcctInfo) or ""
	 */
	public String loadPreferences(
			@NonNull final OrgId orgId,
			@Nullable final java.sql.Timestamp timestamp)
	{
		final LoginContext ctx = getCtx();
		final ClientId clientId = ctx.getClientId();
		final UserId userId = ctx.getUserId();
		final RoleId roleId = ctx.getRoleId();

		//
		// Org Info - assumes that it is valid
		{
			final String orgName = orgsRepo.getOrgName(orgId);
			ctx.setOrg(orgId, orgName);

			final OrgInfo orgInfo = orgsRepo.getOrgInfoById(orgId);
			ctx.setProperty(Env.CTXNAME_StoreCreditCardData, orgInfo.getStoreCreditCardNumberMode().getCode());
			ctx.setProperty(Env.CTXNAME_TimeZone, orgInfo.getTimeZone() != null ? orgInfo.getTimeZone().getId() : null);
		}

		//
		// Date (default today)
		final LocalDate loginDate;
		if (timestamp == null)
		{
			loginDate = de.metas.common.util.time.SystemTime.asLocalDate();
		}
		else
		{
			loginDate = TimeUtil.asLocalDate(timestamp);
		}
		ctx.setLoginDate(loginDate);

		//
		// Role additional info
		final IUserRolePermissions userRolePermissions = userRolePermissionsDAO.getUserRolePermissions(roleId, userId, clientId, loginDate);
		ctx.setUserOrgs(userRolePermissions.getAD_Org_IDs_AsString());

		// Other
		ctx.setPrinterName(printerRoutingBL.getDefaultPrinterName()); // Optional Printer
		ctx.setAutoCommit(Ini.isPropertyBool(Ini.P_A_COMMIT));
		ctx.setAutoNew(Ini.isPropertyBool(Ini.P_A_NEW));
		ctx.setProperty(Env.CTXNAME_ShowAcct, postingService.isEnabled()
				&& userRolePermissions.hasPermission(IUserRolePermissions.PERMISSION_ShowAcct)
				&& Ini.isPropertyBool(Ini.P_SHOW_ACCT));
		ctx.setProperty("#ShowTrl", Ini.isPropertyBool(Ini.P_SHOW_TRL));
		ctx.setProperty("#ShowAdvanced", Ini.isPropertyBool(Ini.P_SHOW_ADVANCED));

		ctx.setProperty("#CashAsPayment", sysConfigBL.getBooleanValue("CASH_AS_PAYMENT", true, clientId.getRepoId()));

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
		catch (final Exception ex)
		{
			log.warn("Failed loading accounting info", ex);
			retValue = "NoValidAcctInfo";
		}

		try
		{
			//
			// Load preferences
			loadPreferences();
		}
		catch (final Exception e)
		{
			log.warn("Failed loading preferences. Skipping.", e);
		}

		//
		// Save Ini properties
		if (Ini.isSwingClient())
		{
			Ini.saveProperties();
		}

		//
		// Country
		ctx.setProperty("#C_Country_ID", countriesRepo.getDefault(ctx.getSessionContext()).getC_Country_ID());

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
	}    // loadPreferences

	/**
	 * Loads accounting info
	 */
	private void loadAccounting()
	{
		final LoginContext ctx = getCtx();
		final RoleId roleId = ctx.getRoleId();

		// Don't try to load the accounting schema for System role
		if (roleId.isSystem())
		{
			return;
		}

		final AcctSchema acctSchema = acctSchemaDAO.getByClientAndOrg(ctx.getSessionContext()); // could throw AccountingException
		ctx.setAcctSchema(acctSchema);

		acctSchema.getSchemaElementTypes()
				.forEach(elementType -> ctx.setProperty(Env.CTXNAME_AcctSchemaElementPrefix + elementType.getCode(), true));
	}

	private void loadPreferences()
	{
		final LoginContext ctx = getCtx();
		final ClientId clientId = ctx.getClientId();
		final OrgId orgId = ctx.getOrgId();
		final UserId userId = ctx.getUserId();

		valuePreferenceBL
				.getAllWindowPreferences(clientId.getRepoId(), orgId.getRepoId(), userId.getRepoId())
				.stream()
				.flatMap(userValuePreferences -> userValuePreferences.values().stream())
				.forEach(ctx::setPreference);
	}

	/**
	 * Set System Status Message.
	 * <p>
	 * See http://dewiki908/mediawiki/index.php/05730_Use_different_Theme_colour_on_UAT_system.
	 * <p>
	 * NOTE: we are retrieving from database and we are storing in context because this String is used in low level UI components and in some cases there is no database connection at all
	 */
	private void loadUIWindowHeaderNotice()
	{
		final LoginContext ctx = getCtx();
		final ClientId clientId = ctx.getClientId();
		final OrgId orgId = ctx.getOrgId();

		final String windowHeaderNoticeText = sysConfigBL.getValue(SYSCONFIG_UI_WindowHeader_Notice_Text, clientId.getRepoId(), orgId.getRepoId());
		ctx.setProperty(Env.CTXNAME_UI_WindowHeader_Notice_Text, windowHeaderNoticeText);

		// FRESH-352: also allow setting the status message's foreground and background color.
		final String windowHeaderBackgroundColor = sysConfigBL.getValue(SYSCONFIG_UI_WindowHeader_Notice_BG_Color, clientId.getRepoId(), orgId.getRepoId());
		ctx.setProperty(Env.CTXNAME_UI_WindowHeader_Notice_BG_COLOR, windowHeaderBackgroundColor);

		final String windowHeaderForegroundColor = sysConfigBL.getValue(SYSCONFIG_UI_WindowHeader_Notice_FG_Color, clientId.getRepoId(), orgId.getRepoId());
		ctx.setProperty(Env.CTXNAME_UI_WindowHeader_Notice_FG_COLOR, windowHeaderForegroundColor);
	}

	public void setRemoteAddr(final String remoteAddr)
	{
		getCtx().setRemoteAddr(remoteAddr);
	}

	public String getRemoteAddr()
	{
		return getCtx().getRemoteAddr();
	}    // RemoteAddr

	public void setRemoteHost(final String remoteHost)
	{
		getCtx().setRemoteHost(remoteHost);
	}

	public String getRemoteHost()
	{
		return getCtx().getRemoteHost();
	}    // RemoteHost

	public void setWebSessionId(final String webSessionId)
	{
		getCtx().setWebSessionId(webSessionId);
	}

	public String getWebSessionId()
	{
		return getCtx().getWebSessionId();
	}

	public boolean isAllowLoginDateOverride()
	{
		return getCtx().isAllowLoginDateOverride();
	}
}    // Login
