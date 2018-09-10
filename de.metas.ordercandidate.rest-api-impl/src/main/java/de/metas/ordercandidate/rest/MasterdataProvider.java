package de.metas.ordercandidate.rest;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

import de.metas.adempiere.model.I_AD_User;
import de.metas.adempiere.service.ICountryDAO;
import de.metas.adempiere.service.ILocationDAO;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
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
	public static final MasterdataProvider newInstance()
	{
		return new MasterdataProvider();
	}

	private final IProductDAO productsRepo = Services.get(IProductDAO.class);
	private final IProductBL productsBL = Services.get(IProductBL.class);
	private final IUOMDAO uomsRepo = Services.get(IUOMDAO.class);
	private final IPriceListDAO priceListsRepo = Services.get(IPriceListDAO.class);
	private final IBPartnerDAO bpartnersRepo = Services.get(IBPartnerDAO.class);
	private final ILocationDAO locationsRepo = Services.get(ILocationDAO.class);
	private final ICountryDAO countryRepo = Services.get(ICountryDAO.class);
	private final IOrgDAO orgsRepo = Services.get(IOrgDAO.class);

	private final Map<String, BPartnerId> bpartnerIdsByCode = new HashMap<>();
	private final Map<String, BPartnerLocationId> bpartnerLocationIdsByCode = new HashMap<>();
	private final Map<String, BPartnerContactId> bpartnerContactIdsByCode = new HashMap<>();

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

	public BPartnerId getCreateBPartnerId(final JsonBPartner json)
	{
		return bpartnerIdsByCode.computeIfAbsent(json.getCode(), code -> retrieveOrCreateBPartnerId(json));
	}

	private BPartnerId retrieveOrCreateBPartnerId(final JsonBPartner json)
	{
		return retrieveExistingBPartnerId(json)
				.orElseGet(() -> createBPartnerId(json));
	}

	private final Optional<BPartnerId> retrieveExistingBPartnerId(final JsonBPartner json)
	{
		final String code = json.getCode();
		if (!Check.isEmpty(code, true))
		{
			return bpartnersRepo.getBPartnerIdByValueIfExists(code);
		}
		else
		{
			return Optional.empty();
		}
	}

	private final BPartnerId createBPartnerId(final JsonBPartner json)
	{
		final String code = json.getCode();
		final String name = json.getName();
		if (Check.isEmpty(name, true))
		{
			throw new AdempiereException("@FillMandatory@ @Name@: " + json);
		}

		final I_C_BPartner bpartnerRecord = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);

		if (!Check.isEmpty(code, true))
		{
			bpartnerRecord.setValue(code);
		}

		bpartnerRecord.setName(name);
		bpartnerRecord.setIsCustomer(true);
		// bpartnerRecord.setC_BP_Group_ID(C_BP_Group_ID); // TODO
		bpartnersRepo.save(bpartnerRecord);

		return BPartnerId.ofRepoId(bpartnerRecord.getC_BPartner_ID());
	}

	public JsonBPartner getJsonBPartnerById(@NonNull final BPartnerId bpartnerId)
	{
		final I_C_BPartner bpartnerRecord = bpartnersRepo.getById(bpartnerId);

		return JsonBPartner.builder()
				.code(bpartnerRecord.getValue())
				.name(bpartnerRecord.getName())
				.build();
	}

	public BPartnerLocationId getCreateBPartnerLocationId(final BPartnerId bpartnerId, final JsonBPartnerLocation json)
	{
		if (json == null)
		{
			return null;
		}

		return bpartnerLocationIdsByCode.computeIfAbsent(json.getCode(), code -> retrieveOrCreateBPartnerLocationId(bpartnerId, json));
	}

	private BPartnerLocationId retrieveOrCreateBPartnerLocationId(final BPartnerId bpartnerId, final JsonBPartnerLocation json)
	{
		final BPartnerLocationId bpartnerLocationId = convertCodeToBPartnerLocationId(bpartnerId, json.getCode());
		if (bpartnerLocationId != null)
		{
			return bpartnerLocationId;
		}

		final int countryId = countryRepo.getCountryIdByCountryCode(json.getCountryCode());

		final I_C_Location locationRecord = InterfaceWrapperHelper.newInstance(I_C_Location.class);
		locationRecord.setAddress1(json.getAddress1());
		locationRecord.setAddress2(json.getAddress2());
		locationRecord.setPostal(locationRecord.getPostal());
		locationRecord.setCity(locationRecord.getCity());
		locationRecord.setC_Country_ID(countryId);
		locationsRepo.save(locationRecord);

		final I_C_BPartner_Location bpLocationRecord = InterfaceWrapperHelper.newInstance(I_C_BPartner_Location.class);
		bpLocationRecord.setC_BPartner_ID(bpartnerId.getRepoId());
		bpLocationRecord.setIsShipTo(true);
		bpLocationRecord.setIsBillTo(true);
		bpLocationRecord.setC_Location_ID(locationRecord.getC_Location_ID());
		bpartnersRepo.save(bpLocationRecord);

		return BPartnerLocationId.ofRepoId(bpartnerId, bpLocationRecord.getC_BPartner_Location_ID());
	}

	private final BPartnerLocationId convertCodeToBPartnerLocationId(final BPartnerId bpartnerId, final String code)
	{
		if (code == null || code.isEmpty())
		{
			return null;
		}

		final int bpartnerLocationIdInt;
		try
		{
			bpartnerLocationIdInt = Integer.parseInt(code);
		}
		catch (final Exception ex)
		{
			throw new AdempiereException("Invalid BPartner Location code: " + code, ex);
		}

		final BPartnerLocationId bpartnerLocationId = BPartnerLocationId.ofRepoId(bpartnerId, bpartnerLocationIdInt);
		if (!bpartnersRepo.exists(bpartnerLocationId))
		{
			throw new AdempiereException("BPartner Location does not exist: " + bpartnerLocationId);
		}

		return bpartnerLocationId;
	}

	private static final String convertBPartnerLocationIdToCode(final BPartnerLocationId bpartnerLocationId)
	{
		return bpartnerLocationId != null ? String.valueOf(bpartnerLocationId.getRepoId()) : null;
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

		final I_C_Location location = bpLocationRecord.getC_Location();
		final String countryCode = countryRepo.retrieveCountryCode2ByCountryId(location.getC_Country_ID());

		return JsonBPartnerLocation.builder()
				.code(convertBPartnerLocationIdToCode(bpartnerLocationId))
				.address1(location.getAddress1())
				.address2(location.getAddress2())
				.postal(location.getPostal())
				.city(location.getCity())
				.countryCode(countryCode)
				.build();
	}

	public BPartnerContactId getCreateBPartnerContactId(final BPartnerId bpartnerId, final JsonBPartnerContact json)
	{
		if (json == null)
		{
			return null;
		}

		return bpartnerContactIdsByCode.computeIfAbsent(json.getCode(), code -> retrieveOrCreateBPartnerContactId(bpartnerId, json));
	}

	private BPartnerContactId retrieveOrCreateBPartnerContactId(final BPartnerId bpartnerId, final JsonBPartnerContact json)
	{
		final BPartnerContactId bpartnerContactId = convertCodeToBPartnerContactId(bpartnerId, json.getCode());
		if (bpartnerContactId != null)
		{
			return bpartnerContactId;
		}

		final I_AD_User bpContactRecord = InterfaceWrapperHelper.newInstance(I_AD_User.class);
		bpContactRecord.setC_BPartner_ID(bpartnerId.getRepoId());
		bpContactRecord.setName(json.getName());
		bpContactRecord.setEMail(json.getEmail());
		bpContactRecord.setPhone(json.getPhone());
		bpartnersRepo.save(bpContactRecord);

		return BPartnerContactId.ofRepoId(bpartnerId, bpContactRecord.getAD_User_ID());
	}

	private static final BPartnerContactId convertCodeToBPartnerContactId(final BPartnerId bpartnerId, final String code)
	{
		if (code == null || code.isEmpty())
		{
			return null;
		}

		final int contactId;
		try
		{
			contactId = Integer.parseInt(code);
		}
		catch (final Exception ex)
		{
			throw new AdempiereException("Invalid BPartner Contact code: " + code, ex);
		}

		return BPartnerContactId.ofRepoId(bpartnerId, contactId);
	}

	private static String convertBPartnerContactIdToCode(final BPartnerContactId bpContactId)
	{
		return bpContactId != null ? String.valueOf(bpContactId.getRepoId()) : null;
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
				.code(convertBPartnerContactIdToCode(bpartnerContactId))
				.name(bpContactRecord.getName())
				.email(bpContactRecord.getEMail())
				.phone(bpContactRecord.getPhone())
				.build();
	}

	public OrgId getCreateOrgId(final JsonOrganization org)
	{
		if (org == null)
		{
			return null;
		}

		final String code = org.getCode();
		Check.assumeNotEmpty(code, "Organization code shall be set: {}", org);
		final OrgId orgId = orgsRepo.getOrgIdByValue(code).orElse(null);
		if (orgId != null)
		{
			return orgId;
		}

		final String orgName = org.getName();
		Check.assumeNotEmpty(orgName, "Organization Name shall be set: {}", org);

		final I_AD_Org orgRecord = InterfaceWrapperHelper.newInstance(I_AD_Org.class);
		orgRecord.setValue(code);
		orgRecord.setName(orgName);
		orgsRepo.save(orgRecord);

		return OrgId.ofRepoId(orgRecord.getAD_Org_ID());
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
}
