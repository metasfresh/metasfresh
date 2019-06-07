package de.metas.rest_api.bpartner.impl;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.util.List;

import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.springframework.stereotype.Repository;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.interfaces.I_C_BPartner;
import de.metas.rest_api.JsonExternalId;
import de.metas.rest_api.MetasfreshId;
import de.metas.rest_api.bpartner.JsonBPartner;
import de.metas.rest_api.bpartner.JsonBPartnerComposite;
import de.metas.rest_api.bpartner.JsonBPartnerComposite.JsonBPartnerCompositeBuilder;
import de.metas.rest_api.bpartner.JsonBPartnerLocation;
import de.metas.rest_api.bpartner.JsonBPartnerLocation.JsonBPartnerLocationBuilder;
import de.metas.rest_api.bpartner.JsonContact;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Repository
public class JsonBPartnerCompositeRepository
{
	// TODO avoid n+1 problem when loding >1 bpartner's

	public JsonBPartnerComposite getById(@NonNull final BPartnerId bPartnerId)
	{
		final I_C_BPartner bpartnerRecord = loadOutOfTrx(bPartnerId, I_C_BPartner.class);

		final JsonBPartnerCompositeBuilder jsonBPartnerComposite = JsonBPartnerComposite.builder();
		jsonBPartnerComposite.bpartner(ofRecord(bpartnerRecord));

		final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);

		final List<I_C_BPartner_Location> bPartnerLocationRecords = bPartnerDAO.retrieveBPartnerLocations(bPartnerId);
		for (final I_C_BPartner_Location bPartnerLocationRecord : bPartnerLocationRecords)
		{
			jsonBPartnerComposite.location(ofRecord(bPartnerLocationRecord));
		}

		final List<I_AD_User> contactRecords = bPartnerDAO.retrieveContacts(bpartnerRecord);
		for (final I_AD_User contactRecord : contactRecords)
		{
			jsonBPartnerComposite.contact(ofRecord(contactRecord));
		}

		return jsonBPartnerComposite.build();
	}

	private JsonBPartner ofRecord(@NonNull final I_C_BPartner bpartnerRecord)
	{
		return JsonBPartner.builder()
				.code(bpartnerRecord.getValue())
				.companyName(bpartnerRecord.getCompanyName())
				.externalId(JsonExternalId.ofOrNull(bpartnerRecord.getExternalId()))
				.group(bpartnerRecord.getC_BP_Group().getName())
				.language(bpartnerRecord.getAD_Language())
				.metasfreshId(MetasfreshId.of(bpartnerRecord.getC_BPartner_ID()))
				.name(bpartnerRecord.getName())
				.parentId(MetasfreshId.ofOrNull(bpartnerRecord.getBPartner_Parent_ID()))
				// .phone(bpartnerRecord.get)
				.url(bpartnerRecord.getURL())
				.build();
	}

	private JsonBPartnerLocation ofRecord(@NonNull final I_C_BPartner_Location bPartnerLocationRecord)
	{
		final I_C_Location locationRecord = bPartnerLocationRecord.getC_Location();

		final JsonBPartnerLocationBuilder location = JsonBPartnerLocation.builder()
				.address1(locationRecord.getAddress1())
				.address2(locationRecord.getAddress2())
				.city(locationRecord.getCity())
				.countryCode(locationRecord.getC_Country().getCountryCode())
				.externalId(JsonExternalId.ofOrNull(bPartnerLocationRecord.getExternalId()))
				.gln(bPartnerLocationRecord.getGLN())
				.metasfreshBPartnerId(MetasfreshId.of(bPartnerLocationRecord.getC_BPartner_ID()))
				.metasfreshId(MetasfreshId.of(bPartnerLocationRecord.getC_BPartner_Location_ID()))
				.poBox(locationRecord.getPOBox())
				.postal(locationRecord.getPostal())
				.region(locationRecord.getRegionName());

		if (locationRecord.getC_Postal_ID() > 0)
		{
			location.district(locationRecord.getC_Postal().getDistrict());
		}

		return location.build();
	}

	private JsonContact ofRecord(I_AD_User contactRecord)
	{
		return JsonContact.builder()
				.email(contactRecord.getEMail())
				.externalId(JsonExternalId.ofOrNull(contactRecord.getExternalId()))
				.firstName(contactRecord.getFirstname())
				.lastName(contactRecord.getLastname())
				.metasfreshBPartnerId(MetasfreshId.of(contactRecord.getC_BPartner_ID()))
				.name(contactRecord.getName())
				.phone(contactRecord.getPhone())
				.build();
	}
}
