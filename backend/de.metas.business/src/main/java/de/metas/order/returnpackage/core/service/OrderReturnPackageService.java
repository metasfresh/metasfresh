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

package de.metas.order.returnpackage.core.service;

import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.order.returnpackage.PalletType;
import de.metas.order.returnpackage.core.repository.OrderReturnPackageRepository;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.I_C_Order;
import org.springframework.stereotype.Service;

/**
 * Creates the default return-package (Rücknahme Gebinde) rows (one per pallet type: EUR + H1) for a sales order. The two quantity columns (QtyDeliveredLU, QtyReturnedLU) are left empty; the user types them.
 */
@Service
@RequiredArgsConstructor
public class OrderReturnPackageService
{
	@NonNull private final OrderReturnPackageRepository orderReturnPackageRepository;
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

	/**
	 * Idempotent: creates the two return-package rows (EUR, H1) for the given order if it has none yet.
	 */
	public void createDefaultsForOrder(@NonNull final OrderId orderId)
	{
		if (orderReturnPackageRepository.hasReturnPackages(orderId))
		{
			return;
		}

		final I_C_Order order = orderDAO.getById(orderId); // load once; reused for both pallet-type rows
		for (final PalletType palletType : PalletType.values())
		{
			orderReturnPackageRepository.createRow(order, palletType);
		}
	}

}
