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

import de.metas.camel.shipping.ProcessXmlToJsonRequest;
import de.metas.camel.shipping.XmlToJsonProcessorUtil;
import de.metas.common.customerreturns.JsonCreateCustomerReturnInfo;
import de.metas.common.filemaker.RESULTSET;
import de.metas.common.receipt.JsonCreateReceiptInfo;
import de.metas.common.receipt.JsonCreateReceiptsRequest;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.spi.PropertiesComponent;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static de.metas.camel.shipping.receipt.ReceiptReturnField.EXTERNAL_ID;
import static de.metas.camel.shipping.receipt.ReceiptReturnField.MOVEMENT_QUANTITY;
import static de.metas.camel.shipping.receipt.ReceiptReturnField.PRODUCT_SEARCH_KEY;
import static de.metas.camel.shipping.receipt.ReceiptReturnField.RECEIPT_EXTERNAL_RESOURCE_URL;
import static de.metas.camel.shipping.receipt.ReceiptReturnField.RETURN_EXTERNAL_RESOURCE_URL;
import static de.metas.camel.shipping.receipt.ReceiptReturnField.SHIPMENT_DOCUMENT_NO;

public class ReceiptXmlToJsonProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange)
	{
		XmlToJsonProcessorUtil.processExchange(exchange, this::buildCreateReceiptsRequest);
	}

	@NonNull
	private JsonCreateReceiptsRequest buildCreateReceiptsRequest(@NonNull final ProcessXmlToJsonRequest request)
	{
		final RESULTSET resultset = request.getResultset();
		final var metadata = request.getMetadata();
		final PropertiesComponent propertiesComponent = request.getPropertiesComponent();

		final List<JsonCreateReceiptInfo> createReceiptInfos = resultset.getRows()
				.stream()
				.map(row -> ReceiptXmlRowWrapper.wrap(row, metadata))
				.filter(rowWrapper -> !rowWrapper.isReturn())
				.map(rowWrapper -> createReceiptInfo(rowWrapper, propertiesComponent))
				.collect(Collectors.toList());

		final List<JsonCreateCustomerReturnInfo> customerReturnInfos = resultset.getRows()
				.stream()
				.map(row -> ReceiptXmlRowWrapper.wrap(row, metadata))
				.filter(ReceiptXmlRowWrapper::isReturn)
				.map(rowWrapper -> createCustomerReturnInfo(rowWrapper, propertiesComponent))
				.collect(Collectors.toList());

		return JsonCreateReceiptsRequest.builder()
				.jsonCreateReceiptInfoList(createReceiptInfos)
				.jsonCreateCustomerReturnInfoList(customerReturnInfos)
				.build();
	}

	@NonNull
	private JsonCreateReceiptInfo createReceiptInfo(@NonNull final ReceiptXmlRowWrapper rowWrapper,
			@NonNull final PropertiesComponent propertiesComponent)
	{


		return JsonCreateReceiptInfo.builder()
				.receiptScheduleId(rowWrapper.getReceiptScheduleId())
				//
				.externalId(StringUtils.trimToNull(rowWrapper.getValueByName(EXTERNAL_ID)))
				//
				.productSearchKey(rowWrapper.getProductSearchKey())
				//
				.movementQuantity(rowWrapper.getMovementQty(propertiesComponent))
				//
				.attributes(rowWrapper.buildAttributeInstanceList())
				//
				.movementDate(rowWrapper.getMovementDate())
				//
				.dateReceived(rowWrapper.getDateReceived())
				//
				.externalResourceURL(rowWrapper.getValueByName(RECEIPT_EXTERNAL_RESOURCE_URL))
				.build();
	}

	private JsonCreateCustomerReturnInfo createCustomerReturnInfo(@NonNull final ReceiptXmlRowWrapper rowWrapper,
			@NonNull final PropertiesComponent propertiesComponent)
	{
		final String productSearchKey = rowWrapper.getProductSearchKey();

		if (StringUtils.isBlank(productSearchKey))
		{
			throw new RuntimeException("Missing mandatory filed: productSearchKey! mapping for " + PRODUCT_SEARCH_KEY.getName() + " !");
		}

		final BigDecimal returnedQty = rowWrapper.getMovementQty(propertiesComponent);

		if (returnedQty == null)
		{
			throw new RuntimeException("Missing mandatory filed: movementQty! mapping for " + MOVEMENT_QUANTITY.getName() + " !");
		}

		return JsonCreateCustomerReturnInfo.builder()
				.externalId(StringUtils.trimToNull(rowWrapper.getValueByName(EXTERNAL_ID)))
				.productSearchKey(productSearchKey)
				.movementQuantity(returnedQty)
				.shipmentScheduleId(rowWrapper.getShipmentScheduleId())
				.shipmentDocumentNumber(StringUtils.trimToNull(rowWrapper.getValueByName(SHIPMENT_DOCUMENT_NO)))
				.externalResourceURL(StringUtils.trimToNull(rowWrapper.getValueByName(RETURN_EXTERNAL_RESOURCE_URL)))
				.movementDate(rowWrapper.getMovementDate())
				.dateReceived(rowWrapper.getDateReceived())
				.attributes(rowWrapper.buildAttributeInstanceList())
				.orgCode(rowWrapper.getOrgCode(propertiesComponent))
				.returnedGoodsWarehouseType(rowWrapper.getReturnGoodsWarehouseType(propertiesComponent))
				.build();
	}

}
