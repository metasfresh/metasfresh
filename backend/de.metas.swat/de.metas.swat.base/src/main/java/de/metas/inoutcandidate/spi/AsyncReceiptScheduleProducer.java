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

import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.inoutcandidate.async.GenerateReceiptScheduleWorkpackageProcessor;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.util.Check;
import de.metas.util.Services;

import javax.annotation.Nullable;

public class AsyncReceiptScheduleProducer extends AbstractReceiptScheduleProducer
{

	@Override
	@Nullable
	public List<I_M_ReceiptSchedule> createOrUpdateReceiptSchedules(@NonNull final Object model, final List<I_M_ReceiptSchedule> previousSchedules)
	{
		Check.assume(previousSchedules.isEmpty(), "Calling async receipt schedule producer is allowed only when there are no other schedules produced previously");

		final Properties ctx = InterfaceWrapperHelper.getCtx(model);

		final IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);
		final IWorkPackageQueue queue = workPackageQueueFactory.getQueueForEnqueuing(ctx, GenerateReceiptScheduleWorkpackageProcessor.class);

		queue.enqueueElement(model);

		return null;
	}
	
	@Override
	public void updateReceiptSchedules(final Object model)
	{
		throw new IllegalStateException("Update receipt schedules is supported to run synchronously");
	}

	@Override
	public void inactivateReceiptSchedules(final Object model)
	{
		throw new IllegalStateException("Inactivation is supported to run synchronously");
	}

}
