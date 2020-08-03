package de.metas.camel.shipping.shipment;

import com.google.common.collect.ImmutableList;
import de.metas.camel.shipping.RouteBuilderCommonUtil;
import de.metas.common.filemaker.COL;
import de.metas.common.filemaker.FIELD;
import de.metas.common.filemaker.FMPXMLRESULT;
import de.metas.common.filemaker.RESULTSET;
import de.metas.common.filemaker.ROW;
import de.metas.common.rest_api.JsonAttributeInstance;
import de.metas.common.rest_api.JsonMetasfreshId;
import de.metas.common.shipment.JsonCreateShipmentInfo;
import de.metas.common.shipment.JsonCreateShipmentRequest;
import de.metas.common.shipment.JsonLocation;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
import static de.metas.camel.shipping.shipment.SiroShipmentConstants.EXPIRY_DATE_PATTERS;
import static de.metas.camel.shipping.shipment.SiroShipmentConstants.SIRO_SHIPPER_SEARCH_KEY;
import static de.metas.camel.shipping.shipment.SiroShipmentConstants.TRACKING_NUMBERS_SEPARATOR;

public class ShipmentXmlToJsonProcessor implements Processor
{
	private final Log log = LogFactory.getLog(ShipmentXmlToJsonProcessor.class);

	@Override public void process(Exchange exchange) throws Exception
	{
		final FMPXMLRESULT fmpxmlresult = exchange.getIn().getBody(FMPXMLRESULT.class);

		if (fmpxmlresult == null || fmpxmlresult.isEmpty() )
		{
			log.debug("exchange.body is empty! -> nothing to do!");
			return; //nothing to do
		}

		final Map<String, Integer> name2Index = new HashMap<>();

		final List<FIELD> fields = fmpxmlresult.getMetadata().getFields();

		for (int i = 0; i < fields.size(); i++)
		{
			name2Index.put(fields.get(i).getName(), i);
		}

		final RESULTSET resultset = fmpxmlresult.getResultset();

		final List<JsonCreateShipmentInfo> createShipmentInfos = resultset.getRows()
				.stream()
				.map(row -> createShipmentInfo(row, name2Index))
				.collect(Collectors.toList());

		final JsonCreateShipmentRequest jsonCreateShipmentRequest = JsonCreateShipmentRequest.builder()
				.shipperCode(SIRO_SHIPPER_SEARCH_KEY)
				.createShipmentInfoList(createShipmentInfos)
				.build();

		exchange.getIn().setHeader(RouteBuilderCommonUtil.NUMBER_OF_ITEMS, createShipmentInfos.size());
		exchange.getIn().setBody(jsonCreateShipmentRequest);
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
				.productSearchKey(StringUtils.trimToNull(getValueByName(row, fieldName2Index, PRODUCT_VALUE)))
				.build();
	}

	@Nullable
	private String getValueByName(@NonNull final ROW row, @NonNull final Map<String, Integer> fieldName2Index, @NonNull final ShipmentField field)
	{
		final Integer index = fieldName2Index.get(field.getName());

		if (index == null || row.getCols() == null || row.getCols().isEmpty())
		{
			return null;
		}

		final COL col = row.getCols().get(index);

		if (col.getData() == null)
		{
			return null;
		}

		return col.getData().getValue();
	}

	@Nullable
	private JsonLocation getLocation(@NonNull final ROW row, @NonNull final Map<String, Integer> fieldName2Index, @NonNull String shipmentScheduleId)
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
			getExpiryDate(expiryDateStr)
					.flatMap(expiryDate -> buildAttribute(AttributeCode.EXPIRY_DATE, expiryDate))
					.map(jsonAttributeInstances::add);
		}

		final String articleFlavor = getValueByName(row, fieldName2Index, ARTICLE_FLAVOR);

		if (StringUtils.isNotBlank(articleFlavor))
		{
			buildAttribute(AttributeCode.ARTICLE_FLAVOR, articleFlavor)
					.map(jsonAttributeInstances::add);
		}

		final String lotNumber = getValueByName(row, fieldName2Index, LOT_NUMBER);

		if (StringUtils.isNotBlank(lotNumber))
		{
			buildAttribute(AttributeCode.LOT_NUMBER, lotNumber)
					.map(jsonAttributeInstances::add);
		}

		final ImmutableList<JsonAttributeInstance> result = jsonAttributeInstances.build();

		return result.isEmpty() ? null : result;
	}

	@NonNull
	private Optional<JsonAttributeInstance> buildAttribute(@NonNull final AttributeCode attributeCode, @NonNull final Object value)
	{
		final JsonAttributeInstance.JsonAttributeInstanceBuilder builder = JsonAttributeInstance.builder()
				.attributeName(attributeCode.getAttributeCode())
				.attributeCode(attributeCode.getAttributeCode());

		switch (attributeCode.getAttributeValueType())
		{
			case STRING:
				builder.valueStr((String) value);
				break;
			case NUMBER:
				builder.valueNumber((BigDecimal) value);
				break;
			case DATE:
				builder.valueDate((LocalDate) value);
				break;
			default:
				log.debug("The given attribute type is not supported! AttributeType: " + attributeCode.getAttributeValueType() + " -> returning null!");
				return Optional.empty();
		}

		return Optional.of(builder.build());
	}

	@NonNull
	private Optional<LocalDate> getExpiryDate(@NonNull final String expiryDateStr)
	{
		Optional<LocalDate> expiryDate = Optional.empty();

		for (final String expiryDatePattern: EXPIRY_DATE_PATTERS)
		{
			final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(expiryDatePattern);

			try
			{
				expiryDate = Optional.ofNullable(LocalDate.parse(expiryDateStr, dateTimeFormatter));
			}
			catch (final Exception e)
			{
				log.debug("*** Error caught when trying to parse the expiry date! ExpiryDate: " + expiryDateStr + " Pattern: " + expiryDatePattern, e);
			}
		}

		return expiryDate;
	}

	@Nullable
	private LocalDateTime getDeliveryDate(@NonNull final ROW row, @NonNull final Map<String, Integer> fieldName2Index)
	{
		final String deliveryDateStr = getValueByName(row, fieldName2Index, DELIVERY_DATE);

		if (StringUtils.isBlank(deliveryDateStr))
		{
			return null;
		}

		final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DELIVERY_DATE_PATTERN);
		LocalDateTime deliveryDate = null;

		try
		{
			deliveryDate = LocalDateTime.parse(deliveryDateStr, dateTimeFormatter);
		}
		catch (final Exception e)
		{
			log.debug("*** Error caught when trying to parse the delivery date! DeliveryDate: " + deliveryDateStr + " Pattern: " + DELIVERY_DATE_PATTERN, e);
		}

		return deliveryDate;
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
		final String qtyToDeliverStr = getValueByName(row, fieldName2Index, DELIVERED_QTY);

		if (StringUtils.isBlank(qtyToDeliverStr))
		{
			return null;
		}

		BigDecimal qtyToDeliver = null;

		try
		{
			qtyToDeliver = new BigDecimal(qtyToDeliverStr);
		}
		catch (final Exception e)
		{
			log.debug("*** Exception caught when trying to parse QtyToDeliver! QtyToDeliver: " + qtyToDeliverStr + " ; -> returning null!");
		}

		return qtyToDeliver;
	}
}
