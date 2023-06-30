/*
 * #%L
 * de-metas-salesorder
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.salesorder.candidate;

import de.metas.async.AsyncBatchId;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.util.Services;
import lombok.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Service;

import static org.compiere.util.Env.getCtx;

@Service
public class ProcessOLCandsWorkpackageEnqueuer
{
	public static final String WP_PARAM_SHIP = "SHIP";
	public static final String WP_PARAM_INVOICE = "INVOICE";
	public static final String WP_PARAM_CLOSE_ORDER = "CLOSE_ORDER";
	public static final String WP_PARAM_VALID_OLCANDIDS_SELECTIONID = "VALID_OLCANDIDS_SELECTIONID";

	private final IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);

	public void enqueue(@NonNull final ProcessOLCandsRequest request, @Nullable final AsyncBatchId processOLCandsBatchId)
	{
		workPackageQueueFactory.getQueueForEnqueuing(getCtx(), ProcessOLCandsWorkpackageProcessor.class)
				.newWorkPackage()
				.bindToThreadInheritedTrx()
				.setC_Async_Batch_ID(processOLCandsBatchId)
				.parameter(WP_PARAM_VALID_OLCANDIDS_SELECTIONID, request.getPInstanceId())
				.parameter(WP_PARAM_SHIP, request.isShip())
				.parameter(WP_PARAM_INVOICE, request.isInvoice())
				.parameter(WP_PARAM_CLOSE_ORDER, request.isCloseOrder())
				.buildAndEnqueue();
	}
}
