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

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BPartnerProductEffectiveBL
{
	@NonNull private final IBPartnerProductDAO bpartnerProductDAO = Services.get(IBPartnerProductDAO.class);
	@NonNull private final BPartnerEffectiveBL bpartnerEffectiveBL;

	/**
	 * Returns the effective purchase transport days for a (vendor, product, org), preserving
	 * the "is set?" distinction so a caller can decide whether to fall through to a lower tier.
	 * <p>
	 * Internal precedence:
	 * <ol>
	 *   <li>{@code C_BPartner_Product.DeliveryTime_Promised} (vendor × product × org) — if set, wins.</li>
	 *   <li>{@code C_BPartner.PO_TransportDays} (vendor default) — fallback when (1) is unset.</li>
	 * </ol>
	 * <p>
	 * Return-value semantics — required contract for callers that chain this with a lower-priority
	 * fallback (e.g. {@code PP_Product_Planning.DeliveryTime_Promised}):
	 * <ul>
	 *   <li>{@code Optional.empty()} — neither source has a value; caller may fall through.</li>
	 *   <li>{@code Optional.of(0)} — one of the two sources is <b>explicitly</b> set to 0.
	 *       Callers <b>must</b> use this 0 and <b>must not</b> fall through to a lower tier;
	 *       conflating "set to 0" with "not set" is a behavioural bug.</li>
	 *   <li>{@code Optional.of(n)} for {@code n &gt; 0} — explicit configured value.</li>
	 * </ul>
	 */
	public Optional<Integer> getPurchaseTransportDaysIfSet(@NonNull final BPartnerId vendorId, @NonNull final ProductId productId, @NonNull final OrgId orgId)
	{
		final Optional<Integer> fromBPartnerProduct = bpartnerProductDAO.getDeliveryTimePromised(vendorId, productId, orgId);
		if (fromBPartnerProduct.isPresent())
		{
			return fromBPartnerProduct;
		}
		return bpartnerEffectiveBL.getPurchaseTransportDaysIfSet(vendorId);
	}

	public int getPurchaseTransportDays(@NonNull final BPartnerId vendorId, @NonNull final ProductId productId, @NonNull final OrgId orgId)
	{
		return getPurchaseTransportDaysIfSet(vendorId, productId, orgId).orElse(0);
	}
}
