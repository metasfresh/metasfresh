package de.metas.handlingunits.process.api;

import java.util.Collection;

import de.metas.process.AdProcessId;
import de.metas.util.ISingletonService;

public interface IMHUProcessDAO extends ISingletonService
{
	Collection<HUProcessDescriptor> getHUProcessDescriptors();

	HUProcessDescriptor getByProcessIdOrNull(AdProcessId processId);
}
