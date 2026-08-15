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
import de.metas.common.util.time.SystemTime;
import de.metas.organization.OrgId;
import de.metas.process.PInstanceId;
import de.metas.tax.api.VATIdentifier;
import de.metas.util.Loggables;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.Duration;
import java.time.Instant;

/**
 * Single entry point for checking one VAT-ID, shared by the {@code C_BPartner} /
 * {@code C_BPartner_Location} after-commit trigger and the check process, so the two paths cannot drift
 * apart in what they check, what they record, or when they skip.
 *
 * <p>Order: offline format check, then de-duplication against {@link VATaxIDConfig#getRecheckAfterDays()},
 * then {@link VATaxIDCheckRepository#writeRequestSent(VATaxIDCheckRequest)} <em>before</em> the online
 * call. That write commits in its own transaction, so a check whose outcome is never learned (crash,
 * timeout, rollback) still leaves evidence that it was asked. Finally the log row is completed and the
 * parent's status columns, which tax determination and the windows read, are refreshed.
 *
 * <p>Countries the online service does not cover are answered {@link VATaxIDStatus#NotSupported} by the
 * checker itself, so that list exists exactly once, in the implementation that owns the protocol.
 *
 * <p>{@link VATaxIDConfig#getOnServiceUnavailable()} is applied here, deliberately not in the checker. An
 * {@link VATaxIDStatus#Invalid} it produces is stored under the same status as a real VIES rejection and
 * no column records the difference; only the check-log {@code RawResponse} tells them apart — a real
 * rejection always carries a boolean {@code valid: false}, a policy-produced one never does.
 *
 * <p>Callers must invoke this <em>outside</em> the save transaction, so a slow or dead service can never
 * fail a save. Progress is reported through {@code Loggables}, never {@code JavaProcess}, so the same code
 * logs to {@code AD_PInstance_Log} under a process and is a no-op under the interceptor.
 */
@Service
@RequiredArgsConstructor
public class VATaxIDCheckService
{
	@NonNull private final VATaxIDConfigRepository configRepository;
	@NonNull private final VATaxIDCheckRepository checkRepository;
	@NonNull private final VATaxIDParentStatusRepository parentStatusRepository;
	@NonNull private final VATaxIDOnlineChecker onlineChecker;

	/**
	 * @return the status the VAT-ID now has — the freshly obtained one, or the still-fresh previous result
	 * when de-duplication skipped the call. Never {@link VATaxIDStatus#RequestSent}, which exists only on a
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
			parentStatusRepository.updateParentStatus(request, freshCheck);
			return freshCheck.getStatus();
		}

		final VATaxIDCheckLogId checkLogId = checkRepository.writeRequestSent(request);

		final VATaxIDCheckResult result = applyOnServiceUnavailable(onlineChecker.check(vataxID, config), config);
		checkRepository.completeCheck(checkLogId, result);

		parentStatusRepository.updateParentStatus(request, VATaxIDLastCheck.builder()
				.checkLogId(checkLogId)
				.status(result.getStatus())
				.checkedAt(SystemTime.asInstant())
				.build());

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
	 * @return the member-state codes {@code orgId}'s online checker reports as unavailable; empty when the
	 * organisation has the online check switched off. Asked once per run, not once per VAT-ID, so the
	 * check-run service can skip the affected member states up front instead of discovering the outage one
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
	 * @return how many online calls the run made, and their average response time. Pass-through, so the
	 * check-run service need not depend on {@link VATaxIDCheckRepository}, this table's sole owner.
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
	 * @return whether {@code orgId} has the online check switched on. Exposed so the nightly selection can
	 * exclude records {@link #check} would return early for: they never get a {@code VATaxIDCheckedAt}, so
	 * they would sort to the front of every future run forever without ever making progress.
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
