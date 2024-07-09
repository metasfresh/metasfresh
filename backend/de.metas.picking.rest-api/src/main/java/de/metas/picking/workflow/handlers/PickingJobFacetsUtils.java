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

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.picking.job.model.PickingJobFacetGroup;
import de.metas.handlingunits.picking.job.model.PickingJobFacets;
import de.metas.handlingunits.picking.job.model.PickingJobQuery;
import de.metas.i18n.TranslatableStrings;
import de.metas.picking.config.MobileUIPickingUserProfile;
import de.metas.util.Check;
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
import java.util.Collection;
import java.util.List;
import java.util.Set;

@UtilityClass
public class PickingJobFacetsUtils
{
	private final ImmutableBiMap<PickingJobFacetGroup, WorkflowLaunchersFacetGroupId> facetGroup2facetId = ImmutableBiMap.<PickingJobFacetGroup, WorkflowLaunchersFacetGroupId>builder()
			.put(PickingJobFacetGroup.CUSTOMER, WorkflowLaunchersFacetGroupId.ofString(PickingJobFacetGroup.CUSTOMER.getCode()))
			.put(PickingJobFacetGroup.DELIVERY_DATE, WorkflowLaunchersFacetGroupId.ofString(PickingJobFacetGroup.DELIVERY_DATE.getCode()))
			.put(PickingJobFacetGroup.HANDOVER_LOCATION, WorkflowLaunchersFacetGroupId.ofString(PickingJobFacetGroup.HANDOVER_LOCATION.getCode()))
			.build();

	public static PickingJobQuery.Facets toPickingJobFacetsQuery(@Nullable final Set<WorkflowLaunchersFacetId> facetIds)
	{
		if (facetIds == null || facetIds.isEmpty())
		{
			return PickingJobQuery.Facets.EMPTY;
		}

		final PickingJobQuery.Facets.FacetsBuilder builder = PickingJobQuery.Facets.builder();
		facetIds.forEach(facetId -> {
			final PickingJobFacetGroup group = toPickingJobFacetGroup(facetId.getGroupId());
			switch (group)
			{
				case CUSTOMER:
					builder.customerId(facetId.getAsId(BPartnerId.class));
					break;
				case DELIVERY_DATE:
					builder.deliveryDay(facetId.getAsLocalDate());
					break;
				case HANDOVER_LOCATION:
					builder.handoverLocationId(facetId.deserializeTo(BPartnerLocationId.class));
					break;
			}
		});

		return builder.build();
	}

	public WorkflowLaunchersFacetGroupList toWorkflowLaunchersFacetGroupList(
			@NonNull final PickingJobFacets pickingFacets,
			@NonNull final MobileUIPickingUserProfile profile)
	{
		final ArrayList<WorkflowLaunchersFacetGroup> groups = new ArrayList<>();
		for (final PickingJobFacetGroup filterOption : profile.getFilterGroupsInOrder())
		{
			final WorkflowLaunchersFacetGroup group;
			switch (filterOption)
			{
				case CUSTOMER:
				{
					group = toWorkflowLaunchersFacetGroupFromCustomers(pickingFacets);
					break;
				}
				case DELIVERY_DATE:
				{
					group = toWorkflowLaunchersFacetGroupFromDeliveryDays(pickingFacets);
					break;
				}
				case HANDOVER_LOCATION:
				{
					group = toWorkflowLaunchersFacetGroupFromHandoverLocations(pickingFacets);
					break;
				}
				default:
				{
					group = null;
				}
			}

			// NOTE: if a group has only one facet that's like not filtering at all, so having that facet in the result is pointless.
			if (group != null && !group.isEmpty())
			{
				groups.add(group);
			}
		}

		return WorkflowLaunchersFacetGroupList.ofList(groups);
	}

	private static WorkflowLaunchersFacetGroup toWorkflowLaunchersFacetGroupFromCustomers(@NonNull final PickingJobFacets facets)
	{
		return WorkflowLaunchersFacetGroup.builder()
				.id(toWorkflowLaunchersFacetGroupId(PickingJobFacetGroup.CUSTOMER))
				.caption(TranslatableStrings.adRefList(PickingJobFacetGroup.PICKING_JOB_FILTER_OPTION_REFERENCE_ID, PickingJobFacetGroup.CUSTOMER))
				.facets(facets.toList(PickingJobFacets.CustomerFacet.class, PickingJobFacetsUtils::toWorkflowLaunchersFacet))
				.build();
	}

	@NonNull
	private static WorkflowLaunchersFacet toWorkflowLaunchersFacet(@NonNull final PickingJobFacets.CustomerFacet facet)
	{
		return WorkflowLaunchersFacet.builder()
				.facetId(WorkflowLaunchersFacetId.ofId(toWorkflowLaunchersFacetGroupId(PickingJobFacetGroup.CUSTOMER), facet.getBpartnerId()))
				.caption(TranslatableStrings.anyLanguage(facet.getCustomerBPValue() + " " + facet.getCustomerName()))
				.isActive(facet.isActive())
				.build();
	}

	private static WorkflowLaunchersFacetGroup toWorkflowLaunchersFacetGroupFromDeliveryDays(@NonNull final PickingJobFacets facets)
	{
		return WorkflowLaunchersFacetGroup.builder()
				.id(toWorkflowLaunchersFacetGroupId(PickingJobFacetGroup.DELIVERY_DATE))
				.caption(TranslatableStrings.adRefList(PickingJobFacetGroup.PICKING_JOB_FILTER_OPTION_REFERENCE_ID, PickingJobFacetGroup.DELIVERY_DATE))
				.facets(facets.toList(PickingJobFacets.DeliveryDayFacet.class, PickingJobFacetsUtils::toWorkflowLaunchersFacet))
				.build();
	}

	@NonNull
	private static WorkflowLaunchersFacet toWorkflowLaunchersFacet(@NonNull final PickingJobFacets.DeliveryDayFacet facet)
	{
		return WorkflowLaunchersFacet.builder()
				.facetId(WorkflowLaunchersFacetId.ofLocalDate(toWorkflowLaunchersFacetGroupId(PickingJobFacetGroup.DELIVERY_DATE), facet.getDeliveryDate()))
				.caption(TranslatableStrings.date(facet.getDeliveryDate()))
				.sortNo(facet.getDeliveryDate().atStartOfDay(facet.getTimeZone()).toInstant().toEpochMilli())
				.isActive(facet.isActive())
				.build();
	}

	private static WorkflowLaunchersFacetGroup toWorkflowLaunchersFacetGroupFromHandoverLocations(@NonNull final PickingJobFacets facets)
	{
		return WorkflowLaunchersFacetGroup.builder()
				.id(toWorkflowLaunchersFacetGroupId(PickingJobFacetGroup.HANDOVER_LOCATION))
				.caption(TranslatableStrings.adRefList(PickingJobFacetGroup.PICKING_JOB_FILTER_OPTION_REFERENCE_ID, PickingJobFacetGroup.HANDOVER_LOCATION))
				.facets(facets.toList(PickingJobFacets.HandoverLocationFacet.class, PickingJobFacetsUtils::toWorkflowLaunchersFacet))
				.build();
	}

	@NonNull
	private static WorkflowLaunchersFacet toWorkflowLaunchersFacet(@NonNull final PickingJobFacets.HandoverLocationFacet facet)
	{
		return WorkflowLaunchersFacet.builder()
				.facetId(WorkflowLaunchersFacetId.ofString(
						toWorkflowLaunchersFacetGroupId(PickingJobFacetGroup.HANDOVER_LOCATION),
						JSONObjectMapper.forClass(BPartnerLocationId.class).writeValueAsString(facet.getBPartnerLocationId())))
				.caption(TranslatableStrings.anyLanguage(facet.getRenderedAddress()))
				.isActive(facet.isActive())
				.build();
	}

	private static WorkflowLaunchersFacetGroupId toWorkflowLaunchersFacetGroupId(@NonNull PickingJobFacetGroup group)
	{
		return Check.assumeNotNull(facetGroup2facetId.get(group), "no groupId found for {}", group);
	}

	public static List<WorkflowLaunchersFacetGroupId> toWorkflowLaunchersFacetGroupIds(@NonNull List<PickingJobFacetGroup> groups)
	{
		if (groups.isEmpty())
		{
			return ImmutableList.of();
		}
		return groups.stream().map(PickingJobFacetsUtils::toWorkflowLaunchersFacetGroupId).distinct().collect(ImmutableList.toImmutableList());
	}

	public static PickingJobFacetGroup toPickingJobFacetGroup(@NonNull WorkflowLaunchersFacetGroupId groupId)
	{
		return Check.assumeNotNull(facetGroup2facetId.inverse().get(groupId), "no group found for {}", groupId);
	}

	public static Set<PickingJobFacetGroup> toPickingJobFacetGroups(@NonNull Collection<WorkflowLaunchersFacetGroupId> groupIds)
	{
		if (groupIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		return groupIds.stream().map(PickingJobFacetsUtils::toPickingJobFacetGroup).collect(ImmutableSet.toImmutableSet());
	}

	public static WorkflowLaunchersFacetGroup toEmptyGroup(final PickingJobFacetGroup group)
	{
		return WorkflowLaunchersFacetGroup.builder()
				.id(toWorkflowLaunchersFacetGroupId(group))
				.caption(TranslatableStrings.adRefList(PickingJobFacetGroup.PICKING_JOB_FILTER_OPTION_REFERENCE_ID, group))
				.facets(ImmutableList.of())
				.build();

	}
}
