/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.audit.apirequest.response;

import de.metas.audit.apirequest.request.ApiRequestAuditId;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.time.Instant;

@Value
@Builder
public class ApiResponseAudit
{
	@Nullable // null, when the entity was not persisted yet
	ApiResponseAuditId apiResponseAuditId;

	@NonNull
	OrgId orgId;

	@NonNull
	ApiRequestAuditId apiRequestAuditId;

	@Nullable
	String body;

	@Nullable
	String httpCode;

	@Nullable
	Instant time;

	@Nullable
	String httpHeaders;

	@NonNull
	public ApiResponseAuditId getIdNotNull()
	{
		if (apiResponseAuditId == null)
		{
			throw new AdempiereException("getIdNotNull() should be called only for already persisted ApiResponseAudit objects!")
					.appendParametersToMessage()
					.setParameter("ApiResponseAudit", this);
		}

		return apiResponseAuditId;
	}
}
