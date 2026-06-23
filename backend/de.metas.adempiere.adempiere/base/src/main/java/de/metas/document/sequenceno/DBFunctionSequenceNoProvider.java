/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.document.sequenceno;

import ch.qos.logback.classic.Level;
import de.metas.adempiere.model.IPOReferenceAware;
import de.metas.common.util.time.SystemTime;
import de.metas.document.DocumentSequenceInfo;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.service.ISysConfigBL;
import org.compiere.util.DB;
import org.compiere.util.Evaluatee;
import org.compiere.util.SQLValueStringResult;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Generic {@link CustomSequenceNoProvider} that delegates the whole sequence-number string to a PL/pgSQL function.
 * <p>
 * The function name is configured <b>per sequence</b> via the SysConfig
 * {@code de.metas.document.seqNo.DBFunctionSequenceNoProvider.<AD_Sequence.Name>.dbFunctionName},
 * so several sequences can each name their own function with no code change. The function is called as
 * {@code SELECT <fn>(Record_ID, generated_at)} and its result is returned verbatim as the full sequence string
 * ({@link #isUseIncrementSeqNoAsPrefix()} is {@code false}, so no incremental counter is appended).
 * <p>
 * The {@code Record_ID} comes from the evaluation context (the caller puts the driving record's id there);
 * {@code generated_at} is the generation timestamp (now).
 */
public class DBFunctionSequenceNoProvider implements CustomSequenceNoProvider
{
	private static final Logger logger = LogManager.getLogger(DBFunctionSequenceNoProvider.class);

	// Per-sequence key: de.metas.document.seqNo.DBFunctionSequenceNoProvider.<AD_Sequence.Name>.dbFunctionName
	private static final String SYSCONFIG_PREFIX = "de.metas.document.seqNo.DBFunctionSequenceNoProvider.";
	private static final String SYSCONFIG_SUFFIX = ".dbFunctionName";

	// The function name is concatenated into SQL (an identifier can't be a bind parameter), so it must be a plain,
	// optionally single-schema-qualified SQL identifier (e.g. fn_x or myschema.fn_x) - guards against a malformed /
	// injected SysConfig value. Matcher.matches() anchors the whole string.
	private static final Pattern FUNCTION_NAME_PATTERN = Pattern.compile("[A-Za-z_][A-Za-z0-9_]*(\\.[A-Za-z_][A-Za-z0-9_]*)?");

	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	private static String sysConfigKey(@NonNull final DocumentSequenceInfo docSeqInfo)
	{
		return SYSCONFIG_PREFIX + docSeqInfo.getName() + SYSCONFIG_SUFFIX;
	}

	/** @return {@code true} if the (trimmed) function name is a plain or single-schema-qualified SQL identifier. */
	static boolean isValidFunctionName(@Nullable final String functionName)
	{
		return functionName != null && FUNCTION_NAME_PATTERN.matcher(functionName.trim()).matches();
	}

	/**
	 * Applicable when this sequence has a (per-sequence) function-name SysConfig set and the context carries a
	 * positive {@code Record_ID}. We deliberately do NOT check here that the function actually exists: a
	 * misconfigured function name must fail <b>loud</b> in {@link #provideSequenceNo} (so the wrong lot number is
	 * never silently replaced by a plain incremental one), not silently disable the provider.
	 */
	@Override
	public boolean isApplicable(@NonNull final Evaluatee context, @NonNull final DocumentSequenceInfo docSeqInfo)
	{
		final String functionName = sysConfigBL.getValue(sysConfigKey(docSeqInfo), (String)null);
		if (Check.isBlank(functionName))
		{
			return false;
		}
		return context.get_ValueAsInt(IPOReferenceAware.COLUMNNAME_Record_ID, -1) > 0;
	}

	@Override
	public String provideSequenceNo(
			@NonNull final Evaluatee context,
			@NonNull final DocumentSequenceInfo docSeqInfo,
			@Nullable final String autoIncrementedSeqNumber)
	{
		final String sysConfigKey = sysConfigKey(docSeqInfo);
		final String functionName = sysConfigBL.getValue(sysConfigKey, (String)null);
		Check.assumeNotEmpty(functionName, "{} sysconfig must be set", sysConfigKey);

		Check.assume(isValidFunctionName(functionName),
				"{}={} must be a plain or single-schema-qualified SQL function identifier", sysConfigKey, functionName);
		final String functionNameNorm = functionName.trim();

		final int recordId = context.get_ValueAsInt(IPOReferenceAware.COLUMNNAME_Record_ID, -1);
		Check.assume(recordId > 0, "context must carry a positive {}", IPOReferenceAware.COLUMNNAME_Record_ID);

		final Timestamp generatedAt = Timestamp.from(SystemTime.asInstant());
		final SQLValueStringResult sqlResult = DB.getSQLValueStringWithWarningEx(ITrx.TRXNAME_None,
				"SELECT " + functionNameNorm + "(?, ?)", recordId, generatedAt);

		// Surface any RAISE NOTICE the DB function emitted (PostgreSQL delivers them as JDBC SQLWarnings) to the
		// ambient Loggable, so they are visible when debugging - the same notice output SQL-type AD_Processes log.
		final List<String> noticeMessages = sqlResult.getWarningMessages();
		if (noticeMessages != null && !noticeMessages.isEmpty())
		{
			final ILoggable loggable = Loggables.withLogger(logger, Level.DEBUG);
			noticeMessages.forEach(noticeMessage -> loggable.addLog("{}", noticeMessage));
		}

		final String result = sqlResult.getReturnedValue();
		Check.assumeNotEmpty(result, "DB function {} returned empty for {}={}",
				functionNameNorm, IPOReferenceAware.COLUMNNAME_Record_ID, recordId);
		return result;
	}

	/** Standalone number - no auto-incremented counter is appended (same day+shift+line legitimately share one number). */
	@Override
	public boolean isUseIncrementSeqNoAsPrefix()
	{
		return false;
	}
}
