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

import java.awt.print.PageFormat;
import java.awt.print.Pageable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.Properties;

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUIFactory;
import javax.print.StreamPrintServiceFactory;
import javax.print.attribute.Attribute;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.JobName;
import javax.print.attribute.standard.JobPriority;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.JDialog;
import javax.swing.JPanel;

import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.I_AD_PrintForm;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Language;
import org.compiere.util.Msg;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 *  Print Utilities
 *
 *  @author     Jorg Janke
 *  @version    $Id: PrintUtil.java,v 1.2 2006/07/30 00:53:02 jjanke Exp $
 */
public class PrintUtil
{
	/**	Logger			*/
	private static Logger log = LogManager.getLogger(PrintUtil.class);
	/** Default Print Request Attribute Set */
	private static PrintRequestAttributeSet     s_prats = new HashPrintRequestAttributeSet();

	/**
	 *  Return Default Print Request Attributes
	 *  @return PrintRequestAttributeSet
	 */
	public static PrintRequestAttributeSet getDefaultPrintRequestAttributes()
	{
		return s_prats;
	}   //  getDefaultPrintRequestAttributes

	/**
	 *  Get Default Application Flavor
	 *  @return Pageable
	 */
	public static DocFlavor getDefaultFlavor()
	{
		return DocFlavor.SERVICE_FORMATTED.PAGEABLE;
	}   //  getDefaultFlavor

	/**
	 *  Get Print Services for standard flavor and pratt
	 *  @return print services
	 */
	public static PrintService[] getPrintServices ()
	{
		return PrintServiceLookup.lookupPrintServices (getDefaultFlavor(), getDefaultPrintRequestAttributes());
	}   //  getPrintServices

	/**
	 *  Get Default Print Service
	 *  @return PrintService
	 */
	public static PrintService getDefaultPrintService()
	{
		return PrintServiceLookup.lookupDefaultPrintService();
	}   //  getPrintServices


	/*************************************************************************/

	/**
	 * 	Print (async)
	 * 	@param printerName optional printer name
	 *  @param jobName optional printer job name
	 * 	@param pageable pageable
	 *  @param copies number of copies
	 *  @param withDialog if true, shows printer dialog
	 */
	static public void print (Pageable pageable, String printerName, String jobName,
		int copies, boolean withDialog)
	{
		if (pageable == null)
			return;
		String name = "Adempiere_";
		if (jobName != null)
			name += jobName;
		//
		PrinterJob job = CPrinter.getPrinterJob(printerName);
		job.setJobName (name);
		job.setPageable (pageable);
		//	Attributes
		HashPrintRequestAttributeSet prats = new HashPrintRequestAttributeSet();
		prats.add(new Copies(copies));
		//	Set Orientation
		if (pageable.getPageFormat(0).getOrientation() == PageFormat.PORTRAIT)
			prats.add(OrientationRequested.PORTRAIT);
		else
			prats.add(OrientationRequested.LANDSCAPE);
		prats.add(new JobName(name, Language.getLoginLanguage().getLocale()));
		prats.add(getJobPriority(pageable.getNumberOfPages(), copies, withDialog));
		//
		print (job, prats, withDialog, false);
	}	//	print

	/**
	 * 	Print Async
	 *  @param pageable pageable
	 *  @param prats print attribure set
	 */
	static public void print (Pageable pageable, PrintRequestAttributeSet prats)
	{
		PrinterJob job = CPrinter.getPrinterJob();
		job.setPageable(pageable);
		print (job, prats, true, false);
	}	//	print

	/**
	 * 	Print
	 * 	@param job printer job
	 *  @param prats print attribure set
	 *  @param withDialog if true shows Dialog
	 *  @param waitForIt if false print async
	 */
	static public void print (final PrinterJob job,
		final PrintRequestAttributeSet prats,
		boolean withDialog, boolean waitForIt)
	{
		if (job == null)
			return;
		boolean printed = true;

		if (withDialog)
			printed = job.printDialog(prats);

		if (printed)
		{
			if (withDialog)
			{
				Attribute[] atts = prats.toArray();
				for (int i = 0; i < atts.length; i++)
					log.debug(atts[i].getName() + "=" + atts[i]);
			}
			//
			if (waitForIt)
			{
				log.debug("(wait) " + job.getPrintService());
				try
				{
					job.print(prats);
				}
				catch (Exception ex)
				{
					log.error("(wait)", ex);
				}
			}
			else	//	Async
			{
				//	Create Thread
				Thread printThread = new Thread()
				{
					@Override
					public void run()
					{
						log.debug("print: " + job.getPrintService());
						try
						{
							job.print(prats);
						}
						catch (Exception ex)
						{
							log.error("print", ex);
						}
					}
				};
				printThread.start();
			}	//	Async
		}	//	printed
	}	//	printAsync

	/**
	 * 	Get Job Priority based on pages printed.
	 *  The more pages, the lower the priority
	 * 	@param pages number of pages
	 *  @param copies number of copies
	 *  @param withDialog dialog gets lower priority than direct print
	 * 	@return Job Priority
	 */
	static public JobPriority getJobPriority (int pages, int copies, boolean withDialog)
	{
		//	Set priority (the more pages, the lower the priority)
		int priority =  copies * pages;
		if (withDialog)				//	 prefer direct print
			priority *= 2;
		priority = 100 - priority;	//	convert to 1-100 supported range
		if (priority < 10)
			priority = 10;
		else if (priority > 100)
			priority = 100;
		return new JobPriority(priority);
	}	//	getJobPriority

	/*************************************************************************/

	/**
	 * 	Dump Printer Job info
	 * 	@param job printer job
	 */
	public static void dump (PrinterJob job)
	{
		StringBuffer sb = new StringBuffer(job.getJobName());
		sb.append("/").append(job.getUserName())
			.append(" Service=").append(job.getPrintService().getName())
			.append(" Copies=").append(job.getCopies());
		PageFormat pf = job.defaultPage();
		sb.append(" DefaultPage ")
			.append("x=").append(pf.getImageableX())
			.append(",y=").append(pf.getImageableY())
			.append(" w=").append(pf.getImageableWidth())
			.append(",h=").append(pf.getImageableHeight());
		System.out.println(sb.toString());
	}	//	dump

	/**
	 * 	Dump Print Service Attribute Set to System.out
	 * 	@param psas PS Attribute Set
	 */
	public static void dump (PrintServiceAttributeSet psas)
	{
		System.out.println("PrintServiceAttributeSet - length=" + psas.size());
		Attribute[] ats = psas.toArray();
		for (int i = 0; i < ats.length; i++)
			System.out.println(ats[i].getName() + " = " + ats[i] + "  (" + ats[i].getCategory() + ")");
	}	//	dump

	/**
	 * 	Dump Print Request Service Attribute Set to System.out
	 * 	@param prats Print Request Attribute Set
	 */
	public static void dump (PrintRequestAttributeSet prats)
	{
		System.out.println("PrintRequestAttributeSet - length=" + prats.size());
		Attribute[] ats = prats.toArray();
		for (int i = 0; i < ats.length; i++)
			System.out.println(ats[i].getName() + " = " + ats[i] + "  (" + ats[i].getCategory() + ")");
	}	//	dump

	/**
	 * 	Dump Stream Print Services
	 * 	@param docFlavor flavor
	 * 	@param outputMimeType mime
	 */
	public static void dump (DocFlavor docFlavor, String outputMimeType)
	{
		System.out.println();
		System.out.println("DocFlavor=" + docFlavor + ", Output=" + outputMimeType);
		StreamPrintServiceFactory[] spsfactories =
			StreamPrintServiceFactory.lookupStreamPrintServiceFactories(docFlavor, outputMimeType);
		for (int i = 0; i < spsfactories.length; i++)
		{
			System.out.println("- " + spsfactories[i]);
			DocFlavor dfs[] = spsfactories[i].getSupportedDocFlavors();
			for (int j = 0; j < dfs.length; j++)
			{
				System.out.println("   -> " + dfs[j]);
			}
		}
	}	//	dump

	/**
	 * 	Dump Stream Print Services
	 * 	@param docFlavor flavor
	 */
	public static void dump (DocFlavor docFlavor)
	{
		System.out.println();
		System.out.println("DocFlavor=" + docFlavor);
		PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
		PrintService[] pss =
			PrintServiceLookup.lookupPrintServices(docFlavor, pras);
		for (int i = 0; i < pss.length; i++)
		{
			PrintService ps = pss[i];
			System.out.println("- " + ps);
			System.out.println("  Factory=" + ps.getServiceUIFactory());
			ServiceUIFactory uiF = pss[i].getServiceUIFactory();
			if (uiF != null)
			{
				System.out.println("about");
				JDialog about = (JDialog) uiF.getUI (ServiceUIFactory.ABOUT_UIROLE, ServiceUIFactory.JDIALOG_UI);
				about.setVisible(true);
				System.out.println("admin");
				JDialog admin = (JDialog) uiF.getUI (ServiceUIFactory.ADMIN_UIROLE, ServiceUIFactory.JDIALOG_UI);
				admin.setVisible(true);
				System.out.println("main");
				JDialog main = (JDialog) uiF.getUI (ServiceUIFactory.MAIN_UIROLE, ServiceUIFactory.JDIALOG_UI);
				main.setVisible(true);
				System.out.println("reserved");
				JDialog res = (JDialog) uiF.getUI (ServiceUIFactory.RESERVED_UIROLE, ServiceUIFactory.JDIALOG_UI);
				res.setVisible(true);
			}
			//
			DocFlavor dfs[] = pss[i].getSupportedDocFlavors();
			System.out.println("  - Supported Doc Flavors");
			for (int j = 0; j < dfs.length; j++)
				System.out.println("    -> " + dfs[j]);
			//	Attribute
			Class[] attCat = pss[i].getSupportedAttributeCategories();
			System.out.println("  - Supported Attribute Categories");
			for (int j = 0; j < attCat.length; j++)
				System.out.println("    -> " + attCat[j].getName() 
					+ " = " + pss[i].getDefaultAttributeValue(attCat[j]));
			//
		}
	}	//	dump

	/*************************************************************************/

	/**
	 * 	Test Print Services
	 */
	private static void testPS()
	{
		PrintService ps = getDefaultPrintService();
		ServiceUIFactory factory = ps.getServiceUIFactory();
		System.out.println(factory);
		if (factory != null)
		{
			System.out.println("Factory");
			JPanel p0 = (JPanel) factory.getUI(ServiceUIFactory.ABOUT_UIROLE, ServiceUIFactory.JDIALOG_UI);
			p0.setVisible(true);
			JPanel p1 = (JPanel) factory.getUI(ServiceUIFactory.ADMIN_UIROLE, ServiceUIFactory.JDIALOG_UI);
			p1.setVisible(true);
			JPanel p2 = (JPanel) factory.getUI(ServiceUIFactory.MAIN_UIROLE, ServiceUIFactory.JDIALOG_UI);
			p2.setVisible(true);

		}
		System.out.println("1----------");
		PrinterJob pj = PrinterJob.getPrinterJob();
		PrintRequestAttributeSet pratts = getDefaultPrintRequestAttributes();
		//	Page Dialog
		PageFormat pf = pj.pageDialog(pratts);
		System.out.println("Pratts Size = " + pratts.size());
		Attribute[] atts = pratts.toArray();
		for (int i = 0; i < atts.length; i++)
			System.out.println(atts[i].getName() + " = " + atts[i] + " - " + atts[i].getCategory());
		System.out.println("PageFormat h=" + pf.getHeight() + ",w=" + pf.getWidth() + " - x=" + pf.getImageableX() + ",y=" + pf.getImageableY() + " - ih=" + pf.getImageableHeight() + ",iw=" + pf.getImageableWidth()
			+ " - Orient=" + pf.getOrientation());
		ps = pj.getPrintService();
		System.out.println("PrintService = " + ps.getName());

		//	Print Dialog
		System.out.println("2----------");
		pj.printDialog(pratts);
		System.out.println("Pratts Size = " + pratts.size());
		atts = pratts.toArray();
		for (int i = 0; i < atts.length; i++)
			System.out.println(atts[i].getName() + " = " + atts[i] + " - " + atts[i].getCategory());
		pf = pj.defaultPage();
		System.out.println("PageFormat h=" + pf.getHeight() + ",w=" + pf.getWidth() + " - x=" + pf.getImageableX() + ",y=" + pf.getImageableY() + " - ih=" + pf.getImageableHeight() + ",iw=" + pf.getImageableWidth()
			+ " - Orient=" + pf.getOrientation());
		ps = pj.getPrintService();
		System.out.println("PrintService= " + ps.getName());

		System.out.println("3----------");
		try
		{
			pj.setPrintService(ps);
		}
		catch (PrinterException pe)
		{
			System.out.println(pe);
		}
		pf = pj.validatePage(pf);
		System.out.println("PageFormat h=" + pf.getHeight() + ",w=" + pf.getWidth() + " - x=" + pf.getImageableX() + ",y=" + pf.getImageableY() + " - ih=" + pf.getImageableHeight() + ",iw=" + pf.getImageableWidth()
			+ " - Orient=" + pf.getOrientation());
		ps = pj.getPrintService();
		System.out.println("PrintService= " + ps.getName());


		System.out.println("4----------");
		pj.printDialog();
	}	//	testPS

	/**
	 * 	Test Stream Print Services
	 */
	private static void testSPS()
	{
	//	dump (DocFlavor.INPUT_STREAM.GIF, DocFlavor.BYTE_ARRAY.POSTSCRIPT.getMimeType());
	//	dump (DocFlavor.SERVICE_FORMATTED.PAGEABLE, DocFlavor.BYTE_ARRAY.POSTSCRIPT.getMimeType());
	//	dump (DocFlavor.INPUT_STREAM.GIF, DocFlavor.BYTE_ARRAY.PDF.getMimeType());
	//	dump (DocFlavor.SERVICE_FORMATTED.PAGEABLE, DocFlavor.BYTE_ARRAY.GIF.getMediaSubtype());
	//	dump (DocFlavor.SERVICE_FORMATTED.PAGEABLE, DocFlavor.BYTE_ARRAY.JPEG.getMediaSubtype());

	//	dump (DocFlavor.SERVICE_FORMATTED.PAGEABLE);					//	lists devices able to output pageable
	//	dump (DocFlavor.SERVICE_FORMATTED.PRINTABLE);
	//	dump (DocFlavor.INPUT_STREAM.TEXT_PLAIN_HOST);
	//	dump (DocFlavor.INPUT_STREAM.POSTSCRIPT);


		PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
		PrintService[] pss =
			PrintServiceLookup.lookupPrintServices(DocFlavor.SERVICE_FORMATTED.PAGEABLE, pras);
		for (int i = 0; i < pss.length; i++)
		{
			PrintService ps = pss[i];
			String name = ps.getName();
			if (name.indexOf("PDF") != -1 || name.indexOf("Acrobat") != -1)
			{
				System.out.println("----");
				System.out.println(ps);
				Class[] cat = ps.getSupportedAttributeCategories();
				for (int j = 0; j < cat.length; j++)
				{
					System.out.println("- " + cat[j]);
				}
			}
		}

	//	dump (null, DocFlavor.BYTE_ARRAY.PDF.getMimeType());			//	lists PDF output
	//	dump (null, DocFlavor.BYTE_ARRAY.POSTSCRIPT.getMediaType());	//	lists PS output

	//	dump(null, null);
	}	//	testSPS


	/**************************************************************************
	 * 	Create Print Form & Print Formats for a new Client.
	 *  - Order, Invoice, etc.
	 *  Called from VSetup
	 *  @param AD_Client_ID new Client
	 */
	public static void setupPrintForm (int AD_Client_ID)
	{
		log.info("AD_Client_ID=" + AD_Client_ID);
		Properties ctx = Env.getCtx();
		//CLogMgt.enable(false);
		//
		//	Order Template
		int Order_PrintFormat_ID = MPrintFormat.copyToClient(ctx, 100, AD_Client_ID).get_ID();
		int OrderLine_PrintFormat_ID = MPrintFormat.copyToClient(ctx, 101, AD_Client_ID).get_ID();
		updatePrintFormatHeader(Order_PrintFormat_ID, OrderLine_PrintFormat_ID);
		//	Invoice
		int Invoice_PrintFormat_ID = MPrintFormat.copyToClient(ctx, 102, AD_Client_ID).get_ID();
		int InvoiceLine_PrintFormat_ID = MPrintFormat.copyToClient(ctx, 103, AD_Client_ID).get_ID();
		updatePrintFormatHeader(Invoice_PrintFormat_ID, InvoiceLine_PrintFormat_ID);
		//	Shipment
		int Shipment_PrintFormat_ID = MPrintFormat.copyToClient(ctx, 104, AD_Client_ID).get_ID();
		int ShipmentLine_PrintFormat_ID = MPrintFormat.copyToClient(ctx, 105, AD_Client_ID).get_ID();
		updatePrintFormatHeader(Shipment_PrintFormat_ID, ShipmentLine_PrintFormat_ID);
		//	Check
		int Check_PrintFormat_ID = MPrintFormat.copyToClient(ctx, 106, AD_Client_ID).get_ID();
		int RemittanceLine_PrintFormat_ID = MPrintFormat.copyToClient(ctx, 107, AD_Client_ID).get_ID();
		updatePrintFormatHeader(Check_PrintFormat_ID, RemittanceLine_PrintFormat_ID);
		//	Remittance
		int Remittance_PrintFormat_ID = MPrintFormat.copyToClient(ctx, 108, AD_Client_ID).get_ID();
		updatePrintFormatHeader(Remittance_PrintFormat_ID, RemittanceLine_PrintFormat_ID);

	//	TODO: MPrintForm	
	//	MPrintForm form = new MPrintForm(); 
		int AD_PrintForm_ID = DB.getNextID (AD_Client_ID, I_AD_PrintForm.Table_Name, ITrx.TRXNAME_None);
		String sql = "INSERT INTO AD_PrintForm(AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_PrintForm_ID,"
			+ "Name,Order_PrintFormat_ID,Invoice_PrintFormat_ID,Remittance_PrintFormat_ID,Shipment_PrintFormat_ID)"
			//
			+ " VALUES (" + AD_Client_ID + ",0,'Y',now(),0,now(),0," + AD_PrintForm_ID + ","
			+ "'" + Msg.translate(ctx, "Standard") + "',"
			+ Order_PrintFormat_ID + "," + Invoice_PrintFormat_ID + ","
			+ Remittance_PrintFormat_ID + "," + Shipment_PrintFormat_ID + ")";
		int no = DB.executeUpdate(sql, null);
		if (no != 1)
			log.error("PrintForm NOT inserted");
		//
		//CLogMgt.enable(true);
	}	//	createDocuments

	/**
	 * 	Update the PrintFormat Header lines with Reference to Child Print Format.
	 * 	@param Header_ID AD_PrintFormat_ID for Header
	 * 	@param Line_ID AD_PrintFormat_ID for Line
	 */
	static private void updatePrintFormatHeader (int Header_ID, int Line_ID)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE AD_PrintFormatItem SET AD_PrintFormatChild_ID=")
			.append(Line_ID)
			.append(" WHERE AD_PrintFormatChild_ID IS NOT NULL AND AD_PrintFormat_ID=")
			.append(Header_ID);
		int no = DB.executeUpdate(sb.toString(), null);
	}	//	updatePrintFormatHeader

	/*************************************************************************/

	/**
	 *  Test
	 *  @param args arg
	 */
	public static void main(String[] args)
	{
	//	org.compiere.Adempiere.startupClient();
	//	setupPrintForm (11);
	//	setupPrintForm (1000000);



		testPS();	//	Print Services
	//	testSPS();	//	Stream Print Services

	//	dumpSPS(null, null);
	}   //  main

}   //  PrintUtil
