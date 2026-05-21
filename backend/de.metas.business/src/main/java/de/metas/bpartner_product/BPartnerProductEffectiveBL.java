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

package de.metas.bpartner_product;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.effective.BPartnerEffectiveBL;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BPartnerProductEffectiveBL
{
	@NonNull private final IBPartnerProductDAO bpartnerProductDAO = Services.get(IBPartnerProductDAO.class);
	@NonNull private final BPartnerEffectiveBL bpartnerEffectiveBL;

	public int getPurchaseTransportDays(@NonNull final BPartnerId vendorId, @NonNull final ProductId productId, @NonNull final OrgId orgId)
	{
		return bpartnerProductDAO.getDeliveryTimePromised(vendorId, productId, orgId)
				.orElseGet(() -> bpartnerEffectiveBL.getPurchaseTransportDays(vendorId));
	}
}
