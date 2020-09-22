package de.metas.camel.shipping.shipment;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.camel.shipping.CommonUtil;
import de.metas.camel.shipping.JsonAttributeInstanceHelper;
import de.metas.camel.shipping.ProcessXmlToJsonRequest;
import de.metas.camel.shipping.XmlToJsonProcessorUtil;
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
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static de.metas.camel.shipping.shipment.ShipmentField.ARTICLE_FLAVOR;
import static de.metas.camel.shipping.shipment.ShipmentField.BEST_BEFORE_DATE;
import static de.metas.camel.shipping.shipment.ShipmentField.CITY;
import static de.metas.camel.shipping.shipment.ShipmentField.COUNTRY_CODE;
import static de.metas.camel.shipping.shipment.ShipmentField.DELIVERED_QTY;
import static de.metas.camel.shipping.shipment.ShipmentField.DELIVERED_QTY_OVERRIDE;
import static de.metas.camel.shipping.shipment.ShipmentField.DELIVERY_DATE;
import static de.metas.camel.shipping.shipment.ShipmentField.DOCUMENT_NO;
import static de.metas.camel.shipping.shipment.ShipmentField.HOUSE_NO;
import static de.metas.camel.shipping.shipment.ShipmentField.LOT_NUMBER;
import static de.metas.camel.shipping.shipment.ShipmentField.PACKAGE_WEIGHT;
import static de.metas.camel.shipping.shipment.ShipmentField.POSTAL_CODE;
import static de.metas.camel.shipping.shipment.ShipmentField.PRODUCT_VALUE;
import static de.metas.camel.shipping.shipment.ShipmentField.SHIPMENT_SCHEDULE_ID;
import static de.metas.camel.shipping.shipment.ShipmentField.SHIPPER_INTERNAL_NAME_SEG_1;
import static de.metas.camel.shipping.shipment.ShipmentField.SHIPPER_INTERNAL_NAME_SEG_2;
import static de.metas.camel.shipping.shipment.ShipmentField.STREET;
import static de.metas.camel.shipping.shipment.ShipmentField.TRACKING_NUMBERS;
import static de.metas.camel.shipping.shipment.SiroShipmentConstants.DEFAULT_DELIVERY_RULE_FORCE;
import static de.metas.camel.shipping.shipment.SiroShipmentConstants.DELIVERY_DATE_PATTERN;
import static de.metas.camel.shipping.shipment.SiroShipmentConstants.EMPTY_FIELD;
import static de.metas.camel.shipping.shipment.SiroShipmentConstants.EXPIRY_DATE_PATTERNS;
import static de.metas.camel.shipping.shipment.SiroShipmentConstants.PACKAGE_WEIGHT_SEPARATOR;
import static de.metas.camel.shipping.shipment.SiroShipmentConstants.SHIPPER_INTERNAL_NAME_SEPARATOR_PROP;
import static de.metas.camel.shipping.shipment.SiroShipmentConstants.TRACKING_NUMBERS_SEPARATOR;

public class ShipmentXmlToJsonProcessor implements Processor
{
	private final static Log log = LogFactory.getLog(ShipmentXmlToJsonProcessor.class);

	@Override public void process(final Exchange exchange)
	{
		XmlToJsonProcessorUtil.processExchange(exchange, this::buildCreateShipmentsRequest);
	}

	@NonNull
	private JsonCreateShipmentInfo createShipmentInfo(@NonNull final ROW row, @NonNull final Map<String, Integer> fieldName2Index, @NonNull final PropertiesComponent propertiesComponent)
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
				.packages(getPackages(row, fieldName2Index, propertiesComponent))
				.movementQuantity(getQtyToDeliver(row, fieldName2Index))
				.documentNo(StringUtils.trimToNull(getValueByName(row, fieldName2Index, DOCUMENT_NO)))
				.productSearchKey(StringUtils.trimToNull(CommonUtil.removeOrgPrefix(getValueByName(row, fieldName2Index, PRODUCT_VALUE))))
				.shipperInternalName(getShipperInternalName(row, fieldName2Index, propertiesComponent))
				.build();
	}

	private JsonCreateShipmentRequest buildCreateShipmentsRequest(@NonNull final ProcessXmlToJsonRequest xmlToJsonRequest)
	{
		final RESULTSET resultset = xmlToJsonRequest.getResultset();
		final Map<String, Integer> name2Index = xmlToJsonRequest.getName2Index();

		final List<JsonCreateShipmentInfo> createShipmentInfos = resultset.getRows()
				.stream()
				.map(row -> createShipmentInfo(row, name2Index, xmlToJsonRequest.getPropertiesComponent()))
				.collect(Collectors.toList());

		return JsonCreateShipmentRequest.builder()
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

		final String expiryDateStr = getValueByName(row, fieldName2Index, BEST_BEFORE_DATE);

		if (StringUtils.isNotBlank(expiryDateStr))
		{
			XmlToJsonProcessorUtil.asLocalDate(expiryDateStr, EXPIRY_DATE_PATTERNS, BEST_BEFORE_DATE.getName())
					.flatMap(expiryDate -> JsonAttributeInstanceHelper.buildAttribute(AttributeCode.BEST_BEFORE_DATE, expiryDate))
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

		return XmlToJsonProcessorUtil.asLocalDateTime(deliveryDateStr, ImmutableSet.of(DELIVERY_DATE_PATTERN), DELIVERY_DATE.getName()).orElse(null);
	}

	@Nullable
	private BigDecimal getQtyToDeliver(@NonNull final ROW row, @NonNull final Map<String, Integer> fieldName2Index)
	{
		final FileMakerDataHelper.GetValueRequest.GetValueRequestBuilder getValueRequestBuilder = FileMakerDataHelper.GetValueRequest.builder()
				.row(row)
				.fieldName2Index(fieldName2Index);

		final BigDecimal movementQtyOverride = FileMakerDataHelper.getBigDecimalValue(getValueRequestBuilder.fieldName(DELIVERED_QTY_OVERRIDE.getName()).build());

		if (movementQtyOverride != null)
		{
			return movementQtyOverride;
		}

		return FileMakerDataHelper.getBigDecimalValue(getValueRequestBuilder.fieldName(DELIVERED_QTY.getName()).build());
	}

	@Nullable
	private List<JsonPackage> getPackages(@NonNull final ROW row, @NonNull final Map<String, Integer> fieldName2Index, @NonNull final PropertiesComponent propertiesComponent)
	{
		final String trackingNumbersStr = getValueByName(row, fieldName2Index, TRACKING_NUMBERS);

		if (StringUtils.isBlank(trackingNumbersStr))
		{
			return null;//skip creating packages if there are no tracking numbers
		}

		final String trackingNumbersSeparator = propertiesComponent.resolveProperty(TRACKING_NUMBERS_SEPARATOR)
				.orElseThrow(() -> new RuntimeException("Missing property:" + TRACKING_NUMBERS_SEPARATOR + "!"));

		final String packageWeightSeparator = propertiesComponent.resolveProperty(PACKAGE_WEIGHT_SEPARATOR)
				.orElseThrow(() -> new RuntimeException("Missing property:" + PACKAGE_WEIGHT_SEPARATOR + "!"));

		final List<String> trackingNumbers = Stream.of(trackingNumbersStr.split(trackingNumbersSeparator))
				.filter(StringUtils::isNotBlank)
				.map(String::trim)
				.collect(ImmutableList.toImmutableList());

		final String packageWeightStr = getValueByName(row, fieldName2Index, PACKAGE_WEIGHT);

		final List<BigDecimal> packageWeightList = StringUtils.isBlank(packageWeightStr)
				? ImmutableList.of()
				: Stream.of(packageWeightStr.split(packageWeightSeparator))
				  	.filter(StringUtils::isNotBlank)
				    .map(FileMakerDataHelper::toBigDecimalOrNull)
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
		final String internalNameSeg1 = CoalesceUtil.coalesce(getValueByName(row, fieldName2Index, SHIPPER_INTERNAL_NAME_SEG_1), EMPTY_FIELD);
		final String internalNameSeg2 = CoalesceUtil.coalesce(getValueByName(row, fieldName2Index, SHIPPER_INTERNAL_NAME_SEG_2), EMPTY_FIELD);

		if (StringUtils.isBlank(internalNameSeg1) && StringUtils.isBlank(internalNameSeg2))
		{
			return null;
		}

		final String shipperInternalNameSeparator = propertiesComponent.resolveProperty(SHIPPER_INTERNAL_NAME_SEPARATOR_PROP)
				.orElseThrow(() -> new RuntimeException("Missing property:" + SHIPPER_INTERNAL_NAME_SEPARATOR_PROP + "!"));

		return StringUtils.joinWith(shipperInternalNameSeparator, internalNameSeg1, internalNameSeg2);
	}

}
