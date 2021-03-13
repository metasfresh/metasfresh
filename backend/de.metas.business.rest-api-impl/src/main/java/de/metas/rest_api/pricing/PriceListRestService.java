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

package de.metas.rest_api.pricing;

import de.metas.common.bpartner.response.JsonResponseUpsertItem;
import de.metas.common.rest_api.JsonMetasfreshId;
import de.metas.common.rest_api.SyncAdvise;
import de.metas.common.rest_api.pricing.pricelist.JsonPriceListVersionRequest;
import de.metas.common.rest_api.pricing.pricelist.JsonUpsertPriceListVersionRequest;
import de.metas.externalreference.rest.ExternalReferenceRestControllerService;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.pricelist.CreatePriceListVersionRequest;
import de.metas.pricing.pricelist.PriceListRepository;
import de.metas.pricing.pricelist.PriceListVersion;
import de.metas.util.Services;
import de.metas.util.web.exception.MissingPropertyException;
import de.metas.util.web.exception.MissingResourceException;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_PriceList_Version;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.Instant;

import static de.metas.RestUtils.retrieveOrgIdOrDefault;

@Service
public class PriceListRestService
{
	private final ExternalReferenceRestControllerService externalReferenceRestControllerService;
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final PriceListRepository priceListRepository;

	public PriceListRestService(
			@NonNull final ExternalReferenceRestControllerService externalReferenceRestControllerService,
			@NonNull final PriceListRepository priceListRepository)
	{
		this.externalReferenceRestControllerService = externalReferenceRestControllerService;
		this.priceListRepository = priceListRepository;
	}

	@NonNull
	public JsonResponseUpsertItem putPriceListVersions(@NonNull final JsonPriceListVersionRequest jsonPriceListVersionRequest)
	{
		final String priceListVersionIdentifier = jsonPriceListVersionRequest.getPriceListVersionIdentifier();
		final JsonUpsertPriceListVersionRequest jsonPriceListVersion = jsonPriceListVersionRequest.getJsonUpsertPriceListVersionRequest();
		final SyncAdvise syncAdvise = jsonPriceListVersion.getSyncAdvise();

		final PriceListVersion persistedPriceListVersion;
		final JsonResponseUpsertItem.SyncOutcome syncOutcome;

		final String priceListVersionIdentifierPrefix = priceListVersionIdentifier.substring(0, 4);

		if (priceListVersionIdentifierPrefix.equals("ext-"))//type EXTERNAL_ID
		{
			// todo florina lookup for external if found update otherwise create
			final CreatePriceListVersionRequest createPriceListVersion = syncJsonToCreatePriceListVersionRequest(jsonPriceListVersion);
			persistedPriceListVersion = priceListRepository.createPriceListVersion(createPriceListVersion);
			syncOutcome = JsonResponseUpsertItem.SyncOutcome.CREATED;
		}
		else
		{
			final PriceListVersionId priceListVersionId = PriceListVersionId.ofRepoIdOrNull(Integer.parseInt(priceListVersionIdentifier));

			if (priceListVersionId == null)
			{
				if (syncAdvise.isFailIfNotExists())
				{
					throw MissingResourceException.builder()
							.resourceName("priceListVersion")
							.resourceIdentifier(priceListVersionIdentifier)
							.parentResource(jsonPriceListVersionRequest).build()
							.setParameter("effectiveSyncAdvise", syncAdvise);
				}
				else
				{
					throw new MissingPropertyException("priceListVersionIdentifier", priceListVersionIdentifier);
				}
			}
			else
			{
				final I_M_PriceList_Version record = getRecordOrNull(priceListVersionId);

				if (record == null)
				{
					if (syncAdvise.isFailIfNotExists())
					{
						throw new AdempiereException("No I_M_PriceList_Version record was found for the given ID")
								.appendParametersToMessage()
								.setParameter("M_PriceList_Version_ID", priceListVersionId);
					}
					else
					{
						final CreatePriceListVersionRequest createPriceListVersion = syncJsonToCreatePriceListVersionRequest(jsonPriceListVersion);
						persistedPriceListVersion = priceListRepository.createPriceListVersion(createPriceListVersion);
						syncOutcome = JsonResponseUpsertItem.SyncOutcome.CREATED;
					}
				}
				else if (syncAdvise.isLoadReadOnly())
				{
					syncOutcome = JsonResponseUpsertItem.SyncOutcome.NOTHING_DONE;
					return JsonResponseUpsertItem.builder()
							.identifier(priceListVersionIdentifier)
							.metasfreshId(JsonMetasfreshId.of(priceListVersionId.getRepoId()))
							.syncOutcome(syncOutcome)
							.build();
				}
				else
				{
					final PriceListVersion priceListVersion = syncJsonToPriceListVersion(priceListVersionIdentifier, jsonPriceListVersion, syncAdvise);
					persistedPriceListVersion = priceListRepository.updatePriceListVersion(priceListVersion);
					syncOutcome = JsonResponseUpsertItem.SyncOutcome.UPDATED;
				}
			}
		}

		return JsonResponseUpsertItem.builder()
				.identifier(jsonPriceListVersionRequest.getPriceListVersionIdentifier())
				.metasfreshId(JsonMetasfreshId.of(persistedPriceListVersion.getPriceListVersionId().getRepoId()))
				.syncOutcome(syncOutcome)
				.build();
	}

	@NonNull
	private PriceListVersion syncJsonToPriceListVersion(
			@NonNull final String priceListVersionIdentifier,
			@NonNull final JsonUpsertPriceListVersionRequest jsonPriceListVersion,
			@NonNull final SyncAdvise syncAdvise)
	{
		final PriceListVersion.PriceListVersionBuilder priceListVersionBuilder = PriceListVersion.builder();
		final boolean isUpdateRemove = syncAdvise.getIfExists().isUpdateRemove();

		final OrgId orgId = retrieveOrgIdOrDefault(jsonPriceListVersion.getOrgCode());
		priceListVersionBuilder.orgId(orgId);

		final PriceListId priceListId = PriceListId.ofRepoId(Integer.parseInt(jsonPriceListVersion.getPriceListIdentifier()));
		priceListVersionBuilder.priceListId(priceListId);

		final PriceListVersionId priceListVersionId = PriceListVersionId.ofRepoId(Integer.parseInt(priceListVersionIdentifier));
		priceListVersionBuilder.priceListVersionId(priceListVersionId);

		if (jsonPriceListVersion.isDescriptionSet())
		{
			priceListVersionBuilder.description(jsonPriceListVersion.getDescription());
		}
		else if (isUpdateRemove)
		{
			priceListVersionBuilder.description(null);
		}

		if (jsonPriceListVersion.isValidFromSet())
		{
			final Instant validFromDate = jsonPriceListVersion.getValidFrom() != null ? Instant.parse(jsonPriceListVersion.getValidFrom()) : null;

			if (validFromDate == null)
			{
				throw new AdempiereException("jsonPriceListVersion.getValidFrom() should never be null at this stage!")
						.appendParametersToMessage()
						.setParameter("jsonPriceListVersion", jsonPriceListVersion);
			}

			priceListVersionBuilder.validFrom(validFromDate);
		}
		else
		{
			throw new AdempiereException("jsonPriceListVersion.getValidFrom() should never be null at this stage!")
					.appendParametersToMessage()
					.setParameter("jsonPriceListVersion", jsonPriceListVersion);
		}

		if (jsonPriceListVersion.isActiveSet())
		{
			final Boolean isActive = jsonPriceListVersion.getActive().equals("true");
			priceListVersionBuilder.isActive(isActive);
		}
		else
		{
			throw new AdempiereException("jsonPriceListVersion.getActive() should never be null at this stage!")
					.appendParametersToMessage()
					.setParameter("jsonPriceListVersion", jsonPriceListVersion);
		}

		return priceListVersionBuilder.build();
	}

	@NonNull
	private CreatePriceListVersionRequest syncJsonToCreatePriceListVersionRequest(
			@NonNull final JsonUpsertPriceListVersionRequest jsonUpsertPriceListVersion)
	{
		final CreatePriceListVersionRequest.CreatePriceListVersionRequestBuilder priceListVersionRequestBuilder = CreatePriceListVersionRequest.builder();

		final OrgId orgId = retrieveOrgIdOrDefault(jsonUpsertPriceListVersion.getOrgCode());
		final PriceListId priceListId = PriceListId.ofRepoId(Integer.parseInt(jsonUpsertPriceListVersion.getPriceListIdentifier()));

		final Boolean isActive = jsonUpsertPriceListVersion.getActive().equals("true");
		final Instant validFromDate = jsonUpsertPriceListVersion.getValidFrom() != null ? Instant.parse(jsonUpsertPriceListVersion.getValidFrom()) : null;

		if (validFromDate == null)
		{
			throw new AdempiereException("jsonUpsertPriceListVersion.getValidFrom() should never be null at this stage!")
					.appendParametersToMessage()
					.setParameter("jsonUpsertPriceListVersion", jsonUpsertPriceListVersion);
		}

		priceListVersionRequestBuilder.orgId(orgId);

		priceListVersionRequestBuilder.priceListId(priceListId);

		priceListVersionRequestBuilder.validFrom(validFromDate);
		priceListVersionRequestBuilder.isActive(isActive);

		if (jsonUpsertPriceListVersion.isDescriptionSet())
		{
			priceListVersionRequestBuilder.description(jsonUpsertPriceListVersion.getDescription());
		}
		else
		{
			priceListVersionRequestBuilder.description(null);
		}

		return priceListVersionRequestBuilder.build();
	}

	@Nullable
	private I_M_PriceList_Version getRecordOrNull(@NonNull final PriceListVersionId priceListVersionId)
	{
		return queryBL
				.createQueryBuilder(I_M_PriceList_Version.class)
				.addEqualsFilter(I_M_PriceList_Version.COLUMN_M_PriceList_Version_ID, priceListVersionId.getRepoId())
				.create()
				.firstOnly(I_M_PriceList_Version.class);
	}
}