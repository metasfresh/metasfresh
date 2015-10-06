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

import java.util.List;
import java.util.Properties;

import org.adempiere.webui.LayoutUtils;
import org.adempiere.webui.component.CompositeADTab;
import org.adempiere.webui.component.IADTab;
import org.adempiere.webui.component.Tabbox;
import org.adempiere.webui.component.Tabpanel;
import org.adempiere.webui.component.Tabs;
import org.adempiere.webui.part.ITabOnSelectHandler;
import org.adempiere.webui.session.SessionManager;
import org.adempiere.webui.util.UserPreference;
import org.compiere.model.GridWindow;
import org.compiere.model.MQuery;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.zkforge.keylistener.Keylistener;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.KeyEvent;
import org.zkoss.zkex.zul.Borderlayout;
import org.zkoss.zkex.zul.Center;
import org.zkoss.zkex.zul.East;
import org.zkoss.zkex.zul.North;
import org.zkoss.zkex.zul.South;
import org.zkoss.zkex.zul.West;
import org.zkoss.zul.Tab;

/**
 *
 * This class is based on org.compiere.apps.APanel written by Jorg Janke.
 * @author Jorg Janke
 *
 * @author <a href="mailto:agramdass@gmail.com">Ashley G Ramdass</a>
 * @author <a href="mailto:hengsin@gmail.com">Low Heng Sin</a>
 * @date Feb 25, 2007
 * @version $Revision: 0.10 $
 */
public class ADWindowPanel extends AbstractADWindowPanel
{
    @SuppressWarnings("unused")
	private static final CLogger logger = CLogger.getCLogger(ADWindowPanel.class);
    
	private Borderlayout layout;

	private Center contentArea;

	private West west;

	private East east;

	private Keylistener keyListener;

    public ADWindowPanel(Properties ctx, int windowNo)
    {
        super(ctx, windowNo);
    }


	public ADWindowPanel(Properties ctx, int windowNo, GridWindow gridWindow,
			int tabIndex, IADTabpanel tabPanel) {
		super(ctx, windowNo, gridWindow, tabIndex, tabPanel);
	}


	protected Component doCreatePart(Component parent)
    {
        layout = new Borderlayout();
        if (parent != null) {
	        layout.setParent(parent);
	        layout.setStyle("position:absolute");
	        layout.setHeight("100%");
	        layout.setWidth("100%");
        } else {
        	layout.setPage(page);
        }

        if (!isEmbedded())
        {
	        North n = new North();
	        n.setParent(layout);
	        n.setCollapsible(false);
	        n.setHeight("30px");
	        toolbar.setHeight("30px");
	        toolbar.setParent(n);
	        toolbar.setWindowNo(getWindowNo());
        }

        South s = new South();
        layout.appendChild(s);
        s.setCollapsible(false);
        statusBar.setParent(s);
        LayoutUtils.addSclass("adwindow-status", statusBar);

        if (!isEmbedded() && adTab.isUseExternalSelection())
        {
        	String tabPlacement = SessionManager.getSessionApplication().getUserPreference().getProperty(UserPreference.P_WINDOW_TAB_PLACEMENT);
        	if (tabPlacement == null || "left".equalsIgnoreCase(tabPlacement))
        	{
        		west = new West();
    	        layout.appendChild(west);
    	        west.setSplittable(false);
    	        west.setAutoscroll(true);
    	        west.setFlex(true);
    	        LayoutUtils.addSclass("adwindow-nav adwindow-left-nav", west);
    	        adTab.setTabplacement(IADTab.LEFT);
    	        adTab.getTabSelectionComponent().setParent(west);

    	        if (SessionManager.getSessionApplication().getUserPreference().isPropertyBool(UserPreference.P_WINDOW_TAB_COLLAPSIBLE))
    	        {
    	        	west.setTitle(Msg.getElement(Env.getCtx(), "AD_Tab_ID"));
    	        	west.setCollapsible(true);
    	        }
        	}
	        else
        	{
	        	east = new East();
		        layout.appendChild(east);
		        east.setSplittable(false);
		        east.setAutoscroll(true);
		        east.setFlex(true);
		        LayoutUtils.addSclass("adwindow-nav adwindow-right-nav", east);
		        adTab.setTabplacement(IADTab.RIGHT);
		        adTab.getTabSelectionComponent().setParent(east);

		        if (SessionManager.getSessionApplication().getUserPreference().isPropertyBool(UserPreference.P_WINDOW_TAB_COLLAPSIBLE))
    	        {
		        	east.setTitle(Msg.getElement(Env.getCtx(), "AD_Tab_ID"));
    	        	east.setCollapsible(true);
    	        }
        	}
	        LayoutUtils.addSclass("adwindow-nav-content", (HtmlBasedComponent) adTab.getTabSelectionComponent());
        }

        contentArea = new Center();
        contentArea.setParent(layout);
        contentArea.setAutoscroll(true);
        contentArea.setFlex(true);
        adTab.createPart(contentArea);

        if (parent instanceof Tabpanel) {
        	TabOnCloseHanlder handler = new TabOnCloseHanlder();
        	((Tabpanel)parent).setOnCloseHandler(handler);
        }

        if (!isEmbedded()) {
        	if (keyListener != null)
        		keyListener.detach();
        	keyListener = new Keylistener();
        	statusBar.appendChild(keyListener);
        	keyListener.setCtrlKeys("#f1#f2#f3#f4#f5#f6#f7#f8#f9#f10#f11#f12^f^i^n^s^d@#left@#right@#up@#down@#pgup@#pgdn@p^p@z@x#enter");
        	keyListener.addEventListener(Events.ON_CTRL_KEY, toolbar);
        	keyListener.addEventListener(Events.ON_CTRL_KEY, this);
        	keyListener.setAutoBlur(false);
        }

        layout.setAttribute(ITabOnSelectHandler.ATTRIBUTE_KEY, new ITabOnSelectHandler() {
			public void onSelect() {
				IADTab adTab = getADTab();
				if (adTab != null) {
					IADTabpanel iadTabpanel = adTab.getSelectedTabpanel();
					if (iadTabpanel != null && iadTabpanel instanceof ADTabpanel) {
						ADTabpanel adTabpanel = (ADTabpanel) iadTabpanel;
						if (adTabpanel.isGridView()) {
							adTabpanel.getGridView().scrollToCurrentRow();
						}
					}
				}
			}
		});
        
        return layout;
    }

    protected IADTab createADTab()
    {
    	CompositeADTab composite = new CompositeADTab();
    	return composite;
    }

	public Borderlayout getComponent() {
		return layout;
	}



	@Override
	public boolean initPanel(int adWindowId, MQuery query) {
		boolean retValue = super.initPanel(adWindowId, query);
		if (adTab.getTabCount() == 1) {
			if (west != null)
				west.setVisible(false);
			else if (east != null)
				east.setVisible(false);
		}
		return retValue;
	}


	/**
     * @param event
     * @see EventListener#onEvent(Event)
     */
    public void onEvent(Event event) {
    	if (Events.ON_CTRL_KEY.equals(event.getName())) {
    		KeyEvent keyEvent = (KeyEvent) event;
    		//enter == 13
    		if (keyEvent.getKeyCode() == 13 && this.getComponent().getParent().isVisible()) {
    			IADTabpanel panel = adTab.getSelectedTabpanel();
    			if (panel != null) {
    				if (panel.onEnterKey()) {
    					keyEvent.stopPropagation();
    				}
    			}
    		}
    	} else {
    		super.onEvent(event);
    	}
    }
    
	class TabOnCloseHanlder implements ITabOnCloseHandler {

		public void onClose(Tabpanel tabPanel) {
			if (ADWindowPanel.this.onExit()) {
				Tab tab = tabPanel.getLinkedTab();
				Tabbox tabbox = (Tabbox) tab.getTabbox();
				if (tabbox.getSelectedTab() == tab) {
					Tabs tabs = (Tabs) tabbox.getTabs();
					List<?> childs = tabs.getChildren();
					for(int i = 0; i < childs.size(); i++) {
						if (childs.get(i) == tab) {
							if (i > 0)
								tabbox.setSelectedIndex((i-1));
							break;
						}
					}
				}
				tabPanel.detach();
				tab.detach();
				if (getWindowNo() > 0)
					SessionManager.getAppDesktop().unregisterWindow(getWindowNo());
			}
		}
	}
}
