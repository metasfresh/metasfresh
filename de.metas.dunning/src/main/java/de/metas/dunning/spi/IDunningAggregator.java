package de.metas.dunning.spi;

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


import de.metas.dunning.api.IDunningProducer;
import de.metas.dunning.model.I_C_Dunning_Candidate;

/**
 * Implementations are registered to the {@link IDunningProducer} and they are called during a dunning run. They decide
 * if a given {@link I_C_Dunning_Candidate} should be aggregated with others or not
 * 
 * @author ts
 * 
 */
public interface IDunningAggregator
{
	public enum Result
	{
		YES, NO, I_FUCKING_DONT_CARE
	}

	Result isNewDunningDoc(I_C_Dunning_Candidate candidate);

	Result isNewDunningDocLine(I_C_Dunning_Candidate candidate);

}
