package org.compiere.model;

import de.metas.ad_reference.ADRefList;
import de.metas.ad_reference.ADReferenceService;
import de.metas.ad_reference.ReferenceId;
import de.metas.cache.model.ModelCacheInvalidationService;
import de.metas.document.sequence.IDocumentNoBL;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.logging.LogManager;
import de.metas.monitoring.adapter.NoopPerformanceMonitoringService;
import de.metas.monitoring.adapter.PerformanceMonitoringService;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.migration.logger.IMigrationLogger;
import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.ad.session.ChangeLogRecord;
import org.adempiere.ad.session.ISessionBL;
import org.adempiere.ad.session.ISessionDAO;
import org.adempiere.ad.session.MFSession;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.service.ISysConfigBL;
import org.compiere.SpringContextHolder;
import org.compiere.util.Ini;
import org.slf4j.Logger;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

final class POServicesFacade
{
	private static final Logger logger = LogManager.getLogger(POServicesFacade.class);

	private IDeveloperModeBL _developerModeBL;
	private ISysConfigBL _sysConfigBL;
	private ISessionBL _sessionBL;
	private ISessionDAO _sessionDAO;
	private IMigrationLogger _migrationLogger;
	private ModelCacheInvalidationService _cacheInvalidationService;
	private IDocumentNoBuilderFactory _documentNoBuilderFactory;
	private IDocumentNoBL _documentNoBL;
	private ITrxManager _trxManager;
	private ADReferenceService _adReferenceService;

	private static PerformanceMonitoringService _performanceMonitoringService;
	private static final String PM_SYSCONFIG_NAME = "de.metas.monitoring.po.enable";
	private static final boolean PM_SYS_CONFIG_DEFAULT_VALUE = false;
	private static final PerformanceMonitoringService.Metadata PM_METADATA_SAVE_EX =
			PerformanceMonitoringService.Metadata
					.builder()
					.className("PO")
					.type(PerformanceMonitoringService.Type.PO)
					.functionName("saveEx")
					.build();
	private static final PerformanceMonitoringService.Metadata PM_METADATA_LOAD =
			PerformanceMonitoringService.Metadata
					.builder()
					.className("PO")
					.type(PerformanceMonitoringService.Type.PO)
					.functionName("load")
					.build();

	private IDeveloperModeBL developerModeBL()
	{
		IDeveloperModeBL developerModeBL = this._developerModeBL;
		if (developerModeBL == null)
		{
			developerModeBL = this._developerModeBL = Services.get(IDeveloperModeBL.class);
		}
		return developerModeBL;
	}

	private ISysConfigBL sysConfigBL()
	{
		ISysConfigBL sysConfigBL = this._sysConfigBL;
		if (sysConfigBL == null)
		{
			sysConfigBL = this._sysConfigBL = Services.get(ISysConfigBL.class);
		}
		return sysConfigBL;
	}

	public ISessionBL sessionBL()
	{
		ISessionBL sessionBL = this._sessionBL;
		if (sessionBL == null)
		{
			sessionBL = this._sessionBL = Services.get(ISessionBL.class);
		}
		return sessionBL;
	}

	private ISessionDAO sessionDAO()
	{
		ISessionDAO sessionDAO = this._sessionDAO;
		if (sessionDAO == null)
		{
			sessionDAO = this._sessionDAO = Services.get(ISessionDAO.class);
		}
		return sessionDAO;
	}

	private IMigrationLogger migrationLogger()
	{
		IMigrationLogger migrationLogger = this._migrationLogger;
		if (migrationLogger == null)
		{
			migrationLogger = this._migrationLogger = Services.get(IMigrationLogger.class);
		}
		return migrationLogger;
	}

	public ModelCacheInvalidationService cacheInvalidationService()
	{
		ModelCacheInvalidationService cacheInvalidationService = this._cacheInvalidationService;
		if (cacheInvalidationService == null)
		{
			//
			// Case: on Swing login which happens before Spring context is created
			if (!SpringContextHolder.instance.isApplicationContextSet()
					&& Ini.isSwingClient())
			{
				logger.warn("Spring context is not yet started => using an empty ModelCacheInvalidationService instance");

				return new ModelCacheInvalidationService(Optional.empty());
			}

			cacheInvalidationService = this._cacheInvalidationService = ModelCacheInvalidationService.get();
		}
		return cacheInvalidationService;
	}

	public IDocumentNoBuilderFactory documentNoBuilderFactory()
	{
		IDocumentNoBuilderFactory documentNoBuilderFactory = this._documentNoBuilderFactory;
		if (documentNoBuilderFactory == null)
		{
			documentNoBuilderFactory = this._documentNoBuilderFactory = Services.get(IDocumentNoBuilderFactory.class);
		}
		return documentNoBuilderFactory;
	}

	private IDocumentNoBL documentNoBL()
	{
		IDocumentNoBL documentNoBL = this._documentNoBL;
		if (documentNoBL == null)
		{
			documentNoBL = this._documentNoBL = Services.get(IDocumentNoBL.class);
		}
		return documentNoBL;
	}

	public ITrxManager trxManager()
	{
		ITrxManager trxManager = this._trxManager;
		if (trxManager == null)
		{
			trxManager = this._trxManager = Services.get(ITrxManager.class);
		}
		return trxManager;
	}

	private ADReferenceService adReferenceService()
	{
		ADReferenceService adReferenceService = this._adReferenceService;
		if (adReferenceService == null)
		{
			adReferenceService = this._adReferenceService = ADReferenceService.get();
		}
		return adReferenceService;
	}

	public boolean isPerfMonActive()
	{
		return getSysConfigBooleanValue(PM_SYSCONFIG_NAME, PM_SYS_CONFIG_DEFAULT_VALUE);
	}

	public void performanceMonitoringServiceSaveEx(@NonNull final Runnable runnable)
	{
		final PerformanceMonitoringService performanceMonitoringService = performanceMonitoringService();

		performanceMonitoringService.monitor(runnable, PM_METADATA_SAVE_EX);
	}

	public boolean performanceMonitoringServiceLoad(@NonNull final Callable<Boolean> callable)
	{
		final PerformanceMonitoringService performanceMonitoringService = performanceMonitoringService();

		return performanceMonitoringService.monitor(callable, PM_METADATA_LOAD);
	}

	private PerformanceMonitoringService performanceMonitoringService()
	{
		PerformanceMonitoringService performanceMonitoringService = _performanceMonitoringService;
		if (performanceMonitoringService == null || performanceMonitoringService instanceof NoopPerformanceMonitoringService)
		{
			performanceMonitoringService = _performanceMonitoringService = SpringContextHolder.instance.getBeanOr(
					PerformanceMonitoringService.class,
					NoopPerformanceMonitoringService.INSTANCE);
		}
		return performanceMonitoringService;
	}

	//
	//
	//

	public boolean isDeveloperMode()
	{
		return developerModeBL().isEnabled();
	}

	public boolean getSysConfigBooleanValue(final String sysConfigName, final boolean defaultValue, final int ad_client_id, final int ad_org_id)
	{
		return sysConfigBL().getBooleanValue(sysConfigName, defaultValue, ad_client_id, ad_org_id);
	}

	public boolean getSysConfigBooleanValue(final String sysConfigName, final boolean defaultValue)
	{
		return sysConfigBL().getBooleanValue(sysConfigName, defaultValue);
	}

	public boolean isChangeLogEnabled()
	{
		return sessionBL().isChangeLogEnabled();
	}

	public String getInsertChangeLogType(final int adClientId)
	{
		return sysConfigBL().getValue("SYSTEM_INSERT_CHANGELOG", "N", adClientId);
	}

	public void saveChangeLogs(final List<ChangeLogRecord> changeLogRecords)
	{
		sessionDAO().saveChangeLogs(changeLogRecords);
	}

	public void logMigration(final MFSession session, final PO po, final POInfo poInfo, final String actionType)
	{
		migrationLogger().logMigration(session, po, poInfo, actionType);
	}

	public void fireDocumentNoChange(final PO po, final String value)
	{
		documentNoBL().fireDocumentNoChange(po, value); // task 09776
	}

	public ADRefList getRefListById(@NonNull final ReferenceId referenceId)
	{
		return adReferenceService().getRefListById(referenceId);
	}

}
