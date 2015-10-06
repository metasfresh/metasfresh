package de.metas.inoutcandidate.api;

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

import org.adempiere.util.ISingletonService;

import de.metas.inoutcandidate.model.I_M_IolCandHandler_Log;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;

public interface IInOutCandHandlerDAO extends ISingletonService
{

	/**
	 * Retrum a list with all <code>M_IolCandHandler_Log</code> that refer to the same record (via <code>AD_Table_ID</code> and <code>Record_ID</code>) as the given schedule. Do not filter by
	 * <code>IsActive='Y'</code>.
	 * 
	 * @param shipmentSchedule
	 * @return
	 */
	List<I_M_IolCandHandler_Log> retrieveAllHandlerLogs(I_M_ShipmentSchedule shipmentSchedule);

}
