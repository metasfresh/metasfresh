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

package org.adempiere.webui.component;

import java.util.List;

import org.adempiere.webui.panel.ITabOnCloseHandler;
import org.zkoss.zul.Tab;

/**
 *
 * @author  <a href="mailto:agramdass@gmail.com">Ashley G Ramdass</a>
 * @date    Feb 25, 2007
 * @version $Revision: 0.10 $
 */
public class Tabpanel extends org.zkoss.zul.Tabpanel
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -3392752891445762516L;

	private ITabOnCloseHandler onCloseHandler = null;
    
    private boolean enabled;

	private int tabLevel;

    public boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }
    
    public int getTabLevel() 
    {
    	return tabLevel;    	
    }
    
    public void setTabLevel(int l)
    {
    	tabLevel = l;
    }

	public void onClose() {
		if (onCloseHandler != null)
			onCloseHandler.onClose(this);
		else {
			Tab tab = this.getLinkedTab();
			Tabbox tabbox = (Tabbox) tab.getTabbox();
			if (tabbox.getSelectedTab() == tab) {
				Tabs tabs = (Tabs) tabbox.getTabs();
				List childs = tabs.getChildren();
				for(int i = 0; i < childs.size(); i++) {
					if (childs.get(i) == tab) {
						if (i > 0) 
							tabbox.setSelectedIndex((i-1));
						break;
					}
				}
			}
			this.detach();
			tab.detach();
		}
	}
	
	public void setOnCloseHandler(ITabOnCloseHandler handler) {
		this.onCloseHandler = handler;
	}
}
