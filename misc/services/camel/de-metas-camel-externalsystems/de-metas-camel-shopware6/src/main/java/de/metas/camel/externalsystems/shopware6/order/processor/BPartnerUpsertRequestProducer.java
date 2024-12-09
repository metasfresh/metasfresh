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
<<<<<<< HEAD
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrderAddress;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrderCustomer;
import de.metas.camel.externalsystems.shopware6.api.model.order.OrderAddressDetails;
import de.metas.camel.externalsystems.shopware6.common.ExternalIdentifier;
import de.metas.camel.externalsystems.shopware6.common.ExternalIdentifierFormat;
=======
import de.metas.camel.externalsystems.shopware6.api.model.customer.JsonCustomerGroup;
import de.metas.camel.externalsystems.shopware6.api.model.order.AddressDetail;
import de.metas.camel.externalsystems.shopware6.api.model.order.Customer;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonAddress;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonCustomerBasicInfo;
import de.metas.camel.externalsystems.shopware6.common.ExternalIdentifier;
import de.metas.camel.externalsystems.shopware6.common.ExternalIdentifierFormat;
import de.metas.camel.externalsystems.shopware6.product.PriceListBasicInfo;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.camel.externalsystems.shopware6.salutation.SalutationInfoProvider;
import de.metas.common.bpartner.v2.request.JsonRequestBPartner;
import de.metas.common.bpartner.v2.request.JsonRequestBPartnerUpsert;
import de.metas.common.bpartner.v2.request.JsonRequestBPartnerUpsertItem;
import de.metas.common.bpartner.v2.request.JsonRequestComposite;
import de.metas.common.bpartner.v2.request.JsonRequestContact;
import de.metas.common.bpartner.v2.request.JsonRequestContactUpsert;
import de.metas.common.bpartner.v2.request.JsonRequestContactUpsert.JsonRequestContactUpsertBuilder;
import de.metas.common.bpartner.v2.request.JsonRequestContactUpsertItem;
import de.metas.common.bpartner.v2.request.JsonRequestLocation;
import de.metas.common.bpartner.v2.request.JsonRequestLocationUpsert;
import de.metas.common.bpartner.v2.request.JsonRequestLocationUpsert.JsonRequestLocationUpsertBuilder;
import de.metas.common.bpartner.v2.request.JsonRequestLocationUpsertItem;
import de.metas.common.externalsystem.JsonExternalSystemShopware6ConfigMapping;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.common.util.Check;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;

import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.BILL_TO_SUFFIX;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.SHIP_TO_SUFFIX;

/**
 * One instance of this class produces one {@link BPartnerRequestProducerResult}.
 */
@Value
public class BPartnerUpsertRequestProducer
{
	@Nullable
	ExternalIdentifier metasfreshId;

	@NonNull
	ExternalIdentifier userId;

	@NonNull
	String orgCode;

	@NonNull
	ShopwareClient shopwareClient;

	@NonNull
<<<<<<< HEAD
	JsonOrderCustomer orderCustomer;

	@NonNull
	OrderAddressDetails shippingAddress;

	@NonNull
	OrderAddressDetails billingAddress;

	@NonNull
=======
	Customer customerCandidate;

	@NonNull
	AddressDetail shippingAddress;

	@NonNull
	AddressDetail billingAddress;

	@Nullable
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	SalutationInfoProvider salutationInfoProvider;

	@NonNull
	Map<String, String> countryIdToISOCode;

<<<<<<< HEAD
	@Nullable
	String bPartnerLocationIdentifierCustomShopwarePath;

	@Nullable
	String bPartnerLocationIdentifierCustomMetasfreshPath;

	@Nullable
	String emailCustomPath;

=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	@NonNull
	BPartnerRequestProducerResult.BPartnerRequestProducerResultBuilder resultBuilder;

	@Nullable
	JsonExternalSystemShopware6ConfigMapping matchingShopware6Mapping;

<<<<<<< HEAD
	/**
	 * @param bPartnerLocationIdentifierCustomShopwarePath   if given, try to get a custom (permanent) shopware6-ID
	 *                                                       from the shopware-address JSON. Fail if there is no such C_BPartner_Location_ID
	 * @param bPartnerLocationIdentifierCustomMetasfreshPath if given, try to get the metasfresh C_BPartner_Location_ID
	 */
=======
	@Nullable
	PriceListBasicInfo priceListBasicInfo;

	@Nullable
	JsonCustomerGroup jsonCustomerGroup;

	boolean isDefaultAddress;

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	@Builder
	public BPartnerUpsertRequestProducer(
			@NonNull final String orgCode,
			@NonNull final ShopwareClient shopwareClient,
<<<<<<< HEAD
			@NonNull final JsonOrderCustomer orderCustomer,
			@NonNull final OrderAddressDetails shippingAddress,
			@NonNull final String billingAddressId,
			@NonNull final SalutationInfoProvider salutationInfoProvider,
			@Nullable final String bPartnerLocationIdentifierCustomShopwarePath,
			@Nullable final String bPartnerLocationIdentifierCustomMetasfreshPath,
			@Nullable final String emailCustomPath,
			@Nullable final ExternalIdentifier metasfreshId,
			@NonNull final ExternalIdentifier userId,
			@Nullable final JsonExternalSystemShopware6ConfigMapping matchingShopware6Mapping)
	{
		this.orgCode = orgCode;
		this.shopwareClient = shopwareClient;
		this.orderCustomer = orderCustomer;
		this.shippingAddress = shippingAddress;
		this.bPartnerLocationIdentifierCustomShopwarePath = bPartnerLocationIdentifierCustomShopwarePath;
		this.bPartnerLocationIdentifierCustomMetasfreshPath = bPartnerLocationIdentifierCustomMetasfreshPath;
		this.emailCustomPath = emailCustomPath;
=======
			@NonNull final Customer customerCandidate,
			@NonNull final AddressDetail shippingAddress,
			@NonNull final AddressDetail billingAddress,
			@Nullable final SalutationInfoProvider salutationInfoProvider,
			@Nullable final ExternalIdentifier metasfreshId,
			@NonNull final ExternalIdentifier userId,
			@Nullable final JsonExternalSystemShopware6ConfigMapping matchingShopware6Mapping,
			@Nullable final PriceListBasicInfo priceListBasicInfo,
			@Nullable final JsonCustomerGroup jsonCustomerGroup,
			final boolean isDefaultAddress)
	{
		this.orgCode = orgCode;
		this.shopwareClient = shopwareClient;
		this.customerCandidate = customerCandidate;
		this.shippingAddress = shippingAddress;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		this.metasfreshId = metasfreshId;
		this.userId = userId;
		this.matchingShopware6Mapping = matchingShopware6Mapping;
		this.salutationInfoProvider = salutationInfoProvider;
<<<<<<< HEAD
		this.countryIdToISOCode = new HashMap<>();
		this.resultBuilder = BPartnerRequestProducerResult.builder();
		this.billingAddress = retrieveBillingAddress(billingAddressId);
=======
		this.isDefaultAddress = isDefaultAddress;
		this.countryIdToISOCode = new HashMap<>();
		this.resultBuilder = BPartnerRequestProducerResult.builder();
		this.billingAddress = billingAddress;
		this.priceListBasicInfo = priceListBasicInfo;
		this.jsonCustomerGroup = jsonCustomerGroup;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	public BPartnerRequestProducerResult run()
	{
		final JsonRequestComposite.JsonRequestCompositeBuilder jsonRequestCompositeBuilder = JsonRequestComposite.builder()
				.orgCode(orgCode)
				.bpartner(getCustomerBPartnerRequest())
				.contacts(getUpsertContactRequest())
				.locations(getUpsertLocationsRequest());

		final JsonRequestBPartnerUpsertItem bPartnerUpsertItem = JsonRequestBPartnerUpsertItem.builder()
				.bpartnerIdentifier(getId().getIdentifier())
				.bpartnerComposite(jsonRequestCompositeBuilder.build())
				.build();

		resultBuilder.jsonRequestBPartnerUpsert(JsonRequestBPartnerUpsert.builder()
<<<<<<< HEAD
				.syncAdvise(SyncAdvise.CREATE_OR_MERGE)
				.requestItem(bPartnerUpsertItem)
				.build());
=======
														.syncAdvise(SyncAdvise.CREATE_OR_MERGE)
														.requestItem(bPartnerUpsertItem)
														.build());
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

		return resultBuilder.build();
	}

	@NonNull
	private ExternalIdentifier getId()
	{
		return CoalesceUtil.coalesce(metasfreshId, userId);
	}

	@NonNull
	private JsonRequestBPartner getCustomerBPartnerRequest()
	{
<<<<<<< HEAD
		final JsonRequestBPartner jsonRequestBPartner = new JsonRequestBPartner();
		jsonRequestBPartner.setName(orderCustomer.getFirstName() + " " + orderCustomer.getLastName());
		jsonRequestBPartner.setCompanyName(orderCustomer.getCompany());
		jsonRequestBPartner.setCode(ExternalIdentifierFormat.formatExternalId(orderCustomer.getCustomerNumber()));
		jsonRequestBPartner.setCustomer(true);

=======
		final JsonCustomerBasicInfo customer = customerCandidate.getCustomerBasicInfo();

		final JsonRequestBPartner jsonRequestBPartner = new JsonRequestBPartner();
		jsonRequestBPartner.setName(customer.getFirstName() + " " + customer.getLastName());
		jsonRequestBPartner.setCompanyName(customer.getCompany());
		jsonRequestBPartner.setCode(ExternalIdentifierFormat.formatExternalId(customer.getCustomerNumber()));
		jsonRequestBPartner.setCustomer(true);

		if (priceListBasicInfo != null)
		{
			jsonRequestBPartner.setPriceListId(priceListBasicInfo.getPriceListId());
		}

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		if (matchingShopware6Mapping != null)
		{
			jsonRequestBPartner.setSyncAdvise(matchingShopware6Mapping.getBPartnerSyncAdvice());
		}

<<<<<<< HEAD
=======
		if (jsonCustomerGroup != null)
		{
			jsonRequestBPartner.setGroup(jsonCustomerGroup.getName());
		}

		if (customer.getVatIdOrNull() != null)
		{
			jsonRequestBPartner.setVatId(customer.getVatIdOrNull());
		}

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		return jsonRequestBPartner;
	}

	@NonNull
	private JsonRequestLocationUpsert getUpsertLocationsRequest()
	{
		final JsonRequestLocationUpsertBuilder upsertLocationsRequestBuilder = JsonRequestLocationUpsert.builder();
		upsertLocationsRequestBuilder.requestItem(getBillingLocationUpsertRequest());

		if (!isBillingAddressSameAsShippingAddress())
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
<<<<<<< HEAD
			@NonNull final OrderAddressDetails orderAddressWithCustomId,
			final boolean isBillingAddress,
			final boolean isShippingAddress)
	{
		final String bpLocationExternalId = getBpLocationIdentifier(orderAddressWithCustomId, isBillingAddress);
=======
			@NonNull final AddressDetail addressWithCustomId,
			final boolean isBillingAddress,
			final boolean isShippingAddress)
	{
		final String bpLocationExternalId = getBpLocationIdentifier(addressWithCustomId, isBillingAddress);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

		if (isBillingAddress)
		{
			resultBuilder.billingBPartnerLocationExternalId(bpLocationExternalId);
		}
		if (isShippingAddress)
		{
			resultBuilder.shippingBPartnerLocationExternalId(bpLocationExternalId);
		}

<<<<<<< HEAD
		final JsonOrderAddress orderAddress = orderAddressWithCustomId.getJsonOrderAddress();
=======
		final JsonAddress orderAddress = addressWithCustomId.getJsonAddress();
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

		final JsonRequestLocation jsonRequestLocation = new JsonRequestLocation();
		jsonRequestLocation.setCountryCode(getCountryCodeById(orderAddress.getCountryId()));
		jsonRequestLocation.setAddress1(orderAddress.getStreet());
		jsonRequestLocation.setAddress2(orderAddress.getAdditionalAddressLine1());
		jsonRequestLocation.setAddress3(orderAddress.getAdditionalAddressLine2());
		jsonRequestLocation.setCity(orderAddress.getCity());
		jsonRequestLocation.setPostal(orderAddress.getZipcode());
		jsonRequestLocation.setShipTo(isShippingAddress);
		jsonRequestLocation.setBillTo(isBillingAddress);
		jsonRequestLocation.setBpartnerName(computeBPartnerName());
		jsonRequestLocation.setPhone(orderAddress.getPhoneNumber());
<<<<<<< HEAD
		jsonRequestLocation.setEmail(orderAddressWithCustomId.getCustomEmail());
=======
		jsonRequestLocation.setEmail(addressWithCustomId.getCustomEmail());

		if (isDefaultAddress)
		{
			jsonRequestLocation.setShipToDefault(isShippingAddress);
			jsonRequestLocation.setBillToDefault(isBillingAddress);
		}
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

		return JsonRequestLocationUpsertItem.builder()
				.locationIdentifier(bpLocationExternalId)
				.location(jsonRequestLocation)
				.build();
	}

	@Nullable
	private JsonRequestContactUpsert getUpsertContactRequest()
	{
		final JsonRequestContactUpsertBuilder upsertContactRequestBuilder = JsonRequestContactUpsert.builder();
<<<<<<< HEAD
		upsertContactRequestBuilder.requestItem(getUpsertContactItemRequest());

		if (matchingShopware6Mapping != null)
		{
			upsertContactRequestBuilder.syncAdvise(matchingShopware6Mapping.getBPartnerLocationSyncAdvice());
		}

=======

		// Only add the contact if there is no mapping OR if the mapping doesn't forbid to change the contact
		// Note that we use the bpartner's sync-advice because logically the contact and bpartner are one in the show, whereas there can be many addresses per bpartner
		final boolean addContactItem = matchingShopware6Mapping == null || !matchingShopware6Mapping.getBPartnerSyncAdvice().isLoadReadOnly();
		if (addContactItem)
		{
			upsertContactRequestBuilder.requestItem(getUpsertContactItemRequest());

			if (matchingShopware6Mapping != null)
			{
				upsertContactRequestBuilder.syncAdvise(matchingShopware6Mapping.getBPartnerSyncAdvice());
			}
		}
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		return upsertContactRequestBuilder.build();
	}

	@NonNull
	private JsonRequestContactUpsertItem getUpsertContactItemRequest()
	{
		final Boolean isInvoiceEmailEnabled = matchingShopware6Mapping != null
				? matchingShopware6Mapping.getInvoiceEmailEnabled()
				: null;

<<<<<<< HEAD
		final JsonRequestContact contactRequest = new JsonRequestContact();
		contactRequest.setFirstName(orderCustomer.getFirstName());
		contactRequest.setLastName(orderCustomer.getLastName());
		contactRequest.setEmail(orderCustomer.getEmail());
=======
		final JsonCustomerBasicInfo customer = customerCandidate.getCustomerBasicInfo();

		final JsonRequestContact contactRequest = new JsonRequestContact();
		contactRequest.setFirstName(customer.getFirstName());
		contactRequest.setLastName(customer.getLastName());
		contactRequest.setEmail(customer.getEmail());
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

		contactRequest.setBillToDefault(true);
		contactRequest.setShipToDefault(true);
		contactRequest.setInvoiceEmailEnabled(isInvoiceEmailEnabled);

		return JsonRequestContactUpsertItem.builder()
				.contactIdentifier(userId.getIdentifier())
				.contact(contactRequest)
				.build();
	}

	/**
	 * @param isBillingAddress only relevant if the custom IDs are blank
	 */
	@NonNull
	private String getBpLocationIdentifier(
<<<<<<< HEAD
			@NonNull final OrderAddressDetails orderAddressWithCustomId,
=======
			@NonNull final AddressDetail orderAddressWithCustomId,
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			final boolean isBillingAddress)
	{

		if (Check.isNotBlank(orderAddressWithCustomId.getCustomMetasfreshId()))
		{
			return orderAddressWithCustomId.getCustomMetasfreshId();
		}
		else if (Check.isNotBlank(orderAddressWithCustomId.getCustomShopwareId()))
		{
			return ExternalIdentifierFormat.formatExternalId(orderAddressWithCustomId.getCustomShopwareId());
		}

		final String suffix = isBillingAddress ? BILL_TO_SUFFIX : SHIP_TO_SUFFIX;
		return ExternalIdentifierFormat.formatExternalId(getId().getRawValue() + suffix);
	}

	@NonNull
	private String getCountryCodeById(@NonNull final String countryId)
	{
		final String isoCountryCode = countryIdToISOCode.get(countryId);

		if (!Check.isEmpty(isoCountryCode))
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
		return getUpsertLocationItemRequest(
				billingAddress,
				true /*isBillingAddress*/,
				isBillingAddressSameAsShippingAddress());
	}

<<<<<<< HEAD
	@NonNull
	private OrderAddressDetails retrieveBillingAddress(@NonNull final String billingAddressId)
	{
		return Objects.equals(shippingAddress.getJsonOrderAddress().getId(), billingAddressId)
				? shippingAddress
				: shopwareClient.getOrderAddressDetails(billingAddressId,
						bPartnerLocationIdentifierCustomShopwarePath,
						bPartnerLocationIdentifierCustomMetasfreshPath,
						emailCustomPath)
				.orElseThrow(() -> new RuntimeException("Missing address details for addressId: " + billingAddressId));
	}

	private boolean isBillingAddressSameAsShippingAddress()
	{
		return Objects.equals(shippingAddress.getJsonOrderAddress().getId(), billingAddress.getJsonOrderAddress().getId())
=======
	private boolean isBillingAddressSameAsShippingAddress()
	{
		return Objects.equals(shippingAddress.getJsonAddress().getId(), billingAddress.getJsonAddress().getId())
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
				|| (shippingAddress.getCustomMetasfreshId() != null && Objects.equals(shippingAddress.getCustomMetasfreshId(), billingAddress.getCustomMetasfreshId()));
	}

	@NonNull
	private String computeBPartnerName()
	{
		final BiFunction<String, String, String> prepareNameSegment = (segment, separator) -> Optional.ofNullable(segment)
				.map(StringUtils::trimBlankToNull)
				.map(s -> s + separator)
				.orElse("");

<<<<<<< HEAD
		return prepareNameSegment.apply(orderCustomer.getCompany(), ", ")
				+ prepareNameSegment.apply(orderCustomer.getFirstName(), " ")
				+ prepareNameSegment.apply(orderCustomer.getLastName(), "");
=======
		final JsonCustomerBasicInfo customer = customerCandidate.getCustomerBasicInfo();

		return prepareNameSegment.apply(customer.getCompany(), ", ")
				+ prepareNameSegment.apply(customer.getFirstName(), " ")
				+ prepareNameSegment.apply(customer.getLastName(), "");
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}
}

