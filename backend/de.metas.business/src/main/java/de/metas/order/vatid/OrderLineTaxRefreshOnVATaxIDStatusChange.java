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
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_OrderLine;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * The {@code de.metas.business} implementation of {@code de.metas.vatid}'s
 * {@link VATaxIDOrderTaxRefresher} seam — that interface's javadoc says why it lives here.
 *
 * <p>Re-derives {@code C_OrderLine.C_Tax_ID} via {@link IOrderLineBL#setTax(I_C_OrderLine)}, the same
 * method every order-line save already goes through, so this adds no tax-computation logic — it only
 * re-triggers the existing one for orders nothing else touches.
 */
@Component
public class OrderLineTaxRefreshOnVATaxIDStatusChange implements VATaxIDOrderTaxRefresher
{
	@NonNull private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	@NonNull private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);

	@Override
	public void refreshOrderLinesTaxForBPartner(@NonNull final BPartnerId bpartnerId)
	{
		final Set<OrderId> notCompletedOrderIds = orderDAO.retrieveNotCompletedOrderIds(bpartnerId);
		if (notCompletedOrderIds.isEmpty())
		{
			return;
		}

		// Thread-inherited, deliberately NOT a new transaction: the check process runs each partner's check
		// in its own per-item transaction (see C_BPartner_VATaxID_Check#checkOneInOwnTrx), and this refresh
		// is called from inside that same still-open transaction. The refresh must be atomic WITH the
		// status change that caused it — if the check's own commit later fails for any reason, the refresh
		// must roll back with it, or an order line ends up carrying a tax that no recorded check justifies.
		// A brand-new transaction would defeat exactly that: it would commit independently of the caller's
		// transaction, so a refresh could survive a check that never actually committed.
		trxManager.runInThreadInheritedTrx(() -> refreshInTrx(notCompletedOrderIds));
	}

	private void refreshInTrx(@NonNull final Set<OrderId> notCompletedOrderIds)
	{
		for (final I_C_OrderLine orderLine : orderDAO.retrieveOrderLinesByOrderIds(notCompletedOrderIds))
		{
			orderLineBL.setTax(orderLine);
			InterfaceWrapperHelper.saveRecord(orderLine);
		}
	}
}
