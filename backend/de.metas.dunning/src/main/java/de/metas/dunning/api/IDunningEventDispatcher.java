package de.metas.dunning.api;

/*
 * #%L
 * de.metas.dunning
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


import org.adempiere.util.ISingletonService;

import de.metas.dunning.invoice.api.IInvoiceSourceBL.DunningDocLineSourceEvent;
import de.metas.dunning.model.I_C_DunningDoc_Line_Source;
import de.metas.dunning.model.I_C_Dunning_Candidate;
import de.metas.dunning.spi.IDunningCandidateListener;
import de.metas.dunning.spi.IDunningDocLineSourceListener;

public interface IDunningEventDispatcher extends ISingletonService
{

	/**
	 * Register an {@link IDunningCandidateListener}. If the listener was already registered, it won't be registered twice.
	 * 
	 * @param eventName
	 * @param listener
	 * @return true if listener was registered now (i.e. was not already registered in past)
	 */
	boolean registerDunningCandidateListener(String eventName, IDunningCandidateListener listener);

	/**
	 * Register an {@link IDunningDocLineSourceListener}. If the listener was already registered, it won't be registered twice.
	 * 
	 * @param eventName
	 * @param listener
	 * @return true if listener was registered now (i.e. was not already registered in past)
	 */
	boolean registerDunningDocLineSourceListener(DunningDocLineSourceEvent eventName, IDunningDocLineSourceListener listener);

	void fireDunningCandidateEvent(String eventName, I_C_Dunning_Candidate candidate);

	void fireDunningDocLineSourceEvent(DunningDocLineSourceEvent eventName, I_C_DunningDoc_Line_Source source);

}
