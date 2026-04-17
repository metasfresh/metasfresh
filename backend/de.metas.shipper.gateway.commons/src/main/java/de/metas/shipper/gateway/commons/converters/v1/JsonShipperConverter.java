/*
 * #%L
 * de.metas.shipper.gateway.commons
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.shipper.gateway.commons.converters.v1;

import com.google.common.collect.ImmutableList;
import de.metas.common.delivery.v1.json.JsonAddress;
import de.metas.common.delivery.v1.json.JsonContact;
import de.metas.common.delivery.v1.json.JsonMoney;
import de.metas.common.delivery.v1.json.JsonPackageDimensions;
import de.metas.common.delivery.v1.json.JsonQuantity;
import de.metas.common.delivery.v1.json.request.JsonCarrierService;
import de.metas.common.delivery.v1.json.request.JsonDeliveryOrderLineContents;
import de.metas.common.delivery.v1.json.request.JsonDeliveryOrderParcel;
import de.metas.common.delivery.v1.json.request.JsonDeliveryRequest;
import de.metas.common.delivery.v1.json.request.JsonGoodsType;
import de.metas.common.delivery.v1.json.request.JsonMappingConfig;
import de.metas.common.delivery.v1.json.request.JsonMappingConfigList;
import de.metas.common.delivery.v1.json.request.JsonShipperConfig;
import de.metas.common.delivery.v1.json.request.JsonShipperProduct;
import de.metas.common.util.Check;
import de.metas.currency.CurrencyRepository;
import de.metas.inoutcandidate.CarrierGoodsType;
import de.metas.inoutcandidate.CarrierService;
import de.metas.product.PackageDimensions;
import de.metas.shipper.gateway.commons.mapping.ShipperMappingConfig;
import de.metas.shipper.gateway.commons.mapping.ShipperMappingConfigList;
import de.metas.shipper.gateway.commons.model.CarrierProduct;
import de.metas.shipper.gateway.commons.model.CarrierProductRepository;
import de.metas.shipper.gateway.commons.model.ShipperConfig;
import de.metas.shipper.gateway.spi.DeliveryOrderId;
import de.metas.shipper.gateway.spi.model.Address;
import de.metas.shipper.gateway.spi.model.ContactPerson;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.DeliveryOrderItem;
import de.metas.shipper.gateway.spi.model.DeliveryOrderParcel;
import de.metas.shipper.gateway.spi.model.PickupDate;
import de.metas.shipper.gateway.spi.model.ShipperProduct;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@RequiredArgsConstructor
public class JsonShipperConverter
{
	@NonNull private final CarrierProductRepository carrierProductRepository;
	@NonNull private final CurrencyRepository currencyRepository;

	public JsonDeliveryRequest toJson(@NonNull final ShipperConfig config, @NonNull final DeliveryOrder order, @NonNull final ShipperMappingConfigList mappingConfigs)
	{
		final PickupDate pickupDate = order.getPickupDate();
		return JsonDeliveryRequest.builder()
				.deliveryOrderId(DeliveryOrderId.toRepoId(order.getId()))
				.pickupAddress(toJsonAddress(order.getPickupAddress()))
				.pickupContact(toJsonContactOrNull(order.getPickupContact()))
				.pickupDate(pickupDate.getDate().toString())
				.timeFrom(pickupDate.getTimeFrom().toString())
				.timeTo(pickupDate.getTimeTo().toString())
				.pickupNote(order.getPickupNote())
				.deliveryAddress(toJsonAddress(order.getDeliveryAddress()))
				.deliveryContact(toJsonContactOrNull(order.getDeliveryContact()))
				.deliveryDate(order.getDeliveryDate() != null ? order.getDeliveryDate().getDate().toString() : null)
				.deliveryNote(order.getDeliveryNote())
				.customerReference(order.getCustomerReference())
				.deliveryOrderParcels(order.getDeliveryOrderParcels().stream().map(this::toJsonDeliveryOrderLine).collect(ImmutableList.toImmutableList()))
				.shipperProduct(toJsonShipperProductOrNull(order.getShipperProduct()))
				.shipperEORI(order.getShipperEORI())
				.receiverEORI(order.getReceiverEORI())
				.shipperConfig(toJsonShipperConfig(config))
				.goodsType(toJsonGoodsType(order.getGoodsType()))
				.services(toCarrierServices(order.getServices()))
				.mappingConfigs(toJsonMappingConfigList(mappingConfigs))
				.build();
	}

	@Nullable
	private JsonShipperProduct toJsonShipperProductOrNull(@Nullable final ShipperProduct shipperProduct)
	{
		if (shipperProduct == null)
		{
			return null;
		}
		return JsonShipperProduct.builder()
				.code(shipperProduct.getCode())
				.name(shipperProduct.getName())
				.build();
	}

	@Nullable
	private JsonGoodsType toJsonGoodsType(@Nullable final CarrierGoodsType goodsType)
	{
		if (goodsType == null)
		{
			return null;
		}
		return JsonGoodsType.builder()
				.id(goodsType.getExternalId())
				.name(goodsType.getName())
				.build();
	}

	private @NonNull Set<JsonCarrierService> toCarrierServices(@NonNull final  Set<CarrierService> services)
	{
		if (services.isEmpty())
		{
			return Collections.emptySet();
		}
		return services
				.stream()
				.map(this::toJsonCarrierService)
				.collect(Collectors.toSet());
	}

	@NonNull
	private JsonCarrierService toJsonCarrierService(@NonNull final CarrierService carrierService)
	{
		return JsonCarrierService.builder()
				.id(carrierService.getExternalId())
				.name(carrierService.getName())
				.build();
	}

	@NonNull
	public JsonShipperConfig toJsonShipperConfig(@NonNull final ShipperConfig config)
	{
		return JsonShipperConfig.builder()
				.url(config.getUrl())
				.username(config.getUsername())
				.password(config.getPassword())
				.clientId(config.getClientId())
				.clientSecret(config.getClientSecret())
				.trackingUrlTemplate(config.getTrackingUrlTemplate())
				.additionalProperties(config.getAdditionalProperties())
				.build();
	}

	@NonNull
	public static JsonAddress toJsonAddress(@NonNull final Address address)
	{
		final Integer bpartnerId = address.getBpartnerId() > 0 ? address.getBpartnerId() : null;
		return JsonAddress.builder()
				.companyName1(address.getCompanyName1())
				.companyName2(address.getCompanyName2())
				.companyDepartment(address.getCompanyDepartment())
				.street(address.getStreet1())
				.additionalAddressInfo(address.getStreet2())
				.houseNo(address.getHouseNo())
				.country(address.getCountry().getAlpha2())
				.zipCode(address.getZipCode())
				.city(address.getCity())
				.bpartnerId(bpartnerId)
				.build();
	}

	@Nullable
	@Contract("!null -> !null")
	public static JsonContact toJsonContactOrNull(@Nullable final ContactPerson contact)
	{
		if (contact == null) {return null;}

		return JsonContact.builder()
				.name(contact.getName())
				.department(contact.getDepartment())
				.phone(contact.getPhoneAsStringOrNull())
				.emailAddress(contact.getEmailAddress())
				.language(contact.getLanguageCode())
				.build();
	}

	private JsonDeliveryOrderParcel toJsonDeliveryOrderLine(@NonNull final DeliveryOrderParcel line)
	{
		Check.assumeNotNull(line.getId(), "lineId shouldn't be null");
		return JsonDeliveryOrderParcel.builder()
				.id(String.valueOf(line.getId().getRepoId()))
				.content(line.getContent())
				.grossWeightKg(line.getGrossWeightKg())
				.packageDimensions(toJsonPackageDimensions(line.getPackageDimensions()))
				.packageId(line.getPackageId().toString())
				.contents(line.getItems().stream().map(this::toJsonDeliveryOrderLineContents).collect(Collectors.toList()))
				.build();
	}

	private JsonDeliveryOrderLineContents toJsonDeliveryOrderLineContents(@NonNull final DeliveryOrderItem item)
	{
		Check.assumeNotNull(item.getId(), "itemId shouldn't be null");
		Check.assumeEquals(item.getTotalValue().getCurrencyId(), item.getUnitPrice().getCurrencyId()); // in sync with de.metas.shipper.gateway.commons.model.ShipmentOrderRepository.createItem
		final String currencyCode = currencyRepository.getById(item.getTotalValue().getCurrencyId()).getCurrencyCode().toThreeLetterCode();
		return JsonDeliveryOrderLineContents.builder()
				.shipmentOrderItemId(String.valueOf(item.getId()))
				.productName(item.getProductName())
				.productValue(item.getProductValue())
				.customsTariff(item.getCustomsTariff())
				.shippedQuantity(JsonQuantity.builder()
						.value(item.getShippedQuantity().toBigDecimal())
						.uomCode(item.getShippedQuantity().getUOMSymbol())
						.build())
				.unitPrice(JsonMoney.builder()
						.amount(item.getUnitPrice().toBigDecimal())
						.currencyCode(currencyCode)
						.build())
				.totalValue(JsonMoney.builder()
						.amount(item.getTotalValue().toBigDecimal())
				        .currencyCode(currencyCode)
						.build())
				.totalWeightInKg(item.getTotalWeightInKg())
				.build();
	}

	private JsonPackageDimensions toJsonPackageDimensions(final PackageDimensions dims)
	{
		if (dims == null) {return JsonPackageDimensions.builder().lengthInCM(0).widthInCM(0).heightInCM(0).build();}
		return JsonPackageDimensions.builder()
				.lengthInCM(dims.getLengthInCM())
				.widthInCM(dims.getWidthInCM())
				.heightInCM(dims.getHeightInCM())
				.build();
	}

	@NonNull
	private JsonMappingConfigList toJsonMappingConfigList(@NonNull final ShipperMappingConfigList configs)
	{
		if (configs == ShipperMappingConfigList.EMPTY)
		{
			return JsonMappingConfigList.EMPTY;
		}

		return JsonMappingConfigList.ofList(
				StreamSupport.stream(configs.spliterator(), false)
						.map(this::toJsonMappingConfig)
						.collect(ImmutableList.toImmutableList()));
	}

	@NonNull
	private JsonMappingConfig toJsonMappingConfig(@NonNull final ShipperMappingConfig config)
	{
		final CarrierProduct carrierProduct = config.getCarrierProductId() != null ? carrierProductRepository.getCachedShipperProductById(config.getCarrierProductId()) : null;
		return JsonMappingConfig.builder()
				.seqNo(config.getSeqNo().toInt())
				.shipperProductExternalId(carrierProduct != null ? carrierProduct.getCode() : null)
				.attributeType(config.getAttributeType().getCode())
				.groupKey(config.getGroupKey())
				.attributeKey(config.getAttributeKey())
				.attributeValue(config.getAttributeValue().getCode())
				.mappingRule(config.getMappingRule() != null ? config.getMappingRule().getCode() : null)
				.mappingRuleValue(config.getMappingRuleValue())
				.build();
	}
}