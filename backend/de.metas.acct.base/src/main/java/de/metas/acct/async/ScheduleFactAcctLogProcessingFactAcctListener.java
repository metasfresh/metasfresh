package de.metas.acct.async;

import org.adempiere.acct.api.IFactAcctListener;
import org.compiere.model.I_Fact_Acct;

import de.metas.acct.model.I_Fact_Acct_Log;

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

/**
 * Listens {@link I_Fact_Acct} records and schedules {@link I_Fact_Acct_Log} processing (async).
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class ScheduleFactAcctLogProcessingFactAcctListener implements IFactAcctListener
{
	public static final transient ScheduleFactAcctLogProcessingFactAcctListener instance = new ScheduleFactAcctLogProcessingFactAcctListener();

	private ScheduleFactAcctLogProcessingFactAcctListener()
	{
		super();
	}

	private final void scheduleFactAcctLogProcessing(final Object document)
	{
		final FactAcctLogProcessRequest request = FactAcctLogProcessRequest.ofDocument(document);
		FactAcctLogWorkpackageProcessor.schedule(request);
	}

	@Override
	public void onBeforePost(final Object document)
	{
		scheduleFactAcctLogProcessing(document);
	}

	@Override
	public void onAfterPost(final Object document)
	{
		scheduleFactAcctLogProcessing(document);
	}

	@Override
	public void onAfterUnpost(final Object document)
	{
		scheduleFactAcctLogProcessing(document);
	}
}
