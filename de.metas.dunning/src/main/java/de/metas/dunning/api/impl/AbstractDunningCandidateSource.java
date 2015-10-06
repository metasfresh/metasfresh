package de.metas.dunning.api.impl;

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


import org.adempiere.util.Check;

import de.metas.dunning.api.IDunningContext;
import de.metas.dunning.spi.IDunningCandidateSource;

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
