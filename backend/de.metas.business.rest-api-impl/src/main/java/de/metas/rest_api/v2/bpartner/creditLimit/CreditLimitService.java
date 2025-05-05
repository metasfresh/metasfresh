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

package de.metas.rest_api.v2.bpartner.creditLimit;

import com.google.common.collect.ImmutableList;
import de.metas.RestUtils;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.BPartnerCreditLimitRepository;
import de.metas.common.bpartner.v2.response.JsonResponseCreditLimitDelete;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.organization.OrgId;
import de.metas.rest_api.v2.bpartner.bpartnercomposite.JsonRetrieverService;
import de.metas.rest_api.v2.bpartner.bpartnercomposite.JsonServiceFactory;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

@Service
public class CreditLimitService
{
	private final BPartnerCreditLimitRepository bPartnerCreditLimitRepository;
	private final JsonRetrieverService jsonRetrieverService;

	public CreditLimitService(
			@NonNull final BPartnerCreditLimitRepository bPartnerCreditLimitRepository,
			@NonNull final JsonServiceFactory jsonServiceFactory)
	{
		this.bPartnerCreditLimitRepository = bPartnerCreditLimitRepository;
		this.jsonRetrieverService = jsonServiceFactory.createRetriever();
	}

	@NonNull
	public JsonResponseCreditLimitDelete deleteExistingRecordsForBPartnerAndOrg(
			@NonNull final String bpartnerIdentifier,
			@Nullable final String orgCode,
			final boolean includingProcessed)
	{
		final OrgId orgId = RestUtils.retrieveOrgIdOrDefault(orgCode);

		final ExternalIdentifier externalIdentifier = ExternalIdentifier.of(bpartnerIdentifier);

		final BPartnerId bPartnerId = jsonRetrieverService.resolveBPartnerExternalIdentifier(externalIdentifier, orgId)
				.orElseThrow(() -> new AdempiereException("Couldn't resolve external identifier: " + externalIdentifier.getRawValue()));

		final ImmutableList<JsonMetasfreshId> deletedIds = bPartnerCreditLimitRepository.deleteRecordsForBPartnerAndOrg(bPartnerId, orgId, includingProcessed);

		return JsonResponseCreditLimitDelete.builder()
				.metasfreshIds(deletedIds)
				.build();
	}
}
