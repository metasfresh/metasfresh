package de.metas.aggregation.api;

/*
 * #%L
 * de.metas.aggregation
 * %%
 * Copyright (C) 2015 metas GmbH
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

<<<<<<< HEAD
import java.util.Collection;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;

=======
import com.google.common.collect.ImmutableList;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.aggregation.api.AggregationItem.Type;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
<<<<<<< HEAD
=======
import org.compiere.model.I_M_InOut;
import org.compiere.util.Evaluatees;

import javax.annotation.Nullable;
import java.util.Collection;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

@Value
public final class Aggregation
{
	private final AggregationId id;
	private final String tableName;
	private final ImmutableList<AggregationItem> items;

	@Builder
	public Aggregation(
			@Nullable final AggregationId id,
			@NonNull final String tableName,
			@NonNull @Singular final Collection<AggregationItem> items)
	{
		Check.assumeNotEmpty(tableName, "tableName not empty");
		Check.assumeNotEmpty(items, "items not empty for aggregationId={} (tableName={})", id, tableName);

		this.id = id;
		this.tableName = tableName;
		this.items = ImmutableList.copyOf(items);
	}

	public boolean hasColumnName(final String columnName)
	{
		Check.assumeNotEmpty(columnName, "columnName not empty");

		for (final AggregationItem item : items)
		{
			if (item.getType() != Type.ModelColumn)
			{
				continue;
			}

			if (!columnName.equals(item.getColumnName()))
			{
				continue;
			}

			return true;
		}

		return false;
	}
<<<<<<< HEAD
=======

	public boolean hasInvoicePerShipmentAttribute()
	{
		return getItems().stream()
				.filter(item -> item.getType() == Type.Attribute && item.getAttribute() != null)
				.anyMatch(item -> ("@" + I_M_InOut.COLUMNNAME_M_InOut_ID + "@").equals(item.getAttribute().evaluate(Evaluatees.empty())));
	}
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
