package de.metas.dunning.api.impl;

import de.metas.dunning.api.IDunningContext;
import de.metas.dunning.spi.IDunningCandidateSource;
import de.metas.util.Check;

/**
 * Abstract implementation of {@link IDunningCandidateSource} which implements common methods.
 * 
 * @author tsa
 * 
 */
public abstract class AbstractDunningCandidateSource implements IDunningCandidateSource
{
	private IDunningContext dunningContext;

	@Override
	public void setDunningContext(IDunningContext context)
	{
		Check.assume(context != null, "context is not null");
		this.dunningContext = context;
	}

	@Override
	public IDunningContext getDunningContext()
	{
		return dunningContext;
	}

}
