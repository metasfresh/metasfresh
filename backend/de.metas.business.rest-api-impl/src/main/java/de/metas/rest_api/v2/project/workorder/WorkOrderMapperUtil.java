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

package de.metas.rest_api.v2.project.workorder;

import de.metas.organization.OrgId;
import de.metas.product.ResourceId;
import de.metas.project.workorder.WOProjectStepId;
import de.metas.project.workorder.data.WOProjectObjectUnderTest;
import de.metas.project.workorder.data.WOProjectResource;
import de.metas.project.workorder.data.WOProjectStep;
import de.metas.resource.ResourceService;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.rest_api.utils.MetasfreshId;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;

import java.util.List;
import java.util.Optional;

@UtilityClass
public class WorkOrderMapperUtil
{
	@NonNull
	public Optional<WOProjectStep> resolveStepForExternalIdentifier(
			@NonNull final IdentifierString externalIdentifier,
			@NonNull final List<WOProjectStep> projectSteps)
	{
		switch (externalIdentifier.getType())
		{
			case METASFRESH_ID:
				return projectSteps.stream()
						.filter(step -> {
							final WOProjectStepId stepId = step.getWOProjectStepIdNonNull();
							final MetasfreshId metasfreshStepId = MetasfreshId.of(stepId);

							return metasfreshStepId.equals(externalIdentifier.asMetasfreshId());
						})
						.findFirst();

			case EXTERNAL_ID:
				return projectSteps.stream()
						.filter(step -> step.getExternalIdNonNull().equals(externalIdentifier.asExternalId()))
						.findFirst();
			default:
				throw new AdempiereException("Unhandled IdentifierString type=" + externalIdentifier);
		}
	}

	@NonNull
	public Optional<WOProjectObjectUnderTest> resolveObjectUnderTestForExternalIdentifier(
			@NonNull final IdentifierString identifier,
			@NonNull final List<WOProjectObjectUnderTest> objectsUnderTest)
	{
		switch (identifier.getType())
		{
			case METASFRESH_ID:
				return objectsUnderTest.stream()
						.filter(objectUnderTest -> {
							final int id = objectUnderTest.getIdNonNull().getRepoId();
							return id == identifier.asMetasfreshId().getValue();
						})
						.findFirst();
			case EXTERNAL_ID:
				return objectsUnderTest.stream()
						.filter(objectUnderTest -> objectUnderTest.getExternalId() != null)
						.filter(objectUnderTest -> objectUnderTest.getExternalId().equals(identifier.asExternalId()))
						.findFirst();
			default:
				throw new AdempiereException("Unhandled IdentifierString type=" + identifier);
		}
	}

	@NonNull
	public Optional<WOProjectResource> resolveWOResourceForExternalIdentifier(
			@NonNull final OrgId orgId,
			@NonNull final IdentifierString identifier,
			@NonNull final List<WOProjectResource> woProjectResources,
			@NonNull final ResourceService resourceService)
	{
		final ResourceId resolvedResourceId = ResourceIdentifierUtil.resolveResourceForExternalIdentifier(orgId, identifier, resourceService);

		return woProjectResources.stream()
				.filter(woProjectResource -> woProjectResource.getResourceIdNonNull().equals(resolvedResourceId))
				.findFirst();
	}
}
