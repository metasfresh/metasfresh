/******************************************************************************
 * Product: Posterita Ajax UI 												  *
 * Copyright (C) 2007 Posterita Ltd.  All Rights Reserved.                    *
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
 * Posterita Ltd., 3, Draper Avenue, Quatre Bornes, Mauritius                 *
 * or via info@posterita.org or http://www.posterita.org/                     *
 *****************************************************************************/

package org.adempiere.webui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import org.adempiere.acct.api.IPostingService;
import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;
import org.adempiere.webui.apps.AEnv;
import org.adempiere.webui.component.DrillCommand;
import org.adempiere.webui.component.TokenCommand;
import org.adempiere.webui.component.ZoomCommand;
import org.adempiere.webui.desktop.DefaultDesktop;
import org.adempiere.webui.desktop.IDesktop;
import org.adempiere.webui.event.TokenEvent;
import org.adempiere.webui.session.SessionContextListener;
import org.adempiere.webui.session.SessionManager;
import org.adempiere.webui.theme.ITheme;
import org.adempiere.webui.theme.ThemeManager;
import org.adempiere.webui.util.BrowserToken;
import org.adempiere.webui.util.UserPreference;
import org.compiere.model.MSession;
import org.compiere.model.MSystem;
import org.compiere.model.MUser;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.compiere.util.Language;
import org.zkoss.zk.au.Command;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.ClientInfoEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.impl.ExecutionCarryOver;
import org.zkoss.zk.ui.sys.DesktopCache;
import org.zkoss.zk.ui.sys.DesktopCtrl;
import org.zkoss.zk.ui.sys.ExecutionCtrl;
import org.zkoss.zk.ui.sys.ExecutionsCtrl;
import org.zkoss.zk.ui.sys.SessionCtrl;
import org.zkoss.zk.ui.sys.Visualizer;
import org.zkoss.zul.Window;

/**
 *
 * @author  <a href="mailto:agramdass@gmail.com">Ashley G Ramdass</a>
 * @date    Feb 25, 2007
 * @version $Revision: 0.10 $
 *
 * @author hengsin
 */
public class AdempiereWebUI extends Window implements EventListener, IWebClient
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3744725245132180915L;

	public static final String APP_NAME = "Adempiere";

    public static final String UID          = "3.5";

    private WLogin             loginDesktop;

    private IDesktop           appDesktop;

    private ClientInfo		   clientInfo;

	private String langSession;

	private UserPreference userPreference;

	private static final CLogger logger = CLogger.getCLogger(AdempiereWebUI.class);

	public static final String EXECUTION_CARRYOVER_SESSION_KEY = "execution.carryover";

	public static final String ZK_DESKTOP_SESSION_KEY = "zk.desktop";

    public AdempiereWebUI()
    {
    	this.addEventListener(Events.ON_CLIENT_INFO, this);
    	this.setVisible(false);

    	userPreference = new UserPreference();
    }

    public void onCreate()
    {
    	final ITheme theme = ThemeManager.getThemeImpl();
        this.getPage().setTitle(theme.getBrowserTitle());

        Properties ctx = Env.getCtx();
        langSession = Env.getContext(ctx, Env.CTXNAME_AD_Language);
        SessionManager.setSessionApplication(this);
        Session session = Executions.getCurrent().getDesktop().getSession();
        if (session.getAttribute(SessionContextListener.SESSION_CTX) == null || !SessionManager.isUserLoggedIn(ctx))
        {
            loginDesktop = new WLogin(this);
            loginDesktop.createPart(this.getPage());
        }
        else
        {
            loginCompleted();
        }
    }

    public void onOk()
    {
    }

    public void onCancel()
    {
    }

    /* (non-Javadoc)
	 * @see org.adempiere.webui.IWebClient#loginCompleted()
	 */
    @Override
	public void loginCompleted()
    {
    	if (loginDesktop != null)
    	{
    		loginDesktop.detach();
    		loginDesktop = null;
    	}

        Properties ctx = Env.getCtx();
        String langLogin = Env.getContext(ctx, Env.CTXNAME_AD_Language);
        if (langLogin == null || langLogin.length() <= 0)
        {
        	langLogin = langSession;
        	Env.setContext(ctx, Env.CTXNAME_AD_Language, langSession);
        }

        // Validate language
		Language language = Language.getLanguage(langLogin);
		String locale = Env.getContext(ctx, AEnv.LOCALE);
		if (locale != null && locale.length() > 0 && !language.getLocale().toString().equals(locale))
		{
			String adLanguage = language.getAD_Language();
			Language tmp = Language.getLanguage(locale);
			language = new Language(tmp.getName(), adLanguage, tmp.getLocale(), tmp.isDecimalPoint(),
	    			tmp.getDateFormat().toPattern(), tmp.getMediaSize());
		}
		else
		{
			Language tmp = language;
			language = new Language(tmp.getName(), tmp.getAD_Language(), tmp.getLocale(), tmp.isDecimalPoint(),
	    			tmp.getDateFormat().toPattern(), tmp.getMediaSize());
		}
    	Env.verifyLanguage(ctx, language);
    	Env.setContext(ctx, Env.CTXNAME_AD_Language, language.getAD_Language()); //Bug

		//	Create adempiere Session - user id in ctx
        Session currSess = Executions.getCurrent().getDesktop().getSession();
        HttpSession httpSess = (HttpSession) currSess.getNativeSession();

		MSession mSession = MSession.get (ctx, currSess.getRemoteAddr(),
			currSess.getRemoteHost(), httpSess.getId() );

		//enable full interface, relook into this when doing preference
		Env.setContext(ctx, "#ShowTrl", true);
		Env.setContext(ctx, Env.CTXNAME_ShowAcct,
				Services.get(IPostingService.class).isEnabled()
				&& Env.getUserRolePermissions().hasPermission(IUserRolePermissions.PERMISSION_ShowAcct));
		Env.setContext(ctx, "#ShowAdvanced", true);

		// to reload preferences when the user refresh the browser
		userPreference = loadUserPreference(Env.getAD_User_ID(ctx));
		
		//auto commit user preference
		String autoCommit = userPreference.getProperty(UserPreference.P_AUTO_COMMIT);
		Env.setAutoCommit(ctx, "true".equalsIgnoreCase(autoCommit) || "y".equalsIgnoreCase(autoCommit));

		//auto new user preference
		String autoNew = userPreference.getProperty(UserPreference.P_AUTO_NEW);
		Env.setAutoNew(ctx, "true".equalsIgnoreCase(autoNew) || "y".equalsIgnoreCase(autoNew));

		IDesktop d = (IDesktop) currSess.getAttribute("application.desktop");
		if (d != null && d instanceof IDesktop)
		{
			ExecutionCarryOver eco = (ExecutionCarryOver) currSess.getAttribute(EXECUTION_CARRYOVER_SESSION_KEY);
			if (eco != null) {
				//try restore
				try {
					appDesktop = d;

					ExecutionCarryOver current = new ExecutionCarryOver(this.getPage().getDesktop());
					ExecutionCtrl ctrl = ExecutionsCtrl.getCurrentCtrl();
					Visualizer vi = ctrl.getVisualizer();
					eco.carryOver();
					Collection<Component> rootComponents = new ArrayList<Component>();
					try {
						ctrl = ExecutionsCtrl.getCurrentCtrl();
						((DesktopCtrl)Executions.getCurrent().getDesktop()).setVisualizer(vi);

						//detach root component from old page
						Page page = appDesktop.getComponent().getPage();
						Collection<?> collection = page.getRoots();
						Object[] objects = new Object[0];
						objects = collection.toArray(objects);
						for(Object obj : objects) {
							if (obj instanceof Component) {
								((Component)obj).detach();
								rootComponents.add((Component) obj);
							}
						}
						appDesktop.getComponent().detach();
						DesktopCache desktopCache = ((SessionCtrl)currSess).getDesktopCache();
						if (desktopCache != null)
							desktopCache.removeDesktop(Executions.getCurrent().getDesktop());
					} catch (Exception e) {
						appDesktop = null;
					} finally {
						eco.cleanup();
						current.carryOver();
					}

					if (appDesktop != null) {
						//re-attach root components
						for (Component component : rootComponents) {
							try {
								component.setPage(this.getPage());
							} catch (UiException e) {
								// e.printStackTrace();
								// an exception is thrown here when refreshing the page, it seems is harmless to catch and ignore it
								// i.e.: org.zkoss.zk.ui.UiException: Not unique in the ID space of [Page z_kg_0]: zk_comp_2
							}
						}
						appDesktop.setPage(this.getPage());
						currSess.setAttribute(EXECUTION_CARRYOVER_SESSION_KEY, current);
					}
					
					currSess.setAttribute(ZK_DESKTOP_SESSION_KEY, this.getPage().getDesktop());
				} catch (Throwable t) {
					//restore fail
					appDesktop = null;
				}

			}
		}

		if (appDesktop == null)
		{
			//create new desktop
			createDesktop();
			appDesktop.setClientInfo(clientInfo);
			appDesktop.createPart(this.getPage());
			currSess.setAttribute("application.desktop", appDesktop);
			ExecutionCarryOver eco = new ExecutionCarryOver(this.getPage().getDesktop());
			currSess.setAttribute(EXECUTION_CARRYOVER_SESSION_KEY, eco);
			currSess.setAttribute(ZK_DESKTOP_SESSION_KEY, this.getPage().getDesktop());
		}
		
		if ("Y".equalsIgnoreCase(Env.getContext(ctx, BrowserToken.REMEMBER_ME)) && MSystem.isZKRememberUserAllowed())
		{
			MUser user = MUser.get(ctx);
			BrowserToken.save(mSession, user);
		}
		else
		{
			BrowserToken.remove();
		}
    }

    private void createDesktop()
    {
    	appDesktop = null;
		String className = Services.get(ISysConfigBL.class).getValue(IDesktop.CLASS_NAME_KEY);
		if ( className != null && className.trim().length() > 0)
		{
			try
			{
				Class<?> clazz = this.getClass().getClassLoader().loadClass(className);
				appDesktop = (IDesktop) clazz.newInstance();
			}
			catch (Throwable t)
			{
				logger.warning("Failed to instantiate desktop. Class=" + className);
			}
		}
		//fallback to default
		if (appDesktop == null)
			appDesktop = new DefaultDesktop();
	}

	/* (non-Javadoc)
	 * @see org.adempiere.webui.IWebClient#logout()
	 */
    @Override
	public void logout()
    {
    	appDesktop.logout();
    	Executions.getCurrent().getDesktop().getSession().getAttributes().clear();

    	MSession mSession = MSession.get(Env.getCtx(), false);
    	if (mSession != null) {
    		mSession.logout();
    	}

        SessionManager.clearSession();
        super.getChildren().clear();
        Page page = this.getPage();
        page.removeComponents();
        Executions.sendRedirect("index.zul");
    }

    /**
     * @return IDesktop
     */
    @Override
	public IDesktop getAppDeskop()
    {
    	return appDesktop;
    }

	@Override
	public void onEvent(Event event) {
		if (event instanceof ClientInfoEvent) {
			ClientInfoEvent c = (ClientInfoEvent)event;
			clientInfo = new ClientInfo();
			clientInfo.colorDepth = c.getColorDepth();
			clientInfo.desktopHeight = c.getDesktopHeight();
			clientInfo.desktopWidth = c.getDesktopWidth();
			clientInfo.desktopXOffset = c.getDesktopXOffset();
			clientInfo.desktopYOffset = c.getDesktopYOffset();
			clientInfo.timeZone = c.getTimeZone();
			if (appDesktop != null)
				appDesktop.setClientInfo(clientInfo);
		}

	}

	/**
	 * @param userId
	 * @return UserPreference
	 */
	@Override
	public UserPreference loadUserPreference(int userId) {
		userPreference.loadPreference(userId);
		return userPreference;
	}

	/**
	 * @return UserPrerence
	 */
	@Override
	public UserPreference getUserPreference() {
		return userPreference;
	}
	
	//global command
	static {
		new ZoomCommand("onZoom", Command.IGNORE_OLD_EQUIV);
		new DrillCommand("onDrillAcross", Command.IGNORE_OLD_EQUIV);
		new DrillCommand("onDrillDown", Command.IGNORE_OLD_EQUIV);
		new TokenCommand(TokenEvent.ON_USER_TOKEN, Command.IGNORE_OLD_EQUIV);
	}
}
