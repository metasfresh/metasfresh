/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
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
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.adempiere.webui.panel;

import java.text.DateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;

import org.adempiere.webui.component.Panel;
import org.adempiere.webui.component.ToolBarButton;
import org.adempiere.webui.window.InfoSchedule;
import org.adempiere.webui.window.WAssignmentDialog;
import org.compiere.model.MResourceAssignment;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.zkforge.timeline.Bandinfo;
import org.zkforge.timeline.Timeline;
import org.zkforge.timeline.event.BandScrollEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.MouseEvent;

/**
 *	Visual and Control Part of Schedule.
 *  Contains Time and Schedule Panels
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: VSchedule.java,v 1.3 2006/07/30 00:51:27 jjanke Exp $
 * 
 *  Zk Port
 *  @author Low Heng Sin
 */
public class WSchedule extends Panel implements EventListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7714179510197450419L;

	private InfoSchedule infoSchedule;

	/**
	 *	Constructor
	 *  @param is InfoSchedule for call back
	 *  @param type Type of schedule TYPE_...
	 */
	public WSchedule (InfoSchedule is)
	{		
		infoSchedule = is;
		
		try
		{
			init();
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, "VSchedule", e);
		}		
	}	//	WSchedule

	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(WSchedule.class);

	Timeline timeLine;
	private Bandinfo hourBand;
	private Bandinfo dayBand;

	private ToolBarButton button;

	private Bandinfo mthBand;

	private Date m_center;

	private MResourceAssignment _assignmentDialogResult;

	/**
	 * 	Static init
	 *  <pre>
	 * 	timePanel (West)
	 *  schedlePanel (in schedulePane - Center)
	 *  </pre>
	 * 	@throws Exception
	 */
	private void init() throws Exception
	{
		this.getChildren().clear();
				
		timeLine = new Timeline();
		timeLine.setHeight("400px");
		timeLine.setWidth("100%");
		timeLine.setId("resoureSchedule");
		
		this.appendChild(timeLine);		
		
		initBandInfo();
		
		button = new ToolBarButton();
		button.setLabel("Edit");
		button.setStyle("visibility: hidden; height: 0px; width: 0px");
		button.addEventListener(Events.ON_CLICK, this);
		this.appendChild(button);
	}	//	jbInit

	private void initBandInfo() {
		if (hourBand != null)
			hourBand.detach();		
		hourBand = new Bandinfo();
		timeLine.appendChild(hourBand);
		hourBand.setIntervalUnit("hour");
		hourBand.setWidth("40%");
		hourBand.setIntervalPixels(40);
		hourBand.setTimeZone(TimeZone.getDefault());
		
		if (dayBand != null)
			dayBand.detach();
		dayBand = new Bandinfo();
		timeLine.appendChild(dayBand);
		dayBand.setIntervalUnit("day");
		dayBand.setWidth("35%");
		dayBand.setIntervalPixels(100);
		dayBand.setSyncWith(hourBand.getId());		
		dayBand.setTimeZone(TimeZone.getDefault());
		dayBand.setShowEventText(false);
		// listening band scroll event
		dayBand.addEventListener("onBandScroll", this);
		
		if (mthBand != null)
			mthBand.detach();
		mthBand = new Bandinfo();
		timeLine.appendChild(mthBand);
		mthBand.setIntervalUnit("month");
		mthBand.setWidth("25%");
		mthBand.setIntervalPixels(150);
		mthBand.setSyncWith(dayBand.getId());		
		mthBand.setTimeZone(TimeZone.getDefault());
		mthBand.setShowEventText(false);		
	}

	/**
	 * 	Recreate View
	 * 	@param S_Resource_ID Resource
	 * 	@param date Date
	 */
	public void recreate (int S_Resource_ID, Date date)
	{
		hourBand.setDate(date);
		// Elaine 2008/12/12
		dayBand.setDate(date);
		mthBand.setDate(date);
//		if (m_center == null || date.getTime() != m_center.getTime())
//			hourBand.scrollToCenter(date);
		//
		
		String feedUrl = "timeline?S_Resource_ID=" + S_Resource_ID + "&date=" + DateFormat.getInstance().format(date)
			+ "&uuid=" + button.getUuid() + "&tlid=" + timeLine.getUuid();
		hourBand.setEventSourceUrl(feedUrl);
		dayBand.setEventSourceUrl(feedUrl);
	}	//	recreate

	public void onAssignmentCallback() {
		if (_assignmentDialogResult != null)
			infoSchedule.mAssignmentCallback(_assignmentDialogResult);
		_assignmentDialogResult = null;
	}
	
	public void onEvent(Event event) throws Exception {
		if (event instanceof MouseEvent) {
			MouseEvent me = (MouseEvent) event;
			if (me.getX() > 0) {
				MResourceAssignment assignment = new MResourceAssignment(Env.getCtx(), me.getX(), null);
				WAssignmentDialog wad = new WAssignmentDialog(assignment, false, infoSchedule.isCreateNew());
				if (!wad.isCancelled()) {
					_assignmentDialogResult =  wad.getMResourceAssignment();
					Events.echoEvent("onAssignmentCallback", this, null);				
				}
			}
		} else if (event instanceof BandScrollEvent){
			BandScrollEvent e = (BandScrollEvent) event;
			Date end = e.getMax();
			Date start = e.getMin();
			Date mid = e.getCenter();
			if (mid != null) {
				m_center = mid;
				infoSchedule.dateCallback(mid);
			}
		}
	}

}	//	WSchedule
