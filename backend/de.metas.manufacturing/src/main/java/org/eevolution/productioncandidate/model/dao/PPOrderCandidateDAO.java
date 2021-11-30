/*
 * #%L
 * de.metas.manufacturing
 * %%
 * Copyright (C) 2021 metas GmbH
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

package org.eevolution.productioncandidate.model.dao;

import com.google.common.collect.ImmutableList;
import de.metas.process.PInstanceId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_OrderCandidate_PP_Order;
import org.eevolution.model.I_PP_OrderLine_Candidate;
import org.eevolution.model.I_PP_Order_Candidate;
import org.eevolution.productioncandidate.model.PPOrderCandidateId;
import org.springframework.stereotype.Component;

import java.util.Iterator;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Component
public class PPOrderCandidateDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public I_PP_Order_Candidate getById(@NonNull final PPOrderCandidateId ppOrderCandidateId)
	{
		return InterfaceWrapperHelper.load(ppOrderCandidateId, I_PP_Order_Candidate.class);
	}

	public void save(@NonNull final I_PP_Order_Candidate candidateRecord)
	{
		saveRecord(candidateRecord);
	}

	@NonNull
	public ImmutableList<I_PP_OrderLine_Candidate> getLinesByCandidateId(@NonNull final PPOrderCandidateId ppOrderCandidateId)
	{
		return queryBL.createQueryBuilder(I_PP_OrderLine_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_PP_OrderLine_Candidate.COLUMNNAME_PP_Order_Candidate_ID, ppOrderCandidateId.getRepoId())
				.create()
				.stream()
				.collect(ImmutableList.toImmutableList());
	}

	public void saveLine(@NonNull final I_PP_OrderLine_Candidate ppOrderLineCandidate)
	{
		saveRecord(ppOrderLineCandidate);
	}

	@NonNull
	public Iterator<I_PP_Order_Candidate> retrieveOCForSelection(@NonNull final PInstanceId pinstanceId)
	{
		return queryBL
				.createQueryBuilder(I_PP_Order_Candidate.class)
				.setOnlySelection(pinstanceId)
				.create()
				.iterate(I_PP_Order_Candidate.class);
	}

	public void createProductionOrderAllocation(
			@NonNull final I_PP_Order_Candidate candidateRecord,
			@NonNull final I_PP_Order orderRecord)
	{
		final I_PP_OrderCandidate_PP_Order alloc = InterfaceWrapperHelper.newInstance(I_PP_OrderCandidate_PP_Order.class);

		alloc.setAD_Org_ID(candidateRecord.getAD_Org_ID());
		alloc.setPP_Order_ID(orderRecord.getPP_Order_ID());
		alloc.setPP_Order_Candidate_ID(candidateRecord.getPP_Order_Candidate_ID());

		alloc.setC_UOM_ID(candidateRecord.getC_UOM_ID());
		alloc.setQtyEntered(candidateRecord.getQtyToProcess());

		saveRecord(alloc);
	}

	@NonNull
	public ImmutableList<I_PP_OrderCandidate_PP_Order> getOrderAllocations(@NonNull final PPOrderCandidateId ppOrderCandidateId)
	{
		return queryBL
				.createQueryBuilder(I_PP_OrderCandidate_PP_Order.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_PP_OrderCandidate_PP_Order.COLUMNNAME_PP_Order_Candidate_ID, ppOrderCandidateId.getRepoId())
				.create()
				.stream()
				.collect(ImmutableList.toImmutableList());
	}
}