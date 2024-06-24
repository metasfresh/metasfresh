package de.metas.materialtracking.qualityBasedInvoicing.ic.spi.impl;

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

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.materialtracking.impl.MaterialTrackingPPOrderBL;
import de.metas.materialtracking.model.I_C_Invoice_Detail;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.model.I_PP_Order;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.IContextAware;
import org.apache.commons.collections4.IteratorUtils;
import org.compiere.util.Env;
import org.eevolution.model.X_PP_Order;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class PP_Order_MaterialTracking_HandlerDAOTest
{
	private IContextAware context;
	private PP_Order_MaterialTracking_HandlerDAO dao;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		this.context = new PlainContextAware(Env.getCtx(), ITrx.TRXNAME_None);

		this.dao = new PP_Order_MaterialTracking_HandlerDAO();

		// NOTE: don't register any model interceptors, this test shall work as is
	}

	/**
	 * Test that method contract is respected. i.e. return only those manufacturing orders which:
	 * <ul>
	 * <li>are closed
	 * <li>have IsInvoicecandidate='N'
	 * <li>don't have an invoice candidate linked to them
	 * <li>reference an unprocessed M_Material_tracking
	 * </ul>
	 */
	@Test
	@SuppressWarnings("unused")
	public void test_retrievePPOrdersWithMissingICs()
	{
		final I_PP_Order ppOrder1 = createPP_Order(X_PP_Order.DOCSTATUS_Completed, null, false);
		final I_PP_Order ppOrder2 = createPP_Order(X_PP_Order.DOCSTATUS_Completed, MaterialTrackingPPOrderBL.C_DocType_DOCSUBTYPE_QualityInspection, false);
		final I_PP_Order ppOrder3 = createPP_Order(X_PP_Order.DOCSTATUS_Closed, null, false);
		final I_PP_Order ppOrder4 = createPP_Order(X_PP_Order.DOCSTATUS_Closed, MaterialTrackingPPOrderBL.C_DocType_DOCSUBTYPE_QualityInspection, false);
		final I_PP_Order ppOrder5 = createPP_Order(X_PP_Order.DOCSTATUS_Closed, null, true);
		final I_PP_Order ppOrder6 = createPP_Order(X_PP_Order.DOCSTATUS_Closed, MaterialTrackingPPOrderBL.C_DocType_DOCSUBTYPE_QualityInspection, true);

		final List<I_PP_Order> resultExpected = Arrays.asList(ppOrder3, ppOrder4);
		test_retrievePPOrdersWithMissingICs(resultExpected);
	}

	private void test_retrievePPOrdersWithMissingICs(final List<I_PP_Order> resultExpected)
	{
		final Iterator<I_PP_Order> resultActualIt = dao.retrievePPOrdersWithMissingICs(QueryLimit.NO_LIMIT);
		final List<I_PP_Order> resultActual = IteratorUtils.toList(resultActualIt);

		Assert.assertEquals("Invalid result", resultExpected, resultActual);
	}

	private I_PP_Order createPP_Order(
			final String docStatus,
			final String orderType,
			final boolean createInvoiceCandidate)
	{
		final I_M_Material_Tracking mt = InterfaceWrapperHelper.newInstance(I_M_Material_Tracking.class, context);
			mt.setProcessed(false);
			InterfaceWrapperHelper.save(mt);

		final I_PP_Order ppOrder = InterfaceWrapperHelper.newInstance(I_PP_Order.class, context);
		ppOrder.setDocStatus(docStatus);
		ppOrder.setOrderType(orderType);
		ppOrder.setIsInvoiceCandidate(false);
		ppOrder.setM_Material_Tracking(mt);
		InterfaceWrapperHelper.save(ppOrder);


		if (createInvoiceCandidate)
		{
			createC_Invoice_Candidate(ppOrder);
		}

		return ppOrder;
	}

	private I_C_Invoice_Candidate createC_Invoice_Candidate(final I_PP_Order ppOrder)
	{
		final I_C_Invoice_Candidate ic = InterfaceWrapperHelper.newInstance(I_C_Invoice_Candidate.class, ppOrder);
// the (AD_Table,Record_ID) reference is not relevant here
//		ic.setAD_Table_ID(InterfaceWrapperHelper.getModelTableId(ppOrder));
//		ic.setRecord_ID(InterfaceWrapperHelper.getId(ppOrder));
		InterfaceWrapperHelper.save(ic);

		final I_C_Invoice_Detail id = InterfaceWrapperHelper.newInstance(I_C_Invoice_Detail.class, ppOrder);
		id.setC_Invoice_Candidate(ic);
		id.setPP_Order(ppOrder);
		InterfaceWrapperHelper.save(id);
		return ic;
	}
}
