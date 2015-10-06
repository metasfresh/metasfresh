/******************************************************************************
 * Copyright (C) 2008 Elaine Tan                                              *
 * Copyright (C) 2008 Idalica Corporation                                     *
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

import org.zkoss.zul.Iframe;

/**
 * Dashboard item: Google calendar
 * @author Elaine
 * @date November 20, 2008
 */
public class DPCalendar extends DashboardPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6420016898259232438L;

	public DPCalendar()
	{
		super();
		
		Iframe iframe = new Iframe("http://www.google.com/calendar/embed?showTitle=0&amp;showTabs=0&amp;height=300&amp;wkst=1&amp;bgcolor=%23FFFFFF&amp;color=%232952A3");
		iframe.setWidth("300px");
		iframe.setHeight("310px");
        this.appendChild(iframe);
	}	
}
