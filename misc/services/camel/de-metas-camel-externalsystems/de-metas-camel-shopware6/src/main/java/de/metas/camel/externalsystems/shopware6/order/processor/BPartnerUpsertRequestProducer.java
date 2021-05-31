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

package de.metas.camel.externalsystems.shopware6.order.processor;

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
import de.metas.common.externalsystem.JsonExternalSystemShopware6ConfigMapping;
import de.metas.common.rest_api.v2.SyncAdvise;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.springframework.util.StringUtils;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.BILL_TO_SUFFIX;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.EXTERNAL_ID_PREFIX;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.SHIP_TO_SUFFIX;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.SHOPWARE6_SYSTEM_NAME;

@Value
public class BPartnerUpsertRequestProducer
{
	@NonNull
	String externalBPartnerId;

	@NonNull
	String orgCode;

	@NonNull
	ShopwareClient shopwareClient;

	@NonNull
	JsonOrderCustomer orderCustomer;

	@NonNull
	JsonOrderAddressAndCustomId shippingAddress;

	@NonNull
	String billingAddressId;

	@NonNull
	Map<String, String> countryIdToISOCode;

	@Nullable
	String bPartnerLocationIdentifierCustomPath;

	@NonNull
	BPartnerRequestProducerResult.BPartnerRequestProducerResultBuilder resultBuilder;

	@Nullable
	JsonExternalSystemShopware6ConfigMapping matchingShopware6Mapping;

	@Builder
	public BPartnerUpsertRequestProducer(
			@NonNull final String orgCode,
			@NonNull final ShopwareClient shopwareClient,
			@NonNull final JsonOrderCustomer orderCustomer,
			@NonNull final JsonOrderAddressAndCustomId shippingAddress,
			@NonNull final String billingAddressId,
			@Nullable final String bPartnerLocationIdentifierCustomPath,
			@NonNull final String externalBPartnerId,
			@Nullable final JsonExternalSystemShopware6ConfigMapping matchingShopware6Mapping)
	{
		this.orgCode = orgCode;
		this.shopwareClient = shopwareClient;
		this.orderCustomer = orderCustomer;
		this.shippingAddress = shippingAddress;
		this.billingAddressId = billingAddressId;
		this.bPartnerLocationIdentifierCustomPath = bPartnerLocationIdentifierCustomPath;
		this.externalBPartnerId = externalBPartnerId;
		this.matchingShopware6Mapping = matchingShopware6Mapping;
		this.countryIdToISOCode = new HashMap<>();
		this.resultBuilder = BPartnerRequestProducerResult.builder();
	}

	public BPartnerRequestProducerResult run()
	{
		final String customerBPartnerIdentifier = asExternalIdentifier(externalBPartnerId);

		final JsonRequestComposite.JsonRequestCompositeBuilder jsonRequestCompositeBuilder = JsonRequestComposite.builder()
				.orgCode(orgCode)
				.bpartner(getCustomerBPartnerRequest())
				.contacts(getUpsertContactRequest())
				.locations(getUpsertLocationsRequest());

		final JsonRequestBPartnerUpsertItem bPartnerUpsertItem = JsonRequestBPartnerUpsertItem.builder()
				.bpartnerIdentifier(customerBPartnerIdentifier)
				.bpartnerComposite(jsonRequestCompositeBuilder.build())
				.build();

		resultBuilder.jsonRequestBPartnerUpsert(JsonRequestBPartnerUpsert.builder()
														.syncAdvise(SyncAdvise.CREATE_OR_MERGE)
														.requestItem(bPartnerUpsertItem)
														.build());

		return resultBuilder.build();
	}

	@NonNull
	private JsonRequestBPartner getCustomerBPartnerRequest()
	{
		final JsonRequestBPartner jsonRequestBPartner = new JsonRequestBPartner();
		jsonRequestBPartner.setName(orderCustomer.getFirstName() + " " + orderCustomer.getLastName());
		jsonRequestBPartner.setCompanyName(orderCustomer.getCompany());
		jsonRequestBPartner.setCode(asExternalIdentifier(orderCustomer.getCustomerNumber()));
		jsonRequestBPartner.setCustomer(true);

		if (matchingShopware6Mapping != null)
		{
			jsonRequestBPartner.setSyncAdvise(matchingShopware6Mapping.getBPartnerSyncAdvice());
		}

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

		final Boolean isInvoiceEmailEnabled = matchingShopware6Mapping != null
				? matchingShopware6Mapping.getInvoiceEmailEnabled()
				: null;

		contactRequest.setInvoiceEmailEnabled(isInvoiceEmailEnabled);

		final SyncAdvise customBPartnerSyncAdvice = matchingShopware6Mapping != null
				? matchingShopware6Mapping.getBPartnerSyncAdvice()
				: null;

		return JsonRequestContactUpsert.builder()
				.requestItem(JsonRequestContactUpsertItem.builder()
									 .contactIdentifier(asExternalIdentifier(externalBPartnerId))
									 .contact(contactRequest)
									 .build())
				.syncAdvise(customBPartnerSyncAdvice)
				.build();
	}

	@NonNull
	private JsonRequestLocationUpsert getUpsertLocationsRequest()
	{
		final JsonRequestLocationUpsert.JsonRequestLocationUpsertBuilder upsertLocationsRequestBuilder = JsonRequestLocationUpsert.builder();
		upsertLocationsRequestBuilder.requestItem(getBillingLocationUpsertRequest());

		if (!billingAddressId.equals(shippingAddress.getJsonOrderAddress().getId()))
		{
			upsertLocationsRequestBuilder.requestItem(getUpsertLocationItemRequest(shippingAddress, false, true));
		}

		if (matchingShopware6Mapping != null)
		{
			upsertLocationsRequestBuilder.syncAdvise(matchingShopware6Mapping.getBPartnerLocationSyncAdvice());
		}

		return upsertLocationsRequestBuilder.build();
	}

	@NonNull
	private JsonRequestLocationUpsertItem getUpsertLocationItemRequest(
			@NonNull final JsonOrderAddressAndCustomId orderAddressWithCustomId,
			final boolean isBillingAddress,
			final boolean isShippingAddress)
	{
		final String bpLocationExternalId = getBpLocationIdentifier(orderAddressWithCustomId, isBillingAddress);

		if (isBillingAddress)
		{
			resultBuilder.billingBPartnerLocationExternalId(bpLocationExternalId);
		}
		if (isShippingAddress)
		{
			resultBuilder.shippingBPartnerLocationExternalId(bpLocationExternalId);
		}

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

		return JsonRequestLocationUpsertItem.builder()
				.locationIdentifier(asExternalIdentifier(bpLocationExternalId))
				.location(jsonRequestLocation)
				.build();
	}

	@NonNull
	private String getBpLocationIdentifier(@NonNull final JsonOrderAddressAndCustomId orderAddressWithCustomId,
			final boolean isBillingAddress)
	{

		return Optional.ofNullable(orderAddressWithCustomId.getCustomId())
				.orElseGet(() -> {
					final String suffix = isBillingAddress ? BILL_TO_SUFFIX : SHIP_TO_SUFFIX;

					return externalBPartnerId + suffix;
				});
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
	private JsonRequestLocationUpsertItem getBillingLocationUpsertRequest()
	{
		final boolean isSameAsShippingAddress = shippingAddress.getJsonOrderAddress().getId().equals(billingAddressId);

		final JsonOrderAddressAndCustomId billingAddress = isSameAsShippingAddress
				? shippingAddress
				: shopwareClient.getOrderAddressDetails(billingAddressId, bPartnerLocationIdentifierCustomPath)
				.orElseThrow(() -> new RuntimeException("Missing address details for addressId: " + billingAddressId));

		final boolean isBillingAddress = true;
		return getUpsertLocationItemRequest(billingAddress, isBillingAddress, isSameAsShippingAddress);
	}

	@NonNull
	private String asExternalIdentifier(@NonNull final String externalId)
	{
		return EXTERNAL_ID_PREFIX + "-" + SHOPWARE6_SYSTEM_NAME + "-" + externalId;
	}
}

