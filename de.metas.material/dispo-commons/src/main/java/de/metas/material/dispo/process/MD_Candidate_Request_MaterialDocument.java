package de.metas.material.dispo.process;

import java.util.function.Predicate;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Services;
import org.compiere.Adempiere;

import de.metas.i18n.ITranslatableString;
import de.metas.material.dispo.CandidateService;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.X_MD_Candidate;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;

/*
 * #%L
 * metasfresh-material-dispo
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * Invokes {@link CandidateService#requestMaterialOrder(Integer)} so that some other part of the system should create a production order for the selected {@link I_MD_Candidate}(s).
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class MD_Candidate_Request_MaterialDocument extends JavaProcess implements IProcessPrecondition
{

	private static final String MSG_MISSING_PRODUCTION_OR_DISTRIBUTRION_RECORDS = "MD_Candidate_Request_MaterialDocument_Missing_Production_Or_Distributrion_Records";
	private final Predicate<I_MD_Candidate> subTypePredicate = r -> {
		final String subType = r.getMD_Candidate_SubType();

		return X_MD_Candidate.MD_CANDIDATE_SUBTYPE_PRODUCTION.equals(subType)
				|| X_MD_Candidate.MD_CANDIDATE_SUBTYPE_DISTRIBUTION.equals(subType);
	};

	@Override
	protected String doIt() throws Exception
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final CandidateService service = Adempiere.getBean(CandidateService.class);

		queryBL.createQueryBuilder(I_MD_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.filter(getProcessInfo().getQueryFilter())
				.create().list()
				.stream()
				.filter(subTypePredicate)
				.map(r -> r.getMD_Candidate_GroupId())
				.distinct()
				.peek(groupId -> addLog("Calling {}.requestOrder() for groupId={}", CandidateService.class.getSimpleName(), groupId))
				.forEach(groupId -> {
					service.requestMaterialOrder(groupId);
				});

		return MSG_OK;
	}

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		boolean atLeastOneMaterialDocCandidateSelected = false;
		for (final I_MD_Candidate selectedRecord : context.getSelectedModels(I_MD_Candidate.class))
		{
			if (subTypePredicate.test(selectedRecord))
			{
				atLeastOneMaterialDocCandidateSelected = true;
				break;
			}
		}

		if (!atLeastOneMaterialDocCandidateSelected)
		{
			final ITranslatableString translatable = msgBL.getTranslatableMsgText(MSG_MISSING_PRODUCTION_OR_DISTRIBUTRION_RECORDS);
			return ProcessPreconditionsResolution.reject(translatable);
		}

		// todo: also check the candidates' status

		return ProcessPreconditionsResolution.accept();
	}

}
