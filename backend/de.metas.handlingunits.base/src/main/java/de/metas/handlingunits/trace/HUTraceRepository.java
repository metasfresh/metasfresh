package de.metas.handlingunits.trace;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.util.List;

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.util.Check;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import de.metas.handlingunits.model.I_M_HU_Trace;
import de.metas.handlingunits.trace.repository.HuTraceEventToDbRecordUtil;
import de.metas.handlingunits.trace.repository.RetrieveDbRecordsUtil;
import de.metas.logging.LogManager;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2017 metas GmbH
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
@Service
public class HUTraceRepository
{
	private static final Logger logger = LogManager.getLogger(HUTraceRepository.class);

	/**
	 * Persists the given event.<br>
	 * If an event with the same properties was already persisted earlier,
	 * then that record is loaded and logged, but nothing more is done.
	 *
	 * @return {@code true} if a new record was inserted, {@code false} if an existing one was updated.
	 */
	public boolean addEvent(@NonNull final HUTraceEvent huTraceEvent)
	{
		final HUTraceEventQuery query = huTraceEvent.asQueryBuilder().build();

		final List<HUTraceEvent> existingDbRecords = RetrieveDbRecordsUtil.query(query);
		final boolean inserted = existingDbRecords.isEmpty();

		if (inserted)
		{
			final I_M_HU_Trace dbRecord = newInstance(I_M_HU_Trace.class);
			logger.info("Found no existing M_HU_Trace record; creating new one; query={}", query);

			HuTraceEventToDbRecordUtil.copyToDbRecord(huTraceEvent, dbRecord);
			save(dbRecord);
		}
		else
		{
			Check.errorIf(existingDbRecords.size() > 1,
					"Expected only one M_HU_Trace record for the given query, but found {}; query={}, M_HU_Trace records={}",
					existingDbRecords.size(), query, existingDbRecords);

			HUTraceEvent existingHuTraceEvent = existingDbRecords.get(0);
			logger.info("Found exiting HUTraceEvent record with ID={}; nothing to do; query={}", existingHuTraceEvent.getHuTraceEventId().getAsInt(), query);
		}

		return inserted;
	}

	/**
	 * Return records according to the given specification.
	 * <p>
	 * <b>Important:</b> if the specification is "empty", i.e. if it specifies no conditions, then return an empty list to prevent an {@code OutOfMemoryError}.
	 */
	public List<HUTraceEvent> query(@NonNull final HUTraceEventQuery query)
	{
		return RetrieveDbRecordsUtil.query(query);
	}

	/**
	 * Similar to {@link #query(HUTraceEventQuery)}, but returns an ID that can be used with {@link IQueryBuilder#setOnlySelection(int)} to retrieve the query result.
	 */
	public int queryToSelection(@NonNull final HUTraceEventQuery query)
	{
		return RetrieveDbRecordsUtil.queryToSelection(query);
	}
}
