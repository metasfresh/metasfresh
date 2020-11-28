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


import java.util.Iterator;

import de.metas.dunning.api.IDunningDAO;
import de.metas.dunning.model.I_C_Dunning_Candidate;
import de.metas.util.Services;

/**
 * Default Dunning Candidates Source.
 * 
 * It's a simple implementation which fetch from database all {@link I_C_Dunning_Candidate}s which are not yet processed.
 * 
 * @author tsa
 * 
 */
public class DefaultDunningCandidateSource extends AbstractDunningCandidateSource
{
	@Override
	public Iterator<I_C_Dunning_Candidate> iterator()
	{
		final IDunningDAO dunningDAO = Services.get(IDunningDAO.class);
		final Iterator<I_C_Dunning_Candidate> it = dunningDAO.retrieveNotProcessedCandidatesIterator(getDunningContext());
		return it;
	}
}
