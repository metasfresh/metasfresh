/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.deliveryplanning.process;

import de.metas.deliveryplanning.DeliveryPlanningCancelResult;
import de.metas.deliveryplanning.DeliveryPlanningId;
import de.metas.deliveryplanning.DeliveryPlanningList;
import de.metas.deliveryplanning.DeliveryPlanningService;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;
import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryFilter;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Delivery_Planning;

public class M_Delivery_Planning_CancelDeliveryInstruction extends JavaProcess implements IProcessPrecondition
{
	private final DeliveryPlanningService deliveryPlanningService = SpringContextHolder.instance.getBean(DeliveryPlanningService.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		return ProcessPreconditionsResolution.firstRejectOrElseAccept(
				() -> DeliveryPlanningProcessHelper.checkAnySelection(context),
				() -> checkAnyOpen(context),
				() -> checkAnyWithReleaseNo(context),
				() -> checkNoBlockedPartner(context));
	}

	private ProcessPreconditionsResolution checkAnyOpen(@NonNull final IProcessPreconditionsContext context)
	{
		final DeliveryPlanningList selectedDeliveryPlannings = deliveryPlanningService.getBySelection(context.getQueryFilter(I_M_Delivery_Planning.class));

		if (!selectedDeliveryPlannings.anyOpen())
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(DeliveryPlanningService.MSG_M_Delivery_Planning_AllClosed));
		}

		return ProcessPreconditionsResolution.accept();
	}

	private ProcessPreconditionsResolution checkAnyWithReleaseNo(@NonNull final IProcessPreconditionsContext context)
	{
		final boolean isExistDeliveryPlanningsWithReleaseNo = deliveryPlanningService.isExistDeliveryPlanningsWithReleaseNo(context.getQueryFilter(I_M_Delivery_Planning.class));

		if (!isExistDeliveryPlanningsWithReleaseNo)
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(DeliveryPlanningService.MSG_M_Delivery_Planning_WhithOutReleaseNo));
		}

		return ProcessPreconditionsResolution.accept();
	}

	private ProcessPreconditionsResolution checkNoBlockedPartner(@NonNull final IProcessPreconditionsContext context)
	{
		final boolean existsBlockedPartnerDeliveryPlannings = deliveryPlanningService.isExistsBlockedPartnerDeliveryPlannings(context.getQueryFilter(I_M_Delivery_Planning.class));

		if (existsBlockedPartnerDeliveryPlannings)
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(DeliveryPlanningService.MSG_M_Delivery_Planning_BlockedPartner));
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter = getProcessInfo().getQueryFilterOrElse(ConstantQueryFilter.of(false));

		final DeliveryPlanningCancelResult result = deliveryPlanningService.cancelDelivery(selectedDeliveryPlanningsFilter);

		// per-row report: a closed planning does not abort the run, it is named here instead
		for (final DeliveryPlanningId skippedId : result.getSkippedClosedIds())
		{
			addLog(msgBL.getMsg(getCtx(), DeliveryPlanningService.MSG_M_Delivery_Planning_Closed, new Object[] { skippedId.getRepoId() }));
		}

		// per-row report: still cancelled, but its planned figures were committed cargo and were left as they were
		for (final DeliveryPlanningId skippedId : result.getSkippedAllocatedIds())
		{
			addLog(msgBL.getMsg(getCtx(), DeliveryPlanningService.MSG_M_Delivery_Planning_CancelAllocated, new Object[] { skippedId.getRepoId() }));
		}

		return MSG_OK;
	}
}
