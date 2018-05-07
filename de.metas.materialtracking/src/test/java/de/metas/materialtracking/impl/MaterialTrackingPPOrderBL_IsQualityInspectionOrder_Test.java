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


import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IContextAware;
import org.compiere.util.Env;
import org.eevolution.model.I_PP_Order;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.metas.materialtracking.IMaterialTrackingPPOrderBL;

/**
 * From {@link MaterialTrackingPPOrderBL} service, tests methods which are about checking if a manufacturing order is Quality Inspection or not.
 *
 * @author tsa
 *
 */
public class MaterialTrackingPPOrderBL_IsQualityInspectionOrder_Test
{
	protected IContextAware context;
	/** Service under test */
	private MaterialTrackingPPOrderBL materialTrackingPPOrderBL;

	@Before
	public final void init()
	{
		AdempiereTestHelper.get().init();
		this.context = new PlainContextAware(Env.getCtx(), ITrx.TRXNAME_None);

		this.materialTrackingPPOrderBL = (MaterialTrackingPPOrderBL)Services.get(IMaterialTrackingPPOrderBL.class);
	}

	@Test
	public void testIsQualityInspectionOrder_For_QualityInspectionOrder()
	{
		final I_PP_Order ppOrder = createPP_Order(MaterialTrackingPPOrderBL.C_DocType_DOCSUBTYPE_QualityInspection);
		testIsQualityInspectionOrder(true, ppOrder);
	}

	@Test
	public void testIsQualityInspectionOrder_For_RegularOrder()
	{
		final I_PP_Order ppOrder = createPP_Order(null);
		testIsQualityInspectionOrder(false, ppOrder);
	}

	/**
	 * Checks various methods which are checking if an order is a quality inspection order.
	 *
	 * @param isQualityInspectionOrderExpected
	 * @param ppOrder
	 */
	protected void testIsQualityInspectionOrder(final boolean isQualityInspectionOrderExpected, final I_PP_Order ppOrder)
	{
		//
		// Check isQualityInspection() method
		Assert.assertEquals("Invalid isQualityInspection() result",
				isQualityInspectionOrderExpected,
				materialTrackingPPOrderBL.isQualityInspection(ppOrder));

		//
		// Checks qualityInspectionFilter return
		final IQueryFilter<I_PP_Order> qualityInspectionFilter = materialTrackingPPOrderBL.getQualityInspectionFilter();
		Assert.assertEquals("Invalid qualityInspectionFilter.accept() result",
				isQualityInspectionOrderExpected,
				qualityInspectionFilter.accept(ppOrder));

		//
		// Checks qualityInspectionFilter in a query result
		{
			final I_PP_Order ppOrderExpected = isQualityInspectionOrderExpected ? ppOrder : null;
			final I_PP_Order ppOrderActual = Services.get(IQueryBL.class)
					.createQueryBuilder(I_PP_Order.class, ppOrder)
					.addEqualsFilter(I_PP_Order.COLUMN_PP_Order_ID, ppOrder.getPP_Order_ID())
					.filter(qualityInspectionFilter)
					.create()
					.firstOnly(I_PP_Order.class);

			Assert.assertEquals("Invalid PP_Order retrieved by query using qualityInspectionFilter", ppOrderExpected, ppOrderActual);
		}

		//
		// Check assertQualityInspectionOrder()
		{
			Exception assertException = null;
			try
			{
				materialTrackingPPOrderBL.assertQualityInspectionOrder(ppOrder);
			}
			catch (Exception e)
			{
				assertException = e;
			}

			if (isQualityInspectionOrderExpected)
			{
				Assert.assertNull("No exception shall be thrown by assertQualityInspectionOrder()", assertException);
			}
			else
			{
				Assert.assertNotNull("Exception shall be thrown by assertQualityInspectionOrder()", assertException);
			}
		}
	}

	protected I_PP_Order createPP_Order(final String orderType)
	{
		final I_PP_Order ppOrder = InterfaceWrapperHelper.newInstance(I_PP_Order.class, context);
		ppOrder.setOrderType(orderType);
		InterfaceWrapperHelper.save(ppOrder);

		return ppOrder;
	}

}
