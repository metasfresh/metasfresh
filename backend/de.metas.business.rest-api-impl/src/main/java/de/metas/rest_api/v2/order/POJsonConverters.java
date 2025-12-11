/*
 * #%L
 * de.metas.business.rest-api-impl
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

package de.metas.rest_api.v2.order;

import de.metas.common.rest_api.v2.JsonPurchaseCandidateCreateItem;
import de.metas.common.rest_api.v2.JsonPurchaseCandidateReference;
import de.metas.externalsystem.ExternalSystemId;
import de.metas.externalsystem.ExternalSystemIdWithExternalIds;
import de.metas.externalsystem.ExternalSystemRepository;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.rest_api.utils.JsonExternalIds;
import de.metas.util.lang.ExternalHeaderIdWithExternalLineIds;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public final class POJsonConverters
{
	@NonNull private final ExternalSystemRepository externalSystemRepository;

	@NonNull
	public ExternalSystemId getExternalSystemIdByExternalSystemCode(@NonNull final JsonPurchaseCandidateCreateItem request)
	{
		return externalSystemRepository.getIdByType(ExternalSystemType.ofValue(request.getExternalSystemCode()));
	}

	@NonNull
	public String getExternalSystemTypeById(@NonNull final ExternalSystemId externalSystemId)
	{
		return externalSystemRepository.getById(externalSystemId).getType().toJson();
	}

	@NonNull
	public List<ExternalSystemIdWithExternalIds> fromJson(@NonNull final List<JsonPurchaseCandidateReference> candidates)
	{
		final List<ExternalSystemIdWithExternalIds> externalSystemIdWithExternalIds = new ArrayList<>();
		for (final JsonPurchaseCandidateReference cand : candidates)
		{
			final ExternalHeaderIdWithExternalLineIds headerAndLineId = ExternalHeaderIdWithExternalLineIds.builder()
					.externalHeaderId(JsonExternalIds.toExternalId(cand.getExternalHeaderId()))
					.externalLineIds(JsonExternalIds.toExternalIds(cand.getExternalLineIds()))
					.build();

			externalSystemIdWithExternalIds.add(ExternalSystemIdWithExternalIds.builder()
					.externalSystemId(externalSystemRepository.getIdByType(ExternalSystemType.ofValue(cand.getExternalSystemCode())))
					.externalHeaderIdWithExternalLineIds(headerAndLineId)
					.build());
		}
		return externalSystemIdWithExternalIds;
	}

}
