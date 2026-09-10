package de.metas.material.dispo.reconcile;

/*
 * #%L
 * de.metas.fresh.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.material.cockpit.stock.StockDataRecordIdentifier;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.model.I_MD_ATP_Reconciliation_Backup;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.util.List;

import static de.metas.util.Check.assumeNotNull;

/**
 * Repository Tables: MD_ATP_Reconciliation_Backup
 * Repository Cluster: AtpReconciliationBackupRepositoryImpl
 * <p>
 * Default {@link AtpReconciliationBackupRepository}: persists rows to {@code MD_ATP_Reconciliation_Backup} via the
 * ordinary {@link InterfaceWrapperHelper} save path (no bespoke SQL, matching every other repository in this
 * package).
 */
@Repository
public class AtpReconciliationBackupRepositoryImpl implements AtpReconciliationBackupRepository
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public void backupBeforeWrite(
			@NonNull final String runUuid,
			@NonNull final StockDataRecordIdentifier key,
			@NonNull final List<Candidate> stockCandidatesInScope)
	{
		for (final Candidate candidate : stockCandidatesInScope)
		{
			final I_MD_ATP_Reconciliation_Backup record = InterfaceWrapperHelper.newInstance(I_MD_ATP_Reconciliation_Backup.class);
			record.setReconciliationRunUUID(runUuid);
			record.setMD_Candidate_ID(candidate.getId().getRepoId());
			record.setDateProjected(TimeUtil.asTimestamp(candidate.getMaterialDescriptor().getDate()));
			record.setM_Warehouse_ID(key.getWarehouseId().getRepoId());
			record.setM_Product_ID(key.getProductId().getRepoId());
			record.setStorageAttributesKey(key.getStorageAttributesKey().getAsString());
			record.setQtyBefore(candidate.getQuantity());
			// unknown until recordAfterWrite completes this same row; the column is NOT NULL, so carry the
			// pre-change value until then rather than leave a half-written row unreadable
			record.setQtyAfter(candidate.getQuantity());
			InterfaceWrapperHelper.save(record);
		}
	}

	@Override
	public void recordAfterWrite(
			@NonNull final String runUuid,
			@NonNull final StockDataRecordIdentifier key,
			@NonNull final List<AtpReconciliationRunLog.Entry> entries)
	{
		for (final AtpReconciliationRunLog.Entry entry : entries)
		{
			final I_MD_ATP_Reconciliation_Backup record = entry.isNewCandidate()
					? newRecordFor(runUuid, key, entry)
					: retrieveBackedUpRecord(runUuid, entry);
			record.setQtyAfter(entry.getQtyAfter());
			InterfaceWrapperHelper.save(record);
		}
	}

	private static I_MD_ATP_Reconciliation_Backup newRecordFor(
			@NonNull final String runUuid,
			@NonNull final StockDataRecordIdentifier key,
			@NonNull final AtpReconciliationRunLog.Entry entry)
	{
		final I_MD_ATP_Reconciliation_Backup record = InterfaceWrapperHelper.newInstance(I_MD_ATP_Reconciliation_Backup.class);
		record.setReconciliationRunUUID(runUuid);
		record.setMD_Candidate_ID(entry.getCandidateId().getRepoId());
		record.setDateProjected(TimeUtil.asTimestamp(entry.getDate()));
		record.setM_Warehouse_ID(key.getWarehouseId().getRepoId());
		record.setM_Product_ID(key.getProductId().getRepoId());
		record.setStorageAttributesKey(key.getStorageAttributesKey().getAsString());
		record.setQtyBefore(null);
		return record;
	}

	/** @return the row {@link #backupBeforeWrite} already created for this exact run and candidate. */
	private I_MD_ATP_Reconciliation_Backup retrieveBackedUpRecord(
			@NonNull final String runUuid,
			@NonNull final AtpReconciliationRunLog.Entry entry)
	{
		return assumeNotNull(
				queryBL.createQueryBuilder(I_MD_ATP_Reconciliation_Backup.class)
						.addEqualsFilter(I_MD_ATP_Reconciliation_Backup.COLUMNNAME_ReconciliationRunUUID, runUuid)
						.addEqualsFilter(I_MD_ATP_Reconciliation_Backup.COLUMNNAME_MD_Candidate_ID, entry.getCandidateId().getRepoId())
						.create()
						.firstOnlyOptional(I_MD_ATP_Reconciliation_Backup.class)
						.orElse(null),
				"backupBeforeWrite must have already created a row for run={} candidateId={}", runUuid, entry.getCandidateId());
	}
}
