package de.metas.camel.inventory;

import com.google.common.collect.ImmutableList;
import de.metas.camel.shipping.CommonUtil;
import de.metas.camel.shipping.RouteBuilderCommonUtil;
import de.metas.common.filemaker.FMPXMLRESULT;
import de.metas.common.filemaker.METADATA;
import de.metas.common.filemaker.ROW;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.spi.PropertiesComponent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

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

class InventoryXmlToJsonProcessor implements Processor
{
	private final static Log log = LogFactory.getLog(InventoryXmlToJsonProcessor.class);

	@Override
	public void process(final Exchange exchange)
	{
		final var propertiesComponent = exchange.getContext().getPropertiesComponent();

		processExchange(exchange,
				(rawRow, metadata) -> toJsonInventoryLine(rawRow, metadata, propertiesComponent),
				lines -> toJsonInventory(lines));
	}

	private static <T, R> void processExchange(
			final Exchange exchange,
			final BiFunction<ROW, METADATA, T> itemBuilder,
			final Function<List<T>, R> requestBuilder)
	{
		final FMPXMLRESULT fmpxmlresult = exchange.getIn().getBody(FMPXMLRESULT.class);

		if (fmpxmlresult == null || fmpxmlresult.isEmpty())
		{
			log.debug("exchange.body is empty! -> nothing to do!");
			exchange.getIn().setHeader(RouteBuilderCommonUtil.NUMBER_OF_ITEMS, 0);
			return; // nothing to do
		}

		final METADATA metadata = fmpxmlresult.getMetadata();

		final List<T> items = fmpxmlresult
				.getResultset()
				.getRows()
				.stream()
				.map(row -> itemBuilder.apply(row, metadata))
				.collect(ImmutableList.toImmutableList());

		final R request = requestBuilder.apply(items);

		exchange.getIn().setHeader(RouteBuilderCommonUtil.NUMBER_OF_ITEMS, items.size());
		exchange.getIn().setBody(request);
	}

	@NonNull
	private static JsonInventoryLine toJsonInventoryLine(
			@NonNull final ROW rawRow,
			@NonNull final METADATA metadata,
			@NonNull final PropertiesComponent propertiesComponent)
	{
		final InventoryXmlRowWrapper row = InventoryXmlRowWrapper.wrap(rawRow, metadata);

		final var warehouseValue = CommonUtil.extractWarehouseValue(propertiesComponent, row.get_siro_kunden_id());
		final var productValue = CommonUtil.removeOrgPrefix(row.get_artikel_nummer());

		return JsonInventoryLine.builder()
				.warehouseValue(warehouseValue)
				.locatorValue(row.get_lagerscan_lagerplatz())
				.inventoryDate(row.get_lagerscan_datum())
				.productValue(productValue)
				.qtyCount(row.get_artikel_menge_lagerplatz())
				.externalLineId(row.get_lagerscan_id())
				.bestbeforeDate(row.get_wareneingang_mhd_ablauf_datum())
				.lotNumber(row.get_wareneingang_mhd_charge())
				.build();
	}

	private static JsonInventory toJsonInventory(final List<JsonInventoryLine> lines)
	{
		return JsonInventory.builder()
				.lines(lines)
				.build();
	}

}
