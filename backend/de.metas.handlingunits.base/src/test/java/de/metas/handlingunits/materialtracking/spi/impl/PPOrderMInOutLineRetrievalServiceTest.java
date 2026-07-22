package de.metas.handlingunits.materialtracking.spi.impl;

import com.google.common.collect.ImmutableList;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.handlingunits.HUDocumentSelectTestHelper;
import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.attribute.HUAndPIAttributes;
import de.metas.handlingunits.attribute.HUAttributeConstants;
import de.metas.handlingunits.attribute.impl.HUAttributesDAO;
import de.metas.handlingunits.attribute.impl.SaveDecoupledHUAttributesDAO;
import de.metas.handlingunits.inout.impl.ReceiptInOutLineHUAssignmentListener;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
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
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


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
		final I_AD_SysConfig sysConfig = newInstance(I_AD_SysConfig.class);
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
			assertThat(reversedLines.get(0).getM_Product_ID()).isEqualTo(helper.pTomato.getM_Product_ID());
			assertThat(reversedLines.get(1).getM_Product_ID()).isEqualTo(helper.pSalad.getM_Product_ID());

			reversedLineTomatoHU = createLU(helper.pTomatoProductId, new BigDecimal("20"));
			assertThat(handlingUnitsBL.isTopLevel(reversedLineTomatoHU)).isEqualTo(true);

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
			assertThat(completedLines.get(0).getM_Product_ID()).isEqualTo(helper.pTomatoProductId.getRepoId());
			assertThat(completedLines.get(1).getM_Product_ID()).isEqualTo(helper.pSaladProductId.getRepoId());

			completedLineTomatoHU = createLU(helper.pTomatoProductId, new BigDecimal("30"));
			assertThat(handlingUnitsBL.isTopLevel(completedLineTomatoHU)).isTrue();

			completedLineSaladHU = createLU(helper.pSaladProductId, new BigDecimal("30"));
			assertThat(handlingUnitsBL.isTopLevel(completedLineSaladHU)).isTrue();

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
		assertThat(provideIssuedInOutLinesTomato).hasSize(1);
		assertThat(provideIssuedInOutLinesTomato.get(0)).isEqualTo(completedLines.get(0)); // expecting the completed tomato line

		final List<de.metas.materialtracking.model.I_M_InOutLine> provideIssuedInOutLinesSalad = new PPOrderMInOutLineRetrievalService().provideIssuedInOutLines(issueCostCollectorSalad);
		assertThat(provideIssuedInOutLinesSalad).hasSize(1);
		assertThat(provideIssuedInOutLinesSalad.get(0)).isEqualTo(completedLines.get(1)); // expecting the completed salad line

	}

	/**
	 * A destroyed HU's attribute rows may all be deactivated (IsActive='N') by the HU-attribute
	 * archival job. The HU-attribute read-path must still return those archived rows for a destroyed
	 * HU, otherwise its HU_ReceiptInOutLine_ID link is unreadable and quality invoicing finds no
	 * receipt (no invoice candidates).
	 * <p>
	 * Asserted at the DAO read methods with a COLD read (HUAttributesDAO.instance, no cache): the
	 * SaveDecoupledHUAttributesDAO runtime wrapper warms a sticky per-HU read cache on assignment that
	 * flush() never evicts, so an end-to-end read through it cannot observe a later DB-level
	 * deactivation in this in-memory harness (the definitive runtime confirmation is a live
	 * re-invoice on the running stack). Both fixed read methods are exercised here:
	 * {@code retrieveAttributesOrdered} (the runtime whole-HU load) and {@code retrieveAttribute} ->
	 * {@code retrieveAttributes} (the point lookup).
	 */
	@Test
	public void test_destroyedHU_deactivatedReceiptAttribute_readReturnsArchivedRow()
	{
		final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

		final List<I_M_InOutLine> completedLines = createReceiptInOutLines(IDocument.STATUS_Completed);
		final I_M_InOutLine completedTomatoLine = completedLines.get(0);
		final I_M_HU tomatoHU = createLU(helper.pTomatoProductId, new BigDecimal("30"));

		// assigning the HU to the receipt line sets its HU_ReceiptInOutLine_ID attribute (via the listener)
		createAssignments(completedTomatoLine, tomatoHU);

		final AttributeId receiptAttrId = attributeDAO.getAttributeIdByCode(HUAttributeConstants.ATTR_ReceiptInOutLine_ID);

		// sanity: while the HU is active, the attribute is set and readable
		final I_M_HU_Attribute activeRead = HUAttributesDAO.instance.retrieveAttribute(tomatoHU, receiptAttrId);
		assertThat(activeRead).isNotNull();
		assertThat(activeRead.getValueNumber().intValue()).isEqualTo(completedTomatoLine.getM_InOutLine_ID());

		// simulate the archival job: HU destroyed + its HU_ReceiptInOutLine_ID attribute row deactivated
		tomatoHU.setHUStatus(X_M_HU.HUSTATUS_Destroyed);
		InterfaceWrapperHelper.save(tomatoHU);
		deactivateHuAttribute(tomatoHU, receiptAttrId);

		// point-lookup path (retrieveAttribute -> retrieveAttributes)
		final I_M_HU_Attribute pointLookup = HUAttributesDAO.instance.retrieveAttribute(tomatoHU, receiptAttrId);
		assertThat(pointLookup)
				.as("retrieveAttribute must return the archived HU_ReceiptInOutLine_ID for a destroyed HU")
				.isNotNull();
		assertThat(pointLookup.getValueNumber().intValue()).isEqualTo(completedTomatoLine.getM_InOutLine_ID());

		// runtime whole-HU load path (retrieveAttributesOrdered)
		final HUAndPIAttributes ordered = HUAttributesDAO.instance.retrieveAttributesOrdered(tomatoHU);
		assertThat(ordered.getHuAttributes())
				.as("retrieveAttributesOrdered must include the archived HU_ReceiptInOutLine_ID for a destroyed HU")
				.anyMatch(attr -> attr.getM_Attribute_ID() == receiptAttrId.getRepoId());
	}

	/**
	 * For a NON-destroyed (live) HU the active-records filter is preserved, so a deactivated attribute
	 * stays invisible (the archival performance win is not given back for live HUs).
	 */
	@Test
	public void test_activeHU_deactivatedReceiptAttribute_stillFilteredOut()
	{
		final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

		final List<I_M_InOutLine> completedLines = createReceiptInOutLines(IDocument.STATUS_Completed);
		final I_M_HU tomatoHU = createLU(helper.pTomatoProductId, new BigDecimal("30"));
		createAssignments(completedLines.get(0), tomatoHU);

		final AttributeId receiptAttrId = attributeDAO.getAttributeIdByCode(HUAttributeConstants.ATTR_ReceiptInOutLine_ID);

		// HU stays active (assignment leaves it HUSTATUS_Active); deactivate the attribute row only
		deactivateHuAttribute(tomatoHU, receiptAttrId);

		assertThat(HUAttributesDAO.instance.retrieveAttribute(tomatoHU, receiptAttrId))
				.as("a deactivated attribute must stay invisible for a live (non-destroyed) HU")
				.isNull();
	}

	/**
	 * Deactivates the given HU's attribute row directly in the DB (as the archival job does), loading a
	 * fresh instance via the query layer so the SaveDecoupled read cache is bypassed.
	 */
	private void deactivateHuAttribute(final I_M_HU hu, final AttributeId attributeId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final I_M_HU_Attribute attr = queryBL.createQueryBuilder(I_M_HU_Attribute.class)
				.addEqualsFilter(I_M_HU_Attribute.COLUMNNAME_M_HU_ID, hu.getM_HU_ID())
				.addEqualsFilter(I_M_HU_Attribute.COLUMNNAME_M_Attribute_ID, attributeId)
				.create()
				.firstOnlyNotNull(I_M_HU_Attribute.class);
		attr.setIsActive(false);
		InterfaceWrapperHelper.save(attr);
	}

	private void createAssignments(
			final Object model,
			final I_M_HU luHU)
	{
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		final IHUAssignmentBL huAssignmentBL = Services.get(IHUAssignmentBL.class);
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		assertThat(handlingUnitsBL.isTopLevel(luHU)).isTrue();
		assertThat(handlingUnitsBL.isLoadingUnit(luHU)).isTrue();

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
		final I_M_InOut io = newInstance(I_M_InOut.class);
		io.setDocStatus(docStatus);
		io.setM_Warehouse_ID(helper.defaultWarehouse.getM_Warehouse_ID());

		final IDocumentBL docActionBL = Services.get(IDocumentBL.class);
		if (!docActionBL.issDocumentDraftedOrInProgress(io))
		{
			io.setProcessed(true); // important, since the code under test might also check for this flag
		}

		InterfaceWrapperHelper.save(io);

		final I_M_InOutLine iol1 = newInstance(I_M_InOutLine.class);
		iol1.setM_InOut(io);
		iol1.setLine(10);
		iol1.setM_Product_ID(helper.pTomato.getM_Product_ID());
		InterfaceWrapperHelper.save(iol1);

		final I_M_InOutLine iol2 = newInstance(I_M_InOutLine.class);
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
