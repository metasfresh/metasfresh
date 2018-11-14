package de.metas.ordercandidate.rest;

import static org.adempiere.model.InterfaceWrapperHelper.isNew;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;

import lombok.NonNull;

import javax.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.OrgId;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.compiere.util.Util;

import de.metas.adempiere.service.ICountryDAO;
import de.metas.adempiere.service.ILocationDAO;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.i18n.ITranslatableString;
import de.metas.ordercandidate.api.OLCandBPartnerInfo;
import de.metas.util.Check;
import de.metas.util.Services;

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
			@NonNull final Properties ctx,
			@Nullable final PermissionService permissionService)
	{

		return new BPartnerMasterDataProvider(
				ctx,
				Util.coalesceSuppliers(() -> permissionService, () -> PermissionService.of(ctx)));
	}

	private final Map<JsonBPartner, BPartnerId> bpartnerIdsByJson = new HashMap<>();
	private final Map<String, BPartnerLocationId> bpartnerLocationIdsByExternalId = new HashMap<>();
	private final Map<String, BPartnerContactId> bpartnerContactIdsByExternalId = new HashMap<>();

	private final IBPartnerDAO bpartnersRepo = Services.get(IBPartnerDAO.class);
	private final ILocationDAO locationsRepo = Services.get(ILocationDAO.class);
	private final ICountryDAO countryRepo = Services.get(ICountryDAO.class);

	private final PermissionService permissionService;

	private BPartnerMasterDataProvider(
			@NonNull final Properties ctx,
			@NonNull final PermissionService permissionService)
	{
		this.permissionService = permissionService;
	}

	public OLCandBPartnerInfo getCreateOrgBPartnerInfo(
			@NonNull final JsonBPartnerInfo bpartner,
			@NonNull final OrgId orgId)
	{
		final Context context = Context
				.builder()
				.orgId(orgId)
				.bPartnerIsOrgBP(true)
				.build();

		return getCreateBPartnerInfo(bpartner, context);
	}

	public final OLCandBPartnerInfo getCreateBPartnerInfo(
			@Nullable final JsonBPartnerInfo json,
			final OrgId orgId)
	{
		if (json == null)
		{
			return null;
		}

		final Context context = Context.ofOrg(orgId);
		final OLCandBPartnerInfo bPartnerInfo = lookupBPartnerInfoOrNull(json, context);
		if (bPartnerInfo == null)
		{
			switch (json.getIfNotExists())
			{
				case IF_NOT_EXISTS_FAIL:
					// TODO fail with an API-user-friendly message
					throw new BPartnerInfoNotFoundException(null);
				case IF_NOT_EXISTS_CREATE:
					return getCreateBPartnerInfo(json, context);
				default:
					Check.fail("Unepected IfNotExists={}", json.getIfNotExists());
					return null;
			}
		}
		else
		{
			switch (json.getIfExists())
			{
				case IF_EXISTS_UPDATE:
					return getCreateBPartnerInfo(json, context);
				case IF_EXISTS_DONT_UPDATE:
					return bPartnerInfo;
				default:
					Check.fail("Unepected IfExists={}", json.getIfExists());
					return null;
			}
		}
	}

	private final OLCandBPartnerInfo lookupBPartnerInfoOrNull(
			@NonNull final JsonBPartnerInfo jsonBPartnerInfo,
			@NonNull final Context context)
	{
		final BPartnerId bpartnerId = lookupBPartnerIdOrNull(jsonBPartnerInfo, context);

		OLCandBPartnerInfo.builder().bpartnerId(bpartnerId)
				// TODO
				.build();

		return null;
	}

	private final BPartnerId lookupBPartnerIdOrNull(
			@NonNull final JsonBPartnerInfo jsonBPartnerInfo,
			@NonNull final Context context)
	{
		final JsonBPartner json = jsonBPartnerInfo.getBpartner();

		// lookup via..
		BPartnerId existingBPartnerId = null;

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
		}
		// ..code (aka value)
		else if (json.getCode() != null)
		{
			existingBPartnerId = bpartnersRepo.getBPartnerIdByValueIfExists(json.getCode()).orElse(null);
		}
		// BPLocation's GLN
		else
		{
			final JsonBPartnerLocation jsonLocation = jsonBPartnerInfo.getLocation();
			if (jsonLocation.getGln() != null)
			{
				existingBPartnerId = bpartnersRepo.getBPartnerIdByLocatorGln(jsonLocation.getGln()).orElse(null);
			}
		}

		return existingBPartnerId;
	}

	private OLCandBPartnerInfo getCreateBPartnerInfo(
			@NonNull final JsonBPartnerInfo json,
			@NonNull final Context context)
	{
		final BPartnerId bpartnerId = getCreateBPartnerId(json.getBpartner(), context);

		final Context childContext = context.with(bpartnerId);
		final BPartnerLocationId bpartnerLocationId = getCreateBPartnerLocationId(json.getLocation(), childContext);
		final BPartnerContactId bpartnerContactId = getCreateBPartnerContactId(json.getContact(), childContext);

		return OLCandBPartnerInfo.builder()
				.bpartnerId(bpartnerId)
				.bpartnerLocationId(BPartnerLocationId.toRepoId(bpartnerLocationId))
				.contactId(BPartnerContactId.toRepoId(bpartnerContactId))
				.build();
	}

	private BPartnerId getCreateBPartnerId(@NonNull final JsonBPartner json, final Context context)
	{
		return bpartnerIdsByJson.compute(json, (existingJson, existingBPartnerId) -> createOrUpdateBPartnerId(json, context.with(existingBPartnerId)));
	}

	private BPartnerId createOrUpdateBPartnerId(
			@NonNull final JsonBPartner json,
			@NonNull final Context context)
	{
		// final BPartnerId existingBPartnerId;
		// if (context.getBpartnerId() != null)
		// {
		// existingBPartnerId = context.getBpartnerId();
		// }
		// else if (json.getExternalId() != null)
		// {
		// existingBPartnerId = bpartnersRepo
		// .getBPartnerIdByExternalIdIfExists(
		// json.getExternalId(),
		// context.getOrgId())
		// .orElse(null);
		// }
		// else if (json.getCode() != null)
		// {
		// existingBPartnerId = bpartnersRepo.getBPartnerIdByValueIfExists(json.getCode()).orElse(null);
		// }
		// else
		// {
		// existingBPartnerId = null;
		// }

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

		try
		{
			updateBPartnerRecord(bpartnerRecord, json);
			permissionService.assertCanCreateOrUpdate(bpartnerRecord);
			bpartnersRepo.save(bpartnerRecord);
		}
		catch (final PermissionNotGrantedException ex)
		{
			throw ex;
		}
		catch (final Exception ex)
		{
			throw new AdempiereException("Failed creating/updating record for " + json, ex);
		}

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
			throw new AdempiereException("@FillMandatory@ @Name@: " + from);
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

		return bpartnerLocationIdsByExternalId.compute(json.getExternalId(), (externalId, existingBPLocationId) -> createOrUpdateBPartnerLocationId(json, context.with(existingBPLocationId)));
	}

	private BPartnerLocationId createOrUpdateBPartnerLocationId(
			@NonNull final JsonBPartnerLocation json,
			final Context context)
	{
		final BPartnerId bpartnerId = context.getBpartnerId();

		BPartnerLocationId existingBPLocationId;
		if (context.getLocationId() != null)
		{
			existingBPLocationId = context.getLocationId();
		}
		else if (json.getExternalId() != null)
		{
			existingBPLocationId = bpartnersRepo.getBPartnerLocationIdByExternalId(bpartnerId, json.getExternalId()).orElse(null);
		}
		else if (json.getGln() != null)
		{
			existingBPLocationId = bpartnersRepo.getBPartnerLocationIdByGln(bpartnerId, json.getGln()).orElse(null);
		}
		else
		{
			existingBPLocationId = null;
		}

		I_C_BPartner_Location bpLocationRecord;
		if (existingBPLocationId != null)
		{
			bpLocationRecord = bpartnersRepo.getBPartnerLocationById(existingBPLocationId);
		}
		else
		{
			bpLocationRecord = newInstance(I_C_BPartner_Location.class);
			bpLocationRecord.setAD_Org_ID(context.getOrgId().getRepoId());
		}

		try
		{
			updateBPartnerLocationRecord(bpLocationRecord, bpartnerId, json);
			permissionService.assertCanCreateOrUpdate(bpLocationRecord);
			bpartnersRepo.save(bpLocationRecord);
		}
		catch (final PermissionNotGrantedException ex)
		{
			throw ex;
		}
		catch (final Exception ex)
		{
			throw new AdempiereException("Failed creating/updating record for " + json, ex);
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
				throw new AdempiereException("@FillMandatory@ @CountryCode@: " + json);
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

		return bpartnerContactIdsByExternalId.compute(json.getExternalId(), (externalId, existingContactId) -> createOrUpdateBPartnerContactId(json, context.with(existingContactId)));
	}

	private BPartnerContactId createOrUpdateBPartnerContactId(
			@NonNull final JsonBPartnerContact json,
			@NonNull final Context context)
	{
		final BPartnerId bpartnerId = context.getBpartnerId();

		final BPartnerContactId existingContactId;
		if (context.getContactId() != null)
		{
			existingContactId = context.getContactId();
		}
		else if (json.getExternalId() != null)
		{
			existingContactId = bpartnersRepo.getContactIdByExternalId(bpartnerId, json.getExternalId()).orElse(null);
		}
		else
		{
			existingContactId = null;
		}

		//
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

		try
		{
			updateBPartnerContactRecord(contactRecord, bpartnerId, json);
			permissionService.assertCanCreateOrUpdate(contactRecord);
			bpartnersRepo.save(contactRecord);
		}
		catch (final PermissionNotGrantedException ex)
		{
			throw ex;
		}
		catch (final Exception ex)
		{
			throw new AdempiereException("Failed creating/updating record for " + json, ex);
		}

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

	public static class BPartnerInfoNotFoundException extends AdempiereException
	{
		private static final long serialVersionUID = -4776977540135879202L;

		public BPartnerInfoNotFoundException(ITranslatableString msg)
		{
			super(msg);
		}
	}
}
