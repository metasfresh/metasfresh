package de.metas.bpartner.service;

/*
 * #%L
 * de.metas.business
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

import de.metas.document.sequence.DocSequenceId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.Setter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.service.ISysConfigBL;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Resolves and allocates debtor/creditor numbers for business partners, per org.
 *
 * <h3>Selection logic (highest priority first)</h3>
 * <ol>
 *   <li><b>Override</b> — sysconfig {@value #SYSCONFIG_OVERRIDE} is set to a DB function name:
 *       delegates entirely to {@code SELECT <fn>(p_ad_org_id, p_c_bpartner_id, p_iscustomer, p_isvendor, p_iscompany, p_kind, p_explicit)}.
 *       The function name is validated as a safe SQL identifier before interpolation;
 *       all argument values are bound as parameters.</li>
 *   <li><b>Sequence</b> — sysconfig {@value #SYSCONFIG_DEBTOR_SEQ} or {@value #SYSCONFIG_CREDITOR_SEQ}
 *       holds an {@code AD_Sequence_ID}: delegates to {@link BPartnerNumberSequenceDAO}.</li>
 *   <li><b>No-op</b> — no config: {@code generateNext} returns {@link Optional#empty()};
 *       {@code reserveExplicit} is a no-op.</li>
 * </ol>
 */
@Service
public class BPartnerNumberGenerator
{
	/** Per-org sysconfig: fully-qualified DB function name used as a number resolver. */
	public static final String SYSCONFIG_OVERRIDE = "de.metas.bpartner.NumberResolverOverride";
	/** Per-org sysconfig: {@code AD_Sequence_ID} (integer) for debtor numbers. */
	public static final String SYSCONFIG_DEBTOR_SEQ = "de.metas.bpartner.DebtorNoSequence";
	/** Per-org sysconfig: {@code AD_Sequence_ID} (integer) for creditor numbers. */
	public static final String SYSCONFIG_CREDITOR_SEQ = "de.metas.bpartner.CreditorNoSequence";

	/**
	 * Allows a plain or single-schema-qualified SQL identifier — same pattern as
	 * {@code DBFunctionSequenceNoProvider} (see {@code FUNCTION_NAME_PATTERN} there).
	 * Matcher.matches() anchors the whole string, so anything outside [A-Za-z0-9_.] is rejected.
	 */
	private static final Pattern FUNCTION_NAME_PATTERN =
			Pattern.compile("[A-Za-z_][A-Za-z0-9_]*(\\.[A-Za-z_][A-Za-z0-9_]*)?");

	/**
	 * Functional seam for the override DB call.
	 * In production this executes the real SELECT; in tests it is replaced with a stub.
	 */
	@FunctionalInterface
	public interface OverrideFunctionExecutor
	{
		/**
		 * @param functionName validated, trimmed SQL identifier
		 * @param ctx          the request context
		 * @param explicitValue {@code null} for draw-next; the explicit value for advance-past
		 * @return the allocated number for draw-next, or {@link Optional#empty()} for advance-past
		 */
		Optional<Integer> execute(@NonNull String functionName, @NonNull BPartnerNumberContext ctx, @Nullable Integer explicitValue);
	}

	@NonNull private final BPartnerNumberSequenceDAO dao;

	/**
	 * Mutable for tests: replaced via {@link #setOverrideFunctionExecutor}.
	 * Production always uses {@link #DB_OVERRIDE_EXECUTOR}.
	 */
	@Setter
	@NonNull private OverrideFunctionExecutor overrideFunctionExecutor;

	public BPartnerNumberGenerator(@NonNull final BPartnerNumberSequenceDAO dao)
	{
		this.dao = dao;
		this.overrideFunctionExecutor = DB_OVERRIDE_EXECUTOR;
	}

	/**
	 * Production override executor — calls the configured PL/pgSQL function via {@link DB#getSQLValueEx}.
	 *
	 * <p>The function receives: (p_ad_org_id, p_c_bpartner_id, p_iscustomer, p_isvendor, p_iscompany, p_kind, p_explicit).
	 * For draw-next, p_explicit is NULL; for advance-past, p_explicit is the value to reserve past.
	 */
	private static final OverrideFunctionExecutor DB_OVERRIDE_EXECUTOR = (functionName, ctx, explicitValue) -> {
		final String sql = "SELECT " + functionName
				+ "(?, ?, ?, ?, ?, CAST(? AS TEXT), ?)";
		final Integer result = DB.getSQLValueEx(
				ITrx.TRXNAME_ThreadInherited,
				sql,
				ctx.getOrgId().getRepoId(),
				ctx.getBPartnerId(),
				ctx.isCustomer(),
				ctx.isVendor(),
				ctx.isCompany(),
				ctx.getKind().name(),
				explicitValue);
		return result == null ? Optional.empty() : Optional.of(result);
	};

	// ─── public API ──────────────────────────────────────────────────────────

	/**
	 * Allocates the next debtor or creditor number for the given context, or returns
	 * {@link Optional#empty()} when no number-generation is configured for this org.
	 */
	@NonNull
	public Optional<Integer> generateNext(@NonNull final BPartnerNumberContext ctx)
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		final int adClientId = resolveClientId();
		final int adOrgId = ctx.getOrgId().getRepoId();

		// 1. Override branch
		final String overrideFn = sysConfigBL.getValue(SYSCONFIG_OVERRIDE, (String)null, adClientId, adOrgId);
		if (!Check.isBlank(overrideFn))
		{
			final String validatedName = validateFunctionName(overrideFn);
			return overrideFunctionExecutor.execute(validatedName, ctx, null);
		}

		// 2. Sequence branch
		final String seqConfig = sysConfigBL.getValue(seqSysconfigName(ctx.getKind()), (String)null, adClientId, adOrgId);
		if (!Check.isBlank(seqConfig))
		{
			final int seqId = Integer.parseInt(seqConfig.trim());
			return Optional.of(dao.drawNext(DocSequenceId.ofRepoId(seqId)));
		}

		// 3. No config → no number
		return Optional.empty();
	}

	/**
	 * Advances the configured sequence or override function so that {@code explicitValue} will not
	 * be re-issued. No-op when no config is present for this org.
	 */
	public void reserveExplicit(@NonNull final BPartnerNumberContext ctx, final int explicitValue)
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		final int adClientId = resolveClientId();
		final int adOrgId = ctx.getOrgId().getRepoId();

		// 1. Override branch
		final String overrideFn = sysConfigBL.getValue(SYSCONFIG_OVERRIDE, (String)null, adClientId, adOrgId);
		if (!Check.isBlank(overrideFn))
		{
			final String validatedName = validateFunctionName(overrideFn);
			overrideFunctionExecutor.execute(validatedName, ctx, explicitValue);
			return;
		}

		// 2. Sequence branch
		final String seqConfig = sysConfigBL.getValue(seqSysconfigName(ctx.getKind()), (String)null, adClientId, adOrgId);
		if (!Check.isBlank(seqConfig))
		{
			final int seqId = Integer.parseInt(seqConfig.trim());
			dao.advancePast(DocSequenceId.ofRepoId(seqId), explicitValue);
		}

		// 3. No config → no-op
	}

	// ─── helpers ─────────────────────────────────────────────────────────────

	/**
	 * Validates that {@code name} is a plain or single-schema-qualified SQL identifier, mirroring the
	 * pattern used in {@code DBFunctionSequenceNoProvider.isValidFunctionName}.
	 *
	 * @throws IllegalArgumentException if the name is null, blank, or contains unsafe characters
	 */
	static String validateFunctionName(@Nullable final String name)
	{
		if (Check.isBlank(name))
		{
			throw new IllegalArgumentException("Override function name must not be blank");
		}
		final String trimmed = name.trim();
		if (!FUNCTION_NAME_PATTERN.matcher(trimmed).matches())
		{
			throw new IllegalArgumentException(
					"Override function name is not a valid SQL identifier (must match [A-Za-z_][A-Za-z0-9_]*(.[A-Za-z_][A-Za-z0-9_]*)? ): " + trimmed);
		}
		return trimmed;
	}

	private static String seqSysconfigName(@NonNull final BPartnerNumberContext.Kind kind)
	{
		return kind == BPartnerNumberContext.Kind.DEBTOR ? SYSCONFIG_DEBTOR_SEQ : SYSCONFIG_CREDITOR_SEQ;
	}

	/**
	 * Returns the AD_Client_ID of the current environment.
	 * Using {@link Env#getAD_Client_ID()} ensures that a sysconfig override stored at the
	 * tenant client level (clientId=&lt;tenant&gt;, orgId=X) is found by the ISysConfigBL hierarchy;
	 * passing 0 here would restrict the fallback chain to (0, orgId) → (0, ANY) and silently
	 * skip any per-tenant row.
	 */
	private static int resolveClientId()
	{
		return Env.getAD_Client_ID();
	}
}
