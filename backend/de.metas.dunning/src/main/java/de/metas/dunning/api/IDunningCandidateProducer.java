package de.metas.dunning.api;

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


import de.metas.dunning.model.I_C_Dunning_Candidate;

/**
 * Dunning Candidate Producer is responsible for creating {@link I_C_Dunning_Candidate} for {@link IDunnableDoc}.
 * 
 * It shall do:
 * <ul>
 * <li>if there is no candidate for given {@link IDunnableDoc} and Dunning Level, it creates it
 * <li>if there is a candidate for given {@link IDunnableDoc} and Dunning Level, and if is not processed, it recreates/updates it
 * <li>if the corresponding candidate is already processed, it skips it and logs a notification (somewhere)
 * </ul>
 * 
 * @author tsa
 * 
 */
public interface IDunningCandidateProducer
{
	/**
	 * 
	 * @param sourceDoc
	 * @return true if the sourceDoc is handled by this producer
	 */
	boolean isHandled(IDunnableDoc sourceDoc);

	/**
	 * Creates or updates dunning candidates for the given <code>context</code> and <code>sourceDoc</code>. This method assumes that there is only one dunning candidate per source doc and dunning
	 * level.
	 *
	 * <p>
	 * Some notes about specific fields
	 * <ul>
	 * <li>{@link I_C_Dunning_Candidate#COLUMNNAME_DunningInterestAmt}: First, the candidate's DunningInterestAmt is computed as <code>OpenAmt</code> times
	 * <code>C_DunningLevel.InterestPercent/100</code> and then it is converted from the sourceDoc's currency to the dunning's currency.
	 * <p>
	 * Note: as far as i could see, the old dunning system never computed an interest amount</li>
	 * <li>{@link I_C_Dunning_Candidate#COLUMNNAME_FeeAmt}: Value is taken from the dunning level (which in turn is provided with the context)</li>
	 * </ul>
	 * 
	 * @param context
	 * @param sourceDoc
	 * @return
	 */
	I_C_Dunning_Candidate createDunningCandidate(IDunningContext context, IDunnableDoc sourceDoc);
}
