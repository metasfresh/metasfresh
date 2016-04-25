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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Vector;

import org.adempiere.webui.component.ListHead;
import org.adempiere.webui.component.ListHeader;
import org.adempiere.webui.component.WListItemRenderer;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Andrew Kimball
 *
 */
public class WListItemRendererTest
{

	WListItemRenderer m_renderer;
	Vector<Object> m_dataValid = new Vector<Object>();
	Vector<Object> m_dataInvalid = new Vector<Object>();

	Vector<String> m_columnNames = new Vector<String>();
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		Vector<Object> dataRowValid = new Vector<Object>();
		Vector<Object> dataRowInvalid = new Vector<Object>();
		
		m_columnNames.add("Name");
		m_columnNames.add("Age");
		
		m_renderer = new WListItemRenderer(m_columnNames);
		
		dataRowValid.add("River Phoenix");
		dataRowValid.add(Integer.valueOf(23));
		m_dataValid.add(dataRowValid);
		
		dataRowInvalid.add("Elvis Presley");
		dataRowInvalid.add(Integer.valueOf(42));
		dataRowInvalid.add("Graceland");
		m_dataInvalid.add(dataRowInvalid);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	{
	}

	/**
	 * Test method for {@link org.adempiere.webui.component.WListItemRenderer#WListItemRenderer()}.
	 */
	@Test
	public final void testWListItemRenderer()
	{
		WListItemRenderer renderer = new WListItemRenderer();
		assertEquals(0, renderer.getNoColumns());
	}

	/**
	 * Test method for {@link org.adempiere.webui.component.WListItemRenderer#WListItemRenderer(java.util.Vector)}.
	 */
	@Test
	public final void testWListItemRendererVectorOfQextendsString()
	{
		assertEquals(2, m_renderer.getNoColumns());
	}

	/**
	 * Test method for {@link org.adempiere.webui.component.WListItemRenderer#render(org.zkoss.zul.Listitem, java.lang.Object)}.
	 */
	@Ignore("Not running because instantiating a ZX listbox causes a NullPointerException as it" +
			" attempts to post events")
	@Test
	public final void testRender() throws Exception
	{
		/*ListModelTable model = new ListModelTable(m_dataValid);
		WListbox table = new WListbox();
		table.setData(model, m_columnNames);
		
		Listitem item = m_renderer.newListitem(table);
		m_renderer.render(item, table.getModel().get(0));
		*/

		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.adempiere.webui.component.WListItemRenderer#updateColumn(int, java.lang.String)}.
	 */
	@Test
	public final void testUpdateColumn()
	{
		ListHead head = new ListHead(); 
		ListHeader header;
		
		m_renderer.updateColumn(1, "Address");
		assertEquals(2, m_renderer.getNoColumns());
		
		m_renderer.renderListHead(head);
		
		header = (ListHeader)head.getChildren().get(1);
		assertEquals("Address", header.getLabel());
	}

	/**
	 * Test method for {@link org.adempiere.webui.component.WListItemRenderer#addColumn(java.lang.String)}.
	 */
	@Test
	public final void testAddColumn()
	{
		m_renderer.addColumn("Address");
		assertEquals(3, m_renderer.getNoColumns());
	}

	/**
	 * Test method for {@link org.adempiere.webui.component.WListItemRenderer#renderListHead(org.adempiere.webui.component.ListHead)}.
	 */
	@Test
	public final void testRenderListHead()
	{
		ListHead head = new ListHead(); 
		Object header;
		
		m_renderer.renderListHead(head);
		
		assertEquals(2, head.getChildren().size());
		
		header = head.getChildren().get(1);
		
		assertTrue(header instanceof ListHeader);
		assertEquals("Age", ((ListHeader)header).getLabel());
	}

	/**
	 * Test method for {@link org.adempiere.webui.component.WListItemRenderer#getRowPosition(org.zkoss.zk.ui.Component)}.
	 */
	@Ignore("Not running because the ZX listbox cannot be instantiated in JUnit")
	@Test
	public final void testGetRowPosition()
	{
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.adempiere.webui.component.WListItemRenderer#getColumnPosition(org.zkoss.zk.ui.Component)}.
	 */
	@Ignore("Not running because the ZX listbox cannot be instantiated in JUnit")
	@Test
	public final void testGetColumnPosition()
	{
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.adempiere.webui.component.WListItemRenderer#clearColumns()}.
	 */
	@Test
	public final void testClearColumns()
	{
		ListHead head = new ListHead();
	
		m_renderer.clearColumns();
		assertEquals(0, m_renderer.getNoColumns());
		
		m_renderer.renderListHead(head);
		
		assertEquals(0, head.getChildren().size());
	}

	/**
	 * Test method for {@link org.adempiere.webui.component.WListItemRenderer#clearSelection()}.
	 */
	@Ignore("Not running because the ZX listbox cannot be instantiated in JUnit")
	@Test
	public final void testClearSelection()
	{
	    fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.adempiere.webui.component.WListItemRenderer#addTableValueChangeListener(org.adempiere.webui.event.TableValueChangeListener)}.
	 */
	@Ignore("Not running because the ZX listbox cannot be instantiated in JUnit")
	@Test
	public final void testAddTableValueChangeListener()
	{
		fail("Not yet implemented");
	}

}
