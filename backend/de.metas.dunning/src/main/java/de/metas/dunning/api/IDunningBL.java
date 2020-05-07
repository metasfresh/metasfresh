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


import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrxRunConfig;
import org.adempiere.util.ISingletonService;

import de.metas.dunning.interfaces.I_C_DunningLevel;
import de.metas.dunning.model.I_C_DunningDoc;
import de.metas.dunning.model.I_C_Dunning_Candidate;
import de.metas.dunning.spi.IDunningCandidateSource;
import de.metas.dunning.spi.IDunningConfigurator;

public interface IDunningBL extends ISingletonService
{
	String EVENT_NewDunningCandidate = IDunningBL.class.getName() + "#NewDunningCandidate";

	public static final String MSG_PAID = "Paid";
	public static final String MSG_OPEN = "Open";
	
	void setDunningConfigurator(IDunningConfigurator configurator);

	IDunningConfig getDunningConfig();

	IDunningContext createDunningContext(Properties ctx, I_C_DunningLevel dunningLevel, Date dunningDate, String trxName);

	IDunningContext createDunningContext(Properties ctx, I_C_DunningLevel dunningLevel, Date dunningDate, ITrxRunConfig trxRunnerConfig, String trxName);

	IDunningContext createDunningContext(IDunningContext context, String trxName);

	/**
	 * Enforce all class invariants (i.e. rules which shall be respected ON ANY time by given {@link I_C_Dunning_Candidate} object)
	 * 
	 * @param candidate
	 */
	void validate(I_C_Dunning_Candidate candidate);

	/**
	 * Create all {@link I_C_Dunning_Candidate}s for given context.
	 * 
	 * @param context
	 * @return number of candidates created
	 */
	int createDunningCandidates(IDunningContext context);

	/**
	 * Process {@link I_C_Dunning_Candidate}s and produces {@link I_C_DunningDoc}s.
	 * 
	 * {@link IDunningConfig#createDunningCandidateSource()} will be used for creating the candidates source.
	 * 
	 * @param context
	 */
	void processCandidates(IDunningContext context);

	/**
	 * Process {@link I_C_Dunning_Candidate}s and produces {@link I_C_DunningDoc}s.
	 * 
	 * @param context
	 * @param candidates candidates source to be used
	 */
	void processCandidates(IDunningContext context, IDunningCandidateSource candidates);

	/**
	 * Get previous dunning levels of given dunning level.
	 * 
	 * For determining previous levels the {@link I_C_DunningLevel#getDaysAfterDue()} + {@link I_C_DunningLevel#getDaysBetweenDunning()} is compared.
	 * 
	 * @param level
	 * @return list of previous dunning levels.
	 */
	List<I_C_DunningLevel> getPreviousLevels(I_C_DunningLevel level);

	/**
	 * Sets the <code>Processed</code> flag of the given <code>dunningDoc</code> and sets the <code>IsDunningDocProcessed</code> flag of the dunning candidates that are the sources of dunning doc's
	 * lines.
	 * <p>
	 * Assumes that the given doc is not yet processed.
	 * 
	 * @param context
	 * @param dunningDoc
	 */
	void processDunningDoc(IDunningContext context, I_C_DunningDoc dunningDoc);

	String getSummary(I_C_Dunning_Candidate candidate);

	/**
	 * Checks if given candidate is expired. Expired means that the dunning candidate makes no sense to be in our table because the dunning is not required.
	 * 
	 * A dunning candidate is considered expired when:
	 * <ul>
	 * <li>is not processed
	 * <li>DunningDate less then given <code>dunningGraceDate</code>
	 * </ul>
	 * 
	 * @param candidate
	 * @param dunningGraceDate
	 * @return true if candidate is expired.
	 */
	boolean isExpired(I_C_Dunning_Candidate candidate, Timestamp dunningGraceDate);
	
	I_C_Dunning_Candidate getLastLevelCandidate(List<I_C_Dunning_Candidate> candidates);
}
