package de.metas.camel.shipping.shipment.kommissionierung;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.camel.shipping.JsonAttributeInstanceHelper;
import de.metas.camel.shipping.XmlToJsonProcessorUtil;
import de.metas.camel.shipping.shipment.SiroShipmentConstants;
import de.metas.common.filemaker.FIELD;
import de.metas.common.filemaker.FileMakerDataHelper;
import de.metas.common.filemaker.METADATA;
import de.metas.common.filemaker.ROW;
import de.metas.common.rest_api.JsonAttributeInstance;
import de.metas.common.rest_api.JsonMetasfreshId;
import lombok.Getter;
import lombok.NonNull;
import org.apache.camel.spi.PropertiesComponent;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static de.metas.camel.shipping.shipment.kommissionierung.ShipmentField.DOCUMENT_NO;
import static de.metas.camel.shipping.shipment.kommissionierung.ShipmentField.SHIPMENT_SCHEDULE_ID;

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

public class ShipmentXmlRowWrapper
{
	public static ShipmentXmlRowWrapper wrap(
			@NonNull final ROW row,
			@NonNull final METADATA metadata)
	{
		return new ShipmentXmlRowWrapper(row, metadata);
	}

	@Getter
	private final ROW row;

	private final METADATA metadata;
	private final ImmutableMap<String, Integer> fieldName2Index;

	private ShipmentXmlRowWrapper(
			@NonNull final ROW row,
			@NonNull final METADATA metadata)
	{
		this.row = row;
		this.metadata = metadata;

		final Map<String, Integer> name2Index = new HashMap<>();

		final List<FIELD> fields = metadata.getFields();
		final var builder = ImmutableMap.<String, Integer>builder();
		for (int i = 0; i < fields.size(); i++)
		{
			builder.put(fields.get(i).getName(), i);
		}
		fieldName2Index = builder.build();
	}

	@Nullable
	public String getValueByName(@NonNull final ShipmentField field)
	{
		final FileMakerDataHelper.GetValueRequest getValueRequest = FileMakerDataHelper.GetValueRequest.builder()
				.row(row)
				.fieldName(field.getName())
				.fieldName2Index(fieldName2Index)
				.build();

		return FileMakerDataHelper.getValue(getValueRequest);
	}

	public JsonMetasfreshId getShipmentScheduleId()
	{
		final String shipmentScheduleId = getValueByName(SHIPMENT_SCHEDULE_ID);

		if (StringUtils.isBlank(shipmentScheduleId))
		{
			throw new RuntimeException("Missing mandatory field: " + SHIPMENT_SCHEDULE_ID + " in rowWrapper" + this);
		}
		return JsonMetasfreshId.of(Integer.parseInt(shipmentScheduleId));
	}

	@Nullable
	public ImmutableList<JsonAttributeInstance> getAttributes()
	{
		final ImmutableList.Builder<JsonAttributeInstance> jsonAttributeInstances = ImmutableList.builder();

		final String expiryDateStr = getValueByName(ShipmentField.BEST_BEFORE_DATE);

		if (StringUtils.isNotBlank(expiryDateStr))
		{
			XmlToJsonProcessorUtil.asLocalDate(expiryDateStr, SiroShipmentConstants.EXPIRY_DATE_PATTERNS, ShipmentField.BEST_BEFORE_DATE.getName())
					.flatMap(expiryDate -> JsonAttributeInstanceHelper.buildAttribute(AttributeCode.BEST_BEFORE_DATE, expiryDate))
					.map(jsonAttributeInstances::add);
		}

		final String articleFlavor = getValueByName(ShipmentField.ARTICLE_FLAVOR);

		if (StringUtils.isNotBlank(articleFlavor))
		{
			JsonAttributeInstanceHelper.buildAttribute(AttributeCode.ARTICLE_FLAVOR, articleFlavor)
					.map(jsonAttributeInstances::add);
		}

		final String lotNumber = getValueByName(ShipmentField.LOT_NUMBER);

		if (StringUtils.isNotBlank(lotNumber))
		{
			JsonAttributeInstanceHelper.buildAttribute(AttributeCode.LOT_NUMBER, lotNumber)
					.map(jsonAttributeInstances::add);
		}

		final ImmutableList<JsonAttributeInstance> result = jsonAttributeInstances.build();

		return result.isEmpty() ? null : result;
	}

	@Nullable
	public LocalDateTime getDeliveryDate()
	{
		final String deliveryDateStr = getValueByName(ShipmentField.PICKING_TIMESTAMP);

		return XmlToJsonProcessorUtil.asLocalDateTime(deliveryDateStr,
				ImmutableSet.of(SiroShipmentConstants.DELIVERY_DATE_PATTERN),
				ShipmentField.PICKING_TIMESTAMP.getName())
				.orElse(null);
	}

	@Nullable
	public BigDecimal getMovementQty(@NonNull final PropertiesComponent propertiesComponent)
	{
		final Locale locale = XmlToJsonProcessorUtil.getLocale(propertiesComponent);

		final FileMakerDataHelper.GetValueRequest.GetValueRequestBuilder getValueRequestBuilder = FileMakerDataHelper.GetValueRequest.builder()
				.row(row)
				.fieldName2Index(fieldName2Index);

		final String movementQtyOverrideStr = FileMakerDataHelper.getValue(getValueRequestBuilder.fieldName(ShipmentField.DELIVERED_QTY_OVERRIDE.getName()).build());

		if (StringUtils.isNotBlank(movementQtyOverrideStr))
		{
			return XmlToJsonProcessorUtil.toBigDecimalOrNull(movementQtyOverrideStr, locale);
		}

		final String movementQty = FileMakerDataHelper.getValue(getValueRequestBuilder.fieldName(ShipmentField.DELIVERED_QTY.getName()).build());

		return XmlToJsonProcessorUtil.toBigDecimalOrNull(movementQty, locale);
	}

	public ROW appendToDocumentNo(@NonNull final String docNoSuffix)
	{
		final FileMakerDataHelper.GetValueRequest getValueRequest = FileMakerDataHelper.GetValueRequest.builder()
				.row(row)
				.fieldName(DOCUMENT_NO.getName())
				.fieldName2Index(fieldName2Index)
				.build();

		final var currentDocNo = FileMakerDataHelper.getValue(getValueRequest);

		return FileMakerDataHelper.setValue(getValueRequest, currentDocNo + docNoSuffix);
	}
}
