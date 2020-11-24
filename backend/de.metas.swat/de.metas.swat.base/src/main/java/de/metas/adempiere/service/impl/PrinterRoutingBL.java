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

import java.util.List;
import java.util.Properties;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import de.metas.adempiere.service.PrinterRoutingsQuery;
import de.metas.document.DocTypeId;
import de.metas.organization.OrgId;
import de.metas.security.RoleId;
import de.metas.user.UserId;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.util.proxy.Cached;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.slf4j.Logger;

import de.metas.adempiere.model.I_AD_Printer;
import de.metas.adempiere.model.I_AD_PrinterRouting;
import de.metas.adempiere.model.X_AD_Printer;
import de.metas.adempiere.model.X_AD_PrinterRouting;
import de.metas.adempiere.service.IPrinterRoutingBL;
import de.metas.adempiere.service.IPrinterRoutingDAO;
import de.metas.adempiere.service.IPrintingService;
import de.metas.cache.annotation.CacheCtx;
import de.metas.document.engine.IDocumentBL;
import de.metas.logging.LogManager;
import de.metas.process.AdProcessId;
import de.metas.process.ProcessInfo;
import de.metas.util.Check;
import de.metas.util.Services;

/**
 * @author tsa
 *
 */
public class PrinterRoutingBL implements IPrinterRoutingBL
{
	private final Logger log = LogManager.getLogger(getClass());

	private static final String DEFAULT_PrinterType = PRINTERTYPE_General;

	public IPrintingService findPrintingService(final ProcessInfo pi)
	{
		final Properties ctx = Env.getCtx();
		final int AD_Client_ID = Env.getAD_Client_ID(ctx);
		final int AD_Org_ID = Env.getAD_Org_ID(ctx);
		final int AD_Role_ID = Env.getAD_Role_ID(ctx);
		final int AD_User_ID = Env.getAD_User_ID(ctx);
		final AdProcessId AD_Process_ID = pi.getAdProcessId();
		final int C_DocType_ID = Services.get(IDocumentBL.class).getC_DocType_ID(ctx, pi.getTable_ID(), pi.getRecord_ID());
		final String printerType = null;

		return findPrintingService0(ctx,
				AD_Client_ID,
				AD_Org_ID,
				AD_Role_ID,
				AD_User_ID,
				C_DocType_ID,
				AD_Process_ID.getRepoId(),
				pi.getTable_ID(),
				printerType);
	}

	@Override
	public IPrintingService findPrintingService(final Properties ctx,
			final int C_DocType_ID,
			final int AD_Process_ID,
			final int AD_Table_ID,
			final String printerType)
	{
		final int AD_Client_ID = Env.getAD_Client_ID(ctx);
		final int AD_Org_ID = Env.getAD_Org_ID(ctx);
		final int AD_Role_ID = Env.getAD_Role_ID(ctx);
		final int AD_User_ID = Env.getAD_User_ID(ctx);

		return findPrintingService0(ctx,
				AD_Client_ID, AD_Org_ID,
				AD_Role_ID, AD_User_ID,
				C_DocType_ID, AD_Process_ID,
				AD_Table_ID,
				printerType);
	}

	// @Cached
	private IPrintingService findPrintingService0(
			@CacheCtx final Properties ctx,
			final int AD_Client_ID, final int AD_Org_ID,
			final int AD_Role_ID, final int AD_User_ID,
			final int C_DocType_ID, final int AD_Process_ID,
			final int AD_Table_ID,
			final String printerType)
	{
		final IPrinterRoutingDAO dao = Services.get(IPrinterRoutingDAO.class);

		final List<I_AD_PrinterRouting> rs = dao.fetchPrinterRoutings(PrinterRoutingsQuery
				.builder()
				.clientId(ClientId.ofRepoIdOrSystem(AD_Client_ID))
				.orgId(OrgId.ofRepoIdOrAny(AD_Org_ID))
				.roleId(RoleId.ofRepoIdOrNull(AD_Role_ID))
				.userId(UserId.ofRepoIdOrNullIfSystem(AD_User_ID))
				.tableId(AdTableId.ofRepoIdOrNull(AD_Table_ID))
				.processId(AdProcessId.ofRepoIdOrNull(AD_Process_ID))
				.docTypeId(DocTypeId.ofRepoIdOrNull(C_DocType_ID))
				.printerType(printerType)
				.build());

		for (final I_AD_PrinterRouting route : rs)
		{
			final IPrintingService printingService = getPrintingService(route);
			if (printingService != null)
			{
				if (LogManager.isLevelFine())
				{
					log.debug("Found: " + printingService);
				}
				return printingService;
			}
		}
		return getDefaultPrintingService(ctx, printerType);
	}

	// set it protected to make it testable
	private IPrintingService getPrintingService(final I_AD_PrinterRouting route)
	{
		log.debug("Checking route: {}", route);

		final I_AD_Printer printer = route.getAD_Printer();
		log.debug("Printer: {}", printer.getPrinterName());

		final PrintService systemPrintService = getSystemPrintService(printer.getPrinterName());
		if (systemPrintService == null)
		{
			log.info("Printer not found in system: {}", printer.getPrinterName());
			return null;
		}

		log.debug("System Print Service: {}", systemPrintService);

		final String printerName = systemPrintService.getName();
		Boolean isDirectPrint = null;
		if (isDirectPrint == null && route.getIsDirectPrint() != null)
		{
			isDirectPrint = X_AD_PrinterRouting.ISDIRECTPRINT_Yes.equals(route.getIsDirectPrint());
			log.debug("IsDirectPrint: {} (From: {})", isDirectPrint, route);
		}
		if (isDirectPrint == null)
		{
			isDirectPrint = isDirectPrint(printer);
		}

		final PrintingServiceImpl printingService = new PrintingServiceImpl(printerName, printer.getPrinterType(), isDirectPrint);
		log.debug("Printing Service: {}", printingService);

		return printingService;
	}

	@Cached
		/* package */PrintService getSystemPrintService(final String printerName)
	{
		final PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
		if (services == null || services.length == 0)
		{
			return null;
		}
		for (final PrintService service : services)
		{
			if (service.getName().equals(printerName))
			{
				return service;
			}
		}
		return null;
	}

	private boolean isDirectPrint(final I_AD_Printer printer)
	{
		Boolean isDirectPrint = null;
		if (printer != null && printer.getIsDirectPrint() != null)
		{
			isDirectPrint = X_AD_Printer.ISDIRECTPRINT_Yes.equals(printer.getIsDirectPrint());
			if (LogManager.isLevelFine())
			{
				log.debug("IsDirectPrint for: " + isDirectPrint + " (From: " + printer + ")");
			}
		}
		if (isDirectPrint == null)
		{
			isDirectPrint = !Ini.isPropertyBool(Ini.P_PRINTPREVIEW);
			if (LogManager.isLevelFine())
			{
				log.debug("IsDirectPrint: " + isDirectPrint + " (From: ini)");
			}
		}
		return isDirectPrint;
	}

	private IPrintingService getDefaultPrintingService(final Properties ctx, String printerType)
	{
		if (printerType == null)
		{
			printerType = DEFAULT_PrinterType;
		}

		final String printerName = getDefaultPrinterName(printerType);
		if (printerName == null)
		{
			throw new AdempiereException("No default printer found for type " + printerType); // TODO: trl
		}

		final I_AD_Printer printer = Services.get(IPrinterRoutingDAO.class).findPrinterByName(printerName);
		final boolean isDirectPrint = isDirectPrint(printer);
		return new PrintingServiceImpl(printerName, printerType, isDirectPrint);
	}

	@Override
	public String getDefaultPrinterName()
	{
		return getDefaultPrinterName(DEFAULT_PrinterType);
	}

	@Override
	public String getDefaultPrinterName(String printerType)
	{
		log.debug("Looking for printerType=" + printerType);

		if (printerType == null)
		{
			printerType = DEFAULT_PrinterType;
		}

		final String printerName;
		if (PRINTERTYPE_General.equals(printerType))
		{
			printerName = getDefaultPrinterNameFromIni(Ini.P_PRINTER);
		}
		else if (PRINTERTYPE_Label.equals(printerType))
		{
			printerName = getDefaultPrinterNameFromIni(Ini.P_LABELPRINTER);
		}
		else
		{
			// TODO: trl
			throw new AdempiereException("No default printer logic defined for PrinterType " + printerType);
		}

		log.debug("PrinterName: " + printerName);
		return printerName;
	}

	private String getDefaultPrinterNameFromIni(final String propName)
	{
		log.debug("Looking for " + propName + " in ini file");

		String printerName = Ini.getProperty(propName);
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
		Ini.setProperty(propName, printerName);
		log.debug("Found printerName: " + printerName);
		return printerName;
	}

	@Override
	public String findPrinterName(final Properties ctx, final int C_DocType_ID,
			final int AD_Process_ID,
			final int AD_Table_ID,
			final String printerType)
	{
		final IPrintingService printingService = findPrintingService(ctx,
				C_DocType_ID,
				AD_Process_ID,
				AD_Table_ID,
				printerType);
		return printingService.getPrinterName();
	}

	@Override
	public String findPrinterName(final ProcessInfo pi)
	{
		final IPrintingService printingService = findPrintingService(pi);
		return printingService.getPrinterName();
	}
}
