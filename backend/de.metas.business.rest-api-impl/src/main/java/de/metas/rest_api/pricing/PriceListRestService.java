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
import de.metas.common.rest_api.pricing.pricelist.JsonRequestPriceListVersion;
import de.metas.common.rest_api.pricing.pricelist.JsonRequestPriceListVersionUpsert;
import de.metas.externalreference.rest.ExternalReferenceRestControllerService;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.pricelist.CreatePriceListVersionRequest;
import de.metas.pricing.pricelist.PriceListRepository;
import de.metas.pricing.pricelist.PriceListVersion;
import de.metas.util.Services;
import de.metas.util.web.exception.MissingResourceException;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

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
	public JsonResponseUpsertItem putPriceListVersions(@NonNull final JsonRequestPriceListVersionUpsert request)
	{
		final String priceListVersionIdentifier = request.getPriceListVersionIdentifier();
		final JsonRequestPriceListVersion jsonRequestPriceListVersion = request.getJsonRequestPriceListVersion();

		final SyncAdvise parentSyncAdvise = request.getSyncAdvise();
		final SyncAdvise effectiveSyncAdvise = jsonRequestPriceListVersion.getSyncAdvise() != null
				? jsonRequestPriceListVersion.getSyncAdvise()
				: parentSyncAdvise;

		final JsonResponseUpsertItem.SyncOutcome syncOutcome;
		final PriceListVersionId priceListVersionId;

		final Optional<PriceListId> priceListId = getPriceListIdOrNull(jsonRequestPriceListVersion.getPriceListIdentifier());

		if (!priceListId.isPresent())
		{
			throw new AdempiereException("PriceListIdentifier could not be found: " + jsonRequestPriceListVersion.getPriceListIdentifier());
		}

		final Optional<PriceListVersion> existingRecord = getPriceListVersionRecordOrNull(priceListVersionIdentifier);

		if (existingRecord.isPresent())
		{
			if (effectiveSyncAdvise.getIfExists().isUpdate())
			{
				final PriceListVersion priceListVersion = syncJsonToPriceListVersion(
						priceListVersionIdentifier,
						jsonRequestPriceListVersion,
						existingRecord.get(),
						effectiveSyncAdvise);

				if (effectiveSyncAdvise.getIfExists().isUpdateRemove())
				{
					priceListRepository.inactivatePriceListVersion(priceListVersion);
				}
				else
				{
					priceListRepository.updatePriceListVersion(priceListVersion);
				}
				priceListVersionId = priceListVersion.getPriceListVersionId();
				syncOutcome = JsonResponseUpsertItem.SyncOutcome.UPDATED;
			}
			else
			{
				priceListVersionId = PriceListVersionId.ofRepoId(Integer.parseInt(priceListVersionIdentifier));
				syncOutcome = JsonResponseUpsertItem.SyncOutcome.NOTHING_DONE;
			}
		}
		else
		{
			//todo florina look for external ID path

			if (parentSyncAdvise.isFailIfNotExists())
			{
				throw MissingResourceException.builder()
						.resourceName("priceListVersion")
						.resourceIdentifier(priceListVersionIdentifier)
						.parentResource(request).build()
						.setParameter("effectiveparentSyncAdvise", parentSyncAdvise);
			}
			else
			{
				final CreatePriceListVersionRequest createPriceListVersion = syncJsonToCreatePriceListVersionRequest(jsonRequestPriceListVersion);
				priceListVersionId = priceListRepository.createPriceListVersion(createPriceListVersion).getPriceListVersionId();
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
	private PriceListVersion syncJsonToPriceListVersion(
			@NonNull final String priceListVersionIdentifier,
			@NonNull final JsonRequestPriceListVersion jsonRequest,
			@NonNull final PriceListVersion existingRecord,
			@NonNull final SyncAdvise syncAdvise)
	{
		final PriceListVersion.PriceListVersionBuilder priceListVersionBuilder = PriceListVersion.builder();
		final boolean isUpdateRemove = syncAdvise.getIfExists().isUpdateRemove();

		final OrgId orgId = retrieveOrgIdOrDefault(jsonRequest.getOrgCode());
		priceListVersionBuilder.orgId(orgId);

		final PriceListId priceListId = PriceListId.ofRepoId(Integer.parseInt(jsonRequest.getPriceListIdentifier()));
		priceListVersionBuilder.priceListId(priceListId);

		final PriceListVersionId priceListVersionId = PriceListVersionId.ofRepoId(Integer.parseInt(priceListVersionIdentifier));
		priceListVersionBuilder.priceListVersionId(priceListVersionId);

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
			priceListVersionBuilder.description(existingRecord.getDescription());
		}

		//validFrom
		if (jsonRequest.isValidFromSet())
		{
			final Instant validFromDate = jsonRequest.getValidFrom() != null
					? Instant.parse(jsonRequest.getValidFrom())
					: null;

			if (validFromDate == null)
			{
				throw new AdempiereException("jsonRequest.getValidFrom() should never be null at this stage!")
						.appendParametersToMessage()
						.setParameter("jsonRequest", jsonRequest);
			}
			else
			{
				priceListVersionBuilder.validFrom(validFromDate);
			}
		}
		else
		{
			priceListVersionBuilder.validFrom(existingRecord.getValidFrom());
		}

		//isActive
		if (jsonRequest.isActiveSet())
		{
			final Boolean isActive = jsonRequest.getActive().equals("true");
			priceListVersionBuilder.isActive(isActive);
		}
		else
		{
			priceListVersionBuilder.isActive(existingRecord.getIsActive());
		}

		return priceListVersionBuilder.build();
	}

	@NonNull
	private CreatePriceListVersionRequest syncJsonToCreatePriceListVersionRequest(
			@NonNull final JsonRequestPriceListVersion jsonUpsertPriceListVersion)
	{
		final OrgId orgId = retrieveOrgIdOrDefault(jsonUpsertPriceListVersion.getOrgCode());
		final PriceListId priceListId = PriceListId.ofRepoId(Integer.parseInt(jsonUpsertPriceListVersion.getPriceListIdentifier()));

		final Boolean isActive = jsonUpsertPriceListVersion.getActive().equals("true");
		final Instant validFromDate = jsonUpsertPriceListVersion.getValidFrom() != null
				? Instant.parse(jsonUpsertPriceListVersion.getValidFrom())
				: null;

		if (validFromDate == null)
		{
			throw new AdempiereException("jsonUpsertPriceListVersion.getValidFrom() should never be null at this stage!")
					.appendParametersToMessage()
					.setParameter("jsonUpsertPriceListVersion", jsonUpsertPriceListVersion);
		}

		return CreatePriceListVersionRequest.builder()
				.orgId(orgId)
				.priceListId(priceListId)
				.description(jsonUpsertPriceListVersion.getDescription())
				.validFrom(validFromDate)
				.isActive(isActive)
				.build();
	}

	@NonNull
	private Optional<PriceListId> getPriceListIdOrNull(@NonNull final String identifier)
	{
		final I_M_PriceList record = queryBL
				.createQueryBuilder(I_M_PriceList.class)
				.filter(item -> item.getM_PriceList_ID() == Integer.parseInt(identifier))
				.create()
				.firstOnly(I_M_PriceList.class);

		return record != null
				? Optional.of(PriceListId.ofRepoId(record.getM_PriceList_ID()))
				: Optional.empty();
	}

	@NonNull
	private Optional<PriceListVersion> getPriceListVersionRecordOrNull(@NonNull final String identifier)
	{
		final I_M_PriceList_Version record = queryBL
				.createQueryBuilder(I_M_PriceList_Version.class)
				.filter(item -> item.getM_PriceList_Version_ID() == Integer.parseInt(identifier))
				.create()
				.firstOnly(I_M_PriceList_Version.class);

		return record != null
				? Optional.of(buildPriceListVersion(record))
				: Optional.empty();
	}

	@NonNull
	private PriceListVersion buildPriceListVersion(@NonNull final I_M_PriceList_Version record)
	{
		return PriceListVersion.builder()
				.priceListVersionId(PriceListVersionId.ofRepoId(record.getM_PriceList_Version_ID()))
				.priceListId(PriceListId.ofRepoId(record.getM_PriceList_ID()))
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.description(record.getDescription())
				.validFrom(TimeUtil.asInstant(record.getValidFrom()))
				.isActive(record.isActive())
				.build();
	}
}