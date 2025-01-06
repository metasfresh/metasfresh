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
import de.metas.util.Check;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;

import javax.annotation.Nullable;

public class InterestComputationWorkPackageProcessor extends WorkpackageProcessorAdapter
{
	private final InterestService interestService = SpringContextHolder.instance.getBean(InterestService.class);

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workPackage, @Nullable final String localTrxName)
	{
		interestService.distributeInterestAndBonus(getRequest());
		return Result.SUCCESS;
	}

	private EnqueueInterestComputationRequest getRequest()
	{
		final String requestJsonStr = getParameters().getParameterAsString(InterestComputationEnqueuer.ENQUEUED_REQUEST_PARAM);
		if (Check.isBlank(requestJsonStr))
		{
			throw new AdempiereException("Missing mandatory ENQUEUED_REQUEST_PARAM!");
		}
		return JsonObjectMapperHolder.fromJson(requestJsonStr, EnqueueInterestComputationRequest.class);
	}

}
