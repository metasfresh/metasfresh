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
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.lang.Mutable;
import org.compiere.model.MSequence;
import org.compiere.util.DB;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * DAO for atomically drawing and advancing an {@code AD_Sequence} row.
 * <p>
 * Uses the same {@code UPDATE … RETURNING} SQL pattern as
 * {@link de.metas.document.sequence.impl.DocumentNoBuilder} — no {@code MSequence} model, no schema change.
 *
 * <p>Repository Tables: AD_Sequence
 * <p>Repository Cluster: BPartnerNumberSequenceDAO
 * (Note: {@link de.metas.document.sequence.impl.DocumentNoBuilder} also writes {@code AD_Sequence} — coordinate changes.)
 */
@Repository
public class BPartnerNumberSequenceDAO
{
	private static final int QUERY_TIME_OUT = MSequence.QUERY_TIME_OUT;

	/**
	 * Allows a plain or single-schema-qualified SQL identifier — same pattern as
	 * {@code DBFunctionSequenceNoProvider} (see {@code FUNCTION_NAME_PATTERN} there).
	 * {@code Matcher.matches()} anchors the whole string, so anything outside [A-Za-z0-9_.] is rejected.
	 */
	private static final Pattern FUNCTION_NAME_PATTERN =
			Pattern.compile("[A-Za-z_][A-Za-z0-9_]*(\\.[A-Za-z_][A-Za-z0-9_]*)?");

	/**
	 * Atomically reads the current {@code CurrentNext} value and advances the sequence by {@code IncrementNo}.
	 * <p>
	 * SQL: {@code UPDATE AD_Sequence SET CurrentNext = CurrentNext + IncrementNo
	 *   WHERE AD_Sequence_ID = ? RETURNING CurrentNext - IncrementNo}
	 *
	 * @param seqId the sequence to draw from
	 * @return the value of {@code CurrentNext} <em>before</em> the increment (i.e. the allocated number)
	 */
	public int drawNext(@NonNull final DocSequenceId seqId)
	{
		final String sql = "UPDATE AD_Sequence"
				+ " SET CurrentNext = CurrentNext + IncrementNo"
				+ " WHERE AD_Sequence_ID = ?"
				+ " RETURNING CurrentNext - IncrementNo";

		final Mutable<Integer> result = new Mutable<>(-1);
		DB.executeUpdateAndThrowExceptionOnFail(
				sql,
				new Object[] { seqId.getRepoId() },
				ITrx.TRXNAME_ThreadInherited,
				QUERY_TIME_OUT,
				rs -> result.setValue(rs.getInt(1)));

		final int value = result.getValue();
		if (value < 0)
		{
			throw new IllegalStateException("AD_Sequence row not found or RETURNING returned no row for AD_Sequence_ID=" + seqId.getRepoId());
		}
		return value;
	}

	/**
	 * Advances {@code CurrentNext} to {@code value + 1} if the current value is below that; never decreases it.
	 * <p>
	 * SQL: {@code UPDATE AD_Sequence SET CurrentNext = GREATEST(CurrentNext, value + 1)
	 *   WHERE AD_Sequence_ID = ?}
	 *
	 * @param seqId the sequence to advance
	 * @param value advance {@code CurrentNext} to at least {@code value + 1}
	 */
	public void advancePast(@NonNull final DocSequenceId seqId, final int value)
	{
		final String sql = "UPDATE AD_Sequence"
				+ " SET CurrentNext = GREATEST(CurrentNext, ?)"
				+ " WHERE AD_Sequence_ID = ?";

		DB.executeUpdateAndThrowExceptionOnFail(
				sql,
				new Object[] { value + 1, seqId.getRepoId() },
				ITrx.TRXNAME_ThreadInherited,
				QUERY_TIME_OUT,
				null);
	}

	/**
	 * Validates {@code functionName} as a safe SQL identifier, then calls
	 * {@code SELECT <functionName>(p_ad_org_id, p_c_bpartner_id, p_iscustomer, p_isvendor, p_iscompany, p_kind, p_explicit)}
	 * via {@link DB#getSQLValueEx}.
	 *
	 * <p>The function name is validated against an anchored identifier pattern before interpolation;
	 * all argument values are bound as {@code ?} parameters (no value interpolation).
	 *
	 * @param functionName the fully-qualified DB function name (e.g. {@code public.fn_bpartner_no})
	 * @param ctx          the request context; supplies orgId, bPartnerId, role flags, and kind
	 * @param explicitValue {@code null} for draw-next; the explicit value to reserve for advance-past
	 * @return the allocated number wrapped in {@link Optional}, or {@link Optional#empty()} when the function returns NULL
	 * @throws IllegalArgumentException if {@code functionName} is blank or not a valid SQL identifier
	 */
	@NonNull
	public Optional<Integer> callOverrideFunction(
			@NonNull final String functionName,
			@NonNull final BPartnerNumberContext ctx,
			@Nullable final Integer explicitValue)
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

		final String sql = "SELECT " + trimmed
				+ "(?, ?, ?, ?, ?, CAST(? AS TEXT), ?)";
		final Integer result = DB.getSQLValueEx(
				ITrx.TRXNAME_ThreadInherited,
				sql,
				ctx.getOrgId().getRepoId(),
				ctx.getBPartnerId().getRepoId(),
				ctx.isCustomer(),
				ctx.isVendor(),
				ctx.isCompany(),
				ctx.getKind().name(),
				explicitValue);
		return result == null ? Optional.empty() : Optional.of(result);
	}
}
