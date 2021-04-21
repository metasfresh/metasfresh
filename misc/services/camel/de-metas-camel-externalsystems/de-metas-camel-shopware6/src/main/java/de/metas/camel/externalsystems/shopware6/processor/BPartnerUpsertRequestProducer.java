/*
 * #%L
 * de-metas-camel-shopware6
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

package de.metas.camel.externalsystems.shopware6.processor;

import de.metas.camel.externalsystems.shopware6.api.ShopwareClient;
import de.metas.camel.externalsystems.shopware6.api.model.country.JsonCountry;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrderAddress;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrderAddressAndCustomId;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrderCustomer;
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
import de.metas.common.rest_api.v2.SyncAdvise;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.springframework.util.StringUtils;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.BILL_TO_SUFFIX;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.EXTERNAL_ID_PREFIX;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.SHIP_TO_SUFFIX;

@Value
public class BPartnerUpsertRequestProducer
{
	@NonNull
	String orgCode;

	@NonNull
	ShopwareClient shopwareClient;

	@NonNull
	JsonOrderCustomer orderCustomer;

	@NonNull
	List<JsonOrderAddressAndCustomId> shippingAddressList;

	@NonNull
	String billingAddressId;

	@NonNull
	Map<String, String> countryIdToISOCode;

	@Nullable
	String bPartnerLocationIdentifierCustomPath;

	@Nullable
	String bPartnerCustomIdentifier;

	@Builder
	public BPartnerUpsertRequestProducer(
			@NonNull final String orgCode,
			@NonNull final ShopwareClient shopwareClient,
			@NonNull final JsonOrderCustomer orderCustomer,
			@NonNull final List<JsonOrderAddressAndCustomId> shippingAddressList,
			@NonNull final String billingAddressId,
			@Nullable final String bPartnerLocationIdentifierCustomPath,
			@Nullable final String bPartnerCustomIdentifier)
	{
		this.orgCode = orgCode;
		this.shopwareClient = shopwareClient;
		this.orderCustomer = orderCustomer;
		this.shippingAddressList = shippingAddressList;
		this.billingAddressId = billingAddressId;
		this.bPartnerLocationIdentifierCustomPath = bPartnerLocationIdentifierCustomPath;
		this.bPartnerCustomIdentifier = bPartnerCustomIdentifier != null ? bPartnerCustomIdentifier : orderCustomer.getCustomerId();
		this.countryIdToISOCode = new HashMap<>();
	}

	public JsonRequestBPartnerUpsert run()
	{
		final String customerBPartnerIdentifier = EXTERNAL_ID_PREFIX + bPartnerCustomIdentifier;

		final JsonRequestComposite.JsonRequestCompositeBuilder jsonRequestCompositeBuilder = JsonRequestComposite.builder()
				.orgCode(orgCode)
				.bpartner(getCustomerBPartnerRequest())
				.contacts(getUpsertContactRequest())
				.locations(getUpsertLocationsRequest());

		final JsonRequestBPartnerUpsertItem bPartnerUpsertItem = JsonRequestBPartnerUpsertItem.builder()
				.bpartnerIdentifier(customerBPartnerIdentifier)
				.bpartnerComposite(jsonRequestCompositeBuilder.build())
				.build();

		return JsonRequestBPartnerUpsert.builder()
				.syncAdvise(SyncAdvise.CREATE_OR_MERGE)
				.requestItem(bPartnerUpsertItem)
				.build();
	}

	@NonNull
	private JsonRequestBPartner getCustomerBPartnerRequest()
	{
		final JsonRequestBPartner jsonRequestBPartner = new JsonRequestBPartner();
		jsonRequestBPartner.setName(orderCustomer.getFirstName() + " " + orderCustomer.getLastName());
		jsonRequestBPartner.setCompanyName(orderCustomer.getCompany());
		jsonRequestBPartner.setCode(EXTERNAL_ID_PREFIX + orderCustomer.getCustomerNumber());
		jsonRequestBPartner.setCustomer(true);
		// jsonRequestBPartner.setEmail(orderCustomer.getEmail()) todo

		return jsonRequestBPartner;
	}

	@NonNull
	private JsonRequestContactUpsert getUpsertContactRequest()
	{
		final JsonRequestContact contactRequest = new JsonRequestContact();
		contactRequest.setFirstName(orderCustomer.getFirstName());
		contactRequest.setLastName(orderCustomer.getLastName());
		contactRequest.setEmail(orderCustomer.getEmail());

		return JsonRequestContactUpsert.builder()
				.requestItem(JsonRequestContactUpsertItem.builder()
									 .contactIdentifier(EXTERNAL_ID_PREFIX + orderCustomer.getCustomerId())
									 .contact(contactRequest)
									 .build())
				.build();
	}

	@NonNull
	private JsonRequestLocationUpsert getUpsertLocationsRequest()
	{
		final JsonRequestLocationUpsert.JsonRequestLocationUpsertBuilder upsertLocationsRequestBuilder = JsonRequestLocationUpsert.builder();

		getBillingLocationUpsertRequest()
				.ifPresent(upsertLocationsRequestBuilder::requestItem);

		shippingAddressList.stream()
				.filter(shippingAddress -> !shippingAddress.getJsonOrderAddress().getId().equals(billingAddressId))
				.map(shippingAddress -> getUpsertLocationItemRequest(shippingAddress, false,
																	 true, shippingAddressList.indexOf(shippingAddress)))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.forEach(upsertLocationsRequestBuilder::requestItem);

		return upsertLocationsRequestBuilder.build();
	}

	@NonNull
	private Optional<JsonRequestLocationUpsertItem> getUpsertLocationItemRequest(
			@NonNull final JsonOrderAddressAndCustomId orderAddressWithCustomId,
			final boolean isBillingAddress,
			final boolean isShippingAddress,
			@Nullable final Integer locationIndex)
	{
		final StringBuilder bpLocationIdentifier = getBpLocationIdentifier(orderAddressWithCustomId, isBillingAddress, locationIndex);

		final JsonOrderAddress orderAddress = orderAddressWithCustomId.getJsonOrderAddress();

		final JsonRequestLocation jsonRequestLocation = new JsonRequestLocation();
		jsonRequestLocation.setCountryCode(getCountryCodeById(orderAddress.getCountryId()));
		jsonRequestLocation.setAddress1(orderAddress.getStreet());
		jsonRequestLocation.setAddress2(orderAddress.getAdditionalAddressLine1());
		jsonRequestLocation.setAddress3(orderAddress.getAdditionalAddressLine2());
		jsonRequestLocation.setCity(orderAddress.getCity());
		jsonRequestLocation.setPostal(orderAddress.getZipcode());
		jsonRequestLocation.setShipTo(isShippingAddress);
		jsonRequestLocation.setBillTo(isBillingAddress);
		//jsonRequestLocation.setPhoneNumber(orderAddress.getPhoneNumber()); todo

		return Optional.of(JsonRequestLocationUpsertItem.builder()
								   .locationIdentifier(bpLocationIdentifier.toString())
								   .location(jsonRequestLocation)
								   .build());
	}

	@NonNull
	private StringBuilder getBpLocationIdentifier(
			@NonNull final JsonOrderAddressAndCustomId orderAddressWithCustomId,
			final boolean isBillingAddress,
			@Nullable final Integer locationIndex)
	{
		final boolean customIdPresent = orderAddressWithCustomId.getCustomId() != null;
		final String bpLocationId = customIdPresent ? orderAddressWithCustomId.getCustomId() : orderCustomer.getCustomerId();

		final StringBuilder bpLocationIdentifier = new StringBuilder(EXTERNAL_ID_PREFIX + bpLocationId);

		if (!customIdPresent)
		{
			if (isBillingAddress)
			{
				bpLocationIdentifier.append(BILL_TO_SUFFIX);
			}
			else
			{
				bpLocationIdentifier.append(SHIP_TO_SUFFIX);
				if (locationIndex > 0)
				{
					bpLocationIdentifier.append(SHIP_TO_SUFFIX).append(locationIndex + 1);
				}
			}
		}

		return bpLocationIdentifier;
	}

	@NonNull
	private String getCountryCodeById(@NonNull final String countryId)
	{
		final String isoCountryCode = countryIdToISOCode.get(countryId);

		if (!StringUtils.isEmpty(isoCountryCode))
		{
			return isoCountryCode;
		}

		final String isoCode = shopwareClient.getCountryDetails(countryId)
				.map(JsonCountry::getIso)
				.orElseThrow(() -> new RuntimeException("Missing country code for countryId: " + countryId));

		countryIdToISOCode.put(countryId, isoCode);

		return isoCode;
	}

	@NonNull
	private Optional<JsonOrderAddressAndCustomId> getBillingAddress()
	{
		final JsonOrderAddressAndCustomId billingAddress = shopwareClient.getOrderAddressDetails(billingAddressId, bPartnerLocationIdentifierCustomPath)
				.orElseThrow(() -> new RuntimeException("Missing address details for addressId: " + billingAddressId));

		return Optional.of(billingAddress);
	}

	@NonNull
	private Optional<JsonRequestLocationUpsertItem> getBillingLocationUpsertRequest()
	{
		return getBillingAddress()
				.map(billingAddress -> {
					final boolean isAlsoShippingAddress = shippingAddressList.stream()
							.anyMatch(shippingAddress -> shippingAddress.getJsonOrderAddress().getId().equals(billingAddress.getJsonOrderAddress().getId()));

					final boolean isBillingAddress = true;
					return getUpsertLocationItemRequest(billingAddress, isBillingAddress, isAlsoShippingAddress, null);
				})
				.orElse(Optional.empty());
	}
}

