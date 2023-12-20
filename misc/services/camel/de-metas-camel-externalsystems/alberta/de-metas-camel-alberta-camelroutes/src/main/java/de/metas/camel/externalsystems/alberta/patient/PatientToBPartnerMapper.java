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

import com.google.common.collect.ImmutableList;
import de.metas.camel.externalsystems.alberta.common.AlbertaUtil;
import de.metas.camel.externalsystems.alberta.common.ExternalIdentifierFormat;
import de.metas.common.bpartner.v2.request.JsonRequestContact;
import de.metas.common.bpartner.v2.request.JsonRequestContactUpsertItem;
import de.metas.common.bpartner.v2.request.JsonRequestLocation;
import de.metas.common.bpartner.v2.request.JsonRequestLocationUpsert;
import de.metas.common.bpartner.v2.request.JsonRequestLocationUpsertItem;
import de.metas.common.bpartner.v2.request.alberta.JsonAlbertaBPartner;
import de.metas.common.bpartner.v2.request.alberta.JsonAlbertaCareGiver;
import de.metas.common.bpartner.v2.request.alberta.JsonAlbertaContact;
import de.metas.common.bpartner.v2.request.alberta.JsonAlbertaPatient;
import de.metas.common.bpartner.v2.request.alberta.JsonBPartnerRole;
import de.metas.common.bpartner.v2.request.alberta.JsonCompositeAlbertaBPartner;
import de.metas.common.util.EmptyUtil;
import io.swagger.client.model.CareGiver;
import io.swagger.client.model.Patient;
import io.swagger.client.model.PatientBillingAddress;
import io.swagger.client.model.PatientDeliveryAddress;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

import static de.metas.camel.externalsystems.alberta.common.AlbertaUtil.asInstant;
import static de.metas.camel.externalsystems.alberta.common.AlbertaUtil.asJavaLocalDate;
import static de.metas.camel.externalsystems.alberta.common.DataMapper.mapToAlbertaContact;
import static de.metas.camel.externalsystems.alberta.common.ExternalIdentifierFormat.formatBillingAddressExternalId;
import static de.metas.camel.externalsystems.alberta.common.ExternalIdentifierFormat.formatDeliveryAddressExternalId;
import static de.metas.camel.externalsystems.alberta.common.ExternalIdentifierFormat.formatExternalId;

public class PatientToBPartnerMapper
{
	@NonNull
	public static Optional<JsonRequestContactUpsertItem> billingAddressToContactUpsertItem(
			@NonNull final String patientId,
			@NonNull final String patientName,
			@Nullable final PatientBillingAddress patientBillingAddress)
	{
		if (patientBillingAddress == null)
		{
			return Optional.empty();
		}
		if (EmptyUtil.isBlank(patientBillingAddress.getName()))
		{
			return Optional.empty();
		}

		final String locationIdentifier = formatBillingAddressExternalId(patientId);

		final JsonRequestContact contact = new JsonRequestContact();

		final String computedName = EmptyUtil.isEmpty(patientBillingAddress.getName())
				? patientName : patientBillingAddress.getName();

		contact.setName(computedName);
		// contact.setLocationIdentifier(locationIdentifier); todo

		return Optional.of(JsonRequestContactUpsertItem.builder()
								   .contactIdentifier(locationIdentifier)
								   .contact(contact)
								   .jsonAlbertaContact(mapToAlbertaContact(patientBillingAddress.getTitle(), patientBillingAddress.getGender()))
								   .build());
	}

	@NonNull
	public static Optional<JsonRequestContactUpsertItem> deliveryAddressToContactUpsertItem(
			@NonNull final String patientId,
			@NonNull final String patientName,
			@Nullable final PatientDeliveryAddress patientDeliveryAddress)
	{
		if (patientDeliveryAddress == null)
		{
			return Optional.empty();
		}

		final String deliveryLocationIdentifier = formatDeliveryAddressExternalId(patientId);

		final JsonRequestContact contact = new JsonRequestContact();

		final String computedName = EmptyUtil.isEmpty(patientDeliveryAddress.getName())
				? patientName : patientDeliveryAddress.getName();

		contact.setName(computedName);
		// contact.setLocationIdentifier(deliveryLocationIdentifier); todo

		return Optional.of(JsonRequestContactUpsertItem.builder()
								   .contactIdentifier(deliveryLocationIdentifier)
								   .contact(contact)
								   .jsonAlbertaContact(mapToAlbertaContact(patientDeliveryAddress.getTitle(), patientDeliveryAddress.getGender()))
								   .build());
	}

	@NonNull
	public static JsonRequestContactUpsertItem patientToContactUpsertItem(@NonNull final Patient patient)
	{
		return JsonRequestContactUpsertItem.builder()
				.contactIdentifier(formatExternalId(patient.getId().toString()))
				.contact(patientToContact(patient))
				.jsonAlbertaContact(patientToAlbertaContact(patient))
				.build();
	}

	@NonNull
	public static JsonRequestLocationUpsert mapBPartnerLocations(@NonNull final Patient patient)
	{
		final JsonRequestLocationUpsert.JsonRequestLocationUpsertBuilder locationUpsertBuilder = JsonRequestLocationUpsert.builder();
		final String patientId = patient.getId().toString();

		locationUpsertBuilder.requestItem(getMainAddress(patient));

		if (patient.getBillingAddress() != null)
		{
			locationUpsertBuilder.requestItem(getBillingAddress(patientId, patient.getBillingAddress()));
		}

		if (patient.getDeliveryAddress() != null)
		{
			locationUpsertBuilder.requestItem(getShippingAddress(patientId, patient.getDeliveryAddress()));
		}

		return locationUpsertBuilder.build();
	}

	public static JsonCompositeAlbertaBPartner patientToAlbertaComposite(@NonNull final Patient patient)
	{
		final JsonCompositeAlbertaBPartner.JsonCompositeAlbertaBPartnerBuilder compositeAlbertaBPartner = JsonCompositeAlbertaBPartner.builder()
				.jsonAlbertaBPartner(patientToAlbertaBPartner(patient))
				.jsonAlbertaPatient(patientToPatientBPartner(patient))
				.role(JsonBPartnerRole.Patient);

		if (patient.getCareGivers() != null)
		{
			final List<JsonAlbertaCareGiver> albertaCareGiverList = patient.getCareGivers().stream()
					.map(PatientToBPartnerMapper::careGiverToBPartnerCareGiver)
					.collect(ImmutableList.toImmutableList());

			compositeAlbertaBPartner.jsonAlbertaCareGivers(albertaCareGiverList);
		}

		return compositeAlbertaBPartner.build();
	}

	@NonNull
	private static JsonRequestLocationUpsertItem getBillingAddress(@NonNull final String patientId, @NonNull final PatientBillingAddress patientBillingAddress)
	{
		final JsonRequestLocation location = new JsonRequestLocation();
		location.setName(patientBillingAddress.getAddress());
		location.setAddress1(patientBillingAddress.getAddress());
		location.setPostal(patientBillingAddress.getPostalCode());
		location.setCity(patientBillingAddress.getCity());
		location.setCountryCode(GetPatientsRouteConstants.COUNTRY_CODE_DE);
		location.setBpartnerName(patientBillingAddress.getName());
		location.setBillToDefault(true);
		location.setBillTo(true);

		return JsonRequestLocationUpsertItem.builder()
				.locationIdentifier(ExternalIdentifierFormat.formatBillingAddressExternalId(patientId))
				.location(location)
				.build();
	}

	@NonNull
	private static JsonRequestLocationUpsertItem getShippingAddress(@NonNull final String patientId, @NonNull final PatientDeliveryAddress patientDeliveryAddress)
	{
		final JsonRequestLocation location = new JsonRequestLocation();
		location.setName(patientDeliveryAddress.getAddress());
		location.setAddress1(patientDeliveryAddress.getAddress());
		location.setPostal(patientDeliveryAddress.getPostalCode());
		location.setCity(patientDeliveryAddress.getCity());
		location.setCountryCode(GetPatientsRouteConstants.COUNTRY_CODE_DE);
		location.setBpartnerName(patientDeliveryAddress.getName());
		location.setShipTo(true);
		location.setShipToDefault(true);

		return JsonRequestLocationUpsertItem.builder()
				.locationIdentifier(ExternalIdentifierFormat.formatMainShippingAddressExternalId(patientId))
				.location(location)
				.build();
	}

	@NonNull
	private static JsonRequestLocationUpsertItem getMainAddress(@NonNull final Patient patient)
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
		location.setCountryCode(GetPatientsRouteConstants.COUNTRY_CODE_DE);
		location.setBillToDefault(!hasExtraBillToAddress);
		location.setBillTo(!hasExtraBillToAddress);
		location.setShipToDefault(!hasExtraShipToAddress);
		location.setShipTo(!hasExtraShipToAddress);

		final String identifier = formatExternalId(patientId);

		return JsonRequestLocationUpsertItem.builder()
				.locationIdentifier(identifier)
				.location(location)
				.build();
	}

	@NonNull
	private static JsonAlbertaContact patientToAlbertaContact(@NonNull final Patient patient)
	{
		final JsonAlbertaContact albertaContact = mapToAlbertaContact(patient.getTitle(), patient.getGender());

		albertaContact.setTimestamp(asInstant(patient.getTimestamp()));

		return albertaContact;
	}

	@NonNull
	private static JsonAlbertaBPartner patientToAlbertaBPartner(@NonNull final Patient patient)
	{
		final JsonAlbertaBPartner albertaBPartner = new JsonAlbertaBPartner();
		albertaBPartner.setIsArchived(patient.isArchived());
		albertaBPartner.setTimestamp(AlbertaUtil.asInstant(patient.getTimestamp()));

		return albertaBPartner;
	}

	@NonNull
	private static JsonAlbertaPatient patientToPatientBPartner(@NonNull final Patient patient)
	{
		final JsonAlbertaPatient albertaPatient = new JsonAlbertaPatient();

		albertaPatient.setIsTransferPatient(patient.isChangeInSupplier());
		albertaPatient.setIVTherapy(patient.isIvTherapy());

		// deactivation
		final String deactivationReason;
		final BigDecimal reasonNumber = patient.getDeactivationReason();
		if (reasonNumber != null)
		{
			final boolean reasonNumberAboveFour = new BigDecimal("4").compareTo(reasonNumber) < 0;
			if (reasonNumberAboveFour)
			{
				deactivationReason = "4"; // FIXME this is a quick workaround
			}
			else
			{
				deactivationReason = reasonNumber.toString();
			}
		}
		else
		{
			deactivationReason = null;
		}
		albertaPatient.setDeactivationReason(deactivationReason);
		albertaPatient.setDeactivationComment(patient.getDeactivationComment());
		albertaPatient.setDeactivationDate(asJavaLocalDate(patient.getDeactivationDate()));

		albertaPatient.setCreatedAt(asInstant(patient.getCreatedAt()));
		albertaPatient.setUpdatedAt(asInstant(patient.getUpdatedAt()));

		albertaPatient.setCreatedByIdentifier(formatExternalId(patient.getCreatedBy()));
		albertaPatient.setUpdateByIdentifier(formatExternalId(patient.getUpdatedBy()));

		albertaPatient.setClassification(patient.getClassification());
		albertaPatient.setCareDegree(patient.getCareDegree());

		if (patient.getHospital() != null && patient.getHospital().getHospitalId() != null)
		{
			albertaPatient.setHospitalIdentifier(formatExternalId(patient.getHospital().getHospitalId()));
			albertaPatient.setDischargeDate(asJavaLocalDate(patient.getHospital().getDischargeDate()));
		}

		if (patient.getPayer() != null && patient.getPayer().getPayerId() != null)
		{
			albertaPatient.setPayerIdentifier(formatExternalId(patient.getPayer().getPayerId()));
			albertaPatient.setPayerType(patient.getPayer().getPayerType());
			albertaPatient.setNumberOfInsured(patient.getPayer().getNumberOfInsured());
			albertaPatient.setCopaymentFrom(asJavaLocalDate(patient.getPayer().getCopaymentFromDate()));
			albertaPatient.setCopaymentTo(asJavaLocalDate(patient.getPayer().getCopaymentToDate()));
		}

		return albertaPatient;
	}

	@NonNull
	private static JsonAlbertaCareGiver careGiverToBPartnerCareGiver(@NonNull final CareGiver careGiver)
	{
		final JsonAlbertaCareGiver albertaCareGiver = new JsonAlbertaCareGiver();

		albertaCareGiver.setCaregiverIdentifier(formatExternalId(careGiver.getId().toString()));
		albertaCareGiver.setIsLegalCarer(careGiver.isIsLegalCarer());
		albertaCareGiver.setType(careGiver.getType() != null ? String.valueOf(careGiver.getType()) : null);

		return albertaCareGiver;
	}

	@NonNull
	private static JsonRequestContact patientToContact(@NonNull final Patient patient)
	{
		final JsonRequestContact contact = new JsonRequestContact();
		contact.setName(new StringJoiner(" ").add(patient.getFirstName()).add(patient.getLastName()).toString());
		contact.setFirstName(patient.getFirstName());
		contact.setLastName(patient.getLastName());
		contact.setEmail(patient.getEmail());
		contact.setFax(patient.getFax());
		contact.setPhone(patient.getPhone());
		contact.setMobilePhone(patient.getMobilePhone());
		contact.setDefaultContact(true);
		contact.setBirthday(asJavaLocalDate(patient.getBirthday()));

		return contact;
	}
}
