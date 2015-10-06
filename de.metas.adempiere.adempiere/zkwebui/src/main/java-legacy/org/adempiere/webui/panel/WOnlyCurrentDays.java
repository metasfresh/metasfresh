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

import java.util.logging.Level;

import org.adempiere.webui.apps.AEnv;
import org.adempiere.webui.component.Button;
import org.adempiere.webui.component.Window;
import org.compiere.grid.VOnlyCurrentDays;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Separator;


/**
 *	Queries how many days back history is displayed as current
 *
 * 	@author 	Niraj Sohun
 * 	@date		September 24, 2007
 */

public class WOnlyCurrentDays extends Window implements EventListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2464266193433220449L;

	/**
	 *	Constructor
	 *  @param parent parent frame
	 *  @param buttonLocation lower left corner of the button
	 */
	public WOnlyCurrentDays()
	{
		//	How long back in History?
		super();
		
		try
		{
			jbInit();
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, "VOnlyCurrentDays", e);
		}

		this.setVisible(true);
		AEnv.showWindow(this);
	}	//	WOnlyCurrentDays

	private Hbox mainPanel = new Hbox();
	private Button bShowAll = new Button();
	private Button bShowMonth = new Button();
	private Button bShowWeek = new Button();
	private Button bShowDay = new Button();
	private Button bShowYear = new Button();

	/**	Days (0=all)			*/
	private int 	m_days = 0;
	
	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(VOnlyCurrentDays.class);

	/**
	 * 	Static Initializer
	 * 	@throws Exception
	 */
	
	private void jbInit() throws Exception
	{
		bShowAll.setLabel(Msg.getMsg(Env.getCtx(), "All"));
		bShowAll.addEventListener(Events.ON_CLICK, this);
	
		bShowYear.setLabel(Msg.getMsg(Env.getCtx(), "Year"));
		bShowYear.addEventListener(Events.ON_CLICK, this);
		
		bShowMonth.setLabel(Msg.getMsg(Env.getCtx(), "Month"));
		bShowMonth.addEventListener(Events.ON_CLICK, this);
		
		bShowWeek.setLabel(Msg.getMsg(Env.getCtx(), "Week"));
		bShowWeek.addEventListener(Events.ON_CLICK, this);
		
		bShowDay.setLabel(Msg.getMsg(Env.getCtx(), "Day"));
		bShowDay.addEventListener(Events.ON_CLICK, this);
		
		mainPanel.setWidth("100%");
		mainPanel.setStyle("text-align:center");
		mainPanel.appendChild(bShowDay);
		mainPanel.appendChild(bShowWeek);
		mainPanel.appendChild(bShowMonth);
		mainPanel.appendChild(bShowYear);
		mainPanel.appendChild(bShowAll);

		this.setWidth("450px");
		this.setBorder("normal");
		this.setTitle(Msg.getMsg(Env.getCtx(), "VOnlyCurrentDays"));
		this.setClosable(true);
		this.setAttribute("mode", "modal");
		
		this.appendChild(new Separator());
		this.appendChild(mainPanel);
		this.appendChild(new Separator());
	}	//	jbInit

	/**
	 * 	Get selected number of days
	 * 	@return days or -1 for all
	 */
	
	public int getCurrentDays()
	{
		return m_days;
	}	//	getCurrentDays

	public void onEvent(Event event) throws Exception 
	{
		if (event.getTarget() == bShowDay)
			m_days = 1;
		else if (event.getTarget() == bShowWeek)
			m_days = 7;
		else if (event.getTarget() == bShowMonth)
			m_days = 31;
		else if (event.getTarget() == bShowYear)
			m_days = 365;
		else
			m_days = 0;		//	all
		
		this.detach();
	}

}
