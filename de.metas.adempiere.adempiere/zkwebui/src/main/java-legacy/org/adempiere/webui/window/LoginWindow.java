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

package org.adempiere.webui.window;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.adempiere.user.api.IUserBL;
import org.adempiere.webui.IWebClient;
import org.adempiere.webui.component.FWindow;
import org.adempiere.webui.panel.LoginPanel;
import org.adempiere.webui.panel.ResetPasswordPanel;
import org.adempiere.webui.panel.RolePanel;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.logging.LogManager;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;

/**
 *
 * @author  <a href="mailto:agramdass@gmail.com">Ashley G Ramdass</a>
 * @date    Feb 25, 2007
 * @version $Revision: 0.10 $
 * @author <a href="mailto:sendy.yagambrum@posterita.org">Sendy Yagambrum</a>
 * @date    July 18, 2007
 */
public class LoginWindow extends FWindow implements EventListener
{
	private final Logger log = LogManager.getLogger(getClass());
	
    /**
	 *
	 */
	private static final long serialVersionUID = -365979563919913804L;
	
	private IWebClient app;
    private Properties ctx;
    private LoginPanel pnlLogin;
    private RolePanel pnlRole;
    private ResetPasswordPanel pnlResetPassword;

    public LoginWindow(IWebClient app)
    {
        this.ctx = Env.getCtx();
        this.app = app;
        initComponents();
        init();
        // add listener on 'ENTER' key for the login window
        addEventListener(Events.ON_OK,this);
    }

    private void init()
    {
        final HttpServletRequest httpRequest = (HttpServletRequest)Executions.getCurrent().getNativeRequest();
        String action = httpRequest.getParameter(IUserBL.PARAM_ACTION);
        boolean defaultAction = true;
        try
        {
	        if (IUserBL.ACTION_ResetPassword.equals(action))
	        {
	        	pnlResetPassword.setResetCode();
	        	defaultAction = false;
	        	this.appendChild(pnlResetPassword);
	        }
        }
        catch (Exception e)
        {
        	log.info(e.getLocalizedMessage(), e);
        	Executions.sendRedirect("index.zul");
        	return;
//        	defaultAction = true;
        }
        
        if (defaultAction)
        {
        	this.appendChild(pnlLogin);
        }
        this.setStyle("background-color: transparent");
    }

    private void initComponents()
    {
        pnlLogin = new LoginPanel(ctx, this);
        pnlResetPassword = new ResetPasswordPanel(ctx, this);
    }

    public void loginOk(String userName, String password)
    {
        pnlRole = new RolePanel(ctx, this, userName, password);
        this.getChildren().clear();
        this.appendChild(pnlRole);
    }

    public void loginCompleted()
    {
        app.loginCompleted();
    }

    public void loginCancelled()
    {
        pnlLogin = new LoginPanel(ctx, this);
        this.getChildren().clear();
        this.appendChild(pnlLogin);
    }

    public void onEvent(Event event)
    {
       // check that 'ENTER' key is pressed
       if (Events.ON_OK.equals(event.getName()))
       {
          /**
           * LoginWindow can have as a child, either LoginPanel or RolePanel
           * If LoginPanel is currently a child, validate login when
           * 'ENTER' key is pressed  or validate Roles if RolePanel is
           * currently a child
           */
           RolePanel rolePanel = (RolePanel)this.getFellowIfAny("rolePanel");
           if (rolePanel != null)
           {
               rolePanel.validateRoles();
           }

           LoginPanel loginPanel = (LoginPanel)this.getFellowIfAny("loginPanel");
           if (loginPanel != null)
           {
               loginPanel.validateLogin();
           }
       }
    }
    
    // metas-US073: teo_sarca
    public void roleAutoValidate()
    {
      if (pnlRole.canAutoValidate())
    	  pnlRole.validateRoles();
    }
}
