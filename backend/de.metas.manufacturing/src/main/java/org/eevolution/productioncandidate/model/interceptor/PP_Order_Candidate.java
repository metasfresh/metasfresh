/*
 * #%L
 * de.metas.manufacturing
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

package org.eevolution.productioncandidate.model.interceptor;

import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.pporder.PPOrderCandidate;
import de.metas.material.event.pporder.PPOrderCandidateCreatedEvent;
import de.metas.material.event.pporder.PPOrderCandidateUpdatedEvent;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;
import org.compiere.util.TimeUtil;
import org.eevolution.model.I_PP_Order_Candidate;
import org.eevolution.productioncandidate.service.PPOrderCandidatePojoConverter;
import org.eevolution.productioncandidate.service.PPOrderCandidateService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

@Interceptor(I_PP_Order_Candidate.class)
@Component
public class PP_Order_Candidate
{
	private final PPOrderCandidatePojoConverter ppOrderCandidateConverter;
	private final PostMaterialEventService materialEventService;
	private final PPOrderCandidateService ppOrderCandidateService;

	public PP_Order_Candidate(
			@NonNull final PPOrderCandidatePojoConverter ppOrderCandidateConverter,
			@NonNull final PostMaterialEventService materialEventService,
			@NonNull final PPOrderCandidateService ppOrderCandidateService)
	{
		this.ppOrderCandidateConverter = ppOrderCandidateConverter;
		this.materialEventService = materialEventService;
		this.ppOrderCandidateService = ppOrderCandidateService;
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW })
	public void postNewMaterialEvent(@NonNull final I_PP_Order_Candidate ppOrderCandidateRecord)
	{
		final PPOrderCandidate ppOrderCandidatePojo = ppOrderCandidateConverter.toPPOrderCandidate(ppOrderCandidateRecord);

		final PPOrderCandidateCreatedEvent ppOrderCandidateCreatedEvent = PPOrderCandidateCreatedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(ppOrderCandidateRecord.getAD_Client_ID(), ppOrderCandidateRecord.getAD_Org_ID()))
				.ppOrderCandidate(ppOrderCandidatePojo)
				.build();

		materialEventService.postEventAfterNextCommit(ppOrderCandidateCreatedEvent);
	}

	@ModelChange(
			timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_PP_Order_Candidate.COLUMNNAME_QtyEntered, I_PP_Order_Candidate.COLUMNNAME_QtyProcessed })
	public void syncLinesAndMaterialDisposition(@NonNull final I_PP_Order_Candidate ppOrderCandidateRecord)
	{
		validateQuantities(ppOrderCandidateRecord);

		ppOrderCandidateRecord.setQtyToProcess(ppOrderCandidateRecord.getQtyEntered().subtract(ppOrderCandidateRecord.getQtyProcessed()));

		if (!InterfaceWrapperHelper.isNew(ppOrderCandidateRecord))
		{
			ppOrderCandidateService.syncLinesWithRequiredQty(ppOrderCandidateRecord);

			fireMaterialUpdateEvent(ppOrderCandidateRecord);
		}
	}

	@ModelChange(
			timings = { ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_PP_Order_Candidate.COLUMNNAME_DateStartSchedule })
	public void onDateStartSchedule(@NonNull final I_PP_Order_Candidate ppOrderCandidateRecord)
	{
		final Optional<Instant> recomputedDatePromised = ppOrderCandidateService.recalculateDatePromised(ppOrderCandidateRecord);

		if (!recomputedDatePromised.isPresent())
		{
			return;
		}

		final Timestamp datePromised = TimeUtil.asTimestamp(recomputedDatePromised.get());
		ppOrderCandidateRecord.setDatePromised(datePromised);

		fireMaterialUpdateEvent(ppOrderCandidateRecord);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_NEW }, ifColumnsChanged = I_PP_Order_Candidate.COLUMNNAME_QtyToProcess)
	public void validateQtyToProcess(@NonNull final I_PP_Order_Candidate ppOrderCandidateRecord)
	{
		final BigDecimal qtyEntered = ppOrderCandidateRecord.getQtyEntered();
		final BigDecimal qtyProcessed = ppOrderCandidateRecord.getQtyProcessed();
		final BigDecimal actualQtyLeftToBeProcessed = qtyEntered.subtract(qtyProcessed);

		if (ppOrderCandidateRecord.getQtyToProcess().compareTo(actualQtyLeftToBeProcessed) > 0)
		{
			throw new AdempiereException("QtyToProcess cannot be greater then the actual qty left to be processed!")
					.appendParametersToMessage()
					.setParameter("PP_Order_Candidate_ID", ppOrderCandidateRecord.getPP_Order_Candidate_ID())
					.setParameter("PP_Order_Candidate.QtyToProcess", ppOrderCandidateRecord.getQtyToProcess())
					.setParameter("PP_Order_Candidate.QtyEntered", ppOrderCandidateRecord.getQtyEntered())
					.setParameter("PP_Order_Candidate.QtyProcessed", ppOrderCandidateRecord.getQtyProcessed())
					.markAsUserValidationError();
		}
	}

	private void validateQuantities(@NonNull final I_PP_Order_Candidate ppOrderCandidateRecord)
	{
		final BigDecimal qtyEntered = ppOrderCandidateRecord.getQtyEntered();
		final BigDecimal qtyProcessed = ppOrderCandidateRecord.getQtyProcessed();

		if (qtyEntered.compareTo(qtyProcessed) < 0)
		{
			throw new AdempiereException("QtyEntered cannot go lower than the QtyProcessed!")
					.appendParametersToMessage()
					.setParameter("PP_Order_Candidate_ID", ppOrderCandidateRecord.getPP_Order_Candidate_ID())
					.setParameter("QtyProcessed", ppOrderCandidateRecord.getQtyProcessed())
					.setParameter("QtyEntered", ppOrderCandidateRecord.getQtyEntered());
		}
	}

	private void fireMaterialUpdateEvent(@NonNull final I_PP_Order_Candidate ppOrderCandidateRecord)
	{
		final PPOrderCandidate ppOrderCandidatePojo = ppOrderCandidateConverter.toPPOrderCandidate(ppOrderCandidateRecord);

		final PPOrderCandidateUpdatedEvent ppOrderCandidateUpdatedEvent = PPOrderCandidateUpdatedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(ppOrderCandidateRecord.getAD_Client_ID(), ppOrderCandidateRecord.getAD_Org_ID()))
				.ppOrderCandidate(ppOrderCandidatePojo)
				.build();

		materialEventService.postEventAfterNextCommit(ppOrderCandidateUpdatedEvent);
	}
}