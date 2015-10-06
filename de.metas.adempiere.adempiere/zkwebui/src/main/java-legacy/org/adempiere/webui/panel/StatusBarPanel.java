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

package org.adempiere.webui.panel;

import org.adempiere.webui.LayoutUtils;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.Panel;
import org.adempiere.webui.session.SessionManager;
import org.adempiere.webui.window.WRecordInfo;
import org.compiere.apps.IStatusBar;
import org.compiere.model.DataStatusEvent;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.zkoss.zhtml.Text;
import org.zkoss.zk.au.out.AuScript;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Vbox;

/**
 * This class is based on org.compiere.apps.StatusBar written by Jorg Janke.
 * @author Jorg Janke
 *
 * @author  <a href="mailto:agramdass@gmail.com">Ashley G Ramdass</a>
 * @date    Mar 12, 2007
 * @version $Revision: 0.10 $
 */
public class StatusBarPanel extends Panel implements EventListener, IStatusBar
{
	/**
	 *
	 */
	private static final long serialVersionUID = 8401520243224743864L;

	private static final String POPUP_INFO_BACKGROUND_STYLE = "background-color: #262626; -moz-border-radius: 3px; -webkit-border-radius: 3px; border: 1px solid #262626; border-radius: 3px; ";
	private static final String POPUP_ERROR_BACKGROUND_STYLE = "background-color: #8B0000; -moz-border-radius: 3px; -webkit-border-radius: 3px; border: 1px solid #8B0000; border-radius: 3px; ";
	private static final String POPUP_POSITION_STYLE = "position: absolute; z-index: 99; display: block; visibility: visible;";
	private static final String POPUP_TEXT_STYLE = "color: white; background-color: transparent; font-size: 14px; font-weight:bold; position: relative; -moz-box-shadow: 0px 0px 0px #000;-webkit-box-shadow: 0px 0px 0px #000;box-shadow: 0px 0px 0px #000; padding: 5px; width: 590px; min-height: 20px;";

	private static final String SHADOW_STYLE = "-moz-box-shadow: 2px 2px 2px #888; -webkit-box-shadow: 2px 2px 2px #888; box-shadow: 2px 2px 2px #888;";

	private Label statusDB;
    private Label infoLine;
    private Label statusLine;

	private DataStatusEvent m_dse;

	private String m_text;

	private Div east;

	private Div west;

	private Div popup;

	private Div popupContent;
	private String popupStyle;
	private boolean embedded;

	public StatusBarPanel()
	{
		this(false);
	}

	/**
	 * @param embedded
	 */
    public StatusBarPanel(boolean embedded)
    {
        super();
        this.embedded = embedded;
        init();
    }

    private void init()
    {
        statusDB = new Label("  ");
        statusLine = new Label();

        Hbox hbox = new Hbox();
        hbox.setWidth("100%");
        hbox.setHeight("100%");
        if (embedded)
        	hbox.setWidths("90%,10%");
        else
        	hbox.setWidths("50%,50%");
        west = new Div();
        west.setStyle("text-align: left; ");
        west.appendChild(statusLine);
        Vbox vbox = new Vbox();
        vbox.setPack("center");
        LayoutUtils.addSclass("status", vbox);
        vbox.appendChild(west);
        hbox.appendChild(vbox);

        east = new Div();
        east.setWidth("100%");
        east.setStyle("text-align: right; ");
        if (!embedded)
        {
        	infoLine = new Label();
        	east.appendChild(infoLine);
        	infoLine.setVisible(false);
        }
        east.appendChild(statusDB);

        LayoutUtils.addSclass("status-db", statusDB);
        if (!embedded)
        	LayoutUtils.addSclass("status-info", infoLine);
        vbox = new Vbox();
        vbox.setPack("center");
        LayoutUtils.addSclass("status", vbox);
        vbox.appendChild(east);
        hbox.appendChild(vbox);

        this.appendChild(hbox);

        statusDB.addEventListener(Events.ON_CLICK, this);

        createPopup();
    }

    /**
     * @param text
     */
    public void setStatusDB (String text)
    {
        setStatusDB(text, null);
    }

    /**
     * @param text
     * @param dse
     */
    public void setStatusDB (String text, DataStatusEvent dse)
    {
        if (text == null || text.length() == 0)
        {
            statusDB.setValue("");
        }
        else
        {
            StringBuffer sb = new StringBuffer (" ");
            sb.append(text).append(" ");
            statusDB.setValue(sb.toString());
        }

        m_text = text;
        m_dse = dse;
    }

    /**
     * @param text
     */
    public void setStatusLine (String text)
    {
        setStatusLine(text, false);
    }

    /**
     * @param text
     * @param error
     */
    public void setStatusLine (String text, boolean error)
    {
    	setStatusLine(text, error, error);
    }

    /**
     * @param text
     * @param error
     * @param showPopup ignore for embedded
     */
    public void setStatusLine (String text, boolean error, boolean showPopup)
    {
    	statusLine.setText(text);
    	if (error)
    		statusLine.setStyle("color: red");
    	else
    		statusLine.setStyle("color: black");
    	statusLine.setTooltiptext(text);

    	if (!embedded && showPopup)
    	{
	    	Text t = new Text(text);
	    	popupContent.getChildren().clear();
	    	popupContent.appendChild(t);
	    	popupContent.setStyle(POPUP_TEXT_STYLE);
	    	if (error)
	    	{
	    		popupStyle = POPUP_ERROR_BACKGROUND_STYLE;
	    	}
	    	else
	    	{
	    		popupStyle = POPUP_INFO_BACKGROUND_STYLE;
	    	}


	    	String shadow = SHADOW_STYLE;
	    	popupStyle = popupStyle + shadow + POPUP_POSITION_STYLE;

	    	showPopup();

	    	//auto hide
	    	String script = "setTimeout('$e(\"" + popup.getUuid() + "\").style.display = \"none\"',";
	    	if (error)
	    		script += "3500";
	    	else
	    		script += "1000";
	    	script += ")";
	    	AuScript aus = new AuScript(popup, script);
	    	Clients.response("statusPopupFade", aus);
    	}
    }

	private void createPopup() {
		popupContent = new Div();

		popup = new Div();
        popup.setWidth("600px");
        popup.appendChild(popupContent);
        popup.addEventListener(Events.ON_CLICK, this);
        popup.setPage(SessionManager.getAppDesktop().getComponent().getPage());
        popup.setStyle("position: absolute; display: none");
	}

	private void showPopup() {
		popup.setVisible(true);
		popup.setStyle(popupStyle);

		String script = "var d = $e('" + popup.getUuid() + "');";
		script += "d.style.display='block';d.style.visibility='hidden';";
		script += "var hs = document.defaultView.getComputedStyle(d, null).getPropertyValue('height');";
		script += "var h = parseInt(hs, 10);";
		script += "h = h - 18;if (h < 0) h = 0;";
		script += "var p = Position.cumulativeOffset($e('" + this.getUuid() + "'));";
		script += "d.style.top=(p[1]-h)+'px';";
		script += "d.style.left=(p[0]+1)+'px';";
		script += "d.style.visibility='visible';";

		AuScript aus = new AuScript(popup, script);
		Clients.response(aus);
	}

    /**
     * Add Component to East of StatusBar
     *
     * @param component
     *            component
     */
    public final void addStatusComponent(final Component component)
    {
        east.appendChild(component);
    } // addStatusComponent

    /**
	 *	Set Info Line
	 *  @param text text
	 */
	public void setInfo (String text)
	{
		if (!embedded)
		{
			infoLine.setValue(text != null ? text : "");
			infoLine.setTooltiptext(text);
			if (text == null || text.trim().length() == 0)
				infoLine.setVisible(false);
			else
				infoLine.setVisible(true);
		}
	}	//	setInfo

	public void onEvent(Event event) throws Exception {
		if (Events.ON_CLICK.equals(event.getName()) && event.getTarget() == statusDB)
		{
			if (m_dse == null
				|| m_dse.CreatedBy == null
				|| !Env.getUserRolePermissions().isShowPreference())
				return;

			String title = Msg.getMsg(Env.getCtx(), "Who") + m_text;
			new WRecordInfo (title, m_dse);
		}
		else if (Events.ON_CLICK.equals(event.getName()) && event.getTarget() == popup)
		{
			popup.setVisible(false);
		}
	}

	@Override
	public void onPageDetached(Page page) {
		super.onPageDetached(page);
		if (popup != null)
			popup.detach();
	}

	/**
	 * @param visible
	 */
	public void setEastVisibility(boolean visible) {
		east.setVisible(visible);
	}

}
