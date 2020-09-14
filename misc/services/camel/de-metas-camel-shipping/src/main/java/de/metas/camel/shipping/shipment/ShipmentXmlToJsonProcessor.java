package de.metas.camel.shipping.shipment;

import static de.metas.camel.shipping.XmlToJsonProcessorUtil.asLocalDate;
import static de.metas.camel.shipping.XmlToJsonProcessorUtil.asLocalDateTime;
import static de.metas.camel.shipping.XmlToJsonProcessorUtil.processExchange;
import static de.metas.camel.shipping.shipment.ShipmentField.ARTICLE_FLAVOR;
import static de.metas.camel.shipping.shipment.ShipmentField.CITY;
import static de.metas.camel.shipping.shipment.ShipmentField.COUNTRY_CODE;
import static de.metas.camel.shipping.shipment.ShipmentField.DELIVERED_QTY;
import static de.metas.camel.shipping.shipment.ShipmentField.DELIVERY_DATE;
import static de.metas.camel.shipping.shipment.ShipmentField.DOCUMENT_NO;
import static de.metas.camel.shipping.shipment.ShipmentField.EXPIRY_DATE;
import static de.metas.camel.shipping.shipment.ShipmentField.HOUSE_NO;
import static de.metas.camel.shipping.shipment.ShipmentField.LOT_NUMBER;
import static de.metas.camel.shipping.shipment.ShipmentField.POSTAL_CODE;
import static de.metas.camel.shipping.shipment.ShipmentField.PRODUCT_VALUE;
import static de.metas.camel.shipping.shipment.ShipmentField.SHIPMENT_SCHEDULE_ID;
import static de.metas.camel.shipping.shipment.ShipmentField.STREET;
import static de.metas.camel.shipping.shipment.ShipmentField.TRACKING_NUMBERS;
import static de.metas.camel.shipping.shipment.SiroShipmentConstants.DEFAULT_DELIVERY_RULE_FORCE;
import static de.metas.camel.shipping.shipment.SiroShipmentConstants.DELIVERY_DATE_PATTERN;
import static de.metas.camel.shipping.shipment.SiroShipmentConstants.EXPIRY_DATE_PATTERNS;
import static de.metas.camel.shipping.shipment.SiroShipmentConstants.SIRO_SHIPPER_SEARCH_KEY;
import static de.metas.camel.shipping.shipment.SiroShipmentConstants.TRACKING_NUMBERS_SEPARATOR;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.camel.shipping.CommonUtil;
import de.metas.camel.shipping.JsonAttributeInstanceHelper;
import de.metas.common.filemaker.FileMakerDataHelper;
import de.metas.common.filemaker.ROW;
import de.metas.common.rest_api.JsonAttributeInstance;
import de.metas.common.rest_api.JsonMetasfreshId;
import de.metas.common.shipment.JsonCreateShipmentInfo;
import de.metas.common.shipment.JsonCreateShipmentRequest;
import de.metas.common.shipment.JsonLocation;
import lombok.NonNull;

public class ShipmentXmlToJsonProcessor implements Processor
{
	private final static Log log = LogFactory.getLog(ShipmentXmlToJsonProcessor.class);

	@Override
	public void process(final Exchange exchange)
	{
		processExchange(exchange, this::createShipmentInfo, this::buildCreateShipmentsRequest);
	}

	@NonNull
	private JsonCreateShipmentInfo createShipmentInfo(@NonNull final ROW row, @NonNull final Map<String, Integer> fieldName2Index)
	{
		final String shipmentScheduleId = getValueByName(row, fieldName2Index, SHIPMENT_SCHEDULE_ID);

		if (StringUtils.isBlank(shipmentScheduleId))
		{
			throw new RuntimeException("Missing mandatory filed: ShipmentScheduleId!");
		}

		return JsonCreateShipmentInfo.builder()
				.deliveryRule(DEFAULT_DELIVERY_RULE_FORCE)
				.shipmentScheduleId(JsonMetasfreshId.of(Integer.parseInt(shipmentScheduleId)))
				.shipToLocation(getLocation(row, fieldName2Index, shipmentScheduleId))
				.attributes(getAttributes(row, fieldName2Index))
				.movementDate(getDeliveryDate(row, fieldName2Index))
				.trackingNumbers(getTrackingNumbers(row, fieldName2Index))
				.movementQuantity(getQtyToDeliver(row, fieldName2Index))
				.documentNo(StringUtils.trimToNull(getValueByName(row, fieldName2Index, DOCUMENT_NO)))
				.productSearchKey(StringUtils.trimToNull(CommonUtil.removeOrgPrefix(getValueByName(row, fieldName2Index, PRODUCT_VALUE))))
				.build();
	}

	private JsonCreateShipmentRequest buildCreateShipmentsRequest(@NonNull final List<JsonCreateShipmentInfo> createShipmentInfos)
	{
		return JsonCreateShipmentRequest.builder()
				.shipperCode(SIRO_SHIPPER_SEARCH_KEY)
				.createShipmentInfoList(createShipmentInfos)
				.build();
	}

	@Nullable
	private String getValueByName(@NonNull final ROW row, @NonNull final Map<String, Integer> fieldName2Index, @NonNull final ShipmentField field)
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
		final String countryCode = getValueByName(row, fieldName2Index, COUNTRY_CODE);

		if (StringUtils.isBlank(countryCode))
		{
			log.debug("*** Skipped setting location for shipmentScheduleID:" + shipmentScheduleId + " as country code is missing!");
			return null;
		}

		return JsonLocation.builder()
				.countryCode(countryCode)
				.city(getValueByName(row, fieldName2Index, CITY))
				.street(getValueByName(row, fieldName2Index, STREET))
				.zipCode(getValueByName(row, fieldName2Index, POSTAL_CODE))
				.houseNo(getValueByName(row, fieldName2Index, HOUSE_NO))
				.build();
	}

	@Nullable
	private ImmutableList<JsonAttributeInstance> getAttributes(@NonNull final ROW row, @NonNull final Map<String, Integer> fieldName2Index)
	{
		final ImmutableList.Builder<JsonAttributeInstance> jsonAttributeInstances = ImmutableList.builder();

		final String expiryDateStr = getValueByName(row, fieldName2Index, EXPIRY_DATE);

		if (StringUtils.isNotBlank(expiryDateStr))
		{
			asLocalDate(expiryDateStr, EXPIRY_DATE_PATTERNS, EXPIRY_DATE.getName())
					.flatMap(expiryDate -> JsonAttributeInstanceHelper.buildAttribute(AttributeCode.EXPIRY_DATE, expiryDate))
					.map(jsonAttributeInstances::add);
		}

		final String articleFlavor = getValueByName(row, fieldName2Index, ARTICLE_FLAVOR);

		if (StringUtils.isNotBlank(articleFlavor))
		{
			JsonAttributeInstanceHelper.buildAttribute(AttributeCode.ARTICLE_FLAVOR, articleFlavor)
					.map(jsonAttributeInstances::add);
		}

		final String lotNumber = getValueByName(row, fieldName2Index, LOT_NUMBER);

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
		final String deliveryDateStr = getValueByName(row, fieldName2Index, DELIVERY_DATE);

		return asLocalDateTime(deliveryDateStr, ImmutableSet.of(DELIVERY_DATE_PATTERN), DELIVERY_DATE.getName()).orElse(null);
	}

	@Nullable
	private ImmutableList<String> getTrackingNumbers(@NonNull final ROW row, @NonNull final Map<String, Integer> fieldName2Index)
	{
		final String trackingNumbers = getValueByName(row, fieldName2Index, TRACKING_NUMBERS);

		if (StringUtils.isBlank(trackingNumbers))
		{
			return null;
		}

		return ImmutableList.copyOf(trackingNumbers.split(TRACKING_NUMBERS_SEPARATOR));
	}

	@Nullable
	private BigDecimal getQtyToDeliver(@NonNull final ROW row, @NonNull final Map<String, Integer> fieldName2Index)
	{
		final FileMakerDataHelper.GetValueRequest getValueRequest = FileMakerDataHelper.GetValueRequest.builder()
				.row(row)
				.fieldName2Index(fieldName2Index)
				.fieldName(DELIVERED_QTY.getName())
				.build();

		return FileMakerDataHelper.getBigDecimalValue(getValueRequest);
	}
}
