package de.metas.document;

import org.compiere.model.I_C_Recurring;

import de.metas.util.ISingletonService;

public interface IRecurringBL extends ISingletonService {

	void recurringRun(I_C_Recurring recurring);
	
}
