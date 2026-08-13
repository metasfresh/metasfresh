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
import de.metas.document.engine.DocStatus;
import de.metas.order.IOrderDAO;
import de.metas.order.IOrderLineBL;
import de.metas.util.Services;
import de.metas.vatid.VATaxIDOrderTaxRefresher;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The {@code de.metas.business} implementation of {@code de.metas.vatid}'s
 * {@link VATaxIDOrderTaxRefresher} seam — see that interface's javadoc for why the implementation lives
 * here rather than in the {@code de.metas.vatid} module itself.
 *
 * <p>Re-derives {@code C_OrderLine.C_Tax_ID} via {@link IOrderLineBL#setTax(I_C_OrderLine)} — the same
 * method every order-line save already goes through ({@code MOrderLine#beforeSave} calls it
 * unconditionally, "since an address change in the header can also cause tax changes in the lines") — so
 * this class introduces no new tax-computation logic; it only re-triggers the existing one for orders that
 * nothing else touches when only the partner's VAT-ID status changes elsewhere.
 */
@Component
public class OrderLineTaxRefreshOnVATaxIDStatusChange implements VATaxIDOrderTaxRefresher
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	@NonNull private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);

	@Override
	public void refreshOrderLinesTaxForBPartner(@NonNull final BPartnerId bpartnerId)
	{
		final List<I_C_Order> notCompletedOrders = queryBL.createQueryBuilder(I_C_Order.class)
				.addEqualsFilter(I_C_Order.COLUMNNAME_C_BPartner_ID, bpartnerId)
				.addNotInArrayFilter(I_C_Order.COLUMNNAME_DocStatus, DocStatus.completedOrClosedStatuses())
				.create()
				.list();

		for (final I_C_Order order : notCompletedOrders)
		{
			for (final I_C_OrderLine orderLine : orderDAO.retrieveOrderLines(order))
			{
				orderLineBL.setTax(orderLine);
				InterfaceWrapperHelper.saveRecord(orderLine);
			}
		}
	}
}
