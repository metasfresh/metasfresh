/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.handlingunits.picking.job.model.facets.external_system;

import com.google.common.collect.ImmutableList;
import de.metas.externalsystem.ExternalSystem;
import de.metas.externalsystem.ExternalSystemId;
import de.metas.handlingunits.picking.job.model.PickingJobQuery;
import de.metas.handlingunits.picking.job.model.facets.CollectingParameters;
import de.metas.handlingunits.picking.job.model.facets.PickingJobFacet;
import de.metas.handlingunits.picking.job.model.facets.PickingJobFacetGroup;
import de.metas.handlingunits.picking.job.model.facets.PickingJobFacetHandler;
import de.metas.handlingunits.picking.job.model.facets.PickingJobFacets;
import de.metas.i18n.TranslatableStrings;
import de.metas.picking.api.Packageable;
import de.metas.rest_workflows.facets.WorkflowLaunchersFacet;
import de.metas.rest_workflows.facets.WorkflowLaunchersFacetGroup;
import de.metas.rest_workflows.facets.WorkflowLaunchersFacetId;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.List;

public class ExternalSystemFacetHandler implements PickingJobFacetHandler
{
	@Override
	public PickingJobFacetGroup getHandledGroup() {return PickingJobFacetGroup.EXTERNAL_SYSTEM;}

	@Override
	public boolean isMatching(@NonNull final PickingJobFacet facet, final PickingJobQuery.@NonNull Facets queryFacets)
	{
		return queryFacets.getExternalSystemIds().contains(facet.asType(ExternalSystemFacet.class).getExternalSystemId());
	}

	@Override
	public void collectHandled(final PickingJobQuery.Facets.FacetsBuilder collector, final PickingJobQuery.Facets from)
	{
		collector.externalSystemIds(from.getExternalSystemIds());
	}

	@Override
	public void collectFromFacetId(@NonNull final PickingJobQuery.Facets.FacetsBuilder collector, @NonNull WorkflowLaunchersFacetId facetId)
	{
		collector.externalSystemId(facetId.getAsId(ExternalSystemId.class));
	}

	@Override
	public WorkflowLaunchersFacetGroup toWorkflowLaunchersFacetGroup(@NonNull final PickingJobFacets facets)
	{
		return WorkflowLaunchersFacetGroup.builder()
				.id(PickingJobFacetGroup.EXTERNAL_SYSTEM.getWorkflowGroupFacetId())
				.caption(TranslatableStrings.adRefList(PickingJobFacetGroup.PICKING_JOB_FILTER_OPTION_REFERENCE_ID, PickingJobFacetGroup.EXTERNAL_SYSTEM))
				.facets(facets.toList(ExternalSystemFacet.class, ExternalSystemFacetHandler::toWorkflowLaunchersFacet))
				.build();
	}

	@NonNull
	private static WorkflowLaunchersFacet toWorkflowLaunchersFacet(@NonNull final ExternalSystemFacet facet)
	{
		return WorkflowLaunchersFacet.builder()
				.facetId(WorkflowLaunchersFacetId.ofId(PickingJobFacetGroup.EXTERNAL_SYSTEM.getWorkflowGroupFacetId(), facet.getExternalSystemId()))
				.caption(TranslatableStrings.anyLanguage(facet.getName()))
				.isActive(facet.isActive())
				.build();
	}

	@Override
	public List<ExternalSystemFacet> extractFacets(@NonNull final Packageable packageable, @NonNull final CollectingParameters parameters)
	{
		// Nullable, unlike CustomerFacetHandler's customerId: an order entered by hand came in through
		// no external system at all, and must still reach the launcher — it simply offers no option
		// here. Cloning the customer handler without this guard would NPE on those rows.
		@Nullable final ExternalSystemId externalSystemId = packageable.getExternalSystemId();
		if (externalSystemId == null)
		{
			return ImmutableList.of();
		}

		// getByIdOrNull, not getById: the repository's map holds only ACTIVE rows, so deactivating an
		// ExternalSystem while orders still reference it would otherwise throw here -- taking the whole
		// filter bar down for every operator, not just that one order.
		final ExternalSystem externalSystem = parameters.getExternalSystemRepository().getByIdOrNull(externalSystemId);
		if (externalSystem == null)
		{
			return ImmutableList.of();
		}

		return ImmutableList.of(ExternalSystemFacet.of(false, externalSystemId, externalSystem.getName()));
	}
}
