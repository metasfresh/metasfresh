package de.metas.printing.model.validator;

import com.google.common.collect.ImmutableList;
import de.metas.async.Async_Constants;
import de.metas.async.api.IAsyncBatchListeners;
import de.metas.cache.CacheMgt;
import de.metas.cache.model.IModelCacheService;
import de.metas.event.Topic;
import de.metas.logging.LogManager;
import de.metas.notification.INotificationBL;
import de.metas.printing.Printing_Constants;
import de.metas.printing.api.IPrintingQueueBL;
import de.metas.printing.async.spi.impl.AutomaticallyInvoicePdfPrintinAsyncBatchListener;
import de.metas.printing.async.spi.impl.PDFPrintingAsyncBatchListener;
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
import de.metas.printing.spi.impl.DefaultPrintingRecordTextProvider;
import de.metas.printing.spi.impl.DocumentPrintingQueueHandler;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.migration.logger.IMigrationLogger;
import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.session.ISessionBL;
import org.adempiere.ad.session.MFSession;
import org.adempiere.server.rpl.trx.api.IReplicationTrxBL;
import org.compiere.util.Env;
import org.slf4j.Logger;

import java.util.List;
import java.util.Properties;

/**
 * Printing base - Main Validator
 *
 * @author tsa
 */
public class Main extends AbstractModuleInterceptor
{
	private static final Logger logger = LogManager.getLogger(Main.class);

	private Boolean enabled;
	private boolean loggedThatPrintingIsNotEnabled = false;

	private boolean checkPrintingEnabled()
	{
		Boolean enabled = this.enabled;
		if (enabled == null)
		{
			enabled = this.enabled = Printing_Constants.isEnabled();
			loggedThatPrintingIsNotEnabled = false;
		}

		if (!enabled && !loggedThatPrintingIsNotEnabled)
		{
			logger.info("Printing is disabled; not registering any printing MIs, callouts etc");
			loggedThatPrintingIsNotEnabled = true;
		}

		return enabled;
	}

	@Override
	protected void onBeforeInit()
	{
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
	}

	@Override
	protected void registerInterceptors(@NonNull final IModelValidationEngine engine)
	{
		// Do not initialize if the printing module is disabled
		if (!checkPrintingEnabled())
		{
			return;
		}

		engine.addModelValidator(AD_User_Login.instance);

		//engine.addModelValidator(new AD_Archive()); added by spring

		//engine.addModelValidator(new AD_Printer_Config()); added by spring
		engine.addModelValidator(new AD_Printer_Matching());
		engine.addModelValidator(new AD_PrinterRouting());
		engine.addModelValidator(new AD_PrinterHW());
		engine.addModelValidator(new AD_PrinterHW_Calibration());
		engine.addModelValidator(new AD_PrinterHW_MediaTray());
		engine.addModelValidator(new AD_PrinterTray_Matching());

		engine.addModelValidator(new C_Print_Job_Instructions());
		engine.addModelValidator(new C_Printing_Queue());
		engine.addModelValidator(new C_Printing_Queue_Recipient());

		// NOTE: we have an entry in AD_ModelValidator for "SwingPrintingClientValidator", so don't add it programmatically
		// engine.addModelValidator(new SwingPrintingClientValidator());
	}

	@Override
	protected void registerCallouts(@NonNull final IProgramaticCalloutProvider programaticCalloutProvider)
	{
		// Do not initialize if the printing module is disabled
		if (!checkPrintingEnabled())
		{
			return;
		}

		// task 09417
		programaticCalloutProvider.registerAnnotatedCallout(de.metas.printing.callout.C_Doc_Outbound_Config.instance);
	}

	@Override
	protected void setupCaching(final IModelCacheService cachingService)
	{
		// task 09417: while we are in the area, also make sure that config changes are propagated
		final CacheMgt cacheMgt = CacheMgt.get();
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_AD_PrinterRouting.Table_Name);
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_AD_Printer_Config.Table_Name);
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_AD_Printer_Matching.Table_Name);
	}

	@Override
	protected void onAfterInit()
	{
		// Do not initialize if the printing module is disabled
		if (!checkPrintingEnabled())
		{
			return;
		}

		Services.get(IPrintingQueueBL.class).registerHandler(DocumentPrintingQueueHandler.instance);

		// task 09833
		// Register the Default Printing Info ctx provider
		Services.get(INotificationBL.class).setDefaultCtxProvider(DefaultPrintingRecordTextProvider.instance);

		Services.get(IAsyncBatchListeners.class).registerAsyncBatchNoticeListener(new PDFPrintingAsyncBatchListener(), Printing_Constants.C_Async_Batch_InternalName_PDFPrinting);
		Services.get(IAsyncBatchListeners.class).registerAsyncBatchNoticeListener(new AutomaticallyInvoicePdfPrintinAsyncBatchListener(), Async_Constants.C_Async_Batch_InternalName_AutomaticallyInvoicePdfPrinting);
	}

	@Override
	protected List<Topic> getAvailableUserNotificationsTopics()
	{
		return ImmutableList.of(Printing_Constants.USER_NOTIFICATIONS_TOPIC);
	}

	@Override
	public void onUserLogin(final int AD_Org_ID, final int AD_Role_ID, final int AD_User_ID)
	{
		// make sure that the host key is set in the context
		if (checkPrintingEnabled())
		{
			final Properties ctx = Env.getCtx();
			final MFSession session = Services.get(ISessionBL.class).getCurrentSession(ctx);
			session.getOrCreateHostKey(ctx);
		}
	}

}
