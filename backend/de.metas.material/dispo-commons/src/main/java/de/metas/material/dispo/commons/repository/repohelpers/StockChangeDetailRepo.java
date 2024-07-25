/*
 * #%L
 * metasfresh-material-dispo-commons
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

package de.metas.material.dispo.commons.repository.repohelpers;

import de.metas.inventory.InventoryId;
import de.metas.inventory.InventoryLineId;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.commons.candidate.CandidateStockChangeDetailId;
import de.metas.material.dispo.commons.candidate.businesscase.StockChangeDetail;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.I_MD_Candidate_StockChange_Detail;
import de.metas.util.NumberUtils;
import lombok.NonNull;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Service
public class StockChangeDetailRepo
{
	@Nullable
	public StockChangeDetail getSingleForCandidateRecordOrNull(@NonNull final CandidateId candidateId)
	{
		final I_MD_Candidate_StockChange_Detail stockChangeDetailRecord = RepositoryCommons
				.createCandidateDetailQueryBuilder(candidateId, I_MD_Candidate_StockChange_Detail.class)
				.firstOnly(I_MD_Candidate_StockChange_Detail.class);

		return ofRecord(stockChangeDetailRecord);
	}

	public void saveOrUpdate(
			@Nullable final StockChangeDetail stockChangeDetail,
			@NonNull final I_MD_Candidate candidateRecord)
	{
		if (stockChangeDetail == null)
		{
			return;
		}

		final CandidateId candidateId = CandidateId.ofRepoId(candidateRecord.getMD_Candidate_ID());
		I_MD_Candidate_StockChange_Detail recordToUpdate = RepositoryCommons.retrieveSingleCandidateDetail(
				candidateId,
				I_MD_Candidate_StockChange_Detail.class);

		if (recordToUpdate == null)
		{
			recordToUpdate = newInstance(I_MD_Candidate_StockChange_Detail.class, candidateRecord);
			recordToUpdate.setMD_Candidate_ID(candidateRecord.getMD_Candidate_ID());
		}

		recordToUpdate.setFresh_QtyOnHand_ID(NumberUtils.asInteger(stockChangeDetail.getFreshQuantityOnHandRepoId(), -1));
		recordToUpdate.setFresh_QtyOnHand_Line_ID(NumberUtils.asInteger(stockChangeDetail.getFreshQuantityOnHandLineRepoId(), -1));

		recordToUpdate.setM_Inventory_ID(NumberUtils.asInteger(stockChangeDetail.getInventoryId(), -1));
		recordToUpdate.setM_InventoryLine_ID(NumberUtils.asInteger(stockChangeDetail.getInventoryLineId(), -1));

		recordToUpdate.setIsReverted(Boolean.TRUE.equals(stockChangeDetail.getIsReverted()));

		recordToUpdate.setEventDateMaterialDispo(TimeUtil.asTimestamp(stockChangeDetail.getEventDate()));

		saveRecord(recordToUpdate);
	}

	@Nullable
	private StockChangeDetail ofRecord(@Nullable final I_MD_Candidate_StockChange_Detail record)
	{
		if (record == null)
		{
			return null;
		}

		final Integer computedFreshQtyOnHandId = NumberUtils.asIntegerOrNull(record.getFresh_QtyOnHand_ID());
		final Integer computedFreshQtyOnHandLineId = NumberUtils.asIntegerOrNull(record.getFresh_QtyOnHand_Line_ID());

		return StockChangeDetail.builder()
				.candidateStockChangeDetailId(CandidateStockChangeDetailId.ofRepoId(record.getMD_Candidate_StockChange_Detail_ID()))
				.candidateId(CandidateId.ofRepoId(record.getMD_Candidate_ID()))
				.freshQuantityOnHandRepoId(computedFreshQtyOnHandId)
				.freshQuantityOnHandLineRepoId(computedFreshQtyOnHandLineId)
				.inventoryId(InventoryId.ofRepoIdOrNull(record.getM_Inventory_ID()))
				.inventoryLineId(InventoryLineId.ofRepoIdOrNull(record.getM_InventoryLine_ID()))
				.isReverted(record.isReverted())
				.eventDate(TimeUtil.asInstantNonNull(record.getEventDateMaterialDispo()))
				.build();
	}
}