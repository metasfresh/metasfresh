package org.adempiere.webui.apps.graph;

/*
 * #%L
 * de.metas.adempiere.adempiere.zkwebui
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.webui.component.Window;
import org.adempiere.webui.session.SessionManager;
import org.compiere.model.MGoal;

/**
 * 	Performance Detail Frame.
 * 	BarPanel for Drill-Down
 *	
 *  @author Jorg Janke
 *  @version $Id: PerformanceDetail.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 */
public class WPerformanceDetail extends Window
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3460594735973451874L;

	/**
	 * 	Constructor.
	 * 	Called from PAPanel, ViewPI (Performance Indicator)
	 *	@param goal goal
	 */
	public WPerformanceDetail (MGoal goal)
	{
		super();
		setTitle(goal.getName());

		WGraph barPanel = new WGraph(goal, 0, true, false, true, true);
		appendChild(barPanel);
				
		this.setAttribute(Window.MODE_KEY, Window.MODE_EMBEDDED);
		this.setStyle("height: 100%; width: 100%; position: absolute; overflow: auto");
		barPanel.setStyle("height: 100%; width: 100%; position: absolute; overflow: visible");
		SessionManager.getAppDesktop().showWindow(this);
	}	//	PerformanceDetail
}
