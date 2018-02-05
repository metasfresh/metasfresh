package de.metas.purchasecandidate.availability;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ImmutablePair;
import org.compiere.Adempiere;
import org.compiere.util.Env;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.vendor.gateway.api.VendorGatewayRegistry;
import de.metas.vendor.gateway.api.VendorGatewayService;
import de.metas.vendor.gateway.api.availability.AvailabilityRequest;
import de.metas.vendor.gateway.api.availability.AvailabilityRequestException;
import de.metas.vendor.gateway.api.availability.AvailabilityRequestItem;
import de.metas.vendor.gateway.api.availability.AvailabilityResponse;
import de.metas.vendor.gateway.api.availability.AvailabilityResponseItem;
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

public class AvailabilityCheck
{
	public static AvailabilityCheck ofPurchaseCandidates(
			@NonNull final Collection<PurchaseCandidate> purchaseCandidates)
	{
		return new AvailabilityCheck(purchaseCandidates);
	}

	private final ImmutableListMultimap<Integer, PurchaseCandidate> vendorBParterId2PurchaseCandidates;

	private AvailabilityCheck(
			@NonNull final Collection<PurchaseCandidate> purchaseCandidates)
	{
		this.vendorBParterId2PurchaseCandidates = Multimaps.index(purchaseCandidates, PurchaseCandidate::getVendorBPartnerId);
	}

	public Multimap<PurchaseCandidate, AvailabilityResult> checkAvailability()
	{
		final Multimap<PurchaseCandidate, AvailabilityResult> result = ArrayListMultimap.create();

		for (final int vendorId : vendorBParterId2PurchaseCandidates.keySet())
		{
			result.putAll(checkAvailabilityAndConvertThrowable(vendorId));
		}

		return result;
	}

	/**
	 * @param callback a consumer that can process the results of the availability check, as soon as they are available.
	 */
	public void checkAvailabilityAsync(
			@NonNull final BiConsumer<Multimap<PurchaseCandidate, AvailabilityResult>, Throwable> callback)
	{
		final Properties localCtx = Env.copyCtx(Env.getCtx());

		for (final int vendorId : vendorBParterId2PurchaseCandidates.keySet())
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
							callback.accept(result, throwable);
						}
					});
		}
	}

	private Multimap<PurchaseCandidate, AvailabilityResult> checkAvailabilityAndConvertThrowable(final int vendorId)
	{
		if (!vendorProvidesAvailabilityCheck(vendorId))
		{
			return ImmutableMultimap.of();
		}

		final Map<AvailabilityRequestItem, PurchaseCandidate> requestItem2purchaseCandidate = //
				createRequestItems(vendorId);
		if (requestItem2purchaseCandidate.isEmpty())
		{
			return ImmutableMultimap.of();
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

	private Multimap<PurchaseCandidate, AvailabilityResult> checkAvailability0(
			final int vendorId,
			@NonNull final Map<AvailabilityRequestItem, PurchaseCandidate> requestItem2purchaseCandidate)
	{
		final Multimap<PurchaseCandidate, AvailabilityResult> result = ArrayListMultimap.create();

		final AvailabilityRequest availabilityRequest = AvailabilityRequest.builder()
				.vendorId(vendorId)
				.availabilityRequestItems(requestItem2purchaseCandidate.keySet())
				.build();

		final VendorGatewayRegistry vendorGatewayRegistry = Adempiere.getBean(VendorGatewayRegistry.class);
		final Optional<VendorGatewayService> vendorGatewayService = //
				vendorGatewayRegistry.getSingleVendorGatewayService(vendorId);
		if (!vendorGatewayService.isPresent())
		{
			return result;
		}

		final AvailabilityResponse availabilityResponse = //
				vendorGatewayService.get().retrieveAvailability(availabilityRequest);

		for (final AvailabilityResponseItem responseItem : availabilityResponse.getAvailabilityResponseItems())
		{
			final PurchaseCandidate purchaseCandidate = //
					requestItem2purchaseCandidate.get(responseItem.getCorrespondingRequestItem());

			final AvailabilityResult availabilityResultBuilder = AvailabilityResult
					.prepareBuilderFor(responseItem)
					.purchaseCandidate(purchaseCandidate)
					.build();

			result.put(
					purchaseCandidate,
					availabilityResultBuilder);
		}

		return result;
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

	private Map<AvailabilityRequestItem, PurchaseCandidate> createRequestItems(final int vendorId)
	{
		final ImmutableMap.Builder<AvailabilityRequestItem, PurchaseCandidate> result = ImmutableMap.builder();

		for (final PurchaseCandidate purchaseCandidate : vendorBParterId2PurchaseCandidates.get(vendorId))
		{
			result.put(purchaseCandidate.createAvailabilityRequestItem(), purchaseCandidate);
		}
		return result.build();
	}

	private boolean vendorProvidesAvailabilityCheck(
			final int vendorBPartnerId)
	{
		final VendorGatewayService vendorGatewayService = Adempiere.getBean(VendorGatewayService.class);

		final boolean providedForVendor = vendorGatewayService.isProvidedForVendor(vendorBPartnerId);
		return providedForVendor;
	}
}
