/*
 * #%L
 * de.metas.business
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

package de.metas.project.workorder.data;

import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderObjectUnderTestUpsertRequest;
import de.metas.project.ProjectId;
import de.metas.project.workorder.WOProjectObjectUnderTestId;
import de.metas.util.lang.ExternalId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import lombok.With;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@Value
@Builder
public class WOProjectObjectUnderTest
{
	@Nullable
	WOProjectObjectUnderTestId id;

	@NonNull
	Integer numberOfObjectsUnderTest;

	@NonNull
	ExternalId externalId;

	@With
	@Nullable
	@Getter(AccessLevel.NONE)
	ProjectId projectId;

	@Nullable
	String woDeliveryNote;

	@Nullable
	String woManufacturer;

	@Nullable
	String woObjectType;

	@Nullable
	String woObjectName;

	@Nullable
	String woObjectWhereabouts;

	@NonNull
	public WOProjectObjectUnderTestId getIdNonNull()
	{
		if (id == null)
		{
			throw new AdempiereException("WOProjectObjectUnderTestId cannot be null at this stage!");
		}

		return id;
	}

	@NonNull
	public ProjectId getProjectIdNonNull()
	{
		if (projectId == null)
		{
			throw new AdempiereException("ProjectId cannot be null at this stage!");
		}

		return projectId;
	}

	@NonNull
	public static WOProjectObjectUnderTest fromJson(@NonNull final JsonWorkOrderObjectUnderTestUpsertRequest request)
	{
		return WOProjectObjectUnderTest.builder()
				.numberOfObjectsUnderTest(request.getNumberOfObjectsUnderTest())
				.externalId(ExternalId.of(request.getExternalId().getValue()))
				.woDeliveryNote(request.getWoDeliveryNote())
				.woManufacturer(request.getWoManufacturer())
				.woObjectType(request.getWoObjectType())
				.woObjectName(request.getWoObjectName())
				.woObjectWhereabouts(request.getWoObjectWhereabouts())
				.build();
	}
}
