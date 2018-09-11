package de.metas.ordercandidate.rest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.security.IUserRolePermissionsDAO;
import org.adempiere.ad.security.UserRolePermissionsKey;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.IOrgDAO;
import org.adempiere.service.OrgId;
import org.adempiere.uom.UomId;
import org.adempiere.uom.api.IUOMDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.compiere.util.Env;

import de.metas.adempiere.model.I_AD_User;
import de.metas.adempiere.service.ICountryDAO;
import de.metas.adempiere.service.ILocationDAO;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.ordercandidate.api.OLCandBPartnerInfo;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import lombok.NonNull;

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

final class MasterdataProvider
{
	public static final MasterdataProvider newInstance(final Properties ctx)
	{
		return new MasterdataProvider(ctx);
	}

	private final IProductDAO productsRepo = Services.get(IProductDAO.class);
	private final IProductBL productsBL = Services.get(IProductBL.class);
	private final IUOMDAO uomsRepo = Services.get(IUOMDAO.class);
	private final IPriceListDAO priceListsRepo = Services.get(IPriceListDAO.class);
	private final IBPartnerDAO bpartnersRepo = Services.get(IBPartnerDAO.class);
	private final ILocationDAO locationsRepo = Services.get(ILocationDAO.class);
	private final ICountryDAO countryRepo = Services.get(ICountryDAO.class);
	private final IOrgDAO orgsRepo = Services.get(IOrgDAO.class);
	private final IUserRolePermissionsDAO userRolePermissionsRepo = Services.get(IUserRolePermissionsDAO.class);

	private final OrgId defaultOrgId;

	private final UserRolePermissionsKey userRolePermissionsKey;
	private final Set<PermissionRequest> permissionsGranted = new HashSet<>();

	private final Map<String, OrgId> orgIdsByCode = new HashMap<>();
	private final Map<JsonBPartner, BPartnerId> bpartnerIdsByJson = new HashMap<>();
	private final Map<String, BPartnerLocationId> bpartnerLocationIdsByExternalId = new HashMap<>();
	private final Map<String, BPartnerContactId> bpartnerContactIdsByExternalId = new HashMap<>();

	private MasterdataProvider(final Properties ctx)
	{
		userRolePermissionsKey = UserRolePermissionsKey.of(ctx);
		defaultOrgId = OrgId.optionalOfRepoId(Env.getAD_Org_ID(ctx)).orElse(OrgId.ANY);
	}

	public void assertCanCreateNewOLCand(final OrgId orgId)
	{
		assertPermission(PermissionRequest.builder()
				.orgId(orgId)
				.adTableId(InterfaceWrapperHelper.getTableId(I_C_OLCand.class))
				.build());
	}

	private void assertCanCreateOrUpdate(final Object record)
	{
		final OrgId orgId = InterfaceWrapperHelper.getOrgId(record).orElse(OrgId.ANY);
		final int adTableId = InterfaceWrapperHelper.getModelTableId(record);
		final int recordId;
		if (InterfaceWrapperHelper.isNew(record))
		{
			recordId = -1;
		}
		else
		{
			recordId = InterfaceWrapperHelper.getId(record);
		}

		assertPermission(PermissionRequest.builder()
				.orgId(orgId)
				.adTableId(adTableId)
				.recordId(recordId)
				.build());
	}

	private void assertPermission(@NonNull final PermissionRequest request)
	{
		if (permissionsGranted.contains(request))
		{
			return;
		}

		final IUserRolePermissions userPermissions = userRolePermissionsRepo.retrieveUserRolePermissions(userRolePermissionsKey);

		final String errmsg;
		if (request.getRecordId() >= 0)
		{
			errmsg = userPermissions.checkCanUpdate(
					userPermissions.getAD_Client_ID(),
					request.getOrgId().getRepoId(),
					request.getAdTableId(),
					request.getRecordId());
		}
		else
		{
			errmsg = userPermissions.checkCanCreateNewRecord(
					userPermissions.getAD_Client_ID(),
					request.getOrgId().getRepoId(),
					request.getAdTableId());
		}

		if (errmsg != null)
		{
			throw new PermissionNotGrantedException(errmsg);
		}

		permissionsGranted.add(request);
	}

	public ProductId getProductIdByValue(final String value)
	{
		return productsRepo.retrieveProductIdByValue(value);
	}

	public UomId getProductUOMId(final ProductId productId, final String uomCode)
	{
		if (!Check.isEmpty(uomCode, true))
		{
			return uomsRepo.getUomIdByX12DE355(uomCode);
		}
		else
		{
			return UomId.ofRepoId(productsBL.getStockingUOMId(productId));
		}
	}

	public PricingSystemId getPricingSystemIdByValue(final String pricingSystemCode)
	{
		if (Check.isEmpty(pricingSystemCode, true))
		{
			return null;
		}

		return priceListsRepo.getPricingSystemIdByValue(pricingSystemCode);
	}

	public final OLCandBPartnerInfo getCreateBPartnerInfo(final JsonBPartnerInfo json, final OrgId orgId)
	{
		if (json == null)
		{
			return null;
		}

		Context context = Context.ofOrg(orgId);
		final BPartnerId bpartnerId = getCreateBPartnerId(json.getBpartner(), context);

		context = context.with(bpartnerId);
		final BPartnerLocationId bpartnerLocationId = getCreateBPartnerLocationId(json.getLocation(), context);
		final BPartnerContactId bpartnerContactId = getCreateBPartnerContactId(json.getContact(), context);

		return OLCandBPartnerInfo.builder()
				.bpartnerId(bpartnerId.getRepoId())
				.bpartnerLocationId(BPartnerLocationId.toRepoId(bpartnerLocationId))
				.contactId(BPartnerContactId.toRepoId(bpartnerContactId))
				.build();
	}

	private BPartnerId getCreateBPartnerId(@NonNull final JsonBPartner json, final Context context)
	{
		return bpartnerIdsByJson.compute(json, (existingJson, existingBPartnerId) -> createOrUpdateBPartnerId(json, context.with(existingBPartnerId)));
	}

	private BPartnerId createOrUpdateBPartnerId(final JsonBPartner json, final Context context)
	{
		final BPartnerId existingBPartnerId;
		if (context.getBpartnerId() != null)
		{
			existingBPartnerId = context.getBpartnerId();
		}
		else if (json.getCode() != null)
		{
			existingBPartnerId = bpartnersRepo.getBPartnerIdByValueIfExists(json.getCode()).orElse(null);
		}
		else
		{
			existingBPartnerId = null;
		}

		final I_C_BPartner bpartnerRecord;
		if (existingBPartnerId != null)
		{
			bpartnerRecord = bpartnersRepo.getById(existingBPartnerId);
		}
		else
		{
			bpartnerRecord = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
			bpartnerRecord.setAD_Org_ID(context.getOrgId().getRepoId());
		}

		try
		{
			updateBPartnerRecord(bpartnerRecord, json);
			assertCanCreateOrUpdate(bpartnerRecord);
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
		final boolean isNew = InterfaceWrapperHelper.isNew(bpartnerRecord);

		final String code = from.getCode();
		if (!Check.isEmpty(code, true))
		{
			bpartnerRecord.setValue(code);
		}

		final String name = from.getName();
		if (!Check.isEmpty(name, true))
		{
			bpartnerRecord.setName(name);
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

	private BPartnerLocationId getCreateBPartnerLocationId(final JsonBPartnerLocation json, final Context context)
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
			bpLocationRecord = InterfaceWrapperHelper.newInstance(I_C_BPartner_Location.class);
			bpLocationRecord.setAD_Org_ID(context.getOrgId().getRepoId());
		}

		try
		{
			updateBPartnerLocationRecord(bpLocationRecord, bpartnerId, json);
			assertCanCreateOrUpdate(bpLocationRecord);
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
		final boolean isNew = InterfaceWrapperHelper.isNew(bpLocationRecord);

		bpLocationRecord.setC_BPartner_ID(bpartnerId.getRepoId());
		bpLocationRecord.setIsShipTo(true);
		bpLocationRecord.setIsBillTo(true);
		bpLocationRecord.setExternalId(json.getExternalId());

		if (isNew || !json.equals(toJsonBPartnerLocation(bpLocationRecord)))
		{
			final String countryCode = json.getCountryCode();
			if (Check.isEmpty(countryCode))
			{
				throw new AdempiereException("@FillMandatory@ @CountryCode@: " + json);
			}
			final int countryId = countryRepo.getCountryIdByCountryCode(countryCode);

			// NOTE: C_Location table might be heavily used, so it's better to create the address OOT to not lock it.
			final I_C_Location locationRecord = InterfaceWrapperHelper.newInstanceOutOfTrx(I_C_Location.class);
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
		final I_C_Location location = bpLocationRecord.getC_Location();
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
			contactRecord = InterfaceWrapperHelper.newInstance(I_AD_User.class);
			contactRecord.setAD_Org_ID(context.getOrgId().getRepoId());
		}

		try
		{
			updateBPartnerContactRecord(contactRecord, bpartnerId, json);
			assertCanCreateOrUpdate(contactRecord);
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

	public OrgId getCreateOrgId(final JsonOrganization json)
	{
		if (json == null)
		{
			return defaultOrgId;
		}

		return orgIdsByCode.compute(json.getCode(), (code, existingOrgId) -> createOrUpdateOrgId(json, existingOrgId));
	}

	private OrgId createOrUpdateOrgId(final JsonOrganization json, OrgId existingOrgId)
	{
		if (existingOrgId == null)
		{
			final String code = json.getCode();
			if (Check.isEmpty(code, true))
			{
				throw new AdempiereException("Organization code shall be set: " + json);
			}

			existingOrgId = orgsRepo.getOrgIdByValue(code).orElse(null);
		}

		I_AD_Org orgRecord;
		if (existingOrgId != null)
		{
			orgRecord = orgsRepo.getById(existingOrgId);
		}
		else
		{
			orgRecord = InterfaceWrapperHelper.newInstance(I_AD_Org.class);
		}

		try
		{
			updateOrgRecord(orgRecord, json);
			assertCanCreateOrUpdate(orgRecord);
			orgsRepo.save(orgRecord);
		}
		catch (final PermissionNotGrantedException ex)
		{
			throw ex;
		}
		catch (final Exception ex)
		{
			throw new AdempiereException("Failed creating/updating record for " + json, ex);
		}

		return OrgId.ofRepoId(orgRecord.getAD_Org_ID());
	}

	private void updateOrgRecord(final I_AD_Org orgRecord, final JsonOrganization json)
	{
		orgRecord.setValue(json.getCode());
		orgRecord.setName(json.getName());
	}

	public JsonOrganization getJsonOrganizationById(final int orgId)
	{
		final I_AD_Org orgRecord = orgsRepo.retrieveOrg(orgId);
		if (orgRecord == null)
		{
			return null;
		}

		return JsonOrganization.builder()
				.code(orgRecord.getValue())
				.name(orgRecord.getName())
				.build();
	}

	@lombok.Value
	@lombok.Builder
	private static class PermissionRequest
	{
		@lombok.NonNull
		OrgId orgId;
		int adTableId;
		@lombok.Builder.Default
		int recordId = -1;
	}

	@lombok.Value
	@lombok.Builder(toBuilder = true)
	private static class Context
	{
		public static Context ofOrg(final OrgId orgId)
		{
			return builder().orgId(orgId).build();
		}

		@lombok.NonNull
		OrgId orgId;
		BPartnerId bpartnerId;
		BPartnerLocationId locationId;
		BPartnerContactId contactId;

		public Context with(final BPartnerId bpartnerId)
		{
			return toBuilder().bpartnerId(bpartnerId).build();
		}

		public Context with(final BPartnerLocationId locationId)
		{
			return toBuilder().locationId(locationId).build();
		}

		public Context with(final BPartnerContactId contactId)
		{
			return toBuilder().contactId(contactId).build();
		}
	}

	@SuppressWarnings("serial")
	private static class PermissionNotGrantedException extends AdempiereException
	{
		public PermissionNotGrantedException(String message)
		{
			super(message);
		}
	}
}
