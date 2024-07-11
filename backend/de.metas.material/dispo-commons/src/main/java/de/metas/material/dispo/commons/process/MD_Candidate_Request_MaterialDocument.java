package de.metas.material.dispo.commons.process;

import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ITranslatableString;
import de.metas.material.dispo.commons.RequestMaterialOrderService;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.X_MD_Candidate;
import de.metas.material.event.pporder.MaterialDispoGroupId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.SpringContextHolder;

import java.util.function.Predicate;

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
 * Invokes {@link RequestMaterialOrderService#requestMaterialOrderForCandidates(MaterialDispoGroupId)} so that some other part of the system should create a production order for the selected {@link I_MD_Candidate}(s).
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public class MD_Candidate_Request_MaterialDocument extends JavaProcess implements IProcessPrecondition
{
	private static final AdMessageKey MSG_MISSING_PRODUCTION_OR_DISTRIBUTRION_RECORDS = AdMessageKey.of("de.metas.material.dispo.MD_Candidate_Request_MaterialDocument_No_Matching_Records_Selected");

	private final RequestMaterialOrderService service = SpringContextHolder.instance.getBean(RequestMaterialOrderService.class);

	private final Predicate<I_MD_Candidate> hasSupportedBusinessCase = r -> {

		final String businessCase = r.getMD_Candidate_BusinessCase();

		return X_MD_Candidate.MD_CANDIDATE_BUSINESSCASE_PRODUCTION.equals(businessCase)
				|| X_MD_Candidate.MD_CANDIDATE_BUSINESSCASE_DISTRIBUTION.equals(businessCase)
				|| X_MD_Candidate.MD_CANDIDATE_BUSINESSCASE_PURCHASE.equals(businessCase)
				|| X_MD_Candidate.MD_CANDIDATE_BUSINESSCASE_FORECAST.equals(businessCase);
	};

	private final Predicate<I_MD_Candidate> statusIsDocPlanned = r -> {

		final String status = r.getMD_Candidate_Status();

		return X_MD_Candidate.MD_CANDIDATE_STATUS_Doc_planned.equals(status)
				|| X_MD_Candidate.MD_CANDIDATE_STATUS_Planned.equals(status);
	};

	@Override
	protected String doIt() throws Exception
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		queryBL.createQueryBuilder(I_MD_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.filter(getProcessInfo().getQueryFilterOrElseFalse())
				.create()
				.iterateAndStream()
				.filter(hasSupportedBusinessCase)
				.filter(statusIsDocPlanned)
				.map(r -> MaterialDispoGroupId.ofInt(r.getMD_Candidate_GroupId()))
				.distinct()
				.peek(groupId -> addLog("Calling {}.requestOrder() for groupId={}", RequestMaterialOrderService.class.getSimpleName(), groupId))
				.forEach(groupId -> service.requestMaterialOrderForCandidates(groupId, null));

		return MSG_OK;
	}

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		final boolean atLeastOneProperCandidateSelected = context.streamSelectedModels(I_MD_Candidate.class)
				.anyMatch(selectedRecord -> hasSupportedBusinessCase.test(selectedRecord) 
						&& statusIsDocPlanned.test(selectedRecord));

		if (!atLeastOneProperCandidateSelected)
		{
			final ITranslatableString translatable = msgBL.getTranslatableMsgText(MSG_MISSING_PRODUCTION_OR_DISTRIBUTRION_RECORDS);
			return ProcessPreconditionsResolution.reject(translatable);
		}

		return ProcessPreconditionsResolution.accept();
	}

}
