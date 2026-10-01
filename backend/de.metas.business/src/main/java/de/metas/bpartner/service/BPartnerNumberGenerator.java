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

import de.metas.bpartner.CreditorId;
import de.metas.bpartner.DebtorId;
import de.metas.document.sequence.DocSequenceId;
import de.metas.interfaces.I_C_BPartner;
import de.metas.organization.ClientAndOrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.service.ISysConfigBL;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Resolves and allocates debtor/creditor numbers for business partners, per org.
 *
 * <h3>Selection logic (highest priority first)</h3>
 * <ol>
 *   <li><b>Override</b> — sysconfig {@value #SYSCONFIG_OVERRIDE} is set to a DB function name:
 *       delegates entirely to {@code SELECT <fn>(p_ad_org_id, p_iscompany, p_kind, p_explicit)}
 *       via {@link BPartnerNumberService#callOverrideFunction}.
 *       The service validates the function name as a safe SQL identifier; all argument values are bound as parameters.</li>
 *   <li><b>Sequence</b> — sysconfig {@value #SYSCONFIG_DEBTOR_SEQ} or {@value #SYSCONFIG_CREDITOR_SEQ}
 *       holds an {@code AD_Sequence_ID} (positive integer): delegates to {@link BPartnerNumberService}.</li>
 *   <li><b>No-op</b> — no config: {@code generateNumbers} yields no number for that role;
 *       {@code reserveExplicit} is a no-op.</li>
 * </ol>
 */
@Service
@RequiredArgsConstructor
public class BPartnerNumberGenerator
{
	/**
	 * Per-org sysconfig: plain or schema-qualified DB function name used as a number resolver.
	 * Validated as {@code [A-Za-z_][A-Za-z0-9_]*(\.[A-Za-z_][A-Za-z0-9_]*)?} before use.
	 * Example: {@code public.fn_bpartner_no}.
	 */
	public static final String SYSCONFIG_OVERRIDE = "de.metas.bpartner.NumberResolverOverride";
	/**
	 * Master on/off switch for debtor/creditor number generation. Default {@code N} (off): the
	 * {@code C_BPartner} interceptor early-exits without any per-save sysconfig-branch lookups unless
	 * an org/client turns it on.
	 */
	public static final String SYSCONFIG_ENABLED = "de.metas.bpartner.NumberGeneration_Enabled";
	/** Per-org sysconfig: {@code AD_Sequence_ID} (integer) for debtor numbers. */
	public static final String SYSCONFIG_DEBTOR_SEQ = "de.metas.bpartner.DebtorNoSequence";
	/** Per-org sysconfig: {@code AD_Sequence_ID} (integer) for creditor numbers. */
	public static final String SYSCONFIG_CREDITOR_SEQ = "de.metas.bpartner.CreditorNoSequence";

	@NonNull private final BPartnerNumberService numberService;
	@NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	/**
	 * Master on/off switch, default {@code false}. When off, the {@code C_BPartner} interceptor
	 * early-exits without any per-save sysconfig-branch lookups. Lets a single org/client enable
	 * the feature while every other instance pays nothing on each business-partner save.
	 */
	public boolean isEnabled(@NonNull final ClientAndOrgId clientAndOrgId)
	{
		return sysConfigBL.getBooleanValue(SYSCONFIG_ENABLED, false, clientAndOrgId);
	}

	/**
	 * Generates the debtor and/or creditor number(s) for {@code bpartner} in one pass — a partner can
	 * be both a customer and a vendor, so both may be produced at once.
	 * <p>
	 * For each applicable role (customer → debtor, vendor → creditor): when no number is set yet, the
	 * next one is drawn; when a number is already set (supplied explicitly at creation), the sequence
	 * is instead advanced past it (so a later draw cannot collide) and no value is returned for that
	 * role. Roles that do not apply, or that have no generation configured, yield no value.
	 * <p>
	 * The result carries the typed {@link DebtorId}/{@link CreditorId} POJOs; conversion to the raw
	 * {@code int} model column happens in the caller, at the model boundary.
	 */
	@NonNull
	public BPartnerNumbers generateNumbers(@NonNull final I_C_BPartner bpartner)
	{
		if (!isEnabled(ClientAndOrgId.ofClientAndOrg(bpartner.getAD_Client_ID(), bpartner.getAD_Org_ID())))
		{
			return BPartnerNumbers.NONE;
		}

		DebtorId debtorId = null;
		CreditorId creditorId = null;

		if (bpartner.isCustomer())
		{
			final BPartnerNumberContext ctx = BPartnerNumberContext.ofBPartner(bpartner, BPartnerNumberContext.Kind.DEBTOR);
			final DebtorId explicit = DebtorId.ofNullableNo(bpartner.getDebtorId());
			if (explicit != null)
			{
				// explicit number supplied at creation → advance the sequence past it instead of generating
				reserveExplicit(ctx, explicit.toInt());
			}
			else
			{
				debtorId = drawNext(ctx).map(DebtorId::ofNullableNo).orElse(null);
			}
		}

		if (bpartner.isVendor())
		{
			final BPartnerNumberContext ctx = BPartnerNumberContext.ofBPartner(bpartner, BPartnerNumberContext.Kind.CREDITOR);
			final CreditorId explicit = CreditorId.ofNullableNo(bpartner.getCreditorId());
			if (explicit != null)
			{
				reserveExplicit(ctx, explicit.toInt());
			}
			else
			{
				creditorId = drawNext(ctx).map(CreditorId::ofNullableNo).orElse(null);
			}
		}

		return BPartnerNumbers.of(debtorId, creditorId);
	}

	/**
	 * Draws the next raw number for the given context, or {@link Optional#empty()} when no
	 * number-generation is configured for this org. The typed wrapping into a
	 * {@link DebtorId}/{@link CreditorId} is done by {@link #generateNumbers(I_C_BPartner)}.
	 */
	@NonNull
	private Optional<Integer> drawNext(@NonNull final BPartnerNumberContext ctx)
	{
		final ClientAndOrgId clientAndOrgId = ClientAndOrgId.ofClientAndOrg(ctx.getClientId(), ctx.getOrgId());

		// 1. Override branch
		final String overrideFn = sysConfigBL.getValue(SYSCONFIG_OVERRIDE, clientAndOrgId);
		if (!Check.isBlank(overrideFn))
		{
			return Optional.of(numberService.callOverrideFunction(overrideFn, ctx, null));
		}

		// 2. Sequence branch
		final DocSequenceId seqId = DocSequenceId.ofRepoIdOrNull(sysConfigBL.getIntValue(seqSysconfigName(ctx.getKind()), -1, clientAndOrgId));
		if (seqId != null)
		{
			return Optional.of(numberService.drawNext(ctx.getClientId(), seqId));
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
		final ClientAndOrgId clientAndOrgId = ClientAndOrgId.ofClientAndOrg(ctx.getClientId(), ctx.getOrgId());

		// 1. Override branch
		final String overrideFn = sysConfigBL.getValue(SYSCONFIG_OVERRIDE, clientAndOrgId);
		if (!Check.isBlank(overrideFn))
		{
			numberService.callOverrideFunction(overrideFn, ctx, explicitValue);
			return;
		}

		// 2. Sequence branch
		final DocSequenceId seqId = DocSequenceId.ofRepoIdOrNull(sysConfigBL.getIntValue(seqSysconfigName(ctx.getKind()), -1, clientAndOrgId));
		if (seqId != null)
		{
			numberService.advancePast(seqId, explicitValue);
		}

		// 3. No config → no-op
	}

	private static String seqSysconfigName(@NonNull final BPartnerNumberContext.Kind kind)
	{
		return kind == BPartnerNumberContext.Kind.DEBTOR ? SYSCONFIG_DEBTOR_SEQ : SYSCONFIG_CREDITOR_SEQ;
	}
}
