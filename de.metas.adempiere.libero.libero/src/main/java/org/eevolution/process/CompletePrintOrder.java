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

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.util.Services;
import org.compiere.model.MQuery;
import org.compiere.model.MQuery.Operator;
import org.compiere.model.PrintInfo;
import org.compiere.print.MPrintFormat;
import org.compiere.print.ReportCtl;
import org.compiere.print.ReportEngine;
import org.compiere.process.ClientProcess;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.eevolution.exceptions.LiberoException;
import org.eevolution.model.MPPOrder;

/**
 * Complete & Print Manufacturing Order
 * @author victor.perez@e-evolution.com
 * @author Teo Sarca, www.arhipac.ro
 */
public class CompletePrintOrder extends SvrProcess
implements ClientProcess
{
	/** The Order */
	private int p_PP_Order_ID = 0;
	private boolean p_IsPrintPickList = false;
	private boolean p_IsPrintWorkflow = false;
	private boolean p_IsPrintPackList = false; // for future use
	private boolean p_IsComplete = false;

	/**
	 * Prepare - e.g., get Parameters.
	 */
	@Override
	protected void prepare()
	{
		for (ProcessInfoParameter para : getParameter())
		{
			String name = para.getParameterName();
			if (para.getParameter() == null)
				;
			else if (name.equals("PP_Order_ID"))
				p_PP_Order_ID = para.getParameterAsInt();
			else if (name.equals("IsPrintPickList"))
				p_IsPrintPickList = para.getParameterAsBoolean();
			else if (name.equals("IsPrintWorkflow"))
				p_IsPrintWorkflow = para.getParameterAsBoolean();
			else if (name.equals("IsPrintPackingList"))
				p_IsPrintPackList = para.getParameterAsBoolean();
			else if (name.equals("IsComplete"))
				p_IsComplete = para.getParameterAsBoolean();
			else
				log.error("prepare - Unknown Parameter: " + name);
		}
	} // prepare

	/**
	 * Perform process.
	 * 
	 * @return Message (clear text)
	 * @throws Exception
	 *             if not successful
	 */
	@Override
	protected String doIt() throws Exception
	{

		if (p_PP_Order_ID == 0)
		{
			throw new FillMandatoryException(MPPOrder.COLUMNNAME_PP_Order_ID);
		}

		if (p_IsComplete)
		{
			MPPOrder order = new MPPOrder(getCtx(), p_PP_Order_ID, get_TrxName());
			if (!order.isAvailable())
			{
				throw new LiberoException("@NoQtyAvailable@");
			}
			//
			// Process document
			boolean ok = order.processIt(MPPOrder.DOCACTION_Complete);
			order.saveEx();
			if (!ok)
			{
				throw new LiberoException(order.getProcessMsg());
			}
			//
			// Document Status should be completed
			if (!MPPOrder.DOCSTATUS_Completed.equals(order.getDocStatus()))
			{
				throw new LiberoException(order.getProcessMsg());
			}
		}

		if (p_IsPrintPickList)
		{
			// Get Format & Data
			ReportEngine re = this.getReportEngine("Manufacturing_Order_BOM_Header ** TEMPLATE **","PP_Order_BOM_Header_v");
			if(re == null )
			{
				return "";
			}
			ReportCtl.preview(re);
			re.print(); // prints only original
		}
		if (p_IsPrintPackList)
		{
			// Get Format & Data
			ReportEngine re = this.getReportEngine("Manufacturing_Order_BOM_Header_Packing ** TEMPLATE **","PP_Order_BOM_Header_v");
			if(re == null )
			{
				return "";
			}
			ReportCtl.preview(re);
			re.print(); // prints only original
		}
		if (p_IsPrintWorkflow)
		{
			// Get Format & Data
			ReportEngine re = this.getReportEngine("Manufacturing_Order_Workflow_Header ** TEMPLATE **","PP_Order_Workflow_Header_v");
			if(re == null )
			{
				return "";
			}
			ReportCtl.preview(re);
			re.print(); // prints only original
		}

		return "@OK@";

	} // doIt
	
	/*
	 * get the a Report Engine Instance using the view table 
	 * @param tableName
	 */
	private ReportEngine getReportEngine(final String formatName, final String tableName)
	{
		final int adTableId = Services.get(IADTableDAO.class).retrieveTableId(tableName);
		
		// Get Format & Data
		int format_id= MPrintFormat.getPrintFormat_ID(formatName, adTableId, getAD_Client_ID());
		MPrintFormat format = MPrintFormat.get(getCtx(), format_id, true);
		if (format == null)
		{
			addLog("@NotFound@ @AD_PrintFormat_ID@");
			return null;
		}
		// query
		MQuery query = new MQuery(tableName);
		query.addRestriction("PP_Order_ID", Operator.EQUAL, p_PP_Order_ID);
		// Engine
		PrintInfo info = new PrintInfo(tableName,  adTableId, p_PP_Order_ID);
		ReportEngine re = new ReportEngine(getCtx(), format, query, info);
		return re;
	}
} // CompletePrintOrder
