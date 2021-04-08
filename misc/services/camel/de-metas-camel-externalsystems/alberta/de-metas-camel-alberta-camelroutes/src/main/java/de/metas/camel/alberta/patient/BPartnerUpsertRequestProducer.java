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

package de.metas.camel.alberta.patient;

import com.google.common.collect.ImmutableMap;
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
import de.metas.common.bprelation.JsonBPRelationRole;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.common.util.EmptyUtil;
import io.swagger.client.model.CareGiver;
import io.swagger.client.model.Doctor;
import io.swagger.client.model.Hospital;
import io.swagger.client.model.NursingHome;
import io.swagger.client.model.NursingService;
import io.swagger.client.model.Patient;
import io.swagger.client.model.PatientBillingAddress;
import io.swagger.client.model.PatientDeliveryAddress;
import io.swagger.client.model.Payer;
import io.swagger.client.model.Pharmacy;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static de.metas.camel.alberta.patient.GetPatientsRouteConstants.ALBERTA_SYSTEM_NAME;
import static de.metas.camel.alberta.patient.GetPatientsRouteConstants.BILLING_ADDR_PREFIX;
import static de.metas.camel.alberta.patient.GetPatientsRouteConstants.COUNTRY_CODE_DE;
import static de.metas.camel.alberta.patient.GetPatientsRouteConstants.EXTERNAL_ID_PREFIX;
import static de.metas.camel.alberta.patient.GetPatientsRouteConstants.MAIN_ADDR_PREFIX;
import static de.metas.camel.alberta.patient.GetPatientsRouteConstants.SHIPPING_ADDR_PREFIX;

public class BPartnerUpsertRequestProducer
{
	@NonNull
	private final String orgCode;
	@NonNull
	private final Patient patient;
	@NonNull
	private final ImmutableMap<String, JsonMetasfreshId> externalId2MetasfreshId;

	@Nullable
	private final Hospital hospital;
	@Nullable
	private final NursingService nursingService;
	@Nullable
	private final NursingHome nursingHome;
	@Nullable
	private final Doctor doctor;
	@Nullable
	private final Payer payer;
	@Nullable
	private final Pharmacy pharmacy;

	@NonNull
	private final Map<String, JsonBPRelationRole> bPartnerIdentifier2RelationRole;

	@NonNull
	private final BPartnerRequestProducerResult.BPartnerRequestProducerResultBuilder requestProducerResultBuilder;

	@Builder
	public BPartnerUpsertRequestProducer(
			@NonNull final String orgCode,
			@NonNull final Patient patient,
			@NonNull final ImmutableMap<String, JsonMetasfreshId> externalId2MetasfreshId,
			@Nullable final Hospital hospital,
			@Nullable final NursingService nursingService,
			@Nullable final NursingHome nursingHome,
			@Nullable final Doctor doctor,
			@Nullable final Payer payer,
			@Nullable final Pharmacy pharmacy)
	{
		if (patient.getId() == null)
		{
			throw new RuntimeException("Missing patient._id!");
		}

		this.hospital = hospital;
		this.nursingService = nursingService;
		this.nursingHome = nursingHome;
		this.doctor = doctor;
		this.orgCode = orgCode;
		this.patient = patient;
		this.payer = payer;
		this.pharmacy = pharmacy;
		this.externalId2MetasfreshId = externalId2MetasfreshId;
		this.bPartnerIdentifier2RelationRole = new HashMap<>();
		this.requestProducerResultBuilder = BPartnerRequestProducerResult.builder();
	}

	public BPartnerRequestProducerResult run()
	{
		final JsonRequestBPartnerUpsertItem patientUpsertItem = mapPatientBPartner();

		final JsonRequestBPartnerUpsert.JsonRequestBPartnerUpsertBuilder upsertBPartnersRequest = JsonRequestBPartnerUpsert.builder()
				.syncAdvise(SyncAdvise.CREATE_OR_MERGE)
				.requestItem(patientUpsertItem);

		if (patient.getCareGivers() != null && !patient.getCareGivers().isEmpty())
		{
			patient.getCareGivers()
					.stream()
					.map(this::mapCareGiver)
					.forEach(upsertBPartnersRequest::requestItem);
		}

		mapHospital().ifPresent(upsertBPartnersRequest::requestItem);
		mapNursingService().ifPresent(upsertBPartnersRequest::requestItem);
		mapNursingHome().ifPresent(upsertBPartnersRequest::requestItem);
		mapDoctor().ifPresent(upsertBPartnersRequest::requestItem);
		mapPayer().ifPresent(upsertBPartnersRequest::requestItem);
		mapPharmacy().ifPresent(upsertBPartnersRequest::requestItem);

		return requestProducerResultBuilder
				.patientBPartnerIdentifier(patientUpsertItem.getBpartnerIdentifier())
				.jsonRequestBPartnerUpsert(upsertBPartnersRequest.build())
				.bPartnerIdentifier2RelationRole(bPartnerIdentifier2RelationRole)
				.build();
	}

	@NonNull
	private JsonRequestBPartnerUpsertItem mapPatientBPartner()
	{
		final String patientId = patient.getId().toString();

		final JsonRequestBPartner bPartner = new JsonRequestBPartner();
		bPartner.setName(patient.getFirstName() + " " + patient.getLastName());
		bPartner.setPhone(patient.getPhone());
		bPartner.setCustomer(true);
		//bPartner.setMemo(patient.getComment()); TODO

		final JsonRequestContact contact = new JsonRequestContact();
		contact.setFirstName(patient.getFirstName());
		contact.setLastName(patient.getLastName());
		contact.setEmail(patient.getEmail());
		contact.setFax(patient.getFax());
		contact.setPhone(patient.getPhone());
		contact.setMobilePhone(patient.getMobilePhone());
		contact.setDefaultContact(true);
		// contact.setTitle(patient.getTitle()); TODO
		// contact.setBirthDay(patient.getBirthday()); TODO
		// patient.getGender(); TODO needed?

		final JsonRequestContactUpsert upsertContactRequest = toJsonRequestContactUpsert(patientId, contact);

		final JsonMetasfreshId actualMFBPartnerId = externalId2MetasfreshId.get(patientId);
		final String bPartnerIdentifier;
		if (actualMFBPartnerId == null)
		{
			final String externalIdentifier = EXTERNAL_ID_PREFIX + ALBERTA_SYSTEM_NAME + "-" + patientId;
			bPartner.setCode(externalIdentifier);
			bPartnerIdentifier = externalIdentifier;
		}
		else
		{
			bPartnerIdentifier = String.valueOf(actualMFBPartnerId.getValue());
		}
		
		final JsonRequestComposite upsertCompositeBPRequest = JsonRequestComposite.builder()
				.orgCode(orgCode)
				.bpartner(bPartner)
				.locations(mapBPartnerLocations())
				.contacts(upsertContactRequest)
				.build();

		return JsonRequestBPartnerUpsertItem.builder()
				.bpartnerIdentifier(bPartnerIdentifier)
				.bpartnerComposite(upsertCompositeBPRequest)
				.build();
	}

	@NonNull
	private JsonRequestLocationUpsert mapBPartnerLocations()
	{
		final JsonRequestLocationUpsert.JsonRequestLocationUpsertBuilder locationUpsertBuilder = JsonRequestLocationUpsert.builder();

		locationUpsertBuilder.requestItem(getMainAddress());

		if (patient.getBillingAddress() != null)
		{
			locationUpsertBuilder.requestItem(getBillingAddress(patient.getBillingAddress()));
		}

		if (patient.getDeliveryAddress() != null)
		{
			locationUpsertBuilder.requestItem(getShippingAddress(patient.getDeliveryAddress()));
		}

		return locationUpsertBuilder.build();
	}

	@NonNull
	private JsonRequestLocationUpsertItem getBillingAddress(@NonNull final PatientBillingAddress patientBillingAddress)
	{
		final String patientId = patient.getId().toString();

		final JsonRequestLocation location = new JsonRequestLocation();
		location.setAddress1(patientBillingAddress.getAddress());
		location.setPostal(patientBillingAddress.getPostalCode());
		location.setCity(patientBillingAddress.getCity());
		location.setCountryCode(COUNTRY_CODE_DE);
		location.setBpartnerName(patientBillingAddress.getName());
		location.setBillToDefault(true);
		location.setBillTo(true);

		return JsonRequestLocationUpsertItem.builder()
				.locationIdentifier(EXTERNAL_ID_PREFIX + ALBERTA_SYSTEM_NAME + "-" + BILLING_ADDR_PREFIX + patientId)
				.location(location)
				.build();
	}

	@NonNull
	private JsonRequestLocationUpsertItem getShippingAddress(@NonNull final PatientDeliveryAddress patientDeliveryAddress)
	{
		final String patientId = patient.getId().toString();

		final JsonRequestLocation location = new JsonRequestLocation();
		location.setAddress1(patientDeliveryAddress.getAddress());
		location.setPostal(patientDeliveryAddress.getPostalCode());
		location.setCity(patientDeliveryAddress.getCity());
		location.setCountryCode(COUNTRY_CODE_DE);
		location.setBpartnerName(patientDeliveryAddress.getName());
		location.setShipTo(true);
		location.setShipToDefault(true);

		return JsonRequestLocationUpsertItem.builder()
				.locationIdentifier(EXTERNAL_ID_PREFIX + ALBERTA_SYSTEM_NAME + "-" + SHIPPING_ADDR_PREFIX + patientId)
				.location(location)
				.build();
	}

	@NonNull
	private JsonRequestLocationUpsertItem getMainAddress()
	{
		final String patientId = patient.getId().toString();

		final boolean hasExtraBillToAddress = patient.getBillingAddress() != null;
		final boolean hasExtraShipToAddress = patient.getDeliveryAddress() != null;

		final JsonRequestLocation location = new JsonRequestLocation();
		location.setAddress1(patient.getAddress());
		location.setAddress2(patient.getAdditionalAddress());
		location.setAddress3(patient.getAdditionalAddress2());
		location.setPostal(patient.getPostalCode());
		location.setCity(patient.getCity());
		location.setCountryCode(COUNTRY_CODE_DE);
		location.setBillToDefault(!hasExtraBillToAddress);
		location.setBillTo(!hasExtraBillToAddress);
		location.setShipToDefault(!hasExtraShipToAddress);
		location.setShipTo(!hasExtraShipToAddress);

		final String identifier = EXTERNAL_ID_PREFIX + ALBERTA_SYSTEM_NAME + "-" + MAIN_ADDR_PREFIX + patientId;
		requestProducerResultBuilder.patientMainAddressIdentifier(identifier);

		return JsonRequestLocationUpsertItem.builder()
				.locationIdentifier(identifier)
				.location(location)
				.build();
	}

	@NonNull
	private JsonRequestBPartnerUpsertItem mapCareGiver(@NonNull final CareGiver careGiver)
	{
		final String careGiverId = Optional.ofNullable(careGiver.getId())
				.map(UUID::toString)
				.orElseThrow(() -> new RuntimeException("Missing careGiver._id!"));

		final var bPartner = new JsonRequestBPartner();
		bPartner.setName(careGiver.getFirstName() + " " + careGiver.getLastName());
		bPartner.setPhone(careGiver.getPhone());
		bPartner.setCustomer(true);

		final JsonRequestLocation location = new JsonRequestLocation();
		location.setAddress1(careGiver.getAddress());
		location.setCity(careGiver.getCity());
		location.setCountryCode(COUNTRY_CODE_DE);
		location.setPostal(careGiver.getPostalCode());

		final JsonRequestLocationUpsert locationUpsertRequest =
				toJsonRequestLocationUpsert(careGiverId, location);

		final JsonRequestContact contact = new JsonRequestContact();
		contact.setFirstName(careGiver.getFirstName());
		contact.setLastName(careGiver.getLastName());
		contact.setEmail(careGiver.getEmail());
		contact.setFax(careGiver.getFax());
		contact.setPhone(careGiver.getPhone());
		contact.setMobilePhone(careGiver.getMobilePhone());
		contact.setDefaultContact(true);
		// contact.setTitle(careGiver.getTitle()); TODO: to be seen

		final JsonMetasfreshId actualMFBPartnerId = externalId2MetasfreshId.get(careGiverId);
		final String bPartnerIdentifier;
		if (actualMFBPartnerId == null)
		{
			final String externalIdentifier = EXTERNAL_ID_PREFIX + ALBERTA_SYSTEM_NAME + "-" + careGiverId;
			bPartner.setCode(externalIdentifier);
			bPartnerIdentifier = externalIdentifier;
		}
		else
		{
			bPartnerIdentifier = String.valueOf(actualMFBPartnerId.getValue());
		}

		bPartnerIdentifier2RelationRole.put(bPartnerIdentifier, JsonBPRelationRole.Caregiver);
		
		final JsonRequestContactUpsert contactUpsertReq = toJsonRequestContactUpsert(careGiverId, contact);
		
		return JsonRequestBPartnerUpsertItem.builder()
				.bpartnerIdentifier(bPartnerIdentifier)
				.bpartnerComposite(JsonRequestComposite.builder()
						.orgCode(orgCode)
						.bpartner(bPartner)
						.locations(locationUpsertRequest)
						.contacts(contactUpsertReq)
						.build())
				.build();
	}

	private Optional<JsonRequestBPartnerUpsertItem> mapHospital()
	{
		if (hospital == null)
		{
			return Optional.empty();
		}

		final JsonRequestBPartner jsonRequestBPartner = new JsonRequestBPartner();
		jsonRequestBPartner.setCompanyName(hospital.getCompanyName());
		jsonRequestBPartner.setName(hospital.getName());
		jsonRequestBPartner.setName2(hospital.getCompany());
		jsonRequestBPartner.setName3(hospital.getAdditionalCompanyName());
		jsonRequestBPartner.setCustomer(true);
		jsonRequestBPartner.setPhone(hospital.getPhone());
		// jsonRequestBPartner.setEmail(hospital.getEmail()); //todo: to be seen

		final JsonRequestLocation requestLocation = new JsonRequestLocation();
		requestLocation.setCountryCode(COUNTRY_CODE_DE);
		requestLocation.setCity(hospital.getCity());
		requestLocation.setPostal(hospital.getPostalCode());
		requestLocation.setAddress1(hospital.getAddress());
		requestLocation.setBillTo(true);
		requestLocation.setBillToDefault(true);
		requestLocation.setShipTo(true);
		requestLocation.setShipToDefault(true);

		final JsonMetasfreshId actualMFBPartnerId = externalId2MetasfreshId.get(hospital.getId());
		final String bPartnerIdentifier;
		if (actualMFBPartnerId == null)
		{
			final String externalIdentifier = EXTERNAL_ID_PREFIX + ALBERTA_SYSTEM_NAME + "-" + hospital.getId();
			jsonRequestBPartner.setCode(externalIdentifier);
			bPartnerIdentifier = externalIdentifier;
		}
		else
		{
			bPartnerIdentifier = String.valueOf(actualMFBPartnerId.getValue());
		}
		bPartnerIdentifier2RelationRole.put(bPartnerIdentifier, JsonBPRelationRole.Hospital);
		
		final JsonRequestLocationUpsert upsertLocationsRequest =
				toJsonRequestLocationUpsert(hospital.getId(), requestLocation);

		final JsonRequestComposite compositeUpsertItem = JsonRequestComposite.builder()
				.orgCode(orgCode)
				.bpartner(jsonRequestBPartner)
				.locations(upsertLocationsRequest)
				.build();

		return Optional.of(JsonRequestBPartnerUpsertItem.builder()
				.bpartnerIdentifier(bPartnerIdentifier)
				.bpartnerComposite(compositeUpsertItem)
				.build());
	}

	private Optional<JsonRequestBPartnerUpsertItem> mapNursingService()
	{
		if (nursingService == null)
		{
			return Optional.empty();
		}
	
		final JsonRequestBPartner jsonRequestBPartner = new JsonRequestBPartner();
		jsonRequestBPartner.setName(nursingService.getName());
		jsonRequestBPartner.setPhone(nursingService.getPhone());
		jsonRequestBPartner.setCustomer(true);
		// jsonRequestBPartner.setEmail(nursingService.getEmail()); //todo: to be seen

		final JsonRequestLocation requestLocation = new JsonRequestLocation();
		requestLocation.setAddress1(nursingService.getAddress());
		requestLocation.setCity(nursingService.getCity());
		requestLocation.setPostal(nursingService.getPostalCode());
		requestLocation.setCountryCode(COUNTRY_CODE_DE);
		requestLocation.setBillTo(true);
		requestLocation.setBillToDefault(true);
		requestLocation.setShipTo(true);
		requestLocation.setShipToDefault(true);

		final JsonMetasfreshId actualMFBPartnerId = externalId2MetasfreshId.get(nursingService.getId());
		final String bPartnerIdentifier;
		if (actualMFBPartnerId == null)
		{
			final String externalIdentifier = EXTERNAL_ID_PREFIX + ALBERTA_SYSTEM_NAME + "-" + nursingService.getId();
			jsonRequestBPartner.setCode(externalIdentifier);
			bPartnerIdentifier = externalIdentifier;
		}
		else
		{
			bPartnerIdentifier = String.valueOf(actualMFBPartnerId.getValue());
		}
		bPartnerIdentifier2RelationRole.put(bPartnerIdentifier, JsonBPRelationRole.NursingService);
		
		final JsonRequestLocationUpsert jsonRequestLocationUpsert =
				toJsonRequestLocationUpsert(nursingService.getId(), requestLocation);

		final JsonRequestComposite jsonRequestComposite = JsonRequestComposite.builder()
				.bpartner(jsonRequestBPartner)
				.locations(jsonRequestLocationUpsert)
				.orgCode(orgCode)
				.build();

		return Optional.of(JsonRequestBPartnerUpsertItem
				.builder()
				.bpartnerIdentifier(bPartnerIdentifier)
				.bpartnerComposite(jsonRequestComposite)
				.build());
	}

	private Optional<JsonRequestBPartnerUpsertItem> mapNursingHome()
	{
		if (nursingHome == null)
		{
			return Optional.empty();
		}

		final JsonRequestBPartner jsonRequestBPartner = new JsonRequestBPartner();
		jsonRequestBPartner.setName(nursingHome.getName());
		jsonRequestBPartner.setPhone(nursingHome.getPhone());
		jsonRequestBPartner.setCustomer(true);
		// jsonRequestBPartner.setEmail(nursingHome.getEmail()); //todo

		final JsonRequestLocation requestLocation = new JsonRequestLocation();
		requestLocation.setAddress1(nursingHome.getAddress());
		requestLocation.setCity(nursingHome.getCity());
		requestLocation.setPostal(nursingHome.getPostalCode());
		requestLocation.setCountryCode(COUNTRY_CODE_DE);
		requestLocation.setBillTo(true);
		requestLocation.setBillToDefault(true);
		requestLocation.setShipTo(true);
		requestLocation.setShipToDefault(true);

		final JsonRequestLocationUpsert jsonRequestLocationUpsert =
				toJsonRequestLocationUpsert(nursingHome.getId(), requestLocation);

		final JsonMetasfreshId actualMFBPartnerId = externalId2MetasfreshId.get(nursingHome.getId());
		final String bPartnerIdentifier;
		if (actualMFBPartnerId == null)
		{
			final String externalIdentifier = EXTERNAL_ID_PREFIX + ALBERTA_SYSTEM_NAME + "-" + nursingHome.getId();
			jsonRequestBPartner.setCode(externalIdentifier);
			bPartnerIdentifier = externalIdentifier;
		}
		else
		{
			bPartnerIdentifier = String.valueOf(actualMFBPartnerId.getValue());
		}
		bPartnerIdentifier2RelationRole.put(bPartnerIdentifier, JsonBPRelationRole.NursingHome);
		
		final JsonRequestComposite jsonRequestComposite = JsonRequestComposite.builder()
				.bpartner(jsonRequestBPartner)
				.locations(jsonRequestLocationUpsert)
				.orgCode(orgCode)
				.build();

		return Optional.of(JsonRequestBPartnerUpsertItem
				.builder()
				.bpartnerIdentifier(bPartnerIdentifier)
				.bpartnerComposite(jsonRequestComposite)
				.build());
	}

	private Optional<JsonRequestBPartnerUpsertItem> mapDoctor()
	{
		if (doctor == null)
		{
			return Optional.empty();
		}

		final JsonRequestBPartner jsonRequestBPartner = new JsonRequestBPartner();
		jsonRequestBPartner.setName(doctor.getFirstName() + " " + doctor.getLastName());
		jsonRequestBPartner.setPhone(doctor.getPhone());
		jsonRequestBPartner.setCustomer(true);

		final JsonRequestContact requestContact = new JsonRequestContact();
		//requestContact.setExternalId(JsonExternalId.of(doctor.getId())); // TODO
		requestContact.setFirstName(doctor.getFirstName());
		requestContact.setLastName(doctor.getLastName());
		requestContact.setPhone(doctor.getPhone());
		requestContact.setFax(doctor.getFax());
		// requestContact.setGender(doctor.getGender());  //TODO

		final JsonRequestLocation requestLocation = new JsonRequestLocation();
		requestLocation.setAddress1(doctor.getAddress());
		requestLocation.setCity(doctor.getCity());
		requestLocation.setPostal(doctor.getPostalCode());
		requestLocation.setCountryCode(COUNTRY_CODE_DE);
		requestLocation.setBillTo(true);
		requestLocation.setBillToDefault(true);
		requestLocation.setShipTo(true);
		requestLocation.setShipToDefault(true);

		final JsonRequestLocationUpsert jsonRequestLocationUpsert =
				toJsonRequestLocationUpsert(doctor.getId(), requestLocation);

		final JsonRequestContactUpsert jsonRequestContactUpsert = toJsonRequestContactUpsert(doctor.getId(), requestContact);

		final JsonMetasfreshId actualMFBPartnerId = externalId2MetasfreshId.get(doctor.getId());
		final String bPartnerIdentifier;
		if (actualMFBPartnerId == null)
		{
			final String externalIdentifier = EXTERNAL_ID_PREFIX + ALBERTA_SYSTEM_NAME + "-" + doctor.getId();
			jsonRequestBPartner.setCode(externalIdentifier);
			bPartnerIdentifier = externalIdentifier;
		}
		else
		{
			bPartnerIdentifier = String.valueOf(actualMFBPartnerId.getValue());
		}
		bPartnerIdentifier2RelationRole.put(bPartnerIdentifier, JsonBPRelationRole.PhysicianDoctor);

		final JsonRequestComposite jsonRequestComposite = JsonRequestComposite.builder()
				.bpartner(jsonRequestBPartner)
				.locations(jsonRequestLocationUpsert)
				.contacts(jsonRequestContactUpsert)
				.orgCode(orgCode)
				.build();

		return Optional.of(JsonRequestBPartnerUpsertItem
				.builder()
				.bpartnerIdentifier(bPartnerIdentifier)
				.bpartnerComposite(jsonRequestComposite)
				.build());
	}

	private Optional<JsonRequestBPartnerUpsertItem> mapPayer()
	{
		if (payer == null)
		{
			return Optional.empty();
		}

		final JsonRequestBPartner jsonRequestBPartner = new JsonRequestBPartner();
		jsonRequestBPartner.setName(payer.getName());
		if(EmptyUtil.isNotBlank(payer.getIkNumber()))
		{
			jsonRequestBPartner.setCode(payer.getIkNumber());
		}
		jsonRequestBPartner.setCustomer(true);

		final JsonRequestLocation requestLocation = new JsonRequestLocation();
		requestLocation.setCountryCode(COUNTRY_CODE_DE);
		requestLocation.setBillTo(true);
		requestLocation.setBillToDefault(true);
		requestLocation.setShipTo(true);
		requestLocation.setShipToDefault(true);
		
		final JsonRequestLocationUpsert jsonRequestLocationUpsert =
				toJsonRequestLocationUpsert(payer.getId(), requestLocation);

		final JsonMetasfreshId actualMFBPartnerId = externalId2MetasfreshId.get(payer.getId());
		final String bPartnerIdentifier;
		if (actualMFBPartnerId == null)
		{
			final String externalIdentifier = EXTERNAL_ID_PREFIX + ALBERTA_SYSTEM_NAME + "-" + payer.getId();
			jsonRequestBPartner.setCode(externalIdentifier);
			bPartnerIdentifier = externalIdentifier;
		}
		else
		{
			bPartnerIdentifier = String.valueOf(actualMFBPartnerId.getValue());
		}
		bPartnerIdentifier2RelationRole.put(bPartnerIdentifier, JsonBPRelationRole.Payer);

		final JsonRequestComposite jsonRequestComposite = JsonRequestComposite.builder()
				.bpartner(jsonRequestBPartner)
				.locations(jsonRequestLocationUpsert)
				.orgCode(orgCode)
				.build();

		return Optional.of(JsonRequestBPartnerUpsertItem
				.builder()
				.bpartnerIdentifier(bPartnerIdentifier)
				.bpartnerComposite(jsonRequestComposite)
				.build());
	}

	private Optional<JsonRequestBPartnerUpsertItem> mapPharmacy()
	{
		if (pharmacy == null)
		{
			return Optional.empty();
		}

		final JsonRequestBPartner jsonRequestBPartner = new JsonRequestBPartner();
		jsonRequestBPartner.setName(pharmacy.getName());
		jsonRequestBPartner.setPhone(pharmacy.getPhone());
		jsonRequestBPartner.setCustomer(true);
		// todo pharmacy.getWebsite() ?
		// todo pharmacy.getEmail() ?
		// todo pharmacy.getFax() ?

		final JsonRequestLocation requestLocation = new JsonRequestLocation();
		requestLocation.setCountryCode(COUNTRY_CODE_DE);
		requestLocation.setCity(pharmacy.getCity());
		requestLocation.setPostal(pharmacy.getPostalCode());
		requestLocation.setAddress1(pharmacy.getAddress());
		requestLocation.setBillTo(true);
		requestLocation.setBillToDefault(true);
		requestLocation.setShipTo(true);
		requestLocation.setShipToDefault(true);

		final JsonRequestLocationUpsert jsonRequestLocationUpsert =
				toJsonRequestLocationUpsert(pharmacy.getId(), requestLocation);

		final JsonMetasfreshId actualMFBPartnerId = externalId2MetasfreshId.get(pharmacy.getId());
		final String bPartnerIdentifier;
		if (actualMFBPartnerId == null)
		{
			final String externalIdentifier = EXTERNAL_ID_PREFIX + ALBERTA_SYSTEM_NAME + "-" + pharmacy.getId();
			jsonRequestBPartner.setCode(externalIdentifier);
			bPartnerIdentifier = externalIdentifier;
		}
		else
		{
			bPartnerIdentifier = String.valueOf(actualMFBPartnerId.getValue());
		}
		bPartnerIdentifier2RelationRole.put(bPartnerIdentifier, JsonBPRelationRole.Pharmacy);

		final JsonRequestComposite jsonRequestComposite = JsonRequestComposite.builder()
				.bpartner(jsonRequestBPartner)
				.locations(jsonRequestLocationUpsert)
				.orgCode(orgCode)
				.build();

		return Optional.of(JsonRequestBPartnerUpsertItem
				.builder()
				.bpartnerIdentifier(bPartnerIdentifier)
				.bpartnerComposite(jsonRequestComposite)
				.build());
	}

	@NonNull
	private JsonRequestLocationUpsert toJsonRequestLocationUpsert(
			@NonNull final String identifier,
			@NonNull final JsonRequestLocation locationRequest)
	{
		final JsonRequestLocationUpsertItem jsonRequestLocationUpsertItem = JsonRequestLocationUpsertItem.builder()
				.locationIdentifier(EXTERNAL_ID_PREFIX + ALBERTA_SYSTEM_NAME + "-" + identifier)
				.location(locationRequest)
				.build();

		return JsonRequestLocationUpsert.builder()
				.requestItem(jsonRequestLocationUpsertItem)
				.build();
	}

	@NonNull
	private JsonRequestContactUpsert toJsonRequestContactUpsert(
			@NonNull final String identifier,
			@NonNull final JsonRequestContact contactRequest)
	{
		final JsonRequestContactUpsertItem jsonRequestLocationUpsertItem = JsonRequestContactUpsertItem.builder()
				.contactIdentifier(EXTERNAL_ID_PREFIX + ALBERTA_SYSTEM_NAME + "-" + identifier)
				.contact(contactRequest)
				//.contactExternalRef(createLocationESR("", identifier)) TODO
				.build();

		return JsonRequestContactUpsert.builder()
				.requestItem(jsonRequestLocationUpsertItem)
				.build();
	}

	@Value
	@Builder
	public static class BPartnerRequestProducerResult
	{
		@NonNull
		String patientBPartnerIdentifier;

		@NonNull
		String patientMainAddressIdentifier;

		@NonNull
		Map<String, JsonBPRelationRole> bPartnerIdentifier2RelationRole;

		@NonNull
		JsonRequestBPartnerUpsert jsonRequestBPartnerUpsert;
	}
}
