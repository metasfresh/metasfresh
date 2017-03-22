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

import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Window;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.RepaintManager;
import javax.swing.SwingUtilities;

import org.adempiere.ad.expression.api.IExpressionFactory;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.language.ILanguageDAO;
import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.security.IUserRolePermissionsDAO;
import org.adempiere.ad.security.UserRolePermissionsKey;
import org.adempiere.context.ContextProvider;
import org.adempiere.context.ThreadLocalContextProvider;
import org.adempiere.model.IWindowNoAware;
import org.adempiere.service.IClientDAO;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.service.IValuePreferenceBL.IUserValuePreference;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.collections.Predicate;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.time.SystemTime;
import org.compiere.Adempiere;
import org.compiere.db.CConnection;
import org.compiere.model.MLanguage;
import org.compiere.model.MSession;
import org.compiere.swing.CFrame;
import org.slf4j.Logger;
import org.springframework.util.CollectionUtils;

import com.google.common.base.Supplier;

import de.metas.adempiere.form.IClientUI;
import de.metas.adempiere.model.I_AD_Role;
import de.metas.logging.LogManager;

/**
 * System Environment and static variables.
 *
 * @author Jorg Janke
 * @version $Id: Env.java,v 1.3 2006/07/30 00:54:36 jjanke Exp $
 *
 * @author Teo Sarca, www.arhipac.ro
 *         <ul>
 *         <li>BF [ 1619390 ] Use default desktop browser as external browser
 *         <li>BF [ 2017987 ] Env.getContext(TAB_INFO) should NOT use global context
 *         <li>FR [ 2392044 ] Introduce Env.WINDOW_MAIN
 *         </ul>
 */
public final class Env
{
	/** Logging */
	private static final Logger s_log = LogManager.getLogger(Env.class);

	/**
	 * This field is volatile because i encountered occasional NPEs in {@link #getCtx()} during adempiere startup and i suspect it'S related to multiple parts of adempiere starting concurrently. To
	 * see why I hope that 'volatile' will help, you could start with this link: http://stackoverflow.com/questions/4934913/are-static-variables-shared-between-threads
	 *
	 * NOTE: we need to set it to a default value because else all other helpers like GenerateModels will fail or they need to be changes to setup a context provider.
	 */
	// private static volatile ContextProvider contextProvider = new DefaultContextProvider();
	private static volatile ContextProvider contextProvider = new ThreadLocalContextProvider();

	public static void setContextProvider(final ContextProvider provider)
	{
		Check.assumeNotNull(provider, "provider not null");

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
	 * @task 08859
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
			MSession session = MSession.get(Env.getCtx(), false);	// finish
			if (session != null)
				session.logout();
		}
		//
		reset(true);	// final cache reset
		//
		LogManager.shutdown();
		//

		// should not be required anymore since we make sure that all non-demon threads are stopped
		// works in my debugging-session (without system.exit), but doesn't (always!) works on lx-term01 (x2go)
		if (Ini.isClient())
		{
			System.exit(status);
		}
	}	// close

	/**
	 * Logout from the system
	 */
	public static void logout()
	{
		// End Session
		MSession session = MSession.get(getCtx(), false);	// finish
		if (session != null)
			session.logout();
		//
		reset(true);	// final cache reset
	}

	/**
	 * Reset Cache
	 *
	 * @param finalCall everything otherwise login data remains
	 */
	public static void reset(final boolean finalCall)
	{
		s_log.info("Reseting environment (finalCall={})", finalCall);
		if (Ini.isClient())
		{
			closeWindows();

			// Dismantle windows
			/**
			 * for (int i = 0; i < s_windows.size(); i++) { Container win = (Container)s_windows.get(i); if (win.getClass().getName().endsWith("AMenu")) // Null pointer ; else if (win instanceof
			 * Window) ((Window)win).dispose(); else win.removeAll(); }
			 **/
			// bug [ 1574630 ]
			if (s_windows.size() > 0)
			{
				if (!finalCall)
				{
					Container c = s_windows.get(0);
					s_windows.clear();
					createWindowNo(c);
				}
				else
				{
					s_windows.clear();
				}
			}
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
		if (Ini.isClient())
		{
			DB.closeTarget();
		}

		// Reset Role Access
		if (!finalCall)
		{
			if (Ini.isClient())
				DB.setDBTarget(CConnection.get());

			// NOTE: there is no need to reset the role because cache was reset
			// MRole defaultRole = MRole.getDefault(ctx, false);
			// if (defaultRole != null)
			// defaultRole.loadAccess(true); // Reload
		}
	}	// resetAll

	/**************************************************************************
	 * Application Context
	 */
	/** WindowNo for Main */
	public static final int WINDOW_MAIN = 0;
	/** WindowNo for Find */
	public static final int WINDOW_FIND = 1110;
	/** WindowNo for PrintCustomize */
	public static final int WINDOW_CUSTOMIZE = 1112;
	/** WindowNo for PrintCustomize */
	public static final int WINDOW_INFO = 1113;

	/** Tab for Info */
	public static final int TAB_INFO = 1113;

	public static final String CTXNAME_AD_Client_ID = "#AD_Client_ID";
	public static final String CTXNAME_AD_Client_Name = "#AD_Client_Name";
	public static final int CTXVALUE_AD_Client_ID_System = IClientDAO.SYSTEM_CLIENT_ID;

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
	public static final int CTXVALUE_AD_Role_ID_System = IUserRolePermissions.SYSTEM_ROLE_ID;
	public static final String CTXNAME_AD_Role_Name = "#AD_Role_Name";
	public static final String CTXNAME_AD_Role_UserLevel = "#User_Level";

	public static final String CTXNAME_AD_PInstance_ID = "#AD_PInstance_ID"; // FRESH-314

	/**
	 * Comma separated list of AD_Org_IDs of which current User/Role has any access (ro/rw)
	 *
	 * NOTE: this is deprecated but we are keeping it for those application dictionary logics which require this information from context.
	 */
	public static final String CTXNAME_User_Org = "#User_Org";

	public static final String CTXNAME_AD_User_ID = "#AD_User_ID";
	public static final String CTXNAME_AD_User_Name = "#AD_User_Name";
	public static final String CTXNAME_SalesRep_ID = "#SalesRep_ID";
	public static final int CTXVALUE_AD_User_ID_System = 0;

	public static final String CTXNAME_Date = "#Date";
	public static final String CTXNAME_IsAllowLoginDateOverride = "#" + I_AD_Role.COLUMNNAME_IsAllowLoginDateOverride;

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

	/**
	 * @task http://dewiki908/mediawiki/index.php/05730_Use_different_Theme_colour_on_UAT_system. The value is loaded into the context on login.
	 */
	public static final String CTXNAME_UI_WindowHeader_Notice_Text = "#UI_WindowHeader_Notice_Text";

	/**
	 * Background color of the optional window header notice. The value is loaded into the context on login.
	 *
	 * @task https://metasfresh.atlassian.net/browse/FRESH-352
	 */
	public static final String CTXNAME_UI_WindowHeader_Notice_BG_COLOR = "#UI_WindowHeader_Notice_BG_Color";

	/**
	 * Foreground color of the optional window header notice. The value is loaded into the context on login.
	 *
	 * @task https://metasfresh.atlassian.net/browse/FRESH-352
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
	 * Matches any key which is about window context (i.e. starts with "WindowNo|").
	 */
	private static final Predicate<Object> CTXNAME_MATCHER_AnyWindow = new Predicate<Object>()
	{
		@Override
		public boolean evaluate(final Object key)
		{
			final String tag = key.toString();

			// NOTE: we kept the old logic which considered enough to check if the key starts with a digit
			final boolean matched = Character.isDigit(tag.charAt(0));
			return matched;
		}
	};

	/**
	 * Get Context
	 *
	 * @return Properties
	 */
	public static final Properties getCtx()
	{
		return contextProvider.getContext();
	}   // getCtx

	/**
	 * Creates and returns a new context instance which does not inherit current context and which shall be used temporary.
	 *
	 * @return new context to be used temporary
	 */
	public static final Properties newTemporaryCtx()
	{
		return new Properties();
	}

	/**
	 * Creates and returns a new empty context which fallbacks to given <code>ctx</code>.
	 * <p>
	 * <b>IMPORTANT:</b> do not use this method if you want to do use the derived context as parameter for {@link #switchContext(Properties)}, to avoid a {@link StackOverflowError}. Use {@link #copyCtx(Properties)} instead.
	 *
	 * @param ctx
	 * @return new context
	 */
	public static final Properties deriveCtx(final Properties ctx)
	{
		Check.assumeNotNull(ctx, "ctx not null");
		return new Properties(ctx);
	}

	/**
	 * Creates a new empty context and then copies all values from the original, <b>including</b> defaults.
	 * <p>
	 * <b>IMPORTANT:</b> use this method instead of {@link #deriveCtx(Properties)} if you plan to call {@link #switchContext(Properties)} with the result.
	 *
	 * @param ctx
	 * @return
	 */
	public static final Properties copyCtx(final Properties ctx)
	{
		Check.assumeNotNull(ctx, "ctx not null");

		final Properties newCtx = new Properties();
		CollectionUtils.mergePropertiesIntoMap(ctx, newCtx);

		return newCtx;
	}

	/**
	 * Creates a special ctx that can be used to create system-level records.
	 *
	 * @param ctx the context that will be the base for the system context.
	 * @return new temporary context
	 */
	public static Properties createSysContext(final Properties ctx)
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
	 *
	 * @return
	 */
	public static final boolean isCtxAvailable()
	{
		return contextProvider != null && contextProvider.getContext() != null;
	}   // getCtx

	/**
	 * Temporary replace current context with the given one.
	 *
	 * This method will return an {@link IAutoCloseable} to be used in try-with-resources and which will restore the context back.
	 *
	 * @param ctx
	 * @return auto-closeable used to put back the original context
	 */
	public static IAutoCloseable switchContext(final Properties ctx)
	{
		return contextProvider.switchContext(ctx);
	}

	private static final void clearContext()
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
	public static final void removeContext(final Properties ctx, final String key)
	{
		// NOTE: because the "ctx" it might have underlying "defaults" we cannot just remove it,
		// because it might be that another value is set on parent level (or parent of the parent).
		// Also, setting it to "null" won't help because Properties interprets this as not existing value and will fallback to "defaults".

		// ctx.remove(key);
		final String nullValue = getNullPropertyValue(key);
		ctx.setProperty(key, nullValue);

		s_log.trace("Unset {}=={}", key, nullValue);
	}

	private static final void setProperty(final Properties ctx, final String key, final String value)
	{
		ctx.setProperty(key, value);

		s_log.trace("Set {}=={}", key, value);
	}

	private static final void removeContextForPrefix(final Properties ctx, final String keyPrefix)
	{
		removeContextMatching(ctx, new Predicate<Object>()
		{
			@Override
			public boolean evaluate(Object key)
			{
				final String tag = key.toString();
				final boolean matched = tag.startsWith(keyPrefix);
				return matched;
			}
		});
	}

	/**
	 * Remove context variables of which the key is matched by given <code>keyMatcher</code>.
	 *
	 * @param ctx
	 * @param keyMatcher
	 */
	public static final void removeContextMatching(final Properties ctx, final Predicate<Object> keyMatcher)
	{
		final Set<String> keys = ctx.stringPropertyNames();
		for (final String key : keys)
		{
			// Skip keys which are not matching
			if (!keyMatcher.evaluate(key))
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
	 * @param ctx context
	 * @param context context key
	 * @param value context value
	 */
	public static void setContext(final Properties ctx, final String context, final String value)
	{
		if (ctx == null || context == null)
			return;
		//
		if (value == null || value.length() == 0)
		{
			removeContext(ctx, context);
		}
		else
		{
			setProperty(ctx, context, value);
		}
	}	// setContext

	/**
	 * Set Global Context to Value
	 *
	 * @param ctx context
	 * @param context context key
	 * @param value context value
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
	}	// setContext

	/**
	 * Set Global Context to (int) Value
	 *
	 * @param ctx context
	 * @param context context key
	 * @param value context value
	 */
	public static void setContext(final Properties ctx, final String context, final int value)
	{
		if (ctx == null || context == null)
		{
			return;
		}

		setProperty(ctx, context, String.valueOf(value));
	}	// setContext

	/**
	 * Set Global Context to Y/N Value
	 *
	 * @param ctx context
	 * @param context context key
	 * @param value context value
	 */
	public static void setContext(final Properties ctx, final String context, final boolean value)
	{
		setContext(ctx, context, toString(value));
	}	// setContext

	/**
	 * Set Context for Window to Value
	 *
	 * @param ctx context
	 * @param WindowNo window no
	 * @param context context key
	 * @param value context value
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
	}	// setContext

	/**
	 * Set Context for Window to Value
	 *
	 * @param ctx context
	 * @param WindowNo window no
	 * @param context context key
	 * @param value context value
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
	}	// setContext

	/**
	 * Set Context for Window to int Value
	 *
	 * @param ctx context
	 * @param WindowNo window no
	 * @param context context key
	 * @param value context value
	 */
	public static void setContext(final Properties ctx, final int WindowNo, final String context, final int value)
	{
		if (ctx == null || context == null)
		{
			return;
		}

		final String propertyName = WindowNo + "|" + context;
		setProperty(ctx, propertyName, String.valueOf(value));
	}	// setContext

	/**
	 * Set Context for Window to Y/N Value
	 *
	 * @param ctx context
	 * @param WindowNo window no
	 * @param context context key
	 * @param value context value
	 */
	public static void setContext(final Properties ctx, final int WindowNo, final String context, final boolean value)
	{
		setContext(ctx, WindowNo, context, toString(value));
	}	// setContext

	/**
	 * Set Context for Window & Tab to Value
	 *
	 * @param ctx context
	 * @param WindowNo window no
	 * @param TabNo tab no
	 * @param context context key
	 * @param value context value
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
	}	// setContext

	/**
	 * Creates fully qualified context name.
	 *
	 * @param windowNo
	 * @param tabNo
	 * @param name
	 * @return built context name
	 */
	public static final String createContextName(final int windowNo, final int tabNo, final String name)
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
	 * @param ctx context
	 * @param autoCommit auto commit (save)
	 */
	public static void setAutoCommit(final Properties ctx, final boolean autoCommit)
	{
		if (ctx == null)
		{
			return;
		}

		setProperty(ctx, CTXNAME_AutoCommit, toString(autoCommit));
	}	// setAutoCommit

	/**
	 * Set Auto Commit for Window
	 *
	 * @param ctx context
	 * @param WindowNo window no
	 * @param autoCommit auto commit (save)
	 */
	public static void setAutoCommit(final Properties ctx, final int WindowNo, final boolean autoCommit)
	{
		if (ctx == null)
			return;
		setProperty(ctx, WindowNo + "|" + CTXNAME_AutoCommit, toString(autoCommit));
	}	// setAutoCommit

	/**
	 * Set Auto New Record
	 *
	 * @param ctx context
	 * @param autoNew auto new record
	 */
	public static void setAutoNew(Properties ctx, boolean autoNew)
	{
		if (ctx == null)
			return;
		setProperty(ctx, CTXNAME_AutoNew, toString(autoNew));
	}	// setAutoNew

	/**
	 * Set Auto New Record for Window
	 *
	 * @param ctx context
	 * @param WindowNo window no
	 * @param autoNew auto new record
	 */
	public static void setAutoNew(Properties ctx, int WindowNo, boolean autoNew)
	{
		if (ctx == null)
			return;
		setProperty(ctx, WindowNo + "|" + CTXNAME_AutoNew, toString(autoNew));
	}	// setAutoNew

	/**
	 * Set SO Trx
	 *
	 * @param ctx context
	 * @param isSOTrx SO Context
	 */
	public static void setSOTrx(Properties ctx, boolean isSOTrx)
	{
		if (ctx == null)
			return;
		setProperty(ctx, CTXNAME_IsSOTrx, toString(isSOTrx));
	}	// setSOTrx

	/**
	 * Get global Value of Context
	 *
	 * @param ctx context
	 * @param context context key
	 * @return value or {@link #CTXVALUE_NullString}
	 */
	public static String getContext(final Properties ctx, final String context)
	{
		return getContext(ctx, WINDOW_None, TAB_None, context, Scope.Exact);
	}	// getContext

	/**
	 * Get Value of Context for Window. if not found global context if available and enabled
	 *
	 * @param ctx context
	 * @param WindowNo window
	 * @param context context key
	 * @param onlyWindow if true, no defaults are used unless explicitly asked for
	 * @return value or {@link #CTXVALUE_NullString}
	 */
	public static String getContext(final Properties ctx, final int WindowNo, final String context, final boolean onlyWindow)
	{
		return getContext(ctx, WindowNo, TAB_None, context, onlyWindow ? Scope.Window : Scope.Global);
	}

	/**
	 * Get Value of Context for Window. if not found global context if available
	 *
	 * @param ctx context
	 * @param WindowNo window
	 * @param context context key
	 * @return value or {@link #CTXVALUE_NullString}
	 */
	public static String getContext(final Properties ctx, final int WindowNo, final String context)
	{
		final boolean onlyWindow = false;
		return getContext(ctx, WindowNo, context, onlyWindow);
	}	// getContext

	/**
	 * Get Value of Context for Window & Tab, if not found global context if available. If TabNo is TAB_INFO only tab's context will be checked.
	 *
	 * @param ctx context
	 * @param WindowNo window no
	 * @param TabNo tab no
	 * @param context context key
	 * @return value or {@link #CTXVALUE_NullString}
	 */
	public static String getContext(final Properties ctx, final int WindowNo, final int TabNo, final String context)
	{
		// metas: changed
		return getContext(ctx, WindowNo, TabNo, context, Scope.Global);
	}	// getContext

	/**
	 * Get Value of Context for Window & Tab, if not found global context if available. If TabNo is TAB_INFO only tab's context will be checked.
	 *
	 * @param ctx context
	 * @param WindowNo window no
	 * @param TabNo tab no
	 * @param context context key
	 * @param onlyTab if true, no window value is searched
	 * @return value or {@link #CTXVALUE_NullString}
	 */
	public static String getContext(final Properties ctx, final int WindowNo, final int TabNo, final String context, final boolean onlyTab)
	{
		return getContext(ctx, WindowNo, TabNo, context, onlyTab ? Scope.Tab : Scope.Global);
	}

	/**
	 * Get Context and convert it to an integer (0 if error)
	 *
	 * @param ctx context
	 * @param context context key
	 * @return value
	 */
	public static int getContextAsInt(Properties ctx, String context)
	{
		if (ctx == null || context == null)
			throw new IllegalArgumentException("Require Context");
		String s = getContext(ctx, context);
		if (isPropertyValueNull(s) || s.length() == 0)
			s = getContext(ctx, 0, context, false);		// search 0 and defaults
		if (isPropertyValueNull(s) || s.length() == 0)
			return 0;
		//
		try
		{
			return Integer.parseInt(s);
		}
		catch (NumberFormatException e)
		{
			s_log.error("Failed converting {}'s value {} to integer", context, s, e);
		}
		return 0;
	}	// getContextAsInt

	/**
	 * Get Context and convert it to an integer (0 if error)
	 *
	 * @param ctx context
	 * @param WindowNo window no
	 * @param context context key
	 * @return value or 0
	 */
	public static int getContextAsInt(Properties ctx, int WindowNo, String context)
	{
		String s = getContext(ctx, WindowNo, context, false);
		if (isPropertyValueNull(s) || s.length() == 0)
			return 0;
		//
		try
		{
			return Integer.parseInt(s);
		}
		catch (NumberFormatException e)
		{
			s_log.error("Failed converting {}'s value {} to integer", context, s, e);
		}
		return 0;
	}	// getContextAsInt

	/**
	 * Get Context and convert it to an integer (0 if error)
	 *
	 * @param ctx context
	 * @param WindowNo window no
	 * @param context context key
	 * @param onlyWindow if true, no defaults are used unless explicitly asked for
	 * @return value or 0
	 */
	public static int getContextAsInt(Properties ctx, int WindowNo, String context, boolean onlyWindow)
	{
		String s = getContext(ctx, WindowNo, context, onlyWindow);
		if (isPropertyValueNull(s) || s.length() == 0)
			return 0;
		//
		try
		{
			return Integer.parseInt(s);
		}
		catch (NumberFormatException e)
		{
			s_log.error("Failed converting {}'s value {} to integer", context, s, e);
		}
		return 0;
	}	// getContextAsInt

	/**
	 * Get Context and convert it to an integer (0 if error)
	 *
	 * @param ctx context
	 * @param WindowNo window no
	 * @param TabNo tab no
	 * @param context context key
	 * @return value or 0
	 */
	public static int getContextAsInt(Properties ctx, int WindowNo, int TabNo, String context)
	{
		String s = getContext(ctx, WindowNo, TabNo, context);
		if (isPropertyValueNull(s) || s.length() == 0)
			return 0;
		//
		try
		{
			return Integer.parseInt(s);
		}
		catch (NumberFormatException e)
		{
			s_log.error("Failed converting {}'s value {} to integer", context, s, e);
		}
		return 0;
	}	// getContextAsInt

	/**
	 * Is AutoCommit
	 *
	 * @param ctx context
	 * @return true if auto commit
	 */
	public static boolean isAutoCommit(final Properties ctx)
	{
		if (ctx == null)
			throw new IllegalArgumentException("Require Context");

		final String s = getContext(ctx, CTXNAME_AutoCommit);
		if (s != null && s.equals("Y"))
			return true;
		return false;
	}	// isAutoCommit

	/**
	 * Is Window AutoCommit (if not set use default)
	 *
	 * @param ctx context
	 * @param WindowNo window no
	 * @return true if auto commit
	 */
	public static boolean isAutoCommit(final Properties ctx, final int WindowNo)
	{
		if (ctx == null)
			throw new IllegalArgumentException("Require Context");

		final boolean onlyWindow = false; // fallback to global context
		final String s = getContext(ctx, WindowNo, CTXNAME_AutoCommit, onlyWindow);
		if (s != null)
		{
			if (s.equals("Y"))
				return true;
			else
				return false;
		}

		return isAutoCommit(ctx);
	}	// isAutoCommit

	/**
	 * Is Auto New Record
	 *
	 * @param ctx context
	 * @return true if auto new
	 */
	public static boolean isAutoNew(Properties ctx)
	{
		if (ctx == null)
			throw new IllegalArgumentException("Require Context");
		String s = getContext(ctx, CTXNAME_AutoNew);
		if (s != null && s.equals("Y"))
			return true;
		return false;
	}	// isAutoNew

	/**
	 * Is Window Auto New Record (if not set use default)
	 *
	 * @param ctx context
	 * @param WindowNo window no
	 * @return true if auto new record
	 */
	public static boolean isAutoNew(Properties ctx, int WindowNo)
	{
		if (ctx == null)
			throw new IllegalArgumentException("Require Context");
		String s = getContext(ctx, WindowNo, CTXNAME_AutoNew, false);
		if (s != null)
		{
			if (s.equals("Y"))
				return true;
			else
				return false;
		}
		return isAutoNew(ctx);
	}	// isAutoNew

	/**
	 * Is Sales Order Trx
	 *
	 * @param ctx context
	 * @return true if SO (default)
	 */
	public static boolean isSOTrx(Properties ctx)
	{
		String s = getContext(ctx, CTXNAME_IsSOTrx);
		if (s != null && s.equals("N"))
			return false;
		return true;
	}	// isSOTrx

	/**
	 * Is Sales Order Trx
	 *
	 * @param ctx context
	 * @param WindowNo window no
	 * @return true if SO (default)
	 * @deprecated Please consider fetching the actual model and then calling it's <code>isSOTrx()</code> method
	 */
	@Deprecated
	public static boolean isSOTrx(Properties ctx, int WindowNo)
	{
		final Boolean soTrx = getSOTrxOrNull(ctx, WindowNo);
		return soTrx != null && soTrx.booleanValue();
	}	// isSOTrx

	/**
	 * Is Sales Order Trx (returns <code>null</code>)
	 *
	 * @param ctx context
	 * @param WindowNo window no
	 * @return true if {@link CTXNAME_IsSOTrx} = <code>Y</code>, false if {@link CTXNAME_IsSOTrx} = <code>N</code> and <code>null</code> if {@link CTXNAME_IsSOTrx} is not set.
	 * @deprecated Please consider fetching the actual model and then calling it's <code>isSOTrx()</code> method
	 */
	@Deprecated
	public static Boolean getSOTrxOrNull(final Properties ctx, final int WindowNo)
	{
		final String s = getContext(ctx, WindowNo, CTXNAME_IsSOTrx, true);
		if (Check.isEmpty(s, true))
		{
			return null;
		}

		return DisplayType.toBoolean(s);
	}	// isSOTrx

	/**
	 * Get Context and convert it to a Timestamp if error return today's date
	 *
	 * @param ctx context
	 * @param context context key
	 * @return Timestamp
	 */
	public static Timestamp getContextAsDate(final Properties ctx, final String context)
	{
		return getContextAsDate(ctx, WINDOW_MAIN, context);
	}	// getContextAsDate

	/**
	 * Get Context and convert it to a Timestamp if error return today's date
	 *
	 * @param ctx context
	 * @param WindowNo window no
	 * @param context context key
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
			// metas: tsa: added a dummy exception to be able to track it quickly
			final Timestamp sysDate = SystemTime.asTimestamp();
			s_log.error("No value for '{}' or value '{}' could not be parsed. Returning system date: {}", context, timestampStr, sysDate, new Exception("StackTrace"));
			return sysDate;
		}

		return timestamp;
	}	// getContextAsDate

	/**
	 * Get Login AD_Client_ID
	 *
	 * @param ctx context
	 * @return login AD_Client_ID
	 */
	public static int getAD_Client_ID(Properties ctx)
	{
		return Env.getContextAsInt(ctx, CTXNAME_AD_Client_ID);
	}	// getAD_Client_ID

	/**
	 * Get Login AD_Org_ID
	 *
	 * @param ctx context
	 * @return login AD_Org_ID
	 */
	public static int getAD_Org_ID(Properties ctx)
	{
		return Env.getContextAsInt(ctx, CTXNAME_AD_Org_ID);
	}	// getAD_Client_ID

	/**
	 * Get Login AD_User_ID
	 *
	 * @param ctx context
	 * @return login AD_User_ID
	 */
	public static int getAD_User_ID(Properties ctx)
	{
		return Env.getContextAsInt(ctx, CTXNAME_AD_User_ID);
	}	// getAD_User_ID

	/**
	 * Get Login AD_Role_ID
	 *
	 * @param ctx context
	 * @return login AD_Role_ID
	 */
	public static int getAD_Role_ID(Properties ctx)
	{
		return Env.getContextAsInt(ctx, CTXNAME_AD_Role_ID);
	}	// getAD_Role_ID

//	public static void setAD_Role_ID(Properties ctx, final int adRoleId)
//	{
//		Env.setContext(ctx, CTXNAME_AD_Role_ID, adRoleId);
//	}	// getAD_Role_ID

	public static IUserRolePermissions getUserRolePermissions()
	{
		final Properties ctx = getCtx();
		return getUserRolePermissions(ctx);
	}

	public static IUserRolePermissions getUserRolePermissions(final Properties ctx)
	{
		final UserRolePermissionsKey userRolePermissionsKey = UserRolePermissionsKey.of(ctx);
		return Services.get(IUserRolePermissionsDAO.class).retrieveUserRolePermissions(userRolePermissionsKey);
	}

	public static IUserRolePermissions getUserRolePermissions(final UserRolePermissionsKey key)
	{
		return Services.get(IUserRolePermissionsDAO.class).retrieveUserRolePermissions(key);
	}

	public static IUserRolePermissions getUserRolePermissions(final String permissionsKey)
	{
		final UserRolePermissionsKey userRolePermissionsKey = UserRolePermissionsKey.fromString(permissionsKey);
		return Services.get(IUserRolePermissionsDAO.class).retrieveUserRolePermissions(userRolePermissionsKey);
	}


	public static void resetUserRolePermissions()
	{
		Services.get(IUserRolePermissionsDAO.class).resetCache();
	}

	public static int getAD_Session_ID(final Properties ctx)
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
	 * @param AD_Window_ID window no
	 * @param context Entity to search
	 * @param system System level preferences (vs. user defined)
	 * @return preference value
	 */
	public static String getPreference(final Properties ctx, final int AD_Window_ID, final String context, final boolean system)
	{
		if (ctx == null || context == null)
			throw new IllegalArgumentException("Require Context");
		String retValue = null;
		//
		if (!system)         	// User Preferences
		{
			retValue = getProperty(ctx, createPreferenceName(AD_Window_ID, context));// Window Pref
			if (retValue == null)
				retValue = getProperty(ctx, createPreferenceName(IUserValuePreference.AD_WINDOW_ID_NONE, context));  			// Global Pref
		}
		else
		// System Preferences
		{
			retValue = getProperty(ctx, "#" + context);   				// Login setting
			if (retValue == null)
				retValue = getProperty(ctx, "$" + context);   			// Accounting setting
		}
		//
		return (retValue == null ? "" : retValue);
	}	// getPreference

	public static void setPreference(final Properties ctx, final IUserValuePreference userValuePreference)
	{
		final String preferenceName = createPreferenceName(userValuePreference.getAD_Window_ID(), userValuePreference.getName());
		final String preferenceValue = userValuePreference.getValue();
		setContext(ctx, preferenceName, preferenceValue);
	}

	private static final String createPreferenceName(final int AD_Window_ID, final String baseName)
	{
		if (AD_Window_ID <= 0 || AD_Window_ID == IUserValuePreference.AD_WINDOW_ID_NONE)
		{
			return "P|" + baseName;
		}
		else
		{
			return "P" + AD_Window_ID + "|" + baseName;
		}
	}

	/** Context for POS ID */
	static public final String POS_ID = "#POS_ID";

	/**
	 * Check Base Language
	 *
	 * @param ctx context
	 * @param tableName table to be translated
	 * @return true if base language and table not translated
	 */
	public static boolean isBaseLanguage(Properties ctx, String tableName)
	{
		/**
		 * if (isBaseTranslation(tableName)) return Language.isBaseLanguage (getAD_Language(ctx)); else // No AD Table if (!isMultiLingualDocument(ctx)) return true; // access base table
		 **/
		return Language.isBaseLanguage(getAD_Language(ctx));
	}

	/**
	 * Check Base Language
	 *
	 * @param AD_Language language
	 * @param tableName table to be translated
	 * @return true if base language and table not translated
	 */
	public static boolean isBaseLanguage(final String AD_Language, final String tableName)
	{
		/**
		 * if (isBaseTranslation(tableName)) return Language.isBaseLanguage (AD_Language); else // No AD Table if (!isMultiLingualDocument(s_ctx)) // Base Context return true; // access base table
		 **/
		return Language.isBaseLanguage(AD_Language);
	}	// isBaseLanguage

	/**
	 * Check Base Language
	 *
	 * @param language language
	 * @param tableName table to be translated
	 * @return true if base language and table not translated
	 */
	public static boolean isBaseLanguage(final Language language, final String tableName)
	{
		/**
		 * if (isBaseTranslation(tableName)) return language.isBaseLanguage(); else // No AD Table if (!isMultiLingualDocument(s_ctx)) // Base Context return true; // access base table
		 **/
		return language.isBaseLanguage();
	}	// isBaseLanguage

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
			return true;
		return false;
	}	// isBaseTranslation

	/**
	 * Do we have Multi-Lingual Documents. Set in DB.loadOrgs
	 *
	 * @param ctx context
	 * @return true if multi lingual documents
	 */
	public static boolean isMultiLingualDocument(final Properties ctx)
	{
		return Services.get(IClientDAO.class)
				.retriveClient(ctx)
				.isMultiLingualDocument();
	}	// isMultiLingualDocument

	/**
	 * Get System AD_Language.
	 *
	 * <b>IMPORTANT: </b>While the language is not yet known (early stages of startup), this method can return <code>null</code>
	 *
	 * @param ctx context
	 * @return AD_Language eg. en_US
	 */
	public static String getAD_Language(final Properties ctx)
	{
		if (ctx != null)
		{
			String lang = getContext(ctx, CTXNAME_AD_Language);
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
	}	// getAD_Language

	/**
	 * Get System Language.
	 *
	 * <b>IMPORTANT: </b>While the language is not yet known (early stages of startup), this method can return <code>null</code>
	 *
	 * @param ctx context
	 * @return Language
	 */
	public static Language getLanguage(final Properties ctx)
	{
		if (ctx != null)
		{
			String lang = getAD_Language(ctx);
			if (!isPropertyValueNull(lang) && !Check.isEmpty(lang))
			{
				return Language.getLanguage(lang);
			}
		}

		// metas-ts: if the language is not known, then don't guess
		// return Language.getBaseAD_Language();
		return null;
	}	// getLanguage

	/**
	 * Get Login Language
	 *
	 * @param ctx context
	 * @return Language
	 * @deprecated Please use {@link #getLanguage(Properties)} instead
	 */
	@Deprecated
	public static Language getLoginLanguage(Properties ctx)
	{
		return Env.getLanguage(ctx); // metas: 02214
		// return Language.getLoginLanguage();
	}	// getLanguage

	/**
	 * Verify Language. Check that language is supported by the system
	 *
	 * @param language language
	 */
	public static void verifyLanguage(final Language language)
	{
		Check.assumeNotNull(language, "Parameter language is not null");
		final String searchAD_Language = language.getAD_Language();

		//
		// Get available languages, having BaseLanguage first and then System Language
		final List<String> AD_Languages = Services.get(ILanguageDAO.class).retrieveAvailableAD_LanguagesForMatching(getCtx());

		//
		// Check if we have a perfect match
		if(AD_Languages.contains(searchAD_Language))
		{
			return;
		}

		//
		// Pick a similar language (with different country code and/or variant)
		final String searchLangPart = searchAD_Language.substring(0, 2);
		for (final String AD_Language : AD_Languages)
		{
			final String lang = AD_Language.substring(0, 2); // en
			if (lang.equals(searchLangPart))
			{
				s_log.debug("Found similar Language {} for {}", AD_Language, language);
				language.setAD_Language(AD_Language);
				return;
			}
		}

		// No Language - set to Base Language
		final String baseAD_Language = Language.getBaseAD_Language();
		s_log.warn("Not System/Base Language={} - Set to Base Language: {}", language, baseAD_Language);
		language.setAD_Language(baseAD_Language);
	}   // verifyLanguage

	/**************************************************************************
	 * Get Context as String array with format: key == value
	 *
	 * @param ctx context
	 * @return context string
	 */
	public static String[] getEntireContext(final Properties ctx)
	{
		if (ctx == null)
			throw new IllegalArgumentException("Require Context");

		final Set<String> keys = ctx.stringPropertyNames();
		String[] sList = new String[keys.size()];
		int i = 0;
		for (final String key : keys)
		{
			final Object value = ctx.get(key);
			sList[i++] = String.valueOf(key) + " == " + value;
		}

		return sList;
	}	// getEntireContext

	/**
	 * Get Header info (connection, org, user).
	 *
	 * Uses {@link #CTXNAME_WindowName} from context to fetch the window name.
	 *
	 * @param ctx context
	 * @param WindowNo window
	 * @return Header String
	 */
	public static String getHeader(final Properties ctx, final int WindowNo)
	{
		StringBuilder sb = new StringBuilder();
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

		final String connectionInfo;
		if (Adempiere.isUnitTestMode())
		{
			connectionInfo = "no database connection";
		}
		else
		{
			connectionInfo = CConnection.get().toString();
		}

		sb.append(getContext(ctx, "#AD_User_Name")).append("@")
				.append(getContext(ctx, "#AD_Client_Name")).append(".")
				.append(getContext(ctx, "#AD_Org_Name"))
				.append(" [").append(connectionInfo).append("]");
		return sb.toString();
	}	// getHeader

	/**
	 * Clean up context for Window (i.e. delete it)
	 *
	 * @param ctx context
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
		if (Ini.isClient())
			removeWindow(WindowNo);
	}	// clearWinContext

	/**
	 * Parse Context replaces global or Window context @tag@ with actual value.
	 *
	 * @tag@ are ignored otherwise "" is returned
	 *
	 * @param ctx context
	 * @param WindowNo Number of Window
	 * @param value Message to be parsed
	 * @param onlyWindow if true, no defaults are used
	 * @param ignoreUnparsable if true, unsuccessful @return parsed String or "" if not successful and ignoreUnparsable
	 * @return parsed context
	 */
	public static String parseContext(Properties ctx, int WindowNo, String value, boolean onlyWindow, boolean ignoreUnparsable)
	{
		final Evaluatee evalCtx = Evaluatees.ofCtx(ctx, WindowNo, onlyWindow);
		final String valueParsed = parseContext(evalCtx, value, ignoreUnparsable);
		return valueParsed;
	}

	public static String parseContext(final Properties ctx, final int WindowNo, final IStringExpression expression, final boolean onlyWindow, final boolean ignoreUnparsable)
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

	public static String parseContext(final Evaluatee evalCtx, final IStringExpression expression, final boolean ignoreUnparsable)
	{
		return expression.evaluate(evalCtx, ignoreUnparsable);
	}	// parseContext

	/**
	 * Parse Context replaces global or Window context @tag@ with actual value.
	 *
	 * @param ctx context
	 * @param WindowNo Number of Window
	 * @param value Message to be parsed
	 * @param onlyWindow if true, no defaults are used
	 * @return parsed String or "" if not successful
	 */
	public static String parseContext(final Properties ctx, final int WindowNo, final String value, final boolean onlyWindow)
	{
		final boolean ignoreUnparsable = false;
		return parseContext(ctx, WindowNo, value, onlyWindow, ignoreUnparsable);
	}	// parseContext

	public static String parseContext(Properties ctx, int WindowNo, IStringExpression expression, boolean onlyWindow)
	{
		final boolean ignoreUnparsable = false;
		return parseContext(ctx, WindowNo, expression, onlyWindow, ignoreUnparsable);
	}	// parseContext


	/*************************************************************************/

	// Array of active Windows
	private static ArrayList<Container> s_windows = new ArrayList<Container>(20);

	/**
	 * Add Container and return WindowNo. The container is a APanel, AWindow or JFrame/JDialog
	 *
	 * @param win window
	 * @return WindowNo used for context
	 */
	public static int createWindowNo(final Container win)
	{
		int retValue = s_windows.size();
		s_windows.add(win);
		return retValue;
	}	// createWindowNo

	/**
	 * Search Window by comparing the Frames
	 *
	 * @param container container or <code>null</code>
	 * @return WindowNo of container or {@link #WINDOW_MAIN} if no WindowNo found for container.
	 */
	public static int getWindowNo(final Component container)
	{
		if (container == null)
		{
			return WINDOW_MAIN;
		}

		JFrame winFrame = null;

		//
		// Navigate up-stream
		{
			Component element = container;
			while (element != null)
			{
				if (element instanceof IWindowNoAware)
				{
					return ((IWindowNoAware)element).getWindowNo();
				}
				if (element instanceof JFrame)
				{
					winFrame = (JFrame)element;
					break;
				}
				element = element.getParent();
			}
		}

		if (winFrame == null)
		{
			return WINDOW_MAIN;
		}

		// loop through windows
		for (int i = 0; i < s_windows.size(); i++)
		{
			final Container cmp = s_windows.get(i);
			if (cmp != null)
			{
				final JFrame cmpFrame = getFrame(cmp);
				if (winFrame.equals(cmpFrame))
					return i;
			}
		}
		return WINDOW_MAIN;
	}	// getWindowNo

	/**
	 * Return the JFrame pointer of WindowNo.
	 *
	 * @param WindowNo window
	 * @return JFrame of WindowNo or <code>null</code> if not found or windowNo is invalid
	 */
	public static JFrame getWindow(int WindowNo)
	{
		if (WindowNo < 0)
		{
			return null;
		}

		try
		{
			return getFrame(s_windows.get(WindowNo));
		}
		catch (Exception e)
		{
			s_log.error("Failed getting frame for windowNo={}", WindowNo, e);
		}

		return null;
	}

	/**
	 * Remove window from active list
	 *
	 * @param WindowNo window
	 */
	private static void removeWindow(final int WindowNo)
	{
		if (WindowNo >= 0 && WindowNo < s_windows.size())
			s_windows.set(WindowNo, null);
	}	// removeWindow

	/**
	 * @return true if given windowNo is a valid windowNo and is for a regular window (not the main window)
	 */
	public static final boolean isRegularWindowNo(final int windowNo)
	{
		return windowNo > 0
				&& windowNo != WINDOW_None
				&& windowNo != WINDOW_MAIN;
	}

	/**
	 * @return true if given windowNo is a valid windowNo and is for a regular window or for main window
	 */
	public static final boolean isRegularOrMainWindowNo(final int windowNo)
	{
		return windowNo == WINDOW_MAIN
				|| isRegularWindowNo(windowNo);
	}

	/**
	 * Clean up context for Window (i.e. delete it)
	 *
	 * @param WindowNo window
	 */
	public static void clearWinContext(int WindowNo)
	{
		clearWinContext(getCtx(), WindowNo);
	}	// clearWinContext

	/**************************************************************************
	 * Get Frame of Window
	 *
	 * @param component AWT component
	 * @return JFrame of component or null
	 */
	public static JFrame getFrame(final Component component)
	{
		Component element = component;
		while (element != null)
		{
			if (element instanceof JFrame)
				return (JFrame)element;
			element = element.getParent();
		}
		return null;
	}	// getFrame

	/**
	 * Get Graphics of container or its parent. The element may not have a Graphic if not displayed yet, but the parent might have.
	 *
	 * @param container Container
	 * @return Graphics of container or null
	 */
	public static Graphics getGraphics(Container container)
	{
		Container element = container;
		while (element != null)
		{
			Graphics g = element.getGraphics();
			if (g != null)
				return g;
			element = element.getParent();
		}
		return null;
	}	// getFrame

	/**
	 * Return JDialog or JFrame Parent
	 *
	 * @param container Container
	 * @return JDialog or JFrame of container
	 */
	public static Window getParent(final Component container)
	{
		Component element = container;
		while (element != null)
		{
			if (element instanceof JDialog || element instanceof JFrame)
				return (Window)element;
			if (element instanceof Window)
				return (Window)element;
			element = element.getParent();
		}
		return null;
	}   // getParent

	/**
	 * Start Browser
	 *
	 * @param url url
	 * @see IClientUI#showURL(String).
	 */
	public static void startBrowser(String url)
	{
		s_log.info("Starting browser using url={}", url);
		Services.get(IClientUI.class).showURL(url);
	}   // startBrowser

	/**
	 * Do we run on Apple
	 *
	 * @return true if Mac
	 */
	public static boolean isMac()
	{
		String osName = System.getProperty("os.name");
		osName = osName.toLowerCase();
		return osName.indexOf("mac") != -1;
	}	// isMac

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
	}	// isWindows

	/** Array of hidden Windows */
	private static ArrayList<CFrame> s_hiddenWindows = new ArrayList<CFrame>();
	/** Closing Window Indicator */
	private static boolean s_closingWindows = false;

	/**
	 * Hide Window
	 *
	 * @param window window
	 * @return true if window is hidden, otherwise close it
	 */
	static public boolean hideWindow(CFrame window)
	{
		if (!Ini.isCacheWindow() || s_closingWindows)
			return false;
		for (int i = 0; i < s_hiddenWindows.size(); i++)
		{
			CFrame hidden = s_hiddenWindows.get(i);
			s_log.info("Checking hidden window {}: {}", i, hidden);
			if (hidden.getAD_Window_ID() == window.getAD_Window_ID())
				return false;	// already there
		}

		if (window.getAD_Window_ID() > 0)         	// workbench
		{
			if (s_hiddenWindows.add(window))
			{
				window.setVisible(false);
				s_log.info("Added to hidden windows list: {}", window);
				// window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_ICONIFIED));
				if (s_hiddenWindows.size() > 10)
				{
					CFrame toClose = s_hiddenWindows.remove(0);		// sort of lru
					try
					{
						s_closingWindows = true;
						toClose.dispose();
					}
					finally
					{
						s_closingWindows = false;
					}
				}
				return true;
			}
		}
		return false;
	}	// hideWindow

	/**
	 * Show Window
	 *
	 * @param AD_Window_ID window
	 * @return {@link CFrame} or <code>null</code> if not found
	 */
	public static CFrame showWindow(final int AD_Window_ID)
	{
		for (int i = 0; i < s_hiddenWindows.size(); i++)
		{
			CFrame hidden = s_hiddenWindows.get(i);
			if (hidden.getAD_Window_ID() == AD_Window_ID)
			{
				s_hiddenWindows.remove(i); // NOTE: we can safely remove here because we are also returning (no future iterations)
				s_log.info("Showing window: {}", hidden);
				hidden.setVisible(true);
				// De-iconify window - teo_sarca [ 1707221 ]
				int state = hidden.getExtendedState();
				if ((state & CFrame.ICONIFIED) > 0)
					hidden.setExtendedState(state & ~CFrame.ICONIFIED);
				//
				hidden.toFront();
				return hidden;
			}
		}
		return null;
	}	// showWindow

	/**
	 * Clode Windows.
	 */
	static void closeWindows()
	{
		s_closingWindows = true;
		for (int i = 0; i < s_hiddenWindows.size(); i++)
		{
			final CFrame hidden = s_hiddenWindows.get(i);
			hidden.dispose();
		}
		s_hiddenWindows.clear();
		s_closingWindows = false;
	}	// closeWindows

	/**
	 * Sleep
	 *
	 * @param sec seconds
	 */
	public static void sleep(final int sec)
	{
		s_log.debug("Sleeping for {} seconds", sec);
		try
		{
			Thread.sleep(sec * 1000);
		}
		catch (Exception e)
		{
			s_log.warn("Failed sleeping for {} seconds", sec, e);
		}
		s_log.debug("Sleeping done");
	}	// sleep

	/**
	 * Update all windows after look and feel changes.
	 *
	 * @since 2006-11-27
	 */
	public static Set<Window> updateUI()
	{
		Set<Window> updated = new HashSet<Window>();
		for (Container c : s_windows)
		{
			Window w = getFrame(c);
			if (w == null)
				continue;
			if (updated.contains(w))
				continue;
			SwingUtilities.updateComponentTreeUI(w);
			w.validate();
			RepaintManager mgr = RepaintManager.currentManager(w);
			Component childs[] = w.getComponents();
			for (Component child : childs)
			{
				if (child instanceof JComponent)
					mgr.markCompletelyDirty((JComponent)child);
			}
			w.repaint();
			updated.add(w);
		}
		for (Window w : s_hiddenWindows)
		{
			if (updated.contains(w))
				continue;
			SwingUtilities.updateComponentTreeUI(w);
			w.validate();
			RepaintManager mgr = RepaintManager.currentManager(w);
			Component childs[] = w.getComponents();
			for (Component child : childs)
			{
				if (child instanceof JComponent)
					mgr.markCompletelyDirty((JComponent)child);
			}
			w.repaint();
			updated.add(w);
		}
		return updated;
	}

	/**
	 * Prepare the context for calling remote server (for e.g, ejb), only default and global variables are pass over.
	 * It is too expensive and also can have serialization issue if every remote call to server is passing the whole client context.
	 *
	 * @param ctx
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
	static final public BigDecimal ZERO = new BigDecimal(0.0);

	/**
	 * Big Decimal 1
	 *
	 * @deprecated please use {@link BigDecimal#ONE} instead.
	 */
	@Deprecated
	static final public BigDecimal ONE = new BigDecimal(1.0);

	/** Big Decimal 100 */
	static final public BigDecimal ONEHUNDRED = new BigDecimal(100.0);

	/** New Line */
	public static final String NL = System.getProperty("line.separator");

	// metas: begin
	public static final int WINDOW_None = -100;
	public static final int TAB_None = -100;

	// /* package */ static final String NoValue = "";

	/** Marker used to flag a NULL ID */
	private static final String CTXVALUE_NullID = new String("0"); // NOTE: new String to make sure it's a unique instance
	/** Default context value (int) */
	/* package */static final int CTXVALUE_NoValueInt = 0;
	/**
	 * Marker used to flag a NULL String
	 *
	 * NOTE: this is the value returned by getContext methods when no value found
	 */
	// NOTE: before changing this to some other value, please evaluate where the result of getContext variables is compared with hardcoded ""
	/* package */static final String CTXVALUE_NullString = new String(""); // NOTE: new String to make sure it's a unique instance

	public static enum Scope
	{
		// Please note that the order is VERY important.
		// Scopes should be ordered by priority, from lower to higher
		Exact(0), Global(100), Window(200), Tab(300);

		private final int priority;

		private Scope(int priority)
		{
			this.priority = priority;
		}

		public int getPriority()
		{
			return this.priority;
		}
	}

	/**
	 *
	 * @param ctx
	 * @param WindowNo
	 * @param TabNo
	 * @param context
	 * @param scope
	 * @return value or {@link #CTXVALUE_NullString}
	 */
	public static String getContext(final Properties ctx, final int WindowNo, final int TabNo, final String context, final Scope scope)
	{
		Check.assumeNotNull(ctx, "ctx not null");

		final CtxName name = CtxName.parse(context);
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
					isExplicitGlobal = false;
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
				scopeActual = Scope.Window;
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
	 * @param ctx
	 * @param context
	 * @return string value or <code>null</code> if it does not exist
	 */
	private static final String getProperty(final Properties ctx, final String context)
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
				value = toString(SystemTime.asDayTimestamp());
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
			return;

		setProperty(ctx, WindowNo + "|" + TabNo + "|" + context, String.valueOf(value));
	}	// setContext

	public static Timestamp getContextAsDate(Properties ctx, int WindowNo, String context, boolean onlyWindow)
	{
		String s = getContext(ctx, WindowNo, context, onlyWindow);
		// JDBC Format YYYY-MM-DD example 2000-09-11 00:00:00.0
		if (isPropertyValueNull(s) || "".equals(s))
		{
			s_log.error("No value for: {}", context);
			return new Timestamp(System.currentTimeMillis());
		}
		return parseTimestamp(s);
	}

	public static Timestamp getContextAsDate(Properties ctx, int WindowNo, int TabNo, String context)
	{
		return getContextAsDate(ctx, WindowNo, TabNo, context, false);
	}

	public static Timestamp getContextAsDate(Properties ctx, int WindowNo, int TabNo, String context, boolean onlyTab)
	{
		String s = getContext(ctx, WindowNo, TabNo, context, onlyTab);
		// JDBC Format YYYY-MM-DD example 2000-09-11 00:00:00.0
		if (isPropertyValueNull(s) || "".equals(s))
		{
			s_log.error("No value for: {}", context);
			return new Timestamp(System.currentTimeMillis());
		}
		return parseTimestamp(s);
	}	// getContextAsDate

	public static void setContextAsDate(Properties ctx, int WindowNo, int TabNo, String context, Date value)
	{
		if (ctx == null || context == null)
			return;

		setProperty(ctx, WindowNo + "|" + TabNo + "|" + context, toString(value));
	}	// setContext

	/**
	 * Convert the string value to integer
	 *
	 * @param s string value
	 * @param context context name that was required (used only for logging)
	 * @return int value
	 */
	private static final int toInteger(String s, String context)
	{
		if (CTXVALUE_NullString.equals(s))
			return CTXVALUE_NoValueInt;

		try
		{
			return Integer.parseInt(s);
		}
		catch (NumberFormatException e)
		{
			s_log.error("Failed converting {}'s value {} to integer", context, s, e);
		}
		return CTXVALUE_NoValueInt;
	}

	/**
	 * Convert given timestamp to string.
	 *
	 * @param timestamp
	 * @return timestamp as string (JDBC Format 2005-05-09 00:00:00, without nanos) or <code>null</code> if timestamp was null
	 * @see #parseTimestamp(String)
	 */
	public static final String toString(final Date date)
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
	 * @see #toString(Timestamp)
	 */
	public static Timestamp parseTimestamp(String timestampStr)
	{
		// JDBC Format YYYY-MM-DD example 2000-09-11 00:00:00.0
		if (timestampStr == null || timestampStr.isEmpty() || isPropertyValueNull(timestampStr))
		{
			return null;
		}

		// timestamp requires time
		timestampStr = timestampStr.trim();
		if (timestampStr.length() == 10)
			timestampStr = timestampStr + " 00:00:00.0";
		else if (timestampStr.indexOf('.') == -1)
			timestampStr = timestampStr + ".0";

		return Timestamp.valueOf(timestampStr);
	}

	public static final String toString(final boolean value)
	{
		return DisplayType.toBooleanString(value);
	}

	private static final boolean isPropertyValueNull(final String value)
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
	 * @param propertyName
	 * @param value
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
	 *
	 * @param propertyName
	 * @return true if given propertyName is for a numeric value (i.e. if it ends with "_ID")
	 */
	public static final boolean isNumericPropertyName(final String propertyName)
	{
		if (propertyName == null)
		{
			return false;
		}

		// TODO: Research potential problems with tables with Record_ID=0
		return propertyName.endsWith("_ID");
	}

	private static final String getNullPropertyValue(final String propertyName)
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
	 *
	 * Being the same means that they have the same AD_Client_ID, AD_Org_ID, AD_Role_ID, AD_User_ID and AD_Session_ID.
	 *
	 * @param ctx1
	 * @param ctx2
	 * @return
	 */
	public static final boolean isSameSession(final Properties ctx1, final Properties ctx2)
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
	 *
	 * @param ctx1
	 * @param ctx2
	 * @return true if given contexts are exactly the same (compared by reference)
	 */
	public static final boolean isSame(final Properties ctx1, final Properties ctx2)
	{
		return ctx1 == ctx2;
	}

	// metas: end

	public static Adempiere getSingleAdempiereInstance()
	{
		return Adempiere.instance;
	}

	/**
	 * Helper method to bind <code>@Autowire</code> annotated properties of given bean using current Spring Application Context.
	 *
	 * @param bean
	 */
	public static void autowireBean(final Object bean)
	{
		Adempiere.getSpringApplicationContext().getAutowireCapableBeanFactory().autowireBean(bean);
	}

	/**
	 * Gets Login/System date
	 *
	 * @param ctx
	 * @return login/system date; never return null
	 */
	public static Timestamp getDate(final Properties ctx)
	{
		return getContextAsDate(ctx, WINDOW_MAIN, CTXNAME_Date);
	}

	/**
	 * @return value or <code>null</code> if the value was not present and value initializer was null
	 */
	public static <V> V get(final Properties ctx, final String propertyName)
	{
		@SuppressWarnings("unchecked")
		final V value = (V)ctx.get(propertyName);
		return value;
	}

	/**
	 * Gets the value identified by <code>propertyName</code> from given <code>ctx</code>.
	 *
	 * If the value is <code>null</code> (or not present) and a value initializer is provided
	 * than that value initializer will be used to initialize the value.
	 *
	 * This method is thread safe.
	 *
	 * @param ctx
	 * @param propertyName
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
	 *
	 * If an validator is provided then it will be used to check if the cached value (if any) is still valid.
	 *
	 * If the value is <code>null</code> (or not present, or not valid) and a value initializer is provided
	 * than that value initializer will be used to initialize the value.
	 *
	 * This method is thread safe.
	 *
	 * @param ctx
	 * @param propertyName
	 * @param validator optional value validator
	 * @param valueInitializer optional value initializer to be used when the value was not found or it's null in <code>ctx</code>
	 * @return value or <code>null</code> if the value was not present and value initializer was null
	 */
	public static <V> V getAndValidate(final Properties ctx, final String propertyName, final Predicate<V> validator, final Supplier<V> valueInitializer)
	{
		Check.assumeNotNull(ctx, "ctx not null");

		// NOTE: we a synchronizing on "ctx" because the Hashtable methods of "Properties ctx" are declared as "synchronized"
		// and we want to get the same effect and synchronize with them.
		synchronized (ctx)
		{
			// Get the existing value. We assume it has the type "V".
			@SuppressWarnings("unchecked")
			V value = (V)ctx.get(propertyName);

			// Check if cached value it's still valid
			if (validator != null && value != null && !validator.evaluate(value))
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
		@SuppressWarnings("unchecked")
		final V value = (V)ctx.remove(propertyName);
		return value;
	}

	public static void put(final Properties ctx, final String propertyName, final Object value)
	{
		ctx.put(propertyName, value);
	}

	/**
	 * Checks if given key is contained in context.
	 *
	 * WARNING: this method is NOT checking the key exists in underlying "defaults". Before changing this please check the API which depends on this logic
	 *
	 * @param ctx
	 * @param key
	 * @return true if given key is contained in context
	 */
	public static boolean containsKey(final Properties ctx, final String key)
	{
		return ctx.containsKey(key);
	}

	/**
	 * Returns given <code>ctx</code> or {@link #getCtx()} if null.
	 *
	 * @param ctx
	 * @return ctx or {@link #getCtx()}; never returns null
	 */
	public static final Properties coalesce(final Properties ctx)
	{
		return ctx == null ? getCtx() : ctx;
	}
}   // Env
