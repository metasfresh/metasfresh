/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.contracts.modular.interest;

import de.metas.JsonObjectMapperHolder;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.lock.api.ILock;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;

import javax.annotation.Nullable;
import java.util.Optional;

public class InterestComputationWorkPackageProcessor extends WorkpackageProcessorAdapter
{
	private final InterestComputationService interestComputationService = SpringContextHolder.instance.getBean(InterestComputationService.class);

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workPackage, @Nullable final String localTrxName)
	{
		interestComputationService.computeInterest(getRequest());

		return Result.SUCCESS;
	}

	@NonNull
	private InterestComputationRequest getRequest()
	{
		return Optional.of(getParameters())
				.map(params -> params.getParameterAsString(InterestComputationEnqueuer.ENQUEUED_REQUEST_PARAM))
				.map(serializedRequest -> JsonObjectMapperHolder.fromJson(serializedRequest, EnqueueInterestComputationRequest.class))
				.map(enqueueRequest -> InterestComputationRequest.builder()
						.interestToDistribute(enqueueRequest.getInterestToDistribute())
						.billingDate(enqueueRequest.getBillingDate())
						.interimDate(enqueueRequest.getInterimDate())
						.invoicingGroupId(enqueueRequest.getInvoicingGroupId())
						.lockOwner(getElementsLock().map(ILock::getOwner).orElseThrow(() -> new AdempiereException("Missing lock owner!")))
						.build())
				.orElseThrow(() -> new AdempiereException("Missing mandatory ENQUEUED_REQUEST_PARAM!"));
	}
}
