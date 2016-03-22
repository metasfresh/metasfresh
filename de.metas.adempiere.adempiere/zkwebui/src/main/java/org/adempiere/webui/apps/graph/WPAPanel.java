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


import org.adempiere.webui.component.Grid;
import org.adempiere.webui.component.Panel;
import org.adempiere.webui.component.Row;
import org.adempiere.webui.component.Rows;
import org.compiere.model.MGoal;
import org.compiere.util.Env;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;

public class WPAPanel extends Panel implements EventListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6491684272848160726L;

	/**
	 * 	Get Panel if User has Performance Goals
	 *	@return panel pr null
	 */
	public static WPAPanel get()
	{
		int AD_User_ID = Env.getAD_User_ID(Env.getCtx());
		MGoal[] goals = MGoal.getUserGoals(Env.getCtx(), AD_User_ID);
		if (goals.length == 0)
			return null;
		return new WPAPanel(goals);
	}
	
	/**************************************************************************
	 * 	Constructor
	 *	@param goals
	 */
	private WPAPanel (MGoal[] goals)
	{
		super ();
		m_goals = goals;
		init();
	}
	
	/** Goals			*/
	private MGoal[] 	m_goals = null;
	
	/**	Logger	*/
	private static Logger log = LogManager.getLogger(WPAPanel.class);
	
	/**
	 * 	Static/Dynamic Init
	 */
	private void init()
	{
		Grid grid = new Grid();
		appendChild(grid);
		grid.setWidth("100%");
		grid.setStyle("margin:0; padding:0; position: absolute;");
		grid.makeNoStrip();
		grid.setOddRowSclass("even");

		Rows rows = new Rows();
		grid.appendChild(rows);

		for (int i = 0; i < m_goals.length; i++)
		{
			Row row = new Row();
			rows.appendChild(row);
			row.setWidth("100%");
			
			WPerformanceIndicator pi = new WPerformanceIndicator(m_goals[i]);
			row.appendChild(pi);
			pi.addEventListener(Events.ON_CLICK, this);			
		}	
	}	//	init

	/**
	 * 	Action Listener for Drill Down
	 *	@param e event
	 */
	public void onEvent(Event e) throws Exception 
	{
		if (e.getTarget() instanceof WPerformanceIndicator)
		{
			WPerformanceIndicator pi = (WPerformanceIndicator) e.getTarget();
			log.info(pi.toString());
			MGoal goal = pi.getGoal();
			if (goal.getMeasure() != null)
				new WPerformanceDetail(goal);
		}
	}
}
