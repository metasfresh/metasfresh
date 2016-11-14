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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.IUOMBL;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.model.MResource;
import org.compiere.model.MResourceType;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.eevolution.model.X_T_MRP_CRP;

/**
 *	Re-Open Order Process (from Closed to Completed)
 *	
 *  @author Victor P�rez, e-Evolution, S.C.
 *  @version $Id: CreateCost.java,v 1.1 2004/06/22 05:24:03 vpj-cd Exp $
 */
@SuppressWarnings("all") // tsa: to many warnings in a code that we don't use. Suppress all to reduce noise.
public class CRPSummary extends SvrProcess
{
	/**					*/

        private int               p_S_Resource_ID = 0 ;        
        private Timestamp         p_DateFrom = null;
        private Timestamp         p_DateTo = null; 
        private String            p_FrequencyType = null;
        private int AD_Client_ID = 0;
        private int AD_PInstance_ID = 0;
        
       
        
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		AD_Client_ID = Integer.parseInt(Env.getContext(Env.getCtx(), "#AD_Client_ID"));
                ProcessInfoParameter[] para = getParametersAsArray();
                
        AD_PInstance_ID = getAD_PInstance_ID();        
               
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();

			if (para[i].getParameter() == null)
				;
            else if (name.equals("S_Resource_ID"))
            {    
            		p_S_Resource_ID = ((BigDecimal)para[i].getParameter()).intValue();
                    
            }
			else if (name.equals("DateFrom"))
            {    
				p_DateFrom = ((Timestamp)para[i].getParameter());				 		
            }
			else if (name.equals("DateTo"))
	        {    
					p_DateTo = ((Timestamp)para[i].getParameter());				 		
	        }
			else if (name.equals("FrequencyType"))
	        {    
					p_FrequencyType = ((String)para[i].getParameter());				 		
	        }
            else
				log.error("prepare - Unknown Parameter: " + name);
		}
	}	//	prepare
       
     protected String doIt() throws Exception                
     {
  
         return runCRP();
     } 
     
     protected String runCRP()
     {
     	/*
     	String sql = "SELECT owf.PP_Order_Workflow_ID , o.DateStartSchedule , o.DateFinishSchedule ,o.QtyOrdered - o.QtyDelivered - o.QtyScrap AS QtyOpen FROM PP_Order o INNER JOIN PP_Order_Workflow owf ON (owf.PP_ORDER_ID = o.PP_Order_ID) WHERE o.DocStatus <> 'CL' AND o.AD_Client_ID = ? AND o.S_Resource_ID= ? ORDER BY DatePromised" ;		
        try
		{
                PreparedStatement pstmt = null;
                pstmt = DB.prepareStatement (sql);
                pstmt.setInt(1, AD_Client_ID);
                pstmt.setInt(1, p_S_Resource_ID);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next())
                {
                	BigDecimal QtyOpen = rs.getBigDecimal(4);
                	
                	MPP_OrderWorkflow owf = new  MPP_OrderWorkflow(getCtx(), rs.getInt(1),null);  
                	                	

                	// Schedule Fordward
                	if (p_ScheduleType.equals("F"))
                	{
                		Timestamp date = rs.getTimestamp(2); 
                		int node = owf.getPP_Order_Node_ID();
                		long seconds = 0;
                		while(node != 0)
                		{
                			MPP_OrderNode n = new MPP_OrderNode(getCtx(),node , null);
                			// Calculate Total seconds for Node 
                			seconds = (n.getQueuingTime() + n.getSetupTime() + QtyOpen.multiply(new BigDecimal(n.getDuration())).longValue() + n.getMovingTime() + n.getWaitingTime()) * owf.getDurationBaseSec();
                			// Calculate Factor Day
                			MResource r = MResource.get(getCtx(),n.getS_Resource_ID());
                			if (r == null)
                			continue;
                			
                			MResourceType type = MResourceType.get(Env.getCtx(),r.getS_ResourceType_ID());
                			long hours = 0 ;
                			
                			if (type.isTimeSlot())                			
                			hours = getHoursAvailable(type.getTimeSlotStart(),type.getTimeSlotStart());                		
                			else 
                			hours  = 24;
                			
                			long factor = (hours * 3600) / 86400; // factor = second for a day / avlailable hour in sencond
                			System.out.print("factor:" + factor);
                			long totalseconds = (seconds / factor) ; // (total seconds * factor seconds avaialble) / a day in seconds                			
                			Long day = new Long((totalseconds/(hours* 3600)));
                			Timestamp dateFinishSchedule = getDate(date, day.intValue() ,type);
                			n.setDateStartSchedule(date);
                			n.setDateFinishSchedule(dateFinishSchedule);	
							n.save(get_TrxName());
                			node = owf.getNext(node);
                			date = n.getDateFinishSchedule();
                		}
                	}
                	
                	// schedule backward
                	if (p_ScheduleType.equals("B"))
                	{
                		Timestamp date = rs.getTimestamp(3); 
                		int node = owf.getLast(0);
                		//System.out.print("First Node" + node);
                		long seconds = 0;
                		while(node != 0)
                		{
                			MPP_OrderNode n = new MPP_OrderNode(getCtx(),node , null);
                			// Calculate Total seconds for Node 
                			seconds = (n.getQueuingTime() + n.getSetupTime() + QtyOpen.multiply(new BigDecimal(n.getDuration())).longValue() + n.getMovingTime() + n.getWaitingTime()) * owf.getDurationBaseSec();
                			// Calculate Factor Day
                			MResource r = MResource.get(getCtx(),n.getS_Resource_ID());
                			if (r == null)
                    			continue;
                			
                			MResourceType type = MResourceType.get(Env.getCtx(),r.getS_ResourceType_ID());
                			long hours = 0 ;
                			
                			if (type.isTimeSlot())                			
                			hours = getHoursAvailable(type.getTimeSlotStart(),type.getTimeSlotStart());                		
                			else 
                			hours  = 24;
                			
                			long factor = (hours * 3600) / 86400; // factor = second for a day / avlailable hour in sencond
                			System.out.print("factor:" + factor);
                			long totalseconds = (seconds / factor) ; // (total seconds * factor seconds avaialble) / a day in seconds                			
                			Long day = new Long((totalseconds/(hours* 3600)) * -1 );
                			Timestamp dateStartSchedule = getDate(date, day.intValue() ,type);
                			n.setDateFinishSchedule(date);
							n.setDateStartSchedule(dateStartSchedule);
							n.save(get_TrxName());
                			node = owf.getPrevious(node);
                			date = n.getDateStartSchedule();
                		}
                	}
                }
                rs.close();
                pstmt.close();              
		}
        catch (Exception e)
		{
        	log.error("doIt - " + sql, e);
            return "";
		}    */
     	return "";
     }
     
 	/**
 	 * 	Return DateTime + offset in Second
 	 * 	@param dateTime Date and Time
 	 * 	@param offset Second offset
 	 * 	@return dateTime + offset in Second
 	 */
 	static public Timestamp addSecond (Timestamp dateTime, long offset)
 	{
 		if (dateTime == null)
 			dateTime = new Timestamp(System.currentTimeMillis());
 		if (offset == 0)
 			return dateTime;
 		//
 		GregorianCalendar cal = new GregorianCalendar();
 		cal.setTime(dateTime);
 		cal.add(Calendar.SECOND, new Long(offset).intValue());			//	may have a problem with negative
 		return new Timestamp (cal.getTimeInMillis());
 	}	//	addMinutes
     
 	
 	/**
 	 * 	Return horus in 
 	 * 	@param Time Start
 	 * 	@param Time End
 	 * 	@return hours
 	 */
 	public long getHoursAvailable(Timestamp time1 , Timestamp time2)
 	{
        GregorianCalendar g1 = new GregorianCalendar();
        g1.setTimeInMillis(time1.getTime());
        GregorianCalendar g2 = new GregorianCalendar();
        g1.setTimeInMillis(time2.getTime());
        // the above two dates are one second apart
        Date d1 = g1.getTime();
        Date d2 = g2.getTime();
        long l1 = d1.getTime();
        long l2 = d2.getTime();
        long difference = l2 - l1;
        System.out.println("Elapsed milliseconds: " + difference);
        return difference;        
     }
 	
 	// Calculate Days not Avialable from Date to Date for a Resource
 	public int getNotAvailbleDays(Timestamp start, Timestamp end, MResourceType t)
 	{
 		 if (!t.isDateSlot())
 		 	return 0;		
 		 
		 GregorianCalendar g1 = new GregorianCalendar();
		 g1.setTimeInMillis(start.getTime());
		 GregorianCalendar g2 = new GregorianCalendar();
		 g2.setTimeInMillis(end.getTime());
 		 		
 		 //int elapsed = 1;
 		 GregorianCalendar gc1, gc2;

 		 if (g2.after(g1)) {
 		 gc2 = (GregorianCalendar) g2.clone();
 		 gc1 = (GregorianCalendar) g1.clone();
 		 }
 		 else {
 		 gc2 = (GregorianCalendar) g1.clone();
 		 gc1 = (GregorianCalendar) g2.clone();
 		 }

 		 gc1.clear(Calendar.MILLISECOND);
 		 gc1.clear(Calendar.SECOND);
 		 gc1.clear(Calendar.MINUTE);
 		 gc1.clear(Calendar.HOUR_OF_DAY);

 		 gc2.clear(Calendar.MILLISECOND);
 		 gc2.clear(Calendar.SECOND);
 		 gc2.clear(Calendar.MINUTE);
 		 gc2.clear(Calendar.HOUR_OF_DAY);
 		 
 		int DaysNotAvialable = 0; 
 	  	  	

 		 while ( gc1.before(gc2) ) 
 		 {
 		 		gc1.add(Calendar.DATE, 1);
 		 		
 		 		switch(gc1.get(Calendar.DAY_OF_WEEK))
				{
					case Calendar.SUNDAY:
						if (!t.isOnSunday())
							DaysNotAvialable++;
						break;
					case Calendar.MONDAY:
						if (!t.isOnMonday())
							DaysNotAvialable++;
						break;
					case Calendar.TUESDAY:
						if (!t.isOnTuesday())
							DaysNotAvialable++;
						break;
					case Calendar.WEDNESDAY:
						if (!t.isOnWednesday())
							DaysNotAvialable++;
						break;
					case Calendar.THURSDAY:
						if (!t.isOnThursday())
							DaysNotAvialable++;
						break;
					case Calendar.FRIDAY:
						if (!t.isOnFriday())
							DaysNotAvialable++;
						break;
					case Calendar.SATURDAY:
						if (!t.isOnSaturday())	
							DaysNotAvialable++;
						break;
				} 		
 		 }
 		 
 		 
 		 
 		 System.out.println("DaysNotAvialable"+ DaysNotAvialable);
 		 return DaysNotAvialable;
 	} 	
 	
 	public void Summary(Timestamp start, Timestamp finish , MResource r)
 	{

		 GregorianCalendar gc1 = new GregorianCalendar();
		 gc1.setTimeInMillis(start.getTime());
		 gc1.clear(Calendar.MILLISECOND);
 		 gc1.clear(Calendar.SECOND);
 		 gc1.clear(Calendar.MINUTE);
 		 gc1.clear(Calendar.HOUR_OF_DAY);
 		 
 		GregorianCalendar gc2 = new GregorianCalendar();
		 gc2.setTimeInMillis(finish.getTime());
		 gc2.clear(Calendar.MILLISECOND);
		 gc2.clear(Calendar.SECOND);
		 gc2.clear(Calendar.MINUTE);
		 gc2.clear(Calendar.HOUR_OF_DAY);
		 
		 MResourceType t = MResourceType.get(Env.getCtx(),r.getS_ResourceType_ID());
	     long hours = 0;
	     
		 if (t.isTimeSlot())                			
 			hours = getHoursAvailable(t.getTimeSlotStart(),t.getTimeSlotStart());                		
 		 else 
 		 	hours  = 24;
		 
		 boolean available = false;
		 ArrayList<Col> list = new ArrayList<Col>();
		 
		 int col = 0;
		 int row =  1;
		 int summary = 0;
		 
		 Col cols = new Col();
		 cols.setFrom("Past Due");
		 cols.setTo(start.toString());
		 cols.setDays(0);
		 cols.setCapacity(0);
		 cols.setLoad(0);
		 cols.setSummary(0);
		
		 list.add(0,cols);
		 
		 col ++;
		 
 		 while(gc1.before(gc2))
 		 {	
 		 		gc1.add(Calendar.DATE, 1);
 		 		
 		 		switch(gc1.get(Calendar.DAY_OF_WEEK))
				{
					case Calendar.SUNDAY:
						if (t.isOnSunday())
							available = true; 
						break;
					case Calendar.MONDAY:
						if (t.isOnMonday())
							available = true; 
						break;
					case Calendar.TUESDAY:
						if (t.isOnTuesday())
							 available = true; 
						break;
					case Calendar.WEDNESDAY:
						if (t.isOnWednesday())
							 available = true; 
						break;
					case Calendar.THURSDAY:
						if (t.isOnThursday())
							 available = true; 
						break;
					case Calendar.FRIDAY:
						if (t.isOnFriday())
							 available = true; 
						break;
					case Calendar.SATURDAY:
						if (t.isOnSaturday())	
							 available = true; 
						break;
				}
 		 		
 		 		
 		 		
 		 		if  (available)
 		 		{
 		 			 cols = new Col();
 		 			 cols.setFrom(gc1.getTime().toString());
 		 			 cols.setTo(gc1.getTime().toString());
 		 			 cols.setDays(1);
 		 			 Long Hours = new Long(hours); 
 		 			 cols.setCapacity(Hours.intValue());
 		 			 int C_UOM_ID = DB.getSQLValue(null,"SELECT C_UOM_ID FROM M_Product WHERE S_Resource_ID = ? " , r.getS_Resource_ID());
 		 			
 		 			 // 07525
 		 			 // Refactoring to fit the new UOM logic
 		 			 //MUOM oum = MUOM.get(getCtx(),C_UOM_ID);
 		 			 final I_C_UOM productUOM = InterfaceWrapperHelper.create(getCtx(), C_UOM_ID, I_C_UOM.class, ITrx.TRXNAME_None);
 		 			
 		 			 final boolean isHourProductUOM = Services.get(IUOMBL.class).isHour(productUOM);
 		 			 if(isHourProductUOM)
 		 			 {	
 		 			 Timestamp date = new Timestamp(gc1.getTimeInMillis()); 
 		 			 int seconds = getLoad(r.getS_Resource_ID(),date ,date);
 		 			 cols.setLoad(seconds / 3600 );
 		 			 }
 		 			 cols.setSummary(summary + cols.getDifference()); 
 		 			 summary = cols.getSummary();
 		 			 list.add(col,cols); 		 			
 		 		} 		 		
 		 } 	
 		 
 	   col = 0;
 	   boolean newrow = true;
 	   Col[] lines = new Col[list.size()];
 	   
 	   //set Title
 	   for (int z = 0 ; z <= lines.length ; z ++)
	   {
 	  	
	   }
 	  
       for (int i = 0 ; i <= lines.length ; i ++)
	   {       	
       	
       	if (newrow)
       	{
       		X_T_MRP_CRP crp = new X_T_MRP_CRP(getCtx(),0 , null);
       		crp.setDescription("CRP Resource" + r.getName());
       		//crp.setRange00(lines[i].getFrom() + "/" + lines[i].getTo());
       		
       	}
       		
       		
       		switch(col)
			{
			case 0:
				col++;	
			case 1:
				col++;	
			case 2:	
				col++;
			case 3:	
				col++;
			case 4:	
				col++;
			case 5:	
				col++;
			case 6:	
				col++;
			case 7:	
				col++;
			case 8:	
				col++;
			case 9:
				col++;
			case 10:
				col++;
			case 11:
				col++;
			case 12:
				col=0;	
				newrow = true; 	
		}
       	
       	col ++;
	   }
 		 
 	}
 		
 	int getLoad(int S_Resource_ID, Timestamp start, Timestamp end)
 	{
 		int load = 0;
 		String sql = "SELECT SUM( CASE WHEN ow.DurationUnit = 's'  THEN 1 * (onode.QueuingTime + onode.SetupTime + (onode.Duration * (o.QtyOrdered - o.QtyDelivered - o.QtyScrap)) + onode.MovingTime + onode.WaitingTime) WHEN ow.DurationUnit = 'm' THEN 60 * (onode.QueuingTime + onode.SetupTime + (onode.Duration * (o.QtyOrdered - o.QtyDelivered - o.QtyScrap)) + onode.MovingTime + onode.WaitingTime) WHEN ow.DurationUnit = 'h'  THEN 3600 * (onode.QueuingTime + onode.SetupTime + (onode.Duration * (o.QtyOrdered - o.QtyDelivered - o.QtyScrap)) + onode.MovingTime + onode.WaitingTime) WHEN ow.DurationUnit = 'Y'  THEN 31536000 *  (onode.QueuingTime + onode.SetupTime + (onode.Duration * (o.QtyOrdered - o.QtyDelivered - o.QtyScrap)) + onode.MovingTime + onode.WaitingTime) WHEN ow.DurationUnit = 'M' THEN 2592000 * (onode.QueuingTime + onode.SetupTime + (onode.Duration * (o.QtyOrdered - o.QtyDelivered - o.QtyScrap)) + onode.MovingTime + onode.WaitingTime) WHEN ow.DurationUnit = 'D' THEN 86400 END ) AS Load FROM PP_Order_Node onode INNER JOIN PP_Order_Workflow ow ON (ow.PP_Order_Workflow_ID =  onode.PP_Order_Workflow_ID) INNER JOIN PP_Order o ON (o.PP_Order_ID = onode.PP_Order_ID)  WHERE onode. = ?  AND onode.DateStartSchedule => ? AND onode.DateFinishSchedule =< ? AND onode.AD_Client_ID = ?" ;		
        try
		{
                PreparedStatement pstmt = null;
                pstmt = DB.prepareStatement (sql, get_TrxName());
                pstmt.setInt(1, S_Resource_ID);
                pstmt.setTimestamp(1, start);
                pstmt.setTimestamp(2, end);
                pstmt.setInt(3,AD_Client_ID); 
                ResultSet rs = pstmt.executeQuery();
                while (rs.next())
                {    			
	 			 load = rs.getInt(1);
                } 
	 		    rs.close();
	            pstmt.close();
	            return load;
			}
	        catch (Exception e)
			{
	        	log.error("doIt - " + sql, e);
			}    
	 			return 0;
 	}

 	
 	private class Col
	{
 		int Day = 0;
 		String From = null;
 		String To = null;
 		int Capacity = 0;
 		int Load = 0;
 		int Summary = 0;
 		
 		public Col()
 		{
 		}
 		
 		void setDays(int day)
 		{
 			Day = day;
 		}
 		
 		int getDays()
 		{
 			return Day;
 		}
 		
 		void setCapacity(int capacity)
 		{
 			Capacity = capacity;
 		}
 		
 		int getCapacity()
 		{
 			return Capacity;
 		}
 		
 		void setLoad(int load)
 		{
 			Load = load;
 		}
 		
 		int getLoad()
 		{
 			return Load;
 		}
 		
 		int getDifference()
 		{
 			return Capacity - Load;
 		}
 		
 		void setSummary(int summary)
 		{
 			Summary = summary;
 		}
 		
 		int getSummary()
 		{
 			return Summary;
 		}
 		
 		void setFrom(String  from)
 		{
 			From = from;
 		}
 		
 		String getFrom()
 		{
 			return From;
 		}
 		
 		void setTo(String  to)
 		{
 			To = to;
 		}
 		
 		String getTo()
 		{
 			return To;
 		}
		
 	}
 	
 }
  
