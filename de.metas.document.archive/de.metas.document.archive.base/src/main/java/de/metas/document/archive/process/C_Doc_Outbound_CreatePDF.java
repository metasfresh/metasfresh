package de.metas.document.archive.process;

/*
 * #%L
 * de.metas.document.archive.base
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

import org.adempiere.util.Services;

import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.processor.IQueueProcessor;
import de.metas.async.processor.IQueueProcessorFactory;
import de.metas.async.processor.IQueueProcessorStatistics;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.document.archive.async.spi.impl.DocOutboundWorkpackageProcessor;
import de.metas.process.SvrProcess;

/**
 * Process all queue work packages for our {@link DocOutboundWorkpackageProcessor}.
 *
 * @author tsa
 */
public class C_Doc_Outbound_CreatePDF extends SvrProcess
{
	@Override
	protected void prepare()
	{
		// nothing
	}

	@Override
	protected String doIt() throws Exception
	{
		final Properties ctx = getCtx();

		final IWorkPackageQueue workpackageQueue = Services.get(IWorkPackageQueueFactory.class).getQueueForEnqueuing(ctx, DocOutboundWorkpackageProcessor.class);
		final IQueueProcessor queueProcessor = Services.get(IQueueProcessorFactory.class).createSynchronousQueueProcessor(workpackageQueue);

		queueProcessor.run();

		final IQueueProcessorStatistics statistics = queueProcessor.getStatisticsSnapshot();

		return "OK/Error #" + statistics.getCountProcessed() + "/" + statistics.getCountErrors();
	}
}
