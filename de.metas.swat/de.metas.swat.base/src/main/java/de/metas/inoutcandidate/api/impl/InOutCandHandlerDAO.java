package de.metas.inoutcandidate.api.impl;

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

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Services;

import de.metas.inoutcandidate.api.IInOutCandHandlerDAO;
import de.metas.inoutcandidate.model.I_M_IolCandHandler_Log;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;

public class InOutCandHandlerDAO implements IInOutCandHandlerDAO
{

	@Override
	public List<I_M_IolCandHandler_Log> retrieveAllHandlerLogs(final I_M_ShipmentSchedule shipmentSchedule)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_IolCandHandler_Log.class)
				.setContext(shipmentSchedule)
				.addEqualsFilter(I_M_IolCandHandler_Log.COLUMN_AD_Table_ID, shipmentSchedule.getAD_Table_ID())
				.addEqualsFilter(I_M_IolCandHandler_Log.COLUMN_Record_ID, shipmentSchedule.getRecord_ID())
				.create()
				.list();
	}
}
