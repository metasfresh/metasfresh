/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

import de.metas.deliveryplanning.DeliveryPlanningList;
import de.metas.deliveryplanning.DeliveryPlanningService;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import lombok.NonNull;
import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryFilter;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Delivery_Planning;

/**
 * Closes the selected delivery plannings: "I am done with this cargo, leave it alone".
 * <p>
 * Deliberately says nothing about the {@code ReleaseNo}: an ALLOCATED planning is exactly the one a planner needs
 * to call off, so a release number - which every allocated planning carries - must not make the action
 * unavailable. The only condition is the one {@link #checkNoneClosed} states: not one selected planning is
 * already closed.
 */
public class M_Delivery_Planning_Close extends JavaProcess implements IProcessPrecondition
{
	private final DeliveryPlanningService deliveryPlanningService = SpringContextHolder.instance.getBean(DeliveryPlanningService.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		return ProcessPreconditionsResolution.firstRejectOrElseAccept(
				() -> DeliveryPlanningProcessHelper.checkAnySelection(context),
				() -> checkNoneClosed(context),
				() -> checkNoBlockedPartner(context));
	}

	/**
	 * Refuses the button as soon as ONE selected planning is already closed, which is the same thing
	 * {@code doIt} does - see {@link DeliveryPlanningService#getCloseRejectionReason(DeliveryPlanningList)} for
	 * why the two have to say it alike.
	 */
	private ProcessPreconditionsResolution checkNoneClosed(@NonNull final IProcessPreconditionsContext context)
	{
		final DeliveryPlanningList selectedDeliveryPlannings = deliveryPlanningService.getBySelection(context.getQueryFilter(I_M_Delivery_Planning.class));

		return deliveryPlanningService.getCloseRejectionReason(selectedDeliveryPlannings)
				.map(ProcessPreconditionsResolution::reject)
				.orElseGet(ProcessPreconditionsResolution::accept);
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
	@RunOutOfTrx
	protected String doIt() throws Exception
	{
		final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter = getProcessInfo().getQueryFilterOrElse(ConstantQueryFilter.of(false));

		deliveryPlanningService.closeSelectedDeliveryPlannings(selectedDeliveryPlanningsFilter);

		return MSG_OK;
	}
}
