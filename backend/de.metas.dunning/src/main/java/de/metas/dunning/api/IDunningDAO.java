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

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.dunning.DunningDocId;
import de.metas.dunning.DunningLevel;
import de.metas.dunning.DunningLevelId;
import de.metas.dunning.api.impl.RecomputeDunningCandidatesQuery;
import de.metas.dunning.interfaces.I_C_Dunning;
import de.metas.dunning.interfaces.I_C_DunningLevel;
import de.metas.dunning.model.I_C_DunningDoc;
import de.metas.dunning.model.I_C_DunningDoc_Line;
import de.metas.dunning.model.I_C_DunningDoc_Line_Source;
import de.metas.dunning.model.I_C_Dunning_Candidate;
import de.metas.organization.OrgId;
import de.metas.util.ISingletonService;
import lombok.NonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public interface IDunningDAO extends ISingletonService
{
	/**
	 * Create a new instance for given bean interface
	 *
	 * @param dunningContext
	 * @param interfaceClass
	 * @return
	 */
	<T> T newInstance(IDunningContext dunningContext, Class<T> interfaceClass);

	/**
	 * Save bean
	 *
	 * @param model
	 */
	void save(Object model);

	List<I_C_Dunning> retrieveDunnings();

	/**
	 * Retrieves the assigned {@link I_C_Dunning} of given business partner.
	 * <p>
	 * The algorithm works as follows:
	 * <ul>
	 * <li>if bpartner has a dunning assigned, that dunning will be returned
	 * <li>if BP's group has a dunning assigned, that dunning will be returned
	 * <li>else return NULL
	 * </ul>
	 */
	I_C_Dunning retrieveDunningForBPartner(final BPartnerId bpartnerId);

	/**
	 * Retrieves default dunning for given organization.
	 *
	 * @return {@link I_C_Dunning}
	 */
	I_C_Dunning retrieveDunningByOrg(OrgId orgId);

	ImmutableList<I_C_Dunning> retrieveDunningsByOrg(OrgId orgId);

	/**
	 * Retrieve the active dunning-levels of the given <code>dunning</code>, orderd by their <code>DaysAfterDue</code> value.
	 *
	 * @param dunning
	 * @return
	 */
	List<I_C_DunningLevel> retrieveDunningLevels(I_C_Dunning dunning);

	I_C_Dunning_Candidate retrieveDunningCandidate(IDunningContext context, int tableId, int recordId, I_C_DunningLevel dunningLevel);

	I_C_Dunning_Candidate retrieveDunningCandidate(IDunningContext context, Object model, I_C_DunningLevel dunningLevel);

	/**
	 * Retrieve all {@link I_C_Dunning_Candidate}s for given tableId/recordId.
	 * <p>
	 * Same as calling {@link #retrieveDunningCandidate(IDunningContext, int, int, I_C_DunningLevel)} with empty levels list.
	 *
	 * @param context  used only for getting session specific parameters (i.e. ctx and trxName)
	 * @param tableId
	 * @param recordId
	 * @return matched {@link I_C_Dunning_Candidate}s
	 * @see #retrieveDunningCandidates(IDunningContext, int, int, List)
	 */
	List<I_C_Dunning_Candidate> retrieveDunningCandidates(IDunningContext dunningContext, int tableId, int recordId);

	/**
	 * Retrieve all {@link I_C_Dunning_Candidate}s for given tableId/recordId and dunning levels.
	 *
	 * @param context       used only for getting session specific parameters (i.e. ctx and trxName)
	 * @param tableId
	 * @param recordId
	 * @param dunningLevels if empty no C_DunningLevel_ID filter will be applied, returning candidates for all levels
	 * @return matched {@link I_C_Dunning_Candidate}s
	 */
	List<I_C_Dunning_Candidate> retrieveDunningCandidates(IDunningContext context, int tableId, int recordId, List<I_C_DunningLevel> dunningLevels);

	/**
	 * Retrieved those dunning candidates to which the given user/role has read access and that have
	 * <ul>
	 * <li>IsActive='Y'
	 * <li>Processed='Y'
	 * <li>AD_Client_ID=the given context's Client ID
	 * </ul>
	 *
	 * @param dunningContext
	 * @return
	 */
	Iterator<I_C_Dunning_Candidate> retrieveNotProcessedCandidatesIterator(IDunningContext dunningContext);

	/**
	 * Similar to {@link #retrieveNotProcessedCandidatesIterator(IDunningContext)}, but additionally
	 * <ul>
	 * <li>appends the given where clause to the final SQL query.
	 * <li>only returns records to which the given user/role has read and write access
	 * </ul>
	 *
	 * @param dunningContext
	 * @param additionalWhere
	 * @return
	 */
	Iterator<I_C_Dunning_Candidate> retrieveNotProcessedCandidatesIteratorRW(IDunningContext dunningContext, String additionalWhere);

	List<I_C_DunningDoc_Line> retrieveDunningDocLines(I_C_DunningDoc dunningDoc);

	List<I_C_DunningDoc_Line_Source> retrieveDunningDocLineSources(I_C_DunningDoc_Line line);

	Iterator<I_C_DunningDoc> retrieveNotProcessedDocumentsIterator(IDunningContext dunningContext);

	Iterator<I_C_Dunning_Candidate> retrieveNotProcessedCandidatesIteratorByLevel(IDunningContext dunningContext, final I_C_DunningLevel dunningLevel);

	/**
	 * Retrieves iterator over all {@link I_C_DunningDoc_Line_Source} that require a write-off.
	 * <p>
	 * Candidates suitable for write-off are:
	 * <ul>
	 * <li>not processed
	 * <li>have IsWriteOff flag set
	 * <li>IsWriteOffApplied flag not set
	 * </ul>
	 *
	 * @param dunningContext
	 * @return iterator of sources that require a write-off
	 */
	Iterator<I_C_DunningDoc_Line_Source> retrieveDunningDocLineSourcesToWriteOff(IDunningContext dunningContext);

	int deleteNotProcessedCandidates(IDunningContext context, I_C_DunningLevel dunningLevel);

	List<I_C_Dunning_Candidate> retrieveProcessedDunningCandidatesForRecord(Properties ctx, int tableId, int recordId, String trxName);

	I_C_DunningDoc getByIdInTrx(@NonNull DunningDocId dunningDocId);

	int deleteTargetObsoleteCandidates(RecomputeDunningCandidatesQuery recomputeDunningCandidatesQuery, I_C_DunningLevel dunningLevel);

	Collection<I_C_DunningDoc> getByIdsInTrx(@NonNull Collection<DunningDocId> dunningDocIds);

	DunningLevel getById(@NonNull DunningLevelId id);
}
