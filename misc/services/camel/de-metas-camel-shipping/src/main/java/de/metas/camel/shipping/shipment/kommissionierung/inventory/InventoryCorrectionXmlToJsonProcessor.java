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
import de.metas.camel.shipping.shipment.kommissionierung.ShipmentField;
import de.metas.camel.shipping.shipment.kommissionierung.SiroShipmentConstants;
import de.metas.common.filemaker.FileMakerDataHelper;
import de.metas.common.filemaker.RESULTSET;
import de.metas.common.filemaker.ROW;
import de.metas.common.util.time.SystemTime;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.spi.PropertiesComponent;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
		final Map<String, Integer> name2Index = xmlToJsonRequest.getName2Index();

		final List<JsonInventoryLine> correctionInventoryLines = resultset.getRows()
				.stream()
				.map(row -> createInventoryLine(row, name2Index, xmlToJsonRequest.getPropertiesComponent()))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toList());

		return JsonInventory.builder()
				.lines(correctionInventoryLines)
				.build();
	}

	private Optional<JsonInventoryLine> createInventoryLine(@NonNull final ROW row, @NonNull final Map<String, Integer> fieldName2Index, @NonNull final PropertiesComponent propertiesComponent)
	{
		final boolean isOutOfStockCorrectionRequired = isOutOfStockCorrectionRequired(row, fieldName2Index, propertiesComponent);

		if(!isOutOfStockCorrectionRequired)
		{
			return Optional.empty();
		}

		final FileMakerDataHelper.GetValueRequest.GetValueRequestBuilder request = FileMakerDataHelper.GetValueRequest.builder()
				.fieldName2Index(fieldName2Index)
				.row(row);

		final String productAndOrg = FileMakerDataHelper.getValue(request.fieldName(ShipmentField.PRODUCT_VALUE.getName()).build());
		final String bestBeforeDateStr = FileMakerDataHelper.getValue(request.fieldName(ShipmentField.BEST_BEFORE_DATE.getName()).build());

		final LocalDate bestBeforeDate = XmlToJsonProcessorUtil.asLocalDate(bestBeforeDateStr, SiroShipmentConstants.EXPIRY_DATE_PATTERNS, ShipmentField.BEST_BEFORE_DATE.getName())
				.orElse(null);

		final JsonInventoryLine inventoryLine = JsonInventoryLine.builder()
				.locatorValue(FileMakerDataHelper.getValue(request.fieldName(ShipmentField.OUT_OF_STOCK_LOCATOR.getName()).build()))
				//
				.inventoryDate(SystemTime.asLocalDate()) // we don't have a specific field for this
				//
				.productValue(CommonUtil.removeOrgPrefix(productAndOrg))
				//
				.qtyCount(BigDecimal.ZERO)//out of stock
				//
				.bestbeforeDate(bestBeforeDate)
				//
				.lotNumber(FileMakerDataHelper.getValue(request.fieldName(ShipmentField.LOT_NUMBER.getName()).build()))
				//
				.build();

		return Optional.of(inventoryLine);
	}

	public static boolean isOutOfStockCorrectionRequired(@NonNull final ROW row, @NonNull final Map<String, Integer> fieldName2Index, @NonNull final PropertiesComponent propertiesComponent)
	{
		final FileMakerDataHelper.GetValueRequest request = FileMakerDataHelper.GetValueRequest.builder()
				.row(row)
				.fieldName2Index(fieldName2Index)
				.fieldName(ShipmentField.IS_OUT_OF_STOCK.getName())
				.build();

		final Locale locale = XmlToJsonProcessorUtil.getLocale(propertiesComponent);

		final String outOfStockValue = FileMakerDataHelper.getValue(request);

		final BigDecimal outOfStockBD = XmlToJsonProcessorUtil.toBigDecimalOrNull(outOfStockValue, locale);

		if (outOfStockBD == null)
		{
			return false;
		}

		return outOfStockBD.compareTo(BigDecimal.ZERO) <= 0;
	}
}
