package de.metas.camel.shipping.receipt;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.camel.shipping.CommonUtil;
import de.metas.camel.shipping.JsonAttributeInstanceHelper;
import de.metas.camel.shipping.XmlToJsonProcessorUtil;
import de.metas.camel.shipping.shipment.kommissionierung.AttributeCode;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static de.metas.camel.CommonConstants.VALUE_TRUE;
import static de.metas.camel.shipping.receipt.ReceiptReturnField.ARTICLE_FLAVOR;
import static de.metas.camel.shipping.receipt.ReceiptReturnField.BEST_BEFORE_DATE;
import static de.metas.camel.shipping.receipt.ReceiptReturnField.DATE_RECEIVED;
import static de.metas.camel.shipping.receipt.ReceiptReturnField.IS_RETURN;
import static de.metas.camel.shipping.receipt.ReceiptReturnField.LOT_NUMBER;
import static de.metas.camel.shipping.receipt.ReceiptReturnField.MOVEMENT_DATE;
import static de.metas.camel.shipping.receipt.ReceiptReturnField.MOVEMENT_QUANTITY;
import static de.metas.camel.shipping.receipt.ReceiptReturnField.PRODUCT_SEARCH_KEY;
import static de.metas.camel.shipping.receipt.ReceiptReturnField.RECEIPT_SCHEDULE_ID;
import static de.metas.camel.shipping.receipt.ReceiptReturnField.SHIPMENT_SCHEDULE_ID;
import static de.metas.camel.shipping.receipt.SiroReceiptConstants.DATE_RECEIVED_PATTERNS;
import static de.metas.camel.shipping.receipt.SiroReceiptConstants.EXPIRY_DATE_PATTERNS;
import static de.metas.camel.shipping.receipt.SiroReceiptConstants.MOVEMENT_DATE_PATTERNS;
import static de.metas.camel.shipping.receipt.SiroReceiptConstants.ORG_PREFIX_TOKEN;
import static de.metas.camel.shipping.receipt.SiroReceiptConstants.ORG_PREFIX_TO_ORG_CODE_PROPERTY_PATTERN;
import static de.metas.camel.shipping.receipt.SiroReceiptConstants.RETURN_GOODS_IN_WAREHOUSE_TYPE;

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

public class ReceiptXmlRowWrapper
{
	public static ReceiptXmlRowWrapper wrap(
			@NonNull final ROW row,
			@NonNull final METADATA metadata)
	{
		return new ReceiptXmlRowWrapper(row, metadata);
	}

	@Getter
	private final ROW row;

	private final METADATA metadata;
	private final ImmutableMap<String, Integer> fieldName2Index;

	private ReceiptXmlRowWrapper(
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
	public String getValueByName(@NonNull final ReceiptReturnField field)
	{
		final FileMakerDataHelper.GetValueRequest getValueRequest = FileMakerDataHelper.GetValueRequest.builder()
				.row(row)
				.fieldName(field.getName())
				.fieldName2Index(fieldName2Index)
				.build();

		return FileMakerDataHelper.getValue(getValueRequest);
	}

	public JsonMetasfreshId getReceiptScheduleId()
	{
		final String receiptScheduleId = getValueByName(RECEIPT_SCHEDULE_ID);

		if (StringUtils.isBlank(receiptScheduleId))
		{
			throw new RuntimeException("Missing mandatory field: " + RECEIPT_SCHEDULE_ID + "in rowWrapper" + this);
		}
		return JsonMetasfreshId.of(Integer.parseInt(receiptScheduleId));
	}

	public boolean isReturn()
	{
		final String isReturn = getValueByName(IS_RETURN);

		if (StringUtils.isBlank(isReturn))
		{
			return false;
		}

		return VALUE_TRUE.equalsIgnoreCase(isReturn);
	}

	@Nullable
	public LocalDate getMovementDate()
	{
		final String value = getValueByName(MOVEMENT_DATE);

		return XmlToJsonProcessorUtil.asLocalDate(value, MOVEMENT_DATE_PATTERNS, MOVEMENT_DATE.getName()).orElse(null);
	}

	@Nullable
	public LocalDateTime getDateReceived()
	{
		final String dateReceived = getValueByName(DATE_RECEIVED);

		return XmlToJsonProcessorUtil.asLocalDateTime(dateReceived, DATE_RECEIVED_PATTERNS, DATE_RECEIVED.getName()).orElse(null);
	}

	@Nullable
	public List<JsonAttributeInstance> buildAttributeInstanceList()
	{
		final ImmutableList.Builder<JsonAttributeInstance> jsonAttributeInstances = ImmutableList.builder();

		final FileMakerDataHelper.GetValueRequest getExpiryDateReq = FileMakerDataHelper.GetValueRequest.builder()
				.row(row)
				.fieldName2Index(fieldName2Index)
				.fieldName(BEST_BEFORE_DATE.getName())
				.build();

		final String expiryDateStr = FileMakerDataHelper.getValue(getExpiryDateReq);

		XmlToJsonProcessorUtil.asLocalDate(expiryDateStr, EXPIRY_DATE_PATTERNS, BEST_BEFORE_DATE.getName())
				.flatMap(expiryDate -> JsonAttributeInstanceHelper.buildAttribute(AttributeCode.BEST_BEFORE_DATE, expiryDate))
				.map(jsonAttributeInstances::add);

		final FileMakerDataHelper.GetValueRequest.GetValueRequestBuilder getValueRequest = FileMakerDataHelper.GetValueRequest.builder()
				.row(row)
				.fieldName2Index(fieldName2Index)
				.fieldName(ARTICLE_FLAVOR.getName());

		final String articleFlavor = FileMakerDataHelper.getValue(getValueRequest.build());

		if (StringUtils.isNotBlank(articleFlavor))
		{
			JsonAttributeInstanceHelper.buildAttribute(AttributeCode.ARTICLE_FLAVOR, articleFlavor)
					.map(jsonAttributeInstances::add);
		}

		final String lotNumber = FileMakerDataHelper.getValue(getValueRequest.fieldName(LOT_NUMBER.getName()).build());

		if (StringUtils.isNotBlank(lotNumber))
		{
			JsonAttributeInstanceHelper.buildAttribute(AttributeCode.LOT_NUMBER, lotNumber)
					.map(jsonAttributeInstances::add);
		}

		final ImmutableList<JsonAttributeInstance> result = jsonAttributeInstances.build();

		return result.isEmpty() ? null : result;
	}

	@Nullable
	public String getProductSearchKey()
	{
		final String productSearchKey = getValueByName(PRODUCT_SEARCH_KEY);

		if (StringUtils.isBlank(productSearchKey))
		{
			return null;
		}

		return CommonUtil.convertProductValue(productSearchKey);
	}

	@Nullable
	public JsonMetasfreshId getShipmentScheduleId()
	{
		final String shipmentScheduleId = getValueByName(SHIPMENT_SCHEDULE_ID);

		if (StringUtils.isBlank(shipmentScheduleId))
		{
			return null;
		}

		return JsonMetasfreshId.of(Integer.parseInt(shipmentScheduleId));
	}

	@NonNull
	public String getReturnGoodsWarehouseType(@NonNull final PropertiesComponent propertiesComponent)
	{
		return propertiesComponent.resolveProperty(RETURN_GOODS_IN_WAREHOUSE_TYPE).orElseThrow(() -> new RuntimeException("Missing property: " + RETURN_GOODS_IN_WAREHOUSE_TYPE + "!"));
	}

	public String getOrgCode(@NonNull final PropertiesComponent propertiesComponent)
	{
		final String productSearchKeyWithOrgId = getValueByName(PRODUCT_SEARCH_KEY);

		final String orgPrefix = CommonUtil.extractOrgPrefixFromProduct(productSearchKeyWithOrgId);

		if (StringUtils.isBlank(orgPrefix))
		{
			throw new RuntimeException("Missing org prefix!");
		}

		final String orgPrefix2CodeProperty = ORG_PREFIX_TO_ORG_CODE_PROPERTY_PATTERN.replace(ORG_PREFIX_TOKEN, orgPrefix);

		return propertiesComponent
				.resolveProperty(orgPrefix2CodeProperty)
				.orElseThrow(() -> new RuntimeException("Missing property: " + orgPrefix2CodeProperty + "!"));
	}

	@Nullable
	public BigDecimal getMovementQty(@NonNull final PropertiesComponent propertiesComponent)
	{
		final Locale locale = XmlToJsonProcessorUtil.getLocale(propertiesComponent);

		final String movementQtyStr = getValueByName(MOVEMENT_QUANTITY);

		return XmlToJsonProcessorUtil.toBigDecimalOrNull(movementQtyStr, locale);
	}
}
