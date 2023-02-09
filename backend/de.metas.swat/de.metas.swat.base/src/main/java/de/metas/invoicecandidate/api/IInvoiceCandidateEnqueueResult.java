package de.metas.invoicecandidate.api;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.async.api.IEnqueueResult;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.lock.api.ILock;

import java.math.BigDecimal;
import java.util.Properties;

/**
 * Result containing information about what was successfully enqueued by {@link IInvoiceCandidateEnqueuer}.
 *
 * @author al
 */
public interface IInvoiceCandidateEnqueueResult extends IEnqueueResult
{
	/**
	 * @return user friendly (translated) summary about what was enqueued
	 */
	String getSummaryTranslated(final Properties ctx);

	/**
	 * @return how many invoice candidates were enqueued
	 */
	int getInvoiceCandidateEnqueuedCount();

	/**
	 * @return how many workpackages were enqueued
	 */
	int getWorkpackageEnqueuedCount();

	/**
	 * @return how many workpackages were there unprocessed before we enqueued this batch
	 */
	int getWorkpackageQueueSizeBeforeEnqueueing();

	/**
	 * @return sum of all {@link I_C_Invoice_Candidate#getNetAmtToInvoice()}, without considering the currency (08610)
	 */
	BigDecimal getTotalNetAmtToInvoiceChecksum();

	/**
	 * Gets the lock used to initially lock the invoice candidates (right before enqueue).
	 * <p>
	 * After invoice candidates were enqueued, this lock shall block NO invoice candidates.
	 *
	 * @return lock
	 */
	ILock getLock();
}
