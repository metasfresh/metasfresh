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

import de.metas.bpartner.BPartnerId;
import de.metas.document.IDocumentSequenceDAO;
import de.metas.document.sequence.DocSequenceId;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.service.ClientId;
import org.compiere.util.DB;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Allocates debtor/creditor numbers at the persistence level, reusing the framework sequence APIs
 * instead of bespoke SQL:
 * <ul>
 *   <li><b>draw next</b> — via {@link IDocumentNoBuilderFactory#forSequenceId(DocSequenceId)}, i.e. the same
 *       atomic {@code UPDATE AD_Sequence … RETURNING} the whole system uses for document numbers (race-free);</li>
 *   <li><b>advance-past</b> — via {@link IDocumentSequenceDAO#advanceCurrentNextPast(DocSequenceId, int)}
 *       (a single atomic {@code GREATEST} update — race-free);</li>
 *   <li><b>override</b> — a per-org DB function call. This is the one remaining SQL here (there is no framework
 *       API for calling an arbitrary customer function); the function name is validated as a safe identifier and
 *       every argument is bound as a parameter.</li>
 * </ul>
 */
@Service
@RequiredArgsConstructor
public class BPartnerNumberService
{
	/**
	 * Allows a plain or single-schema-qualified SQL identifier — same pattern as
	 * {@code DBFunctionSequenceNoProvider} (see {@code FUNCTION_NAME_PATTERN} there).
	 * {@code Matcher.matches()} anchors the whole string, so anything outside [A-Za-z0-9_.] is rejected.
	 */
	private static final Pattern FUNCTION_NAME_PATTERN =
			Pattern.compile("[A-Za-z_][A-Za-z0-9_]*(\\.[A-Za-z_][A-Za-z0-9_]*)?");

	// Spring-injected: DocumentNoBuilderFactory is a @Service with a non-default constructor, so it can only be
	// supplied by Spring. Injecting it (rather than an eager Services.get field) also lets Spring order bean
	// creation correctly — an eager Services.get here runs during this bean's construction, before the
	// Services->Spring bridge is ready, and fails with "DocumentNoBuilderFactory has no default constructor".
	@NonNull private final IDocumentNoBuilderFactory documentNoBuilderFactory;
	// Not constructor-injected like the factory above: IDocumentSequenceDAO is a plain ISingletonService, so there is no Spring bean to inject.
	@NonNull private final IDocumentSequenceDAO documentSequenceDAO = Services.get(IDocumentSequenceDAO.class);

	/**
	 * Draws the next value from the given sequence via the framework document-no builder.
	 * <p>
	 * Thread-safe: the builder performs a single atomic {@code UPDATE AD_Sequence SET CurrentNext = CurrentNext + IncrementNo … RETURNING}.
	 *
	 * @param clientId the client whose {@code AD_Client_ID} the builder needs to pick {@code CurrentNext} vs {@code CurrentNextSys}
	 *                 (a real, non-System client uses {@code CurrentNext}); without it {@code build()} throws {@code Cannot find AD_Client_ID}
	 * @param seqId    the sequence to draw from
	 * @return the allocated number
	 */
	public int drawNext(@NonNull final ClientId clientId, @NonNull final DocSequenceId seqId)
	{
		final String documentNo = documentNoBuilderFactory
				.forSequenceId(seqId)
				.setClientId(clientId)
				.setFailOnError(true)
				.build();
		if (documentNo == null)
		{
			// build() returns NO_DOCUMENTNO (null) WITHOUT throwing when the sequence has IsAutoSequence='N'
			// (setFailOnError only rethrows the exception paths). A debtor/creditor sequence must be an auto-sequence.
			throw new IllegalStateException("AD_Sequence_ID=" + seqId.getRepoId()
					+ " returned no number; a debtor/creditor sequence must have IsAutoSequence='Y'");
		}
		try
		{
			// Debtor/creditor sequences are plain numeric (no prefix/suffix/decimal pattern), so build() yields a bare integer string.
			return Integer.parseInt(documentNo.trim());
		}
		catch (final NumberFormatException e)
		{
			throw new IllegalStateException("AD_Sequence_ID=" + seqId.getRepoId()
					+ " did not yield a plain integer (got '" + documentNo + "'); a debtor/creditor sequence must have no prefix, suffix or decimal pattern", e);
		}
	}

	/**
	 * Advances the sequence so that {@code value} will not be re-issued by a later {@link #drawNext}.
	 * Delegates to the atomic {@link IDocumentSequenceDAO#advanceCurrentNextPast(DocSequenceId, int)}.
	 *
	 * @param seqId the sequence to advance
	 * @param value advance the sequence past this value
	 */
	public void advancePast(@NonNull final DocSequenceId seqId, final int value)
	{
		documentSequenceDAO.advanceCurrentNextPast(seqId, value);
	}

	/**
	 * Validates {@code functionName} as a safe SQL identifier, then calls
	 * {@code SELECT <functionName>(p_ad_org_id, p_c_bpartner_id, p_iscustomer, p_isvendor, p_iscompany, p_kind, p_explicit)}.
	 *
	 * <p>The function name is validated against an anchored identifier pattern before interpolation;
	 * all argument values are bound as {@code ?} parameters (no value interpolation).
	 *
	 * @param functionName  the fully-qualified DB function name (e.g. {@code public.fn_bpartner_no})
	 * @param ctx           the request context; supplies orgId, bPartnerId, role flags, and kind
	 * @param explicitValue {@code null} for draw-next; the explicit value to reserve for advance-past
	 * @return the number the function returns, wrapped in {@link Optional} (never empty — see the NULL note below)
	 * @throws IllegalArgumentException if {@code functionName} is blank or not a valid SQL identifier
	 */
	@NonNull
	public Optional<Integer> callOverrideFunction(
			@NonNull final String functionName,
			@NonNull final BPartnerNumberContext ctx,
			@Nullable final Integer explicitValue)
	{
		final List<Object> sqlParams = new ArrayList<>();
		final String sql = buildOverrideFunctionSql(functionName, ctx, explicitValue, sqlParams);

		// getSQLValueEx returns a primitive int (0 for a SQL-NULL function result — there is no wasNull check),
		// so the result is never null. A 0 from a NULL-returning override is normalized to "no number" downstream
		// by DebtorId/CreditorId.ofNullableNo, so the REST response is null either way.
		final int result = DB.getSQLValueEx(ITrx.TRXNAME_ThreadInherited, sql, sqlParams);
		return Optional.of(result);
	}

	/**
	 * Builds the {@code SELECT <fn>(…)} SQL and fills {@code sqlParams} with the bound argument values,
	 * mirroring the {@code (sql, List<Object> params)} builder pattern used elsewhere (e.g.
	 * {@code PaymentAllocationRepository#buildSelectPaymentsToAllocateSql}).
	 */
	private static String buildOverrideFunctionSql(
			@NonNull final String functionName,
			@NonNull final BPartnerNumberContext ctx,
			@Nullable final Integer explicitValue,
			@NonNull final List<Object> sqlParams)
	{
		final String trimmed = functionName.trim();
		if (trimmed.isEmpty())
		{
			throw new IllegalArgumentException("Override function name must not be blank");
		}
		if (!FUNCTION_NAME_PATTERN.matcher(trimmed).matches())
		{
			throw new IllegalArgumentException(
					"Override function name is not a valid SQL identifier (must match [A-Za-z_][A-Za-z0-9_]*(.[A-Za-z_][A-Za-z0-9_]*)? ): " + trimmed);
		}

		// bPartnerId may be null at TYPE_BEFORE_NEW (native-sequence mode: ID not yet assigned).
		// Pass 0 in that case — the override function must treat 0 as "not yet known".
		final BPartnerId bPartnerId = ctx.getBPartnerId();
		final int bPartnerIdRepoId = bPartnerId != null ? bPartnerId.getRepoId() : 0;

		// The metasfresh DB layer sends a Java boolean as 'Y'/'N' varchar and a null Integer as
		// 'unknown'; cast each param to its intended SQL type so PostgreSQL resolves the override
		// function unambiguously (booleans -> boolean, kind -> text, nullable explicit -> int).
		sqlParams.add(ctx.getOrgId().getRepoId());
		sqlParams.add(bPartnerIdRepoId);
		sqlParams.add(ctx.isCustomer());
		sqlParams.add(ctx.isVendor());
		sqlParams.add(ctx.isCompany());
		sqlParams.add(ctx.getKind().name());
		sqlParams.add(explicitValue);

		return "SELECT " + trimmed
				+ "(?, ?, CAST(? AS BOOLEAN), CAST(? AS BOOLEAN), CAST(? AS BOOLEAN), CAST(? AS TEXT), CAST(? AS INT))";
	}
}
