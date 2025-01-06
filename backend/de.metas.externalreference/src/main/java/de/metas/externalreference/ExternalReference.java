/*
 * #%L
 * de.metas.externalreference
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.externalreference;

import de.metas.organization.OrgId;
import de.metas.util.lang.RepoIdAware;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.function.Function;

@Value
@Builder(toBuilder = true)
public class ExternalReference
{
	/**
	 * Used in maps to indicate that no reference was found for a given query.
	 */
	public static final ExternalReference NULL = ExternalReference.builder()
			.orgId(OrgId.ANY)
			.externalSystem(NullExternalSystem.NULL)
			.externalReferenceType(NullExternalReferenceType.NULL)
			.externalReference("NULL")
			.externalReferenceUrl("NULL")
			.recordId(-1)
			.build();

	@Nullable
	ExternalReferenceId externalReferenceId;

	@NonNull
	OrgId orgId;

	@NonNull
	IExternalSystem externalSystem;

	@NonNull
	IExternalReferenceType externalReferenceType;

	@NonNull
	String externalReference;

	@Nullable
	String version;

	@Nullable
	String externalReferenceUrl;

	@Nullable
	Integer externalSystemParentConfigId;

	@Builder.Default
	boolean readOnlyInMetasfresh = false;

	int recordId;

	@Nullable
	public RepoIdAware getExternalSystemParentConfigId(@NonNull final Function<Integer, RepoIdAware> idMapper)
	{
		if (externalSystemParentConfigId == null)
		{
			return null;
		}

		return idMapper.apply(externalSystemParentConfigId);
	}
}
