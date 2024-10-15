/*
 * #%L
 * de.metas.handlingunits.base
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

package de.metas.handlingunits.pporder.interceptor;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.model.I_PP_Order;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.handlingunits.pporder.api.impl.async.HUMaturingWorkpackageProcessor;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueScheduleRepository;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidator;
import org.eevolution.api.PPOrderId;
import org.eevolution.api.PPOrderPlanningStatus;
import org.eevolution.model.I_PP_Order_Candidate;
import org.eevolution.productioncandidate.model.dao.PPOrderCandidateDAO;
import org.springframework.stereotype.Component;

@Interceptor(I_PP_Order.class)
@Component
public class PP_Order
{
	private final IHUPPOrderBL ppOrderBL = Services.get(IHUPPOrderBL.class);
	private final PPOrderIssueScheduleRepository ppOrderIssueScheduleRepository;
	private final PPOrderCandidateDAO ppOrderCandidateDAO;

	public PP_Order(
			@NonNull final PPOrderIssueScheduleRepository ppOrderIssueScheduleRepository,
			@NonNull final PPOrderCandidateDAO ppOrderCandidateDAO)
	{
		this.ppOrderIssueScheduleRepository = ppOrderIssueScheduleRepository;
		this.ppOrderCandidateDAO = ppOrderCandidateDAO;
	}

	@DocValidate(timings = ModelValidator.TIMING_BEFORE_CLOSE)
	public void onBeforeClose(@NonNull final I_PP_Order order)
	{
		final PPOrderId ppOrderId = PPOrderId.ofRepoId(order.getPP_Order_ID());
		ppOrderIssueScheduleRepository.deleteNotProcessedByOrderId(ppOrderId);

		final PPOrderPlanningStatus planningStatus = PPOrderPlanningStatus.ofCode(order.getPlanningStatus());
		if (!planningStatus.isComplete())
		{
			ppOrderBL.processPlanning(PPOrderPlanningStatus.COMPLETE, ppOrderId);
		}
	}

	@DocValidate(timings = {
			ModelValidator.TIMING_BEFORE_VOID,
			ModelValidator.TIMING_BEFORE_REACTIVATE,
			ModelValidator.TIMING_BEFORE_REVERSECORRECT,
			ModelValidator.TIMING_BEFORE_REVERSEACCRUAL })
	public void onBeforeReverse(@NonNull final I_PP_Order order)
	{
		final PPOrderId ppOrderId = PPOrderId.ofRepoId(order.getPP_Order_ID());
		reverseIssueSchedules(ppOrderId);
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	public void onAfterComplete(@NonNull final I_PP_Order order)
	{
		final PPOrderId ppOrderId = PPOrderId.ofRepoId(order.getPP_Order_ID());
		final ImmutableList<I_PP_Order_Candidate> candidates = ppOrderCandidateDAO.getByOrderId(ppOrderId);

		if (!ppOrderBL.isAtLeastOneCandidateMaturing(candidates))
		{
			return;
		}

		if (candidates.size() > 1)
		{
			throw new AdempiereException("More than one candidate found for maturing !");
		}

		// dev-note: use WorkPackage, otherwise PP_Cost_Collector is posted before PP_Order and therefore posting is failing
		HUMaturingWorkpackageProcessor.prepareWorkpackage()
				.ppOrder(order)
				.enqueue();
	}

	private void reverseIssueSchedules(@NonNull final PPOrderId ppOrderId)
	{
		ppOrderIssueScheduleRepository.deleteNotProcessedByOrderId(ppOrderId);
		if (ppOrderIssueScheduleRepository.matchesByOrderId(ppOrderId))
		{
			throw new AdempiereException("Reversing processed issue schedules is not allowed");
		}
	}
}
