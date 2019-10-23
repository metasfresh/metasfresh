package org.adempiere.process.event;

import de.metas.util.ISingletonService;


public interface IProcessEventSupport extends ISingletonService {

	void addListener(IProcessEventListener listener);

	void removeListener(IProcessEventListener listener);
	
	void fireProcessEvent(ProcessEvent event);
	
}
