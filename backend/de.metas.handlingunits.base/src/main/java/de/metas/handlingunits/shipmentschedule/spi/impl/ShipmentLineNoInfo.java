package de.metas.handlingunits.shipmentschedule.spi.impl;

import static de.metas.common.util.CoalesceUtil.coalesce;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;

import de.metas.inout.InOutLineId;
import de.metas.logging.LogManager;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.handlingunits.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/** One instance is passed between {@link ShipmentLineBuilder}s to detect {@code LineNo} colisions. */
@ToString
@EqualsAndHashCode
@VisibleForTesting
public class ShipmentLineNoInfo
{
	private static final Logger logger = LogManager.getLogger(ShipmentLineNoInfo.class);

	private final Map<InOutLineId, Integer> shipmentLineIdToLineNo = new HashMap<>();
	private final Multimap<Integer, InOutLineId> lineNoToShipmentLineId = MultimapBuilder.hashKeys().arrayListValues().build();

	public boolean put(@NonNull final InOutLineId shipmentLineId, @NonNull final Integer lineNo)
	{
		if (lineNo <= 0)
		{
			logger.debug("Ignoring lineNo={} is associated with shipmentLineId={}; -> ignore, i.e. no collission; return true", lineNo, shipmentLineId);
			return true;
		}
		shipmentLineIdToLineNo.put(shipmentLineId, lineNo);
		lineNoToShipmentLineId.put(lineNo, shipmentLineId);

		if (lineNoToShipmentLineId.get(lineNo).size() > 1)
		{
			logger.debug("LineNo={} is associated with multiple shipmentLineIds={}; -> collision detected; return false", lineNo, lineNoToShipmentLineId.get(lineNo));
			return false;
		}

		logger.debug("LineNo={} is associated only with shipmentLineId={} so far; -> no collision; return true", lineNo, shipmentLineId);
		return true;
	}

	public int getLineNoFor(@NonNull final InOutLineId shipmentLineId)
	{
		return coalesce(shipmentLineIdToLineNo.get(shipmentLineId), 0);
	}

	public ImmutableList<InOutLineId> getShipmentLineIdsWithLineNoCollisions()
	{
		final ImmutableList.Builder<InOutLineId> result = ImmutableList.builder();

		for (final Entry<Integer, Collection<InOutLineId>> entry : lineNoToShipmentLineId.asMap().entrySet())
		{
			final Collection<InOutLineId> shipmentLineIds = entry.getValue();
			if (shipmentLineIds.size() > 1)
			{
				result.addAll(shipmentLineIds);
			}
		}
		return result.build();
	}
}
