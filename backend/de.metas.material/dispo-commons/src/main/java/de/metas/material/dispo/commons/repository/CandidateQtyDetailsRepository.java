/*
 * #%L
 * metasfresh-material-dispo-commons
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.material.dispo.commons.repository;

import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.commons.candidate.CandidateQtyDetails;
import de.metas.material.dispo.commons.candidate.CandidateQtyDetailsId;
import de.metas.material.dispo.commons.candidate.CandidateQtyDetailsPersistMultiRequest;
import de.metas.material.dispo.commons.candidate.CandidateQtyDetailsPersistRequest;
import de.metas.material.dispo.model.I_MD_Candidate_QtyDetails;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

import static org.adempiere.model.InterfaceWrapperHelper.saveAll;

@Repository
public class CandidateQtyDetailsRepository
{
	private final IQueryBL query = Services.get(IQueryBL.class);

	private List<CandidateQtyDetails> getByCandidateId(@NonNull final CandidateId candidateId)
	{
		return query.createQueryBuilder(I_MD_Candidate_QtyDetails.class)
				.addEqualsFilter(I_MD_Candidate_QtyDetails.COLUMNNAME_MD_Candidate_ID, candidateId)
				.stream()
				.map(this::fromDB)
				.collect(Collectors.toList());
	}

	private CandidateQtyDetails fromDB(@NonNull final I_MD_Candidate_QtyDetails candQtyDetails)
	{
		return CandidateQtyDetails.builder()
				.id(CandidateQtyDetailsId.ofRepoId(candQtyDetails.getMD_Candidate_QtyDetails_ID()))
				.candidateId(CandidateId.ofRepoId(candQtyDetails.getMD_Candidate_ID()))
				.stockCandidateId(CandidateId.ofRepoIdOrNull(candQtyDetails.getStock_MD_Candidate_ID()))
				.detailCandidateId(CandidateId.ofRepoIdOrNull(candQtyDetails.getDetail_MD_Candidate_ID()))
				.qtyInStockUOM(candQtyDetails.getQty())
				.build();
	}

	public void save(@NonNull final CandidateQtyDetailsPersistMultiRequest saveRequest)
	{
		deleteByCandidateId(saveRequest.getCandidateId());
		final List<I_MD_Candidate_QtyDetails> qtyDetails = saveRequest
				.getDetails()
				.stream()
				.map(request -> create(saveRequest.getCandidateId(), saveRequest.getStockCandidateId(), request))
				.collect(Collectors.toList());
		saveAll(qtyDetails);
	}

	private void deleteByCandidateId(@NonNull final CandidateId candidateId)
	{
		query.createQueryBuilder(I_MD_Candidate_QtyDetails.class)
				.addEqualsFilter(I_MD_Candidate_QtyDetails.COLUMNNAME_MD_Candidate_ID, candidateId)
				.create()
				.delete();
	}

	private I_MD_Candidate_QtyDetails create(final @NonNull CandidateId candidateId, @Nullable final CandidateId stockCandidateId, final CandidateQtyDetailsPersistRequest request)
	{
		final I_MD_Candidate_QtyDetails po = InterfaceWrapperHelper.newInstance(I_MD_Candidate_QtyDetails.class);
		po.setMD_Candidate_ID(candidateId.getRepoId());
		po.setStock_MD_Candidate_ID(CandidateId.toRepoId(stockCandidateId));
		po.setDetail_MD_Candidate_ID(CandidateId.toRepoId(request.getDetailCandidateId()));
		po.setQty(request.getQtyInStockUom());
		return po;
	}

}
