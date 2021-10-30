package de.metas.invoicecandidate.api.impl;

import de.metas.async.AsyncBatchId;
import de.metas.invoicecandidate.api.IInvoiceCandUpdateSchedulerRequest;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Properties;

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

@Value
public class InvoiceCandUpdateSchedulerRequest implements IInvoiceCandUpdateSchedulerRequest
{
	public static InvoiceCandUpdateSchedulerRequest of (
			@NonNull final Properties ctx,
			@Nullable final String trxName,
			@Nullable final AsyncBatchId asyncBatchId)
	{
		return new InvoiceCandUpdateSchedulerRequest(ctx, trxName, asyncBatchId);
	}

	String trxName;
	Properties ctx;
	AsyncBatchId asyncBatchId;

	private InvoiceCandUpdateSchedulerRequest(
			@NonNull final Properties ctx,
			@Nullable final String trxName,
			@Nullable final AsyncBatchId asyncBatchId)
	{
		this.ctx = ctx;

		// transaction name it's OK to be null
		this.trxName = trxName;

		this.asyncBatchId = asyncBatchId;
	}
}
