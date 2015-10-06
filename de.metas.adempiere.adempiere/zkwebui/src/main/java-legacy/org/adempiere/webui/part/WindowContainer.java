/******************************************************************************
 * Copyright (C) 2008 Low Heng Sin  All Rights Reserved.                      *
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

package org.adempiere.webui.part;

import java.util.List;

import org.adempiere.webui.component.Tab;
import org.adempiere.webui.component.Tabbox;
import org.adempiere.webui.component.Tabpanel;
import org.adempiere.webui.component.Tabpanels;
import org.adempiere.webui.component.Tabs;
import org.adempiere.webui.panel.ADForm;
import org.adempiere.webui.window.ADWindow;
import org.compiere.model.GridWindow;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;

/**
 * 
 * @author Low Heng Sin
 *
 */
public class WindowContainer extends AbstractUIPart 
{
	private static final int MAX_TITLE_LENGTH = 30;
    
    private Tabbox           tabbox;

    public WindowContainer()
    {
    }
    
    /**
     * 
     * @param tb
     * @return WindowContainer
     */
    public static WindowContainer createFrom(Tabbox tb) 
    {
    	WindowContainer wc = new WindowContainer();
    	wc.tabbox = tb;
    	
    	return wc;
    }

    protected Component doCreatePart(Component parent)
    {
        tabbox = new Tabbox();
        
        Tabpanels tabpanels = new Tabpanels();
        Tabs tabs = new Tabs();

        tabbox.appendChild(tabs);
        tabbox.appendChild(tabpanels);
        tabbox.setWidth("100%");
        tabbox.setHeight("100%");
        
        if (parent != null)
        	tabbox.setParent(parent);
        else
        	tabbox.setPage(page);
        
        return tabbox;
    }
    
    /**
     * 
     * @param comp
     * @param title
     * @param closeable
     */
    public void addWindow(Component comp, String title, boolean closeable)
    {
        addWindow(comp, title, closeable, true);
    }
    
    /**
     * 
     * @param comp
     * @param title
     * @param closeable
     * @param enable
     */
    public void addWindow(Component comp, String title, boolean closeable, boolean enable) 
    {
    	insertBefore(null, comp, title, closeable, enable);
    }
    
    /**
     * 
     * @param refTab
     * @param comp
     * @param title
     * @param closeable
     * @param enable
     */
    // metas: changed return type from void to Tab
    public Tab insertBefore(Tab refTab, Component comp, String title, boolean closeable, boolean enable)
    {
        Tab tab = new Tab();
        title = title.replaceAll("[&]", "");
        if (title.length() <= MAX_TITLE_LENGTH) 
        {
        	tab.setLabel(title);
        }
        else
        {
        	tab.setTooltiptext(title);
        	title = title.substring(0, 27) + "...";
        	tab.setLabel(title);
        }
        tab.setClosable(closeable);
        
        // fix scroll position lost coming back into a grid view tab
        tab.addEventListener(Events.ON_SELECT, new EventListener() {
			public void onEvent(Event event) throws Exception {
				Tab tab = (Tab)event.getTarget();
				org.zkoss.zul.Tabpanel panel = tab.getLinkedPanel();
				Component component = panel.getFirstChild();
				if (component != null && component.getAttribute(ITabOnSelectHandler.ATTRIBUTE_KEY) instanceof ITabOnSelectHandler)
				{
					ITabOnSelectHandler handler = (ITabOnSelectHandler) component.getAttribute(ITabOnSelectHandler.ATTRIBUTE_KEY);
					handler.onSelect();
				}
			}
		});

        Tabpanel tabpanel = null;
        if (comp instanceof Tabpanel) {
        	tabpanel = (Tabpanel) comp;
        } else {
        	tabpanel = new Tabpanel();
        	tabpanel.appendChild(comp);
        }                
        tabpanel.setHeight("100%");
        tabpanel.setWidth("100%");
        tabpanel.setZclass("desktop-tabpanel");
        tabpanel.setStyle("position: absolute;");
        
        if (refTab == null)  
        {
        	tabbox.getTabs().appendChild(tab);
        	tabbox.getTabpanels().appendChild(tabpanel);
        }
        else
        {
        	org.zkoss.zul.Tabpanel refpanel = refTab.getLinkedPanel();
        	tabbox.getTabs().insertBefore(tab, refTab);
        	tabbox.getTabpanels().insertBefore(tabpanel, refpanel);
        }

        if (enable)
        	setSelectedTab(tab);
        
        return tab; // metas
    }
    
    /**
     * 
     * @param refTab
     * @param comp
     * @param title
     * @param closeable
     * @param enable
     */
    public void insertAfter(Tab refTab, Component comp, String title, boolean closeable, boolean enable)
    {
    	if (refTab == null)
    		addWindow(comp, title, closeable, enable);
    	else
    		insertBefore((Tab)refTab.getNextSibling(), comp, title, closeable, enable);
    }

    /**
     * 
     * @param tab
     */
    public void setSelectedTab(Tab tab)
    {
    	tabbox.setSelectedTab(tab);
    }

    /**
     * 
     * @return true if successfully close the active window
     */
    public boolean closeActiveWindow()
    {
    	Tab tab = (Tab) tabbox.getSelectedTab();
    	tabbox.getSelectedTab().onClose();
    	if (tab.getParent() == null)
    		return true;
    	else
    		return false;
    }
    
    /**
     * 
     * @return Tab
     */
    public Tab getSelectedTab() {
    	return (Tab) tabbox.getSelectedTab();
    }
    
    // Elaine 2008/07/21
    /**
     * @param tabNo
     * @param title
     * @param tooltip 
     */
    public void setTabTitle(int tabNo, String title, String tooltip)
    {
    	org.zkoss.zul.Tabs tabs = tabbox.getTabs();
    	Tab tab = (Tab) tabs.getChildren().get(tabNo);
    	tab.setLabel(title);
    	tab.setTooltiptext(tooltip);
    }
    //

	/**
	 * @return Tabbox
	 */
	public Tabbox getComponent() {
		return tabbox;
	}
	
// metas: begin
	private static final String ATTR_ComponentImpl = "ComponentImpl";
	
    public void addWindow(Component comp, String title, boolean closeable, Object compImpl) 
    {
    	Tab tab = insertBefore(null, comp, title, closeable, true);
    	tab.setAttribute(ATTR_ComponentImpl, compImpl);
    }


	/**
	 * Selects the tab that contains given form
	 * @param adFormId AD_Form_ID
	 * @return ADForm if found and selected, null if not found
	 */
	public ADForm setSelectedTabByFormId(int adFormId)
	{
    	org.zkoss.zul.Tabs tabs = tabbox.getTabs();
    	List<?> tabList = tabs.getChildren();
    	for (int tabIndex = 0; tabIndex < tabList.size(); tabIndex++)
    	{
			final Tab tab = (Tab)tabList.get(tabIndex);
    		Object comp = tab.getAttribute(ATTR_ComponentImpl);
    		if (comp instanceof ADForm)
    		{
    			ADForm form = (ADForm)comp;
    			if (adFormId == form.getAdFormId())
    			{
    				setSelectedTab(tab);
    				return form;
    			}
    		}
    	}
    	return null;
	}
	
	public ADWindow findADWindow(int adWindowId, boolean selectIfFound)
	{
    	final org.zkoss.zul.Tabs tabs = tabbox.getTabs();
    	final List<?> tabList = tabs.getChildren();
    	for (int tabIndex = 0; tabIndex < tabList.size(); tabIndex++)
    	{
			final Tab tab = (Tab)tabList.get(tabIndex);
    		Object comp = tab.getAttribute(ATTR_ComponentImpl);
    		if (comp instanceof ADWindow)
    		{
    			ADWindow adWindow = (ADWindow)comp;
    			GridWindow gridWindow = adWindow.getGridWindow();
    			if (gridWindow != null && gridWindow.getAD_Window_ID() == adWindowId)
    			{
    				if (selectIfFound)
    					setSelectedTab(tab);
    				return adWindow;
    			}
    		}
    	}
    	return null;
	}
// metas: end
}
