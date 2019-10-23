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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterJob;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.DefaultComboBoxModel;

import org.compiere.swing.CComboBox;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import de.metas.adempiere.service.IPrinterRoutingBL;

/**
 *  Adempiere Printer Selection
 *
 *  @author     Jorg Janke
 *  @version    $Id: CPrinter.java,v 1.3 2006/07/30 00:53:02 jjanke Exp $
 */
public class CPrinter extends CComboBox implements ActionListener 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6366208617152587573L;


	/**
	 *  Get Print (Services) Names
	 *  @return Printer Name array
	 */
	public static String[] getPrinterNames()
	{
		// Refresh print services every time the combobox is constructed
		s_services = PrintServiceLookup.lookupPrintServices(null,null);

		String[] retValue = new String[s_services.length];
		for (int i = 0; i < s_services.length; i++)
			retValue[i] = s_services[i].getName();
		return retValue;
	}   //  getPrintServiceNames


	/**
	 *  Return default PrinterJob
	 *  @return PrinterJob
	 */
	public static PrinterJob getPrinterJob()
	{
		return getPrinterJob(Services.get(IPrinterRoutingBL.class).getDefaultPrinterName()); // metas: us316
		//return getPrinterJob(Ini.getProperty(Ini.P_PRINTER)); // metas: us316: commented
	}   //  getPrinterJob

	/**
	 *  Return PrinterJob with selected printer name.
	 *  @param printerName if null, get default printer (Ini)
	 *  @return PrinterJob
	 */
	public static PrinterJob getPrinterJob (String printerName)
	{
		PrinterJob pj = null;
		PrintService ps = null;
		try
		{
			pj = PrinterJob.getPrinterJob();

			//  find printer service
			if (printerName == null || printerName.length() == 0)
				printerName = Services.get(IPrinterRoutingBL.class).getDefaultPrinterName(); // metas: us316
//				printerName = Ini.getProperty(Ini.P_PRINTER); // metas: us316: commented
			if (printerName != null && printerName.length() != 0)
			{
			//	System.out.println("CPrinter.getPrinterJob - searching " + printerName);
				for (int i = 0; i < s_services.length; i++)
				{
					String serviceName = s_services[i].getName();
					if (printerName.equals(serviceName))
					{
						ps = s_services[i];
					//	System.out.println("CPrinter.getPrinterJob - found " + printerName);
						break;
					}
				//	System.out.println("CPrinter.getPrinterJob - not: " + serviceName);
				}
			}   //  find printer service

			try
			{
				if (ps != null)
					pj.setPrintService(ps);
			}
			catch (Exception e)
			{
				log.warn("Could not set Print Service: " + e.toString());
			}
			//
			PrintService psUsed = pj.getPrintService();
			if (psUsed == null)
				log.warn("Print Service not Found");
			else
			{
				String serviceName = psUsed.getName();
				if (printerName != null && !printerName.equals(serviceName))
					log.warn("Not found: " + printerName + " - Used: " + serviceName);
			}
		}
		catch (Exception e)
		{
			log.warn("Could not create for " + printerName + ": " + e.toString());
		}
		return pj;
	}   //  getPrinterJob


	/** Available Printer Services  */
//	private static PrintService[]   s_services = PrinterJob.lookupPrintServices();
	private static PrintService[]   s_services = PrintServiceLookup.lookupPrintServices(null,null);

	/**	Logger	*/
	private static Logger log = LogManager.getLogger(CPrinter.class);
	
	
	/**************************************************************************
	 *  Create PrinterJob
	 */
	public CPrinter()
	{
		super(getPrinterNames());
		//  Set Default
		setValue(Services.get(IPrinterRoutingBL.class).getDefaultPrinterName()); // metas: us316
		//setValue(Ini.getProperty(Ini.P_PRINTER)); // metas: us316: commented
		this.addActionListener(this);
	}   //  CPrinter

	/**
	 * 	Action Listener
	 * 	@param e event
	 */
	public void actionPerformed (ActionEvent e)
	{

	}	//	actionPerformed

	/**
	 * 	Get PrintService
	 * 	@return print service
	 */
	public PrintService getPrintService()
	{
		String currentService = (String)getSelectedItem();
		for (int i = 0; i < s_services.length; i++)
		{
			if (s_services[i].getName().equals(currentService))
				return s_services[i];
		}
		return PrintServiceLookup.lookupDefaultPrintService();
	}	//	getPrintService
	
	/**
	 * 	Refresh printer list
	 */
	public void refresh() {
		String current = (String) getSelectedItem();
		removeAllItems();
		setModel(new DefaultComboBoxModel(getPrinterNames()));
		if (current != null) {
			for (int i = 0; i < getItemCount(); i++) {
				String item = (String) getItemAt(i);
				if (item.equals(current))
					setSelectedIndex(i);
			}
		}
	}

}   //  CPrinter
