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

package de.metas.rest_api.v2.project.resource;

import de.metas.organization.OrgId;
import de.metas.product.ResourceId;
import de.metas.resource.Resource;
import de.metas.resource.ResourceService;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.util.web.exception.InvalidIdentifierException;
import de.metas.util.web.exception.MissingResourceException;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.Objects;
import java.util.function.Predicate;

@UtilityClass
public class ResourceIdentifierUtil
{
	@NonNull
	public ResourceId resolveResourceIdentifier(
			@NonNull final OrgId orgId,
			@NonNull final IdentifierString resourceIdentifier,
			@NonNull final ResourceService resourceService
	)
	{
		final Predicate<Resource> resourcePredicate;
		switch (resourceIdentifier.getType())
		{
			case METASFRESH_ID:
				resourcePredicate = r -> ResourceId.toRepoId(r.getResourceId()) == resourceIdentifier.asMetasfreshId().getValue();
				break;
			case VALUE:
				resourcePredicate = r -> // make sure that org and value match
						(r.getOrgId().isAny() || orgId.isAny() || OrgId.equals(r.getOrgId(), orgId))
								&& Objects.equals(r.getValue(), resourceIdentifier.asValue());
				break;
			case INTERNALNAME:
				resourcePredicate = r -> // make sure that org and value match
						(r.getOrgId().isAny() || orgId.isAny() || OrgId.equals(r.getOrgId(), orgId))
								&& Objects.equals(r.getInternalName(), resourceIdentifier.asInternalName());
				break;
			default:
				throw new InvalidIdentifierException(resourceIdentifier.getRawIdentifierString(), resourceIdentifier);
		}

		return resourceService.getAllActiveResources()
				.stream()
				.filter(resourcePredicate)
				.findAny()
				.map(Resource::getResourceId)
				.orElseThrow(() -> MissingResourceException.builder()
						.resourceName("resourceIdentifier")
						.resourceIdentifier(resourceIdentifier.getRawIdentifierString())
						.build());
	}
}
