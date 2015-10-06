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
package org.adempiere.webui.panel;

import org.adempiere.ad.expression.api.ILogicExpression;
import org.compiere.model.GridTab;
import org.compiere.model.GridTabMaxRows;
import org.compiere.util.Evaluatee;
import org.zkoss.zk.ui.Component;

/**
 * Interface for UI component that edit/display record using ad_tab definitions
 * @author Low Heng Sin
 *
 */
public interface IADTabpanel extends Component, Evaluatee {

	/**
	 * @return display logic
	 */
	public ILogicExpression getDisplayLogic();

	/**
	 * @return tab level
	 */
	public int getTabLevel();

	/**
	 * @return true if refresh is not needed
	 */
	public boolean isCurrent();

	/**
	 *
	 * @return title
	 */
	public String getTitle();

	/**
	 * Render the panel
	 */
	public void createUI();

	/**
	 *
	 * @return GridTab
	 */
	public GridTab getGridTab();

	/**
	 * activate/deactivate the panel
	 * @param b
	 */
	public void activate(boolean b);

	/**
	 * retrieve data from db
	 */
	public void query();

	/**
	 * Refresh from db
	 */
	public void refresh();

	/**
	 * retrieve data from db
	 * @param currentRows
	 * @param currentDays
	 * @param maxRows
	 */
	public void query(boolean currentRows, int currentDays, GridTabMaxRows maxRows);

	/**
	 * Toggle between grid and form view
	 */
	public void switchRowPresentation();

	/**
	 * Dynamic update of field properties ( visibility, filter and mandatory )
	 * @param i
	 */
	public void dynamicDisplay(int i);

	/**
	 * After save event
	 * @param onSaveEvent
	 */
	public void afterSave(boolean onSaveEvent);

	/**
	 * Enter key event
	 * @return true if the event is process
	 */
	public boolean onEnterKey();
}
