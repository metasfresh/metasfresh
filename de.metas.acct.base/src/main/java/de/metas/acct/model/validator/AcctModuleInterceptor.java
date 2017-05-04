package de.metas.acct.model.validator;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Date;
import java.util.Properties;

import org.adempiere.acct.api.IFactAcctListenersService;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.dao.cache.IModelCacheService;
import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_C_ConversionType;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_C_PeriodControl;
import org.compiere.model.I_GL_Distribution;
import org.compiere.model.I_GL_DistributionLine;
import org.compiere.model.I_M_Product_Acct;
import org.compiere.model.I_M_Product_Category_Acct;
import org.compiere.model.MAccount;
import org.compiere.util.CacheMgt;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.acct.api.IDocumentBL;
import de.metas.acct.async.ScheduleFactAcctLogProcessingFactAcctListener;
import de.metas.acct.model.I_C_VAT_Code;
import de.metas.acct.spi.impl.AllocationHdrDocumentRepostingHandler;
import de.metas.acct.spi.impl.GLJournalDocumentRepostingHandler;
import de.metas.acct.spi.impl.InvoiceDocumentRepostingHandler;
import de.metas.acct.spi.impl.PaymentDocumentRepostingHandler;
import de.metas.currency.ICurrencyDAO;
import de.metas.logging.LogManager;


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

		final IDocumentBL documentBL = Services.get(IDocumentBL.class);

		//FRESH-539: register Reposting Handlers
		documentBL.registerHandler(new InvoiceDocumentRepostingHandler());
		documentBL.registerHandler(new PaymentDocumentRepostingHandler());
		documentBL.registerHandler(new AllocationHdrDocumentRepostingHandler());
		documentBL.registerHandler(new GLJournalDocumentRepostingHandler());
	}

	@Override
	protected void registerInterceptors(final IModelValidationEngine engine, final I_AD_Client client)
	{
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
	};

	@Override
	protected void registerCallouts(final IProgramaticCalloutProvider calloutsRegistry)
	{
		calloutsRegistry.registerAnnotatedCallout(new de.metas.acct.callout.GL_JournalLine());
	}

	@Override
	protected void setupCaching(IModelCacheService cachingService)
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
	public void onUserLogin(int AD_Org_ID, int AD_Role_ID, int AD_User_ID)
	{
		final Properties ctx = Env.getCtx();
		final int adClientId = Env.getAD_Client_ID(ctx);

		//
		// Set default conversion type to context
		if (adClientId > 0 && adClientId != Env.CTXVALUE_AD_Client_ID_System)
		{
			try
			{
				final Date date = Env.getDate(ctx);
				final I_C_ConversionType conversionType = Services.get(ICurrencyDAO.class).retrieveDefaultConversionType(ctx, adClientId, AD_Org_ID, date);
				Env.setContext(ctx, CTXNAME_C_ConversionType_ID, conversionType.getC_ConversionType_ID());
			}
			catch (Exception e)
			{
				logger.warn("Failed finding the default conversion type. Skip", e);
			}
		}
	}
}
