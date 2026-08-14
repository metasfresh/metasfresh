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
 * The single entry point for checking one VAT-ID: used by the {@code C_BPartner} /
 * {@code C_BPartner_Location} after-commit trigger and by the check process alike, so the two
 * invocation paths cannot drift apart in what they check, what they record, or when they skip.
 *
 * <h2>Order of operations</h2>
 *
 * <ol>
 *     <li><b>Offline format check first</b> ({@link VATaxIDValidationUtil}, gated by
 *         {@link VATaxIDConfig#isFormatCheckEnabled()}) — a value the format check rejects never reaches
 *         the online service.</li>
 *     <li><b>A prefix the online service does not cover</b> is recorded as
 *         {@link VATaxIDStatus#NotSupported} without a service call.</li>
 *     <li><b>De-duplication</b>: if the same VAT-ID value already has a result younger than
 *         {@link VATaxIDConfig#getRecheckAfterDays()}, that result is kept and no new request is sent —
 *         which is what collapses a bulk import and repeated saves of one record to a single call, and
 *         what makes an unreachable service harmless while the last result is still fresh (AC8).</li>
 *     <li><b>Write {@link VATaxIDStatus#RequestSent}</b> via
 *         {@link VATaxIDCheckRepository#writeRequestSent(VATaxIDCheckRequest)} <em>before</em> the call, so
 *         a check whose outcome is never learned (crash, timeout, killed container) still leaves evidence
 *         that it was asked.</li>
 *     <li><b>Call {@link VATaxIDOnlineChecker#check(de.metas.tax.api.VATIdentifier, VATaxIDConfig)}</b>.</li>
 *     <li><b>Complete the log row</b> with the final status
 *         ({@link VATaxIDCheckRepository#completeCheck(VATaxIDCheckLogId, VATaxIDCheckResult)}) and
 *         <b>refresh the parent record's</b> {@code VATaxIDStatus}, {@code VATaxIDCheckedAt} and
 *         {@code VATaxID_CheckLog_ID} columns
 *         ({@link VATaxIDParentStatusRepository#updateParentStatus(VATaxIDCheckRequest, VATaxIDLastCheck)}) —
 *         the parent's status column is what tax determination and the windows read.</li>
 * </ol>
 *
 * <p>Step 2 is deliberately <em>not</em> a second country table in this service: the checker itself
 * answers {@code NotSupported} without sending a request for a prefix it does not cover (see the
 * {@code VIESClient} implementation), so the list of covered countries exists exactly once, in the
 * implementation that owns the protocol. Reaching the checker is therefore not the same as reaching the
 * network.
 *
 * <p>Where the service is unreachable and the last result is no longer fresh,
 * {@link VATaxIDConfig#getOnServiceUnavailable()} decides the recorded status — that policy is applied
 * here, deliberately not in the checker (see {@link VATaxIDOnlineChecker}).
 *
 * <h2>Telling a policy-produced {@code Invalid} from a real one</h2>
 *
 * An {@link VATaxIDStatus#Invalid} that {@link VATaxIDOnServiceUnavailableAction#Invalid} produced from an
 * unreachable service is stored under the <b>same status code</b> as a VAT-ID VIES actually rejected: the
 * schema (DESIGN § 3) has no reason column, and the {@code OnServiceUnavailable} value in force at the time
 * is not recorded either. The two are still reconstructable from the evidence, at the cost of knowing the
 * VIES payload shape — <b>read the {@code RawResponse} of the {@code VATaxID_CheckLog} row the parent's
 * {@code VATaxID_CheckLog_ID} points at</b>:
 *
 * <ul>
 *     <li><b>A real VIES rejection</b> — and only that — carries the {@code POST /check-vat-number} body
 *         verbatim, a JSON object with a {@code valid} member holding the boolean {@code false}
 *         ({@code {"valid":false,…}}). {@code VIESClient} reaches {@code Invalid} from nowhere else: a body
 *         is mapped to {@code Invalid} exactly when {@code valid} is present and boolean and false.</li>
 *     <li><b>A policy-produced {@code Invalid}</b> was a {@link VATaxIDStatus#ServiceUnavailable} the
 *         checker returned, so its {@code RawResponse} is one of: {@code null} (no body at all — a
 *         connect/read failure, or an empty response), an HTTP 4xx/5xx error body, or a body VIES did
 *         return that carries no boolean {@code valid} member (unparseable, or a no-verdict payload).
 *         Never {@code valid: false}.</li>
 * </ul>
 *
 * <p>So: a {@code valid} member holding boolean {@code false} in {@code RawResponse} means VIES said no; its
 * absence on an {@code Invalid} row means the service could not answer and this organisation's policy chose
 * to treat that as {@code Invalid}. A dedicated reason column would make the same question answerable
 * without this knowledge; it is deliberately out of the approved schema.
 *
 * <p>An organisation with no {@code VATaxID_Config} record keeps today's behaviour exactly — format check
 * on, online check off. This service is the single place that resolves that default; see
 * {@link VATaxIDConfigRepository#getByOrgId(de.metas.organization.OrgId)} and
 * {@link #CONFIG_DEFAULT_WITHOUT_RECORD}.
 *
 * <h2>Logging</h2>
 *
 * Progress is reported through {@code Loggables}, never through {@code JavaProcess}: the same code then
 * writes to {@code AD_PInstance_Log} when a process drives it and is a silent no-op when the interceptor
 * does.
 *
 * <h2>Transaction</h2>
 *
 * Callers invoke this <em>outside</em> the save transaction (the interceptor does so after commit), so a
 * slow or dead service can never fail a save.
 */
@Service
@RequiredArgsConstructor
public class VATaxIDCheckService
{
	/**
	 * The configuration an organisation with <b>no</b> {@code VATaxID_Config} record effectively has:
	 * <b>format check on, online check off</b> — today's behaviour exactly (REQUIREMENTS § 3, DESIGN § 3),
	 * so switching the module on changes nothing at all for an unconfigured organisation.
	 *
	 * <p>Resolved here because {@link VATaxIDConfigRepository#getByOrgId(OrgId)} is a thin query layer over
	 * one table and deliberately leaves this business rule to its caller — this constant is that single
	 * place, so no caller of the repository has to re-invent the default.
	 *
	 * <p>{@code recheckAfterDays} and {@code onServiceUnavailable} are unreachable while
	 * {@code viesCheckEnabled} is {@code false}; they carry the fail-open values rather than being left to
	 * look meaningful.
	 *
	 * <p>Deliberately a plain constant rather than a configuration lookup: the SysConfig that will govern
	 * the format-check half for unconfigured organisations
	 * ({@code VATaxID_Config.IsFormatCheckEnabledByDefault}, REQUIREMENTS § 3) does not exist yet — it
	 * replaces the shipped {@code C_BPartner.validateVATaxID} gate, which until then still governs the
	 * save-time check in the interceptors. When it lands, only this one line changes.
	 *
	 * <p><b>Known divergence until then</b>: {@code formatCheckEnabled} is a static {@code true} here, whereas
	 * the save-time check in {@code de.metas.vatid.interceptor.C_BPartner} /
	 * {@code C_BPartner_Location} reads the live {@code C_BPartner.validateVATaxID} SysConfig. Were that
	 * SysConfig ever set to {@code N}, a malformed VAT-ID would pass the save while
	 * {@link #check(VATaxIDCheckRequest)} would still throw on it. Dormant as shipped — that SysConfig exists
	 * as a single System-level row ({@code ConfigurationLevel='S'}) with value {@code Y} and no
	 * organisation-level override — and resolved by the task that replaces the SysConfig gate, which makes
	 * both halves read the same configured value. Deliberately not worked around here: the configuration this
	 * constant would have to read does not exist yet.
	 */
	private static final VATaxIDConfig CONFIG_DEFAULT_WITHOUT_RECORD = VATaxIDConfig.builder()
			.formatCheckEnabled(true)
			.viesCheckEnabled(false)
			.recheckAfterDays(0)
			.onServiceUnavailable(VATaxIDOnServiceUnavailableAction.ServiceUnavailable)
			.build();

	@NonNull private final VATaxIDConfigRepository configRepository;
	@NonNull private final VATaxIDCheckRepository checkRepository;
	@NonNull private final VATaxIDParentStatusRepository parentStatusRepository;
	@NonNull private final VATaxIDOnlineChecker onlineChecker;

	/**
	 * Checks the VAT-ID of {@code request} and records the attempt, per the order of operations in the
	 * class javadoc.
	 *
	 * @return the status the VAT-ID now has — either the freshly obtained one, or the still-fresh previous
	 * result when de-duplication skipped the call. Never {@link VATaxIDStatus#RequestSent}, which exists
	 * only on a log row awaiting its answer.
	 */
	@NonNull
	public VATaxIDStatus check(@NonNull final VATaxIDCheckRequest request)
	{
		final VATIdentifier vataxID = request.getVataxID();
		final VATaxIDParentStatus parentStatus = parentStatusRepository.getParentStatus(request);
		final VATaxIDConfig config = getEffectiveConfig(parentStatus.getOrgId());

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
	 * @return the organisation's configuration, or {@link #CONFIG_DEFAULT_WITHOUT_RECORD} where it has none.
	 */
	@NonNull
	private VATaxIDConfig getEffectiveConfig(@NonNull final OrgId orgId)
	{
		final VATaxIDConfig config = configRepository.getByOrgId(orgId);
		return config != null ? config : CONFIG_DEFAULT_WITHOUT_RECORD;
	}

	/**
	 * @return the member-state codes {@code orgId}'s online checker currently reports as unavailable, or
	 * an empty set when the organisation has the online check switched off — asking would burn a service
	 * call for information nothing consults, since {@link #check(VATaxIDCheckRequest)} already skips the
	 * online service entirely for such an organisation. See {@link VATaxIDOnlineChecker#getUnavailableCountryCodes}
	 * for why this is asked once per run rather than once per VAT-ID: the caller (the check-run service)
	 * consults this <em>before</em> looping over its selection and skips any VAT-ID whose member state is
	 * in the returned set, rather than discovering the outage one {@link #check} call at a time.
	 */
	@NonNull
	public ImmutableSet<String> getUnavailableCountryCodes(@NonNull final OrgId orgId)
	{
		final VATaxIDConfig config = getEffectiveConfig(orgId);
		if (!config.isViesCheckEnabled())
		{
			return ImmutableSet.of();
		}

		return onlineChecker.getUnavailableCountryCodes(config);
	}

	/**
	 * @return how many online calls the run identified by {@code pinstanceId} made, and their average
	 * response time — see {@link VATaxIDCheckRepository#getCallStatsForRun(PInstanceId)}, which this
	 * delegates to unchanged: {@link #checkRepository} is this table's sole owner (see its own class
	 * javadoc), so the check-run service reaches the evidence through this pass-through rather than
	 * depending on the repository directly.
	 */
	@NonNull
	public VATaxIDCheckCallStats getCallStatsForRun(@NonNull final PInstanceId pinstanceId)
	{
		return checkRepository.getCallStatsForRun(pinstanceId);
	}

	/**
	 * @return {@code orgId}'s own {@code RecheckAfterDays} (or {@link #CONFIG_DEFAULT_WITHOUT_RECORD}'s
	 * where it has none) — the same window {@link #check(VATaxIDCheckRequest)} itself applies for
	 * de-duplication. Exposed so the nightly selection can pre-filter to records that are actually due,
	 * without duplicating the organisation-config lookup this service already owns.
	 */
	public int getRecheckAfterDays(@NonNull final OrgId orgId)
	{
		return getEffectiveConfig(orgId).getRecheckAfterDays();
	}

	/**
	 * @return whether {@code orgId} has the online check switched on at all (or
	 * {@link #CONFIG_DEFAULT_WITHOUT_RECORD}'s {@code false} where it has no configuration). Exposed so the
	 * nightly selection can exclude records that can never actually be checked: {@link #check} returns
	 * before doing anything at all — no service call, no write, no {@code VATaxIDCheckedAt} update — once it
	 * resolves this same flag {@code false}, so such a record would otherwise stay {@code NotChecked} with a
	 * {@code null VATaxIDCheckedAt} forever, sorting to the very front of every future nightly run and
	 * potentially occupying its whole budget without ever making progress.
	 */
	public boolean isViesCheckEnabled(@NonNull final OrgId orgId)
	{
		return getEffectiveConfig(orgId).isViesCheckEnabled();
	}

	/**
	 * @return the last conclusive check of {@code vataxID} if it is still younger than
	 * {@link VATaxIDConfig#getRecheckAfterDays()}, else {@code null} — i.e. {@code null} means "send a
	 * request". A {@code RecheckAfterDays} of zero or less is no de-duplication window at all: every check
	 * is sent.
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
	 * Turns "the service could not answer" into the status the organisation chose for that case — the one
	 * piece of interpretation the checker must not do itself, because a checker that applied it would make
	 * an unreachable service indistinguishable from a service that answered "invalid", and the two have
	 * opposite consequences for a partner's tax certificate.
	 *
	 * <p>Reached only once de-duplication has already found no fresh result, which is exactly the condition
	 * {@link VATaxIDOnServiceUnavailableAction} is defined for. {@code requestIdentifier} and
	 * {@code rawResponse} are carried over unchanged: they are the evidence of what actually happened, and
	 * the policy changes only how it is judged.
	 *
	 * <p><b>Carrying {@code rawResponse} over unchanged is what keeps the remap reconstructable</b>, since no
	 * column records that it happened: a row this method turned into {@link VATaxIDStatus#Invalid} keeps the
	 * {@code ServiceUnavailable} evidence it came with — {@code null}, an HTTP error body, or a body without a
	 * boolean {@code valid} member — and therefore never the {@code valid: false} that a VAT-ID VIES really
	 * rejected always carries. See the class javadoc, § "Telling a policy-produced {@code Invalid} from a real
	 * one"; a future edit that synthesised a {@code rawResponse} here would destroy that distinction.
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
