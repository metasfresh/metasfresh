/*
 * #%L
 * de.metas.manufacturing
 * %%
 * Copyright (C) 2023 metas GmbH
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

package org.eevolution.productioncandidate.model.dao.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.process.PInstanceId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.eevolution.api.PPOrderId;
import org.eevolution.api.ProductBOMId;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_OrderCandidate_PP_Order;
import org.eevolution.model.I_PP_OrderLine_Candidate;
import org.eevolution.model.I_PP_Order_Candidate;
import org.eevolution.productioncandidate.model.PPOrderCandidateId;
import org.eevolution.productioncandidate.model.dao.DeletePPOrderCandidatesQuery;
import org.eevolution.productioncandidate.model.dao.IPPOrderCandidateDAO;

import java.util.Iterator;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class PPOrderCandidateDAO implements IPPOrderCandidateDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public I_PP_Order_Candidate getById(@NonNull final PPOrderCandidateId ppOrderCandidateId)
	{
		return InterfaceWrapperHelper.load(ppOrderCandidateId, I_PP_Order_Candidate.class);
	}

	@NonNull
	@Override
	public ImmutableList<I_PP_Order_Candidate> getByIds(@NonNull final Set<PPOrderCandidateId> ppOrderCandidateIds)
	{
		if (ppOrderCandidateIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return queryBL
				.createQueryBuilder(I_PP_Order_Candidate.class)
				.addInArrayFilter(I_PP_Order_Candidate.COLUMNNAME_PP_Order_Candidate_ID, ppOrderCandidateIds)
				.create()
				.listImmutable(I_PP_Order_Candidate.class);
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
				.orderBy(I_PP_Order_Candidate.COLUMNNAME_PP_Order_Candidate_ID) // make sure the behavior is predictable
				.create()
				.iterate(I_PP_Order_Candidate.class);
	}

	public void createProductionOrderAllocation(
			@NonNull final PPOrderCandidateId candidateId,
			@NonNull final I_PP_Order orderRecord,
			@NonNull final Quantity qtyToAllocate)
	{
		final I_PP_OrderCandidate_PP_Order alloc = InterfaceWrapperHelper.newInstance(I_PP_OrderCandidate_PP_Order.class);

		alloc.setAD_Org_ID(orderRecord.getAD_Org_ID());
		alloc.setPP_Order_ID(orderRecord.getPP_Order_ID());
		alloc.setPP_Order_Candidate_ID(candidateId.getRepoId());

		alloc.setC_UOM_ID(qtyToAllocate.getUomId().getRepoId());
		alloc.setQtyEntered(qtyToAllocate.toBigDecimal());

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

	@NonNull
	@Override
	public ImmutableList<I_PP_Order_Candidate> getByOrderId(@NonNull final PPOrderId ppOrderId)
	{
		final ImmutableSet<PPOrderCandidateId> ppOrderCandidateIds = queryBL
				.createQueryBuilder(I_PP_OrderCandidate_PP_Order.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_PP_OrderCandidate_PP_Order.COLUMNNAME_PP_Order_ID, ppOrderId)
				.create()
				.stream()
				.map(I_PP_OrderCandidate_PP_Order::getPP_Order_Candidate_ID)
				.map(PPOrderCandidateId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());

		return getByIds(ppOrderCandidateIds);
	}

	@NonNull
	public ImmutableList<I_PP_Order_Candidate> getByProductBOMId(@NonNull final ProductBOMId productBOMId)
	{
		return queryBL
				.createQueryBuilder(I_PP_Order_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_PP_Order_Candidate.COLUMNNAME_PP_Product_BOM_ID, productBOMId.getRepoId())
				.create()
				.listImmutable(I_PP_Order_Candidate.class);
	}

	public void deletePPOrderCandidates(@NonNull final DeletePPOrderCandidatesQuery deletePPOrderCandidatesQuery)
	{
		final IQueryBuilder<I_PP_Order_Candidate> deleteQuery = queryBL.createQueryBuilder(I_PP_Order_Candidate.class);

		if (deletePPOrderCandidatesQuery.isOnlySimulated())
		{
			deleteQuery.addEqualsFilter(I_PP_Order_Candidate.COLUMNNAME_IsSimulated, true);
		}

		if (deletePPOrderCandidatesQuery.getSalesOrderLineId() != null)
		{
			deleteQuery.addEqualsFilter(I_PP_Order_Candidate.COLUMNNAME_C_OrderLine_ID, deletePPOrderCandidatesQuery.getSalesOrderLineId());
		}

		if (deleteQuery.getCompositeFilter().isEmpty())
		{
			throw new AdempiereException("Deleting all PP_Order_Candidate records is not allowed!");
		}

		final boolean failIfProcessed = false;

		deleteQuery
				.create()
				.iterateAndStream()
				.peek(this::deleteLines)
				.forEach(simulatedOrder -> InterfaceWrapperHelper.delete(simulatedOrder, failIfProcessed));
	}

	public void markAsProcessed(@NonNull final I_PP_Order_Candidate candidate)
	{
		if (candidate.isProcessed())
		{
			return;
		}

		candidate.setProcessed(true);

		save(candidate);
	}

	@Override
	public void closeCandidate(@NonNull final PPOrderCandidateId ppOrderCandidateId)
	{
		final I_PP_Order_Candidate ppOrderCandidate = getById(ppOrderCandidateId);

		ppOrderCandidate.setIsClosed(true);
		ppOrderCandidate.setProcessed(true);
		ppOrderCandidate.setQtyEntered(ppOrderCandidate.getQtyProcessed());

		save(ppOrderCandidate);
	}

	private void deleteLines(@NonNull final I_PP_Order_Candidate ppOrderCandidate)
	{
		deleteLines(PPOrderCandidateId.ofRepoId(ppOrderCandidate.getPP_Order_Candidate_ID()));
	}

	public void deleteLines(@NonNull final PPOrderCandidateId ppOrderCandidateId)
	{
		queryBL.createQueryBuilder(I_PP_OrderLine_Candidate.class)
				.addEqualsFilter(I_PP_OrderLine_Candidate.COLUMNNAME_PP_Order_Candidate_ID, ppOrderCandidateId)
				.create()
				.deleteDirectly();
	}
}
