package de.metas.material.planning.impl;

import de.metas.material.planning.IMRPContextFactory;
import de.metas.material.planning.IMutableMRPContext;
import org.springframework.stereotype.Service;

@Service
public class MRPContextFactory implements IMRPContextFactory
{
	@Override
	public IMutableMRPContext createInitialMRPContext()
	{
		return new MRPContext();
	}

}
