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
import de.metas.externalsystem.ExternalSystemId;
import de.metas.common.util.time.SystemTime;
import de.metas.document.DocumentNoFilter;
import de.metas.handlingunits.picking.job.model.facets.PickingJobFacet;
import de.metas.handlingunits.picking.job.model.facets.PickingJobFacetGroup;
import de.metas.handlingunits.picking.job.model.facets.PickingJobFacetHandlers;
import de.metas.picking.api.PackageableQuery;
import de.metas.picking.api.PackageableQuery.PackageableQueryBuilder;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleIdSet;
import de.metas.picking.job_schedule.model.PickingJobScheduleQuery;
import de.metas.product.ResolvedScannedProductCodes;
import de.metas.user.UserId;
import de.metas.workplace.WorkplaceId;
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
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Value
@Builder
public class PickingJobQuery
{
	@NonNull UserId userId;
	@NonNull @Builder.Default ShipmentScheduleAndJobScheduleIdSet excludeScheduleIds = ShipmentScheduleAndJobScheduleIdSet.EMPTY;
	@Nullable Facets facets;
	@NonNull @Builder.Default @Getter(AccessLevel.NONE) ImmutableSet<BPartnerId> onlyCustomerIds = ImmutableSet.of();
	@Nullable WorkplaceId scheduledForWorkplaceId;
	@Nullable WarehouseId warehouseId;
	@Nullable DocumentNoFilter salesOrderDocumentNo;
	@Nullable ResolvedScannedProductCodes scannedProductCodes;
	@NonNull ZonedDateTime currentTime = SystemTime.asZonedDateTime();

	@NonNull
	public Set<BPartnerId> getOnlyCustomerIdsEffective()
	{
		if (onlyCustomerIds.isEmpty())
		{
			return facets != null ? facets.getCustomerIds() : ImmutableSet.of();
		}
		else
		{
			if (facets != null && !facets.getCustomerIds().isEmpty())
			{
				return Sets.intersection(onlyCustomerIds, facets.getCustomerIds());
			}
			else
			{
				return onlyCustomerIds;
			}
		}
	}

	@NonNull
	public ImmutableSet<LocalDate> getPreparationDays()
	{
		return facets != null ? facets.getPreparationDays() : ImmutableSet.of();
	}

	@NonNull
	public ImmutableSet<ExternalSystemId> getExternalSystemIds()
	{
		return facets != null ? facets.getExternalSystemIds() : ImmutableSet.of();
	}

	public ImmutableSet<LocalDate> getDeliveryDays()
	{
		return facets != null ? facets.getDeliveryDays() : ImmutableSet.of();
	}

	@NonNull
	public ImmutableSet<BPartnerLocationId> getOnlyHandoverLocationIds()
	{
		return facets != null ? facets.getHandoverLocationIds() : ImmutableSet.of();
	}

	public PackageableQueryBuilder toPackageableQueryBuilder()
	{
		final PackageableQueryBuilder builder = PackageableQuery.builder()
				.onlyFromSalesOrder(true)
				.salesOrderDocumentNo(this.getSalesOrderDocumentNo())
				.lockedBy(this.getUserId())
				.includeNotLocked(true)
				.excludeLockedForProcessing(true)
				.excludeShipmentScheduleIds(excludeScheduleIds.getShipmentScheduleIdsWithoutJobSchedules())
				.excludeFullyOnDraftShipment(true)
				.scannedProductCodes(this.getScannedProductCodes())
				.maximumFixedPreparationDate(currentTime)
				.orderBys(ImmutableSet.of(
						PackageableQuery.OrderBy.PriorityRule,
						PackageableQuery.OrderBy.PreparationDate,
						PackageableQuery.OrderBy.SetupPlaceNo_Descending,
						PackageableQuery.OrderBy.SalesOrderId,
						PackageableQuery.OrderBy.DeliveryBPLocationId,
						PackageableQuery.OrderBy.WarehouseTypeId));

		final Set<BPartnerId> onlyCustomerIds = this.getOnlyCustomerIdsEffective();
		if (!onlyCustomerIds.isEmpty())
		{
			builder.customerIds(onlyCustomerIds);
		}

		final ImmutableSet<LocalDate> deliveryDays = this.getDeliveryDays();
		if (!deliveryDays.isEmpty())
		{
			builder.deliveryDays(deliveryDays);
		}

		final ImmutableSet<LocalDate> preparationDays = this.getPreparationDays();
		if (!preparationDays.isEmpty())
		{
			builder.preparationDays(preparationDays);
		}

		final ImmutableSet<ExternalSystemId> externalSystemIds = this.getExternalSystemIds();
		if (!externalSystemIds.isEmpty())
		{
			builder.externalSystemIds(externalSystemIds);
		}

		final ImmutableSet<BPartnerLocationId> locationIds = this.getOnlyHandoverLocationIds();
		if (!locationIds.isEmpty())
		{
			builder.handoverLocationIds(locationIds);
		}

		final WarehouseId workplaceWarehouseId = this.getWarehouseId();
		if (workplaceWarehouseId != null)
		{
			builder.warehouseId(workplaceWarehouseId);
		}

		return builder;
	}

	public PickingJobScheduleQuery toPickingJobScheduleQuery()
	{
		if (this.scheduledForWorkplaceId == null)
		{
			throw new AdempiereException("Expected query to have scheduledForWorkplaceId set");
		}

		return PickingJobScheduleQuery.builder()
				.workplaceId(this.scheduledForWorkplaceId)
				.excludeJobScheduleIds(this.excludeScheduleIds.getJobScheduleIds())
				.isProcessed(false)
				.build();
	}

	public boolean isScheduledForWorkplaceOnly()
	{
		return this.scheduledForWorkplaceId != null;
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
		@NonNull @Singular ImmutableSet<LocalDate> preparationDays;
		@NonNull @Singular ImmutableSet<BPartnerLocationId> handoverLocationIds;
		@NonNull @Singular ImmutableSet<ExternalSystemId> externalSystemIds;

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
					builder.preparationDays(other.preparationDays);
					builder.handoverLocationIds(other.handoverLocationIds);
					builder.externalSystemIds(other.externalSystemIds);
				}
				return builder.build();
			}
		}

		public boolean isEmpty()
		{
			return customerIds.isEmpty() && deliveryDays.isEmpty() && preparationDays.isEmpty() && handoverLocationIds.isEmpty() && externalSystemIds.isEmpty();
		}

		public boolean isMatching(final PickingJobReference pickingJobReference)
		{
			return isCustomerMatching(pickingJobReference)
					&& isDeliveryDateMatching(pickingJobReference)
					&& isPreparationDateMatching(pickingJobReference)
					&& isHandoverLocationMatching(pickingJobReference)
					&& isExternalSystemMatching(pickingJobReference);
		}

		public boolean isMatching(final PickingJobFacet facet)
		{
			return PickingJobFacetHandlers.isMatching(facet, this);
		}

		private boolean isCustomerMatching(final PickingJobReference pickingJobReference) {return isCustomerMatching(pickingJobReference.getCustomerId());}

		private boolean isCustomerMatching(final BPartnerId customerId) {return customerIds.isEmpty() || customerIds.contains(customerId);}

		private boolean isDeliveryDateMatching(final PickingJobReference pickingJobReference)
		{
			final ZonedDateTime deliveryDate = pickingJobReference.getDeliveryDate();
			return deliveryDate != null && isDeliveryDateMatching(deliveryDate.toLocalDate());
		}

		private boolean isDeliveryDateMatching(final LocalDate deliveryDay) {return deliveryDays.isEmpty() || deliveryDays.contains(deliveryDay);}

		/**
		 * NOTE the ordering: the "no filter active" check comes FIRST, so a job carrying no preparation
		 * date is not excluded when the operator has selected nothing. Once a day IS selected the match is
		 * strict, matching the SQL-side filter in {@code PackagingDAO}. (The sibling
		 * {@link #isDeliveryDateMatching(PickingJobReference)} tests {@code != null} before consulting the
		 * filter, so an already-started job with no delivery date never reaches the launcher even with no
		 * filter active — a separate, pre-existing defect, deliberately not changed here.)
		 */
		private boolean isPreparationDateMatching(final PickingJobReference pickingJobReference)
		{
			if (preparationDays.isEmpty())
			{
				return true;
			}
			final ZonedDateTime preparationDate = pickingJobReference.getPreparationDate();
			return preparationDate != null && isPreparationDateMatching(preparationDate.toLocalDate());
		}

		private boolean isPreparationDateMatching(final LocalDate preparationDay) {return preparationDays.isEmpty() || preparationDays.contains(preparationDay);}

		private boolean isHandoverLocationMatching(final PickingJobReference pickingJobReference) {return isHandoverLocationMatching(Optional.ofNullable(pickingJobReference.getHandoverLocationId()).orElse(pickingJobReference.getDeliveryBPLocationId()));}

		/**
		 * Same ordering as {@link #isPreparationDateMatching(PickingJobReference)}: the "no filter active"
		 * check comes FIRST, so a job whose order came in through no external system is not excluded while
		 * the operator has selected nothing. Once a system IS selected the match is strict, matching the
		 * SQL-side filter in {@code PackagingDAO}.
		 */
		private boolean isExternalSystemMatching(final PickingJobReference pickingJobReference)
		{
			if (externalSystemIds.isEmpty())
			{
				return true;
			}
			final ExternalSystemId externalSystemId = pickingJobReference.getExternalSystemId();
			return externalSystemId != null && externalSystemIds.contains(externalSystemId);
		}

		private boolean isHandoverLocationMatching(final BPartnerLocationId handoverLocationId) {return handoverLocationIds.isEmpty() || handoverLocationIds.contains(handoverLocationId);}

		public Facets retainFacetsOfGroup(@NonNull final PickingJobFacetGroup group) {return PickingJobFacetHandlers.retainFacetsOfGroups(this, ImmutableSet.of(group));}

		public Set<Facets> toSingleElementFacets()
		{
			if (isEmpty())
			{
				return ImmutableSet.of();
			}

			return Streams.concat(
							customerIds.stream().map(customerId -> Facets.builder().customerId(customerId).build()),
							deliveryDays.stream().map(deliveryDay -> Facets.builder().deliveryDay(deliveryDay).build()),
							preparationDays.stream().map(preparationDay -> Facets.builder().preparationDay(preparationDay).build()),
							handoverLocationIds.stream().map(handoverLocationId -> Facets.builder().handoverLocationId(handoverLocationId).build()),
							externalSystemIds.stream().map(externalSystemId -> Facets.builder().externalSystemId(externalSystemId).build())
					)
					.collect(ImmutableSet.toImmutableSet());
		}
	}
}