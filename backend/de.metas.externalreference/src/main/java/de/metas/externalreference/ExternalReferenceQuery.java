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
import de.metas.rest_api.utils.MetasfreshId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@Value
public class ExternalReferenceQuery
{
	@NonNull
	OrgId orgId;

	@NonNull
	IExternalSystem externalSystem;

	@NonNull
	IExternalReferenceType externalReferenceType;

	@Nullable
	String externalReference;

	@Nullable
	MetasfreshId metasfreshId;

	@Builder
	public ExternalReferenceQuery(
			@NonNull final OrgId orgId,
			@NonNull final IExternalSystem externalSystem,
			@NonNull final IExternalReferenceType externalReferenceType,
			@Nullable final String externalReference,
			@Nullable final MetasfreshId metasfreshId)
	{
		if (externalReference == null && metasfreshId == null)
		{
			throw new AdempiereException("externalReference && metasfreshId cannot be both null!");
		}

		if (externalReference != null && metasfreshId != null)
		{
			throw new AdempiereException("Only one of externalReference && metasfreshId must be provided!");
		}

		this.orgId = orgId;
		this.externalSystem = externalSystem;
		this.externalReferenceType = externalReferenceType;
		this.externalReference = externalReference;
		this.metasfreshId = metasfreshId;
	}

	public boolean matches(@NonNull final ExternalReference externalReference)
	{
		boolean matches = this.externalReferenceType.equals(externalReference.getExternalReferenceType())
				&& this.orgId.equals(externalReference.getOrgId())
				&& this.externalSystem.equals(externalReference.getExternalSystem());

		if (this.externalReference != null)
		{
			matches = matches
					&& this.externalReference.equals(externalReference.getExternalReference());
		}

		if (this.metasfreshId != null)
		{
			matches = matches
					&& this.metasfreshId.getValue() == externalReference.getRecordId();
		}

		return matches;
	}
}
