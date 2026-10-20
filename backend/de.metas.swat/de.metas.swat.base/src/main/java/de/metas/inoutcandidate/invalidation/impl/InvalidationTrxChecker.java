package de.metas.inoutcandidate.invalidation.impl;

import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateBL;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.compiere.Adempiere;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

/*
 * #%L
 * de.metas.swat.base
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

/**
 * Checks that a record-based recompute invalidation runs in the record's own transaction: the flags are written in the
 * thread-inherited trx, so they commit or roll back with the caller's business change only if that trx is the record's trx.
 * <p>
 * Lenient on both sides: no open thread-inherited trx (see {@link ThreadInheritedTrxState#isThreadTrxOpenForWrites(ITrx)}) means the flags
 * are written out of trx, and a record whose trx is not active (ended or never started) is not checked.
 * <p>
 * During any listener dispatch of the thread trx (BEFORE_COMMIT included; e.g. {@code runAfterCommit} callbacks) the check never throws, only warns: after-commit listener errors are logged and skipped,
 * so a throw would swallow the call and lose the flag write itself, which is worse than the mismatch.
 * Known, accepted edge (as in {@link ThreadInheritedTrxState}): the timing API cannot tell BEFORE_COMMIT from AFTER_COMMIT, so a mismatch inside a BEFORE_COMMIT listener also only warns.
 * <p>
 * On any other mismatch: throws in unit tests and when the SysConfig {@value #SYSCONFIG_FailOnTrxMismatch} is {@code true}; otherwise logs a warning, at most once per minute.
 */
final class InvalidationTrxChecker
{
	static final String SYSCONFIG_FailOnTrxMismatch = IShipmentScheduleInvalidateBL.SYSCONFIG_FailOnTrxMismatch;
	private static final long WARN_INTERVAL_MILLIS = 60_000L;

	private static final Logger logger = LogManager.getLogger(InvalidationTrxChecker.class);

	private final BooleanSupplier failOnMismatch;
	private final LongSupplier clockMillis;
	private final Consumer<String> warnSink;

	private final AtomicLong lastWarnMillis = new AtomicLong(0);
	private final AtomicInteger suppressedWarnings = new AtomicInteger(0);

	InvalidationTrxChecker()
	{
		this(
				() -> Adempiere.isUnitTestMode() || Services.get(ISysConfigBL.class).getBooleanValue(SYSCONFIG_FailOnTrxMismatch, false),
				System::currentTimeMillis,
				message -> logger.warn(message, new AdempiereException("trx mismatch stack trace")));
	}

	InvalidationTrxChecker(
			@NonNull final BooleanSupplier failOnMismatch,
			@NonNull final LongSupplier clockMillis,
			@NonNull final Consumer<String> warnSink)
	{
		this.failOnMismatch = failOnMismatch;
		this.clockMillis = clockMillis;
		this.warnSink = warnSink;
	}

	void checkRecordTrx(@NonNull final Object record)
	{
		checkTrx(InterfaceWrapperHelper.getTrxName(record), record::toString);
	}

	void checkTrx(@Nullable final String dataTrxName, @NonNull final String what)
	{
		checkTrx(dataTrxName, () -> what);
	}

	private void checkTrx(@Nullable final String dataTrxName, @NonNull final Supplier<String> what)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);

		final ITrx threadTrx = trxManager.getThreadInheritedTrx(OnTrxMissingPolicy.ReturnTrxNone);
		if (!ThreadInheritedTrxState.isThreadTrxOpenForWrites(threadTrx))
		{
			return;
		}
		if (!trxManager.isActive(dataTrxName))
		{
			return;
		}

		final String threadTrxName = threadTrx.getTrxName();
		if (trxManager.isSameTrxName(dataTrxName, threadTrxName))
		{
			return;
		}

		final String message = "Recompute flags for " + what.get() + " are written in thread-inherited trx '" + threadTrxName
				+ "' but the record is in trx '" + dataTrxName + "'; the flags would not commit or roll back with the record's change";

		// same probe as ThreadInheritedTrxState: BEFORE_COMMIT listeners cannot be registered while any listener of the trx is being dispatched
		final boolean inListenerDispatch = !threadTrx.getTrxListenerManager().canRegisterOnTiming(TrxEventTiming.BEFORE_COMMIT);
		if (!inListenerDispatch && failOnMismatch.getAsBoolean())
		{
			throw new AdempiereException(message);
		}
		warnRateLimited(message);
	}

	private void warnRateLimited(final String message)
	{
		final long now = clockMillis.getAsLong();
		final long last = lastWarnMillis.get();
		if (now - last >= WARN_INTERVAL_MILLIS && lastWarnMillis.compareAndSet(last, now))
		{
			final int suppressed = suppressedWarnings.getAndSet(0);
			warnSink.accept(suppressed > 0 ? message + " (" + suppressed + " similar warnings suppressed since the last one)" : message);
		}
		else
		{
			suppressedWarnings.incrementAndGet();
		}
	}
}
