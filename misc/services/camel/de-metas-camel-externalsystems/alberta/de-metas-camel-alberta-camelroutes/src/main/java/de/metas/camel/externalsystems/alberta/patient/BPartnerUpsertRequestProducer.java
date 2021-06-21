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

import de.metas.camel.externalsystems.alberta.common.DataMapper;
import de.metas.common.bpartner.v2.request.JsonRequestBPartner;
import de.metas.common.bpartner.v2.request.JsonRequestBPartnerUpsert;
import de.metas.common.bpartner.v2.request.JsonRequestBPartnerUpsertItem;
import de.metas.common.bpartner.v2.request.JsonRequestComposite;
import de.metas.common.bpartner.v2.request.JsonRequestContact;
import de.metas.common.bpartner.v2.request.JsonRequestContactUpsert;
import de.metas.common.bpartner.v2.request.JsonRequestContactUpsertItem;
import de.metas.common.bpartner.v2.request.JsonRequestLocation;
import de.metas.common.bpartner.v2.request.JsonRequestLocationUpsert;
import de.metas.common.bpartner.v2.request.alberta.JsonAlbertaBPartner;
import de.metas.common.bpartner.v2.request.alberta.JsonAlbertaContact;
import de.metas.common.bpartner.v2.request.alberta.JsonBPartnerRole;
import de.metas.common.bpartner.v2.request.alberta.JsonCompositeAlbertaBPartner;
import de.metas.common.bprelation.JsonBPRelationRole;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.SyncAdvise;
import io.swagger.client.model.CareGiver;
import io.swagger.client.model.Doctor;
import io.swagger.client.model.Hospital;
import io.swagger.client.model.NursingHome;
import io.swagger.client.model.NursingService;
import io.swagger.client.model.Patient;
import io.swagger.client.model.Payer;
import io.swagger.client.model.Pharmacy;
import io.swagger.client.model.Users;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.UUID;

import static de.metas.camel.externalsystems.alberta.common.AlbertaUtil.asInstant;
import static de.metas.camel.externalsystems.alberta.common.DataMapper.toJsonRequestLocationUpsert;
import static de.metas.camel.externalsystems.alberta.common.ExternalIdentifierFormat.formatExternalId;
import static de.metas.camel.externalsystems.alberta.patient.PatientToBPartnerMapper.billingAddressToContactUpsertItem;
import static de.metas.camel.externalsystems.alberta.patient.PatientToBPartnerMapper.deliveryAddressToContactUpsertItem;
import static de.metas.camel.externalsystems.alberta.patient.PatientToBPartnerMapper.mapBPartnerLocations;
import static de.metas.camel.externalsystems.alberta.patient.PatientToBPartnerMapper.patientToAlbertaComposite;
import static de.metas.camel.externalsystems.alberta.patient.PatientToBPartnerMapper.patientToContactUpsertItem;

public class BPartnerUpsertRequestProducer
{
	@NonNull
	private final String orgCode;
	@NonNull
	private final Patient patient;

	@Nullable
	private final JsonMetasfreshId rootBPartnerIdForUsers;
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
	@Nullable
	private final Users createdBy;
	@Nullable
	private final Users updatedBy;

	@NonNull
	private final Map<String, JsonBPRelationRole> bPartnerIdentifier2RelationRole;

	@NonNull
	private final BPartnerRequestProducerResult.BPartnerRequestProducerResultBuilder requestProducerResultBuilder;

	@Builder
	public BPartnerUpsertRequestProducer(
			@NonNull final String orgCode,
			@NonNull final Patient patient,
			@Nullable final JsonMetasfreshId rootBPartnerIdForUsers,
			@Nullable final Hospital hospital,
			@Nullable final NursingService nursingService,
			@Nullable final NursingHome nursingHome,
			@Nullable final Doctor doctor,
			@Nullable final Payer payer,
			@Nullable final Pharmacy pharmacy,
			@Nullable final Users createdBy,
			@Nullable final Users updatedBy)
	{
		if (patient.getId() == null)
		{
			throw new RuntimeException("Missing patient._id!");
		}

		this.rootBPartnerIdForUsers = rootBPartnerIdForUsers;
		this.hospital = hospital;
		this.nursingService = nursingService;
		this.nursingHome = nursingHome;
		this.doctor = doctor;
		this.orgCode = orgCode;
		this.patient = patient;
		this.payer = payer;
		this.pharmacy = pharmacy;
		this.bPartnerIdentifier2RelationRole = new HashMap<>();
		this.requestProducerResultBuilder = BPartnerRequestProducerResult.builder();
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
	}

	public BPartnerRequestProducerResult run()
	{
		final JsonRequestBPartnerUpsert.JsonRequestBPartnerUpsertBuilder upsertBPartnersRequest = JsonRequestBPartnerUpsert.builder()
				.syncAdvise(SyncAdvise.CREATE_OR_MERGE);

		requestProducerResultBuilder.patientMainAddressIdentifier(formatExternalId(patient.getId().toString()));

		mapCreatedUpdatedBy().ifPresent(upsertBPartnersRequest::requestItem);

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

		final JsonRequestBPartnerUpsertItem patientUpsertItem = mapPatientBPartner();
		upsertBPartnersRequest.requestItem(patientUpsertItem);

		return requestProducerResultBuilder
				.patientBPartnerIdentifier(patientUpsertItem.getBpartnerIdentifier())
				.jsonRequestBPartnerUpsert(upsertBPartnersRequest.build())
				.bPartnerIdentifier2RelationRole(bPartnerIdentifier2RelationRole)
				.build();
	}

	@NonNull
	public JsonRequestBPartnerUpsertItem mapPatientBPartner()
	{
		final String patientId = patient.getId().toString();

		final JsonRequestBPartner bPartner = new JsonRequestBPartner();
		bPartner.setName(patient.getFirstName() + " " + patient.getLastName());
		bPartner.setPhone(patient.getPhone());
		bPartner.setCustomer(true);
		bPartner.setMemo(patient.getComment());

		final String externalIdentifier = formatExternalId(patientId);
		bPartner.setCode(externalIdentifier);

		final JsonRequestContactUpsert.JsonRequestContactUpsertBuilder upsertContactBuilder = JsonRequestContactUpsert.builder()
				.requestItem(patientToContactUpsertItem(patient));

		billingAddressToContactUpsertItem(patientId, bPartner.getName(), patient.getBillingAddress())
				.ifPresent(upsertContactBuilder::requestItem);

		deliveryAddressToContactUpsertItem(patientId, bPartner.getName(), patient.getDeliveryAddress())
				.ifPresent(upsertContactBuilder::requestItem);

		final JsonRequestComposite upsertCompositeBPRequest = JsonRequestComposite.builder()
				.orgCode(orgCode)
				.bpartner(bPartner)
				.locations(mapBPartnerLocations(patient))
				.contacts(upsertContactBuilder.build())
				.compositeAlbertaBPartner(patientToAlbertaComposite(patient))
				.build();

		return JsonRequestBPartnerUpsertItem.builder()
				.bpartnerIdentifier(externalIdentifier)
				.bpartnerComposite(upsertCompositeBPRequest)
				.build();
	}

	@NonNull
	private JsonRequestBPartnerUpsertItem mapCareGiver(@NonNull final CareGiver careGiver)
	{
		final String careGiverId = Optional.ofNullable(careGiver.getId())
				.map(UUID::toString)
				.orElseThrow(() -> new RuntimeException("Missing careGiver._id!"));

		final String careGiverExternalIdentifier = formatExternalId(careGiverId);

		final var bPartner = new JsonRequestBPartner();
		bPartner.setName(careGiver.getFirstName() + " " + careGiver.getLastName());
		bPartner.setPhone(careGiver.getPhone());
		bPartner.setCustomer(true);
		bPartner.setCode(careGiverExternalIdentifier);

		final JsonRequestLocationUpsert locationUpsertRequest;
		{//location
			final JsonRequestLocation location = new JsonRequestLocation();
			location.setAddress1(careGiver.getAddress());
			location.setCity(careGiver.getCity());
			location.setCountryCode(GetPatientsRouteConstants.COUNTRY_CODE_DE);
			location.setPostal(careGiver.getPostalCode());

			locationUpsertRequest = toJsonRequestLocationUpsert(careGiverId, location);
		}

		final JsonRequestContactUpsert requestContactUpsert;
		{//contact
			final JsonRequestContact contact = new JsonRequestContact();
			contact.setName(new StringJoiner(" ").add(careGiver.getFirstName()).add(careGiver.getLastName()).toString());
			contact.setFirstName(careGiver.getFirstName());
			contact.setLastName(careGiver.getLastName());
			contact.setPhone(careGiver.getPhone());
			contact.setEmail(careGiver.getEmail());
			contact.setFax(careGiver.getFax());
			contact.setMobilePhone(careGiver.getMobilePhone());
			// contact.setLocationIdentifier(careGiverExternalIdentifier); // todo

			final JsonAlbertaContact albertaContact = new JsonAlbertaContact();
			albertaContact.setGender(careGiver.getGender());
			albertaContact.setTitle(careGiver.getTitle() != null ? careGiver.getTitle().toString() : null);
			albertaContact.setTimestamp(asInstant(careGiver.getTimestamp()));

			final JsonRequestContactUpsertItem contactUpsertItem = JsonRequestContactUpsertItem.builder()
					.contactIdentifier(careGiverExternalIdentifier)
					.contact(contact)
					.jsonAlbertaContact(albertaContact)
					.build();

			requestContactUpsert = JsonRequestContactUpsert.builder()
					.requestItem(contactUpsertItem)
					.build();
		}

		final JsonCompositeAlbertaBPartner compositeAlbertaBPartner;
		{// alberta composite
			final JsonAlbertaBPartner albertaBPartner = new JsonAlbertaBPartner();
			albertaBPartner.setTimestamp(asInstant(careGiver.getTimestamp()));
			albertaBPartner.setIsArchived(careGiver.isArchived());

			compositeAlbertaBPartner = JsonCompositeAlbertaBPartner.builder()
					.jsonAlbertaBPartner(albertaBPartner)
					.role(JsonBPartnerRole.Caregiver)
					.build();
		}

		bPartnerIdentifier2RelationRole.put(careGiverExternalIdentifier, JsonBPRelationRole.Caregiver);

		return JsonRequestBPartnerUpsertItem.builder()
				.bpartnerIdentifier(careGiverExternalIdentifier)
				.bpartnerComposite(JsonRequestComposite.builder()
										   .orgCode(orgCode)
										   .bpartner(bPartner)
										   .compositeAlbertaBPartner(compositeAlbertaBPartner)
										   .locations(locationUpsertRequest)
										   .contacts(requestContactUpsert)
										   .build())
				.build();
	}

	private Optional<JsonRequestBPartnerUpsertItem> mapHospital()
	{
		if (hospital == null)
		{
			return Optional.empty();
		}

		final JsonRequestBPartnerUpsertItem hospitalUpsertItem =
				DataMapper.mapHospitalToUpsertRequest(hospital, orgCode);

		bPartnerIdentifier2RelationRole.put(hospitalUpsertItem.getBpartnerIdentifier(), JsonBPRelationRole.Hospital);

		return Optional.of(hospitalUpsertItem);
	}

	private Optional<JsonRequestBPartnerUpsertItem> mapNursingService()
	{
		if (nursingService == null)
		{
			return Optional.empty();
		}

		final JsonRequestBPartnerUpsertItem nursingServiceUpsertItem =
				DataMapper.mapNursingServiceToUpsertRequest(nursingService, orgCode);

		bPartnerIdentifier2RelationRole.put(nursingServiceUpsertItem.getBpartnerIdentifier(), JsonBPRelationRole.NursingService);

		return Optional.of(nursingServiceUpsertItem);
	}

	private Optional<JsonRequestBPartnerUpsertItem> mapNursingHome()
	{
		if (nursingHome == null)
		{
			return Optional.empty();
		}

		final JsonRequestBPartnerUpsertItem nursingHomeUpsertItem =
				DataMapper.mapNursingHomeToUpsertRequest(nursingHome, orgCode);

		bPartnerIdentifier2RelationRole.put(nursingHomeUpsertItem.getBpartnerIdentifier(), JsonBPRelationRole.NursingHome);

		return Optional.of(nursingHomeUpsertItem);
	}

	private Optional<JsonRequestBPartnerUpsertItem> mapDoctor()
	{
		if (doctor == null)
		{
			return Optional.empty();
		}

		final JsonRequestBPartnerUpsertItem doctorUpsertRequest =
				DataMapper.mapDoctorToUpsertRequest(doctor, orgCode);

		bPartnerIdentifier2RelationRole.put(doctorUpsertRequest.getBpartnerIdentifier(), JsonBPRelationRole.PhysicianDoctor);

		return Optional.of(doctorUpsertRequest);
	}

	private Optional<JsonRequestBPartnerUpsertItem> mapPayer()
	{
		if (payer == null)
		{
			return Optional.empty();
		}

		final JsonRequestBPartnerUpsertItem payerUpsertItem =
				DataMapper.mapPayerToUpsertRequest(payer, orgCode);

		bPartnerIdentifier2RelationRole.put(payerUpsertItem.getBpartnerIdentifier(), JsonBPRelationRole.Payer);

		return Optional.of(payerUpsertItem);
	}

	private Optional<JsonRequestBPartnerUpsertItem> mapPharmacy()
	{
		if (pharmacy == null)
		{
			return Optional.empty();
		}

		final JsonRequestBPartnerUpsertItem pharmacyUpsertRequest =
				DataMapper.mapPharmacyToUpsertRequest(pharmacy, orgCode);

		bPartnerIdentifier2RelationRole.put(pharmacyUpsertRequest.getBpartnerIdentifier(), JsonBPRelationRole.Pharmacy);

		return Optional.of(pharmacyUpsertRequest);
	}

	private Optional<JsonRequestBPartnerUpsertItem> mapCreatedUpdatedBy()
	{
		if (rootBPartnerIdForUsers == null || (createdBy == null && updatedBy == null))
		{
			return Optional.empty();
		}

		final JsonRequestContactUpsert.JsonRequestContactUpsertBuilder requestContactUpsert = JsonRequestContactUpsert.builder();

		final Optional<JsonRequestContactUpsertItem> createdByContact = DataMapper.userToBPartnerContact(createdBy);
		createdByContact.ifPresent(requestContactUpsert::requestItem);

		DataMapper.userToBPartnerContact(updatedBy)
				.filter(updatedByContact -> createdByContact.isEmpty() || !updatedByContact.getContactIdentifier().equals(createdByContact.get().getContactIdentifier()))
				.ifPresent(requestContactUpsert::requestItem);

		final JsonRequestComposite jsonRequestComposite = JsonRequestComposite.builder()
				.contacts(requestContactUpsert.build())
				.build();

		return Optional.of(JsonRequestBPartnerUpsertItem
								   .builder()
								   .bpartnerIdentifier(String.valueOf(rootBPartnerIdForUsers.getValue()))
								   .bpartnerComposite(jsonRequestComposite)
								   .build());
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
