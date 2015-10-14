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


import java.math.BigDecimal;

import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.ModelValidator;

import de.metas.commission.interfaces.I_C_InvoiceLine;
import de.metas.commission.interfaces.I_C_OrderLine;

/**
 * This is mainly a copy and paste of the former jboss-aop aspect <code>de.metas.commission.aopp.MInvoiceLineAsp</code>. The purpose of that aspect was:
 * 
 * <pre>
 * Set il commissionPoints and commissionPointsSum when a new InvoiceLine
 * is created from an OrderLine or when QtyInvoiced is set.
 * </pre>
 * 
 * @author ts
 * @task http://dewiki908/mediawiki/index.php/fresh_07286_get_rid_of_jboss-aop_for_good_%28104432455599%29
 */
@Validator(I_C_InvoiceLine.class)
public class C_InvoiceLine
{

	@ModelChange(
			timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = I_C_InvoiceLine.COLUMNNAME_C_OrderLine_ID)
	public void onSetOrderLine(I_C_InvoiceLine il)
	{
		final I_C_OrderLine ol = InterfaceWrapperHelper.create(il.getC_OrderLine(), I_C_OrderLine.class);
		il.setCommissionPoints(ol.getCommissionPoints());
	}

	@ModelChange(
			timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = I_C_InvoiceLine.COLUMNNAME_M_InOutLine_ID)
	public void onSetShipLine(I_C_InvoiceLine il)
	{
		if (il.getM_InOutLine_ID() <= 0)
		{
			return;
		}
		final I_M_InOutLine iol = il.getM_InOutLine();
		if (iol.getC_OrderLine_ID() <= 0)
		{
			return;
		}

		final I_C_OrderLine ol = InterfaceWrapperHelper.create(iol.getC_OrderLine(), I_C_OrderLine.class);
		il.setCommissionPoints(ol.getCommissionPoints());
	}

	@ModelChange(
			timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = I_C_InvoiceLine.COLUMNNAME_QtyInvoiced)
	public void onQtyInvoiced(I_C_InvoiceLine il)
	{
		final BigDecimal commissionPointsSum = il.getQtyInvoiced().multiply(il.getCommissionPoints());

		il.setCommissionPointsSum(commissionPointsSum);
	}

}
