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

package de.metas.camel.externalsystems.alberta.common;

import de.metas.camel.externalsystems.alberta.patient.GetPatientsRouteConstants;
import de.metas.camel.externalsystems.common.v2.BPUpsertCamelRequest;
import de.metas.common.bpartner.v2.request.JsonRequestBPartner;
import de.metas.common.bpartner.v2.request.JsonRequestBPartnerUpsert;
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
import de.metas.common.ordercandidates.v2.request.JsonRequestBPartnerLocationAndContact;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.common.util.Check;
import de.metas.common.util.EmptyUtil;
import io.swagger.client.model.Doctor;
import io.swagger.client.model.Hospital;
import io.swagger.client.model.NursingHome;
import io.swagger.client.model.NursingService;
import io.swagger.client.model.Payer;
import io.swagger.client.model.Pharmacy;
import io.swagger.client.model.Users;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.StringJoiner;

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
			requestContact.setName(new StringJoiner(" ").add(doctor.getFirstName()).add(doctor.getLastName()).toString());
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
			contact.setName(pharmacy.getName());
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
	public static JsonRequestBPartnerUpsertItem mapPayerToUpsertRequest(
			@NonNull final Payer payer,
			@Nullable final String orgCode
	)
	{
		final String payerExternalIdentifier = formatExternalId(payer.getId());

		final JsonRequestBPartner jsonRequestBPartner = new JsonRequestBPartner();
		jsonRequestBPartner.setName(payer.getName());
		jsonRequestBPartner.setCustomer(true);
		jsonRequestBPartner.setCode(payerExternalIdentifier);

		if (EmptyUtil.isNotBlank(payer.getIkNumber()))
		{
			jsonRequestBPartner.setCode(payer.getIkNumber());
		}

		final JsonRequestLocationUpsert jsonRequestLocationUpsert;
		{//location
			final JsonRequestLocation requestLocation = new JsonRequestLocation();
			requestLocation.setCountryCode(GetPatientsRouteConstants.COUNTRY_CODE_DE);
			requestLocation.setBillTo(true);
			requestLocation.setBillToDefault(true);
			requestLocation.setShipTo(true);
			requestLocation.setShipToDefault(true);

			jsonRequestLocationUpsert =
					toJsonRequestLocationUpsert(payer.getId(), requestLocation);
		}

		final JsonCompositeAlbertaBPartner compositeAlbertaBPartner;
		{// alberta composite
			final JsonAlbertaBPartner albertaBPartner = new JsonAlbertaBPartner();
			albertaBPartner.setTimestamp(asInstant(payer.getTimestamp()));

			compositeAlbertaBPartner = JsonCompositeAlbertaBPartner.builder()
					.jsonAlbertaBPartner(albertaBPartner)
					.role(JsonBPartnerRole.Payer)
					.build();
		}

		final JsonRequestComposite jsonRequestComposite = JsonRequestComposite.builder()
				.bpartner(jsonRequestBPartner)
				.locations(jsonRequestLocationUpsert)
				.compositeAlbertaBPartner(compositeAlbertaBPartner)
				.orgCode(orgCode)
				.build();

		return JsonRequestBPartnerUpsertItem
				.builder()
				.bpartnerIdentifier(payerExternalIdentifier)
				.bpartnerComposite(jsonRequestComposite)
				.build();
	}

	@NonNull
	public static JsonRequestBPartnerUpsertItem mapNursingServiceToUpsertRequest(
			@NonNull final NursingService nursingService,
			@Nullable final String orgCode
	)
	{
		final String nursingServiceExternalIdentifier = formatExternalId(nursingService.getId());

		final JsonRequestBPartner jsonRequestBPartner = new JsonRequestBPartner();
		jsonRequestBPartner.setName(nursingService.getName());
		jsonRequestBPartner.setPhone(nursingService.getPhone());
		jsonRequestBPartner.setCustomer(true);
		jsonRequestBPartner.setCode(nursingServiceExternalIdentifier);
		jsonRequestBPartner.setUrl(nursingService.getWebsite());

		final JsonRequestLocationUpsert jsonRequestLocationUpsert;
		{//location
			final JsonRequestLocation requestLocation = new JsonRequestLocation();
			requestLocation.setAddress1(nursingService.getAddress());
			requestLocation.setCity(nursingService.getCity());
			requestLocation.setPostal(nursingService.getPostalCode());
			requestLocation.setCountryCode(GetPatientsRouteConstants.COUNTRY_CODE_DE);
			requestLocation.setBillTo(true);
			requestLocation.setBillToDefault(true);
			requestLocation.setShipTo(true);
			requestLocation.setShipToDefault(true);

			jsonRequestLocationUpsert = toJsonRequestLocationUpsert(nursingService.getId(), requestLocation);
		}

		final JsonRequestContactUpsert requestContactUpsert;
		{//contact
			final JsonRequestContact contact = new JsonRequestContact();
			contact.setName(nursingService.getName());
			contact.setPhone(nursingService.getPhone());
			contact.setEmail(nursingService.getEmail());
			contact.setFax(nursingService.getFax());
			// contact.setLocationIdentifier(nursingServiceExternalIdentifier); todo

			final JsonRequestContactUpsertItem contactUpsertItem = JsonRequestContactUpsertItem.builder()
					.contactIdentifier(nursingServiceExternalIdentifier)
					.contact(contact)
					.build();

			requestContactUpsert = JsonRequestContactUpsert.builder()
					.requestItem(contactUpsertItem)
					.build();
		}

		final JsonCompositeAlbertaBPartner compositeAlbertaBPartner;
		{// alberta composite
			final JsonAlbertaBPartner albertaBPartner = new JsonAlbertaBPartner();
			albertaBPartner.setTimestamp(asInstant(nursingService.getTimestamp()));

			compositeAlbertaBPartner = JsonCompositeAlbertaBPartner.builder()
					.jsonAlbertaBPartner(albertaBPartner)
					.role(JsonBPartnerRole.NursingService)
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
				.bpartnerIdentifier(nursingServiceExternalIdentifier)
				.bpartnerComposite(jsonRequestComposite)
				.build();
	}

	@NonNull
	public static JsonRequestBPartnerUpsertItem mapNursingHomeToUpsertRequest(
			@NonNull final NursingHome nursingHome,
			@Nullable final String orgCode
	)
	{
		final String nursingHomeExternalIdentifier = formatExternalId(nursingHome.getId());

		final JsonRequestBPartner jsonRequestBPartner = new JsonRequestBPartner();
		jsonRequestBPartner.setName(nursingHome.getName());
		jsonRequestBPartner.setPhone(nursingHome.getPhone());
		jsonRequestBPartner.setCustomer(true);
		jsonRequestBPartner.setCode(nursingHomeExternalIdentifier);

		final JsonRequestLocationUpsert jsonRequestLocationUpsert;
		{//location
			final JsonRequestLocation requestLocation = new JsonRequestLocation();
			requestLocation.setAddress1(nursingHome.getAddress());
			requestLocation.setCity(nursingHome.getCity());
			requestLocation.setPostal(nursingHome.getPostalCode());
			requestLocation.setCountryCode(GetPatientsRouteConstants.COUNTRY_CODE_DE);
			requestLocation.setBillTo(true);
			requestLocation.setBillToDefault(true);
			requestLocation.setShipTo(true);
			requestLocation.setShipToDefault(true);

			jsonRequestLocationUpsert = toJsonRequestLocationUpsert(nursingHome.getId(), requestLocation);
		}

		final JsonRequestContactUpsert requestContactUpsert;
		{//contact
			final JsonRequestContact contact = new JsonRequestContact();
			contact.setName(nursingHome.getName());
			contact.setPhone(nursingHome.getPhone());
			contact.setEmail(nursingHome.getEmail());
			contact.setFax(nursingHome.getFax());
			// contact.setLocationIdentifier(nursingHomeExternalIdentifier); todo

			final JsonRequestContactUpsertItem contactUpsertItem = JsonRequestContactUpsertItem.builder()
					.contactIdentifier(nursingHomeExternalIdentifier)
					.contact(contact)
					.build();

			requestContactUpsert = JsonRequestContactUpsert.builder()
					.requestItem(contactUpsertItem)
					.build();
		}

		final JsonCompositeAlbertaBPartner compositeAlbertaBPartner;
		{// alberta composite
			final JsonAlbertaBPartner albertaBPartner = new JsonAlbertaBPartner();
			albertaBPartner.setTimestamp(asInstant(nursingHome.getTimestamp()));

			compositeAlbertaBPartner = JsonCompositeAlbertaBPartner.builder()
					.jsonAlbertaBPartner(albertaBPartner)
					.role(JsonBPartnerRole.NursingHome)
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
				.bpartnerIdentifier(nursingHomeExternalIdentifier)
				.bpartnerComposite(jsonRequestComposite)
				.build();
	}

	@NonNull
	public static JsonRequestBPartnerUpsertItem mapHospitalToUpsertRequest(
			@NonNull final Hospital hospital,
			@Nullable final String orgCode
	)
	{
		final String hospitalExternalIdentifier = formatExternalId(hospital.getId());

		final JsonRequestBPartner jsonRequestBPartner = new JsonRequestBPartner();
		jsonRequestBPartner.setCompanyName(hospital.getCompanyName());
		jsonRequestBPartner.setName(hospital.getName());
		jsonRequestBPartner.setName2(hospital.getCompany());
		jsonRequestBPartner.setName3(hospital.getAdditionalCompanyName());
		jsonRequestBPartner.setCustomer(true);
		jsonRequestBPartner.setPhone(hospital.getPhone());
		jsonRequestBPartner.setCode(hospitalExternalIdentifier);
		jsonRequestBPartner.setUrl(hospital.getWebsite());

		final JsonRequestLocationUpsert upsertLocationsRequest;
		{//location
			final JsonRequestLocation requestLocation = new JsonRequestLocation();
			requestLocation.setCountryCode(GetPatientsRouteConstants.COUNTRY_CODE_DE);
			requestLocation.setCity(hospital.getCity());
			requestLocation.setPostal(hospital.getPostalCode());
			requestLocation.setAddress1(hospital.getAddress());
			requestLocation.setBillTo(true);
			requestLocation.setBillToDefault(true);
			requestLocation.setShipTo(true);
			requestLocation.setShipToDefault(true);

			upsertLocationsRequest = toJsonRequestLocationUpsert(hospital.getId(), requestLocation);
		}

		final JsonRequestContactUpsert requestContactUpsert;
		{//contact
			final JsonRequestContact contact = new JsonRequestContact();
			contact.setName(hospital.getName());
			contact.setPhone(hospital.getPhone());
			contact.setEmail(hospital.getEmail());
			contact.setFax(hospital.getFax());
			// contact.setLocationIdentifier(hospitalExternalIdentifier); todo

			final JsonRequestContactUpsertItem contactUpsertItem = JsonRequestContactUpsertItem.builder()
					.contactIdentifier(hospitalExternalIdentifier)
					.contact(contact)
					.build();

			requestContactUpsert = JsonRequestContactUpsert.builder()
					.requestItem(contactUpsertItem)
					.build();
		}

		final JsonCompositeAlbertaBPartner compositeAlbertaBPartner;
		{// alberta composite
			final JsonAlbertaBPartner albertaBPartner = new JsonAlbertaBPartner();
			albertaBPartner.setTimestamp(asInstant(hospital.getTimestamp()));

			compositeAlbertaBPartner = JsonCompositeAlbertaBPartner.builder()
					.jsonAlbertaBPartner(albertaBPartner)
					.role(JsonBPartnerRole.Hospital)
					.build();
		}

		final JsonRequestComposite compositeUpsertItem = JsonRequestComposite.builder()
				.orgCode(orgCode)
				.bpartner(jsonRequestBPartner)
				.locations(upsertLocationsRequest)
				.contacts(requestContactUpsert)
				.compositeAlbertaBPartner(compositeAlbertaBPartner)
				.build();

		return JsonRequestBPartnerUpsertItem.builder()
				.bpartnerIdentifier(hospitalExternalIdentifier)
				.bpartnerComposite(compositeUpsertItem)
				.build();
	}

	public static JsonAlbertaContact mapToAlbertaContact(@Nullable final BigDecimal titleValue, @Nullable final BigDecimal genderValue)
	{
		final JsonAlbertaContact albertaContact = new JsonAlbertaContact();

		albertaContact.setTitle(titleValue != null ? String.valueOf(titleValue) : null);
		albertaContact.setGender(genderValue != null ? String.valueOf(genderValue) : null);

		return albertaContact;
	}

	@NonNull
	public static Optional<JsonRequestContactUpsertItem> userToBPartnerContact(@Nullable final Users users)
	{
		if (users == null)
		{
			return Optional.empty();
		}

		final JsonRequestContact contact = new JsonRequestContact();
		contact.setName(new StringJoiner(" ").add(users.getFirstName()).add(users.getLastName()).toString());
		contact.setFirstName(users.getFirstName());
		contact.setLastName(users.getLastName());
		contact.setEmail(users.getEmail());

		final JsonAlbertaContact albertaContact = new JsonAlbertaContact();
		albertaContact.setTimestamp(asInstant(users.getTimestamp()));

		return Optional.of(JsonRequestContactUpsertItem.builder()
								   .contactIdentifier(formatExternalId(users.getId()))
								   .contact(contact)
								   .jsonAlbertaContact(albertaContact)
								   .build());
	}

	@NonNull
	public static Optional<BPUpsertCamelRequest> contactToBPartnerUpsert(
			@NonNull final JsonRequestContactUpsert requestContactUpsert,
			@NonNull final JsonMetasfreshId rootBPartnerIdForUsers,
			@NonNull final String orgCode)
	{
		if (Check.isEmpty(requestContactUpsert.getRequestItems()))
		{
			return Optional.empty();
		}

		final JsonRequestComposite jsonRequestComposite = JsonRequestComposite.builder()
				.contacts(requestContactUpsert)
				.build();

		final JsonRequestBPartnerUpsertItem upsertItem = JsonRequestBPartnerUpsertItem
				.builder()
				.bpartnerIdentifier(String.valueOf(rootBPartnerIdForUsers.getValue()))
				.bpartnerComposite(jsonRequestComposite)
				.build();

		final JsonRequestBPartnerUpsert bPartnerUpsert = JsonRequestBPartnerUpsert.builder()
				.requestItem(upsertItem)
				.syncAdvise(SyncAdvise.CREATE_OR_MERGE)
				.build();

		return Optional.of(BPUpsertCamelRequest.builder()
								   .jsonRequestBPartnerUpsert(bPartnerUpsert)
								   .orgCode(orgCode)
								   .build());
	}

	@NonNull
	public static Optional<BPUpsertCamelRequest> usersToBPartnerUpsert(
			@NonNull final String orgCode,
			@NonNull final JsonMetasfreshId bPartnerId,
			@Nullable final Users... users)
	{
		if (users == null)
		{
			return Optional.empty();
		}

		final JsonRequestContactUpsert.JsonRequestContactUpsertBuilder usersUpsertBuilder = JsonRequestContactUpsert.builder();

		final HashSet<String> seenUserIds = new HashSet<>();

		for (final Users user : users)
		{
			DataMapper.userToBPartnerContact(user)
					.filter(contactUpsertItem -> !seenUserIds.contains(contactUpsertItem.getContactIdentifier()))
					.ifPresent(contactUpsertItem -> {
						seenUserIds.add(contactUpsertItem.getContactIdentifier());

						usersUpsertBuilder.requestItem(contactUpsertItem);
					});
		}

		final JsonRequestContactUpsert usersUpsert = usersUpsertBuilder.build();

		if (Check.isEmpty(usersUpsert.getRequestItems()))
		{
			return Optional.empty();
		}

		return contactToBPartnerUpsert(usersUpsert, bPartnerId, orgCode);
	}

	@NonNull
	public static Optional<JsonRequestBPartnerLocationAndContact> pharmacyToDropShipBPartner(@Nullable final String pharmacyId)
	{
		if(Check.isBlank(pharmacyId))
		{
			return Optional.empty();
		}

		return Optional.of(JsonRequestBPartnerLocationAndContact.builder()
				.bPartnerIdentifier(formatExternalId(pharmacyId))
				.bPartnerLocationIdentifier(formatExternalId(pharmacyId))
				.build());
	}
}
