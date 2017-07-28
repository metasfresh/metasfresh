package de.metas.printing.spi.impl;

/*
 * #%L
 * de.metas.printing.base
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


import de.metas.printing.model.I_C_Print_Job_Instructions;
import de.metas.printing.spi.IPrintJobBatchMonitor;

/**
 * Null Monitor. A monitor which is doing nothing (pass-through).
 * 
 * @author tsa
 * 
 */
public final class NullPrintJobMonitor extends AbstractPrintJobMonitor
{
	private final static IPrintJobBatchMonitor NULL_BATCH_MONITOR = new IPrintJobBatchMonitor()
	{
		@Override
		public boolean printJobBeforeCreate()
		{
			final boolean continueProcessing = true;
			return continueProcessing;
		}

		@Override
		public boolean printJobCreated(I_C_Print_Job_Instructions printJobInstructions)
		{
			final boolean continueProcessing = true;
			return continueProcessing;
		}

		@Override
		public void finish()
		{
			// nothing
		}
	};

	@Override
	public IPrintJobBatchMonitor createBatchMonitor()
	{
		return NULL_BATCH_MONITOR;
	}

}
