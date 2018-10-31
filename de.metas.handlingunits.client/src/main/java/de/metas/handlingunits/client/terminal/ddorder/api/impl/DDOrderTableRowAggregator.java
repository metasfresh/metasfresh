package de.metas.handlingunits.client.terminal.ddorder.api.impl;

/*
 * #%L
 * de.metas.handlingunits.client
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eevolution.api.IDDOrderDAO;
import org.eevolution.model.I_DD_OrderLine_Alternative;

import de.metas.handlingunits.client.terminal.ddorder.model.DDOrderLineAndAlternatives;
import de.metas.handlingunits.client.terminal.ddorder.model.DDOrderTableRow;
import de.metas.handlingunits.client.terminal.ddorder.model.IDDOrderTableRow;
import de.metas.handlingunits.client.terminal.select.api.IPOSTableRow;
import de.metas.handlingunits.model.I_DD_OrderLine;
import de.metas.util.Check;
import de.metas.util.Services;

/**
 * Aggregates {@link I_DD_OrderLine}s and creates {@link IDDOrderTableRow}s.
 *
 * @author tsa
 *
 */
/* package */class DDOrderTableRowAggregator
{
	// services
	private final transient IDDOrderDAO ddOrderDAO = Services.get(IDDOrderDAO.class);

	private final Map<DDOrderTableRowAggregationKey, List<DDOrderLineAndAlternatives>> aggregatedLines = new HashMap<>();

	public DDOrderTableRowAggregator()
	{
		super();
	}

	/**
	 * Aggregate collected DD Order Lines
	 *
	 * @return {@link IDDOrderTableRow}s
	 */
	public List<IPOSTableRow> aggregate()
	{
		final List<IPOSTableRow> result = new ArrayList<>(aggregatedLines.size());
		for (final Map.Entry<DDOrderTableRowAggregationKey, List<DDOrderLineAndAlternatives>> key2ddOrderLinesAndAlternatives : aggregatedLines.entrySet())
		{
			final DDOrderTableRowAggregationKey aggregationKey = key2ddOrderLinesAndAlternatives.getKey();
			final List<DDOrderLineAndAlternatives> ddOrderLinesAndAlternatives = key2ddOrderLinesAndAlternatives.getValue();
			final IDDOrderTableRow row = new DDOrderTableRow(aggregationKey, ddOrderLinesAndAlternatives);
			result.add(row);
		}

		return result;
	}

	public List<IPOSTableRow> createNotAggregatedRows()
	{
		final List<IPOSTableRow> result = new ArrayList<>();
		for (final Map.Entry<DDOrderTableRowAggregationKey, List<DDOrderLineAndAlternatives>> key2ddOrderLinesAndAlternatives : aggregatedLines.entrySet())
		{
			final DDOrderTableRowAggregationKey aggregationKey = key2ddOrderLinesAndAlternatives.getKey();
			final List<DDOrderLineAndAlternatives> ddOrderLinesAndAlternatives = key2ddOrderLinesAndAlternatives.getValue();

			for (final DDOrderLineAndAlternatives ddOrderLinesAndAlternative : ddOrderLinesAndAlternatives)
			{
				final IDDOrderTableRow row = new DDOrderTableRow(aggregationKey, Collections.singletonList(ddOrderLinesAndAlternative));
				result.add(row);
			}
		}

		return result;
	}

	public DDOrderTableRowAggregator addDD_OrderLine(final org.eevolution.model.I_DD_OrderLine ddOrderLine)
	{
		Check.assumeNotNull(ddOrderLine, "ddOrderLine not null");

		final List<I_DD_OrderLine_Alternative> alternatives = ddOrderDAO.retrieveAllAlternatives(ddOrderLine);

		final DDOrderTableRowAggregationKey aggregationKey = new DDOrderTableRowAggregationKeyBuilder()
				.setDD_OrderLine(ddOrderLine)
				.addAlternatives(alternatives)
				.buildKey();

		List<DDOrderLineAndAlternatives> ddOrderLines = aggregatedLines.get(aggregationKey);
		if (ddOrderLines == null)
		{
			ddOrderLines = new ArrayList<DDOrderLineAndAlternatives>();
			aggregatedLines.put(aggregationKey, ddOrderLines);
		}

		final DDOrderLineAndAlternatives ddOrderLineAndAlternatives = new DDOrderLineAndAlternatives(ddOrderLine, alternatives);
		ddOrderLines.add(ddOrderLineAndAlternatives);

		return this;
	}

	public DDOrderTableRowAggregator addDD_OrderLines(final Collection<? extends org.eevolution.model.I_DD_OrderLine> ddOrderLines)
	{
		if (ddOrderLines == null || ddOrderLines.isEmpty())
		{
			return this;
		}

		for (final org.eevolution.model.I_DD_OrderLine ddOrderLine : ddOrderLines)
		{
			addDD_OrderLine(ddOrderLine);
		}

		return this;
	}

	public DDOrderTableRowAggregator addDDOrderTableRow(final IDDOrderTableRow ddOrderTableRow)
	{
		Check.assumeNotNull(ddOrderTableRow, "ddOrderTableRow not null");

		final DDOrderTableRowAggregationKey aggregationKey = ddOrderTableRow.getAggregationKey();
		final List<DDOrderLineAndAlternatives> ddOrderLinesToAdd = ddOrderTableRow.getDDOrderLineAndAlternatives();

		List<DDOrderLineAndAlternatives> ddOrderLines = aggregatedLines.get(aggregationKey);
		if (ddOrderLines == null)
		{
			ddOrderLines = new ArrayList<DDOrderLineAndAlternatives>();
			aggregatedLines.put(aggregationKey, ddOrderLines);
		}

		ddOrderLines.addAll(ddOrderLinesToAdd);

		return this;
	}

	public DDOrderTableRowAggregator addDDOrderTableRows(final Collection<IDDOrderTableRow> ddOrderTableRows)
	{
		if (ddOrderTableRows == null || ddOrderTableRows.isEmpty())
		{
			return this;
		}

		for (final IDDOrderTableRow ddOrderTableRow : ddOrderTableRows)
		{
			addDDOrderTableRow(ddOrderTableRow);
		}

		return this;
	}

}
