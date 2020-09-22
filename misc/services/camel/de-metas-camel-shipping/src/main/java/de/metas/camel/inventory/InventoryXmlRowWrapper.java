package de.metas.camel.inventory;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.google.common.collect.ImmutableSet;

import de.metas.camel.shipping.XmlToJsonProcessorUtil;
import de.metas.common.filemaker.METADATA;
import de.metas.common.filemaker.ROW;
import lombok.NonNull;

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

class InventoryXmlRowWrapper
{
	public static InventoryXmlRowWrapper wrap(
			@NonNull final ROW row,
			@NonNull final METADATA metadata)
	{
		return new InventoryXmlRowWrapper(row, metadata);
	}

	private final ROW row;
	private final METADATA metadata;

	private InventoryXmlRowWrapper(
			@NonNull final ROW row,
			@NonNull final METADATA metadata)
	{
		this.row = row;
		this.metadata = metadata;
	}

	/** Qty Count */
	public BigDecimal get_artikel_menge_lagerplatz()
	{
		return metadata.getCell(row, "_artikel_menge_lagerplatz").getValueAsBigDecimal();
	}

	/** Product Number */
	public String get_artikel_nummer()
	{
		return metadata.getCell(row, "_artikel_nummer").getValueAsString();
	}

	/** Movement Date */
	public LocalDate get_lagerscan_datum()
	{
		return getValueAsLocalDate("_lagerscan_datum");
	}

	private LocalDate getValueAsLocalDate(final String fieldName)
	{
		final String valueStr = metadata.getCell(row, fieldName).getValueAsString();
		if (valueStr == null || valueStr.trim().isEmpty())
		{
			return null;
		}

		return XmlToJsonProcessorUtil.asLocalDate(
				valueStr,
				ImmutableSet.of("dd.MM.yyyy"),
				fieldName)
				.orElseThrow(() -> new IllegalStateException("Cannot convert `" + valueStr + "` to LocalDate"));
	}

	/** M_Inventory.ExternalLineId */
	public String get_lagerscan_id()
	{
		return metadata.getCell(row, "_lagerscan_id").getValueAsString();
	}

	/** M_Locator.Value */
	public String get_lagerscan_lagerplatz()
	{
		return metadata.getCell(row, "_lagerscan_lagerplatz").getValueAsString();
	}

	/** Best Before Date */
	public LocalDate get_wareneingang_mhd_ablauf_datum()
	{
		return getValueAsLocalDate("_wareneingang_mhd_ablauf_datum");
	}

	/** Lot Number */
	public String get_wareneingang_mhd_charge()
	{
		return metadata.getCell(row, "_wareneingang_mhd_charge").getValueAsString();
	}
}
