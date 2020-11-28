package de.metas.dunning.exception;

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


import de.metas.dunning.api.IDunningCandidateProducer;
import de.metas.dunning.model.I_C_Dunning_Candidate;

/**
 * Exception thrown by {@link IDunningCandidateProducer} when an {@link I_C_Dunning_Candidate} has an inconsistent state.
 * 
 * @author tsa
 *
 */
public class InconsistentDunningCandidateStateException extends DunningException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8214864239266928237L;

	public InconsistentDunningCandidateStateException(final String message)
	{
		super(message);
	}
}
