package de.metas.purchasecandidate.availability;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

import org.adempiere.bpartner.BPartnerId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ImmutablePair;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multimaps;

import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.vendor.gateway.api.VendorGatewayRegistry;
import de.metas.vendor.gateway.api.VendorGatewayService;
import de.metas.vendor.gateway.api.availability.AvailabilityRequest;
import de.metas.vendor.gateway.api.availability.AvailabilityRequestException;
import de.metas.vendor.gateway.api.availability.AvailabilityRequestItem;
import de.metas.vendor.gateway.api.availability.AvailabilityResponse;
import de.metas.vendor.gateway.api.availability.AvailabilityResponseItem;
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

	private final ImmutableListMultimap<BPartnerId, PurchaseCandidate> purchaseCandidatesByVendorId;

	@Builder
	private AvailabilityCheckCommand(
			@NonNull final Collection<PurchaseCandidate> purchaseCandidates,
			@NonNull final VendorGatewayRegistry vendorGatewayRegistry)
	{
		this.vendorGatewayRegistry = vendorGatewayRegistry;
		this.purchaseCandidatesByVendorId = Multimaps.index(purchaseCandidates, PurchaseCandidate::getVendorId);
	}

	public List<AvailabilityResult> checkAvailability()
	{
		final ImmutableList.Builder<AvailabilityResult> result = ImmutableList.builder();

		for (final BPartnerId vendorId : purchaseCandidatesByVendorId.keySet())
		{
			final List<AvailabilityResult> vendorResult = checkAvailabilityAndConvertThrowable(vendorId);
			result.addAll(vendorResult);
		}

		return result.build();
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

	private List<AvailabilityResult> checkAvailabilityAndConvertThrowable(final BPartnerId vendorId)
	{
		if (!vendorProvidesAvailabilityCheck(vendorId))
		{
			return ImmutableList.of();
		}

		final Map<AvailabilityRequestItem, PurchaseCandidate> requestItem2purchaseCandidate = createRequestItems(vendorId);
		if (requestItem2purchaseCandidate.isEmpty())
		{
			return ImmutableList.of();
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

	private List<AvailabilityResult> checkAvailability0(
			@NonNull final BPartnerId vendorId,
			@NonNull final Map<AvailabilityRequestItem, PurchaseCandidate> requestItem2purchaseCandidate)
	{
		final VendorGatewayService vendorGatewayService = vendorGatewayRegistry
				.getSingleVendorGatewayService(vendorId.getRepoId())
				.orElse(null);
		if (vendorGatewayService == null)
		{
			return ImmutableList.of();
		}

		final AvailabilityResponse availabilityResponse = vendorGatewayService.retrieveAvailability(AvailabilityRequest.builder()
				.vendorId(vendorId.getRepoId())
				.availabilityRequestItems(requestItem2purchaseCandidate.keySet())
				.build());

		final ImmutableList.Builder<AvailabilityResult> result = ImmutableList.builder();
		for (final AvailabilityResponseItem responseItem : availabilityResponse.getAvailabilityResponseItems())
		{
			final AvailabilityRequestItem requestItem = responseItem.getCorrespondingRequestItem();
			final PurchaseCandidate purchaseCandidate = requestItem2purchaseCandidate.get(requestItem);

			final AvailabilityResult availabilityResult = AvailabilityResult
					.prepareBuilderFor(responseItem)
					.purchaseCandidate(purchaseCandidate)
					.build();

			result.add(availabilityResult);
		}

		return result.build();
	}

	private RuntimeException convertThrowable(
			@NonNull final Throwable throwable,
			@NonNull final Map<AvailabilityRequestItem, PurchaseCandidate> requestItem2purchaseCandidate)
	{
		final boolean isAvailabilityRequestException = throwable instanceof AvailabilityRequestException;
		if (!isAvailabilityRequestException)
		{
			return AdempiereException.wrapIfNeeded(throwable);
		}

		final AvailabilityRequestException availabilityRequestException = (AvailabilityRequestException)throwable;

		final ImmutableMap<PurchaseCandidate, Throwable> purchseCandidate2Throwable = //
				availabilityRequestException.getRequestItem2Exception().entrySet().stream()
						.map(entry -> ImmutablePair.of(
								requestItem2purchaseCandidate.get(entry.getKey()),  // the requestItem's purchaseCandidate
								entry.getValue())) // the throwable
						.collect(ImmutableMap.toImmutableMap(IPair::getLeft, IPair::getRight));

		return new AvailabilityException(purchseCandidate2Throwable);
	}

	private Map<AvailabilityRequestItem, PurchaseCandidate> createRequestItems(final BPartnerId vendorId)
	{
		final ImmutableMap.Builder<AvailabilityRequestItem, PurchaseCandidate> result = ImmutableMap.builder();

		for (final PurchaseCandidate purchaseCandidate : purchaseCandidatesByVendorId.get(vendorId))
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
}
