/*
 * #%L
 * de-metas-camel-shipping
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.camel.shipping.shipment.aussendung;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.camel.shipping.CommonUtil;
import de.metas.camel.shipping.JsonAttributeInstanceHelper;
import de.metas.camel.shipping.ProcessXmlToJsonRequest;
import de.metas.camel.shipping.XmlToJsonProcessorUtil;
import de.metas.camel.shipping.shipment.kommissionierung.AttributeCode;
import de.metas.camel.shipping.shipment.kommissionierung.SiroShipmentConstants;
import de.metas.camel.shipping.shipment.kommissionierung.inventory.InventoryCorrectionXmlToJsonProcessor;
import de.metas.common.filemaker.FileMakerDataHelper;
import de.metas.common.filemaker.RESULTSET;
import de.metas.common.filemaker.ROW;
import de.metas.common.rest_api.JsonAttributeInstance;
import de.metas.common.rest_api.JsonMetasfreshId;
import de.metas.common.shipment.JsonCreateShipmentInfo;
import de.metas.common.shipment.JsonCreateShipmentRequest;
import de.metas.common.shipment.JsonLocation;
import de.metas.common.shipment.JsonPackage;
import de.metas.common.util.CoalesceUtil;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.spi.PropertiesComponent;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ShipmentAussendungXmlToJsonProcessor implements Processor
{
	private final static Log log = LogFactory.getLog(ShipmentAussendungXmlToJsonProcessor.class);

	@Override
	public void process(final Exchange exchange)
	{
		XmlToJsonProcessorUtil.processExchange(exchange, this::buildCreateShipmentsRequest);
	}

	@NonNull
	private Optional<JsonCreateShipmentInfo> createShipmentInfo(@NonNull final ROW row, @NonNull final Map<String, Integer> fieldName2Index, @NonNull final PropertiesComponent propertiesComponent)
	{
		final boolean isOutOfStockCorrectionRequired = InventoryCorrectionXmlToJsonProcessor.isOutOfStockCorrectionRequired(row, fieldName2Index, propertiesComponent);

		if (isOutOfStockCorrectionRequired)
		{
			return Optional.empty();//will be handled in de.metas.camel.shipping.shipment.ShipmentXmlToJsonRouteBuilder.MF_SHIPMENT_INVENTORY_CORRECTION
		}

		final String shipmentScheduleId = getValueByName(row, fieldName2Index, ShipmentAussendungField.SHIPMENT_SCHEDULE_ID);

		if (StringUtils.isBlank(shipmentScheduleId))
		{
			throw new RuntimeException("Missing mandatory filed: ShipmentScheduleId!");
		}

		final JsonCreateShipmentInfo createShipmentInfo = JsonCreateShipmentInfo.builder()
				.deliveryRule(SiroShipmentConstants.DEFAULT_DELIVERY_RULE_FORCE)
				.shipmentScheduleId(JsonMetasfreshId.of(Integer.parseInt(shipmentScheduleId)))
				.shipToLocation(getLocation(row, fieldName2Index, shipmentScheduleId))
				.attributes(getAttributes(row, fieldName2Index))
				.movementDate(getDeliveryDate(row, fieldName2Index))
				.packages(getPackages(row, fieldName2Index, propertiesComponent))
				.movementQuantity(getMovementQty(row, fieldName2Index, propertiesComponent))
				.documentNo(StringUtils.trimToNull(getValueByName(row, fieldName2Index, ShipmentAussendungField.DOCUMENT_NO)))
				.productSearchKey(StringUtils.trimToNull(CommonUtil.removeOrgPrefix(getValueByName(row, fieldName2Index, ShipmentAussendungField.PRODUCT_VALUE))))
				.shipperInternalName(getShipperInternalName(row, fieldName2Index, propertiesComponent))
				.build();

		return Optional.of(createShipmentInfo);
	}

	private JsonCreateShipmentRequest buildCreateShipmentsRequest(@NonNull final ProcessXmlToJsonRequest xmlToJsonRequest)
	{
		final RESULTSET resultset = xmlToJsonRequest.getResultset();
		final Map<String, Integer> name2Index = xmlToJsonRequest.getName2Index();

		final List<JsonCreateShipmentInfo> createShipmentInfos = resultset.getRows()
				.stream()
				.map(row -> createShipmentInfo(row, name2Index, xmlToJsonRequest.getPropertiesComponent()))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toList());

		return JsonCreateShipmentRequest.builder()
				.createShipmentInfoList(createShipmentInfos)
				.build();
	}

	@Nullable
	private String getValueByName(@NonNull final ROW row, @NonNull final Map<String, Integer> fieldName2Index, @NonNull final ShipmentAussendungField field)
	{
		final FileMakerDataHelper.GetValueRequest getValueRequest = FileMakerDataHelper.GetValueRequest.builder()
				.row(row)
				.fieldName(field.getName())
				.fieldName2Index(fieldName2Index)
				.build();

		return FileMakerDataHelper.getValue(getValueRequest);
	}

	@Nullable
	private JsonLocation getLocation(
			@NonNull final ROW row,
			@NonNull final Map<String, Integer> fieldName2Index,
			@NonNull final String shipmentScheduleId)
	{
		final String countryCode = getValueByName(row, fieldName2Index, ShipmentAussendungField.RECIPIENT_COUNTRY_CODE);

		if (StringUtils.isBlank(countryCode))
		{
			log.debug("*** Skipped setting location for shipmentScheduleID:" + shipmentScheduleId + " as country code is missing!");
			return null;
		}

		return JsonLocation.builder()
				.countryCode(countryCode)
				.city(getValueByName(row, fieldName2Index, ShipmentAussendungField.RECIPIENT_CITY))
				.street(getValueByName(row, fieldName2Index, ShipmentAussendungField.RECIPIENT_STREET))
				.zipCode(getValueByName(row, fieldName2Index, ShipmentAussendungField.RECIPIENT_POSTAL_CODE))
				.houseNo(getValueByName(row, fieldName2Index, ShipmentAussendungField.RECIPIENT_HOUSE_NO))
				.build();
	}

	@Nullable
	private ImmutableList<JsonAttributeInstance> getAttributes(@NonNull final ROW row, @NonNull final Map<String, Integer> fieldName2Index)
	{
		final ImmutableList.Builder<JsonAttributeInstance> jsonAttributeInstances = ImmutableList.builder();

		final String expiryDateStr = getValueByName(row, fieldName2Index, ShipmentAussendungField.BEST_BEFORE_DATE);

		if (StringUtils.isNotBlank(expiryDateStr))
		{
			XmlToJsonProcessorUtil.asLocalDate(expiryDateStr, SiroShipmentConstants.EXPIRY_DATE_PATTERNS, ShipmentAussendungField.BEST_BEFORE_DATE.getName())
					.flatMap(expiryDate -> JsonAttributeInstanceHelper.buildAttribute(AttributeCode.BEST_BEFORE_DATE, expiryDate))
					.map(jsonAttributeInstances::add);
		}

		final String articleFlavor = getValueByName(row, fieldName2Index, ShipmentAussendungField.ARTICLE_FLAVOR);

		if (StringUtils.isNotBlank(articleFlavor))
		{
			JsonAttributeInstanceHelper.buildAttribute(AttributeCode.ARTICLE_FLAVOR, articleFlavor)
					.map(jsonAttributeInstances::add);
		}

		final String lotNumber = getValueByName(row, fieldName2Index, ShipmentAussendungField.LOT_NUMBER);

		if (StringUtils.isNotBlank(lotNumber))
		{
			JsonAttributeInstanceHelper.buildAttribute(AttributeCode.LOT_NUMBER, lotNumber)
					.map(jsonAttributeInstances::add);
		}

		final ImmutableList<JsonAttributeInstance> result = jsonAttributeInstances.build();

		return result.isEmpty() ? null : result;
	}

	@Nullable
	private LocalDateTime getDeliveryDate(@NonNull final ROW row, @NonNull final Map<String, Integer> fieldName2Index)
	{
		final String deliveryDateStr = getValueByName(row, fieldName2Index, ShipmentAussendungField.SHIPPING_TIMESTAMP);

		return XmlToJsonProcessorUtil.asLocalDateTime(deliveryDateStr,
				ImmutableSet.of(SiroShipmentConstants.DELIVERY_DATE_PATTERN),
				ShipmentAussendungField.SHIPPING_TIMESTAMP.getName())
				.orElse(null);
	}

	@Nullable
	private BigDecimal getMovementQty(@NonNull final ROW row, @NonNull final Map<String, Integer> fieldName2Index, @NonNull final PropertiesComponent propertiesComponent)
	{
		final Locale locale = XmlToJsonProcessorUtil.getLocale(propertiesComponent);

		final FileMakerDataHelper.GetValueRequest.GetValueRequestBuilder getValueRequestBuilder = FileMakerDataHelper.GetValueRequest.builder()
				.row(row)
				.fieldName2Index(fieldName2Index);

		final String movementQtyOverrideStr = FileMakerDataHelper.getValue(getValueRequestBuilder.fieldName(ShipmentAussendungField.DELIVERED_QTY_OVERRIDE.getName()).build());

		if (StringUtils.isNotBlank(movementQtyOverrideStr))
		{
			return XmlToJsonProcessorUtil.toBigDecimalOrNull(movementQtyOverrideStr, locale);
		}

		final String movementQty = FileMakerDataHelper.getValue(getValueRequestBuilder.fieldName(ShipmentAussendungField.DELIVERED_QTY.getName()).build());

		return XmlToJsonProcessorUtil.toBigDecimalOrNull(movementQty, locale);
	}

	@Nullable
	private List<JsonPackage> getPackages(@NonNull final ROW row, @NonNull final Map<String, Integer> fieldName2Index, @NonNull final PropertiesComponent propertiesComponent)
	{
		final String trackingNumbersStr = getValueByName(row, fieldName2Index, ShipmentAussendungField.TRACKING_NUMBERS);

		if (StringUtils.isBlank(trackingNumbersStr))
		{
			return null;//skip creating packages if there are no tracking numbers
		}

		final String trackingNumbersSeparator = propertiesComponent.resolveProperty(SiroShipmentConstants.TRACKING_NUMBERS_SEPARATOR)
				.orElseThrow(() -> new RuntimeException("Missing property:" + SiroShipmentConstants.TRACKING_NUMBERS_SEPARATOR + "!"));

		final String packageWeightSeparator = propertiesComponent.resolveProperty(SiroShipmentConstants.PACKAGE_WEIGHT_SEPARATOR)
				.orElseThrow(() -> new RuntimeException("Missing property:" + SiroShipmentConstants.PACKAGE_WEIGHT_SEPARATOR + "!"));

		final List<String> trackingNumbers = Stream.of(trackingNumbersStr.split(trackingNumbersSeparator))
				.filter(StringUtils::isNotBlank)
				.map(String::trim)
				.collect(ImmutableList.toImmutableList());

		final String packageWeightStr = getValueByName(row, fieldName2Index, ShipmentAussendungField.PACKAGE_WEIGHT);

		final Locale locale = XmlToJsonProcessorUtil.getLocale(propertiesComponent);

		final List<BigDecimal> packageWeightList = StringUtils.isBlank(packageWeightStr)
				? ImmutableList.of()
				: Stream.of(packageWeightStr.split(packageWeightSeparator))
				.filter(StringUtils::isNotBlank)
				.map(weight -> XmlToJsonProcessorUtil.toBigDecimalOrNull(weight, locale))
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());

		final ImmutableList.Builder<JsonPackage> packageListBuilder = ImmutableList.builder();

		for (int index = 0; index < trackingNumbers.size(); index++)
		{
			final BigDecimal packageWeight = packageWeightList.size() > index
					? packageWeightList.get(index)
					: null;

			final JsonPackage jsonPackage = JsonPackage.builder()
					.trackingCode(trackingNumbers.get(index))
					.weight(packageWeight)
					.build();

			packageListBuilder.add(jsonPackage);
		}

		return packageListBuilder.build();
	}

	@Nullable
	private String getShipperInternalName(@NonNull final ROW row, @NonNull final Map<String, Integer> fieldName2Index, @NonNull final PropertiesComponent propertiesComponent)
	{
		final String internalNameSeg1 = CoalesceUtil.coalesce(getValueByName(row, fieldName2Index, ShipmentAussendungField.SHIPPER_INTERNAL_NAME_SEG_1), SiroShipmentConstants.EMPTY_FIELD);
		final String internalNameSeg2 = CoalesceUtil.coalesce(getValueByName(row, fieldName2Index, ShipmentAussendungField.SHIPPER_INTERNAL_NAME_SEG_2), SiroShipmentConstants.EMPTY_FIELD);

		if (StringUtils.isBlank(internalNameSeg1) && StringUtils.isBlank(internalNameSeg2))
		{
			return null;
		}

		final String shipperInternalNameSeparator = propertiesComponent.resolveProperty(SiroShipmentConstants.SHIPPER_INTERNAL_NAME_SEPARATOR_PROP)
				.orElseThrow(() -> new RuntimeException("Missing property:" + SiroShipmentConstants.SHIPPER_INTERNAL_NAME_SEPARATOR_PROP + "!"));

		return StringUtils.joinWith(shipperInternalNameSeparator, internalNameSeg1, internalNameSeg2);
	}

}
