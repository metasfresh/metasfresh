package de.metas.purchasecandidate;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ImmutablePair;
import org.compiere.Adempiere;
import org.compiere.util.Env;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

import de.metas.i18n.IMsgBL;
import de.metas.vendor.gateway.api.ProductAndQuantity;
import de.metas.vendor.gateway.api.VendorGatewayRegistry;
import de.metas.vendor.gateway.api.VendorGatewayService;
import de.metas.vendor.gateway.api.availability.AvailabilityRequest;
import de.metas.vendor.gateway.api.availability.AvailabilityRequestException;
import de.metas.vendor.gateway.api.availability.AvailabilityResponse;
import de.metas.vendor.gateway.api.availability.AvailabilityResponseItem;
import groovy.transform.EqualsAndHashCode;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

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

	private final List<PurchaseCandidate> purchaseCandidates;
	private final ImmutableListMultimap<Integer, PurchaseCandidate> vendorBParterId2PurchaseCandidates;

	private AvailabilityCheck(@NonNull final Collection<PurchaseCandidate> purchaseCandidates)
	{
		this.purchaseCandidates = ImmutableList.copyOf(purchaseCandidates);
		vendorBParterId2PurchaseCandidates = Multimaps.index(this.purchaseCandidates, PurchaseCandidate::getVendorBPartnerId);
	}

	@Value
	@Builder
	public static class AvailabilityResult
	{
		public static AvailabilityResultBuilder prepareBuilderFor(
				@NonNull final AvailabilityResponseItem availabilityResponseItem)
		{
			final Type type = Type.ofAvailabilityResponseItemType(availabilityResponseItem.getType());

			return AvailabilityResult.builder()
					.type(type)
					.availabilityText(availabilityResponseItem.getAvailabilityText())
					.datePromised(availabilityResponseItem.getDatePromised())
					.qty(availabilityResponseItem.getAvailableQuantity());
		}

		public enum Type
		{
			AVAILABLE, NOT_AVAILABLE;

			public String translate()
			{
				final String msgValue = "de.metas.purchasecandidate.AvailabilityResult_" + this.toString();
				return Services.get(IMsgBL.class).translate(Env.getCtx(), msgValue);
			}

			public static Type ofAvailabilityResponseItemType(
					@NonNull final de.metas.vendor.gateway.api.availability.AvailabilityResponseItem.Type type)
			{
				if (de.metas.vendor.gateway.api.availability.AvailabilityResponseItem.Type.AVAILABLE.equals(type))
				{
					return Type.AVAILABLE;
				}
				return Type.NOT_AVAILABLE;
			}
		}

		PurchaseCandidate purchaseCandidate;

		Type type;

		BigDecimal qty;

		Date datePromised;

		String availabilityText;

		private AvailabilityResult(
				@NonNull final PurchaseCandidate purchaseCandidate,
				@NonNull final Type type,
				@NonNull final BigDecimal qty,
				@Nullable final Date datePromised,
				@Nullable final String availabilityText)
		{
			this.purchaseCandidate = purchaseCandidate;
			this.type = type;
			this.qty = qty;
			this.datePromised = datePromised;
			this.availabilityText = availabilityText;
		}
	}

	@EqualsAndHashCode(callSuper = false)
	public static class AvailabilityException extends AdempiereException
	{
		private static final long serialVersionUID = -3954110236473712582L;

		@Getter
		private final Map<PurchaseCandidate, Throwable> purchaseCandidate2Throwable;

		private AvailabilityException(@NonNull final Map<PurchaseCandidate, Throwable> purchaseCandidate2Throwable)
		{
			this.purchaseCandidate2Throwable = purchaseCandidate2Throwable;
		}
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
		for (final int vendorId : vendorBParterId2PurchaseCandidates.keySet())
		{
			CompletableFuture
					.supplyAsync(() -> checkAvailabilityAndConvertThrowable(vendorId))
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

		final Map<ProductAndQuantity, PurchaseCandidate> requestItem2purchaseCandidate = createRequestItems(vendorId);
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
			@NonNull final Map<ProductAndQuantity, PurchaseCandidate> requestItem2purchaseCandidate)
	{
		final Multimap<PurchaseCandidate, AvailabilityResult> result = ArrayListMultimap.create();

		final AvailabilityRequest availabilityRequest = AvailabilityRequest.builder()
				.vendorId(vendorId)
				.availabilityRequestItems(requestItem2purchaseCandidate.keySet())
				.build();

		final VendorGatewayRegistry vendorGatewayRegistry = Adempiere.getBean(VendorGatewayRegistry.class);
		final List<VendorGatewayService> vendorGatewayServices = vendorGatewayRegistry.getVendorGatewayServices(vendorId);

		for (final VendorGatewayService vendorGatewayService : vendorGatewayServices)
		{
			final AvailabilityResponse availabilityResponse = vendorGatewayService.retrieveAvailability(availabilityRequest);

			for (final AvailabilityResponseItem responseItem : availabilityResponse.getAvailabilityResponseItems())
			{
				final ProductAndQuantity requestItem = responseItem.getCorrespondingRequestItem();

				final PurchaseCandidate purchaseCandidate = requestItem2purchaseCandidate.get(requestItem);
				result.put(
						purchaseCandidate,
						AvailabilityResult.prepareBuilderFor(responseItem)
								.purchaseCandidate(purchaseCandidate)
								.build());
			}
		}
		return result;
	}

	private RuntimeException convertThrowable(
			@NonNull final Throwable throwable,
			@NonNull final Map<ProductAndQuantity, PurchaseCandidate> requestItem2purchaseCandidate)
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

	private Map<ProductAndQuantity, PurchaseCandidate> createRequestItems(final int vendorId)
	{

		final ImmutableMap.Builder<ProductAndQuantity, PurchaseCandidate> result = ImmutableMap.builder();

		for (final PurchaseCandidate purchaseCandidate : vendorBParterId2PurchaseCandidates.get(vendorId))
		{
			result.put(createRequestItem(purchaseCandidate), purchaseCandidate);
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

	private static ProductAndQuantity createRequestItem(
			@NonNull final PurchaseCandidate purchaseCandidate)
	{
		final String productValue = purchaseCandidate.getVendorProductInfo().getProductNo();
		final BigDecimal qtyRequired = purchaseCandidate.getQtyRequired();

		return new ProductAndQuantity(productValue, qtyRequired);
	}
}
