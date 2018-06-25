package de.metas.materialtracking.impl;

/*
 * #%L
 * de.metas.materialtracking
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


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderByBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOutLine;
import org.compiere.util.Env;
import org.eevolution.model.I_PP_Order;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.metas.materialtracking.IMaterialTrackingDAO;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.model.I_M_Material_Tracking_Ref;

public class MaterialTrackingDAOTest
{
	private IContextAware context;
	private MaterialTrackingDAO materialTrackingDAO;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		this.context = new PlainContextAware(Env.getCtx(), ITrx.TRXNAME_None);

		final ITrxManager trxManager = Services.get(ITrxManager.class);
		trxManager.setThreadInheritedTrxName(trxManager.createTrxName("threadInheritedDummytrx"));

		this.materialTrackingDAO = (MaterialTrackingDAO)Services.get(IMaterialTrackingDAO.class);
	}

	@Test
	public void test_createMaterialTrackingRefNoSave_retrieveReference()
	{
		final I_M_Material_Tracking materialTracking = createMaterialTracking();
		final I_C_OrderLine doc = createDocument(I_C_OrderLine.class);

		final I_M_Material_Tracking_Ref ref = materialTrackingDAO.createMaterialTrackingRefNoSave(materialTracking, doc);
		Assert.assertEquals("Invalid M_Material_Tracking_ID", materialTracking.getM_Material_Tracking_ID(), ref.getM_Material_Tracking_ID());
		Assert.assertEquals("Invalid AD_Table_ID", InterfaceWrapperHelper.getModelTableId(doc), ref.getAD_Table_ID());
		Assert.assertEquals("Invalid Record_ID", doc.getC_OrderLine_ID(), ref.getRecord_ID());
		Assert.assertEquals("Invalid IsActive", true, ref.isActive());
		Assert.assertEquals("Invalid IsQualityInspectionDoc (shall be false by default)", false, ref.isQualityInspectionDoc());

		final I_C_OrderLine docRetrieved = materialTrackingDAO.retrieveReference(ref, I_C_OrderLine.class);
		Assert.assertEquals("Invalid retrieved document", doc, docRetrieved);

		// Validate that the caching is working correctly
		Assert.assertSame("Invalid retrieved document", doc, docRetrieved); // shall be exactly the same because it's cached
	}

	@Test
	public void test_retrieveMaterialTrackingForModel()
	{
		final I_C_OrderLine orderLine = createDocument(I_C_OrderLine.class);
		final I_M_Material_Tracking materialTracking = createMaterialTracking();
		createMaterialTrackingRef(materialTracking, orderLine, false);

		final I_M_Material_Tracking materialTrackingActual = materialTrackingDAO.retrieveMaterialTrackingForModel(orderLine);
		Assert.assertEquals("Invalid material tracking retrieved", materialTracking, materialTrackingActual);

		test_retrieveMaterialTrackingForModels_UsingQueryBuilder(materialTracking, orderLine);
	}

	@Test
	public void test_retrieveMaterialTrackingForModel_NoMaterialTracking()
	{
		final I_C_OrderLine orderLine = createDocument(I_C_OrderLine.class);

		final I_M_Material_Tracking materialTrackingActual = materialTrackingDAO.retrieveMaterialTrackingForModel(orderLine);
		Assert.assertEquals("Invalid material tracking retrieved", null, materialTrackingActual);

		test_retrieveMaterialTrackingForModels_UsingQueryBuilder(null, orderLine);
	}

	/**
	 * Tests the result of {@link MaterialTrackingDAO#retrieveMaterialTrackingForModels(IQueryBuilder)}.
	 * 
	 * @param materialTrackingExpected
	 * @param orderLine
	 */
	private final void test_retrieveMaterialTrackingForModels_UsingQueryBuilder(
			final I_M_Material_Tracking materialTrackingExpected,
			final I_C_OrderLine orderLine)
	{
		//
		// Convert expectation to list
		final List<I_M_Material_Tracking> materialTrackingsExpected;
		if (materialTrackingExpected == null)
		{
			materialTrackingsExpected = Collections.emptyList();
		}
		else
		{
			materialTrackingsExpected = Collections.singletonList(materialTrackingExpected);
		}

		//
		// Fetch actual material tracking list using query builder
		final IQueryBuilder<I_C_OrderLine> orderLineQuery = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_OrderLine.class, orderLine)
				.addEqualsFilter(I_C_OrderLine.COLUMN_C_OrderLine_ID, orderLine.getC_OrderLine_ID());
		final List<I_M_Material_Tracking> materialTrackingsActual = materialTrackingDAO.retrieveMaterialTrackingForModels(orderLineQuery);

		Assert.assertEquals("Invalid material tracking list fetched for " + orderLine,
				materialTrackingsExpected, materialTrackingsActual);
	}

	@Test
	public void test_retrieveMaterialTrackingForModel_MultipleMaterialTrackingAssignments()
	{
		final I_C_OrderLine orderLine = createDocument(I_C_OrderLine.class);

		// Assign order line to multiple material trackings
		// NOTE: this case is not valid but we want to test how the retrieval behaves in this case anyway
		for (int i = 1; i <= 10; i++)
		{
			final I_M_Material_Tracking materialTracking1 = createMaterialTracking();
			createMaterialTrackingRef(materialTracking1, orderLine, false);
		}

		// Following method shall fail
		try
		{
			materialTrackingDAO.retrieveMaterialTrackingForModel(orderLine);
			Assert.fail("Retrieval in case document is assigned to multiple material trackings shall fail");
		}
		catch (Exception e)
		{
			// OK!
		}
	}

	@Test
	public void test_retrieveNumberOfInspection_GeneralCase()
	{
		final I_M_Material_Tracking materialTracking = createMaterialTracking();

		// Add some regular orders in between
		createMaterialTrackingRef(materialTracking, createDocument(I_PP_Order.class), false);

		//
		// Quality Inspection 1
		final I_PP_Order ppOrder_QI1 = createDocument(I_PP_Order.class);
		createMaterialTrackingRef(materialTracking, ppOrder_QI1, true);

		// Add some regular orders in between
		createMaterialTrackingRef(materialTracking, createDocument(I_PP_Order.class), false);

		//
		// Quality Inspection 2
		final I_PP_Order ppOrder_QI2 = createDocument(I_PP_Order.class);
		createMaterialTrackingRef(materialTracking, ppOrder_QI2, true);

		// Add some regular orders in between
		createMaterialTrackingRef(materialTracking, createDocument(I_PP_Order.class), false);

		//
		// Quality Inspection 3
		final I_PP_Order ppOrder_QI3 = createDocument(I_PP_Order.class);
		createMaterialTrackingRef(materialTracking, ppOrder_QI3, true);

		// Add some regular orders in between
		createMaterialTrackingRef(materialTracking, createDocument(I_PP_Order.class), false);

		Assert.assertEquals("Invalid Inspection number", 1, materialTrackingDAO.retrieveNumberOfInspection(ppOrder_QI1));
		Assert.assertEquals("Invalid Inspection number", 2, materialTrackingDAO.retrieveNumberOfInspection(ppOrder_QI2));
		Assert.assertEquals("Invalid Inspection number", 3, materialTrackingDAO.retrieveNumberOfInspection(ppOrder_QI3));
	}

	@Test
	public void test_retrieveNumberOfInspection_NoMaterialTracking()
	{
		final I_PP_Order ppOrder_QI1 = createDocument(I_PP_Order.class);

		// Shall throw exception:
		try
		{
			materialTrackingDAO.retrieveNumberOfInspection(ppOrder_QI1);
			Assert.fail("Shall fail because there is no material tracking for our Quality Inspection order");
		}
		catch (AdempiereException e)
		{
			// OK!
		}
	}

	@Test
	public void test_retrieveMaterialTrackingRefForType_GeneralCase()
	{
		final I_M_Material_Tracking materialTracking = createMaterialTracking();

		// Order Line
		final I_M_Material_Tracking_Ref ref10 = createDocumentAndAssignToMaterialTracking(I_C_OrderLine.class, materialTracking, false);

		// Material receipts
		final I_M_Material_Tracking_Ref ref20 = createDocumentAndAssignToMaterialTracking(I_M_InOutLine.class, materialTracking, false);
		final I_M_Material_Tracking_Ref ref21 = createDocumentAndAssignToMaterialTracking(I_M_InOutLine.class, materialTracking, false);
		final I_M_Material_Tracking_Ref ref22 = createDocumentAndAssignToMaterialTracking(I_M_InOutLine.class, materialTracking, false);
		final I_M_Material_Tracking_Ref ref23 = createDocumentAndAssignToMaterialTracking(I_M_InOutLine.class, materialTracking, false);

		// Quality Inspection 1
		final I_M_Material_Tracking_Ref ref30 = createDocumentAndAssignToMaterialTracking(I_PP_Order.class, materialTracking, true);

		// Some regular orders
		final I_M_Material_Tracking_Ref ref40 = createDocumentAndAssignToMaterialTracking(I_PP_Order.class, materialTracking, false);
		final I_M_Material_Tracking_Ref ref41 = createDocumentAndAssignToMaterialTracking(I_PP_Order.class, materialTracking, false);
		final I_M_Material_Tracking_Ref ref42 = createDocumentAndAssignToMaterialTracking(I_PP_Order.class, materialTracking, false);
		final I_M_Material_Tracking_Ref ref43 = createDocumentAndAssignToMaterialTracking(I_PP_Order.class, materialTracking, false);
		final I_M_Material_Tracking_Ref ref44 = createDocumentAndAssignToMaterialTracking(I_PP_Order.class, materialTracking, false);

		// Quality Inspection 2
		final I_M_Material_Tracking_Ref ref50 = createDocumentAndAssignToMaterialTracking(I_PP_Order.class, materialTracking, true);

		// Test for C_OrderLines
		test_retrieveMaterialTrackingRefForType(
				Arrays.asList(ref10),
				materialTracking,
				I_C_OrderLine.class);

		// Test for M_InOutLines
		test_retrieveMaterialTrackingRefForType(
				Arrays.asList(ref20, ref21, ref22, ref23),
				materialTracking,
				I_M_InOutLine.class);

		// Test for PP_Orders
		test_retrieveMaterialTrackingRefForType(
				Arrays.asList(
						ref30,
						ref40, ref41, ref42, ref43, ref44,
						ref50),
				materialTracking,
				I_PP_Order.class);
	}

	public <T> void test_retrieveMaterialTrackingRefForType(final List<I_M_Material_Tracking_Ref> refsExpected, final I_M_Material_Tracking materialTracking, final Class<T> documentType)
	{
		//
		// Retrieve actual references
		final List<I_M_Material_Tracking_Ref> refsActual = materialTrackingDAO.retrieveMaterialTrackingRefForType(materialTracking, documentType);

		//
		// Make sure retrieved references are correctly sorted
		final List<I_M_Material_Tracking_Ref> refsActualSorted = new ArrayList<>(refsActual);
		Collections.sort(refsActualSorted, getMaterialTrackingRefStandardComparator());
		Assert.assertEquals("material tracking references are not correctly sorted", refsActualSorted, refsActual);

		//
		// Make sure actual references are as expected
		Assert.assertEquals("Invalid retrieved material tracking references for " + documentType, refsExpected, refsActual);
	}

	protected I_M_Material_Tracking createMaterialTracking()
	{
		final I_M_Material_Tracking materialTracking = InterfaceWrapperHelper.newInstance(I_M_Material_Tracking.class, context);
		InterfaceWrapperHelper.save(materialTracking);
		return materialTracking;
	}

	protected I_M_Material_Tracking_Ref createMaterialTrackingRef(final I_M_Material_Tracking materialTracking,
			final Object model,
			final boolean isQualityInspectionDoc)
	{
		final I_M_Material_Tracking_Ref ref = materialTrackingDAO.createMaterialTrackingRefNoSave(materialTracking, model);
		ref.setIsQualityInspectionDoc(isQualityInspectionDoc);

		InterfaceWrapperHelper.save(ref);
		return ref;
	}

	protected <T> T createDocument(final Class<T> documentType)
	{
		final T document = InterfaceWrapperHelper.newInstance(documentType, context);
		InterfaceWrapperHelper.save(document);
		return document;
	}

	private <T> I_M_Material_Tracking_Ref createDocumentAndAssignToMaterialTracking(final Class<T> documentType, final I_M_Material_Tracking materialTracking, final boolean isQualityInspectionDoc)
	{
		final T document = createDocument(documentType);
		return createMaterialTrackingRef(materialTracking, document, isQualityInspectionDoc);
	}

	private Comparator<I_M_Material_Tracking_Ref> getMaterialTrackingRefStandardComparator()
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryOrderByBuilder<I_M_Material_Tracking_Ref> orderByBuilder = queryBL.createQueryOrderByBuilder(I_M_Material_Tracking_Ref.class);
		materialTrackingDAO.setDefaultOrderBy(orderByBuilder);
		final Comparator<I_M_Material_Tracking_Ref> orderByComparator = orderByBuilder.createQueryOrderBy().getComparator(I_M_Material_Tracking_Ref.class);
		return orderByComparator;
	}
}
