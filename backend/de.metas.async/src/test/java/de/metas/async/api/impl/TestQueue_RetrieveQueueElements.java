package de.metas.async.api.impl;

/*
 * #%L
 * de.metas.async
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

import de.metas.async.api.IQueueDAO;
import de.metas.async.model.I_C_Queue_Element;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Properties;

/**
 * Validate {@link IQueueDAO#retrieveQueueElements(I_C_Queue_WorkPackage, boolean)}.
 * 
 * @author tsa
 * 
 */
public class TestQueue_RetrieveQueueElements
{
	@BeforeClass
	public static void staticInit()
	{
		AdempiereTestHelper.get().staticInit();
	}

	private IQueueDAO queueDAO;

	private Properties ctx;
	private String trxName;

	private I_C_Queue_WorkPackage workpackage1;
	private I_C_Queue_Element workpackage1_element1;
	@SuppressWarnings("unused")
	private I_C_Queue_Element workpackage1_element2;
	@SuppressWarnings("unused")
	private I_C_Queue_Element workpackage1_element3;
	private I_C_Queue_WorkPackage workpackage2;
	private I_C_Queue_Element workpackage2_element1;
	private I_C_Queue_Element workpackage2_element2;
	private I_C_Queue_Element workpackage2_element3;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		queueDAO = Services.get(IQueueDAO.class);

		//
		// Setup test data
		ctx = Env.getCtx();
		trxName = ITrx.TRXNAME_None;

		int nextRecordId = 1;

		workpackage1 = createWorkPackage();
		workpackage1_element1 = createQueueElement(workpackage1, nextRecordId++);
		workpackage1_element2 = createQueueElement(workpackage1, nextRecordId++);
		workpackage1_element3 = createQueueElement(workpackage1, nextRecordId++);

		workpackage2 = createWorkPackage();
		workpackage2_element1 = createQueueElement(workpackage2, workpackage1_element1.getRecord_ID()); // same as workpackage1_element1
		workpackage2_element2 = createQueueElement(workpackage2, nextRecordId++);
		workpackage2_element3 = createQueueElement(workpackage2, nextRecordId++);
	}

	private I_C_Queue_WorkPackage createWorkPackage()
	{
		final I_C_Queue_WorkPackage wp = InterfaceWrapperHelper.create(ctx, I_C_Queue_WorkPackage.class, trxName);
		wp.setProcessed(false);
		InterfaceWrapperHelper.save(wp);

		return wp;
	}

	private I_C_Queue_Element createQueueElement(final I_C_Queue_WorkPackage workpackage, final int recordId)
	{
		final int adTableId = 12345;

		final I_C_Queue_Element e = InterfaceWrapperHelper.create(ctx, I_C_Queue_Element.class, trxName);
		e.setC_Queue_WorkPackage(workpackage);
		e.setAD_Table_ID(adTableId);

		e.setRecord_ID(recordId);
		InterfaceWrapperHelper.save(e);

		return e;
	}

	@Test
	public void test_retrieveAllElements()
	{
		final List<I_C_Queue_Element> elements = queueDAO.retrieveQueueElements(workpackage2, false); // skipAlreadyScheduledItems=false
		Assert.assertEquals("Invalid elements count", 3, elements.size());
		Assert.assertEquals("Invalid element at index=0", workpackage2_element1, elements.get(0));
		Assert.assertEquals("Invalid element at index=1", workpackage2_element2, elements.get(1));
		Assert.assertEquals("Invalid element at index=2", workpackage2_element3, elements.get(2));
	}

	@Test
	public void test_retrieveNotAlreadyScheduledElements_WorkpackageNotProcessed()
	{
		// make sure workpackage1 is not processed
		workpackage1.setProcessed(false);

		final List<I_C_Queue_Element> elements = queueDAO.retrieveQueueElements(workpackage2, true); // skipAlreadyScheduledItems=true
		Assert.assertEquals("Invalid elements count", 2, elements.size());
		Assert.assertEquals("Invalid element at index=0", workpackage2_element2, elements.get(0));
		Assert.assertEquals("Invalid element at index=1", workpackage2_element3, elements.get(1));
	}

	@Test
	public void test_retrieveNotAlreadyScheduledElements_WorkpackageProcessed()
	{
		// make sure workpackage1 is not processed
		workpackage1.setProcessed(true);
		InterfaceWrapperHelper.save(workpackage1);
		
		final List<I_C_Queue_Element> elements = queueDAO.retrieveQueueElements(workpackage2, true); // skipAlreadyScheduledItems=true

		// NOTE: result shall be the same as when we retrieve all elements
		Assert.assertEquals("Invalid elements count", 3, elements.size());
		Assert.assertEquals("Invalid element at index=0", workpackage2_element1, elements.get(0));
		Assert.assertEquals("Invalid element at index=1", workpackage2_element2, elements.get(1));
		Assert.assertEquals("Invalid element at index=2", workpackage2_element3, elements.get(2));
	}
}
