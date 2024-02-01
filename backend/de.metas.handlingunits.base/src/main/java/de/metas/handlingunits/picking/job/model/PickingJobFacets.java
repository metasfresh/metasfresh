/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.handlingunits.picking.job.model;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.common.util.time.SystemTime;
import de.metas.organization.InstantAndOrgId;
import de.metas.picking.api.Packageable;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

@Value
@Builder
public class PickingJobFacets
{
	public static PickingJobFacets EMPTY = PickingJobFacets.builder().build();

	@NonNull
	@Singular
	ImmutableSet<CustomerFacet> customers;

	@Value(staticConstructor = "of")
	public static class CustomerFacet
	{
		@NonNull BPartnerId bpartnerId;
		@NonNull String customerBPValue;
		@NonNull String customerName;
	}

	@NonNull
	@Singular
	ImmutableSet<DeliveryDayFacet> deliveryDays;

	@Value(staticConstructor = "of")
	public static class DeliveryDayFacet
	{
		@NonNull LocalDate deliveryDate;
		@NonNull ZoneId timeZone;
	}

	@NonNull
	@Singular
	ImmutableSet<HandoverLocationFacet> handoverLocations;

	@Value(staticConstructor = "of")
	public static class HandoverLocationFacet
	{
		@NonNull BPartnerLocationId bPartnerLocationId;
		@NonNull String renderedAddress;
	}

	public static Collector<Packageable, ?, PickingJobFacets> collectFromPackageables(@NonNull final CollectingParameters parameters)
	{
		final Supplier<PickingJobFacetsBuilder> supplier = PickingJobFacets::builder;
		final BiConsumer<PickingJobFacetsBuilder, Packageable> accumulator = (builder, item) -> {
			extractCustomerFacet(item, parameters).ifPresent(builder::customer);
			extractDeliveryDateFacet(item, parameters).ifPresent(builder::deliveryDay);
			extractHandoverLocation(item, parameters).ifPresent(builder::handoverLocation);
		};
		final BinaryOperator<PickingJobFacetsBuilder> combiner = (builder1, builder2) -> {
			final PickingJobFacets facetsToAdd = builder2.build();
			builder1.customers(facetsToAdd.getCustomers());
			builder1.deliveryDays(facetsToAdd.getDeliveryDays());
			builder1.handoverLocations(facetsToAdd.getHandoverLocations());
			return builder1;
		};
		final Function<PickingJobFacetsBuilder, PickingJobFacets> finisher = PickingJobFacetsBuilder::build;

		return Collector.of(supplier, accumulator, combiner, finisher);
	}

	@NonNull
	private static Optional<PickingJobFacets.CustomerFacet> extractCustomerFacet(
			@NonNull final Packageable packageable,
			@NonNull final CollectingParameters collectingParameters)
	{
		if (!collectingParameters.isCollectBPartner())
		{
			return Optional.empty();
		}

		return Optional.of(PickingJobFacets.CustomerFacet.of(packageable.getCustomerId(),
															 packageable.getCustomerBPValue(),
															 packageable.getCustomerName()));
	}

	@NonNull
	private static Optional<PickingJobFacets.DeliveryDayFacet> extractDeliveryDateFacet(
			@NonNull final Packageable packageable,
			@NonNull final CollectingParameters collectingParameters)
	{
		if (!collectingParameters.isCollectDeliveryDate())
		{
			return Optional.empty();
		}

		final InstantAndOrgId deliveryDate = packageable.getDeliveryDate();
		//final ZoneId timeZone = orgDAO.getTimeZone(deliveryDate.getOrgId());
		final ZoneId timeZone = SystemTime.zoneId();
		final LocalDate deliveryDay = deliveryDate.toZonedDateTime(timeZone).toLocalDate();
		return Optional.of(PickingJobFacets.DeliveryDayFacet.of(deliveryDay, timeZone));
	}

	@NonNull
	private static Optional<HandoverLocationFacet> extractHandoverLocation(
			@NonNull final Packageable packageable,
			@NonNull final CollectingParameters collectingParameters)
	{
		if (!collectingParameters.isCollectHandoverLocation())
		{
			return Optional.empty();
		}
		final String renderedAddress = collectingParameters.getAddressProvider().getAddress(packageable.getHandoverLocationId());

		return Optional.of(HandoverLocationFacet.of(packageable.getHandoverLocationId(), renderedAddress));
	}

	@Value
	@Builder
	public static class CollectingParameters
	{
		@NonNull RenderedAddressProvider addressProvider;
		boolean collectBPartner;
		boolean collectHandoverLocation;
		boolean collectDeliveryDate;
	}
}
