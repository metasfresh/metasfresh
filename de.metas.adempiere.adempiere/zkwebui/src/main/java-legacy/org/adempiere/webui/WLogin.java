/******************************************************************************
 * Copyright (C) 2008 Low Heng Sin                                            *
 * Copyright (C) 2009 Idalica Corporation                                     *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/

package org.adempiere.webui;

import org.adempiere.webui.apps.AEnv;
import org.adempiere.webui.part.AbstractUIPart;
import org.adempiere.webui.theme.ITheme;
import org.adempiere.webui.theme.ThemeManager;
import org.adempiere.webui.window.LoginWindow;
import org.zkoss.zhtml.Text;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.metainfo.PageDefinition;
import org.zkoss.zkex.zul.Borderlayout;
import org.zkoss.zkex.zul.Center;
import org.zkoss.zkex.zul.East;
import org.zkoss.zkex.zul.North;
import org.zkoss.zkex.zul.South;
import org.zkoss.zkex.zul.West;
import org.zkoss.zul.Div;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

/**
 *
 * @author  <a href="mailto:agramdass@gmail.com">Ashley G Ramdass</a>
 * @author  Low Heng Sin
 * @date    Mar 3, 2007
 * @version $Revision: 0.10 $
 */
public class WLogin extends AbstractUIPart
{

	private IWebClient app;
	private Borderlayout layout;
	private Window browserWarningWindow;

    public WLogin(IWebClient app)
    {
        this.app = app;
    }

    protected Component doCreatePart(Component parent)
    {
    	final ITheme theme = ThemeManager.getThemeImpl();
        layout = new Borderlayout();
        if (parent != null)
        	layout.setParent(parent);
        else
        	layout.setPage(page);
        LayoutUtils.addSclass(theme.getCssName(ITheme.LOGIN_WINDOW_CLASS), layout);

        Center center = new Center();
        center.setParent(layout);
        center.setBorder("none");
        center.setFlex(true);
        center.setAutoscroll(true);
        center.setStyle("border: none; background-color: transparent;");

        Vbox vb = new Vbox();
        vb.setParent(center);
        vb.setHeight("100%");
        vb.setWidth("100%");
        vb.setPack("center");
        vb.setAlign("center");
        vb.setStyle("background-color: transparent;");

        LoginWindow loginWindow = new LoginWindow(app);
        loginWindow.setParent(vb);

        if (!AEnv.isBrowserSupported())
        {
        	//TODO: localization
        	String msg = "You might experience slow performance and user interface anomalies using your current browser to access the application. We recommend the use of Firefox, Google Chrome or Apple Safari.";
        	browserWarningWindow = new Window();
        	Div div = new Div();
        	div.setStyle("font-size: 9pt");
        	div.appendChild(new Text(msg));
        	browserWarningWindow.appendChild(div);
        	browserWarningWindow.setPosition("top,right");
        	browserWarningWindow.setWidth("550px");
        	browserWarningWindow.setPage(page);
        	browserWarningWindow.doOverlapped();
        }
        
        try {
        	String right = theme.getLoginRightPanel();
	        PageDefinition pageDefintion = Executions.getCurrent().getPageDefinition(right);
	    	East east = new East();
	    	east.setSclass(theme.getCssName(ITheme.LOGIN_EAST_PANEL_CLASS));
	    	addContent(east, pageDefintion);
        } catch (Exception e) {
        	//ignore page not found exception
        	if (e instanceof UiException) {
        		if (!(e.getMessage() != null && e.getMessage().startsWith("Page not found"))) {
        			e.printStackTrace();
        		}
        	} else {
        		e.printStackTrace();
        	}
        }

        try {
	        String left = theme.getLoginLeftPanel();
	        PageDefinition pageDefintion = Executions.getCurrent().getPageDefinition(left);
	    	West west = new West();
	    	west.setSclass(theme.getCssName(ITheme.LOGIN_WEST_PANEL_CLASS));
	    	addContent(west, pageDefintion);
        } catch (Exception e){
        	//ignore page not found exception
        	if (e instanceof UiException) {
        		if (!(e.getMessage() != null && e.getMessage().startsWith("Page not found"))) {
        			e.printStackTrace();
        		}
        	} else {
        		e.printStackTrace();
        	}
        }

        try {
	        String top = theme.getLoginTopPanel();
	        PageDefinition pageDefintion = Executions.getCurrent().getPageDefinition(top);
	    	North north = new North();
	    	north.setSclass(theme.getCssName(ITheme.LOGIN_NORTH_PANEL_CLASS));
	    	addContent(north, pageDefintion);
        } catch (Exception e) {
        	//ignore page not found exception
        	if (e instanceof UiException) {
        		if (!(e.getMessage() != null && e.getMessage().startsWith("Page not found"))) {
        			e.printStackTrace();
        		}
        	} else {
        		e.printStackTrace();
        	}
        }

        try {
	        String bottom = theme.getLoginBottomPanel();
	        PageDefinition pageDefintion = Executions.getCurrent().getPageDefinition(bottom);
	    	South south = new South();
	    	south.setSclass(theme.getCssName(ITheme.LOGIN_SOUTH_PANEL_CLASS));
	    	addContent(south, pageDefintion);
        } catch (Exception e) {
        	//ignore page not found exception
        	if (e instanceof UiException) {
        		if (!(e.getMessage() != null && e.getMessage().startsWith("Page not found"))) {
        			e.printStackTrace();
        		}
        	} else {
        		e.printStackTrace();
        	}
        }

        return layout;
    }

    private void addContent(Component parent, PageDefinition page) {
    	layout.appendChild(parent);
    	Executions.createComponents(page, parent, null);
    }

	public void detach() {
		layout.detach();
		layout = null;
		if (browserWarningWindow != null)
			browserWarningWindow.detach();
	}

	public Component getComponent() {
		return layout;
	}
}
