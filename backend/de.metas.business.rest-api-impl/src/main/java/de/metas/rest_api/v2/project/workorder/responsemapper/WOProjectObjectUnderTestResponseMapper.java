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
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderObjectUnderTestUpsertResponse;
import de.metas.project.workorder.WOProjectObjectUnderTestId;
import de.metas.project.workorder.data.WOProjectObjectUnderTest;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.rest_api.v2.project.workorder.WorkOrderMapperUtil;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import java.util.List;

@Value
@Builder
public class WOProjectObjectUnderTestResponseMapper
{
	@NonNull
	String identifier;

	@NonNull
	JsonResponseUpsertItem.SyncOutcome syncOutcome;

	@NonNull
	public JsonWorkOrderObjectUnderTestUpsertResponse map(@NonNull final List<WOProjectObjectUnderTest> objectsUnderTest)
	{
		final IdentifierString identifierString = IdentifierString.of(identifier);

		return WorkOrderMapperUtil.resolveObjectUnderTestForExternalIdentifier(identifierString, objectsUnderTest)
				.map(this::toResponse)
				.orElseThrow(() -> new AdempiereException("No WOProjectObjectUnderTest found for identifier.")
						.appendParametersToMessage()
						.setParameter("IdentifierString", identifierString));
	}

	@NonNull
	private JsonWorkOrderObjectUnderTestUpsertResponse toResponse(@NonNull final WOProjectObjectUnderTest woObjectUnderTest)
	{
		final WOProjectObjectUnderTestId woProjectObjectUnderTestId = woObjectUnderTest.getObjectUnderTestId();

		return JsonWorkOrderObjectUnderTestUpsertResponse.builder()
				.metasfreshId(JsonMetasfreshId.of(woProjectObjectUnderTestId.getRepoId()))
				.identifier(this.identifier)
				.syncOutcome(this.syncOutcome)
				.build();
	}
}
