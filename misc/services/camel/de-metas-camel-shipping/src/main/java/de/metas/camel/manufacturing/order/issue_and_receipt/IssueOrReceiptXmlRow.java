package de.metas.camel.manufacturing.order.issue_and_receipt;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.google.common.collect.ImmutableSet;

import de.metas.camel.manufacturing.order.export.MainProductOrComponent;
import de.metas.camel.shipping.XmlToJsonProcessorUtil;
import de.metas.common.filemaker.METADATA;
import de.metas.common.filemaker.ROW;
import de.metas.common.rest_api.JsonMetasfreshId;
import lombok.NonNull;
import lombok.ToString;

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

@ToString
class IssueOrReceiptXmlRow
{
	public static IssueOrReceiptXmlRow wrap(
			@NonNull final ROW row,
			@NonNull final METADATA metadata)
	{
		return new IssueOrReceiptXmlRow(row, metadata);
	}

	private final ROW row;
	private final METADATA metadata;

	private IssueOrReceiptXmlRow(
			@NonNull final ROW row,
			@NonNull final METADATA metadata)
	{
		this.row = row;
		this.metadata = metadata;
	}

	public MainProductOrComponent get_stueckliste_mutter_oder_tochter()
	{
		return metadata.getCell(row, "_stueckliste_mutter_oder_tochter").getValue(MainProductOrComponent::ofCode);
	}

	public JsonMetasfreshId get_stueckliste_id()
	{
		return JsonMetasfreshId.ofOrNull(metadata.getCell(row, "_stueckliste_id").getValueAsInteger());
	}

	public String get_artikel_nummer()
	{
		return metadata.getCell(row, "_artikel_nummer").getValueAsString();
	}

	public BigDecimal get_artikel_menge()
	{
		return metadata.getCell(row, "_artikel_menge").getValueAsBigDecimal();
	}

	public String get_stueckliste_artikelnummer()
	{
		return metadata.getCell(row, "_stueckliste_artikelnummer").getValueAsString();
	}

	public String get_vorkonfektioniertist_mhd_charge()
	{
		return metadata.getCell(row, "_vorkonfektioniertist_mhd_charge").getValueAsString();
	}

	public LocalDate get_vorkonfektioniertist_mhd_ablauf_datum()
	{
		final String fieldName = "_vorkonfektioniertist_mhd_ablauf_datum";
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

}
