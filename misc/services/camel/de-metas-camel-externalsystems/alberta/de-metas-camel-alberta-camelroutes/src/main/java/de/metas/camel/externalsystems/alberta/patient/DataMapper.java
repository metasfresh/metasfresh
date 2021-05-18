/*
 * #%L
 * de-metas-camel-alberta-camelroutes
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

package de.metas.camel.externalsystems.alberta.patient;

import de.metas.common.bpartner.v2.request.JsonRequestBPartner;
import de.metas.common.bpartner.v2.request.JsonRequestBPartnerUpsertItem;
import de.metas.common.bpartner.v2.request.JsonRequestComposite;
import de.metas.common.bpartner.v2.request.JsonRequestContact;
import de.metas.common.bpartner.v2.request.JsonRequestContactUpsert;
import de.metas.common.bpartner.v2.request.JsonRequestContactUpsertItem;
import de.metas.common.bpartner.v2.request.JsonRequestLocation;
import de.metas.common.bpartner.v2.request.JsonRequestLocationUpsert;
import de.metas.common.bpartner.v2.request.JsonRequestLocationUpsertItem;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import io.swagger.client.model.Doctor;
import io.swagger.client.model.Pharmacy;
import lombok.NonNull;

import javax.annotation.Nullable;

public class DataMapper
{
	@NonNull
	public static JsonRequestBPartnerUpsertItem mapDoctorToUpsertRequest(
			@NonNull final Doctor doctor,
			@Nullable final JsonMetasfreshId metasfreshId,
			@Nullable final String orgCode)
	{
		final JsonRequestBPartner jsonRequestBPartner = new JsonRequestBPartner();
		jsonRequestBPartner.setName(doctor.getFirstName() + " " + doctor.getLastName());
		jsonRequestBPartner.setPhone(doctor.getPhone());
		jsonRequestBPartner.setCustomer(true);

		final JsonRequestContact requestContact = new JsonRequestContact();
		requestContact.setFirstName(doctor.getFirstName());
		requestContact.setLastName(doctor.getLastName());
		requestContact.setPhone(doctor.getPhone());
		requestContact.setFax(doctor.getFax());
		// requestContact.setGender(doctor.getGender());  //TODO

		final JsonRequestLocation requestLocation = new JsonRequestLocation();
		requestLocation.setAddress1(doctor.getAddress());
		requestLocation.setCity(doctor.getCity());
		requestLocation.setPostal(doctor.getPostalCode());
		requestLocation.setCountryCode(GetPatientsRouteConstants.COUNTRY_CODE_DE);
		requestLocation.setBillTo(true);
		requestLocation.setBillToDefault(true);
		requestLocation.setShipTo(true);
		requestLocation.setShipToDefault(true);

		final JsonRequestLocationUpsert jsonRequestLocationUpsert =
				toJsonRequestLocationUpsert(doctor.getId(), requestLocation);

		final JsonRequestContactUpsert jsonRequestContactUpsert = toJsonRequestContactUpsert(doctor.getId(), requestContact);

		final String bPartnerIdentifier;
		if (metasfreshId == null)
		{
			final String externalIdentifier = asExternalIdentifier(doctor.getId());
			jsonRequestBPartner.setCode(externalIdentifier);
			bPartnerIdentifier = externalIdentifier;
		}
		else
		{
			bPartnerIdentifier = String.valueOf(metasfreshId.getValue());
		}

		final JsonRequestComposite jsonRequestComposite = JsonRequestComposite.builder()
				.bpartner(jsonRequestBPartner)
				.locations(jsonRequestLocationUpsert)
				.contacts(jsonRequestContactUpsert)
				.orgCode(orgCode)
				.build();

		return JsonRequestBPartnerUpsertItem
				.builder()
				.bpartnerIdentifier(bPartnerIdentifier)
				.bpartnerComposite(jsonRequestComposite)
				.build();
	}

	@NonNull
	public static JsonRequestBPartnerUpsertItem mapPharmacyToUpsertRequest(
			@NonNull final Pharmacy pharmacy,
			@Nullable final JsonMetasfreshId metasfreshId,
			@Nullable final String orgCode)
	{
		final JsonRequestBPartner jsonRequestBPartner = new JsonRequestBPartner();
		jsonRequestBPartner.setName(pharmacy.getName());
		jsonRequestBPartner.setPhone(pharmacy.getPhone());
		jsonRequestBPartner.setCustomer(true);
		// todo pharmacy.getWebsite() ?
		// todo pharmacy.getEmail() ?
		// todo pharmacy.getFax() ?

		final JsonRequestLocation requestLocation = new JsonRequestLocation();
		requestLocation.setCountryCode(GetPatientsRouteConstants.COUNTRY_CODE_DE);
		requestLocation.setCity(pharmacy.getCity());
		requestLocation.setPostal(pharmacy.getPostalCode());
		requestLocation.setAddress1(pharmacy.getAddress());
		requestLocation.setBillTo(true);
		requestLocation.setBillToDefault(true);
		requestLocation.setShipTo(true);
		requestLocation.setShipToDefault(true);

		final JsonRequestLocationUpsert jsonRequestLocationUpsert =
				toJsonRequestLocationUpsert(pharmacy.getId(), requestLocation);

		final String bPartnerIdentifier;
		if (metasfreshId == null)
		{
			final String externalIdentifier = asExternalIdentifier(pharmacy.getId());
			jsonRequestBPartner.setCode(externalIdentifier);
			bPartnerIdentifier = externalIdentifier;
		}
		else
		{
			bPartnerIdentifier = String.valueOf(metasfreshId.getValue());
		}

		final JsonRequestComposite jsonRequestComposite = JsonRequestComposite.builder()
				.bpartner(jsonRequestBPartner)
				.locations(jsonRequestLocationUpsert)
				.orgCode(orgCode)
				.build();

		return JsonRequestBPartnerUpsertItem
				.builder()
				.bpartnerIdentifier(bPartnerIdentifier)
				.bpartnerComposite(jsonRequestComposite)
				.build();
	}

	@NonNull
	public static JsonRequestLocationUpsert toJsonRequestLocationUpsert(
			@NonNull final String identifier,
			@NonNull final JsonRequestLocation locationRequest)
	{
		final JsonRequestLocationUpsertItem jsonRequestLocationUpsertItem = JsonRequestLocationUpsertItem.builder()
				.locationIdentifier(asExternalIdentifier(identifier))
				.location(locationRequest)
				.build();

		return JsonRequestLocationUpsert.builder()
				.requestItem(jsonRequestLocationUpsertItem)
				.build();
	}

	@NonNull
	public static JsonRequestContactUpsert toJsonRequestContactUpsert(
			@NonNull final String identifier,
			@NonNull final JsonRequestContact contactRequest)
	{
		final JsonRequestContactUpsertItem jsonRequestLocationUpsertItem = JsonRequestContactUpsertItem.builder()
				.contactIdentifier(asExternalIdentifier(identifier))
				.contact(contactRequest)
				.build();

		return JsonRequestContactUpsert.builder()
				.requestItem(jsonRequestLocationUpsertItem)
				.build();
	}

	@NonNull
	public static String asExternalIdentifier(@NonNull final String externalId)
	{
		return GetPatientsRouteConstants.EXTERNAL_ID_PREFIX + GetPatientsRouteConstants.ALBERTA_SYSTEM_NAME + "-" + externalId;
	}
}
