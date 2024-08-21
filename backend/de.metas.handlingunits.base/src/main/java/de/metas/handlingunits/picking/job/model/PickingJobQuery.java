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
import com.google.common.collect.Sets;
import com.google.common.collect.Streams;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.document.DocumentNoFilter;
import de.metas.inout.ShipmentScheduleId;
import de.metas.user.UserId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Value
@Builder
public class PickingJobQuery
{
	@NonNull UserId userId;
	@NonNull @Builder.Default ImmutableSet<ShipmentScheduleId> excludeShipmentScheduleIds = ImmutableSet.of();
	@Nullable Facets facets;
	@NonNull @Builder.Default @Getter(AccessLevel.NONE) ImmutableSet<BPartnerId> onlyBPartnerIds = ImmutableSet.of();
	@Nullable WarehouseId warehouseId;
	@Nullable DocumentNoFilter salesOrderDocumentNo;

	@NonNull
	public Set<BPartnerId> getOnlyBPartnerIdsEffective()
	{
		if (onlyBPartnerIds.isEmpty())
		{
			return facets != null ? facets.getCustomerIds() : ImmutableSet.of();
		}
		else
		{
			if (facets != null && !facets.getCustomerIds().isEmpty())
			{
				return Sets.intersection(onlyBPartnerIds, facets.getCustomerIds());
			}
			else
			{
				return onlyBPartnerIds;
			}
		}
	}

	@NonNull
	public ImmutableSet<LocalDate> getDeliveryDays()
	{
		return facets != null ? facets.getDeliveryDays() : ImmutableSet.of();
	}

	@NonNull
	public ImmutableSet<BPartnerLocationId> getOnlyHandoverLocationIds()
	{
		return facets != null ? facets.getHandoverLocationIds() : ImmutableSet.of();
	}

	//
	//
	//
	//
	//

	@Value
	@Builder(toBuilder = true)
	public static class Facets
	{
		public static final Facets EMPTY = builder().build();

		@NonNull @Singular ImmutableSet<BPartnerId> customerIds;
		@NonNull @Singular ImmutableSet<LocalDate> deliveryDays;
		@NonNull @Singular ImmutableSet<BPartnerLocationId> handoverLocationIds;

		public static Facets of(@NonNull PickingJobFacets.PickingJobFacet facet)
		{
			final PickingJobFacetGroup group = facet.getGroup();
			switch (group)
			{
				case CUSTOMER:
					return builder().customerId(facet.asType(PickingJobFacets.CustomerFacet.class).getBpartnerId()).build();
				case DELIVERY_DATE:
					return builder().deliveryDay(facet.asType(PickingJobFacets.DeliveryDayFacet.class).getDeliveryDate()).build();
				case HANDOVER_LOCATION:
					return builder().handoverLocationId(facet.asType(PickingJobFacets.HandoverLocationFacet.class).getBPartnerLocationId()).build();
				default:
					throw new AdempiereException("Unknown group: " + group);
			}
		}

		public Facets add(@NonNull final Facets other)
		{
			return addAll(ImmutableSet.of(other));
		}

		public Facets addAll(@NonNull final Collection<Facets> others)
		{
			if (others.isEmpty())
			{
				return this;
			}
			else
			{

				final FacetsBuilder builder = toBuilder();
				for (final Facets other : others)
				{
					builder.customerIds(other.customerIds);
					builder.deliveryDays(other.deliveryDays);
					builder.handoverLocationIds(other.handoverLocationIds);
				}
				return builder.build();
			}
		}

		public boolean isEmpty()
		{
			return customerIds.isEmpty() && deliveryDays.isEmpty() && handoverLocationIds.isEmpty();
		}

		public boolean isMatching(final PickingJobReference pickingJobReference)
		{
			return isCustomerMatching(pickingJobReference)
					&& isDeliveryDateMatching(pickingJobReference)
					&& isHandoverLocationMatching(pickingJobReference);
		}

		public boolean isMatching(final PickingJobFacets.PickingJobFacet facet)
		{
			final PickingJobFacetGroup group = facet.getGroup();
			switch (group)
			{
				case CUSTOMER:
					return customerIds.contains(facet.asType(PickingJobFacets.CustomerFacet.class).getBpartnerId());
				case DELIVERY_DATE:
					return deliveryDays.contains(facet.asType(PickingJobFacets.DeliveryDayFacet.class).getDeliveryDate());
				case HANDOVER_LOCATION:
					return handoverLocationIds.contains(facet.asType(PickingJobFacets.HandoverLocationFacet.class).getBPartnerLocationId());
				default:
					throw new AdempiereException("Unknown group: " + group);
			}
		}

		private boolean isCustomerMatching(final PickingJobReference pickingJobReference) {return isCustomerMatching(pickingJobReference.getCustomerId());}

		private boolean isCustomerMatching(final BPartnerId customerId) {return customerIds.isEmpty() || customerIds.contains(customerId);}

		private boolean isDeliveryDateMatching(final PickingJobReference pickingJobReference) {return isDeliveryDateMatching(pickingJobReference.getDeliveryDate().toLocalDate());}

		private boolean isDeliveryDateMatching(final LocalDate deliveryDay) {return deliveryDays.isEmpty() || deliveryDays.contains(deliveryDay);}

		private boolean isHandoverLocationMatching(final PickingJobReference pickingJobReference) {return isHandoverLocationMatching(Optional.ofNullable(pickingJobReference.getHandoverLocationId()).orElse(pickingJobReference.getDeliveryLocationId()));}

		private boolean isHandoverLocationMatching(final BPartnerLocationId handoverLocationId) {return handoverLocationIds.isEmpty() || handoverLocationIds.contains(handoverLocationId);}

		public Facets retainFacetsOfGroup(@NonNull final PickingJobFacetGroup group) {return retainFacetsOfGroups(ImmutableSet.of(group));}

		public Facets retainFacetsOfGroups(final Collection<PickingJobFacetGroup> groups)
		{
			if (groups.isEmpty())
			{
				return EMPTY;
			}

			final FacetsBuilder builder = Facets.builder();

			for (final PickingJobFacetGroup group : groups)
			{
				switch (group)
				{
					case CUSTOMER:
						builder.customerIds(this.customerIds);
						break;
					case DELIVERY_DATE:
						builder.deliveryDays(this.deliveryDays);
						break;
					case HANDOVER_LOCATION:
						builder.handoverLocationIds(this.handoverLocationIds);
						break;
					default:
						throw new AdempiereException("Unknown group: " + group);
				}
			}

			final Facets result = builder.build();
			if (result.isEmpty())
			{
				return EMPTY;
			}
			else if (result.equals(this))
			{
				return this;
			}
			else
			{
				return result;
			}
		}

		public Set<Facets> toSingleElementFacets()
		{
			if (isEmpty())
			{
				return ImmutableSet.of();
			}

			return Streams.concat(
							customerIds.stream().map(customerId -> Facets.builder().customerId(customerId).build()),
							deliveryDays.stream().map(deliveryDay -> Facets.builder().deliveryDay(deliveryDay).build()),
							handoverLocationIds.stream().map(handoverLocationId -> Facets.builder().handoverLocationId(handoverLocationId).build())
					)
					.collect(ImmutableSet.toImmutableSet());
		}
	}
}