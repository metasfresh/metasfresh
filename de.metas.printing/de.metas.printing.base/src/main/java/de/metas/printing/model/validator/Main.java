package de.metas.printing.model.validator;

/*
 * #%L
 * de.metas.printing.base
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

import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.dao.cache.IModelCacheService;
import org.adempiere.ad.migration.logger.IMigrationLogger;
import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.server.rpl.trx.api.IReplicationTrxBL;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Client;
import org.compiere.report.IJasperServiceRegistry;
import org.compiere.report.IJasperServiceRegistry.ServiceType;
import org.compiere.util.CacheMgt;

import de.metas.notification.INotificationBL;
import de.metas.printing.Printing_Constants;
import de.metas.printing.api.IPrintingQueueBL;
import de.metas.printing.model.I_AD_Print_Clients;
import de.metas.printing.model.I_AD_PrinterHW;
import de.metas.printing.model.I_AD_PrinterHW_Calibration;
import de.metas.printing.model.I_AD_PrinterHW_MediaSize;
import de.metas.printing.model.I_AD_PrinterHW_MediaTray;
import de.metas.printing.model.I_AD_PrinterRouting;
import de.metas.printing.model.I_AD_Printer_Config;
import de.metas.printing.model.I_AD_Printer_Matching;
import de.metas.printing.model.I_AD_User_Login;
import de.metas.printing.model.I_C_PrintPackageData;
import de.metas.printing.model.I_C_Print_Job;
import de.metas.printing.model.I_C_Print_Job_Detail;
import de.metas.printing.model.I_C_Print_Job_Instructions;
import de.metas.printing.model.I_C_Print_Job_Line;
import de.metas.printing.model.I_C_Print_Package;
import de.metas.printing.model.I_C_Print_PackageInfo;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.printing.spi.impl.DefaultPrintingNotificationCtxProvider;
import de.metas.printing.spi.impl.DocumentPrintingQueueHandler;

/**
 * Printing base - Main Validator
 *
 * @author tsa
 *
 */
public class Main extends AbstractModuleInterceptor
{
	@Override
	protected void onInit(final IModelValidationEngine engine, final I_AD_Client client)
	{
		// Do not initialize if the printing module is disabled
		if (!Printing_Constants.isEnabled())
		{
			return;
		}

		//
		// Configure tables which are skipped when we record migration scripts
		{
			final IMigrationLogger migrationLogger = Services.get(IMigrationLogger.class);
			migrationLogger.addTableToIgnoreList(I_AD_User_Login.Table_Name);
			migrationLogger.addTableToIgnoreList(I_C_Print_Package.Table_Name);
			migrationLogger.addTableToIgnoreList(I_C_PrintPackageData.Table_Name);
			migrationLogger.addTableToIgnoreList(I_C_Print_PackageInfo.Table_Name);
			migrationLogger.addTableToIgnoreList(I_C_Print_Job.Table_Name);
			migrationLogger.addTableToIgnoreList(I_C_Print_Job_Detail.Table_Name);
			migrationLogger.addTableToIgnoreList(I_C_Print_Job_Instructions.Table_Name);
			migrationLogger.addTableToIgnoreList(I_C_Print_Job_Line.Table_Name);
			migrationLogger.addTableToIgnoreList(I_C_Printing_Queue.Table_Name);
			migrationLogger.addTableToIgnoreList(I_AD_Print_Clients.Table_Name);
			migrationLogger.addTableToIgnoreList(I_AD_PrinterHW.Table_Name);
			migrationLogger.addTableToIgnoreList(I_AD_PrinterHW_Calibration.Table_Name);
			migrationLogger.addTableToIgnoreList(I_AD_PrinterHW_MediaSize.Table_Name);
			migrationLogger.addTableToIgnoreList(I_AD_PrinterHW_MediaTray.Table_Name);
			migrationLogger.addTableToIgnoreList(I_AD_Printer_Matching.Table_Name);
		}

		//
		// Configure tables which are excluded by EXP_ReplicationTrx
		{
			final IReplicationTrxBL replicationTrxBL = Services.get(IReplicationTrxBL.class);
			replicationTrxBL.addTableToIgnoreList(I_C_Print_Package.Table_Name);

			// task 08283: no need to create replication-trx records for AD_User_Logins, because there would be just one record per trx,
			// and we don't need gracefully handle not-unique lookup results.
			replicationTrxBL.addTableToIgnoreList(I_AD_User_Login.Table_Name);
		}

		engine.addModelValidator(AD_User_Login.instance, client);

		engine.addModelValidator(new AD_Archive(), client);

		engine.addModelValidator(new AD_Printer_Config(), client);
		engine.addModelValidator(new AD_Printer_Matching(), client);
		engine.addModelValidator(new AD_PrinterRouting(), client);
		engine.addModelValidator(new AD_PrinterHW(), client);
		engine.addModelValidator(new AD_PrinterHW_Calibration(), client);
		engine.addModelValidator(new AD_PrinterHW_MediaTray(), client);
		engine.addModelValidator(new AD_PrinterTray_Matching(), client);
		engine.addModelValidator(new C_BPartner(), client); // task 08958

		engine.addModelValidator(new C_Print_Job_Instructions(), client);
		engine.addModelValidator(new C_Printing_Queue(), client);
		engine.addModelValidator(new C_Printing_Queue_Recipient(), client);

		// NOTE: we have an entry in AD_ModelValidator for "SwingPrintingClientValidator", so don't add it programmatically
		// engine.addModelValidator(new SwingPrintingClientValidator(), client);

		Services.get(IPrintingQueueBL.class).registerHandler(DocumentPrintingQueueHandler.instance);

		//
		// Adapter: Old school Jasper Printing Service to our Printing
		Services.get(IJasperServiceRegistry.class).registerJasperService(ServiceType.MASS_PRINT_FRAMEWORK, de.metas.printing.adapter.JasperServiceAdapter.instance);

		// callouts
		final IProgramaticCalloutProvider programaticCalloutProvider = Services.get(IProgramaticCalloutProvider.class);

		// task 09417
		programaticCalloutProvider.registerAnnotatedCallout(de.metas.printing.callout.C_Doc_Outbound_Config.instance);

		// task 09833
		// Register the Default Printing Info ctx provider
		Services.get(INotificationBL.class).setDefaultCtxProvider(DefaultPrintingNotificationCtxProvider.instance);
	}

	@Override
	protected void setupCaching(IModelCacheService cachingService)
	{
		// task 09417: while we are in the area, also make sure that config changes are propagated
		final CacheMgt cacheMgt = CacheMgt.get();
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_AD_PrinterRouting.Table_Name);
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_AD_Printer_Config.Table_Name);
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_AD_Printer_Matching.Table_Name);
	}

}
