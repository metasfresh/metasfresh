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
package org.compiere.print;

import java.awt.print.PrinterJob;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URI;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Locale;
import java.util.Properties;

import javax.print.DocFlavor;
import javax.print.StreamPrintService;
import javax.print.StreamPrintServiceFactory;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.JobName;
import javax.print.event.PrintServiceAttributeEvent;
import javax.print.event.PrintServiceAttributeListener;
import javax.xml.transform.stream.StreamResult;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.pdf.Document;
import org.adempiere.print.export.PrintDataExcelExporter;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.apache.ecs.XhtmlDocument;
import org.apache.ecs.xhtml.a;
import org.apache.ecs.xhtml.link;
import org.apache.ecs.xhtml.script;
import org.apache.ecs.xhtml.table;
import org.apache.ecs.xhtml.td;
import org.apache.ecs.xhtml.th;
import org.apache.ecs.xhtml.tr;
import org.compiere.model.I_C_DunningRunEntry;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_PaySelectionCheck;
import org.compiere.model.I_C_Project;
import org.compiere.model.I_M_InOut;
import org.compiere.model.MClient;
import org.compiere.model.MQuery;
import org.compiere.model.MQuery.Operator;
import org.compiere.model.MTable;
import org.compiere.model.PrintInfo;
import org.compiere.print.layout.LayoutEngine;
import org.compiere.process.ProcessInfo;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.compiere.util.Language;
import org.compiere.util.Msg;
import org.compiere.util.Util;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_PP_Order;
import org.slf4j.Logger;

import de.metas.adempiere.service.IPrinterRoutingBL;
import de.metas.logging.LogManager;

/**
 *	Report Engine.
 *  For a given PrintFormat,
 *  create a Report
 *  <p>
 *  Change log:
 *  <ul>
 *  <li>2007-02-12 - teo_sarca - [ 1658127 ] Select charset encoding on import
 *  <li>2007-02-10 - teo_sarca - [ 1652660 ] Save XML,HTML,CSV should have utf8 charset
 *  <li>2009-02-06 - globalqss - [ 2574162 ] Priority to choose invoice print format not working
 *  <li>2009-07-10 - trifonnt - [ 2819637 ] Wrong print format on non completed order
 *  </ul>
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: ReportEngine.java,v 1.4 2006/10/08 06:52:51 comdivision Exp $
 *
 * @author Teo Sarca, www.arhipac.ro
 * 			<li>BF [ 2828300 ] Error when printformat table differs from DOC_TABLES
 * 				https://sourceforge.net/tracker/?func=detail&aid=2828300&group_id=176962&atid=879332
 * 			<li>BF [ 2828886 ] Problem with reports from temporary tables
 * 				https://sourceforge.net/tracker/?func=detail&atid=879332&aid=2828886&group_id=176962
 *
 *  FR 2872010 - Dunning Run for a complete Dunning (not just level) - Developer: Carlos Ruiz - globalqss - Sponsor: Metas
 */
public class ReportEngine implements PrintServiceAttributeListener
{
	/**
	 *	Constructor
	 * 	@param ctx context
	 *  @param pf Print Format
	 *  @param query Optional Query
	 *  @param info print info
	 */
	public ReportEngine (Properties ctx, MPrintFormat pf, MQuery query, PrintInfo info)
	{
		this(ctx, pf, query, info, null);
	}	//	ReportEngine

	/**
	 *	Constructor
	 * 	@param ctx context
	 *  @param pf Print Format
	 *  @param query Optional Query
	 *  @param info print info
	 *  @param trxName
	 */
	public ReportEngine (Properties ctx, MPrintFormat pf, MQuery query, PrintInfo info, String trxName)
	{
		Check.errorIf(pf == null, "ReportEngine - no PrintFormat"); // emulating old code, throwing same error msg as before.
		Check.assumeNotNull(pf.getLanguage(), "Param {} has a language set", pf);

		m_printerName = Services.get(IPrinterRoutingBL.class).getDefaultPrinterName(); // metas: us319

		log.info(pf + " -- " + query);
		m_ctx = ctx;
		//
		m_printFormat = pf;
		m_info = info;
		m_trxName = trxName;
		setQuery(query);		//	loads Data

	}	//	ReportEngine

	/**	Static Logger	*/
	private static Logger	log	= LogManager.getLogger(ReportEngine.class);

	/**	Context					*/
	private Properties		m_ctx;

	/**	Print Format			*/
	private MPrintFormat	m_printFormat;
	/** Print Info				*/
	private PrintInfo		m_info;
	/**	Query					*/
	private MQuery			m_query;
	/**	Query Data				*/
	private PrintData		m_printData;
	/** Layout					*/
	private LayoutEngine 	m_layout = null;
	/**	Printer					*/
	private String			m_printerName = null; // metas: us316: commented: Ini.getProperty(Ini.P_PRINTER);
	/**	View					*/
	private View			m_view = null;
	/** Transaction Name 		*/
	private String 			m_trxName = null;
	/** Where filter */
	private String 			m_whereExtended = null;
	/** Window */
	private int m_windowNo = 0;

	private boolean m_summary = false;

	/**
	 * 	Set PrintFormat.
	 *  If Layout was created, re-create layout
	 * 	@param pf print format
	 */
	public void setPrintFormat (MPrintFormat pf)
	{
		m_printFormat = pf;
		if (m_layout != null)
		{
			final PrintData printData = getPrintData();
			m_layout.setPrintFormat(pf, false);
			m_layout.setPrintData(printData, m_query, true);	//	format changes data
		}
		if (m_view != null)
			m_view.revalidate();
	}	//	setPrintFormat

	/**
	 * 	Set Query and generate PrintData.
	 *  If Layout was created, re-create layout
	 * 	@param query query
	 */
	public void setQuery (MQuery query)
	{
		m_query = query;
		if (query == null)
			return;
		//
		m_printData = null; // reset printData
	}	//	setQuery

	/**
	 * 	Get Query
	 * 	@return query
	 */
	public MQuery getQuery()
	{
		return m_query;
	}	//	getQuery

	/**
	 * 	Set PrintData for Format restricted by Query.
	 * 	Nothing set if there is no query
	 *  Sets m_printData
	 */
	private void setPrintData()
	{
		if (m_query == null)
			return;

		final DataEngine de = new DataEngine(m_printFormat.getLanguage(),m_trxName);
		final PrintData printData = de.getPrintData (m_ctx, m_printFormat, m_query, m_summary);
		setPrintData(printData);

		if (m_layout != null)
			m_layout.setPrintData(printData, m_query, true);
		if (m_view != null)
			m_view.revalidate();

	//	m_printData.dump();
	}	//	setPrintData


	/**
	 * 	Get PrintData
	 * 	@return print data
	 */
	public PrintData getPrintData()
	{
		if (m_printData == null)
		{
			setPrintData();
		}
		return m_printData;
	}	//	getPrintData

	/**
	 * 	Set PrintData
	 * 	@param printData printData
	 */
	public void setPrintData (PrintData printData)
	{
		if (printData == null)
			return;
		m_printData = printData;
	}	//	setPrintData


	/**************************************************************************
	 * 	Layout
	 */
	private void layout()
	{
		if (m_printFormat == null)
			throw new IllegalStateException ("No print format");

		final PrintData printData = getPrintData();
		if (printData == null)
			throw new IllegalStateException ("No print data (Delete Print Format and restart)");
		m_layout = new LayoutEngine (m_printFormat, printData, m_query, m_info, m_trxName);
	}	//	layout

	/**
	 * 	Get Layout
	 *  @return Layout
	 */
	public LayoutEngine getLayout()
	{
		if (m_layout == null)
			layout();
		return m_layout;
	}	//	getLayout

	/**
	 * 	Get PrintFormat (Report) Name
	 * 	@return name
	 */
	public String getName()
	{
		return m_printFormat.getName();
	}	//	getName

	/**
	 * 	Get PrintFormat
	 * 	@return print format
	 */
	public MPrintFormat getPrintFormat()
	{
		return m_printFormat;
	}	//	getPrintFormat

	/**
	 * 	Get Print Info
	 *	@return info
	 */
	public PrintInfo getPrintInfo()
	{
		return m_info;
	}	//	getPrintInfo

	/**
	 * 	Get PrintLayout (Report) Context
	 * 	@return context
	 */
	public Properties getCtx()
	{
		return getLayout().getCtx();
	}	//	getCtx

	/**
	 * 	Get Row Count
	 * 	@return row count
	 */
	public int getRowCount()
	{
		return getPrintData().getRowCount();
	}	//	getRowCount

	/**
	 * 	Get Column Count
	 * 	@return column count
	 */
	public int getColumnCount()
	{
		if (m_layout != null)
			return m_layout.getColumnCount();
		return 0;
	}	//	getColumnCount


	/**************************************************************************
	 * 	Get View Panel
	 * 	@return view panel
	 */
	public View getView()
	{
		if (m_layout == null)
			layout();
		if (m_view == null)
			m_view = new View (m_layout);
		return m_view;
	}	//	getView


	/**************************************************************************
	 * 	Print Report
	 */
	public void print ()
	{
		log.info(m_info.toString());
		if (m_layout == null)
			layout();

		//	Paper Attributes: 	media-printable-area, orientation-requested, media
		PrintRequestAttributeSet prats = m_layout.getPaper().getPrintRequestAttributeSet();
		//	add:				copies, job-name, priority
		if (m_info.isDocumentCopy() || m_info.getCopies() < 1)
			prats.add (new Copies(1));
		else
			prats.add (new Copies(m_info.getCopies()));
		Locale locale = Language.getLoginLanguage().getLocale();
		prats.add(new JobName(m_printFormat.getName(), locale));
		prats.add(PrintUtil.getJobPriority(m_layout.getNumberOfPages(), m_info.getCopies(), true));

		try
		{
			//	PrinterJob
			PrinterJob job = getPrinterJob(m_info.getPrinterName());
		//	job.getPrintService().addPrintServiceAttributeListener(this);
			job.setPageable(m_layout.getPageable(false));	//	no copy
		//	Dialog
			try
			{
				if (m_info.isWithDialog() && !job.printDialog(prats))
					return;
			}
			catch (Exception e)
			{
				log.warn("Operating System Print Issue, check & try again", e);
				return;
			}

		//	submit
			boolean printCopy = m_info.isDocumentCopy() && m_info.getCopies() > 1;
			Services.get(IArchiveBL.class).archive(m_layout, m_info, false, ITrx.TRXNAME_None);
			//ArchiveEngine.get().archive(m_layout, m_info);
			PrintUtil.print(job, prats, false, printCopy);

			//	Document: Print Copies
			if (printCopy)
			{
				log.info("Copy " + (m_info.getCopies()-1));
				prats.add(new Copies(m_info.getCopies()-1));
				job = getPrinterJob(m_info.getPrinterName());
			//	job.getPrintService().addPrintServiceAttributeListener(this);
				job.setPageable (m_layout.getPageable(true));		//	Copy
				PrintUtil.print(job, prats, false, false);
			}
		}
		catch (Exception e)
		{
			log.error("", e);
		}
	}	//	print

	/**
	 * 	Print Service Attribute Listener.
	 * 	@param psae event
	 */
	@Override
	public void attributeUpdate(PrintServiceAttributeEvent psae)
	{
		/**
PrintEvent on Win32 Printer : \\MAIN\HP LaserJet 5L
PrintServiceAttributeSet - length=2
queued-job-count = 0  (class javax.print.attribute.standard.QueuedJobCount)
printer-is-accepting-jobs = accepting-jobs  (class javax.print.attribute.standard.PrinterIsAcceptingJobs)
PrintEvent on Win32 Printer : \\MAIN\HP LaserJet 5L
PrintServiceAttributeSet - length=1
queued-job-count = 1  (class javax.print.attribute.standard.QueuedJobCount)
PrintEvent on Win32 Printer : \\MAIN\HP LaserJet 5L
PrintServiceAttributeSet - length=1
queued-job-count = 0  (class javax.print.attribute.standard.QueuedJobCount)
		**/
		log.debug("attributeUpdate - " + psae);
	//	PrintUtil.dump (psae.getAttributes());
	}	//	attributeUpdate


	/**
	 * 	Get PrinterJob based on PrinterName
	 * 	@param printerName optional Printer Name
	 * 	@return PrinterJob
	 */
	private PrinterJob getPrinterJob (String printerName)
	{
		if (printerName != null && printerName.length() > 0)
			return CPrinter.getPrinterJob(printerName);
		return CPrinter.getPrinterJob(m_printerName);
	}	//	getPrinterJob

	/**
	 * 	Show Dialog and Set Paper
	 *  Optionally re-calculate layout
	 */
	public void pageSetupDialog ()
	{
		if (m_layout == null)
			layout();
		m_layout.pageSetupDialog(getPrinterJob(m_printerName));
		if (m_view != null)
			m_view.revalidate();
	}	//	pageSetupDialog

	/**
	 * 	Set Printer (name)
	 * 	@param printerName valid printer name
	 */
	public void setPrinterName(String printerName)
	{
		if (printerName == null)
			m_printerName = Services.get(IPrinterRoutingBL.class).getDefaultPrinterName(); // metas: us316
			//m_printerName = Ini.getProperty(Ini.P_PRINTER); // metas: us316: commented
		else
			m_printerName = printerName;
	}	//	setPrinterName

	/**
	 * 	Get Printer (name)
	 * 	@return printer name
	 */
	public String getPrinterName()
	{
		return m_printerName;
	}	//	getPrinterName

	/**************************************************************************
	 * 	Create HTML File
	 * 	@param file file
	 *  @param onlyTable if false create complete HTML document
	 *  @param language optional language - if null the default language is used to format nubers/dates
	 * 	@return true if success
	 */
	public boolean createHTML (File file, boolean onlyTable, Language language)
	{
		return createHTML(file, onlyTable, language, null);
	}

	/**************************************************************************
	 * 	Create HTML File
	 * 	@param file file
	 *  @param onlyTable if false create complete HTML document
	 *  @param language optional language - if null the default language is used to format nubers/dates
	 *  @param extension optional extension for html output
	 * 	@return true if success
	 */
	public boolean createHTML (File file, boolean onlyTable, Language language, IHTMLExtension extension)
	{
		try
		{
			Language lang = language;
			if (lang == null)
				lang = Language.getLoginLanguage();
			Writer fw = new OutputStreamWriter(new FileOutputStream(file, false), Ini.getCharset()); // teo_sarca: save using adempiere charset [ 1658127 ]
			return createHTML (new BufferedWriter(fw), onlyTable, lang, extension);
		}
		catch (FileNotFoundException fnfe)
		{
			log.error("(f) - " + fnfe.toString());
		}
		catch (Exception e)
		{
			log.error("(f)", e);
		}
		return false;
	}	//	createHTML

	/**
	 * 	Write HTML to writer
	 * 	@param writer writer
	 *  @param onlyTable if false create complete HTML document
	 *  @param language optional language - if null nubers/dates are not formatted
	 * 	@return true if success
	 */
	public boolean createHTML (Writer writer, boolean onlyTable, Language language)
	{
		return createHTML(writer, onlyTable, language, null);
	}

	/**
	 * 	Write HTML to writer
	 * 	@param writer writer
	 *  @param onlyTable if false create complete HTML document
	 *  @param language optional language - if null numbers/dates are not formatted
	 *  @param extension optional extension for html output
	 * 	@return true if success
	 */
	public boolean createHTML (Writer writer, boolean onlyTable, Language language, IHTMLExtension extension)
	{
		final PrintData m_printData = getPrintData();

		try
		{
			String cssPrefix = extension != null ? extension.getClassPrefix() : null;
			if(Check.isEmpty(cssPrefix, true))
				cssPrefix = null;

			table table = new table();
			if (cssPrefix != null)
				table.setClass(cssPrefix + "-table");
			//
			//	for all rows (-1 = header row)
			for (int row = -1; row < m_printData.getRowCount(); row++)
			{
				tr tr = new tr();
				table.addElement(tr);
				if (row != -1)
				{
					m_printData.setRowIndex(row);
					if (extension != null)
					{
						extension.extendRowElement(tr, m_printData);
					}
				}
				//	for all columns
				for (int col = 0; col < m_printFormat.getItemCount(); col++)
				{
					MPrintFormatItem item = m_printFormat.getItem(col);
					if (item.isPrinted())
					{
						//	header row
						if (row == -1)
						{
							th th = new th();
							tr.addElement(th);
							th.addElement(Util.maskHTML(item.getPrintName(language)));
						}
						else
						{
							td td = new td();
							tr.addElement(td);
							Object obj = m_printData.getNode(new Integer(item.getAD_Column_ID()));
							if (obj == null)
								td.addElement("&nbsp;");
							else if (obj instanceof PrintDataElement)
							{
								PrintDataElement pde = (PrintDataElement) obj;
								String value = pde.getValueDisplay(language);	//	formatted
								if (pde.getColumnName().endsWith("_ID") && extension != null)
								{
									//link for column
									a href = new a("javascript:void(0)");
									href.setID(pde.getColumnName() + "_" + row + "_a");
									td.addElement(href);
									href.addElement(Util.maskHTML(value));
									if (cssPrefix != null)
										href.setClass(cssPrefix + "-href");

									extension.extendIDColumn(row, td, href, pde);

								}
								else
								{
									td.addElement(Util.maskHTML(value));
								}
								if (cssPrefix != null)
								{
									if (DisplayType.isNumeric(pde.getDisplayType()))
										td.setClass(cssPrefix + "-number");
									else if (DisplayType.isDate(pde.getDisplayType()))
										td.setClass(cssPrefix + "-date");
									else
										td.setClass(cssPrefix + "-text");
								}
							}
							else if (obj instanceof PrintData)
							{
								//	ignore contained Data
							}
							else
								log.error("Element not PrintData(Element) " + obj.getClass());
						}
					}	//	printed
				}	//	for all columns
			}	//	for all rows

			//
			PrintWriter w = new PrintWriter(writer);
			if (onlyTable)
				table.output(w);
			else
			{
				XhtmlDocument doc = new XhtmlDocument();
				doc.appendBody(table);
				if (extension.getStyleURL() != null)
				{
					link l = new link(extension.getStyleURL(), "stylesheet", "text/css");
					doc.appendHead(l);
				}
				if (extension.getScriptURL() != null)
				{
					script jslink = new script();
					jslink.setLanguage("javascript");
					jslink.setSrc(extension.getScriptURL());
					doc.appendHead(jslink);
				}
				doc.output(w);
			}
			w.flush();
			w.close();
		}
		catch (Exception e)
		{
			log.error("(w)", e);
		}
		return false;
	}	//	createHTML


	/**************************************************************************
	 * 	Create CSV File
	 * 	@param file file
	 *  @param delimiter delimiter, e.g. comma, tab
	 *  @param language translation language
	 * 	@return true if success
	 */
	public boolean createCSV (File file, char delimiter, Language language)
	{
		try
		{
			Writer fw = new OutputStreamWriter(new FileOutputStream(file, false), Ini.getCharset()); // teo_sarca: save using adempiere charset [ 1658127 ]
			return createCSV (new BufferedWriter(fw), delimiter, language);
		}
		catch (FileNotFoundException fnfe)
		{
			log.error("(f) - " + fnfe.toString());
		}
		catch (Exception e)
		{
			log.error("(f)", e);
		}
		return false;
	}	//	createCSV

	/**
	 * 	Write CSV to writer
	 * 	@param writer writer
	 *  @param delimiter delimiter, e.g. comma, tab
	 *  @param language translation language
	 * 	@return true if success
	 */
	private boolean createCSV (Writer writer, char delimiter, Language language)
	{
		final PrintData m_printData = getPrintData();

		if (delimiter == 0)
			delimiter = '\t';
		try
		{
			//	for all rows (-1 = header row)
			for (int row = -1; row < m_printData.getRowCount(); row++)
			{
				StringBuffer sb = new StringBuffer();
				if (row != -1)
					m_printData.setRowIndex(row);

				//	for all columns
				boolean first = true;	//	first column to print
				for (int col = 0; col < m_printFormat.getItemCount(); col++)
				{
					MPrintFormatItem item = m_printFormat.getItem(col);
					if (item.isPrinted())
					{
						//	column delimiter (comma or tab)
						if (first)
							first = false;
						else
							sb.append(delimiter);
						//	header row
						if (row == -1)
							createCSVvalue (sb, delimiter,
								m_printFormat.getItem(col).getPrintName(language));
						else
						{
							Object obj = m_printData.getNode(new Integer(item.getAD_Column_ID()));
							String data = "";
							if (obj == null)
								;
							else if (obj instanceof PrintDataElement)
							{
								PrintDataElement pde = (PrintDataElement)obj;
								if (pde.isPKey())
									data = pde.getValueAsString();
								else
									data = pde.getValueDisplay(language);	//	formatted
							}
							else if (obj instanceof PrintData)
							{
							}
							else
								log.error("Element not PrintData(Element) " + obj.getClass());
							createCSVvalue (sb, delimiter, data);
						}
					}	//	printed
				}	//	for all columns
				writer.write(sb.toString());
				writer.write(Env.NL);
			}	//	for all rows
			//
			writer.flush();
			writer.close();
		}
		catch (Exception e)
		{
			log.error("(w)", e);
		}
		return false;
	}	//	createCSV

	/**
	 * 	Add Content to CSV string.
	 *  Encapsulate/mask content in " if required
	 * 	@param sb StringBuffer to add to
	 * 	@param delimiter delimiter
	 * 	@param content column value
	 */
	private void createCSVvalue (StringBuffer sb, char delimiter, String content)
	{
		//	nothing to add
		if (content == null || content.isEmpty())
			return;
		//
		boolean needMask = false;
		StringBuffer buff = new StringBuffer();
		char chars[] = content.toCharArray();
		for (int i = 0; i < chars.length; i++)
		{
			char c = chars[i];
			if (c == '"')
			{
				needMask = true;
				buff.append(c);		//	repeat twice
			}	//	mask if any control character
			else if (!needMask && (c == delimiter || !Character.isLetterOrDigit(c)))
				needMask = true;
			buff.append(c);
		}

		//	Optionally mask value
		if (needMask)
			sb.append('"').append(buff).append('"');
		else
			sb.append(buff);
	}	//	addCSVColumnValue


	/**************************************************************************
	 * 	Create XML File
	 * 	@param file file
	 * 	@return true if success
	 */
	public boolean createXML (File file)
	{
		try
		{
			Writer fw = new OutputStreamWriter(new FileOutputStream(file, false), Ini.getCharset()); // teo_sarca: save using adempiere charset [ 1658127 ]
			return createXML (new BufferedWriter(fw));
		}
		catch (FileNotFoundException fnfe)
		{
			log.error("(f) - " + fnfe.toString());
		}
		catch (Exception e)
		{
			log.error("(f)", e);
		}
		return false;
	}	//	createXML

	/**
	 * 	Write XML to writer
	 * 	@param writer writer
	 * 	@return true if success
	 */
	public boolean createXML (Writer writer)
	{
		try
		{
			final PrintData m_printData = getPrintData();

			m_printData.createXML(new StreamResult(writer));
			writer.flush();
			writer.close();
			return true;
		}
		catch (Exception e)
		{
			log.error("(w)", e);
		}
		return false;
	}	//	createXML


	/**************************************************************************
	 * 	Create PDF file.
	 * 	(created in temporary storage)
	 *	@return PDF file
	 */
	public File getPDF()
	{
		return getPDF (null);
	}	//	getPDF

	/**
	 * 	Create PDF file.
	 * 	@param file file
	 *	@return PDF file
	 */
	public File getPDF (File file)
	{
		try
		{
			if (file == null)
				file = File.createTempFile ("ReportEngine", ".pdf");
		}
		catch (IOException e)
		{
			log.error("", e);
		}
		if (createPDF (file))
			return file;
		return null;
	}	//	getPDF

	/**
	 * 	Create PDF File
	 * 	@param file file
	 * 	@return true if success
	 */
	public boolean createPDF (File file)
	{
		String fileName = null;
		URI uri = null;

		try
		{
			if (file == null)
				file = File.createTempFile ("ReportEngine", ".pdf");
			fileName = file.getAbsolutePath();
			uri = file.toURI();
			if (file.exists())
				file.delete();

		}
		catch (Exception e)
		{
			log.error("file", e);
			return false;
		}

		log.debug(uri.toString());

		try
		{
			// 03744: begin
			if (getPrintFormat().getJasperProcess_ID() > 0)
			{
				Services.get(IJasperReportEngineAdapter.class).createPDF(this, file);
			}
			else
			{
			// 03744: end
			if (m_layout == null)
				layout ();
			Services.get(IArchiveBL.class).archive(m_layout, m_info, false, ITrx.TRXNAME_None);
			//ArchiveEngine.get().archive(m_layout, m_info);
			Document.getPDFAsFile(fileName, m_layout.getPageable(false));
			} // 03744
		}
		catch (Exception e)
		{
			log.error("PDF", e);
			return false;
		}

		File file2 = new File(fileName);
		log.info(file2.getAbsolutePath() + " - " + file2.length());
		return file2.exists();
	}	//	createPDF

	/**
	 * 	Create PDF as Data array
	 *	@return pdf data
	 */
	public byte[] createPDFData ()
	{
		try
		{
			// 03744: begin
			if (getPrintFormat().getJasperProcess_ID() > 0)
			{
				return Services.get(IJasperReportEngineAdapter.class).createPDFData(this);
			}
			else
			{
			// 03744: end
			if (m_layout == null)
				layout ();
			return Document.getPDFAsArray(m_layout.getPageable(false));
			} // 03744
		}
		catch (Exception e)
		{
			// metas: throw exception instead of logging the error
			throw new AdempiereException(e);
			// log.error("PDF", e);
		}
		// return null;
	}	//	createPDFData

	/**************************************************************************
	 * 	Create PostScript File
	 * 	@param file file
	 * 	@return true if success
	 */
	public boolean createPS (File file)
	{
		try
		{
			return createPS (new FileOutputStream(file));
		}
		catch (FileNotFoundException fnfe)
		{
			log.error("(f) - " + fnfe.toString());
		}
		catch (Exception e)
		{
			log.error("(f)", e);
		}
		return false;
	}	//	createPS

	/**
	 * 	Write PostScript to writer
	 * 	@param os output stream
	 * 	@return true if success
	 */
	public boolean createPS (OutputStream os)
	{
		try
		{
			String outputMimeType = DocFlavor.BYTE_ARRAY.POSTSCRIPT.getMimeType();
			DocFlavor docFlavor = DocFlavor.SERVICE_FORMATTED.PAGEABLE;
			StreamPrintServiceFactory[] spsfactories =
				StreamPrintServiceFactory.lookupStreamPrintServiceFactories(docFlavor, outputMimeType);
			if (spsfactories.length == 0)
			{
				log.error("(fos) - No StreamPrintService");
				return false;
			}
			//	just use first one - sun.print.PSStreamPrinterFactory
			//	System.out.println("- " + spsfactories[0]);
			StreamPrintService sps = spsfactories[0].getPrintService(os);
			//	get format
			if (m_layout == null)
				layout();
			//	print it
			sps.createPrintJob().print(m_layout.getPageable(false),
				new HashPrintRequestAttributeSet());
			//
			os.flush();
			//following 2 line for backward compatibility
			if (os instanceof FileOutputStream)
				((FileOutputStream)os).close();
		}
		catch (Exception e)
		{
			log.error("(fos)", e);
		}
		return false;
	}	//	createPS

	/**
	 * Create Excel file
	 * @param outFile output file
	 * @param language
	 * @throws Exception if error
	 */
	public void createXLS(File outFile, Language language)
	throws Exception
	{
		PrintDataExcelExporter exp = new PrintDataExcelExporter(getPrintData(), getPrintFormat());
		exp.export(outFile, language);
	}

	/**************************************************************************
	 * 	Get Report Engine for process info
	 *	@param ctx context
	 *	@param pi process info with AD_PInstance_ID
	 *	@return report engine or null
	 */
	static public ReportEngine get (Properties ctx, ProcessInfo pi)
	{
		int AD_Client_ID = pi.getAD_Client_ID();
		//
		int AD_Table_ID = 0;
		int AD_ReportView_ID = 0;
		String TableName = null;
		String whereClause = "";
		int AD_PrintFormat_ID = 0;
		boolean IsForm = false;
		int Client_ID = -1;

		//	Get AD_Table_ID and TableName
		String sql = "SELECT rv.AD_ReportView_ID,rv.WhereClause,"
			+ " t.AD_Table_ID,t.TableName, pf.AD_PrintFormat_ID, pf.IsForm, pf.AD_Client_ID "
			+ "FROM AD_PInstance pi"
			+ " INNER JOIN AD_Process p ON (pi.AD_Process_ID=p.AD_Process_ID)"
			+ " INNER JOIN AD_ReportView rv ON (p.AD_ReportView_ID=rv.AD_ReportView_ID)"
			+ " INNER JOIN AD_Table t ON (rv.AD_Table_ID=t.AD_Table_ID)"
			+ " LEFT OUTER JOIN AD_PrintFormat pf ON (p.AD_ReportView_ID=pf.AD_ReportView_ID AND pf.AD_Client_ID IN (0,?)) "
			+ "WHERE pi.AD_PInstance_ID=? "		//	#2
			+ "ORDER BY pf.AD_Client_ID DESC, pf.IsDefault DESC";	//	own first
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, AD_Client_ID);
			pstmt.setInt(2, pi.getAD_PInstance_ID());
			rs = pstmt.executeQuery();
			//	Just get first
			if (rs.next())
			{
				AD_ReportView_ID = rs.getInt(1);		//	required
				whereClause = rs.getString(2);
				if (rs.wasNull())
					whereClause = "";
				//
				AD_Table_ID = rs.getInt(3);
				TableName = rs.getString(4);			//	required for query
				AD_PrintFormat_ID = rs.getInt(5);		//	required
				IsForm = "Y".equals(rs.getString(6));	//	required
				Client_ID = rs.getInt(7);
			}
		}
		catch (SQLException e1)
		{
			log.error("(1) - " + sql, e1);
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		//	Nothing found
		if (AD_ReportView_ID <= 0)
		{
			//	Check Print format in Report Directly
			sql = "SELECT t.AD_Table_ID,t.TableName, pf.AD_PrintFormat_ID, pf.IsForm "
				+ "FROM AD_PInstance pi"
				+ " INNER JOIN AD_Process p ON (pi.AD_Process_ID=p.AD_Process_ID)"
				+ " INNER JOIN AD_PrintFormat pf ON (p.AD_PrintFormat_ID=pf.AD_PrintFormat_ID)"
				+ " INNER JOIN AD_Table t ON (pf.AD_Table_ID=t.AD_Table_ID) "
				+ "WHERE pi.AD_PInstance_ID=?";
			try
			{
				pstmt = DB.prepareStatement(sql, null);
				pstmt.setInt(1, pi.getAD_PInstance_ID());
				rs = pstmt.executeQuery();
				if (rs.next())
				{
					whereClause = "";
					AD_Table_ID = rs.getInt(1);
					TableName = rs.getString(2);			//	required for query
					AD_PrintFormat_ID = rs.getInt(3);		//	required
					IsForm = "Y".equals(rs.getString(4));	//	required
					Client_ID = AD_Client_ID;
				}
			}
			catch (SQLException e1)
			{
				log.error("(2) - " + sql, e1);
			}
			finally {
				DB.close(rs, pstmt);
				rs = null; pstmt = null;
			}
			if (AD_PrintFormat_ID <= 0)
			{
				log.error("Report Info NOT found AD_PInstance_ID=" + pi.getAD_PInstance_ID() + ",AD_Client_ID=" + AD_Client_ID);
				return null;
			}
		}

		//  Create Query from Parameters
		MQuery query = null;
		if (IsForm && pi.getRecord_ID() != 0		//	Form = one record
				&& !TableName.startsWith("T_") )	//	Not temporary table - teo_sarca, BF [ 2828886 ]
		{
			query = MQuery.getEqualQuery(TableName + "_ID", pi.getRecord_ID());
		}
		else
		{
			query = MQuery.get (ctx, pi.getAD_PInstance_ID(), TableName);
		}

		// metas 03915
		if (query.getRestrictionCount() == 0 && pi.getRecord_ID() > 0)
		{
			final MTable table = MTable.get(ctx, pi.getTable_ID());
			final String[] keyColumns = table.getKeyColumns();
			if (keyColumns.length == 1)
			{
				String infoName = Msg.translate(ctx, keyColumns[0]);
				String infoDisplay = String.valueOf(pi.getRecord_ID());
				query.addRestriction(keyColumns[0], Operator.EQUAL, pi.getRecord_ID(), infoName, infoDisplay);
			}
		} // metas end

		//  Add to static where clause from ReportView
		if (whereClause.length() != 0)
			query.addRestriction(whereClause);

		//	Get Print Format
		MPrintFormat format = null;
		Object so = pi.getSerializableObject();
		if (so instanceof MPrintFormat)
			format = (MPrintFormat)so;
		if (format == null && AD_PrintFormat_ID != 0)
		{
			//	We have a PrintFormat with the correct Client
			if (Client_ID == AD_Client_ID)
				format = MPrintFormat.get (ctx, AD_PrintFormat_ID, false);
			else
				format = MPrintFormat.copyToClient (ctx, AD_PrintFormat_ID, AD_Client_ID);
		}
		if (format != null && format.getItemCount() == 0)
		{
			log.info("No Items - recreating:  " + format);
			format.delete(true);
			format = null;
		}
		//	Create Format
		if (format == null && AD_ReportView_ID != 0)
			format = MPrintFormat.createFromReportView(ctx, AD_ReportView_ID, pi.getTitle());
		if (format == null)
			return null;
		//
		PrintInfo info = new PrintInfo (pi);
		info.setAD_Table_ID(AD_Table_ID);

		return new ReportEngine(ctx, format, query, info);
	}	//	get

	/*************************************************************************/

	/** Order = 0				*/
	public static final int		ORDER = 0;
	/** Shipment = 1				*/
	public static final int		SHIPMENT = 1;
	/** Invoice = 2				*/
	public static final int		INVOICE = 2;
	/** Project = 3				*/
	public static final int		PROJECT = 3;
	/** Remittance = 5			*/
	public static final int		REMITTANCE = 5;
	/** Check = 6				*/
	public static final int		CHECK = 6;
	/** Dunning = 7				*/
	public static final int		DUNNING = 7;
	/** Manufacturing Order = 8  */
	public static final int		MANUFACTURING_ORDER = 8;
	/** Distribution Order = 9  */
	public static final int		DISTRIBUTION_ORDER = 9;

	private static final String[]	DOC_BASETABLES = new String[] {
		"C_Order", "M_InOut", "C_Invoice", "C_Project",
		"C_PaySelectionCheck", "C_PaySelectionCheck",
		"C_DunningRunEntry","PP_Order", "DD_Order"};
	private static final String[]	DOC_IDS = new String[] {
		"C_Order_ID", "M_InOut_ID", "C_Invoice_ID", "C_Project_ID",
		"C_PaySelectionCheck_ID", "C_PaySelectionCheck_ID",
		"C_DunningRunEntry_ID" , "PP_Order_ID" , "DD_Order_ID" };
	private static final int[]	DOC_TABLE_ID = new int[] {
		I_C_Order.Table_ID, I_M_InOut.Table_ID, I_C_Invoice.Table_ID, I_C_Project.Table_ID,
		I_C_PaySelectionCheck.Table_ID,
		I_C_DunningRunEntry.Table_ID,
		Services.get(IADTableDAO.class).retrieveTableId(I_PP_Order.Table_Name),
		Services.get(IADTableDAO.class).retrieveTableId(I_DD_Order.Table_Name) };

	/**************************************************************************
	 * 	Get Document Print Engine for Document Type.
	 * 	@param ctx context
	 * 	@param type document type
	 * 	@param Record_ID id
	 * 	@return Report Engine or null
	 */
	public static ReportEngine get (Properties ctx, int type, int Record_ID)
	{
		return get(ctx, type, Record_ID, null);
	}

	/**************************************************************************
	 * 	Get Document Print Engine for Document Type.
	 * 	@param ctx context
	 * 	@param type document type
	 * 	@param Record_ID id
	 *  @param trxName
	 * 	@return Report Engine or null
	 */
	public static ReportEngine get (Properties ctx, int type, int Record_ID, String trxName)
	{
		 final int adPrintFormatToUseId = -1; // auto-detect
		 final int pInstanceId = -1;
		return get(ctx, type, Record_ID, pInstanceId, adPrintFormatToUseId, trxName);
	}
	
	public static ReportEngine get (Properties ctx, int type, int Record_ID, int pInstanceId ,String trxName)
	{
		 final int adPrintFormatToUseId = -1; // auto-detect
		return get(ctx, type, Record_ID,  pInstanceId, adPrintFormatToUseId, trxName);
	}
	// metas: added adPrintFormatToUseId parameter
	public static ReportEngine get (final Properties ctx, int type, int Record_ID, final int pInstanceId, final int adPrintFormatToUseId, final String trxName)
	{
		if (Record_ID < 1)
		{
			log.warn("No PrintFormat for Record_ID=" + Record_ID + ", Type=" + type);
			return null;
		}
		//	Order - Print Shipment or Invoice
		if (type == ORDER)
		{
			int[] what = getDocumentWhat (Record_ID);
			if (what != null)
			{
				type = what[0];
				Record_ID = what[1];
			}
		}	//	Order

		int AD_PrintFormat_ID = 0;
		int C_BPartner_ID = 0;
		String DocumentNo = null;
		int copies = 1;

		//	Language
		MClient client = MClient.get(ctx);
		Language language = client.getLanguage();
		//	Get Document Info
		String sql = null;
		if (type == CHECK)
			sql = "SELECT bad.Check_PrintFormat_ID,"										//	1
				+ "	c.IsMultiLingualDocument,bp.AD_Language,bp.C_BPartner_ID,d.DocumentNo "	//	2..5
				+ "FROM C_PaySelectionCheck d"
				+ " INNER JOIN C_PaySelection ps ON (d.C_PaySelection_ID=ps.C_PaySelection_ID)"
				+ " INNER JOIN C_BP_BankAccountDoc bad ON (ps.C_BP_BankAccount_ID=bad.C_BP_BankAccount_ID AND d.PaymentRule=bad.PaymentRule)"
				+ " INNER JOIN AD_Client c ON (d.AD_Client_ID=c.AD_Client_ID)"
				+ " INNER JOIN C_BPartner bp ON (d.C_BPartner_ID=bp.C_BPartner_ID) "
				+ "WHERE d.C_PaySelectionCheck_ID=?";		//	info from BankAccount
		else if (type == DUNNING)
			sql = "SELECT dl.Dunning_PrintFormat_ID,"
				+ " c.IsMultiLingualDocument,bp.AD_Language,bp.C_BPartner_ID,dr.DunningDate "
				+ "FROM C_DunningRunEntry d"
				+ " INNER JOIN AD_Client c ON (d.AD_Client_ID=c.AD_Client_ID)"
				+ " INNER JOIN C_BPartner bp ON (d.C_BPartner_ID=bp.C_BPartner_ID)"
				+ " INNER JOIN C_DunningRun dr ON (d.C_DunningRun_ID=dr.C_DunningRun_ID)"
				+ " INNER JOIN C_DunningLevel dl ON (dl.C_DunningLevel_ID=d.C_DunningLevel_ID) "
				+ "WHERE d.C_DunningRunEntry_ID=?";			//	info from Dunning
		else if (type == REMITTANCE)
			sql = "SELECT pf.Remittance_PrintFormat_ID,"
				+ " c.IsMultiLingualDocument,bp.AD_Language,bp.C_BPartner_ID,d.DocumentNo "
				+ "FROM C_PaySelectionCheck d"
				+ " INNER JOIN AD_Client c ON (d.AD_Client_ID=c.AD_Client_ID)"
				+ " INNER JOIN AD_PrintForm pf ON (c.AD_Client_ID=pf.AD_Client_ID)"
				+ " INNER JOIN C_BPartner bp ON (d.C_BPartner_ID=bp.C_BPartner_ID) "
				+ "WHERE d.C_PaySelectionCheck_ID=?"		//	info from PrintForm
				+ " AND pf.AD_Org_ID IN (0,d.AD_Org_ID) ORDER BY pf.AD_Org_ID DESC";
		else if (type == PROJECT)
			sql = "SELECT pf.Project_PrintFormat_ID,"
				+ " c.IsMultiLingualDocument,bp.AD_Language,bp.C_BPartner_ID,d.Value "
				+ "FROM C_Project d"
				+ " INNER JOIN AD_Client c ON (d.AD_Client_ID=c.AD_Client_ID)"
				+ " INNER JOIN AD_PrintForm pf ON (c.AD_Client_ID=pf.AD_Client_ID)"
				+ " LEFT OUTER JOIN C_BPartner bp ON (d.C_BPartner_ID=bp.C_BPartner_ID) "
				+ "WHERE d.C_Project_ID=?"					//	info from PrintForm
				+ " AND pf.AD_Org_ID IN (0,d.AD_Org_ID) ORDER BY pf.AD_Org_ID DESC";
		else if (type == MANUFACTURING_ORDER)
			sql = "SELECT COALESCE(bppf_pporder.AD_PrintFormat_ID, pf.Manuf_Order_PrintFormat_ID),"
				+ " c.IsMultiLingualDocument,bp.AD_Language, 0 , d.DocumentNo "
				+ "FROM PP_Order d"
				+ " INNER JOIN AD_Client c ON (d.AD_Client_ID=c.AD_Client_ID)"
				+ " LEFT OUTER JOIN AD_User u ON (u.AD_User_ID=d.Planner_ID)"
				+ " LEFT OUTER JOIN C_BPartner bp ON (u.C_BPartner_ID=bp.C_BPartner_ID) "
				+ " LEFT OUTER JOIN C_DocType dt ON dt.DocBaseType IN ('MOP')"
				+ " LEFT OUTER JOIN C_BP_PrintFormat bppf_pporder "
							+ " ON (bp.C_BPartner_ID=bppf_pporder.C_BPartner_ID) "
							+ " AND (bppf_pporder.IsActive='Y')"
							+ " AND (dt.C_DocType_ID=bppf_pporder.C_DocType_ID) " // Manufacturing Order
				+ " INNER JOIN AD_PrintForm pf ON (c.AD_Client_ID=pf.AD_Client_ID)"
				+ "WHERE d.PP_Order_ID=?"					//	info from PrintForm
				+ " AND pf.AD_Org_ID IN (0,d.AD_Org_ID) ORDER BY pf.AD_Org_ID DESC";
		else if (type == DISTRIBUTION_ORDER)
			sql = "SELECT COALESCE(bppf_ddorder.AD_PrintFormat_ID, pf.Distrib_Order_PrintFormat_ID),"
				+ " c.IsMultiLingualDocument,bp.AD_Language, bp.C_BPartner_ID , d.DocumentNo "
				+ "FROM DD_Order d"
				+ " INNER JOIN AD_Client c ON (d.AD_Client_ID=c.AD_Client_ID)"
				+ " INNER JOIN AD_PrintForm pf ON (c.AD_Client_ID=pf.AD_Client_ID)"
				+ " LEFT OUTER JOIN C_BPartner bp ON (d.C_BPartner_ID=bp.C_BPartner_ID) "
				+ " LEFT OUTER JOIN C_DocType dt ON dt.DocBaseType IN ('DOO')"
				+ " LEFT OUTER JOIN C_BP_PrintFormat bppf_ddorder "
							+ " ON (bp.C_BPartner_ID=bppf_ddorder.C_BPartner_ID) "
							+ " AND (bppf_ddorder.IsActive='Y')"
							+ " AND (dt.C_DocType_ID=bppf_ddorder.C_DocType_ID) " // Distribution Order
				+ "WHERE d.DD_Order_ID=?"					//	info from PrintForm
				+ " AND pf.AD_Org_ID IN (0,d.AD_Org_ID) ORDER BY pf.AD_Org_ID DESC";
		// Fix [2574162] Priority to choose invoice print format not working
		else if (type == ORDER || type == INVOICE)
			sql = "SELECT COALESCE (bppf_order.AD_PrintFormat_ID,pf.Order_PrintFormat_ID),"					//	1
				+ " COALESCE (bppf_ship.AD_PrintFormat_ID,pf.Shipment_PrintFormat_ID),"						//	2
				//	Prio: 1. C_BP_PrintFormat 2. BPartner 3. DocType, 4. PrintFormat (Org)	//	see InvoicePrint
				+ " COALESCE (bppf_inv.AD_PrintFormat_ID,bp.Invoice_PrintFormat_ID,dt.AD_PrintFormat_ID,pf.Invoice_PrintFormat_ID),"	//	3
				+ " pf.Project_PrintFormat_ID,"																//	4
				+ " pf.Remittance_PrintFormat_ID,"															//	5
				+ " c.IsMultiLingualDocument, bp.AD_Language,"												//	6..7
				+ " COALESCE(dt.DocumentCopies,0)+COALESCE(bp.DocumentCopies,1), " 							// 	8
				+ " COALESCE(bppf.AD_PrintFormat_ID, dt.AD_PrintFormat_ID),"								//	9
				+ " bp.C_BPartner_ID,d.DocumentNo "															//	10..11
				+ "FROM " + DOC_BASETABLES[type] + " d"
				+ " INNER JOIN AD_Client c ON (d.AD_Client_ID=c.AD_Client_ID)"
				+ " INNER JOIN AD_PrintForm pf ON (c.AD_Client_ID=pf.AD_Client_ID)"
				+ " INNER JOIN C_BPartner bp ON (d.C_BPartner_ID=bp.C_BPartner_ID)"
				+ " LEFT OUTER JOIN C_DocType dt ON ((d.C_DocType_ID>0 AND d.C_DocType_ID=dt.C_DocType_ID) OR (d.C_DocType_ID=0 AND d.C_DocTypeTarget_ID=dt.C_DocType_ID)) "
				+ " LEFT OUTER JOIN C_BP_PrintFormat bppf "
							+ " ON (bp.C_BPartner_ID=bppf.C_BPartner_ID) "
							+ " AND (bppf.IsActive='Y')"
							+ " AND (dt.C_DocType_ID=bppf.C_DocType_ID) "
				+ " LEFT OUTER JOIN C_BP_PrintFormat bppf_order "
							+ " ON (bp.C_BPartner_ID=bppf_order.C_BPartner_ID) "
							+ " AND (bppf_order.IsActive='Y')"
							+ " AND (dt.C_DocType_ID=bppf_order.C_DocType_ID) "
							+ " AND (d.IsSOTrx=dt.IsSOTrx) "
							+ " AND (dt.DocBaseType IN ('SOO', 'POO')) "
				+ " LEFT OUTER JOIN C_BP_PrintFormat bppf_ship "
							+ " ON (bp.C_BPartner_ID=bppf_ship.C_BPartner_ID) "
							+ " AND (bppf_ship.IsActive='Y')"
							+ " AND (dt.C_DocType_ID=bppf_ship.C_DocType_ID) "
							+ " AND (d.IsSOTrx=dt.IsSOTrx) "
							+ " AND (dt.DocBaseType IN ('MMR', 'MMS')) "
				+ " LEFT OUTER JOIN C_BP_PrintFormat bppf_inv "
							+ " ON (bp.C_BPartner_ID=bppf_inv.C_BPartner_ID) "
							+ " AND (bppf_inv.IsActive='Y')"
							+ " AND (dt.C_DocType_ID=bppf_inv.C_DocType_ID) "
							+ " AND (d.IsSOTrx=dt.IsSOTrx) "
							+ " AND (dt.DocBaseType IN ('AEI', 'APC', 'API', 'ARC', 'ARI', 'AVI', 'MXI')) " // Invoice types
				+ "WHERE d." + DOC_IDS[type] + "=?"			//	info from PrintForm
				+ " AND pf.AD_Org_ID IN (0,d.AD_Org_ID) "
				+ "ORDER BY pf.AD_Org_ID DESC";
		else	//	Get PrintFormat from Org or 0 of document client
		{
			sql = "SELECT COALESCE (bppf_order.AD_PrintFormat_ID,pf.Order_PrintFormat_ID),"					//	1
				+ " COALESCE (bppf_ship.AD_PrintFormat_ID,pf.Shipment_PrintFormat_ID),"						//	2
				//	Prio: 1. C_BP_PrintFormat 2. BPartner 3. DocType, 4. PrintFormat (Org)	//	see InvoicePrint
				+ " COALESCE (bppf_inv.AD_PrintFormat_ID,bp.Invoice_PrintFormat_ID,dt.AD_PrintFormat_ID,pf.Invoice_PrintFormat_ID),"	//	3
				+ " pf.Project_PrintFormat_ID,"																//	4
				+ " pf.Remittance_PrintFormat_ID,"															//	5
				+ " c.IsMultiLingualDocument, bp.AD_Language,"												//	6..7
				+ " COALESCE(dt.DocumentCopies,0)+COALESCE(bp.DocumentCopies,1), " 							// 	8
				+ " COALESCE(bppf.AD_PrintFormat_ID, dt.AD_PrintFormat_ID),"								//	9
				+ " bp.C_BPartner_ID,d.DocumentNo, "														//  10..11
				+ " COALESCE(bppf_pporder.AD_PrintFormat_ID, pf.Manuf_Order_PrintFormat_ID), "				//	12
				+ " COALESCE(bppf_ddorder.AD_PrintFormat_ID, pf.Distrib_Order_PrintFormat_ID) "				//	13
				+ "FROM " + DOC_BASETABLES[type] + " d"
				+ " INNER JOIN AD_Client c ON (d.AD_Client_ID=c.AD_Client_ID)"
				+ " INNER JOIN AD_PrintForm pf ON (c.AD_Client_ID=pf.AD_Client_ID)"
				+ " INNER JOIN C_BPartner bp ON (d.C_BPartner_ID=bp.C_BPartner_ID)"
				+ " LEFT OUTER JOIN C_DocType dt ON (d.C_DocType_ID=dt.C_DocType_ID) "
				+ " LEFT OUTER JOIN C_BP_PrintFormat bppf "
							+ " ON (bp.C_BPartner_ID=bppf.C_BPartner_ID) "
							+ " AND (bppf.IsActive='Y')"
							+ " AND (dt.C_DocType_ID=bppf.C_DocType_ID) "
				+ " LEFT OUTER JOIN C_BP_PrintFormat bppf_order "
							+ " ON (bp.C_BPartner_ID=bppf_order.C_BPartner_ID) "
							+ " AND (bppf_order.IsActive='Y')"
							+ " AND (dt.C_DocType_ID=bppf_order.C_DocType_ID) "
							+ " AND (d.IsSOTrx=dt.IsSOTrx) "
							+ " AND (dt.DocBaseType IN ('SOO', 'POO')) " // Auftrag, Bestellung
				+ " LEFT OUTER JOIN C_BP_PrintFormat bppf_ship "
							+ " ON (bp.C_BPartner_ID=bppf_ship.C_BPartner_ID) "
							+ " AND (bppf_ship.IsActive='Y')"
							+ " AND (dt.C_DocType_ID=bppf_ship.C_DocType_ID) "
							+ " AND (d.IsSOTrx=dt.IsSOTrx) "
							+ " AND (dt.DocBaseType IN ('MMS', 'MMR')) " // Shipment, Receipt
				+ " LEFT OUTER JOIN C_BP_PrintFormat bppf_inv "
							+ " ON (bp.C_BPartner_ID=bppf_inv.C_BPartner_ID) "
							+ " AND (bppf_inv.IsActive='Y')"
							+ " AND (dt.C_DocType_ID=bppf_inv.C_DocType_ID) "
							+ " AND (d.IsSOTrx=dt.IsSOTrx) "
							+ " AND (dt.DocBaseType IN ('AEI', 'APC', 'API', 'ARC', 'ARI', 'AVI', 'MXI')) " // Invoice types
				+ " LEFT OUTER JOIN C_BP_PrintFormat bppf_pporder "
							+ " ON (bp.C_BPartner_ID=bppf_pporder.C_BPartner_ID) "
							+ " AND (bppf_pporder.IsActive='Y')"
							+ " AND (dt.C_DocType_ID=bppf_pporder.C_DocType_ID) "
							+ " AND (dt.DocBaseType IN ('MOP')) " // Manufacturing Order
				+ " LEFT OUTER JOIN C_BP_PrintFormat bppf_ddorder "
							+ " ON (bp.C_BPartner_ID=bppf_ddorder.C_BPartner_ID) "
							+ " AND (bppf_ddorder.IsActive='Y')"
							+ " AND (dt.C_DocType_ID=bppf_ddorder.C_DocType_ID) "
							+ " AND (dt.DocBaseType IN ('DOO')) " // Distribution Order
				+ "WHERE d." + DOC_IDS[type] + "=?"			//	info from PrintForm
				+ " AND pf.AD_Org_ID IN (0,d.AD_Org_ID) "
				+ "ORDER BY pf.AD_Org_ID DESC";
		}
		//
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			pstmt.setInt(1, Record_ID);
			rs = pstmt.executeQuery();
			if (rs.next())	//	first record only
			{
				if (type == CHECK || type == DUNNING || type == REMITTANCE
					|| type == PROJECT || type == MANUFACTURING_ORDER || type == DISTRIBUTION_ORDER)
				{
					AD_PrintFormat_ID = rs.getInt(1);
					copies = 1;
					//	Set Language when enabled
					String AD_Language = rs.getString(3);
					if (AD_Language != null)// && "Y".equals(rs.getString(2)))	//	IsMultiLingualDocument
						language = Language.getLanguage(AD_Language);
					C_BPartner_ID = rs.getInt(4);
					if (type == DUNNING)
					{
						Timestamp ts = rs.getTimestamp(5);
						DocumentNo = ts.toString();
					}
					else
						DocumentNo = rs.getString(5);
				}
				else
				{
					//	Set PrintFormat
					AD_PrintFormat_ID = rs.getInt(type+1);
					if (type != INVOICE && rs.getInt(9) != 0)		//	C_DocType.AD_PrintFormat_ID
						AD_PrintFormat_ID = rs.getInt(9);
					copies = rs.getInt(8);
					//	Set Language when enabled
					String AD_Language = rs.getString(7);
					if (AD_Language != null) // && "Y".equals(rs.getString(6)))	//	IsMultiLingualDocument
						language = Language.getLanguage(AD_Language);
					C_BPartner_ID = rs.getInt(10);
					DocumentNo = rs.getString(11);
				}
			}
		}
		catch (Exception e)
		{
			log.error("Record_ID=" + Record_ID + ", SQL=" + sql, e);
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

		// metas: begin: Use provided print format
		if (adPrintFormatToUseId > 0)
		{
			log.debug("Using AD_PrintFormat_ID={}(provided) instead of {}(detected)", new Object[] { adPrintFormatToUseId, AD_PrintFormat_ID });
			AD_PrintFormat_ID = adPrintFormatToUseId;
		}
		// metas: end

		if (AD_PrintFormat_ID <= 0)
		{
			log.error("No PrintFormat found for Type=" + type + ", Record_ID=" + Record_ID);
			return null;
		}

		//	Get Format & Data
		MPrintFormat format = MPrintFormat.get (ctx, AD_PrintFormat_ID, false);
		format.setLanguage(language);		//	BP Language if Multi-Lingual
	//	if (!Env.isBaseLanguage(language, DOC_TABLES[type]))
			format.setTranslationLanguage(language);
		//	query
		MQuery query = new MQuery(format.getAD_Table_ID());
		query.addRestriction(DOC_IDS[type], Operator.EQUAL, Record_ID);
	//	log.info( "ReportCtrl.startDocumentPrint - " + format, query + " - " + language.getAD_Language());
		//
		
		
		if (DocumentNo == null || DocumentNo.isEmpty())
			DocumentNo = "DocPrint";
		PrintInfo info = new PrintInfo(
			DocumentNo,
			DOC_TABLE_ID[type],
			Record_ID,
			C_BPartner_ID);
		info.setCopies(copies);
		info.setDocumentCopy(false);		//	true prints "Copy" on second
		info.setPrinterName(format.getPrinterName());
		
		info.setAD_PInstance_ID(pInstanceId);

		//	Engine
		ReportEngine re = new ReportEngine(ctx, format, query, info, trxName);
		return re;
	}	//	get

	/**
	 *	Determine what Order document to print.
	 *  @param C_Order_ID id
	 *	@return int Array with [printWhat, ID]
	 */
	private static int[] getDocumentWhat (int C_Order_ID)
	{
		int[] what = new int[2];
		what[0] = ORDER;
		what[1] = C_Order_ID;
		//
		String sql = "SELECT dt.DocSubType "
			+ "FROM C_DocType dt, C_Order o "
			+ "WHERE o.C_DocType_ID=dt.C_DocType_ID"
			+ " AND o.C_Order_ID=?";
		String DocSubType = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, C_Order_ID);
			rs = pstmt.executeQuery();
			if (rs.next())
				DocSubType = rs.getString(1);

			// @Trifon - Order is not completed(C_DoctType_ID=0) then try with C_DocTypeTarget_ID
			// [ 2819637 ] Wrong print format on non completed order - https://sourceforge.net/tracker/?func=detail&aid=2819637&group_id=176962&atid=879332
			if (DocSubType == null || "".equals(DocSubType)) {
				sql = "SELECT dt.DocSubType "
					+ "FROM C_DocType dt, C_Order o "
					+ "WHERE o.C_DocTypeTarget_ID=dt.C_DocType_ID"
					+ " AND o.C_Order_ID=?";
				pstmt = DB.prepareStatement(sql, null);
				pstmt.setInt(1, C_Order_ID);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					DocSubType = rs.getString(1);
				}
			}
		}
		catch (SQLException e1)
		{
			log.error("(1) - " + sql, e1);
			return null;		//	error
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		if (DocSubType == null)
			DocSubType = "";
		//	WalkIn Receipt, WalkIn Invoice,
		if (DocSubType.equals("WR") || DocSubType.equals("WI"))
			what[0] = INVOICE;
		//	WalkIn Pickup,
		else if (DocSubType.equals("WP"))
			what[0] = SHIPMENT;
		//	Offer Binding, Offer Nonbinding, Standard Order
		else
			return what;

		//	Get Record_ID of Invoice/Receipt
		if (what[0] == INVOICE)
			sql = "SELECT C_Invoice_ID REC FROM C_Invoice WHERE C_Order_ID=?"	//	1
				+ " ORDER BY C_Invoice_ID DESC";
		else
			sql = "SELECT M_InOut_ID REC FROM M_InOut WHERE C_Order_ID=?" 	//	1
				+ " ORDER BY M_InOut_ID DESC";
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, C_Order_ID);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
			//	if (i == 1 && ADialog.ask(0, null, what[0] == INVOICE ? "PrintOnlyRecentInvoice?" : "PrintOnlyRecentShipment?")) break;
				what[1] = rs.getInt(1);
			}
			else	//	No Document Found
				what[0] = ORDER;
		}
		catch (SQLException e2)
		{
			log.error("(2) - " + sql, e2);
			return null;
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		log.debug("Order => " + what[0] + " ID=" + what[1]);
		return what;
	}	//	getDocumentWhat

	/**
	 * 	Print Confirm.
	 *  Update Date Printed
	 * 	@param type document type
	 * 	@param Record_ID record id
	 */
	public static void printConfirm (int type, int Record_ID)
	{
		StringBuffer sql = new StringBuffer();
		if (type == ORDER || type == SHIPMENT || type == INVOICE)
			sql.append("UPDATE ").append(DOC_BASETABLES[type])
				.append(" SET DatePrinted=now(), IsPrinted='Y' WHERE ")
				.append(DOC_IDS[type]).append("=").append(Record_ID);
		//
		if (sql.length() > 0)
		{
			int no = DB.executeUpdate(sql.toString(), null);
			if (no != 1)
				log.error("Updated records=" + no + " - should be just one");
		}
	}	//	printConfirm

	public void setWhereExtended(String whereExtended) {
		m_whereExtended = whereExtended;
	}

	public String getWhereExtended() {
		return m_whereExtended;
	}

	/* Save windowNo of the report to parse the context */
	public void setWindowNo(int windowNo) {
		m_windowNo = windowNo;
	}

	public int getWindowNo() {
		return m_windowNo;
	}

	public void setSummary(boolean summary)
	{
		m_summary = summary;
	}

	/**
	 * Gets type (e.g. {@link #ORDER}, {@link #INVOICE} etc) for given tableId
	 * @param tableId
	 * @return type or -1 if not found
	 */
	// metas
	public static int getTypeByTableId(int tableId)
	{
		for (int type = 0; type < DOC_TABLE_ID.length; type++)
		{
			if (DOC_TABLE_ID[type] == tableId)
			{
				return type;
			}
		}

		return -1;
	}
}	//	ReportEngine
