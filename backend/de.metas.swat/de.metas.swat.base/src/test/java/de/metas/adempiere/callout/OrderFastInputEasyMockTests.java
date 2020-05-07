package de.metas.adempiere.callout;

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


import static org.adempiere.test.UnitTestTools.mock;
import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.replay;
import static org.easymock.classextension.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.adempiere.ad.callout.api.ICalloutField;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.util.Env;
import org.junit.Ignore;
import org.junit.Test;

import ch.qos.logback.classic.Level;
import de.metas.adempiere.model.I_C_Order;
import de.metas.logging.LogManager;

// FIXME: NOT WORKING ATM!
@Ignore
public class OrderFastInputEasyMockTests
{
	private static final int M_Product_ID_NULL = -1;
	@Test
	public void evalInputdoNothing()
	{
		assertDoNothing(M_Product_ID_NULL, null);
		assertDoNothing(0, BigDecimal.ZERO);

		// Set the level to warning so in case of a less important log the message is not even built.
		LogManager.setLoggerLevel(OrderFastInput.logger, Level.WARN);
		assertDoNothing(1, null);
		assertDoNothing(1, BigDecimal.ZERO);

		assertDoNothing(M_Product_ID_NULL, new BigDecimal("23"));
		assertDoNothing(0, new BigDecimal("23"));
	}

	private void assertDoNothing(final int productId, final BigDecimal qty)
	{
		final Map<String, Object> mocks = new HashMap<String, Object>();

		//final GridTab gridTab = setupGridTab(productId, qty, mocks);
		final I_C_Order order = setupOrder(productId, qty, mocks);

		final ICalloutField fieldProduct = setupCalloutField(order, productId, mocks); 
//		final ICalloutField fieldProduct = mock(GridField.class, "gridFieldProduct", mocks);
//		expect(fieldProduct.getValue()).andStubReturn(productId);

		//expect(gridTab.getField(I_C_Order.COLUMNNAME_M_Product_ID)).andStubReturn(fieldProduct);
		
		invoke_evalProductQtyInput(mocks, fieldProduct);
	}

	private I_C_Order setupOrder(final int productId, final BigDecimal qty, final Map<String, Object> mocks)
	{
		final I_C_Order order = mock(I_C_Order.class, "order", mocks);
		expect(order.getM_Product_ID()).andStubReturn(productId);
		expect(order.getQty_FastInput()).andStubReturn(qty);
		return order;
	}
	
	private ICalloutField setupCalloutField(final I_C_Order order, final Object value, final Map<String, Object> mocks)
	{
		final GridField field = mock(GridField.class, "field", mocks);
		expect(field.getCtx()).andStubReturn(Env.getCtx());
		expect(field.getWindowNo()).andStubReturn(2);
		expect(field.getModel(I_C_Order.class)).andStubReturn(order);
		expect(field.getValue()).andStubReturn(value);
		
		final GridTab gridTab = mock(GridTab.class, "gridTab", mocks);
		expect(gridTab.getTableName()).andStubReturn(I_C_Order.Table_Name);
		expect(field.getGridTab()).andStubReturn(gridTab);
		
		return field;
	}

	private GridTab setupGridTab(final Integer productId, final BigDecimal qty, final Map<String, Object> mocks)
	{
		final I_C_Order order = mock(I_C_Order.class, "order", mocks);
		final ICalloutField gridFieldQty = mock(GridField.class, "gridFieldQty", mocks);
		expect(gridFieldQty.getCtx()).andStubReturn(Env.getCtx());
		expect(gridFieldQty.getWindowNo()).andStubReturn(2);
		expect(gridFieldQty.getValue()).andStubReturn(qty);
		expect(gridFieldQty.getModel(I_C_Order.class)).andStubReturn(order);

		final GridTab gridTab = mock(GridTab.class, "gridTab", mocks);

//		expect(gridTab.getField(I_C_Order.COLUMNNAME_Qty_FastInput)).andStubReturn(gridFieldQty);

		expect(gridTab.getTableName()).andStubReturn(I_C_Order.Table_Name);

		return gridTab;
	}

	private void invoke_evalProductQtyInput(final Map<String, Object> mocks, final ICalloutField calloutField)
	{

		replay(mocks.values().toArray());
		final OrderFastInput fastInput = new OrderFastInput();
		final String result = fastInput.evalProductQtyInput(calloutField);
		assertEquals(result, "");
		verify(mocks.values().toArray());
	}
}
