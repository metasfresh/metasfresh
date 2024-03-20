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

import com.google.common.base.Supplier;
import de.metas.adempiere.form.IClientUI;
import de.metas.adempiere.model.I_AD_Role;
import de.metas.cache.CacheMgt;
import de.metas.common.util.time.SystemTime;
import de.metas.i18n.ADLanguageList;
import de.metas.i18n.ILanguageDAO;
import de.metas.i18n.Language;
import de.metas.logging.LogManager;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.security.IUserRolePermissions;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.security.RoleId;
import de.metas.security.UserRolePermissionsKey;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.expression.api.IExpressionFactory;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.session.ISessionBL;
import org.adempiere.context.ContextProvider;
import org.adempiere.context.ThreadLocalContextProvider;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.service.IClientDAO;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.service.IValuePreferenceBL.IUserValuePreference;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.db.CConnection;
import org.compiere.model.MLanguage;
import org.compiere.swing.CFrame;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.function.Predicate;

/**
 * System Environment and static variables.
 *
 * @author Jorg Janke
 * @author Teo Sarca, www.arhipac.ro
 * <ul>
 * <li>BF [ 1619390 ] Use default desktop browser as external browser
 * <li>BF [ 2017987 ] Env.getContext(TAB_INFO) should NOT use global context
 * <li>FR [ 2392044 ] Introduce Env.WINDOW_MAIN
 * </ul>
 * @version $Id: Env.java,v 1.3 2006/07/30 00:54:36 jjanke Exp $
 */
public final class Env
{
	/**
	 * Logging
	 */
	private static final Logger s_log = LogManager.getLogger(Env.class);

	/**
	 * This field is volatile because i encountered occasional NPEs in {@link #getCtx()} during adempiere startup and i suspect it'S related to multiple parts of adempiere starting concurrently. To
	 * see why I hope that 'volatile' will help, you could start with this link: http://stackoverflow.com/questions/4934913/are-static-variables-shared-between-threads
	 * <p>
	 * NOTE: we need to set it to a default value because else all other helpers like GenerateModels will fail or they need to be changes to setup a context provider.
	 */
	// private static volatile ContextProvider contextProvider = new DefaultContextProvider();
	private static volatile ContextProvider contextProvider = new ThreadLocalContextProvider();

	public static void setContextProvider(@NonNull final ContextProvider provider)
	{
		final ContextProvider providerOld = contextProvider;
		contextProvider = provider;
		s_log.info("Changed context provider: {} -> {}", providerOld, contextProvider);

		// metas: 03362: Configure context's language only if we have a database connection
		if (DB.isConnected())
		{
			MLanguage.setBaseLanguage();
			if (getAD_Language(getCtx()) == null)
			{
				setContext(getCtx(), CTXNAME_AD_Language, Language.getBaseAD_Language());
			}
		}
	}

	/**
	 * Initializes the context provider if necessary. Multiple calls shall be no problem.
	 * See ThreadLocalContextProvider#init(), because currently that is the only implementation which actually does something
	 *
	 * task 08859
	 */
	public static void initContextProvider()
	{
		if (contextProvider == null)
		{
			return; // should not happen
		}
		contextProvider.init();
	}

	/**
	 * Exit System
	 *
	 * @param status System exit status (usually 0 for no error)
	 */
	public static void exitEnv(final int status)
	{
		s_log.info("Exiting environment with status={}", status);

		// hengsin, avoid unncessary query of session when exit without log in
		if (DB.isConnected())
		{
			// End Session
			Services.get(ISessionBL.class).logoutCurrentSession();
		}
		//
		reset(true);    // final cache reset
		//
		LogManager.shutdown();
		//

		final ApplicationContext springApplicationContext = SpringContextHolder.instance.getApplicationContext();
		if (springApplicationContext != null) // don't fail if we exit before swing-client's login was done
		{
			SpringApplication.exit(springApplicationContext, () -> 0);
		}

		// should not be required anymore since we make sure that all non-demon threads are stopped
		// works in my debugging-session (without system.exit), but doesn't (always!) works on lx-term01 (x2go)
		if (Ini.isSwingClient())
		{
			System.exit(status);
		}
	}    // close

	/**
	 * Reset Cache
	 *
	 * @param finalCall everything otherwise login data remains
	 */
	public static void reset(final boolean finalCall)
	{
		s_log.info("Reseting environment (finalCall={})", finalCall);
		if (Ini.isSwingClient())
		{
			final boolean preserveMainWindow = !finalCall;
			windows.closeAll(preserveMainWindow);
		}

		// Clear all Context
		final Properties ctx = getCtx();
		if (finalCall)
		{
			clearContext();
		}
		else
		// clear window context only
		{
			removeContextMatching(ctx, CTXNAME_MATCHER_AnyWindow);
		}

		// Cache
		CacheMgt.get().reset();

		// Close datasource(s)
		if (Ini.isSwingClient())
		{
			DB.closeTarget();
		}

		// Reset Role Access
		if (!finalCall)
		{
			if (Ini.isSwingClient())
			{
				DB.setDBTarget(CConnection.get());
			}

			// NOTE: there is no need to reset the role because cache was reset
			// MRole defaultRole = MRole.getDefault(ctx, false);
			// if (defaultRole != null)
			// defaultRole.loadAccess(true); // Reload
		}
	}    // resetAll

	/**************************************************************************
	 * Application Context
	 */
	/**
	 * WindowNo for Main
	 */
	public static final int WINDOW_MAIN = 1; // note: 0 is the ALogin window that's shown in the swing client on startup
	/**
	 * WindowNo for Find
	 */
	public static final int WINDOW_FIND = 1110;
	/**
	 * WindowNo for PrintCustomize
	 */
	public static final int WINDOW_CUSTOMIZE = 1112;
	/**
	 * WindowNo for PrintCustomize
	 */
	public static final int WINDOW_INFO = 1113;

	/**
	 * Tab for Info
	 */
	public static final int TAB_INFO = 1113;

	public static final String CTXNAME_AD_Client_ID = "#AD_Client_ID";
	public static final String CTXNAME_AD_Client_Name = "#AD_Client_Name";
	public static final int CTXVALUE_AD_Client_ID_System = ClientId.SYSTEM.getRepoId();

	public static final String CTXNAME_AD_Org_ID = "#AD_Org_ID";
	public static final String CTXNAME_AD_Org_Name = "#AD_Org_Name";
	/**
	 * System AD_Org_ID.
	 * i.e. the AD_Org_ID used to store records which don't belong to a particular organization.
	 */
	public static final int CTXVALUE_AD_Org_ID_System = 0;
	public static final int CTXVALUE_AD_Org_ID_Any = 0;

	public static final String CTXNAME_AD_Role_ID = "#AD_Role_ID";
	public static final int CTXVALUE_AD_Role_ID_NONE = -1;
	public static final int CTXVALUE_AD_Role_ID_System = RoleId.SYSTEM.getRepoId();
	public static final String CTXNAME_AD_Role_Name = "#AD_Role_Name";
	public static final String CTXNAME_AD_Role_UserLevel = "#User_Level";
	public static final String CTXNAME_AD_PInstance_ID = "#AD_PInstance_ID"; // FRESH-314

	/**
	 * Comma separated list of AD_Org_IDs of which current User/Role has any access (ro/rw)
	 * <p>
	 * NOTE: this is deprecated but we are keeping it for those application dictionary logics which require this information from context.
	 */
	public static final String CTXNAME_User_Org = "#User_Org";

	public static final String CTXNAME_AD_User_ID = "#AD_User_ID";
	public static final String CTXNAME_AD_User_Name = "#AD_User_Name";
	public static final String CTXNAME_SalesRep_ID = "#SalesRep_ID";
	public static final int CTXVALUE_AD_User_ID_System = UserId.SYSTEM.getRepoId();

	public static final String CTXNAME_Date = "#Date";
	public static final String CTXNAME_IsAllowLoginDateOverride = "#" + I_AD_Role.COLUMNNAME_IsAllowLoginDateOverride;
	public static final String CTXNAME_TimeZone = "#TimeZone";

	public static final String CTXNAME_AD_Session_ID = "#AD_Session_ID";
	public static final int CTXVALUE_AD_SESSION_ID_NONE = -1;

	public static final String CTXNAME_M_Warehouse_ID = "#M_Warehouse_ID";
	public static final String CTXNAME_AD_Language = "#AD_Language";
	public static final String CTXNAME_AutoNew = "AutoNew";
	public static final String CTXNAME_AutoCommit = "AutoCommit";
	public static final String CTXNAME_IsSOTrx = "IsSOTrx";
	public static final String CTXNAME_WindowName = "WindowName";
	public static final String CTXNAME_Printer = "#Printer";
	public static final String CTXNAME_ShowAcct = "#ShowAcct";
	public static final String CTXNAME_AcctSchemaElementPrefix = "$Element_";
	public static final String CTXNAME_StoreCreditCardData = "#StoreCreditCardData";

	public static final String CTXNAME_PROCESS_SELECTION_WHERECLAUSE = "SELECTION_WHERECLAUSE";

	/**
	 * task http://dewiki908/mediawiki/index.php/05730_Use_different_Theme_colour_on_UAT_system. The value is loaded into the context on login.
	 */
	public static final String CTXNAME_UI_WindowHeader_Notice_Text = "#UI_WindowHeader_Notice_Text";

	/**
	 * Background color of the optional window header notice. The value is loaded into the context on login.
	 * <p>
	 * task https://metasfresh.atlassian.net/browse/FRESH-352
	 */
	public static final String CTXNAME_UI_WindowHeader_Notice_BG_COLOR = "#UI_WindowHeader_Notice_BG_Color";

	/**
	 * Foreground color of the optional window header notice. The value is loaded into the context on login.
	 * <p>
	 * task https://metasfresh.atlassian.net/browse/FRESH-352
	 */
	public static final String CTXNAME_UI_WindowHeader_Notice_FG_COLOR = "#UI_WindowHeader_Notice_FG_Color";

	/**
	 * To be used when setting the windowNo in context
	 */
	public static final String DYNATTR_WindowNo = "WindowNo";

	/**
	 * To be used when setting the tabNo in context
	 */
	public static final String DYNATTR_TabNo = "TabNo";

	/**
	 * To be used when setting the current user ID in context
	 */
	public static final String DYNATTR_AD_User_ID = "AD_User_ID";

	/**
	 * Matches any key which is about window context (i.e. starts with "WindowNo|").
	 */
	private static final Predicate<Object> CTXNAME_MATCHER_AnyWindow = key -> {
		final String tag = key.toString();

		// NOTE: we kept the old logic which considered enough to check if the key starts with a digit
		final boolean matched = Character.isDigit(tag.charAt(0));
		return matched;
	};

	private static final String DATE_PATTEN = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
	public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern(DATE_PATTEN);

	/**
	 * Get Context
	 *
	 * @return Properties
	 */
	public static Properties getCtx()
	{
		return contextProvider.getContext();
	}   // getCtx

	/**
	 * Creates and returns a new context instance which does not inherit current context and which shall be used temporary.
	 *
	 * @return new context to be used temporary
	 */
	public static Properties newTemporaryCtx()
	{
		return new Properties();
	}

	/**
	 * Creates and returns a new empty context which fallbacks to given <code>ctx</code>.
	 * <p>
	 * <b>IMPORTANT:</b> do not use this method if you want to do use the resulting context as parameter for {@link #switchContext(Properties)}, to avoid a {@link StackOverflowError}. Use {@link #copyCtx(Properties)} instead.
	 *
	 * @return new context
	 */
	public static Properties deriveCtx(@NonNull final Properties ctx)
	{
		return new Properties(ctx);
	}

	/**
	 * Creates a new empty context and then copies all values from the original, <b>including</b> defaults.
	 * <p>
	 * <b>IMPORTANT:</b> use this method instead of {@link #deriveCtx(Properties)} if you plan to call {@link #switchContext(Properties)} with the result.
	 */
	public static Properties copyCtx(@NonNull final Properties ctx)
	{
		final Properties newCtx = new Properties();

		// we can't use this great tool, because it (reasonably) assumes that the given ctx does not have null values
		// org.springframework.util.CollectionUtils.mergePropertiesIntoMap(ctx, newCtx);

		for (final Enumeration<?> en = ctx.propertyNames(); en.hasMoreElements(); )
		{
			final String key = (String)en.nextElement();
			Object value = ctx.get(key);
			if (value == null)
			{
				// Allow for defaults fallback or potentially overridden accessor
				value = newCtx.getProperty(key);
			}
			if (value == null)
			{
				continue; // the given ctx might have null values, so this check is crucial
			}
			newCtx.put(key, value);
		}

		return newCtx;
	}

	/**
	 * Creates a special ctx that can be used to create system-level records.
	 *
	 * @param ctx the context that will be the base for the system context.
	 * @return new temporary context
	 */
	public static Properties createSysContext(@NonNull final Properties ctx)
	{
		final Properties sysCtx = Env.newTemporaryCtx();
		Env.setContext(sysCtx, CTXNAME_AD_Client_ID, 0);
		Env.setContext(sysCtx, CTXNAME_AD_Org_ID, 0);
		Env.setContext(sysCtx, CTXNAME_AD_Role_ID, 0);
		Env.setContext(sysCtx, CTXNAME_AD_User_ID, 0);
		Env.setContext(sysCtx, CTXNAME_AD_Language, Env.getAD_Language(ctx));
		Env.setContext(sysCtx, CTXNAME_AD_Session_ID, Env.getAD_Session_ID(ctx));
		Env.setContext(sysCtx, CTXNAME_Date, Env.getDate(ctx));

		return sysCtx;
	}

	/**
	 * Can be used before calling {@link #getCtx()} during startup, to avoid {@link NullPointerException}s.
	 */
	public static boolean isCtxAvailable()
	{
		return contextProvider != null && contextProvider.getContext() != null;
	}   // getCtx

	/**
	 * Temporary replace current context with the given one.
	 * <p>
	 * This method will return an {@link IAutoCloseable} to be used in try-with-resources and which will restore the context back.
	 * <p>
	 * <b>IMPORTANT:</b> do not use this method with a ctx that was created via {@link #deriveCtx(Properties)}, to avoid a {@link StackOverflowError}.<br>
	 * Use {@link #copyCtx(Properties)} instead.
	 *
	 * @return auto-closeable used to put back the original context
	 */
	public static IAutoCloseable switchContext(final Properties ctx)
	{
		return contextProvider.switchContext(ctx);
	}

	private static void clearContext()
	{
		if (contextProvider != null)
		{
			contextProvider.reset();
		}
	}

	/**
	 * Removes given key from context.
	 *
	 * @param ctx context
	 * @param key context key (property name)
	 */
	public static void removeContext(final Properties ctx, final String key)
	{
		// NOTE: because the "ctx" it might have underlying "defaults" we cannot just remove it,
		// because it might be that another value is set on parent level (or parent of the parent).
		// Also, setting it to "null" won't help because Properties interprets this as not existing value and will fallback to "defaults".

		// ctx.remove(key);
		final String nullValue = getNullPropertyValue(key);
		ctx.setProperty(key, nullValue);

		s_log.trace("Unset {}=={}", key, nullValue);
	}

	private static void setProperty(@NonNull final Properties ctx, final String key, final String value)
	{
		ctx.setProperty(key, value);

		s_log.trace("Set {}=={}", key, value);
	}

	private static void removeContextForPrefix(final Properties ctx, final String keyPrefix)
	{
		removeContextMatching(ctx, key -> {
			final String tag = key.toString();
			return tag.startsWith(keyPrefix);
		});
	}

	/**
	 * Remove context variables of which the key is matched by given <code>keyMatcher</code>.
	 */
	public static void removeContextMatching(final Properties ctx, final Predicate<Object> keyMatcher)
	{
		final Set<String> keys = ctx.stringPropertyNames();
		for (final String key : keys)
		{
			// Skip keys which are not matching
			if (!keyMatcher.test(key))
			{
				continue;
			}

			ctx.remove(key);

			// If the key we just removed exists on underlying "defaults", we are just setting it here using null marker.
			if (getProperty(ctx, key) != null)
			{
				final String nullValue = getNullPropertyValue(key);
				ctx.setProperty(key, nullValue);
			}
		}
	}

	/**
	 * Set Global Context to Value
	 *
	 * @param ctx     context
	 * @param context context key
	 * @param value   context value
	 */
	public static void setContext(final Properties ctx, final String context, final String value)
	{
		if (ctx == null || context == null)
		{
			return;
		}
		//
		if (value == null || value.length() == 0)
		{
			removeContext(ctx, context);
		}
		else
		{
			setProperty(ctx, context, value);
		}
	}    // setContext

	/**
	 * Set Global Context to Value
	 *
	 * @param ctx     context
	 * @param context context key
	 * @param value   context value
	 */
	public static void setContext(final Properties ctx, final String context, final Date value)
	{
		if (ctx == null || context == null)
		{
			return;
		}

		if (value == null)
		{
			removeContext(ctx, context);
		}
		else
		{
			final String stringValue = toString(value);
			setProperty(ctx, context, stringValue);
		}
	}    // setContext

	/**
	 * Set Global Context to (int) Value
	 *
	 * @param ctx     context
	 * @param context context key
	 * @param value   context value
	 */
	public static void setContext(final Properties ctx, final String context, final int value)
	{
		if (ctx == null || context == null)
		{
			return;
		}

		setProperty(ctx, context, String.valueOf(value));
	}    // setContext

	/**
	 * Set Global Context to Y/N Value
	 *
	 * @param ctx     context
	 * @param context context key
	 * @param value   context value
	 */
	public static void setContext(final Properties ctx, final String context, final boolean value)
	{
		setContext(ctx, context, toString(value));
	}    // setContext

	/**
	 * Set Context for Window to Value
	 *
	 * @param ctx      context
	 * @param WindowNo window no
	 * @param context  context key
	 * @param value    context value
	 */
	public static void setContext(final Properties ctx, final int WindowNo, final String context, final String value)
	{
		if (ctx == null || context == null)
		{
			return;
		}

		final String propertyName = WindowNo + "|" + context;
		if (isPropertyValueNull(value) || "".equals(value))
		{
			removeContext(ctx, propertyName);
		}
		else
		{
			setProperty(ctx, propertyName, value);
		}
	}    // setContext

	/**
	 * Set Context for Window to Value
	 *
	 * @param ctx      context
	 * @param WindowNo window no
	 * @param context  context key
	 * @param value    context value
	 */
	public static void setContext(final Properties ctx, final int WindowNo, final String context, final Date value)
	{
		if (ctx == null || context == null)
		{
			return;
		}

		final String propertyName = WindowNo + "|" + context;
		if (value == null)
		{
			removeContext(ctx, propertyName);
		}
		else
		{
			final String valueStr = toString(value);
			setProperty(ctx, propertyName, valueStr);
		}
	}    // setContext

	/**
	 * Set Context for Window to int Value
	 *
	 * @param ctx      context
	 * @param WindowNo window no
	 * @param context  context key
	 * @param value    context value
	 */
	public static void setContext(final Properties ctx, final int WindowNo, final String context, final int value)
	{
		if (ctx == null || context == null)
		{
			return;
		}

		final String propertyName = WindowNo + "|" + context;
		setProperty(ctx, propertyName, String.valueOf(value));
	}    // setContext

	/**
	 * Set Context for Window to Y/N Value
	 *
	 * @param ctx      context
	 * @param WindowNo window no
	 * @param context  context key
	 * @param value    context value
	 */
	public static void setContext(final Properties ctx, final int WindowNo, final String context, final boolean value)
	{
		setContext(ctx, WindowNo, context, toString(value));
	}    // setContext

	/**
	 * Set Context for Window & Tab to Value
	 *
	 * @param ctx      context
	 * @param WindowNo window no
	 * @param TabNo    tab no
	 * @param context  context key
	 * @param value    context value
	 */
	public static void setContext(final Properties ctx, final int WindowNo, final int TabNo, final String context, final String value)
	{
		if (ctx == null || context == null)
		{
			return;
		}

		final String propertyName = WindowNo + "|" + TabNo + "|" + context;
		if (value == null)
		{
			removeContext(ctx, propertyName);
		}
		else
		{
			setProperty(ctx, propertyName, value);
		}
	}    // setContext

	/**
	 * Creates fully qualified context name.
	 *
	 * @return built context name
	 */
	public static String createContextName(final int windowNo, final int tabNo, final String name)
	{
		final StringBuilder nameFQ = new StringBuilder();
		nameFQ.append(windowNo).append("|");
		nameFQ.append(tabNo).append("|");
		if (name != null)
		{
			nameFQ.append(name);
		}
		return nameFQ.toString();
	}

	/**
	 * Set Auto Commit
	 *
	 * @param ctx        context
	 * @param autoCommit auto commit (save)
	 */
	public static void setAutoCommit(final Properties ctx, final boolean autoCommit)
	{
		if (ctx == null)
		{
			return;
		}

		setProperty(ctx, CTXNAME_AutoCommit, toString(autoCommit));
	}    // setAutoCommit

	/**
	 * Set Auto Commit for Window
	 *
	 * @param ctx        context
	 * @param WindowNo   window no
	 * @param autoCommit auto commit (save)
	 */
	public static void setAutoCommit(final Properties ctx, final int WindowNo, final boolean autoCommit)
	{
		if (ctx == null)
		{
			return;
		}
		setProperty(ctx, WindowNo + "|" + CTXNAME_AutoCommit, toString(autoCommit));
	}    // setAutoCommit

	/**
	 * Set Auto New Record
	 *
	 * @param ctx     context
	 * @param autoNew auto new record
	 */
	public static void setAutoNew(@Nullable final Properties ctx, final boolean autoNew)
	{
		if (ctx == null)
		{
			return;
		}
		setProperty(ctx, CTXNAME_AutoNew, toString(autoNew));
	}    // setAutoNew

	/**
	 * Set Auto New Record for Window
	 *
	 * @param ctx      context
	 * @param WindowNo window no
	 * @param autoNew  auto new record
	 */
	public static void setAutoNew(final Properties ctx, final int WindowNo, final boolean autoNew)
	{
		if (ctx == null)
		{
			return;
		}
		setProperty(ctx, WindowNo + "|" + CTXNAME_AutoNew, toString(autoNew));
	}    // setAutoNew

	/**
	 * Set SO Trx
	 *
	 * @param ctx     context
	 * @param isSOTrx SO Context
	 */
	public static void setSOTrx(final Properties ctx, final boolean isSOTrx)
	{
		if (ctx == null)
		{
			return;
		}
		setProperty(ctx, CTXNAME_IsSOTrx, toString(isSOTrx));
	}    // setSOTrx

	/**
	 * Get global Value of Context
	 *
	 * @param ctx     context
	 * @param context context key
	 * @return value or {@link #CTXVALUE_NullString}
	 */
	public static String getContext(@NonNull final Properties ctx, final String context)
	{
		return getContext(ctx, WINDOW_None, TAB_None, context, Scope.Exact);
	}    // getContext

	/**
	 * Get Value of Context for Window. if not found global context if available and enabled
	 *
	 * @param ctx        context
	 * @param WindowNo   window
	 * @param context    context key
	 * @param onlyWindow if true, no defaults are used unless explicitly asked for
	 * @return value or {@link #CTXVALUE_NullString}
	 */
	public static String getContext(@NonNull final Properties ctx, final int WindowNo, final String context, final boolean onlyWindow)
	{
		return getContext(ctx, WindowNo, TAB_None, context, onlyWindow ? Scope.Window : Scope.Global);
	}

	/**
	 * Get Value of Context for Window. if not found global context if available
	 *
	 * @param ctx      context
	 * @param WindowNo window
	 * @param context  context key
	 * @return value or {@link #CTXVALUE_NullString}
	 */
	public static String getContext(final Properties ctx, final int WindowNo, final String context)
	{
		final boolean onlyWindow = false;
		return getContext(ctx, WindowNo, context, onlyWindow);
	}    // getContext

	/**
	 * Get Value of Context for Window & Tab, if not found global context if available. If TabNo is TAB_INFO only tab's context will be checked.
	 *
	 * @param ctx      context
	 * @param WindowNo window no
	 * @param TabNo    tab no
	 * @param context  context key
	 * @return value or {@link #CTXVALUE_NullString}
	 */
	public static String getContext(final Properties ctx, final int WindowNo, final int TabNo, final String context)
	{
		// metas: changed
		return getContext(ctx, WindowNo, TabNo, context, Scope.Global);
	}    // getContext

	/**
	 * Get Value of Context for Window & Tab, if not found global context if available. If TabNo is TAB_INFO only tab's context will be checked.
	 *
	 * @param ctx      context
	 * @param WindowNo window no
	 * @param TabNo    tab no
	 * @param context  context key
	 * @param onlyTab  if true, no window value is searched
	 * @return value or {@link #CTXVALUE_NullString}
	 */
	public static String getContext(@NonNull final Properties ctx, final int WindowNo, final int TabNo, final String context, final boolean onlyTab)
	{
		return getContext(ctx, WindowNo, TabNo, context, onlyTab ? Scope.Tab : Scope.Global);
	}

	/**
	 * @return value or ZERO if not found or error
	 */
	public static int getContextAsInt(
			@NonNull final Properties ctx,
			@NonNull final String context)
	{
		final int defaultValueIfNotFoundOrError = 0; // using ZERO instead of "-1" for backward compatibility
		return getContextAsInt(ctx, context, defaultValueIfNotFoundOrError);
	}

	public static int getContextAsInt(
			@NonNull final Properties ctx,
			@NonNull final String context,
			final int defaultValueIfNotFoundOrError)
	{
		String valueStr = getContext(ctx, context);
		if (isPropertyValueNull(valueStr) || valueStr.isEmpty())
		{
			valueStr = getContext(ctx, WINDOW_MAIN, context, false);
		}
		if (isPropertyValueNull(valueStr) || valueStr.isEmpty())
		{
			return defaultValueIfNotFoundOrError;
		}

		try
		{
			return Integer.parseInt(valueStr);
		}
		catch (final NumberFormatException ex)
		{
			s_log.error("Failed converting {}'s value {} to integer. Returning '{}'.", context, valueStr, defaultValueIfNotFoundOrError, ex);
			return defaultValueIfNotFoundOrError;
		}
	}    // getContextAsInt

	/**
	 * Get Context and convert it to an integer (0 if error)
	 *
	 * @param ctx      context
	 * @param WindowNo window no
	 * @param context  context key
	 * @return value or 0
	 */
	public static int getContextAsInt(final Properties ctx, final int WindowNo, final String context)
	{
		final String s = getContext(ctx, WindowNo, context, false);
		if (isPropertyValueNull(s) || s.length() == 0)
		{
			return 0;
		}
		//
		try
		{
			return Integer.parseInt(s);
		}
		catch (final NumberFormatException e)
		{
			s_log.error("Failed converting {}'s value {} to integer", context, s, e);
		}
		return 0;
	}    // getContextAsInt

	/**
	 * Get Context and convert it to an integer (0 if error)
	 *
	 * @param ctx        context
	 * @param WindowNo   window no
	 * @param context    context key
	 * @param onlyWindow if true, no defaults are used unless explicitly asked for
	 * @return value or 0
	 */
	public static int getContextAsInt(final Properties ctx, final int WindowNo, final String context, final boolean onlyWindow)
	{
		final String s = getContext(ctx, WindowNo, context, onlyWindow);
		if (isPropertyValueNull(s) || s.length() == 0)
		{
			return 0;
		}
		//
		try
		{
			return Integer.parseInt(s);
		}
		catch (final NumberFormatException e)
		{
			s_log.error("Failed converting {}'s value {} to integer", context, s, e);
		}
		return 0;
	}    // getContextAsInt

	/**
	 * Get Context and convert it to an integer (0 if error)
	 *
	 * @param ctx      context
	 * @param WindowNo window no
	 * @param TabNo    tab no
	 * @param context  context key
	 * @return value or 0
	 */
	public static int getContextAsInt(final Properties ctx, final int WindowNo, final int TabNo, final String context)
	{
		final String s = getContext(ctx, WindowNo, TabNo, context);
		if (isPropertyValueNull(s) || s.length() == 0)
		{
			return 0;
		}
		//
		try
		{
			return Integer.parseInt(s);
		}
		catch (final NumberFormatException e)
		{
			s_log.error("Failed converting {}'s value {} to integer", context, s, e);
		}
		return 0;
	}    // getContextAsInt

	/**
	 * Is AutoCommit
	 *
	 * @param ctx context
	 * @return true if auto commit
	 */
	public static boolean isAutoCommit(final Properties ctx)
	{
		if (ctx == null)
		{
			throw new IllegalArgumentException("Require Context");
		}

		final String s = getContext(ctx, CTXNAME_AutoCommit);
		if (s != null && s.equals("Y"))
		{
			return true;
		}
		return false;
	}    // isAutoCommit

	/**
	 * Is Window AutoCommit (if not set use default)
	 *
	 * @param ctx      context
	 * @param WindowNo window no
	 * @return true if auto commit
	 */
	public static boolean isAutoCommit(final Properties ctx, final int WindowNo)
	{
		if (ctx == null)
		{
			throw new IllegalArgumentException("Require Context");
		}

		final boolean onlyWindow = false; // fallback to global context
		final String s = getContext(ctx, WindowNo, CTXNAME_AutoCommit, onlyWindow);
		if (s != null)
		{
			if (s.equals("Y"))
			{
				return true;
			}
			else
			{
				return false;
			}
		}

		return isAutoCommit(ctx);
	}    // isAutoCommit

	/**
	 * Is Auto New Record
	 *
	 * @param ctx context
	 * @return true if auto new
	 */
	public static boolean isAutoNew(@Nullable final Properties ctx)
	{
		if (ctx == null)
		{
			throw new IllegalArgumentException("Require Context");
		}
		final String s = getContext(ctx, CTXNAME_AutoNew);
		if ("Y".equals(s))
		{
			return true;
		}
		return false;
	}    // isAutoNew

	/**
	 * Is Window Auto New Record (if not set use default)
	 *
	 * @param ctx      context
	 * @param WindowNo window no
	 * @return true if auto new record
	 */
	public static boolean isAutoNew(final Properties ctx, final int WindowNo)
	{
		if (ctx == null)
		{
			throw new IllegalArgumentException("Require Context");
		}
		final String s = getContext(ctx, WindowNo, CTXNAME_AutoNew, false);
		if (s != null)
		{
			return s.equals("Y");
		}
		return isAutoNew(ctx);
	}    // isAutoNew

	/**
	 * Is Sales Order Trx
	 *
	 * @param ctx context
	 * @return true if SO (default)
	 */
	public static boolean isSOTrx(@NonNull final Properties ctx)
	{
		final String s = getContext(ctx, CTXNAME_IsSOTrx);
		if ("N".equals(s))
		{
			return false;
		}
		return true;
	}    // isSOTrx

	/**
	 * Is Sales Order Trx
	 *
	 * @param ctx      context
	 * @param WindowNo window no
	 * @return true if SO (default)
	 * @deprecated Please consider fetching the actual model and then calling it's <code>isSOTrx()</code> method
	 */
	@Deprecated
	public static boolean isSOTrx(@NonNull final Properties ctx, final int WindowNo)
	{
		final Boolean soTrx = getSOTrxOrNull(ctx, WindowNo);
		return soTrx != null && soTrx.booleanValue();
	}    // isSOTrx

	/**
	 * Is Sales Order Trx (returns <code>null</code>)
	 *
	 * @param ctx      context
	 * @param WindowNo window no
	 * @return true if {@link #CTXNAME_IsSOTrx} = <code>Y</code>, false if {@link #CTXNAME_IsSOTrx} = <code>N</code> and <code>null</code> if {@link #CTXNAME_IsSOTrx} is not set.
	 * @deprecated Please consider fetching the actual model and then calling it's <code>isSOTrx()</code> method
	 */
	@Deprecated
	public static Boolean getSOTrxOrNull(@NonNull final Properties ctx, final int WindowNo)
	{
		final String s = getContext(ctx, WindowNo, CTXNAME_IsSOTrx, true);
		if (Check.isEmpty(s, true))
		{
			return null;
		}

		return DisplayType.toBoolean(s);
	}    // isSOTrx

	/**
	 * Get Context and convert it to a Timestamp if error return today's date
	 *
	 * @param ctx     context
	 * @param context context key
	 * @return Timestamp
	 */
	public static Timestamp getContextAsDate(final Properties ctx, final String context)
	{
		return getContextAsDate(ctx, WINDOW_MAIN, context);
	}    // getContextAsDate

	/**
	 * Get Context and convert it to a Timestamp if error return today's date
	 *
	 * @param ctx      context
	 * @param WindowNo window no
	 * @param context  context key
	 * @return context timestamp or {@link SystemTime#asTimestamp()}; never returns <code>null</code>
	 */
	public static Timestamp getContextAsDate(final Properties ctx, final int WindowNo, final String context)
	{
		if (ctx == null || context == null)
		{
			throw new IllegalArgumentException("Require Context");
		}

		final String timestampStr = getContext(ctx, WindowNo, context, false);
		final Timestamp timestamp = parseTimestamp(timestampStr);
		if (timestamp == null)
		{
			final Timestamp sysDate = SystemTime.asTimestamp();
			if (!Adempiere.isUnitTestMode())
			{
				// metas: tsa: added a dummy exception to be able to track it quickly
				s_log.warn("No value for '{}' or value '{}' could not be parsed. Returning system date: {}", context, timestampStr, sysDate);
			}
			return sysDate;
		}

		return timestamp;
	}    // getContextAsDate

	/**
	 * Get Login AD_Client_ID
	 *
	 * @param ctx context
	 * @return login AD_Client_ID
	 */
	public static int getAD_Client_ID(@NonNull final Properties ctx)
	{
		return Env.getContextAsInt(ctx, CTXNAME_AD_Client_ID);
	}    // getAD_Client_ID

	public static ClientId getClientId(@NonNull final Properties ctx)
	{
		return ClientId.ofRepoId(getAD_Client_ID(ctx));
	}

	public static int getAD_Client_ID()
	{
		return getAD_Client_ID(getCtx());
	}

	public static ClientId getClientId()
	{
		return ClientId.ofRepoId(getAD_Client_ID());
	}

	public static ClientId getClientIdOrSystem()
	{
		return ClientId.ofRepoIdOrSystem(getAD_Client_ID());
	}

	public static void setClientId(@NonNull final Properties ctx, @NonNull final ClientId clientId)
	{
		setContext(ctx, CTXNAME_AD_Client_ID, clientId.getRepoId());
	}

	/**
	 * Get Login AD_Org_ID
	 *
	 * @param ctx context
	 * @return login AD_Org_ID
	 */
	public static int getAD_Org_ID(@NonNull final Properties ctx)
	{
		return getContextAsInt(ctx, CTXNAME_AD_Org_ID);
	}    // getAD_Client_ID

	public static OrgId getOrgId(final Properties ctx)
	{
		return OrgId.ofRepoIdOrAny(getAD_Org_ID(ctx));
	}

	public static OrgId getOrgId()
	{
		return getOrgId(getCtx());
	}

	public static void setOrgId(final Properties ctx, final OrgId orgId)
	{
		setContext(ctx, CTXNAME_AD_Org_ID, orgId.getRepoId());
	}

	public static ClientAndOrgId getClientAndOrgId()
	{
		return getClientAndOrgId(Env.getCtx());
	}

	public static ClientAndOrgId getClientAndOrgId(@NonNull final Properties ctx)
	{
		return ClientAndOrgId.ofClientAndOrg(Env.getClientId(ctx), Env.getOrgId(ctx));
	}

	/**
	 * Get Login AD_User_ID
	 *
	 * @param ctx context
	 * @return login AD_User_ID or -1
	 */
	public static int getAD_User_ID(@NonNull final Properties ctx)
	{
		return getContextAsInt(ctx, CTXNAME_AD_User_ID, -1);
	}    // getAD_User_ID

	public static int getAD_User_ID()
	{
		return getAD_User_ID(getCtx());
	}

	public static UserId getLoggedUserId()
	{
		return UserId.ofRepoId(getAD_User_ID(getCtx()));
	}

	public static UserId getLoggedUserId(final Properties ctx)
	{
		final UserId loggedUserId = UserId.ofRepoIdOrNull(getAD_User_ID(ctx));
		if(loggedUserId == null)
		{
			throw new AdempiereException("No user logged in");
		}
		return loggedUserId;
	}

	public static Optional<UserId> getLoggedUserIdIfExists()
	{
		return getLoggedUserIdIfExists(getCtx());
	}

	public static Optional<UserId> getLoggedUserIdIfExists(final Properties ctx)
	{
		return Optional.ofNullable(UserId.ofRepoIdOrNull(getAD_User_ID(ctx)));
	}

	public static void setLoggedUserId(final Properties ctx, @NonNull final UserId userId)
	{
		setContext(ctx, CTXNAME_AD_User_ID, userId.getRepoId());
	}

	public static IAutoCloseable temporaryChangeLoggedUserId(@NonNull final UserId userId)
	{
		return temporaryChangeLoggedUserId(getCtx(), userId);
	}

	public static IAutoCloseable temporaryChangeLoggedUserId(final Properties ctx, @NonNull final UserId userId)
	{
		final UserId previousUserId = UserId.ofRepoIdOrNull(getContextAsInt(ctx, CTXNAME_AD_User_ID, -1));

		setContext(ctx, CTXNAME_AD_User_ID, userId.getRepoId());

		if (previousUserId != null)
		{
			return () -> setContext(ctx, CTXNAME_AD_User_ID, previousUserId.getRepoId());
		}
		else
		{
			return () -> setContext(ctx, CTXNAME_AD_User_ID, (String)null);
		}
	}

	public static void setSalesRepId(final Properties ctx, @NonNull final UserId userId)
	{
		setContext(ctx, CTXNAME_SalesRep_ID, userId.getRepoId());
	}

	/**
	 * Get Login AD_Role_ID
	 *
	 * @param ctx context
	 * @return {@code #AD_Role_ID}
	 */
	public static int getAD_Role_ID(final Properties ctx)
	{
		return Env.getContextAsInt(ctx, CTXNAME_AD_Role_ID);
	}

	public static RoleId getLoggedRoleId(final Properties ctx)
	{
		return RoleId.ofRepoId(getAD_Role_ID(ctx));
	}

	public static Optional<RoleId> getLoggedRoleIdIfExists(final Properties ctx)
	{
		return Optional.ofNullable(RoleId.ofRepoIdOrNull(Env.getContextAsInt(ctx, CTXNAME_AD_Role_ID, -1)));
	}

	public static RoleId getLoggedRoleId()
	{
		return getLoggedRoleId(getCtx());
	}

	public static IUserRolePermissions getUserRolePermissions()
	{
		final Properties ctx = getCtx();
		return getUserRolePermissions(ctx);
	}

	public static IUserRolePermissions getUserRolePermissionsOrNull()
	{
		final Properties ctx = getCtx();
		return getUserRolePermissionsOrNull(ctx);
	}

	public static IUserRolePermissions getUserRolePermissions(final Properties ctx)
	{
		final UserRolePermissionsKey userRolePermissionsKey = UserRolePermissionsKey.fromContext(ctx);
		return Services.get(IUserRolePermissionsDAO.class).getUserRolePermissions(userRolePermissionsKey);
	}

	public static IUserRolePermissions getUserRolePermissionsOrNull(final Properties ctx)
	{
		final UserRolePermissionsKey userRolePermissionsKey = UserRolePermissionsKey.fromContextOrNull(ctx);
		if (userRolePermissionsKey == null)
		{
			return null;
		}

		return Services.get(IUserRolePermissionsDAO.class).getUserRolePermissions(userRolePermissionsKey);
	}

	public static IUserRolePermissions getUserRolePermissions(final UserRolePermissionsKey key)
	{
		return Services.get(IUserRolePermissionsDAO.class).getUserRolePermissions(key);
	}

	public static IUserRolePermissions getUserRolePermissions(final String permissionsKey)
	{
		final UserRolePermissionsKey userRolePermissionsKey = UserRolePermissionsKey.fromString(permissionsKey);
		return Services.get(IUserRolePermissionsDAO.class).getUserRolePermissions(userRolePermissionsKey);
	}

	public static int getAD_Session_ID(@NonNull final Properties ctx)
	{
		return Env.getContextAsInt(ctx, CTXNAME_AD_Session_ID);
	}

	/**************************************************************************
	 * Get Preference.
	 *
	 * <pre>
	 * 	0)	Current Setting
	 * 	1) 	Window Preference
	 * 	2) 	Global Preference
	 * 	3)	Login settings
	 * 	4)	Accounting settings
	 * </pre>
	 *
	 * @param ctx context
	 * @param context Entity to search
	 * @param system System level preferences (vs. user defined)
	 * @return preference value
	 */
	public static String getPreference(final Properties ctx, @Nullable final AdWindowId adWindowId, final String context, final boolean system)
	{
		if (ctx == null || context == null)
		{
			throw new IllegalArgumentException("Require Context");
		}
		String retValue;
		//
		if (!system)            // User Preferences
		{
			retValue = getProperty(ctx, createPreferenceName(adWindowId, context));// Window Pref
			if (retValue == null)
			{
				retValue = getProperty(ctx, createPreferenceName(null, context));            // Global Pref
			}
		}
		else
		// System Preferences
		{
			retValue = getProperty(ctx, "#" + context);                // Login setting
			if (retValue == null)
			{
				retValue = getProperty(ctx, "$" + context);            // Accounting setting
			}
		}
		//
		return (retValue == null ? "" : retValue);
	}    // getPreference

	public static void setPreference(final Properties ctx, final IUserValuePreference userValuePreference)
	{
		final String preferenceName = createPreferenceName(userValuePreference.getAdWindowId(), userValuePreference.getName());
		final String preferenceValue = userValuePreference.getValue();
		setContext(ctx, preferenceName, preferenceValue);
	}

	private static String createPreferenceName(final AdWindowId adWindowId, final String baseName)
	{
		if (adWindowId == null)
		{
			return "P|" + baseName;
		}
		else
		{
			return "P" + adWindowId.getRepoId() + "|" + baseName;
		}
	}

	/**
	 * Context for POS ID
	 */
	static public final String POS_ID = "#POS_ID";

	/**
	 * Check Base Language
	 *
	 * @param ctx       context
	 * @param tableName table to be translated
	 * @return true if base language and table not translated
	 */
	public static boolean isBaseLanguage(@NonNull final Properties ctx, String tableName)
	{
		/*
		 * if (isBaseTranslation(tableName)) return Language.isBaseLanguage (getAD_Language(ctx)); else // No AD Table if (!isMultiLingualDocument(ctx)) return true; // access base table
		 */
		return Language.isBaseLanguage(getAD_Language(ctx));
	}

	/**
	 * Check Base Language
	 *
	 * @param AD_Language language
	 * @param tableName   table to be translated
	 * @return true if base language and table not translated
	 */
	public static boolean isBaseLanguage(@Nullable final String AD_Language, final String tableName)
	{
		/*
		 * if (isBaseTranslation(tableName)) return Language.isBaseLanguage (AD_Language); else // No AD Table if (!isMultiLingualDocument(s_ctx)) // Base Context return true; // access base table
		 */
		return Language.isBaseLanguage(AD_Language);
	}    // isBaseLanguage

	/**
	 * Check Base Language
	 *
	 * @param language  language
	 * @param tableName table to be translated
	 * @return true if base language and table not translated
	 */
	public static boolean isBaseLanguage(final Language language, final String tableName)
	{
		/*
		 * if (isBaseTranslation(tableName)) return language.isBaseLanguage(); else // No AD Table if (!isMultiLingualDocument(s_ctx)) // Base Context return true; // access base table
		 */
		return language.isBaseLanguage();
	}    // isBaseLanguage

	/**
	 * Table is in Base Translation (AD)
	 *
	 * @param tableName table
	 * @return true if base trl
	 */
	public static boolean isBaseTranslation(final String tableName)
	{
		if (tableName.startsWith("AD")
				|| tableName.equals("C_Country_Trl"))
		{
			return true;
		}
		return false;
	}    // isBaseTranslation

	/**
	 * @return true if multi lingual documents
	 */
	public static boolean isMultiLingualDocument(final Properties ctx)
	{
		final ClientId clientId = getClientId(ctx);

		final IClientDAO clientsRepo = Services.get(IClientDAO.class);
		return clientsRepo.isMultilingualDocumentsEnabled(clientId);
	}    // isMultiLingualDocument

	public static void setAD_Language(final String adLanguage)
	{
		setAD_Language(getCtx(), adLanguage);
	}

	public static void setAD_Language(final Properties ctx, final String adLanguage)
	{
		setContext(ctx, Env.CTXNAME_AD_Language, adLanguage);
	}

	/**
	 * Get System AD_Language.
	 *
	 * <b>IMPORTANT: </b>While the language is not yet known (early stages of startup), this method can return <code>null</code>
	 *
	 * @param ctx context
	 * @return AD_Language eg. en_US
	 */
	@Nullable
	public static String getAD_Language(@Nullable final Properties ctx)
	{
		if (ctx != null)
		{
			final String lang = getContext(ctx, CTXNAME_AD_Language);
			if (!Check.isEmpty(lang))
			{
				return lang;
			}
		}

		// metas-al: if in BACKEND mode, use Base language anyway (i.e for DB exceptions)
		if (Adempiere.RunMode.BACKEND == Ini.getRunMode())
		{
			return Language.getBaseAD_Language();
		}

		// metas-ts: if the language is not known, then don't guess
		// return Language.getBaseAD_Language();
		return null;
	}    // getAD_Language

	public static String getAD_Language()
	{
		return getAD_Language(getCtx());
	}

	public static String getADLanguageOrBaseLanguage()
	{
		final String adLanguage = getAD_Language();
		return adLanguage != null ? adLanguage : Language.getBaseAD_Language();
	}

	public static String getADLanguageOrBaseLanguage(@NonNull final Properties ctx)
	{
		final String adLanguage = getAD_Language(ctx);
		return adLanguage != null ? adLanguage : Language.getBaseAD_Language();
	}

	/**
	 * Get System Language.
	 *
	 * <b>IMPORTANT: </b>While the language is not yet known (early stages of startup), this method can return <code>null</code>
	 *
	 * @param ctx context
	 * @return Language
	 */
	public static Language getLanguage(@Nullable final Properties ctx)
	{
		if (ctx != null)
		{
			final String lang = getAD_Language(ctx);
			if (!isPropertyValueNull(lang) && !Check.isEmpty(lang))
			{
				return Language.getLanguage(lang);
			}
		}

		// metas-ts: if the language is not known, then don't guess
		// return Language.getBaseAD_Language();
		return null;
	}    // getLanguage

	public static Language getLanguage()
	{
		return getLanguage(getCtx());
	}

	/**
	 * Get Login Language
	 *
	 * @param ctx context
	 * @return Language
	 * @deprecated Please use {@link #getLanguage(Properties)} instead
	 */
	@Deprecated
	public static Language getLoginLanguage(@Nullable final Properties ctx)
	{
		return Env.getLanguage(ctx); // metas: 02214
		// return Language.getLoginLanguage();
	}    // getLanguage

	public static String verifyLanguageFallbackToBase(@NonNull final String testLang)
	{
		Language language = Language.getLanguage(testLang);
		language = verifyLanguageFallbackToBase(language);
		return language.getAD_Language();
	}

	/**
	 * Check that language is supported by the system. Returns the base language in case parameter language is not supported.
	 *
	 * @return the received language if it is supported, the base language otherwise.
	 */
	public static Language verifyLanguageFallbackToBase(@NonNull final Language testLang)
	{
		final String searchAD_Language = testLang.getAD_Language();

		//
		// Get available languages, having BaseLanguage first and then System Language
		final ADLanguageList AD_Languages = Services.get(ILanguageDAO.class).retrieveAvailableLanguages();

		//
		// Check if we have a perfect match
		if (AD_Languages.containsADLanguage(searchAD_Language))
		{
			return testLang;
		}

		//
		// Pick a similar language (with different country code and/or variant)
		final String similarADLanguage = AD_Languages.findSimilarADLanguage(searchAD_Language).orElse(null);
		if(similarADLanguage != null)
		{
			s_log.debug("Found similar Language {} for {}", similarADLanguage, testLang);
			final Language similarLanguage = testLang.toBuilder().m_AD_Language(similarADLanguage).build();
			Language.addNewLanguage(similarLanguage);
			return similarLanguage;
		}

		// If the desired language (eg. en_US requested by the browser) is unavailable, we shall return the system base language.
		return Language.getBaseLanguage();
	}

	/**************************************************************************
	 * Get Context as String array with format: key == value
	 *
	 * @param ctx context
	 * @return context string
	 */
	public static String[] getEntireContext(final Properties ctx)
	{
		if (ctx == null)
		{
			throw new IllegalArgumentException("Require Context");
		}

		final Set<String> keys = ctx.stringPropertyNames();
		final String[] sList = new String[keys.size()];
		int i = 0;
		for (final String key : keys)
		{
			final Object value = ctx.get(key);
			sList[i++] = key + " == " + value;
		}

		return sList;
	}    // getEntireContext

	/**
	 * Get Header info (connection, org, user).
	 * <p>
	 * Uses {@link #CTXNAME_WindowName} from context to fetch the window name.
	 *
	 * @param ctx      context
	 * @param WindowNo window
	 * @return Header String
	 */
	public static String getHeader(final Properties ctx, final int WindowNo)
	{
		final StringBuilder sb = new StringBuilder();
		if (isRegularWindowNo(WindowNo))
		{
			sb.append(getContext(ctx, WindowNo, CTXNAME_WindowName, false)).append("  ");
			final String documentNo = getContext(ctx, WindowNo, "DocumentNo", false);
			final String value = getContext(ctx, WindowNo, "Value", false);
			final String name = getContext(ctx, WindowNo, "Name", false);
			if (!"".equals(documentNo))
			{
				sb.append(documentNo).append("  ");
			}
			if (!"".equals(value))
			{
				sb.append(value).append("  ");
			}
			if (!"".equals(name))
			{
				sb.append(name).append("  ");
			}
		}
		else
		{
			sb.append("metasfresh Swing Client");
		}

		final String connectionInfo;
		if (Adempiere.isUnitTestMode())
		{
			connectionInfo = "no database connection";
		}
		else
		{
			connectionInfo = CConnection.get().getConnectionURL();
		}

		sb.append(" [").append(connectionInfo).append("]");
		return sb.toString();
	}    // getHeader

	/**
	 * Clean up context for Window (i.e. delete it)
	 *
	 * @param ctx      context
	 * @param WindowNo window
	 */
	public static void clearWinContext(final Properties ctx, final int WindowNo)
	{
		if (ctx == null)
		{
			throw new IllegalArgumentException("Require Context");
		}

		// Clear WindowNo related context
		final String ctxWindowPrefix = WindowNo + "|";
		removeContextForPrefix(ctx, ctxWindowPrefix);

		//
		if (Ini.isSwingClient())
		{
			removeWindow(WindowNo);
		}
	}    // clearWinContext

	/**
	 * Parse Context replaces global or Window context @tag@ with actual value.
	 *
	 * @param ctx              context
	 * @param WindowNo         Number of Window
	 * @param value            Message to be parsed
	 * @param onlyWindow       if true, no defaults are used
	 * @param ignoreUnparsable if true, unsuccessful @return parsed String or "" if not successful and ignoreUnparsable
	 * @return parsed context
	 * @tag@ are ignored otherwise "" is returned
	 */
	public static String parseContext(
			@NonNull final Properties ctx,
			final int WindowNo,
			final String value,
			final boolean onlyWindow,
			final boolean ignoreUnparsable)
	{
		final Evaluatee evalCtx = Evaluatees.ofCtx(ctx, WindowNo, onlyWindow);
		final String valueParsed = parseContext(evalCtx, value, ignoreUnparsable);
		return valueParsed;
	}

	public static String parseContext(
			@NonNull final Properties ctx,
			final int WindowNo,
			final IStringExpression expression,
			final boolean onlyWindow,
			final boolean ignoreUnparsable)
	{
		final Evaluatee evalCtx = Evaluatees.ofCtx(ctx, WindowNo, onlyWindow);
		final String valueParsed = parseContext(evalCtx, expression, ignoreUnparsable);
		return valueParsed;
	}

	public static String parseContext(final Evaluatee evalCtx, final String value, final boolean ignoreUnparsable)
	{
		final IStringExpression expression = Services.get(IExpressionFactory.class).compile(value, IStringExpression.class);
		return parseContext(evalCtx, expression, ignoreUnparsable);
	}

	public static String parseContext(
			final Evaluatee evalCtx,
			@NonNull final IStringExpression expression,
			final boolean ignoreUnparsable)
	{
		return expression.evaluate(evalCtx, ignoreUnparsable);
	}    // parseContext

	/**
	 * Parse Context replaces global or Window context @tag@ with actual value.
	 *
	 * @param ctx        context
	 * @param WindowNo   Number of Window
	 * @param value      Message to be parsed
	 * @param onlyWindow if true, no defaults are used
	 * @return parsed String or "" if not successful
	 */
	public static String parseContext(final Properties ctx, final int WindowNo, final String value, final boolean onlyWindow)
	{
		final boolean ignoreUnparsable = false;
		return parseContext(ctx, WindowNo, value, onlyWindow, ignoreUnparsable);
	}    // parseContext

	public static String parseContext(
			@NonNull final Properties ctx,
			final int WindowNo,
			final IStringExpression expression,
			final boolean onlyWindow)
	{
		final boolean ignoreUnparsable = false;
		return parseContext(ctx, WindowNo, expression, onlyWindow, ignoreUnparsable);
	}    // parseContext

	/*************************************************************************/

	private static final WindowsIndex windows = new WindowsIndex();

	/**
	 * Add Container and return WindowNo. The container is a APanel, AWindow or JFrame/JDialog
	 *
	 * @param window window
	 * @return WindowNo used for context
	 */
	public static int createWindowNo(final Container window)
	{
		return windows.addWindow(window);
	}

	public static void addWindow(final int windowNo, final Container window)
	{
		windows.addWindow(windowNo, window);
	}

	/**
	 * @return WindowNo of container or {@link #WINDOW_MAIN} if no WindowNo found for container.
	 */
	public static int getWindowNo(@Nullable final Component container)
	{
		return windows.getWindowNo(container);
	}    // getWindowNo

	/**
	 * @return JFrame of WindowNo or <code>null</code> if not found or windowNo is invalid
	 */
	public static JFrame getWindow(final int windowNo)
	{
		return windows.getFrameByWindowNo(windowNo);
	}

	/**
	 * Remove window from active list
	 */
	private static void removeWindow(final int windowNo)
	{
		windows.removeWindow(windowNo);
	}

	/**
	 * @return true if given windowNo is a valid windowNo and is for a regular window (not the main window)
	 */
	public static boolean isRegularWindowNo(final int windowNo)
	{
		return windowNo > 0
				&& windowNo != WINDOW_None
				&& windowNo != WINDOW_MAIN;
	}

	/**
	 * @return true if given windowNo is a valid windowNo and is for a regular window or for main window
	 */
	public static boolean isRegularOrMainWindowNo(final int windowNo)
	{
		return windowNo == WINDOW_MAIN
				|| isRegularWindowNo(windowNo);
	}

	/**
	 * Clean up context for Window (i.e. delete it)
	 *
	 * @param WindowNo window
	 */
	public static void clearWinContext(final int WindowNo)
	{
		clearWinContext(getCtx(), WindowNo);
	}    // clearWinContext

	/**
	 * Start Browser
	 *
	 * @param url url
	 * @see IClientUI#showURL(String).
	 */
	public static void startBrowser(@NonNull final String url)
	{
		s_log.info("Starting browser using url={}", url);
		Services.get(IClientUI.class).showURL(url);
	}   // startBrowser

	public static void startBrowser(@NonNull final File file)
	{
		startBrowser(file.toURI().toString());
	}

	/**
	 * Do we run on Apple
	 *
	 * @return true if Mac
	 */
	public static boolean isMac()
	{
		String osName = System.getProperty("os.name");
		osName = osName.toLowerCase();
		return osName.contains("mac");
	}    // isMac

	/**
	 * Do we run on Windows
	 *
	 * @return true if windows
	 */
	public static boolean isWindows()
	{
		String osName = System.getProperty("os.name");
		osName = osName.toLowerCase();
		return osName.indexOf("windows") != -1;
	}    // isWindows

	/**
	 * Hide Window
	 *
	 * @param window window
	 * @return true if window is hidden, otherwise close it
	 */
	static public boolean hideWindow(final CFrame window)
	{
		return windows.hideWindow(window);
	}

	/**
	 * Show Window
	 *
	 * @param adWindowId window
	 * @return {@link CFrame} or <code>null</code> if not found
	 */
	public static CFrame showWindow(final AdWindowId adWindowId)
	{
		return windows.showWindowByWindowId(adWindowId);
	}

	/**
	 * Update all windows after look and feel changes.
	 *
	 * @since 2006-11-27
	 */
	public static Set<Window> updateUI()
	{
		return windows.updateUI();
	}

	/**
	 * Prepare the context for calling remote server (for e.g, ejb), only default and global variables are pass over.
	 * It is too expensive and also can have serialization issue if every remote call to server is passing the whole client context.
	 *
	 * @return Properties
	 */
	public static Properties getRemoteCallCtx(final Properties ctx)
	{
		final Properties ctxLight = new Properties();
		final Set<String> keys = ctx.stringPropertyNames(); // all property names (including the ones from the underlying "defaults")
		for (final String key : keys)
		{
			// guard agaist nulls (shall not happen)
			if (key == null)
			{
				continue;
			}

			if (key.startsWith("#") || key.startsWith("$"))
			{
				final String value = getProperty(ctx, key);
				if (!isPropertyValueNull(key, value))
				{
					ctxLight.put(key, value);
				}
			}
		}

		return ctxLight;
	}

	/**************************************************************************
	 * Static Variables
	 */

	/**
	 * Big Decimal 0.
	 *
	 * @deprecated please use {@link BigDecimal#ZERO} instead.
	 */
	@Deprecated
	static final public BigDecimal ZERO = new BigDecimal("0.0");

	/**
	 * Big Decimal 1
	 *
	 * @deprecated please use {@link BigDecimal#ONE} instead.
	 */
	@Deprecated
	static final public BigDecimal ONE = new BigDecimal("1.0");

	/**
	 * Big Decimal 100
	 */
	static final public BigDecimal ONEHUNDRED = new BigDecimal("100.0");

	/**
	 * New Line
	 */
	public static final String NL = System.getProperty("line.separator");

	// metas: begin
	public static final int WINDOW_None = -100;
	public static final int TAB_None = -100;

	/**
	 * Note: we only use this internally; by having it as timestamp, we avoid useless conversions between it and {@link LocalDate}
	 *
	 * task 08451
	 */
	public static final Timestamp MAX_DATE = Timestamp.valueOf("9999-12-31 23:59:59");

	// /* package */ static final String NoValue = "";

	/**
	 * Marker used to flag a NULL ID
	 */
	private static final String CTXVALUE_NullID = new String("0"); // NOTE: new String to make sure it's a unique instance
	/**
	 * Default context value (int)
	 */
	/* package */static final int CTXVALUE_NoValueInt = 0;
	/**
	 * Marker used to flag a NULL String
	 * <p>
	 * NOTE: this is the value returned by getContext methods when no value found
	 */
	// NOTE: before changing this to some other value, please evaluate where the result of getContext variables is compared with hardcoded ""
	/* package */static final String CTXVALUE_NullString = new String(""); // NOTE: new String to make sure it's a unique instance

	public enum Scope
	{
		// Please note that the order is VERY important.
		// Scopes should be ordered by priority, from lower to higher
		Exact(0), Global(100), Window(200), Tab(300);

		private final int priority;

		Scope(final int priority)
		{
			this.priority = priority;
		}

		public int getPriority()
		{
			return this.priority;
		}
	}

	/**
	 * @return value or {@link #CTXVALUE_NullString}
	 */
	public static String getContext(@NonNull final Properties ctx,
			final int WindowNo,
			final int TabNo,
			final String context,
			final Scope scope)
	{
		final CtxName name = CtxNames.parse(context);
		Check.assumeNotNull(name, "name not null");

		boolean isExplicitGlobal = name.isExplicitGlobal();

		Scope scopeActual;
		if (Scope.Exact == scope)
		{
			scopeActual = Scope.Global;
		}
		else if (WindowNo != WINDOW_None)
		{
			if (TabNo != TAB_None)
			{
				scopeActual = Scope.Tab;
				// In initial implementation tab level variables does not support explicit global
				if (Scope.Tab == scope)
				{
					isExplicitGlobal = false;
				}
			}
			else
			{
				scopeActual = Scope.Window;
			}
		}
		else
		{
			scopeActual = Scope.Global;
		}

		if (scopeActual == Scope.Tab && scopeActual.compareTo(scope) >= 0)
		{
			final String contextActual = WindowNo + "|" + TabNo + "|" + name.getName();
			final String value = getProperty(ctx, contextActual);
			if (!isPropertyValueNull(value))
			{
				return value;
			}

			// In case of TAB_INFO, don't fallback to Window context
			if (TAB_INFO != TabNo)
			{
				scopeActual = Scope.Window;
			}
		}

		if (scopeActual == Scope.Window && scopeActual.compareTo(scope) >= 0)
		{
			final String contextActual = WindowNo + "|" + name.getName();
			final String value = getProperty(ctx, contextActual);
			if (!isPropertyValueNull(value))
			{
				return value;
			}
			scopeActual = Scope.Global;
		}

		if ((scopeActual == Scope.Global && scopeActual.compareTo(scope) >= 0) || isExplicitGlobal)
		{
			String value;
			if (isExplicitGlobal)
			{
				value = getProperty(ctx, name.getName());
			}
			else
			{
				value = getProperty(ctx, "#" + name.getName());
				if (isPropertyValueNull(value))
				{
					value = getProperty(ctx, name.getName());
				}
			}
			if (!isPropertyValueNull(value))
			{
				return value;
			}
		}

		if (scopeActual == Scope.Exact)
		{
			final String value = getProperty(ctx, name.getName());
			if (!isPropertyValueNull(value))
			{
				return value;
			}
		}

		//
		// Defaults:
		if (!isPropertyValueNull(name.getDefaultValue()))
		{
			return name.getDefaultValue();
		}
		else
		{
			return CTXVALUE_NullString;
		}
	}

	/**
	 * @return string value or <code>null</code> if it does not exist
	 */
	private static String getProperty(final Properties ctx, final String context)
	{
		if (ctx == null || context == null)
		{
			throw new IllegalArgumentException("Require Context");
		}

		String value = ctx.getProperty(context, null);

		//
		// Handle LOGINDATE_AUTOUPDATE
		if (CTXNAME_Date.equals(context))
		{
			// If the role was allowed to override the login date, do not auto-update the date (08247)
			final boolean allowLoginDateOverride = "Y".equals(getContext(ctx, CTXNAME_IsAllowLoginDateOverride));
			if (allowLoginDateOverride)
			{
				return value;
			}

			if (Services.get(ISysConfigBL.class).getBooleanValue("LOGINDATE_AUTOUPDATE", false, Env.getAD_Client_ID(ctx)) == true)
			{
				// Note: we keep these conditions in 2 inner IFs because we want to avoid infinite recursion
				value = toString(de.metas.common.util.time.SystemTime.asDayTimestamp());
				setProperty(ctx, context, value);
			}
		}

		return value;
	}

	public static int getContextAsInt(final Properties ctx, final int WindowNo, final int TabNo, final String context, final boolean onlyTab)
	{
		final String s = getContext(ctx, WindowNo, TabNo, context, onlyTab);
		return toInteger(s, context);
	}

	public static void setContextAsInt(final Properties ctx, final int WindowNo, final int TabNo, final String context, final int value)
	{
		if (ctx == null || context == null)
		{
			return;
		}

		setProperty(ctx, WindowNo + "|" + TabNo + "|" + context, String.valueOf(value));
	}    // setContext

	public static Timestamp getContextAsDate(
			@NonNull final Properties ctx,
			final int WindowNo,
			final String context,
			final boolean onlyWindow)
	{
		final String s = getContext(ctx, WindowNo, context, onlyWindow);
		// JDBC Format YYYY-MM-DD example 2000-09-11 00:00:00.0
		if (isPropertyValueNull(s) || "".equals(s))
		{
			s_log.error("No value for: {}", context);
			return new Timestamp(System.currentTimeMillis());
		}
		return parseTimestamp(s);
	}

	public static Timestamp getContextAsDate(
			@NonNull final Properties ctx,
			int WindowNo,
			int TabNo,
			String context)
	{
		return getContextAsDate(ctx, WindowNo, TabNo, context, false);
	}

	public static Timestamp getContextAsDate(
			@NonNull final Properties ctx,
			final int WindowNo,
			final int TabNo,
			final String context,
			final boolean onlyTab)
	{
		final String s = getContext(ctx, WindowNo, TabNo, context, onlyTab);
		// JDBC Format YYYY-MM-DD example 2000-09-11 00:00:00.0
		if (isPropertyValueNull(s) || "".equals(s))
		{
			s_log.error("No value for: {}", context);
			return new Timestamp(System.currentTimeMillis());
		}
		return parseTimestamp(s);
	}    // getContextAsDate

	public static void setContextAsDate(
			@Nullable final Properties ctx,
			final int WindowNo,
			final int TabNo,
			@Nullable final String context,
			final Date value)
	{
		if (ctx == null || context == null)
		{
			return;
		}

		setProperty(ctx, WindowNo + "|" + TabNo + "|" + context, toString(value));
	}    // setContext

	/**
	 * Convert the string value to integer
	 *
	 * @param s       string value
	 * @param context context name that was required (used only for logging)
	 * @return int value
	 */
	private static int toInteger(
			final String s,
			final String context)
	{
		if (CTXVALUE_NullString.equals(s))
		{
			return CTXVALUE_NoValueInt;
		}

		try
		{
			return Integer.parseInt(s);
		}
		catch (final NumberFormatException e)
		{
			s_log.error("Failed converting {}'s value {} to integer", context, s, e);
		}
		return CTXVALUE_NoValueInt;
	}

	/**
	 * Convert given timestamp to string.
	 *
	 * @return timestamp as string (JDBC Format 2005-05-09 00:00:00, without nanos) or <code>null</code> if timestamp was null
	 * @see #parseTimestamp(String)
	 */
	public static String toString(@Nullable final Date date)
	{
		final Timestamp timestamp;
		if (date == null)
		{
			return null;
		}
		else if (date instanceof Timestamp)
		{
			timestamp = (Timestamp)date;
		}
		else
		{
			timestamp = new Timestamp(date.getTime());
		}

		// JDBC Format 2005-05-09 00:00:00.0
		String stringValue = timestamp.toString();
		// Chop off .0 (nanos)
		stringValue = stringValue.substring(0, stringValue.indexOf("."));
		return stringValue;
	}

	/**
	 * Parse context value as Timestamp
	 *
	 * @param timestampStr Timestamp string representation (JDBC format)
	 * @return Timestamp or <code>null</code> if value is empty
	 */
	public static Timestamp parseTimestamp(@Nullable final String timestampStr)
	{
		if (Check.isBlank(timestampStr) || isPropertyValueNull(timestampStr))
		{
			return null;
		}

		final Timestamp timestamp = parseTimestampUsingJDBCFormatOrNull(timestampStr);
		if (timestamp != null)
		{
			return timestamp;
		}

		try
		{
			final ZonedDateTime zdt = ZonedDateTime.parse(timestampStr.trim(), DATE_FORMAT);
			return Timestamp.from(zdt.toInstant());
		}
		catch (final DateTimeParseException ex)
		{
			// ignore exception
		}

		throw new AdempiereException("Failed converting `" + timestampStr + "` to " + Timestamp.class + "."
				+ "\nExpected following formats:"
				+ "\n1. JDBC format"
				+ "\n2. ISO8601 format: `" + DATE_PATTEN + "`, e.g. " + de.metas.common.util.time.SystemTime.asZonedDateTime().format(DATE_FORMAT));
	}

	private static Timestamp parseTimestampUsingJDBCFormatOrNull(@NonNull final String timestampStr)
	{
		// JDBC Format YYYY-MM-DD example 2000-09-11 00:00:00.0

		// timestamp requires time
		final String timestampStrToUse;
		if (timestampStr.trim().length() == 10)
		{
			timestampStrToUse = timestampStr.trim() + " 00:00:00.0";
		}
		else if (timestampStr.indexOf('.') == -1)
		{
			timestampStrToUse = timestampStr.trim() + ".0";
		}
		else
		{
			timestampStrToUse = timestampStr.trim();
		}

		try
		{
			return Timestamp.valueOf(timestampStrToUse);
		}
		catch (final RuntimeException ex)
		{
			return null;
		}
	}

	public static String toString(final boolean value)
	{
		return DisplayType.toBooleanString(value);
	}

	private static boolean isPropertyValueNull(final String value)
	{
		if (value == null
				|| value == CTXVALUE_NullID
				|| value == CTXVALUE_NullString)
		{
			return true;
		}

		return false;
	}

	/**
	 * Checks if <code>value</code> of property <code>propertyName</code> shall be considered null (i.e. missing).
	 *
	 * @return true if value shall be considered null
	 */
	public static boolean isPropertyValueNull(final String propertyName, final String value)
	{
		if (isPropertyValueNull(value))
		{
			return true;
		}

		if (!value.isEmpty())
		{
			return false;
		}

		if (isNumericPropertyName(propertyName) && Check.isEmpty(value, true))
		{
			return true;
		}

		// NOTE: empty values are considered not null if they are not numeric. Comparation with #CTXVALUE_NullString is done in isPropertyValueNull
		return false;
	}

	/**
	 * @return true if given propertyName is for a numeric value (i.e. if it ends with "_ID")
	 */
	public static boolean isNumericPropertyName(final String propertyName)
	{
		if (propertyName == null)
		{
			return false;
		}

		// TODO: Research potential problems with tables with Record_ID=0
		return propertyName.endsWith("_ID");
	}

	private static String getNullPropertyValue(final String propertyName)
	{
		if (isNumericPropertyName(propertyName))
		{
			return CTXVALUE_NullID;
		}
		else
		{
			return CTXVALUE_NullString;
		}
	}

	/**
	 * Checks if given contexts are about same session.
	 * <p>
	 * Being the same means that they have the same AD_Client_ID, AD_Org_ID, AD_Role_ID, AD_User_ID and AD_Session_ID.
	 */
	public static boolean isSameSession(final Properties ctx1, final Properties ctx2)
	{
		if (ctx1 == ctx2)
		{
			return true;
		}

		return getAD_Session_ID(ctx1) == getAD_Session_ID(ctx2)
				&& getAD_Client_ID(ctx1) == getAD_Client_ID(ctx2)
				&& getAD_Org_ID(ctx1) == getAD_Org_ID(ctx2)
				&& getAD_Role_ID(ctx1) == getAD_Role_ID(ctx2)
				&& getAD_User_ID(ctx1) == getAD_User_ID(ctx2);
	}

	/**
	 * @return true if given contexts are exactly the same (compared by reference)
	 */
	public static boolean isSame(final Properties ctx1, final Properties ctx2)
	{
		return ctx1 == ctx2;
	}

	// metas: end

	public static Adempiere getSingleAdempiereInstance(@Nullable final ApplicationContext applicationContext)
	{
		if (applicationContext != null)
		{
			SpringContextHolder.instance.setApplicationContext(applicationContext);
		}
		return Adempiere.instance;
	}

	/**
	 * Helper method to bind <code>@Autowire</code> annotated properties of given bean using current Spring Application Context.
	 */
	public static void autowireBean(final Object bean)
	{
		SpringContextHolder.instance.autowire(bean);
	}

	/**
	 * Gets Login/System date using the current context
	 *
	 * @return login/system date; never return null
	 */
	public static Timestamp getDate()
	{
		return getContextAsDate(getCtx(), WINDOW_MAIN, CTXNAME_Date);
	}

	/**
	 * Gets Login/System date
	 *
	 * @return login/system date; never return null
	 */
	public static Timestamp getDate(final Properties ctx)
	{
		return getContextAsDate(ctx, WINDOW_MAIN, CTXNAME_Date);
	}

	public static LocalDate getLocalDate(final Properties ctx)
	{
		return TimeUtil.asLocalDate(getDate(ctx));
	}

	public static LocalDate getLocalDate()
	{
		return getLocalDate(getCtx());
	}

	public static ZonedDateTime getZonedDateTime(final Properties ctx)
	{
		return TimeUtil.asZonedDateTime(getDate(ctx));
	}

	public static ZonedDateTime getZonedDateTime()
	{
		return getZonedDateTime(getCtx());
	}

	/**
	 * @return value or <code>null</code> if the value was not present and value initializer was null
	 */
	public static <V> V get(final Properties ctx, final String propertyName)
	{
		@SuppressWarnings("unchecked") final V value = (V)ctx.get(propertyName);
		return value;
	}

	/**
	 * Gets the value identified by <code>propertyName</code> from given <code>ctx</code>.
	 * <p>
	 * If the value is <code>null</code> (or not present) and a value initializer is provided
	 * than that value initializer will be used to initialize the value.
	 * <p>
	 * This method is thread safe.
	 *
	 * @param valueInitializer optional value initializer to be used when the value was not found or it's null in <code>ctx</code>
	 * @return value or <code>null</code> if the value was not present and value initializer was null
	 * @see #getAndValidate(Properties, String, Predicate, Supplier)
	 */
	public static <V> V get(final Properties ctx, final String propertyName, final Supplier<V> valueInitializer)
	{
		final Predicate<V> validator = null; // no validator
		return getAndValidate(ctx, propertyName, validator, valueInitializer);
	}

	/**
	 * Gets the value identified by <code>propertyName</code> from given <code>ctx</code>.
	 * <p>
	 * If an validator is provided then it will be used to check if the cached value (if any) is still valid.
	 * <p>
	 * If the value is <code>null</code> (or not present, or not valid) and a value initializer is provided
	 * than that value initializer will be used to initialize the value.
	 * <p>
	 * This method is thread safe.
	 *
	 * @param validator        optional value validator
	 * @param valueInitializer optional value initializer to be used when the value was not found or it's null in <code>ctx</code>
	 * @return value or <code>null</code> if the value was not present and value initializer was null
	 */
	public static <V> V getAndValidate(@NonNull final Properties ctx, final String propertyName, final Predicate<V> validator, final Supplier<V> valueInitializer)
	{
		// NOTE: we a synchronizing on "ctx" because the Hashtable methods of "Properties ctx" are declared as "synchronized"
		// and we want to get the same effect and synchronize with them.
		synchronized (ctx)
		{
			// Get the existing value. We assume it has the type "V".
			@SuppressWarnings("unchecked")
			V value = (V)ctx.get(propertyName);

			// Check if cached value it's still valid
			if (validator != null && value != null && !validator.test(value))
			{
				ctx.remove(propertyName);
				value = null;
			}

			// Initialize the value if needed
			if (value == null && valueInitializer != null)
			{
				value = valueInitializer.get();
				ctx.put(propertyName, value);
			}

			return value;
		}
	}

	public static <V> V getAndRemove(final Properties ctx, final String propertyName)
	{
		@SuppressWarnings("unchecked") final V value = (V)ctx.remove(propertyName);
		return value;
	}

	public static void put(final Properties ctx, final String propertyName, final Object value)
	{
		ctx.put(propertyName, value);
	}

	/**
	 * Checks if given key is contained in context.
	 * <p>
	 * WARNING: this method is NOT checking the key exists in underlying "defaults". Before changing this please check the API which depends on this logic
	 *
	 * @return true if given key is contained in context
	 */
	public static boolean containsKey(final Properties ctx, final String key)
	{
		return ctx.containsKey(key);
	}

	/**
	 * Returns given <code>ctx</code> or {@link #getCtx()} if null.
	 *
	 * @return ctx or {@link #getCtx()}; never returns null
	 */
	public static Properties coalesce(@Nullable final Properties ctx)
	{
		return ctx == null ? getCtx() : ctx;
	}
}   // Env
