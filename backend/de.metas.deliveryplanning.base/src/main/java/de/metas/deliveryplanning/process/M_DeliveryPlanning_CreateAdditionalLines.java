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

import de.metas.deliveryplanning.DeliveryPlanningId;
import de.metas.deliveryplanning.DeliveryPlanningService;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Check;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

public class M_DeliveryPlanning_CreateAdditionalLines extends JavaProcess implements IProcessPrecondition
{
	private final DeliveryPlanningService deliveryPlanningService = SpringContextHolder.instance.getBean(DeliveryPlanningService.class);
	@Param(parameterName = DeliveryPlanningService.PARAM_AdditionalLines)
	private int additionalLines;

	@Override
	protected String doIt() throws Exception
	{
		Check.assumeGreaterThanZero(additionalLines, DeliveryPlanningService.PARAM_AdditionalLines);

		final DeliveryPlanningId deliveryPlanningId = DeliveryPlanningId.ofRepoId(getRecord_ID());

		deliveryPlanningService.createAdditionalDeliveryPlannings(deliveryPlanningId, additionalLines);

		return MSG_OK;
	}

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

		final DeliveryPlanningId deliveryPlanningId = DeliveryPlanningId.ofRepoId(context.getSingleSelectedRecordId());

		if (deliveryPlanningService.isClosed(deliveryPlanningId))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason(msgBL.getTranslatableMsgText(DeliveryPlanningService.MSG_M_Delivery_Planning_AllClosed));
		}

		final boolean existsBlockedPartnerDeliveryPlannings = deliveryPlanningService.hasBlockedBPartner(deliveryPlanningId);

		if (existsBlockedPartnerDeliveryPlannings)
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(DeliveryPlanningService.MSG_M_Delivery_Planning_BlockedPartner));
		}

		return ProcessPreconditionsResolution.accept();
	}
}
