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

import static de.metas.camel.shipping.XmlToJsonProcessorUtil.asLocalDate;
import static de.metas.camel.shipping.XmlToJsonProcessorUtil.asLocalDateTime;
import static de.metas.camel.shipping.XmlToJsonProcessorUtil.processExchange;
import static de.metas.camel.shipping.receipt.ReceiptField.ARTICLE_FLAVOR;
import static de.metas.camel.shipping.receipt.ReceiptField.DATE_RECEIVED;
import static de.metas.camel.shipping.receipt.ReceiptField.EXPIRY_DATE;
import static de.metas.camel.shipping.receipt.ReceiptField.EXTERNAL_ID;
import static de.metas.camel.shipping.receipt.ReceiptField.LOT_NUMBER;
import static de.metas.camel.shipping.receipt.ReceiptField.MOVEMENT_DATE;
import static de.metas.camel.shipping.receipt.ReceiptField.MOVEMENT_QUANTITY;
import static de.metas.camel.shipping.receipt.ReceiptField.PRODUCT_SEARCH_KEY;
import static de.metas.camel.shipping.receipt.ReceiptField.RECEIPT_SCHEDULE_ID;
import static de.metas.camel.shipping.receipt.SiroReceiptConstants.DATE_RECEIVED_PATTERNS;
import static de.metas.camel.shipping.receipt.SiroReceiptConstants.EXPIRY_DATE_PATTERNS;
import static de.metas.camel.shipping.receipt.SiroReceiptConstants.MOVEMENT_DATE_PATTERNS;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.ImmutableList;

import de.metas.camel.shipping.CommonUtil;
import de.metas.camel.shipping.JsonAttributeInstanceHelper;
import de.metas.camel.shipping.shipment.AttributeCode;
import de.metas.common.filemaker.FileMakerDataHelper;
import de.metas.common.filemaker.ROW;
import de.metas.common.receipt.JsonCreateReceiptInfo;
import de.metas.common.receipt.JsonCreateReceiptsRequest;
import de.metas.common.rest_api.JsonAttributeInstance;
import de.metas.common.rest_api.JsonMetasfreshId;
import lombok.NonNull;

public class ReceiptXmlToJsonProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange) throws Exception
	{
		processExchange(exchange, this::createReceiptInfo, this::buildCreateReceiptsRequest);
	}

	@NonNull
	private JsonCreateReceiptsRequest buildCreateReceiptsRequest(@NonNull final List<JsonCreateReceiptInfo> createReceiptInfos)
	{
		return JsonCreateReceiptsRequest.builder()
				.jsonCreateReceiptInfoList(createReceiptInfos)
				.build();
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

		return asLocalDate(value, MOVEMENT_DATE_PATTERNS, MOVEMENT_DATE.getName()).orElse(null);

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

		return asLocalDateTime(dateReceived, DATE_RECEIVED_PATTERNS, DATE_RECEIVED.getName()).orElse(null);
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

		asLocalDate(expiryDateStr, EXPIRY_DATE_PATTERNS, EXPIRY_DATE.getName())
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
}
