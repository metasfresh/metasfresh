/*
 * #%L
 * metasfresh-material-dispo-commons
 * %%
 * Copyright (C) 2022 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.material.dispo.commons.process;

import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.commons.repository.query.DeleteCandidatesQuery;
import de.metas.material.dispo.model.X_MD_Candidate;
import de.metas.process.JavaProcess;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.SpringContextHolder;

public class MD_Candidate_CleanUp extends JavaProcess
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	protected String doIt()
	{

		final DeleteCandidatesQuery deleteCandidatesQuery = DeleteCandidatesQuery.builder()
				.status(X_MD_Candidate.MD_CANDIDATE_STATUS_Simulated)
				.isActive(false)
				.build();

		final CandidateRepositoryWriteService candidateService = SpringContextHolder.instance.getBean(CandidateRepositoryWriteService.class);

		candidateService.deleteCandidatesByQuery(deleteCandidatesQuery);

		return MSG_OK;
	}



}
