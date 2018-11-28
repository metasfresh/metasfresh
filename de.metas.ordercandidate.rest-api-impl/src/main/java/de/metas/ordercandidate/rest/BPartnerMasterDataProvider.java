package de.metas.ordercandidate.rest;

import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.isNew;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import lombok.NonNull;

import javax.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.StringJoiner;

import org.adempiere.service.IOrgDAO;
import org.adempiere.service.OrgId;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.compiere.util.Util;

import de.metas.adempiere.model.I_AD_OrgInfo;
import de.metas.adempiere.service.ICountryDAO;
import de.metas.adempiere.service.ILocationDAO;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.ordercandidate.api.OLCandBPartnerInfo;
import de.metas.ordercandidate.rest.SyncAdvise.IfExists;
import de.metas.ordercandidate.rest.SyncAdvise.IfNotExists;
import de.metas.ordercandidate.rest.exceptions.BPartnerInfoNotFoundException;
import de.metas.ordercandidate.rest.exceptions.MissingPropertyException;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;

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

public class BPartnerMasterDataProvider
{
	public static BPartnerMasterDataProvider of(
			@Nullable final Properties ctx,
			@Nullable final PermissionService permissionService)
	{
		return new BPartnerMasterDataProvider(
				Util.coalesceSuppliers(
						() -> permissionService,
						() -> PermissionService.of(ctx)));
	}

	private final Map<JsonBPartner, BPartnerId> bpartnerIdsByJson = new HashMap<>();
	private final Map<String, BPartnerLocationId> bpartnerLocationIdsByExternalId = new HashMap<>();
	private final Map<String, BPartnerContactId> bpartnerContactIdsByExternalId = new HashMap<>();

	private final IBPartnerDAO bpartnersRepo = Services.get(IBPartnerDAO.class);
	private final ILocationDAO locationsRepo = Services.get(ILocationDAO.class);
	private final ICountryDAO countryRepo = Services.get(ICountryDAO.class);

	private final PermissionService permissionService;

	public BPartnerMasterDataProvider(@NonNull final PermissionService permissionService)
	{
		this.permissionService = permissionService;
	}

	public OLCandBPartnerInfo getCreateOrgBPartnerInfo(
			@NonNull final JsonBPartnerInfo jsonBPartnerInfo,
			@NonNull final OrgId orgId)
	{
		final Context context = Context
				.builder()
				.orgId(orgId)
				.bPartnerIsOrgBP(true)
				.build();

		return handleBPartnerInfoWithContext(jsonBPartnerInfo, context);
	}

	public final OLCandBPartnerInfo getCreateBPartnerInfo(
			@Nullable final JsonBPartnerInfo jsonBPartnerInfo,
			final OrgId orgId)
	{
		if (jsonBPartnerInfo == null)
		{
			return null;
		}

		final Context context = Context.ofOrg(orgId);

		return handleBPartnerInfoWithContext(jsonBPartnerInfo, context);
	}

	private OLCandBPartnerInfo handleBPartnerInfoWithContext(
			@NonNull final JsonBPartnerInfo jsonBPartnerInfo,
			@NonNull final Context context)
	{
		final SyncAdvise.IfNotExists ifNotExists = jsonBPartnerInfo.getSyncAdvise().getIfNotExists();

		final OLCandBPartnerInfo bPartnerInfo = lookupBPartnerInfoOrNull(jsonBPartnerInfo, context, ifNotExists);
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
				case UPDATE:
					final Context childContext = context
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

	private final OLCandBPartnerInfo lookupBPartnerInfoOrNull(
			@NonNull final JsonBPartnerInfo jsonBPartnerInfo,
			@NonNull final Context context,
			@NonNull final IfNotExists ifNotExists)
	{
		final BPartnerId bpartnerId = lookupBPartnerIdOrNull(
				jsonBPartnerInfo,
				context,
				ifNotExists);
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

		return OLCandBPartnerInfo.builder()
				.bpartnerId(bpartnerId)
				.bpartnerLocationId(bpartnerLocationId)
				.contactId(contactId)
				.build();
	}

	private final BPartnerId lookupBPartnerIdOrNull(
			@NonNull final JsonBPartnerInfo jsonBPartnerInfo,
			@NonNull final Context context,
			@NonNull final IfNotExists ifNotExists)
	{
		final JsonBPartner json = jsonBPartnerInfo.getBpartner();

		// lookup via..
		BPartnerId existingBPartnerId = null;

		final StringJoiner searchedByInfo = new StringJoiner(",");

		// ..context
		if (context.getBpartnerId() != null)
		{
			existingBPartnerId = context.getBpartnerId();
		}
		// ..externalId
		else if (json.getExternalId() != null)
		{
			existingBPartnerId = bpartnersRepo
					.getBPartnerIdByExternalIdIfExists(
							json.getExternalId(),
							context.getOrgId())
					.orElse(null);

			searchedByInfo.add(StringUtils.formatMessage("ExternalId={}", json.getExternalId()));
		}
		// ..code (aka value)
		if (existingBPartnerId == null && json.getCode() != null)
		{
			existingBPartnerId = bpartnersRepo
					.getBPartnerIdByValueIfExists(
							json.getCode(),
							context.getOrgId())
					.orElse(null);

			searchedByInfo.add(StringUtils.formatMessage("Value/Code={}", json.getCode()));
		}
		// BPLocation's GLN
		if (existingBPartnerId == null)
		{
			final JsonBPartnerLocation jsonLocation = jsonBPartnerInfo.getLocation();
			if (jsonLocation != null && jsonLocation.getGln() != null)
			{
				existingBPartnerId = bpartnersRepo
						.getBPartnerIdByLocatorGlnIfExists(
								jsonLocation.getGln(),
								context.getOrgId())
						.orElse(null);

				searchedByInfo.add(StringUtils.formatMessage("Location.GLN={}", jsonLocation.getGln()));
			}
		}

		if (existingBPartnerId == null && IfNotExists.FAIL.equals(ifNotExists))
		{
			final String msg = StringUtils.formatMessage("Found no existing BPartner; Searched via the following properties (one-by-one, may be empty): {}", searchedByInfo.toString());
			throw new BPartnerInfoNotFoundException(msg);
		}

		return existingBPartnerId;
	}

	private final BPartnerLocationId lookupBPartnerLocationIdOrNull(
			@NonNull final JsonBPartnerLocation jsonBPartnerLocation,
			@NonNull final Context context)
	{
		final BPartnerId bpartnerId = context.getBpartnerId();

		BPartnerLocationId existingBPLocationId = null;
		if (context.getLocationId() != null)
		{
			existingBPLocationId = context.getLocationId();
		}

		if (existingBPLocationId == null && jsonBPartnerLocation.getExternalId() != null)
		{
			existingBPLocationId = bpartnersRepo
					.getBPartnerLocationIdByExternalId(
							bpartnerId,
							jsonBPartnerLocation.getExternalId())
					.orElse(null);
		}
		if (existingBPLocationId == null && jsonBPartnerLocation.getGln() != null)
		{
			existingBPLocationId = bpartnersRepo
					.getBPartnerLocationIdByGln(
							bpartnerId,
							jsonBPartnerLocation.getGln())
					.orElse(null);
		}

		return existingBPLocationId;
	}

	private BPartnerContactId lookupContactIdOrNull(
			@Nullable final JsonBPartnerContact jsonBPartnerContact,
			@NonNull final Context context)
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
							jsonBPartnerContact.getExternalId())
					.orElse(null);
		}
		else
		{
			existingContactId = null;
		}
		return existingContactId;
	}

	private OLCandBPartnerInfo getCreateBPartnerInfo(
			@NonNull final JsonBPartnerInfo json,
			@NonNull final Context context)
	{
		final BPartnerId bpartnerId = getCreateBPartnerId(json.getBpartner(), context);

		final Context childContext = context.setIfNotNull(bpartnerId);
		final BPartnerLocationId bpartnerLocationId = getCreateBPartnerLocationId(json.getLocation(), childContext);
		final BPartnerContactId bpartnerContactId = getCreateBPartnerContactId(json.getContact(), childContext);

		return OLCandBPartnerInfo.builder()
				.bpartnerId(bpartnerId)
				.bpartnerLocationId(bpartnerLocationId)
				.contactId(bpartnerContactId)
				.build();
	}

	private BPartnerId getCreateBPartnerId(@NonNull final JsonBPartner json, final Context context)
	{
		return bpartnerIdsByJson
				.compute(
						json,
						(existingJson, existingBPartnerId) -> createOrUpdateBPartnerId(json, context.setIfNotNull(existingBPartnerId)));
	}

	private BPartnerId createOrUpdateBPartnerId(
			@NonNull final JsonBPartner json,
			@NonNull final Context context)
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

	private final void updateBPartnerRecord(final I_C_BPartner bpartnerRecord, final JsonBPartner from)
	{
		final boolean isNew = isNew(bpartnerRecord);

		final String externalId = from.getExternalId();
		if (!Check.isEmpty(externalId, true))
		{
			bpartnerRecord.setExternalId(externalId);
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
			throw new MissingPropertyException("Missing property Name; JsonBPartner={}", from);
		}

		bpartnerRecord.setIsCustomer(true);
		// bpartnerRecord.setC_BP_Group_ID(C_BP_Group_ID);
	}

	public JsonBPartner getJsonBPartnerById(@NonNull final BPartnerId bpartnerId)
	{
		final I_C_BPartner bpartnerRecord = bpartnersRepo.getById(bpartnerId);
		Check.assumeNotNull(bpartnerRecord, "bpartner shall exist for {}", bpartnerId);

		return JsonBPartner.builder()
				.code(bpartnerRecord.getValue())
				.name(bpartnerRecord.getName())
				.build();
	}

	private BPartnerLocationId getCreateBPartnerLocationId(@Nullable final JsonBPartnerLocation json, @NonNull final Context context)
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
			@NonNull final JsonBPartnerLocation jsonBPartnerLocation,
			@NonNull final Context context)
	{
		final BPartnerId bpartnerId = Check.assumeNotNull(context.getBpartnerId(),
				"The given context needs to contain a bpartnerId when this method is called; context={}, jsonBPartnerLocation={}",
				context, jsonBPartnerLocation);

		final BPartnerLocationId existingBPLocationId = context.getLocationId();
		final int orgRepoId = context.getOrgId().getRepoId();

		I_C_BPartner_Location bpLocationRecord;
		if (existingBPLocationId != null)
		{
			bpLocationRecord = bpartnersRepo.getBPartnerLocationById(existingBPLocationId);
		}
		else
		{
			bpLocationRecord = newInstance(I_C_BPartner_Location.class);
			bpLocationRecord.setAD_Org_ID(orgRepoId);
		}

		updateBPartnerLocationRecord(bpLocationRecord, bpartnerId, jsonBPartnerLocation);
		permissionService.assertCanCreateOrUpdate(bpLocationRecord);
		bpartnersRepo.save(bpLocationRecord);

		if (context.isBPartnerIsOrgBP())
		{
			I_AD_OrgInfo orgInfoRecord = create(Services.get(IOrgDAO.class).retrieveOrgInfo(orgRepoId), I_AD_OrgInfo.class);
			if (orgInfoRecord == null)
			{
				orgInfoRecord = newInstance(I_AD_OrgInfo.class);
				orgInfoRecord.setAD_Org_ID(orgRepoId);
			}
			orgInfoRecord.setOrgBP_Location_ID(bpLocationRecord.getC_BPartner_Location_ID());
			saveRecord(orgInfoRecord);
		}

		return BPartnerLocationId.ofRepoId(bpartnerId, bpLocationRecord.getC_BPartner_Location_ID());
	}

	private void updateBPartnerLocationRecord(
			@NonNull final I_C_BPartner_Location bpLocationRecord,
			@NonNull final BPartnerId bpartnerId,
			@NonNull final JsonBPartnerLocation json)
	{
		bpLocationRecord.setC_BPartner_ID(bpartnerId.getRepoId());
		bpLocationRecord.setIsShipTo(true);
		bpLocationRecord.setIsBillTo(true);

		bpLocationRecord.setGLN(json.getGln());

		bpLocationRecord.setExternalId(json.getExternalId());

		final boolean newOrLocationHasChanged = isNew(bpLocationRecord) || !json.equals(toJsonBPartnerLocation(bpLocationRecord));
		if (newOrLocationHasChanged)
		{
			final String countryCode = json.getCountryCode();
			if (Check.isEmpty(countryCode))
			{
				throw new MissingPropertyException("Missing propery CountryCode; JsonBPartnerLocation={}", json);
			}
			final int countryId = countryRepo.getCountryIdByCountryCode(countryCode);

			// NOTE: C_Location table might be heavily used, so it's better to create the address OOT to not lock it.
			final I_C_Location locationRecord = newInstanceOutOfTrx(I_C_Location.class);
			locationRecord.setAddress1(json.getAddress1());
			locationRecord.setAddress2(json.getAddress2());
			locationRecord.setPostal(locationRecord.getPostal());
			locationRecord.setCity(locationRecord.getCity());
			locationRecord.setC_Country_ID(countryId);

			locationsRepo.save(locationRecord);

			bpLocationRecord.setC_Location_ID(locationRecord.getC_Location_ID());
		}
	}

	public JsonBPartnerLocation getJsonBPartnerLocationById(final BPartnerLocationId bpartnerLocationId)
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

	private JsonBPartnerLocation toJsonBPartnerLocation(@NonNull final I_C_BPartner_Location bpLocationRecord)
	{
		final I_C_Location location = Check.assumeNotNull(bpLocationRecord.getC_Location(), "The given bpLocationRecord needs to have a C_Location; bpLocationRecord={}", bpLocationRecord);

		final String countryCode = countryRepo.retrieveCountryCode2ByCountryId(location.getC_Country_ID());

		return JsonBPartnerLocation.builder()
				.externalId(bpLocationRecord.getExternalId())
				.address1(location.getAddress1())
				.address2(location.getAddress2())
				.postal(location.getPostal())
				.city(location.getCity())
				.countryCode(countryCode)
				.build();
	}

	private BPartnerContactId getCreateBPartnerContactId(final JsonBPartnerContact json, final Context context)
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
			@NonNull final JsonBPartnerContact jsonBPartnerContact,
			@NonNull final Context context)
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

	private void updateBPartnerContactRecord(final I_AD_User bpContactRecord, final BPartnerId bpartnerId, final JsonBPartnerContact json)
	{
		bpContactRecord.setC_BPartner_ID(bpartnerId.getRepoId());
		bpContactRecord.setName(json.getName());
		bpContactRecord.setEMail(json.getEmail());
		bpContactRecord.setPhone(json.getPhone());
		bpContactRecord.setExternalId(json.getExternalId());
	}

	public JsonBPartnerContact getJsonBPartnerContactById(final BPartnerContactId bpartnerContactId)
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

		return JsonBPartnerContact.builder()
				.externalId(bpContactRecord.getExternalId())
				.name(bpContactRecord.getName())
				.email(bpContactRecord.getEMail())
				.phone(bpContactRecord.getPhone())
				.build();
	}
}
