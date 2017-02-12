package de.metas.inoutcandidate.spi;

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


import java.util.List;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;

import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.inoutcandidate.async.GenerateReceiptScheduleWorkpackageProcessor;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;

public class AsyncReceiptScheduleProducer extends AbstractReceiptScheduleProducer
{

	@Override
	public List<I_M_ReceiptSchedule> createOrUpdateReceiptSchedules(final Object model, final List<I_M_ReceiptSchedule> previousSchedules)
	{
		Check.assumeNotNull(model, "model not null");
		Check.assume(previousSchedules.isEmpty(), "Calling async receipt schedule producer is allowed only when there are no other schedules produced previously");

		final Properties ctx = InterfaceWrapperHelper.getCtx(model);

		final IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);
		final IWorkPackageQueue queue = workPackageQueueFactory.getQueueForEnqueuing(ctx, GenerateReceiptScheduleWorkpackageProcessor.class);

		queue.enqueueElement(model);

		return null;
	}

	@Override
	public void inactivateReceiptSchedules(Object model)
	{
		throw new IllegalStateException("Inactivation is supported to run synchronously");
	}

}
