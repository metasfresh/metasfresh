package de.metas.dunning.api;

import de.metas.dunning.invoice.api.IInvoiceSourceBL.DunningDocLineSourceEvent;
import de.metas.dunning.model.I_C_DunningDoc_Line_Source;
import de.metas.dunning.model.I_C_Dunning_Candidate;
import de.metas.dunning.spi.IDunningCandidateListener;
import de.metas.dunning.spi.IDunningDocLineSourceListener;
import de.metas.util.ISingletonService;

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
