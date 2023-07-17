package de.metas.purchasecandidate.process;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.PurchaseCandidateId;
import de.metas.purchasecandidate.PurchaseCandidateRepository;
import de.metas.purchasecandidate.model.I_C_PurchaseCandidate;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.Adempiere;

import static com.google.common.base.Predicates.not;

/*
 * #%L
 * de.metas.purchasecandidate.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class C_PurchaseCandiate_Mark_Processed
		extends JavaProcess
		implements IProcessPrecondition
{
	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(
			@NonNull final IProcessPreconditionsContext context)
	{
		if (!I_C_PurchaseCandidate.Table_Name.equals(context.getTableName()))
		{
			return ProcessPreconditionsResolution.reject();
		}

		final boolean containsEligibleRecords = context
				.streamSelectedModels(I_C_PurchaseCandidate.class)
				.anyMatch(not(I_C_PurchaseCandidate::isProcessed));

		return ProcessPreconditionsResolution.acceptIf(containsEligibleRecords);
	}

	@Override
	protected String doIt() throws Exception
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final PurchaseCandidateRepository purchaseCandidateRepository = Adempiere.getBean(PurchaseCandidateRepository.class);

		final ImmutableSet<PurchaseCandidateId> purchaseCandidateIds = queryBL
				.createQueryBuilder(I_C_PurchaseCandidate.class)
				.filter(getProcessInfo().getQueryFilterOrElse(ConstantQueryFilter.of(false)))
				.create()
				.stream()
				.filter(not(I_C_PurchaseCandidate::isProcessed))
				.map(I_C_PurchaseCandidate::getC_PurchaseCandidate_ID)
				.map(PurchaseCandidateId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());

		final ImmutableList<PurchaseCandidate> purchaseCandidates = purchaseCandidateRepository
				.streamAllByIds(purchaseCandidateIds)
				.peek(PurchaseCandidate::markProcessed)
				.collect(ImmutableList.toImmutableList());

		purchaseCandidateRepository.saveAll(purchaseCandidates);

		return MSG_OK;
	}
}
