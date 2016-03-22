package org.compiere.print;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.util.Properties;
import java.util.Vector;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.compiere.model.MProcess;
import org.compiere.model.MQuery;
import org.compiere.model.MTable;
import org.compiere.model.PrintInfo;
import org.compiere.process.ProcessInfo;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.ServerProcessCtl;
import org.compiere.util.ASyncProcess;
import org.compiere.util.Env;



public class ServerReportCtl {

	/**
	 * Constants used to pass process parameters to Jasper Process
	 */
	public static final String PARAM_PRINTER_NAME = "PRINTER_NAME";
	public static final String PARAM_PRINT_FORMAT = "PRINT_FORMAT";
	public static final String PARAM_PRINT_INFO = "PRINT_INFO";
	
	/**	Static Logger	*/
	private static Logger	s_log	= LogManager.getLogger(ServerReportCtl.class);
	
	/**
	 * 	Start Document Print for Type with specified printer.
	 * 	@param type document type in ReportEngine
	 * 	@param Record_ID id
	 *  @param parent The window which invoked the printing
	 *  @param WindowNo The windows number which invoked the printing
	 * 	@param printerName 	Specified printer name
	 * 	@return true if success
	 */
	public static boolean startDocumentPrint (int type, MPrintFormat customPrintFormat, int Record_ID, String printerName)
	{
		ReportEngine re = ReportEngine.get (Env.getCtx(), type, Record_ID);
		if (re == null)
		{
			Logger log = LogManager.getLogger(ServerReportCtl.class);
			log.warn("NoDocPrintFormat");
			return false;
		}
		if (customPrintFormat!=null) {
			// Use custom print format if available
			re.setPrintFormat(customPrintFormat);
		}
		
		if (re.getPrintFormat()!=null)
		{
			MPrintFormat format = re.getPrintFormat();
			
			// We have a Jasper Print Format
			// ==============================
			if(format.getJasperProcess_ID() > 0)	
			{
				boolean result = runJasperProcess(Record_ID, re, true, printerName);
				return(result);
			}
			else
			// Standard Print Format (Non-Jasper)
			// ==================================
			{
				createOutput(re, printerName);
				ReportEngine.printConfirm (type, Record_ID);
			}
		}
		return true;
	}	//	StartDocumentPrint
	

	/**
	 * Runs a Jasper process that prints the record
	 * 
	 * @param format
	 * @param Record_ID
	 * @param re
	 * @param IsDirectPrint
	 * @param printerName
	 * @return
	 */
	public static boolean runJasperProcess(int Record_ID, ReportEngine re, boolean IsDirectPrint, String printerName) {
		MPrintFormat format = re.getPrintFormat();
		ProcessInfo pi = new ProcessInfo ("", format.getJasperProcess_ID());
		pi.setPrintPreview( !IsDirectPrint );
		pi.setRecord_ID ( Record_ID );
		Vector<ProcessInfoParameter> jasperPrintParams = new Vector<ProcessInfoParameter>();
		ProcessInfoParameter pip;
		if (printerName!=null && printerName.trim().length()>0) {
			// Override printer name
			pip = new ProcessInfoParameter(PARAM_PRINTER_NAME, printerName, null, null, null);
			jasperPrintParams.add(pip);
		}
		pip = new ProcessInfoParameter(PARAM_PRINT_FORMAT, format, null, null, null);
		jasperPrintParams.add(pip);
		pip = new ProcessInfoParameter(PARAM_PRINT_INFO, re.getPrintInfo(), null, null, null);
		jasperPrintParams.add(pip);
		
		pi.setParameter(jasperPrintParams.toArray(new ProcessInfoParameter[]{}));
		
		ServerProcessCtl.process(null,		// Parent set to null for synchronous processing, see bugtracker 3010932  
				   pi,
				   null); 		
		
		boolean result = true;
		return(result);
	}
	
	/**
	 * Create output (server only)
	 * 
	 * @param re
	 * @param printerName
	 */
	private static void createOutput(ReportEngine re, String printerName)
	{
		if (printerName!=null) {
			re.getPrintInfo().setPrinterName(printerName);
		}
		re.print();
	}
	
	
	/**
	 *	Create Report.
	 *	Called from ProcessCtl.
	 *	- Check special reports first, if not, create standard Report
	 *
	 *  @param parent The window which invoked the printing
	 *  @param WindowNo The windows number which invoked the printing
	 *  @param pi process info
	 *  @param IsDirectPrint if true, prints directly - otherwise View
	 *  @return true if created
	 */
	static public boolean start (ASyncProcess parent, ProcessInfo pi)
	{

		/**
		 *	Order Print
		 */
		if (pi.getAD_Process_ID() == 110)			//	C_Order
			return startDocumentPrint(ReportEngine.ORDER, null, pi.getRecord_ID(), null);
		if (pi.getAD_Process_ID() ==  MProcess.getProcess_ID("Rpt PP_Order", null))			//	C_Order
			return startDocumentPrint(ReportEngine.MANUFACTURING_ORDER, null, pi.getRecord_ID(), null);
		if (pi.getAD_Process_ID() ==  MProcess.getProcess_ID("Rpt DD_Order", null))			//	C_Order
			return startDocumentPrint(ReportEngine.DISTRIBUTION_ORDER, null, pi.getRecord_ID(), null);
		else if (pi.getAD_Process_ID() == 116)		//	C_Invoice
			return startDocumentPrint(ReportEngine.INVOICE, null, pi.getRecord_ID(), null);
		else if (pi.getAD_Process_ID() == 117)		//	M_InOut
			return startDocumentPrint(ReportEngine.SHIPMENT, null, pi.getRecord_ID(), null);
		else if (pi.getAD_Process_ID() == 217)		//	C_Project
			return startDocumentPrint(ReportEngine.PROJECT, null, pi.getRecord_ID(), null);
		else if (pi.getAD_Process_ID() == 276)		//	C_RfQResponse
			return startDocumentPrint(ReportEngine.RFQ, null, pi.getRecord_ID(), null);
		else if (pi.getAD_Process_ID() == 159)		//	Dunning
			return startDocumentPrint(ReportEngine.DUNNING, null, pi.getRecord_ID(), null);
 	    else if (pi.getAD_Process_ID() == 202			//	Financial Report
			|| pi.getAD_Process_ID() == 204)			//	Financial Statement
		   return startFinReport (pi);
		/********************
		 *	Standard Report
		 *******************/
		return startStandardReport (pi);
	}	//	create

	/**************************************************************************
	 *	Start Standard Report.
	 *  - Get Table Info & submit
	 *  @param pi Process Info
	 *  @param IsDirectPrint if true, prints directly - otherwise View
	 *  @return true if OK
	 */
	static public boolean startStandardReport (ProcessInfo pi, boolean IsDirectPrint)
	{
		pi.setPrintPreview(!IsDirectPrint);
		return startStandardReport(pi);
	}
	
	/**************************************************************************
	 *	Start Standard Report.
	 *  - Get Table Info & submit.<br>
	 *  A report can be created from:
	 *  <ol>
	 *  <li>attached MPrintFormat, if any (see {@link ProcessInfo#setTransientObject(Object)}, {@link ProcessInfo#setSerializableObject(java.io.Serializable)}
	 *  <li>process information (AD_Process.AD_PrintFormat_ID, AD_Process.AD_ReportView_ID)
	 *  </ol>
	 *  @param pi Process Info
	 *  @param IsDirectPrint if true, prints directly - otherwise View
	 *  @return true if OK
	 */
	static public boolean startStandardReport (ProcessInfo pi)
	{
		ReportEngine re = null;
		//
		// Create Report Engine by using attached MPrintFormat (if any)
		Object o = pi.getTransientObject();
		if (o == null)
			o = pi.getSerializableObject();
		if (o != null && o instanceof MPrintFormat) {
			Properties ctx = Env.getCtx();
			MPrintFormat format = (MPrintFormat)o;
			String TableName = MTable.getTableName(ctx, format.getAD_Table_ID());
			MQuery query = MQuery.get (ctx, pi.getAD_PInstance_ID(), TableName);
			PrintInfo info = new PrintInfo(pi);
			re = new ReportEngine(ctx, format, query, info);
			createOutput(re, null);
			return true;
		}
		//
		// Create Report Engine normally
		else {
			re = ReportEngine.get(Env.getCtx(), pi);
			if (re == null)
			{
				pi.setSummary("No ReportEngine");
				return false;
			}
		}
		
		createOutput(re, null);
		return true;
	}	//	startStandardReport

	/**
	 *	Start Financial Report.
	 *  @param pi Process Info
	 *  @return true if OK
	 */
	static public boolean startFinReport (ProcessInfo pi)
	{
		int AD_Client_ID = Env.getAD_Client_ID(Env.getCtx());

		//  Create Query from Parameters
		String TableName = pi.getAD_Process_ID() == 202 ? "T_Report" : "T_ReportStatement";
		MQuery query = MQuery.get (Env.getCtx(), pi.getAD_PInstance_ID(), TableName);

		//	Get PrintFormat
		MPrintFormat format = (MPrintFormat)pi.getTransientObject();
		if (format == null)
			format = (MPrintFormat)pi.getSerializableObject();
		if (format == null)
		{
			s_log.error("startFinReport - No PrintFormat");
			return false;
		}
		PrintInfo info = new PrintInfo(pi);

		ReportEngine re = new ReportEngine(Env.getCtx(), format, query, info);
		createOutput(re, null);
		return true;
	}	//	startFinReport
	
	
}
