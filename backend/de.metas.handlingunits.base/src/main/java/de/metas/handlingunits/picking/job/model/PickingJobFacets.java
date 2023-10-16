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
import de.metas.common.util.time.SystemTime;
import de.metas.organization.InstantAndOrgId;
import de.metas.picking.api.Packageable;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

@Value
@Builder
public class PickingJobFacets
{
	@NonNull @Singular ImmutableSet<CustomerFacet> customers;

	@Value(staticConstructor = "of")
	public static class CustomerFacet
	{
		@NonNull BPartnerId bpartnerId;
		@NonNull String customerBPValue;
		@NonNull String customerName;
	}

	@NonNull @Singular ImmutableSet<DeliveryDayFacet> deliveryDays;

	@Value(staticConstructor = "of")
	public static class DeliveryDayFacet
	{
		@NonNull LocalDate deliveryDate;
		@NonNull ZoneId timeZone;
	}

	public static Collector<Packageable, ?, PickingJobFacets> collectFromPackageables()
	{
		final Supplier<PickingJobFacetsBuilder> supplier = PickingJobFacets::builder;
		final BiConsumer<PickingJobFacetsBuilder, Packageable> accumulator = (builder, item) -> {
			builder.customer(extractCustomerFacet(item));
			builder.deliveryDay(extractDeliveryDateFacet(item));
		};
		final BinaryOperator<PickingJobFacetsBuilder> combiner = (builder1, builder2) -> {
			final PickingJobFacets facetsToAdd = builder2.build();
			builder1.customers(facetsToAdd.getCustomers());
			builder1.deliveryDays(facetsToAdd.getDeliveryDays());
			return builder1;
		};
		final Function<PickingJobFacetsBuilder, PickingJobFacets> finisher = PickingJobFacetsBuilder::build;

		return Collector.of(supplier, accumulator, combiner, finisher);
	}

	private static PickingJobFacets.CustomerFacet extractCustomerFacet(final Packageable packageable)
	{
		return PickingJobFacets.CustomerFacet.of(packageable.getCustomerId(), packageable.getCustomerBPValue(), packageable.getCustomerName());
	}

	private static PickingJobFacets.DeliveryDayFacet extractDeliveryDateFacet(final Packageable packageable)
	{
		final InstantAndOrgId deliveryDate = packageable.getDeliveryDate();
		//final ZoneId timeZone = orgDAO.getTimeZone(deliveryDate.getOrgId());
		final ZoneId timeZone = SystemTime.zoneId();
		final LocalDate deliveryDay = deliveryDate.toZonedDateTime(timeZone).toLocalDate();
		return PickingJobFacets.DeliveryDayFacet.of(deliveryDay, timeZone);
	}

}
