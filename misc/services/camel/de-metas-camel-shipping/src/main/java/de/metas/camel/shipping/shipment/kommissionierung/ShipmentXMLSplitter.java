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

import com.google.common.collect.ImmutableList;
import de.metas.common.filemaker.FMPXMLRESULT;
import de.metas.common.filemaker.RESULTSET;
import de.metas.common.rest_api.JsonMetasfreshId;
import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ShipmentXMLSplitter
{
	public List<FMPXMLRESULT> splitIfSameShipmentScheduleId(@NonNull final FMPXMLRESULT fmpxmlresult)
	{
		final var rows = fmpxmlresult.getResultset().getRows();
		if (rows == null)
		{
			return ImmutableList.of(fmpxmlresult);
		}

		final var metadata = fmpxmlresult.getMetadata();
		final Split split = new Split();

		for (final var row : rows)
		{
			final var rowWrapper = ShipmentXmlRowWrapper.wrap(row, metadata);
			split.add(rowWrapper);
		}

		final var splitResults = ImmutableList.<FMPXMLRESULT>builder();
		int splitCounter = 1;
		for (final Map<JsonMetasfreshId, ShipmentXmlRowWrapper> splitItem : split.getSplitList())
		{
			final var resultSet = RESULTSET.builder();
			for (final var rowWrapper : splitItem.values())
			{
				if (splitCounter > 1)
				{
					// we did some actual splitting => deduplicate the documentNo
					final var modifiedRow = rowWrapper.appendToDocumentNo("-" + splitCounter);
					resultSet.row(modifiedRow);
				}
				else
				{
					resultSet.row(rowWrapper.getRow());
				}

			}

			final var splitResult = fmpxmlresult.toBuilder().resultset(resultSet.build()).build();
			splitResults.add(splitResult);
			splitCounter++;
		}
		return splitResults.build();
	}

	private static class Split
	{
		@Getter
		private final List<Map<JsonMetasfreshId, ShipmentXmlRowWrapper>> splitList = new ArrayList<>();

		private void add(@NonNull final ShipmentXmlRowWrapper rowWrapper)
		{
			for (final Map<JsonMetasfreshId, ShipmentXmlRowWrapper> splitItem : splitList)
			{
				if (!splitItem.containsKey(rowWrapper.getShipmentScheduleId()))
				{
					splitItem.put(rowWrapper.getShipmentScheduleId(), rowWrapper);
					return; // adding done
				}
			}

			// there is no HashMap<JsonMetasfreshId, ShipmentXmlRowWrapper> that does not already have the rowWrapper's ShipmentScheduleId
			// => create a new one
			final LinkedHashMap<JsonMetasfreshId, ShipmentXmlRowWrapper> newSplitItem = new LinkedHashMap<>();
			newSplitItem.put(rowWrapper.getShipmentScheduleId(), rowWrapper);
			splitList.add(newSplitItem);
		}
	}
}
