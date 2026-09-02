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
import lombok.NonNull;
import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryFilter;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Delivery_Planning;

/**
 * Re-opens the selected delivery plannings: takes back the "leave this cargo alone" that Close set.
 * <p>
 * The mirror of {@link M_Delivery_Planning_Close}, down to the selection semantics: the only condition is the
 * one {@link #checkNoneOpen} states - not one selected planning is still open.
 */
public class M_Delivery_Planning_ReOpen extends JavaProcess implements IProcessPrecondition
{
	private final DeliveryPlanningService deliveryPlanningService = SpringContextHolder.instance.getBean(DeliveryPlanningService.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		return ProcessPreconditionsResolution.firstRejectOrElseAccept(
				() -> DeliveryPlanningProcessHelper.checkAnySelection(context),
				() -> checkNoneOpen(context),
				() -> checkNoBlockedPartner(context));
	}

	/**
	 * Refuses the button as soon as ONE selected planning is still open, which is the same thing {@code doIt}
	 * does - see {@link DeliveryPlanningService#getReOpenRejectionReason(DeliveryPlanningList)} for why the two
	 * have to say it alike.
	 */
	private ProcessPreconditionsResolution checkNoneOpen(@NonNull final IProcessPreconditionsContext context)
	{
		final DeliveryPlanningList selectedDeliveryPlannings = deliveryPlanningService.getBySelection(context.getQueryFilter(I_M_Delivery_Planning.class));

		return deliveryPlanningService.getReOpenRejectionReason(selectedDeliveryPlannings)
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

	protected String doIt() throws Exception
	{
		final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter = getProcessInfo().getQueryFilterOrElse(ConstantQueryFilter.of(false));

		deliveryPlanningService.reOpenSelectedDeliveryPlannings(selectedDeliveryPlanningsFilter);

		return MSG_OK;
	}
}
