package de.metas.adempiere.process;

/*
 * #%L
 * de.metas.swat.base
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


import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.adempiere.model.I_AD_Printer;
import de.metas.process.JavaProcess;

public class AD_Printer_CheckSuggest extends JavaProcess
{
	@Override
	protected void prepare()
	{
	}

	@Override
	protected String doIt() throws Exception
	{
		PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null,null);
		for (PrintService ps : printServices)
		{
			addLog("Available service: "+ps.getName()+" - "+ps.toString());
		}
		
		if (I_AD_Printer.Table_Name.equals(getTableName()) && getRecord_ID() > 0)
		{
			int AD_Printer_ID = getRecord_ID();
			I_AD_Printer printer = InterfaceWrapperHelper.create(getCtx(), AD_Printer_ID, I_AD_Printer.class, get_TrxName());
			String printerName = printer.getPrinterName();
			boolean found = false;
			for (PrintService ps : printServices)
			{
				if (ps.getName().equals(printerName))
				{
					addLog("Found service for "+printerName+": "+ps);
					found = true;
				}
			}
			if (!found)
			{
				throw new AdempiereException("No printing services found for "+printerName);
			}
		}

		return "Ok";
	}
}
