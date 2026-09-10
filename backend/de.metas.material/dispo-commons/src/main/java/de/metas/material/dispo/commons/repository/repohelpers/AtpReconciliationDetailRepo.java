/*
 * #%L
 * metasfresh-material-dispo-commons
 * %%
 * Copyright (C) 2026 metas GmbH
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

import de.metas.material.dispo.commons.candidate.CandidateAtpReconciliationDetailId;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.commons.candidate.businesscase.AtpReconciliationDetail;
import de.metas.material.dispo.model.I_MD_ATP_Reconciliation_Backup;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/**
 * Persists the one {@link I_MD_ATP_Reconciliation_Backup} row that names a correction candidate's own creation.
 * <p>
 * Reuses the same table {@link de.metas.material.dispo.reconcile.AtpReconciliationBackupRepository}-equivalent
 * classes back up the STOCK candidates a run touches - but unlike this repo's row, THOSE rows are not unique
 * per candidate: a candidate reconciled by two separate runs legitimately accumulates two backup rows sharing
 * its {@code MD_Candidate_ID} (the table's own migration header: "one row per STOCK candidate a run touched").
 * Looking a candidate up by {@code MD_Candidate_ID} alone would therefore hit
 * {@code DBMoreThanOneRecordsFoundException} on the second overlapping run - {@code IsCandidateOwnDetail}
 * (migration {@code 5824060}) marks the ONE row this repo itself created, so every query here filters on it too,
 * and a partial unique index on the same column enforces "at most one" at the database level.
 */
@Service
public class AtpReconciliationDetailRepo
{
	private static final String COLUMNNAME_IsCandidateOwnDetail = "IsCandidateOwnDetail";

	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Nullable
	public AtpReconciliationDetail getSingleForCandidateRecordOrNull(@NonNull final CandidateId candidateId)
	{
		final I_MD_ATP_Reconciliation_Backup record = ownDetailQueryBuilder(candidateId)
				.create()
				.firstOnly(I_MD_ATP_Reconciliation_Backup.class);

		return ofRecord(record);
	}

	public void saveOrUpdate(
			@Nullable final AtpReconciliationDetail atpReconciliationDetail,
			@NonNull final I_MD_Candidate candidateRecord)
	{
		if (atpReconciliationDetail == null)
		{
			return;
		}

		final CandidateId candidateId = CandidateId.ofRepoId(candidateRecord.getMD_Candidate_ID());
		I_MD_ATP_Reconciliation_Backup recordToUpdate = ownDetailQueryBuilder(candidateId)
				.create()
				.firstOnly(I_MD_ATP_Reconciliation_Backup.class);

		if (recordToUpdate == null)
		{
			recordToUpdate = newInstance(I_MD_ATP_Reconciliation_Backup.class, candidateRecord);
			recordToUpdate.setMD_Candidate_ID(candidateId.getRepoId());
			recordToUpdate.setM_Product_ID(candidateRecord.getM_Product_ID());
			recordToUpdate.setM_Warehouse_ID(candidateRecord.getM_Warehouse_ID());
			recordToUpdate.setStorageAttributesKey(candidateRecord.getStorageAttributesKey());
			recordToUpdate.setDateProjected(candidateRecord.getDateProjected());
			recordToUpdate.setReconciliationRunUUID(atpReconciliationDetail.getReconciliationRunUUID());
			InterfaceWrapperHelper.setValue(recordToUpdate, COLUMNNAME_IsCandidateOwnDetail, Boolean.TRUE);
		}

		if (atpReconciliationDetail.getQtyBefore() != null)
		{
			recordToUpdate.setQtyBefore(atpReconciliationDetail.getQtyBefore());
		}
		recordToUpdate.setQtyAfter(atpReconciliationDetail.getQtyAfter());

		saveRecord(recordToUpdate);
	}

	private IQueryBuilder<I_MD_ATP_Reconciliation_Backup> ownDetailQueryBuilder(@NonNull final CandidateId candidateId)
	{
		return queryBL
				.createQueryBuilder(I_MD_ATP_Reconciliation_Backup.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MD_ATP_Reconciliation_Backup.COLUMNNAME_MD_Candidate_ID, candidateId.getRepoId())
				.addEqualsFilter(COLUMNNAME_IsCandidateOwnDetail, true);
	}

	@Nullable
	private AtpReconciliationDetail ofRecord(@Nullable final I_MD_ATP_Reconciliation_Backup record)
	{
		if (record == null)
		{
			return null;
		}

		return AtpReconciliationDetail.builder()
				.candidateAtpReconciliationDetailId(CandidateAtpReconciliationDetailId.ofRepoId(record.getMD_ATP_Reconciliation_Backup_ID()))
				.candidateId(CandidateId.ofRepoId(record.getMD_Candidate_ID()))
				.reconciliationRunUUID(record.getReconciliationRunUUID())
				.qtyBefore(InterfaceWrapperHelper.isNull(record, I_MD_ATP_Reconciliation_Backup.COLUMNNAME_QtyBefore) ? null : record.getQtyBefore())
				.qtyAfter(record.getQtyAfter())
				.build();
	}
}
