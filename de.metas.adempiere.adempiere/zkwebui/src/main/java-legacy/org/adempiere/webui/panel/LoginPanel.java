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
 *                                                                            *
 * Contributors:                                                              *
 * - Heng Sin Low                                                             *
 *                                                                            *
 * Sponsors:                                                                  *
 * - Idalica Corporation                                                      *
 *****************************************************************************/

package org.adempiere.webui.panel;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.servlet.http.HttpSession;

import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.user.api.IUserBL;
import org.adempiere.user.api.IUserDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.webui.LayoutUtils;
import org.adempiere.webui.apps.AEnv;
import org.adempiere.webui.component.Combobox;
import org.adempiere.webui.component.ConfirmPanel;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.Textbox;
import org.adempiere.webui.component.Window;
import org.adempiere.webui.event.TokenEvent;
import org.adempiere.webui.exception.ApplicationException;
import org.adempiere.webui.session.SessionManager;
import org.adempiere.webui.theme.ITheme;
import org.adempiere.webui.theme.ThemeManager;
import org.adempiere.webui.util.BrowserToken;
import org.adempiere.webui.window.LoginWindow;
import org.compiere.Adempiere;
import org.compiere.apps.ALoginRes;
import org.compiere.model.I_AD_User;
import org.compiere.model.MSession;
import org.compiere.model.MSystem;
import org.compiere.model.MUser;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Language;
import org.compiere.util.Login;
import org.compiere.util.Msg;
import org.compiere.util.ValueNamePair;
import org.slf4j.Logger;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.logging.LogManager;
import org.zkoss.lang.Strings;
import org.zkoss.util.Locales;
import org.zkoss.zhtml.Div;
import org.zkoss.zhtml.Table;
import org.zkoss.zhtml.Td;
import org.zkoss.zhtml.Tr;
import org.zkoss.zk.au.out.AuFocus;
import org.zkoss.zk.au.out.AuScript;
import org.zkoss.zk.fn.ZkFns;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Image;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.mesg.MZul;

import de.metas.adempiere.model.I_AD_Session;
import de.metas.hostkey.api.IHostKeyBL;
import de.metas.logging.MetasfreshLastError;
import de.metas.ui.web.base.session.UserPreference;

/**
 *
 * @author  <a href="mailto:agramdass@gmail.com">Ashley G Ramdass</a>
 * @date    Feb 25, 2007
 * @version $Revision: 0.10 $
 * @author <a href="mailto:sendy.yagambrum@posterita.org">Sendy Yagambrum</a>
 * @date    July 18, 2007
 */
public class LoginPanel extends Window implements EventListener
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3992171368813030624L;
	private static final String RESOURCE = ALoginRes.class.getName();
    private ResourceBundle res = ResourceBundle.getBundle(RESOURCE);
    private static Logger logger = LogManager.getLogger(LoginPanel.class);

    private final Properties ctx;
    private final Label lblTitle = new Label(); // metas
    private Label lblUserId;
    private Label lblPassword;
    private Label lblLanguage;
    private Textbox txtUserId;
    private Textbox txtPassword;
    private Combobox lstLanguage;
    private final LoginWindow wndLogin;
    private Checkbox chkRememberMe;
    private Toolbarbutton btnForgotPassword; // metas

    public LoginPanel(Properties ctx, LoginWindow loginWindow)
    {
        this.ctx = ctx;
        this.wndLogin = loginWindow;
        initComponents();
        init();
        this.setId("loginPanel");

        AuFocus auf = new AuFocus(txtUserId);
        Clients.response(auf);
        
        BrowserToken.load(this.getUuid());
    }

    private void init()
    {
    	final ITheme theme = ThemeManager.getThemeImpl();
    	Div div = new Div();
    	div.setSclass(theme.getCssName(ITheme.LOGIN_BOX_HEADER_CLASS));
    	lblTitle.setSclass(theme.getCssName(ITheme.LOGIN_BOX_HEADER_TXT_CLASS));
    	div.appendChild(lblTitle);
    	this.appendChild(div);

    	Table table = new Table();
    	table.setId("grdLogin");
    	table.setDynamicProperty("cellpadding", "0");
    	table.setDynamicProperty("cellspacing", "5");
    	table.setSclass(theme.getCssName(ITheme.LOGIN_BOX_BODY_CLASS));

    	this.appendChild(table);

    	Tr tr = new Tr();
    	table.appendChild(tr);
    	Td td = new Td();
    	td.setSclass(theme.getCssName(ITheme.LOGIN_BOX_HEADER_LOGO_CLASS));
    	tr.appendChild(td);
    	td.setDynamicProperty("colspan", "2");
    	
    	Image image = new Image();
    	if (theme.getLargeLogo() != null)
    		image.setSrc(theme.getLargeLogo());
    	else
    		image.setContent(theme.getLargeLogoImage());
        td.appendChild(image);

        tr = new Tr();
        tr.setId("rowUser");
        table.appendChild(tr);
    	td = new Td();
    	tr.appendChild(td);
    	td.setSclass(theme.getCssName(ITheme.LOGIN_LABEL_CLASS));
    	td.appendChild(lblUserId);
    	td = new Td();
    	td.setSclass(theme.getCssName(ITheme.LOGIN_FIELD_CLASS));
    	tr.appendChild(td);
    	td.appendChild(txtUserId);

    	tr = new Tr();
        tr.setId("rowPassword");
        table.appendChild(tr);
    	td = new Td();
    	tr.appendChild(td);
    	td.setSclass(theme.getCssName(ITheme.LOGIN_LABEL_CLASS));
    	td.appendChild(lblPassword);
    	td = new Td();
    	td.setSclass(theme.getCssName(ITheme.LOGIN_FIELD_CLASS));
    	tr.appendChild(td);
    	td.appendChild(txtPassword);

    	tr = new Tr();
        tr.setId("rowLanguage");
        table.appendChild(tr);
    	td = new Td();
    	tr.appendChild(td);
    	td.setSclass(theme.getCssName(ITheme.LOGIN_LABEL_CLASS));
    	td.appendChild(lblLanguage);
    	td = new Td();
    	td.setSclass(theme.getCssName(ITheme.LOGIN_FIELD_CLASS));
    	tr.appendChild(td);
    	td.appendChild(lstLanguage);

    	if (MSystem.isZKRememberUserAllowed()) {
        	tr = new Tr();
            tr.setId("rowRememberMe");
            table.appendChild(tr);
        	td = new Td();
        	tr.appendChild(td);
        	td.setSclass(theme.getCssName(ITheme.LOGIN_LABEL_CLASS));
        	td.appendChild(new Label(""));
        	td = new Td();
        	td.setSclass(theme.getCssName(ITheme.LOGIN_FIELD_CLASS));
        	tr.appendChild(td);
        	td.appendChild(chkRememberMe);
    	}

		// metas: begin
    	tr = new Tr();
        tr.setId("rowForgotPassword");
        table.appendChild(tr);
    	td = new Td();
//    	td.setSclass(ITheme.LOGIN_LABEL_CLASS);
    	td.setDynamicProperty("colspan", "2");
//    	td.setStyle("align: left;");
    	tr.appendChild(td);
    	td.appendChild(btnForgotPassword);
    	btnForgotPassword.addEventListener(Events.ON_CLICK, new EventListener()
		{
			@Override
			public void onEvent(Event event) throws Exception
			{
				doForgotPassword();
			}
		});
		// metas: end
    	
    	div = new Div();
    	div.setSclass(theme.getCssName(ITheme.LOGIN_BOX_FOOTER_CLASS));
        ConfirmPanel pnlButtons = new ConfirmPanel(false);
        pnlButtons.addActionListener(this);
        LayoutUtils.addSclass(theme.getCssName(ITheme.LOGIN_BOX_FOOTER_PANEL_CLASS), pnlButtons);
        pnlButtons.setWidth(null);
        pnlButtons.getButton(ConfirmPanel.A_OK).setSclass(theme.getCssName(ITheme.LOGIN_BUTTON_CLASS));
        div.appendChild(pnlButtons);
        this.appendChild(div);
        
        this.addEventListener(TokenEvent.ON_USER_TOKEN, new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				String[] data = (String[]) event.getData();
				try 
				{
					final int AD_Session_ID = Integer.parseInt(data[0]);
					final I_AD_Session session = new Query(ctx, I_AD_Session.Table_Name, I_AD_Session.COLUMNNAME_AD_Session_ID + "=?", null)
							.setParameters(AD_Session_ID)
							.firstOnly(I_AD_Session.class);
					if (session != null && session.getAD_Session_ID() == AD_Session_ID)
					{
						int AD_User_ID = session.getCreatedBy();
						I_AD_User user = MUser.get(Env.getCtx(), AD_User_ID);
						if (user != null && user.getAD_User_ID() == AD_User_ID)
						{
						    String token = data[1];  
						    if (BrowserToken.validateToken(session, user, token))
						    {
						    	if (MSystem.isZKRememberUserAllowed()) {
						    		String username = session.getLoginUsername();
						    		if (Check.isEmpty(username, true))
						    			username = user.getName();
						    		txtUserId.setValue(username);
							    	onUserIdChange();
							    	chkRememberMe.setChecked(true);
						    	}
						    	if (MSystem.isZKRememberPasswordAllowed()) {
							    	txtPassword.setValue(token);
							    	txtPassword.setAttribute("user.token.hash", token);
							    	txtPassword.setAttribute("user.token.sid", AD_Session_ID);
						    	}
						    }
						}
					}
				} catch (Exception e) {
					//safe to ignore
					logger.info(e.getLocalizedMessage(), e);
				}
			}
		});
    }

    private void initComponents()
    {
        lblUserId = new Label();
        lblUserId.setId("lblUserId");
        lblUserId.setValue("User ID");

        lblPassword = new Label();
        lblPassword.setId("lblPassword");
        lblPassword.setValue("Password");

        lblLanguage = new Label();
        lblLanguage.setId("lblLanguage");
        lblLanguage.setValue("Language");

        txtUserId = new Textbox();
        txtUserId.setId("txtUserId");
        txtUserId.setCols(25);
        txtUserId.setMaxlength(40);
        txtUserId.setWidth("220px");
        txtUserId.addEventListener(Events.ON_CHANGE, this); // Elaine 2009/02/06

        txtPassword = new Textbox();
        txtPassword.setId("txtPassword");
        txtPassword.setType("password");
        txtPassword.setCols(25);
//        txtPassword.setMaxlength(40);
        txtPassword.setWidth("220px");

        lstLanguage = new Combobox();
        lstLanguage.setAutocomplete(true);
        lstLanguage.setAutodrop(true);
        lstLanguage.setId("lstLanguage");
        lstLanguage.addEventListener(Events.ON_SELECT, this);
        lstLanguage.setWidth("220px");

        // Update Language List
        lstLanguage.getItems().clear();
        String[] availableLanguages = Language.getNames();
        for (String langName : availableLanguages) {
    		Language language = Language.getLanguage(langName);
			lstLanguage.appendItem(langName, language.getAD_Language());
		}

        chkRememberMe = new Checkbox(Msg.translate(ctx, "login.RememberMe")); // metas: US743
        btnForgotPassword = new Toolbarbutton(Msg.translate(ctx, "ForgotPasswordButton")); // metas: US743  

        //set base language
        String baseLanguage = Language.getBaseLanguage().getName();
        for(int i = 0; i < lstLanguage.getItemCount(); i++)
        {
        	Comboitem li = lstLanguage.getItemAtIndex(i);
        	if(li.getLabel().equals(baseLanguage))
        	{
        		lstLanguage.setSelectedIndex(i);
        		languageChanged(li.getLabel());
        		break;
        	}
        }
   }

    @Override
	public void onEvent(Event event)
    {
        Component eventComp = event.getTarget();

        if (event.getTarget().getId().equals(ConfirmPanel.A_OK))
        {
            validateLogin();
        }
        if (event.getName().equals(Events.ON_SELECT))
        {
            if(eventComp.getId().equals(lstLanguage.getId())) {
            	String langName = lstLanguage.getSelectedItem().getLabel();
            	languageChanged(langName);
            }
        }
        // Elaine 2009/02/06 - initial language
        if (event.getName().equals(Events.ON_CHANGE))
        {
        	if(eventComp.getId().equals(txtUserId.getId()))
        	{
        		onUserIdChange();
        	}
        }
        //
    }

	private void onUserIdChange() {
		String userId = txtUserId.getValue();
		if(userId != null && userId.length() > 0)
		{
			int AD_User_ID = DB.getSQLValue(null, "SELECT AD_User_ID FROM AD_User WHERE Name = ? AND IsSystemUser = ?", userId, true);
			if(AD_User_ID > 0)
			{
				// Elaine 2009/02/06 Load preference from AD_Preference
				UserPreference userPreference = SessionManager.getSessionApplication().loadUserPreference(AD_User_ID);
				String initDefault = userPreference.getProperty(UserPreference.P_LANGUAGE);
				for(int i = 0; i < lstLanguage.getItemCount(); i++)
		        {
		        	Comboitem li = lstLanguage.getItemAtIndex(i);
		        	if(li.getLabel().equals(initDefault))
		        	{
		        		lstLanguage.setSelectedIndex(i);
		        		languageChanged(li.getLabel()); // Elaine 2009/04/17 language changed
		        		break;
		        	}
		        }
			}
		}
	}

    private void languageChanged(String langName)
    {
    	Language language = findLanguage(langName);

    	//	Locales
		Locale loc = language.getLocale();
		Locale.setDefault(loc);
		res = ResourceBundle.getBundle(RESOURCE, loc);
		
    	lblTitle.setValue(Msg.translate(ctx, "login.WindowTitle")); // metas
    	lblUserId.setValue(res.getString("User"));
    	lblPassword.setValue(res.getString("Password"));
    	lblLanguage.setValue(res.getString("Language"));
    	chkRememberMe.setLabel(Msg.getMsg(language, "login.RememberMe")); // metas: TODO: switch from login.RememberMe to RememberMe 
    	btnForgotPassword.setLabel(Msg.getMsg(language, "ForgotPasswordButton")); // metas
    }

	private Language findLanguage(String langName) {
		Language tmp = Language.getLanguage(langName);
    	Language language = new Language(tmp.getName(), tmp.getAD_Language(), tmp.getLocale(), tmp.isDecimalPoint(),
    			tmp.getDateFormat().toPattern(), tmp.getMediaSize());
    	Env.verifyLanguage(ctx, language);
    	Env.setContext(ctx, Env.CTXNAME_AD_Language, language.getAD_Language());
    	Env.setContext(ctx, AEnv.LOCALE, language.getLocale().toString());
		return language;
	}
    /**
     *  validates user name and password when logging in
     *
    **/
    public void validateLogin()
    {
    	txtUserId.clearErrorMessage(true);
    	txtPassword.clearErrorMessage(true);
    	lstLanguage.clearErrorMessage(true);
    	
        Login login = new Login(ctx);
        String userId = txtUserId.getValue();
        String userPassword = txtPassword.getValue();
        
        //check is token
        String token = (String) txtPassword.getAttribute("user.token.hash");
        if (token != null && token.equals(userPassword))
        {
        	userPassword = "";
        	int AD_Session_ID = (Integer)txtPassword.getAttribute("user.token.sid");
        	MSession session = new MSession(Env.getCtx(), AD_Session_ID, null);
        	if (session.get_ID() == AD_Session_ID)
        	{
        		MUser user = MUser.get(Env.getCtx(), session.getCreatedBy());
        		if (BrowserToken.validateToken(session, user, token))
        		{
        			userPassword = user.getPassword();
        		}
        	}
        }
        
        final MSession session = createMSession(login); // metas
        KeyNamePair rolesKNPairs[] = login.getRoles(userId, userPassword);
        if(rolesKNPairs == null || rolesKNPairs.length == 0)
        	closeSessionWithError(session); // metas

        else
        {
        	String langName = null;
        	if ( lstLanguage.getSelectedItem() != null )
        		langName = lstLanguage.getSelectedItem().getLabel();
        	else
        		langName = Language.getBaseLanguage().getName();
        	Language language = findLanguage(langName);
            wndLogin.loginOk(userId, userPassword);

            Env.setContext(ctx, UserPreference.LANGUAGE_NAME, language.getName()); // Elaine 2009/02/06

            Locales.setThreadLocal(language.getLocale());

            Clients.response("zkLocaleJavaScript", new AuScript(null, ZkFns.outLocaleJavaScript()));
            String timeoutText = getUpdateTimeoutTextScript();
            if (!Strings.isEmpty(timeoutText))
            	Clients.response("zkLocaleJavaScript2", new AuScript(null, timeoutText));
        }

		// This temporary validation code is added to check the reported bug
		// [ adempiere-ZK Web Client-2832968 ] User context lost?
		// https://sourceforge.net/tracker/?func=detail&atid=955896&aid=2832968&group_id=176962
		// it's harmless, if there is no bug then this must never fail
        Session currSess = Executions.getCurrent().getDesktop().getSession();
        currSess.setAttribute("Check_AD_User_ID", Env.getAD_User_ID(ctx));
		// End of temporary code for [ adempiere-ZK Web Client-2832968 ] User context lost?
        
        Env.setContext(ctx, BrowserToken.REMEMBER_ME, chkRememberMe.isChecked());

        /* Check DB version */
        String version = DB.getSQLValueString(null, "SELECT Version FROM AD_System");
        //  Identical DB version
		if (!Adempiere.getDatabaseVersion().equals(version))
		{
            String AD_Message = "DatabaseVersionError";
            //  Code assumes Database version {0}, but Database has Version {1}.
            String msg = Msg.getMsg(ctx, AD_Message);   //  complete message
			msg = MessageFormat.format(msg, new Object[] { Adempiere.getDatabaseVersion(), version });
            throw new ApplicationException(msg);
        }

        wndLogin.roleAutoValidate(); // metas-US073: teo_sarca 
    }

	private String getUpdateTimeoutTextScript() {
		String msg = Msg.getMsg(Env.getCtx(), "SessionTimeoutText");
		if (msg == null || msg.equals("SessionTimeoutText")) {
			return null;
		}
		msg = Strings.escape(msg, "\"");
		String s = "adempiere.store.set(\"zkTimeoutText\", \"" + msg + "\")";
		return s;
	}

// metas: begin
	private MSession createMSession(Login login)
	{
		final Session currSess = Executions.getCurrent().getDesktop().getSession();       
        final HttpSession httpSess = (HttpSession) currSess.getNativeSession();

        final String webSessionId = httpSess.getId();
        String remoteAddr = currSess.getRemoteAddr();
        String remoteHost = currSess.getRemoteHost();
        
        //
        // Check if we are behind proxy and if yes, get the actual client IP address
        // NOTE: when configuring apache, don't forget to activate reverse-proxy mode
        // see http://www.xinotes.org/notes/note/770/
        final String forwardedFor = Executions.getCurrent().getHeader("X-Forwarded-For");
        if (!Check.isEmpty(forwardedFor))
        {
        	remoteAddr = forwardedFor;
        	remoteHost = forwardedFor;
        }
        
		final MSession sessionPO = MSession.get (ctx, remoteAddr, remoteHost, webSessionId);
		final I_AD_Session session = InterfaceWrapperHelper.create(sessionPO, I_AD_Session.class);
		
		// Set HostKey
		final String hostKey = Services.get(IHostKeyBL.class).getHostKey();
		session.setHostKey(hostKey);
		InterfaceWrapperHelper.save(session);
		
		// Update Login helper
		login.setRemoteAddr(remoteAddr);
		login.setRemoteHost(remoteHost);
		login.setWebSession(webSessionId);
		
		return sessionPO;
	}
	private void closeSessionWithError(MSession session)
	{
    	String errmsg = null;
    	ValueNamePair vnp = MetasfreshLastError.retrieveError();
    	if (vnp != null)
    		errmsg = Msg.translate(Env.getCtx(), vnp.getValue());
    	if (errmsg == null)
    		errmsg = Msg.translate(Env.getCtx(), "UserOrPasswordInvalid");
    	//
        session.logout();
        Env.setContext(Env.getCtx(), Env.CTXNAME_AD_Session_ID, "");
        throw new WrongValueException(errmsg);

	}
	
    private void doForgotPassword()
    {
    	txtUserId.clearErrorMessage(true);
    	
    	final String userId = txtUserId.getText();
    	boolean ok = false;
    	try
    	{
    		Services.get(IUserBL.class).createResetPasswordByEMailRequest(ctx, userId);
    		ok = true;
    	}
    	catch (FillMandatoryException e)
    	{
    		throw new WrongValueException(txtUserId, MZul.EMPTY_NOT_ALLOWED);
    	}
    	catch (Exception e)
    	{
    		String msg = e.getLocalizedMessage();
    		if (Check.isEmpty(msg, true))
    		{
    			msg = Msg.translate(Env.getCtx(), IUserDAO.MSG_MailOrUsernameNotFound);
    		}
    		throw new WrongValueException(txtUserId, msg);
    	}
    	//
    	if (ok)
    	{
			try
			{
				org.zkoss.zul.Messagebox.show(
						Msg.getMsg(ctx, "PasswordResetMailSent", false),
						Msg.getMsg(ctx, "PasswordResetMailSent", true),
						org.zkoss.zul.Messagebox.OK,
						null);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
    	}
    }
}
