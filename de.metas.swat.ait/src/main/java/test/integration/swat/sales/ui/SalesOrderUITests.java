package test.integration.swat.sales.ui;

/*
 * #%L
 * de.metas.swat.ait
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


import org.compiere.model.X_C_Order;
import org.junit.Test;

import de.metas.adempiere.ait.event.AIntegrationTestDriver;
import de.metas.adempiere.ait.helper.GridWindowHelper;
import de.metas.adempiere.ait.helper.Helper;
import de.metas.adempiere.ait.helper.IHelper;
import de.metas.adempiere.model.I_C_Order;

public class SalesOrderUITests extends AIntegrationTestDriver
{
	@Override
	public IHelper newHelper()
	{
		return new Helper();
	}
	
	@Test
	public void paymentTermVisible()
	{
		final GridWindowHelper gridWindowHelper = getHelper().mkGridWindowHelper();
		
		final I_C_Order order = gridWindowHelper.openTabForTable(I_C_Order.Table_Name, true).getGridTabInterface(I_C_Order.class);
		
		order.setPaymentRule(X_C_Order.PAYMENTRULE_Cash);
		gridWindowHelper.assertFieldDisplayed(I_C_Order.COLUMNNAME_C_PaymentTerm_ID, false);
		
		order.setPaymentRule(X_C_Order.PAYMENTRULE_OnCredit);
		gridWindowHelper.assertFieldDisplayed(I_C_Order.COLUMNNAME_C_PaymentTerm_ID, true);
		
		order.setPaymentRule(X_C_Order.PAYMENTRULE_DirectDebit);
		gridWindowHelper.assertFieldDisplayed(I_C_Order.COLUMNNAME_C_PaymentTerm_ID, true);
		
		order.setPaymentRule(X_C_Order.PAYMENTRULE_CreditCard);
		gridWindowHelper.assertFieldDisplayed(I_C_Order.COLUMNNAME_C_PaymentTerm_ID, true);
	}
}
