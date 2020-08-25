package de.metas.handlingunits.materialtracking.spi.impl;

import com.google.common.collect.ImmutableList;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.handlingunits.HUDocumentSelectTestHelper;
import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.attribute.impl.SaveDecoupledHUAttributesDAO;
import de.metas.handlingunits.inout.impl.ReceiptInOutLineHUAssignmentListener;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_AD_SysConfig;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.util.Env;
import org.eevolution.api.CostCollectorType;
import org.eevolution.model.I_PP_Cost_Collector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

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

@ExtendWith(AdempiereTestWatcher.class)
public class PPOrderMInOutLineRetrievalServiceTest
{
	private HUDocumentSelectTestHelper helper;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		helper = new HUDocumentSelectTestHelper()
		{
			@Override
			protected String createAndStartTransaction()
			{
				return ITrx.TRXNAME_None;
			}
		};

		// register this listener to make sure that when the HUs are assigned, then also the ATTR_ReceiptInOutLine_ID HU-Attribute is set
		Services.get(IHUAssignmentBL.class).registerHUAssignmentListener(ReceiptInOutLineHUAssignmentListener.instance);

		//
		// Create an AD_SysConfig for SaveDecoupledHUAttributesDAO.SYSCONFIG_AutoFlushEnabledInitial, to make sure that the HU_Attributes that are set by the ReceiptInOutLineHUAssignmentListener are actually stored.
		final Properties deriveCtx = Env.deriveCtx(Env.getCtx());
		Env.setContext(deriveCtx, Env.CTXNAME_AD_Client_ID, 0);
		Env.setContext(deriveCtx, Env.CTXNAME_AD_Org_ID, 0);
		final I_AD_SysConfig sysConfig = InterfaceWrapperHelper.newInstance(I_AD_SysConfig.class);
		sysConfig.setName(SaveDecoupledHUAttributesDAO.SYSCONFIG_AutoFlushEnabledInitial);
		sysConfig.setValue("Y");
		InterfaceWrapperHelper.save(sysConfig);
	}

	@Test
	public void test()
	{
		final List<I_M_InOutLine> reversedLines;
		final I_M_HU reversedLineTomatoHU;
		final I_M_HU reversedLineSaladHU;

		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		{
			reversedLines = createReceiptInOutLines(IDocument.STATUS_Reversed);
			assertThat(reversedLines.get(0).getM_Product_ID(), is(helper.pTomato.getM_Product_ID()));
			assertThat(reversedLines.get(1).getM_Product_ID(), is(helper.pSalad.getM_Product_ID()));

			reversedLineTomatoHU = createLU(helper.pTomatoProductId, new BigDecimal("20"));
			assertThat(handlingUnitsBL.isTopLevel(reversedLineTomatoHU), is(true));

			reversedLineSaladHU = createLU(helper.pSaladProductId, new BigDecimal("20"));

			createAssignments(
					reversedLines.get(0), // the one with tomato
					reversedLineTomatoHU);

			createAssignments(
					reversedLines.get(1), // the one with salad
					reversedLineSaladHU);
		}

		final List<I_M_InOutLine> completedLines;
		final I_M_HU completedLineTomatoHU;
		final I_M_HU completedLineSaladHU;
		{
			completedLines = createReceiptInOutLines(IDocument.STATUS_Completed);
			assertThat(completedLines.get(0).getM_Product_ID(), is(helper.pTomatoProductId.getRepoId()));
			assertThat(completedLines.get(1).getM_Product_ID(), is(helper.pSaladProductId.getRepoId()));

			completedLineTomatoHU = createLU(helper.pTomatoProductId, new BigDecimal("30"));
			assertThat(handlingUnitsBL.isTopLevel(completedLineTomatoHU), is(true));

			completedLineSaladHU = createLU(helper.pSaladProductId, new BigDecimal("30"));
			assertThat(handlingUnitsBL.isTopLevel(completedLineSaladHU), is(true));

			createAssignments(
					completedLines.get(0), // the one with tomato
					completedLineTomatoHU);

			createAssignments(
					completedLines.get(1), // the one with salad
					completedLineSaladHU);
		}

		final I_PP_Cost_Collector issueCostCollectorTomato;
		{
			issueCostCollectorTomato = createCostCollector(CostCollectorType.ComponentIssue, helper.pTomatoProductId);

			createAssignments(
					issueCostCollectorTomato,
					reversedLineTomatoHU);

			createAssignments(
					issueCostCollectorTomato,
					completedLineTomatoHU);
		}

		final I_PP_Cost_Collector issueCostCollectorSalad;
		{
			issueCostCollectorSalad = createCostCollector(CostCollectorType.ComponentIssue, helper.pSaladProductId);

			createAssignments(
					issueCostCollectorSalad,
					reversedLineSaladHU);

			createAssignments(
					issueCostCollectorSalad,
					completedLineSaladHU);
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
			final I_M_HU luHU)
	{
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		final IHUAssignmentBL huAssignmentBL = Services.get(IHUAssignmentBL.class);
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		assertThat(handlingUnitsBL.isTopLevel(luHU), is(true));
		assertThat(handlingUnitsBL.isLoadingUnit(luHU), is(true));

		final List<I_M_HU> tuHUs = handlingUnitsDAO.retrieveIncludedHUs(luHU);

		huAssignmentBL.assignHUs(model, ImmutableList.of(luHU));

		for (final I_M_HU tuHU : tuHUs)
		{
			huAssignmentBL.createTradingUnitDerivedAssignmentBuilder(
					Env.getCtx(),
					model,
					luHU,
					luHU,
					tuHU,
					ITrx.TRXNAME_ThreadInherited)
					.build();
		}
	}

	private I_M_HU createLU(final ProductId productId, final BigDecimal qty)
	{
		List<I_M_HU> luHUs = helper.createHUs(helper.getHUContext(), helper.huDefPalet2, productId, qty, helper.uomKg);
		return CollectionUtils.singleElement(luHUs);
	}

	/**
	 * Creates an <code>M_InOut</code> and two lines. The inout has the given doc status.
	 *
	 * @param docStatus
	 * @return
	 */
	private List<I_M_InOutLine> createReceiptInOutLines(final String docStatus)
	{
		final I_M_InOut io = InterfaceWrapperHelper.newInstance(I_M_InOut.class);
		io.setDocStatus(docStatus);
		io.setM_Warehouse_ID(helper.defaultWarehouse.getM_Warehouse_ID());

		final IDocumentBL docActionBL = Services.get(IDocumentBL.class);
		if (!docActionBL.issDocumentDraftedOrInProgress(io))
		{
			io.setProcessed(true); // important, since the code under test might also check for this flag
		}

		InterfaceWrapperHelper.save(io);

		final I_M_InOutLine iol1 = InterfaceWrapperHelper.newInstance(I_M_InOutLine.class);
		iol1.setM_InOut(io);
		iol1.setLine(10);
		iol1.setM_Product_ID(helper.pTomato.getM_Product_ID());
		InterfaceWrapperHelper.save(iol1);

		final I_M_InOutLine iol2 = InterfaceWrapperHelper.newInstance(I_M_InOutLine.class);
		iol2.setM_InOut(io);
		iol2.setLine(20);
		iol2.setM_Product_ID(helper.pSalad.getM_Product_ID());
		InterfaceWrapperHelper.save(iol2);

		return ImmutableList.<I_M_InOutLine> of(iol1, iol2);
	}

	private I_PP_Cost_Collector createCostCollector(final CostCollectorType costCollectorType, final ProductId productId)
	{
		final I_PP_Cost_Collector cc = newInstance(I_PP_Cost_Collector.class);
		cc.setCostCollectorType(costCollectorType.getCode());
		cc.setM_Product_ID(productId.getRepoId());
		saveRecord(cc);

		return cc;
	}
}
