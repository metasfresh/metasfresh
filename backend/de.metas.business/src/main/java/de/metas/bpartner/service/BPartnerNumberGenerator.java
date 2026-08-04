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
import de.metas.interfaces.I_C_BPartner;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Resolves and allocates debtor/creditor numbers for business partners, per org.
 *
 * <h3>Selection logic (highest priority first)</h3>
 * <ol>
 *   <li><b>Override</b> — sysconfig {@value #SYSCONFIG_OVERRIDE} is set to a DB function name:
 *       delegates entirely to {@code SELECT <fn>(p_ad_org_id, p_c_bpartner_id, p_iscustomer, p_isvendor, p_iscompany, p_kind, p_explicit)}
 *       via {@link BPartnerNumberSequenceDAO#callOverrideFunction}.
 *       The DAO validates the function name as a safe SQL identifier; all argument values are bound as parameters.</li>
 *   <li><b>Sequence</b> — sysconfig {@value #SYSCONFIG_DEBTOR_SEQ} or {@value #SYSCONFIG_CREDITOR_SEQ}
 *       holds an {@code AD_Sequence_ID} (positive integer): delegates to {@link BPartnerNumberSequenceDAO}.</li>
 *   <li><b>No-op</b> — no config: {@code generateNext} returns {@link Optional#empty()};
 *       {@code reserveExplicit} is a no-op.</li>
 * </ol>
 */
@Service
@RequiredArgsConstructor
public class BPartnerNumberGenerator
{
	/** Per-org sysconfig: fully-qualified DB function name used as a number resolver. */
	public static final String SYSCONFIG_OVERRIDE = "de.metas.bpartner.NumberResolverOverride";
	/** Per-org sysconfig: {@code AD_Sequence_ID} (integer) for debtor numbers. */
	public static final String SYSCONFIG_DEBTOR_SEQ = "de.metas.bpartner.DebtorNoSequence";
	/** Per-org sysconfig: {@code AD_Sequence_ID} (integer) for creditor numbers. */
	public static final String SYSCONFIG_CREDITOR_SEQ = "de.metas.bpartner.CreditorNoSequence";

	@NonNull private final BPartnerNumberSequenceDAO dao;
	@NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	/**
	 * Allocates the next debtor or creditor number for the given context, or returns
	 * {@link Optional#empty()} when no number-generation is configured for this org.
	 */
	@NonNull
	public Optional<Integer> generateNext(@NonNull final BPartnerNumberContext ctx)
	{
		final int adClientId = ctx.getClientId().getRepoId();
		final int adOrgId = ctx.getOrgId().getRepoId();

		// 1. Override branch
		final String overrideFn = sysConfigBL.getValue(SYSCONFIG_OVERRIDE, (String)null, adClientId, adOrgId);
		if (!Check.isBlank(overrideFn))
		{
			return dao.callOverrideFunction(overrideFn, ctx, null);
		}

		// 2. Sequence branch
		final int seqId = sysConfigBL.getIntValue(seqSysconfigName(ctx.getKind()), -1, adClientId, adOrgId);
		if (seqId > 0)
		{
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
		final int adClientId = ctx.getClientId().getRepoId();
		final int adOrgId = ctx.getOrgId().getRepoId();

		// 1. Override branch
		final String overrideFn = sysConfigBL.getValue(SYSCONFIG_OVERRIDE, (String)null, adClientId, adOrgId);
		if (!Check.isBlank(overrideFn))
		{
			dao.callOverrideFunction(overrideFn, ctx, explicitValue);
			return;
		}

		// 2. Sequence branch
		final int seqId = sysConfigBL.getIntValue(seqSysconfigName(ctx.getKind()), -1, adClientId, adOrgId);
		if (seqId > 0)
		{
			dao.advancePast(DocSequenceId.ofRepoId(seqId), explicitValue);
		}

		// 3. No config → no-op
	}

	/**
	 * Advances the configured sequence past {@code explicitValue} if this is a new record or the
	 * given column has changed in this save cycle. No-op otherwise, or when no config is present.
	 *
	 * <p>Intended for use by model interceptors: the change-detection logic ({@link InterfaceWrapperHelper#isValueChanged})
	 * lives here rather than in the interceptor (architecture §3 — interceptors are thin glue).
	 *
	 * @param bpartner      the model record being saved
	 * @param ctx           the resolved context for this role (debtor or creditor)
	 * @param isNew         whether this is a new (first-save) record
	 * @param columnName    the column to check for changes (e.g. {@link I_C_BPartner#COLUMNNAME_DebtorId})
	 * @param explicitValue the explicit number already set on the record
	 */
	public void reserveExplicitIfChanged(
			@NonNull final I_C_BPartner bpartner,
			@NonNull final BPartnerNumberContext ctx,
			final boolean isNew,
			@NonNull final String columnName,
			final int explicitValue)
	{
		if (isNew || InterfaceWrapperHelper.isValueChanged(bpartner, columnName))
		{
			reserveExplicit(ctx, explicitValue);
		}
	}

	private static String seqSysconfigName(@NonNull final BPartnerNumberContext.Kind kind)
	{
		return kind == BPartnerNumberContext.Kind.DEBTOR ? SYSCONFIG_DEBTOR_SEQ : SYSCONFIG_CREDITOR_SEQ;
	}
}
