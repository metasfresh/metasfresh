/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.cucumber.stepdefs;

import de.metas.util.Check;
import de.metas.util.collections.CollectionUtils;
import io.cucumber.datatable.DataTable;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_Workflow;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@EqualsAndHashCode
@ToString
public class DataTableRow
{
	final Map<String, String> map;

	private DataTableRow(@NonNull final Map<String, String> map)
	{
		this.map = map;
	}

	public static List<DataTableRow> toRows(@NonNull final DataTable dataTable)
	{
		return dataTable.asMaps()
				.stream()
				.map(DataTableRow::new)
				.collect(Collectors.toList());
	}

	public static DataTableRow singleRow(@NonNull final DataTable dataTable)
	{
		return new DataTableRow(CollectionUtils.singleElement(dataTable.asMaps()));
	}

	@NonNull
	public String getAsString(@NonNull final String columnName)
	{
		return DataTableUtil.extractStringForColumnName(map, columnName);
	}

	@NonNull
	public StepDefDataIdentifier getAsIdentifier(@NonNull final String columnName)
	{
		String string = map.get(columnName);
		if (string == null && !columnName.endsWith(StepDefDataIdentifier.SUFFIX))
		{
			string = map.get(columnName + "." + StepDefDataIdentifier.SUFFIX);
		}

		if (string == null || Check.isBlank(string))
		{
			throw new AdempiereException("Missing value for columnName=" + columnName)
					.appendParametersToMessage()
					.setParameter("row", map);
		}

		return StepDefDataIdentifier.ofString(string);
	}

	public BigDecimal getAsBigDecimal(@NonNull final String columnName)
	{
		return DataTableUtil.extractBigDecimalForColumnName(map, columnName);
	}

	public int getAsInt(@NonNull final String columnName)
	{
		return DataTableUtil.extractIntForColumnName(map, columnName);
	}
}
