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

package de.metas.camel.alberta;

import de.metas.common.bpartner.request.JsonRequestBPartner;
import de.metas.common.bpartner.request.JsonRequestBPartnerUpsert;
import de.metas.common.bpartner.request.JsonRequestBPartnerUpsertItem;
import de.metas.common.bpartner.request.JsonRequestComposite;
import de.metas.common.bpartner.request.JsonRequestContact;
import de.metas.common.bpartner.request.JsonRequestContactUpsert;
import de.metas.common.bpartner.request.JsonRequestContactUpsertItem;
import de.metas.common.bpartner.request.JsonRequestLocation;
import de.metas.common.bpartner.request.JsonRequestLocationUpsert;
import de.metas.common.bpartner.request.JsonRequestLocationUpsertItem;
import de.metas.common.bprelation.JsonBPRelationRole;
import de.metas.common.externalreference.JsonExternalReferenceCreateRequest;
import de.metas.common.externalreference.JsonExternalReferenceItem;
import de.metas.common.externalreference.JsonExternalReferenceLookupItem;
import de.metas.common.externalsystem.JsonExternalSystemName;
import de.metas.common.rest_api.JsonExternalId;
import de.metas.common.rest_api.JsonMetasfreshId;
import de.metas.common.rest_api.SyncAdvise;
import io.swagger.client.model.CareGiver;
import io.swagger.client.model.Doctor;
import io.swagger.client.model.Hospital;
import io.swagger.client.model.NursingHome;
import io.swagger.client.model.NursingService;
import io.swagger.client.model.Patient;
import io.swagger.client.model.PatientBillingAddress;
import io.swagger.client.model.PatientDeliveryAddress;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static de.metas.camel.alberta.GetPatientsRouteConstants.ALBERTA_SYSTEM_NAME;
import static de.metas.camel.alberta.GetPatientsRouteConstants.BILLING_ADDR_PREFIX;
import static de.metas.camel.alberta.GetPatientsRouteConstants.COUNTRY_CODE_DE;
import static de.metas.camel.alberta.GetPatientsRouteConstants.ESR_TYPE_BPARTNER;
import static de.metas.camel.alberta.GetPatientsRouteConstants.EXTERNAL_ID_PREFIX;
import static de.metas.camel.alberta.GetPatientsRouteConstants.SHIPPING_ADDR_PREFIX;

public class BPartnerUpsertRequestProducer
{
	@NonNull
	private final String orgCode;
	@NonNull
	private final Patient patient;
	@NonNull
	private final Map<String, JsonMetasfreshId> externalId2MetasfreshId;

	@Nullable
	private final Hospital hospital;
	@Nullable
	private final NursingService nursingService;
	@Nullable
	private final NursingHome nursingHome;
	@Nullable
	private final Doctor doctor;

	@NonNull
	private final Map<String, JsonBPRelationRole> bPartnerIdentifier2RelationRole;

	@NonNull
	private final BPartnerRequestProducerResult.BPartnerRequestProducerResultBuilder requestProducerResultBuilder;

	@Builder
	public BPartnerUpsertRequestProducer(
			@NonNull final String orgCode,
			@NonNull final Patient patient,
			@NonNull final Map<String, JsonMetasfreshId> externalId2MetasfreshId,
			@Nullable final Hospital hospital,
			@Nullable final NursingService nursingService,
			@Nullable final NursingHome nursingHome,
			@Nullable final Doctor doctor)
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
		bPartner.setExternalId(JsonExternalId.of(patientId));
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

		final JsonRequestContactUpsert upsertContactRequest = JsonRequestContactUpsert.builder()
				.requestItem(JsonRequestContactUpsertItem
									 .builder()
									 .contactIdentifier(EXTERNAL_ID_PREFIX + patientId)
									 .contact(contact)
									 .build())
				.build();

		final JsonMetasfreshId actualMFBPartnerId = externalId2MetasfreshId.get(patientId);
		final JsonExternalReferenceCreateRequest referenceCreateRequestOrNull = actualMFBPartnerId == null
				? createInsertExternalReferenceReq(patientId)
				: null;

		final JsonRequestComposite upsertCompositeBPRequest = JsonRequestComposite.builder()
				.orgCode(orgCode)
				.bpartner(bPartner)
				.locations(mapBPartnerLocations())
				.contacts(upsertContactRequest)
				.bPartnerReferenceCreateRequest(referenceCreateRequestOrNull)
				.build();

		final String bPartnerIdentifier = actualMFBPartnerId != null
				? String.valueOf(actualMFBPartnerId.getValue())
				: EXTERNAL_ID_PREFIX + patientId;

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
		location.setExternalId(JsonExternalId.of(BILLING_ADDR_PREFIX + patientId));
		location.setAddress1(patientBillingAddress.getAddress());
		location.setPostal(patientBillingAddress.getPostalCode());
		location.setCity(patientBillingAddress.getCity());
		location.setCountryCode(COUNTRY_CODE_DE);
		location.setBpartnerName(patientBillingAddress.getName());
		location.setBillToDefault(true);
		location.setBillTo(true);

		return JsonRequestLocationUpsertItem.builder()
				.locationIdentifier(EXTERNAL_ID_PREFIX + BILLING_ADDR_PREFIX + patientId)
				.location(location)
				.build();
	}

	@NonNull
	private JsonRequestLocationUpsertItem getShippingAddress(@NonNull final PatientDeliveryAddress patientDeliveryAddress)
	{
		final String patientId = patient.getId().toString();

		final JsonRequestLocation location = new JsonRequestLocation();
		location.setExternalId(JsonExternalId.of(SHIPPING_ADDR_PREFIX + patientId));
		location.setAddress1(patientDeliveryAddress.getAddress());
		location.setPostal(patientDeliveryAddress.getPostalCode());
		location.setCity(patientDeliveryAddress.getCity());
		location.setCountryCode(COUNTRY_CODE_DE);
		location.setBpartnerName(patientDeliveryAddress.getName());
		location.setShipTo(true);
		location.setShipToDefault(true);

		return JsonRequestLocationUpsertItem.builder()
				.locationIdentifier(EXTERNAL_ID_PREFIX + SHIPPING_ADDR_PREFIX + patientId)
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
		location.setExternalId(JsonExternalId.of(patientId));
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

		requestProducerResultBuilder.patientMainAddressIdentifier(EXTERNAL_ID_PREFIX + patientId);

		return JsonRequestLocationUpsertItem.builder()
				.locationIdentifier(EXTERNAL_ID_PREFIX + patientId)
				.location(location).build();
	}

	@NonNull
	private JsonRequestBPartnerUpsertItem mapCareGiver(@NonNull final CareGiver careGiver)
	{
		final String careGiverId = Optional.ofNullable(careGiver.getId())
				.map(UUID::toString)
				.orElseThrow(() -> new RuntimeException("Missing careGiver._id!"));

		final JsonMetasfreshId actualMFBPartnerId = externalId2MetasfreshId.get(careGiverId);
		final String bpartnerIdentifier = actualMFBPartnerId != null
				? String.valueOf(actualMFBPartnerId.getValue())
				: EXTERNAL_ID_PREFIX + careGiverId;

		bPartnerIdentifier2RelationRole.put(bpartnerIdentifier, JsonBPRelationRole.Caregiver);

		final var bPartner = new JsonRequestBPartner();
		bPartner.setName(careGiver.getFirstName() + " " + careGiver.getLastName());
		bPartner.setPhone(careGiver.getPhone());
		bPartner.setCustomer(true);
		//bPartner.setMemo(patient.getComment()); TODO

		final JsonRequestLocation location = new JsonRequestLocation();
		location.setAddress1(careGiver.getAddress());
		location.setCity(careGiver.getCity());
		location.setCountryCode(COUNTRY_CODE_DE);
		location.setExternalId(JsonExternalId.of(careGiverId));
		location.setPostal(careGiver.getPostalCode());

		final JsonRequestLocationUpsert locationUpsertRequest = JsonRequestLocationUpsert.builder()
				.requestItem(JsonRequestLocationUpsertItem.builder()
									 .locationIdentifier(EXTERNAL_ID_PREFIX + careGiverId)
									 .location(location)
									 .build()
				)
				.build();

		final JsonRequestContact contact = new JsonRequestContact();
		contact.setFirstName(careGiver.getFirstName());
		contact.setLastName(careGiver.getLastName());
		contact.setEmail(careGiver.getEmail());
		contact.setFax(careGiver.getFax());
		contact.setPhone(careGiver.getPhone());
		contact.setMobilePhone(careGiver.getMobilePhone());
		contact.setDefaultContact(true);
		// contact.setTitle(patient.getTitle()); TODO: to be seen

		final JsonRequestContactUpsert contactUpsertReq = JsonRequestContactUpsert.builder()
				.requestItem(JsonRequestContactUpsertItem.builder()
									 .contactIdentifier(EXTERNAL_ID_PREFIX + careGiverId)
									 .contact(contact)
									 .build())
				.build();

		final JsonExternalReferenceCreateRequest referenceCreateRequestOrNull = actualMFBPartnerId == null
				? createInsertExternalReferenceReq(careGiverId)
				: null;

		return JsonRequestBPartnerUpsertItem.builder()
				.bpartnerIdentifier(bpartnerIdentifier)
				.bpartnerComposite(JsonRequestComposite.builder()
										   .orgCode(orgCode)
										   .bpartner(bPartner)
										   .locations(locationUpsertRequest)
										   .contacts(contactUpsertReq)
										   .bPartnerReferenceCreateRequest(referenceCreateRequestOrNull)
										   .build())
				.build();
	}

	private Optional<JsonRequestBPartnerUpsertItem> mapHospital()
	{
		if (hospital == null)
		{
			return Optional.empty();
		}

		final JsonMetasfreshId actualMFBPartnerId = externalId2MetasfreshId.get(hospital.getId());
		final String bpartnerIdentifier = actualMFBPartnerId != null
				? String.valueOf(actualMFBPartnerId.getValue())
				: EXTERNAL_ID_PREFIX + hospital.getId();

		bPartnerIdentifier2RelationRole.put(bpartnerIdentifier, JsonBPRelationRole.Hospital);

		final JsonRequestBPartner jsonRequestBPartner = new JsonRequestBPartner();
		jsonRequestBPartner.setExternalId(JsonExternalId.of(hospital.getId()));
		jsonRequestBPartner.setCompanyName(hospital.getCompanyName());
		jsonRequestBPartner.setName(hospital.getName());
		jsonRequestBPartner.setName2(hospital.getCompany());
		jsonRequestBPartner.setName3(hospital.getAdditionalCompanyName());
		jsonRequestBPartner.setCustomer(true);
		jsonRequestBPartner.setPhone(hospital.getPhone());
		// jsonRequestBPartner.setEmail(hospital.getEmail()); //todo: to be seen

		final JsonRequestLocation requestLocation = new JsonRequestLocation();
		requestLocation.setExternalId(JsonExternalId.of(hospital.getId()));
		requestLocation.setCountryCode(COUNTRY_CODE_DE);
		requestLocation.setCity(hospital.getCity());
		requestLocation.setPostal(hospital.getPostalCode());
		requestLocation.setAddress1(hospital.getAddress());
		requestLocation.setBillTo(true);
		requestLocation.setBillToDefault(true);
		requestLocation.setShipTo(true);
		requestLocation.setShipToDefault(true);

		final JsonRequestLocationUpsertItem jsonRequestLocationUpsertItem = JsonRequestLocationUpsertItem.builder()
				.locationIdentifier(EXTERNAL_ID_PREFIX + hospital.getId())
				.location(requestLocation)
				.build();

		final JsonExternalReferenceCreateRequest referenceCreateRequestOrNull = actualMFBPartnerId == null
				? createInsertExternalReferenceReq(hospital.getId())
				: null;

		final JsonRequestComposite compositeUpsertItem = JsonRequestComposite.builder()
				.orgCode(orgCode)
				.bpartner(jsonRequestBPartner)
				.locations(JsonRequestLocationUpsert.builder().requestItem(jsonRequestLocationUpsertItem).build())
				.bPartnerReferenceCreateRequest(referenceCreateRequestOrNull)
				.build();

		return Optional.of(JsonRequestBPartnerUpsertItem.builder()
								   .bpartnerIdentifier(bpartnerIdentifier)
								   .bpartnerComposite(compositeUpsertItem)
								   .build());
	}

	private Optional<JsonRequestBPartnerUpsertItem> mapNursingService()
	{
		if (nursingService == null)
		{
			return Optional.empty();
		}

		final JsonMetasfreshId actualMFBPartnerId = externalId2MetasfreshId.get(nursingService.getId());
		final String bpartnerIdentifier = actualMFBPartnerId != null
				? String.valueOf(actualMFBPartnerId.getValue())
				: EXTERNAL_ID_PREFIX + nursingService.getId();

		bPartnerIdentifier2RelationRole.put(bpartnerIdentifier, JsonBPRelationRole.NursingService);

		final JsonRequestBPartner jsonRequestBPartner = new JsonRequestBPartner();
		jsonRequestBPartner.setExternalId(JsonExternalId.of(nursingService.getId()));
		jsonRequestBPartner.setName(nursingService.getName());
		jsonRequestBPartner.setPhone(nursingService.getPhone());
		jsonRequestBPartner.setCustomer(true);
		// jsonRequestBPartner.setEmail(nursingService.getEmail()); //todo: to be seen

		final JsonRequestLocation requestLocation = new JsonRequestLocation();
		requestLocation.setExternalId(JsonExternalId.of(nursingService.getId()));
		requestLocation.setAddress1(nursingService.getAddress());
		requestLocation.setCity(nursingService.getCity());
		requestLocation.setPostal(nursingService.getPostalCode());
		requestLocation.setCountryCode(COUNTRY_CODE_DE);
		requestLocation.setBillTo(true);
		requestLocation.setBillToDefault(true);
		requestLocation.setShipTo(true);
		requestLocation.setShipToDefault(true);

		final JsonRequestLocationUpsertItem jsonRequestLocationUpsertItem = JsonRequestLocationUpsertItem.builder()
				.locationIdentifier(EXTERNAL_ID_PREFIX + nursingService.getId())
				.location(requestLocation)
				.build();

		final JsonRequestLocationUpsert jsonRequestLocationUpsert = JsonRequestLocationUpsert.builder()
				.requestItem(jsonRequestLocationUpsertItem)
				.build();

		final JsonExternalReferenceCreateRequest referenceCreateRequestOrNull = actualMFBPartnerId == null
				? createInsertExternalReferenceReq(nursingService.getId())
				: null;

		final JsonRequestComposite jsonRequestComposite = JsonRequestComposite.builder()
				.bpartner(jsonRequestBPartner)
				.locations(jsonRequestLocationUpsert)
				.orgCode(orgCode)
				.bPartnerReferenceCreateRequest(referenceCreateRequestOrNull)
				.build();

		return Optional.of(JsonRequestBPartnerUpsertItem
								   .builder()
								   .bpartnerIdentifier(bpartnerIdentifier)
								   .bpartnerComposite(jsonRequestComposite)
								   .build());
	}

	private Optional<JsonRequestBPartnerUpsertItem> mapNursingHome()
	{
		if (nursingHome == null)
		{
			return Optional.empty();
		}

		final JsonMetasfreshId actualMFBPartnerId = externalId2MetasfreshId.get(nursingHome.getId());
		final String bpartnerIdentifier = actualMFBPartnerId != null
				? String.valueOf(actualMFBPartnerId.getValue())
				: EXTERNAL_ID_PREFIX + nursingHome.getId();

		bPartnerIdentifier2RelationRole.put(bpartnerIdentifier, JsonBPRelationRole.NursingHome);

		final JsonRequestBPartner jsonRequestBPartner = new JsonRequestBPartner();
		jsonRequestBPartner.setExternalId(JsonExternalId.of(nursingHome.getId()));
		jsonRequestBPartner.setName(nursingHome.getName());
		jsonRequestBPartner.setPhone(nursingHome.getPhone());
		jsonRequestBPartner.setCustomer(true);
		// jsonRequestBPartner.setEmail(nursingService.getEmail()); //todo

		final JsonRequestLocation requestLocation = new JsonRequestLocation();
		requestLocation.setExternalId(JsonExternalId.of(nursingHome.getId()));
		requestLocation.setAddress1(nursingHome.getAddress());
		requestLocation.setCity(nursingHome.getCity());
		requestLocation.setPostal(nursingHome.getPostalCode());
		requestLocation.setCountryCode(COUNTRY_CODE_DE);
		requestLocation.setBillTo(true);
		requestLocation.setBillToDefault(true);
		requestLocation.setShipTo(true);
		requestLocation.setShipToDefault(true);

		final JsonRequestLocationUpsertItem jsonRequestLocationUpsertItem = JsonRequestLocationUpsertItem.builder()
				.locationIdentifier(EXTERNAL_ID_PREFIX + nursingHome.getId())
				.location(requestLocation)
				.build();

		final JsonRequestLocationUpsert jsonRequestLocationUpsert = JsonRequestLocationUpsert.builder()
				.requestItem(jsonRequestLocationUpsertItem)
				.build();

		final JsonExternalReferenceCreateRequest referenceCreateRequestOrNull = actualMFBPartnerId == null
				? createInsertExternalReferenceReq(nursingHome.getId())
				: null;

		final JsonRequestComposite jsonRequestComposite = JsonRequestComposite.builder()
				.bpartner(jsonRequestBPartner)
				.locations(jsonRequestLocationUpsert)
				.orgCode(orgCode)
				.bPartnerReferenceCreateRequest(referenceCreateRequestOrNull)
				.build();

		return Optional.of(JsonRequestBPartnerUpsertItem
								   .builder()
								   .bpartnerIdentifier(bpartnerIdentifier)
								   .bpartnerComposite(jsonRequestComposite)
								   .build());
	}

	private Optional<JsonRequestBPartnerUpsertItem> mapDoctor()
	{
		if (doctor == null)
		{
			return Optional.empty();
		}

		final JsonMetasfreshId actualMFBPartnerId = externalId2MetasfreshId.get(doctor.getId());
		final String bpartnerIdentifier = actualMFBPartnerId != null
				? String.valueOf(actualMFBPartnerId.getValue())
				: EXTERNAL_ID_PREFIX + doctor.getId();

		bPartnerIdentifier2RelationRole.put(bpartnerIdentifier, JsonBPRelationRole.PhysicianDoctor);

		final JsonRequestBPartner jsonRequestBPartner = new JsonRequestBPartner();
		jsonRequestBPartner.setExternalId(JsonExternalId.of(doctor.getId()));
		jsonRequestBPartner.setName(doctor.getFirstName() + " " + doctor.getLastName());
		jsonRequestBPartner.setPhone(doctor.getPhone());
		jsonRequestBPartner.setCustomer(true);
		// jsonRequestBPartner.setEmail(nursingService.getEmail()); //todo

		final JsonRequestContact requestContact = new JsonRequestContact();
		requestContact.setExternalId(JsonExternalId.of(doctor.getId()));
		requestContact.setFirstName(doctor.getFirstName());
		requestContact.setLastName(doctor.getLastName());
		requestContact.setPhone(doctor.getPhone());
		requestContact.setFax(doctor.getFax());

		final JsonRequestLocation requestLocation = new JsonRequestLocation();
		requestLocation.setExternalId(JsonExternalId.of(doctor.getId()));
		requestLocation.setAddress1(doctor.getAddress());
		requestLocation.setCity(doctor.getCity());
		requestLocation.setPostal(doctor.getPostalCode());
		requestLocation.setCountryCode(COUNTRY_CODE_DE);
		requestLocation.setBillTo(true);
		requestLocation.setBillToDefault(true);
		requestLocation.setShipTo(true);
		requestLocation.setShipToDefault(true);

		final JsonRequestLocationUpsertItem jsonRequestLocationUpsertItem = JsonRequestLocationUpsertItem.builder()
				.locationIdentifier(EXTERNAL_ID_PREFIX + doctor.getId())
				.location(requestLocation)
				.build();

		final JsonRequestLocationUpsert jsonRequestLocationUpsert = JsonRequestLocationUpsert.builder()
				.requestItem(jsonRequestLocationUpsertItem)
				.build();

		final JsonExternalReferenceCreateRequest referenceCreateRequestOrNull = actualMFBPartnerId == null
				? createInsertExternalReferenceReq(doctor.getId())
				: null;

		final JsonRequestComposite jsonRequestComposite = JsonRequestComposite.builder()
				.bpartner(jsonRequestBPartner)
				.locations(jsonRequestLocationUpsert)
				.orgCode(orgCode)
				.bPartnerReferenceCreateRequest(referenceCreateRequestOrNull)
				.build();

		return Optional.of(JsonRequestBPartnerUpsertItem
								   .builder()
								   .bpartnerIdentifier(bpartnerIdentifier)
								   .bpartnerComposite(jsonRequestComposite)
								   .build());
	}

	@NonNull
	private JsonExternalReferenceCreateRequest createInsertExternalReferenceReq(@NonNull final String externalId)
	{
		final JsonExternalReferenceLookupItem jsonExternalReferenceLookupItem = JsonExternalReferenceLookupItem.builder()
				.type(ESR_TYPE_BPARTNER)
				.id(externalId)
				.build();

		return JsonExternalReferenceCreateRequest.builder()
				.systemName(JsonExternalSystemName.of(ALBERTA_SYSTEM_NAME))
				.item(JsonExternalReferenceItem.of(jsonExternalReferenceLookupItem))
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
