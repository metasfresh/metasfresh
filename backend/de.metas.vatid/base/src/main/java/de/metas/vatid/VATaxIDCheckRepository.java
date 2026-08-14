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
 * <p>Persists the individual VAT-ID online check attempts that are the feature's legal evidence. The
 * table is <b>append-only, with exactly one exception</b>: a row is first written at
 * {@link VATaxIDStatus#RequestSent} and, later, that same row — and only that row — is updated to its
 * final status when the VIES answer arrives. This repository exposes exactly the two moves that
 * lifecycle allows — {@link #writeRequestSent(VATaxIDCheckRequest)} appends,
 * {@link #completeCheck(VATaxIDCheckLogId, VATaxIDCheckResult)} completes — and no general "update any
 * field of any row" method, so <b>no bypass path is exposed by this repository's own API</b>: every
 * caller reaching the table through this class can only append or complete, never issue an arbitrary
 * update. That guarantee is scoped to this class's API only — {@code I_VATaxID_CheckLog} is still a
 * public generated model interface, so the invariant's actual enforcement against a caller that skips
 * this repository entirely (e.g. loading and saving the model directly) is not the type system but the
 * codebase-wide review convention that persistence primitives for a model live only in that model's
 * authoritative repository.
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
	 * Appends a new {@code VATaxID_CheckLog} row at {@link VATaxIDStatus#RequestSent}, i.e. records that a
	 * VIES check for {@code request.getVataxID()} was just sent. This is the only way a row is created;
	 * there is no separate "already known" final-status creation path, because every check — successful,
	 * failed or never answered — starts life as a sent request.
	 *
	 * <p><b>Commits in its own, immediately-committing transaction — deliberately, not a shortcut.</b>
	 * {@code callInNewTrx} is normally a hack (every new use needs an inline justification, per
	 * {@code docs/coding-rules/java-general.md} / {@code ITrxManager}'s own deprecation policy on the
	 * method): here it is the entire point. This row IS the feature's legal evidence that a check was
	 * attempted at all (see the class javadoc). Its caller — {@code VATaxIDCheckService#check} — runs
	 * inside a per-item transaction the check-run service opens (see
	 * {@code VATaxIDCheckRunService#checkOneInOwnTrx}); if this append joined that transaction (the
	 * default for a plain {@code saveRecord} with no explicit transaction), a later failure in the SAME
	 * check-and-refresh unit — the online checker throwing, or a triggered order-tax refresh rolling the
	 * whole unit back — would erase this row along with everything else, even though the request really
	 * was sent. Committing independently, before the online service is even called, means the row survives
	 * that rollback: exactly what lets {@link #completeCheck(VATaxIDCheckLogId, VATaxIDCheckResult)}'s later
	 * update — which correctly DOES join the ambient transaction, see that method's own javadoc — roll back
	 * on its own without erasing the evidence that an attempt was made. A row left at
	 * {@link VATaxIDStatus#RequestSent} after such a rollback is not a bug: it is exactly what that status
	 * means — "a check is in flight, or its outcome was never learned."
	 *
	 * @return the id of the newly written row, to be passed to {@link #completeCheck(VATaxIDCheckLogId, VATaxIDCheckResult)}
	 * once (or if) the answer arrives.
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
			// -1, the same "no id" sentinel PInstanceId.toRepoId(null) yields above: the PO layer stores it as SQL
			// NULL, whereas a 0 would be persisted as a literal zero AD_Session_ID.
			record.setAD_Session_ID(request.getAdSessionId() != null ? request.getAdSessionId() : -1);

			InterfaceWrapperHelper.saveRecord(record);

			return VATaxIDCheckLogId.ofRepoId(record.getVATaxID_CheckLog_ID());
		});
	}

	/**
	 * The most recent <b>conclusive</b> check of one VAT-ID <b>value</b> — the lookup de-duplication is
	 * built on, and what the {@code (VATaxID, RequestDate)} index exists for.
	 *
	 * <p>Keyed on the value and not on a partner or a location: the same VAT-ID anywhere is the same
	 * question to the online service, and asking it twice is exactly what de-duplication avoids.
	 *
	 * <p><b>Conclusive</b> means the check ended in a statement about the VAT-ID:
	 * {@link VATaxIDStatus#Valid}, {@link VATaxIDStatus#Invalid} or {@link VATaxIDStatus#NotSupported}.
	 * {@link VATaxIDStatus#RequestSent} rows (a check whose outcome was never learned) and
	 * {@link VATaxIDStatus#ServiceUnavailable} rows (no answer obtained) are deliberately skipped: counting
	 * them would let one failed attempt suppress every retry for the whole re-check interval — the opposite
	 * of what an unreachable service must lead to.
	 *
	 * <p>Freshness is deliberately NOT decided here: {@code RecheckAfterDays} lives in
	 * {@code VATaxID_Config}, so the caller holding the configuration compares the age.
	 *
	 * @return the newest conclusive row for that value, or {@code null} if the value has never been
	 * conclusively checked.
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
	 * Completes the one row identified by {@code checkLogId} — which must currently be at
	 * {@link VATaxIDStatus#RequestSent} — with the VIES outcome. This is the log's single allowed update:
	 * it never touches any other row, and it refuses to run twice on the same row, because once completed
	 * a row is immutable evidence.
	 *
	 * <p>The guard is a {@code SELECT ... FOR NO KEY UPDATE} row lock on the targeted row, taken inside a
	 * transaction that is either joined from the caller or opened here for exactly this method
	 * ({@link ITrxManager#runInThreadInheritedTrx(Runnable)}), followed by an in-Java status check and then
	 * an ordinary {@link InterfaceWrapperHelper#saveRecord}. This is deliberately not a raw conditional
	 * {@code UPDATE}: going through the model layer means the completion is change-logged (the table is
	 * {@code IsChangeLog='Y'}) and invalidates any cached copy of the row, both of which a raw
	 * {@code UPDATE} would silently skip. Atomicity is not weakened by loading first — a second concurrent
	 * call for the same {@code checkLogId} blocks on the row lock until the first call's transaction
	 * commits or rolls back, then observes the now-final status and fails the same way a losing raw
	 * {@code UPDATE} would, without overwriting the winner's evidence.
	 *
	 * @throws org.adempiere.exceptions.AdempiereException if {@code checkLogId} does not currently point
	 * at a {@link VATaxIDStatus#RequestSent} row — either it was already completed (including by a
	 * concurrent caller that won the race), or it never was a request-sent row to begin with.
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
