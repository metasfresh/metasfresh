/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.print;

import de.metas.adempiere.service.IPrinterRoutingBL;
import de.metas.i18n.Language;
import de.metas.i18n.Msg;
import de.metas.impexp.excel.ExcelFormats;
import de.metas.logging.LogManager;
import de.metas.printing.IMassPrintingService;
import de.metas.process.ProcessExecutor;
import de.metas.process.ProcessInfo;
import de.metas.report.StandardDocumentReportType;
import de.metas.util.Check;
import de.metas.util.FileUtil;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.archive.api.ArchiveInfo;
import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.pdf.Document;
import org.adempiere.print.export.PrintDataExcelExporter;
import org.apache.ecs.XhtmlDocument;
import org.apache.ecs.xhtml.a;
import org.apache.ecs.xhtml.link;
import org.apache.ecs.xhtml.script;
import org.apache.ecs.xhtml.table;
import org.apache.ecs.xhtml.td;
import org.apache.ecs.xhtml.th;
import org.apache.ecs.xhtml.tr;
import org.compiere.model.MQuery;
import org.compiere.model.MQuery.Operator;
import org.compiere.model.MTable;
import org.compiere.print.layout.LayoutEngine;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.slf4j.Logger;

import javax.annotation.Nullable;
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
import java.awt.print.PrinterJob;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
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
import java.util.Locale;
import java.util.Properties;

/**
 * Report Engine.
 * For a given PrintFormat,
 * create a Report
 * <p>
 * Change log:
 * <ul>
 * <li>2007-02-12 - teo_sarca - [ 1658127 ] Select charset encoding on import
 * <li>2007-02-10 - teo_sarca - [ 1652660 ] Save XML,HTML,CSV should have utf8 charset
 * <li>2009-02-06 - globalqss - [ 2574162 ] Priority to choose invoice print format not working
 * <li>2009-07-10 - trifonnt - [ 2819637 ] Wrong print format on non completed order
 * </ul>
 *
 * @author Jorg Janke
 * @author Teo Sarca, www.arhipac.ro
 * <li>BF [ 2828300 ] Error when printformat table differs from DOC_TABLES
 * https://sourceforge.net/tracker/?func=detail&aid=2828300&group_id=176962&atid=879332
 * <li>BF [ 2828886 ] Problem with reports from temporary tables
 * https://sourceforge.net/tracker/?func=detail&atid=879332&aid=2828886&group_id=176962
 * <p>
 * FR 2872010 - Dunning Run for a complete Dunning (not just level) - Developer: Carlos Ruiz - globalqss - Sponsor: Metas
 * @version $Id: ReportEngine.java,v 1.4 2006/10/08 06:52:51 comdivision Exp $
 */
@Deprecated // TODO delete me
public class ReportEngine implements PrintServiceAttributeListener
{
	/**
	 * Constructor
	 *
	 * @param ctx   context
	 * @param pf    Print Format
	 * @param query Optional Query
	 * @param info  print info
	 */
	public ReportEngine(Properties ctx, @NonNull final MPrintFormat pf, MQuery query, ArchiveInfo info)
	{
		this(ctx, pf, query, info, null);
	}    // ReportEngine

	public ReportEngine(Properties ctx, @Nullable final MPrintFormat pf, MQuery query, ArchiveInfo archiveInfo, String trxName)
	{

		if (pf != null)
		{
			Check.assumeNotNull(pf.getLanguage(), "Param {} has a language set", pf);
			log.info(pf + " -- " + query);
		}

		m_printerName = Services.get(IPrinterRoutingBL.class).getDefaultPrinterName(); // metas: us319

		m_ctx = ctx;
		//
		m_printFormat = pf;
		this.archiveInfo = archiveInfo;
		m_trxName = trxName;
		setQuery(query);        // loads Data

	}    // ReportEngine

	/**
	 * Static Logger
	 */
	private static Logger log = LogManager.getLogger(ReportEngine.class);

	/**
	 * Context
	 */
	private Properties m_ctx;

	/**
	 * Print Format
	 */
	private MPrintFormat m_printFormat;
	/**
	 * Print Info
	 */
	private final ArchiveInfo archiveInfo;
	/**
	 * Query
	 */
	private MQuery m_query;
	/**
	 * Query Data
	 */
	private PrintData m_printData;
	/**
	 * Layout
	 */
	private LayoutEngine m_layout = null;
	/**
	 * Printer
	 */
	private String m_printerName = null; // metas: us316: commented: Ini.getProperty(Ini.P_PRINTER);
	/**
	 * Transaction Name
	 */
	private String m_trxName = null;
	/**
	 * Where filter
	 */
	private String m_whereExtended = null;
	/**
	 * Window
	 */
	private int m_windowNo = 0;

	private boolean m_summary = false;

	/**
	 * Set PrintFormat.
	 * If Layout was created, re-create layout
	 *
	 * @param pf print format
	 */
	public void setPrintFormat(MPrintFormat pf)
	{
		m_printFormat = pf;
		if (m_layout != null)
		{
			final PrintData printData = getPrintData();
			m_layout.setPrintFormat(pf, false);
			m_layout.setPrintData(printData, m_query, true);    // format changes data
		}
	}    // setPrintFormat

	/**
	 * Set Query and generate PrintData.
	 * If Layout was created, re-create layout
	 *
	 * @param query query
	 */
	public void setQuery(MQuery query)
	{
		m_query = query;
		if (query == null)
			return;
		//
		m_printData = null; // reset printData
	}    // setQuery

	/**
	 * Get Query
	 *
	 * @return query
	 */
	public MQuery getQuery()
	{
		return m_query;
	}    // getQuery

	/**
	 * Set PrintData for Format restricted by Query.
	 * Nothing set if there is no query
	 * Sets m_printData
	 */
	private void setPrintData()
	{
		if (m_query == null || m_printFormat == null)
			return;

		final DataEngine de = new DataEngine(m_printFormat.getLanguage(), m_trxName);
		final PrintData printData = de.getPrintData(m_ctx, m_printFormat, m_query, m_summary);
		setPrintData(printData);

		if (m_layout != null)
			m_layout.setPrintData(printData, m_query, true);

		// m_printData.dump();
	}    // setPrintData

	/**
	 * Get PrintData
	 *
	 * @return print data
	 */
	public PrintData getPrintData()
	{
		if (m_printData == null)
		{
			setPrintData();
		}
		return m_printData;
	}    // getPrintData

	/**
	 * Set PrintData
	 *
	 * @param printData printData
	 */
	public void setPrintData(PrintData printData)
	{
		if (printData == null)
			return;
		m_printData = printData;
	}    // setPrintData

	/**************************************************************************
	 * Layout
	 */
	private void layout()
	{
		if (m_printFormat == null)
			throw new IllegalStateException("No print format");

		final PrintData printData = getPrintData();
		if (printData == null)
			throw new IllegalStateException("No print data (Delete Print Format and restart)");
		m_layout = new LayoutEngine(m_printFormat, printData, m_query, archiveInfo, m_trxName);
	}    // layout

	/**
	 * Get Layout
	 *
	 * @return Layout
	 */
	public LayoutEngine getLayout()
	{
		if (m_layout == null)
			layout();
		return m_layout;
	}    // getLayout

	/**
	 * Get PrintFormat (Report) Name
	 *
	 * @return name
	 */
	public String getName()
	{
		return m_printFormat.getName();
	}    // getName

	/**
	 * Get PrintFormat
	 *
	 * @return print format
	 */
	public MPrintFormat getPrintFormat()
	{
		return m_printFormat;
	}    // getPrintFormat

	/**
	 * Get Print Info
	 *
	 * @return info
	 */
	public ArchiveInfo getPrintInfo()
	{
		return archiveInfo;
	}    // getPrintInfo

	/**
	 * Get PrintLayout (Report) Context
	 *
	 * @return context
	 */
	public Properties getCtx()
	{
		return getLayout().getCtx();
	}    // getCtx

	/**
	 * Get Row Count
	 *
	 * @return row count
	 */
	public int getRowCount()
	{
		return getPrintData().getRowCount();
	}    // getRowCount

	/**
	 * Get Column Count
	 *
	 * @return column count
	 */
	public int getColumnCount()
	{
		if (m_layout != null)
			return m_layout.getColumnCount();
		return 0;
	}    // getColumnCount

	@Deprecated
	public void print()
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Print Service Attribute Listener.
	 *
	 * @param psae event
	 */
	@Override
	public void attributeUpdate(PrintServiceAttributeEvent psae)
	{
		/**
		 * PrintEvent on Win32 Printer : \\MAIN\HP LaserJet 5L
		 * PrintServiceAttributeSet - length=2
		 * queued-job-count = 0 (class javax.print.attribute.standard.QueuedJobCount)
		 * printer-is-accepting-jobs = accepting-jobs (class javax.print.attribute.standard.PrinterIsAcceptingJobs)
		 * PrintEvent on Win32 Printer : \\MAIN\HP LaserJet 5L
		 * PrintServiceAttributeSet - length=1
		 * queued-job-count = 1 (class javax.print.attribute.standard.QueuedJobCount)
		 * PrintEvent on Win32 Printer : \\MAIN\HP LaserJet 5L
		 * PrintServiceAttributeSet - length=1
		 * queued-job-count = 0 (class javax.print.attribute.standard.QueuedJobCount)
		 **/
		log.debug("attributeUpdate - " + psae);
		// PrintUtil.dump (psae.getAttributes());
	}    // attributeUpdate

	/**
	 * Get PrinterJob based on PrinterName
	 *
	 * @param printerName optional Printer Name
	 * @return PrinterJob
	 */
	private PrinterJob getPrinterJob(String printerName)
	{
		if (printerName != null && printerName.length() > 0)
			return CPrinter.getPrinterJob(printerName);
		return CPrinter.getPrinterJob(m_printerName);
	}    // getPrinterJob

	/**
	 * Show Dialog and Set Paper
	 * Optionally re-calculate layout
	 */
	public void pageSetupDialog()
	{
		if (m_layout == null)
			layout();
		m_layout.pageSetupDialog(getPrinterJob(m_printerName));
	}    // pageSetupDialog

	/**
	 * Set Printer (name)
	 *
	 * @param printerName valid printer name
	 */
	public void setPrinterName(String printerName)
	{
		if (printerName == null)
			m_printerName = Services.get(IPrinterRoutingBL.class).getDefaultPrinterName(); // metas: us316
			// m_printerName = Ini.getProperty(Ini.P_PRINTER); // metas: us316: commented
		else
			m_printerName = printerName;
	}    // setPrinterName

	/**
	 * Get Printer (name)
	 *
	 * @return printer name
	 */
	public String getPrinterName()
	{
		return m_printerName;
	}    // getPrinterName

	/**************************************************************************
	 * Create HTML File
	 *
	 * @param file file
	 * @param onlyTable if false create complete HTML document
	 * @param language optional language - if null the default language is used to format nubers/dates
	 * @return true if success
	 */
	public boolean createHTML(File file, boolean onlyTable, Language language)
	{
		return createHTML(file, onlyTable, language, null);
	}

	/**************************************************************************
	 * Create HTML File
	 *
	 * @param file file
	 * @param onlyTable if false create complete HTML document
	 * @param language optional language - if null the default language is used to format nubers/dates
	 * @param extension optional extension for html output
	 * @return true if success
	 */
	public boolean createHTML(File file, boolean onlyTable, Language language, IHTMLExtension extension)
	{
		try
		{
			Language lang = language;
			if (lang == null)
			{
				lang = Language.getLoginLanguage();
			}
			Writer fw = new OutputStreamWriter(new FileOutputStream(file, false), Ini.getCharset()); // teo_sarca: save using adempiere charset [ 1658127 ]
			return createHTML(new BufferedWriter(fw), onlyTable, lang, extension);
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
	}    // createHTML

	/**
	 * Write HTML to writer
	 *
	 * @param writer    writer
	 * @param onlyTable if false create complete HTML document
	 * @param language  optional language - if null nubers/dates are not formatted
	 * @return true if success
	 */
	public boolean createHTML(Writer writer, boolean onlyTable, Language language)
	{
		return createHTML(writer, onlyTable, language, null);
	}

	/**
	 * Write HTML to writer
	 *
	 * @param writer    writer
	 * @param onlyTable if false create complete HTML document
	 * @param language  optional language - if null numbers/dates are not formatted
	 * @param extension optional extension for html output
	 * @return true if success
	 */
	public boolean createHTML(Writer writer, boolean onlyTable, Language language, IHTMLExtension extension)
	{
		final PrintData m_printData = getPrintData();

		try
		{
			String cssPrefix = extension != null ? extension.getClassPrefix() : null;
			if (Check.isEmpty(cssPrefix, true))
				cssPrefix = null;

			table table = new table();
			if (cssPrefix != null)
				table.setClass(cssPrefix + "-table");
			//
			// for all rows (-1 = header row)
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
				// for all columns
				for (int col = 0; col < m_printFormat.getItemCount(); col++)
				{
					MPrintFormatItem item = m_printFormat.getItem(col);
					if (item.isPrinted())
					{
						// header row
						if (row == -1)
						{
							th th = new th();
							tr.addElement(th);
							th.addElement(StringUtils.maskHTML(item.getPrintName(language)));
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
								PrintDataElement pde = (PrintDataElement)obj;
								String value = pde.getValueDisplay(language);    // formatted
								if (pde.getColumnName().endsWith("_ID") && extension != null)
								{
									// link for column
									a href = new a("javascript:void(0)");
									href.setID(pde.getColumnName() + "_" + row + "_a");
									td.addElement(href);
									href.addElement(StringUtils.maskHTML(value));
									if (cssPrefix != null)
										href.setClass(cssPrefix + "-href");

									extension.extendIDColumn(row, td, href, pde);

								}
								else
								{
									td.addElement(StringUtils.maskHTML(value));
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
								// ignore contained Data
							}
							else
								log.error("Element not PrintData(Element) " + obj.getClass());
						}
					}    // printed
				}    // for all columns
			}    // for all rows

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
	}    // createHTML

	/**************************************************************************
	 * Create CSV File
	 *
	 * @param file file
	 * @param delimiter delimiter, e.g. comma, tab
	 * @param language translation language
	 * @return true if success
	 */
	public boolean createCSV(File file, char delimiter, Language language)
	{
		try
		{
			Writer fw = new OutputStreamWriter(new FileOutputStream(file, false), Ini.getCharset()); // teo_sarca: save using adempiere charset [ 1658127 ]
			return createCSV(new BufferedWriter(fw), delimiter, language);
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
	}    // createCSV

	/**
	 * Write CSV to writer
	 *
	 * @param writer    writer
	 * @param delimiter delimiter, e.g. comma, tab
	 * @param language  translation language
	 * @return true if success
	 */
	private boolean createCSV(Writer writer, char delimiter, Language language)
	{
		final PrintData m_printData = getPrintData();

		if (delimiter == 0)
			delimiter = '\t';
		try
		{
			// for all rows (-1 = header row)
			for (int row = -1; row < m_printData.getRowCount(); row++)
			{
				StringBuffer sb = new StringBuffer();
				if (row != -1)
					m_printData.setRowIndex(row);

				// for all columns
				boolean first = true;    // first column to print
				for (int col = 0; col < m_printFormat.getItemCount(); col++)
				{
					MPrintFormatItem item = m_printFormat.getItem(col);
					if (item.isPrinted())
					{
						// column delimiter (comma or tab)
						if (first)
							first = false;
						else
							sb.append(delimiter);
						// header row
						if (row == -1)
							createCSVvalue(sb, delimiter,
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
									data = pde.getValueDisplay(language);    // formatted
							}
							else if (obj instanceof PrintData)
							{
							}
							else
								log.error("Element not PrintData(Element) " + obj.getClass());
							createCSVvalue(sb, delimiter, data);
						}
					}    // printed
				}    // for all columns
				writer.write(sb.toString());
				writer.write(Env.NL);
			}    // for all rows
			//
			writer.flush();
			writer.close();
		}
		catch (Exception e)
		{
			log.error("(w)", e);
		}
		return false;
	}    // createCSV

	/**
	 * Add Content to CSV string.
	 * Encapsulate/mask content in " if required
	 *
	 * @param sb        StringBuffer to add to
	 * @param delimiter delimiter
	 * @param content   column value
	 */
	private void createCSVvalue(StringBuffer sb, char delimiter, String content)
	{
		// nothing to add
		if (content == null || content.isEmpty())
			return;
		//
		boolean needMask = false;
		StringBuffer buff = new StringBuffer();
		char chars[] = content.toCharArray();
		for (char d : chars)
		{
			char c = d;
			if (c == '"')
			{
				needMask = true;
				buff.append(c);        // repeat twice
			}    // mask if any control character
			else if (!needMask && (c == delimiter || !Character.isLetterOrDigit(c)))
				needMask = true;
			buff.append(c);
		}

		// Optionally mask value
		if (needMask)
			sb.append('"').append(buff).append('"');
		else
			sb.append(buff);
	}    // addCSVColumnValue

	/**************************************************************************
	 * Create XML File
	 *
	 * @param file file
	 * @return true if success
	 */
	public boolean createXML(File file)
	{
		try
		{
			Writer fw = new OutputStreamWriter(new FileOutputStream(file, false), Ini.getCharset()); // teo_sarca: save using adempiere charset [ 1658127 ]
			return createXML(new BufferedWriter(fw));
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
	}    // createXML

	/**
	 * Write XML to writer
	 *
	 * @param writer writer
	 * @return true if success
	 */
	public boolean createXML(Writer writer)
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
	}    // createXML

	/**************************************************************************
	 * Create PDF file.
	 * (created in temporary storage)
	 *
	 * @return PDF file
	 */
	public File getPDF()
	{
		return getPDF(null);
	}    // getPDF

	/**
	 * Create PDF file.
	 *
	 * @param file file
	 * @return PDF file
	 */
	public File getPDF(File file)
	{
		try
		{
			if (file == null)
				file = File.createTempFile("ReportEngine", ".pdf");
		}
		catch (IOException e)
		{
			log.error("", e);
		}
		if (createPDF(file))
			return file;
		return null;
	}    // getPDF

	/**
	 * Create PDF File
	 *
	 * @param file file
	 * @return true if success
	 */
	public boolean createPDF(File file)
	{
		String fileName = null;
		URI uri = null;

		try
		{
			if (file == null)
				file = File.createTempFile("ReportEngine", ".pdf");
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
			if (getPrintFormat().getJasperProcess_ID() > 0)
			{
				final byte[] data = createPdfDataInvokeReportProcess();
				final ByteArrayInputStream stream = new ByteArrayInputStream(data == null ? new byte[] {} : data);
				FileUtil.copy(stream, file);
			}
			else
			{
				throw new AdempiereException("legacy report engine are no longer supported");
			}
		}
		catch (Exception e)
		{
			log.error("PDF", e);
			return false;
		}

		File file2 = new File(fileName);
		log.info(file2.getAbsolutePath() + " - " + file2.length());
		return file2.exists();
	}    // createPDF

	public byte[] createPDFData()
	{
		try
		{

			// 03744: begin
			if (getPrintFormat() != null && getPrintFormat().getJasperProcess_ID() > 0)
			{
				return createPdfDataInvokeReportProcess();
			}
			else
			{
				// 03744: end
				if (m_layout == null)
					layout();
				return Document.getPDFAsArray(m_layout.getPageable(false));
			} // 03744
		}
		catch (final RuntimeException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
		// return null;
	}    // createPDFData

	private byte[] createPdfDataInvokeReportProcess()
	{
		final Properties ctx = Env.getCtx(); // ReportEngine.getCtx() fails, because the ctx would be taken from an "old-school" layout

		final ProcessExecutor processExecutor = ProcessInfo.builder()
				.setCtx(ctx)
				.setAD_Process_ID(getPrintFormat().getJasperProcess_ID())
				.setRecord(getPrintInfo().getRecordRef())
				//.addParameter(ReportConstants.REPORT_PARAM_BARCODE_URL, DocumentReportService.getBarcodeServlet(Env.getClientId(ctx), Env.getOrgId(ctx)))
				.addParameter(IMassPrintingService.PARAM_PrintCopies, getPrintInfo().getCopies().toInt())
				.setPrintPreview(true) // don't archive it! just give us the PDF data
				.buildAndPrepareExecution()
				.onErrorThrowException(true)
				.executeSync();
		return processExecutor.getResult().getReportDataAsByteArray();
	}

	/**************************************************************************
	 * Create PostScript File
	 *
	 * @param file file
	 * @return true if success
	 */
	public boolean createPS(File file)
	{
		try
		{
			return createPS(new FileOutputStream(file));
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
	}    // createPS

	/**
	 * Write PostScript to writer
	 *
	 * @param os output stream
	 * @return true if success
	 */
	public boolean createPS(OutputStream os)
	{
		try
		{
			String outputMimeType = DocFlavor.BYTE_ARRAY.POSTSCRIPT.getMimeType();
			DocFlavor docFlavor = DocFlavor.SERVICE_FORMATTED.PAGEABLE;
			StreamPrintServiceFactory[] spsfactories = StreamPrintServiceFactory.lookupStreamPrintServiceFactories(docFlavor, outputMimeType);
			if (spsfactories.length == 0)
			{
				log.error("(fos) - No StreamPrintService");
				return false;
			}
			// just use first one - sun.print.PSStreamPrinterFactory
			// System.out.println("- " + spsfactories[0]);
			StreamPrintService sps = spsfactories[0].getPrintService(os);
			// get format
			if (m_layout == null)
				layout();
			// print it
			sps.createPrintJob().print(m_layout.getPageable(false),
					new HashPrintRequestAttributeSet());
			//
			os.flush();
			// following 2 line for backward compatibility
			if (os instanceof FileOutputStream)
				((FileOutputStream)os).close();
		}
		catch (Exception e)
		{
			log.error("(fos)", e);
		}
		return false;
	}    // createPS

	/**
	 * Create Excel file
	 *
	 * @param outFile  output file
	 * @param language
	 */
	public void createXLS(@NonNull final File outFile, final Language language)
	{
		PrintDataExcelExporter.builder()
				.excelFormat(ExcelFormats.getFormatByFile(outFile))
				.printData(getPrintData())
				.printFormat(getPrintFormat())
				.language(language)
				.build()
				.exportToFile(outFile);
	}

	/**************************************************************************
	 * Get Report Engine for process info
	 *
	 * @param ctx context
	 * @param pi process info with AD_PInstance_ID
	 * @return report engine or null
	 */
	static public ReportEngine get(Properties ctx, ProcessInfo pi)
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

		// Get AD_Table_ID and TableName
		String sql = "SELECT rv.AD_ReportView_ID,rv.WhereClause,"
				+ " t.AD_Table_ID,t.TableName, pf.AD_PrintFormat_ID, pf.IsForm, pf.AD_Client_ID "
				+ "FROM AD_PInstance pi"
				+ " INNER JOIN AD_Process p ON (pi.AD_Process_ID=p.AD_Process_ID)"
				+ " INNER JOIN AD_ReportView rv ON (p.AD_ReportView_ID=rv.AD_ReportView_ID)"
				+ " INNER JOIN AD_Table t ON (rv.AD_Table_ID=t.AD_Table_ID)"
				+ " LEFT OUTER JOIN AD_PrintFormat pf ON (p.AD_ReportView_ID=pf.AD_ReportView_ID AND pf.AD_Client_ID IN (0,?)) "
				+ "WHERE pi.AD_PInstance_ID=? "        // #2
				+ "ORDER BY pf.AD_Client_ID DESC, pf.IsDefault DESC";    // own first
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			DB.setParameters(pstmt, AD_Client_ID, pi.getPinstanceId());
			rs = pstmt.executeQuery();
			// Just get first
			if (rs.next())
			{
				AD_ReportView_ID = rs.getInt(1);        // required
				whereClause = rs.getString(2);
				if (rs.wasNull())
					whereClause = "";
				//
				AD_Table_ID = rs.getInt(3);
				TableName = rs.getString(4);            // required for query
				AD_PrintFormat_ID = rs.getInt(5);        // required
				IsForm = "Y".equals(rs.getString(6));    // required
				Client_ID = rs.getInt(7);
			}
		}
		catch (SQLException e1)
		{
			log.error("(1) - " + sql, e1);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		// Nothing found
		if (AD_ReportView_ID <= 0)
		{
			// Check Print format in Report Directly
			sql = "SELECT t.AD_Table_ID,t.TableName, pf.AD_PrintFormat_ID, pf.IsForm "
					+ "FROM AD_PInstance pi"
					+ " INNER JOIN AD_Process p ON (pi.AD_Process_ID=p.AD_Process_ID)"
					+ " INNER JOIN AD_PrintFormat pf ON (p.AD_PrintFormat_ID=pf.AD_PrintFormat_ID)"
					+ " INNER JOIN AD_Table t ON (pf.AD_Table_ID=t.AD_Table_ID) "
					+ "WHERE pi.AD_PInstance_ID=?";
			try
			{
				pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
				DB.setParameters(pstmt, pi.getPinstanceId());
				rs = pstmt.executeQuery();
				if (rs.next())
				{
					whereClause = "";
					AD_Table_ID = rs.getInt(1);
					TableName = rs.getString(2);            // required for query
					AD_PrintFormat_ID = rs.getInt(3);        // required
					IsForm = "Y".equals(rs.getString(4));    // required
					Client_ID = AD_Client_ID;
				}
			}
			catch (SQLException e1)
			{
				log.error("(2) - " + sql, e1);
			}
			finally
			{
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;
			}
			if (AD_PrintFormat_ID <= 0)
			{
				log.error("Report Info NOT found AD_PInstance_ID=" + pi.getPinstanceId() + ",AD_Client_ID=" + AD_Client_ID);
				return null;
			}
		}

		// Create Query from Parameters
		MQuery query = null;
		if (IsForm && pi.getRecord_ID() != 0        // Form = one record
				&& !TableName.startsWith("T_"))    // Not temporary table - teo_sarca, BF [ 2828886 ]
		{
			query = MQuery.getEqualQuery(TableName + "_ID", pi.getRecord_ID());
		}
		else
		{
			query = MQuery.get(ctx, pi.getPinstanceId(), TableName);
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

		// Add to static where clause from ReportView
		if (whereClause.length() != 0)
			query.addRestriction(whereClause);

		// Get Print Format
		MPrintFormat format = pi.getResult().getPrintFormat();
		if (format == null && AD_PrintFormat_ID > 0)
		{
			// We have a PrintFormat with the correct Client
			if (Client_ID == AD_Client_ID)
				format = MPrintFormat.get(ctx, AD_PrintFormat_ID, false);
			else
				format = MPrintFormat.copyToClient(ctx, AD_PrintFormat_ID, AD_Client_ID);
		}
		if (format != null && format.getItemCount() == 0)
		{
			log.info("No Items - recreating:  " + format);
			format.delete(true);
			format = null;
		}
		// Create Format
		if (format == null && AD_ReportView_ID != 0)
			format = MPrintFormat.createFromReportView(ctx, AD_ReportView_ID, pi.getTitle());
		if (format == null)
			return null;
		//
		ArchiveInfo info = new ArchiveInfo(pi);

		return new ReportEngine(ctx, format, query, info);
	}    // get

	/**
	 * Print Confirm.
	 * Update Date Printed
	 *
	 * @param type      document type
	 * @param Record_ID record id
	 */
	public static void printConfirm(final StandardDocumentReportType type, final int Record_ID)
	{
		final StringBuilder sql = new StringBuilder();
		if (type == StandardDocumentReportType.ORDER || type == StandardDocumentReportType.SHIPMENT || type == StandardDocumentReportType.INVOICE)
		{
			sql.append("UPDATE ").append(type.getBaseTableName())
					.append(" SET DatePrinted=now(), IsPrinted='Y' WHERE ")
					.append(type.getKeyColumnName()).append("=").append(Record_ID);
		}

		//
		if (sql.length() > 0)
		{
			final int no = DB.executeUpdate(sql.toString(), ITrx.TRXNAME_None);
			if (no != 1)
			{
				log.error("Updated records={} - should be just one for SQL={}", no, sql);
			}
		}
	}    // printConfirm

	public void setWhereExtended(String whereExtended)
	{
		m_whereExtended = whereExtended;
	}

	public String getWhereExtended()
	{
		return m_whereExtended;
	}

	/* Save windowNo of the report to parse the context */
	public void setWindowNo(int windowNo)
	{
		m_windowNo = windowNo;
	}

	public int getWindowNo()
	{
		return m_windowNo;
	}

	public void setSummary(boolean summary)
	{
		m_summary = summary;
	}

	@Nullable
	public static StandardDocumentReportType getTypeByTableId(int tableId)
	{
		if (tableId <= 0)
		{
			return null;
		}
		final String tableName = Services.get(IADTableDAO.class).retrieveTableName(tableId);
		return StandardDocumentReportType.ofTableNameOrNull(tableName);
	}
}    // ReportEngine
