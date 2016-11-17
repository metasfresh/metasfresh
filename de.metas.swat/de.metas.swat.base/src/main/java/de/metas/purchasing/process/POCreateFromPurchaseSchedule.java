package de.metas.purchasing.process;

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


import org.adempiere.util.Services;

import de.metas.process.JavaProcess;
import de.metas.purchasing.service.IPurchaseScheduleBL;
public final class POCreateFromPurchaseSchedule extends JavaProcess
{

	@Override
	protected final String doIt() throws Exception
	{
		final IPurchaseScheduleBL blService = Services.get(IPurchaseScheduleBL.class);
		blService.createPOs(getCtx(), this, get_TrxName());
		
		return "@Success@";
	}

	@Override
	protected final void prepare()
	{
		// nothing to do
	}
}
