package de.metas.materialtracking.model.validator;

/*
 * #%L
 * de.metas.materialtracking
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

import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.materialtracking.model.I_C_Invoice_Detail;
import de.metas.materialtracking.model.I_PP_Order;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;

@Interceptor(I_C_Invoice_Detail.class)
public class C_Invoice_Detail
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE })
	public void unsetPPOrderIsInvoiceCandidateBeforeDelete(final I_C_Invoice_Detail id)
	{
		if (id.getPP_Order_ID() <= 0)
		{
			return;
		}
		final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
		if (invoiceCandBL.isUpdateProcessInProgress())
		{
			return;
		}

		final I_PP_Order ppOrder = id.getPP_Order();
		ppOrder.setIsInvoiceCandidate(false);
		InterfaceWrapperHelper.save(ppOrder);
	}
}
