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

/**
 * DAO for atomically drawing and advancing an {@code AD_Sequence} row.
 * <p>
 * Uses the same {@code UPDATE … RETURNING} SQL pattern as
 * {@link de.metas.document.sequence.impl.DocumentNoBuilder} — no {@code MSequence} model, no schema change.
 */
@Repository
public class BPartnerNumberSequenceDAO
{
	private static final int QUERY_TIME_OUT = MSequence.QUERY_TIME_OUT;

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
}
