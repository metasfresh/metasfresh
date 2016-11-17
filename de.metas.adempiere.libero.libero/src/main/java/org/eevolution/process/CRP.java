/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
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
 * Copyright (C) 2003-2007 e-Evolution,SC. All Rights Reserved.               *
 * Contributor(s): Victor Perez www.e-evolution.com                           *
 *                 Teo Sarca, www.arhipac.ro                                  *
 *****************************************************************************/

package org.eevolution.process;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;

import org.compiere.model.I_S_Resource;
import org.compiere.model.MResource;
import org.compiere.model.MResourceType;
import org.compiere.model.MSysConfig;
import org.compiere.util.TimeUtil;
import org.eevolution.exceptions.CRPException;
import org.eevolution.model.MPPOrder;
import org.eevolution.model.MPPOrderNode;
import org.eevolution.model.MPPOrderWorkflow;
import org.eevolution.model.RoutingService;
import org.eevolution.model.RoutingServiceFactory;
import org.eevolution.model.reasoner.CRPReasoner;

import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;

/**
 * Capacity Requirement Planning
 * 
 * @author Gunther Hoppe, tranSIT GmbH Ilmenau/Germany (Original by Victor Perez, e-Evolution, S.C.)
 * @version 1.0, October 14th 2005
 * 
 * @author Teo Sarca, www.arhipac.ro
 */
@SuppressWarnings("all") // tsa: to many warnings in a code that we don't use. Suppress all to reduce noise.
public class CRP extends JavaProcess
{
	public static final String FORWARD_SCHEDULING = "F";
	public static final String BACKWARD_SCHEDULING = "B";

	private int p_S_Resource_ID;        
	private String p_ScheduleType;
	
	/** SysConfig parameter - maximum number of algorithm iterations */ 
	private int p_MaxIterationsNo = -1;
	public static final String SYSCONFIG_MaxIterationsNo = "CRP.MaxIterationsNo";
	public static final int DEFAULT_MaxIterationsNo = 1000;
	
	public RoutingService routingService = null;
	
	/** CRP Reasoner */
	private CRPReasoner reasoner;

	protected void prepare()
	{
		for (ProcessInfoParameter para : getParametersAsArray())
		{
			String name = para.getParameterName();
			if (para.getParameter() == null)
				;			
			if (name.equals("S_Resource_ID")) {
				p_S_Resource_ID = para.getParameterAsInt();
			}
			else if (name.equals("ScheduleType")) {
				p_ScheduleType = (String)para.getParameter();				 		
			}
			else {
				log.error("prepare - Unknown Parameter: " + name);
			}
		}
		//
		p_MaxIterationsNo = MSysConfig.getIntValue(SYSCONFIG_MaxIterationsNo, DEFAULT_MaxIterationsNo, getAD_Client_ID());
	}

	protected String doIt() throws Exception
	{
		reasoner = new CRPReasoner();
		routingService = RoutingServiceFactory.get().getRoutingService(getAD_Client_ID());
		return runCRP();
	} 

	private String runCRP()
	{
		Iterator<MPPOrder> it = reasoner.getPPOrdersNotCompletedQuery(p_S_Resource_ID, get_TrxName()).iterate();
		while(it.hasNext())
		{
			MPPOrder order = it.next();
			try
			{
				runCRP(order);
			}
			catch (Exception e)
			{
				CRPException crpEx;
				if (e instanceof CRPException)
				{
					crpEx = (CRPException)e;
					crpEx.setPP_Order(order);
					throw crpEx;
				}
				else
				{
					crpEx = new CRPException(e);
				}
				throw crpEx;
			}
		}

		return "OK";
	}
	
	public void runCRP(MPPOrder order)
	{
		log.info("PP_Order DocumentNo:" + order.getDocumentNo());
		MPPOrderWorkflow owf = order.getMPPOrderWorkflow();
		if (owf == null)
		{
			// TODO: generate notice
			addLog("WARNING: No workflow found - "+order);
			return;
		}
		log.info("PP_Order Workflow:" + owf.getName());
		
		final ArrayList<Integer> visitedNodes = new ArrayList<Integer>();

		// Schedule Fordward
		if (p_ScheduleType.equals(FORWARD_SCHEDULING))
		{
			Timestamp date = order.getDateStartSchedule(); 
			int nodeId = owf.getPP_Order_Node_ID();
			MPPOrderNode node = null;

			while(nodeId != 0)
			{
				node = owf.getNode(nodeId);
				if (visitedNodes.contains(nodeId))
				{
					throw new CRPException("Cyclic transition found").setPP_Order_Node(node);
				}
				visitedNodes.add(nodeId);
				log.info("PP_Order Node:" + node.getName() != null ? node.getName() : ""  + " Description:" + node.getDescription() != null ? node.getDescription() : "");
				//
				MResource resource = MResource.get(getCtx(), node.getS_Resource_ID());
				
				// Skip this node if there is no resource
				if(resource == null)
				{						
					nodeId = owf.getNext(nodeId, getAD_Client_ID());
					continue;
				}
				
				if(!reasoner.isAvailable(resource))
				{
					throw new CRPException("@ResourceNotInSlotDay@").setS_Resource(resource);
				}

				long nodeMillis = calculateMillisFor(node, owf.getDurationBaseSec());
				Timestamp dateFinish = scheduleForward(date, nodeMillis ,resource);

				node.setDateStartSchedule(date);
				node.setDateFinishSchedule(dateFinish);	
				node.saveEx();

				date = node.getDateFinishSchedule();
				nodeId = owf.getNext(nodeId, getAD_Client_ID());
			}
			// Update order finish date
			if (node != null && node.getDateFinishSchedule()!= null)
			{
				order.setDateFinishSchedule(node.getDateFinishSchedule());
			}
		}
		// Schedule backward
		else if (p_ScheduleType.equals(BACKWARD_SCHEDULING))
		{
			Timestamp date = order.getDateFinishSchedule();
			int nodeId = owf.getNodeLastID(getAD_Client_ID());
			MPPOrderNode node = null;

			while(nodeId != 0)
			{
				node = owf.getNode(nodeId);
				if (visitedNodes.contains(nodeId))
				{
					throw new CRPException("Cyclic transition found - ").setPP_Order_Node(node);
				}
				visitedNodes.add(nodeId);
				log.info("PP_Order Node:" + node.getName() != null ? node.getName() : ""  + " Description:" + node.getDescription() != null ? node.getDescription() : "");
				//
				MResource resource = MResource.get(getCtx(), node.getS_Resource_ID());
				
				// Skip this node if there is no resource
				if(resource == null)
				{						
					nodeId = owf.getPrevious(nodeId, getAD_Client_ID());
					continue;
				}

				if(!reasoner.isAvailable(resource))
				{
					throw new CRPException("@ResourceNotInSlotDay@").setS_Resource(resource);
				}

				long nodeMillis = calculateMillisFor(node, owf.getDurationBaseSec());
				Timestamp dateStart = scheduleBackward(date, nodeMillis ,resource);

				node.setDateStartSchedule(dateStart);
				node.setDateFinishSchedule(date);
				node.saveEx();

				date = node.getDateStartSchedule();
				nodeId = owf.getPrevious(nodeId, getAD_Client_ID());
			}
			// Update order start date
			if (node != null && node.getDateStartSchedule() != null)
			{
				order.setDateStartSchedule(node.getDateStartSchedule()) ;
			}
		}
		else
		{
			throw new CRPException("Unknown scheduling method - "+p_ScheduleType);
		}

		order.saveEx(get_TrxName());
	}

	/**
	 * Calculate how many millis take to complete given qty on given node(operation).
	 * @param node operation
	 * @param commonBase multiplier to convert duration to seconds 
	 * @return duration in millis
	 */
	private long calculateMillisFor(MPPOrderNode node, long commonBase)
	{
		final BigDecimal qty = node.getQtyToDeliver();
		// Total duration of workflow node (seconds) ...
		// ... its static single parts ...
		long totalDuration =
				+ node.getQueuingTime() 
				+ node.getSetupTimeRequiered() // Use the present required setup time to notice later changes  
				+ node.getMovingTime() 
				+ node.getWaitingTime()
		;
		// ... and its qty dependend working time ... (Use the present required duration time to notice later changes)
		final BigDecimal workingTime = routingService.estimateWorkingTime(node, qty);
		totalDuration += workingTime.doubleValue();
		
		// Returns the total duration of a node in milliseconds.
		return (long)(totalDuration * commonBase * 1000);
	}

	/**
	 * Calculate duration in millis 
	 * @param dayStart
	 * @param dayEnd
	 * @param resource
	 * @return dayEnd - dayStart in millis
	 * @throws CRPException if dayStart > dayEnd
	 */
	private long getAvailableDurationMillis(Timestamp dayStart, Timestamp dayEnd, I_S_Resource resource)
	{
		long availableDayDuration = dayEnd.getTime() - dayStart.getTime();
		log.info("--> availableDayDuration  " + availableDayDuration);
		if (availableDayDuration < 0)
		{
			throw new CRPException("@TimeSlotStart@ > @TimeSlotEnd@ ("+dayEnd+" > "+dayStart+")")
					.setS_Resource(resource);
		}
		return availableDayDuration;
	}
	
	private Timestamp scheduleForward(final Timestamp start, final long nodeDurationMillis, MResource r)
	{
		MResourceType t = r.getResourceType();
		int iteration = 0; // statistical interation count
		Timestamp currentDate = start;
		Timestamp end = null;
		long remainingMillis = nodeDurationMillis;
		do
		{
			currentDate = reasoner.getAvailableDate(r, currentDate, false);
			Timestamp dayStart = t.getDayStart(currentDate);
			Timestamp dayEnd = t.getDayEnd(currentDate);
			// If working has already began at this day and the value is in the range of the 
			// resource's availability, switch start time to the given again
			if(currentDate.after(dayStart) && currentDate.before(dayEnd))
			{
				dayStart = currentDate;
			}
	
			// The available time at this day in milliseconds
			long availableDayDuration = getAvailableDurationMillis(dayStart, dayEnd, r);
	
			// The work can be finish on this day.
			if(availableDayDuration >= remainingMillis)
			{
				end = new Timestamp(dayStart.getTime() + remainingMillis);
				remainingMillis = 0;
				break;
			}
			// Otherwise recall with next day and the remained node duration.
			else
			{
				currentDate = TimeUtil.addDays(TimeUtil.getDayBorder(currentDate, null, false), 1);
				remainingMillis -= availableDayDuration;
			}
			
			iteration++;
			if (iteration > p_MaxIterationsNo)
			{
				throw new CRPException("Maximum number of iterations exceeded ("+p_MaxIterationsNo+")"
						+" - Date:"+currentDate+", RemainingMillis:"+remainingMillis);
			}
		} while (remainingMillis > 0);

		return end;
	}  	

	/**
	 * Calculate start date having duration and resource
	 * @param end end date
	 * @param nodeDurationMillis duration [millis]
	 * @param r resource
	 * @return start date
	 */
	private Timestamp scheduleBackward(final Timestamp end, final long nodeDurationMillis, MResource r)
	{
		MResourceType t = r.getResourceType();
		log.info("--> ResourceType " + t);
		Timestamp start = null;
		Timestamp currentDate = end;
		long remainingMillis = nodeDurationMillis;
		int iteration = 0; // statistical iteration count
		do
		{
			log.info("--> end=" + currentDate);
			log.info("--> nodeDuration=" + remainingMillis);
	
			currentDate = reasoner.getAvailableDate(r, currentDate, true);
			log.info("--> end(available)=" + currentDate);
			Timestamp dayEnd = t.getDayEnd(currentDate);
			Timestamp dayStart = t.getDayStart(currentDate);
			log.info("--> dayStart=" + dayStart + ", dayEnd=" + dayEnd);
			
			// If working has already began at this day and the value is in the range of the 
			// resource's availability, switch end time to the given again
			if(currentDate.before(dayEnd) && currentDate.after(dayStart))
			{
				dayEnd = currentDate;
			}
	
			// The available time at this day in milliseconds
			long availableDayDuration = getAvailableDurationMillis(dayStart, dayEnd, r);
	
			// The work can be finish on this day.
			if(availableDayDuration >= remainingMillis)
			{
				log.info("--> availableDayDuration >= nodeDuration true " + availableDayDuration + "|" + remainingMillis );
				start = new Timestamp(dayEnd.getTime() - remainingMillis);
				remainingMillis = 0;
				break;
			}
			// Otherwise recall with previous day and the remained node duration.
			else
			{
				log.info("--> availableDayDuration >= nodeDuration false " + availableDayDuration + "|" + remainingMillis );
				log.info("--> nodeDuration-availableDayDuration " + (remainingMillis-availableDayDuration) );
				
				currentDate = TimeUtil.addDays(TimeUtil.getDayBorder(currentDate, null, true), -1);
				remainingMillis -= availableDayDuration;
			}
			//
			iteration++;
			if (iteration > p_MaxIterationsNo)
			{
				throw new CRPException("Maximum number of iterations exceeded ("+p_MaxIterationsNo+")"
						+" - Date:"+start+", RemainingMillis:"+remainingMillis);
			}
		}
		while(remainingMillis > 0);
	
		log.info("         -->  start=" +  start + " <---------------------------------------- ");
		return start;
	}
}

