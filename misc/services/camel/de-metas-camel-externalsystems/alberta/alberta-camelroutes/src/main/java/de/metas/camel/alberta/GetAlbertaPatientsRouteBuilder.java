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

import com.google.common.collect.ImmutableList;
import de.metas.camel.externalsystems.common.BPRelationsCamelRequest;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
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
import de.metas.common.bpartner.response.JsonResponseBPartnerCompositeUpsert;
import de.metas.common.bprelation.JsonBPRelationRole;
import de.metas.common.bprelation.request.JsonRequestBPRelationTarget;
import de.metas.common.bprelation.request.JsonRequestBPRelationsUpsert;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import io.swagger.client.ApiClient;
import io.swagger.client.api.PatientApi;
import io.swagger.client.model.CareGiver;
import io.swagger.client.model.Patient;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetAlbertaPatientsRouteBuilder extends RouteBuilder
{

	@Override
	public void configure()
	{
		from("direct:Alberta-GetPatients") // this EP's name is matching the JsonExternalSystemRequest's ExternalSystem and Command

				// call the API
				.process(exchange -> {

					final var request = exchange.getIn().getBody(JsonExternalSystemRequest.class);
					final var apiKey = request.getParameters().get(ExternalSystemConstants.PARAM_API_KEY);
					final var tenant = request.getParameters().get(ExternalSystemConstants.PARAM_TENANT);
					final var updatedAfter = request.getParameters().get(ExternalSystemConstants.PARAM_UPDATED_AFTER);
					final var basePath = request.getParameters().get(ExternalSystemConstants.PARAM_BASE_PATH);

					final var apiClient = new ApiClient().setBasePath(basePath);

					final var patientApi = new PatientApi(apiClient);
					final var patients = patientApi.getCreatedPatients(apiKey, tenant, "created", updatedAfter);
					patients.addAll(patientApi.getCreatedPatients(apiKey, tenant, "updated", updatedAfter));

					exchange.getIn().setHeader(ExternalSystemCamelConstants.HEADER_ORG_CODE, request.getOrgCode());
					exchange.getIn().setBody(patients);
				})
				.split(body()) // from here we have 1 message per patient

				// create bpartners in metasfresh
				.process(exchange -> {

					final var patient = exchange.getIn().getBody(Patient.class);
					final var orgCode = exchange.getIn().getHeader(ExternalSystemCamelConstants.HEADER_ORG_CODE, String.class);
					final BPartners bpartners = createPartientAndCareTaker(orgCode, patient);

					exchange.getIn().setHeader("patientBPartners", bpartners);
					exchange.getIn().setBody(bpartners.toUpsertRequest());
				})
				.to("{{" + ExternalSystemCamelConstants.MF_UPSERT_BPARTNER_CAMEL_URI + "}}") // no need to worry about metasfresh-URLs, API-Keys etc

				// create bpartner-relations in metasfresh
				.process(exchange -> {

					final var orgCode = exchange.getIn().getHeader(ExternalSystemCamelConstants.HEADER_ORG_CODE, String.class);
					final var bPartners = exchange.getIn().getHeader("patientBPartners", BPartners.class);

					final var relationsUpsertBuilder = JsonRequestBPRelationsUpsert.builder()
							.orgCode(orgCode);

					final var relatesTo = ImmutableList.<JsonRequestBPRelationTarget>builder();
					for (final var careTaker : bPartners.getCareTakers())
					{
						final var locationIdentifier = careTaker.getBpartnerComposite().getLocationsNotNull()
								.getRequestItems().get(0).getLocationIdentifier();
						
						relatesTo.add(JsonRequestBPRelationTarget.builder()
								.name("name") // TODO do we really need this to be mandatory?
								.role(JsonBPRelationRole.CareTaker)
								.targetBpartnerIdentifier(careTaker.getBpartnerIdentifier())
								.targetLocationIdentifier(locationIdentifier)
								.build());
					}
					relationsUpsertBuilder.relatesTo(relatesTo.build());

					final var bpRelationsCamelRequest = BPRelationsCamelRequest.builder()
							.bpartnerIdentifier(bPartners.getPatient().getBpartnerIdentifier())
							.jsonRequestBPRelationsUpsert(relationsUpsertBuilder.build())
							.build();
					exchange.getIn().setBody(bpRelationsCamelRequest);
				})
				.to("{{" + ExternalSystemCamelConstants.MF_UPSERT_BPRELATION_CAMEL_URI + "}}")

		/* TODO - the rest
		 - also sync their external references (insert or just validate if already existing)
		 - collect the included `alberta-references` to hospitals, doctors etc
		   - :bulb: here, the messages from alberta are aggregated into one message with all the external references
		 - create a bulk-request to `api/externalReference`
		   - :bulb: the response is again one message;
		   - with the response
		     - create maps of `alberta-reference` => `metasfresh-id` or `alberta-reference` => `MISSING`, for each type (hospital, nursing-home etc)
		     - create split-messages of all `alberta-reference` => `MISSING` map-entries
		     - route each message to the right sub-route (for doctor, insurance etc)
		     - there
		       - invoke the Alberta-API for each missing item
		       - create the corresponding BPartner in metasfresh
		         - :bulb: use the metasfresh-response to create a message that contains `alberta-reference` => `metasfresh-id`
		     - wait for all "split messages to be "done" and reaggregate them into one message with all the `alberta-reference` => `metasfresh-id` mappings ("Composed Message Processor"?)
		 - with the now completed `alberta-reference` => `metasfresh-id` map create the proper bpartner-relations in metasfresh.
		 */
		;

	}

	private BPartners createPartientAndCareTaker(@NonNull final String orgCode, @NonNull final Patient patient)
	{
		final var result = BPartners.builder();

		result.patient(createPatientBPartner(orgCode, patient));

		for (final CareGiver careGiver : patient.getCareGivers())
		{
			result.careTaker(createCareGiverBPartner(orgCode, careGiver));
		}

		return result.build();
	}

	private JsonRequestBPartnerUpsertItem createPatientBPartner(final String orgCode, final Patient patient)
	{
		final var bPartner = new JsonRequestBPartner();
		bPartner.setName(patient.getFirstName() + " " + patient.getLastName());
		//bPartner.setMemo(patient.getComment()); TODO
		bPartner.setCustomer(true);

		final var hasExtraBillToAddress = patient.getBillingAddress() != null;
		final var hasExtraShipToAddress = patient.getDeliveryAddress() != null;

		final var locationUpsertBuilder = JsonRequestLocationUpsert.builder();

		// "normal" address
		{
			final var location = new JsonRequestLocation();
			location.setAddress1(patient.getAddress());
			location.setAddress2(patient.getAdditionalAddress());
			location.setAddress3(patient.getAdditionalAddress2());
			location.setPostal(patient.getPostalCode());
			location.setCity(patient.getCity());
			location.setBillToDefault(!hasExtraBillToAddress);
			location.setBillTo(!hasExtraBillToAddress);
			location.setShipToDefault(!hasExtraShipToAddress);
			location.setShipTo(!hasExtraShipToAddress);

			locationUpsertBuilder.requestItem(JsonRequestLocationUpsertItem.builder()
					.locationIdentifier("ext-" + patient.getId().toString())
					.location(location).build());
		}
		if (hasExtraBillToAddress)
		{
			final var billingAddress = patient.getBillingAddress();
			final var location = new JsonRequestLocation();
			location.setAddress1(billingAddress.getAddress());
			location.setPostal(billingAddress.getPostalCode());
			location.setCity(billingAddress.getCity());
			location.setBpartnerName((billingAddress.getTitle() + " " + billingAddress.getName()).trim());
			location.setBillToDefault(true);
			location.setBillTo(true);

			locationUpsertBuilder.requestItem(JsonRequestLocationUpsertItem.builder()
					.locationIdentifier("ext-billingAddress_" + patient.getId().toString())
					.location(location).build());
		}
		if (hasExtraShipToAddress)
		{
			final var deliveryAddress = patient.getDeliveryAddress();
			final var location = new JsonRequestLocation();
			location.setAddress1(deliveryAddress.getAddress());
			location.setPostal(deliveryAddress.getPostalCode());
			location.setCity(deliveryAddress.getCity());
			location.setBpartnerName((deliveryAddress.getTitle() + " " + deliveryAddress.getName()).trim());
			location.setBillToDefault(true);
			location.setBillTo(true);

			locationUpsertBuilder.requestItem(JsonRequestLocationUpsertItem.builder()
					.locationIdentifier("ext-deliveryAddress_" + patient.getId().toString())
					.location(location).build());
		}

		final var contact = new JsonRequestContact();
		// contact.setTitle(patient.getTitle()); TODO
		contact.setFirstName(patient.getFirstName());
		contact.setLastName(patient.getLastName());
		//contact.setBirthDay(patient.getBirthday()); TODO
		contact.setEmail(patient.getEmail());
		contact.setFax(patient.getFax());
		contact.setPhone(patient.getPhone());
		contact.setMobilePhone(patient.getMobilePhone());
		contact.setDefaultContact(true);

		// patient.getGender(); TODO needed?

		return JsonRequestBPartnerUpsertItem.builder()
				.bpartnerIdentifier("ext-" + patient.getId().toString())
				.bpartnerComposite(JsonRequestComposite.builder()
						.orgCode(orgCode)
						.bpartner(bPartner)
						.locations(locationUpsertBuilder.build())
						.contacts(JsonRequestContactUpsert.builder().requestItem(JsonRequestContactUpsertItem.builder()
								.contactIdentifier("ext-" + patient.getId().toString())
								.contact(contact)
								.build())
								.build())
						.build())
				.build();
	}

	private JsonRequestBPartnerUpsertItem createCareGiverBPartner(@NonNull final String orgCode, @NonNull final CareGiver careGiver)
	{
		final var bPartner = new JsonRequestBPartner();
		bPartner.setName(careGiver.getFirstName() + " " + careGiver.getLastName());
		//bPartner.setMemo(patient.getComment()); TODO
		bPartner.setCustomer(true);

		final var location = new JsonRequestLocation();
		location.setAddress1(careGiver.getAddress());
		location.setPostal(careGiver.getPostalCode());
		location.setCity(careGiver.getCity());

		final var contact = new JsonRequestContact();
		// contact.setTitle(patient.getTitle()); TODO
		contact.setFirstName(careGiver.getFirstName());
		contact.setLastName(careGiver.getLastName());
		contact.setEmail(careGiver.getEmail());
		contact.setFax(careGiver.getFax());
		contact.setPhone(careGiver.getPhone());
		contact.setMobilePhone(careGiver.getMobilePhone());
		contact.setDefaultContact(true);

		return JsonRequestBPartnerUpsertItem.builder()
				.bpartnerIdentifier("ext-" + careGiver.getId().toString())
				.bpartnerComposite(JsonRequestComposite.builder()
						.orgCode(orgCode)
						.bpartner(bPartner)
						.locations(JsonRequestLocationUpsert.builder().requestItem(JsonRequestLocationUpsertItem.builder()
								.locationIdentifier("ext-" + careGiver.getId().toString())
								.location(location).build()).build())
						.contacts(JsonRequestContactUpsert.builder().requestItem(JsonRequestContactUpsertItem.builder()
								.contactIdentifier("ext-" + careGiver.getId().toString())
								.contact(contact)
								.build())
								.build())
						.build()).build();
	}

	@Builder
	@Value
	private static class BPartners
	{
		@NonNull
		JsonRequestBPartnerUpsertItem patient;

		@Singular
		List<JsonRequestBPartnerUpsertItem> careTakers;

		public JsonRequestBPartnerUpsert toUpsertRequest()
		{
			return JsonRequestBPartnerUpsert.builder().requestItem(patient).requestItems(careTakers).build();
		}
	}
}
