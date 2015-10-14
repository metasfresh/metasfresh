package de.metas.commission.invoice.model.validator;

/*
 * #%L
 * de.metas.commission.base
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


import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;

import de.metas.commission.interfaces.I_C_Invoice;
import de.metas.commission.interfaces.I_C_Order;
import de.metas.commission.interfaces.I_M_InOut;

/**
 * This is mainly a copy and paste of the former jboss-aop aspect <code>de.metas.commission.aopp.MInvoiceAsp</code>. The purpose of that aspect was:
 * 
 * <pre>
 * Copy the C_Sponsor_ID after a new order, invoice or inout has been
 * created from an existing order or inout.
 * </pre>
 * 
 * and
 * 
 * <pre>
 * Copy the C_Sponsor_ID and IsCommissionLock after a new invoice has
 * been created from an existing one
 * </pre>
 * 
 * @author ts
 * @task http://dewiki908/mediawiki/index.php/07286_get_rid_of_jboss-aop_for_good_%28104432455599%29
 */
@Validator(I_C_Invoice.class)
public class C_Invoice
{

	/**
	 * 
	 * 
	 * @param invoice
	 */
	@ModelChange(
			timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = I_C_Invoice.COLUMNNAME_C_Order_ID)
	public void onSetOrder(I_C_Invoice invoice)
	{
		if (invoice.getC_Order_ID() <= 0)
		{
			return;
		}
		final I_C_Order order = InterfaceWrapperHelper.create(invoice.getC_Order(), I_C_Order.class);
		invoice.setC_Sponsor_ID(order.getC_Sponsor_ID());
	}

	@ModelChange(
			timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = I_C_Invoice.COLUMNNAME_C_Order_ID)
	public void onSetInOut(I_C_Invoice invoice)
	{
		final I_M_InOut inOut =
				InterfaceWrapperHelper.create(
						InterfaceWrapperHelper.getDynAttribute(invoice, I_M_InOut.Table_Name),
						I_M_InOut.class);
		if (inOut == null)
		{
			return;
		}

		invoice.setC_Sponsor_ID(inOut.getC_Sponsor_ID());
	}

}
