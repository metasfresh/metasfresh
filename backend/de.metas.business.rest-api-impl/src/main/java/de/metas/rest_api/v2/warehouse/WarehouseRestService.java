/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.rest_api.v2.warehouse;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.bpartner.service.impl.GLNQuery;
import de.metas.common.externalreference.v2.JsonExternalReferenceLookupItem;
import de.metas.common.externalreference.v2.JsonExternalReferenceRequestItem;
import de.metas.common.externalreference.v2.JsonRequestExternalReferenceUpsert;
import de.metas.common.externalsystem.JsonExternalSystemName;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.JsonResponseUpsert;
import de.metas.common.rest_api.v2.JsonResponseUpsertItem;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.common.rest_api.v2.warehouse.JsonRequestWarehouse;
import de.metas.common.rest_api.v2.warehouse.JsonRequestWarehouseUpsert;
import de.metas.common.rest_api.v2.warehouse.JsonRequestWarehouseUpsertItem;
import de.metas.common.util.CoalesceUtil;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.externalreference.ExternalReferenceValueAndSystem;
import de.metas.externalreference.bpartnerlocation.BPLocationExternalReferenceType;
import de.metas.externalreference.rest.v2.ExternalReferenceRestControllerService;
import de.metas.externalreference.warehouse.WarehouseExternalReferenceType;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import de.metas.util.web.exception.InvalidIdentifierException;
import de.metas.util.web.exception.MissingResourceException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.CreateWarehouseRequest;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.adempiere.warehouse.api.Warehouse;
import org.compiere.model.I_AD_Org;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Optional;

import static de.metas.RestUtils.retrieveOrgIdOrDefault;

@Service
@RequiredArgsConstructor
public class WarehouseRestService
{
	private static final Logger logger = LogManager.getLogger(WarehouseRestService.class);

	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final IBPartnerDAO bpartnersRepo = Services.get(IBPartnerDAO.class);
	private final IBPartnerOrgBL partnerOrgBL = Services.get(IBPartnerOrgBL.class);

	private final ExternalReferenceRestControllerService externalReferenceService;

	@NonNull
	public JsonResponseUpsert upsertWarehouses(
			@Nullable final String orgCode,
			@NonNull final JsonRequestWarehouseUpsert request)
	{
		return trxManager.callInThreadInheritedTrx(() -> upsertWarehousesWithinTrx(orgCode, request));
	}

	@NonNull
	private JsonResponseUpsert upsertWarehousesWithinTrx(
			@Nullable final String orgCode,
			@NonNull final JsonRequestWarehouseUpsert request)

	{
		final SyncAdvise syncAdvise = request.getSyncAdvise();

		final ImmutableList<JsonResponseUpsertItem> responseList =
				request.getRequestItems()
						.stream()
						.map(reqItem -> upsertWarehouseItem(reqItem, syncAdvise, orgCode))
						.collect(ImmutableList.toImmutableList());

		return JsonResponseUpsert.builder()
				.responseItems(responseList)
				.build();
	}

	@NonNull
	private JsonResponseUpsertItem upsertWarehouseItem(
			@NonNull final JsonRequestWarehouseUpsertItem jsonRequestWarehouseUpsertItem,
			@NonNull final SyncAdvise parentSyncAdvise,
			@Nullable final String orgCode)
	{
		final JsonResponseUpsertItem.SyncOutcome syncOutcome;

		final I_AD_Org org = orgDAO.getById(retrieveOrgIdOrDefault(orgCode));

		final JsonRequestWarehouse jsonRequestWarehouse = jsonRequestWarehouseUpsertItem.getRequestWarehouse();

		final SyncAdvise effectiveSyncAdvise = CoalesceUtil.coalesceNotNull(jsonRequestWarehouse.getSyncAdvise(), parentSyncAdvise);

		final ExternalIdentifier externalIdentifier = ExternalIdentifier.of(jsonRequestWarehouseUpsertItem.getWarehouseIdentifier());
		final Optional<Warehouse> existingWarehouse = resolveWarehouseExternalIdentifier(externalIdentifier, org)
				.map(warehouseBL::getByIdNotNull);

		final WarehouseId warehouseId;
		if (existingWarehouse.isPresent())
		{
			warehouseId = existingWarehouse.get().getId();

			if (effectiveSyncAdvise.getIfExists().isUpdate())
			{
				final Warehouse warehouse = syncWarehouseWithJson(jsonRequestWarehouse, existingWarehouse.get(), org);
				warehouseBL.save(warehouse);

				syncOutcome = JsonResponseUpsertItem.SyncOutcome.UPDATED;
			}
			else
			{
				syncOutcome = JsonResponseUpsertItem.SyncOutcome.NOTHING_DONE;
			}
		}
		else
		{
			validateCreateSyncAdvise(jsonRequestWarehouseUpsertItem, externalIdentifier, effectiveSyncAdvise);

			final CreateWarehouseRequest createWarehouseRequest = getCreateWarehouseRequest(jsonRequestWarehouse, org);
			warehouseId = warehouseBL.createWarehouse(createWarehouseRequest).getId();

			syncOutcome = JsonResponseUpsertItem.SyncOutcome.CREATED;
		}

		handleWarehouseExternalReference(org,
										 externalIdentifier,
										 JsonMetasfreshId.of(warehouseId.getRepoId()),
										 jsonRequestWarehouseUpsertItem.getExternalVersion(),
										 jsonRequestWarehouseUpsertItem.getExternalReferenceUrl());

		return JsonResponseUpsertItem.builder()
				.syncOutcome(syncOutcome)
				.identifier(externalIdentifier.getRawValue())
				.metasfreshId(JsonMetasfreshId.of(warehouseId.getRepoId()))
				.build();
	}

	private void handleWarehouseExternalReference(
			@NonNull final I_AD_Org org,
			@NonNull final ExternalIdentifier externalIdentifier,
			@NonNull final JsonMetasfreshId metasfreshId,
			@Nullable final String externalVersion,
			@Nullable final String externalReferenceUrl)
	{
		if (!ExternalIdentifier.Type.EXTERNAL_REFERENCE.equals(externalIdentifier.getType()))
		{
			return;
		}

		final ExternalReferenceValueAndSystem externalReferenceValueAndSystem = externalIdentifier.asExternalValueAndSystem();

		final JsonExternalSystemName systemName = JsonExternalSystemName.of(externalIdentifier.asExternalValueAndSystem().getExternalSystem());
		final JsonExternalReferenceLookupItem externalReferenceLookupItem = JsonExternalReferenceLookupItem.builder()
				.externalReference(externalReferenceValueAndSystem.getValue())
				.type(WarehouseExternalReferenceType.WAREHOUSE.getCode())
				.build();

		final JsonExternalReferenceRequestItem externalReferenceItem = JsonExternalReferenceRequestItem.builder()
				.lookupItem(externalReferenceLookupItem)
				.metasfreshId(metasfreshId)
				.version(externalVersion)
				.externalReferenceUrl(externalReferenceUrl)
				.build();

		final JsonRequestExternalReferenceUpsert externalReferenceCreateRequest = JsonRequestExternalReferenceUpsert.builder()
				.systemName(systemName)
				.externalReferenceItem(externalReferenceItem)
				.build();

		externalReferenceService.performUpsert(externalReferenceCreateRequest, org.getValue());
	}

	@NonNull
	public Optional<WarehouseId> resolveWarehouseExternalIdentifier(
			@NonNull final ExternalIdentifier warehouseIdentifier,
			@NonNull final I_AD_Org org)
	{
		final OrgId orgId = OrgId.ofRepoId(org.getAD_Org_ID());
		
		return resolveWarehouseExternalIdentifier(warehouseIdentifier, orgId);
	}
	
	@NonNull
	public Optional<WarehouseId> resolveWarehouseExternalIdentifier(
			@NonNull final ExternalIdentifier warehouseIdentifier,
			@NonNull final OrgId orgId)
	{
		switch (warehouseIdentifier.getType())
		{
			case METASFRESH_ID:
				return Optional.of(WarehouseId.ofRepoId(warehouseIdentifier.asMetasfreshId().getValue()));

			case EXTERNAL_REFERENCE:
				return externalReferenceService
						.getJsonMetasfreshIdFromExternalReference(orgId, warehouseIdentifier, WarehouseExternalReferenceType.WAREHOUSE)
						.map(JsonMetasfreshId::getValue)
						.map(WarehouseId::ofRepoId);
			case VALUE:
				return warehouseBL.getOptionalIdByValue(warehouseIdentifier.asValue());
			default:
				throw new InvalidIdentifierException(warehouseIdentifier.getRawValue());
		}
	}

	@NonNull
	private Warehouse syncWarehouseWithJson(
			@NonNull final JsonRequestWarehouse jsonRequestWarehouse,
			@NonNull final Warehouse existingWarehouse,
			@NonNull final I_AD_Org org)
	{
		final OrgId orgId = OrgId.ofRepoId(org.getAD_Org_ID());

		final Warehouse.WarehouseBuilder builder = existingWarehouse.toBuilder();

		// name
		if (jsonRequestWarehouse.isNameSet())
		{
			if (jsonRequestWarehouse.getName() == null)
			{
				logger.debug("Ignoring property \"name\" : null ");
			}
			else
			{
				builder.name(jsonRequestWarehouse.getName());
			}
		}

		// value
		if (jsonRequestWarehouse.isCodeSet())
		{
			if (jsonRequestWarehouse.getCode() == null)
			{
				logger.debug("Ignoring property \"code\" : null ");
			}
			else
			{
				builder.value(jsonRequestWarehouse.getCode());
			}
		}

		// BPartner and BPartnerLocation
		if (jsonRequestWarehouse.isBpartnerLocationIdentifierSet())
		{
			if (jsonRequestWarehouse.getBpartnerLocationIdentifier() == null)
			{
				logger.debug("Ignoring property \"bpartnerLocationIdentifier\" : null ");
			}
			else
			{
				final ExternalIdentifier externalIdentifier = ExternalIdentifier.of(jsonRequestWarehouse.getBpartnerLocationIdentifier());
				final BPartnerLocationId bPartnerLocationId = getOrgBPartnerLocationId(externalIdentifier, orgId);

				builder.partnerLocationId(bPartnerLocationId);
			}
		}

		// active
		if (jsonRequestWarehouse.isActiveSet())
		{
			if (jsonRequestWarehouse.getActive() == null)
			{
				logger.debug("Ignoring boolean property \"active\" : null ");
			}
			else
			{
				builder.active(jsonRequestWarehouse.getActive());
			}
		}

		return builder.build();
	}

	@NonNull
	private BPartnerLocationId getOrgBPartnerLocationId(
			@NonNull final ExternalIdentifier externalIdentifier,
			@NonNull final OrgId orgId)
	{
		final BPartnerId bPartnerId = partnerOrgBL.retrieveLinkedBPartnerId(orgId)
				.orElseThrow(() -> new AdempiereException("No Org BPartner found for Org ID !")
						.appendParametersToMessage()
						.setParameter("OrgId", orgId));

		final OrgId bPartnerOrgId = OrgId.ofRepoId(bpartnersRepo.getById(bPartnerId).getAD_Org_ID());

		final BPartnerLocationId bPartnerLocationId = resolveBPartnerLocationExternalIdentifier(externalIdentifier, bPartnerOrgId)
				.orElseThrow(() -> new AdempiereException("No BPartnerLocationId found for external identifier")
						.appendParametersToMessage()
						.setParameter("rawExternalIdentifier", externalIdentifier.getRawValue())); 
		
		if (!bPartnerLocationId.getBpartnerId().equals(bPartnerId))
		{
			throw new AdempiereException("Found BPartnerLocationId does not belong to the current Org BPartner !")
					.appendParametersToMessage()
					.setParameter("rawExternalIdentifier", externalIdentifier.getRawValue())
					.setParameter("BPartnerLocationID", bPartnerLocationId)
					.setParameter("Org BPartnerId", bPartnerOrgId);
		}
		
		return bPartnerLocationId;
	}

	@NonNull
	private Optional<BPartnerLocationId> resolveBPartnerLocationExternalIdentifier(
			@NonNull final ExternalIdentifier bPartnerLocationExternalIdentifier,
			@NonNull final OrgId orgId)
	{
		switch (bPartnerLocationExternalIdentifier.getType())
		{
			case METASFRESH_ID:
				final BPartnerLocationId bPartnerLocationId = bpartnersRepo.getBPartnerLocationIdByRepoId(bPartnerLocationExternalIdentifier.asMetasfreshId().getValue());
				return Optional.of(bPartnerLocationId);
			case EXTERNAL_REFERENCE:
				return externalReferenceService.getJsonMetasfreshIdFromExternalReference(orgId, bPartnerLocationExternalIdentifier, BPLocationExternalReferenceType.BPARTNER_LOCATION)
						.map(JsonMetasfreshId::getValue)
						.map(bpartnersRepo::getBPartnerLocationIdByRepoId);
			case GLN:
				final GLNQuery glnQuery = GLNQuery.builder()
						.onlyOrgId(orgId)
						.gln(bPartnerLocationExternalIdentifier.asGLN())
						.build();

				return bpartnersRepo.retrieveSingleBPartnerLocationIdBy(glnQuery);
			default:
				throw new InvalidIdentifierException("Given external identifier type is not supported!")
						.setParameter("externalIdentifierType", bPartnerLocationExternalIdentifier.getType())
						.setParameter("rawExternalIdentifier", bPartnerLocationExternalIdentifier.getRawValue());
		}
	}

	@NonNull
	private CreateWarehouseRequest getCreateWarehouseRequest(
			@NonNull final JsonRequestWarehouse jsonRequestWarehouse,
			@NonNull final I_AD_Org org)
	{
		final OrgId orgId = OrgId.ofRepoId(org.getAD_Org_ID());
		final ExternalIdentifier externalIdentifier = ExternalIdentifier.of(jsonRequestWarehouse.getBpartnerLocationIdentifier());

		return CreateWarehouseRequest.builder()
				.orgId(orgId)
				.name(jsonRequestWarehouse.getName())
				.value(jsonRequestWarehouse.getCode())
				.partnerLocationId(getOrgBPartnerLocationId(externalIdentifier, orgId))
				.active(Optional.ofNullable(jsonRequestWarehouse.getActive()).orElse(true))
				.build();
	}

	private static void validateCreateSyncAdvise(
			@NonNull final Object parentResource,
			@NonNull final ExternalIdentifier externalIdentifier,
			@NonNull final SyncAdvise effectiveSyncAdvise)
	{
		if (effectiveSyncAdvise.isFailIfNotExists())
		{
			throw MissingResourceException.builder()
					.resourceName("Warehouse")
					.resourceIdentifier(externalIdentifier.getRawValue())
					.parentResource(parentResource)
					.build()
					.setParameter("effectiveSyncAdvise", effectiveSyncAdvise);
		}
		else if (ExternalIdentifier.Type.METASFRESH_ID.equals(externalIdentifier.getType()))
		{
			throw MissingResourceException.builder()
					.resourceName("Warehouse")
					.resourceIdentifier(externalIdentifier.getRawValue())
					.parentResource(parentResource)
					.detail(TranslatableStrings.constant("With this type, only updates are allowed."))
					.build()
					.setParameter("effectiveSyncAdvise", effectiveSyncAdvise);
		}
	}
}

