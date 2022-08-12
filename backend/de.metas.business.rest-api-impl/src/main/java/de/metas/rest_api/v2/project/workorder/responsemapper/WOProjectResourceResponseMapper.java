/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.rest_api.v2.project.workorder.responsemapper;

import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.JsonResponseUpsertItem;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderResourceUpsertResponse;
import de.metas.organization.OrgId;
import de.metas.project.workorder.data.WOProjectResource;
import de.metas.resource.ResourceService;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.rest_api.v2.project.workorder.WorkOrderMapperUtil;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.NonFinal;
import org.adempiere.exceptions.AdempiereException;

import java.util.List;

@Value
@Builder
public class WOProjectResourceResponseMapper
{
	@NonNull
	String identifier;

	@NonNull
	JsonResponseUpsertItem.SyncOutcome syncOutcome;

	@NonNull
	OrgId orgId;

	@NonNull
	ResourceService resourceService;

	@NonNull
	public JsonWorkOrderResourceUpsertResponse map(@NonNull final List<WOProjectResource> resources)
	{
		final IdentifierString identifierString = IdentifierString.of(identifier);

		return WorkOrderMapperUtil.resolveWOResourceForExternalIdentifier(orgId, identifierString, resources, resourceService)
				.map(this::toResponse)
				.orElseThrow(() -> new AdempiereException("No WOProjectResource found for identifier.")
						.appendParametersToMessage()
						.setParameter("IdentifierString", identifierString));
	}

	@NonNull
	private JsonWorkOrderResourceUpsertResponse toResponse(@NonNull final WOProjectResource woProjectResource)
	{
		return JsonWorkOrderResourceUpsertResponse.builder()
				.identifier(this.identifier)
				.metasfreshId(JsonMetasfreshId.of(woProjectResource.getWoProjectResourceId().getRepoId()))
				.syncOutcome(this.syncOutcome)
				.build();
	}
}
