/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2026 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.order.vatid;

import de.metas.bpartner.BPartnerId;
import de.metas.order.IOrderDAO;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderId;
import de.metas.util.Services;
import de.metas.vatid.VATaxIDOrderTaxRefresher;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_OrderLine;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * The {@code de.metas.business} implementation of {@code de.metas.vatid}'s
 * {@link VATaxIDOrderTaxRefresher} seam — that interface's javadoc says why it lives here.
 *
 * <p>Re-derives {@code C_OrderLine.C_Tax_ID} via {@link IOrderLineBL#setTax(I_C_OrderLine)} — the same
 * method every order-line save already goes through, because {@code MOrderLine#beforeSave} calls it
 * UNCONDITIONALLY, not gated on any changed field. That is what makes this class add no tax-computation
 * logic of its own; it only re-triggers the existing one for orders nothing else touches. If that call
 * ever becomes conditional, this assumption breaks.
 */
@Component
public class OrderLineTaxRefreshOnVATaxIDStatusChange implements VATaxIDOrderTaxRefresher
{
	@NonNull private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	@NonNull private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);

	@Override
	public void refreshOrderLinesTaxForBPartner(@NonNull final BPartnerId bpartnerId)
	{
		final Set<OrderId> notProcessedOrderIds = orderDAO.retrieveNotProcessedOrderIds(bpartnerId);
		if (notProcessedOrderIds.isEmpty())
		{
			return;
		}

		// Runs in the caller's transaction — which is what plain code does here; never open a new one.
		// The refresh must be atomic with the status change that caused it (VATaxIDCheckService wraps both
		// in one transaction), or an order line ends up carrying a tax no committed check justifies.
		for (final I_C_OrderLine orderLine : orderDAO.retrieveOrderLinesByOrderIds(notProcessedOrderIds))
		{
			orderLineBL.setTax(orderLine);
			InterfaceWrapperHelper.saveRecord(orderLine);
		}
	}
}
