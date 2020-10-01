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

package de.metas.camel.shipping.shipment.kommissionierung.inventory;

import de.metas.camel.inventory.JsonInventory;
import de.metas.camel.inventory.JsonInventoryLine;
import de.metas.camel.shipping.CommonUtil;
import de.metas.camel.shipping.ProcessXmlToJsonRequest;
import de.metas.camel.shipping.XmlToJsonProcessorUtil;
import de.metas.camel.shipping.shipment.SiroShipmentConstants;
import de.metas.camel.shipping.shipment.kommissionierung.ShipmentField;
import de.metas.camel.shipping.shipment.kommissionierung.ShipmentXmlRowWrapper;
import de.metas.common.filemaker.RESULTSET;
import de.metas.common.util.time.SystemTime;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.spi.PropertiesComponent;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

public class InventoryCorrectionXmlToJsonProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange) throws Exception
	{
		XmlToJsonProcessorUtil.processExchange(exchange, this::createInventory);
	}

	private JsonInventory createInventory(@NonNull final ProcessXmlToJsonRequest xmlToJsonRequest)
	{
		final RESULTSET resultset = xmlToJsonRequest.getResultset();
		final var metadata = xmlToJsonRequest.getMetadata();

		final List<JsonInventoryLine> correctionInventoryLines = resultset.getRows()
				.stream()
				.map(row -> createInventoryLine(ShipmentXmlRowWrapper.wrap(row, metadata), xmlToJsonRequest.getPropertiesComponent()))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toList());

		return JsonInventory.builder()
				.lines(correctionInventoryLines)
				.build();
	}

	private Optional<JsonInventoryLine> createInventoryLine(@NonNull final ShipmentXmlRowWrapper rowWrapper,
			@NonNull final PropertiesComponent propertiesComponent)
	{
		final boolean isOutOfStockCorrectionRequired = isOutOfStockCorrectionRequired(rowWrapper, propertiesComponent);

		if (!isOutOfStockCorrectionRequired)
		{
			return Optional.empty();
		}

		final String productAndOrg = rowWrapper.getValueByName(ShipmentField.ARTICLE_VALUE_TEMP);
		final String bestBeforeDateStr = rowWrapper.getValueByName(ShipmentField.BEST_BEFORE_DATE);

		final LocalDate bestBeforeDate = XmlToJsonProcessorUtil.asLocalDate(bestBeforeDateStr, SiroShipmentConstants.EXPIRY_DATE_PATTERNS, ShipmentField.BEST_BEFORE_DATE.getName())
				.orElse(null);

		final JsonInventoryLine inventoryLine = JsonInventoryLine.builder()
				.locatorValue(rowWrapper.getValueByName(ShipmentField.OUT_OF_STOCK_LOCATOR))
				//
				.inventoryDate(SystemTime.asLocalDate()) // we don't have a specific field for this
				//
				.productValue(CommonUtil.removeOrgPrefix(productAndOrg))
				//
				.qtyCount(BigDecimal.ZERO)//out of stock
				//
				.bestbeforeDate(bestBeforeDate)
				//
				.lotNumber(rowWrapper.getValueByName(ShipmentField.LOT_NUMBER))
				//
				.build();

		return Optional.of(inventoryLine);
	}

	public static boolean isOutOfStockCorrectionRequired(
			@NonNull final ShipmentXmlRowWrapper rowWrapper,
			@NonNull final PropertiesComponent propertiesComponent)
	{
		final String outOfStockValue = rowWrapper.getValueByName(ShipmentField.IS_OUT_OF_STOCK);

		final Locale locale = XmlToJsonProcessorUtil.getLocale(propertiesComponent);

		final BigDecimal outOfStockBD = XmlToJsonProcessorUtil.toBigDecimalOrNull(outOfStockValue, locale);

		if (outOfStockBD == null)
		{
			return false;
		}

		return outOfStockBD.compareTo(BigDecimal.ZERO) <= 0;
	}
}
