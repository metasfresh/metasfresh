/******************************************************************************
 * Copyright (C) 2008 Elaine Tan                                              *
 * Copyright (C) 2008 Idalica Corporation
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
package org.adempiere.webui.dashboard;

import org.adempiere.webui.util.IServerPushCallback;
import org.adempiere.webui.util.ServerPushTemplate;

/**
 * Interface for dashboard panel
 * @author Elaine
 * @author hengsin
 *
 */
public interface IDashboardPanel extends IServerPushCallback {

	/**
	 * Refresh content of panel. For performance reason, keep the activate of desktop as short 
	 * as possible.
	 * @param template 
	 */
	public void refresh(ServerPushTemplate template);
	
}
