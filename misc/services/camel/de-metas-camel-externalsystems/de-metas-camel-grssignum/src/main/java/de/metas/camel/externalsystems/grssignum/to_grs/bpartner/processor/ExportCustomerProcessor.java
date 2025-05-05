/*
 * #%L
 * de-metas-camel-grssignum
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.camel.externalsystems.grssignum.to_grs.bpartner.processor;

import com.google.common.collect.ImmutableList;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.camel.externalsystems.common.ProcessorHelper;
import de.metas.camel.externalsystems.grssignum.GRSSignumConstants;
import de.metas.camel.externalsystems.grssignum.to_grs.api.model.JsonCustomer;
import de.metas.camel.externalsystems.grssignum.to_grs.api.model.JsonCustomerContact;
import de.metas.camel.externalsystems.grssignum.to_grs.api.model.JsonCustomerLocation;
import de.metas.camel.externalsystems.grssignum.to_grs.bpartner.ExportBPartnerRouteContext;
import de.metas.camel.externalsystems.grssignum.to_grs.bpartner.ExportLocationHelper;
import de.metas.camel.externalsystems.grssignum.to_grs.client.model.DispatchRequest;
import de.metas.common.bpartner.v2.response.JsonResponseBPartner;
import de.metas.common.bpartner.v2.response.JsonResponseComposite;
import de.metas.common.bpartner.v2.response.JsonResponseContact;
import de.metas.common.bpartner.v2.response.JsonResponseGreeting;
import de.metas.common.bpartner.v2.response.JsonResponseLocation;
import de.metas.common.externalreference.v2.JsonExternalReferenceLookupResponse;
import de.metas.common.externalreference.v2.JsonExternalReferenceResponseItem;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.util.Check;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import static de.metas.camel.externalsystems.grssignum.GRSSignumConstants.CUSTOMER_FLAG;

public class ExportCustomerProcessor implements Processor
{
	public static void collectProductExternalReferences(@NonNull final Exchange exchange)
	{
		final ExportBPartnerRouteContext routeContext = ProcessorHelper.getPropertyOrThrowError(exchange, GRSSignumConstants.ROUTE_PROPERTY_EXPORT_BPARTNER_CONTEXT, ExportBPartnerRouteContext.class);

		final JsonExternalReferenceLookupResponse jsonExternalReferenceLookupResponse = exchange.getIn().getBody(JsonExternalReferenceLookupResponse.class);

		routeContext.setJsonExternalReferenceLookupResponse(jsonExternalReferenceLookupResponse);
	}

	@Override
	public void process(final Exchange exchange) throws Exception
	{
		final ExportBPartnerRouteContext routeContext = ProcessorHelper.getPropertyOrThrowError(exchange, GRSSignumConstants.ROUTE_PROPERTY_EXPORT_BPARTNER_CONTEXT, ExportBPartnerRouteContext.class);

		final JsonResponseComposite jsonResponseComposite = routeContext.getJsonResponseComposite();

		if (!jsonResponseComposite.getBpartner().isCustomer())
		{
			throw new RuntimeException("JsonResponseComposite must be a Customer! C_BPartner_Id=" + jsonResponseComposite.getBpartner().getMetasfreshId());
		}

		final JsonCustomer customer = toJsonCustomer(jsonResponseComposite, routeContext);

		final DispatchRequest dispatchRequest = DispatchRequest.builder()
				.url(routeContext.getRemoteUrl())
				.authToken(routeContext.getAuthToken())
				.request(JsonObjectMapperHolder.sharedJsonObjectMapper().writeValueAsString(customer))
				.build();

		exchange.getIn().setBody(dispatchRequest);
	}

	@NonNull
	private static JsonCustomer toJsonCustomer(@NonNull final JsonResponseComposite jsonResponseComposite, @NonNull final ExportBPartnerRouteContext routeContext)
	{
		final JsonResponseBPartner jsonResponseBPartner = jsonResponseComposite.getBpartner();

		final JsonMetasfreshId bpartnerMetasfreshId = jsonResponseBPartner.getMetasfreshId();

		final int inactive = jsonResponseBPartner.isActive() ? 0 : 1;

		return JsonCustomer.builder()
				.flag(CUSTOMER_FLAG)
				.id(bpartnerMetasfreshId)
				.bpartnerValue(jsonResponseBPartner.getCode())
				.tenantId(routeContext.getTenantId())
				.url(jsonResponseBPartner.getMetasfreshUrl())
				.name(jsonResponseBPartner.getName())
				.creditorId(jsonResponseBPartner.getCreditorId())
				.debtorId(jsonResponseBPartner.getDebtorId())
				.bpartnerDirPath(routeContext.getBPartnerBasePath())
				.inactive(inactive)
				.locations(toJsonCustomerLocations(jsonResponseComposite.getLocations()))
				.contacts(toJsonCustomerContacts(jsonResponseComposite.getContacts()))
				.bpartnerProductExternalReferences(toBPartnerProductExternalReferences(routeContext.getJsonExternalReferenceLookupResponse()))
				.build();
	}

	@NonNull
	private static List<JsonCustomerContact> toJsonCustomerContacts(@NonNull final List<JsonResponseContact> contacts)
	{
		return contacts.stream()
				.map(contact -> JsonCustomerContact.builder()
						.metasfreshId(contact.getMetasfreshId())
						.fullName(contact.getFirstName() + " " + contact.getLastName())
						.lastName(contact.getLastName())
						.firstName(contact.getFirstName())
						.greeting(Optional.ofNullable(contact.getGreeting())
										  .map(JsonResponseGreeting::getGreeting)
										  .orElse(null))
						.title(contact.getTitle())
						.position(contact.getPosition() == null ? null : contact.getPosition().getName())
						.email(contact.getEmail())
						.phone(contact.getPhone())
						.phone2(contact.getPhone2())
						.fax(contact.getFax())
						.inactive(contact.isActive() ? 0 : 1)
						.build())
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private static List<JsonCustomerLocation> toJsonCustomerLocations(@NonNull final List<JsonResponseLocation> locations)
	{
		final Optional<JsonMetasfreshId> mainAddressId = ExportLocationHelper.getBPartnerMainLocation(locations)
				.map(JsonResponseLocation::getMetasfreshId);

		final Function<JsonResponseLocation,Boolean> isMainAddress = (location) -> mainAddressId
				.map(addressId -> addressId.equals(location.getMetasfreshId())).orElse(false);

		return locations.stream()
				.map(location -> JsonCustomerLocation.builder()
						.metasfreshId(location.getMetasfreshId())
						.name(location.getName())
						.address1(location.getAddress1())
						.address2(location.getAddress2())
						.address3(location.getAddress3())
						.address4(location.getAddress4())
						.postal(location.getPostal())
						.city(location.getCity())
						.countryCode(location.getCountryCode())
						.gln(location.getGln())
						.inactive(location.isActive() ? 0 : 1)
						.shipTo(location.isShipTo())
						.billTo(location.isBillTo())
						.mainAddress(isMainAddress.apply(location) ? 1 : 0)
						.build())
				.collect(ImmutableList.toImmutableList());
	}

	@Nullable
	private static List<String> toBPartnerProductExternalReferences(@Nullable final JsonExternalReferenceLookupResponse jsonExternalReferenceLookupResponse)
	{
		if (jsonExternalReferenceLookupResponse == null || Check.isEmpty(jsonExternalReferenceLookupResponse.getItems()))
		{
			return null;
		}

		return jsonExternalReferenceLookupResponse
				.getItems()
				.stream()
				.map(JsonExternalReferenceResponseItem::getExternalReference)
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());
	}
}