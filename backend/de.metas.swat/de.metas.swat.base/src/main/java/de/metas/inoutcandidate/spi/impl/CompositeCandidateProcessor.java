package de.metas.inoutcandidate.spi.impl;

/*
 * #%L
 * de.metas.swat.base
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

import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;

import org.adempiere.inout.util.IShipmentSchedulesDuringUpdate;
import org.slf4j.Logger;

import de.metas.inoutcandidate.spi.IShipmentSchedulesAfterFirstPassUpdater;
import de.metas.logging.LogManager;
import lombok.NonNull;
import lombok.ToString;

@ToString
public final class CompositeCandidateProcessor implements IShipmentSchedulesAfterFirstPassUpdater
{
	private final static Logger logger = LogManager.getLogger(CompositeCandidateProcessor.class);

	private final CopyOnWriteArrayList<IShipmentSchedulesAfterFirstPassUpdater> processors = new CopyOnWriteArrayList<IShipmentSchedulesAfterFirstPassUpdater>();

	public void addCandidateProcessor(@NonNull final IShipmentSchedulesAfterFirstPassUpdater processor)
	{
		if (!processors.addIfAbsent(processor))
		{
			throw new IllegalStateException(processor + " has already been added");
		}
	}

	@Override
	public void doUpdateAfterFirstPass(Properties ctx, @NonNull final IShipmentSchedulesDuringUpdate candidates)
	{
		for (final IShipmentSchedulesAfterFirstPassUpdater processor : processors)
		{
			logger.debug("Invoking {}", processor);
			processor.doUpdateAfterFirstPass(ctx, candidates);
		}
	}
}
