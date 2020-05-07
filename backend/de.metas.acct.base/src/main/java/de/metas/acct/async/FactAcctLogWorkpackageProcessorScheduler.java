package de.metas.acct.async;

import java.util.Properties;

import de.metas.async.spi.WorkpackagesOnCommitSchedulerTemplate;

/*
 * #%L
 * de.metas.acct
 * %%
 * Copyright (C) 2016 metas GmbH
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

final class FactAcctLogWorkpackageProcessorScheduler extends WorkpackagesOnCommitSchedulerTemplate<FactAcctLogProcessRequest>
{
	public FactAcctLogWorkpackageProcessorScheduler()
	{
		super(FactAcctLogWorkpackageProcessor.class);
	}

	@Override
	protected Properties extractCtxFromItem(final FactAcctLogProcessRequest item)
	{
		return item.getCtx();
	}

	@Override
	protected String extractTrxNameFromItem(final FactAcctLogProcessRequest item)
	{
		return item.getTrxName();
	}

	@Override
	protected Object extractModelToEnqueueFromItem(final Collector collector, final FactAcctLogProcessRequest item)
	{
		return null; // there is no actual model to be collected
	}

	@Override
	protected boolean isEnqueueWorkpackageWhenNoModelsEnqueued()
	{
		return true;
	}
}
