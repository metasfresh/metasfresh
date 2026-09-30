/*
 * #%L
 * de.metas.vatid
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

package de.metas.vatid;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.common.util.time.SystemTime;
import de.metas.process.PInstanceId;
import de.metas.tax.api.VATIdentifier;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ForUpdate;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.session.AdSessionId;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_VATaxID_CheckLog;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.sql.Timestamp;

/**
 * Repository Tables: {@code VATaxID_CheckLog}.
 *
 * <p>Repository Cluster: sole owner of {@code VATaxID_CheckLog}.
 *
 * <p>Persists the individual check attempts that are the feature's legal evidence. The table is append-only
 * with exactly one exception: a row is written at {@link VATaxIDStatus#RequestSent} and later completed with
 * its final status. This class exposes only those two moves and no general update, so no caller going through
 * it can issue an arbitrary one. That is an API guarantee only — {@code I_VATaxID_CheckLog} stays a public
 * generated interface, so a caller bypassing this repository is caught by review convention, not by the type
 * system.
 */
@Repository
public class VATaxIDCheckRepository
{
	/** The statuses that count as a check having produced a statement — see {@link #getLastConclusiveCheck(VATIdentifier)}. */
	private static final ImmutableSet<VATaxIDStatus> CONCLUSIVE_STATUSES = ImmutableSet.of(
			VATaxIDStatus.Valid,
			VATaxIDStatus.Invalid,
			VATaxIDStatus.NotSupported);

	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);

	@VisibleForTesting
	public static VATaxIDCheckRepository newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		//noinspection DataFlowIssue
		return SpringContextHolder.getBeanOrSupply(VATaxIDCheckRepository.class, VATaxIDCheckRepository::new);
	}

	/**
	 * Appends a new row at {@link VATaxIDStatus#RequestSent}. The only way a row is created — every check,
	 * successful or not, starts life as a sent request.
	 *
	 * <p><b>Commits in its own transaction.</b> {@code callInNewTrx} normally needs an inline justification
	 * ({@code docs/coding-rules/java-general.md}); here it is the entire point. This row is the evidence that
	 * a check was attempted, and its caller runs inside a per-item transaction that a throwing checker or a
	 * triggered order-tax refresh can roll back. Committing independently, before the service is even called,
	 * is what makes the evidence survive that rollback. A row left at {@code RequestSent} afterwards is not a
	 * bug — it is exactly what the status means.
	 *
	 * @return the id to pass to {@link #completeCheck(VATaxIDCheckLogId, VATaxIDCheckResult)}.
	 */
	@NonNull
	public VATaxIDCheckLogId writeRequestSent(@NonNull final VATaxIDCheckRequest request)
	{
		return trxManager.callInNewTrx(() -> {
			final I_VATaxID_CheckLog record = InterfaceWrapperHelper.newInstance(I_VATaxID_CheckLog.class);
			record.setC_BPartner_ID(BPartnerId.toRepoId(request.getBpartnerId()));
			record.setC_BPartner_Location_ID(BPartnerLocationId.toRepoId(request.getBpartnerLocationId()));
			record.setVATaxID(request.getVataxID().getAsString());
			record.setVATaxIDStatus(VATaxIDStatus.RequestSent.getCode());
			record.setRequestDate(TimeUtil.asTimestampNotNull(SystemTime.asInstant()));
			record.setAD_PInstance_ID(PInstanceId.toRepoId(request.getPinstanceId()));
			record.setAD_Session_ID(AdSessionId.toRepoId(request.getAdSessionId()));

			InterfaceWrapperHelper.saveRecord(record);

			return VATaxIDCheckLogId.ofRepoId(record.getVATaxID_CheckLog_ID());
		});
	}

	/**
	 * The most recent <b>conclusive</b> check of one VAT-ID <b>value</b> — what de-duplication is built on,
	 * and what the {@code (VATaxID, RequestDate)} index exists for. Keyed on the value, not on a partner or a
	 * location: the same VAT-ID anywhere is the same question to the service.
	 *
	 * <p>Conclusive means {@link VATaxIDStatus#Valid}, {@link VATaxIDStatus#Invalid} or
	 * {@link VATaxIDStatus#NotSupported}. {@code RequestSent} and {@code ServiceUnavailable} rows are skipped,
	 * else one failed attempt would suppress every retry for the whole re-check interval.
	 *
	 * <p>Freshness is deliberately not decided here — the caller holds {@code RecheckAfterDays}.
	 *
	 * @return the newest conclusive row for that value, or {@code null} if there is none.
	 */
	@Nullable
	public VATaxIDLastCheck getLastConclusiveCheck(@NonNull final VATIdentifier vataxID)
	{
		final I_VATaxID_CheckLog record = queryBL
				.createQueryBuilder(I_VATaxID_CheckLog.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_VATaxID_CheckLog.COLUMNNAME_VATaxID, vataxID.getAsString())
				.addInArrayFilter(I_VATaxID_CheckLog.COLUMNNAME_VATaxIDStatus, CONCLUSIVE_STATUSES)
				.orderByDescending(I_VATaxID_CheckLog.COLUMNNAME_RequestDate)
				// tie-breaker: two rows can share a RequestDate, and "the last check" must still be one row
				.orderByDescending(I_VATaxID_CheckLog.COLUMNNAME_VATaxID_CheckLog_ID)
				.create()
				.first(I_VATaxID_CheckLog.class);

		if (record == null)
		{
			return null;
		}

		final Timestamp responseDate = record.getResponseDate();
		return VATaxIDLastCheck.builder()
				.checkLogId(VATaxIDCheckLogId.ofRepoId(record.getVATaxID_CheckLog_ID()))
				.status(VATaxIDStatus.ofCode(record.getVATaxIDStatus()))
				.checkedAt(TimeUtil.asInstantNonNull(responseDate != null ? responseDate : record.getRequestDate()))
				.build();
	}

	/**
	 * Completes the one row identified by {@code checkLogId}, which must currently be at
	 * {@link VATaxIDStatus#RequestSent}. The log's single allowed update; it refuses to run twice, because a
	 * completed row is immutable evidence.
	 *
	 * <p>Guarded by a {@code SELECT ... FOR NO KEY UPDATE} row lock plus an in-Java status check, then an
	 * ordinary {@link InterfaceWrapperHelper#saveRecord} rather than a raw conditional {@code UPDATE}: the
	 * model layer change-logs the completion and invalidates cached copies, both of which a raw
	 * {@code UPDATE} would skip. A concurrent second call blocks on the lock, then observes the final status
	 * and fails without overwriting the winner's evidence.
	 *
	 * @throws org.adempiere.exceptions.AdempiereException if the row is not currently request-sent — already
	 * completed (possibly by a concurrent winner), or never a request-sent row to begin with.
	 */
	public void completeCheck(@NonNull final VATaxIDCheckLogId checkLogId, @NonNull final VATaxIDCheckResult result)
	{
		Check.assume(
				result.getStatus() != VATaxIDStatus.RequestSent,
				"A check can only be completed with a final status, not {}; checkLogId={}", VATaxIDStatus.RequestSent, checkLogId);

		trxManager.runInThreadInheritedTrx(() -> completeCheckInTrx(checkLogId, result));
	}

	private void completeCheckInTrx(@NonNull final VATaxIDCheckLogId checkLogId, @NonNull final VATaxIDCheckResult result)
	{
		final I_VATaxID_CheckLog record = queryBL
				.createQueryBuilder(I_VATaxID_CheckLog.class)
				.addEqualsFilter(I_VATaxID_CheckLog.COLUMNNAME_VATaxID_CheckLog_ID, checkLogId)
				.create()
				.setForUpdate(ForUpdate.FOR_NO_KEY_UPDATE) // lock the row now; the PK never changes here (see javadoc on ForUpdate.FOR_NO_KEY_UPDATE)
				.firstOnly(I_VATaxID_CheckLog.class);

		final boolean isStillAtRequestSent = record != null && VATaxIDStatus.ofCode(record.getVATaxIDStatus()) == VATaxIDStatus.RequestSent;
		Check.assume(
				isStillAtRequestSent,
				"Only a row still at {} can be completed, but checkLogId={} is not — either it was already completed or it does not exist",
				VATaxIDStatus.RequestSent, checkLogId);

		record.setVATaxIDStatus(result.getStatus().getCode());
		record.setResponseDate(TimeUtil.asTimestampNotNull(SystemTime.asInstant()));
		record.setRequestIdentifier(result.getRequestIdentifier());
		record.setRawResponse(result.getRawResponse());

		InterfaceWrapperHelper.saveRecord(record);
	}

	/**
	 * @return how many {@code VATaxID_CheckLog} rows one run ({@code pinstanceId}) wrote — every one is
	 * a call the online service was actually asked, regardless of the answer — and the average time
	 * between {@code RequestDate} and {@code ResponseDate} over the ones that got one. Deliberately
	 * computed only over the rows carrying that one run's own {@code AD_PInstance_ID}, not a running
	 * total across every run there has ever been.
	 */
	@NonNull
	public VATaxIDCheckCallStats getCallStatsForRun(@NonNull final PInstanceId pinstanceId)
	{
		final ImmutableList<I_VATaxID_CheckLog> rows = queryBL
				.createQueryBuilder(I_VATaxID_CheckLog.class)
				.addEqualsFilter(I_VATaxID_CheckLog.COLUMNNAME_AD_PInstance_ID, pinstanceId)
				.create()
				.listImmutable(I_VATaxID_CheckLog.class);

		long totalResponseMillis = 0L;
		int answeredCount = 0;
		for (final I_VATaxID_CheckLog row : rows)
		{
			final Timestamp responseDate = row.getResponseDate();
			if (responseDate != null)
			{
				totalResponseMillis += responseDate.getTime() - row.getRequestDate().getTime();
				answeredCount++;
			}
		}

		final long averageResponseTimeMillis = answeredCount > 0 ? totalResponseMillis / answeredCount : 0L;
		return VATaxIDCheckCallStats.builder()
				.callCount(rows.size())
				.averageResponseTimeMillis(averageResponseTimeMillis)
				.build();
	}
}
