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

package de.metas.rest_api.v2.pricing;

import de.metas.common.externalreference.JsonExternalReferenceCreateRequest;
import de.metas.common.externalreference.JsonExternalReferenceItem;
import de.metas.common.externalreference.JsonExternalReferenceLookupItem;
import de.metas.common.externalsystem.JsonExternalSystemName;
import de.metas.common.pricing.v2.pricelist.request.JsonRequestPriceListVersion;
import de.metas.common.pricing.v2.pricelist.request.JsonRequestPriceListVersionUpsertItem;
import de.metas.common.rest_api.JsonMetasfreshId;
import de.metas.common.rest_api.SyncAdvise;
import de.metas.common.rest_api.v2.JsonResponseUpsertItem;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.externalreference.pricelist.PriceListExternalReferenceType;
import de.metas.externalreference.pricelist.PriceListVersionExternalReferenceType;
import de.metas.externalreference.rest.ExternalReferenceRestControllerService;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.pricelist.CreatePriceListVersionRequest;
import de.metas.pricing.pricelist.PriceListVersion;
import de.metas.pricing.pricelist.PriceListVersionRepository;
import de.metas.util.Check;
import de.metas.util.web.exception.MissingResourceException;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static de.metas.RestUtils.retrieveOrgIdOrDefault;

@Service
public class PriceListRestService
{
	private final ExternalReferenceRestControllerService externalReferenceRestControllerService;
	private final PriceListVersionRepository priceListVersionRepository;

	public PriceListRestService(
			@NonNull final ExternalReferenceRestControllerService externalReferenceRestControllerService,
			@NonNull final PriceListVersionRepository priceListVersionRepository)
	{
		this.externalReferenceRestControllerService = externalReferenceRestControllerService;
		this.priceListVersionRepository = priceListVersionRepository;
	}

	@NonNull
	public JsonResponseUpsertItem upsertPriceListVersion(
			@NonNull final JsonRequestPriceListVersionUpsertItem request,
			@NonNull final SyncAdvise parentSyncAdvice)
	{
		final JsonRequestPriceListVersion jsonRequestPriceListVersion = request.getJsonRequestPriceListVersion();

		final SyncAdvise effectiveSyncAdvise = jsonRequestPriceListVersion.getSyncAdvise() != null
				? jsonRequestPriceListVersion.getSyncAdvise()
				: parentSyncAdvice;

		final JsonResponseUpsertItem.SyncOutcome syncOutcome;
		final PriceListVersionId priceListVersionId;

		final ExternalIdentifier priceListVersionIdentifier = ExternalIdentifier.of(request.getPriceListVersionIdentifier());
		final Optional<PriceListVersion> existingRecord = getPriceListVersion(priceListVersionIdentifier, jsonRequestPriceListVersion.getOrgCode());

		if (existingRecord.isPresent())
		{
			if (effectiveSyncAdvise.getIfExists().isUpdate())
			{
				final PriceListVersion priceListVersion = syncJsonToPriceListVersion(
						jsonRequestPriceListVersion,
						existingRecord.get(),
						effectiveSyncAdvise);

				final PriceListVersion updatedPriceListVersion = priceListVersionRepository.savePriceListVersion(priceListVersion);

				priceListVersionId = updatedPriceListVersion.getPriceListVersionId();
				syncOutcome = JsonResponseUpsertItem.SyncOutcome.UPDATED;
			}
			else
			{
				priceListVersionId = existingRecord.get().getPriceListVersionId();
				syncOutcome = JsonResponseUpsertItem.SyncOutcome.NOTHING_DONE;
			}
		}
		else
		{
			if (effectiveSyncAdvise.isFailIfNotExists())
			{
				throw MissingResourceException.builder()
						.resourceName("priceListVersion")
						.resourceIdentifier(request.getPriceListVersionIdentifier())
						.parentResource(request)
						.build()
						.setParameter("effectiveSyncAdvice", parentSyncAdvice);
			}
			else
			{
				final CreatePriceListVersionRequest createPriceListVersion = buildCreatePriceListVersionRequest(jsonRequestPriceListVersion);

				final PriceListVersion createdPriceListVersion = priceListVersionRepository.createPriceListVersion(createPriceListVersion);
				priceListVersionId = createdPriceListVersion.getPriceListVersionId();

				handleNewPriceListVersionExternalReference(jsonRequestPriceListVersion.getOrgCode(), priceListVersionIdentifier, priceListVersionId);

				syncOutcome = JsonResponseUpsertItem.SyncOutcome.CREATED;
			}
		}

		return JsonResponseUpsertItem.builder()
				.identifier(request.getPriceListVersionIdentifier())
				.metasfreshId(JsonMetasfreshId.of(priceListVersionId.getRepoId()))
				.syncOutcome(syncOutcome)
				.build();
	}

	@NonNull
	public Optional<PriceListVersionId> getPriceListVersionId(@NonNull final ExternalIdentifier priceListVersionIdentifier, @NonNull final String orgCode)
	{
		switch (priceListVersionIdentifier.getType())
		{
			case METASFRESH_ID:
				return Optional.of(PriceListVersionId.ofRepoId(priceListVersionIdentifier.asMetasfreshId().getValue()));
			case EXTERNAL_REFERENCE:
				return externalReferenceRestControllerService
						.getJsonMetasfreshIdFromExternalReference(orgCode, priceListVersionIdentifier, PriceListVersionExternalReferenceType.PRICE_LIST_VERSION)
						.map(metasfreshId -> PriceListVersionId.ofRepoId(metasfreshId.getValue()));
			default:
				throw new AdempiereException("Unsupported external identifier type!")
						.appendParametersToMessage()
						.setParameter("priceListVersionIdentifier", priceListVersionIdentifier);
		}
	}

	@NonNull
	private PriceListVersion syncJsonToPriceListVersion(
			@NonNull final JsonRequestPriceListVersion jsonRequest,
			@NonNull final PriceListVersion existingPriceListVersion,
			@NonNull final SyncAdvise syncAdvise)
	{
		final PriceListVersion.PriceListVersionBuilder priceListVersionBuilder = PriceListVersion.builder()
				.priceListVersionId(existingPriceListVersion.getPriceListVersionId())
				.isActive(jsonRequest.getActive())
				.validFrom(jsonRequest.getValidFrom());

		final boolean isUpdateRemove = syncAdvise.getIfExists().isUpdateRemove();

		final OrgId orgId = retrieveOrgIdOrDefault(jsonRequest.getOrgCode());
		priceListVersionBuilder.orgId(orgId);

		final PriceListId priceListId = getPriceListId(jsonRequest.getPriceListIdentifier(), jsonRequest.getOrgCode());
		priceListVersionBuilder.priceListId(priceListId);

		//description
		if (jsonRequest.isDescriptionSet())
		{
			priceListVersionBuilder.description(jsonRequest.getDescription());
		}
		else if (isUpdateRemove)
		{
			priceListVersionBuilder.description(null);
		}
		else
		{
			priceListVersionBuilder.description(existingPriceListVersion.getDescription());
		}

		return priceListVersionBuilder.build();
	}

	@NonNull
	private CreatePriceListVersionRequest buildCreatePriceListVersionRequest(@NonNull final JsonRequestPriceListVersion jsonUpsertPriceListVersion)
	{
		final OrgId orgId = retrieveOrgIdOrDefault(jsonUpsertPriceListVersion.getOrgCode());
		final PriceListId priceListId = getPriceListId(jsonUpsertPriceListVersion.getPriceListIdentifier(), jsonUpsertPriceListVersion.getOrgCode());

		return CreatePriceListVersionRequest.builder()
				.orgId(orgId)
				.priceListId(priceListId)
				.validFrom(jsonUpsertPriceListVersion.getValidFrom())
				.description(jsonUpsertPriceListVersion.getDescription())
				.isActive(jsonUpsertPriceListVersion.getActive())
				.build();
	}

	@NonNull
	private PriceListId getPriceListId(@NonNull final String priceListRawIdentifier, @NonNull final String orgCode)
	{
		final ExternalIdentifier priceListIdentifier = ExternalIdentifier.of(priceListRawIdentifier);

		switch (priceListIdentifier.getType())
		{
			case METASFRESH_ID:
				return PriceListId.ofRepoId(priceListIdentifier.asMetasfreshId().getValue());
			case EXTERNAL_REFERENCE:
				return externalReferenceRestControllerService
						.getJsonMetasfreshIdFromExternalReference(orgCode, priceListIdentifier, PriceListExternalReferenceType.PRICE_LIST)
						.map(metasfreshId -> PriceListId.ofRepoId(metasfreshId.getValue()))
						.orElseThrow(() -> new AdempiereException("No PriceListId could be found for the given external reference!")
								.appendParametersToMessage()
								.setParameter("ExternalIdentifier", priceListRawIdentifier));
			default:
				throw new AdempiereException("Unsupported external identifier type!")
						.appendParametersToMessage()
						.setParameter("priceListRawIdentifier", priceListRawIdentifier)
						.setParameter("priceListIdentifier", priceListIdentifier);
		}
	}

	@NonNull
	private Optional<PriceListVersion> getPriceListVersion(
			@NonNull final ExternalIdentifier priceListVersionIdentifier,
			@NonNull final String orgCode)
	{
		return getPriceListVersionId(priceListVersionIdentifier, orgCode)
				.map(priceListVersionRepository::getPriceListVersionById);
	}

	private void handleNewPriceListVersionExternalReference(
			@NonNull final String orgCode,
			@NonNull final ExternalIdentifier externalPriceListVersionIdentifier,
			@NonNull final PriceListVersionId priceListVersionId)
	{
		Check.assume(externalPriceListVersionIdentifier.getType().equals(ExternalIdentifier.Type.EXTERNAL_REFERENCE), "ExternalIdentifier must be of type external reference.");

		final ExternalIdentifier.ExternalReferenceValueAndSystem externalReferenceValueAndSystem = externalPriceListVersionIdentifier.asExternalValueAndSystem();

		final JsonExternalReferenceLookupItem externalReferenceLookupItem = JsonExternalReferenceLookupItem.builder()
				.id(externalReferenceValueAndSystem.getValue())
				.type(PriceListVersionExternalReferenceType.PRICE_LIST_VERSION.getCode())
				.build();

		final JsonMetasfreshId jsonPriceListVersionId = JsonMetasfreshId.of(priceListVersionId.getRepoId());
		final JsonExternalReferenceItem externalReferenceItem = JsonExternalReferenceItem.of(externalReferenceLookupItem, jsonPriceListVersionId);

		final JsonExternalSystemName systemName = JsonExternalSystemName.of(externalReferenceValueAndSystem.getExternalSystem());
		final JsonExternalReferenceCreateRequest externalReferenceCreateRequest = JsonExternalReferenceCreateRequest.builder()
				.systemName(systemName)
				.item(externalReferenceItem)
				.build();

		externalReferenceRestControllerService.performInsert(orgCode, externalReferenceCreateRequest);
	}
}