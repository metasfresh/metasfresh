package de.metas.handlingunits.materialtracking.spi.impl;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IMutable;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_AD_SysConfig;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Product;
import org.compiere.process.DocAction;
import org.compiere.util.Env;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.X_PP_Cost_Collector;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

import com.google.common.collect.ImmutableList;

import de.metas.document.engine.IDocActionBL;
import de.metas.handlingunits.HUDocumentSelectTestHelper;
import de.metas.handlingunits.HUIteratorListenerAdapter;
import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.attribute.impl.SaveDecoupledHUAttributesDAO;
import de.metas.handlingunits.impl.HUIterator;
import de.metas.handlingunits.inout.impl.ReceiptInOutLineHUAssignmentListener;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.util.HUTopLevel;

/*
 * #%L
 * de.metas.handlingunits.base
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

public class PPOrderMInOutLineRetrievalServiceTest
{
	private IContextAware context;

	private HUDocumentSelectTestHelper helper;

	/** Watches current test and dumps the database to console in case of failure */
	@Rule
	public final TestWatcher testWatcher = new AdempiereTestWatcher();

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		helper = new HUDocumentSelectTestHelper();

		// note that the helper's constructor also sets up the ctx
		context = new PlainContextAware(Env.getCtx(), ITrx.TRXNAME_None);

		// register this listener to make sure that when the HUs are assigned, then also the ATTR_ReceiptInOutLine_ID HU-Attribute is set
		Services.get(IHUAssignmentBL.class).registerHUAssignmentListener(ReceiptInOutLineHUAssignmentListener.instance);

		//
		// Create an AD_SysConfig for SaveDecoupledHUAttributesDAO.SYSCONFIG_AutoFlushEnabledInitial, to make sure that the HU_Attributes that are set by the ReceiptInOutLineHUAssignmentListener are actually stored.
		final Properties deriveCtx = Env.deriveCtx(context.getCtx());
		Env.setContext(deriveCtx, Env.CTXNAME_AD_Client_ID, 0);
		Env.setContext(deriveCtx, Env.CTXNAME_AD_Org_ID, 0);
		final I_AD_SysConfig sysConfig = InterfaceWrapperHelper.newInstance(I_AD_SysConfig.class, new PlainContextAware(deriveCtx, ITrx.TRXNAME_None));
		sysConfig.setName(SaveDecoupledHUAttributesDAO.SYSCONFIG_AutoFlushEnabledInitial);
		sysConfig.setValue("Y");
		InterfaceWrapperHelper.save(sysConfig);
	}

	@Test
	public void test()
	{
		final List<I_M_InOutLine> reversedLines;
		final List<I_M_HU> reversedLineTomatoHUs;
		final List<I_M_HU> reversedLineSaladHUs;

		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		{
			reversedLines = createReceiptInOutLines(DocAction.STATUS_Reversed);
			assertThat(reversedLines.get(0).getM_Product(), is(helper.pTomato));
			assertThat(reversedLines.get(1).getM_Product(), is(helper.pSalad));

			reversedLineTomatoHUs = createHUs(helper.pTomato, new BigDecimal("20"));
			assertThat(handlingUnitsBL.isTopLevel(reversedLineTomatoHUs.get(0)), is(true));

			reversedLineSaladHUs = createHUs(helper.pSalad, new BigDecimal("20"));

			createAssignments(
					reversedLines.get(0), // the one with tomato
					reversedLineTomatoHUs);

			createAssignments(
					reversedLines.get(1), // the one with salad
					reversedLineSaladHUs);
		}

		final List<I_M_InOutLine> completedLines;
		final List<I_M_HU> completedLineTomatoHUs;
		final List<I_M_HU> completedLineSaladHUs;
		{
			completedLines = createReceiptInOutLines(DocAction.STATUS_Completed);
			assertThat(completedLines.get(0).getM_Product(), is(helper.pTomato));
			assertThat(completedLines.get(1).getM_Product(), is(helper.pSalad));

			completedLineTomatoHUs = createHUs(helper.pTomato, new BigDecimal("30"));
			assertThat(handlingUnitsBL.isTopLevel(completedLineTomatoHUs.get(0)), is(true));

			completedLineSaladHUs = createHUs(helper.pSalad, new BigDecimal("30"));
			assertThat(handlingUnitsBL.isTopLevel(completedLineSaladHUs.get(0)), is(true));

			createAssignments(
					completedLines.get(0), // the one with product1
					completedLineTomatoHUs);

			createAssignments(
					completedLines.get(1), // the one with salad
					completedLineSaladHUs);
		}

		final I_PP_Cost_Collector issueCostCollectorTomato;
		{
			issueCostCollectorTomato = createCostCollector(X_PP_Cost_Collector.COSTCOLLECTORTYPE_ComponentIssue, helper.pTomato);

			createAssignments(
					issueCostCollectorTomato,
					reversedLineTomatoHUs);

			createAssignments(
					issueCostCollectorTomato,
					completedLineTomatoHUs);
		}

		final I_PP_Cost_Collector issueCostCollectorSalad;
		{
			issueCostCollectorSalad = createCostCollector(X_PP_Cost_Collector.COSTCOLLECTORTYPE_ComponentIssue, helper.pSalad);

			createAssignments(
					issueCostCollectorSalad,
					reversedLineSaladHUs);

			createAssignments(
					issueCostCollectorSalad,
					completedLineSaladHUs);
		}

		final List<de.metas.materialtracking.model.I_M_InOutLine> provideIssuedInOutLinesTomato = new PPOrderMInOutLineRetrievalService().provideIssuedInOutLines(issueCostCollectorTomato);
		assertThat(provideIssuedInOutLinesTomato.size(), is(1));
		assertThat(provideIssuedInOutLinesTomato.get(0), is(completedLines.get(0))); // expecting the completed tomato line

		final List<de.metas.materialtracking.model.I_M_InOutLine> provideIssuedInOutLinesSalad = new PPOrderMInOutLineRetrievalService().provideIssuedInOutLines(issueCostCollectorSalad);
		assertThat(provideIssuedInOutLinesSalad.size(), is(1));
		assertThat(provideIssuedInOutLinesSalad.get(0), is(completedLines.get(1))); // expecting the completed salad line

	}

	private void createAssignments(
			final Object model,
			final List<I_M_HU> topLevelTuSingletonList)
	{
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		final IHUAssignmentBL huAssignmentBL = Services.get(IHUAssignmentBL.class);

		final I_M_HU luHU = topLevelTuSingletonList.get(0);
		assertThat(handlingUnitsBL.isTopLevel(luHU), is(true));
		assertThat(handlingUnitsBL.isLoadingUnit(luHU), is(true));

		final List<HUTopLevel> toTopLevels = new ArrayList<HUTopLevel>();

		final HUIterator huIterator = new HUIterator();
		huIterator.setDate(SystemTime.asTimestamp());
		huIterator.setListener(new HUIteratorListenerAdapter()
		{
			@Override
			public Result beforeHU(final IMutable<I_M_HU> hu)
			{
				if (handlingUnitsBL.isTransportUnit(hu.getValue()))
				{
					toTopLevels.add(new HUTopLevel(luHU, luHU, hu.getValue(), null));
				}
				return Result.CONTINUE;
			}
		});
		huIterator.iterate(luHU);

		huAssignmentBL.assignHUs(
				model,
				topLevelTuSingletonList);

		for (final HUTopLevel huTopLevel : toTopLevels)
		{
			huAssignmentBL.createTradingUnitDerivedAssignmentBuilder(
					context.getCtx(),
					model,
					huTopLevel.getM_HU_TopLevel(),
					huTopLevel.getM_LU_HU(),
					huTopLevel.getM_TU_HU(),
					context.getTrxName())
					.build();
		}
	}

	private List<I_M_HU> createHUs(final I_M_Product product, final BigDecimal qty)
	{
		return helper.createHUs(helper.getHUContext(), helper.huDefPalet2, product, qty, helper.uomKg);
	}

	/**
	 * Creates an <code>M_InOut</code> and two lines. The inout has the given doc status.
	 *
	 * @param docStatus
	 * @return
	 */
	private List<I_M_InOutLine> createReceiptInOutLines(final String docStatus)
	{
		final I_M_InOut io = InterfaceWrapperHelper.newInstance(I_M_InOut.class, context);
		io.setDocStatus(docStatus);

		final IDocActionBL docActionBL = Services.get(IDocActionBL.class);
		if (!docActionBL.isStatusDraftedOrInProgress(io))
		{
			io.setProcessed(true); // important, since the code under test might also check for this flag
		}

		InterfaceWrapperHelper.save(io);

		final I_M_InOutLine iol1 = InterfaceWrapperHelper.newInstance(I_M_InOutLine.class, context);
		iol1.setM_InOut(io);
		iol1.setLine(10);
		iol1.setM_Product(helper.pTomato);
		InterfaceWrapperHelper.save(iol1);

		final I_M_InOutLine iol2 = InterfaceWrapperHelper.newInstance(I_M_InOutLine.class, context);
		iol2.setM_InOut(io);
		iol2.setLine(20);
		iol2.setM_Product(helper.pSalad);
		InterfaceWrapperHelper.save(iol2);

		return ImmutableList.<I_M_InOutLine> of(iol1, iol2);
	}

	private I_PP_Cost_Collector createCostCollector(final String costCollectorType, final I_M_Product product)
	{
		final I_PP_Cost_Collector cc = InterfaceWrapperHelper.newInstance(I_PP_Cost_Collector.class, context);
		cc.setCostCollectorType(costCollectorType);
		cc.setM_Product(product);
		InterfaceWrapperHelper.save(cc);

		return cc;
	}
}
