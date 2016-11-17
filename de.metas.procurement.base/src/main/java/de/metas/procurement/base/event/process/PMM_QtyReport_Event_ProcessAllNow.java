package de.metas.procurement.base.event.process;

import de.metas.process.JavaProcess;
import de.metas.procurement.base.event.impl.PMMQtyReportEventsProcessor;

/*
 * #%L
 * de.metas.procurement.base
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

public class PMM_QtyReport_Event_ProcessAllNow extends JavaProcess
{

	@Override
	protected String doIt() throws Exception
	{
		PMMQtyReportEventsProcessor.newInstance().processAll();
		return MSG_OK;
	}
}
