package de.metas.acct.model.validator;

import java.time.LocalDate;
import java.util.Properties;

import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.service.ClientId;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_C_ConversionType;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_C_PeriodControl;
import org.compiere.model.I_GL_Distribution;
import org.compiere.model.I_GL_DistributionLine;
import org.compiere.model.I_I_ElementValue;
import org.compiere.model.I_M_Product_Acct;
import org.compiere.model.I_M_Product_Category_Acct;
import org.compiere.model.MAccount;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.acct.aggregation.async.ScheduleFactAcctLogProcessingFactAcctListener;
import de.metas.acct.api.IFactAcctListenersService;
import de.metas.acct.api.IPostingService;
import de.metas.acct.api.IProductAcctDAO;
import de.metas.acct.impexp.AccountImportProcess;
import de.metas.acct.model.I_C_VAT_Code;
import de.metas.acct.posting.IDocumentRepostingSupplierService;
import de.metas.acct.spi.impl.AllocationHdrDocumentRepostingSupplier;
import de.metas.acct.spi.impl.GLJournalDocumentRepostingSupplier;
import de.metas.acct.spi.impl.InvoiceDocumentRepostingSupplier;
import de.metas.acct.spi.impl.PaymentDocumentRepostingSupplier;
import de.metas.cache.CacheMgt;
import de.metas.cache.model.IModelCacheService;
import de.metas.currency.ICurrencyDAO;
import de.metas.impexp.processing.IImportProcessFactory;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.organization.OrgId;
import de.metas.product.IProductActivityProvider;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.util.Services;

/**
 * Accounting module activator
 *
 * @author tsa
 *
 */
public class AcctModuleInterceptor extends AbstractModuleInterceptor
{
	private static final transient Logger logger = LogManager.getLogger(AcctModuleInterceptor.class);

	private static final String CTXNAME_C_ConversionType_ID = "#" + I_C_ConversionType.COLUMNNAME_C_ConversionType_ID;

	@Override
	protected void onAfterInit()
	{
		Services.get(IFactAcctListenersService.class).registerListener(ScheduleFactAcctLogProcessingFactAcctListener.instance);

		final IDocumentRepostingSupplierService documentBL = Services.get(IDocumentRepostingSupplierService.class);

		// FRESH-539: register Reposting Handlers
		documentBL.registerSupplier(new InvoiceDocumentRepostingSupplier());
		documentBL.registerSupplier(new PaymentDocumentRepostingSupplier());
		documentBL.registerSupplier(new AllocationHdrDocumentRepostingSupplier());
		documentBL.registerSupplier(new GLJournalDocumentRepostingSupplier());

		if (Services.get(IPostingService.class).isEnabled())
		{
			Services.get(IUserRolePermissionsDAO.class).setAccountingModuleActive();
		}

		Services.registerService(IProductActivityProvider.class, Services.get(IProductAcctDAO.class));
		
		Services.get(IImportProcessFactory.class).registerImportProcess(I_I_ElementValue.class, AccountImportProcess.class);
	}

	@Override
	protected void registerInterceptors(final IModelValidationEngine engine, final I_AD_Client client)
	{
		// engine.addModelValidator(new de.metas.acct.model.validator.C_AcctSchema(), client); // spring component
		engine.addModelValidator(new de.metas.acct.model.validator.C_AcctSchema_GL(), client);
		engine.addModelValidator(new de.metas.acct.model.validator.C_AcctSchema_Default(), client);
		engine.addModelValidator(new de.metas.acct.model.validator.C_AcctSchema_Element(), client);

		engine.addModelValidator(new de.metas.acct.model.validator.C_BP_BankAccount(), client); // 08354
		engine.addModelValidator(new de.metas.acct.model.validator.C_ElementValue(), client);
		engine.addModelValidator(new de.metas.acct.model.validator.C_ValidCombination(), client);
		engine.addModelValidator(new de.metas.acct.model.validator.GL_Journal(), client);
		engine.addModelValidator(new de.metas.acct.model.validator.GL_JournalLine(), client);
		engine.addModelValidator(new de.metas.acct.model.validator.GL_JournalBatch(), client);
		//
		engine.addModelValidator(new de.metas.acct.model.validator.C_TaxDeclaration(), client);
		//
		engine.addModelValidator(new de.metas.acct.model.validator.M_MatchInv(), client);
		//
		engine.addModelValidator(new de.metas.acct.model.validator.GL_Distribution(), client);
		engine.addModelValidator(new de.metas.acct.model.validator.GL_DistributionLine(), client);
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
		if (adClientId != null && adClientId.isRegular())
		{
			try
			{
				final OrgId adOrgId = OrgId.ofRepoId(adOrgRepoId);
				final LocalDate date = Env.getLocalDate(ctx);
				final ICurrencyDAO currenciesRepo = Services.get(ICurrencyDAO.class);
				final CurrencyConversionTypeId conversionTypeId = currenciesRepo.getDefaultConversionTypeId(adClientId, adOrgId, date);
				Env.setContext(ctx, CTXNAME_C_ConversionType_ID, conversionTypeId.getRepoId());
			}
			catch (Exception e)
			{
				logger.warn("Failed finding the default conversion type. Skip", e);
			}
		}
	}
}
