/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2024 metas GmbH
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

import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.lang.RepoIdAware;
import de.metas.util.text.tabular.Cell;
import de.metas.util.text.tabular.Row;
import de.metas.util.text.tabular.Table;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

@UtilityClass
public class TabularStringConverterUtil
{
	public Table toTableFromRows(@NonNull final List<Row> rows)
	{
		final Table table = new Table();
		table.addRows(rows);
		table.removeColumnsWithBlankValues();
		table.updateHeaderFromRows();
		return table;
	}

	public <T extends RepoIdAware> Cell toCell(
			@Nullable final T id,
			@NonNull final StepDefDataGetIdAware<T, ?> lookupTable)
	{
		if (id == null)
		{
			return Cell.NULL;
		}

		final String value = lookupTable.getFirstIdentifierById(id)
				.map(StepDefDataIdentifier::getAsString)
				.orElseGet(() -> String.valueOf(id.getRepoId()));
		return Cell.ofNullable(value);
	}

	public Cell toQtyCell(final BigDecimal valueBD, final int uomRepoId)
	{
		if (uomRepoId <= 0)
		{
			return Cell.ofNullable(valueBD);
		}

		final Quantity qty = Quantitys.create(valueBD, UomId.ofRepoId(uomRepoId));
		return Cell.ofNullable(qty);
	}

	public Cell toBooleanCell(@Nullable final Boolean valueBoolean)
	{
		return Cell.ofNullable(valueBoolean);
	}
	
}

