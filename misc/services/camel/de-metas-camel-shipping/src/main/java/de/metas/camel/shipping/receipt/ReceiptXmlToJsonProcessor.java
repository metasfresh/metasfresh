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

package de.metas.camel.shipping.receipt;

import com.google.common.collect.ImmutableList;
import de.metas.camel.shipping.CommonUtil;
import de.metas.camel.shipping.JsonAttributeInstanceHelper;
import de.metas.camel.shipping.ProcessXmlToJsonRequest;
import de.metas.camel.shipping.XmlToJsonProcessorUtil;
import de.metas.camel.shipping.shipment.AttributeCode;
import de.metas.common.customerreturns.JsonCreateCustomerReturnInfo;
import de.metas.common.filemaker.FileMakerDataHelper;
import de.metas.common.filemaker.RESULTSET;
import de.metas.common.filemaker.ROW;
import de.metas.common.receipt.JsonCreateReceiptInfo;
import de.metas.common.receipt.JsonCreateReceiptsRequest;
import de.metas.common.rest_api.JsonAttributeInstance;
import de.metas.common.rest_api.JsonMetasfreshId;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.spi.PropertiesComponent;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static de.metas.camel.shipping.receipt.ReceiptReturnField.ARTICLE_FLAVOR;
import static de.metas.camel.shipping.receipt.ReceiptReturnField.DATE_RECEIVED;
import static de.metas.camel.shipping.receipt.ReceiptReturnField.EXPIRY_DATE;
import static de.metas.camel.shipping.receipt.ReceiptReturnField.EXTERNAL_ID;
import static de.metas.camel.shipping.receipt.ReceiptReturnField.IS_RETURN;
import static de.metas.camel.shipping.receipt.ReceiptReturnField.LOT_NUMBER;
import static de.metas.camel.shipping.receipt.ReceiptReturnField.MOVEMENT_DATE;
import static de.metas.camel.shipping.receipt.ReceiptReturnField.MOVEMENT_QUANTITY;
import static de.metas.camel.shipping.receipt.ReceiptReturnField.PRODUCT_SEARCH_KEY;
import static de.metas.camel.shipping.receipt.ReceiptReturnField.RECEIPT_EXTERNAL_RESOURCE_URL;
import static de.metas.camel.shipping.receipt.ReceiptReturnField.RECEIPT_SCHEDULE_ID;
import static de.metas.camel.shipping.receipt.ReceiptReturnField.RETURN_EXTERNAL_RESOURCE_URL;
import static de.metas.camel.shipping.receipt.ReceiptReturnField.SHIPMENT_DOCUMENT_NO;
import static de.metas.camel.shipping.receipt.ReceiptReturnField.SHIPMENT_SCHEDULE_ID;
import static de.metas.camel.shipping.receipt.SiroReceiptConstants.DATE_RECEIVED_PATTERNS;
import static de.metas.camel.shipping.receipt.SiroReceiptConstants.EXPIRY_DATE_PATTERNS;
import static de.metas.camel.shipping.receipt.SiroReceiptConstants.IS_RETURN_VALUE_TRUE;
import static de.metas.camel.shipping.receipt.SiroReceiptConstants.MOVEMENT_DATE_PATTERNS;
import static de.metas.camel.shipping.receipt.SiroReceiptConstants.ORG_PREFIX_TOKEN;
import static de.metas.camel.shipping.receipt.SiroReceiptConstants.ORG_PREFIX_TO_ORG_CODE_PROPERTY_PATTERN;
import static de.metas.camel.shipping.receipt.SiroReceiptConstants.RETURN_GOODS_IN_WAREHOUSE_TYPE;

public class ReceiptXmlToJsonProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange) throws Exception
	{
		XmlToJsonProcessorUtil.processExchange(exchange, this::buildCreateReceiptsRequest);
	}

	@NonNull
	private JsonCreateReceiptsRequest buildCreateReceiptsRequest(@NonNull final ProcessXmlToJsonRequest request)
	{
		final RESULTSET resultset = request.getResultset();
		final Map<String, Integer> name2Index = request.getName2Index();
		final PropertiesComponent propertiesComponent = request.getPropertiesComponent();

		final List<JsonCreateReceiptInfo> createReceiptInfos = resultset.getRows()
				.stream()
				.filter(row -> !isReturn(row, name2Index))
				.map(row -> createReceiptInfo(row, name2Index))
				.collect(Collectors.toList());

		final List<JsonCreateCustomerReturnInfo> customerReturnInfos = resultset.getRows()
				.stream()
				.filter(row -> isReturn(row, name2Index))
				.map(row -> createCustomerReturnInfo(row, name2Index, propertiesComponent))
				.collect(Collectors.toList());

		return JsonCreateReceiptsRequest.builder()
				.jsonCreateReceiptInfoList(createReceiptInfos)
				.jsonCreateCustomerReturnInfoList(customerReturnInfos)
				.build();
	}

	private boolean isReturn(@NonNull final ROW row, @NonNull final Map<String, Integer> name2Index)
	{
		final FileMakerDataHelper.GetValueRequest getIsReturnReq = FileMakerDataHelper.GetValueRequest.builder()
				.fieldName(IS_RETURN.getName())
				.fieldName2Index(name2Index)
				.row(row)
				.build();

		final String isReturn = FileMakerDataHelper.getValue(getIsReturnReq);

		if (StringUtils.isBlank(isReturn))
		{
			return false;
		}

		return IS_RETURN_VALUE_TRUE.equals(isReturn);
	}

	@NonNull
	private JsonCreateReceiptInfo createReceiptInfo(@NonNull final ROW row,@NonNull final Map<String, Integer> name2Index)
	{
		final FileMakerDataHelper.GetValueRequest.GetValueRequestBuilder getValueRequest = FileMakerDataHelper.GetValueRequest.builder()
				.row(row)
				.fieldName2Index(name2Index);

		final String receiptScheduleId = FileMakerDataHelper.getValue(getValueRequest.fieldName(RECEIPT_SCHEDULE_ID.getName()).build());

		if (StringUtils.isBlank(receiptScheduleId))
		{
			throw new RuntimeException("Missing mandatory filed: receiptScheduleId! mapping for " + RECEIPT_SCHEDULE_ID.getName() + " !");
		}

		return JsonCreateReceiptInfo.builder()
				.receiptScheduleId(JsonMetasfreshId.of(Integer.parseInt(receiptScheduleId)))
				//
				.externalId(StringUtils.trimToNull(FileMakerDataHelper.getValue(getValueRequest.fieldName(EXTERNAL_ID.getName()).build())))
				//
				.productSearchKey(getProductSearchKey(getValueRequest.fieldName(PRODUCT_SEARCH_KEY.getName()).build()))
				//
				.movementQuantity(FileMakerDataHelper.getBigDecimalValue(getValueRequest.fieldName(MOVEMENT_QUANTITY.getName()).build()))
				//
				.attributes(buildAttributeInstanceList(row, name2Index))
				//
				.movementDate(getMovementDate(row, name2Index))
				//
				.dateReceived(getDateReceived(row, name2Index))
				//
				.externalResourceURL(FileMakerDataHelper.getValue(getValueRequest.fieldName(RECEIPT_EXTERNAL_RESOURCE_URL.getName()).build()))
				.build();
	}


	private JsonCreateCustomerReturnInfo createCustomerReturnInfo(@NonNull final ROW row,@NonNull final Map<String, Integer> name2Index, @NonNull final PropertiesComponent propertiesComponent)
	{
		final FileMakerDataHelper.GetValueRequest.GetValueRequestBuilder getValueRequest = FileMakerDataHelper.GetValueRequest.builder()
				.row(row)
				.fieldName2Index(name2Index);

		final String productSearchKey = getProductSearchKey(getValueRequest.fieldName(PRODUCT_SEARCH_KEY.getName()).build());

		if (StringUtils.isBlank(productSearchKey))
		{
			throw new RuntimeException("Missing mandatory filed: productSearchKey! mapping for " + PRODUCT_SEARCH_KEY.getName() + " !");
		}

		final BigDecimal returnedQty = FileMakerDataHelper.getBigDecimalValue(getValueRequest.fieldName(MOVEMENT_QUANTITY.getName()).build());

		if (returnedQty == null)
		{
			throw new RuntimeException("Missing mandatory filed: movementQty! mapping for " + MOVEMENT_QUANTITY.getName() + " !");
		}

		return JsonCreateCustomerReturnInfo.builder()
				.externalId(StringUtils.trimToNull(FileMakerDataHelper.getValue(getValueRequest.fieldName(EXTERNAL_ID.getName()).build())))
				.productSearchKey(productSearchKey)
				.movementQuantity(returnedQty)
				.shipmentScheduleId(getShipmentScheduleId(row, name2Index))
				.shipmentDocumentNumber(StringUtils.trimToNull(FileMakerDataHelper.getValue(getValueRequest.fieldName(SHIPMENT_DOCUMENT_NO.getName()).build())))
				.externalResourceURL(StringUtils.trimToNull(FileMakerDataHelper.getValue(getValueRequest.fieldName(RETURN_EXTERNAL_RESOURCE_URL.getName()).build())))
				.movementDate(getMovementDate(row, name2Index))
				.dateReceived(getDateReceived(row, name2Index))
				.attributes(buildAttributeInstanceList(row, name2Index))
				.orgCode(getOrgCode(row, name2Index, propertiesComponent))
				.returnedGoodsWarehouseType(getReturnGoodsWarehouseType(propertiesComponent))
				.build();
	}

	@Nullable
	private LocalDate getMovementDate(@NonNull final ROW row,@NonNull final Map<String, Integer> name2Index)
	{
		final FileMakerDataHelper.GetValueRequest request =
				FileMakerDataHelper.GetValueRequest.builder()
				.row(row)
				.fieldName2Index(name2Index)
				.fieldName(MOVEMENT_DATE.getName())
				.build();

		final String value = FileMakerDataHelper.getValue(request);

		return XmlToJsonProcessorUtil.asLocalDate(value, MOVEMENT_DATE_PATTERNS, MOVEMENT_DATE.getName()).orElse(null);

	}

	@Nullable
	private LocalDateTime getDateReceived(@NonNull final ROW row,@NonNull final Map<String, Integer> name2Index)
	{
		final FileMakerDataHelper.GetValueRequest request =
				FileMakerDataHelper.GetValueRequest.builder()
						.row(row)
						.fieldName2Index(name2Index)
						.fieldName(DATE_RECEIVED.getName())
						.build();

		final String dateReceived = FileMakerDataHelper.getValue(request);

		return XmlToJsonProcessorUtil.asLocalDateTime(dateReceived, DATE_RECEIVED_PATTERNS, DATE_RECEIVED.getName()).orElse(null);
	}

	@Nullable
	private List<JsonAttributeInstance> buildAttributeInstanceList(@NonNull final ROW row, @NonNull final Map<String, Integer> fieldName2Index)
	{
		final ImmutableList.Builder<JsonAttributeInstance> jsonAttributeInstances = ImmutableList.builder();

		final FileMakerDataHelper.GetValueRequest getExpiryDateReq = FileMakerDataHelper.GetValueRequest.builder()
				.row(row)
				.fieldName2Index(fieldName2Index)
				.fieldName(EXPIRY_DATE.getName())
				.build();

		final String expiryDateStr = FileMakerDataHelper.getValue(getExpiryDateReq);

		XmlToJsonProcessorUtil.asLocalDate(expiryDateStr, EXPIRY_DATE_PATTERNS, EXPIRY_DATE.getName())
				.flatMap(expiryDate -> JsonAttributeInstanceHelper.buildAttribute(AttributeCode.EXPIRY_DATE, expiryDate))
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
	private String getProductSearchKey(@NonNull final FileMakerDataHelper.GetValueRequest getValueRequest)
	{
		final String productSearchKey = FileMakerDataHelper.getValue(getValueRequest);

		if (StringUtils.isBlank(productSearchKey))
		{
			return null;
		}

		return CommonUtil.removeOrgPrefix(productSearchKey);
	}

	@Nullable
	private JsonMetasfreshId getShipmentScheduleId(@NonNull final ROW row,@NonNull final Map<String, Integer> name2Index)
	{
		final FileMakerDataHelper.GetValueRequest request =
				FileMakerDataHelper.GetValueRequest.builder()
						.row(row)
						.fieldName2Index(name2Index)
						.fieldName(SHIPMENT_SCHEDULE_ID.getName())
						.build();

		final String shipmentScheduleId = FileMakerDataHelper.getValue(request);

		if (StringUtils.isBlank(shipmentScheduleId))
		{
			return null;
		}

		return JsonMetasfreshId.of(Integer.parseInt(shipmentScheduleId));
	}

	@NonNull
	private String getReturnGoodsWarehouseType(@NonNull final PropertiesComponent propertiesComponent)
	{
		return propertiesComponent.resolveProperty(RETURN_GOODS_IN_WAREHOUSE_TYPE).orElseThrow(() -> new RuntimeException("Missing property: " + RETURN_GOODS_IN_WAREHOUSE_TYPE + "!"));
	}

	private String getOrgCode(@NonNull final ROW row, @NonNull final Map<String, Integer> name2Index, @NonNull final PropertiesComponent propertiesComponent)
	{
		final FileMakerDataHelper.GetValueRequest getValueRequest = FileMakerDataHelper.GetValueRequest.builder()
				.row(row)
				.fieldName2Index(name2Index)
				.fieldName(PRODUCT_SEARCH_KEY.getName())
				.build();

		final String productSearchKeyWithOrgId = FileMakerDataHelper.getValue(getValueRequest);

		final String orgPrefix = CommonUtil.extractOrgPrefixFromProduct(productSearchKeyWithOrgId);

		if (StringUtils.isBlank(orgPrefix))
		{
			throw new RuntimeException("Missing org prefix!");
		}

		final String orgPrefix2CodeProperty = ORG_PREFIX_TO_ORG_CODE_PROPERTY_PATTERN.replace(ORG_PREFIX_TOKEN, orgPrefix);

		final String orgCode = propertiesComponent.resolveProperty(orgPrefix2CodeProperty)
				.orElseThrow( () -> new RuntimeException("Missing property: " + orgPrefix2CodeProperty + "!"));

		return orgCode;
	}
}
