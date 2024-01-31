/*
 * #%L
 * de.metas.picking.rest-api
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

package de.metas.picking.workflow.handlers;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.picking.job.model.PickingJobFacets;
import de.metas.handlingunits.picking.job.model.PickingJobFacetsQuery;
import de.metas.i18n.TranslatableStrings;
import de.metas.picking.config.PickingJobFilterOption;
import de.metas.util.JSONObjectMapper;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacet;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacetGroup;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacetGroupId;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacetGroupList;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacetId;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Set;

@UtilityClass
class PickingJobFacetsUtils
{
	private static final WorkflowLaunchersFacetGroupId CUSTOMERS_FACET_GROUP_ID = WorkflowLaunchersFacetGroupId.ofString(PickingJobFilterOption.CUSTOMER.getCode());
	private static final WorkflowLaunchersFacetGroupId DELIVERY_DAY_GROUP_ID = WorkflowLaunchersFacetGroupId.ofString(PickingJobFilterOption.DELIVERY_DATE.getCode());
	private static final WorkflowLaunchersFacetGroupId HANDOVER_LOCATION_GROUP_ID = WorkflowLaunchersFacetGroupId.ofString(PickingJobFilterOption.HANDOVER_LOCATION.getCode());

	public static PickingJobFacetsQuery toPickingJobFacetsQuery(@Nullable final Set<WorkflowLaunchersFacetId> facetIds)
	{
		if (facetIds == null || facetIds.isEmpty())
		{
			return PickingJobFacetsQuery.EMPTY;
		}

		final PickingJobFacetsQuery.PickingJobFacetsQueryBuilder builder = PickingJobFacetsQuery.builder();
		facetIds.forEach(facetId -> {
			if (facetId.isGroupId(CUSTOMERS_FACET_GROUP_ID))
			{
				builder.customerId(facetId.getAsId(BPartnerId.class));
			}
			else if (facetId.isGroupId(DELIVERY_DAY_GROUP_ID))
			{
				builder.deliveryDay(facetId.getAsLocalDate());
			}
			else if (facetId.isGroupId(HANDOVER_LOCATION_GROUP_ID))
			{
				builder.handoverLocationId(facetId.deserializeTo(BPartnerLocationId.class));
			}
		});

		return builder.build();
	}

	public WorkflowLaunchersFacetGroupList toWorkflowLaunchersFacetGroupList(@NonNull final PickingJobFacets pickingFacets)
	{
		final ArrayList<WorkflowLaunchersFacetGroup> groups = new ArrayList<>();

		// NOTE: if a group has only one facet that's like not filtering at all, so having that facet in the result is pointless.

		if (pickingFacets.getCustomers().size() > 1)
		{
			groups.add(WorkflowLaunchersFacetGroup.builder()
							   .id(CUSTOMERS_FACET_GROUP_ID)
							   .caption(TranslatableStrings.adRefList(PickingJobFilterOption.PICKING_JOB_FILTER_OPTION_REFERENCE_ID, PickingJobFilterOption.CUSTOMER.getCode()))
							   .facets(pickingFacets.getCustomers().stream()
											   .map(PickingJobFacetsUtils::toWorkflowLaunchersFacet)
											   .distinct()
											   .collect(ImmutableList.toImmutableList()))
							   .build());
		}
		if (pickingFacets.getDeliveryDays().size() > 1)
		{
			groups.add(WorkflowLaunchersFacetGroup.builder()
							   .id(DELIVERY_DAY_GROUP_ID)
							   .caption(TranslatableStrings.adRefList(PickingJobFilterOption.PICKING_JOB_FILTER_OPTION_REFERENCE_ID, PickingJobFilterOption.DELIVERY_DATE.getCode()))
							   .facets(pickingFacets.getDeliveryDays().stream()
											   .map(PickingJobFacetsUtils::toWorkflowLaunchersFacet)
											   .distinct()
											   .collect(ImmutableList.toImmutableList()))
							   .build());
		}
		if (pickingFacets.getHandoverLocations().size() > 1)
		{
			groups.add(WorkflowLaunchersFacetGroup.builder()
							   .id(HANDOVER_LOCATION_GROUP_ID)
							   .caption(TranslatableStrings.adRefList(PickingJobFilterOption.PICKING_JOB_FILTER_OPTION_REFERENCE_ID, PickingJobFilterOption.HANDOVER_LOCATION.getCode()))
							   .facets(pickingFacets.getHandoverLocations().stream()
											   .map(PickingJobFacetsUtils::toWorkflowLaunchersFacet)
											   .distinct()
											   .collect(ImmutableList.toImmutableList()))
							   .build());
		}

		return WorkflowLaunchersFacetGroupList.ofList(groups);
	}

	@NonNull
	private static WorkflowLaunchersFacet toWorkflowLaunchersFacet(@NonNull final PickingJobFacets.CustomerFacet customer)
	{
		return WorkflowLaunchersFacet.builder()
				.facetId(WorkflowLaunchersFacetId.ofId(CUSTOMERS_FACET_GROUP_ID, customer.getBpartnerId()))
				.caption(TranslatableStrings.anyLanguage(customer.getCustomerBPValue() + " " + customer.getCustomerName()))
				.build();
	}

	@NonNull
	private static WorkflowLaunchersFacet toWorkflowLaunchersFacet(@NonNull final PickingJobFacets.DeliveryDayFacet deliveryDay)
	{
		return WorkflowLaunchersFacet.builder()
				.facetId(WorkflowLaunchersFacetId.ofLocalDate(DELIVERY_DAY_GROUP_ID, deliveryDay.getDeliveryDate()))
				.caption(TranslatableStrings.date(deliveryDay.getDeliveryDate()))
				.build();
	}

	@NonNull
	private static WorkflowLaunchersFacet toWorkflowLaunchersFacet(@NonNull final PickingJobFacets.HandoverLocationFacet handoverLocation)
	{
		return WorkflowLaunchersFacet.builder()
				.facetId(WorkflowLaunchersFacetId.ofString(
						HANDOVER_LOCATION_GROUP_ID,
						JSONObjectMapper.forClass(BPartnerLocationId.class).writeValueAsString(handoverLocation.getBPartnerLocationId())))
				.caption(TranslatableStrings.anyLanguage(handoverLocation.getRenderedAddress()))
				.build();
	}
}
