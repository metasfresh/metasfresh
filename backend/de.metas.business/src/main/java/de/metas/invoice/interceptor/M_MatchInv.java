package de.metas.invoice.interceptor;

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


import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.invoice.model.I_M_MatchInv;

/**
 *
 * @author ts
 *
 */
@Interceptor(I_M_MatchInv.class)
@Component
public class M_MatchInv
{
	/**
	 * Note that we need the C_Invoice_ID for be set, because in the "Eingansrechnung" (PO-Invoice) window, the invoice lines are in an included tab, so the matchInv tab is a subtab of the C_Invoice
	 * tab.
	 *
	 * @param matchInv
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = I_M_MatchInv.COLUMNNAME_C_InvoiceLine_ID)
	public void updateC_Invoice_ID(final I_M_MatchInv matchInv)
	{
		final int invoiceId;
		if (matchInv.getC_InvoiceLine_ID() > 0)
		{
			invoiceId = matchInv.getC_InvoiceLine().getC_Invoice_ID();
		}
		else
		{
			invoiceId = 0;
		}
		matchInv.setC_Invoice_ID(invoiceId);
	}

}
