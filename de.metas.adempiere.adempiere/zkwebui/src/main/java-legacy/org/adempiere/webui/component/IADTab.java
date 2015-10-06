/******************************************************************************
 * Copyright (C) 2008 Low Heng Sin                                            *
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
package org.adempiere.webui.component;

import org.adempiere.webui.panel.IADTabpanel;
import org.adempiere.webui.part.UIPart;
import org.compiere.model.DataStatusEvent;
import org.compiere.model.GridTab;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.EventListener;

/**
 *
 * @author <a href="mailto:hengsin@gmail.com">Low Heng Sin</a>
 *
 */
public interface IADTab extends UIPart {

	/**
	 *
	 * @return selected tab index
	 */
	public int getSelectedIndex();

	/**
	 *
	 * @param tab index
	 */
	public void setSelectedIndex(int i);

	/**
	 * @param oldTabIndex
	 * @param newTabIndex
	 * @return
	 */
	public boolean updateSelectedIndex(int oldTabIndex, int newTabIndex);

	/**
	 * @return selected tab panel reference
	 */
	public IADTabpanel getSelectedTabpanel();

	/**
	 *
	 * @param fromIndex
	 * @param toIndex
	 * @return boolean
	 */
	public boolean canNavigateTo(int fromIndex, int toIndex);

	/**
	 * @param index
	 * @return boolean
	 */
	public boolean isDisplay(int index);

	/**
	 *
	 * @param tab
	 * @param tabPanel
	 */
	public void addTab(GridTab tab, IADTabpanel tabPanel);

	/**
	 * @return Number of tab
	 */
	public int getTabCount();

	/**
	 * @param e
	 */
	public void evaluate(DataStatusEvent e);

	/**
	 * @return path to the active tab
	 */
	public String getPath();

	/**
	 *
	 * @param listener
	 */
	public void addSelectionEventListener(EventListener listener);

	/**
	 * @return tab selection component
	 */
	public Component getTabSelectionComponent();

	/**
	 * @return boolean
	 */
	public boolean isUseExternalSelection();

	/**
	 * @param index
	 * @return IADTabpanel
	 */
	public IADTabpanel getADTabpanel(int index);

	/**
	 * constant for tab placement
	 */
	public final int LEFT = 0;
	public final int RIGHT = 1;

	/**
	 * Set tab placement ( left or right )
	 * @param tabPlacement
	 */
	public void setTabplacement(int tabPlacement);

	/**
	 * @param gTab
	 * @return IADTabpanel or null if not found
	 */
	public IADTabpanel findADTabpanel(GridTab gTab);
}
