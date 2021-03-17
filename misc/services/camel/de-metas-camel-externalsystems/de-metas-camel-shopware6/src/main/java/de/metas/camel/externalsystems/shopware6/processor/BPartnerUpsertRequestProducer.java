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

import com.google.common.collect.ImmutableMap;
import de.metas.camel.externalsystems.shopware6.api.ShopwareClient;
import de.metas.camel.externalsystems.shopware6.api.model.country.JsonCountry;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrderAddress;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrderCustomer;
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
import de.metas.common.externalreference.JsonExternalReferenceItem;
import de.metas.common.externalreference.JsonExternalReferenceLookupItem;
import de.metas.common.externalreference.JsonExternalReferenceLookupResponse;
import de.metas.common.externalreference.JsonSingleExternalReferenceCreateReq;
import de.metas.common.externalsystem.JsonExternalSystemName;
import de.metas.common.rest_api.JsonExternalId;
import de.metas.common.rest_api.JsonMetasfreshId;
import de.metas.common.rest_api.SyncAdvise;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.springframework.util.StringUtils;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.ESR_TYPE_BPARTNER;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.ESR_TYPE_BPARTNER_LOCATION;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.EXTERNAL_ID_PREFIX;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.SHOPWARE6_SYSTEM_NAME;

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
	List<JsonOrderAddress> shippingAddressList;

	@NonNull
	String billingAddressId;

	@NonNull
	String billingAddressVersion;

	@NonNull
	Map<JsonExternalReferenceLookupItem, JsonExternalReferenceItem> externalKey2ExternalRef;

	@NonNull
	Map<String, String> countryIdToISOCode;

	@Builder
	public BPartnerUpsertRequestProducer(
			@NonNull final String orgCode,
			@NonNull final ShopwareClient shopwareClient,
			@NonNull final JsonOrderCustomer orderCustomer,
			@NonNull final List<JsonOrderAddress> shippingAddressList,
			@NonNull final String billingAddressId,
			@NonNull final String billingAddressVersion,
			@NonNull final JsonExternalReferenceLookupResponse externalReferenceLookupResponse)
	{
		this.orgCode = orgCode;
		this.shopwareClient = shopwareClient;
		this.orderCustomer = orderCustomer;
		this.shippingAddressList = shippingAddressList;
		this.billingAddressId = billingAddressId;
		this.billingAddressVersion = billingAddressVersion;
		this.countryIdToISOCode = new HashMap<>();
		this.externalKey2ExternalRef = buildExternalRefByKeyMap(externalReferenceLookupResponse);
	}

	public JsonRequestBPartnerUpsert run()
	{
		final Optional<MetasfreshIdAndVersion> customerMFIdAndVersion = getMetasfreshIdAndVersion(orderCustomer.getCustomerId(), ESR_TYPE_BPARTNER);

		final String customerBPartnerIdentifier = customerMFIdAndVersion
				.map(metasIdAndVersion -> String.valueOf(metasIdAndVersion.getJsonMetasfreshId().getValue()))
				.orElseGet(() -> EXTERNAL_ID_PREFIX + orderCustomer.getCustomerId());

		final JsonRequestComposite.JsonRequestCompositeBuilder jsonRequestCompositeBuilder = JsonRequestComposite.builder()
				.orgCode(orgCode)
				.bpartner(getCustomerBPartnerRequest())
				.contacts(getUpsertContactRequest())
				.locations(getUpsertLocationsRequest());

		if (customerMFIdAndVersion.isEmpty())
		{
			jsonRequestCompositeBuilder.bPartnerReferenceCreateRequest(
					createInsertExternalReferenceReq(orderCustomer.getCustomerId(), ESR_TYPE_BPARTNER, null/*version*/));
		}

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
	private Map<JsonExternalReferenceLookupItem, JsonExternalReferenceItem> buildExternalRefByKeyMap(
			@NonNull final JsonExternalReferenceLookupResponse externalReferenceLookupResponse)
	{
		return externalReferenceLookupResponse.getItems()
				.stream()
				.collect(ImmutableMap.toImmutableMap(
						JsonExternalReferenceItem::getLookupItem,
						Function.identity()));
	}

	@NonNull
	private JsonRequestBPartner getCustomerBPartnerRequest()
	{
		final JsonRequestBPartner jsonRequestBPartner = new JsonRequestBPartner();
		jsonRequestBPartner.setExternalId(JsonExternalId.of(orderCustomer.getCustomerId()));
		jsonRequestBPartner.setName(orderCustomer.getFirstName() + " " + orderCustomer.getLastName());
		jsonRequestBPartner.setCompanyName(orderCustomer.getCompany());
		jsonRequestBPartner.setCode(orderCustomer.getCustomerNumber());
		jsonRequestBPartner.setCustomer(true);
		// jsonRequestBPartner.setEmail(orderCustomer.getEmail()) todo

		return jsonRequestBPartner;
	}

	@NonNull
	private JsonRequestContactUpsert getUpsertContactRequest()
	{
		final JsonRequestContact contactRequest = new JsonRequestContact();
		contactRequest.setExternalId(JsonExternalId.of(orderCustomer.getCustomerId()));
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
				.filter(shippingAddress -> !shippingAddress.getId().equals(billingAddressId))
				.map(shippingAddress -> getUpsertLocationItemRequest(shippingAddress, false, true))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.forEach(upsertLocationsRequestBuilder::requestItem);

		return upsertLocationsRequestBuilder.build();
	}

	@NonNull
	private Optional<JsonRequestLocationUpsertItem> getUpsertLocationItemRequest(
			@NonNull final JsonOrderAddress orderAddress,
			final boolean isBillingAddress,
			final boolean isShippingAddress)
	{
		final Optional<MetasfreshIdAndVersion> bPartnerLocationMFIdAndVersion = getMetasfreshIdAndVersion(orderAddress.getId(), ESR_TYPE_BPARTNER_LOCATION);

		final boolean isBPLocationAlreadyUpToDate = bPartnerLocationMFIdAndVersion
				.map(idAndVersion -> orderAddress.getVersionId().equals(idAndVersion.getVersion()))
				.orElse(Boolean.FALSE);

		if (isBPLocationAlreadyUpToDate)
		{
			return Optional.empty();
		}

		final String bpLocationIdentifier = bPartnerLocationMFIdAndVersion
				.map(idAndVersion -> String.valueOf(idAndVersion.getJsonMetasfreshId().getValue()))
				.orElseGet(() -> EXTERNAL_ID_PREFIX + orderAddress.getId());

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

		final JsonSingleExternalReferenceCreateReq createESRRequest = bPartnerLocationMFIdAndVersion.isPresent()
				? null
				: createInsertExternalReferenceReq(orderAddress.getId(), ESR_TYPE_BPARTNER_LOCATION, orderAddress.getVersionId());

		return Optional.of(JsonRequestLocationUpsertItem.builder()
								   .locationIdentifier(bpLocationIdentifier)
								   .location(jsonRequestLocation)
								   .locationExternalRef(createESRRequest)
								   .build());
	}

	@NonNull
	private Optional<MetasfreshIdAndVersion> getMetasfreshIdAndVersion(
			@NonNull final String externalId,
			@NonNull final String externalReferenceType)
	{
		final JsonExternalReferenceLookupItem externalReferenceLookupItem = JsonExternalReferenceLookupItem.builder()
				.type(externalReferenceType)
				.id(externalId)
				.build();

		return Optional.ofNullable(externalKey2ExternalRef.get(externalReferenceLookupItem))
				.map(externalReferenceItem -> externalReferenceItem.getMetasfreshId() == null
						? null
						: MetasfreshIdAndVersion.of(externalReferenceItem.getMetasfreshId(), externalReferenceItem.getVersion()));
	}

	@NonNull
	private JsonSingleExternalReferenceCreateReq createInsertExternalReferenceReq(
			@NonNull final String externalId,
			@NonNull final String externalReferenceType,
			@Nullable final String externalReferenceVersion)
	{
		final JsonExternalReferenceLookupItem jsonExternalReferenceLookupItem = JsonExternalReferenceLookupItem.builder()
				.type(externalReferenceType)
				.id(externalId)
				.build();

		final JsonExternalReferenceItem externalReferenceItem = JsonExternalReferenceItem.builder()
				.lookupItem(jsonExternalReferenceLookupItem)
				.version(externalReferenceVersion)
				.build();

		return JsonSingleExternalReferenceCreateReq.builder()
				.systemName(JsonExternalSystemName.of(SHOPWARE6_SYSTEM_NAME))
				.externalReferenceItem(externalReferenceItem)
				.build();
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
	private Optional<JsonOrderAddress> getBillingAddress()
	{
		final Optional<MetasfreshIdAndVersion> billingAddressMFIdAndVersion = getMetasfreshIdAndVersion(billingAddressId, ESR_TYPE_BPARTNER_LOCATION);

		final boolean isBillingAddressAlreadyUpToDate = billingAddressMFIdAndVersion
				.map(bpBillingMFIdAndVersion -> billingAddressVersion.equals(bpBillingMFIdAndVersion.getVersion()))
				.orElse(Boolean.FALSE);

		if (isBillingAddressAlreadyUpToDate)
		{
			return Optional.empty();
		}

		final JsonOrderAddress billingAddress = shopwareClient.getOrderAddressDetails(billingAddressId)
				.orElseThrow(() -> new RuntimeException("Missing address details for addressId: " + billingAddressId));

		return Optional.of(billingAddress);
	}

	@NonNull
	private Optional<JsonRequestLocationUpsertItem> getBillingLocationUpsertRequest()
	{
		return getBillingAddress()
				.map(billingAddress -> {
					final boolean isAlsoShippingAddress = shippingAddressList.stream()
							.anyMatch(shippingAddress -> shippingAddress.getId().equals(billingAddress.getId()));

					final boolean isBillingAddress = true;
					return getUpsertLocationItemRequest(billingAddress, isBillingAddress, isAlsoShippingAddress);
				})
				.orElse(Optional.empty());
	}

	@Value
	private static class MetasfreshIdAndVersion
	{
		@NonNull
		JsonMetasfreshId jsonMetasfreshId;

		@Nullable
		String version;

		private static MetasfreshIdAndVersion of(@NonNull final JsonMetasfreshId metasfreshId, @Nullable final String version)
		{
			return new MetasfreshIdAndVersion(metasfreshId, version);
		}
	}
}

