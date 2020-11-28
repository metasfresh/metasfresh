package de.metas.workflow.api;

import de.metas.util.ISingletonService;
import de.metas.workflow.model.I_C_Doc_Responsible;

public interface IWorkflowDAO extends ISingletonService
{
	I_C_Doc_Responsible retrieveDocResponsible(Object doc);
}
