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

import de.metas.camel.shipping.CommonUtil;
import de.metas.camel.shipping.ProcessXmlToJsonRequest;
import de.metas.camel.shipping.XmlToJsonProcessorUtil;
import de.metas.camel.shipping.shipment.SiroShipmentConstants;
import de.metas.camel.shipping.shipment.kommissionierung.inventory.InventoryCorrectionXmlToJsonProcessor;
import de.metas.common.filemaker.RESULTSET;
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

import java.util.List;
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
	private Optional<JsonCreateShipmentInfo> createShipmentInfo(
			@NonNull final ShipmentXmlRowWrapper rowWrapper,
			@NonNull final PropertiesComponent propertiesComponent)
	{
		final boolean isOutOfStockCorrectionRequired = InventoryCorrectionXmlToJsonProcessor.isOutOfStockCorrectionRequired(rowWrapper, propertiesComponent);

		if (isOutOfStockCorrectionRequired)
		{
			return Optional.empty();//will be handled in de.metas.camel.shipping.shipment.ShipmentXmlToJsonRouteBuilder.MF_SHIPMENT_INVENTORY_CORRECTION
		}

		final JsonMetasfreshId shipmentScheduleId = rowWrapper.getShipmentScheduleId();


		final JsonCreateShipmentInfo createShipmentInfo = JsonCreateShipmentInfo.builder()
				.deliveryRule(SiroShipmentConstants.DEFAULT_DELIVERY_RULE_FORCE) // this is what was shipped, no mapper what metasfresh might think at this point
				.shipmentScheduleId(shipmentScheduleId)
				.attributes(rowWrapper.getAttributes())
				.movementDate(rowWrapper.getDeliveryDate())
				.movementQuantity(rowWrapper.getMovementQty(propertiesComponent))
				.documentNo(StringUtils.trimToNull(rowWrapper.getValueByName(ShipmentField.DOCUMENT_NO)))
				.productSearchKey(StringUtils.trimToNull(CommonUtil.convertProductValue(rowWrapper.getValueByName(ShipmentField.ARTICLE_VALUE_TEMP))))
				.build();

		return Optional.of(createShipmentInfo);
	}

	private JsonCreateShipmentRequest buildCreateShipmentsRequest(@NonNull final ProcessXmlToJsonRequest xmlToJsonRequest)
	{
		final RESULTSET resultset = xmlToJsonRequest.getResultset();
		final var metadata = xmlToJsonRequest.getMetadata();

		final List<JsonCreateShipmentInfo> createShipmentInfos = resultset.getRows()
				.stream()
				.map(row -> createShipmentInfo(ShipmentXmlRowWrapper.wrap(row,metadata), xmlToJsonRequest.getPropertiesComponent()))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toList());

		return JsonCreateShipmentRequest.builder()
				.createShipmentInfoList(createShipmentInfos)
				.build();
	}

}
