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
import de.metas.common.bpartner.v2.request.alberta.JsonAlbertaBPartner;
import de.metas.common.bpartner.v2.request.alberta.JsonAlbertaContact;
import de.metas.common.bpartner.v2.request.alberta.JsonBPartnerRole;
import de.metas.common.bpartner.v2.request.alberta.JsonCompositeAlbertaBPartner;
import io.swagger.client.model.Doctor;
import io.swagger.client.model.Pharmacy;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.math.BigDecimal;

import static de.metas.camel.externalsystems.alberta.common.AlbertaUtil.asInstant;
import static de.metas.camel.externalsystems.alberta.common.ExternalIdentifierFormat.formatExternalId;

public class DataMapper
{
	@NonNull
	public static JsonRequestBPartnerUpsertItem mapDoctorToUpsertRequest(
			@NonNull final Doctor doctor,
			@Nullable final String orgCode)
	{
		final JsonRequestBPartner jsonRequestBPartner = new JsonRequestBPartner();
		jsonRequestBPartner.setName(doctor.getFirstName() + " " + doctor.getLastName());
		jsonRequestBPartner.setPhone(doctor.getPhone());
		jsonRequestBPartner.setCustomer(true);
		final String doctorExternalIdentifier = formatExternalId(doctor.getId());
		jsonRequestBPartner.setCode(doctorExternalIdentifier);

		final JsonRequestLocationUpsert jsonRequestLocationUpsert;
		{ //location
			final JsonRequestLocation requestLocation = new JsonRequestLocation();
			requestLocation.setAddress1(doctor.getAddress());
			requestLocation.setCity(doctor.getCity());
			requestLocation.setPostal(doctor.getPostalCode());
			requestLocation.setCountryCode(GetPatientsRouteConstants.COUNTRY_CODE_DE);
			requestLocation.setBillTo(true);
			requestLocation.setBillToDefault(true);
			requestLocation.setShipTo(true);
			requestLocation.setShipToDefault(true);

			jsonRequestLocationUpsert = toJsonRequestLocationUpsert(doctor.getId(), requestLocation);
		}

		final JsonRequestContactUpsert jsonRequestContactUpsert;
		{//contact
			final JsonRequestContact requestContact = new JsonRequestContact();
			requestContact.setFirstName(doctor.getFirstName());
			requestContact.setLastName(doctor.getLastName());
			requestContact.setPhone(doctor.getPhone());
			requestContact.setFax(doctor.getFax());
			// requestContact.setLocationIdentifier(doctorExternalIdentifier); todo

			final JsonRequestContactUpsertItem jsonRequestLocationUpsertItem = JsonRequestContactUpsertItem.builder()
					.contactIdentifier(formatExternalId(doctor.getId()))
					.contact(requestContact)
					.jsonAlbertaContact(mapToAlbertaContact(null, doctor.getGender()))
					.build();

			jsonRequestContactUpsert = JsonRequestContactUpsert.builder()
					.requestItem(jsonRequestLocationUpsertItem)
					.build();
		}

		final JsonCompositeAlbertaBPartner albertaComposite;
		{//alberta composite
			final JsonAlbertaBPartner albertaBPartner = new JsonAlbertaBPartner();
			albertaBPartner.setTitle(doctor.getTitle());
			albertaBPartner.setTitleShort(doctor.getTitleShort());
			albertaBPartner.setTimestamp(asInstant(doctor.getTimestamp()));

			albertaComposite = JsonCompositeAlbertaBPartner.builder()
					.jsonAlbertaBPartner(albertaBPartner)
					.role(JsonBPartnerRole.PhysicianDoctor)
					.build();
		}

		final JsonRequestComposite jsonRequestComposite = JsonRequestComposite.builder()
				.bpartner(jsonRequestBPartner)
				.locations(jsonRequestLocationUpsert)
				.contacts(jsonRequestContactUpsert)
				.compositeAlbertaBPartner(albertaComposite)
				.orgCode(orgCode)
				.build();

		return JsonRequestBPartnerUpsertItem
				.builder()
				.bpartnerIdentifier(doctorExternalIdentifier)
				.bpartnerComposite(jsonRequestComposite)
				.build();
	}

	@NonNull
	public static JsonRequestBPartnerUpsertItem mapPharmacyToUpsertRequest(
			@NonNull final Pharmacy pharmacy,
			@Nullable final String orgCode)
	{
		final String pharmacyExternalIdentifier = formatExternalId(pharmacy.getId());

		final JsonRequestBPartner jsonRequestBPartner = new JsonRequestBPartner();
		jsonRequestBPartner.setName(pharmacy.getName());
		jsonRequestBPartner.setPhone(pharmacy.getPhone());
		jsonRequestBPartner.setCustomer(true);
		jsonRequestBPartner.setCode(pharmacyExternalIdentifier);
		jsonRequestBPartner.setUrl(pharmacy.getWebsite());

		final JsonRequestLocationUpsert jsonRequestLocationUpsert;
		{//location
			final JsonRequestLocation requestLocation = new JsonRequestLocation();
			requestLocation.setCountryCode(GetPatientsRouteConstants.COUNTRY_CODE_DE);
			requestLocation.setCity(pharmacy.getCity());
			requestLocation.setPostal(pharmacy.getPostalCode());
			requestLocation.setAddress1(pharmacy.getAddress());
			requestLocation.setBillTo(true);
			requestLocation.setBillToDefault(true);
			requestLocation.setShipTo(true);
			requestLocation.setShipToDefault(true);

			jsonRequestLocationUpsert = toJsonRequestLocationUpsert(pharmacy.getId(), requestLocation);
		}

		final JsonRequestContactUpsert requestContactUpsert;
		{//contact
			final JsonRequestContact contact = new JsonRequestContact();
			contact.setFirstName(pharmacy.getName());
			contact.setPhone(pharmacy.getPhone());
			contact.setEmail(pharmacy.getEmail());
			contact.setFax(pharmacy.getFax());
			// contact.setLocationIdentifier(pharmacyExternalIdentifier); todo

			final JsonRequestContactUpsertItem contactUpsertItem = JsonRequestContactUpsertItem.builder()
					.contactIdentifier(pharmacyExternalIdentifier)
					.contact(contact)
					.build();

			requestContactUpsert = JsonRequestContactUpsert.builder()
					.requestItem(contactUpsertItem)
					.build();
		}

		final JsonCompositeAlbertaBPartner compositeAlbertaBPartner;
		{// alberta composite
			final JsonAlbertaBPartner albertaBPartner = new JsonAlbertaBPartner();
			albertaBPartner.setTimestamp(asInstant(pharmacy.getTimestamp()));

			compositeAlbertaBPartner = JsonCompositeAlbertaBPartner.builder()
					.jsonAlbertaBPartner(albertaBPartner)
					.role(JsonBPartnerRole.Pharmacy)
					.build();
		}

		final JsonRequestComposite jsonRequestComposite = JsonRequestComposite.builder()
				.bpartner(jsonRequestBPartner)
				.locations(jsonRequestLocationUpsert)
				.contacts(requestContactUpsert)
				.compositeAlbertaBPartner(compositeAlbertaBPartner)
				.orgCode(orgCode)
				.build();

		return JsonRequestBPartnerUpsertItem
				.builder()
				.bpartnerIdentifier(pharmacyExternalIdentifier)
				.bpartnerComposite(jsonRequestComposite)
				.build();
	}

	@NonNull
	public static JsonRequestLocationUpsert toJsonRequestLocationUpsert(
			@NonNull final String identifier,
			@NonNull final JsonRequestLocation locationRequest)
	{
		final JsonRequestLocationUpsertItem jsonRequestLocationUpsertItem = JsonRequestLocationUpsertItem.builder()
				.locationIdentifier(formatExternalId(identifier))
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
				.contactIdentifier(formatExternalId(identifier))
				.contact(contactRequest)
				.build();

		return JsonRequestContactUpsert.builder()
				.requestItem(jsonRequestLocationUpsertItem)
				.build();
	}

	public static JsonAlbertaContact mapToAlbertaContact(@Nullable final BigDecimal titleValue, @Nullable final BigDecimal genderValue)
	{
		final JsonAlbertaContact albertaContact = new JsonAlbertaContact();

		albertaContact.setTitle(titleValue != null ? String.valueOf(titleValue) : null);
		albertaContact.setGender(genderValue != null ? String.valueOf(genderValue) : null);

		return albertaContact;
	}
}
