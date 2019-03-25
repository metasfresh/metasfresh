package de.metas.purchasecandidate.availability;

import java.util.Properties;
import java.util.concurrent.CompletableFuture;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.model.I_C_UOM;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import de.metas.vendor.gateway.api.VendorGatewayRegistry;
import de.metas.vendor.gateway.api.VendorGatewayService;
import de.metas.vendor.gateway.api.availability.AvailabilityRequest;
import de.metas.vendor.gateway.api.availability.AvailabilityRequestException;
import de.metas.vendor.gateway.api.availability.AvailabilityResponse;
import de.metas.vendor.gateway.api.availability.AvailabilityResponseItem;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;

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
	private final IUOMDAO uomsRepo = Services.get(IUOMDAO.class);

	private final ImmutableSet<AvailabilityRequest> requests;

	@Builder
	private AvailabilityCheckCommand(
			@NonNull @Singular final ImmutableSet<AvailabilityRequest> requests,
			@NonNull final VendorGatewayRegistry vendorGatewayRegistry)
	{
		this.vendorGatewayRegistry = vendorGatewayRegistry;
		this.requests = requests;
	}

	public AvailabilityMultiResult checkAvailability()
	{
		return requests.stream()
				.map(this::checkAvailabilityAndConvertThrowable)
				.reduce(AvailabilityMultiResult::merge)
				.orElse(AvailabilityMultiResult.EMPTY);
	}

	public void checkAvailabilityAsync(@NonNull final AvailabilityCheckCallback callback)
	{
		requests.forEach(request -> checkAvailabilityAsync(request, callback));
	}

	private void checkAvailabilityAsync(@NonNull final AvailabilityRequest request, @NonNull final AvailabilityCheckCallback callback)
	{
		final Properties localCtx = Env.copyCtx(Env.getCtx());

		CompletableFuture
				.supplyAsync(() -> {
					try (final IAutoCloseable ctxWithinAsyncThread = Env.switchContext(localCtx))
					{
						return checkAvailabilityAndConvertThrowable(request);
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

	private AvailabilityMultiResult checkAvailabilityAndConvertThrowable(final AvailabilityRequest request)
	{
		if (!vendorProvidesAvailabilityCheck(request.getVendorId()))
		{
			return AvailabilityMultiResult.EMPTY;
		}

		try
		{
			return checkAvailability0(request);
		}
		catch (final Throwable t)
		{
			throw convertThrowable(t);
		}
	}

	private AvailabilityMultiResult checkAvailability0(@NonNull final AvailabilityRequest request)
	{
		final VendorGatewayService vendorGatewayService = vendorGatewayRegistry
				.getSingleVendorGatewayService(request.getVendorId())
				.orElse(null);
		if (vendorGatewayService == null)
		{
			// shall not happen because we already checked that
			return AvailabilityMultiResult.EMPTY;
		}

		final AvailabilityResponse availabilityResponse = vendorGatewayService.retrieveAvailability(request);

		final ImmutableList.Builder<AvailabilityResult> result = ImmutableList.builder();
		for (final AvailabilityResponseItem responseItem : availabilityResponse.getAvailabilityResponseItems())
		{
			final I_C_UOM uom = uomsRepo.getById(responseItem.getUomId());
			final AvailabilityResult availabilityResult = AvailabilityResult
					.prepareBuilderFor(responseItem, uom)
					.build();

			result.add(availabilityResult);
		}

		return AvailabilityMultiResult.of(result.build());
	}

	private AdempiereException convertThrowable(@NonNull final Throwable throwable)
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

	private boolean vendorProvidesAvailabilityCheck(final int vendorBPartnerId)
	{
		return vendorGatewayRegistry.getSingleVendorGatewayService(vendorBPartnerId).isPresent();
	}
}
