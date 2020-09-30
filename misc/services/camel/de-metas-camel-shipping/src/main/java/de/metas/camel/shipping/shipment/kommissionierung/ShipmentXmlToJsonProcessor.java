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

package de.metas.camel.shipping.shipment.kommissionierung;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.camel.shipping.CommonUtil;
import de.metas.camel.shipping.JsonAttributeInstanceHelper;
import de.metas.camel.shipping.ProcessXmlToJsonRequest;
import de.metas.camel.shipping.XmlToJsonProcessorUtil;
import de.metas.camel.shipping.shipment.SiroShipmentConstants;
import de.metas.camel.shipping.shipment.kommissionierung.inventory.InventoryCorrectionXmlToJsonProcessor;
import de.metas.common.filemaker.FileMakerDataHelper;
import de.metas.common.filemaker.RESULTSET;
import de.metas.common.filemaker.ROW;
import de.metas.common.rest_api.JsonAttributeInstance;
import de.metas.common.rest_api.JsonMetasfreshId;
import de.metas.common.shipment.JsonCreateShipmentInfo;
import de.metas.common.shipment.JsonCreateShipmentRequest;
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
import java.util.Optional;
import java.util.stream.Collectors;

public class ShipmentXmlToJsonProcessor implements Processor
{
	private final static Log log = LogFactory.getLog(ShipmentXmlToJsonProcessor.class);

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

		final String shipmentScheduleId = getValueByName(row, fieldName2Index, ShipmentField.SHIPMENT_SCHEDULE_ID);

		if (StringUtils.isBlank(shipmentScheduleId))
		{
			throw new RuntimeException("Missing mandatory filed: ShipmentScheduleId!");
		}

		final JsonCreateShipmentInfo createShipmentInfo = JsonCreateShipmentInfo.builder()
				.deliveryRule(SiroShipmentConstants.DEFAULT_DELIVERY_RULE_FORCE) // this is what was shipped, no mapper what metasfresh might think at this point
				.shipmentScheduleId(JsonMetasfreshId.of(Integer.parseInt(shipmentScheduleId)))
				.attributes(getAttributes(row, fieldName2Index))
				.movementDate(getDeliveryDate(row, fieldName2Index))
				.movementQuantity(getMovementQty(row, fieldName2Index, propertiesComponent))
				.documentNo(StringUtils.trimToNull(getValueByName(row, fieldName2Index, ShipmentField.DOCUMENT_NO)))
				.productSearchKey(StringUtils.trimToNull(CommonUtil.removeOrgPrefix(getValueByName(row, fieldName2Index, ShipmentField.ARTICLE_VALUE_TEMP))))
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
	private ImmutableList<JsonAttributeInstance> getAttributes(@NonNull final ROW row, @NonNull final Map<String, Integer> fieldName2Index)
	{
		final ImmutableList.Builder<JsonAttributeInstance> jsonAttributeInstances = ImmutableList.builder();

		final String expiryDateStr = getValueByName(row, fieldName2Index, ShipmentField.BEST_BEFORE_DATE);

		if (StringUtils.isNotBlank(expiryDateStr))
		{
			XmlToJsonProcessorUtil.asLocalDate(expiryDateStr, SiroShipmentConstants.EXPIRY_DATE_PATTERNS, ShipmentField.BEST_BEFORE_DATE.getName())
					.flatMap(expiryDate -> JsonAttributeInstanceHelper.buildAttribute(AttributeCode.BEST_BEFORE_DATE, expiryDate))
					.map(jsonAttributeInstances::add);
		}

		final String articleFlavor = getValueByName(row, fieldName2Index, ShipmentField.ARTICLE_FLAVOR);

		if (StringUtils.isNotBlank(articleFlavor))
		{
			JsonAttributeInstanceHelper.buildAttribute(AttributeCode.ARTICLE_FLAVOR, articleFlavor)
					.map(jsonAttributeInstances::add);
		}

		final String lotNumber = getValueByName(row, fieldName2Index, ShipmentField.LOT_NUMBER);

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
		final String deliveryDateStr = getValueByName(row, fieldName2Index, ShipmentField.PICKING_TIMESTAMP);

		return XmlToJsonProcessorUtil.asLocalDateTime(deliveryDateStr,
				ImmutableSet.of(SiroShipmentConstants.DELIVERY_DATE_PATTERN),
				ShipmentField.PICKING_TIMESTAMP.getName())
				.orElse(null);
	}

	@Nullable
	private BigDecimal getMovementQty(@NonNull final ROW row, @NonNull final Map<String, Integer> fieldName2Index, @NonNull final PropertiesComponent propertiesComponent)
	{
		final Locale locale = XmlToJsonProcessorUtil.getLocale(propertiesComponent);

		final FileMakerDataHelper.GetValueRequest.GetValueRequestBuilder getValueRequestBuilder = FileMakerDataHelper.GetValueRequest.builder()
				.row(row)
				.fieldName2Index(fieldName2Index);

		final String movementQtyOverrideStr = FileMakerDataHelper.getValue(getValueRequestBuilder.fieldName(ShipmentField.DELIVERED_QTY_OVERRIDE.getName()).build());

		if (StringUtils.isNotBlank(movementQtyOverrideStr))
		{
			return XmlToJsonProcessorUtil.toBigDecimalOrNull(movementQtyOverrideStr, locale).negate(); // qty is negative in _kommissionierung file
		}

		final String movementQty = FileMakerDataHelper.getValue(getValueRequestBuilder.fieldName(ShipmentField.DELIVERED_QTY.getName()).build());

		return XmlToJsonProcessorUtil.toBigDecimalOrNull(movementQty, locale).negate(); // qty is negative in _kommissionierung file
	}
}
