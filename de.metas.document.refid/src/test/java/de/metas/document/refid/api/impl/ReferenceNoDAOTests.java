package de.metas.document.refid.api.impl;

/*
 * #%L
 * de.metas.document.refid
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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBMoreThenOneRecordsFoundException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Order;
import org.compiere.util.Env;
import org.junit.Test;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.document.refid.RefIdTestBase;
import de.metas.document.refid.model.I_C_ReferenceNo;
import de.metas.document.refid.model.I_C_ReferenceNo_Doc;
import de.metas.document.refid.model.I_C_ReferenceNo_Type;
import de.metas.document.refid.spi.IReferenceNoGenerator;

public class ReferenceNoDAOTests extends RefIdTestBase
{

	/**
	 * Expecting the code to to property return the type by classname.
	 */
	@Test
	public void testGetTypeByClassOK()
	{
		final I_C_ReferenceNo_Type type1 = setupType(Gen1.class);

		final I_C_ReferenceNo_Type type2 = setupType(Gen2.class);

		PlainReferenceNoDAO dao = new PlainReferenceNoDAO();

		assertThat(dao.retrieveRefNoTypeByClass(Env.getCtx(), Gen1.class), is(type1));
		assertThat(dao.retrieveRefNoTypeByClass(Env.getCtx(), Gen2.class), is(type2));
	}

	/**
	 * Expecting the code to check that there is only one type per classname and throw an DBMoreThenOneRecordsFoundException if that is not the case (instead of simply returning the first one).
	 */
	@Test(expected = DBMoreThenOneRecordsFoundException.class)
	public void testGetTypeByClassDuplicate()
	{
		setupType(Gen1.class);
		setupType(Gen1.class);

		PlainReferenceNoDAO dao = new PlainReferenceNoDAO();
		assertThat(dao.retrieveReferenceNoTypes().size(), is(2)); // guard

		dao.retrieveRefNoTypeByClass(Env.getCtx(), Gen1.class);
	}

	/**
	 * Expecting the code to check that there is a type for the given classname and throwing an {@link AdempiereException} if that is not the case (instead of returning <code>null</code>).
	 */
	@Test(expected = AdempiereException.class)
	public void testGetTypeByClassMissing()
	{
		setupType(Gen1.class);

		PlainReferenceNoDAO dao = new PlainReferenceNoDAO();
		assertThat(dao.retrieveReferenceNoTypes().size(), is(1)); // guard

		dao.retrieveRefNoTypeByClass(Env.getCtx(), Gen2.class);
	}

	/**
	 * Expecting the code to to property return the type by Name.
	 */
	@Test
	public void testGetTypeByNameOK()
	{
		final I_C_ReferenceNo_Type type1 = setupType(Gen1.class);

		final I_C_ReferenceNo_Type type2 = setupType(Gen2.class);

		PlainReferenceNoDAO dao = new PlainReferenceNoDAO();

		assertThat(dao.retrieveRefNoTypeByName(mkName(Gen1.class)), is(type1));
		assertThat(dao.retrieveRefNoTypeByName(mkName(Gen2.class)), is(type2));
	}

	/**
	 * Expecting the code to check that there is only one type per Name and throw an DBMoreThenOneRecordsFoundException if that is not the case (instead of simply returning the first one).
	 */
	@Test(expected = DBMoreThenOneRecordsFoundException.class)
	public void testGetTypeByNameDuplicate()
	{
		setupType(Gen1.class);
		setupType(Gen1.class);

		final PlainReferenceNoDAO dao = new PlainReferenceNoDAO();
		assertThat(dao.retrieveReferenceNoTypes().size(), is(2)); // guard

		dao.retrieveRefNoTypeByName(mkName(Gen1.class));
	}

	/**
	 * Expecting the code to check that there is a type for the given name and throwing an {@link AdempiereException} if that is not the case (instead of returning <code>null</code>).
	 */
	@Test(expected = AdempiereException.class)
	public void testGetTypeByNameMissing()
	{
		setupType(Gen1.class);

		PlainReferenceNoDAO dao = new PlainReferenceNoDAO();
		assertThat(dao.retrieveReferenceNoTypes().size(), is(1)); // guard

		dao.retrieveRefNoTypeByName(mkName(Gen2.class));
	}

	private static class Gen1 implements IReferenceNoGenerator
	{
		@Override
		public String generateReferenceNo(Object sourceModel)
		{
			throw new UnsupportedOperationException();
		};
	};

	private static class Gen2 implements IReferenceNoGenerator
	{
		@Override
		public String generateReferenceNo(Object sourceModel)
		{
			throw new UnsupportedOperationException();
		};
	};

	private I_C_ReferenceNo_Type setupType(Class<? extends IReferenceNoGenerator> clazz)
	{
		final POJOLookupMap lookupMap = POJOLookupMap.get();
		final I_C_ReferenceNo_Type type = lookupMap.newInstance(I_C_ReferenceNo_Type.class);
		POJOWrapper.enableStrictValues(type);

		type.setClassname(clazz.getName());
		type.setName(mkName(clazz));
		lookupMap.save(type);

		return type;
	}

	private String mkName(Class<? extends IReferenceNoGenerator> clazz)
	{
		return clazz.getSimpleName() + "_name";
	}

	@Test
	public void testRetrieveAssociatedRecords()
	{
		final POJOLookupMap lookupMap = POJOLookupMap.get();

		final I_C_Order order = lookupMap.newInstance(I_C_Order.class);
		lookupMap.save(order);

		final I_C_Invoice invoice = lookupMap.newInstance(I_C_Invoice.class);
		lookupMap.save(invoice);

		//
		// create a reference number, and associate both 'order' and 'invoice' with it
		// that way the method under test can retrieve 'order' from 'invoice'
		final I_C_ReferenceNo_Type refNoType = setupType(Gen1.class);

		final I_C_ReferenceNo refNo = lookupMap.newInstance(I_C_ReferenceNo.class);
		refNo.setC_ReferenceNo_Type(refNoType);
		lookupMap.save(refNo);

		final I_C_ReferenceNo_Doc ocRefNoDoc = lookupMap.newInstance(I_C_ReferenceNo_Doc.class);
		ocRefNoDoc.setC_ReferenceNo(refNo);
		ocRefNoDoc.setAD_Table_ID(InterfaceWrapperHelper.getTableId(I_C_Order.class));
		ocRefNoDoc.setRecord_ID(order.getC_Order_ID());
		lookupMap.save(ocRefNoDoc);

		final I_C_ReferenceNo_Doc invoiceRefNoDoc = lookupMap.newInstance(I_C_ReferenceNo_Doc.class);
		invoiceRefNoDoc.setC_ReferenceNo(refNo);
		invoiceRefNoDoc.setAD_Table_ID(InterfaceWrapperHelper.getTableId(I_C_Invoice.class));
		invoiceRefNoDoc.setRecord_ID(invoice.getC_Invoice_ID());
		lookupMap.save(invoiceRefNoDoc);

		final PlainReferenceNoDAO dao = new PlainReferenceNoDAO();
		assertThat(dao.retrieveAssociatedRecords(order, Gen1.class, I_C_Invoice.class).size(), is(1));
		assertThat(dao.retrieveAssociatedRecords(order, Gen1.class, I_C_Invoice.class).get(0), is(invoice));

		assertThat(dao.retrieveAssociatedRecords(order, Gen1.class, I_C_Order.class).size(), is(1));
		assertThat(dao.retrieveAssociatedRecords(order, Gen1.class, I_C_Order.class).get(0), is(order));

		// create another unused type
		setupType(Gen2.class);

		// check that the method returns the empty list, when using Gen2.class for referenceNo type
		assertThat(dao.retrieveAssociatedRecords(order, Gen2.class, I_C_Invoice.class).isEmpty(), is(true));
		assertThat(dao.retrieveAssociatedRecords(invoice, Gen2.class, I_C_Order.class).isEmpty(), is(true));
	}
}
