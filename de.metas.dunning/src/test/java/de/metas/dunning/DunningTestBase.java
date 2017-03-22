package de.metas.dunning;

/*
 * #%L
 * de.metas.dunning
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


import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.ITrxRunConfig;
import org.adempiere.ad.trx.api.ITrxRunConfig.OnRunnableFail;
import org.adempiere.ad.trx.api.ITrxRunConfig.OnRunnableSuccess;
import org.adempiere.ad.trx.api.ITrxRunConfig.TrxPropagation;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.document.service.impl.DummyDocumentLocationBL;
import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.invoice.service.impl.PlainInvoiceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Currency;
import org.compiere.util.Env;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestWatcher;

import de.metas.document.IDocumentLocationBL;
import de.metas.document.engine.IDocActionBL;
import de.metas.document.engine.impl.PlainDocActionBL;
import de.metas.dunning.api.IDunnableDoc;
import de.metas.dunning.api.IDunningBL;
import de.metas.dunning.api.IDunningContext;
import de.metas.dunning.api.IDunningDAO;
import de.metas.dunning.api.IDunningUtil;
import de.metas.dunning.api.impl.DefaultDunningCandidateProducer;
import de.metas.dunning.api.impl.DunningConfig;
import de.metas.dunning.api.impl.MockedDunnableSourceFactory;
import de.metas.dunning.api.impl.PlainDunningContext;
import de.metas.dunning.api.impl.PlainDunningDAO;
import de.metas.dunning.api.impl.PlainDunningUtil;
import de.metas.dunning.interfaces.I_C_Dunning;
import de.metas.dunning.interfaces.I_C_DunningLevel;
import de.metas.dunning.model.I_C_DunningDoc;
import de.metas.dunning.model.I_C_Dunning_Candidate;
import de.metas.dunning.spi.impl.MockedCloseableIterator;
import de.metas.dunning.spi.impl.MockedDunnableSource;

public class DunningTestBase
{
	/** Watches current test and dumps the database to console in case of failure */
	@Rule
	public final TestWatcher testWatcher = new AdempiereTestWatcher();

	@BeforeClass
	public static void staticInit()
	{
		AdempiereTestHelper.get().staticInit();
		POJOWrapper.setDefaultStrictValues(true);
	}

	private Properties ctx;
	protected POJOLookupMap db;
	protected PlainDunningDAO dao;
	protected PlainInvoiceBL invoiceBL;
	protected PlainDunningUtil dunningUtil;

	protected I_C_Currency currencyEUR;
	protected I_C_Currency currencyCHF;
	protected final IDunningBL dunningBL = Services.get(IDunningBL.class);;

	@Before
	public final void beforeTest()
	{
		AdempiereTestHelper.get().init();
		POJOLookupMap.get().setCopyOnSave(true); // FIXME : Adapt dunning tests to new behavior

		dao = new PlainDunningDAO();
		Services.registerService(IDunningDAO.class, dao);

		db = dao.getDB();

		Services.registerService(IDocumentLocationBL.class, new DummyDocumentLocationBL());

		//
		invoiceBL = new PlainInvoiceBL();
		Services.registerService(IInvoiceBL.class, invoiceBL);

		dunningUtil = (PlainDunningUtil)Services.get(IDunningUtil.class);

		final PlainDocActionBL docActionBL = (PlainDocActionBL)Services.get(IDocActionBL.class);
		docActionBL.setDefaultProcessInterceptor(PlainDocActionBL.PROCESSINTERCEPTOR_CompleteDirectly);

		MockedCloseableIterator.clear();

		//
		// Setup context
		ctx = Env.getCtx();
		ctx.clear();
		Env.setContext(ctx, "#AD_Client_ID", 1);
		Env.setContext(ctx, "#AD_Org_ID", 1);
		Env.setContext(ctx, "#AD_Role_ID", 1);
		Env.setContext(ctx, "#AD_User_ID", 1);

		//
		// now set up master data
		currencyEUR = InterfaceWrapperHelper.create(ctx, I_C_Currency.class, ITrx.TRXNAME_None);
		currencyEUR.setISO_Code("EUR");
		currencyEUR.setStdPrecision(2);
		currencyEUR.setIsEuro(true);
		InterfaceWrapperHelper.save(currencyEUR);
		POJOWrapper.enableStrictValues(currencyEUR);

		currencyCHF = InterfaceWrapperHelper.create(ctx, I_C_Currency.class, ITrx.TRXNAME_None);
		currencyCHF.setISO_Code("CHF");
		currencyCHF.setStdPrecision(2);
		currencyCHF.setIsEuro(true);
		InterfaceWrapperHelper.save(currencyCHF);
		POJOWrapper.enableStrictValues(currencyCHF);

		createMasterData();
	}

	protected void createMasterData()
	{
		// nothing; to be implemented by particular test classes
	}

	@After
	public final void assumeAllIteratorsClosed()
	{
		MockedCloseableIterator.assertAllClosed();
		MockedCloseableIterator.clear();
	}

	protected void generateDunningCandidates(final PlainDunningContext dunningContext)
	{
		for (final I_C_Dunning dunning : dao.retrieveDunnings(dunningContext.getCtx()))
		{
			for (final I_C_DunningLevel dunningLevel : dao.retrieveDunningLevels(dunning))
			{
				dunningContext.setDunningLevel(dunningLevel);
				dunningBL.createDunningCandidates(dunningContext);
			}
		}
	}

	/**
	 * Process {@link I_C_Dunning_Candidate}s and generate {@link I_C_DunningDoc}s.
	 *
	 * @param dunningContext
	 */
	protected void processDunningCandidates(final PlainDunningContext dunningContext)
	{
		dunningBL.processCandidates(dunningContext);
	}

	protected void processDunningDocs(final PlainDunningContext dunningContext)
	{
		final List<I_C_DunningDoc> dunningDocs = db.getRecords(I_C_DunningDoc.class);
		for (I_C_DunningDoc dunningDoc : dunningDocs)
		{
			if (dunningDoc.isProcessed())
			{
				continue;
			}
			dunningBL.processDunningDoc(dunningContext, dunningDoc);
		}
	}

	protected Properties getCtx()
	{
		return ctx;
	}

	protected PlainDunningContext createPlainDunningContext()
	{
		final Properties ctx = getCtx();
		final ITrxRunConfig trxRunConfig = Services.get(ITrxManager.class).createTrxRunConfig(TrxPropagation.REQUIRES_NEW, OnRunnableSuccess.COMMIT, OnRunnableFail.ASK_RUNNABLE);
		final PlainDunningContext dunningContext = new PlainDunningContext(ctx, trxRunConfig);
		final DunningConfig config = dunningContext.getDunningConfig();
		config.setDunnableSourceFactory(new MockedDunnableSourceFactory());
		config.getDunningCandidateProducerFactory().registerDunningCandidateProducer(DefaultDunningCandidateProducer.class);
		return dunningContext;
	}

	protected PlainDunningContext createPlainDunningContext(Date dunningDate, I_C_DunningLevel dunningLevel)
	{
		final PlainDunningContext dunningContext = createPlainDunningContext();
		dunningContext.setDunningDate(dunningDate);
		dunningContext.setDunningLevel(dunningLevel);
		return dunningContext;
	}

	/**
	 *
	 * @param dunningDateStr dunningDate in format yyyy-MM-dd
	 * @param dunningLevel
	 * @return
	 */
	protected PlainDunningContext createPlainDunningContext(String dunningDateStr, I_C_DunningLevel dunningLevel)
	{
		final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
		Date dunningDate;
		try
		{
			dunningDate = df.parse(dunningDateStr);
		}
		catch (ParseException e)
		{
			throw new IllegalArgumentException(e);
		}
		return createPlainDunningContext(dunningDate, dunningLevel);
	}

	protected I_C_Dunning createDunning(String name)
	{
		final IDunningContext dunningContext = null;
		final I_C_Dunning dunning = dao.newInstance(dunningContext, I_C_Dunning.class);
		dunning.setName(name);
		dunning.setCreateLevelsSequentially(true);
		dunning.setIsActive(true);
		dunning.setC_Currency_ID(-1); // no currency by default

		dao.save(dunning);
		POJOWrapper.enableStrictValues(dunning);

		return dunning;
	}

	protected I_C_DunningLevel createDunningLevel(I_C_Dunning dunning, int DaysBetweenDunning, int DaysAfterDue, int InterestPercent)
	{
		final IDunningContext dunningContext = null; // not needed for creating an instance

		final I_C_DunningLevel level = dao.newInstance(dunningContext, I_C_DunningLevel.class);
		level.setC_Dunning_ID(dunning.getC_Dunning_ID());
		level.setIsActive(true);
		level.setIsShowAllDue(false);
		level.setIsShowNotDue(false);
		level.setDaysBetweenDunning(DaysBetweenDunning);
		level.setDaysAfterDue(BigDecimal.valueOf(DaysAfterDue));
		level.setInterestPercent(BigDecimal.valueOf(InterestPercent));
		level.setFeeAmt(Env.ZERO);
		level.setIsWriteOff(false);

		dao.save(level);
		POJOWrapper.enableStrictValues(level);

		return level;
	}

	public MockedDunnableSource getMockedDunnableSource(final IDunningContext context)
	{
		final MockedDunnableSourceFactory factory = (MockedDunnableSourceFactory)context.getDunningConfig().getDunnableSourceFactory();
		final MockedDunnableSource source = factory.getSource();
		return source;
	}

	/**
	 * Live list of dunnable documents.
	 *
	 * @param context
	 * @return
	 */
	public List<IDunnableDoc> getLiveDunnableDocList(final IDunningContext context)
	{
		final MockedDunnableSource source = getMockedDunnableSource(context);
		return source.getDunnableDocList();
	}
}
