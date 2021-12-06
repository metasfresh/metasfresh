/**
 *
 */
package de.metas.adempiere.service.impl;

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

import de.metas.adempiere.service.IPrinterRoutingBL;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import org.compiere.util.Ini;
import org.slf4j.Logger;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

/**
 * @author tsa
 *
 */
public class PrinterRoutingBL implements IPrinterRoutingBL
{
	private final Logger log = LogManager.getLogger(getClass());
	
	@Override
	public String getDefaultPrinterName()
	{
		log.debug("Looking for " + Ini.P_PRINTER + " in ini file");

		String printerName = Ini.getProperty(Ini.P_PRINTER);
		if (!Check.isEmpty(printerName))
		{
			log.debug("Found printerName: " + printerName);
			return printerName;
		}

		log.debug("Looking for machine's printers");
		final PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
		if (services == null || services.length == 0)
		{
			// t.schoeneberg@metas.de: so what? we don't need a printer to generate PDFs
			// log.warn("No default printer found on this machine");
			return "";
		}

		printerName = services[0].getName();
		Ini.setProperty(Ini.P_PRINTER, printerName);
		log.debug("Found printerName: " + printerName);
		return printerName;
	}
}
