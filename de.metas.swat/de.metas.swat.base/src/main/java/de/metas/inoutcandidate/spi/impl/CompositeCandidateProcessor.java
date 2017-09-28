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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.adempiere.inout.util.IShipmentCandidates;
import org.adempiere.util.Check;
import de.metas.inoutcandidate.spi.ICandidateProcessor;

public final class CompositeCandidateProcessor implements ICandidateProcessor
{
	private final static Logger logger = LogManager.getLogger(CompositeCandidateProcessor.class);

	private final CopyOnWriteArrayList<ICandidateProcessor> processors = new CopyOnWriteArrayList<ICandidateProcessor>();

	public void addCandidateProcessor(final ICandidateProcessor processor)
	{
		Check.assumeNotNull(processor, "processor not null");
		if (!processors.addIfAbsent(processor))
		{
			throw new IllegalStateException(processor + " has already been added");
		}
	}

	@Override
	public int processCandidates(Properties ctx, IShipmentCandidates candidates, String trxName)
	{
		int removeCount = 0;

		for (final ICandidateProcessor processor : processors)
		{
			logger.info("Invoking {}", processor);
			final int currentCount = processor.processCandidates(ctx, candidates, trxName);

			logger.info("{} records were discarded by {}", new Object[] { currentCount, processor });
			removeCount += currentCount;
		}
		return removeCount;
	}
}
