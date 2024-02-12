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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.picking.api.Packageable;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collector;

@Value
@Builder
public class PickingJobFacets
{
	public static PickingJobFacets EMPTY = PickingJobFacets.builder().build();

	@NonNull @Builder.Default ImmutableSet<PickingJobFacet> facets = ImmutableSet.of();

	public static Collector<Packageable, ?, PickingJobFacets> collectFromPackageables(@NonNull final CollectingParameters parameters)
	{
		return PickingJobFacetsAccumulator.collect(parameters);
	}

	public <T extends PickingJobFacet, R> ImmutableList<R> toList(@NonNull final Class<T> type, @NonNull Function<T, R> mapper)
	{
		return facets.stream()
				.map(facet -> facet.asTypeOrNull(type))
				.filter(Objects::nonNull)
				.map(mapper)
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());
	}

	public interface PickingJobFacet
	{
		@NonNull PickingJobFacetGroup getGroup();

		boolean isActive();

		PickingJobFacet withActive(boolean isActive);

		@Nullable
		default <T extends PickingJobFacet> T asTypeOrNull(@NonNull Class<T> type) {return type.isInstance(this) ? type.cast(this) : null;}

		@NonNull
		default <T extends PickingJobFacet> T asType(@NonNull Class<T> type) {return type.cast(this);}
	}

	@Value(staticConstructor = "of")
	public static class CustomerFacet implements PickingJobFacet
	{
		@NonNull PickingJobFacetGroup group = PickingJobFacetGroup.CUSTOMER;
		@With boolean isActive;
		@NonNull BPartnerId bpartnerId;
		@NonNull String customerBPValue;
		@NonNull String customerName;
	}

	@Value(staticConstructor = "of")
	public static class DeliveryDayFacet implements PickingJobFacet
	{
		@NonNull PickingJobFacetGroup group = PickingJobFacetGroup.DELIVERY_DATE;
		@With boolean isActive;
		@NonNull LocalDate deliveryDate;
		@NonNull ZoneId timeZone;
	}

	@Value(staticConstructor = "of")
	public static class HandoverLocationFacet implements PickingJobFacet
	{
		@NonNull PickingJobFacetGroup group = PickingJobFacetGroup.HANDOVER_LOCATION;
		@With boolean isActive;
		@NonNull BPartnerLocationId bPartnerLocationId;
		@NonNull String renderedAddress;
	}

	@Value
	@Builder
	public static class CollectingParameters
	{
		@NonNull RenderedAddressProvider addressProvider;
		@NonNull ImmutableList<PickingJobFacetGroup> groupsInOrder;
		@NonNull PickingJobQuery.Facets activeFacets;
	}
}
