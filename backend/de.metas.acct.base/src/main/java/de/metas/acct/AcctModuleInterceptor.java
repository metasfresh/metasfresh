package de.metas.acct;

import de.metas.Profiles;
import de.metas.acct.aggregation.FactAcctLogDBTableWatcher;
import de.metas.acct.aggregation.FactAcctLogService;
import de.metas.acct.api.IAccountBL;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.acct.api.IFactAcctDAO;
import de.metas.acct.api.IPostingService;
import de.metas.acct.api.IProductAcctDAO;
import de.metas.acct.impexp.AccountImportProcess;
import de.metas.acct.model.I_C_VAT_Code;
import de.metas.acct.model.I_Fact_Acct_EndingBalance;
import de.metas.acct.model.I_Fact_Acct_Log;
import de.metas.acct.model.I_Fact_Acct_Summary;
import de.metas.acct.posting.IDocumentRepostingSupplierService;
import de.metas.acct.posting.server.accouting_docs_to_repost_db_table.AccoutingDocsToRepostDBTableWatcher;
import de.metas.acct.spi.impl.AllocationHdrDocumentRepostingSupplier;
import de.metas.acct.spi.impl.GLJournalDocumentRepostingSupplier;
import de.metas.acct.spi.impl.InvoiceDocumentRepostingSupplier;
import de.metas.acct.spi.impl.PaymentDocumentRepostingSupplier;
import de.metas.cache.CacheMgt;
import de.metas.cache.model.IModelCacheService;
import de.metas.costing.ICostElementRepository;
import de.metas.currency.ICurrencyDAO;
import de.metas.elementvalue.MElementValueTreeSupport;
import de.metas.impexp.processing.IImportProcessFactory;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.organization.OrgId;
import de.metas.product.IProductActivityProvider;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.treenode.TreeNodeService;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.migration.logger.IMigrationLogger;
import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.model.tree.IPOTreeSupportFactory;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_C_ConversionType;
import org.compiere.model.I_C_ElementValue;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_C_PeriodControl;
import org.compiere.model.I_Fact_Acct;
import org.compiere.model.I_GL_Distribution;
import org.compiere.model.I_GL_DistributionLine;
import org.compiere.model.I_I_ElementValue;
import org.compiere.model.I_M_Product_Acct;
import org.compiere.model.I_M_Product_Category_Acct;
import org.compiere.model.MAccount;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Properties;

/**
 * Accounting module activator
 */
@Component
public class AcctModuleInterceptor extends AbstractModuleInterceptor
{
	private static final Logger logger = LogManager.getLogger(AcctModuleInterceptor.class);
	private final IPostingService postingService = Services.get(IPostingService.class);
	private final IFactAcctDAO factAcctDAO = Services.get(IFactAcctDAO.class);
	private final IDocumentRepostingSupplierService documentBL = Services.get(IDocumentRepostingSupplierService.class);
	private final IImportProcessFactory importProcessFactory = Services.get(IImportProcessFactory.class);
	private final IUserRolePermissionsDAO userRolePermissionsDAO = Services.get(IUserRolePermissionsDAO.class);
	private final ICurrencyDAO currenciesRepo = Services.get(ICurrencyDAO.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final IAcctSchemaDAO acctSchemaDAO = Services.get(IAcctSchemaDAO.class);
	private final IAccountBL accountBL = Services.get(IAccountBL.class);
	private final FactAcctLogService factAcctLogService;

	private final ICostElementRepository costElementRepo;
	private final TreeNodeService treeNodeService;

	private static final String CTXNAME_C_ConversionType_ID = "#" + I_C_ConversionType.COLUMNNAME_C_ConversionType_ID;

	public AcctModuleInterceptor(
			@NonNull final ICostElementRepository costElementRepo,
			@NonNull final TreeNodeService treeNodeService,
			@NonNull final FactAcctLogService factAcctLogService)
	{
		this.costElementRepo = costElementRepo;
		this.treeNodeService = treeNodeService;
		this.factAcctLogService = factAcctLogService;
	}

	@Override
	protected void onAfterInit()
	{
		// FRESH-539: register Reposting Handlers
		documentBL.registerSupplier(new InvoiceDocumentRepostingSupplier());
		documentBL.registerSupplier(new PaymentDocumentRepostingSupplier());
		documentBL.registerSupplier(new AllocationHdrDocumentRepostingSupplier());
		documentBL.registerSupplier(new GLJournalDocumentRepostingSupplier());

		if (postingService.isEnabled())
		{
			userRolePermissionsDAO.setAccountingModuleActive();
		}

		Services.registerService(IProductActivityProvider.class, Services.get(IProductAcctDAO.class));

		importProcessFactory.registerImportProcess(I_I_ElementValue.class, AccountImportProcess.class);

		final IPOTreeSupportFactory treeSupportFactory = Services.get(IPOTreeSupportFactory.class);
		treeSupportFactory.register(I_C_ElementValue.Table_Name, MElementValueTreeSupport.class);

		//
		// Accounting service
		if (Profiles.isProfileActive(Profiles.PROFILE_AccountingService))
		{
			setupAccountingService();
		}
		else
		{
			logger.info("Skip setting up accounting service because profile {} is not active", Profiles.PROFILE_AccountingService);
		}

		final IMigrationLogger migrationLogger = Services.get(IMigrationLogger.class);
		migrationLogger.addTableToIgnoreList(I_Fact_Acct.Table_Name);
		migrationLogger.addTableToIgnoreList(I_Fact_Acct_Log.Table_Name);
		migrationLogger.addTableToIgnoreList(I_Fact_Acct_Summary.Table_Name);
		migrationLogger.addTableToIgnoreList(I_Fact_Acct_EndingBalance.Table_Name);
		migrationLogger.addTableToIgnoreList(I_I_ElementValue.Table_Name);
	}

	@Override
	protected void registerInterceptors(final IModelValidationEngine engine)
	{
		engine.addModelValidator(new de.metas.acct.model.validator.C_AcctSchema(acctSchemaDAO, costElementRepo));
		engine.addModelValidator(new de.metas.acct.model.validator.C_AcctSchema_GL());
		engine.addModelValidator(new de.metas.acct.model.validator.C_AcctSchema_Default());
		engine.addModelValidator(new de.metas.acct.model.validator.C_AcctSchema_Element());

		engine.addModelValidator(new de.metas.acct.model.validator.C_BP_BankAccount()); // 08354
		engine.addModelValidator(new de.metas.acct.model.validator.C_ElementValue(acctSchemaDAO, treeNodeService));
		engine.addModelValidator(new de.metas.acct.model.validator.C_ValidCombination(accountBL));

		engine.addModelValidator(new de.metas.acct.model.validator.GL_Journal(importProcessFactory));
		engine.addModelValidator(new de.metas.acct.interceptor.GL_JournalLine());
		engine.addModelValidator(new de.metas.acct.model.validator.GL_JournalBatch());
		//
		engine.addModelValidator(new de.metas.acct.model.validator.C_TaxDeclaration());
		//
		engine.addModelValidator(new de.metas.acct.model.validator.M_MatchInv(postingService, factAcctDAO));
		//
		engine.addModelValidator(new de.metas.acct.model.validator.GL_Distribution());
		engine.addModelValidator(new de.metas.acct.model.validator.GL_DistributionLine());
	}

	@Override
	protected void registerCallouts(final IProgramaticCalloutProvider calloutsRegistry)
	{
		calloutsRegistry.registerAnnotatedCallout(new de.metas.acct.callout.GL_JournalBatch());
		calloutsRegistry.registerAnnotatedCallout(new de.metas.acct.callout.GL_Journal());
		calloutsRegistry.registerAnnotatedCallout(new de.metas.acct.callout.GL_JournalLine());
	}

	@Override
	protected void setupCaching(final IModelCacheService cachingService)
	{
		cachingService.addTableCacheConfigIfAbsent(I_C_AcctSchema.class);

		final CacheMgt cacheMgt = CacheMgt.get();
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_C_Period.Table_Name);
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_C_PeriodControl.Table_Name);

		cacheMgt.enableRemoteCacheInvalidationForTableName(MAccount.Table_Name);
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_M_Product_Acct.Table_Name);
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_M_Product_Category_Acct.Table_Name);

		// GL Distribution: changes performed by Admin (on client) shall be visible to accounting engine (on server).
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_GL_Distribution.Table_Name);
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_GL_DistributionLine.Table_Name);
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_C_VAT_Code.Table_Name);
	}

	@Override
	public void onUserLogin(final int adOrgRepoId, final int AD_Role_ID, final int AD_User_ID)
	{
		final Properties ctx = Env.getCtx();
		final ClientId adClientId = Env.getClientId(ctx);

		//
		// Set default conversion type to context
		if (adClientId.isRegular())
		{
			try
			{
				final OrgId adOrgId = OrgId.ofRepoId(adOrgRepoId);
				final LocalDate date = Env.getLocalDate(ctx);
				final CurrencyConversionTypeId conversionTypeId = currenciesRepo.getDefaultConversionTypeId(adClientId, adOrgId, date);
				Env.setContext(ctx, CTXNAME_C_ConversionType_ID, conversionTypeId.getRepoId());
			}
			catch (final Exception e)
			{
				logger.warn("Failed finding the default conversion type. Skip", e);
			}
		}
	}

	private void setupAccountingService()
	{
		runInThread(AccoutingDocsToRepostDBTableWatcher.builder()
				.sysConfigBL(sysConfigBL)
				.postingService(postingService)
				.build());

		runInThread(FactAcctLogDBTableWatcher.builder()
				.sysConfigBL(sysConfigBL)
				.factAcctLogService(factAcctLogService)
				.build());
	}

	private void runInThread(@NonNull final Runnable watcher)
	{
		final Thread thread = new Thread(watcher);
		thread.setDaemon(true);
		thread.setName("accounting-" + watcher.getClass().getSimpleName());
		thread.start();
		logger.info("Started {}", thread);
	}
}
