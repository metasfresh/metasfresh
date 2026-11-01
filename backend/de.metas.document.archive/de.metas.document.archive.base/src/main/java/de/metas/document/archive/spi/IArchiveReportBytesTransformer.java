package de.metas.document.archive.spi;

/*
 * #%L
 * de.metas.document.archive.base
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

import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;

/**
 * SPI for transforming report bytes just before they are persisted as an {@code AD_Archive}.
 *
 * <p>Implementations are discovered via Spring's component scan and invoked by
 * {@code DefaultModelArchiver} between "report bytes produced" and "archive persisted".
 * <strong>Exactly one implementation may be registered in the Spring context at a time.</strong>
 * If none is registered a no-op pass-through is used (PDF archiving is unaffected). If two or
 * more are registered, {@code SpringContextHolder.getBeanOr} catches the resulting
 * {@code NoUniqueBeanDefinitionException} (a subtype of {@code NoSuchBeanDefinitionException})
 * and returns none — so no transformer runs and CII embedding is silently skipped for every
 * invoice. Archiving keeps working, but ZUGFeRD output is lost without any error. Do not register
 * a second implementation without removing or qualifying the first.
 *
 * <p>Implementations must be:
 * <ul>
 *   <li><strong>Idempotent</strong>: calling transform twice must produce the same result as
 *       calling it once.</li>
 *   <li><strong>No-op for non-matching records</strong>: if the record is not eligible for
 *       transformation (wrong table, wrong e-invoice type, …) the implementation MUST return
 *       the original {@code reportBytes} array by reference (see {@code @return} below).</li>
 * </ul>
 *
 * <p>All e-invoice–specific transformation logic (ZUGFeRD, XRechnung, …) lives in
 * {@code de.metas.einvoice.base} — the archive layer itself has no dependency on einvoice.
 */
public interface IArchiveReportBytesTransformer
{
	/**
	 * Optionally transforms the report bytes for the given document record.
	 *
	 * @param recordRef  non-null reference to the document being archived (table name + record ID)
	 * @param reportBytes  the raw report bytes produced by the report engine; may be empty ({@code length == 0})
	 *                     but is never {@code null}
	 * @return the (possibly transformed) bytes to store as archive data; must be non-null.
	 *         <strong>No-op signal</strong>: to indicate that no transformation occurred,
	 *         implementations MUST return the exact same {@code reportBytes} array reference
	 *         (i.e. {@code return reportBytes} — reference/identity equality, not value equality).
	 *         {@code DefaultModelArchiver} uses reference identity ({@code ==}) to detect the
	 *         no-op case and reuse the original Spring {@link org.springframework.core.io.Resource}.
	 *         Returning a new array with identical content is treated as a transformation and
	 *         results in unnecessary resource allocation.
	 */
	byte[] transform(@NonNull TableRecordReference recordRef, byte[] reportBytes);
}
