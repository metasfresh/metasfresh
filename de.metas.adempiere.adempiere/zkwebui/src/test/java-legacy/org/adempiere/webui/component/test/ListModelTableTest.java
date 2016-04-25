/******************************************************************************
 * Product: Posterita Ajax UI 												  *
 * Copyright (C) 2007 Posterita Ltd.  All Rights Reserved.                    *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * Posterita Ltd., 3, Draper Avenue, Quatre Bornes, Mauritius                 *
 * or via info@posterita.org or http://www.posterita.org/                     *
 *****************************************************************************/

package org.adempiere.webui.component.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Vector;

import org.adempiere.webui.component.ListModelTable;
import org.adempiere.webui.event.WTableModelEvent;
import org.adempiere.webui.event.WTableModelListener;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for {@link org.adempiere.webui.component.ListModelTable}.
 * 
 * @author Andrew Kimball
 *
 */
public class ListModelTableTest implements WTableModelListener
{
	/** First data object. */
	private static final Integer ms_number0 =  Integer.valueOf(0);
	/** Second data object. */
	private static final Integer ms_number1 =  Integer.valueOf(1);
	/** Third data object. */
	private static final Integer ms_number2 =  Integer.valueOf(2);
	/** Third data object. */
	private static final Integer ms_number3 =  Integer.valueOf(3);
	/** Fourth data object. */
	private static final Integer ms_number4 =  Integer.valueOf(4);
	/** Fifth data object. */
	private static final Integer ms_number5 =  Integer.valueOf(5);
	/** Sixth data object. */
	private static final Integer ms_number6 =  Integer.valueOf(6);
	
	/** The table instance on which tests are to be run. */
	private ListModelTable m_table;
	/** A flag to indicate whether the listener has been called and has succeeded. */
	private boolean m_isListenerCalled = false;

		
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		Vector<Integer> row0 = new Vector<Integer>();
		Vector<Integer> row1 = new Vector<Integer>();
		Vector<Object> data = new Vector<Object>();
		
		// create two rows of data
		row0.add(ms_number0);
		row0.add(ms_number1);
		row1.add(ms_number2);
		row1.add(ms_number3);
		
		// create the data
		data.add(row0);
		data.add(row1);
		
		// instantiate the model
		m_table = new ListModelTable(data);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	{
	}

	/**
	 * Test method for {@link org.adempiere.webui.component.ListModelTable#ListModelTable()}.
	 */
	@Test
	public final void testListModelTable()
	{
		ListModelTable table = new ListModelTable();
		boolean isException = false;
		Object data;
		
		assertEquals(0, table.getNoColumns());
		assertEquals(0, table.getSize());
		// try to get data from an invalid field
		try
		{
			data = table.getDataAt(0, 0);
			// never reach here, but removes warnings
			assertNull(data);
		}
		catch (IllegalArgumentException exception)
		{
			isException = true;
		}
		assertTrue(isException);
	}

	/**
	 * Test method for {@link org.adempiere.webui.component.ListModelTable#ListModelTable(java.util.Collection)}.
	 */
	@Test (expected= IllegalArgumentException.class)
	public final void testListModelTableCollection()
	{	
		final int invalidRow = 2;
		final int noColumns = 2;
		Object data;
		
		assertEquals(noColumns, m_table.getNoColumns());
		assertEquals(noColumns, m_table.getSize());
		assertEquals(Integer.valueOf(0), m_table.getDataAt(0, 0));
		
		//try to get data from an invalid field
		data = m_table.getDataAt(invalidRow, 0);
	}


	/**
	 * Test method for {@link org.adempiere.webui.component.ListModelTable#addColumn()}.
	 */
	@Test
	public final void testAddColumn()
	{
		final int noColumns = 3;
		m_table.addColumn();
		
		assertEquals(noColumns, m_table.getNoColumns());
		assertNull(m_table.getDataAt(0, noColumns - 1));
	}

	/**
	 * Test method for {@link org.adempiere.webui.component.ListModelTable#setNoColumns(int)}.
	 */
	@Test
	public final void testSetNoColumns()
	{
		final int noColumns = 3;
		
		m_table.setNoColumns(noColumns);
		
		assertEquals(noColumns, m_table.getNoColumns());
		assertNull(m_table.getDataAt(0, noColumns - 1));
	}

	/**
	 * Test method for {@link org.adempiere.webui.component.ListModelTable#getDataAt(int, int)}.
	 */
	@Test
	public final void testGetDataAt()
	{
		assertEquals(ms_number0, m_table.getDataAt(0, 0));
		assertEquals(ms_number3, m_table.getDataAt(1, 1));
	}

	/**
	 * Test method for {@link org.adempiere.webui.component.ListModelTable#setDataAt(java.lang.Object, int, int)}.
	 */
	@Test (expected= IllegalArgumentException.class)
	public final void testSetDataAt()
	{
		final int invalidRow = 2;
		
		m_table.setDataAt(ms_number4, 0, 0);
		m_table.setDataAt(ms_number5, 1, 1);
		
		assertEquals(ms_number4, m_table.getDataAt(0, 0));
		assertEquals(ms_number5, m_table.getDataAt(1, 1));
		
		// expect this to throw an exception
		m_table.setDataAt(ms_number6, invalidRow, 0);
	}

	/**
	 * Test method for {@link org.adempiere.webui.component.ListModelTable#setNoRows(int)}.
	 */
	@Test
	public final void testSetNoRows()
	{
		final int noRows = 3;
		m_table.setNoRows(m_table.getSize() + 1);
		
		assertEquals(noRows, m_table.getSize());
		assertEquals(ms_number3, m_table.getDataAt(1, 1));
		assertNull(m_table.getDataAt(2, 1));
	}

	/**
	 * Test method for 
	 * {@link org.adempiere.webui.component.ListModelTable#addTableModelListener(org.adempiere.webui.event.WTableModelListener)}.
	 */
	@Test
	public final void testAddTableModelListener()
	{
		m_table.addTableModelListener(this);
		m_table.setDataAt(ms_number4, 0, 0);
		
		assertTrue(m_isListenerCalled);
	}

	/* (non-Javadoc)
	 * @see org.adempiere.webui.event.WTableModelListener#tableChanged(org.adempiere.webui.event.WTableModelEvent)
	 */
	public void tableChanged(WTableModelEvent event)
	{
		assertEquals(ms_number4, m_table.getDataAt(0, 0));
		m_isListenerCalled = true;
	}

}
