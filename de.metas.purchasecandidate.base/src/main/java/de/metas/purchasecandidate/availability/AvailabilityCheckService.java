package de.metas.purchasecandidate.availability;

import static java.math.BigDecimal.ONE;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ListMultimap;

import de.metas.bpartner.BPartnerId;
import de.metas.order.OrderAndLineId;
import de.metas.purchasecandidate.PurchaseCandidateId;
import de.metas.purchasecandidate.PurchaseCandidatesGroup;
import de.metas.quantity.Quantity;
import de.metas.util.GuavaCollectors;
import de.metas.vendor.gateway.api.ProductAndQuantity;
import de.metas.vendor.gateway.api.VendorGatewayRegistry;
import de.metas.vendor.gateway.api.availability.AvailabilityRequest;
import de.metas.vendor.gateway.api.availability.AvailabilityRequestItem;
import de.metas.vendor.gateway.api.availability.TrackingId;
import lombok.NonNull;

/*
 * #%L
 * de.metas.purchasecandidate.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Service
public class AvailabilityCheckService
{
	private final VendorGatewayRegistry vendorGatewayRegistry;

	public AvailabilityCheckService(@NonNull final VendorGatewayRegistry vendorGatewayRegistry)
	{
		this.vendorGatewayRegistry = vendorGatewayRegistry;
	}

	public AvailabilityMultiResult checkAvailability(@NonNull final PurchaseCandidatesAvailabilityRequest request)
	{
		return newAvailabilityCheckCommand(request).checkAvailability();
	}

	public void checkAvailabilityAsync(@NonNull final PurchaseCandidatesAvailabilityRequest request, @NonNull final AvailabilityCheckCallback callback)
	{
		newAvailabilityCheckCommand(request).checkAvailabilityAsync(callback);
	}

	private AvailabilityCheckCommand newAvailabilityCheckCommand(final PurchaseCandidatesAvailabilityRequest request)
	{
		return AvailabilityCheckCommand.builder()
				.vendorGatewayRegistry(vendorGatewayRegistry)
				.requests(createAvailabilityRequests(request.getPurchaseCandidatesGroups()))
				.build();
	}

	private static Set<AvailabilityRequest> createAvailabilityRequests(@NonNull final Map<TrackingId, PurchaseCandidatesGroup> purchaseCandidatesGroupsByTrackingId)
	{
		final ListMultimap<BPartnerId, AvailabilityRequestItem> requestItemsByVendorId = purchaseCandidatesGroupsByTrackingId
				.entrySet()
				.stream()
				.map(entry -> toVendorAndRequestItem(entry))
				.collect(GuavaCollectors.toImmutableListMultimap());

		final Set<BPartnerId> vendorIds = requestItemsByVendorId.keySet();

		return vendorIds.stream()
				.map(vendorId -> createAvailabilityRequestOrNull(vendorId, requestItemsByVendorId.get(vendorId)))
				.filter(Predicates.notNull())
				.collect(ImmutableSet.toImmutableSet());
	}

	private static Map.Entry<BPartnerId, AvailabilityRequestItem> toVendorAndRequestItem(final Map.Entry<TrackingId, PurchaseCandidatesGroup> entry)
	{
		final TrackingId trackingId = entry.getKey();
		final PurchaseCandidatesGroup purchaseCandidatesGroup = entry.getValue();
		final BPartnerId vendorId = purchaseCandidatesGroup.getVendorId();
		final AvailabilityRequestItem requestItem = createAvailabilityRequestItem(trackingId, purchaseCandidatesGroup);
		return GuavaCollectors.entry(vendorId, requestItem);
	}

	private static AvailabilityRequestItem createAvailabilityRequestItem(final TrackingId trackingId, final PurchaseCandidatesGroup purchaseCandidatesGroup)
	{
		final Quantity qtyToPurchase = purchaseCandidatesGroup.getQtyToPurchase();
		final ProductAndQuantity productAndQuantity = ProductAndQuantity.of(
				purchaseCandidatesGroup.getVendorProductNo(),
				qtyToPurchase.toBigDecimal().max(ONE), // check availability for at least one, even if qtyToPurchase is still zero
				qtyToPurchase.getUOMId());

		return AvailabilityRequestItem.builder()
				.trackingId(trackingId)
				.productAndQuantity(productAndQuantity)
				.purchaseCandidateId(PurchaseCandidateId.getRepoIdOr(purchaseCandidatesGroup.getSinglePurchaseCandidateIdOrNull(), -1))
				.salesOrderLineId(OrderAndLineId.getOrderLineRepoIdOr(purchaseCandidatesGroup.getSingleSalesOrderAndLineIdOrNull(), -1))
				.build();
	}

	private static AvailabilityRequest createAvailabilityRequestOrNull(final BPartnerId vendorId, final Collection<AvailabilityRequestItem> requestItems)
	{
		if (requestItems.isEmpty())
		{
			return null;
		}

		return AvailabilityRequest.builder()
				.vendorId(vendorId.getRepoId())
				.availabilityRequestItems(requestItems)
				.build();
	}
}
