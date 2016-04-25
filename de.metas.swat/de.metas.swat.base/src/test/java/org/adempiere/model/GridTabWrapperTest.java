package org.adempiere.model;

/*
 * #%L
 * de.metas.swat.base
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
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.Adempiere;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import mockit.Mocked;
//import mockit.NonStrict;
import mockit.NonStrictExpectations;

/**
 * 
 * @author tsa
 * 
 */
@Ignore // Mocked things are not working
public class GridTabWrapperTest
{
	@Mocked
	GridTab gridTab;

	@Mocked
	GridField gridField;

	//@NonStrict
	@Mocked
	MTable adTable;

	private final POJOLookupMap db = POJOLookupMap.get();

	public static interface Interface1
	{
		public void setT_Int(int value);

		public int getT_Int();

		public void setT_String(String value);

		public String getT_String();

		public void setT_Number(BigDecimal value);

		public BigDecimal getT_Number();

		public void setT_Date(Timestamp value);

		public Timestamp getT_Date();

		public void setT_Boolean(boolean value);

		public boolean isT_Boolean();

		public int getC_Invoice_ID();

		public org.compiere.model.I_C_Invoice getC_Invoice();

		public int getOtherInvoice_ID();

		public org.compiere.model.I_C_Invoice getOtherInvoice();
	}

	public static interface Interface2
	{
	}

	@Before
	public void setup()
	{
		Adempiere.enableUnitTestMode();
	}

	@Test
	public void test_create() throws Exception
	{
		Assert.assertNull(GridTabWrapper.create(null, Interface1.class));

		Interface1 o1 = GridTabWrapper.create(gridTab, Interface1.class);
		Assert.assertSame(gridTab, GridTabWrapper.getGridTab(o1));

		Interface2 o2 = GridTabWrapper.create(gridTab, Interface2.class);
		Assert.assertSame(gridTab, GridTabWrapper.getGridTab(o2));

		try
		{
			GridTabWrapper.create(new Object(), Interface1.class);
			Assert.fail("Should throw an exception when there is no underlying GridTab object");
		}
		catch (AdempiereException e)
		{
		}
	}

	@Test
	public void test_getters_int() throws Exception
	{
		expectGridTabGetValue("T_Int", 1234);
		
		Interface1 o1 = GridTabWrapper.create(gridTab, Interface1.class);
		Assert.assertEquals(1234, o1.getT_Int());
	}
	
	@Test
	public void test_getters_String() throws Exception
	{
		expectGridTabGetValue("T_String", "test string");
	
		Interface1 o1 = GridTabWrapper.create(gridTab, Interface1.class);
		Assert.assertEquals("test string", o1.getT_String());
	}
		
	@Test
	public void test_getters_Date() throws Exception
	{
		final Timestamp date = new Timestamp(System.currentTimeMillis());
		expectGridTabGetValue("T_Date", date);

		Interface1 o1 = GridTabWrapper.create(gridTab, Interface1.class);
		Assert.assertEquals(date, o1.getT_Date());
	}
	
	@Test
	public void test_getters_Number() throws Exception
	{
		expectGridTabGetValue("T_Number", new BigDecimal("123.4567"));

		Interface1 o1 = GridTabWrapper.create(gridTab, Interface1.class);
		Assert.assertEquals(new BigDecimal("123.4567"), o1.getT_Number());
	}
	
	@Test
	public void test_getters_Boolean() throws Exception
	{
		expectGridTabGetValue("T_Boolean", true);

		Interface1 o1 = GridTabWrapper.create(gridTab, Interface1.class);
		Assert.assertEquals(true, o1.isT_Boolean());
	}
	
	@Test
	public void test_getters_WhenNoColumnNameFound() throws Exception
	{
		expectGridTabGetValueNotFound("T_Int");
		expectGridTabGetValueNotFound("T_String");
		expectGridTabGetValueNotFound("T_Number");
		expectGridTabGetValueNotFound("T_Date");
		expectGridTabGetValueNotFound("T_Boolean");

		Interface1 o1 = GridTabWrapper.create(gridTab, Interface1.class);
		Assert.assertEquals(0, o1.getT_Int());
		Assert.assertNull("Should be null", o1.getT_String());
		Assert.assertNotNull(o1.getT_Number());
		Assert.assertEquals("Should be zero", 0, o1.getT_Number().signum());
		Assert.assertNull("Should be null", o1.getT_Date());
		// Assert.assertEquals(false, o1.isT_Boolean());
	}

	@Test
	@Ignore // ts: unable to make the test work and :-(
	public void test_getters_ReferencedObjects() throws Exception
	{
		final I_C_Invoice invoice = db.newInstance(I_C_Invoice.class);
		db.save(invoice);
		
		final int invoiceId = invoice.getC_Invoice_ID();
		expectTableGetPO("C_Invoice_ID", invoiceId, invoice);

		Assert.assertEquals(invoiceId, gridTab.getValue("C_Invoice_ID")); // guard

		Interface1 o1 = GridTabWrapper.create(gridTab, Interface1.class);
		Assert.assertEquals(invoiceId, o1.getC_Invoice_ID()); // guard
		
		org.compiere.model.I_C_Invoice invoiceResult = o1.getC_Invoice();
		Assert.assertNotNull(invoiceResult);
		Assert.assertSame(invoice, invoiceResult);
	}

	@Test
	@Ignore // ts: unable to make the test work and :-(
	public void test_getters_ReferencedObjects2() throws Exception
	{
		final I_C_Invoice invoice = db.newInstance(I_C_Invoice.class);
		db.save(invoice);
		
		final int invoiceId = invoice.getC_Invoice_ID();
		expectTableGetPO("C_Invoice_ID", invoiceId, invoice);

		Interface1 o1 = GridTabWrapper.create(gridTab, Interface1.class);
		Assert.assertEquals(invoiceId, o1.getOtherInvoice_ID());
		org.compiere.model.I_C_Invoice invoice2 = o1.getOtherInvoice();
		Assert.assertNotNull(invoice2);
		Assert.assertSame(invoice, invoice2);
	}

	@Test
	public void test_getters_ReferencedObjects_NotSet() throws Exception
	{
		final int invoiceId = 0;

		expectGridTabGetObject("C_Invoice_ID", "C_Invoice", invoiceId, null);

		Interface1 o1 = GridTabWrapper.create(gridTab, Interface1.class);
		Assert.assertEquals(invoiceId, o1.getC_Invoice_ID());
		org.compiere.model.I_C_Invoice invoice2 = o1.getC_Invoice();
		Assert.assertNull(invoice2);
	}

	@Test
	public void test_setters() throws Exception
	{
		final Timestamp date = new Timestamp(System.currentTimeMillis());

		final Interface1 o1 = GridTabWrapper.create(gridTab, Interface1.class);

		expectSetValue(gridTab, "T_Int", 123);
		expectSetValue(gridTab, "T_String", "test str2");
		expectSetValue(gridTab, "T_Number", new BigDecimal("567.123"));
		expectSetValue(gridTab, "T_Date", date);
		expectSetValue(gridTab, "T_Boolean", true);

		o1.setT_Int(123);
		o1.setT_String("test str2");
		o1.setT_Number(new BigDecimal("567.123"));
		o1.setT_Date(date);
		o1.setT_Boolean(true);
	}

	@Test
	public void test_refresh() throws Exception
	{
		new NonStrictExpectations(gridTab)
		{
			{
				gridTab.dataRefresh();
				times = 1;
			}
		};

		final Interface1 o1 = GridTabWrapper.create(gridTab, Interface1.class);
		GridTabWrapper.refresh(o1);
	}

	@Test
	public void test_save() throws Exception
	{
		new NonStrictExpectations(gridTab)
		{
			{
				gridTab.dataSave(false);
				result = true;
				times = 1;
			}
		};

		final Interface1 o1 = GridTabWrapper.create(gridTab, Interface1.class);
		GridTabWrapper.save(o1);
	}

	private void expectTableGetPO(final String tableName, final int recordId, final Object model)
	{
		new NonStrictExpectations()
		{
			{
				MTable.get((Properties)any, tableName);
				result = adTable;

				adTable.getPO(recordId, anyString);
				result = model;
				
				final String columnName = tableName+"_ID";
				gridTab.getValue(columnName);
				result = recordId;

				gridTab.getField(columnName);
				result = gridField;

				gridField.getValue();
				result = recordId;
			}
		};
	}

	private void expectGridTabGetValue(final String columnName, final Object value)
	{
		new NonStrictExpectations(gridTab)
		{
			{
				gridTab.getValue(columnName);
				result = value;

				gridTab.getField(columnName);
				result = gridField;

				gridField.getValue();
				result = value;
			}
		};
	}

	private void expectGridTabGetObject(String columnName, final String refTableName, int id, PO expectedPO)
	{
		expectGridTabGetValue(columnName, id);
		expectGridTabGetValueNotFound(columnName.substring(0, columnName.length() - 3));

		if (id > 0)
		{
			// invoked only on id > 0
			expectTableGetPO(refTableName, id, expectedPO);
		}
		else
		{
			// DB layer should not be accessed
			new NonStrictExpectations()
			{
				{
					MTable.get((Properties)any, refTableName);
					times = 0;
					result = null;
				}
			};

		}

	}

	private void expectGridTabGetValueNotFound(final String columnName)
	{
		new NonStrictExpectations(gridTab, gridField)
		{
			{
				gridTab.getValue(columnName);
				result = null;

				gridTab.getField(columnName);
				result = null;
			}
		};
	}

	private void expectSetValue(final GridTab gridTab, final String columnName, final Object value)
	{
		new NonStrictExpectations(gridTab)
		{
			{
				gridTab.setValue(columnName, value);
				times = 1;
			}
		};
	}
}
