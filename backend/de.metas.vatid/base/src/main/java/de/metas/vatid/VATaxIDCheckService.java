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

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.common.util.time.SystemTime;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.process.PInstanceId;
import de.metas.tax.api.VATIdentifier;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.Duration;
import java.time.Instant;

/**
 * Checks one VAT-ID. The single convergence point for both callers — the {@code C_BPartner} /
 * {@code C_BPartner_Location} after-commit trigger and {@link VATaxIDMassCheckService} — so the two cannot
 * drift on what gets checked, recorded, or skipped.
 *
 * <p>Invariants to know before changing anything here:
 * <ul>
 * <li>Call this <b>outside</b> the save transaction — a slow or dead service must never fail a save.</li>
 * <li>{@link VATaxIDCheckRepository#writeRequestSent(VATaxIDCheckRequest)} commits in its own transaction
 * <b>before</b> the online call, so a check whose outcome is never learned still leaves evidence it was
 * asked.</li>
 * <li>The parent's status columns are written only while the record still holds the VAT-ID that was
 * checked — {@link #updateParentStatusIfStillCurrent(VATaxIDCheckRequest, VATaxIDLastCheck)}.</li>
 * <li>A status that actually changed also refreshes that partner's open orders' tax, here and not in
 * either caller — a refresh anywhere else is one that only one caller gets.</li>
 * <li>An {@link VATaxIDStatus#Invalid} produced by {@link VATaxIDConfig#getOnServiceUnavailable()} is
 * stored indistinguishably from a real VIES rejection; only the check-log {@code RawResponse} separates
 * them — a real rejection carries {@code valid: false}, a policy-produced one never does.</li>
 * </ul>
 */
@Service
@RequiredArgsConstructor
public class VATaxIDCheckService
{
	private static final Logger logger = LogManager.getLogger(VATaxIDCheckService.class);

	@NonNull private final VATaxIDConfigRepository configRepository;
	@NonNull private final VATaxIDCheckRepository checkRepository;
	@NonNull private final VATaxIDParentStatusRepository parentStatusRepository;
	@NonNull private final VATaxIDOnlineChecker onlineChecker;
	@NonNull private final VATaxIDOrderTaxRefresher orderTaxRefresher;
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);

	/**
	 * @return the status the RECORD now has — the freshly obtained one, or the still-fresh previous result
	 * when de-duplication skipped the call, or the unchanged stored one when nothing was written (the online
	 * check is off, or the record no longer holds the checked VAT-ID; see
	 * {@link #updateParentStatusIfStillCurrent(VATaxIDCheckRequest, VATaxIDLastCheck)}). Never the answer
	 * itself when that answer was not stored. Never {@link VATaxIDStatus#RequestSent}, which exists only on a
	 * log row awaiting its answer.
	 */
	@NonNull
	public VATaxIDStatus check(@NonNull final VATaxIDCheckRequest request)
	{
		final VATIdentifier vataxID = request.getVataxID();
		final VATaxIDParentStatus parentStatus = parentStatusRepository.getParentStatus(request);
		final VATaxIDConfig config = configRepository.getByOrgId(parentStatus.getOrgId());

		if (config.isFormatCheckEnabled())
		{
			// Throws for a malformed value, exactly as the save-time check does — a value the format check
			// rejects must not reach the online service, and must not be recorded as if it had been checked.
			VATaxIDValidationUtil.validate(vataxID);
		}

		if (!config.isViesCheckEnabled())
		{
			// Nothing was checked, so nothing is recorded and the stored status stands.
			return parentStatus.getStatus();
		}

		final VATaxIDLastCheck freshCheck = getStillFreshCheck(vataxID, config);
		if (freshCheck != null)
		{
			Loggables.addLog("VAT-ID {} keeps status {} from the check of {}: still younger than"
							+ " RecheckAfterDays={}, so no request was sent",
					vataxID.getAsString(), freshCheck.getStatus(), freshCheck.getCheckedAt(), config.getRecheckAfterDays());

			// Written even though nothing was checked: the parent must mirror the log row it points at, and
			// this VAT-ID's fresh result may have been obtained for a different parent carrying the same value.
			// That write can therefore change this record's status, which is why the order-tax refresh has to
			// be considered on this path too and not only after a real online call.
			if (!storeVerdictAndRefreshOrderTax(request, parentStatus.getStatus(), freshCheck))
			{
				return parentStatus.getStatus();
			}
			return freshCheck.getStatus();
		}

		final VATaxIDCheckLogId checkLogId = checkRepository.writeRequestSent(request);

		final VATaxIDCheckResult result = applyOnServiceUnavailable(onlineChecker.check(vataxID, config), config);
		checkRepository.completeCheck(checkLogId, result);

		if (!storeVerdictAndRefreshOrderTax(request, parentStatus.getStatus(), VATaxIDLastCheck.builder()
				.checkLogId(checkLogId)
				.status(result.getStatus())
				.checkedAt(SystemTime.asInstant())
				.build()))
		{
			// No status was stored, so there is no status change to report either.
			return parentStatus.getStatus();
		}

		// Suppressed on a first-ever check (previous status NotChecked): the initial rollout would
		// otherwise produce one line per record -- every VAT-ID "changes" the first time it is checked at
		// all -- drowning the handful of genuine re-check flips a run summary exists to surface. A real
		// re-check flip (Valid -> Invalid, ServiceUnavailable -> Valid, ...) still logs.
		if (result.getStatus() != parentStatus.getStatus() && parentStatus.getStatus() != VATaxIDStatus.NotChecked)
		{
			Loggables.addLog("VAT-ID {}: status {} -> {}", vataxID.getAsString(), parentStatus.getStatus(), result.getStatus());
		}

		return result.getStatus();
	}

	/**
	 * Stores {@code lastCheck} on the parent and, when that write actually CHANGED the record's status,
	 * refreshes the partner's not-yet-processed orders' tax — the two as one unit of work.
	 *
	 * <p><b>Why the comparison is against {@code previousStatus} read from the record.</b> The stored status is
	 * what tax determination reads, so "the verdict differs from what the record held" is exactly the condition
	 * under which an open order's tax can come out differently. A snapshot held by a CALLER is not the same
	 * thing: both paths converge on this method, and whichever of them reaches a record first consumes its
	 * transition, so a snapshot the other one is still holding no longer describes any change left to react to.
	 * Only the record is authoritative about the status it held immediately before this write.
	 *
	 * <p><b>Residual risk, accepted</b> — the same shape as the one
	 * {@link #updateParentStatusIfStillCurrent(VATaxIDCheckRequest, VATaxIDLastCheck)} documents.
	 * {@code previousStatus} is read at the top of {@link #check(VATaxIDCheckRequest)}, before the online call,
	 * so a concurrent check of the SAME record landing in between leaves it stale. A stale snapshot can only
	 * MISS a refresh, never cause a wrong one — but the miss need not heal: if the concurrent check writes
	 * {@code S1} and refreshes while this one re-writes the {@code S0} it read, the record ends at {@code S0}
	 * with its order tax derived under {@code S1}, and while the verdict stably stays {@code S0} every later
	 * re-check compares {@code S0} to {@code S0} and refreshes nothing. The bound accepted here is therefore
	 * "the orders can lag the record", not "the next re-check corrects it".
	 *
	 * <p>Re-reading the status here would narrow that window without closing it, for a case that needs two
	 * checks of one record to overlap AND the service to answer differently for one identical value.
	 *
	 * <p><b>Only a write that happened can trigger a refresh.</b> A verdict abandoned by
	 * {@link #updateParentStatusIfStillCurrent(VATaxIDCheckRequest, VATaxIDLastCheck)} changed no status, so
	 * there is nothing for a tax rule to have started reading differently. Likewise a re-check that merely
	 * reconfirms the stored status: refreshing there would re-save every line of every open order of every
	 * partner the nightly run touches, for no changed input. (The remaining no-write case,
	 * {@code IsVIESCheckEnabled=N}, never reaches this method — {@code check} returns before it.)
	 *
	 * <p><b>{@code callInThreadInheritedTrx}, so the pair is atomic on BOTH paths</b> — a refresh failure must
	 * never leave a committed status behind stale order tax, and the two paths arrive here differently:
	 * <ul>
	 * <li>Under {@code C_BPartner_VATaxID_Check} there IS an ambient transaction ({@code VATaxIDMassCheckService}
	 * wraps each target's whole unit), so this NESTS in it — a savepoint, committing nothing of its own. The
	 * rethrow below then unwinds out of {@code check} and takes that whole per-target unit down with it.</li>
	 * <li>Under a save-triggered check there is NONE: {@code VATaxIDCheckWorkpackageProcessor} declares
	 * {@code isRunInTransaction()==false}, so the async framework runs it with no thread-inherited transaction
	 * at all. Here {@code callInThreadInheritedTrx} therefore opens one and commits it, which is what makes
	 * the write and the refresh atomic on this path — separately auto-committing saves would not be.</li>
	 * </ul>
	 *
	 * <p>The rethrow cannot fail a user's save: that path already runs off the saving thread, after the save
	 * committed, and {@code VATaxIDCheckWorkpackageProcessor} logs and swallows whatever comes out of
	 * {@code check}.
	 *
	 * @param previousStatus the status the record held before this check, as read at the top of
	 * {@link #check(VATaxIDCheckRequest)}.
	 * @return whether the parent record was updated — {@code false} means the verdict was abandoned, and the
	 * caller must report the stored status rather than the answer.
	 */
	private boolean storeVerdictAndRefreshOrderTax(
			@NonNull final VATaxIDCheckRequest request,
			@NonNull final VATaxIDStatus previousStatus,
			@NonNull final VATaxIDLastCheck lastCheck)
	{
		return trxManager.callInThreadInheritedTrx(() -> {
			if (!updateParentStatusIfStillCurrent(request, lastCheck))
			{
				return false;
			}

			if (lastCheck.getStatus() != previousStatus)
			{
				refreshOrderTax(request, previousStatus, lastCheck.getStatus());
			}

			return true;
		});
	}

	/**
	 * Wraps a refresh failure rather than swallowing it: the whole unit — the status write included — must
	 * roll back together, and the message has to say the check itself SUCCEEDED, or the run log's per-target
	 * failure line reads as though the VAT-ID could not be checked.
	 */
	private void refreshOrderTax(
			@NonNull final VATaxIDCheckRequest request,
			@NonNull final VATaxIDStatus previousStatus,
			@NonNull final VATaxIDStatus newStatus)
	{
		try
		{
			orderTaxRefresher.refreshOrderLinesTaxForBPartner(request.getBpartnerId());
		}
		catch (final Exception ex)
		{
			throw new AdempiereException(
					"VAT-ID check for " + toLogLabel(request)
							+ " succeeded (status " + previousStatus + " -> " + newStatus
							+ "), but refreshing its open orders' tax failed: " + ex.getMessage(),
					ex);
		}
	}

	/**
	 * Writes {@code lastCheck} onto the parent record — unless that record's {@code VATaxID} is no longer the
	 * value {@code request} was checked for, in which case the verdict is abandoned.
	 *
	 * <p><b>Why this guard exists.</b> A save-triggered check runs asynchronously
	 * ({@code VATaxIDCheckWorkpackageProcessor}) on the VAT-ID captured when the work package was ENQUEUED,
	 * while the parent columns are written onto the record as it stands when the package is PROCESSED. In
	 * between, the user can clear the field — {@code VATaxIDCheckTrigger} enqueues nothing for a cleared
	 * value, but the package already queued still runs — or correct it, in which case two in-flight packages
	 * for the same record have no ordering guarantee and the older answer could overwrite the newer one.
	 * Either way the record would end up carrying a status, a timestamp and a {@code VATaxID_CheckLog_ID}
	 * that belong to a VAT-ID it does not hold, and under a {@code Valid} that is a tax certificate granted
	 * on the strength of someone else's number.
	 *
	 * <p><b>The {@code VATaxID_CheckLog} row is written and kept regardless</b> — the attempt genuinely
	 * happened, and that table is append-only historical evidence by design. Only the denormalised copy on
	 * the parent is withheld.
	 *
	 * <p>The comparison lives here rather than inside
	 * {@link VATaxIDParentStatusRepository#updateParentStatus(VATaxIDCheckRequest, VATaxIDLastCheck)}
	 * because it is a decision, and a repository must not make conditional writes based on business state
	 * ({@code docs/coding-rules/architecture.md} §8). It also has to be visible to the caller: neither the
	 * status-change log line nor the order-line-tax refresh in
	 * {@link #storeVerdictAndRefreshOrderTax(VATaxIDCheckRequest, VATaxIDStatus, VATaxIDLastCheck)} may fire
	 * on a verdict that was never written.
	 * The repository still owns the branch on parent type, on both the read and the write.
	 *
	 * <p>The synchronous process path keeps working unchanged in the ordinary case — it builds its request
	 * from the record it has just read, so the values match and this returns {@code true}. It is not exempt
	 * from the race, though: {@code VATaxIDMassCheckService} reads its whole selection up front and then works
	 * through it one target at a time, so a user editing a VAT-ID mid-run hits exactly the same window, and
	 * the guard covers that too.
	 *
	 * <p><b>Residual risk, accepted.</b> The read and the write are two round-trips with no row lock between
	 * them, so a write landing in that gap still slips past. That gap is now two adjacent local statements
	 * with no third-party I/O between them, where before it spanned the whole online call plus however long
	 * the work package sat in the queue — a narrowing from minutes to sub-millisecond, not an elimination.
	 * Locking the parent row for the duration would serialise checks against ordinary partner edits, which
	 * costs more than the remaining exposure is worth.
	 *
	 * @return whether the parent record was updated.
	 */
	private boolean updateParentStatusIfStillCurrent(
			@NonNull final VATaxIDCheckRequest request,
			@NonNull final VATaxIDLastCheck lastCheck)
	{
		final VATIdentifier currentVATaxID = parentStatusRepository.getCurrentVATaxID(request);
		if (!request.getVataxID().equals(currentVATaxID))
		{
			// withWarnLoggerToo, not a bare Loggables: the case that matters is the asynchronous one, where
			// Loggables is a no-op and the server log is the only place an operator can find this.
			Loggables.withWarnLoggerToo(logger).addLog(
					"VAT-ID check result {} for {} was NOT stored on {}: the record now holds {} instead of the"
							+ " checked value, so the answer is about a VAT-ID it no longer has. The"
							+ " VATaxID_CheckLog row is kept as evidence of the attempt.",
					lastCheck.getStatus(), request.getVataxID().getAsString(), toLogLabel(request),
					VATIdentifier.toString(currentVATaxID));
			return false;
		}

		parentStatusRepository.updateParentStatus(request, lastCheck);
		return true;
	}

	@NonNull
	private static String toLogLabel(@NonNull final VATaxIDCheckRequest request)
	{
		final BPartnerLocationId bpartnerLocationId = request.getBpartnerLocationId();
		return bpartnerLocationId != null
				? "C_BPartner_ID=" + request.getBpartnerId().getRepoId()
				+ ", C_BPartner_Location_ID=" + bpartnerLocationId.getRepoId()
				: "C_BPartner_ID=" + request.getBpartnerId().getRepoId();
	}

	/**
	 * @return the member-state codes {@code orgId}'s online checker reports as unavailable; empty when the
	 * organisation has the online check switched off. Asked once per run, not once per VAT-ID, so the
	 * mass-check service can skip the affected member states up front instead of discovering the outage one
	 * {@link #check} call at a time.
	 */
	@NonNull
	public ImmutableSet<String> getUnavailableCountryCodes(@NonNull final OrgId orgId)
	{
		final VATaxIDConfig config = configRepository.getByOrgId(orgId);
		if (!config.isViesCheckEnabled())
		{
			return ImmutableSet.of();
		}

		return onlineChecker.getUnavailableCountryCodes(config);
	}

	/**
	 * @return how many online calls the run made, and their average response time.
	 */
	@NonNull
	public VATaxIDCheckCallStats getCallStatsForRun(@NonNull final PInstanceId pinstanceId)
	{
		return checkRepository.getCallStatsForRun(pinstanceId);
	}

	/**
	 * @return {@code orgId}'s de-duplication window, the same one {@link #check(VATaxIDCheckRequest)}
	 * applies. Exposed so the nightly selection can pre-filter to records that are actually due.
	 */
	public int getRecheckAfterDays(@NonNull final OrgId orgId)
	{
		return configRepository.getByOrgId(orgId).getRecheckAfterDays();
	}

	/**
	 * @return whether {@code orgId} has the online check switched on. The single accessor for this
	 * question, so the save-time enqueue gate and the nightly selection cannot express it differently.
	 */
	public boolean isViesCheckEnabled(@NonNull final OrgId orgId)
	{
		return configRepository.getByOrgId(orgId).isViesCheckEnabled();
	}

	/**
	 * @return the last conclusive check if still younger than {@link VATaxIDConfig#getRecheckAfterDays()},
	 * else {@code null}, meaning "send a request". A window of zero or less disables de-duplication.
	 */
	@Nullable
	private VATaxIDLastCheck getStillFreshCheck(@NonNull final VATIdentifier vataxID, @NonNull final VATaxIDConfig config)
	{
		if (config.getRecheckAfterDays() <= 0)
		{
			return null;
		}

		final VATaxIDLastCheck lastCheck = checkRepository.getLastConclusiveCheck(vataxID);
		if (lastCheck == null)
		{
			return null;
		}

		final Instant staleBefore = SystemTime.asInstant().minus(Duration.ofDays(config.getRecheckAfterDays()));
		return lastCheck.getCheckedAt().isBefore(staleBefore) ? null : lastCheck;
	}

	/**
	 * Turns "the service could not answer" into the status the organisation chose — the one interpretation
	 * the checker must not make itself, since an unreachable service and a rejected VAT-ID have opposite
	 * consequences for a partner's tax certificate.
	 *
	 * <p>{@code rawResponse} is carried over unchanged. No column records that this remap happened, so that
	 * evidence is the only thing separating it from a real rejection (see the class javadoc) — never
	 * synthesise one here.
	 */
	@NonNull
	private static VATaxIDCheckResult applyOnServiceUnavailable(
			@NonNull final VATaxIDCheckResult result,
			@NonNull final VATaxIDConfig config)
	{
		if (result.getStatus() != VATaxIDStatus.ServiceUnavailable)
		{
			return result;
		}

		final VATaxIDStatus statusPerPolicy = config.getOnServiceUnavailable().toVATaxIDStatus();
		if (statusPerPolicy == result.getStatus())
		{
			return result;
		}

		return VATaxIDCheckResult.builder()
				.status(statusPerPolicy)
				.requestIdentifier(result.getRequestIdentifier())
				.rawResponse(result.getRawResponse())
				.build();
	}

}
