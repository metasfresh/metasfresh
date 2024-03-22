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

import com.google.common.collect.ImmutableList;
import de.metas.common.externalreference.v2.JsonExternalReferenceCreateRequest;
import de.metas.common.externalreference.v2.JsonExternalReferenceLookupItem;
import de.metas.common.externalreference.v2.JsonExternalReferenceRequestItem;
import de.metas.common.externalsystem.JsonExternalSystemName;
import de.metas.common.pricing.v2.pricelist.request.JsonRequestPriceListVersion;
import de.metas.common.pricing.v2.pricelist.request.JsonRequestPriceListVersionUpsert;
import de.metas.common.pricing.v2.pricelist.request.JsonRequestPriceListVersionUpsertItem;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.JsonResponseUpsert;
import de.metas.common.rest_api.v2.JsonResponseUpsertItem;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.externalreference.ExternalReferenceValueAndSystem;
import de.metas.externalreference.pricelist.PriceListExternalReferenceType;
import de.metas.externalreference.pricelist.PriceListVersionExternalReferenceType;
import de.metas.externalreference.rest.v2.ExternalReferenceRestControllerService;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.pricelist.CreatePriceListVersionRequest;
import de.metas.pricing.pricelist.PriceListVersion;
import de.metas.pricing.pricelist.PriceListVersionRepository;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.web.exception.MissingResourceException;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static de.metas.RestUtils.retrieveOrgIdOrDefault;

@Service
public class PriceListRestService
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

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
	public JsonResponseUpsert upsertPriceListVersion(@NonNull final JsonRequestPriceListVersionUpsert request)
	{
	 	return trxManager.callInNewTrx(() -> upsertPriceListVersionWithinTrx(request));
	}

	@NonNull
	private JsonResponseUpsert upsertPriceListVersionWithinTrx(@NonNull final JsonRequestPriceListVersionUpsert request)
	{
		final List<JsonResponseUpsertItem> jsonResponseUpsertItemList = request.getRequestItems()
				.stream()
				.map(item -> upsertPriceListVersionItem(item, request.getSyncAdvise()))
				.collect(ImmutableList.toImmutableList());

		return JsonResponseUpsert.builder()
				.responseItems(jsonResponseUpsertItemList)
				.build();
	}

	@NonNull
	private JsonResponseUpsertItem upsertPriceListVersionItem(
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
						existingRecord.get()
				);

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
			@NonNull final PriceListVersion existingPriceListVersion)
	{
		final PriceListVersion.PriceListVersionBuilder priceListVersionBuilder = PriceListVersion.builder()
				.priceListVersionId(existingPriceListVersion.getPriceListVersionId())
				.validFrom(jsonRequest.getValidFrom());


		final OrgId orgId = retrieveOrgIdOrDefault(jsonRequest.getOrgCode());
		priceListVersionBuilder.orgId(orgId);

		final PriceListId priceListId = getPriceListId(jsonRequest.getPriceListIdentifier(), jsonRequest.getOrgCode());
		priceListVersionBuilder.priceListId(priceListId);

		//description
		if (jsonRequest.isDescriptionSet())
		{
			priceListVersionBuilder.description(jsonRequest.getDescription());
		}
		else
		{
			priceListVersionBuilder.description(existingPriceListVersion.getDescription());
		}

		if (jsonRequest.isActiveSet())
		{
			priceListVersionBuilder.isActive(jsonRequest.getActive());
		}
		else
		{
			//the update_remove case is intentionally ignored here as `active` is a mandatory column
			priceListVersionBuilder.isActive(existingPriceListVersion.getIsActive());
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

	@NonNull
	public PriceListVersionId getNewestVersionId(
			@NonNull final String priceListIdentifier,
			@NonNull final OrgId orgId)
	{
		final String orgCode = orgDAO.retrieveOrgValue(orgId);
		final PriceListId priceListId = getPriceListId(priceListIdentifier, orgCode);

		return priceListDAO.retrieveNewestPriceListVersionId(priceListId)
				.orElseThrow(() -> new AdempiereException("No price list version found for price list identifier !")
						.appendParametersToMessage()
						.setParameter("priceListIdentifier", priceListIdentifier));
	}

	private void handleNewPriceListVersionExternalReference(
			@NonNull final String orgCode,
			@NonNull final ExternalIdentifier externalPriceListVersionIdentifier,
			@NonNull final PriceListVersionId priceListVersionId)
	{
		Check.assume(externalPriceListVersionIdentifier.getType().equals(ExternalIdentifier.Type.EXTERNAL_REFERENCE), "ExternalIdentifier must be of type external reference.");

		final ExternalReferenceValueAndSystem externalReferenceValueAndSystem = externalPriceListVersionIdentifier.asExternalValueAndSystem();

		final JsonExternalReferenceLookupItem externalReferenceLookupItem = JsonExternalReferenceLookupItem.builder()
				.externalReference(externalReferenceValueAndSystem.getValue())
				.type(PriceListVersionExternalReferenceType.PRICE_LIST_VERSION.getCode())
				.build();

		final JsonMetasfreshId jsonPriceListVersionId = JsonMetasfreshId.of(priceListVersionId.getRepoId());
		final JsonExternalReferenceRequestItem externalReferenceItem = JsonExternalReferenceRequestItem.of(externalReferenceLookupItem, jsonPriceListVersionId);

		final JsonExternalSystemName systemName = JsonExternalSystemName.of(externalReferenceValueAndSystem.getExternalSystem());
		final JsonExternalReferenceCreateRequest externalReferenceCreateRequest = JsonExternalReferenceCreateRequest.builder()
				.systemName(systemName)
				.item(externalReferenceItem)
				.build();

		externalReferenceRestControllerService.performInsert(orgCode, externalReferenceCreateRequest);
	}
}