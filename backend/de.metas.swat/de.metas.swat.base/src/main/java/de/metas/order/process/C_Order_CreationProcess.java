/*
 * #%L
 * de.metas.swat.base
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

package de.metas.order.process;

import de.metas.document.references.zoom_into.RecordWindowFinder;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Order;

import javax.annotation.Nullable;

public abstract class C_Order_CreationProcess extends JavaProcess implements IProcessPrecondition
{
	private final IOrderBL orderBL = Services.get(IOrderBL.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{

		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		if (context.isMoreThanOneSelected())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final OrderId orderId = OrderId.ofRepoId(context.getSingleSelectedRecordId());
		final I_C_Order order = orderBL.getById(orderId);
		return checkPreconditionsApplicable(order);
	}

	public abstract ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull I_C_Order order);

	protected void openOrder(@NonNull final I_C_Order order)
	{
		final AdWindowId orderWindowId = RecordWindowFinder
				.findAdWindowId(TableRecordReference.of(order))
				.orElse(null);
		openOrder(order, orderWindowId);
	}

	protected void openOrder(@NonNull final I_C_Order order, @Nullable final AdWindowId orderWindowId)
	{
		if (orderWindowId == null)
		{
			log.warn("Skip opening {} because no window found for it", order);
			return;
		}

		getResult().setRecordToOpen(
				TableRecordReference.of(order),
				orderWindowId.getRepoId(),
				ProcessExecutionResult.RecordsToOpen.OpenTarget.SingleDocument,
				ProcessExecutionResult.RecordsToOpen.TargetTab.SAME_TAB);
	}
}
