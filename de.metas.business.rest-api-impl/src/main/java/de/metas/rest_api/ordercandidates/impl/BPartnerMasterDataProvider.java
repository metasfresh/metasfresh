package de.metas.rest_api.ordercandidates.impl;

import static de.metas.util.lang.CoalesceUtil.coalesceSuppliers;
import static org.adempiere.model.InterfaceWrapperHelper.isNew;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import javax.annotation.Nullable;

import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.GLN;
import de.metas.bpartner.service.BPartnerInfo;
import de.metas.bpartner.service.BPartnerQuery;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.cache.CCache;
import de.metas.location.CountryId;
import de.metas.location.ICountryDAO;
import de.metas.location.ILocationDAO;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.organization.OrgInfoUpdateRequest;
import de.metas.rest_api.JsonExternalId;
import de.metas.rest_api.SyncAdvise;
import de.metas.rest_api.SyncAdvise.IfExists;
import de.metas.rest_api.bpartner.impl.BPartnerMasterDataContext;
import de.metas.rest_api.bpartner.request.JsonRequestBPartner;
import de.metas.rest_api.bpartner.request.JsonRequestContact;
import de.metas.rest_api.bpartner.request.JsonRequestLocation;
import de.metas.rest_api.ordercandidates.JsonBPartnerInfo;
import de.metas.rest_api.utils.JsonExternalIds;
import de.metas.rest_api.utils.MissingPropertyException;
import de.metas.rest_api.utils.PermissionService;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.rest.ExternalId;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.ordercandidate.rest-api-impl
 * %%
 * Copyright (C) 2018 metas GmbH
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

final class BPartnerMasterDataProvider
{
	public static BPartnerMasterDataProvider of(
			@Nullable final Properties ctx,
			@Nullable final PermissionService permissionService)
	{
		return new BPartnerMasterDataProvider(
				coalesceSuppliers(
						() -> permissionService,
						() -> PermissionService.of(ctx)));
	}

	private final BiMap<JsonRequestBPartner, BPartnerId> bpartnerIdsByJson = HashBiMap.<JsonRequestBPartner, BPartnerId> create();
	private final Map<JsonExternalId, BPartnerLocationId> bpartnerLocationIdsByExternalId = new HashMap<>();
	private final Map<JsonExternalId, BPartnerContactId> bpartnerContactIdsByExternalId = new HashMap<>();

	private final IBPartnerDAO bpartnersRepo = Services.get(IBPartnerDAO.class);
	private final ILocationDAO locationsRepo = Services.get(ILocationDAO.class);
	private final ICountryDAO countryRepo = Services.get(ICountryDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	private final PermissionService permissionService;

	@VisibleForTesting
	BPartnerMasterDataProvider(@NonNull final PermissionService permissionService)
	{
		this.permissionService = permissionService;
	}

	public BPartnerInfo getCreateOrgBPartnerInfo(
			@NonNull final JsonBPartnerInfo jsonBPartnerInfo,
			@NonNull final OrgId orgId)
	{
		final BPartnerMasterDataContext context = BPartnerMasterDataContext
				.builder()
				.orgId(orgId)
				.bPartnerIsOrgBP(true)
				.build();

		return handleBPartnerInfoWithContext(jsonBPartnerInfo, context);
	}

	public BPartnerInfo getCreateBPartnerInfo(
			@Nullable final JsonBPartnerInfo jsonBPartnerInfo,
			final OrgId orgId)
	{
		if (jsonBPartnerInfo == null)
		{
			return null;
		}

		final BPartnerMasterDataContext context = BPartnerMasterDataContext.ofOrg(orgId);

		return handleBPartnerInfoWithContext(jsonBPartnerInfo, context);
	}

	@Value
	private static class CachingKey
	{
		OrgId orgId;
		JsonBPartnerInfo jsonBPartnerInfo;
	}

	private final CCache<CachingKey, BPartnerInfo> BPartnerInfoCache = CCache
			.<CachingKey, BPartnerInfo> builder()
			.cacheName(this.getClass().getSimpleName() + "-BPartnerInfoCache")
			.build();

	private BPartnerInfo handleBPartnerInfoWithContext(
			@NonNull final JsonBPartnerInfo jsonBPartnerInfo,
			@NonNull final BPartnerMasterDataContext context)
	{
		final CachingKey key = new CachingKey(context.getOrgId(), jsonBPartnerInfo);
		return BPartnerInfoCache.getOrLoad(key, () -> handleBPartnerInfoWithContext0(jsonBPartnerInfo, context));
	}

	private BPartnerInfo handleBPartnerInfoWithContext0(
			@NonNull final JsonBPartnerInfo jsonBPartnerInfo,
			@NonNull final BPartnerMasterDataContext context)
	{
		final SyncAdvise.IfNotExists ifNotExists = jsonBPartnerInfo.getSyncAdvise().getIfNotExists();

		final BPartnerInfo bPartnerInfo = lookupBPartnerInfoOrNull(jsonBPartnerInfo, context);
		if (bPartnerInfo == null)
		{
			switch (ifNotExists)
			{
				case FAIL:
					Check.fail("An exception should have been thrown alread in lookupBPartnerInfoOrNull");
				case CREATE:
					return getCreateBPartnerInfo(jsonBPartnerInfo, context);
				default:
					Check.fail("Unexpected IfNotExists={}", ifNotExists);
					return null;
			}
		}
		else
		{
			final IfExists ifExists = jsonBPartnerInfo.getSyncAdvise().getIfExists();
			switch (ifExists)
			{
				case UPDATE_MERGE:
					final BPartnerMasterDataContext childContext = context
							.setIfNotNull(bPartnerInfo.getBpartnerId())
							.setIfNotNull(bPartnerInfo.getBpartnerLocationId())
							.setIfNotNull(bPartnerInfo.getContactId());
					return getCreateBPartnerInfo(jsonBPartnerInfo, childContext);
				case DONT_UPDATE:
					return bPartnerInfo;
				default:
					Check.fail("Unepected IfExists={}", ifExists);
					return null;
			}
		}
	}

	private BPartnerInfo lookupBPartnerInfoOrNull(
			@NonNull final JsonBPartnerInfo jsonBPartnerInfo,
			@NonNull final BPartnerMasterDataContext context)
	{
		final BPartnerId bpartnerId = lookupBPartnerIdOrNull(
				jsonBPartnerInfo,
				context);
		if (bpartnerId == null)
		{
			return null;
		}

		final BPartnerLocationId bpartnerLocationId = lookupBPartnerLocationIdOrNull(
				jsonBPartnerInfo.getLocation(),
				context.setIfNotNull(bpartnerId));

		final BPartnerContactId contactId = lookupContactIdOrNull(
				jsonBPartnerInfo.getContact(),
				context.setIfNotNull(bpartnerId));

		return BPartnerInfo.builder()
				.bpartnerId(bpartnerId)
				.bpartnerLocationId(bpartnerLocationId)
				.contactId(contactId)
				.build();
	}

	private BPartnerId lookupBPartnerIdOrNull(
			@NonNull final JsonBPartnerInfo jsonBPartnerInfo,
			@NonNull final BPartnerMasterDataContext context)
	{
		final JsonRequestBPartner json = jsonBPartnerInfo.getBpartner();

		// ..context
		if (context.getBpartnerId() != null)
		{
			return context.getBpartnerId();
		}

		final SyncAdvise syncAdvise = jsonBPartnerInfo.getSyncAdvise();
		final ExternalId externalId = JsonExternalIds.toExternalIdOrNull(json.getExternalId());

		final BPartnerQuery.BPartnerQueryBuilder query = BPartnerQuery.builder()
				.onlyOrgId(context.getOrgId())
				.onlyOrgId(OrgId.ANY)
				.outOfTrx(syncAdvise.isLoadReadOnly())
				.failIfNotExists(syncAdvise.isFailIfNotExists())
				.externalId(externalId)
				.bpartnerValue(json.getCode());

		final JsonRequestLocation jsonLocation = jsonBPartnerInfo.getLocation();
		if (jsonLocation != null && jsonLocation.getGln() != null)
		{
			query.gln(GLN.ofString(jsonLocation.getGln()));
		}

		return bpartnersRepo
				.retrieveBPartnerIdBy(query.build())
				.orElse(null);
	}

	private BPartnerLocationId lookupBPartnerLocationIdOrNull(
			@Nullable final JsonRequestLocation jsonBPartnerLocation,
			@NonNull final BPartnerMasterDataContext context)
	{
		final BPartnerId bpartnerId = context.getBpartnerId();

		BPartnerLocationId existingBPLocationId = null;
		if (context.getLocationId() != null)
		{
			existingBPLocationId = context.getLocationId();
		}

		if (existingBPLocationId == null
				&& jsonBPartnerLocation != null
				&& jsonBPartnerLocation.getExternalId() != null)
		{
			existingBPLocationId = bpartnersRepo
					.getBPartnerLocationIdByExternalId(
							bpartnerId,
							JsonExternalIds.toExternalIdOrNull(jsonBPartnerLocation.getExternalId()))
					.orElse(null);
		}
		if (existingBPLocationId == null
				&& jsonBPartnerLocation != null
				&& jsonBPartnerLocation.getGln() != null)
		{
			existingBPLocationId = bpartnersRepo
					.getBPartnerLocationIdByGln(
							bpartnerId,
							GLN.ofString(jsonBPartnerLocation.getGln()))
					.orElse(null);
		}

		return existingBPLocationId;
	}

	private BPartnerContactId lookupContactIdOrNull(
			@Nullable final JsonRequestContact jsonBPartnerContact,
			@NonNull final BPartnerMasterDataContext context)
	{
		final BPartnerId bpartnerId = context.getBpartnerId();

		final BPartnerContactId existingContactId;
		if (context.getContactId() != null)
		{
			existingContactId = context.getContactId();
		}
		else if (jsonBPartnerContact != null && jsonBPartnerContact.getExternalId() != null)
		{
			existingContactId = bpartnersRepo
					.getContactIdByExternalId(
							bpartnerId,
							JsonExternalIds.toExternalIdOrNull(jsonBPartnerContact.getExternalId()))
					.orElse(null);
		}
		else
		{
			existingContactId = null;
		}
		return existingContactId;
	}

	private BPartnerInfo getCreateBPartnerInfo(
			@NonNull final JsonBPartnerInfo json,
			@NonNull final BPartnerMasterDataContext context)
	{
		final BPartnerId bpartnerId = getCreateBPartnerId(json.getBpartner(), context);

		final BPartnerMasterDataContext childContext = context.setIfNotNull(bpartnerId);
		final BPartnerLocationId bpartnerLocationId = getCreateBPartnerLocationId(json.getLocation(), childContext);
		final BPartnerContactId bpartnerContactId = getCreateBPartnerContactId(json.getContact(), childContext);

		return BPartnerInfo.builder()
				.bpartnerId(bpartnerId)
				.bpartnerLocationId(bpartnerLocationId)
				.contactId(bpartnerContactId)
				.build();
	}

	private BPartnerId getCreateBPartnerId(@NonNull final JsonRequestBPartner json, final BPartnerMasterDataContext context)
	{
		final BPartnerId result = bpartnerIdsByJson
				.compute(
						json,
						(existingJson, existingBPartnerId) -> createOrUpdateBPartnerId(json, context.setIfNotNull(existingBPartnerId)));
		return result;
	}

	private BPartnerId createOrUpdateBPartnerId(
			@NonNull final JsonRequestBPartner json,
			@NonNull final BPartnerMasterDataContext context)
	{
		final BPartnerId existingBPartnerId = context.getBpartnerId();

		final I_C_BPartner bpartnerRecord;
		if (existingBPartnerId != null)
		{
			bpartnerRecord = bpartnersRepo.getById(existingBPartnerId);
		}
		else
		{
			bpartnerRecord = newInstance(I_C_BPartner.class);
			bpartnerRecord.setAD_Org_ID(context.getOrgId().getRepoId());
			if (context.isBPartnerIsOrgBP())
			{
				bpartnerRecord.setAD_OrgBP_ID(context.getOrgId().getRepoId());
			}
		}

		updateBPartnerRecord(bpartnerRecord, json);
		permissionService.assertCanCreateOrUpdate(bpartnerRecord);
		bpartnersRepo.save(bpartnerRecord);

		return BPartnerId.ofRepoId(bpartnerRecord.getC_BPartner_ID());
	}

	private void updateBPartnerRecord(
			@NonNull final I_C_BPartner bpartnerRecord,
			@NonNull final JsonRequestBPartner from)
	{
		final boolean isNew = isNew(bpartnerRecord);

		final JsonExternalId externalId = from.getExternalId();
		if (externalId != null)
		{
			bpartnerRecord.setExternalId(externalId.getValue());
		}

		final String code = from.getCode();
		if (!Check.isEmpty(code, true))
		{
			bpartnerRecord.setValue(code);
		}

		final String name = from.getName();
		if (!Check.isEmpty(name, true))
		{
			bpartnerRecord.setName(name);
			if (Check.isEmpty(bpartnerRecord.getCompanyName(), true))
			{
				bpartnerRecord.setCompanyName(name);
			}
		}
		else if (isNew)
		{
			throw new MissingPropertyException("JsonBPartner.name", from);
		}

		bpartnerRecord.setIsCustomer(true);
	}

	public JsonRequestBPartner getJsonBPartnerById(@NonNull final BPartnerId bpartnerId)
	{
		return bpartnerIdsByJson
				.inverse()
				.compute(
						bpartnerId,
						(id, existingJsonBPartner) -> {

							if (existingJsonBPartner != null)
							{
								return existingJsonBPartner;
							}
							final I_C_BPartner bpartnerRecord = bpartnersRepo.getById(id);
							Check.assumeNotNull(bpartnerRecord, "bpartner shall exist for {}", bpartnerId);

							return JsonRequestBPartner
									.builder()
									.code(bpartnerRecord.getValue())
									.externalId(JsonExternalId.ofOrNull(bpartnerRecord.getExternalId()))
									.name(bpartnerRecord.getName())
									.companyName(bpartnerRecord.getCompanyName())
									.build();
						});
	}

	private BPartnerLocationId getCreateBPartnerLocationId(@Nullable final JsonRequestLocation json, @NonNull final BPartnerMasterDataContext context)
	{
		if (json == null)
		{
			return null;
		}

		return bpartnerLocationIdsByExternalId
				.compute(
						json.getExternalId(),
						(externalId, existingBPLocationId) -> createOrUpdateBPartnerLocationId(json, context.setIfNotNull(existingBPLocationId)));
	}

	private BPartnerLocationId createOrUpdateBPartnerLocationId(
			@NonNull final JsonRequestLocation jsonBPartnerLocation,
			@NonNull final BPartnerMasterDataContext context)
	{
		final BPartnerId bpartnerId = Check.assumeNotNull(context.getBpartnerId(),
				"The given context needs to contain a bpartnerId when this method is called; context={}, jsonBPartnerLocation={}",
				context, jsonBPartnerLocation);

		final BPartnerLocationId existingBPLocationId = context.getLocationId();
		final OrgId orgId = context.getOrgId();

		final I_C_BPartner_Location bpLocationRecord;
		if (existingBPLocationId != null)
		{
			bpLocationRecord = bpartnersRepo.getBPartnerLocationById(existingBPLocationId);
		}
		else
		{
			bpLocationRecord = newInstance(I_C_BPartner_Location.class);
			bpLocationRecord.setAD_Org_ID(orgId.getRepoId());
		}

		updateBPartnerLocationRecord(bpLocationRecord, bpartnerId, jsonBPartnerLocation);
		permissionService.assertCanCreateOrUpdate(bpLocationRecord);
		bpartnersRepo.save(bpLocationRecord);
		final BPartnerLocationId bpartnerLocationId = BPartnerLocationId.ofRepoId(bpartnerId, bpLocationRecord.getC_BPartner_Location_ID());

		if (context.isBPartnerIsOrgBP())
		{
			orgDAO.createOrUpdateOrgInfo(OrgInfoUpdateRequest.builder()
					.orgId(orgId)
					.orgBPartnerLocationId(Optional.of(bpartnerLocationId))
					.build());
		}

		return bpartnerLocationId;
	}

	private void updateBPartnerLocationRecord(
			@NonNull final I_C_BPartner_Location bpLocationRecord,
			@NonNull final BPartnerId bpartnerId,
			@NonNull final JsonRequestLocation json)
	{
		bpLocationRecord.setC_BPartner_ID(bpartnerId.getRepoId());
		bpLocationRecord.setIsShipTo(true);
		bpLocationRecord.setIsBillTo(true);

		bpLocationRecord.setGLN(json.getGln());
		bpLocationRecord.setExternalId(json.getExternalId().getValue());

		final boolean newOrLocationHasChanged = isNew(bpLocationRecord) || !json.equals(toJsonBPartnerLocation(bpLocationRecord));
		if (newOrLocationHasChanged)
		{
			final String countryCode = json.getCountryCode();
			if (Check.isEmpty(countryCode))
			{
				throw new MissingPropertyException("JsonBPartnerLocation.countryCode", json);
			}
			final CountryId countryId = countryRepo.getCountryIdByCountryCode(countryCode);

			// NOTE: C_Location table might be heavily used, so it's better to create the address OOT to not lock it.
			final I_C_Location locationRecord = newInstanceOutOfTrx(I_C_Location.class);
			locationRecord.setAddress1(json.getAddress1());
			locationRecord.setAddress2(json.getAddress2());
			locationRecord.setPostal(locationRecord.getPostal());
			locationRecord.setCity(locationRecord.getCity());
			locationRecord.setC_Country_ID(countryId.getRepoId());

			locationsRepo.save(locationRecord);

			bpLocationRecord.setC_Location_ID(locationRecord.getC_Location_ID());
		}
	}

	public JsonRequestLocation getJsonBPartnerLocationById(final BPartnerLocationId bpartnerLocationId)
	{
		if (bpartnerLocationId == null)
		{
			return null;
		}

		final I_C_BPartner_Location bpLocationRecord = bpartnersRepo.getBPartnerLocationById(bpartnerLocationId);
		if (bpLocationRecord == null)
		{
			return null;
		}

		return toJsonBPartnerLocation(bpLocationRecord);
	}

	private JsonRequestLocation toJsonBPartnerLocation(@NonNull final I_C_BPartner_Location bpLocationRecord)
	{
		final I_C_Location location = Check.assumeNotNull(bpLocationRecord.getC_Location(), "The given bpLocationRecord needs to have a C_Location; bpLocationRecord={}", bpLocationRecord);

		final String countryCode = countryRepo.retrieveCountryCode2ByCountryId(CountryId.ofRepoId(location.getC_Country_ID()));

		return JsonRequestLocation
				.builder()
				.externalId(JsonExternalId.of(bpLocationRecord.getExternalId()))
				.gln(bpLocationRecord.getGLN())
				.address1(location.getAddress1())
				.address2(location.getAddress2())
				.postal(location.getPostal())
				.city(location.getCity())
				.region(location.getRegionName())
				.countryCode(countryCode)
				.build();
	}

	private BPartnerContactId getCreateBPartnerContactId(final JsonRequestContact json, final BPartnerMasterDataContext context)
	{
		if (json == null)
		{
			return null;
		}

		return bpartnerContactIdsByExternalId
				.compute(
						json.getExternalId(),
						(externalId, existingContactId) -> createOrUpdateBPartnerContactId(json, context.setIfNotNull(existingContactId)));
	}

	private BPartnerContactId createOrUpdateBPartnerContactId(
			@NonNull final JsonRequestContact jsonBPartnerContact,
			@NonNull final BPartnerMasterDataContext context)
	{
		final BPartnerId bpartnerId = Check.assumeNotNull(context.getBpartnerId(),
				"The given context needs to contain a bpartnerId when this method is called; context={}, jsonBPartnerLocation={}",
				context, jsonBPartnerContact);

		final BPartnerContactId existingContactId = context.getContactId();

		I_AD_User contactRecord;
		if (existingContactId != null)
		{
			contactRecord = bpartnersRepo.getContactById(existingContactId);
		}
		else
		{
			contactRecord = newInstance(I_AD_User.class);
			contactRecord.setAD_Org_ID(context.getOrgId().getRepoId());
		}

		updateBPartnerContactRecord(contactRecord, bpartnerId, jsonBPartnerContact);
		permissionService.assertCanCreateOrUpdate(contactRecord);
		bpartnersRepo.save(contactRecord);

		return BPartnerContactId.ofRepoId(bpartnerId, contactRecord.getAD_User_ID());
	}

	private void updateBPartnerContactRecord(final I_AD_User bpContactRecord, final BPartnerId bpartnerId, final JsonRequestContact json)
	{
		bpContactRecord.setC_BPartner_ID(bpartnerId.getRepoId());
		bpContactRecord.setName(json.getName());
		bpContactRecord.setEMail(json.getEmail());
		bpContactRecord.setPhone(json.getPhone());
		bpContactRecord.setExternalId(json.getExternalId().getValue());
	}

	public JsonRequestContact getJsonBPartnerContactById(final BPartnerContactId bpartnerContactId)
	{
		if (bpartnerContactId == null)
		{
			return null;
		}

		final I_AD_User bpContactRecord = bpartnersRepo.getContactById(bpartnerContactId);
		if (bpContactRecord == null)
		{
			return null;
		}

		return JsonRequestContact
				.builder()
				.externalId(JsonExternalId.of(bpContactRecord.getExternalId()))
				.name(bpContactRecord.getName())
				.email(bpContactRecord.getEMail())
				.phone(bpContactRecord.getPhone())
				.build();
	}
}
