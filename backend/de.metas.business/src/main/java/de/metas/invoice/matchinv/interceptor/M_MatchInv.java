package de.metas.invoice.matchinv.interceptor;

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

import de.metas.invoice.InvoiceLineId;
import de.metas.invoice.matchinv.service.MatchInvoiceService;
import de.metas.order.IMatchPOBL;
import de.metas.order.OrderLineId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_M_MatchInv;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

/**
 * @author ts
 */
@Interceptor(I_M_MatchInv.class)
@Component
public class M_MatchInv
{
	private final IMatchPOBL matchPOBL = Services.get(IMatchPOBL.class);
	private final MatchInvoiceService matchInvoiceService;

	public M_MatchInv(
			@NonNull final MatchInvoiceService matchInvoiceService)
	{
		this.matchInvoiceService = matchInvoiceService;
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_DELETE)
	public void afterDelete(final I_M_MatchInv matchInv)
	{
		clearInvoiceLineFromMatchPOs(matchInv);
	}

	private void clearInvoiceLineFromMatchPOs(final I_M_MatchInv matchInv)
	{
		final OrderLineId orderLineId = matchInvoiceService.getOrderLineId(matchInv).orElse(null);
		if (orderLineId == null)
		{
			return;
		}

		final InvoiceLineId invoiceLineId = InvoiceLineId.ofRepoId(matchInv.getC_Invoice_ID(), matchInv.getC_InvoiceLine_ID());
		matchPOBL.unlink(orderLineId, invoiceLineId);
	}
}
