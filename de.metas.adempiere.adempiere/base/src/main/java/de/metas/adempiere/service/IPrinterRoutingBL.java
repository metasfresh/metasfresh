/**
 *
 */
package de.metas.adempiere.service;

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

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.ISingletonService;

import de.metas.process.ProcessInfo;

/**
 * @author tsa
 *
 */
public interface IPrinterRoutingBL extends ISingletonService
{
	// Printer Types - please keep in sync with X_AD_Printer.PRINTERTYPE_*
	String PRINTERTYPE_General = "G";
	String PRINTERTYPE_Fax = "F";
	String PRINTERTYPE_Label = "L";

	String findPrinterName(Properties ctx, int C_DocType_ID, int AD_Process_ID, String printerType);

	/**
	 * Uses the properties of the given <code>pi</code> to retrieve the printer to use via <code>AD_PrinterRouting</code>
	 * @param pi
	 * @return
	 */
	String findPrinterName(ProcessInfo pi);

	/**
	 * Try to find printing service for given parameters.
	 *
	 * @param ctx
	 * @param C_DocType_ID
	 * @param AD_Process_ID
	 * @param printerType
	 * @return printing service; never return null
	 * @throws AdempiereException if printing service was not found or printerType is not supported
	 */
	IPrintingService findPrintingService(Properties ctx, int C_DocType_ID, int AD_Process_ID, String printerType);

	String getDefaultPrinterName();

	String getDefaultPrinterName(String printerType);
}
