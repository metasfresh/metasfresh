/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2024 metas GmbH
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
import com.google.common.collect.ImmutableSetMultimap;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.common.util.time.SystemTime;
import de.metas.organization.InstantAndOrgId;
import de.metas.picking.api.Packageable;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;

class PickingJobFacetsAccumulator
{
	@NonNull private final RenderedAddressProvider renderedAddressProvider;
	@NonNull private final ImmutableList<PickingJobFacetGroup> groupsInOrder;
	@NonNull private final PickingJobQuery.Facets activeFacets;

	@NonNull private final HashSet<FacetAwareItem> items = new HashSet<>();

	public PickingJobFacetsAccumulator(@NonNull final PickingJobFacets.CollectingParameters parameters)
	{
		this.renderedAddressProvider = parameters.getAddressProvider();
		this.groupsInOrder = parameters.getGroupsInOrder();
		this.activeFacets = parameters.getActiveFacets();
	}

	public static Collector<Packageable, ?, PickingJobFacets> collect(@NonNull final PickingJobFacets.CollectingParameters parameters)
	{
		return Collector.of(
				() -> new PickingJobFacetsAccumulator(parameters),
				PickingJobFacetsAccumulator::accumulate,
				PickingJobFacetsAccumulator::combine,
				PickingJobFacetsAccumulator::toPickingJobFacets
		);
	}

	void accumulate(@NonNull final Packageable packageable)
	{
		final FacetAwareItem item = extractFacetAwareItem(packageable);
		if (!item.hasFacets())
		{
			return;
		}

		items.add(item);
	}

	PickingJobFacetsAccumulator combine(@NonNull final PickingJobFacetsAccumulator other)
	{
		this.items.addAll(other.items);
		return this;
	}

	PickingJobFacets toPickingJobFacets()
	{
		@NonNull final HashSet<PickingJobFacets.PickingJobFacet> collectedFacets = new HashSet<>();

		//
		// Iterate groups in order and collect facets from each group
		PickingJobQuery.Facets activeFacetsOfPreviousGroups = PickingJobQuery.Facets.EMPTY;
		for (final PickingJobFacetGroup group : groupsInOrder)
		{
			final Set<PickingJobQuery.Facets> activeFacetsOfThisGroup = activeFacets.retainFacetsOfGroup(group).toSingleElementFacets();
			final HashSet<PickingJobQuery.Facets> activeFacetsOfThisGroupEffective = new HashSet<>();

			//
			// Iterate items and collect facets from them
			@NonNull final HashSet<PickingJobFacets.PickingJobFacet> collectedGroupFacets = new HashSet<>();
			for (final FacetAwareItem item : items)
			{
				if (!item.isMatching(activeFacetsOfPreviousGroups))
				{
					continue;
				}

				final ImmutableSet<PickingJobFacets.PickingJobFacet> itemFacets = item.getFacets(group);
				for (final PickingJobFacets.PickingJobFacet facet : itemFacets)
				{
					boolean isActive = false;
					for (PickingJobQuery.Facets activeFacetOfThisGroupPart : activeFacetsOfThisGroup)
					{
						if (activeFacetOfThisGroupPart.isMatching(facet))
						{
							isActive = true;
							activeFacetsOfThisGroupEffective.add(activeFacetOfThisGroupPart);
						}
					}

					collectedGroupFacets.add(facet.withActive(isActive));
				}
			}

			//
			// In case we found only one facet in this group, consider it active
			// NOTE: commented out because even if is nice in some cases in other cases it's annoying
			// if (collectedGroupFacets.size() == 1)
			// {
			// 	final PickingJobFacets.PickingJobFacet facet = collectedGroupFacets.iterator().next().withActive(true);
			// 	collectedGroupFacets.clear();
			// 	collectedGroupFacets.add(facet);
			//
			// 	activeFacetsOfThisGroupEffective.add(PickingJobQuery.Facets.of(facet));
			// }

			//
			// Add collected facets of this group to all collected facets
			collectedFacets.addAll(collectedGroupFacets);

			//
			// If user didn't activate any facet on this group, stop collecting facets here.
			// Let him/her active some facets on this group, and then we can continue collecting for next groups
			if (collectedGroupFacets.stream().noneMatch(PickingJobFacets.PickingJobFacet::isActive))
			{
				break;
			}

			activeFacetsOfPreviousGroups = activeFacetsOfPreviousGroups.addAll(activeFacetsOfThisGroupEffective);
		}

		return PickingJobFacets.builder().facets(ImmutableSet.copyOf(collectedFacets)).build();
	}

	private FacetAwareItem extractFacetAwareItem(@NonNull final Packageable packageable)
	{
		final ImmutableSet.Builder<PickingJobFacets.PickingJobFacet> facets = ImmutableSet.builder();

		if (groupsInOrder.contains(PickingJobFacetGroup.CUSTOMER))
		{
			facets.add(PickingJobFacets.CustomerFacet.of(
					false,
					packageable.getCustomerId(),
					packageable.getCustomerBPValue(),
					packageable.getCustomerName()));
		}
		if (groupsInOrder.contains(PickingJobFacetGroup.DELIVERY_DATE))
		{
			final InstantAndOrgId deliveryDate = packageable.getDeliveryDate();
			//final ZoneId timeZone = orgDAO.getTimeZone(deliveryDate.getOrgId());
			final ZoneId timeZone = SystemTime.zoneId();
			final LocalDate deliveryDay = deliveryDate.toZonedDateTime(timeZone).toLocalDate();
			facets.add(PickingJobFacets.DeliveryDayFacet.of(false, deliveryDay, timeZone));
		}
		if (groupsInOrder.contains(PickingJobFacetGroup.HANDOVER_LOCATION))
		{
			final String renderedAddress = renderedAddressProvider.getAddress(packageable.getHandoverLocationId());
			facets.add(PickingJobFacets.HandoverLocationFacet.of(false, packageable.getHandoverLocationId(), renderedAddress));
		}

		return FacetAwareItem.ofList(facets.build());
	}

	//
	//
	//

	@EqualsAndHashCode
	@ToString
	private static class FacetAwareItem
	{
		private static final FacetAwareItem EMPTY = new FacetAwareItem(ImmutableSet.of());

		private final ImmutableSetMultimap<PickingJobFacetGroup, PickingJobFacets.PickingJobFacet> facetsByGroup;
		private final ImmutableSet<BPartnerId> customerIds;
		private final ImmutableSet<LocalDate> deliveryDays;
		private final ImmutableSet<BPartnerLocationId> handoverLocationIds;

		private FacetAwareItem(@NonNull final Set<PickingJobFacets.PickingJobFacet> facets)
		{
			this.facetsByGroup = facets.stream().collect(ImmutableSetMultimap.toImmutableSetMultimap(PickingJobFacets.PickingJobFacet::getGroup, facet -> facet));
			this.customerIds = extract(facets, PickingJobFacets.CustomerFacet.class, PickingJobFacets.CustomerFacet::getBpartnerId);
			this.deliveryDays = extract(facets, PickingJobFacets.DeliveryDayFacet.class, PickingJobFacets.DeliveryDayFacet::getDeliveryDate);
			this.handoverLocationIds = extract(facets, PickingJobFacets.HandoverLocationFacet.class, PickingJobFacets.HandoverLocationFacet::getBPartnerLocationId);
		}

		private static <T extends PickingJobFacets.PickingJobFacet, R> ImmutableSet<R> extract(Set<PickingJobFacets.PickingJobFacet> facets, Class<T> type, Function<T, R> mapper)
		{
			if (facets.isEmpty())
			{
				return ImmutableSet.of();
			}
			return facets.stream()
					.map(facet -> facet.asTypeOrNull(type))
					.filter(Objects::nonNull)
					.map(facet -> mapper.apply(facet.asType(type)))
					.filter(Objects::nonNull)
					.collect(ImmutableSet.toImmutableSet());
		}

		public static FacetAwareItem ofList(final Set<PickingJobFacets.PickingJobFacet> facets)
		{
			if (facets.isEmpty())
			{
				return EMPTY;
			}
			return new FacetAwareItem(facets);
		}

		public boolean hasFacets() {return !facetsByGroup.isEmpty();}

		public boolean isMatching(@NonNull final PickingJobQuery.Facets query)
		{
			return isMatching(customerIds, query.getCustomerIds())
					&& isMatching(deliveryDays, query.getDeliveryDays())
					&& isMatching(handoverLocationIds, query.getHandoverLocationIds());
		}

		private static <T> boolean isMatching(final Set<T> values, final Set<T> requiredValues)
		{
			if (requiredValues.isEmpty())
			{
				return true;
			}

			return values.stream().anyMatch(requiredValues::contains);
		}

		public ImmutableSet<PickingJobFacets.PickingJobFacet> getFacets(@NonNull final PickingJobFacetGroup group)
		{
			return facetsByGroup.get(group);
		}
	}
}
