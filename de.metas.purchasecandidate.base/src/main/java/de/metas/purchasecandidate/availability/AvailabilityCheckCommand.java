package de.metas.purchasecandidate.availability;

import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import org.adempiere.bpartner.BPartnerId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;

import de.metas.order.OrderAndLineId;
import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.PurchaseCandidateId;
import de.metas.vendor.gateway.api.ProductAndQuantity;
import de.metas.vendor.gateway.api.VendorGatewayRegistry;
import de.metas.vendor.gateway.api.VendorGatewayService;
import de.metas.vendor.gateway.api.availability.AvailabilityRequest;
import de.metas.vendor.gateway.api.availability.AvailabilityRequestException;
import de.metas.vendor.gateway.api.availability.AvailabilityRequestItem;
import de.metas.vendor.gateway.api.availability.AvailabilityResponse;
import de.metas.vendor.gateway.api.availability.AvailabilityResponseItem;
import de.metas.vendor.gateway.api.availability.TrackingId;
import lombok.Builder;
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

class AvailabilityCheckCommand
{
	// services
	private final VendorGatewayRegistry vendorGatewayRegistry;

	private final ImmutableListMultimap<BPartnerId, PurchaseCandidateAndTrackingId> purchaseCandidatesByVendorId;

	@Builder
	private AvailabilityCheckCommand(
			@NonNull final PurchaseCandidatesAvailabilityRequest request,
			@NonNull final VendorGatewayRegistry vendorGatewayRegistry)
	{
		this.vendorGatewayRegistry = vendorGatewayRegistry;
		this.purchaseCandidatesByVendorId = request.getPurchaseCandidates()
				.entrySet()
				.stream()
				.map(PurchaseCandidateAndTrackingId::ofMapEntry)
				.collect(GuavaCollectors.toImmutableListMultimap(PurchaseCandidateAndTrackingId::getVendorId));
	}

	public AvailabilityMultiResult checkAvailability()
	{
		final Set<BPartnerId> vendorIds = purchaseCandidatesByVendorId.keySet();

		return vendorIds.stream()
				.map(this::checkAvailabilityAndConvertThrowable)
				.reduce(AvailabilityMultiResult::merge)
				.orElse(AvailabilityMultiResult.EMPTY);
	}

	public void checkAvailabilityAsync(@NonNull final AvailabilityCheckCallback callback)
	{
		final Properties localCtx = Env.copyCtx(Env.getCtx());

		for (final BPartnerId vendorId : purchaseCandidatesByVendorId.keySet())
		{
			CompletableFuture
					.supplyAsync(() -> {
						try (final IAutoCloseable ctxWithinAsyncThread = Env.switchContext(localCtx))
						{
							return checkAvailabilityAndConvertThrowable(vendorId);
						}
					})
					.whenComplete((result, throwable) -> {

						final boolean resultWasFound = result != null && !result.isEmpty();
						final boolean errorOccured = throwable != null;
						if (resultWasFound || errorOccured)
						{
							callback.onResult(result, throwable);
						}
					});
		}
	}

	private AvailabilityMultiResult checkAvailabilityAndConvertThrowable(final BPartnerId vendorId)
	{
		if (!vendorProvidesAvailabilityCheck(vendorId))
		{
			return AvailabilityMultiResult.EMPTY;
		}

		final Map<AvailabilityRequestItem, PurchaseCandidateAndTrackingId> requestItem2purchaseCandidate = createRequestItems(vendorId);
		if (requestItem2purchaseCandidate.isEmpty())
		{
			return AvailabilityMultiResult.EMPTY;
		}

		try
		{
			return checkAvailability0(vendorId, requestItem2purchaseCandidate);
		}
		catch (final Throwable t)
		{
			throw convertThrowable(t, requestItem2purchaseCandidate);
		}
	}

	private AvailabilityMultiResult checkAvailability0(
			@NonNull final BPartnerId vendorId,
			@NonNull final Map<AvailabilityRequestItem, PurchaseCandidateAndTrackingId> requestItem2purchaseCandidate)
	{
		final VendorGatewayService vendorGatewayService = vendorGatewayRegistry
				.getSingleVendorGatewayService(vendorId.getRepoId())
				.orElse(null);
		if (vendorGatewayService == null)
		{
			return AvailabilityMultiResult.EMPTY;
		}

		final AvailabilityResponse availabilityResponse = vendorGatewayService.retrieveAvailability(AvailabilityRequest.builder()
				.vendorId(vendorId.getRepoId())
				.availabilityRequestItems(requestItem2purchaseCandidate.keySet())
				.build());

		final ImmutableList.Builder<AvailabilityResult> result = ImmutableList.builder();
		for (final AvailabilityResponseItem responseItem : availabilityResponse.getAvailabilityResponseItems())
		{
			final AvailabilityRequestItem requestItem = responseItem.getCorrespondingRequestItem();
			final PurchaseCandidateAndTrackingId purchaseCandidate = requestItem2purchaseCandidate.get(requestItem);

			final AvailabilityResult availabilityResult = AvailabilityResult
					.prepareBuilderFor(responseItem)
					.purchaseCandidate(purchaseCandidate.getPurchaseCandidate())
					.build();

			result.add(availabilityResult);
		}

		return AvailabilityMultiResult.of(result.build());
	}

	private RuntimeException convertThrowable(
			@NonNull final Throwable throwable,
			@NonNull final Map<AvailabilityRequestItem, PurchaseCandidateAndTrackingId> requestItem2purchaseCandidate)
	{
		final boolean isAvailabilityRequestException = throwable instanceof AvailabilityRequestException;
		if (!isAvailabilityRequestException)
		{
			return AdempiereException.wrapIfNeeded(throwable);
		}

		final AvailabilityRequestException availabilityRequestException = AvailabilityRequestException.cast(throwable);

		final ImmutableList<AvailabilityException.ErrorItem> errorItems = availabilityRequestException
				.getRequestItem2Exception()
				.entrySet()
				.stream()
				.map(entry -> AvailabilityException.ErrorItem.builder()
						.trackingId(entry.getKey().getTrackingId())
						.error(entry.getValue())
						.build())
				.collect(ImmutableList.toImmutableList());

		return new AvailabilityException(errorItems);
	}

	private Map<AvailabilityRequestItem, PurchaseCandidateAndTrackingId> createRequestItems(final BPartnerId vendorId)
	{
		final ImmutableMap.Builder<AvailabilityRequestItem, PurchaseCandidateAndTrackingId> result = ImmutableMap.builder();

		for (final PurchaseCandidateAndTrackingId purchaseCandidate : purchaseCandidatesByVendorId.get(vendorId))
		{
			if (!purchaseCandidate.isProcessed())
			{
				result.put(purchaseCandidate.createAvailabilityRequestItem(), purchaseCandidate);
			}
		}
		return result.build();
	}

	private boolean vendorProvidesAvailabilityCheck(@NonNull final BPartnerId vendorBPartnerId)
	{
		return vendorGatewayRegistry.getSingleVendorGatewayService(vendorBPartnerId.getRepoId()).isPresent();
	}

	@lombok.Value
	@lombok.Builder
	private static final class PurchaseCandidateAndTrackingId
	{
		public static PurchaseCandidateAndTrackingId ofMapEntry(Map.Entry<TrackingId, PurchaseCandidate> entry)
		{
			return builder().trackingId(entry.getKey()).purchaseCandidate(entry.getValue()).build();
		}

		@NonNull
		TrackingId trackingId;
		@NonNull
		PurchaseCandidate purchaseCandidate;

		public BPartnerId getVendorId()
		{
			return purchaseCandidate.getVendorId();
		}

		public boolean isProcessed()
		{
			return purchaseCandidate.isProcessed();
		}

		public AvailabilityRequestItem createAvailabilityRequestItem()
		{
			final ProductAndQuantity productAndQuantity = purchaseCandidate.createProductAndQuantity();

			return AvailabilityRequestItem.builder()
					.trackingId(trackingId)
					.productAndQuantity(productAndQuantity)
					.purchaseCandidateId(PurchaseCandidateId.getRepoIdOr(purchaseCandidate.getId(), -1))
					.salesOrderLineId(OrderAndLineId.getOrderLineRepoIdOr(purchaseCandidate.getSalesOrderAndLineId(), -1))
					.build();
		}

	}
}
