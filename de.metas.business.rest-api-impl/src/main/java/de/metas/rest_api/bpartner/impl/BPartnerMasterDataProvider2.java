package de.metas.rest_api.bpartner.impl;

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


public class BPartnerMasterDataProvider2
{
//	public static BPartnerMasterDataProvider2 of(
//			@Nullable final Properties ctx,
//			@Nullable final PermissionService permissionService)
//	{
//		return new BPartnerMasterDataProvider2(
//				coalesceSuppliers(
//						() -> permissionService,
//						() -> PermissionService.of(ctx)));
//	}
//
//	private final BiMap<JsonBPartner, BPartnerId> bpartnerIdsByJson = HashBiMap.<JsonBPartner, BPartnerId> create();
//	private final Map<JsonExternalId, BPartnerLocationId> bpartnerLocationIdsByExternalId = new HashMap<>();
//	private final Map<JsonExternalId, BPartnerContactId> bpartnerContactIdsByExternalId = new HashMap<>();
//
//	private final IBPartnerDAO bpartnersRepo = Services.get(IBPartnerDAO.class);
//	private final ILocationDAO locationsRepo = Services.get(ILocationDAO.class);
//	private final ICountryDAO countryRepo = Services.get(ICountryDAO.class);
//	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
//
//	private final PermissionService permissionService;
//
//	@VisibleForTesting
//	BPartnerMasterDataProvider2(@NonNull final PermissionService permissionService)
//	{
//		this.permissionService = permissionService;
//	}
//
//	public BPartnerComposite getCreateOrgBPartnerComposite(
//			@NonNull final JsonBPartnerComposite jsonBPartnerComposite,
//			@NonNull final OrgId orgId)
//	{
//		final BPartnerMasterDataContext context = BPartnerMasterDataContext
//				.builder()
//				.orgId(orgId)
//				.bPartnerIsOrgBP(true)
//				.build();
//
//		return handleBPartnerInfoWithContext(jsonBPartnerComposite, context);
//	}
//
//	public final BPartnerComposite getCreateBPartnerInfo(
//			@Nullable final JsonBPartnerComposite jsonBPartnerComposite,
//			final OrgId orgId)
//	{
//		if (jsonBPartnerComposite == null)
//		{
//			return null;
//		}
//
//		final BPartnerMasterDataContext context = BPartnerMasterDataContext.ofOrg(orgId);
//
//		return handleBPartnerInfoWithContext(jsonBPartnerComposite, context);
//	}
//
//	@Value
//	private static class CachingKey
//	{
//		OrgId orgId;
//		JsonBPartnerComposite jsonBPartnerInfo;
//	}
//
//	private final CCache<CachingKey, BPartnerComposite> bpartnerInfoCache = CCache
//			.<CachingKey, BPartnerComposite> builder()
//			.cacheName(this.getClass().getSimpleName() + "-BPartnerCompositeCache")
//			.build();
//
//	private BPartnerComposite handleBPartnerInfoWithContext(
//			@NonNull final JsonBPartnerComposite jsonBPartnerComposite,
//			@NonNull final BPartnerMasterDataContext context)
//	{
//		final CachingKey key = new CachingKey(context.getOrgId(), jsonBPartnerComposite);
//		return bpartnerInfoCache.getOrLoad(key, () -> handleBPartnerInfoWithContext0(jsonBPartnerComposite, context));
//	}
//
//	private BPartnerComposite handleBPartnerInfoWithContext0(
//			@NonNull final JsonBPartnerComposite jsonBPartnerComposite,
//			@NonNull final BPartnerMasterDataContext context)
//	{
//		final SyncAdvise.IfNotExists ifNotExists = jsonBPartnerComposite.getSyncAdvise().getIfNotExists();
//
//		final BPartnerComposite bPartnerComposite = lookupBPartnerInfoOrNull(jsonBPartnerComposite, context);
//		if (bPartnerComposite == null)
//		{
//			switch (ifNotExists)
//			{
//				case FAIL:
//					Check.fail("An exception should have been thrown already in lookupBPartnerInfoOrNull");
//				case CREATE:
//					return getCreateBPartnerInfo(jsonBPartnerComposite, context);
//				default:
//					Check.fail("Unexpected IfNotExists={}", ifNotExists);
//					return null;
//			}
//		}
//		else
//		{
//			final IfExists ifExists = jsonBPartnerComposite.getSyncAdvise().getIfExists();
//			switch (ifExists)
//			{
//				case UPDATE_MERGE:
//					final BPartnerMasterDataContext childContext = context
//							.setIfNotNull(jsonBPartnerComposite.getBpartnerId())
//							.setIfNotNull(jsonBPartnerComposite.getBpartnerLocationId())
//							.setIfNotNull(jsonBPartnerComposite.getContactId());
//					return getCreateBPartnerInfo(jsonBPartnerComposite, childContext);
//				case DONT_UPDATE:
//					return jsonBPartnerComposite;
//				default:
//					Check.fail("Unepected IfExists={}", ifExists);
//					return null;
//			}
//		}
//	}
//
//	private final BPartnerComposite lookupBPartnerInfoOrNull(
//			@NonNull final JsonBPartnerComposite jsonBPartnerComposite,
//			@NonNull final BPartnerMasterDataContext context)
//	{
//		final BPartnerId bpartnerId = lookupBPartnerIdOrNull(
//				jsonBPartnerComposite,
//				context);
//		if (bpartnerId == null)
//		{
//			return null;
//		}
//
//		final BPartnerCompositeBuilder result = BPartnerComposite.builder().bpartnerId(bpartnerId);
//
//		// TODO iterate locations&contacts
//		final BPartnerLocationId bpartnerLocationId = lookupBPartnerLocationIdOrNull(
//				jsonBPartnerComposite.getLocation(),
//				context.setIfNotNull(bpartnerId));
//
//		final BPartnerContactId contactId = lookupContactIdOrNull(
//				jsonBPartnerComposite.getContact(),
//				context.setIfNotNull(bpartnerId));
//
//		return result
//				.bpartnerLocationId(bpartnerLocationId)
//				.contactId(contactId)
//				.build();
//	}
//
//	private final BPartnerId lookupBPartnerIdOrNull(
//			@NonNull final JsonBPartnerComposite jsonBPartnerComposite,
//			@NonNull final BPartnerMasterDataContext context)
//	{
//		final JsonBPartner json = jsonBPartnerComposite.getBpartner();
//
//		// ..context
//		if (context.getBpartnerId() != null)
//		{
//			return context.getBpartnerId();
//		}
//
//		final SyncAdvise syncAdvise = jsonBPartnerComposite.getSyncAdvise();
//		final ExternalId externalId = JsonExternalIds.toExternalIdOrNull(json.getExternalId());
//
//		final BPartnerQuery.BPartnerQueryBuilder query = BPartnerQuery.builder()
//				.onlyOrgId(context.getOrgId())
//				.onlyOrgId(OrgId.ANY)
//				.outOfTrx(syncAdvise.isLoadReadOnly())
//				.failIfNotExists(syncAdvise.isFailIfNotExists())
//				.externalId(externalId)
//				.bpartnerValue(json.getCode());
//
//		// iterate, use first location that has a GLN
//		final Optional<String> gln = jsonBPartnerComposite.extractFirstLocationGLN();
//		if (gln.isPresent())
//		{
//			query.locationGln(gln.get());
//		}
//
//		return bpartnersRepo
//				.retrieveBPartnerIdBy(query.build())
//				.orElse(null);
//	}
//
//	private final BPartnerLocationId lookupBPartnerLocationIdOrNull(
//			@NonNull final JsonBPartnerLocation jsonBPartnerLocation,
//			@NonNull final BPartnerMasterDataContext context)
//	{
//		final BPartnerId bpartnerId = assumeNotNull(context.getBpartnerId(),
//				"When this method is called, the context param already needs to have a bpartnerId; context={}", context);
//
//		BPartnerLocationId existingBPLocationId = null;
//		if (context.getLocationId() != null)
//		{
//			existingBPLocationId = context.getLocationId();
//		}
//
//		if (existingBPLocationId == null && jsonBPartnerLocation.getExternalId() != null)
//		{
//			existingBPLocationId = bpartnersRepo
//					.getBPartnerLocationIdByExternalId(
//							bpartnerId,
//							JsonExternalIds.toExternalIdOrNull(jsonBPartnerLocation.getExternalId()))
//					.orElse(null);
//		}
//		if (existingBPLocationId == null && jsonBPartnerLocation.getGln() != null)
//		{
//			existingBPLocationId = bpartnersRepo
//					.getBPartnerLocationIdByGln(
//							bpartnerId,
//							jsonBPartnerLocation.getGln())
//					.orElse(null);
//		}
//
//		return existingBPLocationId;
//	}
//
//	private BPartnerContactId lookupContactIdOrNull(
//			@Nullable final JsonContact jsonBPartnerContact,
//			@NonNull final BPartnerMasterDataContext context)
//	{
//		final BPartnerId bpartnerId = context.getBpartnerId();
//
//		final BPartnerContactId existingContactId;
//		if (context.getContactId() != null)
//		{
//			existingContactId = context.getContactId();
//		}
//		else if (jsonBPartnerContact != null && jsonBPartnerContact.getExternalId() != null)
//		{
//			existingContactId = bpartnersRepo
//					.getContactIdByExternalId(
//							bpartnerId,
//							JsonExternalIds.toExternalIdOrNull(jsonBPartnerContact.getExternalId()))
//					.orElse(null);
//		}
//		else
//		{
//			existingContactId = null;
//		}
//		return existingContactId;
//	}
//
//	private BPartnerComposite getCreateBPartnerInfo(
//			@NonNull final JsonBPartnerComposite json,
//			@NonNull final BPartnerMasterDataContext context)
//	{
//		// iterate locations&contacts
//		final BPartnerId bpartnerId = getCreateBPartnerId(json.getBpartner(), context);
//
//		BPartnerCompositeBuilder result = BPartnerComposite.builder()
//				.bpartnerId(bpartnerId);
//
//		final BPartnerMasterDataContext childContext = context.setIfNotNull(bpartnerId);
//
//		for (final JsonBPartnerLocation location : json.getLocations())
//		{
//			final BPartnerLocationId bpartnerLocationId = getCreateBPartnerLocationId(location, childContext);
//			result.bpartnerLocationId(bpartnerLocationId);
//		}
//		for (final JsonContact contact : json.getContacts())
//		{
//			final BPartnerContactId bpartnerContactId = getCreateBPartnerContactId(contact, childContext);
//			result.contactId(bpartnerContactId);
//		}
//
//		return result.build();
//	}
//
//	private BPartnerId getCreateBPartnerId(@NonNull final JsonBPartner json, final BPartnerMasterDataContext context)
//	{
//		final BPartnerId result = bpartnerIdsByJson
//				.compute(
//						json,
//						(existingJson, existingBPartnerId) -> createOrUpdateBPartnerId(json, context.setIfNotNull(existingBPartnerId)));
//		return result;
//	}
//
//	private BPartnerId createOrUpdateBPartnerId(
//			@NonNull final JsonBPartner json,
//			@NonNull final BPartnerMasterDataContext context)
//	{
//		final BPartnerId existingBPartnerId = context.getBpartnerId();
//
//		final I_C_BPartner bpartnerRecord;
//		if (existingBPartnerId != null)
//		{
//			bpartnerRecord = bpartnersRepo.getById(existingBPartnerId);
//		}
//		else
//		{
//			bpartnerRecord = newInstance(I_C_BPartner.class);
//			bpartnerRecord.setAD_Org_ID(context.getOrgId().getRepoId());
//			if (context.isBPartnerIsOrgBP())
//			{
//				bpartnerRecord.setAD_OrgBP_ID(context.getOrgId().getRepoId());
//			}
//		}
//
//		updateBPartnerRecord(bpartnerRecord, json);
//		permissionService.assertCanCreateOrUpdate(bpartnerRecord);
//		bpartnersRepo.save(bpartnerRecord);
//
//		return BPartnerId.ofRepoId(bpartnerRecord.getC_BPartner_ID());
//	}
//
//	private final void updateBPartnerRecord(final I_C_BPartner bpartnerRecord, final JsonBPartner from)
//	{
//		final boolean isNew = isNew(bpartnerRecord);
//
//		final JsonExternalId externalId = from.getExternalId();
//		if (externalId != null)
//		{
//			bpartnerRecord.setExternalId(externalId.getValue());
//		}
//
//		final String code = from.getCode();
//		if (!Check.isEmpty(code, true))
//		{
//			bpartnerRecord.setValue(code);
//		}
//
//		final String name = from.getName();
//		if (!Check.isEmpty(name, true))
//		{
//			bpartnerRecord.setName(name);
//			if (Check.isEmpty(bpartnerRecord.getCompanyName(), true))
//			{
//				bpartnerRecord.setCompanyName(name);
//			}
//		}
//		else if (isNew)
//		{
//			throw new MissingPropertyException("Missing property Name; JsonBPartner={}", from);
//		}
//
//		bpartnerRecord.setIsCustomer(true);
//		// bpartnerRecord.setC_BP_Group_ID(C_BP_Group_ID);
//	}
//
//	public JsonBPartner getJsonBPartnerById(@NonNull final BPartnerId bpartnerId)
//	{
//		return bpartnerIdsByJson
//				.inverse()
//				.compute(
//						bpartnerId,
//						(id, existingJsonBPartner) -> {
//
//							if (existingJsonBPartner != null)
//							{
//								return existingJsonBPartner;
//							}
//							final I_C_BPartner bpartnerRecord = bpartnersRepo.getById(id);
//							Check.assumeNotNull(bpartnerRecord, "bpartner shall exist for {}", bpartnerId);
//
//							return JsonBPartner
//									.builder()
//									.code(bpartnerRecord.getValue())
//									.externalId(JsonExternalId.ofOrNull(bpartnerRecord.getExternalId()))
//									.name(bpartnerRecord.getName())
//									.companyName(bpartnerRecord.getCompanyName())
//									.build();
//						});
//	}
//
//	private BPartnerLocationId getCreateBPartnerLocationId(@Nullable final JsonBPartnerLocation json, @NonNull final BPartnerMasterDataContext context)
//	{
//		if (json == null)
//		{
//			return null;
//		}
//
//		return bpartnerLocationIdsByExternalId
//				.compute(
//						json.getExternalId(),
//						(externalId, existingBPLocationId) -> createOrUpdateBPartnerLocationId(json, context.setIfNotNull(existingBPLocationId)));
//	}
//
//	private BPartnerLocationId createOrUpdateBPartnerLocationId(
//			@NonNull final JsonBPartnerLocation jsonBPartnerLocation,
//			@NonNull final BPartnerMasterDataContext context)
//	{
//		final BPartnerId bpartnerId = Check.assumeNotNull(context.getBpartnerId(),
//				"The given context needs to contain a bpartnerId when this method is called; context={}, jsonBPartnerLocation={}",
//				context, jsonBPartnerLocation);
//
//		final BPartnerLocationId existingBPLocationId = context.getLocationId();
//		final int orgRepoId = context.getOrgId().getRepoId();
//
//		final I_C_BPartner_Location bpLocationRecord;
//		if (existingBPLocationId != null)
//		{
//			bpLocationRecord = bpartnersRepo.getBPartnerLocationById(existingBPLocationId);
//		}
//		else
//		{
//			bpLocationRecord = newInstance(I_C_BPartner_Location.class);
//			bpLocationRecord.setAD_Org_ID(orgRepoId);
//		}
//
//		updateBPartnerLocationRecord(bpLocationRecord, bpartnerId, jsonBPartnerLocation);
//		permissionService.assertCanCreateOrUpdate(bpLocationRecord);
//		bpartnersRepo.save(bpLocationRecord);
//
//		if (context.isBPartnerIsOrgBP())
//		{
//			I_AD_OrgInfo orgInfoRecord = create(orgDAO.retrieveOrgInfo(Env.getCtx(), orgRepoId, ITrx.TRXNAME_ThreadInherited), I_AD_OrgInfo.class);
//			if (orgInfoRecord == null)
//			{
//				orgInfoRecord = newInstance(I_AD_OrgInfo.class);
//				orgInfoRecord.setAD_Org_ID(orgRepoId);
//			}
//			orgInfoRecord.setOrgBP_Location_ID(bpLocationRecord.getC_BPartner_Location_ID());
//			saveRecord(orgInfoRecord);
//		}
//
//		return BPartnerLocationId.ofRepoId(bpartnerId, bpLocationRecord.getC_BPartner_Location_ID());
//	}
//
//	private void updateBPartnerLocationRecord(
//			@NonNull final I_C_BPartner_Location bpLocationRecord,
//			@NonNull final BPartnerId bpartnerId,
//			@NonNull final JsonBPartnerLocation json)
//	{
//		bpLocationRecord.setC_BPartner_ID(bpartnerId.getRepoId());
//		bpLocationRecord.setIsShipTo(true);
//		bpLocationRecord.setIsBillTo(true);
//
//		bpLocationRecord.setGLN(json.getGln());
//		bpLocationRecord.setExternalId(json.getExternalId().getValue());
//
//		final boolean newOrLocationHasChanged = isNew(bpLocationRecord) || !json.equals(toJsonBPartnerLocation(bpLocationRecord));
//		if (newOrLocationHasChanged)
//		{
//			final String countryCode = json.getCountryCode();
//			if (Check.isEmpty(countryCode))
//			{
//				throw new MissingPropertyException("Missing propery CountryCode; JsonBPartnerLocation={}", json);
//			}
//			final CountryId countryId = countryRepo.getCountryIdByCountryCode(countryCode);
//
//			// NOTE: C_Location table might be heavily used, so it's better to create the address OOT to not lock it.
//			final I_C_Location locationRecord = newInstanceOutOfTrx(I_C_Location.class);
//			locationRecord.setAddress1(json.getAddress1());
//			locationRecord.setAddress2(json.getAddress2());
//			locationRecord.setPostal(locationRecord.getPostal());
//			locationRecord.setCity(locationRecord.getCity());
//			locationRecord.setC_Country_ID(countryId.getRepoId());
//
//			locationsRepo.save(locationRecord);
//
//			bpLocationRecord.setC_Location_ID(locationRecord.getC_Location_ID());
//		}
//	}
//
//	public JsonBPartnerLocation getJsonBPartnerLocationById(final BPartnerLocationId bpartnerLocationId)
//	{
//		if (bpartnerLocationId == null)
//		{
//			return null;
//		}
//
//		final I_C_BPartner_Location bpLocationRecord = bpartnersRepo.getBPartnerLocationById(bpartnerLocationId);
//		if (bpLocationRecord == null)
//		{
//			return null;
//		}
//
//		return toJsonBPartnerLocation(bpLocationRecord);
//	}
//
//	private JsonBPartnerLocation toJsonBPartnerLocation(@NonNull final I_C_BPartner_Location bpLocationRecord)
//	{
//		final I_C_Location location = Check.assumeNotNull(bpLocationRecord.getC_Location(), "The given bpLocationRecord needs to have a C_Location; bpLocationRecord={}", bpLocationRecord);
//
//		final String countryCode = countryRepo.retrieveCountryCode2ByCountryId(CountryId.ofRepoId(location.getC_Country_ID()));
//
//		return JsonBPartnerLocation
//				.builder()
//				.externalId(JsonExternalId.of(bpLocationRecord.getExternalId()))
//				.gln(bpLocationRecord.getGLN())
//				.address1(location.getAddress1())
//				.address2(location.getAddress2())
//				.postal(location.getPostal())
//				.city(location.getCity())
//				.region(location.getRegionName())
//				.countryCode(countryCode)
//				.build();
//	}
//
//	private BPartnerContactId getCreateBPartnerContactId(final JsonContact json, final BPartnerMasterDataContext context)
//	{
//		if (json == null)
//		{
//			return null;
//		}
//
//		return bpartnerContactIdsByExternalId
//				.compute(
//						json.getExternalId(),
//						(externalId, existingContactId) -> createOrUpdateBPartnerContactId(json, context.setIfNotNull(existingContactId)));
//	}
//
//	private BPartnerContactId createOrUpdateBPartnerContactId(
//			@NonNull final JsonContact jsonBPartnerContact,
//			@NonNull final BPartnerMasterDataContext context)
//	{
//		final BPartnerId bpartnerId = Check.assumeNotNull(context.getBpartnerId(),
//				"The given context needs to contain a bpartnerId when this method is called; context={}, jsonBPartnerLocation={}",
//				context, jsonBPartnerContact);
//
//		final BPartnerContactId existingContactId = context.getContactId();
//
//		I_AD_User contactRecord;
//		if (existingContactId != null)
//		{
//			contactRecord = bpartnersRepo.getContactById(existingContactId);
//		}
//		else
//		{
//			contactRecord = newInstance(I_AD_User.class);
//			contactRecord.setAD_Org_ID(context.getOrgId().getRepoId());
//		}
//
//		updateBPartnerContactRecord(contactRecord, bpartnerId, jsonBPartnerContact);
//		permissionService.assertCanCreateOrUpdate(contactRecord);
//		bpartnersRepo.save(contactRecord);
//
//		return BPartnerContactId.ofRepoId(bpartnerId, contactRecord.getAD_User_ID());
//	}
//
//	private void updateBPartnerContactRecord(final I_AD_User bpContactRecord, final BPartnerId bpartnerId, final JsonContact json)
//	{
//		bpContactRecord.setC_BPartner_ID(bpartnerId.getRepoId());
//		bpContactRecord.setName(json.getName());
//		bpContactRecord.setEMail(json.getEmail());
//		bpContactRecord.setPhone(json.getPhone());
//		bpContactRecord.setExternalId(json.getExternalId().getValue());
//	}
//
//	public JsonContact getJsonBPartnerContactById(final BPartnerContactId bpartnerContactId)
//	{
//		if (bpartnerContactId == null)
//		{
//			return null;
//		}
//
//		final I_AD_User bpContactRecord = bpartnersRepo.getContactById(bpartnerContactId);
//		if (bpContactRecord == null)
//		{
//			return null;
//		}
//
//		return JsonContact
//				.builder()
//				.externalId(JsonExternalId.of(bpContactRecord.getExternalId()))
//				.name(bpContactRecord.getName())
//				.email(bpContactRecord.getEMail())
//				.phone(bpContactRecord.getPhone())
//				.build();
//	}
}
