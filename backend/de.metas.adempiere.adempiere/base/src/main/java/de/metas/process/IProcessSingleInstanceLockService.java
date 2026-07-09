package de.metas.process;

import lombok.NonNull;
import org.adempiere.util.lang.IAutoCloseable;

import java.util.Optional;

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

/**
 * Acquires a process-wide "only one instance at a time" lock for processes flagged
 * {@code AD_Process.IsPreventConcurrentExecution='Y'}. Consulted by {@link ProcessExecutor} before
 * running such a process: if the lock can't be acquired, the run is skipped.
 * <p>
 * The interface lives in {@code base} (the process engine), but the real implementation lives in an
 * {@code de.metas.async}-aware module (the lock primitive is there, and {@code base} can't depend on
 * it). When no implementation is registered (e.g. base-only unit contexts), {@link #NOOP} is used and
 * concurrency is not restricted.
 */
public interface IProcessSingleInstanceLockService
{
	/** No-op fallback: always "acquires" with a do-nothing release; imposes no restriction. */
	IProcessSingleInstanceLockService NOOP = adProcessId -> Optional.of(() -> {});

	/**
	 * Try to acquire the single-instance lock for the given process.
	 *
	 * @return a release handle if acquired; {@link Optional#empty()} if another instance already holds it.
	 */
	Optional<IAutoCloseable> acquireFor(@NonNull AdProcessId adProcessId);
}
